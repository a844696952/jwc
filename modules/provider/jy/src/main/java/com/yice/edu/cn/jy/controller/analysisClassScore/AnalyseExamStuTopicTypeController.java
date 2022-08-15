package com.yice.edu.cn.jy.controller.analysisClassScore;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamStuTopicTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyseExamStuTopicType")
@Api(value = "/analyseExamStuTopicType",description = "学生考试题型分析模块")
public class AnalyseExamStuTopicTypeController {
    @Autowired
    private AnalyseExamStuTopicTypeService analyseExamStuTopicTypeService;

    @GetMapping("/findAnalyseExamStuTopicTypeById/{id}")
    @ApiOperation(value = "通过id查找学生考试题型分析", notes = "返回学生考试题型分析对象")
    public AnalyseExamStuTopicType findAnalyseExamStuTopicTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeById(id);
    }

    @PostMapping("/saveAnalyseExamStuTopicType")
    @ApiOperation(value = "保存学生考试题型分析", notes = "返回学生考试题型分析对象")
    public AnalyseExamStuTopicType saveAnalyseExamStuTopicType(
            @ApiParam(value = "学生考试题型分析对象", required = true)
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        analyseExamStuTopicTypeService.saveAnalyseExamStuTopicType(analyseExamStuTopicType);
        return analyseExamStuTopicType;
    }

    @PostMapping("/findAnalyseExamStuTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找学生考试题型分析列表", notes = "返回学生考试题型分析列表")
    public List<AnalyseExamStuTopicType> findAnalyseExamStuTopicTypeListByCondition(
            @ApiParam(value = "学生考试题型分析对象")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        return analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeListByCondition(analyseExamStuTopicType);
    }
    @PostMapping("/findAnalyseExamStuTopicTypeCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试题型分析列表个数", notes = "返回学生考试题型分析总个数")
    public long findAnalyseExamStuTopicTypeCountByCondition(
            @ApiParam(value = "学生考试题型分析对象")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        return analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeCountByCondition(analyseExamStuTopicType);
    }

    @PostMapping("/updateAnalyseExamStuTopicType")
    @ApiOperation(value = "修改学生考试题型分析", notes = "学生考试题型分析对象必传")
    public void updateAnalyseExamStuTopicType(
            @ApiParam(value = "学生考试题型分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        analyseExamStuTopicTypeService.updateAnalyseExamStuTopicType(analyseExamStuTopicType);
    }

    @GetMapping("/deleteAnalyseExamStuTopicType/{id}")
    @ApiOperation(value = "通过id删除学生考试题型分析")
    public void deleteAnalyseExamStuTopicType(
            @ApiParam(value = "学生考试题型分析对象", required = true)
            @PathVariable String id){
        analyseExamStuTopicTypeService.deleteAnalyseExamStuTopicType(id);
    }
    @PostMapping("/deleteAnalyseExamStuTopicTypeByCondition")
    @ApiOperation(value = "根据条件删除学生考试题型分析")
    public void deleteAnalyseExamStuTopicTypeByCondition(
            @ApiParam(value = "学生考试题型分析对象")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        analyseExamStuTopicTypeService.deleteAnalyseExamStuTopicTypeByCondition(analyseExamStuTopicType);
    }
    @PostMapping("/findOneAnalyseExamStuTopicTypeByCondition")
    @ApiOperation(value = "根据条件查找单个学生考试题型分析,结果必须为单条数据", notes = "返回单个学生考试题型分析,没有时为空")
    public AnalyseExamStuTopicType findOneAnalyseExamStuTopicTypeByCondition(
            @ApiParam(value = "学生考试题型分析对象")
            @RequestBody AnalyseExamStuTopicType analyseExamStuTopicType){
        return analyseExamStuTopicTypeService.findOneAnalyseExamStuTopicTypeByCondition(analyseExamStuTopicType);
    }
}
