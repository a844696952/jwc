package com.yice.edu.cn.bmp.controller.forget;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/forget")
public class forgetController {
    @Autowired
    private ParentService parentService;

    @PostMapping("/updateParentPassword")  //修改密码
    @ApiOperation(value = "修改密码，传入密码的值", notes = "返回结果")
    public  ResponseJson updateParentPassword(@ApiParam(value = "例如__:{tel:'*****',password:'*****',passwordagain:'*****'}") @RequestBody @Validated(GroupTwo.class) Parent parent){
        String passWord= DigestUtil.sha1Hex(parent.getPassword());
        String passWordAgain=DigestUtil.sha1Hex(parent.getPasswordagain());
        if(!passWord.equals(passWordAgain)){
            return new ResponseJson(false,"两次输入的密码不一致，请重新输入");
        }
           parent.setPassword(passWord);
           parentService.updatePassword(parent);
           return  new ResponseJson(true,"修改成功");
    }

    @PostMapping("/updateParentPasswordInCenter")  //个人中心修改密码
    @ApiOperation(value = "个人中心修改密码，传入新旧密码的值", notes = "返回结果")
    public  ResponseJson updateParentPasswordInCenter(@ApiParam(value = "例如__:{tel:'*****',oldPassword:'*****',password:'*****',passwordagain:'*****'}")
                                                          @RequestBody @Validated(GroupTwo.class) Parent parent){
        Parent p = new Parent();
        p.setTel(parent.getTel());
        p.setPassword(DigestUtil.sha1Hex(parent.getOldPassword()));
      List<Parent> parentList = parentService.findParentListByCondition(p);
        System.out.println(parentList);
        if(parentList.size() == 1){
            String passWord= DigestUtil.sha1Hex(parent.getPassword());
            String passWordAgain=DigestUtil.sha1Hex(parent.getPasswordagain());
            if(!passWord.equals(passWordAgain)){
                return new ResponseJson(false,"两次输入的密码不一致，请重新输入");
            }
            parent.setPassword(passWord);
            parentService.updatePassword(parent);
            return  new ResponseJson(true,"修改成功");
        }

       return new ResponseJson(false,"您输入的旧密码有误");
    }

}
