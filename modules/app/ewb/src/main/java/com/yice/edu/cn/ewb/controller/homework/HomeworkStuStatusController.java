package com.yice.edu.cn.ewb.controller.homework;


import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.practice.FileObject;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.StudentFindHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.CompleteHomeworkQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkOffViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.StuHomeworkStatusVo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.ewb.service.homework.HomeworkStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;


@RestController
@RequestMapping("/homeworkStuStatus")
@Api(value = "/homeworkStuStatus", description = "查询学生作业状态")
public class HomeworkStuStatusController {

    @Autowired
    private HomeworkStudentService homeworkStudentService;

    @PostMapping("/findHomeworkStudentsByCondition")
    @ApiOperation(value = "根据科目查找学生提交状态对应的作业列表", notes = "返回响应对象-HomeworkStudent",response = HomeworkStudent.class)
    public ResponseJson findHomeworkStudentsByCondition(
            @ApiParam(value = "ps:Page分页 排序字段固定 sortField:createTime;排序规则切换：sortOrder:desc/asc")
            @RequestBody StudentFindHomeworkVo studentFindHomeworkVo){
        List<HomeworkStudent> data=homeworkStudentService.findHomeworkStudents4Bmp(studentFindHomeworkVo);
        return new ResponseJson(data);
    }

    @GetMapping("/findHomeworkSubject")
    @ApiOperation(value = "作业列表查询科目")
    public ResponseJson findHomeworkSubject(){
        return homeworkStudentService.findHomeworkSubject();
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

    @PostMapping("/studentSubmitHomework")
    @ApiOperation(value = "学生提交作业", notes = "返回响应对象")
    public ResponseJson studentSubmitHomework(
            @ApiParam(value = "作业内容必须提交。ps:homeworkype：1线上/2线下")
            @Validated
            @RequestBody HomeworkVo homeworkVo){
        return homeworkStudentService.studentSubmitHomework(homeworkVo);
    }

    @PostMapping("/findHomeworkStuOffListByCondition")
    @ApiOperation(value = "获取学生线下作业的列表", notes = "返回学生作业列表",response = StuHomeworkOffViewVo.class)
    public ResponseJson findHomeworkStuOffListByCondition(
            @ApiParam(value = "作业sqId和type 1.准时提交 2.未提交  3.已逾期和replyWay 1.拍照上传 2.确认回复",required=true) @RequestBody CompleteHomeworkQueryVo remarkHomeworkQueryVo) {

        StuHomeworkOffViewVo result = new StuHomeworkOffViewVo();
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        Homework homeworkObj = new Homework();
        homeworkObj.setId(remarkHomeworkQueryVo.getId());
        homeworkObj.setType(Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE);
        homeworkObj.setTeacherId(myId());
        homeworkStudent.setHomeworkObj(homeworkObj);

        homeworkStudent.setStatus(remarkHomeworkQueryVo.getType().intValue());
        List<HomeworkStudent> homeworkStudentList = homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
        result.setHomeworkStudentList(homeworkStudentList);

        homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_HAS);
        long hasComplete = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
        result.setHasCompleteNum(hasComplete);

        homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
        long hasNotCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
        result.setHasNotCompleteNum(hasNotCompleteNum);

        homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_OUT_TIME);
        long hasOutTimeCompleteNum = homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
        result.setHasOutTimeCompleteNum(hasOutTimeCompleteNum);
        return new ResponseJson(result);
    }

    @PostMapping("/ignore/ewbUploadHomework")
    public ResponseJson ewbUploadHomework(MultipartFile file){
        FileObject fileObj = new FileObject();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_EWBHOMEWORK));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return new ResponseJson(fileObj);
    }
}
