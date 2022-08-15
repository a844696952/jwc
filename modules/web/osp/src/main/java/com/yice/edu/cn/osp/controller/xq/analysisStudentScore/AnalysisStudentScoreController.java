package com.yice.edu.cn.osp.controller.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/analysisStudentScore")
@Api(value = "/analysisStudentScore",description = "学生考试成绩-单科的模块")
public class AnalysisStudentScoreController {
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;

    /*============================================================================*/
    @GetMapping("/analysisStudentScore/{examinationId}")
    @ApiOperation(value = "按考试id生成学期分析数据")
    public ResponseJson analysisStudentScore(@PathVariable String examinationId){
        analysisStudentScoreService.analysisStudentScore(examinationId);
        return new ResponseJson();
    }
    @PostMapping("/findAnalysisStudentScores")
    @ApiOperation(value = "根据条件查找学生单科考试成绩", notes = "返回成绩列表", response=AnalysisStudentScore.class)
    public ResponseJson findAnalysisStudentScores(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalysisVo analysisVo){
        List<AnalysisStudentScore> data=analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo);
        return new ResponseJson(data);
    }

}
