package com.yice.edu.cn.osp.controller.oa.processCopy;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import com.yice.edu.cn.osp.service.oa.processBusinessData.ProcessBusinessDataService;
import com.yice.edu.cn.osp.service.oa.processCopy.ProcessCopyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/processCopy")
@Api(value = "/processCopy",description = "流程抄送模块")
public class ProcessCopyController {
    @Autowired
    private ProcessCopyService processCopyService;
    @Autowired
    private ProcessBusinessDataService processBusinessDataService;

    @PostMapping("/saveProcessCopy")
    @ApiOperation(value = "保存流程抄送对象", notes = "返回响应对象")
    public ResponseJson saveProcessCopy(
            @ApiParam(value = "流程抄送对象", required = true)
            @RequestBody ProcessCopy processCopy){
        ProcessCopy s=processCopyService.saveProcessCopy(processCopy);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findProcessCopyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流程抄送", notes = "返回响应对象")
    public ResponseJson findProcessCopyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessCopy processCopy=processCopyService.findProcessCopyById(id);
        return new ResponseJson(processCopy);
    }

    @PostMapping("/update/updateProcessCopy")
    @ApiOperation(value = "修改流程抄送对象", notes = "返回响应对象")
    public ResponseJson updateProcessCopy(
            @ApiParam(value = "被修改的流程抄送对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessCopy processCopy){
        processCopyService.updateProcessCopy(processCopy);
        return new ResponseJson();
    }

    @GetMapping("/look/lookProcessCopyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流程抄送", notes = "返回响应对象")
    public ResponseJson lookProcessCopyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessCopy processCopy=processCopyService.findProcessCopyById(id);
        return new ResponseJson(processCopy);
    }

    @PostMapping("/findProcessCopysByCondition")
    @ApiOperation(value = "根据条件查找流程抄送", notes = "返回响应对象")
    public ResponseJson findProcessCopysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessCopy processCopy){
        //处理时间 默认加上时分秒
        String[] rangeTime = processCopy.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2) {
            rangeTime[0] = rangeTime[0] + " 00:00:00";
            rangeTime[1] = rangeTime[1] + " 23:59:59";
            processCopy.setRangeTime(rangeTime);
        }
        processCopy.setTeacherId(myId());
        List<ProcessCopyVo> data=processCopyService.findProcessCopyListByCondition(processCopy);
        long count=processCopyService.findProcessCopyCountByCondition(processCopy);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneProcessCopyByCondition")
    @ApiOperation(value = "根据条件查找单个流程抄送,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneProcessCopyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ProcessCopy processCopy){
        ProcessCopy one=processCopyService.findOneProcessCopyByCondition(processCopy);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteProcessCopy/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteProcessCopy(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        processCopyService.deleteProcessCopy(id);
        return new ResponseJson();
    }


    @PostMapping("/findProcessCopyListByCondition")
    @ApiOperation(value = "根据条件查找流程抄送列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findProcessCopyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessCopy processCopy){
        List<ProcessCopyVo> data=processCopyService.findProcessCopyListByCondition(processCopy);
        return new ResponseJson(data);
    }

    @GetMapping("/look/findProcessBusinessDataById/{id}/{processBusinessDataId}")
    public ResponseJson findProcessBusinessDataById(@PathVariable String id,@PathVariable String processBusinessDataId){
        ProcessBusinessData processBusinessData = processBusinessDataService.findProcessBusinessDataById(processBusinessDataId);
        //如流程已被撤销或删除  删除当前抄送
        if(Objects.isNull(processBusinessData)){
            processCopyService.deleteProcessCopy(id);
        }else{
            ProcessCopy processCopy = new ProcessCopy();
            processCopy.setId(id);
            processCopy.setLooked(true);
            processCopyService.updateProcessCopy(processCopy);
        }
        return new ResponseJson(processBusinessData);
    }
    @PostMapping("/ignore/batchLookProcessCopyByIds")
    @ApiOperation(value = "根据id批量改变抄送状态", notes = "返回响应对象")
    public ResponseJson batchLookProcessCopyByIds(
            @ApiParam(value = "抄送记录的id集合", required = true)
            @RequestBody String[] processCopyIds  ){
        processCopyService.batchLookProcessCopyByIds(processCopyIds);
        return new ResponseJson();
    }

}
