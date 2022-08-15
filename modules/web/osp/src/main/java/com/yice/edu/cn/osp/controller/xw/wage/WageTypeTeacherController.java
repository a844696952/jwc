package com.yice.edu.cn.osp.controller.xw.wage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.wage.WageTypeService;
import com.yice.edu.cn.osp.service.xw.wage.WageTypeTeacherService;
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
import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/wageTypeTeacher")
@Api(value = "/wageTypeTeacher",description = "教师工资模块")
public class WageTypeTeacherController {
    @Autowired
    private WageTypeTeacherService wageTypeTeacherService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private WageTypeService wageTypeService;
    @PostMapping("/saveWageTypeTeacher")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveWageTypeTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
       wageTypeTeacher.setSchoolId(mySchoolId());
        WageTypeTeacher s=wageTypeTeacherService.saveWageTypeTeacher(wageTypeTeacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findWageTypeTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findWageTypeTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        WageTypeTeacher wageTypeTeacher=wageTypeTeacherService.findWageTypeTeacherById(id);
        return new ResponseJson(wageTypeTeacher);
    }

    @PostMapping("/update/updateWageTypeTeacher")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWageTypeTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.updateWageTypeTeacher(wageTypeTeacher);
        return new ResponseJson();
    }

    @GetMapping("/look/lookWageTypeTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookWageTypeTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        WageTypeTeacher wageTypeTeacher=wageTypeTeacherService.findWageTypeTeacherById(id);
        return new ResponseJson(wageTypeTeacher);
    }

    @PostMapping("/findWageTypeTeachersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findWageTypeTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WageTypeTeacher wageTypeTeacher){
       wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher> data=wageTypeTeacherService.findWageTypeTeacherListByCondition(wageTypeTeacher);
        long count=wageTypeTeacherService.findWageTypeTeacherCountByCondition(wageTypeTeacher);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneWageTypeTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneWageTypeTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        WageTypeTeacher one=wageTypeTeacherService.findOneWageTypeTeacherByCondition(wageTypeTeacher);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteWageTypeTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWageTypeTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        wageTypeTeacherService.deleteWageTypeTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findWageTypeTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWageTypeTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WageTypeTeacher wageTypeTeacher){
       wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher> data=wageTypeTeacherService.findWageTypeTeacherListByCondition(wageTypeTeacher);
        return new ResponseJson(data);
    }

    @PostMapping("/saveWageTypeValue")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveWageTypeValue(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        Teacher teacher=new Teacher();
        teacher.setName(wageTypeTeacher.getName());
        teacher.setWorkNumber(wageTypeTeacher.getWorkNumber());
        teacher.setSchoolId(mySchoolId());
        List<Teacher> teacherList = teacherService.findTeacherListByCondition(teacher);
        if(teacherList.size()==0){
            return new ResponseJson("false","该职工不存在");
        }
        wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher> wageTypeTeacherList = wageTypeTeacherService.findWageTypeTeacherListByWorkNum(wageTypeTeacher);
        if(wageTypeTeacherList.size()>0){
            return new ResponseJson("false","该职工信息已存在");
        }
        wageTypeTeacher.setTeacherId(teacherList.get(0).getId());
        wageTypeTeacher.setSchoolId(mySchoolId());
        wageTypeTeacherService.saveWageTypeValue(wageTypeTeacher);
        return new ResponseJson("success","保存成功");
    }

    @PostMapping("/update/findWageValueByTypeId")//传入TypeId显示属性列表
    @ApiOperation(value = "根据条件显示属性列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWageValueByTypeId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        if(wageTypeTeacher.getWageTypeId()==null){
            return new ResponseJson();
        }
        WageType wageType=new WageType();
        wageType.setId(wageTypeTeacher.getWageTypeId());
        wageType.setSchoolId(mySchoolId());
        List<WageType>wageTypeList=wageTypeService.findWageAttributeListByTypeId(wageType);
        Map<String,Object>wageTypeTeacherMap=new HashMap<>();
        wageTypeTeacherMap.put("id",wageTypeTeacher.getWageTypeId());
        wageTypeTeacherMap.put("pager",wageTypeTeacher.getPager());
        wageTypeTeacherMap.put("wageTypeList",wageTypeList);
        if(wageTypeTeacher.getName()!=""&&wageTypeTeacher.getName()!=null){
            wageTypeTeacherMap.put("name",wageTypeTeacher.getName());
        }
        if(wageTypeTeacher.getReleaseState()!=""&&wageTypeTeacher.getReleaseState()!=null){
            wageTypeTeacherMap.put("releaseState",wageTypeTeacher.getReleaseState());
        }

        if(wageTypeTeacher.getRangeTime()!=null){
            String startTime = DateUtil.format( DateUtil.beginOfDay(DateUtil.parse(wageTypeTeacher.getRangeTime()[0])), "yyyy-MM-dd HH:mm");
            String endTime = DateUtil.format(DateUtil.endOfDay(DateUtil.parse(wageTypeTeacher.getRangeTime()[1])), "yyyy-MM-dd HH:mm");
            wageTypeTeacherMap.put("startTime",startTime);
            wageTypeTeacherMap.put("endTime",endTime);
            wageTypeTeacher.setStartTime(startTime);
            wageTypeTeacher.setEndTime(endTime);
        }

        List<Map<String, Object>> data=wageTypeTeacherService.findWageValueByTypeId(wageTypeTeacherMap);
        long count=wageTypeTeacherService.findWageValueByTypeIdCount(wageTypeTeacher);
        return new ResponseJson(data,count);
    }

    @PostMapping("/update/findWageValueByRecordId/{id}")//传入RecordId显示属性列表
    @ApiOperation(value = "根据条件(RecordId)显示属性列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWageValueByRecordId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable String id){
        WageTypeTeacher wageTypeTeacher=new WageTypeTeacher();
        wageTypeTeacher.setId(id);
        wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher>WageTypeRecordList=wageTypeTeacherService.findWageAttributeListByRecordId(wageTypeTeacher);
        Map<String,Object>wageTypeRecordMap=new HashMap<>();
        wageTypeRecordMap.put("id",id);
        wageTypeRecordMap.put("WageTypeRecordList",WageTypeRecordList);
        List<Map<String, String>> data=wageTypeTeacherService.findWageValueByRecordId(wageTypeRecordMap);
        return new ResponseJson(data);
    }

    @PostMapping("/update/updateWageTypeValue")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson updateWageTypeValue(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        Teacher teacher=new Teacher();
        teacher.setName(wageTypeTeacher.getName());
        teacher.setWorkNumber(wageTypeTeacher.getWorkNumber());
        teacher.setSchoolId(mySchoolId());
        List<Teacher> teacherList = teacherService.findTeacherListByCondition(teacher);
        if(teacherList.size()==0){
            return new ResponseJson("false","该职工不存在");
        }
        wageTypeTeacher.setSchoolId(mySchoolId());
        wageTypeTeacherService.updateWageTypeValue(wageTypeTeacher);
        return new ResponseJson("success","修改成功");
    }

    @PostMapping("/update/exportWage")
    public void exportWage(@ApiParam(value = "工资信息对象") @RequestBody WageTypeTeacher wageTypeTeacher,HttpServletResponse response) {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            wageTypeTeacher.getPager().setSortOrder("desc");
            wageTypeTeacher.getPager().setSortField("createTime");
            wageTypeTeacher.getPager().setPaging(false);
            Workbook workbook = wageTypeTeacherService.exportWage(wageTypeTeacher);
            workbook.write(s);
        } catch (Exception e) {

        }
    }
    @PostMapping("/update/exportWageTemplate")
    public void exportWageTemplate(@ApiParam(value = "工资信息对象") @RequestBody WageTypeTeacher wageTypeTeacher,HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = wageTypeTeacherService.exportWageTemplate(wageTypeTeacher);
            workbook.write(s);
        } catch (Exception e) {

        }
    }


    @PostMapping("/update/uploadWage")
    public ResponseJson uploadWage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String id = request.getParameter("wageTypeId");
        return new ResponseJson(wageTypeTeacherService.uploadWage(file,id));
    }

    @GetMapping("/update/deleteWageValueByRecordId/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWageValueByRecordId(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        wageTypeTeacherService.deleteWageValueByRecordId(id);
        return new ResponseJson();
    }


    @PostMapping("/update/updateWageTypeTeacherReleaseState")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWageTypeTeacherReleaseState(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.updateWageTypeTeacherReleaseState(wageTypeTeacher);
        return new ResponseJson("success","发放成功");
    }

    @PostMapping("/FindMyPayslipByTeacherId") //我的工资条数据
    @ApiOperation(value = "查询对象", notes = "返回响应对象")
    public ResponseJson FindMyPayslipByTeacherId(
            @ApiParam(value = "被查询的对象,对象属性不为空则修改", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        List<Map<String, String>> data=new ArrayList<>();
        wageTypeTeacher.setTeacherId(myId());
        wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher>WageTypeTeacherList=wageTypeTeacherService.findWageAttributeListByTeacherId(wageTypeTeacher);
        if(WageTypeTeacherList.size()>0){
            Map<String,Object>wageTypeTeacherMap=new HashMap<>();
            wageTypeTeacherMap.put("id",myId());
            wageTypeTeacherMap.put("salaryTime",wageTypeTeacher.getSalaryTime());
            wageTypeTeacherMap.put("WageTypeTeacherList",WageTypeTeacherList);
            data=wageTypeTeacherService.findWageValueByTeacherId(wageTypeTeacherMap);
        }
        return new ResponseJson(data);
    }

    @PostMapping("/update/findWageAttributeNameByTeacherId") //我的工资条头属性
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public ResponseJson findWageAttributeNameByTeacherId(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacher.setTeacherId(myId());
        wageTypeTeacher.setSchoolId(mySchoolId());
        List<WageTypeTeacher> date = wageTypeTeacherService.findWageAttributeNameByTeacherId(wageTypeTeacher);
        return new ResponseJson(date);
    }

}
