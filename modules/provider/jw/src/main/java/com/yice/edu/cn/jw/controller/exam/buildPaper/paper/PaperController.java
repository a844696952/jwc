package com.yice.edu.cn.jw.controller.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testPaper")
@Api(value = "/testPaper",description = "模块")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @GetMapping("/findTestPaperById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Paper findTestPaperById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return paperService.findTestPaperById(id);
    }

    @PostMapping("/saveTestPaper")
    @ApiOperation(value = "保存", notes = "返回对象")
    public String[] saveTestPaper(
            @ApiParam(value = "对象", required = true)
            @RequestBody Paper paper){
        return paperService.saveTestPaper(paper);
    }

    @PostMapping("/findTestPaperListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Paper> findTestPaperListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Paper paper){
        return paperService.findTestPaperListByCondition(paper);
    }
    @PostMapping("/findTestPaperCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findTestPaperCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Paper paper){
        return paperService.findTestPaperCountByCondition(paper);
    }

    @PostMapping("/updateTestPaper")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateTestPaper(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Paper paper){
        paperService.updateTestPaper(paper);
    }

    @GetMapping("/deleteTestPaper/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteTestPaper(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        paperService.deleteTestPaper(id);
    }
    @PostMapping("/deleteTestPaperByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteTestPaperByCondition(
            @ApiParam(value = "对象")
            @RequestBody Paper paper){
        paperService.deleteTestPaperByCondition(paper);
    }
    @PostMapping("/findOneTestPaperByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Paper findOneTestPaperByCondition(
            @ApiParam(value = "对象")
            @RequestBody Paper paper){
        return paperService.findOneTestPaperByCondition(paper);
    }



    @PostMapping("/savePaperAndPaperQuestion")
    @ApiOperation(value = "添加试卷",notes = "返回添加后的Id")
    public  Paper savePaperAndPaperQuestion(
            @ApiParam(value = "需要添加的题目")
            @RequestBody PaperTopics paperTopics
            ){
        return paperService.savePaperAndPaperQuestion(paperTopics);
    }

    @PostMapping("/removePaperQuestionKong")
    @ApiOperation(value = "根据对象id删除",notes = "无返回")
    public void removePagerBasketKong(
            @ApiParam(value = "试题篮对象")
            @RequestBody PaperTopics paperTopics
    ){
        paperService.removePaperQuestionKong(paperTopics);
    }


    /**
     * 自定义方法
     */

     @PostMapping("/findTestPaperListByConditionKong")
     @ApiOperation(value = "查询列表，自定义",notes = "返回对象")
     public List<Paper> findTestPaperListByConditionKong(
             @ApiParam(value = "查询列表")
             @RequestBody Paper paper
     ){
         return paperService.findTestPaperListByConditionKong(paper);
     }

    @PostMapping("/findTestPaperCountByConditionKong")
    @ApiOperation(value = "查询列表总量，自定义",notes = "数量")
    public long findTestPaperCountByConditionKong(
            @ApiParam(value = "查询列表总量")
            @RequestBody Paper paper
    ){
        return paperService.findTestPaperCountByConditionKong(paper);
    }

    @PostMapping("/setPaperFlags/{flag}")
    public void setPaperFlags(
            @ApiParam(value = "修改试卷使用状况")
            @RequestBody SchoolExam schoolExam,
            @PathVariable Boolean flag
            ){
            paperService.setPaperFlags(schoolExam,flag);
        }

    @PostMapping("/findEveryDayPaper")
    public List<PaperDayMonth> findEveryDayPaper(@RequestBody Paper paper){
         return paperService.findEveryDayPaper(paper);
    }

    @GetMapping("/findBySchoolExamIdAndCouserId/{schoolExamId}/{courseId}")
    public List<PaperQuestion> findBySchoolExamIdAndCouserId(@PathVariable("schoolExamId")String schoolExamId,@PathVariable("courseId")String courseId){
         return paperService.findBySchoolExamIdAndCouserId(schoolExamId, courseId);
    }
}
