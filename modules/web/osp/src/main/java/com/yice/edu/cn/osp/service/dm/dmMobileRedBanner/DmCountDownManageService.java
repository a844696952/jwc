package com.yice.edu.cn.osp.service.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.osp.feignClient.dm.dmMobileRedBanner.DmCountDownManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmCountDownManageService {
    @Autowired
    private DmCountDownManageFeign dmCountDownManageFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
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

    public void updateDmCountDownManageForAll(DmCountDownManage dmCountDownManage) {
        dmCountDownManageFeign.updateDmCountDownManageForAll(dmCountDownManage);
    }

    public void deleteDmCountDownManage(String id) {
        dmCountDownManageFeign.deleteDmCountDownManage(id);
    }

    public void deleteDmCountDownManageByCondition(DmCountDownManage dmCountDownManage) {
        dmCountDownManageFeign.deleteDmCountDownManageByCondition(dmCountDownManage);
    }

    public void updateDmCountDownManageStatus(DmCountDownManage dmCountDownManage) {
        dmCountDownManageFeign.updateDmCountDownManageStatus(dmCountDownManage);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
