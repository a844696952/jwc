package com.yice.edu.cn.xw.service.wage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import com.yice.edu.cn.common.pojo.xw.wage.WageValue;
import com.yice.edu.cn.xw.dao.wage.IWageTeacherDao;
import com.yice.edu.cn.xw.dao.wage.IWageTypeTeacherDao;
import com.yice.edu.cn.xw.dao.wage.IWageValueDao;
import io.netty.util.internal.StringUtil;
import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WageTypeTeacherService {
    @Autowired
    private IWageTypeTeacherDao wageTypeTeacherDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IWageValueDao wageValueDao;
    @Autowired
    private IWageTeacherDao wageTeacherDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(readOnly = true)
    public WageTypeTeacher findWageTypeTeacherById(String id) {
        return wageTypeTeacherDao.findWageTypeTeacherById(id);
    }

    @Transactional
    public void saveWageTypeTeacher(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacher.setId(sequenceId.nextId());
        wageTypeTeacherDao.saveWageTypeTeacher(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public List<WageTypeTeacher> findWageTypeTeacherListByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageTypeTeacherListByCondition(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public List<WageTypeTeacher> findWageTypeTeacherListByWorkNum(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageTypeTeacherListByWorkNum(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public WageTypeTeacher findOneWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findOneWageTypeTeacherByCondition(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public long findWageTypeTeacherCountByCondition(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageTypeTeacherCountByCondition(wageTypeTeacher);
    }

    @Transactional
    public void updateWageTypeTeacher(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherDao.updateWageTypeTeacher(wageTypeTeacher);
    }

    @Transactional
    public void deleteWageTypeTeacher(String id) {
        wageTypeTeacherDao.deleteWageTypeTeacher(id);
    }

    @Transactional
    public void deleteWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacherDao.deleteWageTypeTeacherByCondition(wageTypeTeacher);
    }
   /* @Transactional
    public void batchSaveWageTypeTeacher(List<WageTypeTeacher> wageTypeTeachers){
        wageTypeTeachers.forEach(wageTypeTeacher -> wageTypeTeacher.setId(sequenceId.nextId()));
        wageTypeTeacherDao.batchSaveWageTypeTeacher(wageTypeTeachers);
    }*/


    @Transactional
    public void saveWageTypeValue(WageTypeTeacher wageTypeTeacher) {
        wageTypeTeacher.setId(sequenceId.nextId());
        wageTypeTeacher.setReleaseState("0");
        wageTypeTeacherDao.saveWageTypeTeacher(wageTypeTeacher);
        for (Map.Entry<String, Object> entry : wageTypeTeacher.getPropmap().entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            WageValue wageValue = new WageValue();
            wageValue.setId(sequenceId.nextId());
            wageValue.setWageAttributeId(key);
            wageValue.setValueSize(value);
            wageValue.setSchoolId(wageTypeTeacher.getSchoolId());
            wageValue.setRecordId(wageTypeTeacher.getId());
            wageValueDao.saveWageValue(wageValue);
        }
    }

    @Transactional
    public void updateWageTypeValue(WageTypeTeacher wageTypeTeacher) {


        wageTypeTeacherDao.updateWageTypeTeacher(wageTypeTeacher);
        wageValueDao.deleteWageValueByRecordId(wageTypeTeacher.getId());
        for (Map.Entry<String, Object> entry : wageTypeTeacher.getPropmap().entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            WageValue wageValue = new WageValue();
            wageValue.setId(sequenceId.nextId());
            wageValue.setWageAttributeId(key);
            wageValue.setValueSize(value);
            wageValue.setSchoolId(wageTypeTeacher.getSchoolId());
            wageValue.setRecordId(wageTypeTeacher.getId());
            wageValueDao.saveWageValue(wageValue);
        }
    }


    @Transactional(readOnly = true)
    public List<Map<String, String>> findWageValueByTypeId(Map<String, Object> wageTypeTeacherMap) {
        return wageTypeTeacherDao.findWageValueByTypeId(wageTypeTeacherMap);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> findWageValueByRecordId(Map<String, Object> wageTypeRecordMap) {
        return wageTypeTeacherDao.findWageValueByRecordId(wageTypeRecordMap);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> findWageValueByTeacherId(Map<String, Object> wageTypeTeacherMap) {
        return wageTypeTeacherDao.findWageValueByTeacherId(wageTypeTeacherMap);
    }

    @Transactional(readOnly = true)
    public List<WageTypeTeacher> findWageAttributeListByRecordId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageAttributeListByRecordId(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public List<WageTypeTeacher> findWageAttributeListByTeacherId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageAttributeListByTeacherId(wageTypeTeacher);
    }

    @Transactional(readOnly = true)
    public List<WageTypeTeacher> findWageAttributeNameByTeacherId(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageAttributeNameByTeacherId(wageTypeTeacher);
    }

    @Transactional
    public void deleteWageValueByRecordId(String id) {
        wageTypeTeacherDao.deleteWageTypeTeacher(id);
        wageValueDao.deleteWageValueByRecordId(id);
    }

    @Transactional
    public void updateWageTypeTeacherReleaseState(WageTypeTeacher wageTypeTeacher) {
        List<WageTypeTeacher> wageTypeTeachers = new ArrayList<>();
        for (int i = 0; i < wageTypeTeacher.getSelAllWorkId().length; i++) {
            Date date = DateUtil.date();
            String nowTime = DateUtil.format(date, "yyyy-MM-dd HH:mm");
            wageTypeTeacher.setId(wageTypeTeacher.getSelAllWorkId()[i]);
            wageTypeTeacher.setReleaseState("1");
            wageTypeTeacher.setReleaseTime(nowTime);
            wageTypeTeacherDao.updateWageTypeTeacher(wageTypeTeacher);
            WageTypeTeacher wageTeacher = wageTypeTeacherDao.findWageTeacherIdByRecordId(wageTypeTeacher); //获取教师(工资)对象
            wageTypeTeachers.add(wageTeacher);
        }
        if (wageTypeTeachers.size() > 0) { //获取教师id，对教师进行推送通知
            //处理推送
            final Push push = Push.newBuilder(JpushApp.TAP)
                    .setAlias(wageTypeTeachers.stream().map(WageTypeTeacher::getTeacherId).toArray(String[]::new))
                    .setNotification(Push.Notification.newBuilder().setAlert("工资通知").setExtras(Extras.newBuilder().setType(Extras.TAP_TEACHER_WAGE).build()).setSound(Constant.JPUSH.SOUND_1).build())
                    .build();
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }

    @Transactional(readOnly = true)
    public long findWageValueByTypeIdCount(WageTypeTeacher wageTypeTeacher) {
        return wageTypeTeacherDao.findWageValueByTypeIdCount(wageTypeTeacher);
    }

    @Transactional
    public Map<String, Object> batchSaveWageTypeTeacher(List<WageTypeTeacher> wageTypeTeacherList) {
        Map<String, Object> result = new HashMap<>();
        Teacher teacher = new Teacher();
        teacher.setSchoolId(wageTypeTeacherList.get(0).getSchoolId());
        List<Teacher> teacherList = wageTeacherDao.findTeacherListByCondition(teacher);
        for (int i = 0; i < wageTypeTeacherList.size(); i++) {
            WageTypeTeacher wageTypeTeacher = new WageTypeTeacher();
            wageTypeTeacher.setWorkNumber(wageTypeTeacherList.get(i).getWorkNumber());
            wageTypeTeacher.setName(wageTypeTeacherList.get(i).getName());
            wageTypeTeacher.setId(sequenceId.nextId());
            wageTypeTeacher.setWageTypeId(wageTypeTeacherList.get(i).getWageTypeId());
            wageTypeTeacher.setReleaseState("0");
            wageTypeTeacher.setSalaryTime(wageTypeTeacherList.get(i).getSalaryTime());
            wageTypeTeacher.setSchoolId(wageTypeTeacherList.get(i).getSchoolId());
            for (int j = 0; j < teacherList.size(); j++) {
                if (wageTypeTeacherList.get(i).getName().equals(teacherList.get(j).getName()) && wageTypeTeacherList.get(i).getWorkNumber().equals(teacherList.get(j).getWorkNumber())) {
                    wageTypeTeacher.setTeacherId(teacherList.get(j).getId());
                }
            }
            wageTypeTeacherDao.saveWageTypeTeacher(wageTypeTeacher);
            for (Map.Entry<String, Object> entry : wageTypeTeacherList.get(i).getPropmap().entrySet()) {
                WageValue wageValue = new WageValue();
                wageValue.setId(sequenceId.nextId());
                wageValue.setWageAttributeId(entry.getKey().toString());
                wageValue.setValueSize(entry.getValue().toString());
                wageValue.setRecordId(wageTypeTeacher.getId());
                wageValue.setSchoolId(wageTypeTeacher.getSchoolId());
                wageValueDao.saveWageValue(wageValue);
            }
        }
        return result;
    }

/*    public static void main(String[] args) {
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(String.valueOf(20.30));
        if(match.matches()==true){
            System.out.println("true");
        }else{
            System.out.println("false");
        }

    }*/
}
