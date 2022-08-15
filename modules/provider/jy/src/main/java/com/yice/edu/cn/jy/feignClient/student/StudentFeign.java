package com.yice.edu.cn.jy.feignClient.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "studentFeign",path = "/student")
public interface StudentFeign {
    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);
    @PostMapping("/saveStudent")
    Student saveStudent(Student student);
    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentListByCondition(Student student);
    @PostMapping("/findStudentCodeCountByCondition")
    long findStudentCountByCondition(Student student);
    @PostMapping("/updateStudent")
    void updateStudent(Student student);
    @GetMapping("/deleteStudent/{id}")
    void deleteStudent(@PathVariable("id") String id);
    @PostMapping("/deleteStudentByCondition")
    void deleteStudentByCondition(Student student);

    @PostMapping("/findStudentListByConditionWithFamily")
    List<Student> findStudentListByConditionWithFamily(Student student);

    @PostMapping("/batchSaveStudent")
    Map<String,Object> batchSaveStudent(List<Student> studentList);

    @PostMapping("/findCorrespondencesByStudent")
    List<Student> findCorrespondencesByStudent(Student student);

    @PostMapping("/findClassStudentByclassTitle")
    List<Student> findClassStudentByclassTitle(Student student);
}
