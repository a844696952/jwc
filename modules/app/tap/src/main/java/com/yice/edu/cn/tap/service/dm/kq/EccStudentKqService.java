package com.yice.edu.cn.tap.service.dm.kq;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqRecord;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.tap.feignClient.dm.kq.EccStudentKqRecordFeign;
import com.yice.edu.cn.tap.feignClient.dm.kq.EccStudentKqSettingFeign;
import com.yice.edu.cn.tap.feignClient.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class EccStudentKqService {

    @Autowired
    private EccStudentKqSettingFeign eccStudentKqSettingFeign;



    @Autowired
    private EccStudentKqRecordFeign eccStudentKqRecordFeign;

    @Autowired
    private StudentFeign studentFeign;

    @CreateCache(name= Constant.Redis.ECC_CHECKIN_SETTING2,timeUnit= TimeUnit.DAYS,expire=1)
    private Cache<String, EccStudentKqSetting> settingCache;

    @PostConstruct
    private void init(){

        RefreshPolicy policy = RefreshPolicy.newPolicy(1, TimeUnit.DAYS);
        settingCache.config().setLoader(this::loadSettingFromDatabase);
        settingCache.config().setRefreshPolicy(policy);
    }

    private EccStudentKqSetting loadSettingFromDatabase(String schoolId){
        //重新查询
        EccStudentKqSetting setting = new EccStudentKqSetting();
        setting.setSchoolId(schoolId);
        return eccStudentKqSettingFeign.currentKqSetting(setting);
    }


    public String getKqSettingTiming(@NotNull String schoolId){
        EccStudentKqSetting setting = settingCache.get(schoolId);
        if(setting ==null){
            setting = loadSettingFromDatabase(schoolId);
            settingCache.put(schoolId,setting);
        }
        return setting==null?null:setting.getKqTime();
    }

    public void dk(Student student) {
        final ProtectedStudent protectedStudent = new ProtectedStudent(student);
        final EccStudentKqRecord record = new EccStudentKqRecord(protectedStudent);
        record.setSchoolId(student.getSchoolId());
        record.setClassesId(student.getClassesId());
        LocalTime nowTime = LocalTime.now();
        final String timingStr = getKqSettingTiming(student.getSchoolId());
        if(timingStr ==null){
            record.setKqStatus("4");
        }else{
            final LocalTime timing = LocalTime.parse(timingStr);
            if(nowTime.compareTo(timing)>0){
                record.setKqStatus("2");
            }else{
                record.setKqStatus("5");
            }
        }
        eccStudentKqRecordFeign.dk(record);

    }

    public String dkRecord(Student student) {
        return eccStudentKqRecordFeign.dkRecord(student);
    }

    public boolean findOneKqOriginalDataByCondition(EccStudentFace studentFace) {
        EccStudentKqRecord kqOriginalData = new EccStudentKqRecord();
        kqOriginalData.setStudentId(studentFace.getStudentId());
        final String today = DateUtil.today();

        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setRangeArray(new String[]{today+" 00:00:00",today+" 23:59:59"});
        pager.setRangeField("dkTime");
        kqOriginalData.setPager(pager);

        EccStudentKqRecord record = eccStudentKqRecordFeign.findOneEccStudentKqRecordByCondition(kqOriginalData);

        return record != null;

    }



    /**
     * 根据时间获取考勤统计
     * @param dmClassCard 班牌信息
     * @param date  当前统计时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public List<ProtectedStudent> getKqStatistics(DmClassCard dmClassCard, String date){
                ProtectedStudent protectedStudent=new ProtectedStudent();
                protectedStudent.setSchoolId(dmClassCard.getSchoolId());
                protectedStudent.setClassId(dmClassCard.getClassId());
                protectedStudent.setGradeId(dmClassCard.getGradeId());
                if(StringUtils.isNotEmpty(date)){
                    if(DateUtil.parse(date,Constant.DATE_FORMATTER_DAY).compareTo(DateUtil.parse(DateUtil.now(),Constant.DATE_FORMATTER_DAY)) == 0){
                        protectedStudent.setKqEndDate(DateUtil.now());
                    }else{
                        protectedStudent.setKqEndDate(DateUtils.getOriginalDateTime(date,2));
                    }
                }else{
                    protectedStudent.setKqEndDate(DateUtil.now());
                }
                protectedStudent.setKqBeginDate(DateUtils.getOriginalDateTime(date,1));
                return  eccStudentKqRecordFeign.findCurrentKqListByCondition(protectedStudent);
    }

    /**
     * 查询一段时间内的当前学生考勤数据
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findKqListByStudentId(ProtectedStudent protectedStudent){
        if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate())){
            //判断是当前月 还是 过期的月
            int currentMonth = DateUtil.thisMonth();
            int oldMonth = DateUtil.month(DateUtil.parse(protectedStudent.getKqBeginDate(), Constant.DATE_FORMATTER_DAY));
            if(oldMonth > currentMonth){
                return  new ArrayList<>();
            }else if(oldMonth == currentMonth){
                protectedStudent.setKqEndDate(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
            }else{
                DateTime dateTime = DateUtil.endOfMonth(DateUtil.parse(protectedStudent.getKqBeginDate(), Constant.DATE_FORMATTER_DAY));
                protectedStudent.setKqEndDate(DateUtil.format(dateTime,Constant.DATE_FORMATTER));
            }
            String kqBeginDate= DateUtils.getMonthOfDate(protectedStudent.getKqBeginDate());
            if(StringUtils.isNotEmpty(kqBeginDate)){
                protectedStudent.setKqBeginDate(kqBeginDate);
            }
        }
        return  eccStudentKqRecordFeign.findKqListByStudentId(protectedStudent);
    }


    /**
     * 查询一段时间内的考勤统计
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findStudentKqByCondition(ProtectedStudent protectedStudent){
       return eccStudentKqRecordFeign.findStudentKqByCondition(protectedStudent);
    }


}
