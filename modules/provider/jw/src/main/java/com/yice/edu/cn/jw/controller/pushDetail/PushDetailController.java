package com.yice.edu.cn.jw.controller.pushDetail;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ts.jpush.*;
import com.yice.edu.cn.jw.service.pushDetail.PushDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pushDetail")
@Api(value = "/pushDetail",description = "推送详情模块")
public class PushDetailController {
    @Autowired
    private PushDetailService pushDetailService;

    @GetMapping("/findPushDetailById/{id}")
    @ApiOperation(value = "通过id查找推送详情", notes = "返回推送详情对象")
    public PushDetail findPushDetailById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pushDetailService.findPushDetailById(id);
    }

    @PostMapping("/savePushDetail")
    @ApiOperation(value = "保存推送详情", notes = "返回推送详情对象")
    public PushDetail savePushDetail(
            @ApiParam(value = "推送详情对象", required = true)
            @RequestBody PushDetail pushDetail){
        pushDetailService.savePushDetail(pushDetail);
        return pushDetail;
    }
    @PostMapping("/savePushDetail4Push")
    @ApiOperation(value = "保存推送详情", notes = "返回推送详情对象")
    public void savePushDetail4Push(
            @ApiParam(value = "推送详情对象", required = true)
            @RequestBody PushDetail pushDetail){
        pushDetailService.savePushDetail4Push(pushDetail);
    }

    @PostMapping("/findPushDetailListByCondition")
    @ApiOperation(value = "根据条件查找推送详情列表", notes = "返回推送详情列表")
    public List<PushDetail> findPushDetailListByCondition(
            @ApiParam(value = "推送详情对象")
            @RequestBody PushDetail pushDetail){
        return pushDetailService.findPushDetailListByCondition(pushDetail);
    }
    @PostMapping("/findPushDetailCountByCondition")
    @ApiOperation(value = "根据条件查找推送详情列表个数", notes = "返回推送详情总个数")
    public long findPushDetailCountByCondition(
            @ApiParam(value = "推送详情对象")
            @RequestBody PushDetail pushDetail){
        return pushDetailService.findPushDetailCountByCondition(pushDetail);
    }

    @PostMapping("/updatePushDetail")
    @ApiOperation(value = "修改推送详情", notes = "推送详情对象必传")
    public void updatePushDetail(
            @ApiParam(value = "推送详情对象,对象属性不为空则修改", required = true)
            @RequestBody PushDetail pushDetail){
        pushDetailService.updatePushDetail(pushDetail);
    }

    @GetMapping("/deletePushDetail/{id}")
    @ApiOperation(value = "通过id删除推送详情")
    public void deletePushDetail(
            @ApiParam(value = "推送详情对象", required = true)
            @PathVariable String id){
        pushDetailService.deletePushDetail(id);
    }
    @PostMapping("/deletePushDetailByCondition")
    @ApiOperation(value = "根据条件删除推送详情")
    public void deletePushDetailByCondition(
            @ApiParam(value = "推送详情对象")
            @RequestBody PushDetail pushDetail){
        pushDetailService.deletePushDetailByCondition(pushDetail);
    }
    @PostMapping("/findOnePushDetailByCondition")
    @ApiOperation(value = "根据条件查找单个推送详情,结果必须为单条数据", notes = "返回单个推送详情,没有时为空")
    public PushDetail findOnePushDetailByCondition(
            @ApiParam(value = "推送详情对象")
            @RequestBody PushDetail pushDetail){
        return pushDetailService.findOnePushDetailByCondition(pushDetail);
    }

