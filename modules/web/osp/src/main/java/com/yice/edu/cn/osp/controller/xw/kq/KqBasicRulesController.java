package com.yice.edu.cn.osp.controller.xw.kq;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.osp.service.xw.kq.KqBasicRulesService;
import com.yice.edu.cn.osp.service.xw.kq.KqSpecialDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqBasicRules")
@Api(value = "/kqBasicRules", description = "考勤管理基础规则表模块")
public class KqBasicRulesController {
    @Autowired
    private KqBasicRulesService kqBasicRulesService;
    @Autowired
    private KqSpecialDateService kqSpecialDateService;

    @PostMapping("/save/saveKqBasicRules")
    @ApiOperation(value = "保存考勤管理基础规则表对象", notes = "返回保存好的考勤管理基础规则表对象", response = KqBasicRules.class)
    public ResponseJson saveKqBasicRules(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @RequestBody KqBasicRules kqBasicRules) {
        boolean flag = true;
        KqBasicRules kqBasicRules1 = new KqBasicRules();
        kqBasicRules1.setSchoolId(mySchoolId());
        List<KqBasicRules> rulesList = kqBasicRulesService.findKqBasicRulesListByCondition(kqBasicRules1);
        for (KqBasicRules rl : rulesList) {
            if (rl.getRule().equals("1")) {
                flag = false;
                kqBasicRules.setId(rl.getId());
            }
        }
        if (flag == false) {
            kqBasicRulesService.updateKqBasicRules(kqBasicRules);
            return new ResponseJson();
        } else {
            kqBasicRules.setSchoolId(mySchoolId());
            kqBasicRules.setRule("1");
            KqBasicRules s = kqBasicRulesService.saveKqBasicRules(kqBasicRules);
            return new ResponseJson(s);
        }
    }

