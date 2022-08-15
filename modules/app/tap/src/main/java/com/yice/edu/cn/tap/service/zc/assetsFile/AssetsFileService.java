package com.yice.edu.cn.tap.service.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.tap.feignClient.zc.assetsFile.AssetsFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsFileService {
    @Autowired
    private AssetsFileFeign assetsFileFeign;

    public List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile) {
        return assetsFileFeign.findAssetsFileListByCondition(assetsFile);
    }

}
