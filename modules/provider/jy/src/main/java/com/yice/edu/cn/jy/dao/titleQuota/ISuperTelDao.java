package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ISuperTelDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<SuperTel> findSuperTelListByCondition(SuperTel superTel);

    long findSuperTelCountByCondition(SuperTel superTel);

    SuperTel findOneSuperTelByCondition(SuperTel superTel);

    SuperTel findSuperTelById(@Param("id") String id);

    void saveSuperTel(SuperTel superTel);

    void updateSuperTel(SuperTel superTel);

    void updateSuperTelForAll(SuperTel superTel);

    void deleteSuperTel(@Param("id") String id);

    void deleteSuperTelByCondition(SuperTel superTel);

    void batchSaveSuperTel(List<SuperTel> superTels);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
