package com.yice.edu.cn.osp.service.jw.schoolPush;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.osp.feignClient.jw.schoolPush.SchoolPushFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class SchoolPushService {
    @Autowired
    private SchoolPushFeign schoolPushFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public SchoolPush findSchoolPushById(String id) {
        return schoolPushFeign.findSchoolPushById(id);
    }

    public SchoolPush saveSchoolPush(SchoolPush schoolPush) {
        //保存成功后发送推送(广播)
        final SchoolPush sp = schoolPushFeign.saveSchoolPush(schoolPush);
        System.out.println(sp);
        final int[] appIds = sp.getAppIds();
        for (int appId : appIds) {
            final JpushApp app = JpushApp.getValueById(appId);
            if(app!=null){
                final Push push;
                if(app.getId()==1){//云班牌发透传,其他发通知
                    push = Push.newBuilder(app)
                            .setTag(mySchoolId())
                            .setMessage(Push.Message.newBuilder().setMsgContent(sp.getTitle()).setExtras(Extras.newBuilder().setType(Extras.SCHOOL_BROADCAST).setId(sp.getId()).build()).build())
                            .build();
                }else{
                    push = Push.newBuilder(app)
                            .setTag(mySchoolId())
                            .setNotification(Push.Notification.newBuilder().setAlert(sp.getTitle()).setExtras(Extras.newBuilder().setType(Extras.SCHOOL_BROADCAST).setId(sp.getId()).build()).setSound(Constant.JPUSH.SOUND_1).build())
                            .build();
                }
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
        }
        return sp;
    }

    public List<SchoolPush> findSchoolPushListByCondition(SchoolPush schoolPush) {
        return schoolPushFeign.findSchoolPushListByCondition(schoolPush);
    }

    public SchoolPush findOneSchoolPushByCondition(SchoolPush schoolPush) {
        return schoolPushFeign.findOneSchoolPushByCondition(schoolPush);
    }

    public long findSchoolPushCountByCondition(SchoolPush schoolPush) {
        return schoolPushFeign.findSchoolPushCountByCondition(schoolPush);
    }

    public void updateSchoolPush(SchoolPush schoolPush) {
        schoolPushFeign.updateSchoolPush(schoolPush);
    }

    public void deleteSchoolPush(String id) {
        schoolPushFeign.deleteSchoolPush(id);
    }

    public void deleteSchoolPushByCondition(SchoolPush schoolPush) {
        schoolPushFeign.deleteSchoolPushByCondition(schoolPush);
    }

    public List<SchoolPush> getSchoolPushListToWebIndex() {
        SchoolPush schoolPush =new SchoolPush();
        schoolPush.setSchoolId(mySchoolId());
        Pager p=new Pager();
        p.setSortField("createTime");
        p.setSortOrder("desc");
        p.setPage(1);
        p.setPageSize(3);
        schoolPush.setPager(p);
       return findSchoolPushListByCondition(schoolPush);

    }
}
