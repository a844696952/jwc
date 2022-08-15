package com.yice.edu.cn.yed.feignClient.jw.loc;

import com.yice.edu.cn.common.pojo.loc.SchoolConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolConfigFeign",path = "/schoolConfig")
public interface SchoolConfigFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolConfigById/{id}")
    SchoolConfig findSchoolConfigById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolConfig")
    SchoolConfig saveSchoolConfig(SchoolConfig schoolConfig);
    @PostMapping("/batchSaveSchoolConfig")
    void batchSaveSchoolConfig(List<SchoolConfig> schoolConfigs);
    @PostMapping("/findSchoolConfigListByCondition")
    List<SchoolConfig> findSchoolConfigListByCondition(SchoolConfig schoolConfig);
    @PostMapping("/findOneSchoolConfigByCondition")
    SchoolConfig findOneSchoolConfigByCondition(SchoolConfig schoolConfig);
    @PostMapping("/findSchoolConfigCountByCondition")
    long findSchoolConfigCountByCondition(SchoolConfig schoolConfig);
    @PostMapping("/updateSchoolConfig")
    void updateSchoolConfig(SchoolConfig schoolConfig);
    @PostMapping("/updateSchoolConfigForAll")
    void updateSchoolConfigForAll(SchoolConfig schoolConfig);
    @GetMapping("/deleteSchoolConfig/{id}")
    void deleteSchoolConfig(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolConfigByCondition")
    void deleteSchoolConfigByCondition(SchoolConfig schoolConfig);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/saveSchoolConfigKong")
    void saveSchoolConfigKong(SchoolConfig schoolConfig);
}
