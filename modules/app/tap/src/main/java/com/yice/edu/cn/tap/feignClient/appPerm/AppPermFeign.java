package com.yice.edu.cn.tap.feignClient.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/appPerm")
public interface AppPermFeign {
    @GetMapping("/findAppPermById/{id}")
    AppPerm findAppPermById(@PathVariable("id") String id);
    @PostMapping("/saveAppPerm")
    AppPerm saveAppPerm(AppPerm appPerm);
    @PostMapping("/findAppPermListByCondition")
    List<AppPerm> findAppPermListByCondition(AppPerm appPerm);
    @PostMapping("/findOneAppPermByCondition")
    AppPerm findOneAppPermByCondition(AppPerm appPerm);
    @PostMapping("/findAppPermCountByCondition")
    long findAppPermCountByCondition(AppPerm appPerm);
    @PostMapping("/updateAppPerm")
    void updateAppPerm(AppPerm appPerm);
    @GetMapping("/deleteAppPerm/{id}")
    void deleteAppPerm(@PathVariable("id") String id);
    @PostMapping("/deleteAppPermByCondition")
    void deleteAppPermByCondition(AppPerm appPerm);

    @PostMapping("/findAppPermListTreeByClass")
    List<AppPerm> findAppPermListTreeByClass(AppPerm appPerm);
}
