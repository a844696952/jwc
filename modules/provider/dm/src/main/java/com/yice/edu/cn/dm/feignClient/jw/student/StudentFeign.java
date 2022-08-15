package com.yice.edu.cn.dm.feignClient.jw.student;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 
* @ClassName: StudentFeign  
* @Description: 调用JW学生信息  
* @author xuchang  
* @date 2018年9月3日
 */
@FeignClient(value="jw",contextId = "studentFeign",path = "/student")
public interface StudentFeign {
	
    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);
    
    @PostMapping("/saveStudent")
    Student saveStudent(Student student);
    
    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentListByCondition(Student student);
    
    @PostMapping("/findStudentCountByCondition")
    long findStudentCountByCondition(Student student);
    
    @PostMapping("/updateStudent")
    void updateStudent(Student student);
    
    @GetMapping("/deleteStudent/{id}")
    void deleteStudent(@PathVariable("id") String id);
    
    @PostMapping("/deleteStudentByCondition")
    void deleteStudentByCondition(Student student);

    @PostMapping("/findStudentsByGroupId/{groupId}")
    List<Student> findStudentsByGroupId(@PathVariable("groupId") String groupId);

    @PostMapping("/findStudentListByConditionWithBmp")
    List<Student> findStudentListByConditionWithBmp(Student student);
}
