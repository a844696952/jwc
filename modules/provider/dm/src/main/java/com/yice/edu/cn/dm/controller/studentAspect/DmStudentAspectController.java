package com.yice.edu.cn.dm.controller.studentAspect;

import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.dm.service.studentAspect.DmStudentAspectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmStudentAspect")
@Api(value = "/dmStudentAspect",description = "学生人脸特征模块")
public class DmStudentAspectController {
    @Autowired
    private DmStudentAspectService dmStudentAspectService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmStudentAspectById/{id}")
    @ApiOperation(value = "通过id查找学生人脸特征", notes = "返回学生人脸特征对象")
    public DmStudentAspect findDmStudentAspectById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmStudentAspectService.findDmStudentAspectById(id);
    }

    @PostMapping("/saveDmStudentAspect")
    @ApiOperation(value = "保存学生人脸特征", notes = "返回学生人脸特征对象")
    public DmStudentAspect saveDmStudentAspect(
            @ApiParam(value = "学生人脸特征对象", required = true)
            @RequestBody   @Validated(value = GroupTwo.class) DmStudentAspect dmStudentAspect){
        dmStudentAspectService.saveDmStudentAspect(dmStudentAspect);
        return dmStudentAspect;
    }

    @PostMapping("/batchSaveDmStudentAspect")
    @ApiOperation(value = "批量保存学生人脸特征")
    public void batchSaveDmStudentAspect(
            @ApiParam(value = "学生人脸特征对象集合", required = true)
            @RequestBody List<DmStudentAspect> dmStudentAspects){
        dmStudentAspectService.batchSaveDmStudentAspect(dmStudentAspects);
    }

    @PostMapping("/findDmStudentAspectListByCondition")
    @ApiOperation(value = "根据条件查找学生人脸特征列表", notes = "返回学生人脸特征列表")
    public List<DmStudentAspect> findDmStudentAspectListByCondition(
            @ApiParam(value = "学生人脸特征对象")
            @RequestBody DmStudentAspect dmStudentAspect){
        return dmStudentAspectService.findDmStudentAspectListByCondition(dmStudentAspect);
    }
    @PostMapping("/findDmStudentAspectCountByCondition")
    @ApiOperation(value = "根据条件查找学生人脸特征列表个数", notes = "返回学生人脸特征总个数")
    public long findDmStudentAspectCountByCondition(
            @ApiParam(value = "学生人脸特征对象")
            @RequestBody DmStudentAspect dmStudentAspect){
        return dmStudentAspectService.findDmStudentAspectCountByCondition(dmStudentAspect);
    }

    @PostMapping("/updateDmStudentAspect")
    @ApiOperation(value = "修改学生人脸特征有值的字段", notes = "学生人脸特征对象必传")
    public void updateDmStudentAspect(
            @ApiParam(value = "学生人脸特征对象,对象属性不为空则修改", required = true)
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspectService.updateDmStudentAspect(dmStudentAspect);
    }
    @PostMapping("/updateDmStudentAspectForAll")
    @ApiOperation(value = "修改学生人脸特征所有字段", notes = "学生人脸特征对象必传")
    public void updateDmStudentAspectForAll(
            @ApiParam(value = "学生人脸特征对象", required = true)
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspectService.updateDmStudentAspectForAll(dmStudentAspect);
    }

    @GetMapping("/deleteDmStudentAspect/{id}")
    @ApiOperation(value = "通过id删除学生人脸特征")
    public void deleteDmStudentAspect(
            @ApiParam(value = "学生人脸特征对象", required = true)
            @PathVariable String id){
        dmStudentAspectService.deleteDmStudentAspect(id);
    }
    @PostMapping("/deleteDmStudentAspectByCondition")
    @ApiOperation(value = "根据条件删除学生人脸特征")
    public void deleteDmStudentAspectByCondition(
            @ApiParam(value = "学生人脸特征对象")
            @RequestBody DmStudentAspect dmStudentAspect){
        dmStudentAspectService.deleteDmStudentAspectByCondition(dmStudentAspect);
    }
    @PostMapping("/findOneDmStudentAspectByCondition")
    @ApiOperation(value = "根据条件查找单个学生人脸特征,结果必须为单条数据", notes = "返回单个学生人脸特征,没有时为空")
    public DmStudentAspect findOneDmStudentAspectByCondition(
            @ApiParam(value = "学生人脸特征对象")
            @RequestBody DmStudentAspect dmStudentAspect){
        return dmStudentAspectService.findOneDmStudentAspectByCondition(dmStudentAspect);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
