package com.yice.edu.cn.xw.controller.dj.partyMemberActivity;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import com.yice.edu.cn.xw.service.dj.partyMemberActivity.XwDjActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjActivity")
@Api(value = "/xwDjActivity",description = "党建活动表模块")
public class XwDjActivityController {
    @Autowired
    private XwDjActivityService xwDjActivityService;

    @GetMapping("/findXwDjActivityById/{id}")
    @ApiOperation(value = "通过id查找党建活动表", notes = "返回党建活动表对象")
    public XwDjActivity findXwDjActivityById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjActivityService.findXwDjActivityById(id);
    }

    @PostMapping("/findTeacherXwDjActivityById")
    @ApiOperation(value = "活动接收人去查看页面,通过id查找党建活动内容", notes = "返回响应对象", response=XwDjActivity.class)
    public XwDjActivityAndUserVo findTeacherXwDjActivityById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody XwDjActivityAndUserVo xwDjActivityAndUserVo){
        return xwDjActivityService.findTeacherXwDjActivityById(xwDjActivityAndUserVo);
    }

    /**
     * @dscription:新增党建活动
     * @param xwDjActivity
     */
    @PostMapping("/saveXwDjActivity")
    @ApiOperation(value = "保存党建活动表", notes = "返回党建活动表对象")
    public XwDjActivity saveXwDjActivity(
            @ApiParam(value = "党建活动表对象", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivityService.saveXwDjActivity(xwDjActivity);
        return xwDjActivity;
    }

    @PostMapping("/findXwDjActivityIsSendByCondition")
    @ApiOperation(value = "活动发起人查看人数统计列表", notes = "返回党建活动用户表列表")
    public XwDjActivityAndUserVo findXwDjActivityIsSendByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        XwDjActivityAndUserVo xwDjActivityAndUserVo = xwDjActivityService.count(xwDjActivityUser);
        xwDjActivityAndUserVo.setXwDjActivityUsers(xwDjActivityService.findXwDjActivityListByCondition(xwDjActivityUser));
        return xwDjActivityAndUserVo;
    }

    @PostMapping("/findXwDjActivityPartyMemberListByCondition")
    @ApiOperation(value = "党建实践页面查看党建活动列表", notes = "返回党建活动列表")
    public List<XwDjActivity> findXwDjActivityPartyMemberListByCondition(
            @ApiParam(value = "党建活动对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityPartyMemberListByCondition(xwDjActivity);
    }

    @PostMapping("/findXwDjActivityPartyMemberCountByCondition")
    @ApiOperation(value = "党建实践页面查看党建活动列表总数", notes = "列表总数")
    public long findXwDjActivityPartyMemberCountByCondition(
            @ApiParam(value = "long")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityPartyMemberCountByCondition(xwDjActivity);
    }

    @PostMapping("/findXwDjActivityIsSendCountByCondition")
    @ApiOperation(value = "活动发起人查看人数统计列表", notes = "返回党建活动用户表列表")
    public long findXwDjActivityIsSendCountByCondition(
            @ApiParam(value = "党建活动用户表对象")
            @RequestBody XwDjActivityUser xwDjActivityUser){
        long count = xwDjActivityService.findXwDjActivityListCountByCondition(xwDjActivityUser);
        return count;
    }

    @PostMapping("/findXwDjActivityListByCondition")
    @ApiOperation(value = "根据条件查找党建活动表列表", notes = "返回党建活动表列表")
    public List<XwDjActivity> findXwDjActivityListByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityListByCondition(xwDjActivity);
    }
    @PostMapping("/findXwDjActivityTeacherListByCondition")
    @ApiOperation(value = "根据条件查找党建活动表列表", notes = "返回党建活动表列表")
    public List<XwDjActivity> findXwDjActivityTeacherListByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityTeacherListByCondition(xwDjActivity);
    }
    @PostMapping("/findXwDjActivityCountByCondition")
    @ApiOperation(value = "根据条件查找党建活动表列表个数", notes = "返回党建活动表总个数")
    public long findXwDjActivityCountByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityCountByCondition(xwDjActivity);
    }
    @PostMapping("/findXwDjActivityTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找党建活动表列表个数", notes = "返回党建活动表总个数")
    public long findXwDjActivityTeacherCountByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findXwDjActivityTeacherCountByCondition(xwDjActivity);
    }

    @PostMapping("/updateXwDjActivity")
    @ApiOperation(value = "修改党建活动表", notes = "党建活动表对象必传")
    public void updateXwDjActivity(
            @ApiParam(value = "党建活动表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivityService.updateXwDjActivity(xwDjActivity);
    }

    @GetMapping("/deleteXwDjActivity/{id}")
    @ApiOperation(value = "通过id删除党建活动表")
    public void deleteXwDjActivity(
            @ApiParam(value = "党建活动表对象", required = true)
            @PathVariable String id){
        xwDjActivityService.deleteXwDjActivity(id);
    }
    @PostMapping("/deleteXwDjActivityByCondition")
    @ApiOperation(value = "根据条件删除党建活动表")
    public void deleteXwDjActivityByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivityService.deleteXwDjActivityByCondition(xwDjActivity);
    }
    @PostMapping("/findOneXwDjActivityByCondition")
    @ApiOperation(value = "根据条件查找单个党建活动表,结果必须为单条数据", notes = "返回单个党建活动表,没有时为空")
    public XwDjActivity findOneXwDjActivityByCondition(
            @ApiParam(value = "党建活动表对象")
            @RequestBody XwDjActivity xwDjActivity){
        return xwDjActivityService.findOneXwDjActivityByCondition(xwDjActivity);
    }

    @PostMapping("/closeXwDjActivity")
    @ApiOperation(value = "通过id关闭党建活动")
    public void closeXwDjActivity(
            @ApiParam(value = "党建活动表对象", required = true)
            @RequestBody XwDjActivity xwDjActivity){
        xwDjActivityService.closeXwDjActivity(xwDjActivity);
    }
}
