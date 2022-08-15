package com.yice.edu.cn.osp.service.zc.assetsUnit;

import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import com.yice.edu.cn.osp.feignClient.zc.assetsUnit.AssetsUnitFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsUnitService {
    @Autowired
    private AssetsUnitFeign assetsUnitFeign;

    public AssetsUnit findAssetsUnitById(String id) {
        return assetsUnitFeign.findAssetsUnitById(id);
    }

    public AssetsUnit saveAssetsUnit(AssetsUnit assetsUnit) {
        return assetsUnitFeign.saveAssetsUnit(assetsUnit);
    }

    public List<AssetsUnit> findAssetsUnitListByCondition(AssetsUnit assetsUnit) {
        return assetsUnitFeign.findAssetsUnitListByCondition(assetsUnit);
    }

    public AssetsUnit findOneAssetsUnitByCondition(AssetsUnit assetsUnit) {
        return assetsUnitFeign.findOneAssetsUnitByCondition(assetsUnit);
    }

    public long findAssetsUnitCountByCondition(AssetsUnit assetsUnit) {
        return assetsUnitFeign.findAssetsUnitCountByCondition(assetsUnit);
    }

    public void updateAssetsUnit(AssetsUnit assetsUnit) {
        assetsUnitFeign.updateAssetsUnit(assetsUnit);
    }

    public void deleteAssetsUnit(String id) {
        assetsUnitFeign.deleteAssetsUnit(id);
    }

    public void deleteAssetsUnitByCondition(AssetsUnit assetsUnit) {
        assetsUnitFeign.deleteAssetsUnitByCondition(assetsUnit);
    }

    public long findAssetsUnitCountByName(AssetsUnit assetsUnit) {
        return assetsUnitFeign.findAssetsUnitCountByName(assetsUnit);
    }


}
