package com.yice.edu.cn.jy.controller.homeworkAnalyse;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.jy.service.homeworkAnalyse.HomeworkAnalyseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homeworkAnalyse")
public class homeworkAnalyseController {
    @Autowired
    private HomeworkAnalyseService homeworkAnalyseService;

    @GetMapping("/findHomeworkAnalyseById/{id}")
    @ApiOperation(value = "通过id查找布置作业", notes = "返回布置作业对象")
    public Homework findHomeworkAnalyseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        Homework homework = homeworkAnalyseService.findHomeworkAnalyseById(id);
        return homework;
    }


    @PostMapping("/findHomeworkAnalyseListByConditionNew")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<Homework> findHomeworkAnalyseListByConditionNew(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework) {
        return homeworkAnalyseService.findHomeworkAnalyseListByConditionNew(homework);
    }

    @PostMapping("/findHomeworkAnalyseCountByConditionNew")
    @ApiOperation(value = "根据条件查找布置作业列表个数", notes = "返回布置作业列表")
    public long findHomeworkAnalyseCountByConditionNew(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework) {
        return homeworkAnalyseService.findHomeworkAnalyseCountByConditionNew(homework);
    }

    @PostMapping("/findHomeworkStudentListAnalyseByCondition")
    @ApiOperation(value = "根据条件查询学生作业列表", notes = "返回学生作业列表")
    public List<HomeworkStudent> findHomeworkStudentListAnalyseByCondition(
            @ApiParam(value = "学生作业对象")
            @RequestBody HomeworkStudent homeworkStudent) {
        return homeworkAnalyseService.findHomeworkStudentListAnalyseByCondition(homeworkStudent);
    }

    @PostMapping("/findTopicsRecordListAnalyseByCondition")
    @ApiOperation(value = "根据条件查询学生作业题目列表", notes = "返回学生作业题目列表")
    public List<TopicsRecord> findTopicsRecordListAnalyseByCondition(
            @ApiParam(value = "学生作业题目记录对象")
            @RequestBody TopicsRecord topicsRecord) {
        return homeworkAnalyseService.findTopicsRecordListAnalyseByCondition(topicsRecord);

    }
}