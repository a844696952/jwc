package com.yice.edu.cn.ecc.feignClient.schoolPush;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolPushFeign",path = "/schoolPush")
public interface SchoolPushFeign {
    @PostMapping("/findPageSchoolPushesByAppIdAndSchoolId")
    List<SchoolPush> findPageSchoolPushesByAppIdAndSchoolId(SchoolPush schoolPush);
}
