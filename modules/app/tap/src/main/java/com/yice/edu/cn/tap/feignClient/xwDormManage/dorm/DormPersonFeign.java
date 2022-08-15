package com.yice.edu.cn.tap.feignClient.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "dormPersonFeign",path = "/dormPerson")
public interface DormPersonFeign {
    @GetMapping("/findDormPersonById/{id}")
    DormPerson findDormPersonById(@PathVariable("id") String id);
    @PostMapping("/saveDormPerson")
    DormPerson saveDormPerson(DormPerson dormPerson);
    @PostMapping("/findDormPersonListByCondition")
    List<DormPerson> findDormPersonListByCondition(DormPerson dormPerson);
    @PostMapping("/findOneDormPersonByCondition")
    DormPerson findOneDormPersonByCondition(DormPerson dormPerson);
    @PostMapping("/findDormPersonCountByCondition")
    long findDormPersonCountByCondition(DormPerson dormPerson);
    @PostMapping("/updateDormPerson")
    long updateDormPerson(DormPerson dormPerson);
    @GetMapping("/deleteDormPerson/{id}")
    void deleteDormPerson(@PathVariable("id") String id);
    @PostMapping("/deleteDormPersonByCondition")
    void deleteDormPersonByCondition(DormPerson dormPerson);

    /*------------------------------------------------------------------------*/
    @PostMapping("/findStudentListByConditionOnDorm")
    List<Student> findStudentListByConditionOnDorm(Student student);
    @PostMapping("/findStudentListCountByConditionOnDorm")
    Long findStudentListCountByConditionOnDorm(Student student);
    @PostMapping("/findTeacherListByConditionOnDorm")
    List<Teacher> findTeacherListByConditionOnDorm(Teacher teacher);
    @PostMapping("/findTeacherListCountByConditionOnDorm")
    Long findTeacherListCountByConditionOnDorm(Teacher teacher);
    @PostMapping("/findNoTeacherListByConditionOnDorm")
    List<Teacher> findNoTeacherListByConditionOnDorm(Teacher teacher);
    @PostMapping("/findNoTeacherListCountByConditionOnDorm")
    Long findNoTeacherListCountByConditionOnDorm(Teacher teacher);

    @PostMapping("/findDormPersonListConnectTeacher")
    List<DormPerson> findDormPersonListConnectTeacher(DormBuildVo dormBuildVo);
    @PostMapping("/findDormPersonListConnectStudent")
    List<DormPerson> findDormPersonListConnectStudent(DormBuildVo dormBuildVo);
    @GetMapping("/getDormBuildingById/{id}")
    DormBuildingPersonInfo getDormBuildingById(@PathVariable("id") String id);

    @PostMapping("/findDormPersonOneConnectTeacher")
    DormPerson findDormPersonOneConnectTeacher(DormBuildVo dormBuildVo);
    @PostMapping("/findDormPersonOneConnectStudent")
    DormPerson findDormPersonOneConnectStudent(DormBuildVo dormBuildVo);
    @PostMapping("/leaveDorm")
    void leaveDorm(DormPerson dormPerson);

    @PostMapping("/findDormPersonInfoWithStudent")
    List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo);
    @PostMapping("/findDormPersonInfoCountWithStudent")
    long findDormPersonInfoCountWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo);
    @PostMapping("/findDormPersonInfoWithTeacher")
    List<DormBuildingPersonInfo> findDormPersonInfoWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo);
    @PostMapping("/findDormPersonInfoCountWithTeacher")
    long findDormPersonInfoCountWithTeacher(DormBuildingPersonInfo dormBuildingPersonInfo);
    @PostMapping("/findDormMoveIntoPersonNumByDormId")
    long findDormMoveIntoPersonNumByDormId(@PathVariable("dormId") String dormId);
    @PostMapping("/findEmptyDormByDormCategory")
    List<Map<String, String>> findEmptyDormByDormCategory(DormBuildVo dormBuildVo);
    @PostMapping("/updateSaveDormPerson")
    long updateSaveDormPerson(DormPerson dormPerson);
}
