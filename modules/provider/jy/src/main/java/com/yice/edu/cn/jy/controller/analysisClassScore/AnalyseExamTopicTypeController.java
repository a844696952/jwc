package com.yice.edu.cn.jy.controller.analysisClassScore;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseExamTopicType")
@Api(value = "/analyseExamTopicType",description = "考试的题型分析模块")
public class AnalyseExamTopicTypeController {
    @Autowired
    private AnalyseExamTopicTypeService analyseExamTopicTypeService;

    @GetMapping("/findAnalyseExamTopicTypeById/{id}")
    @ApiOperation(value = "通过id查找考试的题型分析", notes = "返回考试的题型分析对象")
    public AnalyseExamTopicType findAnalyseExamTopicTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseExamTopicTypeService.findAnalyseExamTopicTypeById(id);
    }

    @PostMapping("/saveAnalyseExamTopicType")
    @ApiOperation(value = "保存考试的题型分析", notes = "返回考试的题型分析对象")
    public AnalyseExamTopicType saveAnalyseExamTopicType(
            @ApiParam(value = "考试的题型分析对象", required = true)
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        analyseExamTopicTypeService.saveAnalyseExamTopicType(analyseExamTopicType);
        return analyseExamTopicType;
    }

    @PostMapping("/findAnalyseExamTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找考试的题型分析列表", notes = "返回考试的题型分析列表")
    public List<AnalyseExamTopicType> findAnalyseExamTopicTypeListByCondition(
            @ApiParam(value = "考试的题型分析对象")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        return analyseExamTopicTypeService.findAnalyseExamTopicTypeListByCondition(analyseExamTopicType);
    }
    @PostMapping("/findAnalyseExamTopicTypeCountByCondition")
    @ApiOperation(value = "根据条件查找考试的题型分析列表个数", notes = "返回考试的题型分析总个数")
    public long findAnalyseExamTopicTypeCountByCondition(
            @ApiParam(value = "考试的题型分析对象")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        return analyseExamTopicTypeService.findAnalyseExamTopicTypeCountByCondition(analyseExamTopicType);
    }

    @PostMapping("/updateAnalyseExamTopicType")
    @ApiOperation(value = "修改考试的题型分析", notes = "考试的题型分析对象必传")
    public void updateAnalyseExamTopicType(
            @ApiParam(value = "考试的题型分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        analyseExamTopicTypeService.updateAnalyseExamTopicType(analyseExamTopicType);
    }

    @GetMapping("/deleteAnalyseExamTopicType/{id}")
    @ApiOperation(value = "通过id删除考试的题型分析")
    public void deleteAnalyseExamTopicType(
            @ApiParam(value = "考试的题型分析对象", required = true)
            @PathVariable String id){
        analyseExamTopicTypeService.deleteAnalyseExamTopicType(id);
    }
    @PostMapping("/deleteAnalyseExamTopicTypeByCondition")
    @ApiOperation(value = "根据条件删除考试的题型分析")
    public void deleteAnalyseExamTopicTypeByCondition(
            @ApiParam(value = "考试的题型分析对象")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        analyseExamTopicTypeService.deleteAnalyseExamTopicTypeByCondition(analyseExamTopicType);
    }
    @PostMapping("/findOneAnalyseExamTopicTypeByCondition")
    @ApiOperation(value = "根据条件查找单个考试的题型分析,结果必须为单条数据", notes = "返回单个考试的题型分析,没有时为空")
    public AnalyseExamTopicType findOneAnalyseExamTopicTypeByCondition(
            @ApiParam(value = "考试的题型分析对象")
            @RequestBody AnalyseExamTopicType analyseExamTopicType){
        return analyseExamTopicTypeService.findOneAnalyseExamTopicTypeByCondition(analyseExamTopicType);
    }

    @PostMapping("/analyseExamTopicTypes4Exam")
    @ApiOperation(value = "根据考试对象进行题型分析")
    public List<AnalyseExamTopicType> analyseExamTopicTypes4Exam(
            @ApiParam(value = "考试对象")
            @RequestBody SchoolExam schoolExam){
        return analyseExamTopicTypeService.analyseExamTopicTypes4Exam(schoolExam);
    }
}
