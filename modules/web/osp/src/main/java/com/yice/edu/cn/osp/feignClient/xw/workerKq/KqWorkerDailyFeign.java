package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.TeacherAttendance;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerManageMonth;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw", contextId = "KqWorkerDailyFeign", path = "/kqWorkerDaily")
public interface KqWorkerDailyFeign {
    @GetMapping("/findKqWorkerDailyById/{id}")
    KqWorkerDaily findKqWorkerDailyById(@PathVariable("id") String id);

    @PostMapping("/saveKqWorkerDaily")
    KqWorkerDaily saveKqWorkerDaily(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/findKqWorkerDailyListByCondition")
    List<KqWorkerDaily> findKqWorkerDailyListByCondition(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/findOneKqWorkerDailyByCondition")
    KqWorkerDaily findOneKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/findKqWorkerDailyCountByCondition")
    long findKqWorkerDailyCountByCondition(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/updateKqWorkerDaily")
    void updateKqWorkerDaily(KqWorkerDaily kqWorkerDaily);

    @GetMapping("/deleteKqWorkerDaily/{id}")
    void deleteKqWorkerDaily(@PathVariable("id") String id);

    @PostMapping("/deleteKqWorkerDailyByCondition")
    void deleteKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/dailyRecords")
    KqWorkerDaily dailyRecords(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/createWorkerKqDailyRecord")
    void createWorkerKqDailyRecord();

    @PostMapping("/findSchoolLeaderMonthStatistic")
    KqWorkerManageMonth findSchoolLeaderMonthStatistic(  KqWorkerMonth kqWorkerMonth);

    @PostMapping("/isKqGroupManager")
    List<String> isKqGroupManage(KqWorkerDaily kqWorkerDaily);

    @PostMapping("/dateCenterFindWorkerArrendance")
    TeacherAttendance dateCenterFindWorkerArrendance(TeacherAttendance attendance);
    @PostMapping("/findTodayCheckRate")
    Double findTodayCheckRate(TeacherAttendance teacherAttendance);
    @PostMapping("/findNearSeverDaysWorkerKqHistogram")
    TeacherAttendance findNearSeverDaysWorkerKqHistogram(TeacherAttendance teacherAttendance);
}
