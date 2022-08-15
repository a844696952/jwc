package com.yice.edu.cn.ecc.service.dmCountDownManage;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.ecc.feignClient.dmCountDownManage.DmCountDownManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmCountDownManageService {
    @Autowired
    private DmCountDownManageFeign dmCountDownManageFeign;

    public DmCountDownManage findDmCountDownManageById(String id) {
        return dmCountDownManageFeign.findDmCountDownManageById(id);
    }

    public DmCountDownManage saveDmCountDownManage(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageFeign.saveDmCountDownManage(dmCountDownManage);
    }

    public List<DmCountDownManage> findDmCountDownManageListByCondition(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageFeign.findDmCountDownManageListByCondition(dmCountDownManage);
    }

    public DmCountDownManage findOneDmCountDownManageByCondition(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageFeign.findOneDmCountDownManageByCondition(dmCountDownManage);
    }

    public long findDmCountDownManageCountByCondition(DmCountDownManage dmCountDownManage) {
        return dmCountDownManageFeign.findDmCountDownManageCountByCondition(dmCountDownManage);
    }

    public void updateDmCountDownManage(DmCountDownManage dmCountDownManage) {
        dmCountDownManageFeign.updateDmCountDownManage(dmCountDownManage);
    }

    public void deleteDmCountDownManage(String id) {
        dmCountDownManageFeign.deleteDmCountDownManage(id);
    }

    public void deleteDmCountDownManageByCondition(DmCountDownManage dmCountDownManage) {
        dmCountDownManageFeign.deleteDmCountDownManageByCondition(dmCountDownManage);
    }
}
