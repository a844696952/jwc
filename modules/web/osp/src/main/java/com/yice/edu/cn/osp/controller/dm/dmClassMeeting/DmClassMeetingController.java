package com.yice.edu.cn.osp.controller.dm.dmClassMeeting;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.dm.dmClassMeeting.DmClassMeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassMeeting")
@Api(value = "/dmClassMeeting",description = "电子班牌班会表模块")
public class DmClassMeetingController {
    @Autowired
    private DmClassMeetingService dmClassMeetingService;

    @PostMapping("/saveDmClassMeeting")
    @ApiOperation(value = "保存电子班牌班会表对象", notes = "返回保存好的电子班牌班会表对象", response= DmClassMeeting.class)
    @EccJpush(type = Extras.DM_ECC_MEETING,content = "班级管理班会刷新")
    public ResponseJson saveDmClassMeeting(
            @ApiParam(value = "电子班牌班会表对象", required = true)
            @RequestBody @Validated(value = GroupOne.class) DmClassMeeting dmClassMeeting){
       dmClassMeeting.setSchoolId(mySchoolId());
        try {
            dmClassMeetingService.saveDmClassMeeting(dmClassMeeting);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false,"提交失败，服务器错误");
        }
        return new ResponseJson(true,"提交成功");
    }

    @GetMapping("/update/findDmClassMeetingById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找电子班牌班会表", notes = "返回响应对象", response=DmClassMeeting.class)
    public ResponseJson findDmClassMeetingById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassMeeting dmClassMeeting=dmClassMeetingService.findDmClassMeetingById(id);
        return new ResponseJson(dmClassMeeting);
    }

    @PostMapping("/update/updateDmClassMeeting")
    @ApiOperation(value = "修改电子班牌班会表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_ECC_MEETING,content = "班级管理班会刷新")
    public ResponseJson updateDmClassMeeting(
            @ApiParam(value = "被修改的电子班牌班会表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassMeeting dmClassMeeting){
        dmClassMeeting.setSchoolId(mySchoolId());
        dmClassMeetingService.updateDmClassMeeting(dmClassMeeting);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassMeetingById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找电子班牌班会表", notes = "返回响应对象", response=DmClassMeeting.class)
    public ResponseJson lookDmClassMeetingById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassMeeting dmClassMeeting=dmClassMeetingService.findDmClassMeetingById(id);
        return new ResponseJson(dmClassMeeting);
    }

    @PostMapping("/findDmClassMeetingsByCondition")
    @ApiOperation(value = "根据条件查找电子班牌班会表", notes = "返回响应对象", response=DmClassMeeting.class)
    public ResponseJson findDmClassMeetingsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassMeeting dmClassMeeting){
       dmClassMeeting.setSchoolId(mySchoolId());
        List<DmClassMeeting> data=dmClassMeetingService.findDmClassMeetingListByCondition(dmClassMeeting);
        long count=dmClassMeetingService.findDmClassMeetingCountByCondition(dmClassMeeting);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassMeetingByCondition")
    @ApiOperation(value = "根据条件查找单个电子班牌班会表,结果必须为单条数据", notes = "没有时返回空", response=DmClassMeeting.class)
    public ResponseJson findOneDmClassMeetingByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassMeeting dmClassMeeting){
        DmClassMeeting one=dmClassMeetingService.findOneDmClassMeetingByCondition(dmClassMeeting);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmClassMeeting/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_ECC_MEETING,content = "班级管理班会刷新")
    public ResponseJson deleteDmClassMeeting(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassMeetingService.deleteDmClassMeeting(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmClassMeetingListByCondition")
    @ApiOperation(value = "根据条件查找电子班牌班会表列表", notes = "返回响应对象,不包含总条数", response=DmClassMeeting.class)
    public ResponseJson findDmClassMeetingListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassMeeting dmClassMeeting){
       dmClassMeeting.setSchoolId(mySchoolId());
        List<DmClassMeeting> data=dmClassMeetingService.findDmClassMeetingListByCondition(dmClassMeeting);
        return new ResponseJson(data);
    }



}
