package com.yice.edu.cn.tap.feignClient.qrcode;

import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="ewb",contextId = "QrCodeFeign",path = "/login")
public interface QrCodeFeign {

    @PostMapping("/loginByQRcode")
     ResponseJson loginByQRCode(@RequestParam("tel")String tel, @RequestParam("qrCode")String qrCode);


}
