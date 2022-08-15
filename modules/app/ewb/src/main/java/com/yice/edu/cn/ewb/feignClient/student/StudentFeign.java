package com.yice.edu.cn.ewb.feignClient.student;

import com.yice.edu.cn.common.pojo.jw.student.StuParentVo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentForSchoolNotify;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
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
    @PostMapping("/findStudentListByConditionWithBmp")
    List<Student> findStudentListByCondition(Student student);
    @PostMapping("/findStudentCountByCondition")
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

    @PostMapping("/findCorrespondencesByStudentApp")
    List<Student> findCorrespondencesByStudent(Student student);

    @PostMapping("/findTeacherClassList")
    List<Student> findTeacherClassList(Student student);

    @PostMapping("/findClassStudentByclassTitle")
    List<Student> findClassStudentByclassTitle(Student student);

    @PostMapping("/findStudentByTitleim")
    List<Student> findStudentByTitleim(Student student);

    @PostMapping("/findTeacherClassListim")
    List<Student> findTeacherClassListim(Student student);
    @PostMapping("/findCorrespondencesByStudentAppim")
    List<Student> findCorrespondencesByStudentim(Student student);

    @PostMapping("/findStudentListByCondition4Like")
    List<Student> findStudentListByCondition4Like(Student s);

    @PostMapping("/findStudentListByConditionim")
    List<Student> findStudentListByConditionim(Student s);

    @PostMapping("/findStudentInfoAndFamily")
    List<Student> findStudentInfoAndFamily(Student student);
    @PostMapping("/findStudentListForSchoolNotify")
    List<StudentForSchoolNotify> findStudentListForSchoolNotify(Student student);

    @PostMapping("/findStudentWithParent")
    List<Student> findStudentWithParent(StuParentVo stuParentVo);

    @PostMapping("/findStudentLogin")
    List<Student> findStudentLogin(StudentAccount studentAccount);

    @GetMapping("/getStudentLoginInfo/{id}")
    Student getStudentLoginInfo(@PathVariable("id") String id);
}
