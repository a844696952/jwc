package com.yice.edu.cn.xw.dao.zc.assetsInNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsInNewDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsInNew> findAssetsInNewListByCondition(AssetsInNew assetsInNew);

    long findAssetsInNewCountByCondition(AssetsInNew assetsInNew);

    AssetsInNew findOneAssetsInNewByCondition(AssetsInNew assetsInNew);

    AssetsInNew findAssetsInNewById(@Param("id") String id);

    void saveAssetsInNew(AssetsInNew assetsInNew);

    void updateAssetsInNew(AssetsInNew assetsInNew);

    void deleteAssetsInNew(@Param("id") String id);

    void deleteAssetsInNewByCondition(AssetsInNew assetsInNew);

    void batchSaveAssetsInNew(List<AssetsInNew> assetsInNews);

    List<AssetsInNew> findAssetsNoListByCondition(AssetsInNew assetsInNew);

    String findAssetsNoByToday(@Param("today") String today);

    List<AssetsInNew> findAssetsInNewDetailByNo(AssetsInNew assetsInNew);

    public long findAssetsInNewDetailCountByNo(AssetsInNew assetsInNew);

    long findAssetsNoCountByCondition(AssetsInNew assetsInNew);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
