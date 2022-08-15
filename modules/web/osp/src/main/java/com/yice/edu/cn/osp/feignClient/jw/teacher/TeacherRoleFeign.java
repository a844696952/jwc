package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw",contextId = "teacherRoleFeign",path = "/teacherRole")
public interface TeacherRoleFeign {
    @GetMapping("/findTeacherRoleById/{id}")
    TeacherRole findTeacherRoleById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherRole")
    TeacherRole saveTeacherRole(TeacherRole teacherRole);
    @PostMapping("/findTeacherRoleListByCondition")
    List<TeacherRole> findTeacherRoleListByCondition(TeacherRole teacherRole);
    @PostMapping("/findTeacherRoleCountByCondition")
    long findTeacherRoleCountByCondition(TeacherRole teacherRole);
    @PostMapping("/updateTeacherRole")
    void updateTeacherRole(TeacherRole teacherRole);
    @GetMapping("/deleteTeacherRole/{id}")
    void deleteTeacherRole(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherRoleByCondition")
    void deleteTeacherRoleByCondition(TeacherRole teacherRole);
    @PostMapping("/batchDelsertTeacherRoles")
    void batchDelsertTeacherRoles(TeacherRole teacherRole);
}
