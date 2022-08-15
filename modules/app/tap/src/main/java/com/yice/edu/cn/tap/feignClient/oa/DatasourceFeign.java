package com.yice.edu.cn.tap.feignClient.oa;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.oa.common.SelComponent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="jw",contextId="datasourceFeign",path = "/oaDatasource")
public interface DatasourceFeign {
    @GetMapping("/getMyDepartmentNames/{id}")
    List<Department> getMyDepartmentNames(@PathVariable("id") String id);
    @GetMapping("/getclassHour")
    public List<SelComponent> getclassHour();
    @GetMapping("/getTimeInterval")
    public List<SelComponent> getTimeInterval();
}
