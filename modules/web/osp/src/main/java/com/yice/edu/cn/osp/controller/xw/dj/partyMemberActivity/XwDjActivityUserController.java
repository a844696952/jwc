package com.yice.edu.cn.osp.controller.xw.dj.partyMemberActivity;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberActivity.XwDjActivityUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjActivityUser")
@Api(value = "/xwDjActivityUser",description = "党建活动用户模块")
public class XwDjActivityUserController {
    @Autowired
    private XwDjActivityUserService xwDjActivityUserService;

    @PostMapping("/saveXwDjActivityUser")
    @ApiOperation(value = "保存党建活动用户对象", notes = "返回保存好的党建活动用户对象", response= XwDjActivityUser.class)
    public ResponseJson saveXwDjActivityUser(
            @ApiParam(value = "党建活动用户对象", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setSchoolId(mySchoolId());
        xwDjActivityUser.setSchoolId(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        XwDjActivityUser s=xwDjActivityUserService.saveXwDjActivityUser(xwDjActivityUser);
        return new ResponseJson(s);
    }
    @GetMapping("/findXwDjActivityUserByActivityUserId/{id}")
    @ApiOperation(value = "通过教师id查找党建活动用户表", notes = "返回党建活动用户表对象")
    public boolean findXwDjActivityUserByActivityUserId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjActivityUserService.findXwDjActivityUserByActivityUserId(id);
    }
    @PostMapping("/saveBatchXwDjActivityUser")
    @ApiOperation(value = "保存党建活动用户对象", notes = "返回保存好的党建活动用户对象", response= XwDjActivityUser.class)
    public ResponseJson saveBatchXwDjActivityUser(
            @ApiParam(value = "党建活动用户对象", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivity.setSchoolId(mySchoolId());
        xwDjActivityUserService.saveBatchXwDjActivityUser(xwDjActivity);
        return new ResponseJson();
    }

    @GetMapping("/update/findXwDjActivityUserById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUserById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjActivityUser xwDjActivityUser=xwDjActivityUserService.findXwDjActivityUserById(id);
        return new ResponseJson(xwDjActivityUser);
    }

    @PostMapping("/update/updateXwDjActivityUser")
    @ApiOperation(value = "修改党建活动用户对象", notes = "返回响应对象")
    public ResponseJson updateXwDjActivityUser(
            @ApiParam(value = "被修改的党建活动用户对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setActivityUserId(myId());
        xwDjActivityUserService.updateXwDjActivityUser(xwDjActivityUser);
        return new ResponseJson();
    }

    @PostMapping("/update/askForLeave")
    @ApiOperation(value = "请假", notes = "返回响应对象")
    public ResponseJson askForLeave(
            @ApiParam(value = "被修改的党建活动用户对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setActivityUserId(LoginInterceptor.myId());
        xwDjActivityUserService.askForLeave(xwDjActivityUser);
        return new ResponseJson();
    }

    @PostMapping("/update/signUp")
    @ApiOperation(value = "报名", notes = "返回响应对象")
    public ResponseJson signUp(
            @ApiParam(value = "被修改的党建活动用户对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setActivityUserId(LoginInterceptor.myId());
        xwDjActivityUserService.signUp(xwDjActivityUser);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjActivityUserById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public ResponseJson lookXwDjActivityUserById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjActivityUser xwDjActivityUser=xwDjActivityUserService.findXwDjActivityUserById(id);
        return new ResponseJson(xwDjActivityUser);
    }

    @GetMapping("/look/findXwDjActivityUserSignInQRCode/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUserSignInQRCode(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        String data=xwDjActivityUserService.findXwDjActivityUserSignInQRCode(id);
        return new ResponseJson(data);
    }

    /**
    *@Description 查询活动接受列表
    *@Param [xwDjActivityUser]
    *@Return com.yice.edu.cn.common.pojo.ResponseJson
    *@Date 2019/5/15
    *@Time 9:38
    */
    @PostMapping("/findXwDjActivityUsersByCondition")
    @ApiOperation(value = "根据条件查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUsersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setSchoolId(mySchoolId());
        xwDjActivityUser.setActivityUserId(myId());
        List<XwDjActivityAndUserVo> data=xwDjActivityUserService.findXwDjActivityUserListByCondition(xwDjActivityUser);
        long count=xwDjActivityUserService.findXwDjActivityUserListCountByCondition(xwDjActivityUser);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneXwDjActivityUserByCondition")
    @ApiOperation(value = "根据条件查找单个党建活动用户,结果必须为单条数据", notes = "没有时返回空", response=XwDjActivityUser.class)
    public ResponseJson findOneXwDjActivityUserByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        XwDjActivityUser one=xwDjActivityUserService.findOneXwDjActivityUserByCondition(xwDjActivityUser);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwDjActivityUser/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjActivityUser(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwDjActivityUserService.deleteXwDjActivityUser(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjActivityUserListCondition")
    @ApiOperation(value = "根据条件查找党建活动用户列表", notes = "返回响应对象,不包含总条数", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUserListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        List<XwDjActivityUser> data=xwDjActivityUserService.findXwDjActivityUserListCondition(xwDjActivityUser);
        long count=xwDjActivityUserService.findXwDjActivityUserListCountCondition(xwDjActivityUser);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findXwDjActivityUserSignUpListByCondition")
    @ApiOperation(value = "签到负责人根据条件查找已报名党建活动用户列表", notes = "返回响应对象,不包含总条数", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUserSignUpListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        XwDjActivityAndUserVo data=xwDjActivityUserService.findXwDjActivityUserSignUpListByCondition(xwDjActivityUser);
        long count=xwDjActivityUserService.findXwDjActivityUserSignUpListCountByCondition(xwDjActivityUser);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findXwDjActivityUserCount")
    @ApiOperation(value = "根据条件查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public ResponseJson findXwDjActivityUserCount(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setSchoolId(mySchoolId());
        long data =xwDjActivityUserService.findXwDjActivityUserCount(xwDjActivityUser);
        return new ResponseJson(data);
    }

}
