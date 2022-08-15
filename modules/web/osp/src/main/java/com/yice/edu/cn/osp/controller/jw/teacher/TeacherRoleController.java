package com.yice.edu.cn.osp.controller.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacherRole")
public class TeacherRoleController {
    @Autowired
    private TeacherRoleService teacherRoleService;

    @PostMapping("/batchDelsertTeacherRoles")
    public ResponseJson batchDelsertTeacherRoles(@RequestBody TeacherRole teacherRole){
        teacherRoleService.batchDelsertTeacherRoles(teacherRole);
        return new ResponseJson();
    }
}
