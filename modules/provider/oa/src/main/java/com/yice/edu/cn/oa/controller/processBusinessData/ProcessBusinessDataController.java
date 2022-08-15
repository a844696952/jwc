package com.yice.edu.cn.oa.controller.processBusinessData;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.oa.service.processBusinessData.ProcessBusinessDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processBusinessData")
@Api(value = "/processBusinessData",description = "流程业务数据模块")
public class ProcessBusinessDataController {
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;

    @GetMapping("/findProcessBusinessDataById/{id}")
    @ApiOperation(value = "通过id查找流程业务数据", notes = "返回流程业务数据对象")
    public ProcessBusinessData findProcessBusinessDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return processBusinessDataService.findProcessBusinessDataById(id);
    }

    @PostMapping("/saveProcessBusinessData")
    @ApiOperation(value = "保存流程业务数据", notes = "返回流程业务数据对象")
    public ProcessBusinessData saveProcessBusinessData(
            @ApiParam(value = "流程业务数据对象", required = true)
            @RequestBody ProcessBusinessData processBusinessData){
        processBusinessDataService.saveProcessBusinessData(processBusinessData);
        return processBusinessData;
    }

    @PostMapping("/findProcessBusinessDataListByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据列表", notes = "返回流程业务数据列表")
    public List<ProcessBusinessData> findProcessBusinessDataListByCondition(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.findProcessBusinessDataListByCondition(processBusinessData);
    }
    @PostMapping("/findProcessBusinessDataCountByCondition")
    @ApiOperation(value = "根据条件查找流程业务数据列表个数", notes = "返回流程业务数据总个数")
    public long findProcessBusinessDataCountByCondition(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.findProcessBusinessDataCountByCondition(processBusinessData);
    }

    @PostMapping("/updateProcessBusinessData")
    @ApiOperation(value = "修改流程业务数据", notes = "流程业务数据对象必传")
    public void updateProcessBusinessData(
            @ApiParam(value = "流程业务数据对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessBusinessData processBusinessData){
        processBusinessDataService.updateProcessBusinessData(processBusinessData);
    }

    @GetMapping("/deleteProcessBusinessData/{id}")
    @ApiOperation(value = "通过id删除流程业务数据")
    public void deleteProcessBusinessData(
            @ApiParam(value = "流程业务数据对象", required = true)
            @PathVariable String id){
        processBusinessDataService.deleteProcessBusinessData(id);
    }
    @PostMapping("/deleteProcessBusinessDataByCondition")
    @ApiOperation(value = "根据条件删除流程业务数据")
    public void deleteProcessBusinessDataByCondition(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        processBusinessDataService.deleteProcessBusinessDataByCondition(processBusinessData);
    }
    @PostMapping("/findOneProcessBusinessDataByCondition")
    @ApiOperation(value = "根据条件查找单个流程业务数据,结果必须为单条数据", notes = "返回单个流程业务数据,没有时为空")
    public ProcessBusinessData findOneProcessBusinessDataByCondition(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.findOneProcessBusinessDataByCondition(processBusinessData);
    }


    @PostMapping("/clearLeave")
    @ApiOperation(value = "根据流程id销假当前流程,id/clearLeaveTime必传", notes = "返回单个流程业务数据,没有时为空")
    public ResponseJson clearLeave(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        if(StrUtil.isEmpty(processBusinessData.getId()) && StrUtil.isEmpty(processBusinessData.getClearLeaveTime())){
            return new ResponseJson(false,"确认到校时间不能为空");
        }
        return processBusinessDataService.clearLeave(processBusinessData);
    }


    /**
     * 请假申请的统计
     * @param processBusinessData
     * @return
     */
    @PostMapping("/calculateLeaveStatis")
    public ResponseJson calculateLeaveStatis(@RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.calculateLeaveStatis(processBusinessData);
    }

    /**
     * 发送催办通知
     * @param id
     * @return
     */
    @ApiOperation(value = "根据流程实例id发送短信", notes = "返回响应对象")
    @PostMapping("/urge/{id}")
    public ResponseJson sendMessageByProcessDataById(@PathVariable("id") String id){
        return processBusinessDataService.sendMessageByProcessDataById(id);
    }
    @ApiOperation(value = "撤销流程,如果ProcessBusinessData不为null 重新发起审批流程", notes = "返回响应对象")
    @PostMapping("/cancel/{id}")
    public ResponseJson cancelFlow(@PathVariable String id,
                                   @RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.cancelFlow(id,processBusinessData);
    }
    @ApiOperation(value = "根据流程实例id发送app推送", notes = "返回响应对象")
    @GetMapping("/urgeApp/{id}")
    public ResponseJson pushUrge(@PathVariable("id") String id){
        return processBusinessDataService.pushUrge(id);
    }

    @PostMapping("/findProcessBusinessDataListByConditionForKq")
    @ApiOperation(value = "根据条件查找流程业务数据列表", notes = "返回流程业务数据列表")
    public List<ProcessBusinessData> findProcessBusinessDataListByConditionForKq(
            @ApiParam(value = "流程业务数据对象")
            @RequestBody ProcessBusinessData processBusinessData){
        return processBusinessDataService.findProcessBusinessDataListByConditionForKq(processBusinessData);
    }
}
