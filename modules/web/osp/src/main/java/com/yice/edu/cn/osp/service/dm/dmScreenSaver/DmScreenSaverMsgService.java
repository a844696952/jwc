package com.yice.edu.cn.osp.service.dm.dmScreenSaver;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.osp.feignClient.dm.dmScreenSaver.DmScreenSaverMsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DmScreenSaverMsgService {
    @Autowired
    private DmScreenSaverMsgFeign dmScreenSaverMsgFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public DmScreenSaverMsg findDmScreenSaverMsgById(String id) {
        return dmScreenSaverMsgFeign.findDmScreenSaverMsgById(id);
    }

    public DmScreenSaverMsg saveDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        DmScreenSaverMsg s = dmScreenSaverMsgFeign.saveDmScreenSaverMsg(dmScreenSaverMsg);
        //如果修改屏保，状态还是进行中，则需要重新推送
        if (dmScreenSaverMsg.getStatus() == Constant.dmScreenSaver.RUNNING) {
            DmScreenSaverMsg dmScreenSaverMsg1 =  dmScreenSaverMsgFeign.findDmScreenSaverMsgById(s.getId());
            //新增屏保，开始推送信息
            JpushApp app = JpushApp.getValueById(4);
            try {
                Push push;
                push = Push.newBuilder(app)
                        .setTag(mySchoolId())
                        .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.toJsonStr(dmScreenSaverMsg1)).setTitle("新增信息推送")
                                .setExtras(
                                        Extras.newBuilder()
                                                .setType(
                                                        Extras.DM_SCREEN_SAVER)
                                                .setId(dmScreenSaverMsg1.getId())
                                                .build()
                                )
                                .build()
                        ).build();
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    public List<DmScreenSaverMsg> findDmScreenSaverMsgListByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgFeign.findDmScreenSaverMsgListByCondition(dmScreenSaverMsg);
    }

    public DmScreenSaverMsg findOneDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgFeign.findOneDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }

    public long findDmScreenSaverMsgCountByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        return dmScreenSaverMsgFeign.findDmScreenSaverMsgCountByCondition(dmScreenSaverMsg);
    }

    public void updateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgFeign.updateDmScreenSaverMsg(dmScreenSaverMsg);
        //如果修改屏保，状态还是进行中，则需要重新推送
        if (dmScreenSaverMsg.getStatus() == Constant.dmScreenSaver.RUNNING) {
            JpushApp app = JpushApp.getValueById(4);
            try {
                Push push;
                push = Push.newBuilder(app)
                        .setTag(mySchoolId())
                        .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.toJsonStr(dmScreenSaverMsg)).setTitle("修改消息")
                                .setExtras(
                                        Extras.newBuilder()
                                                .setType(Extras.DM_SCREEN_SAVER)
                                                .setId(dmScreenSaverMsg.getId())
                                                .build()
                                )
                                .build()
                        ).build();
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDmScreenSaverMsg(String id) {
        dmScreenSaverMsgFeign.deleteDmScreenSaverMsg(id);
    }

    public void deleteDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgFeign.deleteDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }

    public void batchUpdateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgFeign.batchUpdateDmScreenSaverMsg(dmScreenSaverMsg);
    }

    public void batchDeleteDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg) {
        dmScreenSaverMsgFeign.batchDeleteDmScreenSaverMsg(dmScreenSaverMsg);
    }

    //根据时间修改当前学校的屏保状态
    public void batchUpdateDmScreenSaverMsgStatus(DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgFeign.batchUpdateDmScreenSaverMsgStatus(dmScreenSaverMsg);
    }
}
