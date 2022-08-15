package com.yice.edu.cn.pcd.controller.login;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.pcd.service.eehManagement.EehAccountService;
import com.yice.edu.cn.pcd.service.eehManagement.EehTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电教馆平台登录
 */
@RestController
@RequestMapping("/login")
@Api(value = "/login",description = "模块")
public class LoginController {
      @Autowired
      private EehAccountService eehAccountService;
      @Autowired
      private EehTreeService eehTreeService;
      @PostMapping("/login")
      @ApiOperation(value = "通过对象查找", notes = "返回对象")
      public ResponseJson Login(@RequestBody EehAccount eehAccount){
            eehAccount.setStatus("1");
            EehAccount eehAccount1 = eehAccountService.EehAccountLogin(eehAccount);
            if(eehAccount1!=null){
                  EehTree eehTreeById = eehTreeService.findEehTreeById(eehAccount1.getEehId());
                  eehAccount1.setLevel(eehTreeById.getLevel());
                  eehAccount1.setEehName(eehTreeById.getTitle());
                  String token = JwtUtil.createJWT(eehAccount1.getId(), "{}", null, -1);
                  eehAccountService.saveAccountToCache(eehAccount1);
                  eehAccount1.setPassword(null);
                  return new ResponseJson(token,eehAccount1);
            }else {
                  return new ResponseJson(false,"登录失败,用户名或密码错误");
            }

      }

}
