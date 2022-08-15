package com.yice.edu.cn.jy.dao.resourceCenter;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IResourceCenterDao {
    List<ResourceCenter> findResourceCenterListByCondition(ResourceCenter resourceCenter);

    long findResourceCenterCountByCondition(ResourceCenter resourceCenter);

    ResourceCenter findOneResourceCenterByCondition(ResourceCenter resourceCenter);

    ResourceCenter findResourceCenterById(@Param("id") String id);

    void saveResourceCenter(ResourceCenter resourceCenter);

    void updateResourceCenter(ResourceCenter resourceCenter);

    void deleteResourceCenter(@Param("id") String id);

    void deleteResourceCenterByCondition(ResourceCenter resourceCenter);

    void batchSaveResourceCenter(List<ResourceCenter> resourceCenters);

    List<TeacherCourse> findTeacherCourseListBySchoolId(TeacherCourse teacherCourse);

    long findTeacherCourseCountBySchoolId(TeacherCourse teacherCourse);

    List<ResourceCenter> findResourceCentersForH5ByCondition(ResourceCenter resourceCenter);

    long findResourceCenterCountForH5ByCondition(ResourceCenter resourceCenter);
}
