package com.yice.edu.cn.osp.controller.oa.oaStats;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.oa.oaStats.OaStatsService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/oaStats")
public class OaStatsController {

    @Autowired
    private OaStatsService oaStatsService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/findTotalStats")
    public ResponseJson findTotalStats(@RequestBody  @Validated OaStats oaStats){
        String[] rangeTime = oaStats.getRangeTime();
        // 验证rangeTime非空并且时间范围正在一年之内
        if(null == rangeTime && rangeTime.length != 2 ){
            return new ResponseJson(false, "时间范围必须是两个字符串元素");
        }
        return oaStatsService.findTotalStats(oaStats, mySchoolId());
    }


    @PostMapping("/findStatsDetail")
    public ResponseJson findStatsDetail(@Validated @RequestBody ProcessBusinessData processBusinessData){
        if(null == processBusinessData.getRangeTime() && processBusinessData.getRangeTime().length != 2){
            return new ResponseJson(false,"参数不正确,发起时间不能为空");
        }
        processBusinessData.setSchoolId(mySchoolId());
        if(StrUtil.isNotEmpty(processBusinessData.getSchoolYearId())){
            curSchoolYearService.setSchoolYearId(processBusinessData,mySchoolId());
        }
        return oaStatsService.findStatsDetail(processBusinessData);
    }

