package com.yice.edu.cn.ewb.feignClient.prepareLessons;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(value="jw",contextId = "schoolQusBankFeign",path = "/schoolQusBank")
public interface SchoolQusBankFeign {
    @GetMapping("/findSchoolQusBankById/{id}")
    SchoolQusBank findSchoolQusBankById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolQusBank")
    SchoolQusBank saveSchoolQusBank(SchoolQusBank schoolQusBank);
    @PostMapping("/findSchoolQusBankListByCondition")
    List<SchoolQusBank> findSchoolQusBankListByCondition(SchoolQusBank schoolQusBank);
    @PostMapping("/findOneSchoolQusBankByCondition")
    SchoolQusBank findOneSchoolQusBankByCondition(SchoolQusBank schoolQusBank);
    @PostMapping("/findSchoolQusBankCountByCondition")
    long findSchoolQusBankCountByCondition(SchoolQusBank schoolQusBank);
    @PostMapping("/updateSchoolQusBank")
    void updateSchoolQusBank(SchoolQusBank schoolQusBank);
    @GetMapping("/deleteSchoolQusBank/{id}")
    void deleteSchoolQusBank(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolQusBankByCondition")
    void deleteSchoolQusBankByCondition(SchoolQusBank schoolQusBank);
}
