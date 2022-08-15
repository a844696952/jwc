package com.yice.edu.cn.yed.controller.jy.topics;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.jy.knowledgePoint.KnowledgePointService;
import com.yice.edu.cn.yed.service.jy.questionItem.QuestionItemService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialItemService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.yed.service.jy.subjectSource.SubjectMaterialService;
import com.yice.edu.cn.yed.service.jy.topics.TopicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/topics")
@Api(value = "/topics",description = "题目表 mongodb 专用模块")
public class TopicsController {
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private QuestionItemService questionItemService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialItemService materialItemService;

    @PostMapping("/saveTopics")
    @ApiOperation(value = "保存题目表 mongodb 专用对象", notes = "返回响应对象")
    public ResponseJson saveTopics(
            @ApiParam(value = "题目表 mongodb 专用对象", required = true)
            @RequestBody Topics topics){
        //设置学校 和 当前人员
        topics.setCreateUser(myId());
        Topics s=topicsService.saveTopics(topics);
        return new ResponseJson(s);
    }

    @GetMapping("/ignore/findTopicsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找题目表 mongodb 专用", notes = "返回响应对象")
    public ResponseJson findTopicsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Topics topics=topicsService.findTopicsById(id);
        return new ResponseJson(topics);
    }

    @PostMapping("/update/updateTopics")
    @ApiOperation(value = "修改题目表 mongodb 专用对象", notes = "返回响应对象")
    public ResponseJson updateTopics(
            @ApiParam(value = "被修改的题目表 mongodb 专用对象,对象属性不为空则修改", required = true)
            @RequestBody Topics topics){
        topicsService.updateTopics(topics);
        return new ResponseJson();
    }

    @GetMapping("/look/lookTopicsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找题目表 mongodb 专用", notes = "返回响应对象")
    public ResponseJson lookTopicsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Topics topics=topicsService.findTopicsById(id);
        return new ResponseJson(topics);
    }

    @PostMapping("/findTopicssByCondition")
    @ApiOperation(value = "根据条件查找题目表 mongodb 专用", notes = "返回响应对象")
    public ResponseJson findTopicssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Topics topics){
        System.out.println(topics);
        topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
        List<Topics> data=topicsService.findTopicsListByCondition(topics);
        long count=topicsService.findTopicsCountByCondition(topics);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneTopicsByCondition")
    @ApiOperation(value = "根据条件查找单个题目表 mongodb 专用,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneTopicsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Topics topics){
        Topics one=topicsService.findOneTopicsByCondition(topics);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteTopics/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTopics(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        topicsService.deleteTopics(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteTopicsByCondition")
    @ApiOperation(value = "根据条件删除题目表 mongodb 专用", notes = "返回响应对象")
    public ResponseJson deleteTopicsByCondition(
            @ApiParam(value = "被删除的题目表 mongodb 专用对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Topics topics){
        topicsService.deleteTopicsByCondition(topics);
        return new ResponseJson();
    }


    @PostMapping("/findTopicsListByCondition4Muti")
    @ApiOperation(value = "根据条件查找题目列表（题目内容，题目类型等）", notes = "返回响应对象,不包含总条数")
    public ResponseJson findTopicsListByCondition4Muti(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Topics topics){
        topics.setPager(Optional.ofNullable(topics.getPager()).orElse(new Pager().setPaging(false)).setLike("content"));
        List<Topics> data=topicsService.findTopicsListByCondition4Muti(topics);
        Long count = topicsService.findTopicsCountByCondition4Muti(topics);
        return new ResponseJson(data,count);
    }
    @PostMapping("/ignore/findKnowledgePointsByCondition")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePoint.setPager(Optional.ofNullable(knowledgePoint.getPager()).orElse(new Pager()).setPaging(false).setIncludes("id","name","parentId"));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointTreeByCondition(knowledgePoint);
        return new ResponseJson(data);
    }
    @PostMapping("/ignore/findQuestionItemListByCondition")
    @ApiOperation(value = "根据条件查找题目类型列表", notes = "返回响应对象,不包含总条数", response= QuestionItem.class)
    public ResponseJson findQuestionItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QuestionItem questionItem){
        List<QuestionItem> data=questionItemService.findQuestionItemListByCondition(questionItem);
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/findSubjectMaterialListByCondition/{subjectId}")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable("subjectId") String subjectId){
        SubjectMaterial subjectMaterial = new SubjectMaterial();
        subjectMaterial.setDdId(subjectId);
        subjectMaterial = subjectMaterialService.findOneSubjectMaterialByCondition(subjectMaterial);
        if(subjectMaterial!=null){
            SubjectMaterial temp = new SubjectMaterial();
            temp.setParentId(subjectMaterial.getId());
            List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(temp);
            return new ResponseJson(data);
        }else{
            return new ResponseJson();
        }
    }
    @GetMapping("/ignore/findMaterialListByCondition/{subjectMaterialId}")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @PathVariable("subjectMaterialId") String subjectMaterialId){
        Material material = new Material();
        material.setSubjectMaterialId(subjectMaterialId);
        List<Material> data=materialService.findMaterialListByCondition(material);
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据教材Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable("materialId") String materialId){
        MaterialItem materialItem = new MaterialItem();
        materialItem.setMaterialId(materialId);
        materialItem.setPager(new Pager().setPaging(false).setSortField("sort").setSortOrder(Pager.DESC));
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        List<MaterialItem> treeData = ObjectKit.buildTree(data, "-1");
        return new ResponseJson(treeData);
    }
    @GetMapping("/ignore/findKnowledgePointListByItem/{materialItemId}")
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
}
