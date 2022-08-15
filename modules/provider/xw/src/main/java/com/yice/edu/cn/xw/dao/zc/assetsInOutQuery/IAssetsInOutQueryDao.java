package com.yice.edu.cn.xw.dao.zc.assetsInOutQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAssetsInOutQueryDao {

    List<AssetsFile> findInOutQueryByCondition(AssetsFile assetsFile);

    long findInOutQueryCountByCondition(AssetsFile assetsFile);

    List<AssetsFile> findAssetUseDataByCondition(AssetsFile assetsFile);

    long findAssetUseDataCountByCondition(AssetsFile assetsFile);

}
