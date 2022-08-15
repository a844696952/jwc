package com.yice.edu.cn.ecc.controller.studentAspect;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import com.yice.edu.cn.ecc.service.studentAspect.DmStudentAspectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.currentDmClassCard;
import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmStudentAspect")
@Api(value = "/dmStudentAspect",description = "学生人脸特征模块")
public class DmStudentAspectController {
    @Autowired
    private DmStudentAspectService dmStudentAspectService;

    @PostMapping("/saveDmStudentAspect")
    @ApiOperation(value = "保存学生人脸特征对象", notes = "返回保存好的学生人脸特征对象", response=DmStudentAspect.class)
    public ResponseJson saveDmStudentAspect(
            @ApiParam(value = "学生人脸特征对象", required = true)
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspect.setClassId(currentDmClassCard().getClassId());
        dmStudentAspect.setSchoolId(mySchoolId());
        DmStudentAspect s=dmStudentAspectService.saveDmStudentAspect(dmStudentAspect);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmStudentAspect")
    @ApiOperation(value = "修改学生人脸特征对象", notes = "返回响应对象")
    public ResponseJson updateDmStudentAspect(
            @ApiParam(value = "被修改的学生人脸特征对象,对象属性不为空则修改", required = true)
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspectService.updateDmStudentAspect(dmStudentAspect);
        return new ResponseJson();
    }

    @GetMapping("/findDmStudentAspectById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生人脸特征", notes = "返回响应对象", response=DmStudentAspect.class)
    public ResponseJson findDmStudentAspectById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmStudentAspect dmStudentAspect=dmStudentAspectService.findDmStudentAspectById(id);
        return new ResponseJson(dmStudentAspect);
    }

    @GetMapping("/deleteDmStudentAspect/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmStudentAspect(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmStudentAspectService.deleteDmStudentAspect(id);
        return new ResponseJson();
    }


    @PostMapping("/deleteDmStudentAspectByCondition")
    @ApiOperation(value = "根据人脸对象删除", notes = "返回响应对象")
    public ResponseJson deleteDmStudentAspectByCondition(
            @ApiParam(value = "被删除人脸对象", required = true)
            @Validated
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspect.setSchoolId(mySchoolId());
        dmStudentAspectService.deleteDmStudentAspectByCondition(dmStudentAspect);
        return new ResponseJson();
    }



    @PostMapping("/findDmStudentAspectList")
    @ApiOperation(value = "根据条件查找学生人脸特征列表", notes = "返回响应对象,不包含总条数", response=DmStudentAspect.class)
    public ResponseJson findDmStudentAspectList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody List<DmStudentAspect> dmStudentAspect){
        dmStudentAspect.forEach(x->x.setSchoolId(mySchoolId()));
        List<DmStudentAspect> data=dmStudentAspectService.findDmStudentAspectList(dmStudentAspect);
        return new ResponseJson(data);
    }


    @PostMapping("/findOneDmStudentAspectByCondition")
    @ApiOperation(value = "根据条件查找单个学生人脸特征,结果必须为单条数据", notes = "没有时返回空", response=DmStudentAspect.class)
    public ResponseJson findOneDmStudentAspectByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspect.setSchoolId(mySchoolId());
        DmStudentAspect one=dmStudentAspectService.findOneDmStudentAspectByCondition(dmStudentAspect);
        return new ResponseJson(one);
    }


}
