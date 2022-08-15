package com.yice.edu.cn.jw.service.auth;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.jw.dao.auth.ITeacherRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherRoleService {
    @Autowired
    private ITeacherRoleDao teacherRoleDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TeacherRole findTeacherRoleById(String id) {
        return teacherRoleDao.findTeacherRoleById(id);
    }
    @Transactional
    public void saveTeacherRole(TeacherRole teacherRole) {
        teacherRole.setId(sequenceId.nextId());
        teacherRoleDao.saveTeacherRole(teacherRole);
    }
    @Transactional(readOnly = true)
    public List<TeacherRole> findTeacherRoleListByCondition(TeacherRole teacherRole) {
        return teacherRoleDao.findTeacherRoleListByCondition(teacherRole);
    }
    @Transactional(readOnly = true)
    public long findTeacherRoleCountByCondition(TeacherRole teacherRole) {
        return teacherRoleDao.findTeacherRoleCountByCondition(teacherRole);
    }
    @Transactional
    public void updateTeacherRole(TeacherRole teacherRole) {
        teacherRoleDao.updateTeacherRole(teacherRole);
    }
    @Transactional
    public void deleteTeacherRole(String id) {
        teacherRoleDao.deleteTeacherRole(id);
    }
    @Transactional
    public void deleteTeacherRoleByCondition(TeacherRole teacherRole) {
        teacherRoleDao.deleteTeacherRoleByCondition(teacherRole);
    }
    @Transactional
    public void batchDelsertTeacherRoles(TeacherRole teacherRole) {
        String[] teacherArr = teacherRole.getTeacherArr();
        String[] roleArr = teacherRole.getRoleArr();
        teacherRoleDao.batchDeleteByTeacherIds(teacherArr);
        List<TeacherRole> list = new ArrayList<>();
        for (String roleId : roleArr) {
            for (String teacherId : teacherArr) {
                TeacherRole tr = new TeacherRole();
                tr.setRoleId(roleId);
                tr.setTeacherId(teacherId);
                tr.setId(sequenceId.nextId());
                list.add(tr);

            }
        }
        teacherRoleDao.batchSaveTeacherRole(list);
    }
}
