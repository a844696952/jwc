package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesCommonConfigDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesCommonConfig> findMesCommonConfigListByCondition(MesCommonConfig mesCommonConfig);

    long findMesCommonConfigCountByCondition(MesCommonConfig mesCommonConfig);

    MesCommonConfig findOneMesCommonConfigByCondition(MesCommonConfig mesCommonConfig);

    MesCommonConfig findMesCommonConfigById(@Param("id") String id);

    void saveMesCommonConfig(MesCommonConfig mesCommonConfig);

    void updateMesCommonConfig(MesCommonConfig mesCommonConfig);

    void deleteMesCommonConfig(@Param("id") String id);

    void deleteMesCommonConfigByCondition(MesCommonConfig mesCommonConfig);

    void batchSaveMesCommonConfig(List<MesCommonConfig> mesCommonConfigs);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    MesCommonConfig findMesCommonConfigByTimeStatusId(@Param("id") String id);
}
