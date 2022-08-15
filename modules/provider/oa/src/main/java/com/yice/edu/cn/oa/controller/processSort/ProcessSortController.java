package com.yice.edu.cn.oa.controller.processSort;

import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.oa.service.processSort.ProcessSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processSort")
@Api(value = "/processSort",description = "流程分类模块")
public class ProcessSortController {
    @Autowired
    private ProcessSortService processSortService;

    @GetMapping("/findProcessSortById/{id}")
    @ApiOperation(value = "通过id查找流程分类", notes = "返回流程分类对象")
    public ProcessSort findProcessSortById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return processSortService.findProcessSortById(id);
    }

    @PostMapping("/saveProcessSort")
    @ApiOperation(value = "保存流程分类", notes = "返回流程分类对象")
    public ProcessSort saveProcessSort(
            @ApiParam(value = "流程分类对象", required = true)
            @RequestBody ProcessSort processSort){
        processSortService.saveProcessSort(processSort);
        return processSort;
    }

    @PostMapping("/findProcessSortListByCondition")
    @ApiOperation(value = "根据条件查找流程分类列表", notes = "返回流程分类列表")
    public List<ProcessSort> findProcessSortListByCondition(
            @ApiParam(value = "流程分类对象")
            @RequestBody ProcessSort processSort){
        return processSortService.findProcessSortListByCondition(processSort);
    }
    @PostMapping("/findProcessSortCountByCondition")
    @ApiOperation(value = "根据条件查找流程分类列表个数", notes = "返回流程分类总个数")
    public long findProcessSortCountByCondition(
            @ApiParam(value = "流程分类对象")
            @RequestBody ProcessSort processSort){
        return processSortService.findProcessSortCountByCondition(processSort);
    }

    @PostMapping("/updateProcessSort")
    @ApiOperation(value = "修改流程分类", notes = "流程分类对象必传")
    public void updateProcessSort(
            @ApiParam(value = "流程分类对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessSort processSort){
        processSortService.updateProcessSort(processSort);
    }

    @GetMapping("/deleteProcessSort/{id}")
    @ApiOperation(value = "通过id删除流程分类")
    public void deleteProcessSort(
            @ApiParam(value = "流程分类对象", required = true)
            @PathVariable String id){
        processSortService.deleteProcessSort(id);
    }
    @PostMapping("/deleteProcessSortByCondition")
    @ApiOperation(value = "根据条件删除流程分类")
    public void deleteProcessSortByCondition(
            @ApiParam(value = "流程分类对象")
            @RequestBody ProcessSort processSort){
        processSortService.deleteProcessSortByCondition(processSort);
    }
    @PostMapping("/findOneProcessSortByCondition")
    @ApiOperation(value = "根据条件查找单个流程分类,结果必须为单条数据", notes = "返回单个流程分类,没有时为空")
    public ProcessSort findOneProcessSortByCondition(
            @ApiParam(value = "流程分类对象")
            @RequestBody ProcessSort processSort){
        return processSortService.findOneProcessSortByCondition(processSort);
    }
    @GetMapping("/findProcessSortList/{schoolId}")
    @ApiOperation(value = "获取学校流程分类,按照序号排序", notes = "返回响应对象", response=ProcessSort.class)
    public List<ProcessSort> getProcessSortList(@PathVariable("schoolId") String schoolId){
        return processSortService.getProcessSortList(schoolId);
    }
}
