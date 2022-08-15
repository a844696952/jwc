package com.yice.edu.cn.jy.controller.analysisKnowledge;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import com.yice.edu.cn.jy.feignClient.examManage.SchoolExamFeign;
import com.yice.edu.cn.jy.service.analysisKnowledge.AnalyseGradeKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseGradeKnowledge")
@Api(value = "/analyseGradeKnowledge",description = "考试年级知识点分析模块")
public class AnalyseGradeKnowledgeController {
    @Autowired
    private AnalyseGradeKnowledgeService analyseGradeKnowledgeService;
    @Autowired
    private SchoolExamFeign schoolExamFeign;
    @GetMapping("/findAnalyseGradeKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找考试年级知识点分析", notes = "返回考试年级知识点分析对象")
    public AnalyseGradeKnowledge findAnalyseGradeKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseGradeKnowledgeService.findAnalyseGradeKnowledgeById(id);
    }

    @PostMapping("/saveAnalyseGradeKnowledge")
    @ApiOperation(value = "保存考试年级知识点分析", notes = "返回考试年级知识点分析对象")
    public AnalyseGradeKnowledge saveAnalyseGradeKnowledge(
            @ApiParam(value = "考试年级知识点分析对象", required = true)
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        analyseGradeKnowledgeService.saveAnalyseGradeKnowledge(analyseGradeKnowledge);
        return analyseGradeKnowledge;
    }

    @PostMapping("/findAnalyseGradeKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找考试年级知识点分析列表", notes = "返回考试年级知识点分析列表")
    public List<AnalyseGradeKnowledge> findAnalyseGradeKnowledgeListByCondition(
            @ApiParam(value = "考试年级知识点分析对象")
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        return analyseGradeKnowledgeService.findAnalyseGradeKnowledgeListByCondition(analyseGradeKnowledge);
    }
    @PostMapping("/findAnalyseGradeKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找考试年级知识点分析列表个数", notes = "返回考试年级知识点分析总个数")
    public long findAnalyseGradeKnowledgeCountByCondition(
            @ApiParam(value = "考试年级知识点分析对象")
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        return analyseGradeKnowledgeService.findAnalyseGradeKnowledgeCountByCondition(analyseGradeKnowledge);
    }

    @PostMapping("/updateAnalyseGradeKnowledge")
    @ApiOperation(value = "修改考试年级知识点分析", notes = "考试年级知识点分析对象必传")
    public void updateAnalyseGradeKnowledge(
            @ApiParam(value = "考试年级知识点分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        analyseGradeKnowledgeService.updateAnalyseGradeKnowledge(analyseGradeKnowledge);
    }

    @GetMapping("/deleteAnalyseGradeKnowledge/{id}")
    @ApiOperation(value = "通过id删除考试年级知识点分析")
    public void deleteAnalyseGradeKnowledge(
            @ApiParam(value = "考试年级知识点分析对象", required = true)
            @PathVariable String id){
        analyseGradeKnowledgeService.deleteAnalyseGradeKnowledge(id);
    }
    @PostMapping("/deleteAnalyseGradeKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除考试年级知识点分析")
    public void deleteAnalyseGradeKnowledgeByCondition(
            @ApiParam(value = "考试年级知识点分析对象")
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        analyseGradeKnowledgeService.deleteAnalyseGradeKnowledgeByCondition(analyseGradeKnowledge);
    }
    @PostMapping("/findOneAnalyseGradeKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个考试年级知识点分析,结果必须为单条数据", notes = "返回单个考试年级知识点分析,没有时为空")
    public AnalyseGradeKnowledge findOneAnalyseGradeKnowledgeByCondition(
            @ApiParam(value = "考试年级知识点分析对象")
            @RequestBody AnalyseGradeKnowledge analyseGradeKnowledge){
        return analyseGradeKnowledgeService.findOneAnalyseGradeKnowledgeByCondition(analyseGradeKnowledge);
    }
    @GetMapping("/analyGradeKnowledge4Exam/{id}")
    @ApiOperation(value = "通过id考试年级知识点分析")
    public void analyGradeKnowledge4Exam(
            @ApiParam(value = "考试年级知识点分析", required = true)
            @PathVariable String id){
        SchoolExam schoolExam = schoolExamFeign.findSchoolExamById(id);
        analyseGradeKnowledgeService.analyGradeKnowledge4Exam(schoolExam);
    }
}
