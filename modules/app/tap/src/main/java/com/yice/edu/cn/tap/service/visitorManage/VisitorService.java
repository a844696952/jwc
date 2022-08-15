package com.yice.edu.cn.tap.service.visitorManage;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.tap.feignClient.visitorManage.VisitorFeign;
import com.yice.edu.cn.tap.service.school.SchoolService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorService {
    @Autowired
    private VisitorFeign visitorFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SchoolService schoolService;

    // 推送  0 家长 极光推送  1 陌生人 短信推送
    public void sendMsg(Visitor visitor) {
        if (visitor.getVisitorType().equals("1")) {
            sendMessage(visitor);
        } else if (visitor.getVisitorType().equals("0")) {
            String[] studentList = new String[]{visitor.getStudentId()};
            if (visitor.getApplyStatus().equals("0")) {
                Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"访客申请","您提交的访问申请已经通过了！",Extras.VISITOR_NOTICE);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                //System.out.println("您提交的访问申请已经通过了");
            } else if (visitor.getApplyStatus().equals("1")) {
                Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(studentList,"访客申请","您提交的访问申请被拒绝了！",Extras.VISITOR_NOTICE);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                //System.out.println("您提交的访问申请被拒绝了");
            }
        }
    }


    //日期转年月日
    private static final String toYear(String time) {
        String year = time.substring(0, 4) + "年";
        String month;
        String day;
        if (time.substring(5, 7).substring(0, 1).equals("0")) {
            month = time.substring(6, 7) + "月";
        } else {
            month = time.substring(5, 7) + "月";
        }
        if (time.substring(8, 10).substring(0, 1).equals("0")) {
            day = time.substring(9, 10) + "日";
        } else {
            day = time.substring(8, 10) + "日";
        }
        return (year + month + day + time.substring(10,16));
    }

    public String sendMessage(Visitor visitor) {
        School school=schoolService.findSchoolById(visitor.getSchoolId());
        Map<String, String> msg = new HashMap<>();
        String startTime = toYear(visitor.getStartTime());
        String endTime = toYear(visitor.getEndTime());
        String method = null;
        String reason = null;
        msg.put("time", startTime + " - " + endTime);
        msg.put("schoolName",school.getName());
        if (visitor.getVisitorWay().equals("0")) {
            msg.put("method", "直接刷脸");
        } else if (visitor.getVisitorWay().equals("1")) {
            msg.put("method", "刷身份证");
        }
        if (visitor.getApplyStatus().equals("1")) {
            if (StringUtil.isNullOrEmpty(visitor.getUnpassReason())) {
                msg.put("reason", "无");
            } else {
                msg.put("reason", visitor.getUnpassReason());
            }
            final Sms sms = new Sms(visitor.getVisitorTel(), Constant.MCS_SIGN_NAME.YCJD, Constant.MCS_TEMPLATE.SMS_VISITOR_REFUSE, msg);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));

        } else if (visitor.getApplyStatus().equals("0")) {
            final Sms sms = new Sms(visitor.getVisitorTel(), Constant.MCS_SIGN_NAME.YCJD, Constant.MCS_TEMPLATE.SMS_VISITOR_PASS, msg);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));
        }
        return method;
    }

    public Visitor findVisitorById(String id) {
        return visitorFeign.findVisitorById(id);
    }

    public Visitor saveVisitor(Visitor visitor) {
        return visitorFeign.saveVisitor(visitor);
    }

    public List<Visitor> findVisitorListByCondition(Visitor visitor) {
        return visitorFeign.findVisitorListByCondition(visitor);
    }

    public Visitor findOneVisitorByCondition(Visitor visitor) {
        return visitorFeign.findOneVisitorByCondition(visitor);
    }

    public long findVisitorCountByCondition(Visitor visitor) {
        return visitorFeign.findVisitorCountByCondition(visitor);
    }

    public void updateVisitor(Visitor visitor) {
        visitorFeign.updateVisitor(visitor);
    }

    public void deleteVisitor(String id) {
        visitorFeign.deleteVisitor(id);
    }

    public void deleteVisitorByCondition(Visitor visitor) {
        visitorFeign.deleteVisitorByCondition(visitor);
    }

}
