package com.yice.edu.cn.osp.service.zc.assetsContract;

import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.osp.feignClient.zc.assetsContract.AssetsContractFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsContractService {
    @Autowired
    private AssetsContractFeign assetsContractFeign;

    public AssetsContract findAssetsContractById(String id) {
        return assetsContractFeign.findAssetsContractById(id);
    }

    public AssetsContract saveAssetsContract(AssetsContract assetsContract) {
        return assetsContractFeign.saveAssetsContract(assetsContract);
    }

    public List<AssetsContract> findAssetsContractListByCondition(AssetsContract assetsContract) {
        return assetsContractFeign.findAssetsContractListByCondition(assetsContract);
    }

    public AssetsContract findOneAssetsContractByCondition(AssetsContract assetsContract) {
        return assetsContractFeign.findOneAssetsContractByCondition(assetsContract);
    }

    public long findAssetsContractCountByCondition(AssetsContract assetsContract) {
        return assetsContractFeign.findAssetsContractCountByCondition(assetsContract);
    }

    public void updateAssetsContract(AssetsContract assetsContract) {
        assetsContractFeign.updateAssetsContract(assetsContract);
    }

    public void deleteAssetsContract(String id) {
        assetsContractFeign.deleteAssetsContract(id);
    }

    public void deleteAssetsContractByCondition(AssetsContract assetsContract) {
        assetsContractFeign.deleteAssetsContractByCondition(assetsContract);
    }

    public List<AssetsContract> getFileList(AssetsContract assetsContract){
        return assetsContractFeign.getFileList(assetsContract);
    }

    public void deleteAssetsContractByIds(AssetsContract assetsContract) {
        assetsContractFeign.deleteAssetsContractByIds(assetsContract);
    }
}
