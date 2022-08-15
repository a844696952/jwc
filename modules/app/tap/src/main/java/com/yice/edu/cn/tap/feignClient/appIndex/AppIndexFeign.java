package com.yice.edu.cn.tap.feignClient.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="jw",path = "/appIndex")
public interface AppIndexFeign {
    @GetMapping("/getAppIndexesForTeacher/{schoolId}/{id}")
    List<AppIndex> getAppIndexesForTeacher(@PathVariable("schoolId") String schoolId,@PathVariable("id") String id);

    @GetMapping("/getCountLeaveSchool/{schoolId}/{id}")
    Long getCountLeaveSchool(@PathVariable("schoolId") String schoolId,@PathVariable("id") String id);

}
