package com.yice.edu.cn.ewb.feignClient.latticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.LatticePager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="dm",contextId = "latticePagerFeign",path = "/latticePager")
public interface LatticePagerFeign {

    @PostMapping("/findLatticePagerReference")
    ResponseJson findLatticePagerReference(DmPagerBackground dmPagerBackground);
}
