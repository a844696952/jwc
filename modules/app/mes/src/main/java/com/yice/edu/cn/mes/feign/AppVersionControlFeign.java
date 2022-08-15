package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jw", path = "/appVersionControl")
public interface AppVersionControlFeign {
    @PostMapping("/findVersionControlUptodate")
    AppVersionControl findVersionControlUptodate(AppVersionControl appVersionControl);
}
