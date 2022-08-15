package com.yice.edu.cn.osp.controller.xq.analysisClassScore;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamTopicTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseExamTopicType")
@Api(value = "/analyseExamTopicType",description = "考试的题型分析模块")
public class AnalyseExamTopicTypeController {
    @Autowired
    private AnalyseExamTopicTypeService analyseExamTopicTypeService;

    @PostMapping("/saveAnalyseExamTopicType")
    @ApiOperation(value = "保存考试的题型分析对象", notes = "返回保存好的考试的题型分析对象", response=AnalyseExamTopicType.class)
    public ResponseJson saveAnalyseExamTopicType(
            @ApiParam(value = "考试的题型分析对象", required = true)
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        AnalyseExamTopicType s=analyseExamTopicTypeService.saveAnalyseExamTopicType(analyseExamTopicType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseExamTopicTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考试的题型分析", notes = "返回响应对象", response=AnalyseExamTopicType.class)
    public ResponseJson findAnalyseExamTopicTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopicType analyseExamTopicType=analyseExamTopicTypeService.findAnalyseExamTopicTypeById(id);
        return new ResponseJson(analyseExamTopicType);
    }

    @PostMapping("/update/updateAnalyseExamTopicType")
    @ApiOperation(value = "修改考试的题型分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseExamTopicType(
            @ApiParam(value = "被修改的考试的题型分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        analyseExamTopicTypeService.updateAnalyseExamTopicType(analyseExamTopicType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseExamTopicTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考试的题型分析", notes = "返回响应对象", response=AnalyseExamTopicType.class)
    public ResponseJson lookAnalyseExamTopicTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopicType analyseExamTopicType=analyseExamTopicTypeService.findAnalyseExamTopicTypeById(id);
        return new ResponseJson(analyseExamTopicType);
    }

    @PostMapping("/findAnalyseExamTopicTypesByCondition")
    @ApiOperation(value = "根据条件查找考试的题型分析", notes = "返回响应对象", response=AnalyseExamTopicType.class)
    public ResponseJson findAnalyseExamTopicTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        Pager pager = analyseExamTopicType.getPager() == null ? new Pager().setPaging(false) : analyseExamTopicType.getPager();
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("typeId").setSortOrder(Pager.ASC);
        analyseExamTopicType.setPager(pager);
        List<AnalyseExamTopicType> data=analyseExamTopicTypeService.findAnalyseExamTopicTypeListByCondition(analyseExamTopicType);
        long count=analyseExamTopicTypeService.findAnalyseExamTopicTypeCountByCondition(analyseExamTopicType);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseExamTopicTypeByCondition")
    @ApiOperation(value = "根据条件查找单个考试的题型分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseExamTopicType.class)
    public ResponseJson findOneAnalyseExamTopicTypeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        AnalyseExamTopicType one=analyseExamTopicTypeService.findOneAnalyseExamTopicTypeByCondition(analyseExamTopicType);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseExamTopicType/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseExamTopicType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseExamTopicTypeService.deleteAnalyseExamTopicType(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseExamTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找考试的题型分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseExamTopicType.class)
    public ResponseJson findAnalyseExamTopicTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        List<AnalyseExamTopicType> data=analyseExamTopicTypeService.findAnalyseExamTopicTypeListByCondition(analyseExamTopicType);
        return new ResponseJson(data);
    }

    @PostMapping("/analyseExamTopicTypes4Exam")
    @ApiOperation(value = "根据考试对象进行题型分析")
    public List<AnalyseExamTopicType> analyseExamTopicTypes4Exam(
            @ApiParam(value = "考试对象")
            @RequestBody SchoolExam schoolExam){
        return analyseExamTopicTypeService.analyseExamTopicTypes4Exam(schoolExam);
    }

}
