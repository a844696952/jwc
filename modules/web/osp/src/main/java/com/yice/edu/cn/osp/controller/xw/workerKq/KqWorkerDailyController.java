package com.yice.edu.cn.osp.controller.xw.workerKq;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.workerKq.KqDailyStatusDetailService;
import com.yice.edu.cn.osp.service.xw.workerKq.KqWorkerDailyService;
import com.yice.edu.cn.osp.service.xw.workerKq.WorkerKqRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqWorkerDaily")
@Api(value = "/kqWorkerDaily", description = "教职工考勤日统计模块")
public class KqWorkerDailyController {
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/save/saveKqWorkerDaily")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = KqWorkerDaily.class)
    public ResponseJson saveKqWorkerDaily(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        KqWorkerDaily s = kqWorkerDailyService.saveKqWorkerDaily(kqWorkerDaily);
        return new ResponseJson(s);
    }

    @GetMapping("/update/find/findKqWorkerDailyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqWorkerDaily kqWorkerDaily = kqWorkerDailyService.findKqWorkerDailyById(id);
        return new ResponseJson(kqWorkerDaily);
    }

    @PostMapping("/update/updateKqWorkerDaily")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateKqWorkerDaily(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyService.updateKqWorkerDaily(kqWorkerDaily);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqWorkerDailyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = KqWorkerDaily.class)
    public ResponseJson lookKqWorkerDailyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqWorkerDaily kqWorkerDaily = kqWorkerDailyService.findKqWorkerDailyById(id);
        return new ResponseJson(kqWorkerDaily);
    }

    @PostMapping("/find/findKqWorkerDailysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDaily.getPager().setLike("userName");
        kqWorkerDaily.setSchoolId(mySchoolId());
        List<KqWorkerDaily> data = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        if (data == null||data.size()==0) {
            data = new ArrayList<>();
        }else {
            for (KqWorkerDaily inClock:data){
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
        }
        long count = kqWorkerDailyService.findKqWorkerDailyCountByCondition(kqWorkerDaily);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqWorkerDailyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = KqWorkerDaily.class)
    public ResponseJson findOneKqWorkerDailyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        KqWorkerDaily one = kqWorkerDailyService.findOneKqWorkerDailyByCondition(kqWorkerDaily);
        return new ResponseJson(one);
    }

    @GetMapping("/delete/deleteKqWorkerDaily/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqWorkerDaily(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqWorkerDailyService.deleteKqWorkerDaily(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqWorkerDailyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = KqWorkerDaily.class)
    public ResponseJson findKqWorkerDailyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        List<KqWorkerDaily> data = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        return new ResponseJson(data);
    }

    @PostMapping("/save/dailyRecords")
    @ApiOperation(value = "生成日统计数据", notes = "返回单个,没有时为空")
    public KqWorkerDaily dailyRecords(
            @ApiParam(value = "对象")
            @RequestBody KqWorkerDaily kqWorkerDaily) {
        return kqWorkerDailyService.dailyRecords(kqWorkerDaily);
    }

    @GetMapping("/find/findWorkerKqRulesListByCondition")
    @ApiOperation(value = "查找考勤组列表", notes = "返回响应对象,不包含总条数", response = WorkerKqRules.class)
    public ResponseJson findWorkerKqRulesListByCondition() {
        WorkerKqRules workerKqRules1 = new WorkerKqRules();
        workerKqRules1.setSchoolId(mySchoolId());
        List<WorkerKqRules> data = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules1);
        return new ResponseJson(data);
    }

    @GetMapping("/delete/deleteKqDailyStatusDetail/{id}")
    @ApiOperation(value = "根据id删除日统计详细", notes = "返回响应对象")
    public ResponseJson deleteKqDailyStatusDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqDailyStatusDetailService.deleteKqDailyStatusDetail(id);
        return new ResponseJson();
    }

    @PostMapping("/find/findKqDailyStatusDetailListByCondition")
    @ApiOperation(value = "根据条件查找日统计详细列表", notes = "返回响应对象,不包含总条数", response = KqDailyStatusDetail.class)
    public ResponseJson findKqDailyStatusDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        Pager pager = kqDailyStatusDetail.getPager();
        if (kqDailyStatusDetail.getRangeTime() != null && kqDailyStatusDetail.getRangeTime().length > 0) {
            String[] arr = kqDailyStatusDetail.getRangeTime();
            arr[0] = arr[0] + " 00:00:00";
            arr[1] = arr[1] + " 23:59:59";
            pager.setRangeField("createTime");
            pager.setRangeArray(arr);
        }
        kqDailyStatusDetail.setSchoolId(mySchoolId());
        pager.setLike("applicant");
        pager.setSortOrder(Pager.DESC);
        pager.setSortField("createTime");
        kqDailyStatusDetail.setSource("0");
        List<KqDailyStatusDetail> data = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
        long count = kqDailyStatusDetailService.findKqDailyStatusDetailCountByCondition(kqDailyStatusDetail);
        return new ResponseJson(data, count);
    }

    @PostMapping("/save/saveKqDailyStatusDetail")
    @ApiOperation(value = "保存日统计详细状态 手动修改", notes = "返回保存好的对象", response = KqDailyStatusDetail.class)
    public ResponseJson saveKqDailyStatusDetail(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqDailyStatusDetail kqDailyStatusDetail) {
        KqDailyStatusDetail kqDailyStatusDetail2 = new KqDailyStatusDetail();
        kqDailyStatusDetail2.setSchoolId(mySchoolId());
        kqDailyStatusDetail2.setApplicantId(kqDailyStatusDetail.getApplicantId());
        kqDailyStatusDetail2.setSource("0");
        List<KqDailyStatusDetail> list = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail2);
        List<KqDailyStatusDetail> list2 = list.stream().filter(l -> l.getEndTime() == null).collect(Collectors.toList());
        List<KqDailyStatusDetail> list1 = list.stream().filter(l -> l.getEndTime() != null).collect(Collectors.toList());
        if (list2.size() > 0) {
            for (KqDailyStatusDetail sl : list2) {
                if (sl.getFillUpTime().equals(kqDailyStatusDetail.getFillUpTime()) && kqDailyStatusDetail.getStatus().equals("5")) {
                    if (sl.getFillMissCardPoint().equals(kqDailyStatusDetail.getFillMissCardPoint())) {
                        kqDailyStatusDetailService.updateKqDailyStatusDetail(kqDailyStatusDetail);
                        return new ResponseJson(true, "修改成功");
                    } else {
                        kqDailyStatusDetail.setSchoolId(mySchoolId());
                        kqDailyStatusDetail.setCreateTime(DateUtil.now());
                        kqDailyStatusDetail.setApplyTime(DateUtil.now());
                        kqDailyStatusDetail.setSource("0");
                        kqDailyStatusDetail.setOperatorMan(LoginInterceptor.currentTeacher().getName());
                        KqDailyStatusDetail s = kqDailyStatusDetailService.saveKqDailyStatusDetail(kqDailyStatusDetail);
                        return new ResponseJson(s);
                    }
                }
            }
        }
        if (list1.size() > 0) {
            for (KqDailyStatusDetail s2 : list1) {
                if (kqDailyStatusDetail.getEndTime() != null) {
                    DateTime sStartTime = DateUtil.parse(kqDailyStatusDetail.getBeginTime());
                    DateTime sEndTime = DateUtil.parse(kqDailyStatusDetail.getEndTime());
                    DateTime s1StartTime = DateUtil.parse(s2.getBeginTime());
                    DateTime s1EndTime = DateUtil.parse(s2.getEndTime());
                    if (
                            (sStartTime.isAfterOrEquals(s1StartTime) && sStartTime.isBeforeOrEquals(s1EndTime))
                                    || (sEndTime.isAfterOrEquals(s1StartTime) && sEndTime.isBeforeOrEquals(s1EndTime))
                                    || ((sStartTime).isBeforeOrEquals(s1StartTime) && sEndTime.isAfterOrEquals(s1EndTime))
                                    || (sStartTime.isAfterOrEquals(s1StartTime) && sEndTime.isBeforeOrEquals(s1EndTime))
                            ) {
                        return new ResponseJson(false, "时间段不能出现重复!");
                    }
                }
            }
        }
        kqDailyStatusDetail.setSchoolId(mySchoolId());
        kqDailyStatusDetail.setCreateTime(DateUtil.now());
        kqDailyStatusDetail.setApplyTime(DateUtil.now());
        kqDailyStatusDetail.setSource("0");
        kqDailyStatusDetail.setOperatorMan(LoginInterceptor.currentTeacher().getName());
        curSchoolYearService.setSchoolYearTerm(kqDailyStatusDetail,mySchoolId());
        KqDailyStatusDetail s = kqDailyStatusDetailService.saveKqDailyStatusDetail(kqDailyStatusDetail);
        return new ResponseJson(s);
    }

    @PostMapping("/update/exportKqDaily")
    public void exportKqDaily(@ApiParam(value = "日考勤信息对象") @RequestBody KqWorkerDaily kqWorkerDaily, HttpServletResponse response) {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            kqWorkerDaily.getPager().setLike("userName");
            kqWorkerDaily.getPager().setSortOrder("asc");
            kqWorkerDaily.getPager().setSortField("kqDate");
            kqWorkerDaily.getPager().setPaging(false);
            Workbook workbook = kqWorkerDailyService.exportWorkerKq(kqWorkerDaily);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/update/createWorkerKqDailyRecord")
    public ResponseJson createWorkerKqDailyRecord(@ApiParam(value = "日考勤信息对象") @RequestBody KqWorkerDaily kqWorkerDaily) {
        kqWorkerDailyService.createWorkerKqDailyRecord();
        return new ResponseJson();
    }

    @GetMapping("/save/saveOaKqDailyStatusDetail")
    @ApiOperation(value = "保存OA同步日统计数据", notes = "返回保存好的对象")
    public ResponseJson saveOaKqDailyStatusDetail() {
        kqDailyStatusDetailService.saveOaKqDailyStatusDetail();
        return new ResponseJson();
    }
}
