package com.yice.edu.cn.jw.dao.teacher;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ITeacherPostDao {
    List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost);

    long findTeacherPostCountByCondition(TeacherPost teacherPost);

    TeacherPost findOneTeacherPostByCondition(TeacherPost teacherPost);

    TeacherPost findTeacherPostById(@Param("id") String id);

    void saveTeacherPost(TeacherPost teacherPost);

    void updateTeacherPost(TeacherPost teacherPost);

    void deleteTeacherPost(@Param("id") String id);

    void deleteTeacherPostByCondition(TeacherPost teacherPost);

    void batchSaveTeacherPost(List<TeacherPost> teacherPosts);
    /**
     * 通过职务获取对应的老师列表
     * @param teacherPost
     * @return
     */
    List<Teacher> findTeachersByPost(TeacherPost teacherPost);
    /**
     * 通过职务获取对应的老师
     * @param teacherPost
     * @return TeacherPost
     */
    List<TeacherPost> findTeacherListByPost(TeacherPost teacherPost);

    @Select("SELECT DISTINCT c.* FROM ( " +
            "SELECT c.* FROM jw_classes c  " +
            "INNER JOIN jw_teacher_post tp ON c.`id`=tp.`class_id`  " +
            "WHERE tp.`dd_id` = '74' AND tp.`teacher_id` = #{teacherId}  " +
            "UNION ALL  " +
            "SELECT c.* FROM jw_classes c  " +
            "INNER JOIN jw_teacher_classes tc ON tc.`classes_id` = c.`id` " +
            "WHERE tc.`teacher_id` = #{teacherId} " +
            ") c ORDER BY c.grade_id,c.number")
    @ResultType(JwClasses.class)
    List<JwClasses> findClass4AssociationByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 获取学校各个年段 年段长
     * @param schoolId
     * @return
     */
    List<TeacherPost> findTeachers4Grade(@Param("schoolId") String schoolId);

    /**
     * 获取学校 各班班主任
     * @param teacherPost
     * @return
     */
    List<TeacherPost> findTeachers4Class(TeacherPost teacherPost);
}
