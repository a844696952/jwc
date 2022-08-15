package com.yice.edu.cn.jw.service.educationBureau;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.jw.dao.educationBureau.ISchoolEducationBureauDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolEducationBureauService {
    @Autowired
    private ISchoolEducationBureauDao schoolEducationBureauDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public SchoolEducationBureau findSchoolEducationBureauById(String id) {
        return schoolEducationBureauDao.findSchoolEducationBureauById(id);
    }
    @Transactional
    public void saveSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureau.setId(sequenceId.nextId());
        schoolEducationBureauDao.saveSchoolEducationBureau(schoolEducationBureau);
    }
    @Transactional(readOnly = true)
    public List<SchoolEducationBureau> findSchoolEducationBureauListByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauDao.findSchoolEducationBureauListByCondition(schoolEducationBureau);
    }
    @Transactional(readOnly = true)
    public SchoolEducationBureau findOneSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauDao.findOneSchoolEducationBureauByCondition(schoolEducationBureau);
    }
    @Transactional(readOnly = true)
    public long findSchoolEducationBureauCountByCondition(SchoolEducationBureau schoolEducationBureau) {
        return schoolEducationBureauDao.findSchoolEducationBureauCountByCondition(schoolEducationBureau);
    }
    @Transactional
    public void updateSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauDao.updateSchoolEducationBureau(schoolEducationBureau);
    }
    @Transactional
    public void deleteSchoolEducationBureau(String id) {
        schoolEducationBureauDao.deleteSchoolEducationBureau(id);
    }
    @Transactional
    public void deleteSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauDao.deleteSchoolEducationBureauByCondition(schoolEducationBureau);
    }
    @Transactional
    public void batchSaveSchoolEducationBureau(List<SchoolEducationBureau> schoolEducationBureaus){
        schoolEducationBureauDao.batchSaveSchoolEducationBureau(schoolEducationBureaus);
    }
    @Transactional
    public void resetSchoolEducationBureaus(SchoolEducationBureau schoolEducationBureau) {
        schoolEducationBureauDao.deleteSchoolEducationBureauByCondition(schoolEducationBureau);
        List<String> schoolIds = schoolEducationBureau.getSchoolIds();
        List<SchoolEducationBureau> list = new ArrayList<>();
        schoolIds.forEach(schoolId->{
            SchoolEducationBureau seb = new SchoolEducationBureau();
            seb.setSchoolId(schoolId);
            seb.setEducationBureauId(schoolEducationBureau.getEducationBureauId());
            seb.setId(sequenceId.nextId());
            list.add(seb);
        });
        if(list.size()>0){
            schoolEducationBureauDao.batchSaveSchoolEducationBureau(list);
        }
    }
}
