package com.yice.edu.cn.osp.controller.dm.honourRoll;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.osp.service.dm.honourRoll.DmHonourRollStudentService;
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

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmHonourRollStudent")
@Api(value = "/dmHonourRollStudent",description = "光荣榜，学生获得者模块")
public class DmHonourRollStudentController {
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;

    @PostMapping("/saveDmHonourRollStudent")
    @ApiOperation(value = "保存光荣榜，学生获得者对象", notes = "返回保存好的光荣榜，学生获得者对象", response=DmHonourRollStudent.class)
    public ResponseJson saveDmHonourRollStudent(
            @ApiParam(value = "光荣榜，学生获得者对象", required = true)
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        DmHonourRollStudent s=dmHonourRollStudentService.saveDmHonourRollStudent(dmHonourRollStudent);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmHonourRollStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找光荣榜，学生获得者", notes = "返回响应对象", response=DmHonourRollStudent.class)
    public ResponseJson findDmHonourRollStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRollStudent dmHonourRollStudent=dmHonourRollStudentService.findDmHonourRollStudentById(id);
        return new ResponseJson(dmHonourRollStudent);
    }

    @PostMapping("/update/updateDmHonourRollStudent")
    @ApiOperation(value = "修改光荣榜，学生获得者对象", notes = "返回响应对象")
    public ResponseJson updateDmHonourRollStudent(
            @ApiParam(value = "被修改的光荣榜，学生获得者对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        dmHonourRollStudentService.updateDmHonourRollStudent(dmHonourRollStudent);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmHonourRollStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找光荣榜，学生获得者", notes = "返回响应对象", response=DmHonourRollStudent.class)
    public ResponseJson lookDmHonourRollStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRollStudent dmHonourRollStudent=dmHonourRollStudentService.findDmHonourRollStudentById(id);
        return new ResponseJson(dmHonourRollStudent);
    }

    @PostMapping("/findDmHonourRollStudentsByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者", notes = "返回响应对象", response=DmHonourRollStudent.class)
    public ResponseJson findDmHonourRollStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        List<DmHonourRollStudent> data=dmHonourRollStudentService.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
        long count=dmHonourRollStudentService.findDmHonourRollStudentCountByCondition(dmHonourRollStudent);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmHonourRollStudentByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜，学生获得者,结果必须为单条数据", notes = "没有时返回空", response=DmHonourRollStudent.class)
    public ResponseJson findOneDmHonourRollStudentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        DmHonourRollStudent one=dmHonourRollStudentService.findOneDmHonourRollStudentByCondition(dmHonourRollStudent);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmHonourRollStudent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmHonourRollStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmHonourRollStudentService.deleteDmHonourRollStudent(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmHonourRollStudentListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者列表", notes = "返回响应对象,不包含总条数", response=DmHonourRollStudent.class)
    public ResponseJson findDmHonourRollStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        List<DmHonourRollStudent> data=dmHonourRollStudentService.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
        return new ResponseJson(data);
    }
    @PostMapping("/findDmHonourRollStudentsByConditions")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者", notes = "返回响应对象", response=DmHonourRollStudent.class)
    public ResponseJson findDmHonourRollStudentsByConditions(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        List<DmHonourRollStudent> data=dmHonourRollStudentService.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
        long count=dmHonourRollStudentService.findDmHonourRollStudentCountByConditions(dmHonourRollStudent);
        return new ResponseJson(data,count);
    }

    @PostMapping("/explorerHonourRollStudent")
    public void explorerHonourRollStudent(@ApiParam(value = "光荣榜汇总导出")
                                   @RequestBody DmHonourRollStudent dmHonourRollStudent, HttpServletResponse response) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=honourRoll.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = dmHonourRollStudentService.explorerHonourRoll(dmHonourRollStudent);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
