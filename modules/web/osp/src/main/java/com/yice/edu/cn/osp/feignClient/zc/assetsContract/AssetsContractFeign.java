package com.yice.edu.cn.osp.feignClient.zc.assetsContract;

import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsContractFeign",path = "/assetsContract")
public interface AssetsContractFeign {
    @GetMapping("/findAssetsContractById/{id}")
    AssetsContract findAssetsContractById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsContract")
    AssetsContract saveAssetsContract(AssetsContract assetsContract);
    @PostMapping("/findAssetsContractListByCondition")
    List<AssetsContract> findAssetsContractListByCondition(AssetsContract assetsContract);
    @PostMapping("/findOneAssetsContractByCondition")
    AssetsContract findOneAssetsContractByCondition(AssetsContract assetsContract);
    @PostMapping("/findAssetsContractCountByCondition")
    long findAssetsContractCountByCondition(AssetsContract assetsContract);
    @PostMapping("/updateAssetsContract")
    void updateAssetsContract(AssetsContract assetsContract);
    @GetMapping("/deleteAssetsContract/{id}")
    void deleteAssetsContract(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsContractByCondition")
    void deleteAssetsContractByCondition(AssetsContract assetsContract);
    @PostMapping("/getFileList")
    List<AssetsContract> getFileList(AssetsContract assetsContract);
    @PostMapping("/deleteAssetsContractByIds")
    void deleteAssetsContractByIds(AssetsContract assetsContract);
}
