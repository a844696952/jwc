package com.yice.edu.cn.osp.feignClient.dm.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver.YcVerifaceAlarmReceiver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dm", contextId = "ycVerifaceAlarmReceiverFeign", path = "/ycVerifaceAlarmReceiver")
public interface YcVerifaceAlarmReceiverFeign {
    @GetMapping("/findYcVerifaceAlarmReceiverById/{id}")
    YcVerifaceAlarmReceiver findYcVerifaceAlarmReceiverById(@PathVariable("id") String id);

    @PostMapping("/saveYcVerifaceAlarmReceiver")
    YcVerifaceAlarmReceiver saveYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @PostMapping("/findYcVerifaceAlarmReceiverListByCondition")
    List<YcVerifaceAlarmReceiver> findYcVerifaceAlarmReceiverListByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @PostMapping("/findOneYcVerifaceAlarmReceiverByCondition")
    YcVerifaceAlarmReceiver findOneYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @PostMapping("/findYcVerifaceAlarmReceiverCountByCondition")
    long findYcVerifaceAlarmReceiverCountByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @PostMapping("/updateYcVerifaceAlarmReceiver")
    void updateYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @GetMapping("/deleteYcVerifaceAlarmReceiver/{id}")
    void deleteYcVerifaceAlarmReceiver(@PathVariable("id") String id);

    @PostMapping("/deleteYcVerifaceAlarmReceiverByCondition")
    void deleteYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver);

    @GetMapping("/sendPushToAlarmReceiver/{id}/{type}")
    void sendPushToAlarmReceiver(@PathVariable("id") String id, @PathVariable("type") String type);

    @PostMapping("/batchSaveYcVerifaceAlarmReceiver")
    void batchSaveYcVerifaceAlarmReceiver(List<YcVerifaceAlarmReceiver> ycVerifaceAlarmReceiver);
}
