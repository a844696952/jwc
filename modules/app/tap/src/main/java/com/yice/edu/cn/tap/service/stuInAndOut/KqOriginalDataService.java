package com.yice.edu.cn.tap.service.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalDataTest;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;

import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqDevicePersonFeign;
import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqOriginalDataFeign;
import com.yice.edu.cn.tap.feignClient.student.StudentFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.tap.feignClient.visitorManage.VisitorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KqOriginalDataService {
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    public KqOriginalData findKqOriginalDataById(String id) {
        return kqOriginalDataFeign.findKqOriginalDataById(id);
    }

    public KqOriginalData saveKqOriginalData(KqOriginalData kqOriginalData) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 10);
        kqOriginalData.setCapturedTime(df.format(nowTime.getTime()));
        //查教职工类型
        String  tid =  kqOriginalData.getUserId();
        Teacher teacherById = teacherFeign.findTeacherById(tid);
        Integer personType = teacherById.getPersonType();
        kqOriginalData.setUserType(String.valueOf(personType));
        return kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
    }

    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
    }

    public KqOriginalData findOneKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findOneKqOriginalDataByCondition(kqOriginalData);
    }

    public long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataCountByCondition(kqOriginalData);
    }

    public void updateKqOriginalData(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.updateKqOriginalData(kqOriginalData);
    }

    public void deleteKqOriginalData(String id) {
        kqOriginalDataFeign.deleteKqOriginalData(id);
    }

    public void deleteKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.deleteKqOriginalDataByCondition(kqOriginalData);
    }

    public KqDailyStatistical judgePunchStatusByrules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.judgePunchStatusByrules(kqOriginalData);
    }
    public KqDailyStatistical dayBasicRecords(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.dayBasicRecords(kqOriginalData);
    }
    public KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.inTimeCountByRules(kqOriginalData);
    }
    public boolean statusOfKqOriginalData(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.statusOfKqOriginalData(kqOriginalData);
    }


}

