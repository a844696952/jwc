package com.yice.edu.cn.jy.controller.topics;

import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.jy.service.topics.WrongTopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wrongTopics")
@Api(value = "/wrongTopics",description = "学生错题记录表模块")
public class WrongTopicsController {
    @Autowired
    private WrongTopicsService wrongTopicsService;

    @GetMapping("/findWrongTopicsById/{id}")
    @ApiOperation(value = "通过id查找学生错题记录表", notes = "返回学生错题记录表对象")
    public WrongTopics findWrongTopicsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wrongTopicsService.findWrongTopicsById(id);
    }

    @PostMapping("/saveWrongTopics")
    @ApiOperation(value = "保存学生错题记录表", notes = "返回学生错题记录表对象")
    public WrongTopics saveWrongTopics(
            @ApiParam(value = "学生错题记录表对象", required = true)
            @RequestBody WrongTopics wrongTopics){
        wrongTopicsService.saveWrongTopics(wrongTopics);
        return wrongTopics;
    }

    @PostMapping("/findWrongTopicsListByCondition")
    @ApiOperation(value = "根据条件查找学生错题记录表列表", notes = "返回学生错题记录表列表")
    public List<WrongTopics> findWrongTopicsListByCondition(
            @ApiParam(value = "学生错题记录表对象")
            @RequestBody WrongTopics wrongTopics){
        return wrongTopicsService.findWrongTopicsListByCondition(wrongTopics);
    }
    @PostMapping("/findWrongTopicsCountByCondition")
    @ApiOperation(value = "根据条件查找学生错题记录表列表个数", notes = "返回学生错题记录表总个数")
    public long findWrongTopicsCountByCondition(
            @ApiParam(value = "学生错题记录表对象")
            @RequestBody WrongTopics wrongTopics){
        return wrongTopicsService.findWrongTopicsCountByCondition(wrongTopics);
    }

    @PostMapping("/updateWrongTopics")
    @ApiOperation(value = "修改学生错题记录表", notes = "学生错题记录表对象必传")
    public void updateWrongTopics(
            @ApiParam(value = "学生错题记录表对象,对象属性不为空则修改", required = true)
            @RequestBody WrongTopics wrongTopics){
        wrongTopicsService.updateWrongTopics(wrongTopics);
    }

    @GetMapping("/deleteWrongTopics/{id}")
    @ApiOperation(value = "通过id删除学生错题记录表")
    public void deleteWrongTopics(
            @ApiParam(value = "学生错题记录表对象", required = true)
            @PathVariable String id){
        wrongTopicsService.deleteWrongTopics(id);
    }
    @PostMapping("/deleteWrongTopicsByCondition")
    @ApiOperation(value = "根据条件删除学生错题记录表")
    public void deleteWrongTopicsByCondition(
            @ApiParam(value = "学生错题记录表对象")
            @RequestBody WrongTopics wrongTopics){
        wrongTopicsService.deleteWrongTopicsByCondition(wrongTopics);
    }
    @PostMapping("/findOneWrongTopicsByCondition")
    @ApiOperation(value = "根据条件查找单个学生错题记录表,结果必须为单条数据", notes = "返回单个学生错题记录表,没有时为空")
    public WrongTopics findOneWrongTopicsByCondition(
            @ApiParam(value = "学生错题记录表对象")
            @RequestBody WrongTopics wrongTopics){
        return wrongTopicsService.findOneWrongTopicsByCondition(wrongTopics);
    }
}
