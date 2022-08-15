package com.yice.edu.cn.tap.feignClient.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "oa",contextId="schoolProcessFeign",path = "/schoolProcess")
public interface SchoolProcessFeign {
    @GetMapping("/findSchoolProcessById/{id}")
    com.yice.edu.cn.common.pojo.oa.process.SchoolProcess findSchoolProcessById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolProcess")
    com.yice.edu.cn.common.pojo.oa.process.SchoolProcess saveSchoolProcess(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @PostMapping("/findSchoolProcessListByCondition")
    List<com.yice.edu.cn.common.pojo.oa.process.SchoolProcess> findSchoolProcessListByCondition(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @PostMapping("/findOneSchoolProcessByCondition")
    com.yice.edu.cn.common.pojo.oa.process.SchoolProcess findOneSchoolProcessByCondition(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @PostMapping("/findSchoolProcessCountByCondition")
    long findSchoolProcessCountByCondition(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @PostMapping("/updateSchoolProcess")
    void updateSchoolProcess(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @GetMapping("/deleteSchoolProcess/{id}")
    void deleteSchoolProcess(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolProcessByCondition")
    void deleteSchoolProcessByCondition(com.yice.edu.cn.common.pojo.oa.process.SchoolProcess schoolProcess);
    @GetMapping("/findSchoolProcessListFromProcessLib/{schoolId}")
    List<com.yice.edu.cn.common.pojo.oa.process.SchoolProcess> findSchoolProcessListFromProcessLib(@PathVariable("schoolId") String schoolId);
    @PostMapping("/addNewProcessLib/{schoolId}")
    void addNewProcessLib(List<String> processLibIds,@PathVariable("schoolId") String schoolId);
    @GetMapping("/listValidSchoolProcessBySchoolId/{schoolId}")
    List<com.yice.edu.cn.common.pojo.oa.process.SchoolProcess> listValidSchoolProcessBySchoolId(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findSchoolProcessByIdForStart/{schoolProcessId}/{id}")
    SchoolProcess findSchoolProcessByIdForStart(@PathVariable("schoolProcessId") String schoolProcessId, @PathVariable("id") String id);
    @PostMapping("/start")
    void start(ProcessBusinessData processBusinessData);
    @PostMapping("/startProcess/{schoolProcessId}/{id}")
    ResponseJson startProcess(Map<String, Object> formData, @PathVariable("schoolProcessId") String schoolProcessId, @PathVariable("id") String id);
    @PostMapping("/startProcess/v1")
    ResponseJson startProcess(ProcessBusinessData processBusinessData);
    @PostMapping("/findFlowConditionByFormData")
    ResponseJson findFlowConditionByFormData(ProcessBusinessData processBusinessData);

    @GetMapping("/listSchoolProcessGroup/{schoolId}")
    List<SchoolProcess> listSchoolProcessGroup(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findSchoolProcessForSelect/{schoolId}")
    List<SchoolProcess> findSchoolProcessForSelect(@PathVariable("schoolId") String schoolId);

}
