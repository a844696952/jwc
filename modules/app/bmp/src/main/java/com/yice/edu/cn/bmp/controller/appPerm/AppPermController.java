package com.yice.edu.cn.bmp.controller.appPerm;

import com.yice.edu.cn.bmp.interceptor.LoginInterceptor;
import com.yice.edu.cn.bmp.service.appPerm.AppPermService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appPerm")
@Api(value = "/appPerm",description = "app端菜单权限模块")
public class AppPermController {
    @Autowired
    private AppPermService appPermService;



    @PostMapping("/findAppPermListTreeByClass")
    @ApiOperation(value = "whatApp:访问来源查询对应权限(整型、0:原生教师端,1:小程序教师端,2:原生家长端,3:小程序家长端，原生家长端(小程序家长端)需额外传学生学校Id->schoolId)",notes ="返回客户端权限树对象",response = AppPerm.class)
    public ResponseJson findAppPermListTreeByClass(
            @Validated(GroupOne.class)
            @RequestBody AppPerm appPerm
    ){
        ResponseJson responseJson = appPermService.findSchoolExpireOrSchoolYear(LoginInterceptor.mySchoolId());
        if(!responseJson.getResult().isSuccess()){
            return responseJson;
        }
        List<AppPerm> list = appPermService.findAppPermListTreeByClass(appPerm);
        return new ResponseJson(list);
    }

}
