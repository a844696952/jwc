package com.yice.edu.cn.jy.dao.courseware;

import com.yice.edu.cn.common.pojo.jy.courseware.CourseRes;
import com.yice.edu.cn.common.pojo.jy.courseware.CourseWare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICourseResDao {
    List<CourseRes> findCourseResListByCondition(CourseRes courseRes);

    long findCourseResCountByCondition(CourseRes courseRes);

    CourseRes findOneCourseResByCondition(CourseRes courseRes);

    CourseRes findCourseResById(@Param("id") String id);

    void saveCourseRes(CourseRes courseRes);

    void updateCourseRes(CourseRes courseRes);

    void deleteCourseRes(@Param("id") String id);

    void deleteCourseResByCondition(CourseRes courseRes);

    void batchSaveCourseRes(List<CourseRes> courseRess);

    /**
     * 批量移动资源至某个目录
     * @param courseRes
     */
    void batchUpdateCourseRes(CourseRes courseRes);

    /**
     * 根据ids批量删除资源
     * @param ids
     */
    void deletes(List<String> ids);

    /**
     * 删除目录之后，该目录下所有的资源移动到未分类（将目录层级lv1，lv2，lv3，lv4删除）
     */
    void mv0(CourseRes courseRes);

    void remark(CourseRes courseRes);

    CourseRes findLastRes(CourseRes courseRes);


    CourseRes findRecentlyCourseWare(CourseWare courseWare);


}
