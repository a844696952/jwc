package com.yice.edu.cn.dm.dao.wb.LatticePager;

import java.util.List;

import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmPagerNumberDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmPagerNumber> findDmPagerNumberListByCondition(DmPagerNumber dmPagerNumber);

    long findDmPagerNumberCountByCondition(DmPagerNumber dmPagerNumber);

    DmPagerNumber findOneDmPagerNumberByCondition(DmPagerNumber dmPagerNumber);

    DmPagerNumber findDmPagerNumberById(@Param("id") String id);

    void saveDmPagerNumber(DmPagerNumber dmPagerNumber);

    void updateDmPagerNumber(DmPagerNumber dmPagerNumber);

    void updateDmPagerNumberForAll(DmPagerNumber dmPagerNumber);

    void deleteDmPagerNumber(@Param("id") String id);

    void deleteDmPagerNumberByCondition(DmPagerNumber dmPagerNumber);

    void batchSaveDmPagerNumber(List<DmPagerNumber> dmPagerNumbers);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
