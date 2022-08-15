package com.yice.edu.cn.osp.feignClient.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolNotifyFeign",path = "/schoolNotify")
public interface SchoolNotifyFeign {
    @GetMapping("/findSchoolNotifyById/{id}")
    SchoolNotify findSchoolNotifyById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolNotify")
    SchoolNotify saveSchoolNotify(SchoolNotify schoolNotify);
    @PostMapping("/findSchoolNotifyListByCondition")
    List<SchoolNotify> findSchoolNotifyListByCondition(SchoolNotify schoolNotify);
    @PostMapping("/findOneSchoolNotifyByCondition")
    SchoolNotify findOneSchoolNotifyByCondition(SchoolNotify schoolNotify);
    @PostMapping("/findSchoolNotifyCountByCondition")
    long findSchoolNotifyCountByCondition(SchoolNotify schoolNotify);
    @PostMapping("/updateSchoolNotify")
    void updateSchoolNotify(SchoolNotify schoolNotify);
    @GetMapping("/deleteSchoolNotify/{id}")
    void deleteSchoolNotify(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolNotifyByCondition")
    void deleteSchoolNotifyByCondition(SchoolNotify schoolNotify);
}
