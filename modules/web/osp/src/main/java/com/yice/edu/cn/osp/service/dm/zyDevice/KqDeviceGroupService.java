package com.yice.edu.cn.osp.service.dm.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDeviceGroupFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KqDeviceGroupService {
    @Autowired
    private KqDeviceGroupFeign kqDeviceGroupFeign;

    public KqDeviceGroup findKqDeviceGroupById(String id) {
        return kqDeviceGroupFeign.findKqDeviceGroupById(id);
    }

    public KqDeviceGroup saveKqDeviceGroup(KqDeviceGroup kqDeviceGroup) {
        return kqDeviceGroupFeign.saveKqDeviceGroup(kqDeviceGroup);
    }

    public List<KqDeviceGroup> findKqDeviceGroupListByCondition(KqDeviceGroup kqDeviceGroup) {
        return kqDeviceGroupFeign.findKqDeviceGroupListByCondition(kqDeviceGroup);
    }

    public KqDeviceGroup findOneKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup) {
        return kqDeviceGroupFeign.findOneKqDeviceGroupByCondition(kqDeviceGroup);
    }

    public long findKqDeviceGroupCountByCondition(KqDeviceGroup kqDeviceGroup) {
        return kqDeviceGroupFeign.findKqDeviceGroupCountByCondition(kqDeviceGroup);
    }

    public void updateKqDeviceGroup(KqDeviceGroup kqDeviceGroup) {
        kqDeviceGroupFeign.updateKqDeviceGroup(kqDeviceGroup);
    }

    public void deleteKqDeviceGroup(String id) {
        kqDeviceGroupFeign.deleteKqDeviceGroup(id);
    }

    public void deleteKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup) {
        kqDeviceGroupFeign.deleteKqDeviceGroupByCondition(kqDeviceGroup);
    }

    public void updateKqDeviceGroupAndDeviceType(KqDeviceGroup kqDeviceGroup) {
        kqDeviceGroupFeign.updateKqDeviceGroupAndDeviceType(kqDeviceGroup);
    }
}
