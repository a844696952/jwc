package com.yice.edu.cn.jy.controller.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.jy.service.subjectSource.ExamPointKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/examPointKnowledge")
@Api(value = "/examPointKnowledge",description = "考点和知识点的中间表模块")
public class ExamPointKnowledgeController {
    @Autowired
    private ExamPointKnowledgeService examPointKnowledgeService;

    @GetMapping("/findExamPointKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找考点和知识点的中间表", notes = "返回考点和知识点的中间表对象")
    public ExamPointKnowledge findExamPointKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return examPointKnowledgeService.findExamPointKnowledgeById(id);
    }

    @PostMapping("/saveExamPointKnowledge")
    @ApiOperation(value = "保存考点和知识点的中间表", notes = "返回考点和知识点的中间表对象")
    public ExamPointKnowledge saveExamPointKnowledge(
            @ApiParam(value = "考点和知识点的中间表对象", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
        examPointKnowledgeService.saveExamPointKnowledge(examPointKnowledge);
        return examPointKnowledge;
    }

    @PostMapping("/findExamPointKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找考点和知识点的中间表列表", notes = "返回考点和知识点的中间表列表")
    public List<ExamPointKnowledge> findExamPointKnowledgeListByCondition(
            @ApiParam(value = "考点和知识点的中间表对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        return examPointKnowledgeService.findExamPointKnowledgeListByCondition(examPointKnowledge);
    }
    @PostMapping("/findExamPointKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找考点和知识点的中间表列表个数", notes = "返回考点和知识点的中间表总个数")
    public long findExamPointKnowledgeCountByCondition(
            @ApiParam(value = "考点和知识点的中间表对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        return examPointKnowledgeService.findExamPointKnowledgeCountByCondition(examPointKnowledge);
    }

    @PostMapping("/updateExamPointKnowledge")
    @ApiOperation(value = "修改考点和知识点的中间表", notes = "考点和知识点的中间表对象必传")
    public void updateExamPointKnowledge(
            @ApiParam(value = "考点和知识点的中间表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
        examPointKnowledgeService.updateExamPointKnowledge(examPointKnowledge);
    }

    @GetMapping("/deleteExamPointKnowledge/{id}")
    @ApiOperation(value = "通过id删除考点和知识点的中间表")
    public void deleteExamPointKnowledge(
            @ApiParam(value = "考点和知识点的中间表对象", required = true)
            @PathVariable String id){
        examPointKnowledgeService.deleteExamPointKnowledge(id);
    }
    @PostMapping("/deleteExamPointKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除考点和知识点的中间表")
    public void deleteExamPointKnowledgeByCondition(
            @ApiParam(value = "考点和知识点的中间表对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        examPointKnowledgeService.deleteExamPointKnowledgeByCondition(examPointKnowledge);
    }
    @PostMapping("/findOneExamPointKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个考点和知识点的中间表,结果必须为单条数据", notes = "返回单个考点和知识点的中间表,没有时为空")
    public ExamPointKnowledge findOneExamPointKnowledgeByCondition(
            @ApiParam(value = "考点和知识点的中间表对象")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        return examPointKnowledgeService.findOneExamPointKnowledgeByCondition(examPointKnowledge);
    }
}
