package com.yice.edu.cn.dm.controller.ycVeriface;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver.YcVerifaceAlarmReceiver;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceAlarmReceiverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ycVerifaceAlarmReceiver")
@Api(value = "/ycVerifaceAlarmReceiver", description = "人脸识别预警接收人员")
public class YcVerifaceAlarmReceiverController {
    @Autowired
    private YcVerifaceAlarmReceiverService ycVerifaceAlarmReceiverService;

    @GetMapping("/findYcVerifaceAlarmReceiverById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceAlarmReceiver findYcVerifaceAlarmReceiverById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverById(id);
    }

    @PostMapping("/saveYcVerifaceAlarmReceiver")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceAlarmReceiver saveYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverService.saveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
        return ycVerifaceAlarmReceiver;
    }

    @PostMapping("/findYcVerifaceAlarmReceiverListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceAlarmReceiver> findYcVerifaceAlarmReceiverListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverListByCondition(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/findYcVerifaceAlarmReceiverCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceAlarmReceiverCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverCountByCondition(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/updateYcVerifaceAlarmReceiver")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverService.updateYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
    }

    @GetMapping("/deleteYcVerifaceAlarmReceiver/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        ycVerifaceAlarmReceiverService.deleteYcVerifaceAlarmReceiver(id);
    }

    @PostMapping("/deleteYcVerifaceAlarmReceiverByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceAlarmReceiverByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverService.deleteYcVerifaceAlarmReceiverByCondition(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/findOneYcVerifaceAlarmReceiverByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceAlarmReceiver findOneYcVerifaceAlarmReceiverByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverService.findOneYcVerifaceAlarmReceiverByCondition(ycVerifaceAlarmReceiver);
    }

    @PostMapping("/batchSaveYcVerifaceAlarmReceiver")
    @ApiOperation(value = "批量保存")
    public void batchSaveYcVerifaceAlarmReceiver(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<YcVerifaceAlarmReceiver> ycVerifaceAlarmReceivers) {
        ycVerifaceAlarmReceiverService.batchSaveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceivers);
    }

    @GetMapping("/sendPushToAlarmReceiver/{id}/{type}")
    @ApiOperation(value = "发推送给预警接收人员", notes = "返回单个,没有时为空")
    public void sendPushToAlarmReceiver(
            @ApiParam(value = "对象")
            @PathVariable("id") String id,
            @PathVariable("type") String type) {
        ycVerifaceAlarmReceiverService.sendPushToAlarmReceiver(id, type);
    }

}
