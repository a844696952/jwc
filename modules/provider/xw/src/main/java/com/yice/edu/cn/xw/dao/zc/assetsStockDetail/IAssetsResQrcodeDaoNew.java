package com.yice.edu.cn.xw.dao.zc.assetsStockDetail;

import com.yice.edu.cn.common.pojo.xw.zc.AssetsResQrcode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetsResQrcodeDaoNew {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<AssetsResQrcode> findAssetsResQrcodeListByCondition(AssetsResQrcode assetsResQrcode);

    long findAssetsResQrcodeCountByCondition(AssetsResQrcode assetsResQrcode);

    AssetsResQrcode findOneAssetsResQrcodeByCondition(AssetsResQrcode assetsResQrcode);

    AssetsResQrcode findAssetsResQrcodeById(@Param("id") String id);

    void saveAssetsResQrcode(AssetsResQrcode assetsResQrcode);

    void updateAssetsResQrcode(AssetsResQrcode assetsResQrcode);

    void updateAssetsResQrcodeForNotNull(AssetsResQrcode assetsResQrcode);

    void deleteAssetsResQrcode(@Param("id") String id);

    void deleteAssetsResQrcodeByCondition(AssetsResQrcode assetsResQrcode);

    void batchSaveAssetsResQrcode(List<AssetsResQrcode> assetsResQrcodes);

    int findAssetsResQrcodeCountByAssetsResIds(@Param("assetsResIds1") List<String> assetsResIds);

    void deleteQrcodeByAssetsResIds(@Param("assetsResIds2") List<String> assetsResIds);

    List<AssetsResQrcode> findAssetsResQrcodes(@Param("assetsResIds3") List<String> assetsResIds);

    List<String> findHasQrcodeByAssetsResIds(@Param("assetsResIds4") List<String> assetsResIds);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
