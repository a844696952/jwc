package com.yice.edu.cn.ecc.feignClient.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmSchoolBigNewsFeign",path = "/dmSchoolBigNews")
public interface DmSchoolBigNewsFeign {
    @GetMapping("/findDmSchoolBigNewsById/{id}")
    DmSchoolBigNews findDmSchoolBigNewsById(@PathVariable("id") String id);

    @PostMapping("/findDmSchoolBigNewsListByCondition")
    List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(DmSchoolBigNews dmSchoolBigNews);

    @PostMapping("/findDmSchoolBigNewsCountByCondition")
    long findDmSchoolBigNewsCountByCondition(DmSchoolBigNews dmSchoolBigNews);

}
