package com.yice.edu.cn.osp.service.zc.AssetsInNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.osp.feignClient.zc.assetsInNew.AssetsInNewFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsInNewService {
    @Autowired
    private AssetsInNewFeign assetsInNewFeign;

    public AssetsInNew findAssetsInNewById(String id) {
        return assetsInNewFeign.findAssetsInNewById(id);
    }

    public AssetsInNew saveAssetsInNew(AssetsInNew assetsInNew) {
        return assetsInNewFeign.saveAssetsInNew(assetsInNew);
    }

    //入库单详情，包括几个资产。
    public List<AssetsInNew> findAssetsInNewDetailByNo(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsInNewDetailByNo(assetsInNew);
    }

    public AssetsInNew findOneAssetsInNewByCondition(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findOneAssetsInNewByCondition(assetsInNew);
    }

    public long findAssetsInNewCountByCondition(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsInNewCountByCondition(assetsInNew);
    }

    public void updateAssetsInNew(AssetsInNew assetsInNew) {
        assetsInNewFeign.updateAssetsInNew(assetsInNew);
    }

    public void deleteAssetsInNew(String id) {
        assetsInNewFeign.deleteAssetsInNew(id);
    }

    public void deleteAssetsInNewByCondition(AssetsInNew assetsInNew) {
        assetsInNewFeign.deleteAssetsInNewByCondition(assetsInNew);
    }

    public List<AssetsInNew> findAssetsNoListByCondition(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsNoListByCondition(assetsInNew);
    }

    public AssetsInNew findAssetsNoInfoByNo(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsNoInfoByNo(assetsInNew);
    }
    public long findAssetsInNewDetailCountByNo(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsInNewDetailCountByNo(assetsInNew);
    }

    public long findAssetsNoCountByCondition(AssetsInNew assetsInNew) {
        return assetsInNewFeign.findAssetsNoCountByCondition(assetsInNew);
    }
}
