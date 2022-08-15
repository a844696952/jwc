package com.yice.edu.cn.dm.feignClient.jw.classes;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jw", contextId = "jwClassesFeign", path = "/jwClasses")
public interface JwClassesFeign {
    @GetMapping("/findJwClassesById/{id}")
    JwClasses findJwClassesById(@PathVariable("id") String id);
}
