package com.yice.edu.cn.jy.service.courseware;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseTestAnswer;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.jy.dao.courseware.ICourseResDao;
import com.yice.edu.cn.jy.dao.courseware.ICourseTestAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseResService {
    @Autowired
    private ICourseResDao courseResDao;

    @Autowired
    private ICourseTestAnswerDao courseTestAnswerDao;

    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CourseRes findCourseResById(String id) {
        return courseResDao.findCourseResById(id);
    }
    @Transactional
    public void saveCourseRes(CourseRes courseRes) {
        courseRes.setId(sequenceId.nextId());
        courseRes.setCreateTime(DateUtil.now());
        courseResDao.saveCourseRes(courseRes);
    }
    @Transactional(readOnly = true)
    public List<CourseRes> findCourseResListByCondition(CourseRes courseRes) {
        List<CourseRes> courseResListByCondition = courseResDao.findCourseResListByCondition(courseRes);
        return courseResListByCondition;
    }
    @Transactional(readOnly = true)
    public CourseRes findOneCourseResByCondition(CourseRes courseRes) {
        return courseResDao.findOneCourseResByCondition(courseRes);
    }
    @Transactional(readOnly = true)
    public long findCourseResCountByCondition(CourseRes courseRes) {
        return courseResDao.findCourseResCountByCondition(courseRes);
    }
    @Transactional
    public void updateCourseRes(CourseRes courseRes) {
        courseResDao.updateCourseRes(courseRes);
    }

    @Transactional
    public void deleteCourseRes(String id) {
        courseResDao.deleteCourseRes(id);
        CourseTestAnswer answer = new CourseTestAnswer();
        answer.setTestId(id);
        courseTestAnswerDao.deleteCourseTestAnswerByCondition(answer);
    }
    @Transactional
    public void deleteCourseResByCondition(CourseRes courseRes) {
        courseResDao.deleteCourseResByCondition(courseRes);
        if(courseRes.getId()!=null){
            CourseTestAnswer answer = new CourseTestAnswer();
            answer.setTestId(courseRes.getId());
            courseTestAnswerDao.deleteCourseTestAnswerByCondition(answer);
        }

    }
    @Transactional
    public void batchSaveCourseRes(List<CourseRes> courseRess){
        courseRess.forEach(courseRes -> {
            courseRes.setId(sequenceId.nextId());
            courseRes.setCreateTime(DateUtil.now());
        });
        courseResDao.batchSaveCourseRes(courseRess);
    }

    @Transactional
    public CourseRes mv(CourseRes courseRes) {
        final CourseRes res = courseResDao.findCourseResById(courseRes.getId());
        if(res!=null){
            res.setSubjectMateriaId(courseRes.getSubjectMateriaId());
            res.setLv1(courseRes.getLv1());
            res.setLv2(courseRes.getLv2());
            res.setLv3(courseRes.getLv3());
            res.setLv4(courseRes.getLv4());
            courseResDao.updateCourseRes(res);

        }

        return res;
    }

    @Transactional
    public void mvs(CourseRes courseRes) {

        courseResDao.batchUpdateCourseRes(courseRes);
    }

    @Transactional
    public void deletes(List<String> ids) {
        courseResDao.deletes(ids);
        courseTestAnswerDao.deletes(ids);
    }

    @Transactional
    public CourseRes rename(CourseRes courseRes) {
        final CourseRes res = courseResDao.findCourseResById(courseRes.getId());
        if(res!=null){
            res.setName(courseRes.getName());
            courseResDao.updateCourseRes(res);

        }
        return res;
    }


    @Transactional
    public void saveTestCourseRes(CourseRes courseRes) {
        courseRes.setId(sequenceId.nextId());
        courseResDao.saveCourseRes(courseRes);
        //新增答案
        courseRes.getCourseTestAnswers().forEach(answer->{
            answer.setId(sequenceId.nextId());
            answer.setTestId(courseRes.getId());
        });
        courseTestAnswerDao.batchSaveCourseTestAnswer(courseRes.getCourseTestAnswers());
    }

    @Transactional
    public void remark(CourseRes courseRes) {
        courseResDao.remark(courseRes);
    }

    @Transactional(readOnly = true)
    public CourseRes findLastRes(CourseRes courseRes) {
        return courseResDao.findLastRes(courseRes);
    }

    public CourseRes findRecentlyCourseWare(CourseWare courseWare){
        return courseResDao.findRecentlyCourseWare(courseWare);
    }

}
