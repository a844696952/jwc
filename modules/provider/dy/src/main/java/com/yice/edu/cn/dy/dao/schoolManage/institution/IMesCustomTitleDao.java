package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesCustomTitleDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesCustomTitle> findMesCustomTitleListByCondition(MesCustomTitle mesCustomTitle);

    List<MesCustomTitle> findMesCustomTitleListByFirstRank(@Param("id") String id);

    List<MesCustomTitle> findMesCustomTitleListByTotalRank(@Param("id") String id);

    List<MesCustomTitle> findMesCustomTitleListByTotalReach(@Param("id") String id);

    List<MesCustomTitle> findMesCustomTitleListByFirstReach(@Param("id") String id);

    long findMesCustomTitleCountByCondition(MesCustomTitle mesCustomTitle);

    MesCustomTitle findOneMesCustomTitleByCondition(MesCustomTitle mesCustomTitle);

    MesCustomTitle findMesCustomTitleById(@Param("id") String id);

    void saveMesCustomTitle(MesCustomTitle mesCustomTitle);

    void updateMesCustomTitle(MesCustomTitle mesCustomTitle);

    void deleteMesCustomTitle(@Param("id") String id);

    void deleteMesCustomTitleByCondition(MesCustomTitle mesCustomTitle);

    void batchSaveMesCustomTitle(List<MesCustomTitle> mesCustomTitles);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    SchoolYear findCurrentSchoolYear(@Param("schoolId")String schoolId);

    List<MesCustomTitle> selectByTotalAndTimeStatusId(String timeStatusId);

    List<MesCustomTitle> selectByFirstInstitutionAndTimeStatusId(String timeStatusId);
}
