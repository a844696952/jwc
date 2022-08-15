package com.yice.edu.cn.osp.controller.xw.psycholgConsult;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import com.yice.edu.cn.osp.controller.dd.DdController;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.xw.psycholgConsult.XwPshcholgConsultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwPshcholgConsult")
@Api(value = "/xwPshcholgConsult", description = "心理咨询模块")
public class XwPshcholgConsultController {
    @Autowired
    private XwPshcholgConsultService xwPshcholgConsultService;
    @Autowired
    private DdService ddService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/saveXwPshcholgConsult")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveXwPshcholgConsult(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        Student stu = new Student();
        stu.setName(xwPshcholgConsult.getName());
        stu.setGradeId(xwPshcholgConsult.getGradeId());
        stu.setClassesId(xwPshcholgConsult.getClassId());
        stu.setSex(xwPshcholgConsult.getSex());
        List<Student> student = studentService.findStudentListByCondition(stu);
        if (student.size() != 0) {
            xwPshcholgConsult.setSchoolId(mySchoolId());
            xwPshcholgConsult.setTeacherId(myId());
            XwPshcholgConsult s = xwPshcholgConsultService.saveXwPshcholgConsult(xwPshcholgConsult);
            return new ResponseJson(s);
        } else {
            return new ResponseJson(false);
        }
    }

    @GetMapping("/update/findXwPshcholgConsultById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findXwPshcholgConsultById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwPshcholgConsult xwPshcholgConsult = xwPshcholgConsultService.findXwPshcholgConsultById(id);
        return new ResponseJson(xwPshcholgConsult);
    }

    @PostMapping("/update/updateXwPshcholgConsult")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwPshcholgConsult(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultService.updateXwPshcholgConsult(xwPshcholgConsult);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwPshcholgConsultById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookXwPshcholgConsultById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwPshcholgConsult xwPshcholgConsult = xwPshcholgConsultService.findXwPshcholgConsultById(id);
        return new ResponseJson(xwPshcholgConsult);
    }

    @PostMapping("/findXwPshcholgConsultsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findXwPshcholgConsultsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsult.setSchoolId(mySchoolId());
        List<XwPshcholgConsult> data = xwPshcholgConsultService.findXwPshcholgConsultListByCondition(xwPshcholgConsult);
        long count = xwPshcholgConsultService.findXwPshcholgConsultCountByCondition(xwPshcholgConsult);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwPshcholgConsultByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneXwPshcholgConsultByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsult.setTeacherId(myId());
        XwPshcholgConsult one = xwPshcholgConsultService.findOneXwPshcholgConsultByCondition(xwPshcholgConsult);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwPshcholgConsult/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwPshcholgConsult(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwPshcholgConsultService.deleteXwPshcholgConsult(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwPshcholgConsultListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findXwPshcholgConsultListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsult.setSchoolId(mySchoolId());
        List<XwPshcholgConsult> data = xwPshcholgConsultService.findXwPshcholgConsultListByCondition(xwPshcholgConsult);
        long count = xwPshcholgConsultService.findXwPshcholgConsultCountByCondition(xwPshcholgConsult);
        return new ResponseJson(data);
    }

    @PostMapping("/findXwPshcholgConsultByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,包含总条数")
    public ResponseJson findXwPshcholgConsultByCondition2(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        Pager pager = xwPshcholgConsult.getPager();
        pager.setLike("name");
        xwPshcholgConsult.setPager(pager);
        xwPshcholgConsult.setSchoolId(mySchoolId());
        xwPshcholgConsult.setTeacherId(myId());
        List<XwPshcholgConsult> data = xwPshcholgConsultService.findXwPshcholgConsultByCondition2(xwPshcholgConsult);
        Long count = xwPshcholgConsultService.findXwPshcholgConsultCountByCondition2(xwPshcholgConsult);
        return new ResponseJson(data, count);
    }

    @GetMapping("/update/findSex")
    @ApiOperation(value = "查找性别", notes = "返回响应对象")
    public ResponseJson findSex() {
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SEX);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findAllGradesInCurrentSchool")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findAllGradesInCurrentSchool() {
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        List<Dd> data = ddService.findDdListByCondition(dd);
        data.forEach(f -> {
            f.getGradeId();
        });
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/findAllClassByGradeId/{gradeId}")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        JwClasses c = new JwClasses();
        c.setSchoolId(LoginInterceptor.mySchoolId());
        c.setGradeId(gradeId);
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(c));
    }

}
