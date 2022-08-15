package com.yice.edu.cn.ecc.service.kq;

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
import com.yice.edu.cn.ecc.feignClient.kq.EccStudentFaceFeign;
import com.yice.edu.cn.ecc.feignClient.kq.EccStudentKqRecordFeign;
import com.yice.edu.cn.ecc.feignClient.kq.EccStudentKqSettingFeign;
import com.yice.edu.cn.ecc.feignClient.student.StudentFeign;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.currentDmClassCard;


@Service
public class EccStudentKqService {

    @Autowired
    private EccStudentKqSettingFeign eccStudentKqSettingFeign;

    @Autowired
    private EccStudentFaceFeign eccStudentFaceFeign;

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
                if((StringUtils.isNotEmpty(date) &&  DateUtil.parse(date,Constant.DATE_FORMATTER_DAY).compareTo (DateUtil.parse(DateUtil.now(),Constant.DATE_FORMATTER_DAY)) == 0)){
                    protectedStudent.setKqEndDate(DateUtil.now());
                 }else if(org.apache.commons.lang3.StringUtils.isEmpty(date)){
                    protectedStudent.setKqEndDate(DateUtil.now());
                }else{
                    protectedStudent.setKqEndDate(DateUtils.getOriginalDateTime(date,2));
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
            //计算出当月最后一天
            DateTime lastDay = DateUtil.endOfMonth(DateTime.now());
            DateTime firstDay=DateUtil.beginOfMonth(DateTime.now());
            if(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY).isAfter(lastDay)){
                return  new ArrayList<>();
            }else if(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY).isAfterOrEquals(firstDay) && DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY)
                    .isBeforeOrEquals(lastDay)){
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

    public String  getKqBeginTime(String schoolId){
        ProtectedStudent protectedStudent=new ProtectedStudent();
        protectedStudent.setSchoolId(schoolId);
        return eccStudentKqRecordFeign.getKqBeginTime(protectedStudent);
    }




    /**
     * 接口作废
     * @param classId
     * @param date
     * @return
     */
    @Deprecated
    public List<ProtectedStudent> getTodayStatistics(String classId, String date) {
        EccStudentKqRecord record = new EccStudentKqRecord();
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setSortField("dkTime");
        pager.setSortOrder("DESC");
        pager.setRangeField("dkTime");
        pager.setRangeArray(new String[]{date+" 00:00:00",date+" 23:59:59"});
        record.setPager(pager);
        record.setClassesId(classId);
        final List<EccStudentKqRecord> datas = eccStudentKqRecordFeign.findEccStudentKqRecordListByCondition(record);
        final Map<String,EccStudentKqRecord> datasMap = datas.stream().collect(Collectors.toMap(EccStudentKqRecord::getStudentId, d->d));


        final DmClassCard dmClassCard = currentDmClassCard();
        Student student = new Student();
        student.setClassesId(dmClassCard.getClassId());
        student.setSchoolId(dmClassCard.getSchoolId());
        final List<Student> students = studentFeign.findStudentListByCondition(student);
        final List<String> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());
        EccStudentFace studentFace = new EccStudentFace();
        studentFace.setClassesId(classId);
        final List<EccStudentFace> studentFaces = eccStudentFaceFeign.findEccStudentFaceListByCondition(studentFace);

        final List<ProtectedStudent> protectedStudents= studentFaces.stream()
                .filter(sf-> studentIds.contains(sf.getStudentId()))
                .map(s->{
                    ProtectedStudent protectedStudent = new ProtectedStudent(s.getStudent());
                    protectedStudent.setHead(s.getFaceImg());
                    final EccStudentKqRecord kqData = datasMap.get(s.getStudentId());
                    if(kqData!=null){
                        protectedStudent.setTime(kqData.getCreateTime());
                        protectedStudent.setKqStatus(kqData.getKqStatus());
                    }else{
                        protectedStudent.setKqStatus("1");
                    }
                    return protectedStudent;
                })
                .collect(Collectors.toList());

        return protectedStudents;
    }
}
