package com.yice.edu.cn.jy.controller.analysisStudentScore;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.jy.service.analysisStudentScore.AnalysisStudentScoreAllService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysisStudentScoreAll")
@Api(value = "/analysisStudentScoreAll",description = "学生考试成绩-总成绩模块")
public class AnalysisStudentScoreAllController {
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;

    @GetMapping("/findAnalysisStudentScoreAllById/{id}")
    @ApiOperation(value = "通过id查找学生考试成绩-总成绩", notes = "返回学生考试成绩-总成绩对象")
    public AnalysisStudentScoreAll findAnalysisStudentScoreAllById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analysisStudentScoreAllService.findAnalysisStudentScoreAllById(id);
    }

    @PostMapping("/saveAnalysisStudentScoreAll")
    @ApiOperation(value = "保存学生考试成绩-总成绩", notes = "返回学生考试成绩-总成绩对象")
    public AnalysisStudentScoreAll saveAnalysisStudentScoreAll(
            @ApiParam(value = "学生考试成绩-总成绩对象", required = true)
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        analysisStudentScoreAllService.saveAnalysisStudentScoreAll(analysisStudentScoreAll);
        return analysisStudentScoreAll;
    }

    @PostMapping("/findAnalysisStudentScoreAllListByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-总成绩列表", notes = "返回学生考试成绩-总成绩列表")
    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(
            @ApiParam(value = "学生考试成绩-总成绩对象")
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        return analysisStudentScoreAllService.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
    }
    @PostMapping("/findAnalysisStudentScoreAllCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-总成绩列表个数", notes = "返回学生考试成绩-总成绩总个数")
    public long findAnalysisStudentScoreAllCountByCondition(
            @ApiParam(value = "学生考试成绩-总成绩对象")
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        return analysisStudentScoreAllService.findAnalysisStudentScoreAllCountByCondition(analysisStudentScoreAll);
    }

    @PostMapping("/updateAnalysisStudentScoreAll")
    @ApiOperation(value = "修改学生考试成绩-总成绩", notes = "学生考试成绩-总成绩对象必传")
    public void updateAnalysisStudentScoreAll(
            @ApiParam(value = "学生考试成绩-总成绩对象,对象属性不为空则修改", required = true)
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        analysisStudentScoreAllService.updateAnalysisStudentScoreAll(analysisStudentScoreAll);
    }

    @GetMapping("/deleteAnalysisStudentScoreAll/{id}")
    @ApiOperation(value = "通过id删除学生考试成绩-总成绩")
    public void deleteAnalysisStudentScoreAll(
            @ApiParam(value = "学生考试成绩-总成绩对象", required = true)
            @PathVariable String id){
        analysisStudentScoreAllService.deleteAnalysisStudentScoreAll(id);
    }
    @PostMapping("/deleteAnalysisStudentScoreAllByCondition")
    @ApiOperation(value = "根据条件删除学生考试成绩-总成绩")
    public void deleteAnalysisStudentScoreAllByCondition(
            @ApiParam(value = "学生考试成绩-总成绩对象")
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        analysisStudentScoreAllService.deleteAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }
    @PostMapping("/findOneAnalysisStudentScoreAllByCondition")
    @ApiOperation(value = "根据条件查找单个学生考试成绩-总成绩,结果必须为单条数据", notes = "返回单个学生考试成绩-总成绩,没有时为空")
    public AnalysisStudentScoreAll findOneAnalysisStudentScoreAllByCondition(
            @ApiParam(value = "学生考试成绩-总成绩对象")
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        return analysisStudentScoreAllService.findOneAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }
    @PostMapping("/findAnalysisStuScoreAllListByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩列表-总成绩", notes = "返回学生考试成绩列表-总成绩")
    public List<Map<String,Object>> findAnalysisStuScoreAllListByCondition(@RequestBody AnalysisVo analysisVo){
        return analysisStudentScoreAllService.findAnalysisStuScoreAllListByCondition(analysisVo);
    }
    @PostMapping("/findAnalysisStuScoreAllCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩数量-总成绩", notes = "返回学生考试成绩数量-总成绩")
    public long findAnalysisStuScoreAllCountByCondition(@RequestBody AnalysisVo analysisVo){
        return analysisStudentScoreAllService.findAnalysisStuScoreAllCountByCondition(analysisVo);
    }
    @PostMapping("/findSchoolExam4Student/{studentId}")
    @ApiOperation(value = "学生查找考试信息")
    public List<SchoolExam> findSchoolExam4Student(
            @PathVariable String studentId,@RequestBody SchoolExam schoolExam){
        return analysisStudentScoreAllService.findSchoolExam4Student(studentId,schoolExam);
    }
}
