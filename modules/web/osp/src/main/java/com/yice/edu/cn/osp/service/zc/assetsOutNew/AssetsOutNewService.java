package com.yice.edu.cn.osp.service.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import com.yice.edu.cn.osp.feignClient.zc.assetsOutNew.AssetsOutNewFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsOutNewService {
    @Autowired
    private AssetsOutNewFeign assetsOutNewFeign;

    public AssetsOutNew findAssetsOutNewById(String id) {
        return assetsOutNewFeign.findAssetsOutNewById(id);
    }

    public AssetsOutNew saveAssetsOutNew(AssetsOutNew assetsOutNew) {
        return assetsOutNewFeign.saveAssetsOutNew(assetsOutNew);
    }

    public List<AssetsOutNew> findAssetsOutNewListByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewFeign.findAssetsOutNewListByCondition(assetsOutNew);
    }

    public AssetsOutNew findOneAssetsOutNewByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewFeign.findOneAssetsOutNewByCondition(assetsOutNew);
    }

    public long findAssetsOutNewCountByCondition(AssetsOutNew assetsOutNew) {
        return assetsOutNewFeign.findAssetsOutNewCountByCondition(assetsOutNew);
    }

    public void updateAssetsOutNew(AssetsOutNew assetsOutNew) {
        assetsOutNewFeign.updateAssetsOutNew(assetsOutNew);
    }

    public void deleteAssetsOutNew(String id) {
        assetsOutNewFeign.deleteAssetsOutNew(id);
    }

    public void deleteAssetsOutNewByCondition(AssetsOutNew assetsOutNew) {
        assetsOutNewFeign.deleteAssetsOutNewByCondition(assetsOutNew);
    }
    public void batchSaveAssetsOutNew(List<AssetsOutNew> assetsOutNews){
        assetsOutNewFeign.batchSaveAssetsOutNew(assetsOutNews);
    }
    public List<AssetsOutNew> findAssetsOutNewListByCondition4Gather(AssetsOutNew assetsOutNew){
        return assetsOutNewFeign.findAssetsOutNewListByCondition4Gather(assetsOutNew);
    }
    public long findAssetsOutNewCountByCondition4Gather(AssetsOutNew assetsOutNew){
        return assetsOutNewFeign.findAssetsOutNewCountByCondition4Gather(assetsOutNew);
    }
}
