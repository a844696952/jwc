package com.yice.edu.cn.bmp.feignClient.perm;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="jw",contextId = "permFeign",path = "/perm")
public interface PermFeign {
    @GetMapping("/findPermsForH5BySchoolId/{schoolId}")
    List<Perm> findPermsForH5BySchoolId(@PathVariable("schoolId") String schoolId);
}
