package com.yice.edu.cn.jw.service.parent;

import cn.hutool.crypto.digest.DigestUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.jw.dao.parent.IParentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ParentService {
    @Autowired
    private IParentDao parentDao;
    @Autowired
    private SequenceId sequenceId;
    @CreateCache(name= Constant.Redis.BMP_TOKEN_CACHE,expire = Constant.Redis.BMP_PARENT_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String,String> tokenCache;
    @Transactional(readOnly = true)
    public Parent findParentById(String id) {
        return parentDao.findParentById(id);
    }
    @Transactional
    public void saveParent(Parent parent) {
        parent.setId(sequenceId.nextId());
        parentDao.saveParent(parent);
    }
    @Transactional
    public void saveParentStudent(ParentStudent parentStudent) {
        parentStudent.setId(sequenceId.nextId());
        parentDao.saveParentStudent(parentStudent);
    }
    @Transactional(readOnly = true)
    public List<Parent> findParentListByCondition(Parent parent) {
        return parentDao.findParentListByCondition(parent);
    }
    @Transactional(readOnly = true)
    public Parent findOneParentByCondition(Parent parent) {
        return parentDao.findOneParentByCondition(parent);
    }
    @Transactional(readOnly = true)
    public long findParentCountByCondition(Parent parent) {
        return parentDao.findParentCountByCondition(parent);
    }
    @Transactional
    public void updateParent(Parent parent) {
        parentDao.updateParent(parent);
    }
    @Transactional
    public void updateParent1(Parent parent) {
        parentDao.updateParent1(parent);
    }
    @Transactional
    public void updatePassword(Parent parent){
        parentDao.updatePassword(parent);
    }
    @Transactional
    public void deleteParent(String id) {
        parentDao.deleteParent(id);
    }
    @Transactional
    public void deleteParentByCondition(Parent parent) {
        parentDao.deleteParentByCondition(parent);
    }
    @Transactional
    public void batchSaveParent(List<Parent> parent){
        parentDao.batchSaveParent(parent);
    }

    @Transactional(readOnly = true)
    public Parent login(Parent parent){
        parent.setPassword(DigestUtil.sha1Hex(parent.getPassword()));
        List<Parent>results=parentDao.findParentListByCondition(parent);
        if(results.size()==0){
            return null;
        }
        Parent p=results.get(0);
        return p;
    }
    @Transactional(readOnly = true)
    public List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent) {
        return parentDao.findParentStudentListByCondition(parentStudent);
    }

    @Transactional
    public void updateParentStudent(ParentStudent parentStudent){
        parentDao.updateParentStudent(parentStudent);
    }

    @Transactional
    public void setStudentidToNull(Parent parent){
        parentDao.setStudentidToNull(parent);
    }

    @Transactional
    public void deleteParentStudentByCondition(ParentStudent parentStudent) {
        parentDao.deleteParentStudentByCondition(parentStudent);
    }

    @Transactional
    public void deleteParentStudentByParentId(ParentStudent parentStudent) {
        parentDao.deleteParentStudentByParentId(parentStudent);
    }

    @Transactional
    public List<Student> getBoundStudentList(ParentStudent parentStudent){
       return parentDao.getBoundStudentList(parentStudent);
    }
    @Transactional
    public List<ParentStudentFile> getBoundStudentListInCenter(ParentStudent parentStudent){
        return parentDao.getBoundStudentListInCenter(parentStudent);
    }
    @Transactional
    public Parent findParentToRedisById(String id){
        Parent parent=parentDao.findParentById(id);
        return parent;
    }
    @Transactional
    public void updateRelationshipAndParentName( ParentNameRelationship parentNameRelationship){
        parentDao.updateParentStudent(parentNameRelationship.getParentStudent());
        parentDao.updateParent(parentNameRelationship.getParent());
    }

    @Transactional
    public void bindOpenIdParent(Parent parent){
        parentDao.unbindOpenIdParent(parent.getOpenId());
        Parent p=new Parent();
        p.setId(parent.getId());
        p.setOpenId(parent.getOpenId());
        parentDao.updateParent(p);
    }

    @Transactional(readOnly = true)
    public Parent findParentByStudentId(String id) {
        return parentDao.findParentByStudentId(id);
    }

    @Transactional(readOnly = true)
    public   ParentStudentFile findParentMsgByStudentId(String id){
        return parentDao.findParentMsgByStudentId(id);
    }

   /* @Transactional(readOnly = true)
    public List<String> findStudentIdByClassId(List<String> classId){
       return parentDao.findStudentIdByClassId(classId);
    }*/

    @Transactional(readOnly = true)
    public void deleteParentStudentByShiftpromotion(List<String> classId){
        parentDao.deleteParentStudentByShiftpromotion(classId);
        List<String> parentIdList = parentDao.removeParentIdByClassList(classId);
        parentIdList.forEach(s->{
            tokenCache.remove(s+Constant.Redis.BMP_TOKEN_SUFFIX);
        });

    }

    @Transactional(readOnly = true)
    public List<ParentStudent> findSchoolByParentId(ParentStudent parentStudent) {
        return parentDao.findSchoolByParentId(parentStudent);
    }

    /*@Transactional(readOnly = true)
    public void removeParentIdByStudentList(List<String> stuList){
        List<String> parentIdList = parentDao.removeParentIdByStudentList(stuList);
        parentIdList.forEach(s->{
            tokenCache.remove(s+Constant.Redis.BMP_TOKEN_SUFFIX);
        });
    }*/
}
