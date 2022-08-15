package com.yice.edu.cn.bmp.controller.homework;

import com.yice.edu.cn.bmp.service.homework.HomeworkService;
import com.yice.edu.cn.bmp.service.homework.HomeworkStudentService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.bmp.service.topics.TopicsRecordService;
import com.yice.edu.cn.bmp.service.topics.WrongTopicsService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.practice.FileObject;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.StudentFindHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkStatusVo;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.jy.topics.app.WrongTopicsVo;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@RestController
@RequestMapping("/homeworkStudent")
@Api(value = "/homeworkStudent",description = "学生作业状态表模块")
public class HomeworkStudentController {
    @Autowired
    private HomeworkStudentService homeworkStudentService;
    @Autowired
    private TopicsRecordService topicsRecordService;
    @Autowired
    private WrongTopicsService wrongTopicsService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @GetMapping("/getSubject4WrongTwo")
    @ApiOperation(value = "查找当前学生错题本科目", notes = "返回学生科目列表")
    public ResponseJson getSubject4WrongTwo(){
        return new ResponseJson(homeworkStudentService.getSubject4Wrong());
    }

    @GetMapping("/getSubject4Wrong")
    @ApiOperation(value = "查找当前学生错题本科目", notes = "返回学生科目列表")
    public ResponseJson getSubject4Wrong(){
        return new ResponseJson(homeworkStudentService.getSubject4Wrong());
    }

