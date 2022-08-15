package com.yice.edu.cn.jy.controller.analysisKnowledge;

import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.jy.service.analysisKnowledge.AnalyseStuKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseStuKnowledge")
@Api(value = "/analyseStuKnowledge",description = "学生知识点分析表模块")
public class AnalyseStuKnowledgeController {
    @Autowired
    private AnalyseStuKnowledgeService analyseStuKnowledgeService;

    @GetMapping("/findAnalyseStuKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找学生知识点分析表", notes = "返回学生知识点分析表对象")
    public AnalyseStuKnowledge findAnalyseStuKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseStuKnowledgeService.findAnalyseStuKnowledgeById(id);
    }

    @PostMapping("/saveAnalyseStuKnowledge")
    @ApiOperation(value = "保存学生知识点分析表", notes = "返回学生知识点分析表对象")
    public AnalyseStuKnowledge saveAnalyseStuKnowledge(
            @ApiParam(value = "学生知识点分析表对象", required = true)
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        analyseStuKnowledgeService.saveAnalyseStuKnowledge(analyseStuKnowledge);
        return analyseStuKnowledge;
    }

    @PostMapping("/findAnalyseStuKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找学生知识点分析表列表", notes = "返回学生知识点分析表列表")
    public List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(
            @ApiParam(value = "学生知识点分析表对象")
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        return analyseStuKnowledgeService.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
    }
    @PostMapping("/findAnalyseStuKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找学生知识点分析表列表个数", notes = "返回学生知识点分析表总个数")
    public long findAnalyseStuKnowledgeCountByCondition(
            @ApiParam(value = "学生知识点分析表对象")
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        return analyseStuKnowledgeService.findAnalyseStuKnowledgeCountByCondition(analyseStuKnowledge);
    }

    @PostMapping("/updateAnalyseStuKnowledge")
    @ApiOperation(value = "修改学生知识点分析表", notes = "学生知识点分析表对象必传")
    public void updateAnalyseStuKnowledge(
            @ApiParam(value = "学生知识点分析表对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        analyseStuKnowledgeService.updateAnalyseStuKnowledge(analyseStuKnowledge);
    }

    @GetMapping("/deleteAnalyseStuKnowledge/{id}")
    @ApiOperation(value = "通过id删除学生知识点分析表")
    public void deleteAnalyseStuKnowledge(
            @ApiParam(value = "学生知识点分析表对象", required = true)
            @PathVariable String id){
        analyseStuKnowledgeService.deleteAnalyseStuKnowledge(id);
    }
    @PostMapping("/deleteAnalyseStuKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除学生知识点分析表")
    public void deleteAnalyseStuKnowledgeByCondition(
            @ApiParam(value = "学生知识点分析表对象")
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        analyseStuKnowledgeService.deleteAnalyseStuKnowledgeByCondition(analyseStuKnowledge);
    }
    @PostMapping("/findOneAnalyseStuKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个学生知识点分析表,结果必须为单条数据", notes = "返回单个学生知识点分析表,没有时为空")
    public AnalyseStuKnowledge findOneAnalyseStuKnowledgeByCondition(
            @ApiParam(value = "学生知识点分析表对象")
            @RequestBody AnalyseStuKnowledge analyseStuKnowledge){
        return analyseStuKnowledgeService.findOneAnalyseStuKnowledgeByCondition(analyseStuKnowledge);
    }
}
