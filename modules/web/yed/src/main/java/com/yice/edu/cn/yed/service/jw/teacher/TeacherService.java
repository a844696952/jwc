package com.yice.edu.cn.yed.service.jw.teacher;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.yed.feignClient.jw.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Teacher findTeacherById(String id) {
        return teacherFeign.findTeacherById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherFeign.saveTeacher(teacher);
    }

    public List<Teacher> findTeacherListByCondition(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition(teacher);
    }

    public long findTeacherCountByCondition(Teacher teacher) {
        return teacherFeign.findTeacherCountByCondition(teacher);
    }
    public List<Teacher> findTeachers4Yed(Teacher teacher) {
        return teacherFeign.findTeachers4Yed(teacher);
    }
    public long findTeachersCount4Yed(Teacher teacher) {
        return teacherFeign.findTeachersCount4Yed(teacher);
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherFeign.updateTeacher(teacher);
    }

    public void deleteTeacher(String id) {
        teacherFeign.deleteTeacher(id);
    }

    public void updateTeacherAdmin(Teacher teacher) {
        teacherFeign.updateTeacherAdmin(teacher);
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
}
