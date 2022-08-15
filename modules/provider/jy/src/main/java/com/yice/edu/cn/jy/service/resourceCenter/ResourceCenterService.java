package com.yice.edu.cn.jy.service.resourceCenter;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceCenterService {
    @Autowired
    private IResourceCenterDao resourceCenterDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ResourceCenter findResourceCenterById(String id) {
        return resourceCenterDao.findResourceCenterById(id);
    }
    @Transactional
    public void saveResourceCenter(ResourceCenter resourceCenter) {
        resourceCenter.setId(sequenceId.nextId());
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        resourceCenter.setUploadTime(today);
        resourceCenterDao.saveResourceCenter(resourceCenter);
    }
    @Transactional(readOnly = true)
    public List<ResourceCenter> findResourceCenterListByCondition(ResourceCenter resourceCenter) {
        return resourceCenterDao.findResourceCenterListByCondition(resourceCenter);
    }
    @Transactional(readOnly = true)
    public ResourceCenter findOneResourceCenterByCondition(ResourceCenter resourceCenter) {
        return resourceCenterDao.findOneResourceCenterByCondition(resourceCenter);
    }
    @Transactional(readOnly = true)
    public long findResourceCenterCountByCondition(ResourceCenter resourceCenter) {
        return resourceCenterDao.findResourceCenterCountByCondition(resourceCenter);
    }
    @Transactional
    public void updateResourceCenter(ResourceCenter resourceCenter) {
        resourceCenterDao.updateResourceCenter(resourceCenter);
    }
    @Transactional
    public void deleteResourceCenter(String id) {
        resourceCenterDao.deleteResourceCenter(id);
    }
    @Transactional
    public void deleteResourceCenterByCondition(ResourceCenter resourceCenter) {
        resourceCenterDao.deleteResourceCenterByCondition(resourceCenter);
    }
    @Transactional
    public void batchSaveResourceCenter(List<ResourceCenter> resourceCenters){
        resourceCenters.forEach(resourceCenter -> resourceCenter.setId(sequenceId.nextId()));
        resourceCenterDao.batchSaveResourceCenter(resourceCenters);
    }

    public List<TeacherCourse> findTeacherCourseListBySchoolId(TeacherCourse teacherCourse) {
        return resourceCenterDao.findTeacherCourseListBySchoolId(teacherCourse);
    }

    public long findTeacherCourseCountBySchoolId(TeacherCourse teacherCourse) {
        return resourceCenterDao.findTeacherCourseCountBySchoolId(teacherCourse);
    }

    public List<ResourceCenter> findResourceCentersForH5ByCondition(ResourceCenter resourceCenter) {
        return resourceCenterDao.findResourceCentersForH5ByCondition(resourceCenter);
    }

    public long findResourceCenterCountForH5ByCondition(ResourceCenter resourceCenter) {
        return resourceCenterDao.findResourceCenterCountForH5ByCondition(resourceCenter);
    }
}
