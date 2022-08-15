package com.yice.edu.cn.osp.service.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceAccountFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceOtherCheckFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class YcVerifaceOtherCheckService {
    @Autowired
    private YcVerifaceOtherCheckFeign ycVerifaceOtherCheckFeign;

    public YcVerifaceOtherCheckBean checkPersonExistWithoutUserId(YcEnterBean ycEnterBean) {
        return ycVerifaceOtherCheckFeign.checkPersonExistWithoutUserId(ycEnterBean);
    }

    public YcVerifaceOtherCheckBean checkPersonExistByUserId(YcEnterBean ycEnterBean) {
        return ycVerifaceOtherCheckFeign.checkPersonExistByUserId(ycEnterBean);
    }
}
