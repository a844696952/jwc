package com.yice.edu.cn.jw.controller.JwCourse;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.service.jwCourse.JwCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jwCourse")
@Api(value = "/jwCourse", description = "课程信息模块")
public class JwCourseController {
    @Autowired
    private JwCourseService jwCourseService;

    @GetMapping("/findJwCourseById/{id}")
    @ApiOperation(value = "通过id查找课程信息", notes = "返回课程信息对象")
    public JwCourse findJwCourseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return jwCourseService.findJwCourseById(id);
    }

    @PostMapping("/saveJwCourse")
    @ApiOperation(value = "保存课程信息", notes = "返回课程信息对象")
    public JwCourse saveJwCourse(
            @ApiParam(value = "课程信息对象", required = true)
            @RequestBody JwCourse jwCourse) {
        jwCourseService.saveJwCourse(jwCourse);
        return jwCourse;
    }

    @PostMapping("/findJwCourseListByCondition")
    @ApiOperation(value = "根据条件查找课程信息列表", notes = "返回课程信息列表")
    public List<JwCourse> findJwCourseListByCondition(
            @ApiParam(value = "课程信息对象")
            @RequestBody JwCourse jwCourse) {
        return jwCourseService.findJwCourseListByCondition(jwCourse);
    }

    @PostMapping("/findJwCourseCountByCondition")
    @ApiOperation(value = "根据条件查找课程信息列表个数", notes = "返回课程信息总个数")
    public long findJwCourseCountByCondition(
            @ApiParam(value = "课程信息对象")
            @RequestBody JwCourse jwCourse) {
        return jwCourseService.findJwCourseCountByCondition(jwCourse);
    }

    @PostMapping("/updateJwCourse")
    @ApiOperation(value = "修改课程信息", notes = "课程信息对象必传")
    public void updateJwCourse(
            @ApiParam(value = "课程信息对象,对象属性不为空则修改", required = true)
            @RequestBody JwCourse jwCourse) {
        jwCourseService.updateJwCourse(jwCourse);
    }

    @GetMapping("/deleteJwCourse/{id}")
    @ApiOperation(value = "通过id删除课程信息")
    public void deleteJwCourse(
            @ApiParam(value = "课程信息对象", required = true)
            @PathVariable String id) {
        jwCourseService.deleteJwCourse(id);
    }
    @PostMapping("/findOneJwCourseByCondition")
    @ApiOperation(value = "根据条件查找单个课程信息,结果必须为单条数据", notes = "返回单个课程信息,没有时为空")
    public JwCourse findOneJwCourseByCondition(
            @ApiParam(value = "课程信息对象")
            @RequestBody JwCourse jwCourse){
        return jwCourseService.findOneJwCourseByCondition(jwCourse);
    }

    @PostMapping("/deleteJwCourseByCondition")
    @ApiOperation(value = "根据条件删除课程信息")
    public void deleteJwCourseByCondition(
            @ApiParam(value = "课程信息对象")
            @RequestBody JwCourse jwCourse) {
        jwCourseService.deleteJwCourseByCondition(jwCourse);
    }

    @PostMapping("/queryAllByTypeIdGrade")
    @ApiOperation(value = "获得数据字典的高中年级")
    public List<Dd> queryAllByTypeIdGrade() {
        return jwCourseService.queryAllByTypeIdGrade();
    }

    @PostMapping("/queryAllByTypeIdCourse")
    @ApiOperation(value = "获得数据字典的课程名称")
    public List<Dd> queryAllByTypeIdCourse() {
        return jwCourseService.queryAllByTypeIdCourse();
    }

    @PostMapping("/queryAllByTypeIdType")
    @ApiOperation(value = "获得数据字典的课程类型")
    public List<Dd> queryAllByTypeIdType() {
        return jwCourseService.queryAllByTypeIdType();
    }

    @PostMapping("/queryAllByTypeIdBuilding")
    @ApiOperation(value = "获得数据字典的场地类型")
    public List<Dd> queryAllByTypeIdBuilding() {
        return jwCourseService.queryAllByTypeIdBuilding();
    }

    @PostMapping("/queryOneById")
    @ApiOperation(value = "通过id获得数组字典的一行记录")
    public Dd queryOneById(String id) {
        return jwCourseService.queryOneById(id);
    }

    @PostMapping("/distinctJwCourse")
    @ApiOperation(value = "判断是否有重复")
    public long distinctJwCourse(
            @ApiParam(value = "传入要添加的课程信息", required = true)
            @RequestBody JwCourse jwCourse) {
        return jwCourseService.distinctJwCourse(jwCourse);
    }



    @PostMapping("/deleteTeacherClassesCourseByCondition")
    @ApiOperation(value = "删除任课老师的课程信息")
    public void deleteTeacherClassesCourseByCondition(
            @ApiParam(value = "传入课程的id", required = true)
            @RequestBody JwCourse jwCourse
    ) {
        jwCourseService.deleteTeacherClassesCourseByCondition(jwCourse);
    }

    @PostMapping("/updateTeacherClassesCourseByCondition")
    @ApiOperation(value = "修改课程")
    public  void  updateTeacherClassesCourseByCondition(
            @ApiParam(value = "传入课程对象",required = true)
            @RequestBody  JwCourse jwCourse
    ){
        jwCourseService.updateTeacherClassesCourseByCondition(jwCourse);
    }


    @PostMapping("/saveJwCourses")
    @ApiOperation(value = "同时增加多个年级")
    public long saveJwCourses(
            @ApiParam(value = "传入课程对象",required = true)
            @RequestBody JwCourse jwCourse
    ){
        return jwCourseService.saveJwcourses(jwCourse);
    }


    @PostMapping("/findJwCourseListByConditionGai")
    @ApiOperation(value ="原生成查询代码",notes = "返回列表信息")
    public  List<JwCourse> findJwCourseListByConditionGai(
            @ApiParam(value = "传入课程信息")
            @RequestBody JwCourse jwCourse
    ){
        return  jwCourseService.findJwCourseListByConditionGai(jwCourse);
    }

    @GetMapping("/findTeachersByNameId/{nameId}/{schoolId}")
    @ApiOperation(value ="根据系统的课程id,查找学校里拥有该课程的老师",notes = "返回列表信息")
    public List<Teacher> findTeachersByNameId(@PathVariable("nameId") String typeId,@PathVariable("schoolId") String schoolId){
        return jwCourseService.findTeachersByNameId(typeId,schoolId);
    }

    @PostMapping("/findJwCourseListByConditionKong")
    public List<JwCourse> findJwCourseListByConditionKong(@RequestBody JwCourse jwCourse){
        return jwCourseService.findJwCourseListByConditionKong(jwCourse);
    }

    @PostMapping("/findJwCourseCountByConditionKong")
    public long findJwCourseCountByConditionKong(@RequestBody JwCourse jwCourse){
        return jwCourseService.findJwCourseCountByConditionKong(jwCourse);
    }

    @GetMapping("/findSchoolEaxmCourseList/{schoolId}")
    public List<JwCourse> findSchoolEaxmCourseList(@PathVariable("schoolId")String schoolId){
        return jwCourseService.findSchoolEaxmCourseList(schoolId);
    }

    @PostMapping("/findFiltrationJwCouserBySchoolId")
    public List<Dd> findFiltrationJwCouserBySchoolId(@RequestBody Dd dd){
        return jwCourseService.findFiltrationJwCouserBySchoolId(dd);
    }
}