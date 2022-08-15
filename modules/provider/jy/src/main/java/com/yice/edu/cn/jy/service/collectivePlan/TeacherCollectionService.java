package com.yice.edu.cn.jy.service.collectivePlan;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import com.yice.edu.cn.jy.dao.collectivePlan.ITeacherCollectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TeacherCollectionService {
    @Autowired
    private ITeacherCollectionDao teacherCollectionDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TeacherCollection findTeacherCollectionById(String id) {
        return teacherCollectionDao.findTeacherCollectionById(id);
    }
    @Transactional
    public void saveTeacherCollection(TeacherCollection teacherCollection) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        teacherCollection.setId(sequenceId.nextId());
        teacherCollection.setCreateTime(formatter.format(new Date()));
        teacherCollection.setUpdateTime(formatter.format(new Date()));
        teacherCollection.setCommentCount(0);
        teacherCollection.setUpdateCount(0);
        teacherCollectionDao.saveTeacherCollection(teacherCollection);
    }
    @Transactional(readOnly = true)
    public List<TeacherCollection> findTeacherCollectionListByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionDao.findTeacherCollectionListByCondition(teacherCollection);
    }
    @Transactional(readOnly = true)
    public TeacherCollection findOneTeacherCollectionByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionDao.findOneTeacherCollectionByCondition(teacherCollection);
    }
    @Transactional(readOnly = true)
    public long findTeacherCollectionCountByCondition(TeacherCollection teacherCollection) {
        return teacherCollectionDao.findTeacherCollectionCountByCondition(teacherCollection);
    }
    @Transactional
    public void updateTeacherCollection(TeacherCollection teacherCollection) {
        teacherCollectionDao.updateTeacherCollection(teacherCollection);
    }
    @Transactional
    public void deleteTeacherCollection(String id) {
        teacherCollectionDao.deleteTeacherCollection(id);
    }
    @Transactional
    public void deleteTeacherCollectionByCondition(TeacherCollection teacherCollection) {
        teacherCollectionDao.deleteTeacherCollectionByCondition(teacherCollection);
    }
    @Transactional
    public void batchSaveTeacherCollection(List<TeacherCollection> teacherCollections){
        teacherCollections.forEach(teacherCollection -> teacherCollection.setId(sequenceId.nextId()));
        teacherCollectionDao.batchSaveTeacherCollection(teacherCollections);
    }

    //对集体备课教案的 评论次数 进行增加
    @Transactional
    public void updateCommentCount(TeacherCollection teacherCollection) {
        teacherCollectionDao.updateCommentCount(teacherCollection);
    }

    //对集体备课教案的修改次数  进行增加
    @Transactional
    public void updateModifyCount(TeacherCollection teacherCollection) {
        teacherCollectionDao.updateModifyCount(teacherCollection);
    }
}
