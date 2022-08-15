package com.yice.edu.cn.dm.dao.dmMobileRedBanner;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBannerList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmMobileRedBannerListDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmMobileRedBannerList> findDmMobileRedBannerListListByCondition(DmMobileRedBannerList dmMobileRedBannerList);

    long findDmMobileRedBannerListCountByCondition(DmMobileRedBannerList dmMobileRedBannerList);

    DmMobileRedBannerList findOneDmMobileRedBannerListByCondition(DmMobileRedBannerList dmMobileRedBannerList);

    DmMobileRedBannerList findDmMobileRedBannerListById(@Param("id") String id);

    void saveDmMobileRedBannerList(DmMobileRedBannerList dmMobileRedBannerList);

    void updateDmMobileRedBannerList(DmMobileRedBannerList dmMobileRedBannerList);

    void updateDmMobileRedBannerListForAll(DmMobileRedBannerList dmMobileRedBannerList);

    void deleteDmMobileRedBannerList(@Param("id") String id);

    void deleteDmMobileRedBannerListByCondition(DmMobileRedBannerList dmMobileRedBannerList);

    void batchSaveDmMobileRedBannerList(List<DmMobileRedBannerList> dmMobileRedBannerLists);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void deleteDmMobileRedBannerListByClassIds(@Param("clazzIds") List<String> clazzIds);

}
