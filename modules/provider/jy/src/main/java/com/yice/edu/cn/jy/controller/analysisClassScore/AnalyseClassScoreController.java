package com.yice.edu.cn.jy.controller.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseClassScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseClassScore")
@Api(value = "/analyseClassScore",description = "班级全部科目考试分析模块")
public class AnalyseClassScoreController {
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;

    @GetMapping("/findAnalyseClassScoreById/{id}")
    @ApiOperation(value = "通过id查找班级全部科目考试分析", notes = "返回班级全部科目考试分析对象")
    public AnalyseClassScore findAnalyseClassScoreById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseClassScoreService.findAnalyseClassScoreById(id);
    }

    @PostMapping("/saveAnalyseClassScore")
    @ApiOperation(value = "保存班级全部科目考试分析", notes = "返回班级全部科目考试分析对象")
    public AnalyseClassScore saveAnalyseClassScore(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @RequestBody AnalyseClassScore analyseClassScore){
        analyseClassScoreService.saveAnalyseClassScore(analyseClassScore);
        return analyseClassScore;
    }

    @PostMapping("/findAnalyseClassScoreListByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表", notes = "返回班级全部科目考试分析列表")
    public List<AnalyseClassScore> findAnalyseClassScoreListByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScore analyseClassScore){
        return analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
    }
    @PostMapping("/findAnalyseClassScoreCountByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表个数", notes = "返回班级全部科目考试分析总个数")
    public long findAnalyseClassScoreCountByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScore analyseClassScore){
        return analyseClassScoreService.findAnalyseClassScoreCountByCondition(analyseClassScore);
    }

    @PostMapping("/updateAnalyseClassScore")
    @ApiOperation(value = "修改班级全部科目考试分析", notes = "班级全部科目考试分析对象必传")
    public void updateAnalyseClassScore(
            @ApiParam(value = "班级全部科目考试分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseClassScore analyseClassScore){
        analyseClassScoreService.updateAnalyseClassScore(analyseClassScore);
    }

    @GetMapping("/deleteAnalyseClassScore/{id}")
    @ApiOperation(value = "通过id删除班级全部科目考试分析")
    public void deleteAnalyseClassScore(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @PathVariable String id){
        analyseClassScoreService.deleteAnalyseClassScore(id);
    }
    @PostMapping("/deleteAnalyseClassScoreByCondition")
    @ApiOperation(value = "根据条件删除班级全部科目考试分析")
    public void deleteAnalyseClassScoreByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScore analyseClassScore){
        analyseClassScoreService.deleteAnalyseClassScoreByCondition(analyseClassScore);
    }
    @PostMapping("/findOneAnalyseClassScoreByCondition")
    @ApiOperation(value = "根据条件查找单个班级全部科目考试分析,结果必须为单条数据", notes = "返回单个班级全部科目考试分析,没有时为空")
    public AnalyseClassScore findOneAnalyseClassScoreByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScore analyseClassScore){
        return analyseClassScoreService.findOneAnalyseClassScoreByCondition(analyseClassScore);
    }
    
    @PostMapping("/saveTopClazzScoreAnalyse")
    @ApiOperation(value = "存档毕业班级和学生的学情数据", notes = "")
    public void saveTopClazzScoreAnalyse(
    		@RequestBody
    		List<String> clazzIdList){
        analyseClassScoreService.saveTopClazzScoreAnalyse(clazzIdList);
    }
    
}
