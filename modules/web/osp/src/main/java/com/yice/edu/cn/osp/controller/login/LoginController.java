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

        //??????????????????????????????vaptcha ??? token??????
        if(loginCache.get(teacher.getTel())!=null){
            if(StrUtil.isEmpty(teacher.getToken())){
                return new ResponseJson(false, Constant.NEED_VALID);
            }
            VaptchaResponse vaptchaResponse = vaptchaTest.verify(teacher.getToken());
            if(vaptchaResponse.getSuccess()==0){
                return new ResponseJson(false, "???????????????");
            }
        }
        Teacher t=loginService.login(teacher);
        if(t!=null){
            loginCache.remove(teacher.getTel());
            //?????????????????????????????????????????????
            boolean valid=schoolService.validateSchoolAccount(t.getSchoolId());
            if(!valid){
                return new ResponseJson(false,"?????????????????????,?????????????????????");
            }
            //????????????
            if(!t.getStatus().equals(Constant.STATUS.TEACHER_ADMIN)) {
                //??????????????????????????????
                Integer riseGradeStatus = loginService.riseGradeStatus(t.getSchoolId());
                if(riseGradeStatus==null || riseGradeStatus.intValue()==Constant.SCHOOL_RISE_RECORD.NO_SET_TIME_RISE || riseGradeStatus.intValue()==Constant.SCHOOL_RISE_RECORD.ERROR_RISE) {
                	return new ResponseJson(false,"??????????????????!");
                }
                if(riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.NOT_BEGIN_RISE && riseGradeStatus.intValue()!=Constant.SCHOOL_RISE_RECORD.HAS_RISE) {
                	return new ResponseJson(false,"???????????????,????????????????????????????????????????????????");
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
        return new ResponseJson(false,"???????????????????????????");
    }



    @GetMapping("/testPubSub/{tel}/{code}")
    public String testPubSub(@PathVariable String tel,@PathVariable String code){
        Msn msn = new Msn(tel,code,"????????????");
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.AUTH_IDENTIFY,JSONUtil.toJsonStr(msn));
        return "success";
    }

//    @GetMapping("/testSendPush/{type}")
    public String testSendPush(@PathVariable int type){
        Push push=null;
        if(type==0){
            //?????????alias????????????
            push= Push.newBuilder(JpushApp.TAP)
                    .setAlias("1753688335901876224")
                    .setNotification(Push.Notification.newBuilder().setTitle("????????????").setAlert("??????alias").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
        }else if(type==1){
            //???????????????
            push=Push.newBuilder(JpushApp.TAP)
                    .setAll(true)
                    .setNotification(Push.Notification.newBuilder().setTitle("???????????????").setAlert("????????????").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setOptions(Push.Options.newBuilder().build())
                    .build();
        }else if(type==2){
            //???????????????tag(?????????????????????app?????????????????????)
            push=Push.newBuilder(JpushApp.TAP)
                    .setTag("1741058039675183104")
                    .setNotification(Push.Notification.newBuilder().setAlert("??????tag?????????????????????").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setMessage(Push.Message.newBuilder().setAlert("??????tag?????????????????????").setMsgContent("??????tag?????????????????????").setTitle("????????????").build())
                    .build();
        }else if(type==3){
            //????????????uuid,??????????????????
            push=Push.newBuilder(JpushApp.TAP)
                    .setAlias("1753688335901876224")
                    .setNotification(Push.Notification.newBuilder().setAlert("????????????uuid,??????????????????").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_BROADCAST).build()).build())
                    .setMessage(Push.Message.newBuilder().setMsgContent("??????tag?????????????????????").setAlert("????????????uuid,??????????????????").setTitle("????????????").build())
                    .build();
        }else if(type==4){
            //?????????????????????
            push=Push.newBuilder(JpushApp.ECC)
                    .setAlias("17358737736307425284")
                    .setMessage(Push.Message.newBuilder().setMsgContent("??????").setExtras(Extras.newBuilder().setType(Extras.SYSTEM_EQUIPMENT_SHUTDOWN).build()).build())
                    .build();
        }
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH,JSONUtil.toJsonStr(push));
        return "success";
    }



}