//    =========================================================
    @PostMapping("/updatePushDetail2Read")
    @ApiOperation(value = "设置消息已读")
    public void updatePushDetail2Read(@RequestBody Receiver receiver){
        pushDetailService.updatePushDetail2Read(receiver);
    }
    @PostMapping("/findPushDetailListByCondition4Receiver")
    @ApiOperation(value = "根据条件查找推送详情列表", notes = "返回推送详情列表")
    public List<PushDetail> findPushDetailListByCondition4Receiver(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailListByCondition4Receiver(receiver);
    }
    @PostMapping("/findPushDetailList4Work")
    @ApiOperation(value = "查询工作内容推送消息", notes = "返回推送详情列表")
    public List<PushDetail> findPushDetailList4Work(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailList4Work(receiver);
    }
    @PostMapping("/findPushDetailList4InOutSchool")
    @ApiOperation(value = "查询出入校推送消息", notes = "返回推送详情列表")
    public List<PushDetail> findPushDetailList4InOutSchool(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailList4InOutSchool(receiver);
    }

    @PostMapping("/findPushActiveList")
    @ApiOperation(value = "查询活动推送列表", notes = "返回推送详情列表")
    public List<PushDetail> findPushActiveList(@RequestBody Receiver receiver){
        return pushDetailService.findPushActiveList (receiver);
    }

    @PostMapping("/findPushDetailAllList")
    @ApiOperation(value = "查询所有的推送列表", notes = "返回推送详情列表")
    public List<PushDetail> findPushDetailAllList(@RequestBody Receiver receiver){
        return pushDetailService.findPushDetailAllList (receiver);
    }

    @PostMapping("/findPushDetailCount")
    @ApiOperation(value = "查询所有的推送总数", notes = "总数")
    public long  findPushDetailCount(@RequestBody Receiver receiver){
        return pushDetailService.findPushDetailCount (receiver);
    }




    @PostMapping("/findPushActiveCount")
    @ApiOperation(value = "查询活动推送总数", notes = "返回推送详情总数")
    public long findPushActiveCount(@RequestBody Receiver receiver){
        return pushDetailService.findPushActiveCount (receiver);
    }



    @PostMapping("/findPushDetailCountByCondition4Receiver")
    @ApiOperation(value = "根据条件查找推送详情列表个数", notes = "返回推送详情总个数")
    public long findPushDetailCountByCondition4Receiver(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailCountByCondition4Receiver(receiver);
    }
    @PostMapping("/findPushDetailCount4Work")
    @ApiOperation(value = "查询工作内容推送消息数量", notes = "返回推送详情总个数")
    public long findPushDetailCount4Work(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailCount4Work(receiver);
    }
    @PostMapping("/findPushDetailCount4InOutSchool")
    @ApiOperation(value = "查询工作内容推送消息数量", notes = "返回推送详情总个数")
    public long findPushDetailCount4InOutSchool(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findPushDetailCount4InOutSchool(receiver);
    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/test")
    public void test(){
        //处理推送
        String[] alias = {"1753688335901876224","1774760235730034688"};
        String title = "测试新推送";
        String alert = "测试新通知推送"+DateUtil.now();
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(alias,title,alert,Extras.SYSTEM_BROADCAST_HOMEWORK);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }
    @GetMapping("/test1")
    public void test1(){
        Receiver receiver = new Receiver();
        receiver.setReceiverId("1753688335901876224");
        receiver.setPushId("1555990832");
        this.updatePushDetail2Read(receiver);
    }
    @GetMapping("/test2")
    public List<PushDetail> test2(){
        Receiver a = new Receiver("1753688335901876224",true);
        return pushDetailService.findPushDetailListByCondition4Receiver(a);
    }

    @PostMapping("/findDyPushDetailListByConditionReceiver")
    @ApiOperation(value = "根据条件查找德育推送详情列表", notes = "返回推送详情列表")
    public List<PushDetail> findDyPushDetailListByConditionReceiver(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findDyPushDetailListByConditionReceiver(receiver);
    }
    @PostMapping("/findSchoolActivePushDetailListByConditionReceiver")
    @ApiOperation(value = "根据条件查找校园活动推送详情列表", notes = "返回推送详情列表")
    public List<PushDetail> findSchoolActivePushDetailListByConditionReceiver(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findSchoolActivePushDetailListByConditionReceiver(receiver);
    }
    @PostMapping("/findSchoolActivePushDetailListByConditionTeacherReceiver")
    @ApiOperation(value = "根据条件查找校园活动推送详情列表", notes = "返回推送详情列表")
    public List<PushDetail> findSchoolActivePushDetailListByConditionTeacherReceiver(
            @ApiParam(value = "推送详情对象")
            @RequestBody Receiver receiver){
        return pushDetailService.findSchoolActivePushDetailListByConditionTeacherReceiver(receiver);
    }
}