    @GetMapping("/find/findKqBasicRulesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤管理基础规则表", notes = "返回响应对象", response = KqBasicRules.class)
    public ResponseJson findKqBasicRulesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqBasicRules kqBasicRules = kqBasicRulesService.findKqBasicRulesById(id);
        return new ResponseJson(kqBasicRules);
    }

    @PostMapping("/update/updateKqBasicRules")
    @ApiOperation(value = "修改考勤管理基础规则表对象", notes = "返回响应对象")
    public ResponseJson updateKqBasicRules(
            @ApiParam(value = "被修改的考勤管理基础规则表对象,对象属性不为空则修改", required = true)
            @RequestBody KqBasicRules kqBasicRules) {
        kqBasicRulesService.updateKqBasicRules(kqBasicRules);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqBasicRulesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤管理基础规则表", notes = "返回响应对象", response = KqBasicRules.class)
    public ResponseJson lookKqBasicRulesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqBasicRules kqBasicRules = kqBasicRulesService.findKqBasicRulesById(id);
        return new ResponseJson(kqBasicRules);
    }

    @PostMapping("/find/findKqBasicRulessByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表(有条数)", notes = "返回响应对象", response = KqBasicRules.class)
    public ResponseJson findKqBasicRulessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqBasicRules kqBasicRules) {
        kqBasicRules.setSchoolId(mySchoolId());
        List<KqBasicRules> data = kqBasicRulesService.findKqBasicRulesListByCondition(kqBasicRules);
        long count = kqBasicRulesService.findKqBasicRulesCountByCondition(kqBasicRules);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqBasicRulesByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理基础规则表,结果必须为单条数据", notes = "没有时返回空", response = KqBasicRules.class)
    public ResponseJson findOneKqBasicRulesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqBasicRules kqBasicRules) {
        KqBasicRules one = kqBasicRulesService.findOneKqBasicRulesByCondition(kqBasicRules);
        return new ResponseJson(one);
    }

    @GetMapping("/delete/deleteKqBasicRules/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqBasicRules(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqBasicRulesService.deleteKqBasicRules(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqBasicRulesListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表(无需条数)", notes = "返回响应对象,不包含总条数", response = KqBasicRules.class)
    public ResponseJson findKqBasicRulesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqBasicRules kqBasicRules) {
        kqBasicRules.setSchoolId(mySchoolId());
        List<KqBasicRules> data = kqBasicRulesService.findKqBasicRulesListByCondition(kqBasicRules);
        return new ResponseJson(data);
    }

    @PostMapping("/save/saveKqSpecialDate")
    @ApiOperation(value = "保存考勤管理特殊日期表 对象", notes = " 返回保存好的考勤管理特殊日期表对象", response = KqSpecialDate.class)
    public ResponseJson saveKqSpecialDate(
            @ApiParam(value = "考勤管理特殊日期表 对象", required = true)
            @RequestBody KqSpecialDate kqSpecialDate) {
        KqSpecialDate kqSpecialDate1 = new KqSpecialDate();
        kqSpecialDate1.setSchoolId(mySchoolId());
        List<KqSpecialDate> rulesList = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate1);
        for (KqSpecialDate rl : rulesList) {
            if (DateUtil.parseDate(rl.getEndTime()).isAfterOrEquals(DateUtil.parseDate(kqSpecialDate.getStartTime()))) {
                return new ResponseJson(false, "特殊日期重复!");
            }
        }
        kqSpecialDate.setSchoolId(mySchoolId());
        KqSpecialDate s = kqSpecialDateService.saveKqSpecialDate(kqSpecialDate);
        return new ResponseJson(s);
    }

    @GetMapping("/find/findKqSpecialDateById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤管理特殊日期表 ", notes = " 返回响应对象", response = KqSpecialDate.class)
    public ResponseJson findKqSpecialDateById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqSpecialDate kqSpecialDate = kqSpecialDateService.findKqSpecialDateById(id);
        return new ResponseJson(kqSpecialDate);
    }

    @PostMapping("/update/updateKqSpecialDate")
    @ApiOperation(value = "修改考勤管理特殊日期表 对象", notes = " 返回响应对象")
    public ResponseJson updateKqSpecialDate(
            @ApiParam(value = "被修改的考勤管理特殊日期表 对象, 对象属性不为空则修改", required = true)
            @RequestBody KqSpecialDate kqSpecialDate) {
        KqSpecialDate kqSpecialDate1 = new KqSpecialDate();
        kqSpecialDate1.setSchoolId(mySchoolId());
        List<KqSpecialDate> rulesList = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate1);
        for (KqSpecialDate rl : rulesList) {
            if (!rl.getId().equals(kqSpecialDate.getId())) {
                if (DateUtil.parseDate(rl.getEndTime()).isAfterOrEquals(DateUtil.parseDate(kqSpecialDate.getStartTime()))) {
                    return new ResponseJson(false, "特殊日期重复!");
                }
            }
        }
        kqSpecialDateService.updateKqSpecialDate(kqSpecialDate);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqSpecialDateById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤管理特殊日期表 ", notes = " 返回响应对象", response = KqSpecialDate.class)
    public ResponseJson lookKqSpecialDateById(@ApiParam(value = "去查看页面,需要用到的id", required = true)
                                              @PathVariable String id) {
        KqSpecialDate kqSpecialDate = kqSpecialDateService.findKqSpecialDateById(id);
        return new ResponseJson(kqSpecialDate);
    }

    @PostMapping("/find/findKqSpecialDatesByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表(有条数) ", notes = " 返回响应对象", response = KqSpecialDate.class)
    public ResponseJson findKqSpecialDatesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询") @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDate.setSchoolId(mySchoolId());
        List<KqSpecialDate> data = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate);
        long count = kqSpecialDateService.findKqSpecialDateCountByCondition(kqSpecialDate);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqSpecialDateByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理特殊日期表, 结果必须为单条数据", notes = " 没有时返回空", response = KqSpecialDate.class)
    public ResponseJson findOneKqSpecialDateByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqSpecialDate kqSpecialDate) {
        KqSpecialDate one = kqSpecialDateService.findOneKqSpecialDateByCondition(kqSpecialDate);
        return new ResponseJson(one);
    }

    @GetMapping("/delete/deleteKqSpecialDate/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqSpecialDate(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqSpecialDateService.deleteKqSpecialDate(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqSpecialDateListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表列表(无需条数)", notes = " 返回响应对象, 不包含总条数", response = KqSpecialDate.class)
    public ResponseJson findKqSpecialDateListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDate.getPager().setSortOrder(Pager.DESC);
        kqSpecialDate.getPager().setSortField("createTime");
        kqSpecialDate.setSchoolId(mySchoolId());
        List<KqSpecialDate> data = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate);
        long count = kqSpecialDateService.findKqSpecialDateCountByCondition(kqSpecialDate);
        return new ResponseJson(data, count);
    }
}
