package com.yice.edu.cn.osp.controller.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.qusBankResouece.PersonalTopicsService;
import com.yice.edu.cn.osp.service.jy.subjectSource.KnowledgePointService;
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

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/personalTopics")
@Api(value = "/personalTopics",description = "模块")
public class PersonalTopicsController {
    @Autowired
    private PersonalTopicsService personalTopicsService;
    @Autowired
    private DdService ddService;
    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private TopicsService topicsService;
    @PostMapping("/savePersonalTopics")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= PersonalTopics.class)
    public ResponseJson savePersonalTopics(
            @ApiParam(value = "对象", required = true)
            @RequestBody PersonalTopics personalTopics){
        personalTopics.setSchoolId(mySchoolId());
        personalTopics.setTeacherId(myId());
        personalTopics.setCreateUser(myId());
        PersonalTopics s=personalTopicsService.savePersonalTopics(personalTopics);
        return new ResponseJson(s);
    }

    @GetMapping("/find/findPersonalTopicsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=PersonalTopics.class)
    public ResponseJson findPersonalTopicsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PersonalTopics personalTopics=personalTopicsService.findPersonalTopicsById(id);
        return new ResponseJson(personalTopics);
    }

    @PostMapping("/update/updatePersonalTopics")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updatePersonalTopics(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PersonalTopics personalTopics){
        personalTopicsService.updatePersonalTopics(personalTopics);
        return new ResponseJson();
    }

    @GetMapping("/look/lookPersonalTopicsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=PersonalTopics.class)
    public ResponseJson lookPersonalTopicsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        PersonalTopics personalTopics=personalTopicsService.findPersonalTopicsById(id);
        return new ResponseJson(personalTopics);
    }

    @PostMapping("/find/findPersonalTopicssByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=PersonalTopics.class)
    public ResponseJson findPersonalTopicssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PersonalTopics personalTopics){
        personalTopics.setContentText(StringUtils.specialCharacterConvert(personalTopics.getContentText()));
        personalTopics.setSchoolId(mySchoolId());
        personalTopics.setTeacherId(myId());
        personalTopics.getPager().setLike("contentText");
        personalTopics.getPager().setSortField("createTime");
        personalTopics.getPager().setSortOrder(Pager.DESC);
        List<PersonalTopics> data=personalTopicsService.findPersonalTopicsListByCondition(personalTopics);
        long count=personalTopicsService.findPersonalTopicsCountByCondition(personalTopics);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOnePersonalTopicsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=PersonalTopics.class)
    public ResponseJson findOnePersonalTopicsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PersonalTopics personalTopics){
        PersonalTopics one=personalTopicsService.findOnePersonalTopicsByCondition(personalTopics);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePersonalTopics/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePersonalTopics(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        personalTopicsService.deletePersonalTopics(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findPersonalTopicsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=PersonalTopics.class)
    public ResponseJson findPersonalTopicsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PersonalTopics personalTopics){
       personalTopics.setSchoolId(mySchoolId());
        List<PersonalTopics> data=personalTopicsService.findPersonalTopicsListByCondition(personalTopics);
        return new ResponseJson(data);
    }

    /*-------*/
    @GetMapping("/ignore/findSubjectByLevelId")
    @ApiOperation(value = "查找年段科目", notes = "返回响应对象")
    public ResponseJson findSubjectByLevelId(){
        Dd dd = new Dd();
        School school = currentTeacher().getSchool();
        PersonalTopics personalTopics=new PersonalTopics();
        personalTopics.setAnnualPeriodId(school.getTypeId());
        personalTopics.setAnnualPeriodName(school.getTypeName());
        dd.setTypeId(Constant.DD_TYPE.SUBJECT);
        dd.setLevelType(school.getTypeId());
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data,personalTopics);
    }
    @PostMapping("/ignore/findKnowledgePointsByCondition")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KnowledgePoint knowledgePoint){
        Pager pager = knowledgePoint.getPager()==null?new Pager().setPaging(false):knowledgePoint.getPager();
        knowledgePoint.setPager(pager.setLike("name"));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointListByCondition(knowledgePoint);
        long count=knowledgePointService.findKnowledgePointCountByCondition(knowledgePoint);
        return new ResponseJson(data,count);
    }
    //====================新的题目查询所需条件===================
    @GetMapping("/findSubjectMaterialListByCondition/{subjectId}")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable("subjectId") String subjectId){
        return subjectMaterialService.findSubjectMaterialBySubject(new ResourceVo().setTempId(subjectId).setPlatform(Constant.TOPIC_SOURCE.WDTK));
    }
    @GetMapping("/findMaterialListByCondition/{subjectMaterialId}")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @PathVariable("subjectMaterialId") String subjectMaterialId){
        return materialService.findMaterialsByCondition(new ResourceVo().setTempId(subjectMaterialId).setPlatform(Constant.TOPIC_SOURCE.WDTK));
    }
    @GetMapping("/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据教材Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable("materialId") String materialId){
        return materialItemService.findChapterTree(new ResourceVo().setTempId(materialId).setPlatform(Constant.TOPIC_SOURCE.WDTK));
    }
    @GetMapping("/findKnowledgePointListByItem/{materialItemId}")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象{materialItemId:章节id}")
            @PathVariable("materialItemId") String materialItemId){
        MaterialItemKnowledge materialItemKnowledge = new MaterialItemKnowledge();
        materialItemKnowledge.setMaterialItemId(materialItemId);
        materialItemKnowledge.setPager(Optional.ofNullable(materialItemKnowledge.getPager()).orElse(new Pager()).setPaging(false));
        List<KnowledgePoint> data = materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
        return new ResponseJson(data);
    }
    @PostMapping("/findTopicList")
    @ApiOperation(value = "多平台查询题目列表", notes = "返回响应对象,含总条数",response = Topics.class)
    public ResponseJson findTopicList(@RequestBody SearchParam searchParam){
        return topicsService.findTopicList(searchParam);
    }
    @PostMapping("/findTopicDetail")
    @ApiOperation(value = "多平台查询题目详情", notes = "返回响应对象,含总条数",response = PersonalTopics.class)
    public ResponseJson findTopicDetail(@RequestBody ResourceVo resourceVo){
        return new ResponseJson(topicsService.findTopicDetail(resourceVo));
    }
}
