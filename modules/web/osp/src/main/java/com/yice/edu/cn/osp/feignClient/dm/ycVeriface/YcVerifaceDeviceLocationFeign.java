package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceLocation.YcVerifaceDeviceLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "ycVerifaceDeviceLocationFeign",path = "/ycVerifaceDeviceLocation")
public interface YcVerifaceDeviceLocationFeign {
    @GetMapping("/findYcVerifaceDeviceLocationById/{id}")
    YcVerifaceDeviceLocation findYcVerifaceDeviceLocationById(@PathVariable("id") String id);
    @PostMapping("/saveYcVerifaceDeviceLocation")
    YcVerifaceDeviceLocation saveYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
    @PostMapping("/findYcVerifaceDeviceLocationListByCondition")
    List<YcVerifaceDeviceLocation> findYcVerifaceDeviceLocationListByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
    @PostMapping("/findOneYcVerifaceDeviceLocationByCondition")
    YcVerifaceDeviceLocation findOneYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
    @PostMapping("/findYcVerifaceDeviceLocationCountByCondition")
    long findYcVerifaceDeviceLocationCountByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
    @PostMapping("/updateYcVerifaceDeviceLocation")
    void updateYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
    @GetMapping("/deleteYcVerifaceDeviceLocation/{id}")
    void deleteYcVerifaceDeviceLocation(@PathVariable("id") String id);
    @PostMapping("/deleteYcVerifaceDeviceLocationByCondition")
    void deleteYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation);
}
