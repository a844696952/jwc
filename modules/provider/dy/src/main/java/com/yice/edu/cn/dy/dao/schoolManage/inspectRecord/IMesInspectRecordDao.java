package com.yice.edu.cn.dy.dao.schoolManage.inspectRecord;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesInstitutionStudent;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesInspectRecordDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord);

    long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord);

    MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord);

    MesInspectRecord findMesInspectRecordById(@Param("id") String id);

    void saveMesInspectRecord(MesInspectRecord mesInspectRecord);

    void updateMesInspectRecord(MesInspectRecord mesInspectRecord);

    void deleteMesInspectRecord(@Param("id") String id);

    void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord);

    void batchSaveMesInspectRecord(List<MesInspectRecord> mesInspectRecords);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<MesInstitution> findMesInstitutionTlListByParentId(MesUserAuthInstitution mesUserAuthInstitution);

    List<MesInspectRecord> findMirAndClassId(MirQuery query);

    long findMirAndClassIdCount(MirQuery query);

    Integer findMesInstitution(MesInstitutionStudent mesInstitutionStudent);

    Integer findMesInstitutionScore(MesInstitutionStudent mesInstitutionStudent);

    MesInspectRecord selectMesInspectRecordById(String auditId);

    void updateMesInspectRecordScoreToZero(String auditId);

    List<MesInspectRecord> checkMesInspectRecord(MesInspectRecord mesInspectRecord);

    List<MesInstitution> findMesInstitutionOlList(String userId);

    void updateMesInspectRecordScoreToZeroByIds(List<String> recordIds);

    List<MesInspectRecord> findHistoryMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord);

    List<String> findTeacherPostBySid(String Sid);

    List<String> findTeacherPostByCid(String Cid);

    List<MesInspectRecord> findTlInstitutionStatistics(@Param("startDate") String startDate, @Param("schoolId") String schoolId);

    List<MesInspectRecord> findRadarStatistics(@Param("startDate") String startDate, @Param("schoolId") String schoolId);

    MesInspectRecord findMesInspectRecordOneById(String id);

    SchoolYear findSchoolYearByCondition(SchoolYear schoolYear);

    SchoolYear findSchoolYearByNowDate(SchoolYear schoolYear);

    List<MesInspectRecord> findMesInspectRecordByTime(MesInspectRecord mesInspectRecord);

    Integer findMesInspectRecordCountByTime(MesInspectRecord mesInspectRecord);

    List<MesInspectRecord> findAllOlInstitution(String schoolId);

    List<MesInspectRecord> findMesInspectRecordByClassId(MesInspectRecord mesInspectRecord);

    long findMesInspectRecordCountByClassId(MesInspectRecord mesInspectRecord);

    String findAuditIdByInspectRecordId(@Param("recordId")String recordId);

    long judgeTeacher(Teacher teacher);

    JwClasses selectClassName(String classId);

    List<MesInspectRecord> findMesInspectRecordListByScoreTypeIsOne(MesInspectRecord mesInspectRecord);

    List<MesInspectRecord> findMesInspectRecordListByScoreTypeIsZero(MesInspectRecord mesInspectRecord);

    String findSchoolIdBystudentId(@Param("studentId") String studentId);
}
