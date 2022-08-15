package com.yice.edu.cn.osp.controller.oa.processSort;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.osp.service.oa.processSort.ProcessSortService;
import com.yice.edu.cn.osp.service.oa.schoolProcess.SchoolProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/processSort")
@Api(value = "/processSort",description = "流程分类模块")
public class ProcessSortController {
    @Autowired
    private ProcessSortService processSortService;
    @Autowired
    private SchoolProcessService schoolProcessService;

    @PostMapping("/saveProcessSort")
    @ApiOperation(value = "保存流程分类对象", notes = "返回保存好的流程分类对象", response= ProcessSort.class)
    public ResponseJson saveProcessSort(
            @ApiParam(value = "流程分类对象", required = true)
            @RequestBody ProcessSort processSort){
        ProcessSort temp = new ProcessSort();
        temp.setSchoolId(mySchoolId());
        temp.setSortName(processSort.getSortName());
       ProcessSort sort =  processSortService.findOneProcessSortByCondition(temp);
       if(Objects.nonNull(sort)){
           return new ResponseJson(false, StrUtil.format("分类名:{}已经存在,请重新输入",processSort.getSortName()));
       }
        processSort.setSchoolId(mySchoolId());
        ProcessSort s=processSortService.saveProcessSort(processSort);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findProcessSortById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找流程分类", notes = "返回响应对象", response=ProcessSort.class)
    public ResponseJson findProcessSortById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessSort processSort=processSortService.findProcessSortById(id);
        return new ResponseJson(processSort);
    }

    @PostMapping("/update/updateProcessSort")
    @ApiOperation(value = "修改流程分类对象", notes = "返回响应对象")
    public ResponseJson updateProcessSort(
            @ApiParam(value = "被修改的流程分类对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessSort processSort){
        processSortService.updateProcessSort(processSort);
        return new ResponseJson();
    }

    @GetMapping("/look/lookProcessSortById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找流程分类", notes = "返回响应对象", response=ProcessSort.class)
    public ResponseJson lookProcessSortById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ProcessSort processSort=processSortService.findProcessSortById(id);
        return new ResponseJson(processSort);
    }

    @PostMapping("/findProcessSortsByCondition")
    @ApiOperation(value = "根据条件查找流程分类", notes = "返回响应对象", response=ProcessSort.class)
    public ResponseJson findProcessSortsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessSort processSort){
        processSort.setSchoolId(mySchoolId());
        List<ProcessSort> data=processSortService.findProcessSortListByCondition(processSort);
        long count=processSortService.findProcessSortCountByCondition(processSort);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneProcessSortByCondition")
    @ApiOperation(value = "根据条件查找单个流程分类,结果必须为单条数据", notes = "没有时返回空", response=ProcessSort.class)
    public ResponseJson findOneProcessSortByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ProcessSort processSort){
        ProcessSort one=processSortService.findOneProcessSortByCondition(processSort);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteProcessSort/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteProcessSort(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        SchoolProcess schoolProcess=new SchoolProcess();
        schoolProcess.setSchoolId(mySchoolId());
        schoolProcess.setGroupId(id);
        long count=schoolProcessService.findSchoolProcessCountByCondition(schoolProcess);
        if(count > 0){
                return new ResponseJson(false,"分类已经被占用，无法删除");
        }
        processSortService.deleteProcessSort(id);
        return new ResponseJson();
    }


    @PostMapping("/findProcessSortListByCondition")
    @ApiOperation(value = "根据条件查找流程分类列表", notes = "返回响应对象,不包含总条数", response=ProcessSort.class)
    public ResponseJson findProcessSortListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ProcessSort processSort){
        processSort.setSchoolId(mySchoolId());
        List<ProcessSort> data=processSortService.findProcessSortListByCondition(processSort);
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/findProcessSortList")
    @ApiOperation(value = "获取学校流程分类,按照序号排序", notes = "返回响应对象", response=ProcessSort.class)
    public ResponseJson getProcessSortList(){
        List<ProcessSort> data=processSortService.getProcessSortList(mySchoolId());
        return new ResponseJson(data);
    }



}
