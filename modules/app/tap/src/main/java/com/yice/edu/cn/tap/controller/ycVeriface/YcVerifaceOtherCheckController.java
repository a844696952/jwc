package com.yice.edu.cn.tap.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.tap.service.ycVeriface.YcVerifaceOtherCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/YcVerifaceOtherCheckController")
@Api(value = "/YcVerifaceOtherCheckController",description = "人脸识别")
public class YcVerifaceOtherCheckController {

    @Autowired
    private YcVerifaceOtherCheckService ycVerifaceOtherCheckService;

    @PostMapping("/checkPersonExistWithoutUserId")
    @ApiOperation(value = "校验人员头像,存在返回用户id,不存在返回null", notes = "学校id和base64图片必传")
    public ResponseJson checkPersonExistWithoutUserId(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        ycEnterBean.setSchoolId(mySchoolId());
        YcVerifaceOtherCheckBean result = ycVerifaceOtherCheckService.checkPersonExistWithoutUserId(ycEnterBean);
        return  new ResponseJson(result);
    }

    @PostMapping("/checkPersonExistByUserId")
    @ApiOperation(value = "校验人员头像与指定人员Id是否符合", notes = "返回真假")
    public ResponseJson checkPersonExistByUserId(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        ycEnterBean.setSchoolId(mySchoolId());
        YcVerifaceOtherCheckBean result = ycVerifaceOtherCheckService.checkPersonExistByUserId(ycEnterBean);
        return  new ResponseJson(result);
    }



}
