package com.yice.edu.cn.xw.dao.zc.assetsContract;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAssetsContractDao {
    List<AssetsContract> findAssetsContractListByCondition(AssetsContract assetsContract);

    long findAssetsContractCountByCondition(AssetsContract assetsContract);

    AssetsContract findOneAssetsContractByCondition(AssetsContract assetsContract);

    AssetsContract findAssetsContractById(@Param("id") String id);

    void saveAssetsContract(AssetsContract assetsContract);

    void updateAssetsContract(AssetsContract assetsContract);

    void deleteAssetsContract(@Param("id") String id);

    void deleteAssetsContractByCondition(AssetsContract assetsContract);

    void batchSaveAssetsContract(List<AssetsContract> assetsContracts);

    List<AssetsContract> getFileList(AssetsContract assetsContract);

    void deleteAssetsContractByIds(AssetsContract assetsContract);
}
