package com.yice.edu.cn.jw.dao.region;

import java.util.List;

import com.yice.edu.cn.common.pojo.general.region.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRegionDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<Region> findRegionListByCondition(Region region);

    long findRegionCountByCondition(Region region);

    Region findOneRegionByCondition(Region region);

    Region findRegionById(@Param("id") String id);

    void saveRegion(Region region);

    void updateRegion(Region region);

    void deleteRegion(@Param("id") String id);

    void deleteRegionByCondition(Region region);

    void batchSaveRegion(List<Region> regions);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
