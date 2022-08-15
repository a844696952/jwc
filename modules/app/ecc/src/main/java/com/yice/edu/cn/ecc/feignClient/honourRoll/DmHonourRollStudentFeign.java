package com.yice.edu.cn.ecc.feignClient.honourRoll;

import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollStudentFeign",path = "/dmHonourRollStudent")
public interface DmHonourRollStudentFeign {

    @PostMapping("/getHonourRollList")
    List<EccHonourRoll> getHonourRollList(DmHonourRollStudent dmHonourRollStudent);

}
