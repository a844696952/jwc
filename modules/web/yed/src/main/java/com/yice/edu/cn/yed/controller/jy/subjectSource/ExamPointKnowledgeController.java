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

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.yed.service.jy.subjectSource.ExamPointKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/examPointKnowledge")
@Api(value = "/examPointKnowledge",description = "考点和知识点的关联模块")
public class ExamPointKnowledgeController {
    @Autowired
    private ExamPointKnowledgeService examPointKnowledgeService;

    @PostMapping("/saveExamPointKnowledge")
    @ApiOperation(value = "保存考点和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson saveExamPointKnowledge(
            @ApiParam(value = "考点和知识点的关联对象{examPointId:考点id,knowledgePointId:知识点id,examPointPath:考点的path}", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
        ExamPointKnowledge s=examPointKnowledgeService.saveExamPointKnowledge(examPointKnowledge);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findExamPointKnowledgeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考点和知识点的中间表", notes = "返回响应对象")
    public ResponseJson findExamPointKnowledgeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ExamPointKnowledge examPointKnowledge=examPointKnowledgeService.findExamPointKnowledgeById(id);
        return new ResponseJson(examPointKnowledge);
    }

    @PostMapping("/update/updateExamPointKnowledge")
    @ApiOperation(value = "修改考点和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson updateExamPointKnowledge(
            @ApiParam(value = "被修改的考点和知识点的中间表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamPointKnowledge examPointKnowledge){
        examPointKnowledgeService.updateExamPointKnowledge(examPointKnowledge);
        return new ResponseJson();
    }

    @GetMapping("/look/lookExamPointKnowledgeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考点和知识点的中间表", notes = "返回响应对象")
    public ResponseJson lookExamPointKnowledgeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ExamPointKnowledge examPointKnowledge=examPointKnowledgeService.findExamPointKnowledgeById(id);
        return new ResponseJson(examPointKnowledge);
    }

    @PostMapping("/findExamPointKnowledgesByCondition")
    @ApiOperation(value = "根据条件查找考点和知识点的中间表", notes = "返回响应对象")
    public ResponseJson findExamPointKnowledgesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ExamPointKnowledge examPointKnowledge){
        List<ExamPointKnowledge> data=examPointKnowledgeService.findExamPointKnowledgeListByCondition(examPointKnowledge);
        long count=examPointKnowledgeService.findExamPointKnowledgeCountByCondition(examPointKnowledge);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneExamPointKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个考点和知识点的中间表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneExamPointKnowledgeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ExamPointKnowledge examPointKnowledge){
        ExamPointKnowledge one=examPointKnowledgeService.findOneExamPointKnowledgeByCondition(examPointKnowledge);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteExamPointKnowledge/{id}")
    @ApiOperation(value = "根据id删除关联", notes = "返回响应对象")
    public ResponseJson deleteExamPointKnowledge(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        examPointKnowledgeService.deleteExamPointKnowledge(id);
        return new ResponseJson();
    }


    @PostMapping("/findExamPointKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找考点和知识点的中间表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findExamPointKnowledgeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ExamPointKnowledge examPointKnowledge){
        List<ExamPointKnowledge> data=examPointKnowledgeService.findExamPointKnowledgeListByCondition(examPointKnowledge);
        return new ResponseJson(data);
    }



}
