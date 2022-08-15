package com.yice.edu.cn.yed.feignClient.jw.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "appIndexFeign",path = "/appIndex")
public interface AppIndexFeign {
    @GetMapping("/findAppIndexById/{id}")
    AppIndex findAppIndexById(@PathVariable("id") String id);
    @PostMapping("/saveAppIndex")
    AppIndex saveAppIndex(AppIndex appIndex);
    @PostMapping("/findAppIndexListByCondition")
    List<AppIndex> findAppIndexListByCondition(AppIndex appIndex);
    @PostMapping("/findOneAppIndexByCondition")
    AppIndex findOneAppIndexByCondition(AppIndex appIndex);
    @PostMapping("/findAppIndexCountByCondition")
    long findAppIndexCountByCondition(AppIndex appIndex);
    @PostMapping("/updateAppIndex")
    void updateAppIndex(AppIndex appIndex);
    @GetMapping("/deleteAppIndex/{id}")
    void deleteAppIndex(@PathVariable("id") String id);
    @PostMapping("/deleteAppIndexByCondition")
    void deleteAppIndexByCondition(AppIndex appIndex);
    @GetMapping("/moveAppIndexItem/{fromIndex}/{toIndex}/{type}")
    void moveAppIndexItem(@PathVariable("fromIndex")int fromIndex,@PathVariable("toIndex") int toIndex,@PathVariable("type") int type);
}
