package com.yice.edu.cn.tap.feignClient.appVersion;

import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/appVersionControl")
public interface AppVersionControlFeign {
    @GetMapping("/findAppVersionControlById/{id}")
    AppVersionControl findAppVersionControlById(@PathVariable("id") String id);
    @PostMapping("/saveAppVersionControl")
    AppVersionControl saveAppVersionControl(AppVersionControl appVersionControl);
    @PostMapping("/findAppVersionControlListByCondition")
    List<AppVersionControl> findAppVersionControlListByCondition(AppVersionControl appVersionControl);
    @PostMapping("/findOneAppVersionControlByCondition")
    AppVersionControl findOneAppVersionControlByCondition(AppVersionControl appVersionControl);
    @PostMapping("/findAppVersionControlCountByCondition")
    long findAppVersionControlCountByCondition(AppVersionControl appVersionControl);
    @PostMapping("/updateAppVersionControl")
    void updateAppVersionControl(AppVersionControl appVersionControl);
    @GetMapping("/deleteAppVersionControl/{id}")
    void deleteAppVersionControl(@PathVariable("id") String id);
    @PostMapping("/deleteAppVersionControlByCondition")
    void deleteAppVersionControlByCondition(AppVersionControl appVersionControl);

    @PostMapping("/findVersionControlUptodate")
    AppVersionControl findVersionControlUptodate(AppVersionControl appVersionControl);

}
