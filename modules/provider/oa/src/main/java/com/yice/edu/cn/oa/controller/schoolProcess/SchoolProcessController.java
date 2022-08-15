package com.yice.edu.cn.oa.controller.schoolProcess;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.oa.service.schoolProcess.SchoolProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schoolProcess")
@Api(value = "/schoolProcess",description = "学校流程模块")
public class SchoolProcessController {
    @Autowired
    private SchoolProcessService schoolProcessService;

    @GetMapping("/findSchoolProcessById/{id}")
    @ApiOperation(value = "通过id查找学校流程", notes = "返回学校流程对象")
    public SchoolProcess findSchoolProcessById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolProcessService.findSchoolProcessById(id);
    }

    @PostMapping("/saveSchoolProcess")
    @ApiOperation(value = "保存学校流程", notes = "返回学校流程对象")
    public SchoolProcess saveSchoolProcess(
            @ApiParam(value = "学校流程对象", required = true)
            @RequestBody SchoolProcess schoolProcess){
        schoolProcessService.saveSchoolProcess(schoolProcess);
        return schoolProcess;
    }

    @PostMapping("/findSchoolProcessListByCondition")
    @ApiOperation(value = "根据条件查找学校流程列表", notes = "返回学校流程列表")
    public List<SchoolProcess> findSchoolProcessListByCondition(
            @ApiParam(value = "学校流程对象")
            @RequestBody SchoolProcess schoolProcess){
        return schoolProcessService.findSchoolProcessListByCondition(schoolProcess);
    }
    @PostMapping("/findSchoolProcessCountByCondition")
    @ApiOperation(value = "根据条件查找学校流程列表个数", notes = "返回学校流程总个数")
    public long findSchoolProcessCountByCondition(
            @ApiParam(value = "学校流程对象")
            @RequestBody SchoolProcess schoolProcess){
        return schoolProcessService.findSchoolProcessCountByCondition(schoolProcess);
    }

    @PostMapping("/updateSchoolProcess")
    @ApiOperation(value = "修改学校流程", notes = "学校流程对象必传")
    public void updateSchoolProcess(
            @ApiParam(value = "学校流程对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolProcess schoolProcess){
        schoolProcessService.updateSchoolProcess(schoolProcess);
    }

    @GetMapping("/deleteSchoolProcess/{id}")
    @ApiOperation(value = "通过id删除学校流程")
    public void deleteSchoolProcess(
            @ApiParam(value = "学校流程对象", required = true)
            @PathVariable String id){
        schoolProcessService.deleteSchoolProcess(id);
    }
    @PostMapping("/deleteSchoolProcessByCondition")
    @ApiOperation(value = "根据条件删除学校流程")
    public void deleteSchoolProcessByCondition(
            @ApiParam(value = "学校流程对象")
            @RequestBody SchoolProcess schoolProcess){
        schoolProcessService.deleteSchoolProcessByCondition(schoolProcess);
    }
    @PostMapping("/findOneSchoolProcessByCondition")
    @ApiOperation(value = "根据条件查找单个学校流程,结果必须为单条数据", notes = "返回单个学校流程,没有时为空")
    public SchoolProcess findOneSchoolProcessByCondition(
            @ApiParam(value = "学校流程对象")
            @RequestBody SchoolProcess schoolProcess){
        return schoolProcessService.findOneSchoolProcessByCondition(schoolProcess);
    }

    /**
     * 获取从流程库里获取进来的，某个学校的流程,(只要id和type)
     * @param schoolId
     * @return
     */
    @GetMapping("/findSchoolProcessListFromProcessLib/{schoolId}")
    public List<SchoolProcess> findSchoolProcessListFromProcessLib(@PathVariable("schoolId") String schoolId){
        return schoolProcessService.findSchoolProcessListFromProcessLib(schoolId);
    }

    @PostMapping("/addNewProcessLib/{schoolId}")
    public void addNewProcessLib(@RequestBody List<String> processLibIds,@PathVariable("schoolId") String schoolId){
        schoolProcessService.addNewProcessLib(processLibIds,schoolId);
    }

    @GetMapping("/listValidSchoolProcessBySchoolId/{schoolId}")
    public List<SchoolProcess> listValidSchoolProcessBySchoolId(@PathVariable("schoolId") String schoolId){
        return schoolProcessService.listValidSchoolProcessBySchoolId(schoolId);
    }

    /**
     *
     * @param schoolProcessId
     * @param myId myId当前登录的教师id
     * @return
     */
    @GetMapping("/findSchoolProcessByIdForStart/{schoolProcessId}/{myId}")
    public SchoolProcess findSchoolProcessByIdForStart(@PathVariable("schoolProcessId") String schoolProcessId,@PathVariable String myId){
        SchoolProcess schoolProcess=schoolProcessService.findSchoolProcessByIdForStart(schoolProcessId,myId);
        return schoolProcess;

    }

    /**
     * 启动流程(过期)
     * @param processBusinessData
     */
    @Deprecated
    @PostMapping("/start")
    public void start(@RequestBody ProcessBusinessData processBusinessData){
        //todo 启动流程
        schoolProcessService.start(processBusinessData);
    }

    /**
     * 启动流程,作废
     * @param formData
     * @param schoolProcessId
     * @param id
     * @return
     */
    @PostMapping("/startProcess/{schoolProcessId}/{id}")
    @Deprecated
    public ResponseJson startProcess(@RequestBody Map<String, Object> formData, @PathVariable("schoolProcessId") String schoolProcessId, @PathVariable("id") String id){
        return schoolProcessService.startProcess(formData, schoolProcessId, id);
    }

    /**
     * 启动流程
     * @param processBusinessData
     */
    @PostMapping("/startProcess/v1")
    public ResponseJson startProcess(@RequestBody ProcessBusinessData processBusinessData){
        return schoolProcessService.startProcess(processBusinessData);
    }

    /**
     * 根据表单数据获取流程条件信息
     * @param processBusinessData
     * @return
     */
    @PostMapping("/findFlowConditionByFormData")
    public ResponseJson findFlowConditionByFormData(@RequestBody ProcessBusinessData processBusinessData){
        return schoolProcessService.findFlowConditionByFormData(processBusinessData);
    }

    /**
     * 获取学校流程分组
     * @param schoolId
     * @return
     */
    @GetMapping("/listSchoolProcessGroup/{schoolId}")
    public List<SchoolProcess> listSchoolProcessGroup(@PathVariable("schoolId") String schoolId){
        return schoolProcessService.listSchoolProcessGroup(schoolId);
    }

    /**
     *
     * @param schoolId
     * @return
     */
    @GetMapping("/findSchoolProcessForSelect/{schoolId}")
    public List<SchoolProcess> findSchoolProcessForSelect(@PathVariable("schoolId") String schoolId){
        return schoolProcessService.findSchoolProcessForSelect(schoolId);
    }
    @GetMapping("/deleteSchoolProcessAll/{schoolId}/{id}")
    public void deleteSchoolProcessAll(@PathVariable("schoolId") String schoolId, @PathVariable("id")String id){
        schoolProcessService.deleteSchoolProcessAll(schoolId,id);
    };
}
