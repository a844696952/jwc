package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.manage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.manage.ScanPersonService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/scanPerson")
@Api(value = "/scanPerson", description = "扫描员管理表模块")
public class ScanPersonController {
    @Autowired
    private ScanPersonService scanPersonService;

    @PostMapping("/saveScanPerson")
    @ApiOperation(value = "保存扫描员管理表对象", notes = "返回保存好的扫描员管理表对象", response = ScanPerson.class)
    public ResponseJson saveScanPerson(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody ScanPerson scanPerson) {
        scanPerson.setSchoolId(mySchoolId());
        ScanPerson s = scanPersonService.saveScanPerson(scanPerson);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findScanPersonById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找扫描员管理表", notes = "返回响应对象", response = ScanPerson.class)
    public ResponseJson findScanPersonById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        ScanPerson scanPerson = scanPersonService.findScanPersonById(id);
        return new ResponseJson(scanPerson);
    }

    @PostMapping("/update/updateScanPerson")
    @ApiOperation(value = "修改扫描员管理表对象", notes = "返回响应对象")
    public ResponseJson updateScanPerson(
            @ApiParam(value = "被修改的扫描员管理表对象,对象属性不为空则修改", required = true)
            @RequestBody ScanPerson scanPerson) {
        scanPersonService.updateScanPerson(scanPerson);
        return new ResponseJson();
    }

    @GetMapping("/look/lookScanPersonById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找扫描员管理表", notes = "返回响应对象", response = ScanPerson.class)
    public ResponseJson lookScanPersonById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        ScanPerson scanPerson = scanPersonService.findScanPersonById(id);
        return new ResponseJson(scanPerson);
    }

    @PostMapping("/findScanPersonsByCondition")
    @ApiOperation(value = "根据条件查找扫描员管理表", notes = "返回响应对象", response = ScanPerson.class)
    public ResponseJson findScanPersonsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ScanPerson scanPerson) {
        Pager pager = scanPerson.getPager();
        pager.setLike("teacherName");
        scanPerson.setPager(pager);
        scanPerson.setSchoolId(mySchoolId());

        List<ScanPerson> data = scanPersonService.findScanPersonListByCondition(scanPerson);
        long count = scanPersonService.findScanPersonCountByCondition(scanPerson);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneScanPersonByCondition")
    @ApiOperation(value = "根据条件查找单个扫描员管理表,结果必须为单条数据", notes = "没有时返回空", response = ScanPerson.class)
    public ResponseJson findOneScanPersonByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ScanPerson scanPerson) {
        ScanPerson one = scanPersonService.findOneScanPersonByCondition(scanPerson);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteScanPerson/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteScanPerson(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        scanPersonService.deleteScanPerson(id);
        return new ResponseJson();
    }


    @PostMapping("/findScanPersonListByCondition")
    @ApiOperation(value = "根据条件查找扫描员管理表列表", notes = "返回响应对象,不包含总条数", response = ScanPerson.class)
    public ResponseJson findScanPersonListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ScanPerson scanPerson) {
        scanPerson.setSchoolId(mySchoolId());
        List<ScanPerson> data = scanPersonService.findScanPersonListByCondition(scanPerson);
        return new ResponseJson(data);
    }

    @GetMapping("/updateScanPersons/{id}")
    @ApiOperation(value = "根据id修改扫描员id", notes = "返回响应对象")
    public ResponseJson updateScanPersons(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        scanPersonService.deleteScanPerson(id);
        return new ResponseJson(false);
    }


    @PostMapping("/find/findAllScanPerson")
    @ApiOperation(value = "从教师表查询所有本校教师", notes = "返回响应对象", response = ScanPerson.class)
    public ResponseJson findAllScanPerson(@ApiParam(value = "属性不为空则作为条件查询")
                                          @RequestBody ScanPerson scanPerson) {
        scanPerson.setSchoolId(mySchoolId());
        Pager pager = scanPerson.getPager();
        pager.setLike("teacherName");
        scanPerson.setPager(pager);
        List<ScanPerson> data = scanPersonService.findAllScanPerson(scanPerson);
        return new ResponseJson(data);
    }


    @PostMapping("/ignore/addScanPerson")
    @ApiOperation(value = "保存扫描员管理表对象", notes = "返回保存好的扫描员管理表对象", response = ScanPerson.class)
    public ResponseJson addScanPerson(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody ScanPerson scanPerson) {
        List<ScanPerson> data = scanPersonService.findScanPersonListByCondition(scanPerson);
        if (data.size() == 0) {
            scanPerson.setSchoolId(mySchoolId());
            ScanPerson s = scanPersonService.saveScanPerson(scanPerson);
            return new ResponseJson(s);
        } else {
            return new ResponseJson();
        }

    }


    @PostMapping("/ignore/addScanPersonList")
    @ApiOperation(value = "保存多条教师表对象", notes = "", response = ScanPerson.class)
    public ResponseJson addScanPersonList(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody ScanPerson scanPerson) {
        //批量插入学校id
        scanPerson.getTeachers().forEach(scanPerson1 -> scanPerson1.setSchoolId(mySchoolId()));

        //数据库查询 前台传过来(添加)的数据 是否与ScanPerson 表中 有相同数据  ids:记录相同的条数
        /*List<ScanPerson> ids = scanPersonService.findTeacherByIds(scanPerson);
        //用来存放于ScanPerson表去重后 的 教师
        List<ScanPerson> teachers = new ArrayList<>();
        //ScanPerson表中所有教师
        List<ScanPerson> l = scanPerson.getTeachers();
        for (int i = 0; i < l.size(); i++) {
            int k = 0;
            for (int j = 0; j < ids.size(); j++) {
                if (l.get(i).getTeacherId().equals(ids.get(j).getTeacherId())) {
                    k++;
                }
            }
            if (k == 0) {
                teachers.add(l.get(i));
            }
        }
        //如果 没有与ScanPerson 相同数据,全部添加进ScanPerson表
        if (ids.size() == 0) {*/

            List<ScanPerson> data = scanPersonService.batchSaveScanPerson(scanPerson.getTeachers());
            return new ResponseJson(data);
        }

        //多个添加  排除重复后 再添加
        /*if (teachers.size() > 0) {
            List<ScanPerson> data = scanPersonService.batchSaveScanPerson(teachers);
            return new ResponseJson(data, ids);
        }
        return new ResponseJson();*/
    //}


}
