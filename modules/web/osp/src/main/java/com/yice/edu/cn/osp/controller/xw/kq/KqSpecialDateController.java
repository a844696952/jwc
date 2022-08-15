package com.yice.edu.cn.osp.controller.xw.kq;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import com.yice.edu.cn.osp.service.xw.kq.KqSpecialDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqSpecialDate")
@Api(value = "/kqSpecialDate", description = "考勤管理特殊日期表模块")
public class KqSpecialDateController {
    @Autowired
    private KqSpecialDateService kqSpecialDateService;

    @PostMapping("/saveKqSpecialDate")
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

    @GetMapping("/update/findKqSpecialDateById/{id}")
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

    @PostMapping("/findKqSpecialDatesByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表 ", notes = " 返回响应对象", response = KqSpecialDate.class)
    public ResponseJson findKqSpecialDatesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")  @Validated @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDate.setSchoolId(mySchoolId());
        List<KqSpecialDate> data = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate);
        long count = kqSpecialDateService.findKqSpecialDateCountByCondition(kqSpecialDate);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneKqSpecialDateByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理特殊日期表, 结果必须为单条数据", notes = " 没有时返回空", response = KqSpecialDate.class)
    public ResponseJson findOneKqSpecialDateByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqSpecialDate kqSpecialDate) {
        KqSpecialDate one = kqSpecialDateService.findOneKqSpecialDateByCondition(kqSpecialDate);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqSpecialDate/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqSpecialDate(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqSpecialDateService.deleteKqSpecialDate(id);
        return new ResponseJson();
    }


    @PostMapping("/findKqSpecialDateListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表列表", notes = " 返回响应对象, 不包含总条数", response = KqSpecialDate.class)
    public ResponseJson findKqSpecialDateListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDate.setSchoolId(mySchoolId());
        List<KqSpecialDate> data = kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate);
        return new ResponseJson(data);
    }
}
