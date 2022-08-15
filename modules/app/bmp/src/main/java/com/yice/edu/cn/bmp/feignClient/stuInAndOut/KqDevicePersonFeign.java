package com.yice.edu.cn.bmp.feignClient.stuInAndOut;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/kqDevicePerson")
public interface KqDevicePersonFeign {
    @GetMapping("/findKqDevicePersonById/{id}")
    KqDevicePerson findKqDevicePersonById(@PathVariable("id") String id);
    @PostMapping("/saveKqDevicePerson")
    KqDevicePerson saveKqDevicePerson(KqDevicePerson kqDevicePerson);
    @PostMapping("/findKqDevicePersonListByCondition")
    List<KqDevicePerson> findKqDevicePersonListByCondition(KqDevicePerson kqDevicePerson);
    @PostMapping("/findOneKqDevicePersonByCondition")
    KqDevicePerson findOneKqDevicePersonByCondition(KqDevicePerson kqDevicePerson);
    @PostMapping("/findKqDevicePersonCountByCondition")
    long findKqDevicePersonCountByCondition(KqDevicePerson kqDevicePerson);
    @PostMapping("/updateKqDevicePerson")
    void updateKqDevicePerson(KqDevicePerson kqDevicePerson);
    @GetMapping("/deleteKqDevicePerson/{id}")
    void deleteKqDevicePerson(@PathVariable("id") String id);
    @PostMapping("/deleteKqDevicePersonByCondition")
    void deleteKqDevicePersonByCondition(KqDevicePerson kqDevicePerson);
}
