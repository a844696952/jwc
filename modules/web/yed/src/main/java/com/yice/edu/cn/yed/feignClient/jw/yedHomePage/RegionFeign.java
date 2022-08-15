package com.yice.edu.cn.yed.feignClient.jw.yedHomePage;

import com.yice.edu.cn.common.pojo.jw.school.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jw",contextId = "regionFeign",path = "/school")
public interface RegionFeign {

    @GetMapping("/findSchoolById/{id}")
    School findSchoolById(@PathVariable("id") String id);
}
