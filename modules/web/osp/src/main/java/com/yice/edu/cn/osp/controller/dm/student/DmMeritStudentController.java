package com.yice.edu.cn.osp.controller.dm.student;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.student.DmMeritStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmMeritStudent")
@Api(value = "/dmMeritStudent",description = "三好学生表模块")
public class DmMeritStudentController {
    @Autowired
    private DmMeritStudentService dmMeritStudentService;

    @PostMapping("/saveDmMeritStudentOrUpdate")
    @EccJpush(type = Extras.DM_ECC_MERIT_STUDENT,content = "新增或者编辑三好学生")
    @ApiOperation(value = "新增或者编辑三好学生表对象", notes = "返回保存好的三好学生表对象", response=DmMeritStudent.class)
    public ResponseJson saveDmMeritStudentOrUpdate(
            @ApiParam(value = "三好学生表对象", required = true)
            @RequestBody List<DmMeritStudent> dmMeritStudent){
        if(CollectionUtil.isEmpty(dmMeritStudent)){
            return new ResponseJson(false,"请设置学生");
        }
        dmMeritStudent.forEach(d ->d.setSchoolId(mySchoolId()));
        dmMeritStudentService.saveDmMeritStudentOrUpdate(dmMeritStudent);
        return new ResponseJson();
    }

    @GetMapping("/findDmMeritStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找三好学生表", notes = "返回响应对象", response=DmMeritStudent.class)
    public ResponseJson findDmMeritStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmMeritStudent dmMeritStudent=dmMeritStudentService.findDmMeritStudentById(id);
        return new ResponseJson(dmMeritStudent);
    }

    @PostMapping("/updateDmMeritStudent")
    @ApiOperation(value = "修改三好学生表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmMeritStudent(
            @ApiParam(value = "被修改的三好学生表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudentService.updateDmMeritStudent(dmMeritStudent);
        return new ResponseJson();
    }

    @PostMapping("/updateDmMeritStudentForAll")
    @ApiOperation(value = "修改三好学生表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmMeritStudentForAll(
            @ApiParam(value = "被修改的三好学生表对象", required = true)
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudentService.updateDmMeritStudentForAll(dmMeritStudent);
        return new ResponseJson();
    }


    @PostMapping("/findDmMeritStudentsByCondition")
    @ApiOperation(value = "根据条件查找三好学生表", notes = "返回响应对象", response=DmMeritStudent.class)
    public ResponseJson findDmMeritStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMeritStudent dmMeritStudent){
       dmMeritStudent.setSchoolId(mySchoolId());
        List<DmMeritStudent> data=dmMeritStudentService.findDmMeritStudentListByCondition(dmMeritStudent);
        long count=dmMeritStudentService.findDmMeritStudentCountByCondition(dmMeritStudent);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmMeritStudentByCondition")
    @ApiOperation(value = "根据条件查找单个三好学生表,结果必须为单条数据", notes = "没有时返回空", response=DmMeritStudent.class)
    public ResponseJson findOneDmMeritStudentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmMeritStudent dmMeritStudent){
        DmMeritStudent one=dmMeritStudentService.findOneDmMeritStudentByCondition(dmMeritStudent);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmMeritStudent/{id}")
    @EccJpush(type = Extras.DM_ECC_MERIT_STUDENT,content = "删除三好学生")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmMeritStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmMeritStudentService.deleteDmMeritStudent(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmMeritStudentListByCondition")
    @ApiOperation(value = "根据条件查找三好学生表列表", notes = "返回响应对象,不包含总条数", response=DmMeritStudent.class)
    public ResponseJson findDmMeritStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmMeritStudent dmMeritStudent){
       dmMeritStudent.setSchoolId(mySchoolId());
        List<DmMeritStudent> data=dmMeritStudentService.findDmMeritStudentListByCondition(dmMeritStudent);
        return new ResponseJson(data);
    }



    @GetMapping("/findAllJwClassesAndStudents")
    @ApiOperation(value = "查询当前学校所有班级里面的学生")
    public ResponseJson findAllJwClassesAndStudents() {
        List<AllStudent> jwClassesList = dmMeritStudentService.findAllJwClassesAndStudents(mySchoolId());
        return new ResponseJson(jwClassesList);
    }


    @GetMapping("/findAll")
    @ApiOperation(value = "查询数据库所有的三好学生数据")
    public ResponseJson findAll() {
        return new ResponseJson(dmMeritStudentService.findAll(mySchoolId()));
    }


}
