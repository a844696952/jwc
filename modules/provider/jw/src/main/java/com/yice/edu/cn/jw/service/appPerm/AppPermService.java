package com.yice.edu.cn.jw.service.appPerm;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.appPerm.AppPermDao;
import com.yice.edu.cn.jw.dao.appPerm.AppSchoolPermDao;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AppPermService {
    @Autowired
    private AppPermDao appPermDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IPermDao permDao;

    @Autowired
    private AppSchoolPermDao appSchoolPermDao;


    @Transactional(readOnly = true)
    public AppPerm findAppPermById(String id) {
        return appPermDao.findAppPermById(id);
    }
    @Transactional
    public void saveAppPerm(AppPerm appPerm) {
        appPerm.setId(sequenceId.nextId());
        appPermDao.saveAppPerm(appPerm);
    }
    @Transactional(readOnly = true)
    public List<AppPerm> findAppPermListByCondition(AppPerm appPerm) {
        return appPermDao.findAppPermListByCondition(appPerm);
    }
    @Transactional(readOnly = true)
    public AppPerm findOneAppPermByCondition(AppPerm appPerm) {
        return appPermDao.findOneAppPermByCondition(appPerm);
    }
    @Transactional(readOnly = true)
    public long findAppPermCountByCondition(AppPerm appPerm) {
        return appPermDao.findAppPermCountByCondition(appPerm);
    }
    @Transactional
    public void updateAppPerm(AppPerm appPerm) {
        appPermDao.updateAppPerm(appPerm);
    }
    @Transactional
    public void deleteAppPerm(String id) {
        appPermDao.deleteAppPerm(id);
    }
    @Transactional
    public void deleteAppPermByCondition(AppPerm appPerm) {
        appPermDao.deleteAppPermByCondition(appPerm);
    }
    @Transactional
    public void batchSaveAppPerm(List<AppPerm> appPerms){
        appPerms.forEach(appPerm -> appPerm.setId(sequenceId.nextId()));
        appPermDao.batchSaveAppPerm(appPerms);
    }

    //返回对应学校对应老师或家长权限
    @Transactional(readOnly = true)
    public List<AppPerm> findAppPermListTreeByClass(AppPerm appPerm){
        Pager pager = new Pager();
        pager.setIncludes("id,appName,parentId,appIcon,identify,schoolPermId");
        appPerm.setPager(pager);
        List<Perm> permList = new ArrayList<>();
        List<AppPerm> appPermList = new ArrayList<>();
        Perm perm = new Perm();
        if(appPerm.getWhatApp()==0){//原生教师端
            permList = appPermDao.findTeacherTreeMenuByTId(appPerm.getTeacherId());
        }else if(appPerm.getWhatApp()==1){//小程序教    师端
            permList = appPermDao.findTeacherTreeMenuByTId(appPerm.getTeacherId());
        } else if(appPerm.getWhatApp()==2){//原生家长端
            perm.setSchoolId(appPerm.getSchoolId());
            appPerm.setSchoolId(null);
            permList = permDao.findPermListByCondition(perm);
        }else {//小程序家长端
            perm.setSchoolId(appPerm.getSchoolId());
            appPerm.setSchoolId(null);
            permList = permDao.findPermListByCondition(perm);
        }
       appPermList = appPermDao.findAppPermAndSchoolPermKong(appPerm);

        return treeAppPermList(appPermList,permList);
    }


    //返回对应学校对应权限
    @Transactional(readOnly = true)
    public List<AppPerm> findAppPermAndSchoolPermKong(Integer type,String schoolId){
        AppPerm appPerm = new AppPerm();
        appPerm.setWhatApp(type);
        Perm perm = new Perm();
        perm.setSchoolId(schoolId);
        List<Perm> permList =  permDao.findPermListByCondition(perm);
        List<AppPerm> appPermList = appPermDao.findAppPermAndSchoolPermKong(appPerm);
        return treeAppPermList(appPermList,permList);
    }

    //修改我的应用默认模板
    @Transactional
    public void updateAppPermModel(Integer type,List<AppPerm> appPerms){
        AppPerm appPerm = new AppPerm();
        appPerm.setWhatApp(type);
        appPermDao.deleteAppPermByCondition(appPerm);
        appPerms = saveSchoolSort(appPerms,false);
        List<AppPerm> appPermList = new ArrayList<>();
        findTreeChildren(appPerms,appPermList);
        appPermDao.batchSaveAppPerm(appPermList);
       /* appPermDao.moveAppPerm(appPermList);*/
    }


    //修改学校权限
    @Transactional
    public void updatesAppSchoolPerm(Integer type,String schoolId,List<AppPerm> appPerms){
        //先删除当前学校权限
        AppSchoolPerm appSchoolPerm = new AppSchoolPerm();
        appSchoolPerm.setSchoolId(schoolId);
        appSchoolPerm.setWhatApp(type);
        appSchoolPermDao.deleteAppSchoolPermByCondition(appSchoolPerm);

        List<AppSchoolPerm> list = new ArrayList<>();
        if(appPerms!=null&&appPerms.size()>0){
            appPerms = saveSchoolSort(appPerms,true);
            appPerms.forEach(f->{
                AppSchoolPerm appSchoolPerm1 = new AppSchoolPerm();
                BeanUtil.copyProperties(f,appSchoolPerm1);
                list.add(appSchoolPerm1);
            });
            appSchoolPermDao.batchSaveAppSchoolPerm(list);
        }

    }

    /**
     * 将树所有的对象取出
     * @param list
     * @param list1
     * @return
     */
    public List<AppPerm> findTreeChildren(List<AppPerm> list,List<AppPerm> list1){
        for(AppPerm appPerm : list){
            if (appPerm.getChildren()!=null){
                findTreeChildren(appPerm.getChildren(),list1);
                appPerm.setChildren(null);
            }
            list1.add(appPerm);
        }
        return list1;
    }


    /**
     * 迭代添加排序
     * flags为true时，给对应学校修改对应排序，false时，修改默认模板顺序
     * @param appPerms
     * @param flags
     * @return
     */
    public static List<AppPerm> saveSchoolSort(List<AppPerm> appPerms,Boolean flags){
        Integer sort = 1;
        for(AppPerm appPerm : appPerms){
            if(flags){
                appPerm.setSchoolSort(sort);
            }else {
                appPerm.setSort(sort);
            }
            sort++;
            if(appPerm.getChildren()!=null){
                saveSchoolSort(appPerm.getChildren(),flags);
            }
        }
        return appPerms;
    }

    /**
     * @param appPermList
     * @param permList
     * @return
     */
    public static List<AppPerm>  treeAppPermList(List<AppPerm> appPermList,List<Perm> permList){
        to: for(int i =appPermList.size()-1;i>=0;i--){
            for(int j = 0;j<permList.size();j++){
                if(appPermList.get(i).getSchoolPermId()!=null&&appPermList.get(i).getSchoolPermId().equals(permList.get(j).getId())){
                    continue to;
                }
            }
            if(appPermList.get(i).getSchoolPermId()!=null&&appPermList.get(i).getSchoolPermId().length()!=0){
                appPermList.remove(i);
            }

        }
        return ObjectKit.buildTree(appPermList,"-1");
    }
}
