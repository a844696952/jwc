package com.yice.edu.cn.jy.controller.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExaminationPoint;
import com.yice.edu.cn.jy.service.subjectSource.ExaminationPointService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/examinationPoint")
@Api(value = "/examinationPoint",description = "考点模块")
public class ExaminationPointController {
    @Autowired
    private ExaminationPointService examinationPointService;

    @GetMapping("/findExaminationPointById/{id}")
    @ApiOperation(value = "通过id查找考点", notes = "返回考点对象")
    public ExaminationPoint findExaminationPointById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return examinationPointService.findExaminationPointById(id);
    }

    @PostMapping("/saveExaminationPoint")
    @ApiOperation(value = "保存考点", notes = "返回考点对象")
    public ExaminationPoint saveExaminationPoint(
            @ApiParam(value = "考点对象", required = true)
            @RequestBody ExaminationPoint examinationPoint){
        examinationPointService.saveExaminationPoint(examinationPoint);
        return examinationPoint;
    }

    @PostMapping("/findExaminationPointListByCondition")
    @ApiOperation(value = "根据条件查找考点列表", notes = "返回考点列表")
    public List<ExaminationPoint> findExaminationPointListByCondition(
            @ApiParam(value = "考点对象")
            @RequestBody ExaminationPoint examinationPoint){
        return examinationPointService.findExaminationPointListByCondition(examinationPoint);
    }
    @PostMapping("/findExaminationPointCountByCondition")
    @ApiOperation(value = "根据条件查找考点列表个数", notes = "返回考点总个数")
    public long findExaminationPointCountByCondition(
            @ApiParam(value = "考点对象")
            @RequestBody ExaminationPoint examinationPoint){
        return examinationPointService.findExaminationPointCountByCondition(examinationPoint);
    }

    @PostMapping("/updateExaminationPoint")
    @ApiOperation(value = "修改考点", notes = "考点对象必传")
    public void updateExaminationPoint(
            @ApiParam(value = "考点对象,对象属性不为空则修改", required = true)
            @RequestBody ExaminationPoint examinationPoint){
        examinationPointService.updateExaminationPoint(examinationPoint);
    }

    @GetMapping("/deleteExaminationPoint/{id}")
    @ApiOperation(value = "通过id删除考点")
    public void deleteExaminationPoint(
            @ApiParam(value = "考点对象", required = true)
            @PathVariable String id){
        examinationPointService.deleteExaminationPoint(id);
    }
    @PostMapping("/deleteExaminationPointByCondition")
    @ApiOperation(value = "根据条件删除考点")
    public void deleteExaminationPointByCondition(
            @ApiParam(value = "考点对象")
            @RequestBody ExaminationPoint examinationPoint){
        examinationPointService.deleteExaminationPointByCondition(examinationPoint);
    }
    @PostMapping("/findOneExaminationPointByCondition")
    @ApiOperation(value = "根据条件查找单个考点,结果必须为单条数据", notes = "返回单个考点,没有时为空")
    public ExaminationPoint findOneExaminationPointByCondition(
            @ApiParam(value = "考点对象")
            @RequestBody ExaminationPoint examinationPoint){
        return examinationPointService.findOneExaminationPointByCondition(examinationPoint);
    }
    
    @PostMapping("/findKnowledgePointListByExamPoint")
    @ApiOperation(value = "根据考点条件查找关联的知识点列表", notes = "返回考点列表")
    public List<KnowledgePoint> findKnowledgePointListByExamPoint(
            @ApiParam(value = "考点对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        return examinationPointService.findKnowledgePointListByExamPoint(examPointKnowledge);
    }
    @PostMapping("/findKnowledgePointCountByExamPoint")
    @ApiOperation(value = "根据考点条件查找关联的知识点列表个数", notes = "返回考点总个数")
    public long findKnowledgePointCountByExamPoint(
            @ApiParam(value = "考点对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        return examinationPointService.findKnowledgePointCountByExamPoint(examPointKnowledge);
    }
}
