package com.yice.edu.cn.osp.feignClient.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "jw", contextId = "schoolQusBankFeign", path = "/schoolQusBank")
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

    @PostMapping("/findSchoolQusNumByCreateTimeZone")
    List<JySchoolResourcesByDay> findSchoolQusNumByCreateTimeZone(SchoolQusBank schoolQusBank);

    @PostMapping("/copyTopicToSchoolQusBank")
    void copyTopicToSchoolQusBank(SchoolQusBank schoolQusBank);
}
