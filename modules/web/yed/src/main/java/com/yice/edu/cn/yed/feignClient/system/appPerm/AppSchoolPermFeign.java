package com.yice.edu.cn.yed.feignClient.system.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "appSchoolPermFeign",path = "/appSchoolPerm")
public interface AppSchoolPermFeign {
    @GetMapping("/findAppSchoolPermById/{id}")
    AppSchoolPerm findAppSchoolPermById(@PathVariable("id") String id);
    @PostMapping("/saveAppSchoolPerm")
    AppSchoolPerm saveAppSchoolPerm(AppSchoolPerm appSchoolPerm);
    @PostMapping("/findAppSchoolPermListByCondition")
    List<AppSchoolPerm> findAppSchoolPermListByCondition(AppSchoolPerm appSchoolPerm);
    @PostMapping("/findOneAppSchoolPermByCondition")
    AppSchoolPerm findOneAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm);
    @PostMapping("/findAppSchoolPermCountByCondition")
    long findAppSchoolPermCountByCondition(AppSchoolPerm appSchoolPerm);
    @PostMapping("/updateAppSchoolPerm")
    void updateAppSchoolPerm(AppSchoolPerm appSchoolPerm);
    @GetMapping("/deleteAppSchoolPerm/{id}")
    void deleteAppSchoolPerm(@PathVariable("id") String id);
    @PostMapping("/deleteAppSchoolPermByCondition")
    void deleteAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm);
}
