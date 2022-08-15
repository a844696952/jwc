package com.yice.edu.cn.xw.dao.teacher;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITeacherDao {
    List<Teacher> findTeacherListSchoolId(Teacher teacher);
    List<Teacher> findTeacherListByCondition(Teacher teacher);
    Teacher findOneTeacherByCondition(Teacher teacher);
    long findTeacherCountByCondition(Teacher teacher);
    long findTeacherCountByCondition4Like(Teacher teacher);

    Teacher findTeacherById(String id);

    Teacher teacherLoginByQRCode(@Param("tel") String tel);

    void saveTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    long findRepeatByCondition(Teacher teacher);

    void deleteTeacher(String id);

    void deleteTeacherByCondition(Teacher teacher);

    void batchSaveTeacher(List<Teacher> teachers);

    List<String> findCheckedRolesByTeacherId(@Param("id") String id);

    List<Perm> findFuncPermsByTeacherId(@Param("id") String id);

    //用关联查询
    List<Teacher> findTeacherCorrespondencesList(Teacher teacher);

    List<Teacher> findTeacherListInfoByCondition(Teacher teacher);

    Teacher teacherLogin(Teacher teacher);

    List<Teacher> findTeachers4Yed(Teacher teacher);

    long findTeachersCount4Yed(Teacher teacher);
    @Select("SELECT t.* FROM jw_teacher t " +
            "WHERE t.`account` IS NOT NULL AND t.`status` = #{status} AND t.`school_id` = #{schoolId} LIMIT 1")
    @ResultType(Teacher.class)
    Teacher findAdminBySchool(Teacher teacher);

    List<Teacher> findTeacherListByConditionRegister(Teacher teacher);

    void batchUpdateTeacherRegisterStatus(@Param("ids") List<String> ids, @Param("schoolId") String schoolId);

    List<Teacher> findStudentTeachers(Teacher teacher);

    List<Perm> findDmssFuncPermsByTeacherId(@Param("id") String id);
    //查找该教师当班主任的班级
    List<Teacher> findClassTeacherIsDirector(Teacher teacher);

    @Update("UPDATE jw_teacher SET open_id = NULL WHERE open_id = #{openId}")
    void unBindOpenId2Teacher(@Param("openId") String openId);
    @Update("UPDATE jw_teacher SET school_name = #{name} WHERE school_id = #{id}")
    void updateSchoolName(School school);

    /**
     * 查询教师/职工 同时关联部门和认证头像
     * 列表
     * @param teacher
     * @return
     */
    List<Teacher> findTeacherImgListByCondition(Teacher teacher);
    /**
     * 查询教师/职工 同时关联部门和认证头像
     * 数量
     * @param teacher
     * @return
     */
    Long findTeacherImgCountByCondition(Teacher teacher);

    /**
     * 学校教师进行汇总
     * 得出总数，男老师数量，女老师数量
     * @param schoolId
     * @return
     */
    @Select("SELECT COUNT(1) total,COUNT(CASE WHEN t.`sex`='4' THEN 1 END) nan,COUNT(CASE WHEN t.`sex`='5' THEN 1 END) nv  FROM jw_teacher t " +
            "WHERE t.`status` = '40' AND t.`person_type` = 1 AND t.`school_id` = #{schoolId}")
    Map<String,Long> findTeacherSummaryBySchool4Index(@Param("schoolId") String schoolId);


    /**
     * 查询教师拥有的班牌权限
     * 列表
     * @param teacher
     * @return
     */
    List<Teacher> findTeacherManagerById(Teacher teacher);

    /**
     * 查询教师列表
     * 内联班级信息
     * @param teacherVo
     * @return
     */
    List<Teacher> findTeacherListInClassByCondition(TeacherVo teacherVo);
    /**
     * 查询教师列表数量
     * 内联班级信息
     * @param teacherVo
     * @return
     */
    long findTeacherCountInClassByCondition(TeacherVo teacherVo);
}
