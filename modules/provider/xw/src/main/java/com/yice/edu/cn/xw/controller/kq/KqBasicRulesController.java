package com.yice.edu.cn.xw.controller.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.xw.service.kq.KqBasicRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqBasicRules")
@Api(value = "/kqBasicRules",description = "考勤管理基础规则表模块")
public class KqBasicRulesController {
    @Autowired
    private KqBasicRulesService kqBasicRulesService;

    @GetMapping("/findKqBasicRulesById/{id}")
    @ApiOperation(value = "通过id查找考勤管理基础规则表", notes = "返回考勤管理基础规则表对象")
    public KqBasicRules findKqBasicRulesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return kqBasicRulesService.findKqBasicRulesById(id);
    }

    @PostMapping("/saveKqBasicRules")
    @ApiOperation(value = "保存考勤管理基础规则表", notes = "返回考勤管理基础规则表对象")
    public KqBasicRules saveKqBasicRules(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @RequestBody KqBasicRules kqBasicRules){
        kqBasicRulesService.saveKqBasicRules(kqBasicRules);
        return kqBasicRules;
    }

    @PostMapping("/findKqBasicRulesListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表", notes = "返回考勤管理基础规则表列表")
    public List<KqBasicRules> findKqBasicRulesListByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody KqBasicRules kqBasicRules){
        return kqBasicRulesService.findKqBasicRulesListByCondition(kqBasicRules);
    }
    @PostMapping("/findKqBasicRulesCountByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表个数", notes = "返回考勤管理基础规则表总个数")
    public long findKqBasicRulesCountByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody KqBasicRules kqBasicRules){
        return kqBasicRulesService.findKqBasicRulesCountByCondition(kqBasicRules);
    }

    @PostMapping("/updateKqBasicRules")
    @ApiOperation(value = "修改考勤管理基础规则表", notes = "考勤管理基础规则表对象必传")
    public void updateKqBasicRules(
            @ApiParam(value = "考勤管理基础规则表对象,对象属性不为空则修改", required = true)
            @RequestBody KqBasicRules kqBasicRules){
        kqBasicRulesService.updateKqBasicRules(kqBasicRules);
    }

    @GetMapping("/deleteKqBasicRules/{id}")
    @ApiOperation(value = "通过id删除考勤管理基础规则表")
    public void deleteKqBasicRules(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @PathVariable String id){
        kqBasicRulesService.deleteKqBasicRules(id);
    }
    @PostMapping("/deleteKqBasicRulesByCondition")
    @ApiOperation(value = "根据条件删除考勤管理基础规则表")
    public void deleteKqBasicRulesByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody KqBasicRules kqBasicRules){
        kqBasicRulesService.deleteKqBasicRulesByCondition(kqBasicRules);
    }
    @PostMapping("/findOneKqBasicRulesByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理基础规则表,结果必须为单条数据", notes = "返回单个考勤管理基础规则表,没有时为空")
    public KqBasicRules findOneKqBasicRulesByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody KqBasicRules kqBasicRules){
        return kqBasicRulesService.findOneKqBasicRulesByCondition(kqBasicRules);
    }
}
