package com.yice.edu.cn.yed.feignClient.general.standard;

import com.yice.edu.cn.common.pojo.general.standard.Standard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "standardFeign",path = "/standard",fallbackFactory = StandardFeignFallbackFactory.class)
public interface StandardFeign {
    @GetMapping("/findStandardById/{id}")
    Standard findStandardById(@PathVariable("id") String id);
    @RequestMapping("/saveStandard")
    Standard saveStandard(@RequestBody Standard standard);
    @RequestMapping("/findStandardListByCondition")
    List<Standard> findStandardListByCondition(Standard standard);
    @RequestMapping("/findStandardCountByCondition")
    long findStandardCountByCondition(Standard standard);
    @RequestMapping("/updateStandard")
    void updateStandard(Standard standard);
    @GetMapping("/deleteStandard/{id}")
    void deleteStandard(@PathVariable("id") String id);
}
