package com.yice.edu.cn.xw.controller.workerKq;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.TeacherAttendance;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.workerKq.*;
import com.yice.edu.cn.xw.service.workerKq.KqDailyStatusDetailService;
import com.yice.edu.cn.xw.service.workerKq.KqWorkerDailyService;
import com.yice.edu.cn.xw.service.workerKq.WorkerKqRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kqWorkerDaily")
@Api(value = "/kqWorkerDaily", description = "教职工考勤模块")
public class KqWorkerDailyController {
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;
    @GetMapping("/findKqWorkerDailyById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public KqWorkerDaily findKqWorkerDailyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return kqWorkerDailyService.findKqWorkerDailyById(id);
    }

    @PostMapping("/saveKqWorkerDaily")
    @ApiOperation(value = "保存", notes = "返回对象")
    public KqWorkerDaily saveKqWorkerDaily(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyService.saveKqWorkerDaily(kqWorkerDaily);
        return kqWorkerDaily;
    }

    @PostMapping("/findKqWorkerDailyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<KqWorkerDaily> findKqWorkerDailyListByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
    }

    @PostMapping("/findKqWorkerDailyCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findKqWorkerDailyCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.findKqWorkerDailyCountByCondition(kqWorkerDaily);
    }

    @PostMapping("/updateKqWorkerDaily")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateKqWorkerDaily(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyService.updateKqWorkerDaily(kqWorkerDaily);
    }

    @GetMapping("/deleteKqWorkerDaily/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteKqWorkerDaily(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        kqWorkerDailyService.deleteKqWorkerDaily(id);
    }

    @PostMapping("/deleteKqWorkerDailyByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteKqWorkerDailyByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyService.deleteKqWorkerDailyByCondition(kqWorkerDaily);
    }

    @PostMapping("/findOneKqWorkerDailyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public KqWorkerDaily findOneKqWorkerDailyByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.findOneKqWorkerDailyByCondition(kqWorkerDaily);
    }

    @PostMapping("/dailyRecords")
    @ApiOperation(value = "生成日统计数据", notes = "返回单个,没有时为空")
    public KqWorkerDaily dailyRecords(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.dailyRecords(kqWorkerDaily);
    }

    @PostMapping("/createWorkerKqDailyRecord")
    @ApiOperation(value = "每日创建所有人职工考勤日统计", notes = "")
    public void createWorkerKqDailyRecord() {
        kqWorkerDailyService.createWorkerKqDailyRecord();
    }


    @PostMapping("/getInClock")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public KqWorkerDaily getInClock(
            @ApiParam(value = "对象")
            @RequestBody   KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.getInClock(kqWorkerDaily);
    }


    @PostMapping("/isKqGroupManager")
    @ApiOperation(value = "是否考勤组管理员", notes = "返回响应对象")
    public List<String> isKqGroupManage(
            @ApiParam(value = "对象")
            @RequestBody   KqWorkerDaily kqWorkerDaily) {
        List<String> groupIds = new ArrayList<>();
        WorkerKqRules workerKqRules = new WorkerKqRules();
        workerKqRules.setSchoolId(kqWorkerDaily.getSchoolId());
        List<WorkerKqRules> list = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
        /*if (list.size() > 0) {
            for (WorkerKqRules k : list) {
                if (k.getKqGroupManager().size() > 0 && k.getKqGroupManager() != null) {
                    List<Teacher> kqGroupManager = k.getKqGroupManager();
                    for (Teacher kk : kqGroupManager) {
                        if (kk.getId().equals(kqWorkerDaily.getUserId())) {
                            groupIds.add(k.getId());
                        }
                    }
                }
            }
        }*/
        if (CollectionUtil.isNotEmpty(list)){
            groupIds = list.stream().map(WorkerKqRules::getId).collect(Collectors.toList());
        }
        return groupIds;
    }

    @PostMapping("/findWorkerMonthStatistic")
    @ApiOperation(value = "查找指定时间区间内的月统计", notes = "返回对象")
    public KqWorkerMonth findWorkerMonthStatistic(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        return kqWorkerDailyService.findWorkerMonthStatistic(kqWorkerMonth);
    }


    @PostMapping("/findWorkerManageMonthStatistic")
    @ApiOperation(value = "管理员查找指定时间区间内的月统计", notes = "返回对象")
    public KqWorkerManageMonth findWorkerManageMonthStatistic(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        return kqWorkerDailyService.findWorkerManageMonthStatistic(kqWorkerMonth);
    }

    @PostMapping("/findWorkerManageNowStatistic")
    @ApiOperation(value = "管理员查找今日即时统计", notes = "返回对象")
    public KqWorkerManageNow findWorkerManageNowStatistic(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        return kqWorkerDailyService.findWorkerManageNowStatistic(kqWorkerMonth);
    }

    @PostMapping("/findWorkerManageNotInManStatistic")
    @ApiOperation(value = "管理员查看某一通知缺卡人员", notes = "返回对象")
    public List<KqWorkerDaily> findWorkerManageNotInManStatistic(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        return kqWorkerDailyService.findWorkerManageNotInManStatistic(kqWorkerMonth);
    }
    @PostMapping("/findSchoolLeaderMonthStatistic")
    @ApiOperation(value = "查找全校教师考勤情况", notes = "返回对象")
    public KqWorkerManageMonth findSchoolLeaderMonthStatistic(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerMonth kqWorkerMonth) {
        return kqWorkerDailyService.findSchoolLeaderMonthStatistic(kqWorkerMonth);
    }
    @GetMapping("/syncStatusDetailAndOaMsg")
    @ApiOperation(value = "同步OA", notes = "返回对象")
    public void syncStatusDetailAndOaMsg() {
        kqDailyStatusDetailService.syncStatusDetailAndOaMsg();
    }


    @PostMapping("/dateCenterFindWorkerArrendance")
    @ApiOperation(value = "数据中心教师考勤展示", notes = "返回对象")
    public TeacherAttendance dateCenterFindWorkerArrendance(
            @ApiParam(value = "数据中心教师考勤展示对象")
            @RequestBody TeacherAttendance attendance ) {
        return kqWorkerDailyService.dateCenterFindWorkerArrendance(attendance);
    }
    @PostMapping("/findTodayCheckRate")
    @ApiOperation(value = "数据中心教师考勤展示今日签到率", notes = "返回对象")
    public Double findTodayCheckRate(
            @ApiParam(value = "数据中心教师考勤展示对象")
            @RequestBody TeacherAttendance attendance ) {
        return kqWorkerDailyService.findTodayCheckRate(attendance.getSchooleId());
    }
    @PostMapping("/findNearSeverDaysWorkerKqHistogram")
    @ApiOperation(value = "数据中心教师考勤展示近七日考勤直方图", notes = "返回对象")
    public TeacherAttendance findNearSeverDaysWorkerKqHistogram(
            @ApiParam(value = "数据中心教师考勤展示对象")
            @RequestBody TeacherAttendance attendance ) {
        return kqWorkerDailyService.findNearSeverDaysWorkerKqHistogram(attendance);
    }

    /**
     * recalculate workerKq
     * */
    @GetMapping("/reCalculateKqWorkerDaily")
    @ApiOperation(value = "重新计算考勤日统计", notes = "返回对象")
    public void reCalculateKqWorkerDaily() {
        kqWorkerDailyService.reCalculateKqWorkerDaily();
    }


}
