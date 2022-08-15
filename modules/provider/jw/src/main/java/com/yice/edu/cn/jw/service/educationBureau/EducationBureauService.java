package com.yice.edu.cn.jw.service.educationBureau;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.jw.dao.educationBureau.IEducationBureauDao;
import com.yice.edu.cn.jw.dao.educationBureau.ISchoolEducationBureauDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationBureauService {
    @Autowired
    private IEducationBureauDao educationBureauDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ISchoolEducationBureauDao schoolEducationBureauDao;

    @Transactional(readOnly = true)
    public EducationBureau findEducationBureauById(String id) {
        return educationBureauDao.findEducationBureauById(id);
    }
    @Transactional
    public void saveEducationBureau(EducationBureau educationBureau) {
        educationBureau.setId(sequenceId.nextId());
        educationBureauDao.saveEducationBureau(educationBureau);
    }
    @Transactional(readOnly = true)
    public List<EducationBureau> findEducationBureauListByCondition(EducationBureau educationBureau) {
        return educationBureauDao.findEducationBureauListByCondition(educationBureau);
    }
    @Transactional(readOnly = true)
    public EducationBureau findOneEducationBureauByCondition(EducationBureau educationBureau) {
        return educationBureauDao.findOneEducationBureauByCondition(educationBureau);
    }
    @Transactional(readOnly = true)
    public long findEducationBureauCountByCondition(EducationBureau educationBureau) {
        return educationBureauDao.findEducationBureauCountByCondition(educationBureau);
    }
    @Transactional
    public void updateEducationBureau(EducationBureau educationBureau) {
        educationBureauDao.updateEducationBureau(educationBureau);
    }
    @Transactional
    public void deleteEducationBureau(String id) {
        educationBureauDao.deleteEducationBureau(id);
        //同时删除学校和教育局关联数据
        SchoolEducationBureau seb = new SchoolEducationBureau();
        seb.setEducationBureauId(id);
        schoolEducationBureauDao.deleteSchoolEducationBureauByCondition(seb);
    }
    @Transactional
    public void deleteEducationBureauByCondition(EducationBureau educationBureau) {
        educationBureauDao.deleteEducationBureauByCondition(educationBureau);
    }
    @Transactional
    public void batchSaveEducationBureau(List<EducationBureau> educationBureaus){
        educationBureauDao.batchSaveEducationBureau(educationBureaus);
    }

    @Transactional(readOnly = true)
    public List<School> findUnSelectedSchoolsById(String educationBureauId) {
        return educationBureauDao.findUnSelectedSchoolsById(educationBureauId);
    }
    @Transactional(readOnly = true)
    public List<String> findSelectedSchoolsById(String educationBureauId) {
        return educationBureauDao.findSelectedSchoolsById(educationBureauId);
    }
}
