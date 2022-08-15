package com.yice.edu.cn.osp.controller.xw.dutyArrment;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.osp.service.xw.checkedDetail.CheckedDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/allSchoolDuty")
@Api(value = "/allSchoolDuty",description = "全校值班")
public class AllSchoolDutyController {

    @Autowired
    private CheckedDetailService checkedDetailService;

    @PostMapping("/look/lookAllSchoolDutyById/findCheckedDetailsByMonthCondition")
    @ApiOperation(value = "根据条件查找每个月的安排值班几次", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsByMonthCondition(
            @ApiParam(value = "属性不为空则作为条件查询(monthCheckTime:2019-02)")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());//学校id
        if(checkedDetail.getMonthCheckTime()!=null){
            String[] recordDate = checkedDetail.getMonthCheckTime().split("-");
            String firstDay = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
            String lastDay = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
            Pager pager = new Pager();
            checkedDetail.setPager(pager);
            checkedDetail.getPager().setRangeArray(new String[] {firstDay,lastDay });
            checkedDetail.getPager().setRangeField("recordDate");//符合范围内的数据
            checkedDetail.getPager().setIncludes(new String[]{"dutyArrmentSpace","recordDate","schoolId"});
        }
        List<CheckedDetail> data=checkedDetailService.findCheckedDetailListByCondition4like(checkedDetail);
        Map<String,Integer> mapOut = null;
        if(data.size()>0){
            Map<String, List<CheckedDetail>> collect =
                    data.stream().collect(Collectors.groupingBy(CheckedDetail::getRecordDate));
            Map<String,Integer> map = new HashMap<>();
            collect.forEach((k,v)->{
                map.put(k,v.size());
            });
            mapOut = map;
        }
        return new ResponseJson(mapOut);
    }

    @PostMapping("/download/downloadOne")
    public void exportTeacher(@ApiParam(value = "导出当月明细只传（比如：2019-02)")
                              @RequestBody CheckedDetail checkedDetail, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("Content-Disposition", "attachment;filename=checkedDetail.xls");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try (ServletOutputStream s = response.getOutputStream()) {
            checkedDetail.setSchoolId(mySchoolId());
            if(checkedDetail.getMonthCheckTime() !=null){
                String[] recordDate = checkedDetail.getMonthCheckTime().split("-");
                String firstDay = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
                String lastDay = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(recordDate[0]),Integer.parseInt(recordDate[1]));
                Pager pager = new Pager();
                pager.setPaging(false);
                checkedDetail.setPager(pager);
                checkedDetail.getPager().setRangeArray(new String[] {firstDay,lastDay });
                checkedDetail.getPager().setRangeField("recordDate");//符合范围内的数据
                Workbook workbook = checkedDetailService.exportCheckedDetail(checkedDetail);
                workbook.write(s);
            }
        } catch (Exception e) {

        }
    }

    @PostMapping("/look/lookAllSchoolDutyById/lookCheckedDetailsByCondition")
    @ApiOperation(value = "查看", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson lookCheckedDetailsByCondition(
            @ApiParam(value = "传入日期进行查询{recordDate:2019-02-04}")
            @RequestBody CheckedDetail checkedDetail){
        CheckedDetail one = new CheckedDetail();
        one.setSchoolId(mySchoolId());
        one.setRecordDate(checkedDetail.getRecordDate());
        if(StringUtils.isNotBlank(checkedDetail.getSpaceId())){
            one.setSpaceId(checkedDetail.getSpaceId());
        }
        if(StringUtils.isNotBlank(checkedDetail.getDutyType())){
            one.setDutyType(checkedDetail.getDutyType());
        }
        List<CheckedDetail> data= checkedDetailService.findCheckedDetailListByCondition(one);
        Map<String, List<CheckedDetail>> map = new HashMap<>();
        if(!data.isEmpty()) {
            map = data.stream().collect(Collectors.groupingBy(CheckedDetail::getDutyArrmentId));
        }
        List<CheckedDetail> list = new ArrayList<>();
        Long count = 0L;
        if(!map.isEmpty()){
            map.forEach((k,v)->{
                if(!v.isEmpty()){
                    list.add(v.get(0));
                }
            });
        }
        List<CheckedDetail> resultPage = new ArrayList<>();
        List<CheckedDetail> two = new ArrayList<>();
        if(!list.isEmpty()){
            two = list.stream().sorted((t1,t2)->{
                return  t1.getDutyTimeStart().compareTo(t2.getDutyTimeStart());
            }).collect(Collectors.toList());
            int num = (checkedDetail.getPager().getPageSize())*(checkedDetail.getPager().getPage()-1);//新增分页
            resultPage = two.stream().skip(num).limit(checkedDetail.getPager().getPageSize()).collect(Collectors.toList());
            count = new Long(two.size());
        }
        return new ResponseJson(resultPage,count);
    }

    @PostMapping("/look/lookAllSchoolDutyById/findCheckedDetailsByCondition")
    @ApiOperation(value = "查看->签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsByCondition(
            @ApiParam(value = "recordDate,dutyArrmentId,要传page对象")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        List<CheckedDetail> data= checkedDetailService.findCheckedDetailListByCondition(checkedDetail);
        long count = checkedDetailService.findCheckedDetailCountByCondition(checkedDetail);
        return new ResponseJson(data,count);
    }

    @PostMapping("/download/downloadSecond")
    public void downloadSecond(@ApiParam(value = "查看->签到明细->导出execle文档|传入参数:recordDate,dutyArrmentId")
                               @RequestBody CheckedDetail checkedDetail, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("Content-Disposition", "attachment;filename=checkedDetail.xls");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        try (ServletOutputStream s = response.getOutputStream()) {
            checkedDetail.setSchoolId(mySchoolId());
            Workbook workbook = checkedDetailService.exportCheckedDetail(checkedDetail);
            workbook.write(s);
        } catch (Exception e) {

        }
    }

    @PostMapping("/update/updateCheckedDetailsSchoolByCondition")
    @ApiOperation(value = "根据条件修改全校细表", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson updateCheckedDetailsSchoolByCondition(
            @ApiParam(value = "recordDate,dutyArrmentId,dutyOfficer,dutyAfterSyn,dutyteachersDutyarrmentCheck,dutyType,dutyOfficerId,punchTimeLater")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        CheckedDetail c = checkedDetailService.updateTochangeCheckedDetail4Like(checkedDetail);
        return new ResponseJson(c);
    }

    @PostMapping("/change/changeCheckedDetailsSchoolByCondition")
    @ApiOperation(value = "根据条件变更明细表", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson changeCheckedDetailsSchoolByCondition(
            @ApiParam(value = "dutyArrmentId,punchTimeLater,recordDate,changedDutye")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        CheckedDetail c = checkedDetailService.updateCheckedDetail4Like(checkedDetail);
        return new ResponseJson(c);
    }

    @PostMapping("/deleteAllSchoolDuty/deleteAllSchoolCheckDetailByCondition")
    @ApiOperation(value = "根据值班计划删除", notes = "返回响应对象")
    public ResponseJson deleteAllSchoolCheckDetailByCondition(
            @ApiParam(value = "dutyArrmentId,punchTimeLater,recordDate", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetail.setSchoolId(mySchoolId());
        CheckedDetail s = checkedDetailService.deleteCheckedDetailByCondition4Like(checkedDetail);
        return new ResponseJson(s);
    }
}
