package com.yice.edu.cn.osp.feignClient.dm.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/kqDeviceGroupPerson")
public interface KqDeviceGroupPersonFeign {
    @GetMapping("/findKqDeviceGroupPersonById/{id}")
    KqDeviceGroupPerson findKqDeviceGroupPersonById(@PathVariable("id") String id);
    @PostMapping("/saveKqDeviceGroupPerson")
    KqDeviceGroupPerson saveKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/findKqDeviceGroupPersonListByCondition")
    List<KqDeviceGroupPerson> findKqDeviceGroupPersonListByCondition(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/findOneKqDeviceGroupPersonByCondition")
    KqDeviceGroupPerson findOneKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/findKqDeviceGroupPersonCountByCondition")
    long findKqDeviceGroupPersonCountByCondition(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/updateKqDeviceGroupPerson")
    void updateKqDeviceGroupPerson(KqDeviceGroupPerson kqDeviceGroupPerson);
    @GetMapping("/deleteKqDeviceGroupPerson/{id}")
    void deleteKqDeviceGroupPerson(@PathVariable("id") String id);
    @PostMapping("/deleteKqDeviceGroupPersonByCondition")
    void deleteKqDeviceGroupPersonByCondition(KqDeviceGroupPerson kqDeviceGroupPerson);
    @PostMapping("/batchSaveKqDeviceGroupPerson")
    void batchSaveKqDeviceGroupPerson(List<KqDeviceGroupPerson> kqDeviceGroupPerson);

    @PostMapping("/findPersonsBoundDevices")
    List<KqDeviceGroupPerson> findPersonsBoundDevices(KqDeviceGroupPerson kqDeviceGroupPerson);
}
