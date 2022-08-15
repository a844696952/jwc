package com.yice.edu.cn.xw.dao.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsFileDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile);

    long findAssetsFileCountByCondition(AssetsFile assetsFile);

    AssetsFile findOneAssetsFileByCondition(AssetsFile assetsFile);

    AssetsFile findAssetsFileById(@Param("id") String id);

    void saveAssetsFile(AssetsFile assetsFile);

    void updateAssetsFile(AssetsFile assetsFile);

    void updateAssetsFileForAll(AssetsFile assetsFile);

    void deleteAssetsFile(@Param("id") String id);

    void deleteAssetsFileByCondition(AssetsFile assetsFile);

    void batchSaveAssetsFile(List<AssetsFile> assetsFiles);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
