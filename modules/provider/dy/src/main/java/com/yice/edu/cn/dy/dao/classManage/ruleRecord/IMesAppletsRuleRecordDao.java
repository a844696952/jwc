package com.yice.edu.cn.dy.dao.classManage.ruleRecord;

import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadres;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesAppletsRuleRecordDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAppletsRuleRecord> findMesAppletsRuleRecordListByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);

    long findMesAppletsRuleRecordCountByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);

    MesAppletsRuleRecord findOneMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);

    MesAppletsRuleRecord findMesAppletsRuleRecordById(@Param("id") String id);

    void saveMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);

    void updateMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);

    void deleteMesAppletsRuleRecord(@Param("id") String id);

    void deleteMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);

    void batchSaveMesAppletsRuleRecord(List<MesAppletsRuleRecord> mesAppletsRuleRecords);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<MesAppletsRuleRecord> findCommentatorByTidAndCid(MesAppletsRuleRecord mesAppletsRuleRecord);

    MesAppletsRuleRecord findStudentBySid(String operatorId);

    List<Student> findStudentByParentId(@Param("parentId") String parentId);

    List<MesAppletsRuleRecord> findClassRuleRecordByRange(MesAppletsRuleRecord mesAppletsRuleRecord);

    List<MesAppletsRuleRecord> findClassRuleRecordList(MesAppletsRuleRecord mesAppletsRuleRecord);

    MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord);

    List<MesAppletsRuleRecord> findTeachingClassByTid(@Param("teacherId") String teacherId, @Param("schoolId") String schoolId);

    List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);

    List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecords);

    Student findStudentById(@Param("id") String id);

    List<MesStudentRecordVo> findMesStudentRecordVoListByCid(MesAppletsRuleRecord mesAppletsRuleRecord);

    List<Parent> findJwParentByStudentId(@Param("studentId") String studentId);

    List<Teacher> findClassTeacherTelByClassesId(@Param("classesId") String classesId);

    SchoolYear findCurrentTermTime(@Param("schoolId") String schoolId);

    List<JwClaCadres> findJwClaCadresBySid(@Param("studentId") String studentId);

    List<String> findParentOpenIdsBySid(String studentId);

    List<MesStudentRecordVo> findTodayRecordOidAndSid(@Param("searchBeginTime") String searchBeginTime,@Param("searchEndTime") String searchEndTime);

    List<String> findClassIdByWeeks(@Param("searchBeginTime") String searchBeginTime, @Param("searchEndTime") String searchEndTime);

    List<MesStudentRecordVo> findMesStudentRecordVoBySid(String studentId);

    TeacherPost findTeacherPostByTidAndCid(@Param("teacherId") String teacherId,@Param("classId") String classId);

    long findStudentCount(@Param("studentId") String studentId);
}
