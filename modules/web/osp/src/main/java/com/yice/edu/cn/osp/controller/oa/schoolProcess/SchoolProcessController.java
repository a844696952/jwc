package com.yice.edu.cn.osp.controller.oa.schoolProcess;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.osp.service.oa.schoolProcess.SchoolProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolProcess")
@Api(value = "/schoolProcess",description = "学校流程模块")
public class SchoolProcessController {
    @Autowired
    private SchoolProcessService schoolProcessService;


    @PostMapping("/saveSchoolProcess")
    @ApiOperation(value = "保存学校流程对象", notes = "返回响应对象")
    public ResponseJson saveSchoolProcess(
            @ApiParam(value = "学校流程对象", required = true)
            @RequestBody SchoolProcess schoolProcess){
       schoolProcess.setSchoolId(mySchoolId());
        SchoolProcess s=schoolProcessService.saveSchoolProcess(schoolProcess);
        return new ResponseJson(s);
    }

    @GetMapping("/set/findSchoolProcessById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校流程", notes = "返回响应对象")
    public ResponseJson findSchoolProcessById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolProcess schoolProcess=schoolProcessService.findSchoolProcessById(id);
        return new ResponseJson(schoolProcess);
    }

    @PostMapping("/set/updateSchoolProcess")
    public ResponseJson updateSchoolProcessForSet(@RequestBody SchoolProcess schoolProcess){
        schoolProcessService.updateSchoolProcess(schoolProcess);
        return new ResponseJson();
    }

    @PostMapping("/update/updateSchoolProcess")
    @ApiOperation(value = "修改学校流程对象", notes = "返回响应对象")
    public ResponseJson updateSchoolProcess(
            @ApiParam(value = "被修改的学校流程对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolProcess schoolProcess){
        schoolProcessService.updateSchoolProcess(schoolProcess);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolProcessById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校流程", notes = "返回响应对象")
    public ResponseJson lookSchoolProcessById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolProcess schoolProcess=schoolProcessService.findSchoolProcessById(id);
        return new ResponseJson(schoolProcess);
    }

    @PostMapping("/findSchoolProcesssByCondition")
    @ApiOperation(value = "根据条件查找学校流程", notes = "返回响应对象")
    public ResponseJson findSchoolProcesssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolProcess schoolProcess){
       schoolProcess.setSchoolId(mySchoolId());
        List<SchoolProcess> data=schoolProcessService.findSchoolProcessListByCondition(schoolProcess);
        long count=schoolProcessService.findSchoolProcessCountByCondition(schoolProcess);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolProcessByCondition")
    @ApiOperation(value = "根据条件查找单个学校流程,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSchoolProcessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolProcess schoolProcess){
        SchoolProcess one=schoolProcessService.findOneSchoolProcessByCondition(schoolProcess);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolProcess/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolProcess(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolProcessService.deleteSchoolProcess(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolProcessListByCondition")
    @ApiOperation(value = "根据条件查找学校流程列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolProcessListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolProcess schoolProcess){
       schoolProcess.setSchoolId(mySchoolId());
        List<SchoolProcess> data=schoolProcessService.findSchoolProcessListByCondition(schoolProcess);
        return new ResponseJson(data);
    }

    @GetMapping("/select/findProcessLibForSelect")
    public ResponseJson findProcessLibForSelect(){
        List<ProcessLib> processLibs=schoolProcessService.findProcessLibForSelect(mySchoolId());
        return new ResponseJson(processLibs);
    }
    @PostMapping("/select/addNewProcessLib")
    public ResponseJson addNewProcessLib(@RequestBody List<String> processLibIds){
        schoolProcessService.addNewProcessLib(processLibIds,mySchoolId());
        return new ResponseJson();
    }
    @GetMapping("/select/findSchoolProcessForSelect")
    public ResponseJson findSchoolProcessForSelect(){
        List<SchoolProcess> schoolProcesses=schoolProcessService.findSchoolProcessForSelect(mySchoolId());
        return new ResponseJson(schoolProcesses);
    }

    @GetMapping("/deleteSchoolProcessAll/{id}")
    @ApiOperation(value = "根据id删除学校流程以及该流程所在学校的所有数据", notes = "返回响应对象")
    public ResponseJson deleteSchoolProcessAll(
            @ApiParam(value = "根据id删除学校流程以及该流程所在学校的所有数据", required = true)
            @PathVariable String id){
        schoolProcessService.deleteSchoolProcessAll(mySchoolId(),id);
        return new ResponseJson();
    }

}
