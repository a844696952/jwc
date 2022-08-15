package com.yice.edu.cn.tap.controller.appPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.appPerm.AppPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/appPerm")
@Api(value = "/appPerm",description = "app端菜单权限模块")
public class AppPermController {
    @Autowired
    private AppPermService appPermService;

    @PostMapping("/findAppPermListTreeByClass")
    @ApiOperation(value = "whatApp:访问来源查询对应权限(整型、0:原生教师端,1:小程序教师端,2:原生家长端,3:小程序家长端)",notes ="返回客户端权限树对象",response = AppPerm.class)
    public ResponseJson findAppPermListTreeByClass(
            @ApiParam(value = "访问来源类型",required = true)
            @Validated(GroupOne.class)
            @RequestBody AppPerm appPerm
    ){
        ResponseJson responseJson = appPermService.findSchoolExpireOrSchoolYear(LoginInterceptor.mySchoolId());
        if (!responseJson.getResult().isSuccess()){
            return responseJson;
        }
        appPerm.setTeacherId(LoginInterceptor.myId());
        List<AppPerm> list = appPermService.findAppPermListTreeByClass(appPerm);
        return new ResponseJson(list);
    }

}
