package com.yice.edu.cn.jw.service.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.jw.dao.appPerm.AppPermDao;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import com.yice.edu.cn.jw.dao.auth.IRolePermDao;
import com.yice.edu.cn.jw.dao.auth.ISchoolPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SchoolPermService {
    @Autowired
    private ISchoolPermDao schoolPermDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IPermDao permDao;
    @Autowired
    private IRolePermDao rolePermDao;

    @Autowired
    private AppPermDao appPermDao;

    @Transactional(readOnly = true)
    public SchoolPerm findSchoolPermById(String id) {
        return schoolPermDao.findSchoolPermById(id);
    }

    @Transactional
    public void saveSchoolPerm(SchoolPerm schoolPerm) {
        String id = sequenceId.nextId();
        schoolPerm.setId(id);
        schoolPermDao.saveSchoolPerm(schoolPerm);
        //同时增加到jw_perm中去
       /* if (!"-1".equals(schoolPerm.getParentId())) {//不是顶级的情况下
            Perm con = new Perm();
            con.setId(schoolPerm.getParentId());
            List<Perm> perms = permDao.findPermListByCondition(con);
            List<Perm> list = new ArrayList<>();
            perms.forEach(perm -> {
                Perm item = new Perm();
                BeanUtil.copyProperties(schoolPerm, item);
                item.setSchoolId(perm.getSchoolId());
                list.add(item);
            });
            permDao.batchSavePerm(list);
        }*/


    }

    @Transactional(readOnly = true)
    public List<SchoolPerm> findSchoolPermListByCondition(SchoolPerm schoolPerm) {
        return schoolPermDao.findSchoolPermListByCondition(schoolPerm);
    }

    @Transactional(readOnly = true)
    public long findSchoolPermCountByCondition(SchoolPerm schoolPerm) {
        return schoolPermDao.findSchoolPermCountByCondition(schoolPerm);
    }

    /**
     * 更新学校总权限的时候也要更新传输给学校的权限
     *
     * @param schoolPerm
     */
    @Transactional
    public void updateSchoolPerm(SchoolPerm schoolPerm) {
        schoolPermDao.updateSchoolPerm(schoolPerm);
        Perm perm = new Perm();
        BeanUtil.copyProperties(schoolPerm, perm);
        permDao.updatePerm(perm);
    }

    @Transactional
    public void deleteSchoolPerm(String id) {
        schoolPermDao.deleteSchoolPerm(id);
    }

    @Transactional
    public void deleteSchoolPermByCondition(SchoolPerm schoolPerm) {
        schoolPermDao.deleteSchoolPermByCondition(schoolPerm);
    }

    @Transactional(readOnly = true)
    public List<SysPerm> findAllTreeMenu() {
        return findAllSchoolPermTreeRecursive("-1");
    }

    private List<SysPerm> findAllSchoolPermTreeRecursive(String pId) {
        List<SysPerm> perms = schoolPermDao.findSysPermsByPId(pId);
        for (SysPerm perm : perms) {
            List<SysPerm> list = findAllSchoolPermTreeRecursive(perm.getId());
            perm.setChildren(list);
        }
        return perms;
    }

    @Transactional
    public void deleteSchoolPermRecursive(String id) {
        schoolPermDao.deleteSchoolPerm(id);
        permDao.deletePerm(id);
        rolePermDao.deleteRolePermByPermId(id);
        deleteSchoolPermByPIdRecursive(id);
    }

    @Transactional
    public void deleteSchoolPermByPIdRecursive(String pId) {
        List<SchoolPerm> perms = schoolPermDao.findSchoolPermByPId(pId);
        if (perms.size() > 0) {
            schoolPermDao.deleteSchoolPermByPid(pId);
            permDao.deletePermByPId(pId);
        }
        for (SchoolPerm perm : perms) {
            rolePermDao.deleteRolePermByPermId(perm.getId());
            deleteSchoolPermByPIdRecursive(perm.getId());
        }
    }
    @Transactional
    public ResponseJson findSchoolAndAppPermRelation(String id){
        AppPerm appPerm = new AppPerm();
        appPerm.setSchoolPermId(id);
        List<AppPerm> appPermList = appPermDao.findAppPermListByCondition(appPerm);
        if(appPermList.size()>0){
            return new ResponseJson(false,"此菜单已经与App端应用关联，请先解除关联！");
        }
        return findSchoolAndAppPermBySchoolId(id);
    }

    @Transactional
    public ResponseJson findSchoolAndAppPermBySchoolId(String pid){
        List<SchoolPerm> schoolPerms = schoolPermDao.findSchoolPermByPId(pid);
        if (schoolPerms.size()==0){
            return new ResponseJson();
        }
        List<AppPerm> appPermList = appPermDao.findSchoolAndAppPermRelation(schoolPerms);
        if(appPermList.size()>0){
            return new ResponseJson(false,"此菜单的子菜单已经与App端应用关联，请先解除关联！");
        }
        for(SchoolPerm schoolPerm : schoolPerms){
            findSchoolAndAppPermBySchoolId(schoolPerm.getId());
        }
        return new ResponseJson(true,"允许删除");
    }

    @Transactional
    public void updatePerms(String schoolId, List<String> checkedIds) {
        Perm perm = new Perm();
        perm.setPager(new Pager().setPaging(false).setIncludes("id"));
        perm.setSchoolId(schoolId);
        List<Perm> permObjs = permDao.findPermListByCondition(perm);
        List<String> perms = permObjs.stream().flatMap(p -> Stream.of(p.getId())).collect(Collectors.toList());
        //获取被删除的jw_perm 的id
        List<String> deletedIds = new ArrayList<>();
        perms.forEach(p -> {
            boolean match = checkedIds.stream().anyMatch(id -> id.equals(p));
            if (!match) {
                deletedIds.add(p);
            }
        });
        //首先删除jw_perm表的学校权限，然后批量添加进去，最后删除jw_role_jw_perm里的被删除jw_perm的数据
        permDao.deletePermBySchoolId(schoolId);
        if (checkedIds.size() > 0) {
            List<SchoolPerm> schoolPerms = schoolPermDao.findSchoolPermByIds(checkedIds);
            List<Perm> newPerms = new ArrayList<>();
            schoolPerms.forEach(sp -> {
                Perm p = new Perm();
                BeanUtil.copyProperties(sp, p);
                p.setSchoolId(schoolId);
                newPerms.add(p);
            });
            permDao.batchSavePerm(newPerms);
        }
        if (deletedIds.size() > 0) {
            rolePermDao.deleteRolePermByPermIdsAndSchoolId(deletedIds, schoolId);
        }


    }

    @Transactional
    public void syncUpdate() {
        //1.删除不存在的
        List<String> ids = schoolPermDao.findNotExistsJwPermIds();
        if (ids.size() > 0) {
            permDao.deletePermByIds(ids);
            //还要删除中间表
            rolePermDao.deleteRolePermByPermIds(ids);
        }
        //2.更新修改
        List<SchoolPerm> schoolPerms = schoolPermDao.findAllSchoolPerms();
        permDao.batchUpdate(schoolPerms);
    }
    
    /**
              * 调用权限同步存储过程
     */
    @Transactional
    public void syncSchoolPermByPro() {
    	schoolPermDao.syncSchoolPermByPro();
    }
    @Transactional
    public void batchUpdateSortNum(List<SchoolPerm> perms) {
        schoolPermDao.batchUpdateSortNum(perms);
        permDao.batchUpdateSortNum(perms);
    }
}
