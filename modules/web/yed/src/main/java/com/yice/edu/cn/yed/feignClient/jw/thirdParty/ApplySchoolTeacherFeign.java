package com.yice.edu.cn.yed.feignClient.jw.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchoolTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "applySchoolTeacherFeign",path = "/applySchoolTeacher")
public interface ApplySchoolTeacherFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findApplySchoolTeacherById/{id}")
    ApplySchoolTeacher findApplySchoolTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveApplySchoolTeacher")
    ApplySchoolTeacher saveApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher);
    @PostMapping("/batchSaveApplySchoolTeacher")
    void batchSaveApplySchoolTeacher(List<ApplySchoolTeacher> applySchoolTeachers);
    @PostMapping("/findApplySchoolTeacherListByCondition")
    List<ApplySchoolTeacher> findApplySchoolTeacherListByCondition(ApplySchoolTeacher applySchoolTeacher);
    @PostMapping("/findOneApplySchoolTeacherByCondition")
    ApplySchoolTeacher findOneApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher);
    @PostMapping("/findApplySchoolTeacherCountByCondition")
    long findApplySchoolTeacherCountByCondition(ApplySchoolTeacher applySchoolTeacher);
    @PostMapping("/updateApplySchoolTeacher")
    void updateApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher);
    @PostMapping("/updateApplySchoolTeacherForAll")
    void updateApplySchoolTeacherForAll(ApplySchoolTeacher applySchoolTeacher);
    @GetMapping("/deleteApplySchoolTeacher/{id}")
    void deleteApplySchoolTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteApplySchoolTeacherByCondition")
    void deleteApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
