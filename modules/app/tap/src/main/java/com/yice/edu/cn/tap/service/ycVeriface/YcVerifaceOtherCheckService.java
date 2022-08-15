package com.yice.edu.cn.tap.service.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import com.yice.edu.cn.tap.feignClient.ycveriface.YcVerifaceOtherCheckFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YcVerifaceOtherCheckService {

    @Autowired
    private YcVerifaceOtherCheckFeign ycVerifaceOtherCheckFeign;


    public YcVerifaceOtherCheckBean checkPersonExistWithoutUserId(YcEnterBean ycEnterBean){
        return ycVerifaceOtherCheckFeign.checkPersonExistWithoutUserId(ycEnterBean);
    }

    public YcVerifaceOtherCheckBean checkPersonExistByUserId(YcEnterBean ycEnterBean){
        return ycVerifaceOtherCheckFeign.checkPersonExistByUserId(ycEnterBean);
    }


}
