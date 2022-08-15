package com.yice.edu.cn.osp.feignClient.jw.perm;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "permFeign",path = "/perm")
public interface PermFeign {
    @GetMapping("/findPermById/{id}")
    Perm findPermById(@PathVariable("id") String id);
    @PostMapping("/savePerm")
    Perm savePerm(Perm perm);
    @PostMapping("/findPermListByCondition")
    List<Perm> findPermListByCondition(Perm perm);
    @PostMapping("/findOnePermByCondition")
    Perm findOnePermByCondition(Perm perm);
    @PostMapping("/findPermCountByCondition")
    long findPermCountByCondition(Perm perm);
    @PostMapping("/updatePerm")
    void updatePerm(Perm perm);
    @GetMapping("/deletePerm/{id}")
    void deletePerm(@PathVariable("id") String id);
    @PostMapping("/deletePermByCondition")
    void deletePermByCondition(Perm perm);
}
