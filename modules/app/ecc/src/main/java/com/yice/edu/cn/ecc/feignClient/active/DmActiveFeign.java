package com.yice.edu.cn.ecc.feignClient.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmActiveFeign",path = "/dmActive")
public interface DmActiveFeign {
    @GetMapping("/findDmActiveById/{id}")
    DmActive findDmActiveById(@PathVariable("id") String id);
    @PostMapping("/findDmActiveListByConditionVue")
    List<DmActive> findDmActiveListByConditionVue(DmActive dmActive);

}
