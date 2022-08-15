package com.yice.edu.cn.osp.controller.jw.jwCourse;

import cn.hutool.db.Page;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jwCourse")
@Api(value = "/jwCourse", description = "课程信息模块")
public class JwCourseController {
    @Autowired
    private JwCourseService jwCourseService;

    @Autowired
    private DdService ddService;


    @GetMapping("/findJwCourseById/{id}")
    @ApiOperation(value = "通过id查找课程信息", notes = "返回响应对象")
    public ResponseJson findJwCourseById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        jwCourse.setId(id);
        JwCourse jwCourse1 = jwCourseService.findOneJwCourseByCondition(jwCourse);
        return new ResponseJson(jwCourse1);
    }

    @PostMapping("/saveJwCourse")
    @ApiOperation(value = "保存课程信息对象", notes = "返回响应对象")
    public ResponseJson saveJwCourse(
            @ApiParam(value = "课程信息对象", required = true)
            @RequestBody @Validated({GroupTwo.class}) JwCourse jwCourse) {
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        long c = jwCourseService.saveJwCourse(jwCourse);
        jwCourseService.clearDdSubjectSchool(LoginInterceptor.mySchoolId());
        return new ResponseJson(c);

    }

    @PostMapping("/findOneJwCourseByCondition")
    @ApiOperation(value = "根据条件查找单个课程信息,结果必须为单条数据", notes = "没有时返回空", response=JwCourse.class)
    public ResponseJson findOneJwCourseByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwCourse jwCourse){
        JwCourse one=jwCourseService.findOneJwCourseByCondition(jwCourse);
        return new ResponseJson(one);
    }

    @PostMapping("/update/updateJwCourse")
    @ApiOperation(value = "修改课程信息对象", notes = "返回响应对象")
    public ResponseJson updateJwCourse(
           @ApiParam(value = "被修改的课程信息对象,对象属性不为空则修改", required = true)
           @RequestBody  @Validated({GroupOne.class})  JwCourse jwCourse) {
        long c = 0;
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        c = jwCourseService.distinctJwCourse(jwCourse);
        if (c != 0) {
            return new ResponseJson(false,"课程别名已存在！");
        }
        long s = -1;
        jwCourseService.updateTeacherClassesCourseByCondition(jwCourse);
        jwCourseService.clearDdSubjectSchool(LoginInterceptor.mySchoolId());
        return new ResponseJson(s);
    }

    @PostMapping("/findJwCoursesByCondition")
    @ApiOperation(value = "根据条件查找课程信息", notes = "返回响应对象")
    public ResponseJson findJwCoursesByConditionGai(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwCourse jwCourse) {
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        List<JwCourse> data = jwCourseService.findJwCourseListByConditionKong(jwCourse);
        long count = jwCourseService.findJwCourseCountByConditionKong(jwCourse);
        return new ResponseJson(data, count);
    }

    @PostMapping("/delete/deletefindTeacherClassesCourseCountByConditionJwCourse")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJwCourse(
            @ApiParam(value = "被删除记录的id", required = true)
            @RequestBody JwCourse jwCourse) {
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        jwCourseService.deleteTeacherClassesCourseByCondition(jwCourse);
        jwCourseService.clearDdSubjectSchool(LoginInterceptor.mySchoolId());
        return new ResponseJson();
    }

    @GetMapping("/delete/deletekc/{id}")
    @ApiOperation(value = "根据id删除课程信息", notes = "返回响应对象")
    public ResponseJson deletekc(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        jwCourse.setId(id);
        jwCourseService.deleteJwCourseByCondition(jwCourse);
        jwCourseService.clearDdSubjectSchool(LoginInterceptor.mySchoolId());
        return new ResponseJson();
    }


    @GetMapping("/delete/deleteJwCourseByCondition")
    @ApiOperation(value = "根据条件删除课程信息", notes = "返回响应对象")
    public ResponseJson deleteJwCourseByCondition(
            @ApiParam(value = "被删除的课程信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody JwCourse jwCourse) {
        jwCourseService.deleteJwCourseByCondition(jwCourse);
        jwCourseService.clearDdSubjectSchool(LoginInterceptor.mySchoolId());
        return new ResponseJson();
    }

    @PostMapping("/findJwCourseById/queryAllByTypeIdGrade")
    @ApiOperation(value = "获得数据字典的高中年级", notes = "返回响应对象")
    public ResponseJson queryAllByTypeIdGrade() {
        //获取学校类型
        Dd dd = new Dd();
        dd.setLevelType(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        dd.setTypeId("18");
        List<Dd> data =  ddService.findDdListByCondition(dd);
//         = jwCourseService.queryAllByTypeIdCourse();
        List<Dd> data2 = jwCourseService.queryAllByTypeIdType();
        List<Dd> data3 = jwCourseService.queryAllByTypeIdBuilding();

        return new ResponseJson(data, data2, data3);
    }

    @PostMapping("/findJwCourseById/queryOneById/{id}")
    @ApiOperation(value = "通过id获得数组字典的一行记录", notes = "返回响应对象")
    public ResponseJson queryOneById(
            @ApiParam(value = "根据id查询", required = true)
            @PathVariable String id) {
        Dd data1 = jwCourseService.queryOneById(id);
        return new ResponseJson(data1);
    }


    @PostMapping("/delete/findTeacherClassesCourseCountByCondition/{id}")
    @ApiOperation(value = "查询是否有老师教这门课程", notes = "返回响应个数")
    public ResponseJson findTeacherClassesCurrCountByCondition(
            @ApiParam(value = "根据课程id查询", required = true)
            @PathVariable String id) {
        long c = jwCourseService.findTeacherClassesCourseCountByCondition(id);
        return new ResponseJson(c);
    }

    //通过年段和科目查找出对应的年级
    @PostMapping("/findJwCourseById/findSubjectMaterialListByCondition")
    @ApiOperation(value = "查询",notes = "返回响应个数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "根据课程对象",required = true)
            @RequestBody JwCourse jwCourse
    ){
        SubjectMaterial subjectMaterial = new SubjectMaterial();
        //查询当前角色所处年段（小学、初中、高中）
        subjectMaterial.setAnnualPeriodId(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        subjectMaterial.setLeaf(2);
        subjectMaterial.setDdId(jwCourse.getNameId());
        List<SubjectMaterial> subjectMaterials = jwCourseService.findSubjectMaterialListByCondition(subjectMaterial);
        SubjectMaterial subjectMateria2 = new SubjectMaterial();
        if(subjectMaterials.size()!=0){
            subjectMateria2.setParentId(subjectMaterials.get(0).getId());
            Pager pager = new Pager();
            pager.setSortField("ddId");
            pager.setSortOrder(Pager.ASC);
            subjectMateria2.setPager(pager);
            //查询出改课程的所有年级段
            List<SubjectMaterial> subjectMaterials1 = jwCourseService.findSubjectMaterialListByCondition(subjectMateria2);

            return new ResponseJson(subjectMaterials1);
        }else{
            return  new ResponseJson(subjectMaterials);
        }

    }

    //查询出改课程对应的教材
    @GetMapping("/findJwCourseById/findSubjectMaterialListByConditionTwo/{id}")
    @ApiOperation(value = "查询",notes = "返回响应个数")
    public ResponseJson findSubjectMaterialListByConditionTwo(
            @ApiParam(value = "根据科目id查询教材", required = true)
            @PathVariable String id
    ) {
        Material material = new Material();
        material.setSubjectMaterialId(id);
        List<Material> list = jwCourseService.findMaterialListByCondition(material);
        return  new ResponseJson(list);
    }


    @GetMapping("/findFiltrationJwCouserBySchoolId")
    public ResponseJson findFiltrationJwCouserBySchoolId(){
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.SUBJECT);
        dd.setSchoolId(mySchoolId());
        List<Dd> data = jwCourseService.findFiltrationJwCouserBySchoolId(dd);
        return new ResponseJson(data);
    }



}
