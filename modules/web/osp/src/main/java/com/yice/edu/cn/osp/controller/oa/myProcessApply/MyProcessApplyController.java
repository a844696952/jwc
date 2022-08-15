package com.yice.edu.cn.osp.controller.oa.myProcessApply;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.oa.processBusinessData.ProcessBusinessDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/myProcessApply")
@Api(value = "/myProcessApply", description = "我的流程申请")
public class MyProcessApplyController {
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/saveProcessBusinessData")
    @ApiOperation(value = "保存流程业务数据对象", notes = "返回响应对象")
    public ResponseJson saveProcessBusinessData(
            @ApiParam(value = "流程业务数据对象", required = true)
            @RequestBody ProcessBusinessData processBusinessData) {
        processBusinessData.setSchoolId(mySchoolId());
        ProcessBusinessData s = processBusinessDataService.saveProcessBusinessData(processBusinessData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findProcessBusinessDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流程业务数据", notes = "返回响应对象")
    public ResponseJson findProcessBusinessDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        ProcessBusinessData processBusinessData = processBusinessDataService.findProcessBusinessDataById(id);
        return new ResponseJson(processBusinessData);
    }

    @PostMapping("/update/updateProcessBusinessData")
    @ApiOperation(value = "修改流程业务数据对象", notes = "返回响应对象")
    public ResponseJson updateProcessBusinessData(
            @ApiParam(value = "被修改的流程业务数据对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessBusinessData processBusinessData) {
        processBusinessDataService.updateProcessBusinessData(processBusinessData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookProcessBusinessDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流程业务数据", notes = "返回响应对象")
    public ResponseJson lookProcessBusinessDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        ProcessBusinessData processBusinessData = processBusinessDataService.findProcessBusinessDataById(id);
        return new ResponseJson(processBusinessData);
    }

    @PostMapping("/findProcessBusinessDatasByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据", notes = "返回响应对象")
    public ResponseJson findProcessBusinessDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessBusinessData processBusinessData) {
        //处理时间 默认加上时分秒
        String[] rangeTime = processBusinessData.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2) {
            rangeTime[0] = rangeTime[0] + " 00:00:00";
            rangeTime[1] = rangeTime[1] + " 23:59:59";
            processBusinessData.setRangeTime(rangeTime);
        }
        processBusinessData.setInitiatorId(myId());
        processBusinessData.getPager().setIncludes("id","type","initiator","createTime","status","formData","clearLeave","clearLeaveTime","processLibId","cancelReason","canceDate","approver");
        List<ProcessBusinessData> data = processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
        long count = processBusinessDataService.findProcessBusinessDataCountByCondition(processBusinessData);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneProcessBusinessDataByCondition")
    @ApiOperation(value = "根据条件查找单个流程业务数据,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneProcessBusinessDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ProcessBusinessData processBusinessData) {
        ProcessBusinessData one = processBusinessDataService.findOneProcessBusinessDataByCondition(processBusinessData);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteProcessBusinessData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteProcessBusinessData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        processBusinessDataService.deleteProcessBusinessData(id);
        return new ResponseJson();
    }


    @PostMapping("/findProcessBusinessDataListByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findProcessBusinessDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessBusinessData processBusinessData) {
        processBusinessData.setSchoolId(mySchoolId());
        List<ProcessBusinessData> data = processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
        return new ResponseJson(data);
    }


    @GetMapping("/ignore/clearLeave/{id}")
    public ResponseJson clearLeave(@PathVariable String id){
        return processBusinessDataService.clearLeave(id);

    }
    @PostMapping("/operation/urge/{id}")
    @ApiOperation(value = "发送短信通知审批人", notes = "返回响应对象")
    public ResponseJson sendMessageByProcessDataById(
            @ApiParam(value = "流程实例id")
           @PathVariable("id") String id) {
        return   processBusinessDataService.sendMessageByProcessDataById(id);
    }
    @ApiOperation(value = "撤销流程-未审批通过的流程", notes = "返回响应对象")
    @GetMapping("/operation/cancel/{id}")
    public ResponseJson cancelFlowByWait(@PathVariable String id){
        return processBusinessDataService.cancelFlow(id,new ProcessBusinessData());

    }
    @ApiOperation(value = "撤销流程-如果ProcessBusinessData不为null 重新发起审批流程", notes = "返回响应对象")
    @PostMapping("/operation/cancel/{id}")
    public ResponseJson cancelFlowByHas(@PathVariable String id,@RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getCancelReason())){
            return new ResponseJson(false,"撤销原因不能为空");
        }
        return processBusinessDataService.cancelFlow(id,processBusinessData);

    }
    /**
     *    StrUtil.format("public/templates/{}.docx","assets")
     * @param processBusinessDataId
     * @param response
     */
    @GetMapping("/operation/exportAssets/{processBusinessDataId}")
    public void exportAssets( @PathVariable String processBusinessDataId, HttpServletResponse response) {
        ProcessBusinessData ps= processBusinessDataService.findProcessBusinessDataById(processBusinessDataId);
        if(Objects.isNull(ps) || !ps.getProcessLibId().equals("1121702486948855813")){
            //当前流程为空或者不是资产报废流程
            throw  new RuntimeException(StrUtil.format("当前流程为空或者不是资产报废流程"));
        }
        Map<String, Object> map = ps.getFormData();
        map.put("initiator",ps.getInitiator());
        map.put("createTime",ps.getCreateTime());
        map.put("status", Stream.of(ps.getStatus()).map(v->{
            switch (v) {
                case Constant.OA.WAIT_TO_APPROVE: return "待审批";
                case Constant.OA.SUCCESS_COMPLETE: return "审批成功";
                case Constant.OA.FAIL_COMPLETE: return "审批拒绝";
                case Constant.OA.CANCEL_COMPLETE: return "已撤销";
                default: return "未知状态";
            }
        }).findAny().get());
        Object asset =  map.get("asset");
        if(Objects.nonNull(asset)){
            Map<String, Object>  assetMap = (Map<String, Object>) asset;
            assetMap.put("assetsProperty", MapUtil.getInt(assetMap,"assetsProperty")== 1 ? "固定资产" : "易耗品");
            if(!assetMap.containsKey("useTimeLimitDate")){
                assetMap.put("useTimeLimitDate","暂无");
            }
            map.put("asset",assetMap);
        }
        InputStream in= Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/assetsScrap.docx");
        if(Objects.isNull(in)){
            throw  new RuntimeException(StrUtil.format("未找到 {} 模板,请检查模板路径是否存在",ps.getType()));
        }
        Department department = departmentService.findDepartmentById(MapUtil.getStr(map,"department"));
        map.put("department",department.getName());
        map.put("schoolName",currentTeacher().getSchoolName());
        try(OutputStream out = response.getOutputStream(); MyXWPFDocument doc = new MyXWPFDocument(in);
            BufferedOutputStream bos = new BufferedOutputStream(out)) {
            WordExportUtil.exportWord07(doc,map);
            doc.write(bos);
        } catch (Exception e ) {
            e.printStackTrace();
        }finally {
            IoUtil.close(in);
        }
    }
}

