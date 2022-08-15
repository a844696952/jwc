package com.yice.edu.cn.bmp.feignClient.push;

import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value="jw",path = "/pushDetail")
public interface PushDetailFeign {
    @PostMapping("/updatePushDetail2Read")
    void updatePushDetail2Read(Receiver receiver);
    @PostMapping("/findPushDetailListByCondition4Receiver")
    List<PushDetail> findPushDetailListByCondition4Receiver(Receiver receiver);
    @PostMapping("/findPushDetailCountByCondition4Receiver")
    long findPushDetailCountByCondition4Receiver(Receiver receiver);
    @PostMapping("/findDyPushDetailListByConditionReceiver")
    List<PushDetail> findDyPushDetailListByConditionReceiver(Receiver receiver);
    @PostMapping("/findSchoolActivePushDetailListByConditionReceiver")
    List<PushDetail> findSchoolActivePushDetailListByConditionReceiver(Receiver receiver);
}
