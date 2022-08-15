package com.yice.edu.cn.tap.controller.homework;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.practice.FileObject;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.app.TopicsKnowledgeVo;
import com.yice.edu.cn.common.pojo.jy.knowledge.JyKnowledge;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.service.homework.HomeworkService;
import com.yice.edu.cn.tap.service.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/homework")
@Api(value = "/homework",description = "教师作业模块")
public class HomeworkController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @PostMapping("/publishHomework")
    @ApiOperation(value = "布置作业", notes = "返回成功标识")
    public ResponseJson publishHomework(
            @ApiParam(value = "布置作业对象\n" +
                    "{\n" +
                    "\"classesList\": [//班级列表数据\n" +
                    " {\n" +
                    "  \"id\": \"classesId\",\n" +
                    " \"enrollYear\": \"enrollYear\",\n" +
                    "  \"number\":\"classesName\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"endTime\": \"string\",\n" +
                    "\"gradeId\": \"string\",\n" +
                    "\"gradeName\": \"string\",\n" +
                    "\"homeworkContent\": \"string\"//线下作业图片使用 线上作业可以不传,\n" +
                    "\"homeworkName\": \"string\",\n" +
                    "\"imageArr\": [\n" +
                    "\"string\"\n" +
                    " ],//线下作业图片使用 线上作业可以不传\n" +
                    "\"publishStatus\": \"integer\",\n" +
                    "\"replyWay\": \"integer\",//线下作业图片使用 线上作业可以不传\n" +
                    "\"subjectId\": \"string\",\n" +
                    "\"subjectName\": \"string\",\n" +
                    "\"topicArr\": [作业对象列表],//线上作业图片使用 线下作业可以不传\n" +
                    "\"type\": \"integer\",//标识线上[1]还是线下[2]作业\n" +
                    "}", required = true)
            @RequestBody Homework homework) {
        homework.setSchoolId(mySchoolId());
        homework.setTeacherId(myId());
        homework.setTeacherName(currentTeacher().getName());
        homework.setTeacherImg(currentTeacher().getImgUrl());
        curSchoolYearService.setSchoolYearTerm(homework,mySchoolId());
        homeworkService.publishHomework(homework);
        return new ResponseJson();
    }
    @PostMapping("/updateHomework")
    @ApiOperation(value = "修改布置作业对象", notes = "返回响应对象")
    public ResponseJson updateHomework(
            @ApiParam(value = "被修改的布置作业对象,对象属性-其中 年级科目班级（不是班级列表，是具体班级信息 classesId、classesName、enrollYear）不用修改", required = true)
            @RequestBody Homework homework) {
        homeworkService.updateHomework(homework);
        return new ResponseJson();
    }
    @GetMapping("/deleteHomework/{id}")
    @ApiOperation(value = "根据作业id删除", notes = "返回响应对象")
    public ResponseJson deleteHomework(
            @ApiParam(value = "被删除记录的作业id", required = true)
            @PathVariable String id){
        homeworkService.deleteHomework(id);
        return new ResponseJson();
    }
    @GetMapping("/findHomeworkById/{id}")
    @ApiOperation(value = "通过作业id查找布置作业", notes = "返回响应对象-Homework")
    public ResponseJson findHomeworkById(
            @ApiParam(value = "需要用到的作业id", required = true)
            @PathVariable String id){
        Homework homework=homeworkService.findHomeworkById(id);
        return new ResponseJson(homework);
    }

    @PostMapping("/findHomeworkListByCondition")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回查询作业列表-Homework")
    public ResponseJson findHomeworkListByCondition(
            @ApiParam(value = "{\n" +
                    "    \"page\": 0,\n" +
                    "    \"pageSize\": 0,\n" +
                    "    \"sortField\": \"publishTime\",\n" +
                    "    \"sortOrder\": \"desc\"\n" +
                    "}")
            @Validated
            @RequestBody Pager pager){
        Homework homework = new Homework();
        homework.setPublishStatus(Constant.HOMEWORK.PUBLISH_ON);
        homework.setPager(pager);
        homework.setTeacherId(myId());
        curSchoolYearService.setSchoolYearId(homework,mySchoolId());
        List<Homework> data=homeworkService.findHomeworkListByCondition(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/findHomeworkList4Not")
    @ApiOperation(value = "查找教师未发布的作业列表", notes = "返回查询作业列表-Homework")
    public ResponseJson findHomeworkList4Not(
            @ApiParam(value = "{\n" +
                    "    \"page\": 0,\n" +
                    "    \"pageSize\": 0,\n" +
                    "    \"sortField\": \"createTime\",\n" +
                    "    \"sortOrder\": \"desc\"\n" +
                    "}")
            @Validated
            @RequestBody Pager pager){
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        /*homework.setPager(new Pager().setSortField("createTime").setSortOrder(Pager.DESC).setPaging(false));*/
        homework.setPager(pager);
        homework.setPublishStatus(Constant.HOMEWORK.PUBLISH_NOT);
        curSchoolYearService.setSchoolYearId(homework,mySchoolId());
        List<Homework> data=homeworkService.findHomeworkListByCondition(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/findTopicsListByKnowledge")
    @ApiOperation(value = "根据知识点查找题目列表（知识点传递 id和名称，支持分页，排序）", notes = "返回响应对象,包含总条数-Topics")
    public ResponseJson findTopicsListByKnowledge(
            @ApiParam(value = "{\n" +
                    "  \"knowledge\": {//知识点\n" +
                    "    \"id\": \"string\",\n" +
                    "    \"name\": \"string\"\n" +
                    "  },\n" +
                    "  \"pager\": {\n" +
                    "    \"page\": 0,\n" +
                    "    \"pageSize\": 0,\n" +
                    "    \"sortField\": \"string\",\n" +
                    "    \"sortOrder\": \"string\"\n" +
                    "  },\n" +
                    "  \"typeId\": \"integer\"//作业类型\n" +
                    "}")
            @Validated
            @RequestBody TopicsKnowledgeVo topicsKnowledgeVo) {
        Topics topics = new Topics();
        if (topicsKnowledgeVo.getTypeId()!=null&&topicsKnowledgeVo.getTypeId()>0)
            topics.setTypeIds(new Integer[]{topicsKnowledgeVo.getTypeId()});
        else
            topics.setTypeIds(new Integer[]{1,2,3});
        List<KnowledgePoint> knowledgePoints = new ArrayList<>();
        knowledgePoints.add(topicsKnowledgeVo.getKnowledge());
        topics.setKnowledges(knowledgePoints);
        topics.setPager(topicsKnowledgeVo.getPager());
        List<Topics> data = topicsService.findTopicsListByCondition4Muti(topics);
        Long count = topicsService.findTopicsCountByCondition4Muti(topics);
        return new ResponseJson(data,count);
    }

    @GetMapping("/findGradeByTeacher")
    @ApiOperation(value = "查询当前教师所任职的年级", notes = "返回年级列表")
    public ResponseJson findGradeByTeacher() {
        return new ResponseJson(homeworkService.findGradeByTeacher(myId()));
    }


    @GetMapping("/findCourseByTeacherGrade/{gradeId}") //线上作业查询课程
    @ApiOperation(value = "查询当前教师在年级中教的科目", notes = "返回科目列表")
    public ResponseJson findCourseByTeacherGrade(@ApiParam(value = "教师年级", required = true) @PathVariable String gradeId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setGradeId(gradeId);
        return new ResponseJson(homeworkService.findCourseByTeacherGrade(teacherClasses));
    }

    @GetMapping("/findCourseByTeacherGrade2/{gradeId}") //线下作业查询课程
    @ApiOperation(value = "查询当前教师在年级中教的科目", notes = "返回科目列表")
    public ResponseJson findCourseByTeacherGrade2(@ApiParam(value = "教师年级", required = true)
                                                 @PathVariable String gradeId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setGradeId(gradeId);
        return new ResponseJson(homeworkService.findCourseByTeacherGrade2(teacherClasses));
    }

    @GetMapping("/findCourseByTeacherGrade3") //线上作业查询科目
    @ApiOperation(value = "查询当前教师在年级中教的科目", notes = "返回科目列表")
    public ResponseJson findCourseByTeacherGrade3() {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        return new ResponseJson(homeworkService.findCourseByTeacherGrade3(teacherClasses));
    }

    @PostMapping("/findClassesByTeacherCourse")
    @ApiOperation(value = "通过年级和课程id获取教师对应所教的班级", notes = "返回班级列表")
    public ResponseJson findClassesByTeacherCourse(@ApiParam(value = "课程id和年级id", required = true)
                                                       @Validated
                                                   @RequestBody TeacherClassVo teacherClassVo) {
        teacherClassVo.setTeacherId(myId());
        return new ResponseJson(homeworkService.findClassesByTeacherCourse(teacherClassVo));
    }
    @GetMapping("/findHomeworks4Index")
    @ApiOperation(value = "首页查询教师作业（显示截止日期为昨日和今日的作业，按照截止时间倒序进行排列）", notes = "返回班级列表-Homework")
    public ResponseJson findHomeworks4Index(){
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        return new ResponseJson(homeworkService.findHomeworks4Index(homework));
    }

    @PostMapping("/ignore/tapUploadHomework")
    public ResponseJson tapUploadHomework(MultipartFile file){
        FileObject fileObj = new FileObject();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_TAPHOMEWORK));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return new ResponseJson(fileObj);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(topicsService.findTopicDetail(resourceVo));
    }
    @PostMapping("/findTopicList")
    @ApiOperation(value = "查询题目列表-21世纪", notes = "返回响应对象,含总条数",response = Question.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        if(StringUtils.isEmpty(searchParam.getBaseType()))
            searchParam.setBaseType("1,2,3");
        return topicsService.findTopicList(searchParam);
    }
    @GetMapping("/findTopicUseCount/{topicId}")
    @ApiOperation(value = "获取近几个月题目使用次数", notes = "返回题目使用次数",response = Long.class)
    public ResponseJson findTopicUseCount(@PathVariable String topicId){
        return new ResponseJson(topicsService.findTopicUseCount(topicId));
    }
}
