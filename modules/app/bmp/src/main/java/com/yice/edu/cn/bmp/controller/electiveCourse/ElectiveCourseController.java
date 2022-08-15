package com.yice.edu.cn.bmp.controller.electiveCourse;

import com.yice.edu.cn.bmp.service.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.bmp.service.electiveCourse.ElectiveStudentService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/electiveCourse")
@Api(value = "/electiveCourse",description = "选修课模块")
public class ElectiveCourseController {
    @Autowired
    private ElectiveStudentService electiveStudentService;
    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/saveElectiveCourse")
    @ApiOperation(value = "保存选修课对象", notes = "返回保存好的选修课对象", response=ElectiveCourse.class)
    public ResponseJson saveElectiveCourse(
            @ApiParam(value = "选修课对象", required = true)
            @RequestBody ElectiveCourse electiveCourse){
        electiveCourse.setSchoolId(mySchoolId());
        curSchoolYearService.setSchoolYearTerm(electiveCourse,mySchoolId());
        electiveCourse.setCloseStatus(0);
        electiveCourse.setClassEndStatus(0);
        ElectiveCourse s=electiveCourseService.saveElectiveCourse(electiveCourse);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findElectiveCourseById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找选修课", notes = "返回响应对象", response=ElectiveCourse.class)
    public ResponseJson findElectiveCourseById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveCourse electiveCourse=electiveCourseService.findElectiveCourseById(id);
        return new ResponseJson(electiveCourse);
    }

    @PostMapping("/update/updateElectiveCourse")
    @ApiOperation(value = "修改选修课对象", notes = "返回响应对象")
    public ResponseJson updateElectiveCourse(
            @ApiParam(value = "被修改的选修课对象,对象属性不为空则修改", required = true)
            @RequestBody ElectiveCourse electiveCourse){
        electiveCourse.setSchoolId(mySchoolId());
        electiveCourseService.updateElectiveCourse(electiveCourse);
        return new ResponseJson();
    }

    @GetMapping("/look/lookElectiveCourseById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找选修课", notes = "返回响应对象", response=ElectiveCourse.class)
    public ResponseJson lookElectiveCourseById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ElectiveCourse electiveCourse=electiveCourseService.findElectiveCourseById(id);
        return new ResponseJson(electiveCourse);
    }

    @PostMapping("/findElectiveCoursesByCondition")
    @ApiOperation(value = "根据条件查找选修课", notes = "返回响应对象", response=ElectiveCourse.class)
    public ResponseJson findElectiveCoursesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveCourse electiveCourse){
        electiveCourse.setSchoolId(mySchoolId());
        List<ElectiveCourse> data=electiveCourseService.findElectiveCourseListByCondition(electiveCourse);
        long count=electiveCourseService.findElectiveCourseCountByCondition(electiveCourse);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneElectiveCourseByCondition")
    @ApiOperation(value = "根据条件查找单个选修课,结果必须为单条数据", notes = "没有时返回空", response=ElectiveCourse.class)
    public ResponseJson findOneElectiveCourseByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ElectiveCourse electiveCourse){
        ElectiveCourse one=electiveCourseService.findOneElectiveCourseByCondition(electiveCourse);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteElectiveCourse/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteElectiveCourse(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        electiveCourseService.deleteElectiveCourse(id);
        return new ResponseJson();
    }


    @PostMapping("/findElectiveCourseListByCondition")
    @ApiOperation(value = "根据条件查找选修课列表", notes = "返回响应对象,不包含总条数", response=ElectiveCourse.class)
    public ResponseJson findElectiveCourseListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveCourse electiveCourse){
       electiveCourse.setSchoolId(mySchoolId());
        List<ElectiveCourse> data=electiveCourseService.findElectiveCourseListByCondition(electiveCourse);
        return new ResponseJson(data);
    }

    @PostMapping("/findElectiveCoursesByConditionForStu")
    @ApiOperation(value = "根据条件查找选修课,传入 classesId、studentId", notes = "返回响应对象", response=ElectiveCourse.class)
    public ResponseJson findElectiveCoursesByConditionForStu(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveCourse electiveCourse){

        if(electiveCourse.getListType().equals("1")){

            electiveCourse.setSchoolId(mySchoolId());
            List<ElectiveCourse> data=electiveCourseService.findElectiveCoursesByConditionForStu(electiveCourse);

            //获取已选课程集合
            ElectiveStudent electiveStudent=new ElectiveStudent();
            electiveStudent.setSchoolId(mySchoolId());
            electiveStudent.setStudentId(electiveCourse.getStudentId());
            electiveStudent.setClassesId(electiveCourse.getClassesId());
            curSchoolYearService.setSchoolYearId(electiveStudent,mySchoolId());
            List<ElectiveCourse> data1=electiveStudentService.getAlreadySelectCourseList(electiveStudent);


            data.forEach(d->{
                data1.forEach(d1->{
                    if(d.getId().equals(d1.getElectiveId())){
                        d.setSignUpStatus(8);
                    }
                });
            });


            long count=electiveCourseService.findElectiveCourseCountByConditionForStu(electiveCourse);
            return new ResponseJson(data,count);

        }else  if(electiveCourse.getListType().equals("2")){
            //获取已选课程集合
            ElectiveStudent electiveStudent=new ElectiveStudent();
            electiveStudent.setSchoolId(mySchoolId());
            electiveStudent.setStudentId(electiveCourse.getStudentId());
            electiveStudent.setClassesId(electiveCourse.getClassesId());
            electiveStudent.setPager(electiveCourse.getPager());
            electiveStudent.setName(electiveCourse.getName());
            curSchoolYearService.setSchoolYearId(electiveStudent,mySchoolId());
            List<ElectiveCourse> data1=electiveStudentService.getAlreadySelectCourseList(electiveStudent);
            long count=electiveStudentService.getAlreadySelectCourseCount(electiveStudent);
            return new ResponseJson(data1,count);

        }
        return new ResponseJson();

    }










}
