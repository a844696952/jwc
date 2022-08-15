package com.yice.edu.cn.osp.controller.dm.modeManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.osp.service.dm.modeManage.ExamModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/examMode")
@Api(value = "/examMode",description = "考试模式表模块")
public class ExamModeController {

    @Autowired
    private ExamModeService examModeService;




    @PostMapping("/saveExamMode")
    @ApiOperation(value = "保存考试模式表对象", notes = "返回保存好的考试模式表对象", response= ExamMode.class)
    public ResponseJson saveExamMode(
            @ApiParam(value = "考试模式表对象", required = true)
            @RequestBody ExamMode examMode){
        examMode.setSchoolId(mySchoolId());
        ExamMode s=examModeService.saveExamMode(examMode);
        if(Objects.isNull(s)){
            return new ResponseJson(false,"当前考试模式和其余的有冲突");
        }
        return new ResponseJson(s);
    }

    @GetMapping("/update/findExamModeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考试模式表", notes = "返回响应对象", response=ExamMode.class)
    public ResponseJson findExamModeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ExamMode examMode=examModeService.findExamModeById(id);
        return new ResponseJson(examMode);
    }


    @PostMapping("/update/updateExamMode")
    @ApiOperation(value = "修改考试模式表对象", notes = "返回响应对象")
    public ResponseJson updateExamMode(
            @ApiParam(value = "被修改的考试模式表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamMode examMode){
        ExamMode mode = examModeService.updateExamMode(examMode);
        if(Objects.isNull(mode)){
            return new ResponseJson(false,"当前考试模式和其余的有冲突");
        }
        return new ResponseJson();
    }



    @GetMapping("/look/lookExamModeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考试模式", notes = "返回响应对象", response=ExamMode.class)
    public ResponseJson lookExamModeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ExamMode examMode=examModeService.findExamModeById(id);
        return new ResponseJson(examMode);
    }

    @PostMapping("/findExamModesByCondition")
    @ApiOperation(value = "根据条件查找考试模式表", notes = "返回响应对象", response=ExamMode.class)
    public ResponseJson findExamModesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ExamMode examMode){
        examMode.setSchoolId(mySchoolId());
        List<ExamMode> data=examModeService.findExamModeListByCondition(examMode);
        long count=examModeService.findExamModeCountByCondition(examMode);
        return new ResponseJson(data,count);
    }

    @GetMapping("/deleteExamMode/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteExamMode(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        examModeService.deleteExamMode(id);
        return new ResponseJson();
    }

    @GetMapping("/update/deleteExamInfo/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteExamInfo(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        examModeService.deleteExamInfo(id);
        return new ResponseJson();
    }




    @PostMapping("/close/{id}")
    @ApiOperation(value = "根据id关闭", notes = "返回响应对象")
    public ResponseJson close(
            @ApiParam(value = "被关闭记录的id", required = true)
            @PathVariable String id){
        examModeService.close(id);
        return new ResponseJson();
    }



    @PostMapping("/findExamModeListByCondition")
    @ApiOperation(value = "根据条件查找考试模式表列表", notes = "返回响应对象,不包含总条数", response=ExamMode.class)
    public ResponseJson findExamModeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ExamMode ExamMode){
        ExamMode.setSchoolId(mySchoolId());
        List<ExamMode> data=examModeService.findExamModeListByCondition(ExamMode);
        return new ResponseJson(data);
    }

    @GetMapping("/exportExamModeById/{id}")
    @ApiOperation(value = "根据id导出Excel", notes = "返回响应对象")
    public void exportExamModeById(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id, HttpServletResponse response){
        Workbook workbook = examModeService.exportExamModeById(id);
        response.setHeader("Content-Disposition", "attachment;filename=Excel.xls");
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/exportExamMode/{id}")
    @ApiOperation(value = "根据id导出Excel", notes = "返回响应对象")
    public void exportExamMode(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id, HttpServletResponse response){
        Workbook workbook = examModeService.exportExamMode(id);
        response.setHeader("Content-Disposition", "attachment;filename=Excel.xls");
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/importExamModeById/{id}")
    @ApiOperation(value = "根据id导入Excel", notes = "返回响应对象")
    public ResponseJson importExamModeById(
            @ApiParam(value = "通过excel导入", required = true)
                    MultipartFile file, @PathVariable("id") String id){
        ExamMode excelInfo=examModeService.importExamModeById(file,id);
        if(null == excelInfo){
            return new ResponseJson(false,"模板有误");
        }else{
            return new ResponseJson(true,"导入成功");
        }
    }
}
