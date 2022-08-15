package com.yice.edu.cn.dm.controller.teacher;

import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import com.yice.edu.cn.dm.service.teacher.DmFamousTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmFamousTeacher")
@Api(value = "/dmFamousTeacher",description = "名师风采表模块")
public class DmFamousTeacherController {
    @Autowired
    private DmFamousTeacherService dmFamousTeacherService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmFamousTeacherById/{id}")
    @ApiOperation(value = "通过id查找名师风采表", notes = "返回名师风采表对象")
    public DmFamousTeacher findDmFamousTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmFamousTeacherService.findDmFamousTeacherById(id);
    }

    @PostMapping("/saveDmFamousTeacher")
    @ApiOperation(value = "保存名师风采表", notes = "返回名师风采表对象")
    public DmFamousTeacher saveDmFamousTeacher(
            @ApiParam(value = "名师风采表对象", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacherService.saveDmFamousTeacher(dmFamousTeacher);
        return dmFamousTeacher;
    }

    @PostMapping("/batchSaveDmFamousTeacher")
    @ApiOperation(value = "批量保存名师风采表")
    public void batchSaveDmFamousTeacher(
            @ApiParam(value = "名师风采表对象集合", required = true)
            @RequestBody List<DmFamousTeacher> dmFamousTeachers){
        dmFamousTeacherService.batchSaveDmFamousTeacher(dmFamousTeachers);
    }

    @PostMapping("/findDmFamousTeacherListByCondition")
    @ApiOperation(value = "根据条件查找名师风采表列表", notes = "返回名师风采表列表")
    public List<DmFamousTeacher> findDmFamousTeacherListByCondition(
            @ApiParam(value = "名师风采表对象")
            @RequestBody DmFamousTeacher dmFamousTeacher){
        return dmFamousTeacherService.findDmFamousTeacherListByCondition(dmFamousTeacher);
    }
    @PostMapping("/findDmFamousTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找名师风采表列表个数", notes = "返回名师风采表总个数")
    public long findDmFamousTeacherCountByCondition(
            @ApiParam(value = "名师风采表对象")
            @RequestBody DmFamousTeacher dmFamousTeacher){
        return dmFamousTeacherService.findDmFamousTeacherCountByCondition(dmFamousTeacher);
    }

    @PostMapping("/updateDmFamousTeacher")
    @ApiOperation(value = "修改名师风采表有值的字段", notes = "名师风采表对象必传")
    public void updateDmFamousTeacher(
            @ApiParam(value = "名师风采表对象,对象属性不为空则修改", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacherService.updateDmFamousTeacher(dmFamousTeacher);
    }
    @PostMapping("/updateDmFamousTeacherForAll")
    @ApiOperation(value = "修改名师风采表所有字段", notes = "名师风采表对象必传")
    public void updateDmFamousTeacherForAll(
            @ApiParam(value = "名师风采表对象", required = true)
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacherService.updateDmFamousTeacherForAll(dmFamousTeacher);
    }

    @GetMapping("/deleteDmFamousTeacher/{id}")
    @ApiOperation(value = "通过id删除名师风采表")
    public void deleteDmFamousTeacher(
            @ApiParam(value = "名师风采表对象", required = true)
            @PathVariable String id){
        dmFamousTeacherService.deleteDmFamousTeacher(id);
    }
    @PostMapping("/deleteDmFamousTeacherByCondition")
    @ApiOperation(value = "根据条件删除名师风采表")
    public void deleteDmFamousTeacherByCondition(
            @ApiParam(value = "名师风采表对象")
            @RequestBody DmFamousTeacher dmFamousTeacher){
        dmFamousTeacherService.deleteDmFamousTeacherByCondition(dmFamousTeacher);
    }
    @PostMapping("/findOneDmFamousTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个名师风采表,结果必须为单条数据", notes = "返回单个名师风采表,没有时为空")
    public DmFamousTeacher findOneDmFamousTeacherByCondition(
            @ApiParam(value = "名师风采表对象")
            @RequestBody DmFamousTeacher dmFamousTeacher){
        return dmFamousTeacherService.findOneDmFamousTeacherByCondition(dmFamousTeacher);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
