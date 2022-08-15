package com.yice.edu.cn.tap.controller.workerKqOriginalData;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.TeacherAttendance;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.workerKq.*;
import com.yice.edu.cn.tap.service.school.SchoolService;
import com.yice.edu.cn.tap.service.workerKqOriginalData.KqWorkerDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqWorkerDaily")
@Api(value = "/kqWorkerDaily",description = "教职工考勤日统计模块")
public class KqWorkerDailyController {
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private SchoolService schoolService;

    /*@PostMapping("/save/saveKqWorkerDaily")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= KqWorkerDaily.class)
    public ResponseJson saveKqWorkerDaily(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily){
        KqWorkerDaily s=kqWorkerDailyService.saveKqWorkerDaily(kqWorkerDaily);
        return new ResponseJson(s);
    }

    @GetMapping("/update/find/findKqWorkerDailyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        KqWorkerDaily kqWorkerDaily=kqWorkerDailyService.findKqWorkerDailyById(id);
        return new ResponseJson(kqWorkerDaily);
    }

    @PostMapping("/update/updateKqWorkerDaily")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateKqWorkerDaily(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily){
        kqWorkerDailyService.updateKqWorkerDaily(kqWorkerDaily);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqWorkerDailyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=KqWorkerDaily.class)
    public ResponseJson lookKqWorkerDailyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        KqWorkerDaily kqWorkerDaily=kqWorkerDailyService.findKqWorkerDailyById(id);
        return new ResponseJson(kqWorkerDaily);
    }

    @PostMapping("/find/findKqWorkerDailysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerDaily kqWorkerDaily){
        List<KqWorkerDaily> data=kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        long count=kqWorkerDailyService.findKqWorkerDailyCountByCondition(kqWorkerDaily);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneKqWorkerDailyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=KqWorkerDaily.class)
    public ResponseJson findOneKqWorkerDailyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqWorkerDaily kqWorkerDaily){
        KqWorkerDaily one=kqWorkerDailyService.findOneKqWorkerDailyByCondition(kqWorkerDaily);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/deleteKqWorkerDaily/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqWorkerDaily(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        kqWorkerDailyService.deleteKqWorkerDaily(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqWorkerDailyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerDaily kqWorkerDaily){
        List<KqWorkerDaily> data=kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        return new ResponseJson(data);
    }
    */
    @GetMapping("/update/getInClock")
    @ApiOperation(value = "进入教职工手机打卡页", notes = "返回打卡是否成功", response = KqWorkerDaily.class)
    public ResponseJson getInClock() {
        //教职工打卡
        KqWorkerDaily inClock = kqWorkerDailyService.getInClock();
        return new ResponseJson(inClock);
    }

    @PostMapping("/update/workerClockInOrOut")
    @ApiOperation(value = "教职工手机打卡", notes = "返回打卡是否成功", response = KqWorkerDaily.class)
    public ResponseJson workerClockInOrOut(@ApiParam(value = "传打卡位置theKqLocation,学校位置schoolLocation，打卡时间captureTime 07:05") @RequestBody KqWorkerDaily kqWorkerDaily) {
        //教职工打卡
        KqLocation schoolLocation = kqWorkerDaily.getSchoolLocation();
        School schoolById = schoolService.findSchoolById(mySchoolId());
        String lat = schoolById.getLat();
        String lon = schoolById.getLon();
        if (!lat.equals(schoolLocation.getLat())||!lon.equals(schoolLocation.getLon())){
            return new ResponseJson(false,"学校已经更改位置，请重新确认您的位置信息");
        }
        kqWorkerDailyService.workerClockInOrOut(kqWorkerDaily);
        return new ResponseJson(true,"打卡成功");
    }

    @PostMapping("/find/workerDailyStatistic")
    @ApiOperation(value = "查看本人指定日统计", notes = "返回当天考勤详情", response = KqWorkerDaily.class)
    public ResponseJson workerDailyStatistic(@ApiParam(value = "传考勤日期kqDate") @RequestBody KqWorkerDaily kqWorkerDaily) {
        //教职工打卡
        kqWorkerDaily.setUserId(myId());
        kqWorkerDaily.setSchoolId(mySchoolId());
        kqWorkerDaily.setPager(null);
        List<KqWorkerDaily> kqWorkerDailyListByCondition = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        if (kqWorkerDailyListByCondition == null || kqWorkerDailyListByCondition.size() == 0) {
            return new ResponseJson(false, "当日没有考勤记录");
        } else if (kqWorkerDailyListByCondition == null || kqWorkerDailyListByCondition.size() > 1) {
            return new ResponseJson(false, "当日考勤记录有误");
        }
        KqWorkerDaily inClock = kqWorkerDailyListByCondition.get(0);
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
        return new ResponseJson(inClock);
    }

