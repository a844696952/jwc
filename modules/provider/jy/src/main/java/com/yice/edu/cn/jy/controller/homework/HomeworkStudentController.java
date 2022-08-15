package com.yice.edu.cn.jy.controller.homework;

import java.util.List;

import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.jy.service.homework.HomeworkStudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/homeworkStudent")
@Api(value = "/homeworkStudent",description = "学生作业状态表模块")
public class HomeworkStudentController {
    @Autowired
    private HomeworkStudentService homeworkStudentService;

    @GetMapping("/findHomeworkStudentById/{id}")
    @ApiOperation(value = "通过id查找学生作业状态表", notes = "返回学生作业状态表对象")
    public HomeworkStudent findHomeworkStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return homeworkStudentService.findHomeworkStudentById(id);
    }

    @PostMapping("/saveHomeworkStudent")
    @ApiOperation(value = "保存学生作业状态表", notes = "返回学生作业状态表对象")
    public HomeworkStudent saveHomeworkStudent(
            @ApiParam(value = "学生作业状态表对象", required = true)
            @RequestBody HomeworkStudent homeworkStudent){
        homeworkStudentService.saveHomeworkStudent(homeworkStudent);
        return homeworkStudent;
    }

    @PostMapping("/findHomeworkStudentListByCondition")
    @ApiOperation(value = "根据条件查找学生作业状态表列表", notes = "返回学生作业状态表列表")
    public List<HomeworkStudent> findHomeworkStudentListByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
        return homeworkStudentService.findHomeworkStudentListByCondition(homeworkStudent);
    }
    @PostMapping("/findHomeworkStudents4Bmp")
    @ApiOperation(value = "根据条件查找学生作业表列表", notes = "返回学生作业表列表")
    public List<HomeworkStudent> findHomeworkStudents4Bmp(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
        return homeworkStudentService.findHomeworkStudents4Bmp(homeworkStudent);
    }
    @PostMapping("/findTodayHomeworkByStudent")
    @ApiOperation(value = "学生查找指定day发布的作业", notes = "返回学生作业列表")
    public List<HomeworkStudent> findTodayHomeworkByStudent(
            @ApiParam(value = "学生作业对象")
            @RequestBody HomeworkStudent homeworkStudent){
        return homeworkStudentService.findTodayHomeworkByStudent(homeworkStudent);
    }
    @PostMapping("/findHomeworkStudentCountByCondition")
    @ApiOperation(value = "根据条件查找学生作业状态表列表个数", notes = "返回学生作业状态表总个数")
    public long findHomeworkStudentCountByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
        return homeworkStudentService.findHomeworkStudentCountByCondition(homeworkStudent);
    }

    @PostMapping("/updateHomeworkStudent")
    @ApiOperation(value = "修改学生作业状态表", notes = "学生作业状态表对象必传")
    public void updateHomeworkStudent(
            @ApiParam(value = "学生作业状态表对象,对象属性不为空则修改", required = true)
            @RequestBody HomeworkStudent homeworkStudent){
        homeworkStudentService.updateHomeworkStudent(homeworkStudent);
    }

    @GetMapping("/deleteHomeworkStudent/{id}")
    @ApiOperation(value = "通过id删除学生作业状态表")
    public void deleteHomeworkStudent(
            @ApiParam(value = "学生作业状态表对象", required = true)
            @PathVariable String id){
        homeworkStudentService.deleteHomeworkStudent(id);
    }
    @PostMapping("/deleteHomeworkStudentByCondition")
    @ApiOperation(value = "根据条件删除学生作业状态表")
    public void deleteHomeworkStudentByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
        homeworkStudentService.deleteHomeworkStudentByCondition(homeworkStudent);
    }
    @PostMapping("/findOneHomeworkStudentByCondition")
    @ApiOperation(value = "根据条件查找单个学生作业状态表,结果必须为单条数据", notes = "返回单个学生作业状态表,没有时为空")
    public HomeworkStudent findOneHomeworkStudentByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
        return homeworkStudentService.findOneHomeworkStudentByCondition(homeworkStudent);
    }
    @PostMapping("/studentSubmitHomework")
    @ApiOperation(value = "学生提交作业")
    public ResponseJson studentSubmitHomework(
            @Validated(value = {GroupOne.class})
            @RequestBody HomeworkVo homeworkVo){
        if(homeworkVo.getHomeworkType() == Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE){
            //线上作业
            return homeworkStudentService.submitOnlineHomework(homeworkVo);
        }else if(homeworkVo.getHomeworkType() == Constant.HOMEWORK.HOMEWORK_TYPE_OFFLINE){
            //线下作业
            return homeworkStudentService.submitUnderlineHomework(homeworkVo);
        }
        return new ResponseJson(false,"作业类型错误");
    }
    
    @GetMapping("/delRemarkNoteByHomeworkStudentId/{id}")
    @ApiOperation(value = "通过id删除学生作业批阅")
    public void delRemarkNoteByHomeworkStudentId(@PathVariable String id) {
            homeworkStudentService.delRemarkNoteByHomeworkStudentId(id);
    }
    
    @PostMapping("/findHasCompleteHomeworkStuListByCondition")
    @ApiOperation(value = "根据条件查找学生作业线下未点评作业", notes = "返回学生作业状态表列表")
    public List<HomeworkStudent> findHasCompleteHomeworkStuListByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
    	Object [] statusArr = new Object[]{Constant.HOMEWORK.SUBMIT_HAS,Constant.HOMEWORK.SUBMIT_OUT_TIME};
    	homeworkStudent.setStatus(null);
        return homeworkStudentService.findHasCompleteHomeworkStuListByCondition(homeworkStudent,statusArr);
    }
    
    @PostMapping("/findHasCompleteHomeworkStuCountByCondition")
    @ApiOperation(value = "根据条件查找学生作业线下未点评作业数量", notes = "返回学生作业状态表列表")
    public long findHasCompleteHomeworkStuCountByCondition(
            @ApiParam(value = "学生作业状态表对象")
            @RequestBody HomeworkStudent homeworkStudent){
    	Object [] statusArr = new Object[]{Constant.HOMEWORK.SUBMIT_HAS,Constant.HOMEWORK.SUBMIT_OUT_TIME};
    	homeworkStudent.setStatus(null);
        return homeworkStudentService.findHasCompleteHomeworkStuCountByCondition(homeworkStudent,statusArr);
    }
    
    @PostMapping("/remakrStuHomework")
    @ApiOperation(value = "点评学生作业", notes = "学生作业状态表对象必传")
    public void remakrStuHomework(
            @ApiParam(value = "学生作业状态表对象,对象属性不为空则修改", required = true)
            @RequestBody HomeworkStudent homeworkStudent){
        homeworkStudentService.remakrStuHomework(homeworkStudent);
    }


}
