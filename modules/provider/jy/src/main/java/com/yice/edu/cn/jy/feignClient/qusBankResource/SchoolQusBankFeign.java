package com.yice.edu.cn.jy.feignClient.qusBankResource;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jw", contextId = "schoolQusBankFeign", path = "/schoolQusBank")
public interface SchoolQusBankFeign {
    @GetMapping("/findSchoolQusBankById/{id}")
    SchoolQusBank findSchoolQusBankById(@PathVariable("id") String id);
    @PostMapping("/findSchoolQusBankListByCondition")
    List<SchoolQusBank> findSchoolQusBankListByCondition(SchoolQusBank schoolQusBank);
    @PostMapping("/findSchoolQusBankCountByCondition")
    long findSchoolQusBankCountByCondition(SchoolQusBank schoolQusBank);
    @PostMapping("/copyTopicToSchoolQusBank")
    void copyTopicToSchoolQusBank(SchoolQusBank schoolQusBank);
}
