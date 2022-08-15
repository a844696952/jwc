package com.yice.edu.cn.tap.feignClient.ycveriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="dm",path = "/ycVerifaceOtherCheck")
public interface YcVerifaceOtherCheckFeign {

    @PostMapping("/checkPersonExistWithoutUserId")
    YcVerifaceOtherCheckBean checkPersonExistWithoutUserId(
        @ApiParam(value = "对象", required = true)
        @RequestBody YcEnterBean ycEnterBean);

    @PostMapping("/checkPersonExistByUserId")
    YcVerifaceOtherCheckBean checkPersonExistByUserId(
        @ApiParam(value = "对象", required = true)
        @RequestBody YcEnterBean ycEnterBean);



}
