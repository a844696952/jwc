package com.yice.edu.cn.osp.controller.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.StuNotComeToSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuNotComeToSchool")
@Api(value = "/stuNotComeToSchool",description = "学生未到校模块")
public class StuNotComeToSchoolController {
    @Autowired
    private StuNotComeToSchoolService stuNotComeToSchoolService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @PostMapping("/saveStuNotComeToSchool")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= StuNotComeToSchool.class)
    public ResponseJson saveStuNotComeToSchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
       stuNotComeToSchool.setSchoolId(mySchoolId());
        StuNotComeToSchool s=stuNotComeToSchoolService.saveStuNotComeToSchool(stuNotComeToSchool);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuNotComeToSchoolById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=StuNotComeToSchool.class)
    public ResponseJson findStuNotComeToSchoolById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuNotComeToSchool stuNotComeToSchool=stuNotComeToSchoolService.findStuNotComeToSchoolById(id);
        return new ResponseJson(stuNotComeToSchool);
    }

    @PostMapping("/update/updateStuNotComeToSchool")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateStuNotComeToSchool(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        stuNotComeToSchoolService.updateStuNotComeToSchool(stuNotComeToSchool);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuNotComeToSchoolById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=StuNotComeToSchool.class)
    public ResponseJson lookStuNotComeToSchoolById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuNotComeToSchool stuNotComeToSchool=stuNotComeToSchoolService.findStuNotComeToSchoolById(id);
        return new ResponseJson(stuNotComeToSchool);
    }

    @PostMapping("/findStuNotComeToSchoolsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=StuNotComeToSchool.class)
    public ResponseJson findStuNotComeToSchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
         if (stuNotComeToSchool.getTimeZone()!=null&&stuNotComeToSchool.getTimeZone().length==2){
             String[] timeZone = stuNotComeToSchool.getTimeZone();
             timeZone[0] = timeZone[0]+" 00:00:00";
             timeZone[1] = timeZone[1]+" 23:59:59";
             stuNotComeToSchool.getPager().setRangeField("createTime");
             stuNotComeToSchool.getPager().setRangeArray(timeZone);
         }
        stuNotComeToSchool.setTimeZone(null);
        stuNotComeToSchool.getPager().setLike("studentName");
        stuNotComeToSchool.setSchoolId(mySchoolId());
        if (stuNotComeToSchool.getSchoolYearId()==null){
            CurSchoolYear curSchoolYear = new CurSchoolYear();
            curSchoolYearService.setSchoolYearTerm(curSchoolYear,mySchoolId());
            stuNotComeToSchool.setFromTo(curSchoolYear.getFromTo());
            stuNotComeToSchool.setSchoolYearId(curSchoolYear.getSchoolYearId());
            stuNotComeToSchool.setTerm(curSchoolYear.getTerm());
        }
        List<StuNotComeToSchool> data=stuNotComeToSchoolService.findStuNotComeToSchoolListByCondition(stuNotComeToSchool);
        long count=stuNotComeToSchoolService.findStuNotComeToSchoolCountByCondition(stuNotComeToSchool);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuNotComeToSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=StuNotComeToSchool.class)
    public ResponseJson findOneStuNotComeToSchoolByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        StuNotComeToSchool one=stuNotComeToSchoolService.findOneStuNotComeToSchoolByCondition(stuNotComeToSchool);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuNotComeToSchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuNotComeToSchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuNotComeToSchoolService.deleteStuNotComeToSchool(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuNotComeToSchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=StuNotComeToSchool.class)
    public ResponseJson findStuNotComeToSchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
       stuNotComeToSchool.setSchoolId(mySchoolId());
        List<StuNotComeToSchool> data=stuNotComeToSchoolService.findStuNotComeToSchoolListByCondition(stuNotComeToSchool);
        return new ResponseJson(data);
    }

    @PostMapping("/update/exportStuNotComeToSchool")
    public void exportKqDaily(@ApiParam(value = "日考勤信息对象") @RequestBody StuNotComeToSchool stuNotComeToSchool, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            if (stuNotComeToSchool.getTimeZone()!=null&&stuNotComeToSchool.getTimeZone().length==2){
                String[] timeZone = stuNotComeToSchool.getTimeZone();
                timeZone[0] = timeZone[0]+" 00:00:00";
                timeZone[1] = timeZone[1]+" 23:59:59";
                stuNotComeToSchool.getPager().setRangeField("createTime");
                stuNotComeToSchool.getPager().setRangeArray(timeZone);
            }
            stuNotComeToSchool.setTimeZone(null);
            stuNotComeToSchool.getPager().setLike("studentName");
            stuNotComeToSchool.setSchoolId(mySchoolId());
            if (stuNotComeToSchool.getSchoolYearId()==null){
                CurSchoolYear curSchoolYear = new CurSchoolYear();
                curSchoolYearService.setSchoolYearTerm(curSchoolYear,mySchoolId());
                stuNotComeToSchool.setFromTo(curSchoolYear.getFromTo());
                stuNotComeToSchool.setSchoolYearId(curSchoolYear.getSchoolYearId());
                stuNotComeToSchool.setTerm(curSchoolYear.getTerm());
            }
            stuNotComeToSchool.getPager().setPaging(false);
            Workbook workbook = stuNotComeToSchoolService.exportStuNotComeToSchool(stuNotComeToSchool);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/makeNotComeData")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson makeNotComeData(){
        stuNotComeToSchoolService.makeNotComeData();
        return new ResponseJson();
    }
}
