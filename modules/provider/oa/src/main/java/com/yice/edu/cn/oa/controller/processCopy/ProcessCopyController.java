package com.yice.edu.cn.oa.controller.processCopy;

import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import com.yice.edu.cn.oa.service.processCopy.ProcessCopyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processCopy")
@Api(value = "/processCopy",description = "流程抄送模块")
public class ProcessCopyController {
    @Autowired
    private ProcessCopyService processCopyService;

    @GetMapping("/findProcessCopyById/{id}")
    @ApiOperation(value = "通过id查找流程抄送", notes = "返回流程抄送对象")
    public ProcessCopy findProcessCopyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return processCopyService.findProcessCopyById(id);
    }

    @PostMapping("/saveProcessCopy")
    @ApiOperation(value = "保存流程抄送", notes = "返回流程抄送对象")
    public ProcessCopy saveProcessCopy(
            @ApiParam(value = "流程抄送对象", required = true)
            @RequestBody ProcessCopy processCopy){
        processCopyService.saveProcessCopy(processCopy);
        return processCopy;
    }

    @PostMapping("/findProcessCopyListByCondition")
    @ApiOperation(value = "根据条件查找流程抄送列表", notes = "返回流程抄送列表")
    public List<ProcessCopyVo> findProcessCopyListByCondition(
            @ApiParam(value = "流程抄送对象")
            @RequestBody ProcessCopy processCopy){
        return processCopyService.findProcessCopyListByCondition(processCopy);
    }
    @PostMapping("/findProcessCopyCountByCondition")
    @ApiOperation(value = "根据条件查找流程抄送列表个数", notes = "返回流程抄送总个数")
    public long findProcessCopyCountByCondition(
            @ApiParam(value = "流程抄送对象")
            @RequestBody ProcessCopy processCopy){
        return processCopyService.findProcessCopyCountByCondition(processCopy);
    }

    @PostMapping("/updateProcessCopy")
    @ApiOperation(value = "修改流程抄送", notes = "流程抄送对象必传")
    public void updateProcessCopy(
            @ApiParam(value = "流程抄送对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessCopy processCopy){
        processCopyService.updateProcessCopy(processCopy);
    }

    @GetMapping("/deleteProcessCopy/{id}")
    @ApiOperation(value = "通过id删除流程抄送")
    public void deleteProcessCopy(
            @ApiParam(value = "流程抄送对象", required = true)
            @PathVariable String id){
        processCopyService.deleteProcessCopy(id);
    }
    @PostMapping("/deleteProcessCopyByCondition")
    @ApiOperation(value = "根据条件删除流程抄送")
    public void deleteProcessCopyByCondition(
            @ApiParam(value = "流程抄送对象")
            @RequestBody ProcessCopy processCopy){
        processCopyService.deleteProcessCopyByCondition(processCopy);
    }
    @PostMapping("/findOneProcessCopyByCondition")
    @ApiOperation(value = "根据条件查找单个流程抄送,结果必须为单条数据", notes = "返回单个流程抄送,没有时为空")
    public ProcessCopy findOneProcessCopyByCondition(
            @ApiParam(value = "流程抄送对象")
            @RequestBody ProcessCopy processCopy){
        return processCopyService.findOneProcessCopyByCondition(processCopy);
    }
    @PostMapping("/batchLookProcessCopyByIds")
    @ApiOperation(value = "根据id批量改变抄送状态", notes = "返回响应对象")
    public  void batchLookProcessCopyByIds(  @ApiParam(value = "抄送记录的id集合", required = true)  @RequestBody String[] ids  ){
        processCopyService.batchLookProcessCopyByIds(ids);
    }
}
