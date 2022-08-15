package com.yice.edu.cn.dm.dao.schoolActive;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmActivityInfoDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo);

    List<DmActivityInfo> findDmActivityInfoByActivityId(DmActivityInfo dmActivityInfo);

    long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo);

    DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);

    List<DmActivityInfo> findActiveListById(DmActivityInfo dmActivityInfo);

    List<String> findHeadTeacherListByGradeId(DmActivityInfo dmActivityInfo);

    List<String> findParentByGradeId(DmActivityInfo dmActivityInfo);

    List<JwClasses> findClassInfoByGradeId(JwClasses jwClasses);

    DmActivityInfo findDmActivityInfoById(@Param("id") String id);

    void saveDmActivityInfo(DmActivityInfo dmActivityInfo);

    void updateDmActivityInfo(DmActivityInfo dmActivityInfo);

    void deleteDmActivityInfo(@Param("id") String id);

    void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo);

    void batchSaveDmActivityInfo(List<DmActivityInfo> dmActivityInfos);

    List<Dd> findGradesByGradeIds(List<String> gradeIds);

    List<DmActivityInfo> findDmActivityInfosByCondition(DmActivityInfo dmActivityInfo);

    List<JwClasses> findSiginUpGrade(List<String> classesIdList);

    void deleteDmActivityInfoByActivityId(@Param("activityId") String activityId);

    DmActivityInfo findOldDmActivityInfoDao(String activityId);

    DmActivityInfo findOneDmActivityInfoByActivityId(String activityId);

    List<String> findTeacherGradeIdsByTeacherId(@Param("teacherId") String teacherId);

    List<DmActivityInfo> selectDmActivityInfosByGradeIdsAndSchoolId(@Param("gradeIds") List<String> gradeIds, @Param("dmActivityInfo") DmActivityInfo dmActivityInfo, @Param("pager")Pager pager);

    DmActivityInfo findDmActivityInfoNoItemByActivityId(@Param("activityId") String activityId);

    List<String> selectGradeIdsByActivityId(@Param("activityId")String activityId);

    List<String> selectClassesIdsByGradeIds(@Param("gradeIds") List<String> newGradeIds);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
