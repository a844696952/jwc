package com.yice.edu.cn.jy.controller.collectivePlan;

import com.yice.edu.cn.common.dto.jy.DiscussAndReply;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.jy.service.collectivePlan.JyPrepareLessonsDiscussService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jyPrepareLessonsDiscuss")
@Api(value = "/jyPrepareLessonsDiscuss", description = "模块")
public class JyPrepareLessonsDiscussController {
    @Autowired
    private JyPrepareLessonsDiscussService jyPrepareLessonsDiscussService;

    @GetMapping("/findJyPrepareLessonsDiscussById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public JyPrepareLessonsDiscuss findJyPrepareLessonsDiscussById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussById(id);
    }

    @PostMapping("/saveJyPrepareLessonsDiscuss")
    @ApiOperation(value = "保存", notes = "返回对象")
    public JyPrepareLessonsDiscuss saveJyPrepareLessonsDiscuss(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussService.saveJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
        return jyPrepareLessonsDiscuss;
    }

    @PostMapping("/findJyPrepareLessonsDiscussListByConditionDto")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DiscussAndReply> findJyPrepareLessonsDiscussListByConditionDto(
            @ApiParam(value = "对象")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussListByConditionDto(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/findJyPrepareLessonsDiscussListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<JyPrepareLessonsDiscuss> findJyPrepareLessonsDiscussListByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussListByCondition(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/findJyPrepareLessonsDiscussCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findJyPrepareLessonsDiscussCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussService.findJyPrepareLessonsDiscussCountByCondition(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/updateJyPrepareLessonsDiscuss")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateJyPrepareLessonsDiscuss(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussService.updateJyPrepareLessonsDiscuss(jyPrepareLessonsDiscuss);
    }

    @GetMapping("/deleteJyPrepareLessonsDiscuss/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteJyPrepareLessonsDiscuss(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        jyPrepareLessonsDiscussService.deleteJyPrepareLessonsDiscuss(id);
    }

    @PostMapping("/deleteJyPrepareLessonsDiscussByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteJyPrepareLessonsDiscussByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        jyPrepareLessonsDiscussService.deleteJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }

    @PostMapping("/findOneJyPrepareLessonsDiscussByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public JyPrepareLessonsDiscuss findOneJyPrepareLessonsDiscussByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        return jyPrepareLessonsDiscussService.findOneJyPrepareLessonsDiscussByCondition(jyPrepareLessonsDiscuss);
    }
}
