package com.yice.edu.cn.oa.feignClient;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jw",contextId="departmentFeign", path = "/department")
public interface DepartmentFeign {
    @GetMapping("/findDepartmentById/{id}")
     Department findDepartmentById( @PathVariable("id") String id);
}
