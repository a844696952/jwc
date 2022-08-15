package com.yice.edu.cn.bmp.feignClient.parent;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "parentFeign",path = "/parent")
public interface ParentFeign {
    @PostMapping("/login")
    Parent login(Parent parent);
    @GetMapping("/findParentById/{id}")
    Parent findParentById(@PathVariable("id") String id);
    @PostMapping("/saveParent")
    Parent saveParent(Parent parent);
    @PostMapping("/saveParentStudent")
    ParentStudent saveParentStudent(ParentStudent parentStudent);
    @PostMapping("/findParentListByCondition")
    List<Parent> findParentListByCondition(Parent parent);
    @PostMapping("/findOneParentByCondition")
    Parent findOneParentByCondition(Parent parent);
    @PostMapping("/findParentCountByCondition")
    long findParentCountByCondition(Parent parent);
    @PostMapping("/updateParent")
    void updateParent(Parent parent);
    @PostMapping("/updateParent1")
    void updateParent1(Parent parent);
    @GetMapping("/deleteParent/{id}")
    void deleteParent(@PathVariable("id") String id);
    @PostMapping("/deleteParentByCondition")
    void deleteParentByCondition(Parent parent);
    @PostMapping("/deleteParentStudentByCondition")
    void  deleteParentStudentByCondition(ParentStudent parentStudent);
    @PostMapping("/deleteParentStudentByParentId")
    void  deleteParentStudentByParentId(ParentStudent parentStudent);
    @PostMapping("/updatePassword")
    void updatePassword(Parent parent);
    @PostMapping("/findParentStudentListByCondition")
    List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent);
    @GetMapping("/findParentToRedisById/{id}")
    Parent findParentToRedisById(@PathVariable("id") String id);

    @PostMapping("/updateParentStudent")
    void updateParentStudent(ParentStudent parentStudent);

    @PostMapping("/getBoundStudentList")
    List<Student> getBoundStudentList(ParentStudent parentStudent);
    @PostMapping("/getBoundStudentListInCenter")
    List<ParentStudentFile> getBoundStudentListInCenter(ParentStudent parentStudent);
    @PostMapping("/updateRelationshipAndParentName")
    void updateRelationshipAndParentName (ParentNameRelationship parentNameRelationship);
    @PostMapping("/bindOpenIdParent")
    void bindOpenIdParent(Parent parent);
    @GetMapping("/findParentMsgByStudentId/{id}")
    ParentStudentFile findParentMsgByStudentId(@PathVariable("id")String id);

    @PostMapping("/findSchoolByParentId")
    List<ParentStudent> findSchoolByParentId(ParentStudent parentStudent);
}
