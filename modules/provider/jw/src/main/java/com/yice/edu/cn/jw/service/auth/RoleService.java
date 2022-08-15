package com.yice.edu.cn.jw.service.auth;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import com.yice.edu.cn.jw.dao.auth.IRoleDao;
import com.yice.edu.cn.jw.dao.auth.IRolePermDao;
import com.yice.edu.cn.jw.dao.auth.ITeacherRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoleService {
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IPermDao permDao;
    @Autowired
    private IRolePermDao rolePermDao;
    @Autowired
    private ITeacherRoleDao teacherRoleDao;
    @Transactional(readOnly = true)
    public Role findRoleById(String id) {
        return roleDao.findRoleById(id);
    }
    @Transactional
    public void saveRole(Role role) {
        role.setId(sequenceId.nextId());
        roleDao.saveRole(role);
    }
    @Transactional(readOnly = true)
    public List<Role> findRoleListByCondition(Role role) {
        return roleDao.findRoleListByCondition(role);
    }
    @Transactional(readOnly = true)
    public long findRoleCountByCondition(Role role) {
        return roleDao.findRoleCountByCondition(role);
    }
    @Transactional
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }
    @Transactional
    public void deleteRole(String id) {
        roleDao.deleteRole(id);
    }
    @Transactional
    public void deleteRoleByCondition(Role role) {
        roleDao.deleteRoleByCondition(role);
    }
    @Transactional(readOnly = true)
    public List<Perm> findAllPermTreeBySchoolId(String id) {
        return findAllPermTreeByPIdAndSchoolIdRecursive("-1",id);
    }
    @Transactional(readOnly = true)
    public List<Perm> findAllPermTreeByPIdAndSchoolIdRecursive(String pId,String schoolId){
        List<Perm> perms=permDao.findAllPermTreeByPIdAndSchoolId(pId,schoolId);
        perms.forEach(perm -> {
            if(perm.getHasChild()){
                List<Perm> children = findAllPermTreeByPIdAndSchoolIdRecursive(perm.getId(), schoolId);
                perm.setChildren(children);
            }
        });
        return perms;
    }

    public List<String> findCheckedPermIdsByRoleId(String id) {
        List<Perm> perms = permDao.findCheckedPermsByRoleId(id);
        //过滤掉只剩下叶子节点
        return perms.stream().filter(perm -> perms.stream().noneMatch(child -> child.getParentId().equals(perm.getId()))).flatMap(perm -> Stream.of(perm.getId())).collect(Collectors.toList());
    }

    @Transactional
    public void delsertRolePerms(RolePerm rolePerm) {
        rolePermDao.deleteRolePermByCondition(rolePerm);
        String permIds = rolePerm.getPermIds();
        if (permIds != null) {
            String[] arr = permIds.split(",");
            List<RolePerm> rolePerms = new ArrayList<>();
            for (String permId : arr) {
                RolePerm rp = new RolePerm();
                rp.setId(sequenceId.nextId());
                rp.setRoleId(rolePerm.getRoleId());
                rp.setPermId(permId);
                rp.setSchoolId(rolePerm.getSchoolId());
                rolePerms.add(rp);
            }
            rolePermDao.batchSaveRolePerm(rolePerms);
        }
    }

    @Transactional
    public void deleteRoleRelated(String id) {
        roleDao.deleteRole(id);
        TeacherRole teacherRole = new TeacherRole();
        teacherRole.setRoleId(id);
        teacherRoleDao.deleteTeacherRoleByCondition(teacherRole);
        RolePerm rolePerm = new RolePerm();
        rolePerm.setRoleId(id);
        rolePermDao.deleteRolePermByCondition(rolePerm);
    }
}
