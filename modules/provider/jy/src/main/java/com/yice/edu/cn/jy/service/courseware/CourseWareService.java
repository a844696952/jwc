package com.yice.edu.cn.jy.service.courseware;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import com.yice.edu.cn.common.pojo.jy.courseware.ResType;
import com.yice.edu.cn.jy.dao.courseware.ICourseResDao;
import com.yice.edu.cn.jy.dao.courseware.ICourseWareDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.beans.BeanUtils.*;

@Service
public class CourseWareService {
    @Autowired
    private ICourseWareDao courseWareDao;
    @Autowired
    private ICourseResDao courseResDao;

    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public CourseWare findCourseWareById(String id) {
        return courseWareDao.findCourseWareById(id);
    }
    @Transactional
    public void saveCourseWare(CourseWare courseWare) {
        courseWare.setId(sequenceId.nextId());
        courseWareDao.saveCourseWare(courseWare);
    }
    @Transactional(readOnly = true)
    public List<CourseWare> findCourseWareListByCondition(CourseWare courseWare) {
        return courseWareDao.findCourseWareListByCondition(courseWare);
    }
    @Transactional(readOnly = true)
    public CourseWare findOneCourseWareByCondition(CourseWare courseWare) {
        return courseWareDao.findOneCourseWareByCondition(courseWare);
    }
    @Transactional(readOnly = true)
    public long findCourseWareCountByCondition(CourseWare courseWare) {
        return courseWareDao.findCourseWareCountByCondition(courseWare);
    }
    @Transactional
    public void updateCourseWare(CourseWare courseWare) {
        courseWareDao.updateCourseWare(courseWare);
    }
    @Transactional
    public void deleteCourseWare(String id) {
        courseWareDao.deleteCourseWare(id);
    }
    @Transactional
    public void deleteCourseWareByCondition(CourseWare courseWare) {
        courseWareDao.deleteCourseWareByCondition(courseWare);
    }
    @Transactional
    public void batchSaveCourseWare(List<CourseWare> courseWares){
        courseWares.forEach(courseWare -> courseWare.setId(sequenceId.nextId()));
        courseWareDao.batchSaveCourseWare(courseWares);
    }

    @Transactional
    public void deleteCourseWareByIds(List<String> ids) {
        courseWareDao.deleteCourseWareByIds(ids);
    }
    @Transactional
    public void batchUpdateCourseWare(CourseWare courseWare) {
        courseWareDao.batchUpdateCourseWare(courseWare);
    }


    @Transactional(readOnly = true)
    public CourseWare findRecentlyCourseWare(CourseWare courseWare) {
        CourseRes courseRes = courseResDao.findRecentlyCourseWare(courseWare);
        CourseWare courseResult=new CourseWare();
        BeanUtils.copyProperties(courseResult,courseRes);
        courseResult.setResoucesId(courseRes.getId());
        courseResult.setCoursewareName(courseRes.getName());
        courseResult.setCoursewareUrl(courseRes.getResUrl());
        courseResult.setCoursewareSize(courseRes.getResSize().toString());
        return courseResult;
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateCourseWareToNoType(CourseWare courseWare){
         courseWareDao.updateCourseWareToNoType(courseWare);
    }




}
