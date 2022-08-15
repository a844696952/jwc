package com.yice.edu.cn.tap.feignClient.student;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentForSchoolNotify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",path = "/student")
public interface StudentFeign {
    @GetMapping("/findStudentById/{id}")
    Student findStudentById(@PathVariable("id") String id);
    @PostMapping("/saveStudent")
    Student saveStudent(Student student);
    @PostMapping("/findStudentListByConditionWithBmp")
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

    @PostMapping("/findStudentListByCondition4Like")
    List<Student> findStudentListByCondition4Like(Student s);

    @PostMapping("/findStudentListByConditionim")
    List<Student> findStudentListByConditionim(Student s);

    @PostMapping("/findStudentInfoAndFamily")
    List<Student> findStudentInfoAndFamily(Student student);
    @PostMapping("/findStudentListForSchoolNotify")
    List<StudentForSchoolNotify> findStudentListForSchoolNotify(Student student);
    @PostMapping("/findStudentListByCondition")
    List<Student> findStudentsByCondition(Student student);
    @PostMapping("/findStudentCodeCountByCondition")
    long findStudentListCountByCondition(Student student);

    @PostMapping("/findClassStudentByClassesId")
    List<Student> findClassStudentByClassesId(DmActivitySiginUp dmActivitySiginUp);
}
