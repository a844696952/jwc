package com.yice.edu.cn.dm.dao.dmMobileRedBanner;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmCountDownManageDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmCountDownManage> findDmCountDownManageListByCondition(DmCountDownManage dmCountDownManage);

    long findDmCountDownManageCountByCondition(DmCountDownManage dmCountDownManage);

    DmCountDownManage findOneDmCountDownManageByCondition(DmCountDownManage dmCountDownManage);

    DmCountDownManage findDmCountDownManageById(@Param("id") String id);

    void saveDmCountDownManage(DmCountDownManage dmCountDownManage);

    void updateDmCountDownManage(DmCountDownManage dmCountDownManage);

    void updateDmCountDownManageForAll(DmCountDownManage dmCountDownManage);

    void deleteDmCountDownManage(@Param("id") String id);

    void deleteDmCountDownManageByCondition(DmCountDownManage dmCountDownManage);

    void batchSaveDmCountDownManage(List<DmCountDownManage> dmCountDownManages);

    void updateDmCountDownManageStatus(DmCountDownManage dmCountDownManage);

    void updateDmCountDownManageStatusAll(DmCountDownManage dmCountDownManage);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
