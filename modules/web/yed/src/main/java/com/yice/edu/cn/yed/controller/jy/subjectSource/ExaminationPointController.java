package com.yice.edu.cn.yed.controller.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.jy.knowledgePoint.KnowledgePointService;
import com.yice.edu.cn.yed.service.jy.subjectSource.ExamPointKnowledgeService;
import com.yice.edu.cn.yed.service.jy.subjectSource.ExaminationPointService;
import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/examinationPoint")
@Api(value = "/examinationPoint",description = "考点模块")
public class ExaminationPointController {
    @Autowired
    private ExaminationPointService examinationPointService;
    
    @Autowired
    private ExamPointKnowledgeService examPointKnowledgeService;
    
    @Autowired
    private KnowledgePointService knowledgePointService;

    @PostMapping("/saveExaminationPoint")
    @ApiOperation(value = "保存考点对象", notes = "返回响应对象")
    public ResponseJson saveExaminationPoint(
            @ApiParam(value = "考点对象{parentId:树节点的父id(顶级节点值为-1),name:名称 ,sort:排序,subjectId:科目id,level:树级别,path:父节点path}", required = true)
            @RequestBody ExaminationPoint examinationPoint){
    	if(examinationPoint.getParentId()==null) {
    		examinationPoint.setParentId("-1");
    	}
    	
    	ExaminationPoint queryExaminationPoint = new ExaminationPoint();
		queryExaminationPoint.setLevel(examinationPoint.getLevel());
		queryExaminationPoint.setParentId(examinationPoint.getParentId());
		queryExaminationPoint.setName(examinationPoint.getName());
		queryExaminationPoint.setSubjectId(examinationPoint.getSubjectId());
		long count = examinationPointService.findExaminationPointCountByCondition(queryExaminationPoint);
		//同一级考点的名称不能相同
		if(count>0) {
			return new ResponseJson(false, "该考点名称重复!");
		}
    	
    	examinationPoint.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
        ExaminationPoint s=examinationPointService.saveExaminationPoint(examinationPoint);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findExaminationPointById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考点", notes = "返回响应对象")
    public ResponseJson findExaminationPointById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ExaminationPoint examinationPoint=examinationPointService.findExaminationPointById(id);
        return new ResponseJson(examinationPoint);
    }

    @PostMapping("/update/updateExaminationPoint")
    @ApiOperation(value = "修改考点对象", notes = "返回响应对象")
    public ResponseJson updateExaminationPoint(
            @ApiParam(value = "被修改的考点对象,对象属性不为空则修改", required = true)
            @RequestBody ExaminationPoint examinationPoint){
        examinationPointService.updateExaminationPoint(examinationPoint);
        return new ResponseJson();
    }

