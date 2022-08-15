package com.yice.edu.cn.dm.dao.wb.LatticePager;

import java.util.List;

import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmPagerBackgroundDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmPagerBackground> findDmPagerBackgroundListByCondition(DmPagerBackground dmPagerBackground);

    long findDmPagerBackgroundCountByCondition(DmPagerBackground dmPagerBackground);

    DmPagerBackground findOneDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground);

    DmPagerBackground findDmPagerBackgroundById(@Param("id") String id);

    void saveDmPagerBackground(DmPagerBackground dmPagerBackground);

    void updateDmPagerBackground(DmPagerBackground dmPagerBackground);

    void updateDmPagerBackgroundForAll(DmPagerBackground dmPagerBackground);

    void deleteDmPagerBackground(@Param("id") String id);

    void deleteDmPagerBackgroundByCondition(DmPagerBackground dmPagerBackground);

    void batchSaveDmPagerBackground(List<DmPagerBackground> dmPagerBackgrounds);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    List<DmPagerBackground> findDmPagerBackgroundInNumber(DmPagerBackground dmPagerBackground);


    List<DmPagerBackground> findPagerNumberIsNull(DmPagerBackground dmPagerBackground);


}
