package com.yice.edu.cn.osp.service.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.osp.feignClient.zc.assetsFile.AssetsFileFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsFileService {
    @Autowired
    private AssetsFileFeign assetsFileFeign;

    public AssetsFile findAssetsFileById(String id) {
        return assetsFileFeign.findAssetsFileById(id);
    }

    public AssetsFile saveAssetsFile(AssetsFile assetsFile) {
        return assetsFileFeign.saveAssetsFile(assetsFile);
    }

    public List<AssetsFile> findAssetsFileListByCondition(AssetsFile assetsFile) {
        return assetsFileFeign.findAssetsFileListByCondition(assetsFile);
    }

    public AssetsFile findOneAssetsFileByCondition(AssetsFile assetsFile) {
        return assetsFileFeign.findOneAssetsFileByCondition(assetsFile);
    }

    public long findAssetsFileCountByCondition(AssetsFile assetsFile) {
        return assetsFileFeign.findAssetsFileCountByCondition(assetsFile);
    }

    public void updateAssetsFile(AssetsFile assetsFile) {
        assetsFileFeign.updateAssetsFile(assetsFile);
    }

    public void deleteAssetsFile(String id) {
        assetsFileFeign.deleteAssetsFile(id);
    }

    public void deleteAssetsFileByCondition(AssetsFile assetsFile) {
        assetsFileFeign.deleteAssetsFileByCondition(assetsFile);
    }
}
