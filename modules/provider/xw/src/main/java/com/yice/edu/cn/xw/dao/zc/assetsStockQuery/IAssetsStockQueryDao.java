package com.yice.edu.cn.xw.dao.zc.assetsStockQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface IAssetsStockQueryDao {
    AssetsStockGatherDto findAssetsStockGather(@Param("schoolId") String schoolId);

    List<AssetsFile> findAssetsStockListByCondition(AssetsFile assetsFile);

    long findAssetsStockListCountByCondition(AssetsFile assetsFile);
}
