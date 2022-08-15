package com.yice.edu.cn.osp.controller.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.qusBankResouece.SchoolQusBankService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialItemService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.osp.service.jy.subjectSource.SubjectMaterialService;
import com.yice.edu.cn.osp.service.jy.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/schoolQusBank")
@Api(value = "/schoolQusBank", description = "模块")
public class SchoolQusBankController {
    @Autowired
    private SchoolQusBankService schoolQusBankService;
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private TopicsService topicsService;

    @PostMapping("/saveSchoolQusBank")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveSchoolQusBank(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBank.setSchoolId(mySchoolId());
        schoolQusBank.setCreateUser(myId());
        schoolQusBank.setCreateBy(Constant.TOPIC_CREATEBY.SCHOOLBASE);
        School school = schoolService.findSchoolById(mySchoolId());
        schoolQusBank.setAnnualPeriodId(school.getTypeId());
        schoolQusBank.setAnnualPeriodName(school.getTypeName());
        SchoolQusBank s = schoolQusBankService.saveSchoolQusBank(schoolQusBank);
        return new ResponseJson(s);
    }

    @GetMapping("/findSchoolQusBankById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findSchoolQusBankById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        SchoolQusBank schoolQusBank = schoolQusBankService.findSchoolQusBankById(id);
        return new ResponseJson(schoolQusBank);
    }

    @PostMapping("/update/updateSchoolQusBank")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolQusBank(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBankService.updateSchoolQusBank(schoolQusBank);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolQusBankById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookSchoolQusBankById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        SchoolQusBank schoolQusBank = schoolQusBankService.findSchoolQusBankById(id);
        return new ResponseJson(schoolQusBank);
    }

    @PostMapping("/findSchoolQusBanksByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findSchoolQusBanksByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolQusBank schoolQusBank) {
        //schoolQusBank.setContentText(StringUtils.specialCharacterConvert(schoolQusBank.getContentText()));
        schoolQusBank.getPager().setLike("content");
        schoolQusBank.getPager().setSortField("createTime");
        schoolQusBank.getPager().setSortOrder(Pager.DESC);
        schoolQusBank.setSchoolId(mySchoolId());
        schoolQusBank.setAnnualPeriodId(currentTeacher().getSchool().getTypeId());
        List<SchoolQusBank> data = schoolQusBankService.findSchoolQusBankListByCondition(schoolQusBank);
        long count = schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneSchoolQusBankByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSchoolQusBankByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBank.setSchoolId(mySchoolId());
        SchoolQusBank one = schoolQusBankService.findOneSchoolQusBankByCondition(schoolQusBank);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteSchoolQusBank/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolQusBank(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        schoolQusBankService.deleteSchoolQusBank(id);
        return new ResponseJson();
    }

    @PostMapping("/findSchoolQusBankListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolQusBankListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBank.setSchoolId(mySchoolId());
        schoolQusBank.setAnnualPeriodId(currentTeacher().getSchool().getTypeId());
        List<SchoolQusBank> data = schoolQusBankService.findSchoolQusBankListByCondition(schoolQusBank);
        return new ResponseJson(data);
    }

    @GetMapping("/findSubject4School")
    @ApiOperation(value = "查找当前学校的科目", notes = "返回响应对象")
    public ResponseJson findSubject4School() {
        return new ResponseJson(jwCourseService.findSchoolEaxmCourseList(mySchoolId()));
    }

    @PostMapping("/ignore/findSchoolQusNumByCreateTimeZone")
    @ApiOperation(value = "根据时间区间查找每日上传", notes = "返回响应对象")
    public ResponseJson findSchoolQusNumByCreateTimeZone(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolQusBank schoolQusBank) {
        schoolQusBank.setSchoolId(mySchoolId());
        schoolQusBank.setAnnualPeriodId(currentTeacher().getSchool().getTypeId());
        List<JySchoolResourcesByDay> schoolQusNumByCreateTimeZone = schoolQusBankService.findSchoolQusNumByCreateTimeZone(schoolQusBank);
        return new ResponseJson(schoolQusNumByCreateTimeZone);
    }
    //====================新的题目查询所需条件===================
    @GetMapping("/findSubjectMaterialListByCondition/{subjectId}")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable("subjectId") String subjectId){
        return subjectMaterialService.findSubjectMaterialBySubject(new ResourceVo().setTempId(subjectId).setPlatform(Constant.TOPIC_SOURCE.XBTK));
    }
    @GetMapping("/findMaterialListByCondition/{subjectMaterialId}")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @PathVariable("subjectMaterialId") String subjectMaterialId){
        return materialService.findMaterialsByCondition(new ResourceVo().setTempId(subjectMaterialId).setPlatform(Constant.TOPIC_SOURCE.XBTK));
    }
    @GetMapping("/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据教材Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable("materialId") String materialId){
        return materialItemService.findChapterTree(new ResourceVo().setTempId(materialId).setPlatform(Constant.TOPIC_SOURCE.XBTK));
    }
    @GetMapping("/findKnowledgePointListByItem/{materialItemId}")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象{materialItemId:章节id}")
            @PathVariable("materialItemId") String materialItemId){
        MaterialItemKnowledge materialItemKnowledge = new MaterialItemKnowledge();
        materialItemKnowledge.setPager(Optional.ofNullable(materialItemKnowledge.getPager()).orElse(new Pager()).setPaging(false));
        materialItemKnowledge.setMaterialItemId(materialItemId);
        List<KnowledgePoint> data = materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
        return new ResponseJson(data);
    }

    @GetMapping("/copyTopicToPersonalTopics/{id}")
    @ApiOperation(value = "校本题库移动到我的题库")
    public ResponseJson copyTopicToPersonalTopics(@PathVariable String id){
        return schoolQusBankService.copyTopicToPersonalTopics(id);
    }
    @PostMapping("/findTopicList")
    @ApiOperation(value = "多平台查询题目列表", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        return topicsService.findTopicList(searchParam);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "多平台查询题目详情", notes = "返回响应对象,含总条数",response = SchoolQusBank.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(topicsService.findTopicDetail(resourceVo));
    }
}
