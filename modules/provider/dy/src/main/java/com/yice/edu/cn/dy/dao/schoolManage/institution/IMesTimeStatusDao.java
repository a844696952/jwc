package com.yice.edu.cn.dy.dao.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesTimeStatusDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesTimeStatus> findMesTimeStatusListByCondition(MesTimeStatus mesTimeStatus);

    List<MesTimeStatus> findAllUsingMesTimeStatus(MesTimeStatus mesTimeStatus);

    long findMesTimeStatusCountByCondition(MesTimeStatus mesTimeStatus);

    MesTimeStatus findOneMesTimeStatusByCondition(MesTimeStatus mesTimeStatus);

    MesTimeStatus findMesTimeStatusById(@Param("id") String id);

    void saveMesTimeStatus(MesTimeStatus mesTimeStatus);

    void updateMesTimeStatus(MesTimeStatus mesTimeStatus);

    void deleteMesTimeStatus(@Param("id") String id);

    void deleteMesTimeStatusByCondition(MesTimeStatus mesTimeStatus);

    void batchSaveMesTimeStatus(List<MesTimeStatus> mesTimeStatuss);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    MesTimeStatus findMesTimeStatusListWhereCreateTimeMax(MesTimeStatus mesTimeStatus);

    MesTimeStatus selectLatestBySchoolId(String schoolId);

    List<MesTimeStatus> selectMesTimeStatusByCondition(@Param("beginTime") String beginTime,@Param("endTime") String endTime, @Param("schoolId") String schoolId);

    MesTimeStatus findMesTimeStatusBySchoolId(@Param("schoolId") String schoolId);
}