    @GetMapping("/look/lookExaminationPointById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考点", notes = "返回响应对象")
    public ResponseJson lookExaminationPointById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ExaminationPoint examinationPoint=examinationPointService.findExaminationPointById(id);
        return new ResponseJson(examinationPoint);
    }

    @PostMapping("/findExaminationPointsByCondition")
    @ApiOperation(value = "根据条件查找考点", notes = "返回响应对象")
    public ResponseJson findExaminationPointsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ExaminationPoint examinationPoint){
        List<ExaminationPoint> data=examinationPointService.findExaminationPointListByCondition(examinationPoint);
        long count=examinationPointService.findExaminationPointCountByCondition(examinationPoint);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneExaminationPointByCondition")
    @ApiOperation(value = "根据条件查找单个考点,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneExaminationPointByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ExaminationPoint examinationPoint){
        ExaminationPoint one=examinationPointService.findOneExaminationPointByCondition(examinationPoint);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteExaminationPoint/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteExaminationPoint(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
    	//判断考点是否还有子节点
    	ExaminationPoint childrenModel = new ExaminationPoint();
    	childrenModel.setParentId(id);
    	long childreCount = examinationPointService.findExaminationPointCountByCondition(childrenModel);
        if(childreCount>0) {
        	return new ResponseJson(false,"请删除子考点!");
        }
    	//判断是否绑定了知识点
        ExamPointKnowledge examPointKnowledge = new ExamPointKnowledge();
        examPointKnowledge.setExamPointId(id);
        long count = examPointKnowledgeService.findExamPointKnowledgeCountByCondition(examPointKnowledge);
        if(count>0) {
        	return new ResponseJson(false,"请先移除关联的知识点!");
        }
        examinationPointService.deleteExaminationPoint(id);
        return new ResponseJson();
    }


    @PostMapping("/findExaminationPointListByCondition")
    @ApiOperation(value = "根据条件查找考点列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findExaminationPointListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ExaminationPoint examinationPoint){
        List<ExaminationPoint> data=examinationPointService.findExaminationPointListByCondition(examinationPoint);
        return new ResponseJson(data);
    }

    @GetMapping("/findExaminationPointTreeByGradeId/{subjectId}")
    @ApiOperation(value = "根据科目查找考点树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findExaminationPointTreeBySubjectId(
    		@ApiParam(value = "科目id")
    		@PathVariable String subjectId){
    	ExaminationPoint examinationPoint = new ExaminationPoint();
    	examinationPoint.setSubjectId(subjectId);
    	Pager pager = new Pager();
    	pager.setSortField("sort");
    	pager.setSortOrder("desc");
    	pager.setPaging(false);
    	examinationPoint.setPager(pager);
    	List<ExaminationPoint> data=examinationPointService.findExaminationPointListByCondition(examinationPoint);
    	List<ExaminationPoint> treeData = ObjectKit.buildTree(data, "-1");
    	
    	return new ResponseJson(treeData);
    }
    
    @PostMapping("/ignore/saveExamPointKnowledge")
    @ApiOperation(value = "保存考点和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson saveExamPointKnowledge(
            @ApiParam(value = "考点和知识点的关联对象{examPointId:考点id,knowledgePointId:知识点id,examPointPath:考点的path}", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
    	//判断是否已经关联过
    	long count = examPointKnowledgeService.findExamPointKnowledgeCountByCondition(examPointKnowledge);
    	if(count>0) {
    		return new ResponseJson(false,"该考点已经关联过知识点");
    	}
        ExamPointKnowledge s=examPointKnowledgeService.saveExamPointKnowledge(examPointKnowledge);
        return new ResponseJson(s);
    }
    
    @PostMapping("/ignore/deleteExamPointKnowledge")
    @ApiOperation(value = "根据id删除关联", notes = "返回响应对象")
    public ResponseJson deleteExamPointKnowledge(
            @ApiParam(value = "{knowledgePointId:知识点id,examPointId:考点id}", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
    	if(org.apache.commons.lang.StringUtils.isEmpty(examPointKnowledge.getKnowledgePointId()) || org.apache.commons.lang.StringUtils.isEmpty(examPointKnowledge.getKnowledgePointId())) {
    		new ResponseJson(false,"知识点id或者章节Id不能为空!");
    	}
        examPointKnowledgeService.deleteExamPointKnowledgeByCondition(examPointKnowledge);
        return new ResponseJson();
    }
    
    @PostMapping("/ignore/findKnowledgePointListByExamPoint")
    @ApiOperation(value = "根据考点条件查找关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByExamPoint(
            @ApiParam(value = "考点对象{examPointId:考点id}")
            @RequestBody ExamPointKnowledge examPointKnowledge){
         List<KnowledgePoint> data =  examinationPointService.findKnowledgePointListByExamPoint(examPointKnowledge);
         long count = examinationPointService.findKnowledgePointCountByExamPoint(examPointKnowledge);
         return new ResponseJson(data,count);
    }
    
    @PostMapping("/ignore/findKnowledgePointListByCondition4Like")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointListByCondition4Like(
            @ApiParam(value = "属性不为空则作为条件查询{name:知识点名称(可为空),typeId:年段id,subjectId:科目的ddId,page:page对象}")
            @RequestBody KnowledgePoint knowledgePoint){
        Pager pager = knowledgePoint.getPager()==null?new Pager().setPaging(false):knowledgePoint.getPager();
        knowledgePoint.setPager(pager.setLike("name"));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointListByCondition(knowledgePoint);
        long count = knowledgePointService.findKnowledgePointCountByCondition(knowledgePoint);
        return new ResponseJson(data,count);
    }

}
