package com.yice.edu.cn.tap.service.push;

import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import com.yice.edu.cn.tap.feignClient.push.PushDetailFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushDetailService {
    @Autowired
    private PushDetailFeign pushDetailFeign;

    public void updatePushDetail2Read(Receiver receiver) {
        pushDetailFeign.updatePushDetail2Read(receiver);
    }

    public List<PushDetail> findPushDetailList4Work(Receiver receiver) {
        return pushDetailFeign.findPushDetailList4Work(receiver);
    }

    public List<PushDetail> findPushDetailList4InOutSchool(Receiver receiver) {
        return pushDetailFeign.findPushDetailList4InOutSchool(receiver);
    }

    public long findPushDetailCount4Work(Receiver receiver) {
        return pushDetailFeign.findPushDetailCount4Work(receiver);
    }

    public long findPushDetailCount4InOutSchool(Receiver receiver) {
        return pushDetailFeign.findPushDetailCount4InOutSchool(receiver);
    }


    public List<PushDetail> findPushActiveList(Receiver receiver){
        return pushDetailFeign.findPushActiveList (receiver);
    }

    public long findPushActiveCount(Receiver receiver){
        return pushDetailFeign.findPushActiveCount (receiver);
    }



    public List<PushDetail> findPushDetailAllList(Receiver receiver){
        return pushDetailFeign.findPushDetailAllList (receiver);
    }


    public long findPushDetailCount(Receiver receiver){
        return  pushDetailFeign.findPushDetailCount (receiver);
    }

    public List<PushDetail> findSchoolActivePushDetailListByConditionTeacherReceiver(Receiver receiver) {
        return pushDetailFeign.findSchoolActivePushDetailListByConditionTeacherReceiver(receiver);
    }
}
