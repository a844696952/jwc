package com.yice.edu.cn.dm.dao.dmMobileRedBanner;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmMobileRedBannerDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmMobileRedBanner> findDmMobileRedBannerListByCondition(DmMobileRedBanner dmMobileRedBanner);

    long findDmMobileRedBannerCountByCondition(DmMobileRedBanner dmMobileRedBanner);

    DmMobileRedBanner findOneDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner);

    DmMobileRedBanner findDmMobileRedBannerById(@Param("id") String id);

    void saveDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner);

    void updateDmMobileRedBanner(DmMobileRedBanner dmMobileRedBanner);

    void updateDmMobileRedBannerForNotNull(DmMobileRedBanner dmMobileRedBanner);

    void deleteDmMobileRedBanner(@Param("id") String id);

    void deleteDmMobileRedBannerByCondition(DmMobileRedBanner dmMobileRedBanner);

    void batchSaveDmMobileRedBanner(List<DmMobileRedBanner> dmMobileRedBanners);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void updateStatus();

    String getClassNames(@Param("ids")String[] ids);

    DmMobileRedBanner findDmMobileRedBannerByClassId(@Param("classId") String classId);

    DmMobileRedBanner findTodayAwardRedBanner();

    void updateStatusShowById(@Param("id") String id);

    DmMobileRedBanner findToBeIssuedDmMobileRedBannerByAwardTime(@Param("awardTime") String awardTime);

    List<DmMobileRedBanner> findDmMobileRedBannersByClassId(@Param("classId") String classId);
}
