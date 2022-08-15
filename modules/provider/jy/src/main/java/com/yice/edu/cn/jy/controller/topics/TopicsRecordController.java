package com.yice.edu.cn.jy.controller.topics;

import com.yice.edu.cn.common.pojo.jy.homework.StuHomeRecordVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.jy.service.topics.TopicsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicsRecord")
@Api(value = "/topicsRecord",description = "学生答题记录表模块")
public class TopicsRecordController {
    @Autowired
    private TopicsRecordService topicsRecordService;

    @GetMapping("/findTopicsRecordById/{id}")
    @ApiOperation(value = "通过id查找学生答题记录表", notes = "返回学生答题记录表对象")
    public TopicsRecord findTopicsRecordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return topicsRecordService.findTopicsRecordById(id);
    }

    @PostMapping("/saveTopicsRecord")
    @ApiOperation(value = "保存学生答题记录表", notes = "返回学生答题记录表对象")
    public TopicsRecord saveTopicsRecord(
            @ApiParam(value = "学生答题记录表对象", required = true)
            @RequestBody TopicsRecord topicsRecord){
        topicsRecordService.saveTopicsRecord(topicsRecord);
        return topicsRecord;
    }

    @PostMapping("/findTopicsRecordListByCondition")
    @ApiOperation(value = "根据条件查找学生答题记录表列表", notes = "返回学生答题记录表列表")
    public List<TopicsRecord> findTopicsRecordListByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicsRecord topicsRecord){
        return topicsRecordService.findTopicsRecordListByCondition(topicsRecord);
    }
    @PostMapping("/findTopicsRecordCountByCondition")
    @ApiOperation(value = "根据条件查找学生答题记录表列表个数", notes = "返回学生答题记录表总个数")
    public long findTopicsRecordCountByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicsRecord topicsRecord){
        return topicsRecordService.findTopicsRecordCountByCondition(topicsRecord);
    }

    @PostMapping("/updateTopicsRecord")
    @ApiOperation(value = "修改学生答题记录表", notes = "学生答题记录表对象必传")
    public void updateTopicsRecord(
            @ApiParam(value = "学生答题记录表对象,对象属性不为空则修改", required = true)
            @RequestBody TopicsRecord topicsRecord){
        topicsRecordService.updateTopicsRecord(topicsRecord);
    }

    @GetMapping("/deleteTopicsRecord/{id}")
    @ApiOperation(value = "通过id删除学生答题记录表")
    public void deleteTopicsRecord(
            @ApiParam(value = "学生答题记录表对象", required = true)
            @PathVariable String id){
        topicsRecordService.deleteTopicsRecord(id);
    }
    @PostMapping("/deleteTopicsRecordByCondition")
    @ApiOperation(value = "根据条件删除学生答题记录表")
    public void deleteTopicsRecordByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicsRecord topicsRecord){
        topicsRecordService.deleteTopicsRecordByCondition(topicsRecord);
    }
    @PostMapping("/findOneTopicsRecordByCondition")
    @ApiOperation(value = "根据条件查找单个学生答题记录表,结果必须为单条数据", notes = "返回单个学生答题记录表,没有时为空")
    public TopicsRecord findOneTopicsRecordByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicsRecord topicsRecord){
        return topicsRecordService.findOneTopicsRecordByCondition(topicsRecord);
    }
    
    @GetMapping("/queryHomeworkCorrectRateByHomeworkId/{homeworkSqId}")
    @ApiOperation(value = "根据线上作业sqId查询每个学生的线上作业正确率", notes = "")
    public List<StuHomeRecordVo> queryHomeworkCorrectRateByHomeworkId(
            @ApiParam(value = "学生线上作业正确数")
            @PathVariable String homeworkSqId){
        return topicsRecordService.queryHomeworkCorrectRateByHomeworkId(homeworkSqId);
    }

    @PostMapping("/findTopicsRecordBy4Like")
    @ApiOperation(value = "", notes = "返回学生答题记录表列表")
    public List<TopicsRecord> findOneTopicsRecordListByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicsRecord topicsRecord){
        return topicsRecordService.findTopicsRecordBy4Like(topicsRecord);
    }
}
