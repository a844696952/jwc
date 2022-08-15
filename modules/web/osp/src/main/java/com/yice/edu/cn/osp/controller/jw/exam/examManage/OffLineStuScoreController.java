package com.yice.edu.cn.osp.controller.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.SchoolExamService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.StuScoreService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/offlineStuScore")
@Api(value = "/offlineStuScore",description = "学生成绩模块")
public class OffLineStuScoreController {
    @Autowired
    private StuScoreService stuScoreService;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @PostMapping("/saveStuScore")
    @ApiOperation(value = "保存学生成绩对象", notes = "返回保存好的学生成绩对象", response= StuScore.class)
    public ResponseJson saveStuScore(
            @ApiParam(value = "学生成绩对象", required = true)
            @RequestBody StuScore stuScore){
        StuScore s=stuScoreService.saveStuScore(stuScore);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuScoreById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson findStuScoreById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuScore stuScore=stuScoreService.findStuScoreById(id);
        return new ResponseJson(stuScore);
    }

    @PostMapping("/update/updateStuScore")
    @ApiOperation(value = "修改学生成绩对象", notes = "返回响应对象")
    public ResponseJson updateStuScore(
            @ApiParam(value = "被修改的学生成绩对象,对象属性不为空则修改", required = true)
            @RequestBody StuScore stuScore){
        boolean flag=false;
        SchoolExam schoolExam= schoolExamService.findSchoolExamById(stuScore.getSchoolExamId());
        for (int i = 0; i <schoolExam.getCourses().size() ; i++) {
            if(schoolExam.getCourses().get(i).getId().equals(stuScore.getCourse().getId())){
                if(schoolExam.getCourses().get(i).getTotalScore()>=stuScore.getScore()){
                    flag=true;
                }
            }
        }
        if(flag){
            stuScore.setCourse(null);
            if(stuScore.getScore()==-1){
                stuScore.setMissing(true);
            }
            stuScoreService.updateStuScore(stuScore);
            return new ResponseJson("success","修改成功");
        }else {
            return new ResponseJson("false","分数不能大于总分");
        }
    }

    @GetMapping("/look/lookStuScoreById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson lookStuScoreById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuScore stuScore=stuScoreService.findStuScoreById(id);
        return new ResponseJson(stuScore);
    }

    @PostMapping("/findStuScoresByCondition")
    @ApiOperation(value = "根据条件查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson findStuScoresByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuScore stuScore){
        List<StuScore> data=stuScoreService.findStuScoreListByCondition1(stuScore);
        long count=stuScoreService.findStuScoreCountByCondition1(stuScore);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuScoreByCondition")
    @ApiOperation(value = "根据条件查找单个学生成绩,结果必须为单条数据", notes = "没有时返回空", response=StuScore.class)
    public ResponseJson findOneStuScoreByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuScore stuScore){
        StuScore one=stuScoreService.findOneStuScoreByCondition(stuScore);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuScore/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuScore(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuScoreService.deleteStuScore(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteStuScoreByschoolExamId/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuScoreByschoolExamId(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuScoreService.deleteStuScoreByschoolExamId(id);
        SchoolExam schoolExam=new SchoolExam();
        schoolExam.setId(id);
        schoolExam.setFinished(false);
        schoolExamService.updateSchoolExam(schoolExam);
        analysisStudentScoreService.analysisStudentScore(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生成绩列表", notes = "返回响应对象,不包含总条数", response=StuScore.class)
    public ResponseJson findStuScoreListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuScore stuScore){
        List<StuScore> data=stuScoreService.findStuScoreListByCondition(stuScore);

        return new ResponseJson(data);
    }
    @PostMapping("/update/exportStuScoreTemplate/{id}")
    public void exportWageTemplate(@ApiParam(value = "成绩信息对象") @PathVariable String id, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=stuscore.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = stuScoreService.exportStuScoreTemplate(id);
            workbook.write(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @PostMapping("/update/exportStuScore/{id}")
    public void exportStuScore(@ApiParam(value = "成绩信息对象") @PathVariable String id, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=stuscore.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = stuScoreService.exportStuScore(id);
            workbook.write(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @PostMapping("/update/uploadStuScore")
    public ResponseJson uploadStuScore(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        String id = request.getParameter("schoolExamId");
        return new ResponseJson(stuScoreService.uploadStuScore(file,id));
    }


 /*   @PostMapping("/findStudentByScoreSection")
    @ApiOperation(value = "根据条件查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson findStudentByScoreSection(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuScore stuScore){
        Pager pager=new Pager();
        stuScore.setPager(pager);
        stuScore.getPager().setPaging(false);
        List<StuScore> data=stuScoreService.findStudentByScoreSection(stuScore);
        return new ResponseJson(data);
    }*/
}
