package com.yice.edu.cn.jw.controller.electiveCourse;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.classes.JwClassesService;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveClassesService;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.jw.service.electiveCourse.ElectiveStudentService;
import com.yice.edu.cn.jw.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/electiveCourse")
@Api(value = "/electiveCourse",description = "选修课模块")
public class ElectiveCourseController {
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private ElectiveClassesService electiveClassesService;
    @Autowired
    private ElectiveStudentService electiveStudentService;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService classesService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/findElectiveCourseById/{id}")
    @ApiOperation(value = "通过id查找选修课", notes = "返回选修课对象")
    public ElectiveCourse findElectiveCourseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return electiveCourseService.findElectiveCourseById(id);
    }

    @GetMapping("/findElectiveCourseWithClassInfoById/{id}")
    @ApiOperation(value = "通过id查找选修课", notes = "返回选修课对象")
    public ElectiveCourse findElectiveCourseWithClassInfoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
         return electiveCourseService.findElectiveCourseWithClassInfoById(id);
    }

    @PostMapping("/saveElectiveCourse")
    @ApiOperation(value = "保存选修课", notes = "返回选修课对象")
    public ElectiveCourse saveElectiveCourse(
            @ApiParam(value = "选修课对象", required = true)
            @RequestBody ElectiveCourse electiveCourse){
        ElectiveCourse e=electiveCourseService.saveElectiveCourse(electiveCourse);
        if(electiveCourse.getClassIdList()!=null){
            List<ElectiveClasses> electiveClassess=new ArrayList<>();
            electiveCourse.getClassIdList().forEach(classId->{
                JwClasses cl=  classesService.findJwClassesById(classId);
                ElectiveClasses ec=new ElectiveClasses();
                ec.setId(sequenceId.nextId());
                ec.setElectiveId(electiveCourse.getId());
                ec.setSchoolYearId(electiveCourse.getSchoolYearId());
                ec.setClassesId(classId);
                ec.setClassesName(cl.getNumber()+"班");
                ec.setGradeId(Integer.valueOf(cl.getGradeId()));
                ec.setGradeName(cl.getGradeName());
                ec.setSchoolId(electiveCourse.getSchoolId());
                electiveClassess.add(ec);
            });
            electiveClassesService.batchSaveElectiveClasses(electiveClassess);
        }else {
            JwClasses cl=  classesService.findJwClassesById(electiveCourse.getClassesId());
            ElectiveClasses ec=new ElectiveClasses();
            ec.setId(sequenceId.nextId());
            ec.setElectiveId(electiveCourse.getId());
            ec.setSchoolYearId(electiveCourse.getSchoolYearId());
            ec.setClassesId(electiveCourse.getClassesId());
            ec.setClassesName(cl.getNumber()+"班");
            ec.setGradeId(Integer.valueOf(cl.getGradeId()));
            ec.setGradeName(cl.getGradeName());
            ec.setSchoolId(electiveCourse.getSchoolId());
            electiveClassesService.saveElectiveClasses(ec);
        }

        return electiveCourse;
    }

    @PostMapping("/findElectiveCourseListByCondition")
    @ApiOperation(value = "根据条件查找选修课列表", notes = "返回选修课列表")
    public List<ElectiveCourse> findElectiveCourseListByCondition(
            @ApiParam(value = "选修课对象")
            @RequestBody ElectiveCourse electiveCourse){
        return electiveCourseService.findElectiveCourseListByCondition(electiveCourse);
    }
    @PostMapping("/findElectiveCourseCountByCondition")
    @ApiOperation(value = "根据条件查找选修课列表个数", notes = "返回选修课总个数")
    public long findElectiveCourseCountByCondition(
            @ApiParam(value = "选修课对象")
            @RequestBody ElectiveCourse electiveCourse){
        return electiveCourseService.findElectiveCourseCountByCondition(electiveCourse);
    }

    @PostMapping("/updateElectiveCourse")
    @ApiOperation(value = "修改选修课", notes = "选修课对象必传")
    public void updateElectiveCourse(
            @ApiParam(value = "选修课对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveCourse electiveCourse){
        electiveCourseService.updateElectiveCourse(electiveCourse);

        //关闭后，将选修课内的学生清空，以免占用学生选修课名额。
        if(electiveCourse.getCloseStatus()!=null&&electiveCourse.getCloseStatus().equals(1)){
            ElectiveStudent electiveStudent=new ElectiveStudent();
            electiveStudent.setElectiveId(electiveCourse.getId());
            List<ElectiveStudent> students= electiveStudentService.findElectiveStudentListByCondition(electiveStudent);
            List<String> studentIdList=students.stream().map(ElectiveStudent::getStudentId).distinct().collect(Collectors.toList());
            pushToStudent(studentIdList,electiveCourse.getName(),electiveCourse.getId());
            electiveStudentService.deleteElectiveStudentByCondition(electiveStudent);
        }

        if(electiveCourse.getClassIdList()!=null){
            //先删除原有关联，在重新添加
            ElectiveClasses electiveClasses=new ElectiveClasses();
            electiveClasses.setElectiveId(electiveCourse.getId());
            electiveClassesService.deleteElectiveClassesByCondition(electiveClasses);

            List<ElectiveClasses> electiveClassess=new ArrayList<>();
            electiveCourse.getClassIdList().forEach(classId->{
                JwClasses cl=  classesService.findJwClassesById(classId);
                ElectiveClasses ec=new ElectiveClasses();
                ec.setId(sequenceId.nextId());
                ec.setElectiveId(electiveCourse.getId());
                ec.setSchoolYearId(electiveCourse.getSchoolYearId());
                ec.setClassesId(classId);
                ec.setClassesName(cl.getNumber()+"班");
                ec.setGradeId(Integer.valueOf(cl.getGradeId()));
                ec.setGradeName(cl.getGradeName());
                ec.setSchoolId(electiveCourse.getSchoolId());
                electiveClassess.add(ec);
            });

            electiveClassesService.batchSaveElectiveClasses(electiveClassess);
        }

    }

    @GetMapping("/deleteElectiveCourse/{id}")
    @ApiOperation(value = "通过id删除选修课")
    public void deleteElectiveCourse(
            @ApiParam(value = "选修课对象", required = true)
            @PathVariable String id){
        electiveCourseService.deleteElectiveCourse(id);
    }
    @PostMapping("/deleteElectiveCourseByCondition")
    @ApiOperation(value = "根据条件删除选修课")
    public void deleteElectiveCourseByCondition(
            @ApiParam(value = "选修课对象")
            @RequestBody ElectiveCourse electiveCourse){
        electiveCourseService.deleteElectiveCourseByCondition(electiveCourse);
    }
    @PostMapping("/findOneElectiveCourseByCondition")
    @ApiOperation(value = "根据条件查找单个选修课,结果必须为单条数据", notes = "返回单个选修课,没有时为空")
    public ElectiveCourse findOneElectiveCourseByCondition(
            @ApiParam(value = "选修课对象")
            @RequestBody ElectiveCourse electiveCourse){
        return electiveCourseService.findOneElectiveCourseByCondition(electiveCourse);
    }

    @PostMapping("/findStudentListByClassIdList")
    @ApiOperation(value = "传入班级id的数组，查询符合条件的学生数组", notes = "返回响应对象")
    public List<StudentPojo> findStudentListByClassIdList(@RequestBody List <String> classIdList){
        Student student=new Student();
        student.setClazzIdList(classIdList);
        List<StudentPojo> data=  studentService.findStudentListAndClassByClazzIdList(classIdList);
        return  data;
    }



    @PostMapping("/findElectiveCoursesByConditionForStu")
    List<ElectiveCourse> findElectiveCoursesByConditionForStu(@RequestBody  ElectiveCourse electiveCourse){

       return electiveCourseService.findElectiveCoursesByConditionForStu(electiveCourse);
    }
    @PostMapping("/findElectiveCourseCountByConditionForStu")
     long findElectiveCourseCountByConditionForStu(@RequestBody ElectiveCourse electiveCourse){
        return electiveCourseService.findElectiveCourseCountByConditionForStu(electiveCourse);
    }

    public void pushToStudent(List<String> stuIdList,String title,String electiveId){
        if(stuIdList.size()>0){
            //处理推送
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePusByRefrenceId(stuIdList.toArray(new String[stuIdList.size()]),"选修课","你选择的:"+title+"选课已关闭，点击查看详情。",Extras.ELECTIVE_COURSE_CLOSE,electiveId);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }

    }
}
