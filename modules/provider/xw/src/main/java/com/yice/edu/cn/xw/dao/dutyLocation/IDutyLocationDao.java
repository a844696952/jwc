package com.yice.edu.cn.xw.dao.dutyLocation;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDutyLocationDao {
    List<DutyLocation> findDutyLocationListByCondition(DutyLocation dutyLocation);

    long findDutyLocationCountByCondition(DutyLocation dutyLocation);

    DutyLocation findOneDutyLocationByCondition(DutyLocation dutyLocation);

    DutyLocation findDutyLocationById(@Param("id") String id);

    void saveDutyLocation(DutyLocation dutyLocation);

    void updateDutyLocation(DutyLocation dutyLocation);

    void deleteDutyLocation(@Param("id") String id);

    void deleteDutyLocationByCondition(DutyLocation dutyLocation);

    void batchSaveDutyLocation(List<DutyLocation> dutyLocations);
}
