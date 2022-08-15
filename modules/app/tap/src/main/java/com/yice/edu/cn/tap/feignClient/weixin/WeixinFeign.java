package com.yice.edu.cn.tap.feignClient.weixin;

import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jw",path = "/wx")
public interface WeixinFeign {
    @PostMapping("/jsCode2Session")
    String jsCode2Session(Jscode2session jscode2session);
}
