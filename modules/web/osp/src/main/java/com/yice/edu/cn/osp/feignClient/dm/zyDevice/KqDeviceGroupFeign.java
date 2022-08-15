package com.yice.edu.cn.osp.feignClient.dm.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "kqDeviceGroupFeign",path = "/kqDeviceGroup")
public interface KqDeviceGroupFeign {
    @GetMapping("/findKqDeviceGroupById/{id}")
    KqDeviceGroup findKqDeviceGroupById(@PathVariable("id") String id);
    @PostMapping("/saveKqDeviceGroup")
    KqDeviceGroup saveKqDeviceGroup(KqDeviceGroup kqDeviceGroup);
    @PostMapping("/findKqDeviceGroupListByCondition")
    List<KqDeviceGroup> findKqDeviceGroupListByCondition(KqDeviceGroup kqDeviceGroup);
    @PostMapping("/findOneKqDeviceGroupByCondition")
    KqDeviceGroup findOneKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup);
    @PostMapping("/findKqDeviceGroupCountByCondition")
    long findKqDeviceGroupCountByCondition(KqDeviceGroup kqDeviceGroup);
    @PostMapping("/updateKqDeviceGroup")
    void updateKqDeviceGroup(KqDeviceGroup kqDeviceGroup);
    @GetMapping("/deleteKqDeviceGroup/{id}")
    void deleteKqDeviceGroup(@PathVariable("id") String id);
    @PostMapping("/deleteKqDeviceGroupByCondition")
    void deleteKqDeviceGroupByCondition(KqDeviceGroup kqDeviceGroup);
    @PostMapping("/updateKqDeviceGroupAndDeviceType")
    void updateKqDeviceGroupAndDeviceType(KqDeviceGroup kqDeviceGroup);
}
