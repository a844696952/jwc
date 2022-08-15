package com.yice.edu.cn.ewb.feignClient.classRegister;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value="jw",contextId = "jwClassesFeign",path = "/jwClasses")
public interface JwClassesFeign {
    @PostMapping("/findJwClassesListByCondition")
    List<JwClasses> findJwClassesListByCondition(JwClasses jwClasses);
    @GetMapping("/findJwClassesById/{id}")
    JwClasses findJwClassesById(@PathVariable("id") String id);
    @PostMapping("/updateJwClasses")
    void updateJwClasses(JwClasses jwClasses);

}
