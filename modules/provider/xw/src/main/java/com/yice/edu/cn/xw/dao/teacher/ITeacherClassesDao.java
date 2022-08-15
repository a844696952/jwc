package com.yice.edu.cn.xw.dao.teacher;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITeacherClassesDao {


    /**
     * 查询班主任信息
     * 应该是传递teacherPost对象的 因为不想其他模块受影像 就先不错替换
     * @param teacherClasses
     * @return
     */
    @Select("SELECT t.* FROM jw_teacher t " +
            "INNER JOIN jw_teacher_post tp ON  t.`id` = tp.`teacher_id` " +
            "WHERE tp.`dd_id`='74' AND tp.`class_id` = #{classesId} limit 1")
    @ResultType(value = Teacher.class)
    Teacher findHeadmasterByClasses(TeacherClasses teacherClasses);

    @Select("SELECT DISTINCT t.id,t.`img_url`,t.`tel`,t.`name`,tc.course_name,GROUP_CONCAT(tp.`name` ORDER BY tp.dd_id) post FROM jw_teacher t " +
            "LEFT JOIN ( " +
            "SELECT tc.`teacher_id`,GROUP_CONCAT(tcc.`alias` ORDER BY tcc.`id`) course_name FROM jw_teacher_classes tc " +
            "INNER JOIN jw_course tcc ON tc.`course_id` = tcc.`id` and tcc.`school_id`=tc.`school_id`" +
            "WHERE tc.`classes_id` = #{classesId} " +
            "GROUP BY tc.`teacher_id` ) tc ON tc.`teacher_id` = t.`id` " +
            "LEFT JOIN (" +
            "SELECT * FROM jw_teacher_post tp " +
            "WHERE (tp.`class_id`=#{classesId} OR tp.`class_id` IS NULL) AND tp.`school_id` = #{schoolId} ORDER BY tp.`dd_id` " +
            ") tp ON tp.`teacher_id` = t.`id` " +
            "WHERE EXISTS (SELECT tp.`teacher_id` FROM jw_teacher_post tp WHERE tp.`teacher_id`=t.`id` AND tp.`class_id` = #{classesId}) " +
            "OR EXISTS (SELECT tc.`teacher_id` FROM jw_teacher_classes tc WHERE  tc.`teacher_id`=t.`id` AND tc.`classes_id` = #{classesId}) " +
            "AND t.person_type = 1 " +
            "GROUP BY t.`id`")
    @ResultType(value = Teacher4Classes.class)
    List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses);

    @Select("SELECT DISTINCT tc.`course_id`,tcc.alias as course_name FROM jw_teacher_classes tc " +
            "INNER JOIN jw_course tcc ON tc.`course_id` = tcc.`id` and tcc.`school_id`=tc.`school_id`" +
            "WHERE tc.`classes_id` = #{classesId} AND tc.`school_id` = #{schoolId} ")
    @ResultType(value = Map.class)
    List<Map<String,String>> findCourseTeacherByClass(TeacherClasses teacherClasses);

    @Select("SELECT tc.`classes_id`,c.`number` AS `classes_name`,c.`enroll_year`,c.`grade_id`,c.`grade_name` FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes c ON c.`id` = tc.`classes_id`  WHERE tc.`teacher_id` = #{teacherId}")
    @ResultType(value = TeacherClasses.class)
    List<TeacherClasses> findTeacherClassesByTeacher(@Param("teacherId") String teacherId);

    @Select("SELECT DISTINCT c.`grade_id`,c.`grade_name` FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes c ON c.`id` = tc.`classes_id` WHERE tc.`teacher_id` = #{teacherId} ORDER BY c.`grade_id`")
    @ResultType(value = TeacherClasses.class)
    List<TeacherClasses> findGradeByTeacher(@Param("teacherId") String teacherId);

    @Select("SELECT DISTINCT c.`id` AS courseId,c.`alias` AS courseName,c.`name` AS subjectName FROM jw_course c " +
            "INNER JOIN jw_teacher_classes tc ON tc.`course_id` = c.`id` AND tc.`school_id`=c.`school_id`" +
            "INNER JOIN jw_classes jc ON jc.`id` = tc.`classes_id`" +
            "INNER JOIN ad_dd dd ON dd.`id` = c.`id`" +
            "WHERE c.exam = 1 AND dd.`ext1` = 1 AND tc.`teacher_id` = #{teacherId} AND  jc.`grade_id` = #{gradeId} ")
    @ResultType(value = TeacherClassesCourse.class)
    List<TeacherClassesCourse> findCourseByTeacherGrade(TeacherClasses teacherClasses);

    @Select("SELECT DISTINCT c.`id` AS courseId,c.`alias` AS courseName,c.`name` AS subjectName FROM jw_course c " +
            "INNER JOIN jw_teacher_classes tc ON tc.`course_id` = c.`id` AND tc.`school_id`=c.`school_id`" +
            "INNER JOIN jw_classes jc ON jc.`id` = tc.`classes_id`" +
            "WHERE c.exam = 1  AND tc.`teacher_id` = #{teacherId} AND  jc.`grade_id` = #{gradeId} ")
    @ResultType(value = TeacherClassesCourse.class)
    List<TeacherClassesCourse> findCourseByTeacherGrade2(TeacherClasses teacherClasses);

    @Select("SELECT DISTINCT tc.`classes_id`,a.`number` AS classes_name,a.`enroll_year`,count(b.id) stutotal FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes a on a.`id`=tc.`classes_id` " +
            "LEFT JOIN jw_student b on b.`classes_id`=a.`id` " +
            "WHERE tc.`teacher_id` = #{teacherId} AND tc.`course_id` = #{courseId} AND a.`grade_id`= #{gradeId}    GROUP BY a.`number`,a.`enroll_year`  order by  CAST(a.`number` as SIGNED)")
    @ResultType(value = TeacherClasses.class)
    List<TeacherClasses> findClassesByTeacherCourse(@Param("teacherId") String teacherId, @Param("courseId") String courseId, @Param("gradeId") String gradeId);
    @Select("SELECT DISTINCT tcc.`alias` FROM `jw_course` tcc " +
            "INNER JOIN jw_teacher_classes tc ON tc.`course_id` = tcc.`id` AND tc.`school_id`=tcc.`school_id`" +
            "WHERE tc.`teacher_id` = #{teacherId}")
    @ResultType(value = String.class)
    List<String> findCourseNameList4Teacher(@Param("teacherId") String teacherId);

    /**
    * 作业分析
    */
    @Select("SELECT DISTINCT a.`grade_name`,a.`grade_id`,CONCAT(a.number,'') as `classes_name`,tc.`classes_id`,'任课老师' post " +
            "FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes a on a.`id`=tc.`classes_id` " +
            "WHERE tc.`teacher_id` = #{teacherId}  GROUP BY tc.`id` " +
            "UNION ALL " +
            "SELECT a.grade_name,a.grade_id,CONCAT(a.number,'') as classes_name,tp.class_id classes_id,'班主任' post FROM jw_teacher_post tp  " +
            "INNER JOIN jw_classes a on a.`id`=tp.`classes_id` " +
            "WHERE tp.`teacher_id` = #{teacherId} AND tp.`dd_id` = '74'")

    @ResultType(Map.class)
    List<Map<String,String>> findTeacherClassPostCourseListHomeworkAnalyse(@Param("teacherId") String teacherId);

    /**
     * 根据班级id查出本班级有多少门课程
     */
    @Select("SELECT DISTINCT t.`id`,tc.`classes_id`,tc.`id` tc_id,t.`name`"+
            " FROM jw_teacher_classes tc INNER JOIN jw_teacher t "+
            "ON t.`id` = tc.`teacher_id` WHERE tc.`classes_id`=#{classesId} GROUP BY t.`id`")
    @ResultType(value = TeacherHomeworkAnalyseClasses.class)
    List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(@Param("classesId") String classesId);

    @Select("SELECT DISTINCT tc.id, c.grade_id,c.grade_name ,tc.classes_id ,CONCAT(c.number,'班') classes_name FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes c ON c.`id` = tc.`classes_id` where tc.teacher_id =#{teacherId}")
    @ResultType(value = Map.class)
    List<Map<String,String>> findTeacherClassesHomeworkAnalyseByTeacherId(@Param("teacherId") String teacherId);


    /**
     * 通过教师id查询教师所教的班级信息（含年级）
     * @param teacherId
     * @return
     */
    @Select("SELECT DISTINCT c.* FROM jw_classes c " +
            "INNER JOIN jw_teacher_classes tc ON tc.`classes_id` = c.`id` " +
            "WHERE tc.`teacher_id` = #{teacherId} " +
            "ORDER BY c.grade_id,c.number")
    @ResultType(JwClasses.class)
    List<JwClasses> findTeacherClassByTeacherId(@Param("teacherId") String teacherId);


    /**
     * 通过教师id查询教师所教的课程列表包含对应班级年级信息
     */
    @Select("SELECT DISTINCT tc.`classes_id` AS classId,c.`grade_id` AS gradeId,c.`grade_name` AS gradeName,CONCAT(c.`number`,'') AS className,tcc.`alias` AS courseName,tc.`course_id` AS courseId " +
            "FROM jw_teacher_classes tc " +
            "INNER JOIN jw_classes c ON c.`id` = tc.`classes_id` " +
            "INNER JOIN jw_course tcc ON tcc.`id` = tc.`course_id` AND tc.`school_id` = tcc.`school_id` " +
            "WHERE tc.`teacher_id` = #{teacherId} ORDER BY c.`number`")
    @ResultType(Map.class)
    List<Map<String,String>> findTeacherClassCourseByTeacherId(@Param("teacherId") String teacherId);

    List<TeacherClasses>  findTeacherClassesByTeacherId(String id);

    /**
     * 通过班级id和课程id获取教师信息
     * @param classId
     * @param courseId
     * @return
     */
    @Select("SELECT DISTINCT t.id,t.`name` FROM jw_teacher t " +
            "INNER JOIN jw_teacher_classes tc ON t.`id` = tc.`teacher_id` " +
            "WHERE tc.`classes_id` = #{classId} AND tc.`course_id`=#{courseId}")
    @ResultType(Teacher.class)
    List<Teacher> findTeacherByClassAndCourse(@Param("classId") String classId, @Param("courseId") String courseId);

    List<TeacherClasses> findTeacherClasses(@Param("id") String id);

    /**
     * 学校教师进行汇总
     * 按课程信息进行统计数据
     * 课程信息关联到字典表取字典课程名称
     * @param schoolId
     * @return
     */
    @Select("SELECT DISTINCT c.`id`,c.`alias` AS name,COUNT(DISTINCT tc.`teacher_id`) total FROM jw_teacher_classes tc " +
            "INNER JOIN jw_course c ON c.`id` = tc.`course_id` AND tc.`school_id`=c.`school_id`" +
            "WHERE tc.`school_id` = #{schoolId} " +
            "GROUP BY c.`id`,c.`alias` ORDER BY total DESC")
    List<Map<String,Object>> findCourseTeacherSummaryBySchool4Index(@Param("schoolId") String schoolId);
    List<Teacher> findTeachersByNameId(@Param("nameId") String nameId, @Param("schoolId") String schoolId);
    void deleteTeacherClassesByMutil(@Param("teacherId") String teacherId, @Param("classIds") String[] classIds);
}
