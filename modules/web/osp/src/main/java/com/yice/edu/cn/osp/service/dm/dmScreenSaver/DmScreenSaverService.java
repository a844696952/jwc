package com.yice.edu.cn.osp.service.dm.dmScreenSaver;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.osp.feignClient.dm.dmScreenSaver.DmScreenSaverFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DmScreenSaverService {
    @Autowired
    private DmScreenSaverFeign dmScreenSaverFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public DmScreenSaver findDmScreenSaverById(String id) {
        return dmScreenSaverFeign.findDmScreenSaverById(id);
    }

    public DmScreenSaver saveDmScreenSaver(DmScreenSaver dmScreenSaver) {
        DmScreenSaver s = dmScreenSaverFeign.saveDmScreenSaver(dmScreenSaver);
        //如果修改屏保，状态还是进行中，则需要重新推送
        if (dmScreenSaver.getStatus() == Constant.dmScreenSaver.RUNNING) {
            DmScreenSaver dmScreenSaver1 =  dmScreenSaverFeign.findDmScreenSaverById(s.getId());
            String row[] = dmScreenSaver1.getSendArea().split(",");
            dmScreenSaver1.setRowData(row);
            //新增屏保，开始推送信息
            JpushApp app = JpushApp.getValueById(1);
            try {
                Push push;
                push = Push.newBuilder(app)
                        .setTag(mySchoolId())
                        .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.toJsonStr(dmScreenSaver1)).setTitle("新增屏保")
                                .setExtras(
                                        Extras.newBuilder()
                                                .setType(
                                                        Extras.DM_SCREEN_SAVER)
                                                .setId(dmScreenSaver1.getId())
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


    public List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver) {
        //修改状态
        dmScreenSaver.setStatus(Constant.dmScreenSaver.STOP);
        //修改状态
        dmScreenSaverFeign.batchUpdateDmScreenSaverStatus(dmScreenSaver);
        //修改状态之后，在进行查询列表
        dmScreenSaver.setStatus(null);
        return dmScreenSaverFeign.findDmScreenSaverListByCondition(dmScreenSaver);
    }

    public DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.findOneDmScreenSaverByCondition(dmScreenSaver);
    }

    public long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.findDmScreenSaverCountByCondition(dmScreenSaver);
    }

    public void updateDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.updateDmScreenSaver(dmScreenSaver);
        //如果修改屏保，状态还是进行中，则需要重新推送
        if (dmScreenSaver.getStatus() == Constant.dmScreenSaver.RUNNING) {
            String row[] = dmScreenSaver.getSendArea().split(",");
            dmScreenSaver.setRowData(row);
            //新增屏保，开始推送信息
            JpushApp app = JpushApp.getValueById(1);
            try {
                Push push;
                push = Push.newBuilder(app)
                    .setTag(mySchoolId())
                    .setMessage(Push.Message.newBuilder().setMsgContent(JSONUtil.toJsonStr(dmScreenSaver)).setTitle("修改屏保")
                        .setExtras(
                            Extras.newBuilder()
                                .setType(Extras.DM_SCREEN_SAVER)
                                .setId(dmScreenSaver.getId())
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

    public void deleteDmScreenSaver(String id) {
        dmScreenSaverFeign.deleteDmScreenSaver(id);
    }

    public void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.deleteDmScreenSaverByCondition(dmScreenSaver);
    }

    public void batchUpdateDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.batchUpdateDmScreenSaver(dmScreenSaver);
    }

    public void batchDeleteDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.batchDeleteDmScreenSaver(dmScreenSaver);
    }

    //获取班级名称和班牌的用户名
    public List<AreaByDmClassVo> getAreaByDmClass(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.getAreaByDmClass(dmScreenSaver);
    }

    //根据班级编号获取到云班牌用户名
    public String getUserNameByClassId(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.getUserNameByClassId(dmScreenSaver);
    }
    //根据时间修改当前学校的屏保状态
    public void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver){
        dmScreenSaverFeign.batchUpdateDmScreenSaverStatus(dmScreenSaver);
    }
}
