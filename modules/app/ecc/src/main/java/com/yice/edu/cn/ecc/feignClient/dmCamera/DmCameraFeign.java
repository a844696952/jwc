package com.yice.edu.cn.ecc.feignClient.dmCamera;

import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmCameraFeign",path = "/dmCamera")
public interface DmCameraFeign {
    @GetMapping("/findDmCameraById/{id}")
    DmCamera findDmCameraById(@PathVariable("id") String id);
    @PostMapping("/findDmCameraListByCondition")
    List<DmCamera> findDmCameraListByCondition(DmCamera dmCamera);
    @PostMapping("/findOneDmCameraByCondition")
    DmCamera findOneDmCameraByCondition(DmCamera dmCamera);
    @PostMapping("/findDmCameraCountByCondition")
    long findDmCameraCountByCondition(DmCamera dmCamera);
}
