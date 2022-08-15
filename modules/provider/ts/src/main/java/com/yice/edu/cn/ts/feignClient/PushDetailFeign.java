package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value="jw",path = "/pushDetail")
public interface PushDetailFeign {
    @PostMapping("/savePushDetail4Push")
    void savePushDetail4Push(PushDetail pushDetail);
}
