package com.yice.edu.cn.dm.dao.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmCodeResourceDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmCodeResource> findDmCodeResourceListByCondition(DmCodeResource dmCodeResource);

    long findDmCodeResourceCountByCondition(DmCodeResource dmCodeResource);

    DmCodeResource findOneDmCodeResourceByCondition(DmCodeResource dmCodeResource);

    DmCodeResource findDmCodeResourceById(@Param("id") String id);

    void saveDmCodeResource(DmCodeResource dmCodeResource);

    void updateDmCodeResource(DmCodeResource dmCodeResource);

    void deleteDmCodeResource(@Param("id") String id);

    void deleteDmCodeResourceByCondition(DmCodeResource dmCodeResource);

    void batchSaveDmCodeResource(List<DmCodeResource> dmCodeResources);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