    //错题集
    @PostMapping("/mistakesCollection")
    @ApiOperation(value = "查找莫个学生的错题集(String subjectId)或者subjectId和id值，则查询的是具体莫道题", notes = "返回响应对象")
    public ResponseJson mistakesCollection(
            @RequestBody Homework homework) {
        String studentId = myStudentId();
        Student student =  studentService.findStudentById(studentId);
        homework.setClassesId(student.getClassesId());
        homework.setStudentId(studentId);
        homework.setSchoolId(student.getSchoolId());
        homework.setTypeId("23");//2和3：单选和多选
        List<WrongTopics> data = homeworkService.mistakesCollection(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/getWrongTopicsListBySubject")
    @ApiOperation(value = "学生根据科目、序号获取对应错题")
    public ResponseJson getWrongTopicsListBySubject(
            @ApiParam(value = "提交学生id、科目id和题目序号(第几道题)练习接口")
            @RequestBody WrongTopicsVo wrongTopicsVo){
        String studentId = myStudentId();
        Student student =  studentService.findStudentById(studentId);
        Homework homework = new Homework();
        homework.setClassesId(student.getClassesId());
        homework.setStudentId(studentId);
        homework.setSubjectId(wrongTopicsVo.getSubjectId());
        homework.setSchoolId(student.getSchoolId());
        homework.setTypeId("23");//2和3：单选和多选
        homework.setPager(new Pager());
        homework.getPager().setPageSize(1);
        homework.getPager().setBeginRow(wrongTopicsVo.getTopicsNum());
        List<WrongTopics> data = homeworkService.mistakesCollection(homework);
        if(data!=null && data.size()>0){
            return new ResponseJson(data.get(0));
        }
        return new ResponseJson();
    }

    @PostMapping("/findOneStudentDetail")
    @ApiOperation(value = "查找当前点击学生薄弱知识点概率情况(String subjectId)", notes = "返回响应对象")
    public ResponseJson findOneStudentDetail(
            @RequestBody Homework homework) {
        String studentId = myStudentId();
        Student student =  studentService.findStudentById(studentId);
        homework.setClassesId(student.getClassesId());
        homework.setStudentId(studentId);
        homework.setSchoolId(student.getSchoolId());
        List<Map<String, Object>> data = homeworkService.findOneStudentDetail(homework);
        return new ResponseJson(data);
    }

    @PostMapping("/getWrongTopicsCountBySubject")
    @ApiOperation(value = "学生根据科目获取错题数量")
    public ResponseJson getWrongTopicsCountBySubject(
            @ApiParam(value = "科目id")
            @RequestBody WrongTopicsVo wrongTopicsVo){
        String studentId = myStudentId();
        Student student =  studentService.findStudentById(studentId);
        wrongTopicsVo.setClassesId(student.getClassesId());
        wrongTopicsVo.setStudentId(myStudentId());
        wrongTopicsVo.setSchoolId(student.getSchoolId());
        return new ResponseJson(homeworkService.mistakesCollectionCount(wrongTopicsVo));
        //return new ResponseJson(wrongTopicsService.findWrongTopicsCountByCondition(wrongTopicsVo));
    }

    @GetMapping("/findHomeworkStudentById/{id}")
    @ApiOperation(value = "通过作业id查找学生作业", notes = "返回响应对象")
    public ResponseJson findHomeworkStudentById(
            @ApiParam(value = "需要用到的作业id", required = true)
            @PathVariable String id){
        HomeworkStudent homeworkStudent=homeworkStudentService.findHomeworkStudentById(id);
        return new ResponseJson(homeworkStudent);
    }
    @PostMapping("/findHomeworkStudentById")
    @ApiOperation(value = "通过作业sqId和学生id查找学生线下作业详情", notes = "返回响应对象",response = HomeworkStudent.class)
    public ResponseJson findHomeworkStudentById(
            @ApiParam(value = "需要用到的作业sqId和学生id", required = true)
            @RequestBody StuHomeworkStatusVo stuHomeworkStatusVo){
    	HomeworkStudent homeworkStudent = new HomeworkStudent();
    	homeworkStudent.setStudentId(stuHomeworkStatusVo.getStudentId());
    	Homework homeworkObj = new Homework();
    	homeworkObj.setId(stuHomeworkStatusVo.getHomeworkSqId());
    	homeworkStudent.setHomeworkObj(homeworkObj);
        HomeworkStudent returnModel =homeworkStudentService.findOneHomeworkStudentByCondition(homeworkStudent);
        return new ResponseJson(returnModel);
    }

    @PostMapping("/findHomeworkStudentListByCondition")
    @ApiOperation(value = "查找学生作业列表", notes = "返回响应对象-HomeworkStudent",response = HomeworkStudent.class)
    public ResponseJson findHomeworkStudentListByCondition(
            @ApiParam(value = "ps:Page分页 排序字段固定 sortField:createTime;排序规则切换：sortOrder:desc/asc")
            @RequestBody HomeworkStudent homeworkStudent){
        List<HomeworkStudent> data=homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
        return new ResponseJson(data);
    }

    @PostMapping("/findHomeworkStudentsByCondition")
    @ApiOperation(value = "根据科目查找学生提交状态对应的作业列表", notes = "返回响应对象-HomeworkStudent",response = HomeworkStudent.class)
    public ResponseJson findHomeworkStudentsByCondition(
            @ApiParam(value = "ps:Page分页 排序字段固定 sortField:createTime;排序规则切换：sortOrder:desc/asc")
            @RequestBody StudentFindHomeworkVo studentFindHomeworkVo){
        List<HomeworkStudent> data=homeworkStudentService.findHomeworkStudents4Bmp(studentFindHomeworkVo);
        return new ResponseJson(data);
    }
    @PostMapping("/studentSubmitHomework")
    @ApiOperation(value = "学生提交作业", notes = "返回响应对象")
    public ResponseJson studentSubmitHomework(
            @ApiParam(value = "作业内容必须提交。ps:homeworkype：1线上/2线下；答案拍好续提交，如ACBD转成ABCD提交")
            @Validated(value = {GroupOne.class})
            @RequestBody HomeworkVo homeworkVo){
        HomeworkStudent homeworkStudent = homeworkStudentService.findHomeworkStudentById(homeworkVo.getHomeworkId());
        curSchoolYearService.setSchoolYearTerm(homeworkVo,mySchoolId());
        if(homeworkStudent==null){
            return new ResponseJson(false,"该作业不存在");
        }
        return homeworkStudentService.studentSubmitHomework(homeworkVo);
    }
    
    @PostMapping("/findTopicsRecordListByCondition")
    @ApiOperation(value = "根据作业sqId和学生id查找学生线上作业做题详情", notes = "返回学生答题记录表列表")
    public ResponseJson findTopicsRecordListByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody StuHomeworkStatusVo stuHomeworkStatusVo){
    	TopicsRecord topicsRecord = new TopicsRecord();
    	topicsRecord.setStudentId(stuHomeworkStatusVo.getStudentId());
    	topicsRecord.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
    	topicsRecord.setChannelId(stuHomeworkStatusVo.getHomeworkSqId());
        return new ResponseJson(topicsRecordService.findTopicsRecordListByCondition(topicsRecord));
    }

    @PostMapping("/findTopicsRecordBy4Like")
    @ApiOperation(value = "根据作业sqId和和题目的topicsObjId", notes = "返回学生答题记录表列表")
    public ResponseJson findOneTopicsRecordListByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody StuHomeworkStatusVo stuHomeworkStatusVo){
        TopicsRecord topicsRecord = new TopicsRecord();
        topicsRecord.setStudentId(myStudentId());
        topicsRecord.setChannelType(Constant.TOPICS.FROM_HOMEWORK);
        topicsRecord.setChannelId(stuHomeworkStatusVo.getHomeworkSqId());
        Topics topicsObj = new Topics();
        topicsObj.setId(stuHomeworkStatusVo.getTopicsObjId());
        topicsRecord.setTopicsObj(topicsObj);
        return new ResponseJson(topicsRecordService.findTopicsRecordBy4Like(topicsRecord));
    }

    @GetMapping("/findTodayHomeworkByStudent/{studentId}")
    @ApiOperation(value = "首页-今日作业")
    public ResponseJson findTodayHomeworkByStudent(
            @ApiParam(value = "学生id",required = true)
            @PathVariable("studentId") String studentId){
        return new ResponseJson(homeworkStudentService.findTodayHomeworkByStudent(studentId));
    }
    @GetMapping("/findHomeworkSubject")
    @ApiOperation(value = "作业列表查询科目")
    public ResponseJson findHomeworkSubject(){
        return homeworkStudentService.findHomeworkSubject();
    }

    @GetMapping("/delWrongTopic/{id}")
    @ApiOperation(value = "错题本通过id删除错题")
    public ResponseJson delWrongTopic(
            @ApiParam(value = "错题记录对应的id",required = true)
            @PathVariable("id") String id){
        wrongTopicsService.deleteWrongTopics(id);
        return new ResponseJson();
    }

    @PostMapping("/ignore/bmpUploadHomework")
    public ResponseJson bmpUploadHomework(MultipartFile file){
        FileObject fileObj = new FileObject();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_BMPHOMEWORK));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return new ResponseJson(fileObj);
    }

}