package com.yice.edu.cn.jw.controller.yed;

import com.yice.edu.cn.common.pojo.jw.yed.*;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.yed.JournalCircle;
import com.yice.edu.cn.common.pojo.jw.yed.StudentCheckWork;
import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.common.pojo.jw.yed.Yed;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.jw.service.yed.YedService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yedHomePage")
public class YedController {

    @Autowired
    private YedService yedService;

    @PostMapping("/findNewbornList")
    @ApiOperation("返回新生人数占比")
    public List<Yed> findNewbornList(@RequestBody  String educationBureauId){
        return  yedService.findNewbornList(educationBureauId);
    }

    @GetMapping("/findJournalStatic/{educationBureauId}")
    @ApiOperation("查询日志发布情况")
    public List<JournalCircle> findJournalStatic(@PathVariable("educationBureauId") String educationBureauId){
        return yedService.findJournalStatic(educationBureauId);
    }
  /*  @PostMapping("/findSchoolHomeworkNumByDateAndStatus")
    @ApiOperation("查询一段时间内学校布置的作业学生完成状态总数量")
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(@RequestBody HomeworkCountQueryVo vo){
        return yedService.findSchoolHomeworkNumByDateAndStatus(vo);
    }
    @PostMapping("/findSchoolHomeworkNumByDate")
    @ApiOperation("查询一段时间内学校布置的作业总数量")
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDate(@RequestBody HomeworkCountQueryVo vo){
        return yedService.findSchoolHomeworkNumByDate(vo);
    }*/

    @GetMapping("/findStudentCheckWork/{educationBureauId}")
    @ApiOperation("查询学生考勤情况")
    public StudentCheckWork findStudentCheckWork(@PathVariable("educationBureauId") String educationBureauId){
        StudentCheckWork studentCheckWork=new StudentCheckWork();
        studentCheckWork.setEducationBureauId(educationBureauId);
        return yedService.findStudentCheckWork(studentCheckWork);
    }



    @GetMapping("/findSpaceByRoleList/{educationBureauId}")
    @ApiOperation("查询学校场地类型数量")
    public  List<Yed> findSpaceByRoleList(@PathVariable("educationBureauId") String educationBureauId ){
        return yedService.findSpaceByRoleList(educationBureauId);
    }

    @PostMapping("/findSchoolNotifiedListByCondition")
    @ApiOperation("查询重大通知")
    public List<SchoolNotified> findSchoolNotifiedListByCondition(@RequestBody SchoolNotified schoolNotified){
        return yedService.findSchoolNotifiedListByCondition(schoolNotified);
    }

    @PostMapping("/findEnrolmentList")
    @ApiOperation("查询近三年升学率")
    public List<Object> findEnrolmentList(@RequestBody Enrolment enrolment){
        return yedService.findEnrolmentList(enrolment);
    }

    /*@GetMapping("/findSchoolByEducation/{educationBureauId}")
    List<School>findSchoolByEducation(@PathVariable("educationBureauId") String educationBureauId){
        return  yedService.findSchoolByEducation(educationBureauId);
    }*/

    @GetMapping("/findTaskSituation/{educationBureauId}")
    List<AreaOperationVolume>findTaskSituation(@PathVariable("educationBureauId") String educationBureauId){
        return  yedService.findTaskSituation(educationBureauId);
    }
}
