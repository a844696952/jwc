package com.yice.edu.cn.osp.feignClient.dm.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm",contextId = "dmSchoolHonourFeign",path = "/dmSchoolHonour")
public interface DmSchoolHonourFeign {
    @GetMapping("/findDmSchoolHonourById/{id}")
    DmSchoolHonour findDmSchoolHonourById(@PathVariable("id") String id);

    @PostMapping("/saveDmSchoolHonour")
    DmSchoolHonour saveDmSchoolHonour(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findDmSchoolHonourListByCondition")
    List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findOneDmSchoolHonourByCondition")
    DmSchoolHonour findOneDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findDmSchoolHonourCountByCondition")
    long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/updateDmSchoolHonour")
    void updateDmSchoolHonour(DmSchoolHonour dmSchoolHonour);

    @GetMapping("/deleteDmSchoolHonour/{id}")
    void deleteDmSchoolHonour(@PathVariable("id") String id);

    @PostMapping("/deleteDmSchoolHonourByCondition")
    void deleteDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findDmSchoolHonourByactiveNameLike")
    List<DmSchoolHonour> findDmSchoolHonourByactiveNameLike(DmSchoolHonour dmSchoolHonour);

    @PostMapping("/findDmSchoolHonourByactiveNameLikeCount")
    Long findDmSchoolHonourByactiveNameLikeCount(DmSchoolHonour dmSchoolHonour);
}