    @PostMapping("/exportExcel")
    public void exportExcel(@NotNull String processBusinessData,@NotNull String columns,String titleBar,Integer total, HttpServletResponse response){
        ProcessBusinessData ps =  JSONUtil.toBean(processBusinessData,ProcessBusinessData.class);
        ps.setSchoolId(mySchoolId());
        List<Map<String, String>> cls = JSON.parseObject(columns, new TypeReference<List<Map<String, String>>>(){});
        ps.getPager().setPageSize(Objects.isNull(total) ? 100 : total);
        ResponseJson responseJson = oaStatsService.findStatsDetail(ps);
        Map<String,Object> statsDetailFacet = (Map<String,Object>) responseJson.getData();
        List<Map<String,Object>> processBusinessDatas = (List<Map<String,Object>>) responseJson.getData2();
        //构建个列表用来支持动态列导出
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("序号", "index"));
        boolean isCancel = false;
        for(int i=0,len=cls.size();i<len;i++){
            Map<String, String> v = cls.get(i);
            String  field = v.get("field");
            if(field.startsWith("formData.")){
                field = field.substring(9);
            }
            if(field.equals("cancelReason")){
                isCancel = true;
            }
            entityList.add(new ExcelExportEntity(v.get("label"), field,30));
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < processBusinessDatas.size(); i++) {
            Map<String, Object> businessData = processBusinessDatas.get(i);
            //整个formData的map塞进去得了
            Map<String, Object> map = (Map<String, Object>)businessData.get("formData");
            // 转换数据字典
            convert((List<Map<String, Object>>) businessData.get("processForms"),map);
            map.put("index",i+1);
            map.put("initiator", businessData.get("initiator"));
            map.put("type", businessData.get("type"));
            map.put("createTime", businessData.get("createTime"));
            map.put("approver", handlePerson((List<Map<String,Object>>)businessData.get("approver")));
            map.put("copyFor",handlePerson( (List<Map<String,Object>>)businessData.get("copyFor")));
            String status = Optional.ofNullable(businessData.get("status")).map(v -> {
               int var1 =  Integer.valueOf(v.toString());
                boolean clearLeave = (boolean) businessData.getOrDefault("clearLeave", false);
                if(clearLeave && businessData.containsKey("clearLeaveTime")){
                    return "确认到校";
               }
               return var1==0?"待审批":var1==1?"通过":"驳回";
            }).orElse("");
            map.put("status", status);
            if(isCancel){
                map.put("cancelReason", Objects.nonNull(businessData.get("cancelReason")) ? "已撤销" :"未撤销" );
            }
            list.add(map);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(handle4Stats(statsDetailFacet,titleBar), "统计"), entityList, list);
        try(ServletOutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 转换字典名称
     * @param processForms
     * @param map
     */
    private void convert(List<Map<String, Object>> processForms, Map<String, Object> map) {
        processForms.stream().forEach(v ->{
            String key = MapUtil.getStr(v,"name");
            Object obj =map.get(key);
            if(Objects.isNull(obj)) { return; }
            switch (MapUtil.getStr(v,"formType")){
                case "selectTeacher":
                    Map<String,Object> teacher= (Map<String, Object>)obj;
                    map.put(key,teacher.get("name"));
                    break;
                case "multiSelectTeacher":
                    ArrayList<Map<String,Object>> teachers= (ArrayList<Map<String,Object>>)obj;
                    map.put(key, teachers.stream().map(t -> t.get("name").toString()).collect(Collectors.joining(",")));
                    break;
                case "selectAssetFile":
                    Map<String,Object> selectAssetFile= (Map<String, Object>)obj;
                    map.put(key,selectAssetFile.get("assetsName"));
                    break;
                case "selectAsset":
                    Map<String,Object> asset= (Map<String, Object>)obj;
                    map.put(key,asset.get("assetsName"));
                    break;
                    default:
            }
            if(v.containsKey("localDatasource")){
                List<Map<String,Object>> localDatasource= (List<Map<String, Object>>) v.get("localDatasource");
                switch(MapUtil.getStr(v,"dataType")){
                    case "String":
                        String newVal = localDatasource.stream().filter( loc -> StrUtil.equals(obj.toString(),MapUtil.getStr(loc,"id")) )
                                .map(m-> MapUtil.getStr(m,"name")).findAny().orElse("");
                        map.put(key,newVal);
                        break;
                    case "Array":
                        List<String> vals= (List<String>) obj;
                        newVal = localDatasource.stream().filter( loc -> vals.contains(MapUtil.getStr(loc,"id")))
                                .map(m-> MapUtil.getStr(m,"name")).collect(Collectors.joining(","));
                        map.put(key,newVal);
                        break;
                    default:
                }
            }
        });
    }

    private String handlePerson(List<Map<String,Object>> oaPeoples){
        if(CollectionUtil.isNotEmpty(oaPeoples)){
            StringBuilder r= new StringBuilder();
            for (Map<String,Object> oaPeople : oaPeoples) {
                r.append(",").append(oaPeople.get("name"));
            }
            return r.toString().replaceAll("^,", "");
        }
        return "";
    }

    private String handle4Stats(Map<String,Object> data,String titleBar){
        StringBuilder sb = new StringBuilder();
        sb.append("申请总次数：").append(((List<Map<String,Object>>)data.get("total")).get(0).get("count")).append("次").append("     ")
                .append("次数最多的人:").append(((List<Map<String,Object>>)data.get("maxPerson")).get(0).get("initiator")).append(" ").append(((List<Map<String,Object>>)data.get("maxPerson")).get(0).get("count")).append("次");
        if(data.containsKey("maxDepartment")){
            List<Map<String, Object>> dep = (List<Map<String,Object>>)data.get("maxDepartment");
            if(dep.get(0).containsKey("departmentName")){
                 sb.append("\r\n").append("申请最多部门:").append(((List<Map<String,Object>>)data.get("maxDepartment")).get(0).get("departmentName"))
                        .append(" ").append(((List<Map<String,Object>>)data.get("maxDepartment")).get(0).get("count")).append("次");
            }
        }
       if(StrUtil.isNotEmpty(titleBar)){
           sb.append("  ").append(titleBar);
       }
        return sb.toString();


    }

    /**
     * 根据教师id和时间范围以及分页参数进行查询
     * @param teacherId
     * @param pager
     * @return
     */
    @PostMapping("/findProcessesByRangeTime/{teacherId}")
    @ApiOperation(value = "根据教师id和时间范围以及分页参数进行查询",notes = "教师id和分页参数必传,时间范围可选",response = ProcessBusinessData.class)
    public ResponseJson findProcessesByRangeTime(@PathVariable("teacherId") String teacherId, @Validated @RequestBody Pager pager){
        ResponseJson responseJson = oaStatsService.findProcessesByRangeTime(teacherId, pager);
        return responseJson;
    }
    @PostMapping("/ignore/findTotalMoney")
    @ApiOperation(value = "根据学校id及流程id获取审批成功统计的金额",notes = "返回响应对象ResponseJson",response = ResponseJson.class)
    public ResponseJson findTotalMoney(@RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getProcessLibId())){
            return new ResponseJson(false,"请正确选择流程类型");
        }
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime ==null && rangeTime.length != 2) {
            return new ResponseJson(false,"查询时间范围格式不正确");
        }
        rangeTime[0] = rangeTime[0] + " 00:00:00";
        rangeTime[1] = rangeTime[1] + " 23:59:59";
        processBusinessData.setRangeTime(rangeTime);
        processBusinessData.setSchoolId(mySchoolId());
        return oaStatsService.findTotalMoney(processBusinessData);
    }

}
