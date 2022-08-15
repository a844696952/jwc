package com.yice.edu.cn.tap.service.teacher;

import cn.hutool.core.util.NumberUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherPostFeign;
import com.yice.edu.cn.tap.feignClient.weixin.WeixinFeign;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private TeacherPostFeign teacherPostFeign;
    @Autowired
    private WeixinFeign weixinFeign;

    public Teacher login(Teacher teacher) {
        return teacherFeign.login(teacher);
    }

    @Cached(name = Constant.Redis.TAP_TEACHER_CACHE,key ="#teacher.id",expire =Constant.Redis.TAP_TEACHER__TIMEOUT, timeUnit = TimeUnit.DAYS)
    public Teacher saveTeacherToRedis(Teacher teacher){
        return teacher;
    }

    /**
     * 当更新教师对象的数据时，tap重新登录，删除教师对象的缓存
     * @param key
     */
    @CacheInvalidate(name=Constant.Redis.TAP_TEACHER_CACHE,key = "#key")
    public void clearTeacherInRedis(String key){
    }

    public long findTeacherCountByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherFeign.findTeacherCountByCondition(teacher);
    }


    public void updateTeacherAdmin(@RequestBody Teacher teacher){
        teacherFeign.updateTeacherAdmin(teacher);
    }

    public Teacher findTeacherById(
            @ApiParam(value = "老师的id", required = true)
            @PathVariable String id){
        return teacherFeign.findTeacherById(id);
    }

    public List<Teacher> findCorrespondencesByTeacher(Teacher teacher) {
        return teacherFeign.findCorrespondencesByTeacher(teacher);
    }

    public String getIdentifyingCode(String tel){
        int[]num= NumberUtil.generateRandomNumber(100000,999999,1);
        String code=String.valueOf(num[0]);
        Map<String, String> msg = new HashMap<>();
        msg.put("code",code);
        msg.put("product","亿策教育");
        final Sms sms = new Sms(tel, Constant.MCS_SIGN_NAME.YCJD, Constant.MCS_TEMPLATE.SMS_VERIFICATION, msg);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.SMS_SEND_MSG, JSONUtil.toJsonStr(sms));
        return  code;
    }



    @Cached(name=Constant.Redis.TAP_TEACHER_IDENTIFYING_CODE, key="#tel",expire = Constant.Redis.TAP_TEACHER_IDENTIFYING_CODE_TIME)
    public String saveCodeToRadis(String tel,String code){
        return  code;
    }

    public  List<Teacher> findTeacherListByCondition(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition(teacher);
    }

    @CacheInvalidate(name=Constant.Redis.TAP_TEACHER_IDENTIFYING_CODE,key = "#tel")
    public void clearCodeCache(String tel){

    }

    @Cached(name=Constant.Redis.TAP_TEACHER_LOGIN_ERROR, key="#key",expire = 1,timeUnit=TimeUnit.DAYS)
    public String saveLoginErroInfoToRedis(String ErrorInfo,String key){

        return  ErrorInfo;
    }

    @CacheUpdate(name=Constant.Redis.TAP_TEACHER_LOGIN_ERROR, key="#key",value = "#ErrorInfo")
    public void updateLoginErroInfoToRedis(String ErrorInfo,String key){

    }

    @CacheInvalidate(name=Constant.Redis.TAP_TEACHER_LOGIN_ERROR,key = "#key")
    public void clearLoginErroInfoCache(String key){

    }

    public long findTeacherCountByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherCountByCondition4Like(teacher);
    }

    public List<Teacher> findTeacherListByConditionRegister(Teacher teacher) {
        return teacherFeign.findTeacherListByConditionRegister(teacher);
    }

    public Map<String,List> findTeacherOrstudentByName(Teacher teacher) {
        return teacherFeign.findTeacherOrstudentByName(teacher);
    }

    public List<JwClasses> findTeacherClassByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassByTeacherId(teacherId);
    }

    public List<TeacherClasses> findCourse4TeacherClasses(String myId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId);
        return teacherClassesFeign.findTeacherClassesListByCondition(teacherClasses);
    }
    public List<TeacherClasses> findCourse4TeacherClasses(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassesListByCondition(teacherClasses);
    }
    public TeacherClasses findTeacherClassByTeacherIdAndPost(String teacherId,String postName){
        TeacherClasses teacherClasses = teacherClassesFeign.findTeacherClassByTeacherIdAndPost(teacherId,postName);
        if(teacherClasses!=null&&teacherClasses.getClassesName()!=null)
            teacherClasses.setClassesName(teacherClasses.getClassesName().replace("班",""));
        return teacherClasses;
    }
    public List<Map<String,String>> findTeacherClassCourseByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassCourseByTeacherId(teacherId);
    }

    /**
     * 教师app权限
     * 目前只给 学情配置 后期进行迭代优化
     * @param teacherId
     * @return
     */
    public List<Perm> findTeacherPerm4AppXq(String teacherId){
        //4个页面对应的唯一标识或者id
        //Stream ps = Stream.of("schoolmaster","grademaster","headmaster","classTeacher");
        String[] ids = {"2070951831981088768","2070961383988355072","2070963170694750208",
                "2070966022553034752","2320728369499299840","2320729365931712512",
                "1854228194541789184","2344180162245779456","1116162527802298368"};
        List<Perm> permList = teacherFeign.findTeacherMenu4App(teacherId);
        if(permList!=null&&permList.size()>0){
            return permList.stream().filter(p-> Stream.of(ids).anyMatch(i->i.equals(p.getId()))).map(p->{
                Perm np = new Perm();
                np.setId(p.getId());
                np.setTitle(p.getTitle());
                np.setIdentify(p.getIdentify());
                return np;
            }).collect(Collectors.toList());
        }else
            return new ArrayList<>();
    }
    public List<TeacherPost> findTeacherPost(String teacherId){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacherId);
        teacherPost.setPager(new Pager().setPaging(false).setIncludes("name","ddId","gradeId","gradeName","classId","className","sort").setSortField("sort"));
        return teacherPostFeign.findTeacherPostListByCondition(teacherPost);
    }
    public List<Perm> findDmssTeacherFuncPermsByTeacherId(String id) {
        return teacherFeign.findDmssTeacherFuncPermsByTeacherId(id);
    }

    //查找该教师当班主任的班级findClassTeacherIsDirector
    public  List<Teacher> findClassTeacherIsDirector(Teacher teacher) {
        return teacherFeign.findClassTeacherIsDirector(teacher);
    }

    /**
     * 绑定教师微信openId
     * @param teacher
     */
    public void bindOpenId2Teacher(Teacher teacher){
        teacherFeign.bindOpenId2Teacher(teacher);
    }

    /**
     * 微信获取openId使用
     * @param jscode2session
     * @return
     */
    public String jsCode2Session(Jscode2session jscode2session){
        return weixinFeign.jsCode2Session(jscode2session);
    }
}
