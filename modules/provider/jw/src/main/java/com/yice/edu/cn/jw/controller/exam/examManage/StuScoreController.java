package com.yice.edu.cn.jw.controller.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.jw.service.exam.examManage.StuScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stuScore")
@Api(value = "/stuScore",description = "学生成绩模块")
public class StuScoreController {
    @Autowired
    private StuScoreService stuScoreService;

    @GetMapping("/findStuScoreById/{id}")
    @ApiOperation(value = "通过id查找学生成绩", notes = "返回学生成绩对象")
    public StuScore findStuScoreById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuScoreService.findStuScoreById(id);
    }

    @PostMapping("/saveStuScore")
    @ApiOperation(value = "保存学生成绩", notes = "返回学生成绩对象")
    public StuScore saveStuScore(
            @ApiParam(value = "学生成绩对象", required = true)
            @RequestBody StuScore stuScore){
        stuScoreService.saveStuScore(stuScore);
        return stuScore;
    }

    @PostMapping("/findStuScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生成绩列表", notes = "返回学生成绩列表")
    public List<StuScore> findStuScoreListByCondition(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        return stuScoreService.findStuScoreListByCondition(stuScore);
    }
    @PostMapping("/findStuScoreCountByCondition")
    @ApiOperation(value = "根据条件查找学生成绩列表个数", notes = "返回学生成绩总个数")
    public long findStuScoreCountByCondition(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        return stuScoreService.findStuScoreCountByCondition(stuScore);
    }

    @PostMapping("/updateStuScore")
    @ApiOperation(value = "修改学生成绩", notes = "学生成绩对象必传")
    public void updateStuScore(
            @ApiParam(value = "学生成绩对象,对象属性不为空则修改", required = true)
            @RequestBody StuScore stuScore){
        stuScoreService.updateStuScore(stuScore);
    }

    @GetMapping("/deleteStuScore/{id}")
    @ApiOperation(value = "通过id删除学生成绩")
    public void deleteStuScore(
            @ApiParam(value = "学生成绩对象", required = true)
            @PathVariable String id){
        stuScoreService.deleteStuScore(id);
    }
    @GetMapping("/deleteStuScoreByschoolExamId/{id}")
    @ApiOperation(value = "通过id删除学生成绩")
    public void deleteStuScoreByschoolExamId(
            @ApiParam(value = "学生成绩对象", required = true)
            @PathVariable String id){
        stuScoreService.deleteStuScoreByschoolExamId(id);
    }

    @PostMapping("/deleteStuScoreByCondition")
    @ApiOperation(value = "根据条件删除学生成绩")
    public void deleteStuScoreByCondition(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        stuScoreService.deleteStuScoreByCondition(stuScore);
    }
    @PostMapping("/findOneStuScoreByCondition")
    @ApiOperation(value = "根据条件查找单个学生成绩,结果必须为单条数据", notes = "返回单个学生成绩,没有时为空")
    public StuScore findOneStuScoreByCondition(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        return stuScoreService.findOneStuScoreByCondition(stuScore);
    }
    @PostMapping("/batchSaveStuScore")
    public Map<String,Object> batchSaveStuScore(@RequestBody List<StuScore> stuScoreList){
        return stuScoreService.batchSaveStuScore1(stuScoreList);
    }

    @GetMapping("/findStuScoreByschoolExamId/{id}")
    @ApiOperation(value = "通过id查找学生成绩", notes = "返回学生成绩对象")
    public List<Map<String,Object>> findStuScoreByschoolExamId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuScoreService.findStuScoreByschoolExamId(id);
    }
    @PostMapping("/findStuScoreListByCondition1")
    @ApiOperation(value = "根据条件查找学生成绩列表", notes = "返回学生成绩列表")
    public List<StuScore> findStuScoreListByCondition1(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        return stuScoreService.findStuScoreListByCondition1(stuScore);
    }
    @PostMapping("/findStuScoreCountByCondition1")
    @ApiOperation(value = "根据条件查找学生成绩列表个数", notes = "返回学生成绩总个数")
    public long findStuScoreCountByCondition1(
            @ApiParam(value = "学生成绩对象")
            @RequestBody StuScore stuScore){
        return stuScoreService.findStuScoreCountByCondition1(stuScore);
    }
    @PostMapping("/findStudentByScoreSection")
    @ApiOperation(value = "根据条件查找学生个数", notes = "返回学生总个数")
    public List<StuScore> findStudentByScoreSection(@ApiParam(value = "学生成绩对象") @RequestBody StuScore stuScore){
        return stuScoreService.findStudentByScoreSection(stuScore);
    }

    @PostMapping("/findStuScoresForDownload/{schoolExamId}")
    public List<StuScore> findStuScoresForDownload(@PathVariable("schoolExamId") String schoolExamId,@RequestBody List<String> courseIds){
        return stuScoreService.findStuScoresForDownload(schoolExamId, courseIds);
    }

}