    @PostMapping("/find/workerMonthStatistic")
    @ApiOperation(value = "查看本人指定月统计", notes = "返回当月考勤详情", response = KqWorkerDaily.class)
    public ResponseJson workerMonthStatistic(@ApiParam(value = "传考勤日期kqDate") @RequestBody KqWorkerMonth KqWorkerMonth) {
        //教职工打卡
        KqWorkerMonth.setUserId(myId());
        KqWorkerMonth.setSchoolId(mySchoolId());
        KqWorkerMonth workerMonthStatistic = kqWorkerDailyService.workerMonthStatistic(KqWorkerMonth);
        return new ResponseJson(workerMonthStatistic);
    }

    @PostMapping("/find/findWorkerManageMonthStatistic")
    @ApiOperation(value = "查看管理员查看指定时间统计", notes = "返回当月考勤详情", response = KqWorkerDaily.class)
    public ResponseJson findWorkerManageMonthStatistic(@ApiParam(value = "传考勤日期kqDate;今日传空，日传日期2019-05-24，月传2019-05 ;指定人员传schoolNotifySendObjectList，不指定传空，默认查本人负责的考勤组") @RequestBody KqWorkerMonth kqWorkerMonth) {
        //教职工打卡
        kqWorkerMonth.setSchoolId(mySchoolId());
        if (kqWorkerMonth.getSchoolNotifySendObjectList() == null || kqWorkerMonth.getSchoolNotifySendObjectList().size() == 0) {
            List<String> groupIdlist = kqWorkerDailyService.isKqGroupManage();
            if (groupIdlist.size() == 0) {
                return new ResponseJson(false, "不是考勤管理员，没权限");
            }
            kqWorkerMonth.setGroupIdList(groupIdlist);
        }
        KqWorkerManageMonth workerManageMonthStatistic = kqWorkerDailyService.findWorkerManageMonthStatistic(kqWorkerMonth);
        return new ResponseJson(workerManageMonthStatistic);
    }


    @PostMapping("/find/findWorkerManageNowStatistic")
    @ApiOperation(value = "查看管理员查看当前日统计", notes = "返回今日考勤详情", response = KqWorkerDaily.class)
    public ResponseJson findWorkerManageNowStatistic(@ApiParam(value = "指定人员传schoolNotifySendObjectList，不指定传空，默认查本人负责的考勤组") @RequestBody KqWorkerMonth kqWorkerMonth) {
        //教职工打卡
        kqWorkerMonth.setSchoolId(mySchoolId());
        if (kqWorkerMonth.getSchoolNotifySendObjectList() == null || kqWorkerMonth.getSchoolNotifySendObjectList().size() == 0) {
            List<String> groupIdlist = kqWorkerDailyService.isKqGroupManage();
            if (groupIdlist.size() == 0) {
                return new ResponseJson(false, "不是考勤管理员，没权限");
            }
            kqWorkerMonth.setGroupIdList(groupIdlist);
        }
        KqWorkerManageNow workerManageNowStatistic = kqWorkerDailyService.findWorkerManageNowStatistic(kqWorkerMonth);
        return new ResponseJson(workerManageNowStatistic);
    }

    @GetMapping("/find/isKqGroupManage")
    @ApiOperation(value = "查看是否有考勤统计权限", notes = "返回data是否有权限", response = KqWorkerDaily.class)
    public ResponseJson isKqGroupManage() {
        List<String> groupIdlist = kqWorkerDailyService.isKqGroupManage();
        if (groupIdlist.size() > 0) {

            return new ResponseJson(true);
        }
        return new ResponseJson(false);
    }

    @PostMapping("/find/findWorkerManageNotInManStatistic")
    @ApiOperation(value = "管理员查看某一通知缺卡人员", notes = "返回对象")
    public ResponseJson findWorkerManageNotInManStatistic(
            @ApiParam(value = "manageSendTime通知记录的时间")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        kqWorkerMonth.setSchoolId(mySchoolId());
        kqWorkerMonth.setUserId(myId());
        List <KqWorkerDaily> workerManageNotInManStatistic = kqWorkerDailyService.findWorkerManageNotInManStatistic(kqWorkerMonth);
        return new ResponseJson(workerManageNotInManStatistic);
    }

    @GetMapping("/dateCenterFindWorkerArrendance")
    @ApiOperation(value = "数据中心教师考勤展示", notes = "返回对象")
    public ResponseJson dateCenterFindWorkerArrendance() {
        TeacherAttendance attendance = new TeacherAttendance();
        attendance.setSchooleId(mySchoolId());
        TeacherAttendance teacherAttendance = kqWorkerDailyService.dateCenterFindWorkerArrendance(attendance);
        return new ResponseJson(teacherAttendance);
    }

    @GetMapping("/findTodayCheckRate")
    @ApiOperation(value = "数据中心教师考勤展示今日考勤率", notes = "返回对象")
    public ResponseJson findTodayCheckRate() {
        TeacherAttendance attendance = new TeacherAttendance();
        attendance.setSchooleId(mySchoolId());
        Double rate = kqWorkerDailyService.findTodayCheckRate(attendance);
        return new ResponseJson(rate);
    }
}