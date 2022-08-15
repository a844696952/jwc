package com.yice.edu.cn.osp.controller.xq.analysisClassScore;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamStuTopicTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/analyseExamStuTopicType")
@Api(value = "/analyseExamStuTopicType",description = "学生考试题型分析模块")
public class AnalyseExamStuTopicTypeController {
    @Autowired
    private AnalyseExamStuTopicTypeService analyseExamStuTopicTypeService;

    @PostMapping("/saveAnalyseExamStuTopicType")
    @ApiOperation(value = "保存学生考试题型分析对象", notes = "返回保存好的学生考试题型分析对象", response=AnalyseExamStuTopicType.class)
    public ResponseJson saveAnalyseExamStuTopicType(
            @ApiParam(value = "学生考试题型分析对象", required = true)
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        AnalyseExamStuTopicType s=analyseExamStuTopicTypeService.saveAnalyseExamStuTopicType(analyseExamStuTopicType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseExamStuTopicTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生考试题型分析", notes = "返回响应对象", response=AnalyseExamStuTopicType.class)
    public ResponseJson findAnalyseExamStuTopicTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamStuTopicType analyseExamStuTopicType=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeById(id);
        return new ResponseJson(analyseExamStuTopicType);
    }

    @PostMapping("/update/updateAnalyseExamStuTopicType")
    @ApiOperation(value = "修改学生考试题型分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseExamStuTopicType(
            @ApiParam(value = "被修改的学生考试题型分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        analyseExamStuTopicTypeService.updateAnalyseExamStuTopicType(analyseExamStuTopicType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseExamStuTopicTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生考试题型分析", notes = "返回响应对象", response=AnalyseExamStuTopicType.class)
    public ResponseJson lookAnalyseExamStuTopicTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamStuTopicType analyseExamStuTopicType=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeById(id);
        return new ResponseJson(analyseExamStuTopicType);
    }

    @PostMapping("/findAnalyseExamStuTopicTypesByCondition")
    @ApiOperation(value = "根据条件查找学生考试题型分析", notes = "返回响应对象", response=AnalyseExamStuTopicType.class)
    public ResponseJson findAnalyseExamStuTopicTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        Pager pager = analyseExamStuTopicType.getPager() == null ? new Pager().setPaging(false) : analyseExamStuTopicType.getPager();
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("typeId").setSortOrder(Pager.ASC);
        analyseExamStuTopicType.setPager(pager);
        List<AnalyseExamStuTopicType> data=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeListByCondition(analyseExamStuTopicType);
        long count=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeCountByCondition(analyseExamStuTopicType);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseExamStuTopicTypeByCondition")
    @ApiOperation(value = "根据条件查找单个学生考试题型分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseExamStuTopicType.class)
    public ResponseJson findOneAnalyseExamStuTopicTypeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        AnalyseExamStuTopicType one=analyseExamStuTopicTypeService.findOneAnalyseExamStuTopicTypeByCondition(analyseExamStuTopicType);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseExamStuTopicType/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseExamStuTopicType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseExamStuTopicTypeService.deleteAnalyseExamStuTopicType(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseExamStuTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找学生考试题型分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseExamStuTopicType.class)
    public ResponseJson findAnalyseExamStuTopicTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        List<AnalyseExamStuTopicType> data=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeListByCondition(analyseExamStuTopicType);
        return new ResponseJson(data);
    }



}
