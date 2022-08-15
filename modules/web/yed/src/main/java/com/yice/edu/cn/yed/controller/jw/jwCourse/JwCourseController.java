//package com.yice.edu.cn.yed.controller.jwCourse;
//
//import com.yice.edu.cn.common.pojo.JwCourse.JwCourse;
//import com.yice.edu.cn.common.pojo.ResponseJson;
//import com.yice.edu.cn.common.pojo.dd.Dd;
//import com.yice.edu.cn.common.pojo.teacher.TeacherClassesCourse;
//import com.yice.edu.cn.yed.service.jwCourse.JwCourseService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/jwCourse")
//@Api(value = "/jwCourse",description = "课程信息模块")
//public class JwCourseController {
//    @Autowired
//    private JwCourseService jwCourseService;
//    @Transactional
//    @GetMapping("/findJwCourseById/{id}")
//    @ApiOperation(value = "通过id查找课程信息", notes = "返回响应对象")
//    public ResponseJson findJwCourseById(
//            @ApiParam(value = "需要用到的id", required = true)
//            @PathVariable String id){
//        JwCourse jwCourse=jwCourseService.findJwCourseById(id);
//        return new ResponseJson(jwCourse);
//    }
//
//    @Transactional
//    @PostMapping("/saveJwCourse")
//    @ApiOperation(value = "保存课程信息对象", notes = "返回响应对象")
//    public ResponseJson saveJwCourse(
//            @ApiParam(value = "课程信息对象", required = true)
//            @RequestBody JwCourse jwCourse){
//        long c = jwCourseService.distinctJwCourse(jwCourse);
//        if(c!=0){
//            return new ResponseJson(c);
//        }
//        JwCourse s=jwCourseService.saveJwCourse(jwCourse);
//        return new ResponseJson(s,c);
//
//    }
//    @PostMapping("/updateJwCourse")
//    @ApiOperation(value = "修改课程信息对象", notes = "返回响应对象")
//    public ResponseJson updateJwCourse(
//            @ApiParam(value = "被修改的课程信息对象,对象属性不为空则修改", required = true)
//            @RequestBody JwCourse jwCourse){
//        jwCourseService.updateJwCourse(jwCourse);
//        return new ResponseJson();
//    }
//
//    @PostMapping("/findJwCoursesByCondition")
//    @ApiOperation(value = "根据条件查找课程信息", notes = "返回响应对象")
//    public ResponseJson findJwCoursesByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @RequestBody JwCourse jwCourse){
//        List<JwCourse> data=jwCourseService.findJwCourseListByCondition(jwCourse);
//        long count=jwCourseService.findJwCourseCountByCondition(jwCourse);
//        return new ResponseJson(data,count);
//    }
//    @GetMapping("/delete/deletefindTeacherClassesCourseCountByConditionJwCourse/{id}")
//    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
//    public ResponseJson deleteJwCourse(
//            @ApiParam(value = "被删除记录的id", required = true)
//            @PathVariable String id){
//        jwCourseService.deleteJwCourse(id);
//        jwCourseService.deleteTeacherClassesCourseByCondition(id);
//        return new ResponseJson();
//    }
//
//    @GetMapping("/delete/deletekc/{id}")
//    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
//    public ResponseJson deletekc(
//            @ApiParam(value = "被删除记录的id", required = true)
//            @PathVariable String id){
//        jwCourseService.deleteJwCourse(id);
//        return new ResponseJson();
//    }
//
//
//    @GetMapping("/delete/deleteJwCourseByCondition")
//    @ApiOperation(value = "根据条件删除课程信息", notes = "返回响应对象")
//    public ResponseJson deleteJwCourseByCondition(
//            @ApiParam(value = "被删除的课程信息对象,对象属性不为空则作为删除条件", required = true)
//            @RequestBody JwCourse jwCourse){
//
//        jwCourseService.deleteJwCourseByCondition(jwCourse);
//        return new ResponseJson();
//    }
//
//    @PostMapping("/findJwCourseById/queryAllByTypeIdGrade")
//    @ApiOperation(value = "获得数据字典的高中年级",notes = "返回响应对象")
//    public ResponseJson queryAllByTypeIdGrade(){
//        List<Dd> data = jwCourseService.queryAllByTypeIdGrade();
//        List<Dd> data2 = jwCourseService.queryAllByTypeIdCourse();
//        List<Dd> data3 = jwCourseService.queryAllByTypeIdType();
//        List<Dd> data4 = jwCourseService.queryAllByTypeIdBuilding();
//        return  new ResponseJson(data,data2,data3,data4);
//    }
//
//    @PostMapping("/findJwCourseById/queryOneById/{id}")
//    @ApiOperation(value = "通过id获得数组字典的一行记录",notes = "返回响应对象")
//    public ResponseJson queryOneById(
//            @ApiParam(value = "根据id查询",required = true)
//            @PathVariable  String id){
//         Dd data1 = jwCourseService.queryOneById(id);
//         return  new ResponseJson(data1);
//    }
//
//
//    @PostMapping("/delete/findTeacherClassesCourseCountByCondition/{id}")
//    @ApiOperation(value = "查询是否有老师教这门课程",notes = "返回响应个数")
//    public ResponseJson findTeacherClassesCurrCountByCondition(
//            @ApiParam(value = "根据课程id查询",required = true)
//            @PathVariable String id){
//            long c = jwCourseService.findTeacherClassesCourseCountByCondition(id);
//        return  new ResponseJson(c);
//    }
//
//
//
//}
