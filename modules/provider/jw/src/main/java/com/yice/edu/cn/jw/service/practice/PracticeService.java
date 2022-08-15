package com.yice.edu.cn.jw.service.practice;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import com.yice.edu.cn.jw.dao.practice.IPracticeDao;
import com.yice.edu.cn.jw.dao.practice.IPracticeFileDao;
import com.yice.edu.cn.jw.dao.practice.IPracticeTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PracticeService {
    @Autowired
    private IPracticeDao practiceDao;
    @Autowired
    private IPracticeTeacherDao practiceTeacherDao;
    @Autowired
    private IPracticeFileDao practiceFileDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Practice findPracticeById(String id) {
        return practiceDao.findPracticeById(id);
    }
    @Transactional
    public void savePractice(Practice practice) {
        practice.setId(sequenceId.nextId());
        practice.setPracticeStartdate(practice.getRangeTime()[0]);
        practice.setPracticeEnddate(practice.getRangeTime()[1]);
        List<PracticeTeacher> practiceTeacherList=practice.getPracticeTeacherCheck();
        practiceTeacherList.forEach(practiceTeacher -> {
            practiceTeacher.setId(sequenceId.nextId());
            practiceTeacher.setPracticeId(practice.getId());
            practiceTeacher.setTeacherId(practiceTeacher.getTeacherId());
            practiceTeacher.setName(practiceTeacher.getName());
            practiceTeacher.setSchoolId(practice.getSchoolId());
            practiceTeacherDao.savePracticeTeacher(practiceTeacher);
        });
        List<PracticeFile> practiceFileList=practice.getFiles();
        practiceFileList.forEach(practiceFile -> {
            practiceFile.setId(sequenceId.nextId());
            practiceFile.setPracticeId(practice.getId());
            practiceFile.setFileName(practiceFile.getFileName());
            practiceFile.setFileUrl(practiceFile.getFileUrl());
            practiceFile.setFileSize(practiceFile.getFileSize());
            practiceFile.setSchoolId(practice.getSchoolId());
            practiceFileDao.savePracticeFile(practiceFile);
        });
        practiceDao.savePractice(practice);
    }
    @Transactional(readOnly = true)
    public List<Practice> findPracticeListByCondition(Practice practice) {
        return practiceDao.findPracticeListByCondition(practice);
    }
    @Transactional(readOnly = true)
    public List<Practice> findPracticeListByCondition1(Practice practice) {
        return practiceDao.findPracticeListByCondition1(practice);
    }
    @Transactional(readOnly = true)
    public List<Practice> findPracticeListByTeacherId(Practice practice){
        return practiceDao.findPracticeListByTeacherId(practice);
    }

    @Transactional(readOnly = true)
    public Practice findOnePracticeByCondition(Practice practice) {
        return practiceDao.findOnePracticeByCondition(practice);
    }

    @Transactional(readOnly = true)
    public long findPracticeCountByTeacherId(Practice practice) {
        return practiceDao.findPracticeCountByTeacherId(practice);
    }
    @Transactional(readOnly = true)
    public long findPracticeCountByCondition(Practice practice) {
        return practiceDao.findPracticeCountByCondition(practice);
    }
    @Transactional(readOnly = true)
    public long findPracticeCountByCondition1(Practice practice) {
        return practiceDao.findPracticeCountByCondition1(practice);
    }

    @Transactional
    public void updatePractice(Practice practice) {
        PracticeTeacher practiceTeachers=new PracticeTeacher();
        practiceTeachers.setPracticeId(practice.getId());
        practiceTeacherDao.deletePracticeTeacherByCondition(practiceTeachers);
        PracticeFile practiceFiles=new PracticeFile();
        practiceFiles.setPracticeId(practice.getId());
        practiceFileDao.deletePracticeFileByCondition(practiceFiles);

        practice.setPracticeStartdate(practice.getRangeTime()[0]);
        practice.setPracticeEnddate(practice.getRangeTime()[1]);
        List<PracticeTeacher> practiceTeacherList=practice.getPracticeTeacherCheck();
        practiceTeacherList.forEach(practiceTeacher -> {
            practiceTeacher.setId(sequenceId.nextId());
            practiceTeacher.setPracticeId(practice.getId());
            practiceTeacher.setTeacherId(practiceTeacher.getTeacherId());
            practiceTeacher.setName(practiceTeacher.getName());
            practiceTeacher.setSchoolId(practice.getSchoolId());
            practiceTeacherDao.savePracticeTeacher(practiceTeacher);
        });
        List<PracticeFile> practiceFileList=practice.getFiles();
        practiceFileList.forEach(practiceFile -> {
            practiceFile.setId(sequenceId.nextId());
            practiceFile.setPracticeId(practice.getId());
            practiceFile.setFileName(practiceFile.getFileName());
            practiceFile.setFileUrl(practiceFile.getFileUrl());
            practiceFile.setFileSize(practiceFile.getFileSize());
            practiceFile.setSchoolId(practice.getSchoolId());
            practiceFileDao.savePracticeFile(practiceFile);
        });
        practiceDao.updatePractice(practice);
    }
    @Transactional
    public void deletePractice(String id) {
        PracticeTeacher practiceTeacher=new PracticeTeacher();
        practiceTeacher.setPracticeId(id);
        practiceTeacherDao.deletePracticeTeacherByCondition(practiceTeacher);
        practiceDao.deletePractice(id);
    }
    @Transactional
    public void deletePracticeByCondition(Practice practice) {
        practiceDao.deletePracticeByCondition(practice);
    }
    @Transactional
    public void batchSavePractice(List<Practice> practices){
        practices.forEach(practice -> practice.setId(sequenceId.nextId()));
        practiceDao.batchSavePractice(practices);
    }

}
