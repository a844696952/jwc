package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherRoleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherRoleService {
    @Autowired
    private TeacherRoleFeign teacherRoleFeign;

    public void batchDelsertTeacherRoles(TeacherRole teacherRole) {
        teacherRoleFeign.batchDelsertTeacherRoles(teacherRole);
    }
}
