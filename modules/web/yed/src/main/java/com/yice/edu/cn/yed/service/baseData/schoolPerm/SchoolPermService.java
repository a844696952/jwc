package com.yice.edu.cn.yed.service.baseData.schoolPerm;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.feignClient.baseData.schoolPerm.SchoolPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolPermService {
    @Autowired
    private SchoolPermFeign schoolPermFeign;

    public SchoolPerm findSchoolPermById(String id) {
        return schoolPermFeign.findSchoolPermById(id);
    }

    public SchoolPerm saveSchoolPerm(SchoolPerm schoolPerm) {
        return schoolPermFeign.saveSchoolPerm(schoolPerm);
    }

    public List<SchoolPerm> findSchoolPermListByCondition(SchoolPerm schoolPerm) {
        return schoolPermFeign.findSchoolPermListByCondition(schoolPerm);
    }

    public long findSchoolPermCountByCondition(SchoolPerm schoolPerm) {
        return schoolPermFeign.findSchoolPermCountByCondition(schoolPerm);
    }

    public void updateSchoolPerm(SchoolPerm schoolPerm) {
        schoolPermFeign.updateSchoolPerm(schoolPerm);
    }

    public void deleteSchoolPerm(String id) {
        schoolPermFeign.deleteSchoolPerm(id);
    }

    public void deleteSchoolPermByCondition(SchoolPerm schoolPerm) {
        schoolPermFeign.deleteSchoolPermByCondition(schoolPerm);
    }

    public List<SysPerm> findAllTreeMenu() {
        return schoolPermFeign.findAllTreeMenu();

    }

    public void deleteSchoolPermRecursive(String id) {
        schoolPermFeign.deleteSchoolPermRecursive(id);
    }

    public ResponseJson findSchoolAndAppPermRelation(String id){
        return schoolPermFeign.findSchoolAndAppPermRelation(id);
    }
    public List<SchoolPerm> findAllSchoolTreeMenu() {
        SchoolPerm schoolPerm = new SchoolPerm();
        Pager pager = new Pager().setPaging(false).addExcludes("path", "urlPath","finished","routeName","type").setSortField("sortNum").setSortOrder(Pager.ASC);
        schoolPerm.setPager(pager);
        List<SchoolPerm> schoolPerms = schoolPermFeign.findSchoolPermListByCondition(schoolPerm);
        return ObjectKit.buildTree(schoolPerms,"-1");
    }

    public void updatePerms(String schoolId, List<String> checkedIds) {
        schoolPermFeign.updatePerms(schoolId, checkedIds);
    }

    public void syncUpdate() {
        schoolPermFeign.syncUpdate();

    }
    /**
     * 获取学校总权限树
     * @return
     */
    public List<SchoolPerm> findAllSchoolTreeMenuNoButton() {
        SchoolPerm schoolPerm = new SchoolPerm();
        schoolPerm.setType(0);//只获取菜单不获取按钮
        Pager pager = new Pager().setPaging(false);
        schoolPerm.setPager(pager);
        List<SchoolPerm> schoolPerms = schoolPermFeign.findSchoolPermListByCondition(schoolPerm);
        return ObjectKit.buildTree(schoolPerms,"-1");
    }

    public void batchUpdateSortNum(List<SchoolPerm> perms) {
        schoolPermFeign.batchUpdateSortNum(perms);

    }
}
