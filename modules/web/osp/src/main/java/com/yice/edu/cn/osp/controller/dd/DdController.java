package com.yice.edu.cn.osp.controller.dd;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.TopicType;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.common.pojo.jy.topics.TopicClass;
import com.yice.edu.cn.common.pojo.jy.topics.TopicDifficult;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jy.questionItem.QuestionItemService;
import com.yice.edu.cn.osp.service.jy.subjectSource.KnowledgePointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dd")
@Api(value = "/dd",description = "数据字典系统维护字段模块")
public class DdController {
    @Autowired
    private DdService ddService;

    @Autowired
    private JwClassesService jwClassesService;

    @GetMapping("/findDdById/{id}")
    @ApiOperation(value = "通过id查找数据字典系统维护字段", notes = "返回响应对象")
    public ResponseJson findDdById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Dd dd=ddService.findDdById(id);
        return new ResponseJson(dd);
    }

    @PostMapping("/saveDd")
    @ApiOperation(value = "保存数据字典系统维护字段对象", notes = "返回响应对象")
    public ResponseJson saveDd(
            @ApiParam(value = "数据字典系统维护字段对象", required = true)
            @RequestBody Dd dd){
        Dd s=ddService.saveDd(dd);
        return new ResponseJson(s);
    }
    @PostMapping("/updateDd")
    @ApiOperation(value = "修改数据字典系统维护字段对象", notes = "返回响应对象")
    public ResponseJson updateDd(
            @ApiParam(value = "被修改的数据字典系统维护字段对象,对象属性不为空则修改", required = true)
            @RequestBody Dd dd){
        ddService.updateDd(dd);
        return new ResponseJson();
    }

    @PostMapping("/findDdsByCondition")
    @ApiOperation(value = "根据条件查找数据字典系统维护字段", notes = "返回响应对象")
    public ResponseJson findDdsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Dd dd){
        List<Dd> data=ddService.findDdListByCondition(dd);
        long count=ddService.findDdCountByCondition(dd);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteDd/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDd(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ddService.deleteDd(id);
        return new ResponseJson();
    }
    @GetMapping("/findNationality")
    @ApiOperation(value = "查找国籍", notes = "返回响应对象")
    public ResponseJson findNationality(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.NATIONALITY);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findSex")
    @ApiOperation(value = "查找性别", notes = "返回响应对象")
    public ResponseJson findSex(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SEX);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findPoliticalFace")
    @ApiOperation(value = "查找政治面貌", notes = "返回响应对象")
    public ResponseJson findPoliticalFace(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.POLITICAL_FACE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findGradesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
    public ResponseJson findGradesCurrentSchool(){
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    
    @GetMapping("/findCoursesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的课程", notes = "返回响应对象")
    public ResponseJson findCoursesCurrentSchool(){
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.SUBJECT);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    
    @GetMapping("/findHighestEdu")
    @ApiOperation(value = "查找最高学历", notes = "返回响应对象")
    public ResponseJson findHighestEdu(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.HIGHEST_EDU);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findTeacherStatus")
    @ApiOperation(value = "查找教师状态", notes = "返回响应对象")
    public ResponseJson findTeacherStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.TEACHER_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findStudentStatus")
    @ApiOperation(value = "查找学生状态", notes = "返回响应对象")
    public ResponseJson findStudentStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.STUDENT_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findDocumentType")
    @ApiOperation(value = "查找证件类型", notes = "返回响应对象")
    public ResponseJson findDocumentType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.DOCUMENT_TYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findTeacherPost")
    @ApiOperation(value = "查找教师职务", notes = "返回响应对象")
    public ResponseJson findTeacherPost(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.TEACHER_POST);
        dd.setPager(new Pager().setPaging(false).setSortField("sort"));
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @PostMapping("/findDdListByCondition")
    @ApiOperation(value = "根据条件查找字典数据")
    public ResponseJson findDdListByCondition(@RequestBody Dd dd){
        return new ResponseJson(ddService.findDdListByCondition(dd));
    }
    @GetMapping("/findMaritalStatus")
    @ApiOperation(value = "查询婚姻状况数据")
    public ResponseJson findMaritalStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.MARITALSTATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    
    @GetMapping("/findSchoolType")
    @ApiOperation(value = "查找学校类型", notes = "返回响应对象")
    public ResponseJson findSchoolType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findExaminationType")
    @ApiOperation(value = "查找考试类型")
    public ResponseJson findExaminationType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.EXAMINATIONTYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findTopicTypes")
    @ApiOperation(value = "查找题型",response = Map.class)
    public ResponseJson  findTopicTypes(){
        return new ResponseJson(TopicType.turnToListMap());
    }
    @GetMapping("/findTopicDifficult")
    @ApiOperation(value = "查找题目难度",response = Map.class)
    public ResponseJson  findTopicDifficult(){
        return new ResponseJson(TopicDifficult.turnToListMap());
    }
    @GetMapping("/findTopicClass")
    @ApiOperation(value = "查找题类",response = Map.class)
    public ResponseJson  findTopicClass(){
        return new ResponseJson(TopicClass.turnToListMap());
    }

    @GetMapping("/findExamTypes")
    @ApiOperation(value = "查找考试类型", notes = "返回响应对象")
    public ResponseJson findExamTypes(){
        Dd dd = new Dd();
        dd.setTypeId("30");
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findGradesAndClassCurrentBySchool")
    @ApiOperation(value = "查找当前学校包含的年级班级树结构", notes = "返回响应对象")
    public ResponseJson findGradesAndClassCurrentBySchool(){
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        data.forEach(s->{
            Dd d  = new Dd();
            d.setGradeId(s.getId());
            d.setSchoolId(mySchoolId());
            s.setChildren(ddService.findJwClassesList(d));
        });
        return new ResponseJson(data);
    }

    @GetMapping("/findSchoolSpace")
    @ApiOperation(value = "查询数据字典的场地",notes = "返回响应对象")
    public ResponseJson findSchoolSpace(){
        Dd dd = new Dd();
        dd.setTypeId("10");
        List<Dd> ddList = ddService.findDdListByCondition(dd);
        return new ResponseJson(ddList);
    }
    @GetMapping("/findSubject4KnowledgeByLevelId/{id}")
    @ApiOperation(value = "查找年段科目", notes = "返回响应对象")
    public ResponseJson findSubject4KnowledgeByLevelId(@PathVariable("id") String id){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SUBJECT);
        dd.setExt1("1");
        dd.setLevelType(id);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    
    @GetMapping("/findSchoolStatus")
    @ApiOperation(value = "查找学校状态", notes = "返回响应对象")
    public ResponseJson findSchoolStatus(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.SCHOOL_STATUS);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
}