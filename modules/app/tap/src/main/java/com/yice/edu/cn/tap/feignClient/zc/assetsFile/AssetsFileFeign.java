package com.yice.edu.cn.tap.feignClient.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "assetsFileFeign",path = "/assetsFile")
public interface AssetsFileFeign {
    @PostMapping("/findAssetsFileListByCondition")
    List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile);
}
