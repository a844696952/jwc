package com.yice.edu.cn.tap.service.workerKqOriginalData;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.TeacherAttendance;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.workerKq.*;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqOriginalDataFeign;
import com.yice.edu.cn.tap.feignClient.workerKqOriginlData.KqWorkerDailyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class KqWorkerDailyService {
    @Autowired
    private KqWorkerDailyFeign kqWorkerDailyFeign;
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    public KqWorkerDaily findKqWorkerDailyById(String id) {
        return kqWorkerDailyFeign.findKqWorkerDailyById(id);
    }

    public KqWorkerDaily saveKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.saveKqWorkerDaily(kqWorkerDaily);
    }

    public List<KqWorkerDaily> findKqWorkerDailyListByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findKqWorkerDailyListByCondition(kqWorkerDaily);
    }

    public KqWorkerDaily findOneKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findOneKqWorkerDailyByCondition(kqWorkerDaily);
    }

    public long findKqWorkerDailyCountByCondition(KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyFeign.findKqWorkerDailyCountByCondition(kqWorkerDaily);
    }

    public void updateKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyFeign.updateKqWorkerDaily(kqWorkerDaily);
    }

    public void deleteKqWorkerDaily(String id) {
        kqWorkerDailyFeign.deleteKqWorkerDaily(id);
    }

    public void deleteKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyFeign.deleteKqWorkerDailyByCondition(kqWorkerDaily);
    }

    public KqWorkerDaily workerClockInOrOut(KqWorkerDaily kqWorkerDaily3) {
        //判断学校位置是否改变
        KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
        kqWorkerDaily1.setKqDate(DateUtil.today());
        kqWorkerDaily1.setUserId(myId());
        List<KqWorkerDaily> kqDaily = kqWorkerDailyFeign.findKqWorkerDailyListByCondition(kqWorkerDaily1);
        if (kqDaily==null||kqDaily.size()!=1){
            //System.out.println("没有生成这个人的记录");
            return null;
        }
        KqWorkerDaily kqWorkerDaily = kqDaily.get(0);
        if (kqWorkerDaily.getTodayStandardRules()==null){
            //System.out.println("数据错误");
            return null;
        }
        kqWorkerDaily.setCaptureTime(kqWorkerDaily3.getCaptureTime());
        kqWorkerDaily.setTheKqLocation(kqWorkerDaily3.getTheKqLocation());
        ///查找此人此次打卡是否加班打卡
        //保存至原始记录表
        String nowTime1 = kqWorkerDaily3.getCaptureTime();
        DateTime parse = DateUtil.parse(nowTime1 + ":00");
        String capt = parse.toString();
        KqWorkerDaily theDaily = kqDaily.get(0);
        KqOriginalData kqOriginalData = new KqOriginalData();
        kqOriginalData.setUserId(theDaily.getUserId());
        kqOriginalData.setName(theDaily.getUserName());
        if (theDaily.getUserType().equals("2")){
            kqOriginalData.setUserType("3");
        }else {
            kqOriginalData.setUserType("1");
        }
        kqOriginalData.setCapturedTime(capt);
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setDerectionFlag("-1");
        kqOriginalData.setDeviceType(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_USER_PHONE);
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_USER_PHONE);
        kqOriginalData.setDeviceName("UserPhone");
        kqOriginalData.setWorknumber(theDaily.getWorknumber());
        curSchoolYearService.setSchoolYearTerm(kqOriginalData,mySchoolId());
        KqOriginalData savedOriginalData = kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
        if (savedOriginalData.getId()==null){
            //System.out.println("保存打卡原始记录失败了");
            return null;
        }
        //System.out.println("原始记录保存成功");
        //打卡规则校验并返回结果
        KqWorkerDaily kqWorkerDaily2 = kqWorkerDailyFeign.dailyRecords(kqWorkerDaily);
        if (kqWorkerDaily2==null){
            // System.out.println("打卡错误");
        }
        //System.out.println(kqWorkerDaily2);
        return kqWorkerDaily2;
    }

    public KqWorkerDaily getInClock() {
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setSchoolId(mySchoolId());
        kqWorkerDaily.setUserId(myId());
        KqWorkerDaily inClock = kqWorkerDailyFeign.getInClock(kqWorkerDaily);
        String todayNeedClockIn = inClock.getTodayNeedClockIn();
        if (todayNeedClockIn.equals("1")){
            WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
            String punchNumber = todayStandardRules.getPunchNumber();
            String noNeedCard = todayStandardRules.getNoNeedCard();
            String todayIsHoliday = inClock.getTodayIsHoliday();
            if (noNeedCard.equals("1")&&todayIsHoliday.equals("0")){
                if (punchNumber.equals("1")){
                    inClock.getPunchRules().setDuskOut("--");
                }
                if (punchNumber.equals("2")){
                    inClock.getPunchRules().setMorningOut("--");
                    inClock.getPunchRules().setDuskOut("--");
                }
            }
        }

        return inClock;
    }

    public KqWorkerMonth workerMonthStatistic(KqWorkerMonth kqWorkerMonth) {
        KqWorkerMonth workerMonthStatistic = kqWorkerDailyFeign.findWorkerMonthStatistic(kqWorkerMonth);
        return workerMonthStatistic;
    }
    public KqWorkerManageMonth  findWorkerManageMonthStatistic(KqWorkerMonth kqWorkerMonth) {
        KqWorkerManageMonth workerManageMonthStatistic = kqWorkerDailyFeign.findWorkerManageMonthStatistic(kqWorkerMonth);
        return workerManageMonthStatistic;
    }
    public KqWorkerManageNow  findWorkerManageNowStatistic(KqWorkerMonth kqWorkerMonth) {
        KqWorkerManageNow workerManageNowStatistic = kqWorkerDailyFeign.findWorkerManageNowStatistic(kqWorkerMonth);
        return workerManageNowStatistic;
    }
    public  List<String> isKqGroupManage() {
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setUserId(myId());
        kqWorkerDaily.setSchoolId(mySchoolId());
        List<String> kqGroupManage = kqWorkerDailyFeign.isKqGroupManage(kqWorkerDaily);
        return kqGroupManage;
    }
    public List <KqWorkerDaily> findWorkerManageNotInManStatistic(KqWorkerMonth kqWorkerMonth){
        return kqWorkerDailyFeign.findWorkerManageNotInManStatistic(kqWorkerMonth);
    }
    public TeacherAttendance dateCenterFindWorkerArrendance(TeacherAttendance attendance ){
        return kqWorkerDailyFeign.dateCenterFindWorkerArrendance(attendance);
    };
    public Double findTodayCheckRate(TeacherAttendance attendance ){
        return kqWorkerDailyFeign.findTodayCheckRate(attendance);
    };
}
