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
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseClassScoreAllService;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseClassScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseClassScoreAll")
@Api(value = "/analyseClassScoreAll",description = "班级全部科目考试分析模块")
public class AnalyseClassScoreAllController {
    @Autowired
    private AnalyseClassScoreAllService analyseClassScoreAllService;
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;

    @GetMapping("/findAnalyseClassScoreAllById/{id}")
    @ApiOperation(value = "通过id查找班级全部科目考试分析", notes = "返回班级全部科目考试分析对象")
    public AnalyseClassScoreAll findAnalyseClassScoreAllById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseClassScoreAllService.findAnalyseClassScoreAllById(id);
    }

    @PostMapping("/saveAnalyseClassScoreAll")
    @ApiOperation(value = "保存班级全部科目考试分析", notes = "返回班级全部科目考试分析对象")
    public AnalyseClassScoreAll saveAnalyseClassScoreAll(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        analyseClassScoreAllService.saveAnalyseClassScoreAll(analyseClassScoreAll);
        return analyseClassScoreAll;
    }

    @PostMapping("/findAnalyseClassScoreAllListByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表", notes = "返回班级全部科目考试分析列表")
    public List<AnalyseClassScoreAll> findAnalyseClassScoreAllListByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        return analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
    }
    @PostMapping("/findAnalyseClassScoreAllCountByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表个数", notes = "返回班级全部科目考试分析总个数")
    public long findAnalyseClassScoreAllCountByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        return analyseClassScoreAllService.findAnalyseClassScoreAllCountByCondition(analyseClassScoreAll);
    }

    @PostMapping("/updateAnalyseClassScoreAll")
    @ApiOperation(value = "修改班级全部科目考试分析", notes = "班级全部科目考试分析对象必传")
    public void updateAnalyseClassScoreAll(
            @ApiParam(value = "班级全部科目考试分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        analyseClassScoreAllService.updateAnalyseClassScoreAll(analyseClassScoreAll);
    }

    @GetMapping("/deleteAnalyseClassScoreAll/{id}")
    @ApiOperation(value = "通过id删除班级全部科目考试分析")
    public void deleteAnalyseClassScoreAll(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @PathVariable String id){
        analyseClassScoreAllService.deleteAnalyseClassScoreAll(id);
    }
    @PostMapping("/deleteAnalyseClassScoreAllByCondition")
    @ApiOperation(value = "根据条件删除班级全部科目考试分析")
    public void deleteAnalyseClassScoreAllByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        analyseClassScoreAllService.deleteAnalyseClassScoreAllByCondition(analyseClassScoreAll);
    }
    @PostMapping("/findOneAnalyseClassScoreAllByCondition")
    @ApiOperation(value = "根据条件查找单个班级全部科目考试分析,结果必须为单条数据", notes = "返回单个班级全部科目考试分析,没有时为空")
    public AnalyseClassScoreAll findOneAnalyseClassScoreAllByCondition(
            @ApiParam(value = "班级全部科目考试分析对象")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        return analyseClassScoreAllService.findOneAnalyseClassScoreAllByCondition(analyseClassScoreAll);
    }
    
//    @GetMapping("/analyseClassScore/{examId}")
//    @ApiOperation(value = "开始分析班级成绩")
//    public void analyseClassScore(@PathVariable String examId) {
//    	analyseClassScoreService.analyseClassScoreGroupByCourse(examId);
//    };

}
