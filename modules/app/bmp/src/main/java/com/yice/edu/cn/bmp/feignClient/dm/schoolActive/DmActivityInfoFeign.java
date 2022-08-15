package com.yice.edu.cn.bmp.feignClient.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="dm",contextId = "dmActivityInfoFeign",path = "/dmActivityInfo")
public interface DmActivityInfoFeign {

    @GetMapping("/findBmpDmActivityInfoByActivityId/{activityId}")
    DmActivityInfo findDmActivityInfoByActivityId(@PathVariable("activityId") String activityId);


}
