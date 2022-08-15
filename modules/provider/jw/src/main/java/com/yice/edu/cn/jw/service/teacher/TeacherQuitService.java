package com.yice.edu.cn.jw.service.teacher;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import com.yice.edu.cn.jw.dao.teacher.ITeacherQuitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherQuitService {
    @Autowired
    private ITeacherQuitDao teacherQuitDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public TeacherQuit findTeacherQuitById(String id) {
        return teacherQuitDao.findTeacherQuitById(id);
    }
    @Transactional
    public void saveTeacherQuit(TeacherQuit teacherQuit) {
        teacherQuit.setId(sequenceId.nextId());
        teacherQuitDao.saveTeacherQuit(teacherQuit);
    }
    @Transactional(readOnly = true)
    public List<TeacherQuit> findTeacherQuitListByCondition(TeacherQuit teacherQuit) {
        return teacherQuitDao.findTeacherQuitListByCondition(teacherQuit);
    }
    @Transactional(readOnly = true)
    public long findTeacherQuitCountByCondition(TeacherQuit teacherQuit) {
        return teacherQuitDao.findTeacherQuitCountByCondition(teacherQuit);
    }
    @Transactional
    public void updateTeacherQuit(TeacherQuit teacherQuit) {
        teacherQuitDao.updateTeacherQuit(teacherQuit);
    }
    @Transactional
    public void deleteTeacherQuit(String id) {
        teacherQuitDao.deleteTeacherQuit(id);
    }
    @Transactional
    public void deleteTeacherQuitByCondition(TeacherQuit teacherQuit) {
        teacherQuitDao.deleteTeacherQuitByCondition(teacherQuit);
    }
    @Transactional(readOnly = true)
    public List<TeacherQuit> findQuitTeachers4Yed(TeacherQuit teacherQuit) {
        return teacherQuitDao.findQuitTeachers4Yed(teacherQuit);
    }
    @Transactional(readOnly = true)
    public long findQuitTeachersCount4Yed(TeacherQuit teacherQuit) {
        return teacherQuitDao.findQuitTeachersCount4Yed(teacherQuit);
    }
}
