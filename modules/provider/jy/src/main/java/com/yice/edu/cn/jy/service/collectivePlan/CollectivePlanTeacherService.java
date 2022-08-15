package com.yice.edu.cn.jy.service.collectivePlan;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import com.yice.edu.cn.jy.dao.collectivePlan.ICollectivePlanTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectivePlanTeacherService {
    @Autowired
    private ICollectivePlanTeacherDao collectivePlanTeacherDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CollectivePlanTeacher findCollectivePlanTeacherById(String id) {
        return collectivePlanTeacherDao.findCollectivePlanTeacherById(id);
    }
    @Transactional
    public void saveCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher) {
        collectivePlanTeacher.setId(sequenceId.nextId());
        collectivePlanTeacherDao.saveCollectivePlanTeacher(collectivePlanTeacher);
    }
    @Transactional(readOnly = true)
    public List<CollectivePlanTeacher> findCollectivePlanTeacherListByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherDao.findCollectivePlanTeacherListByCondition(collectivePlanTeacher);
    }
    @Transactional(readOnly = true)
    public CollectivePlanTeacher findOneCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherDao.findOneCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }
    @Transactional(readOnly = true)
    public long findCollectivePlanTeacherCountByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherDao.findCollectivePlanTeacherCountByCondition(collectivePlanTeacher);
    }
    @Transactional
    public void updateCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher) {
        collectivePlanTeacherDao.updateCollectivePlanTeacher(collectivePlanTeacher);
    }
    @Transactional
    public void deleteCollectivePlanTeacher(String id) {
        collectivePlanTeacherDao.deleteCollectivePlanTeacher(id);
    }
    @Transactional
    public void deleteCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher) {
        collectivePlanTeacherDao.deleteCollectivePlanTeacherByCondition(collectivePlanTeacher);
    }
    @Transactional
    public void batchSaveCollectivePlanTeacher(List<CollectivePlanTeacher> collectivePlanTeachers){
        collectivePlanTeacherDao.batchSaveCollectivePlanTeacher(collectivePlanTeachers);
    }
    //查询讨论组中的教师
    @Transactional(readOnly = true)
    public List<CollectivePlanTeacher> findOneCollectivePlanByCollectivePlanId(CollectivePlanTeacher collectivePlanTeacher) {
        return collectivePlanTeacherDao.findOneCollectivePlanByCollectivePlanId(collectivePlanTeacher);
    }

}
