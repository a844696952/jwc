package com.yice.edu.cn.jy.controller.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.jy.service.analysisKnowledge.AnalyseClassKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseClassKnowledge")
@Api(value = "/analyseClassKnowledge",description = "考试班级知识点分析模块")
public class AnalyseClassKnowledgeController {
    @Autowired
    private AnalyseClassKnowledgeService analyseClassKnowledgeService;

    @GetMapping("/findAnalyseClassKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找考试班级知识点分析", notes = "返回考试班级知识点分析对象")
    public AnalyseClassKnowledge findAnalyseClassKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseClassKnowledgeService.findAnalyseClassKnowledgeById(id);
    }

    @PostMapping("/saveAnalyseClassKnowledge")
    @ApiOperation(value = "保存考试班级知识点分析", notes = "返回考试班级知识点分析对象")
    public AnalyseClassKnowledge saveAnalyseClassKnowledge(
            @ApiParam(value = "考试班级知识点分析对象", required = true)
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        analyseClassKnowledgeService.saveAnalyseClassKnowledge(analyseClassKnowledge);
        return analyseClassKnowledge;
    }

    @PostMapping("/findAnalyseClassKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找考试班级知识点分析列表", notes = "返回考试班级知识点分析列表")
    public List<AnalyseClassKnowledge> findAnalyseClassKnowledgeListByCondition(
            @ApiParam(value = "考试班级知识点分析对象")
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        return analyseClassKnowledgeService.findAnalyseClassKnowledgeListByCondition(analyseClassKnowledge);
    }
    @PostMapping("/findAnalyseClassKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找考试班级知识点分析列表个数", notes = "返回考试班级知识点分析总个数")
    public long findAnalyseClassKnowledgeCountByCondition(
            @ApiParam(value = "考试班级知识点分析对象")
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        return analyseClassKnowledgeService.findAnalyseClassKnowledgeCountByCondition(analyseClassKnowledge);
    }

    @PostMapping("/updateAnalyseClassKnowledge")
    @ApiOperation(value = "修改考试班级知识点分析", notes = "考试班级知识点分析对象必传")
    public void updateAnalyseClassKnowledge(
            @ApiParam(value = "考试班级知识点分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        analyseClassKnowledgeService.updateAnalyseClassKnowledge(analyseClassKnowledge);
    }

    @GetMapping("/deleteAnalyseClassKnowledge/{id}")
    @ApiOperation(value = "通过id删除考试班级知识点分析")
    public void deleteAnalyseClassKnowledge(
            @ApiParam(value = "考试班级知识点分析对象", required = true)
            @PathVariable String id){
        analyseClassKnowledgeService.deleteAnalyseClassKnowledge(id);
    }
    @PostMapping("/deleteAnalyseClassKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除考试班级知识点分析")
    public void deleteAnalyseClassKnowledgeByCondition(
            @ApiParam(value = "考试班级知识点分析对象")
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        analyseClassKnowledgeService.deleteAnalyseClassKnowledgeByCondition(analyseClassKnowledge);
    }
    @PostMapping("/findOneAnalyseClassKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个考试班级知识点分析,结果必须为单条数据", notes = "返回单个考试班级知识点分析,没有时为空")
    public AnalyseClassKnowledge findOneAnalyseClassKnowledgeByCondition(
            @ApiParam(value = "考试班级知识点分析对象")
            @RequestBody AnalyseClassKnowledge analyseClassKnowledge){
        return analyseClassKnowledgeService.findOneAnalyseClassKnowledgeByCondition(analyseClassKnowledge);
    }
}
