package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceDeviceFeign",path = "/ycVerifaceDevice")
public interface YcVerifaceDeviceFeign {
    @GetMapping("/findYcVerifaceDeviceById/{id}")
    YcVerifaceDevice findYcVerifaceDeviceById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceDevice")
    YcVerifaceDevice saveYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice);
    @PostMapping("/findYcVerifaceDeviceListByCondition")
    List<YcVerifaceDevice> findYcVerifaceDeviceListByCondition(YcVerifaceDevice ycVerifaceDevice);
    @PostMapping("/findOneYcVerifaceDeviceByCondition")
    YcVerifaceDevice findOneYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice);
    @PostMapping("/findYcVerifaceDeviceCountByCondition")
    long findYcVerifaceDeviceCountByCondition(YcVerifaceDevice ycVerifaceDevice);
    @PostMapping("/updateYcVerifaceDevice")
    void updateYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice);
    @GetMapping("/deleteYcVerifaceDevice/{id}")
    void deleteYcVerifaceDevice(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceDeviceByCondition")
    void deleteYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice);
}
