package com.yice.edu.cn.bmp.service.push;

import com.yice.edu.cn.bmp.feignClient.push.PushDetailFeign;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
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

    public List<PushDetail> findPushDetailListByCondition4Receiver(Receiver receiver) {
        return pushDetailFeign.findPushDetailListByCondition4Receiver(receiver);
    }

    public long findPushDetailCountByCondition4Receiver(Receiver receiver) {
        return pushDetailFeign.findPushDetailCountByCondition4Receiver(receiver);
    }


    public List<PushDetail> findDyPushDetailListByConditionReceiver(Receiver receiver) {
        return pushDetailFeign.findDyPushDetailListByConditionReceiver(receiver);
    }

    public List<PushDetail> findSchoolActivePushDetailListByConditionReceiver(Receiver receiver) {
        return pushDetailFeign.findSchoolActivePushDetailListByConditionReceiver(receiver);
    }
}
