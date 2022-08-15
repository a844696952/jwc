package com.yice.edu.cn.jw.dao.jwCourse;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface JwCourseDao {
    List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse);

    long findJwCourseCountByCondition(JwCourse jwCourse);

    JwCourse findOneJwCourseByCondition(JwCourse jwCourse);

    JwCourse findJwCourseById(@Param("id") String id);

    void saveJwCourse(JwCourse jwCourse);

    void updateJwCourse(JwCourse jwCourse);

    void updateJwCourseForAll(JwCourse jwCourse);

    void deleteJwCourse(@Param("id") String id);

    void deleteJwCourseByCondition(JwCourse jwCourse);

    void batchSaveJwCourse(List<JwCourse> jwCourses);

    //获得数据字典的高中年级
    List<Dd> queryAllByTypeIdGrade();

    //获得数据字典的课程名称
    List<Dd> queryAllByTypeIdCourse();

    //获得数据字典的课程类型
    List<Dd> queryAllByTypeIdType();

    //获得数据字典的场地类型
    List<Dd> queryAllByTypeIdBuilding();

    //<!--通过id获得数组字典的一行记录-->
    Dd queryOneById(String id);

    //判断重复
    long distinctJwCourse(JwCourse jwCourse);



    //原生成的查询
    List<JwCourse> findJwCourseListByConditionGai(JwCourse jwCourse);

    //web端页面使用
    List<JwCourse> findJwCourseListByConditionKong(JwCourse jwCourse);
    long findJwCourseCountByConditionKong(JwCourse jwCourse);
    List<Teacher> findTeachersByNameId(@Param("nameId") String nameId, @Param("schoolId") String schoolId);

    void updateJwCourseSchoolId(JwCourse jwCourse);

    List<JwCourse> findSchoolEaxmCourseList(@Param("schoolId") String schoolId);
}
