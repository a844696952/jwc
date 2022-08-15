package com.yice.edu.cn.tap.feignClient.student;

import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/studentFamily")
public interface StudentFamilyFeign {
    @GetMapping("/findStudentFamilyById/{id}")
    List<StudentFamily> findStudentFamilyById(@PathVariable("id") String id);
    @PostMapping("/saveStudentFamily")
    StudentFamily saveStudentFamily(StudentFamily studentFamily);
    @PostMapping("/findStudentFamilyListByCondition")
    List<StudentFamily> findStudentFamilyListByCondition(StudentFamily studentFamily);
    @PostMapping("/findStudentFamilyCountByCondition")
    long findStudentFamilyCountByCondition(StudentFamily studentFamily);
    @PostMapping("/updateStudentFamily")
    void updateStudentFamily(StudentFamily studentFamily);
    @GetMapping("/deleteStudentFamily/{id}")
    void deleteStudentFamily(@PathVariable("id") String id);
    @PostMapping("/deleteStudentFamilyByCondition")
    void deleteStudentFamilyByCondition(StudentFamily studentFamily);

    @PostMapping("/batchSaveStudentFamily")
    List<StudentFamily> batchSaveStudentFamily(List<StudentFamily> studentFamilyList);
}
