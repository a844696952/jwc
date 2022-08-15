package com.yice.edu.cn.osp.controller.dd;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jy.questionItem.QuestionItemService;
import com.yice.edu.cn.osp.service.jy.subjectSource.KnowledgePointService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialItemService;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.osp.service.jy.subjectSource.SubjectMaterialService;
import com.yice.edu.cn.osp.service.jy.titleQuota.TopicQuotaResourcesService;
import io.swagger.annotations.ApiModelProperty;
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

/**
 * 公共查询条件
 * 这里放置一些公共条件查询的接口方便前端请求和做成组件
 * 类似题目知识点，章节等 基本固定的条件请求接口
 * 放置这的条件：
 * 1：处理查询条件接口其他不放
 * 2：这个条件复用率高，不是就一次两次
 * 3：这个条件不受自定义逻辑控制是固定的
 */
@RestController
@RequestMapping("/publicCondition")
public class PublicConditionQueryController {
    @Autowired
    private QuestionItemService questionItemService;
    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private TopicQuotaResourcesService quotaResourcesService;

    @GetMapping("/findAllPlatform4School")
    @ApiOperation(value = "获取学校所有可用平台，包含校本和我的")
    public ResponseJson findAllPlatform4School(){
        return new ResponseJson(quotaResourcesService.findAllPlatform4School(mySchoolId()));
    }
    @GetMapping("/findPlatform4School")
    @ApiOperation(value = "获取学校可用平台，不包含校本和我的")
    public ResponseJson findPlatform4School(){
        return new ResponseJson(quotaResourcesService.findPlatform4School(mySchoolId()));
    }

    @GetMapping("/findSubject4School")
    @ApiOperation(value = "查找当前学校题目相关的科目", notes = "返回响应对象",response = Dd.class)
    public ResponseJson findSubject4School() {
        return new ResponseJson(jwCourseService.findSchoolEaxmCourseList(mySchoolId()));
    }
    @GetMapping("/findQuestionItemListBySubject/{subjectId}")
    @ApiOperation(value = "按科目查找题目题型", notes = "返回题目题型", response= QuestionItem.class)
    public ResponseJson findQuestionItemListBySubject(
            @ApiParam(value = "属性不为空")
            @Validated
            @PathVariable String subjectId){
        QuestionItem questionItem = new QuestionItem();
        questionItem.setSubjectId(subjectId);
        questionItem.setSchoolSectionId(currentTeacher().getSchool().getTypeId());
        List<QuestionItem> data=questionItemService.findQuestionItemListByCondition(questionItem);
        return new ResponseJson(data);
    }
    @GetMapping("/findKnowledgePointsBySubject/{subjectId}")
    @ApiOperation(value = "按科目查找知识点", notes = "返回知识点树",response = KnowledgePoint.class)
    public ResponseJson findKnowledgePointsBySubject(
            @ApiParam(value = "属性不为空")
            @Validated
            @PathVariable String subjectId) {
        KnowledgePoint knowledgePoint = new KnowledgePoint();
        knowledgePoint.setSubjectId(subjectId);
        knowledgePoint.setPager(Optional.ofNullable(knowledgePoint.getPager()).orElse(new Pager()).setPaging(false).setIncludes("id","name","parentId"));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointTreeByCondition(knowledgePoint);
        return new ResponseJson(data);
    }
    @PostMapping("/findSubjectMaterialBySubject")
    @ApiOperation(value = "1、根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数",response = SubjectMaterial.class)
    public ResponseJson findSubjectMaterialBySubject(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceVo resourceVo){
        return subjectMaterialService.findSubjectMaterialBySubject(resourceVo);
    }
    @PostMapping("/findMaterialsByCondition")
    @ApiOperation(value = "2、根据条件查找教材列表", notes = "返回响应对象,不包含总条数",response = Material.class)
    public ResponseJson findMaterialsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @Validated
            @RequestBody ResourceVo resourceVo){
        return materialService.findMaterialsByCondition(resourceVo);
    }
    @PostMapping("/findChapterTree")
    @ApiOperation(value = "3、根据教材Id查找章节树", notes = "返回响应对象,不包含总条数",response = MaterialItem.class)
    public ResponseJson findChapterTree(
            @ApiParam(value = "教材id")
            @Validated
            @RequestBody ResourceVo resourceVo){
        return materialItemService.findChapterTree(resourceVo);
    }
    @GetMapping("/findTeacherAndSchoolRemain")
    @ApiOperation(value = "查询教师每日题目剩余额度")
    public Long findTeacherAndSchoolRemain(){
        return quotaResourcesService.findTeacherAndSchoolRemain();
    }
}
