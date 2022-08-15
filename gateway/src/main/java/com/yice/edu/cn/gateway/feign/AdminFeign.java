package com.yice.edu.cn.gateway.feign;

import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "adminFeign",path = "/admin")
public interface AdminFeign {
    @GetMapping("/findSysFuncPermsByAdminId/{adminId}")
    List<SysPerm> findSysFuncPermsByAdminId(@PathVariable("adminId") String adminId);
}
