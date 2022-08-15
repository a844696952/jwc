package com.yice.edu.cn.oa.feignClient;

import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "jw",contextId="departmentTeacherFeign", path = "/departmentTeacher")
public interface DepartmentTeacherFeign {
    @GetMapping("/findDepartmentByTeacherId/{teacherId}")
    List<DepartmentTeacher> findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId);
}
