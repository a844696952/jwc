package com.yice.edu.cn.dm.service.teacher;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import com.yice.edu.cn.dm.dao.teacher.IDmFamousTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmFamousTeacherService {
    @Autowired
    private IDmFamousTeacherDao dmFamousTeacherDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmFamousTeacher findDmFamousTeacherById(String id) {
        return dmFamousTeacherDao.findDmFamousTeacherById(id);
    }
    @Transactional
    public void saveDmFamousTeacher(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacher.setId(sequenceId.nextId());
        dmFamousTeacherDao.saveDmFamousTeacher(dmFamousTeacher);
    }
    @Transactional(readOnly = true)
    public List<DmFamousTeacher> findDmFamousTeacherListByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherDao.findDmFamousTeacherListByCondition(dmFamousTeacher);
    }
    @Transactional(readOnly = true)
    public DmFamousTeacher findOneDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherDao.findOneDmFamousTeacherByCondition(dmFamousTeacher);
    }
    @Transactional(readOnly = true)
    public long findDmFamousTeacherCountByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherDao.findDmFamousTeacherCountByCondition(dmFamousTeacher);
    }
    @Transactional
    public void updateDmFamousTeacher(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherDao.updateDmFamousTeacher(dmFamousTeacher);
    }
    @Transactional
    public void updateDmFamousTeacherForAll(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherDao.updateDmFamousTeacherForAll(dmFamousTeacher);
    }
    @Transactional
    public void deleteDmFamousTeacher(String id) {
        dmFamousTeacherDao.deleteDmFamousTeacher(id);
    }
    @Transactional
    public void deleteDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherDao.deleteDmFamousTeacherByCondition(dmFamousTeacher);
    }
    @Transactional
    public void batchSaveDmFamousTeacher(List<DmFamousTeacher> dmFamousTeachers){
        dmFamousTeachers.forEach(dmFamousTeacher -> dmFamousTeacher.setId(sequenceId.nextId()));
        dmFamousTeacherDao.batchSaveDmFamousTeacher(dmFamousTeachers);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
