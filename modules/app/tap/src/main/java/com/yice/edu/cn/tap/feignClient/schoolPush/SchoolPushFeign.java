package com.yice.edu.cn.tap.feignClient.schoolPush;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/schoolPush")
public interface SchoolPushFeign {
    @GetMapping("/findSchoolPushById/{id}")
    SchoolPush findSchoolPushById(@PathVariable("id") String id);

    @PostMapping("/findPageSchoolPushesByAppIdAndSchoolId")
    List<SchoolPush> findSchoolPushsByCondition(SchoolPush schoolPush);

}
