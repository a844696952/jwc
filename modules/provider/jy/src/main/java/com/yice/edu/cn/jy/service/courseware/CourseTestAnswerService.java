package com.yice.edu.cn.jy.service.courseware;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import com.yice.edu.cn.jy.dao.courseware.ICourseTestAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseTestAnswerService {
    @Autowired
    private ICourseTestAnswerDao courseTestAnswerDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CourseTestAnswer findCourseTestAnswerById(String id) {
        return courseTestAnswerDao.findCourseTestAnswerById(id);
    }
    @Transactional
    public void saveCourseTestAnswer(CourseTestAnswer courseTestAnswer) {
        courseTestAnswer.setId(sequenceId.nextId());
        courseTestAnswerDao.saveCourseTestAnswer(courseTestAnswer);
    }
    @Transactional(readOnly = true)
    public List<CourseTestAnswer> findCourseTestAnswerListByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerDao.findCourseTestAnswerListByCondition(courseTestAnswer);
    }
    @Transactional(readOnly = true)
    public CourseTestAnswer findOneCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerDao.findOneCourseTestAnswerByCondition(courseTestAnswer);
    }
    @Transactional(readOnly = true)
    public long findCourseTestAnswerCountByCondition(CourseTestAnswer courseTestAnswer) {
        return courseTestAnswerDao.findCourseTestAnswerCountByCondition(courseTestAnswer);
    }
    @Transactional
    public void updateCourseTestAnswer(CourseTestAnswer courseTestAnswer) {
        courseTestAnswerDao.updateCourseTestAnswer(courseTestAnswer);
    }
    @Transactional
    public void deleteCourseTestAnswer(String id) {
        courseTestAnswerDao.deleteCourseTestAnswer(id);
    }
    @Transactional
    public void deleteCourseTestAnswerByCondition(CourseTestAnswer courseTestAnswer) {
        courseTestAnswerDao.deleteCourseTestAnswerByCondition(courseTestAnswer);
    }
    @Transactional
    public void batchSaveCourseTestAnswer(List<CourseTestAnswer> courseTestAnswers){
        courseTestAnswers.forEach(courseTestAnswer -> courseTestAnswer.setId(sequenceId.nextId()));
        courseTestAnswerDao.batchSaveCourseTestAnswer(courseTestAnswers);
    }

}
