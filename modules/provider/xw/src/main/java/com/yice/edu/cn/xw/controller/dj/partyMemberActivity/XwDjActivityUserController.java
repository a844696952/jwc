package com.yice.edu.cn.xw.controller.dj.partyMemberActivity;

import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.xw.service.dj.partyMemberActivity.XwDjActivityUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjActivityUser")
@Api(value = "/xwDjActivityUser",description = "党建活动用户表模块")
public class XwDjActivityUserController {
    @Autowired
    private XwDjActivityUserService xwDjActivityUserService;

    @GetMapping("/findXwDjActivityUserById/{id}")
    @ApiOperation(value = "通过id查找党建活动用户表", notes = "返回党建活动用户表对象")
    public XwDjActivityUser findXwDjActivityUserById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjActivityUserService.findXwDjActivityUserById(id);
    }

    @GetMapping("/findXwDjActivityUserByActivityUserId/{id}")
    @ApiOperation(value = "通过教师id查找党建活动用户表", notes = "返回党建活动用户表对象")
    public boolean findXwDjActivityUserByActivityUserId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjActivityUserService.findXwDjActivityUserByActivityUserId(id);
    }


    @PostMapping("/saveXwDjActivityUser")
    @ApiOperation(value = "保存党建活动用户表", notes = "返回党建活动用户表对象")
    public XwDjActivityUser saveXwDjActivityUser(
            @ApiParam(value = "党建活动用户表对象", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUserService.saveXwDjActivityUser(xwDjActivityUser);
        return xwDjActivityUser;
    }

    /**
     * @description:查询党建接受方列表
     * @param xwDjActivityUser
     * @return
     */
    @PostMapping("/findXwDjActivityUserListByCondition")
    @ApiOperation(value = "根据条件查找党建活动用户表列表", notes = "返回党建活动用户表列表")
    public List<XwDjActivityAndUserVo> findXwDjActivityUserListByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserListByCondition(xwDjActivityUser);
    }

    @PostMapping("/findXwDjActivityUserListCondition")
    @ApiOperation(value = "根据条件查找党建活动用户表列表", notes = "返回党建活动用户表列表")
    public List<XwDjActivityUser> findXwDjActivityUserListCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserListCondition(xwDjActivityUser);
    }


    /**
     * @description:查询党建接受方总数
     * @param xwDjActivityUser
     * @return
     */
    @PostMapping("/findXwDjActivityUserListCountByCondition")
    @ApiOperation(value = "签到负责人根据条件查找党建活动已报名用户表总数", notes = "返回党建活动用户表列表")
    public long findXwDjActivityUserListCountByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }
    @PostMapping("/findXwDjActivityUserListCountCondition")
    @ApiOperation(value = "签到负责人根据条件查找党建活动已报名用户表总数", notes = "返回党建活动用户表列表")
    public long findXwDjActivityUserListCountCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserListCountCondition(xwDjActivityUser);
    }


    @PostMapping("/saveBatchXwDjActivityUser")
    @ApiOperation(value = "保存党建活动用户对象", notes = "返回保存好的党建活动用户对象", response= XwDjActivityUser.class)
    public void saveBatchXwDjActivityUser(
            @ApiParam(value = "党建活动用户对象", required = true)
            @RequestBody List<XwDjActivityUser> list){
        xwDjActivityUserService.saveBatchXwDjActivityUser(list);
    }

    @PostMapping("/findXwDjActivityUserSignUpListByCondition")
    @ApiOperation(value = "签到负责人根据条件查找党建活动已报名用户表列表", notes = "返回党建活动用户表列表")
    public XwDjActivityAndUserVo findXwDjActivityUserSignUpListByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        XwDjActivityAndUserVo xwDjActivityAndUserVo = xwDjActivityUserService.count(xwDjActivityUser);
        xwDjActivityAndUserVo.setXwDjActivityUsers(xwDjActivityUserService.findXwDjActivityUserSignUpListByCondition(xwDjActivityUser));
        return xwDjActivityAndUserVo;
    }

    @PostMapping("/findXwDjActivityUserSignUpListCountByCondition")
    @ApiOperation(value = "签到负责人根据条件查找党建活动已报名用户表总数", notes = "返回党建活动用户表列表")
    public long findXwDjActivityUserSignUpListCountByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findSignUpUserListCountByCondition(xwDjActivityUser);
    }
    @PostMapping("/findXwDjActivityUserCountByCondition")
    @ApiOperation(value = "根据条件查找党建活动用户表列表个数", notes = "返回党建活动用户表总个数")
    public long findXwDjActivityUserCountByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }

    @PostMapping("/updateXwDjActivityUser")
    @ApiOperation(value = "修改党建活动用户表", notes = "党建活动用户表对象必传")
    public void updateXwDjActivityUser(
            @ApiParam(value = "党建活动用户表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUserService.updateXwDjActivityUser(xwDjActivityUser);
    }

    @PostMapping("/askForLeave")
    @ApiOperation(value = "修改党建活动用户表", notes = "党建活动用户表对象必传")
    public void askForLeave(
            @ApiParam(value = "党建活动用户表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUserService.askForLeave(xwDjActivityUser);
    }
    @PostMapping("/signUp")
    @ApiOperation(value = "修改党建活动用户表", notes = "党建活动用户表对象必传")
    public void signUp(
            @ApiParam(value = "党建活动用户表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUserService.signUp(xwDjActivityUser);
    }
    @GetMapping("/deleteXwDjActivityUser/{id}")
    @ApiOperation(value = "通过id删除党建活动用户表")
    public void deleteXwDjActivityUser(
            @ApiParam(value = "党建活动用户表对象", required = true)
            @PathVariable String id){
        xwDjActivityUserService.deleteXwDjActivityUser(id);
    }
    @PostMapping("/deleteXwDjActivityUserByCondition")
    @ApiOperation(value = "根据条件删除党建活动用户表")
    public void deleteXwDjActivityUserByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUserService.deleteXwDjActivityUserByCondition(xwDjActivityUser);
    }
    @PostMapping("/findOneXwDjActivityUserByCondition")
    @ApiOperation(value = "根据条件查找单个党建活动用户表,结果必须为单条数据", notes = "返回单个党建活动用户表,没有时为空")
    public XwDjActivityUser findOneXwDjActivityUserByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findOneXwDjActivityUserByCondition(xwDjActivityUser);
    }

    @PostMapping("/findXwDjActivityUserCount")
    @ApiOperation(value = "根据条件查找党建活动用户", notes = "返回响应对象", response=XwDjActivityUser.class)
    public long findXwDjActivityUserCount(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        return xwDjActivityUserService.findXwDjActivityUserCountByCondition(xwDjActivityUser);
    }
    @GetMapping("/findXwDjActivityUserSignInQRCode/{id}")
    @ApiOperation(value = "根据活动ID生成签到二维码", notes = "返回响应对象", response=XwDjActivityUser.class)
    public String findXwDjActivityUserSignInQRCode(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @PathVariable String id){
        return xwDjActivityUserService.findXwDjActivityUserSignInQRCode(id);
    }
    @PostMapping("/signIn")
    @ApiOperation(value = "签到", notes = "党建活动用户表对象必传")
    public JSONObject signIn(
            @ApiParam(value = "党建活动用户表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivityAndUserVo xwDjActivityAndUserVo){
        return xwDjActivityUserService.signIn(xwDjActivityAndUserVo);
    }
}
