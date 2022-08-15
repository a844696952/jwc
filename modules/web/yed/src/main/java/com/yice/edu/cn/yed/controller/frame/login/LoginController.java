package com.yice.edu.cn.yed.controller.frame.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.common.util.vaptch.VaptchaResponse;
import com.yice.edu.cn.common.util.vaptch.VaptchaTest;
import com.yice.edu.cn.yed.service.frame.login.LoginService;
import com.yice.edu.cn.yed.service.system.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;

@RestController
@RequestMapping("/login")
//@RefreshScope
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private VaptchaTest vaptchaTest;
    @Value("${spring.profiles.active}")
    private String profile;


    @PostMapping("/login")
    public ResponseJson login(@RequestBody @Validated(value = GroupOne.class) Admin admin) throws Exception {
        //只在生产环境进行登录vaptcha 的 token验证
        if("prod".equals(profile)){
            if(StrUtil.isEmpty(admin.getToken())){
                return new ResponseJson(false, "required token");
            }
            VaptchaResponse vaptchaResponse = vaptchaTest.verify(admin.getToken());
            if(vaptchaResponse.getSuccess()==0){
                return new ResponseJson(false, "验证码错误");
            }
        }
        Admin exist=loginService.adminLogin(admin);
        if(exist!=null){
            Admin subject = new Admin(){{
                this.setId(exist.getId());
                this.setRealName(exist.getRealName());
                this.setEducationBureauId(exist.getEducationBureauId());
            }};
            exist.setPassword(null);
            String token = JwtUtil.createJWT(exist.getId(), JSONUtil.toJsonStr(subject), null, Constant.Redis.YED_ADMIN_TIMEOUT*1000);
            return new ResponseJson(token,exist);
        }
        return new ResponseJson(false,"错误的用户名或密码");
    }





}
