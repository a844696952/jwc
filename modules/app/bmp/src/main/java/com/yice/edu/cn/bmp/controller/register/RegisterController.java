package com.yice.edu.cn.bmp.controller.register;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.bmp.controller.register
 * @Author: gzw
 * @CreateTime: 2018-09-03 09:48
 * @Description: 实现家长app手机号验证码注册，设置密码
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ParentService parentService;
    @Autowired
    private StudentService studentService;
    @CreateCache(name = Constant.Redis.BMP_VERIFICATION_CODE, expire = 180)
    private Cache<String, String> codeCache;

    //点击注册按钮
    @PostMapping("/parentRegister")
    @ApiOperation(value = "点击注册按钮，传入手机号，验证码，密码", notes = "返回验证结果")

    public ResponseJson parentRegister(
            @ApiParam(value = "家长对象，要传入手机号tel，验证码code ，密码password（加密后）")
            @RequestBody @Validated(GroupThree.class) Parent parent
    ) {
        //1.检查验证码
        boolean verifyFlag = parentService.compareIdentifyingCode(parent.getTel(), parent.getCode());
        if (verifyFlag == false) {
            return new ResponseJson(false, "验证码错误");
        }
        String passWord = DigestUtil.sha1Hex(parent.getPassword());
        parent.setPassword(null);
        //2.检查家长表中是否已经注册
        List<Parent> parents = parentService.findParentListByCondition(parent);
        if (parents.size() != 0) {
            return new ResponseJson(false, "输入的手机号已经注册过，去登陆页");
        }
        parent.setPassword(passWord);
        Parent parent1 = parentService.saveParent(parent);
        parent1.setPassword(null);
        parent1.setPasswordagain(null);
        parent1.setCode(null);
        parent1.setName(null);
        parentService.saveParenToCache(parent1);
        if (StringUtils.isNotEmpty(parent.getOpenId())) {
            parent1.setOpenId(parent.getOpenId());
            parentService.bindOpenIdParent(parent1);
        }
        Parent   parent2 = parentService.findParentById(parent1.getId());
        parent2.setPassword(null);
        parent2.setPasswordagain(null);
        parent2.setCode(null);
        String token = JwtUtil.createJWT(parent2.getId(), "{}", null, -1);
        parentService.saveTokenToCache(parent2.getId()+ Constant.Redis.BMP_TOKEN_SUFFIX, token);
        codeCache.remove(parent.getTel());
        return new ResponseJson(token, parent2);
    }

    //移动客户端请求获得验证码
    @GetMapping("/getIdentifyingCode/{tel}")
    @ApiOperation(value = "点击获取验证码，传入手机号，服务器发送验证码至手机", notes = "返回发送结果")
    public ResponseJson getIdentifyingCode(
            @ApiParam(value = "手机号tel")
            @Pattern(regexp = "^1\\d{10}$", message = "电话号码必须为11位手机号码")
            @PathVariable String tel) {
        //2.检查家长表中是否已经注册
        Parent parent = new Parent();
        parent.setTel(tel);
        List<Parent> parents = parentService.findParentListByCondition(parent);
        if (parents.size() != 0) {
            return new ResponseJson(false, "该手机号已被注册，可以直接登陆哦");
        }
        String code = parentService.getVerificationCode(tel);
        if (!code.equals("")) {
            return new ResponseJson(true, "验证码发送成功");
        }
        return new ResponseJson(false, "验证码发送失败");
    }

  /* //初次设置密码
    @PostMapping("/updateParentPassword")
    @ApiOperation(value = "初次设置密码", notes = "返回设置结果")
    public  ResponseJson updateParentPassword(
            @ApiParam(value = "手机号tel,密码password，确认密码passwordagain")
            @RequestBody   @Validated(GroupTwo.class) Parent parent){
        String tel = parent.getTel();
        if (tel .equals("")) {
            return new ResponseJson(false,"手机号有误");
        }
        //DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD))//注册后端双重加密
        String passWord= DigestUtil.sha1Hex(DigestUtil.md5Hex(parent.getPassword()));
        String passWordAgain=DigestUtil.sha1Hex(DigestUtil.md5Hex(parent.getPasswordagain()));
        if(!passWord.equals(passWordAgain)){
            return new ResponseJson(false,"两次输入的密码不一致，请重新输入");
        }
        parent.setPassword(passWord);
        parent.setPasswordagain(null);
        parentService.saveParent(parent);
        return  new ResponseJson(true,"初次设置密码成功");
    }
*/

}