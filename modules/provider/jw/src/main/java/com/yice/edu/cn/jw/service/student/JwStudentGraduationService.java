package com.yice.edu.cn.jw.service.student;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.jw.dao.student.StudentGraduationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JwStudentGraduationService {
    @Autowired
    private StudentGraduationDao jwStudentGraduationDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public JwStudentGraduation findJwStudentGraduationById(String id) {
        return jwStudentGraduationDao.findJwStudentGraduationById(id);
    }
    @Transactional
    public void saveJwStudentGraduation(JwStudentGraduation jwStudentGraduation) {
        jwStudentGraduation.setId(sequenceId.nextId());
        jwStudentGraduationDao.saveJwStudentGraduation(jwStudentGraduation);
    }
    @Transactional(readOnly = true)
    public List<JwStudentGraduation> findJwStudentGraduationListByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationDao.findJwStudentGraduationListByCondition(jwStudentGraduation);
    }
    @Transactional(readOnly = true)
    public JwStudentGraduation findOneJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationDao.findOneJwStudentGraduationByCondition(jwStudentGraduation);
    }
    @Transactional(readOnly = true)
    public long findJwStudentGraduationCountByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationDao.findJwStudentGraduationCountByCondition(jwStudentGraduation);
    }
    @Transactional
    public void updateJwStudentGraduation(JwStudentGraduation jwStudentGraduation) {
        jwStudentGraduationDao.updateJwStudentGraduation(jwStudentGraduation);
    }
    @Transactional
    public void deleteJwStudentGraduation(String id) {
        jwStudentGraduationDao.deleteJwStudentGraduation(id);
    }
    @Transactional
    public void deleteJwStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation) {
        jwStudentGraduationDao.deleteJwStudentGraduationByCondition(jwStudentGraduation);
    }

    @Transactional
    public void batchSaveJwStudentGraduation(List<JwStudentGraduation> jwStudentGraduations){
        jwStudentGraduationDao.batchSaveJwStudentGraduation(jwStudentGraduations);
    }

    @Transactional
    public void piLiangSaveJwStudentGraduation(JwStudentGraduation[] jwStudentGraduations){
        jwStudentGraduationDao.piLiangSaveJwStudentGraduation(jwStudentGraduations);
    }

    public List<Integer> findJwStudentGraduationClassesByYear(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationDao.findJwStudentGraduationClassesByYear(jwStudentGraduation);
    }

    public List<JwStudentGraduation> findStudentGraduationListForExportStudentGraduationByCondition(JwStudentGraduation jwStudentGraduation) {
        return jwStudentGraduationDao.findStudentGraduationListForExportStudentGraduationByCondition(jwStudentGraduation);
    }
}
