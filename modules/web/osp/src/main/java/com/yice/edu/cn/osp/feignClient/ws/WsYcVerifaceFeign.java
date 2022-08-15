package com.yice.edu.cn.osp.feignClient.ws;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.general.region.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@FeignClient(value="ws",contextId = "wsYcVerifaceFeign",path = "/wsYcVeriface")
public interface WsYcVerifaceFeign {
    @PostMapping("/toldDevicePeopleHaveChange")
    void toldDevicePeopleHaveChange(YcVerifaceDevice userList);
}
