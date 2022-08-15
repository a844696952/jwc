package com.yice.edu.cn.rpm.controller.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.rpm.service.login.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @CreateCache(name=Constant.Redis.RPM_TEACHER_CACHE,expire =Constant.Redis.RPM_TEACHER_TIMEOUT )
    private Cache<String,Object> loginCache;

    @PostMapping("/login")
    @ApiOperation(value = "1.登录,登录成功后获取到token的值,请在后续的请求里放入请求头,请求头的名字为token，值为登录后获取的值")
    public ResponseJson login(@RequestBody
                                          @ApiParam(value = "只需要tel和password字段,password请使用md5加密后小写")
                                      LoginObj loginObj){
        if(StrUtil.isEmpty(loginObj.getTel())){
            return new ResponseJson(false, "手机号码必填");
        }
        if(StrUtil.isEmpty(loginObj.getPassword())){
            return new ResponseJson(false, "密码必填");
        }
        if(loginObj.getPassword().length()!=32){
            return new ResponseJson(false,"请使用md5加密");
        }
        if(!loginObj.getPassword().toLowerCase().equals(loginObj.getPassword())){
            return new ResponseJson(false,"请使用md5加密成小写字符串");
        }
        Teacher t = new Teacher();
        t.setTel(loginObj.getTel());
        t.setPassword(DigestUtil.sha1Hex(loginObj.getPassword()));
        Teacher exist=loginService.login(t);
        if(exist!=null){
            Teacher subject = new Teacher();
            subject.setId(exist.getId());
            subject.setSchoolId(exist.getSchoolId());
            subject.setSchoolName(exist.getSchoolName());
            subject.setTel(exist.getTel());
            subject.setName(exist.getName());
            subject.setImgUrl(exist.getImgUrl());
            subject.setStatus(exist.getStatus());
            String token = JwtUtil.createJWT(exist.getId(), JSONUtil.toJsonStr(subject), null, -1);
            return new ResponseJson(token,exist);
        }
        return new ResponseJson(false,"错误的用户名或密码");


    }
}
