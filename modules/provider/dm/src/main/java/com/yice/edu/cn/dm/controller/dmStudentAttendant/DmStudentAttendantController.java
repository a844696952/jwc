package com.yice.edu.cn.dm.controller.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import com.yice.edu.cn.dm.service.dmStudentAttendant.DmStudentAttendantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmStudentAttendant")
@Api(value = "/dmStudentAttendant",description = "值日生表模块")
public class DmStudentAttendantController {
    @Autowired
    private DmStudentAttendantService dmStudentAttendantService;

    @GetMapping("/findDmStudentAttendantById/{id}")
    @ApiOperation(value = "通过id查找值日生表", notes = "返回值日生表对象")
    public DmStudentAttendant findDmStudentAttendantById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmStudentAttendantService.findDmStudentAttendantById(id);
    }

    @PostMapping("/saveDmStudentAttendant")
    @ApiOperation(value = "保存值日生表", notes = "返回值日生表对象")
    public DmStudentAttendant saveDmStudentAttendant(
            @ApiParam(value = "值日生表对象", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.saveDmStudentAttendant(dmStudentAttendant);
        return dmStudentAttendant;
    }

    @PostMapping("/findDmStudentAttendantListByCondition")
    @ApiOperation(value = "根据条件查找值日生表列表", notes = "返回值日生表列表")
    public List<DmStudentAttendant> findDmStudentAttendantListByCondition(
            @ApiParam(value = "值日生表对象")
            @RequestBody DmStudentAttendant dmStudentAttendant){
        return dmStudentAttendantService.findDmStudentAttendantListByCondition(dmStudentAttendant);
    }
    @PostMapping("/findDmStudentAttendantCountByCondition")
    @ApiOperation(value = "根据条件查找值日生表列表个数", notes = "返回值日生表总个数")
    public long findDmStudentAttendantCountByCondition(
            @ApiParam(value = "值日生表对象")
            @RequestBody DmStudentAttendant dmStudentAttendant){
        return dmStudentAttendantService.findDmStudentAttendantCountByCondition(dmStudentAttendant);
    }

    @PostMapping("/updateDmStudentAttendant")
    @ApiOperation(value = "修改值日生表所有非@Transient注释的字段", notes = "值日生表对象必传")
    public void updateDmStudentAttendant(
            @ApiParam(value = "值日生表对象,对象属性不为空则修改", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.updateDmStudentAttendant(dmStudentAttendant);
    }
    @PostMapping("/updateDmStudentAttendantForNotNull")
    @ApiOperation(value = "修改值日生表有值的字段", notes = "值日生表对象必传")
    public void updateDmStudentAttendantForNotNull(
            @ApiParam(value = "值日生表对象,对象属性不为空则修改", required = true)
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.updateDmStudentAttendantForNotNull(dmStudentAttendant);
    }

    @GetMapping("/deleteDmStudentAttendant/{id}")
    @ApiOperation(value = "通过id删除值日生表")
    public void deleteDmStudentAttendant(
            @ApiParam(value = "值日生表对象", required = true)
            @PathVariable String id){
        dmStudentAttendantService.deleteDmStudentAttendant(id);
    }
    @PostMapping("/deleteDmStudentAttendantByCondition")
    @ApiOperation(value = "根据条件删除值日生表")
    public void deleteDmStudentAttendantByCondition(
            @ApiParam(value = "值日生表对象")
            @RequestBody DmStudentAttendant dmStudentAttendant){
        dmStudentAttendantService.deleteDmStudentAttendantByCondition(dmStudentAttendant);
    }
    @PostMapping("/findOneDmStudentAttendantByCondition")
    @ApiOperation(value = "根据条件查找单个值日生表,结果必须为单条数据", notes = "返回单个值日生表,没有时为空")
    public DmStudentAttendant findOneDmStudentAttendantByCondition(
            @ApiParam(value = "值日生表对象")
            @RequestBody DmStudentAttendant dmStudentAttendant){
        return dmStudentAttendantService.findOneDmStudentAttendantByCondition(dmStudentAttendant);
    }

    @PostMapping("/deleteDmStudentAttendantByClassIds")
    @ApiOperation(value = "根据班级id列表删除值日生表")
    public void deleteDmStudentAttendantByClassIds(
            @ApiParam(value = "电子班牌删除对象")
            @RequestBody DmDeleteData dmDeleteData){
        dmStudentAttendantService.deleteDmStudentAttendantByClassIds(dmDeleteData.getClassIdList());
    }
}
