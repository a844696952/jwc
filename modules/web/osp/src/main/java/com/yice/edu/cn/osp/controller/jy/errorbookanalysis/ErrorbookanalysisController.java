package com.yice.edu.cn.osp.controller.jy.errorbookanalysis;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/errorbookanalysis")
@Api(value = "/errorbookanalysis", description = "布置作业模块")
public class ErrorbookanalysisController {

    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private HomeworkService homeworkService;

    @PostMapping("/ignore/findTeachClassAndSubject")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息(无须传参)")
    public ResponseJson findTeacherClassPostCourseList(
            @ApiParam(value = "教师在班级信息")
            @RequestBody TeacherClasses teacherClasses) {
        teacherClasses.setSchoolId(mySchoolId());
        teacherClasses.setTeacherId(myId());
        List<Map<String,String>> t = teacherClassesService.findTeacherClassPostCourseListXq(teacherClasses);
        return new ResponseJson(t);
    }

    @PostMapping("/ignore/findKnowlegRateAllByCondition")
    @ApiOperation(value = "查找薄弱知识点前6位(传入参数String subjectId,String classesId)", notes = "返回响应对象")
    public ResponseJson findKnowlegRateAllByCondition(
            @RequestBody Homework homework) {
        homework.setTeacherId(myId());
        homework.setSchoolId(mySchoolId());
        List<Map<String, Object>> data = homeworkService.findHomeworkListByConditionXq(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findKnowlegAllMoreByCondition")
    @ApiOperation(value = "查找全部知识点错误率情况(String classesId,String subjectId)分页传对应的page对象", notes = "返回响应对象")
    public ResponseJson findKnowlegAllMoreByCondition(
            @RequestBody Homework homework) {
        homework.setTeacherId(myId());
        homework.setSchoolId(mySchoolId());
        Map<String, Object> data = homeworkService.findHomeworksByConditionDetail(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findKnowlegAllMoreDetailByCondition")
    @ApiOperation(value = "点击当前知识点显示相关的题目(String classesId,String subjectId,knowledgeId)", notes = "返回响应对象")
    public ResponseJson findKnowlegAllMoreDetailByCondition(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<HomeworkNew> data = homeworkService.findKnowlegAllMoreDetailByCondition(homework);
        Integer count = homeworkService.findKnowlegAllMoreDetailByConditionCount(homework);
        return new ResponseJson(data,count.longValue());
    }
    /**
     * {
     *   "classesId" : "1807003276775612416",
     *   "subjectId" : "1818889684666638336",
     *   "knowledgeId": "1839733882509541376",
     *   "topicSqId" : "1859862401361068032",;
     *   "sqId" : "1937341137479745536"
     * }
     * @param homework
     * @return
     */
    @PostMapping("/ignore/findCurrentTopicDetail")
    @ApiOperation(value = "点击当前题目获取这道题的正确率和班级错题名单(String classesId,String subjectId,String knowledgeId,String topicSqId,String sqId//homework主键)", notes = "返回响应对象")
    public ResponseJson findCurrentTopicDetail(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        HomeworkNew one = homeworkService.findCurrentTopicDetail(homework);
        return new ResponseJson(one);
    }

    @PostMapping("/ignore/findOneError")
    @ApiOperation(value = "查找班级每个人的错题情况(String classesId,String subjectId)", notes = "返回响应对象")
    public ResponseJson findOneError(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<Map<String, Object>> data = homeworkService.findOneError(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findOneStudentDetail")
    @ApiOperation(value = "查找当前点击学生薄弱知识点概率情况(String classesId,String subjectId,String studentId)", notes = "返回响应对象")
    public ResponseJson findOneStudentDetail(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<Map<String, Object>> data = homeworkService.findOneStudentDetail(homework);
        return new ResponseJson(data);
    }

    //(学生薄弱知识点部分)点击学生知识点对应的相关题数和班级错误率
    @PostMapping("/ignore/findWrongtopicInfo")
    @ApiOperation(value = "查找全部知识点错误率情况(String classesId,String subjectId,String knowledgeId,String studentId)", notes = "返回响应对象")
    public ResponseJson findWrongtopicInfo(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        Map<String, Object> data = homeworkService.findWrongtopicInfo(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findOneStudentKnoledgeContext")
    @ApiOperation(value = "查找当前点击学生个人知识点对应的题目(String classesId,String subjectId,knowledgeId,String studentId)", notes = "返回响应对象")
    public ResponseJson findOneStudentKnoledgeContext(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<Map<String, Object>> data = homeworkService.findOneStudentKnoledgeContext(homework);
        Integer count =  homeworkService.findOneStudentKnoledgeContextCount(homework);
        return new ResponseJson(data,count.longValue());
    }

    //错题集
    @PostMapping("/ignore/mistakesCollection")
    @ApiOperation(value = "查找莫个学生的错题集(String classesId,String subjectId,String studentId)", notes = "返回响应对象")
    public ResponseJson mistakesCollection(
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        List<WrongTopics> data = homeworkService.mistakesCollection(homework);
        Integer count =  homeworkService.mistakesCollectionCount(homework);
        return new ResponseJson(data,count.longValue());
    }
}