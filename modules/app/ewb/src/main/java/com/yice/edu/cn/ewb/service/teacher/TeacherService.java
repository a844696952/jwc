package com.yice.edu.cn.ewb.service.teacher;

import cn.hutool.core.util.NumberUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.ewb.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.ewb.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.ewb.feignClient.teacher.TeacherPostFeign;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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

    public Teacher login(Teacher teacher) {
        return teacherFeign.login(teacher);
    }

    /**
     * 根据手机号码查当前用户信息
     * @param tel
     * @return
     */
    public Teacher findTeacherByTel(String tel){
        return teacherFeign.findTeacherByTel(tel);
    }

    @Cached(name = Constant.Redis.EWB_TEACHER_CACHE,key ="#teacher.id",expire =Constant.Redis.EWB_TEACHER__TIMEOUT, timeUnit = TimeUnit.DAYS)
    public Teacher saveTeacherToRedis(Teacher teacher){
        return teacher;
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
        Msn msn = new Msn(tel,code,"亿策教育");
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.AUTH_IDENTIFY,JSONUtil.toJsonStr(msn));
        return  code;
    }



    @Cached(name=Constant.Redis.EWB_TEACHER_IDENTIFYING_CODE, key="#tel",expire = Constant.Redis.EWB_TEACHER_IDENTIFYING_CODE_TIME)
    public String saveCodeToRadis(String tel,String code){
        return  code;
    }

    public  List<Teacher> findTeacherListByCondition(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition(teacher);
    }

    @CacheInvalidate(name=Constant.Redis.EWB_TEACHER_IDENTIFYING_CODE,key = "#tel")
    public void clearCodeCache(String tel){

    }

    @Cached(name=Constant.Redis.EWB_TEACHER_LOGIN_ERROR, key="#key",expire = 1,timeUnit=TimeUnit.DAYS)
    public String saveLoginErroInfoToRedis(String ErrorInfo,String key){

        return  ErrorInfo;
    }

    @CacheUpdate(name=Constant.Redis.EWB_TEACHER_LOGIN_ERROR, key="#key",value = "#ErrorInfo")
    public void updateLoginErroInfoToRedis(String ErrorInfo,String key){

    }

    @CacheInvalidate(name=Constant.Redis.EWB_TEACHER_LOGIN_ERROR,key = "#key")
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

    public List<Teacher> findTeacherListByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition4Like(teacher);
    }

    public List<JwClasses> findTeacherClassByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassByTeacherId(teacherId);
    }

    public List<TeacherClassesCourse> findCourse4TeacherClasses(String myId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId);
        return this.findCourse4TeacherClasses(teacherClasses);
    }
    public List<TeacherClassesCourse> findCourse4TeacherClasses(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourse4TeacherClasses(teacherClasses);
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
        String[] ids = {"2070951831981088768","2070961383988355072","2070963170694750208","2070966022553034752","2216406878054604800","2216411757137453056"};
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
}
