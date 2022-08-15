package com.yice.edu.cn.ecc.feignClient.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmSchoolHonourFeign",path = "/dmSchoolHonour")
public interface DmSchoolHonourFeign {
    @GetMapping("/findDmSchoolHonourById/{id}")
    DmSchoolHonour findDmSchoolHonourById(@PathVariable("id") String id);

    @PostMapping("/findDmSchoolHonourListByCondition")
    List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findDmSchoolHonourCountByCondition")
    long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour);

}
