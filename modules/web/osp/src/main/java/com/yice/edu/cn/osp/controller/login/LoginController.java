package com.yice.edu.cn.osp.controller.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.common.util.vaptch.VaptchaResponse;
import com.yice.edu.cn.common.util.vaptch.VaptchaTest;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
//@RefreshScope
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private VaptchaTest vaptchaTest;
    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private SchoolService schoolService;
    @CreateCache(name =Constant.Redis.OSP_LOGIN_VALID,expire = 60)
    private Cache<String,Integer> loginCache;

    @PostMapping("/login")
    public ResponseJson login(@RequestBody @Validated(value = GroupTwo.class) Teacher teacher){

        //只在生产环境进行登录vaptcha 的 token验证
        if(loginCache.get(teacher.getTel())!=null){
            if(StrUtil.isEmpty(teacher.getToken())){
                return new ResponseJson(false, Constant.NEED_VALID);
            }
            VaptchaResponse vaptchaResponse = vaptchaTest.verify(teacher.getToken());
            if(vaptchaResponse.getSuccess()==0){
                return new ResponseJson(false, "验证码错误");
            }
        }
        Teacher t=loginService.login(teacher);
        if(t!=null){
            loginCache.remove(teacher.getTel());
            //验证学校账号是否失效或者已过期
            boolean valid=schoolService.validateSchoolAccount(t.getSchoolId());
            if(!valid){
                return new ResponseJson(false,"学校账号已过期,请联系亿策公司");
            }
            //非管理员
            if(!t.getStatus().equals(Constant.STATUS.TEACHER_ADMIN)) {
                //验证学校是否升学成功
                Integer riseGradeStatus = loginService.riseGradeStatus(t.getSchoolId());
                if(riseGradeStatus==null || riseGradeStatus.intValue()==Constant.SCHOOL_RISE_RECORD.NO_SET_TIME_RISE || riseGradeStatus.intValue()==Constant.SCHOOL_RISE_RECORD.ERROR_RISE) {
                	return new ResponseJson(false,"学校升学异常!");
                }
                if(riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.NOT_BEGIN_RISE && riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.HAS_RISE) {
                	return new ResponseJson(false,"学校未升学,无法使用平台功能！请联系运营人员");
                }
            }
            //t.setPostList(teacherService.findTeacherPost(t.getId()));
            Teacher subject = new Teacher();
            subject.setId(t.getId());
            subject.setSchoolId(t.getSchoolId());
            subject.setSchoolName(t.getSchoolName());
            subject.setTel(t.getTel());
            subject.setName(t.getName());
            subject.setImgUrl(t.getImgUrl());
            subject.setStatus(t.getStatus());
            subject.setSchool(new School(){{
                this.setTypeId(t.getSchool().getTypeId());
            }});
            String token = JwtUtil.createJWT(t.getId(), JSONUtil.toJsonStr(subject), null, Constant.Redis.OSP_TEACHER_TIMEOUT*1000);
            t.setPassword(null);
            return new ResponseJson(token, t);
        }else{
            loginCache.PUT(teacher.getTel(),1);
        }
        return new ResponseJson(false,"错误的用户名或密码");
    }



    @GetMapping("/testPubSub/{tel}/{code}")
    public String testPubSub(@PathVariable String tel,@PathVariable String code){
        Msn msn = new Msn(tel,code,"亿策教育");
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.AUTH_IDENTIFY,JSONUtil.toJsonStr(msn));
        return "success";
    }

//    @GetMapping("/testSendPush/{type}")
    public String testSendPush(@PathVariable int type){
        Push push=null;
        if(type==0){
            //给某个alias发送通知
            push= Push.newBuilder(JpushApp.TAP)
                    .setAlias("1753688335901876224")
                    .setNotification(Push.Notification.newBuilder().setTitle("我的标题").setAlert("推送alias").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
        }else if(type==1){
            //广播所有人
            push=Push.newBuilder(JpushApp.TAP)
                    .setAll(true)
                    .setNotification(Push.Notification.newBuilder().setTitle("这个是标题").setAlert("我是广播").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
        }else if(type==2){
            //发送透传给tag(也加通知，现在app端没有整合透传)
            push=Push.newBuilder(JpushApp.TAP)
                    .setTag("1741058039675183104")
                    .setNotification(Push.Notification.newBuilder().setAlert("根据tag发送透传和推送").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setMessage(Push.Message.newBuilder().setAlert("根据tag发送透传和推送").setMsgContent("根据tag发送透传和推送").setTitle("我是标题").build())
                    .build();
        }else if(type==3){
            //测试使用uuid,避免重复推送
            push=Push.newBuilder(JpushApp.TAP)
                    .setAlias("1753688335901876224")
                    .setNotification(Push.Notification.newBuilder().setAlert("测试使用uuid,避免重复推送").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setMessage(Push.Message.newBuilder().setMsgContent("根据tag发送透传和推送").setAlert("测试使用uuid,避免重复推送").setTitle("我是标题").build())
                    .build();
        }else if(type==4){
            //发透传给云班牌
            push=Push.newBuilder(JpushApp.ECC)
                    .setAlias("17358737736307425284")
                    .setMessage(Push.Message.newBuilder().setMsgContent("关机").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_EQUIPMENT_SHUTDOWN).build()).build())
                    .build();
        }
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH,JSONUtil.toJsonStr(push));
        return "success";
    }



}
