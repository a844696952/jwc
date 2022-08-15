package com.yice.edu.cn.osp.feignClient.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsFileFeign",path = "/assetsFile")
public interface AssetsFileFeign {
    @GetMapping("/findAssetsFileById/{id}")
    AssetsFile findAssetsFileById(@PathVariable("id") String id);
    @PostMapping("/saveAssetsFile")
    AssetsFile saveAssetsFile(AssetsFile assetsFile);
    @PostMapping("/findAssetsFileListByCondition")
    List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile);
    @PostMapping("/findOneAssetsFileByCondition")
    AssetsFile findOneAssetsFileByCondition(AssetsFile assetsFile);
    @PostMapping("/findAssetsFileCountByCondition")
    long findAssetsFileCountByCondition(AssetsFile assetsFile);
    @PostMapping("/updateAssetsFile")
    void updateAssetsFile(AssetsFile assetsFile);
    @GetMapping("/deleteAssetsFile/{id}")
    void deleteAssetsFile(@PathVariable("id") String id);
    @PostMapping("/deleteAssetsFileByCondition")
    void deleteAssetsFileByCondition(AssetsFile assetsFile);
}
