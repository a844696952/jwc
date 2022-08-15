package com.yice.edu.cn.tap.feignClient.push;

import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value="jw",path = "/pushDetail")
public interface PushDetailFeign {
    @PostMapping("/updatePushDetail2Read")
    void updatePushDetail2Read(Receiver receiver);
    @PostMapping("/findPushDetailList4Work")
    List<PushDetail> findPushDetailList4Work(Receiver receiver);
    @PostMapping("/findPushDetailList4InOutSchool")
    List<PushDetail> findPushDetailList4InOutSchool(Receiver receiver);
    @PostMapping("/findPushDetailCount4Work")
    long findPushDetailCount4Work(Receiver receiver);
    @PostMapping("/findPushDetailCount4InOutSchool")
    long findPushDetailCount4InOutSchool(Receiver receiver);

    @PostMapping("/findPushActiveList")
    List<PushDetail> findPushActiveList(Receiver receiver);

    @PostMapping("/findPushActiveCount")
    long findPushActiveCount(Receiver receiver);


    @PostMapping("/findPushDjDocList")
    List<PushDetail> findPushDjDocList(Receiver receiver);

    @PostMapping("/findPushDjDocCount")
    long findPushDjDocCount(Receiver receiver);

    @PostMapping("/findPushDetailAllList")
    List<PushDetail> findPushDetailAllList(Receiver receiver);

    @PostMapping("/findPushActiveCount")
    long findPushDetailCount(Receiver receiver);

    @PostMapping("/findSchoolActivePushDetailListByConditionTeacherReceiver")
    List<PushDetail> findSchoolActivePushDetailListByConditionTeacherReceiver(Receiver receiver);
}
