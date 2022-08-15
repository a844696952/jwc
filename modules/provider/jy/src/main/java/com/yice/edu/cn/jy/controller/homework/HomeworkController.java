package com.yice.edu.cn.jy.controller.homework;

import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.jy.service.homework.HomeworkService;
import com.yice.edu.cn.jy.service.homework.HomeworkStudentService;
import com.yice.edu.cn.jy.service.topics.TopicsRecordService;
import com.yice.edu.cn.jy.service.topics.WrongTopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homework")
@Api(value = "/homework",description = "布置作业模块")
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;

    @GetMapping("/findHomeworkById/{id}")
    @ApiOperation(value = "通过id查找布置作业", notes = "返回布置作业对象")
    public Homework findHomeworkById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return homeworkService.findHomeworkById(id);
    }

    @PostMapping("/saveHomework")
    @ApiOperation(value = "保存布置作业", notes = "返回布置作业对象")
    public Homework saveHomework(
            @ApiParam(value = "布置作业对象", required = true)
            @RequestBody Homework homework){
        homeworkService.saveHomework(homework);
        return homework;
    }
    @PostMapping("/publishHomework")
    @ApiOperation(value = "布置作业", notes = "返回布置作业对象")
    public Homework publishHomework(
            @ApiParam(value = "布置作业对象", required = true)
            @RequestBody Homework homework){
        homeworkService.publishHomework(homework);
        return homework;
    }

    @PostMapping("/findHomeworkListByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<Homework> findHomeworkListByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworkListByCondition(homework);
    }
    @PostMapping("/findHomeworkListByConditionNew")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<Homework> findHomeworkListByConditionNew(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworkListByConditionNew(homework);
    }
    @PostMapping("/findHomeworkCountByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表个数", notes = "返回布置作业总个数")
    public long findHomeworkCountByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworkCountByCondition(homework);
    }
    @PostMapping("/findHomeworkCountByConditionNew")
    @ApiOperation(value = "根据条件查找布置作业列表个数", notes = "返回布置作业总个数")
    public long findHomeworkCountByConditionNew(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworkCountByConditionNew(homework);
    }

    @PostMapping("/updateHomework")
    @ApiOperation(value = "修改布置作业", notes = "布置作业对象必传")
    public void updateHomework(
            @ApiParam(value = "布置作业对象,对象属性不为空则修改", required = true)
            @RequestBody Homework homework){
        homeworkService.updateHomework(homework);
    }
    @PostMapping("/updateHomeworkNew")
    @ApiOperation(value = "修改布置作业", notes = "布置作业对象必传")
    public void updateHomeworkNew(@RequestBody Homework homework){
        homeworkService.updateHomeworkNew(homework);
    }

    @GetMapping("/deleteHomework/{id}")
    @ApiOperation(value = "通过id删除布置作业")
    public void deleteHomework(
            @ApiParam(value = "布置作业对象", required = true)
            @PathVariable String id){
        homeworkService.deleteHomework(id);
    }
    @GetMapping("/deleteHomeworkNew/{id}")
    @ApiOperation(value = "通过id删除布置作业")
    public void deleteHomeworkNew(
            @ApiParam(value = "布置作业对象", required = true)
            @PathVariable String id){
        homeworkService.deleteHomeworkNew(id);
    }
    @PostMapping("/deleteHomeworkByCondition")
    @ApiOperation(value = "根据条件删除布置作业")
    public void deleteHomeworkByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        homeworkService.deleteHomeworkByCondition(homework);
    }
    @PostMapping("/findOneHomeworkByCondition")
    @ApiOperation(value = "根据条件查找单个布置作业,结果必须为单条数据", notes = "返回单个布置作业,没有时为空")
    public Homework findOneHomeworkByCondition(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findOneHomeworkByCondition(homework);
    }

    @PostMapping("/findHomeworks4Index")
    @ApiOperation(value = "首页查询教师作业（显示截止日期为昨日和今日的作业，按照截止时间倒序进行排列）", notes = "返回班级列表")
    public List<Homework> findHomeworks4Index(@RequestBody Homework homework){
        return homeworkService.findHomeworks4Index(homework);
    }

    @GetMapping("/scheduleEndTime/{interval}")
    @ApiOperation(value = "教师作业定时器-定时判断截止日期")
    public void scheduleEndTime(
            @ApiParam(value = "定时间隔时间",required = true)
            @PathVariable("interval") int interval){
        homeworkService.scheduleEndTime(interval);
    }

    @PostMapping("/findHomeworkListByConditionXq")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<Map<String, Object>> findHomeworkListByConditionXq(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        //List<Map<String,Object>> map = homeworkService.findHomeworkListByConditionXq(homework);
        return homeworkService.findHomeworkListByConditionXq(homework);
    }

    @PostMapping("/findHomeworksByConditionDetail")
    @ApiOperation(value = "根据条件", notes = "返回班级学生的所有响应对象")
    public  Map<String, Object> findHomeworksByConditionDetail(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworksByConditionDetail(homework);
    }

    @PostMapping("/findKnowlegAllMoreDetailByCondition")
    @ApiOperation(value = "", notes = "返回班级列表")
    public List<HomeworkNew> findKnowlegAllMoreDetailByCondition(@RequestBody Homework homework){
        return homeworkService.findKnowlegAllMoreDetailByCondition(homework);
    }

    @PostMapping("/findKnowlegAllMoreDetailByConditionCount")
    @ApiOperation(value = "", notes = "返回班级列表")
    public Integer findKnowlegAllMoreDetailByConditionCount(@RequestBody Homework homework){
        return homeworkService. findKnowlegAllMoreDetailByConditionCount(homework);
    }

    @PostMapping("/findOneStudentKnoledgeContext")
    @ApiOperation(value = "", notes = "返回班级列表")
    public List<Map<String, Object>> findOneStudentKnoledgeContext(@RequestBody Homework homework){
        return homeworkService.findOneStudentKnoledgeContext(homework);
    }

    @PostMapping("/findOneStudentKnoledgeContextCount")
    @ApiOperation(value = "", notes = "返回班级列表")
    public Integer findOneStudentKnoledgeContextCount(@RequestBody Homework homework){
        return homeworkService.findOneStudentKnoledgeContextCount(homework);
    }

    @PostMapping("/findWrongtopicInfo")
    @ApiOperation(value = "薄弱知识点信息", notes = "返回班级列表")
    public Map<String, Object> findWrongtopicInfo(@RequestBody Homework homework){
        return homeworkService.findWrongtopicInfo(homework);
    }

    @PostMapping("/mistakesCollectionDetail")
    @ApiOperation(value = "", notes = "返回班级列表")
    public HomeworkNew mistakesCollectionDetail(@RequestBody Homework homework){
        return homeworkService.mistakesCollectionDetail(homework);
    }


    @PostMapping("/findCurrentTopicDetail")
    @ApiOperation(value = "", notes = "返回班级列表")
    public HomeworkNew findCurrentTopicDetail(@RequestBody Homework homework){
        return homeworkService.findCurrentTopicDetail(homework);
    }

    @PostMapping("/findOneError")
    @ApiOperation(value = "根据条件", notes = "返回班级学生的所有响应对象")
    public  List<Map<String, Object>> findOneError(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findOneError(homework);
    }

    @PostMapping("/findOneStudentDetail")
    @ApiOperation(value = "查找班级每个人的错题情况(String classesId,String subjectId,String studentId)", notes = "返回响应对象")
    public List<Map<String, Object>> findOneStudentDetail(
            @RequestBody Homework homework) {
        return homeworkService.findOneStudentDetail(homework);
    }

    //错题集(数量)
    @PostMapping("/mistakesCollectionCount")
    @ApiOperation(value = "查找莫个学生的错题集(String classesId,String subjectId,String studentId)", notes = "返回响应对象")
    public Integer mistakesCollectionCount(
            @RequestBody Homework homework) {
        return homeworkService.mistakesCollectionCount(homework);
    }


    //错题集
    @PostMapping("/mistakesCollection")
    @ApiOperation(value = "查找莫个学生的错题集(String classesId,String subjectId,String studentId)", notes = "返回响应对象")
    public  List<WrongTopics> mistakesCollection(
            @RequestBody Homework homework) {
        return homeworkService.mistakesCollection(homework);
    }
    @PostMapping("/findHomeworkListByConditionEwb")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回布置作业列表")
    public List<Homework> findHomeworkListByConditionEwb(
            @ApiParam(value = "布置作业对象")
            @RequestBody Homework homework){
        return homeworkService.findHomeworkListByConditionEwb(homework);
    }
    @PostMapping("/deleteHomeworkByClasses")
    @ApiOperation(value = "根据条件删除作业列表", notes = "删除作业列表")
    public void deleteHomeworkByClasses( @RequestBody List<String> classIdList){
        homeworkService.deleteHomeworkByClasses(classIdList);
    }
}
