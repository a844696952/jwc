package com.yice.edu.cn.osp.controller.xw.dj.partyMemberActivity;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberActivity.XwDjActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwDjActivity")
@Api(value = "/xwDjActivity",description = "党建活动内容模块")
public class XwDjActivityController {
    @Autowired
    private XwDjActivityService xwDjActivityService;

    @PostMapping("/saveXwDjActivity")
    @ApiOperation(value = "保存党建活动内容对象", notes = "返回保存好的党建活动内容对象", response= XwDjActivity.class)
    public ResponseJson saveXwDjActivity(
            @ApiParam(value = "党建活动内容对象", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivity.setActivityCreatorId(myId());
        xwDjActivity.setSchoolId(mySchoolId());
        xwDjActivity.setActivityCreateDate(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        xwDjActivity.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        XwDjActivity s=xwDjActivityService.saveXwDjActivity(xwDjActivity);
        return new ResponseJson(s);
    }

    /**
     * @Description 查询活动详情
     * @Date  2019/5/16 13:08
     * @Param [id]
     * @return com.yice.edu.cn.common.pojo.ResponseJson
     **/

    @GetMapping("/update/findXwDjActivityById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson findXwDjActivityById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjActivity xwDjActivity=xwDjActivityService.findXwDjActivityById(id);
        return new ResponseJson(xwDjActivity);
    }

    @PostMapping("/update/updateXwDjActivity")
    @ApiOperation(value = "修改党建活动内容对象", notes = "返回响应对象")
    public ResponseJson updateXwDjActivity(
            @ApiParam(value = "被修改的党建活动内容对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivity.setSchoolId(mySchoolId());
        xwDjActivityService.updateXwDjActivity(xwDjActivity);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjActivityById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson lookXwDjActivityById(
            @ApiParam(value = "活动发起人去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjActivity xwDjActivity=xwDjActivityService.findXwDjActivityById(id);
        return new ResponseJson(xwDjActivity);
    }

    @GetMapping("/look/lookTeacherXwDjActivityById/{id}")
    @ApiOperation(value = "活动接收人去查看页面,通过id查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson lookTeacherXwDjActivityById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjActivityAndUserVo xwDjActivityAndUserVo = new XwDjActivityAndUserVo();
        xwDjActivityAndUserVo.setId(id);
        xwDjActivityAndUserVo.setActivityUserId(LoginInterceptor.myId());
        XwDjActivityAndUserVo xwDjActivity=xwDjActivityService.findTeacherXwDjActivityById(xwDjActivityAndUserVo);
        return new ResponseJson(xwDjActivity);
    }

    @PostMapping("/look/lookXwDjActivity")
    @ApiOperation(value = "活动接收人去查看页面,通过id查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson lookXwDjActivity(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody XwDjActivityAndUserVo xwDjActivityAndUserVo){
        XwDjActivityAndUserVo xwDjActivity=xwDjActivityService.findTeacherXwDjActivityById(xwDjActivityAndUserVo);
        return new ResponseJson(xwDjActivity);
    }

    /**
     * @description:查询发送列表
     * @param xwDjActivity
     * @return
     */
    @PostMapping("/findXwDjActivitysByCondition")
    @ApiOperation(value = "根据条件查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson findXwDjActivitysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivity xwDjActivity){
        if(StringUtils.isNotEmpty(xwDjActivity.getIsAdmin()) && Objects.equals(Constant.IS_ADMIN.IS_ADMIN,xwDjActivity.getIsAdmin())){
            xwDjActivity.setActivityCreatorId(null);
        }else{
            xwDjActivity.setActivityCreatorId(myId());
        }
        xwDjActivity.setSchoolId(mySchoolId());

        List<XwDjActivity> data=xwDjActivityService.findXwDjActivityListByCondition(xwDjActivity);
        long count=xwDjActivityService.findXwDjActivityCountByCondition(xwDjActivity);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findXwDjActivityIsSendByCondition")
    @ApiOperation(value = "根据条件查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public ResponseJson findXwDjActivityIsSendByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivityUser xwDjActivityUser){
        xwDjActivityUser.setSchoolId(mySchoolId());
        XwDjActivityAndUserVo data=xwDjActivityService.findXwDjActivityIsSendByCondition(xwDjActivityUser);
        long count=xwDjActivityService.findXwDjActivityIsSendCountByCondition(xwDjActivityUser);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findXwDjActivityPartyMemberListByCondition")
    @ApiOperation(value = "党建实践页面查看党建活动列表", notes = "返回响应对象")
    public ResponseJson findXwDjActivityPartyMemberListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivity xwDjActivity){
        List<XwDjActivity> data=xwDjActivityService.findXwDjActivityPartyMemberListByCondition(xwDjActivity);
        long count=xwDjActivityService.findXwDjActivityPartyMemberCountByCondition(xwDjActivity);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneXwDjActivityByCondition")
    @ApiOperation(value = "根据条件查找单个党建活动内容,结果必须为单条数据", notes = "没有时返回空", response=XwDjActivity.class)
    public ResponseJson findOneXwDjActivityByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjActivity xwDjActivity){
        XwDjActivity one=xwDjActivityService.findOneXwDjActivityByCondition(xwDjActivity);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwDjActivity/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjActivity(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwDjActivityService.deleteXwDjActivity(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjActivityListByCondition")
    @ApiOperation(value = "根据条件查找党建活动内容列表", notes = "返回响应对象,不包含总条数", response=XwDjActivity.class)
    public ResponseJson findXwDjActivityListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjActivity xwDjActivity){
        List<XwDjActivity> data=xwDjActivityService.findXwDjActivityListByCondition(xwDjActivity);
        return new ResponseJson(data);
    }

    @GetMapping("/update/closeXwDjActivity/{id}")
    @ApiOperation(value = "通过id关闭党建活动")
    public ResponseJson closeXwDjActivity(
            @ApiParam(value = "党建活动表对象id", required = true)
            @PathVariable String id){
        xwDjActivityService.closeXwDjActivity(id,myId());
        return new ResponseJson();
    }


}
