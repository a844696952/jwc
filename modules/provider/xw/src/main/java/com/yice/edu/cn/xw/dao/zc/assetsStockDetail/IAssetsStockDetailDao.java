package com.yice.edu.cn.xw.dao.zc.assetsStockDetail;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsStockDetailDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsStockDetail> findAssetsStockDetailListByCondition(AssetsStockDetail assetsStockDetail);

    long findAssetsStockDetailCountByCondition(AssetsStockDetail assetsStockDetail);

    AssetsStockDetail findOneAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail);

    AssetsStockDetail findAssetsStockDetailById(@Param("id") String id);

    void saveAssetsStockDetail(AssetsStockDetail assetsStockDetail);

    void updateAssetsStockDetail(AssetsStockDetail assetsStockDetail);

    void updateAssetsStockDetailForNotNull(AssetsStockDetail assetsStockDetail);

    void deleteAssetsStockDetail(@Param("id") String id);

    void deleteAssetsStockDetailByCondition(AssetsStockDetail assetsStockDetail);

    void batchSaveAssetsStockDetail(List<AssetsStockDetail> assetsStockDetails);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    List<AssetsStockDetail> findAssetsStockDetailListByFuzzyCondition(AssetsStockDetail assetsStockDetail);

    long findAssetsStockDetailCountByFuzzyCondition(AssetsStockDetail assetsStockDetail);

    String getLastCodeByInventoryCode(String inventoryCode);

    List<AssetsStockDetail> findAssetsUseRecordById(AssetsStockDetail assetsStockDetail);

    long findAssetsUseRecordCountById(AssetsStockDetail assetsStockDetail);

    List<AssetsStockDetail> findAssetsResListByAssetsResIds(@Param("assetsResIds1") List<String> assetsResIds);

    AssetsStockDetail findAssetsStockDetailJoinTableById(@Param("id") String id);

    void updateAssetsStockDetailForNotNullByCondition(AssetsStockDetail assetsStockDetail);

    String findAssetsUsePercentage(AssetsStockDetail assetsStockDetail);

    List<AssetsStockDetail> findRecentOneYearAssertsCount(AssetsStockDetail assetsStockDetail);

    List<AssetsStockDetail> findRecentOneMonthAssertsCount(AssetsStockDetail assetsStockDetail);

    List<AssetsFile> findFileTotalPrice(AssetsStockDetail assetsStockDetail);
}
