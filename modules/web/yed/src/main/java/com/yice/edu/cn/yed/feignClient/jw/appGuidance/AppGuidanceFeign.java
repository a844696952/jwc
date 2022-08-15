package com.yice.edu.cn.yed.feignClient.jw.appGuidance;

import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "appGuidanceFeign",path = "/appGuidance")
public interface AppGuidanceFeign {
    @GetMapping("/findAppGuidanceById/{id}")
    AppGuidance findAppGuidanceById(@PathVariable("id") String id);
    @PostMapping("/saveAppGuidance")
    AppGuidance saveAppGuidance(AppGuidance appGuidance);
    @PostMapping("/findAppGuidanceListByCondition")
    List<AppGuidance> findAppGuidanceListByCondition(AppGuidance appGuidance);
    @PostMapping("/findOneAppGuidanceByCondition")
    AppGuidance findOneAppGuidanceByCondition(AppGuidance appGuidance);
    @PostMapping("/findAppGuidanceCountByCondition")
    long findAppGuidanceCountByCondition(AppGuidance appGuidance);
    @PostMapping("/updateAppGuidance")
    void updateAppGuidance(AppGuidance appGuidance);
    @GetMapping("/deleteAppGuidance/{id}")
    void deleteAppGuidance(@PathVariable("id") String id);
    @PostMapping("/deleteAppGuidanceByCondition")
    void deleteAppGuidanceByCondition(AppGuidance appGuidance);
}
