package com.yice.edu.cn.jw.controller.qusBankResource;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.jw.service.qusBankResource.PersonalTopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personalTopics")
@Api(value = "/personalTopics",description = "模块")
public class PersonalTopicsController {
    @Autowired
    private PersonalTopicsService personalTopicsService;

    @GetMapping("/find/findPersonalTopicsById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PersonalTopics findPersonalTopicsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return personalTopicsService.findPersonalTopicsById(id);
    }

    @PostMapping("/savePersonalTopics")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PersonalTopics savePersonalTopics(
            @ApiParam(value = "对象", required = true)
            @RequestBody PersonalTopics personalTopics){
        personalTopicsService.savePersonalTopics(personalTopics);
        return personalTopics;
    }

    @PostMapping("/find/findPersonalTopicsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PersonalTopics> findPersonalTopicsListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PersonalTopics personalTopics){
        return personalTopicsService.findPersonalTopicsListByCondition(personalTopics);
    }
    @PostMapping("/find/findPersonalTopicsCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPersonalTopicsCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PersonalTopics personalTopics){
        return personalTopicsService.findPersonalTopicsCountByCondition(personalTopics);
    }

    @PostMapping("/updatePersonalTopics")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePersonalTopics(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PersonalTopics personalTopics){
        personalTopicsService.updatePersonalTopics(personalTopics);
    }

    @GetMapping("/deletePersonalTopics/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePersonalTopics(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        personalTopicsService.deletePersonalTopics(id);
    }
    @PostMapping("/deletePersonalTopicsByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePersonalTopicsByCondition(
            @ApiParam(value = "对象")
            @RequestBody PersonalTopics personalTopics){
        personalTopicsService.deletePersonalTopicsByCondition(personalTopics);
    }
    @PostMapping("/findOnePersonalTopicsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PersonalTopics findOnePersonalTopicsByCondition(
            @ApiParam(value = "对象")
            @RequestBody PersonalTopics personalTopics){
        return personalTopicsService.findOnePersonalTopicsByCondition(personalTopics);
    }
    @PostMapping("/copyTopicToPersonalTopics")
    @ApiOperation(value = "将平台题库的题目添加到校本题库")
    public void copyTopicToPersonalTopics(
            @ApiParam(value = "对象")
            @RequestBody PersonalTopics personalTopics){
        personalTopicsService.copyTopicToPersonalTopics(personalTopics);
    }
}
