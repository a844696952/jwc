package com.yice.edu.cn.osp.controller.xw.workerKq;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.osp.service.xw.workerKq.SpecialDataService;
import com.yice.edu.cn.osp.service.xw.workerKq.WorkerKqRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.el.stream.Optional;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/workerKqRules")
@Api(value = "/workerKqRules", description = "职工考勤规则及特殊日期模块")
public class WorkerKqRulesController {
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private SpecialDataService specialDataService;

    @PostMapping("/save/addUser")
    @ApiOperation(value = "添加人员", notes = "返回已在其他组存在的人员", response = WorkerKqRules.class)
    public ResponseJson addUser(
            @ApiParam(value = "对象", required = true)
            @RequestBody WorkerKqRules workerKqRules) {
        boolean flag2 = true;
        WorkerKqRules workerKqRules1 = new WorkerKqRules();
        workerKqRules1.setSchoolId(mySchoolId());
        List<WorkerKqRules> list = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules1);
        List<Teacher> list1 = new ArrayList<>();
        List<Teacher> list2 = new ArrayList<>();
        if (list.size() > 0) {
            for (WorkerKqRules w : list) {
                for (Teacher teacher : workerKqRules.getBasicWorker()) {
                    if (w.getBasicWorker().stream().anyMatch(c -> c.getId() != null && c.getId().equals(teacher.getId()))) {
                        flag2 = false;
                        list1 = w.getBasicWorker().stream().filter(c -> c.getId().equals(teacher.getId())).collect(Collectors.toList());
                        list2.addAll(list1);
                    }
                }
            }
        }
        if (flag2 == false) {
            return new ResponseJson(0, list2);
        }
        return new ResponseJson();
    }

    @PostMapping("/save/saveWorkerKqRules")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = WorkerKqRules.class)
    public ResponseJson saveWorkerKqRules(
            @ApiParam(value = "对象", required = true)
            @RequestBody WorkerKqRules workerKqRules) {
        boolean flag1 = true;
        WorkerKqRules workerKqRules1 = new WorkerKqRules();
        workerKqRules1.setSchoolId(mySchoolId());
        List<WorkerKqRules> list = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules1);
        if (list.size() > 0) {
            for (WorkerKqRules w : list) {
                if (w.getKqGroupName().equals(workerKqRules.getKqGroupName())) {
                    flag1 = false;
                }
                for (Teacher teacher : workerKqRules.getBasicWorker()) {
                    List<Teacher> s1 = w.getBasicWorker().stream().filter(t -> t.getId() != null && !t.getId().equals(teacher.getId())).collect(Collectors.toList());
                    w.setBasicWorker(s1);
                    workerKqRulesService.updateWorkerKqRules(w);
                }
            }
        }
        if (flag1 == false) {
            return new ResponseJson(false, "考勤组名称已存在");
        }
        workerKqRules.setSchoolId(mySchoolId());
        workerKqRules.setRule("1");
        WorkerKqRules s = workerKqRulesService.saveWorkerKqRules(workerKqRules);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findWorkerKqRulesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = WorkerKqRules.class)
    public ResponseJson findWorkerKqRulesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        WorkerKqRules workerKqRules = workerKqRulesService.findWorkerKqRulesById(id);
        return new ResponseJson(workerKqRules);
    }

    @PostMapping("/update/updateWorkerKqRules")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWorkerKqRules(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody WorkerKqRules workerKqRules) {
        boolean flag1 = true;
        WorkerKqRules workerKqRules1 = new WorkerKqRules();
        workerKqRules1.setSchoolId(mySchoolId());
        List<WorkerKqRules> workerKqRulesList = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules1);
        List<WorkerKqRules> list = workerKqRulesList.stream().filter(s -> !s.getId().equals(workerKqRules.getId())).collect(Collectors.toList());
        if (list.size() > 0) {
            for (WorkerKqRules w : list) {
                if (w.getKqGroupName().equals(workerKqRules.getKqGroupName())) {
                    flag1 = false;
                }
                for (Teacher teacher : workerKqRules.getBasicWorker()) {
                    List<Teacher> s1 = w.getBasicWorker().stream().filter(t -> t.getId() != null && !t.getId().equals(teacher.getId())).collect(Collectors.toList());
                    w.setBasicWorker(s1);
                    workerKqRulesService.updateWorkerKqRules(w);
                }
            }
        }
        if (flag1 == false) {
            return new ResponseJson(false, "考勤组名称已存在");
        }
        workerKqRulesService.updateWorkerKqRules(workerKqRules);
        SpecialData specialData = new SpecialData();
        specialData.setKqGroupId(workerKqRules.getId());
        List<SpecialData> lll = specialDataService.findSpecialDataListByCondition(specialData);
        if (lll.size() > 0) {
            for (SpecialData ss : lll) {
                ss.setKqGroupName(workerKqRules.getKqGroupName());
                specialDataService.updateSpecialData(ss);
            }
        }
        return new ResponseJson();
    }

    @GetMapping("/look/lookWorkerKqRulesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = WorkerKqRules.class)
    public ResponseJson lookWorkerKqRulesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        WorkerKqRules workerKqRules = workerKqRulesService.findWorkerKqRulesById(id);
        return new ResponseJson(workerKqRules);
    }

    @PostMapping("/find/findWorkerKqRulessByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = WorkerKqRules.class)
    public ResponseJson findWorkerKqRulessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRules.setSchoolId(mySchoolId());
        List<WorkerKqRules> data = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
        long count = workerKqRulesService.findWorkerKqRulesCountByCondition(workerKqRules);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneWorkerKqRulesByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = WorkerKqRules.class)
    public ResponseJson findOneWorkerKqRulesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WorkerKqRules workerKqRules) {
        WorkerKqRules one = workerKqRulesService.findOneWorkerKqRulesByCondition(workerKqRules);
        return new ResponseJson(one);
    }

    @GetMapping("/delete/deleteWorkerKqRules/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWorkerKqRules(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        workerKqRulesService.deleteWorkerKqRules(id);
        SpecialData specialData = new SpecialData();
        specialData.setKqGroupId(id);
        specialDataService.deleteSpecialDataByCondition(specialData);
        return new ResponseJson();
    }

    @PostMapping("/find/findWorkerKqRulesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = WorkerKqRules.class)
    public ResponseJson findWorkerKqRulesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WorkerKqRules workerKqRules) {
        workerKqRules.setSchoolId(mySchoolId());
        workerKqRules.getPager().setSortOrder(Pager.DESC);
        workerKqRules.getPager().setSortField("createTime");
        List<WorkerKqRules> data = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
        long count = workerKqRulesService.findWorkerKqRulesCountByCondition(workerKqRules);
        return new ResponseJson(data, count);
    }

    @PostMapping("/save/saveSpecialData")
    @ApiOperation(value = "保存特殊日期", notes = "返回保存好的特殊日期", response = SpecialData.class)
    public ResponseJson saveSpecialData(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @RequestBody SpecialData specialData) {
        SpecialData specialData1 = new SpecialData();
        specialData1.setSchoolId(mySchoolId());
        specialData1.setKqGroupId(specialData.getKqGroupId());
        List<SpecialData> list = specialDataService.findSpecialDataListByCondition(specialData1);
        if (list.size() > 0) {
            for (SpecialData sl : list) {
                DateTime sStartTime = DateUtil.parseDate(specialData.getStartTime());
                DateTime sEndTime = DateUtil.parseDate(specialData.getEndTime());
                DateTime s1StartTime = DateUtil.parseDate(sl.getStartTime());
                DateTime s1EndTime = DateUtil.parseDate(sl.getEndTime());
                if (
                        (sStartTime.isAfterOrEquals(s1StartTime) && sStartTime.isBeforeOrEquals(s1EndTime))
                                || (sEndTime.isAfterOrEquals(s1StartTime) && sEndTime.isBeforeOrEquals(s1EndTime))
                                || ((sStartTime).isBeforeOrEquals(s1StartTime) && sEndTime.isAfterOrEquals(s1EndTime))
                                || (sStartTime.isAfterOrEquals(s1StartTime) && sEndTime.isBeforeOrEquals(s1EndTime))

                        ) {
                    return new ResponseJson(false, "特殊日期重复!");
                }
            }
        }
        specialData.setSchoolId(mySchoolId());
        SpecialData s = specialDataService.saveSpecialData(specialData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSpecialDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找特殊日期表", notes = "返回响应对象", response = SpecialData.class)
    public ResponseJson findSpecialDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        SpecialData specialData = specialDataService.findSpecialDataById(id);
        return new ResponseJson(specialData);
    }

    @PostMapping("/update/updateSpecialData")
    @ApiOperation(value = "修改特殊日期表对象", notes = "返回响应对象")
    public ResponseJson updateSpecialData(
            @ApiParam(value = "被修改的特殊日期表对象,对象属性不为空则修改", required = true)
            @RequestBody SpecialData specialData) {
        SpecialData specialData1 = new SpecialData();
        specialData1.setSchoolId(mySchoolId());
        specialData1.setKqGroupId(specialData.getKqGroupId());
        List<SpecialData> list = specialDataService.findSpecialDataListByCondition(specialData1);
        for (SpecialData sl : list) {
            if (!sl.getId().equals(specialData.getId())) {
                if (DateUtil.parse(sl.getEndTime()).equals(DateUtil.parse(specialData.getEndTime()))) {
                    return new ResponseJson(false, "特殊日期重复!");
                }
            }
        }
        specialDataService.updateSpecialData(specialData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSpecialDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找特殊日期表", notes = "返回响应对象", response = SpecialData.class)
    public ResponseJson lookSpecialDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        SpecialData specialData = specialDataService.findSpecialDataById(id);
        return new ResponseJson(specialData);
    }

    @PostMapping("/find/findSpecialDatasByCondition")
    @ApiOperation(value = "根据条件查找特殊日期表", notes = "返回响应对象", response = SpecialData.class)
    public ResponseJson findSpecialDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SpecialData specialData) {
        specialData.getPager().setSortField("createTime");
        specialData.getPager().setSortOrder(Pager.DESC);
        specialData.setSchoolId(mySchoolId());
        List<SpecialData> data = specialDataService.findSpecialDataListByCondition(specialData);
        long count = specialDataService.findSpecialDataCountByCondition(specialData);
        return new ResponseJson(data, count);
    }

    @GetMapping("/delete/deleteSpecialData/{id}")
    @ApiOperation(value = "根据id删除特殊日期", notes = "返回响应对象")
    public ResponseJson deleteSpecialData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        specialDataService.deleteSpecialData(id);
        return new ResponseJson();
    }

}
