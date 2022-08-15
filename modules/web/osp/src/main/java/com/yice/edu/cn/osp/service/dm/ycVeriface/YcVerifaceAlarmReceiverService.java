package com.yice.edu.cn.osp.service.dm.ycVeriface;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver.YcVerifaceAlarmReceiver;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceAlarmReceiverFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YcVerifaceAlarmReceiverService {
    @Autowired
    private YcVerifaceAlarmReceiverFeign ycVerifaceAlarmReceiverFeign;

    public YcVerifaceAlarmReceiver findYcVerifaceAlarmReceiverById(String id) {
        return ycVerifaceAlarmReceiverFeign.findYcVerifaceAlarmReceiverById(id);
    }

    public YcVerifaceAlarmReceiver saveYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverFeign.saveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
    }

    public List<YcVerifaceAlarmReceiver> findYcVerifaceAlarmReceiverListByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverFeign.findYcVerifaceAlarmReceiverListByCondition(ycVerifaceAlarmReceiver);
    }

    public YcVerifaceAlarmReceiver findOneYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverFeign.findOneYcVerifaceAlarmReceiverByCondition(ycVerifaceAlarmReceiver);
    }

    public long findYcVerifaceAlarmReceiverCountByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return ycVerifaceAlarmReceiverFeign.findYcVerifaceAlarmReceiverCountByCondition(ycVerifaceAlarmReceiver);
    }

    public void updateYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverFeign.updateYcVerifaceAlarmReceiver(ycVerifaceAlarmReceiver);
    }

    public void deleteYcVerifaceAlarmReceiver(String id) {
        ycVerifaceAlarmReceiverFeign.deleteYcVerifaceAlarmReceiver(id);
    }

    public void deleteYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiverFeign.deleteYcVerifaceAlarmReceiverByCondition(ycVerifaceAlarmReceiver);
    }

    public void sendPushToAlarmReceiver(String id,String type) {
        ycVerifaceAlarmReceiverFeign.sendPushToAlarmReceiver(id,type);
    }

    public void batchSaveYcVerifaceAlarmReceiver(List<YcVerifaceAlarmReceiver> ycVerifaceAlarmReceivers) {
        ycVerifaceAlarmReceiverFeign.batchSaveYcVerifaceAlarmReceiver(ycVerifaceAlarmReceivers);
    }
}
