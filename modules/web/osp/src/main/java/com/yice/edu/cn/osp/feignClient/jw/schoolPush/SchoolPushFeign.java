package com.yice.edu.cn.osp.feignClient.jw.schoolPush;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolPushFeign",path = "/schoolPush")
public interface SchoolPushFeign {
    @GetMapping("/findSchoolPushById/{id}")
    SchoolPush findSchoolPushById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolPush")
    SchoolPush saveSchoolPush(SchoolPush schoolPush);
    @PostMapping("/findSchoolPushListByCondition")
    List<SchoolPush> findSchoolPushListByCondition(SchoolPush schoolPush);
    @PostMapping("/findOneSchoolPushByCondition")
    SchoolPush findOneSchoolPushByCondition(SchoolPush schoolPush);
    @PostMapping("/findSchoolPushCountByCondition")
    long findSchoolPushCountByCondition(SchoolPush schoolPush);
    @PostMapping("/updateSchoolPush")
    void updateSchoolPush(SchoolPush schoolPush);
    @GetMapping("/deleteSchoolPush/{id}")
    void deleteSchoolPush(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolPushByCondition")
    void deleteSchoolPushByCondition(SchoolPush schoolPush);
}
