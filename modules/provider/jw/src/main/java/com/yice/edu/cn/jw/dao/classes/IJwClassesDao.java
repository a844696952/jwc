package com.yice.edu.cn.jw.dao.classes;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IJwClassesDao {
    List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses);

    long findJwClassesCountByCondition(JwClasses jwClasses);

    JwClasses findJwClassesById(String id);

    void saveJwClasses(JwClasses jwClasses);

    void updateJwClasses(JwClasses jwClasses);

    void deleteJwClasses(String id);

    void batchSaveJwClasses(List<JwClasses> jwClassess);

    JwClasses findOneJwClassesByCondition(JwClasses jwClasses);
    /**
     * 查询最大班号
     * @param jwClasses
     * @return
     */
    Integer findMaxClassesNoByCondition(JwClasses jwClasses);
    
    /**
     * 查询班级信息
     * @param jwClasses
     * @return
     */
    List<JwClasses> findJwClassesListByConditionAndRelate(JwClasses jwClasses);
    
    /**
     * 删除班级后，修改之后班级号
     * @param number 删除的班级号
     * @param gradeId 年级id
     * @param schoolId 学校id
     */
    void updateJwClassesNo(String number ,String gradeId,String schoolId);
    
    /**
     * 修改班级的毕业年份
     * @param spaceId
     */
    void updateJwClassesEnrollYear(String classesId);

    /**
     *
     * @param schoolId
     * @return 返回树形结构的数据
     */
    List<JwClasses> getClassTree(@Param("schoolId")String schoolId,@Param("teacherId")String teacherId);

    /**
     * @param jwClasses
     * @return 返回年段对应班数占比
     */

    List<JwClasses> getClassesNumber(JwClasses jwClasses);
    
    /**
     * 根据学校id删除学校的顶级年级班级
     * @param jwClasses
     */
    void deleteJTopClazz(@Param("clazzIdList") List<String> clazzIdList);
    
    /**
     * 普通班级升级(修改grade_id,而不同步grade_name)
     * @param jwClasses
     */
    void riseGenerateClazz(JwClasses jwClasses);
    
    void updateByGradeIdAndSchoolId(JwClasses jwClasses);

    List<JwClasses> findJwClassessByConditionForExam(JwClasses jwClasses);

    List<JwClasses> findTeacherClassesByTeacherId(@Param("teacherId")String teacherId,@Param("activityId")String activityId);

    List<JwClasses> findClassListByJwClassesKong(JwClasses jwClasses);
}
