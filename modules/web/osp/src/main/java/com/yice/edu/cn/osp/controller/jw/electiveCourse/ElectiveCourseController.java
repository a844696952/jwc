package com.yice.edu.cn.osp.controller.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.classSchedule.ClassScheduleInitService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveCourseService;
import com.yice.edu.cn.osp.service.jw.electiveCourse.ElectiveSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/electiveCourse")
@Api(value = "/electiveCourse",description = "选修课模块")
public class ElectiveCourseController {

    @Autowired
    private ElectiveCourseService electiveCourseService;
    @Autowired
    private ClassScheduleInitService classScheduleInitService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ElectiveSetService electiveSetService;
    @Autowired
    JwClassesService jwClassesService;
    @Autowired
    DdService ddService;
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


    @GetMapping("/findClassScheduleList")
    @ApiOperation(value = "查询每天的课程节数", notes = "返回班级完整课表")
    public ResponseJson findClassScheduleList() {
        ClassScheduleInit classScheduleInit = new ClassScheduleInit();
        classScheduleInit.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        classScheduleInit.setPager(pager);
        List<ClassScheduleInit> classScheduleInits = classScheduleInitService.findListClassScheduleInitBySchool(classScheduleInit);
        return new ResponseJson(classScheduleInits);
    }

    @GetMapping("/findDepartmentForTree")
    @ApiOperation(value = "查找为学校结构树")
    public ResponseJson findDepartmentForTree() {
        List<Department> departments=departmentService.findDepartmentHaveOrNotTeacherForTree(LoginInterceptor.mySchoolId(), false, 0);
        return new ResponseJson(departments);
    }

    @PostMapping("/saveElectiveSet")
    @ApiOperation(value = "保存选修课设置对象", notes = "返回保存好的选修课设置对象", response=ElectiveCourse.class)
    public ResponseJson saveElectiveSet(
            @ApiParam(value = "选修课设置对象", required = true)
            @RequestBody List<ElectiveSet> electiveSetList){

        electiveSetList.forEach(es->{
            if(es.getId()!=null){
                electiveSetService.deleteElectiveSet(es.getId());
            }
            ElectiveSet electiveSet=new ElectiveSet();
            electiveSet.setSchoolId(mySchoolId());
            curSchoolYearService.setSchoolYearTerm(electiveSet,mySchoolId());
            electiveSet.setGradeId(es.getGradeId());
            electiveSet.setMinNum(es.getMinNum());
            electiveSet.setMaxNum(es.getMaxNum());
            electiveSetService.saveElectiveSet(electiveSet);

        });
        return new ResponseJson();
    }


    @GetMapping("/findGradesAndClassCurrentBySchool")
    @ApiOperation(value = "查找当前学校包含的年级班级树结构", notes = "返回响应对象")
    public ResponseJson findGradesAndClassCurrentBySchool(){

        List<Department> tree=new ArrayList<>();

        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        data.forEach(s->{
            Department grade=new Department();
            grade.setParentId("-1");
            grade.setName(s.getName());
            grade.setId(s.getId());
             grade.setType(0);
            List<Department> children=new ArrayList<>();

            JwClasses jwClasses=new JwClasses();
            jwClasses.setGradeId(s.getId());
            jwClasses.setSchoolId(mySchoolId());
            Pager pager=new Pager();
            pager.setSortField("number");
            pager.setSortOrder("asc");
            pager.setPaging(false);
            jwClasses.setPager(pager);
            List<JwClasses> classesList= jwClassesService.findJwClassesListByCondition(jwClasses);
            classesList.forEach(cl->{
                Department classes=new Department();
                classes.setParentId(s.getId());
                classes.setId(cl.getId());
                classes.setName(s.getName()+cl.getNumber()+'班');
                classes.setType(1);
                children.add(classes);
            });
            grade.setChildren(children);

            tree.add(grade);
        });
        return new ResponseJson(tree);
    }

    @PostMapping("/findElectiveSetListByCondition")
    @ApiOperation(value = "根据条件查找选修课设置列表", notes = "返回响应对象,不包含总条数", response=ElectiveSet.class)
    public ResponseJson findElectiveSetListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ElectiveSet electiveSet){
        electiveSet.setSchoolId(mySchoolId());
        electiveSet.getPager().setPaging(false);
        List<ElectiveSet> data=electiveSetService.findElectiveSetListByCondition(electiveSet);
        return new ResponseJson(data);
    }


}
