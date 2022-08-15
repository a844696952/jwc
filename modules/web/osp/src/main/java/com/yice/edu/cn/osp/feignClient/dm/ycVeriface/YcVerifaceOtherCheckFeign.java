package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="dm",contextId = "YcVerifaceOtherCheckFeign",path = "/ycVerifaceOtherCheck")
public interface YcVerifaceOtherCheckFeign {
    @PostMapping("/checkPersonExistWithoutUserId")
    YcVerifaceOtherCheckBean checkPersonExistWithoutUserId(YcEnterBean ycEnterBean);
    @PostMapping("/checkPersonExistByUserId")
    YcVerifaceOtherCheckBean checkPersonExistByUserId(YcEnterBean ycEnterBean);
}
