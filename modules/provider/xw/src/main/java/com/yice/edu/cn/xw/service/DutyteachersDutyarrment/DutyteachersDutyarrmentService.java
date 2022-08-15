package com.yice.edu.cn.xw.service.DutyteachersDutyarrment;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.xw.dao.DutyteachersDutyarrment.IDutyteachersDutyarrmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DutyteachersDutyarrmentService {
    @Autowired
    private IDutyteachersDutyarrmentDao dutyteachersDutyarrmentDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DutyteachersDutyarrment findDutyteachersDutyarrmentById(String id) {
        return dutyteachersDutyarrmentDao.findDutyteachersDutyarrmentById(id);
    }
    @Transactional
    public void saveDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment) {
        dutyteachersDutyarrment.setId(sequenceId.nextId());
        dutyteachersDutyarrmentDao.saveDutyteachersDutyarrment(dutyteachersDutyarrment);
    }
    @Transactional(readOnly = true)
    public List<DutyteachersDutyarrment> findDutyteachersDutyarrmentListByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentDao.findDutyteachersDutyarrmentListByCondition(dutyteachersDutyarrment);
    }
    @Transactional(readOnly = true)
    public DutyteachersDutyarrment findOneDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentDao.findOneDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }
    @Transactional(readOnly = true)
    public long findDutyteachersDutyarrmentCountByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentDao.findDutyteachersDutyarrmentCountByCondition(dutyteachersDutyarrment);
    }
    @Transactional
    public void updateDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment) {
        dutyteachersDutyarrmentDao.updateDutyteachersDutyarrment(dutyteachersDutyarrment);
    }
    @Transactional
    public void deleteDutyteachersDutyarrment(String id) {
        dutyteachersDutyarrmentDao.deleteDutyteachersDutyarrment(id);
    }
    @Transactional
    public void deleteDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        dutyteachersDutyarrmentDao.deleteDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }
    @Transactional
    public void batchSaveDutyteachersDutyarrment(List<DutyteachersDutyarrment> dutyteachersDutyarrments){
        dutyteachersDutyarrments.forEach(dutyteachersDutyarrment -> dutyteachersDutyarrment.setId(sequenceId.nextId()));
        dutyteachersDutyarrmentDao.batchSaveDutyteachersDutyarrment(dutyteachersDutyarrments);
    }

}
