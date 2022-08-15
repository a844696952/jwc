package com.yice.edu.cn.jw.feign.honourRoll;

import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmHonourRollFeign",path = "/dmHonourRoll")
public interface DmHonourRollFeign {
    @GetMapping("/findDmHonourRollByStudentId/{id}")
    DmHonourRoll findDmHonourRollByStudentId(@PathVariable("id") String id);
}
