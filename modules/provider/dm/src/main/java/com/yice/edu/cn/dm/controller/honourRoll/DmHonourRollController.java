package com.yice.edu.cn.dm.controller.honourRoll;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.dm.service.honourRoll.DmHonourRollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmHonourRoll")
@Api(value = "/dmHonourRoll",description = "光荣榜，管理模块")
public class DmHonourRollController {
    @Autowired
    private DmHonourRollService dmHonourRollService;

    @GetMapping("/findDmHonourRollById/{id}")
    @ApiOperation(value = "通过id查找光荣榜，管理", notes = "返回光荣榜，管理对象")
    public DmHonourRoll findDmHonourRollById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmHonourRollService.findDmHonourRollById(id);
    }

    @PostMapping("/saveDmHonourRoll")
    @ApiOperation(value = "保存光荣榜，管理", notes = "返回光荣榜，管理对象")
    public DmHonourRoll saveDmHonourRoll(
            @ApiParam(value = "光荣榜，管理对象", required = true)
            @RequestBody DmHonourRoll dmHonourRoll){
        dmHonourRollService.saveDmHonourRoll(dmHonourRoll);
        return dmHonourRoll;
    }

    @PostMapping("/findDmHonourRollListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理列表", notes = "返回光荣榜，管理列表")
    public List<DmHonourRoll> findDmHonourRollListByCondition(
            @ApiParam(value = "光荣榜，管理对象")
            @RequestBody DmHonourRoll dmHonourRoll){
        return dmHonourRollService.findDmHonourRollListByCondition(dmHonourRoll);
    }
    @PostMapping("/findDmHonourRollCountByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理列表个数", notes = "返回光荣榜，管理总个数")
    public long findDmHonourRollCountByCondition(
            @ApiParam(value = "光荣榜，管理对象")
            @RequestBody DmHonourRoll dmHonourRoll){
        return dmHonourRollService.findDmHonourRollCountByCondition(dmHonourRoll);
    }

    @PostMapping("/updateDmHonourRoll")
    @ApiOperation(value = "修改光荣榜，管理", notes = "光荣榜，管理对象必传")
    public void updateDmHonourRoll(
            @ApiParam(value = "光荣榜，管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRoll dmHonourRoll){
        dmHonourRollService.updateDmHonourRoll(dmHonourRoll);
    }

    @GetMapping("/deleteDmHonourRoll/{id}")
    @ApiOperation(value = "通过id删除光荣榜，管理")
    public void deleteDmHonourRoll(
            @ApiParam(value = "光荣榜，管理对象", required = true)
            @PathVariable String id){
        dmHonourRollService.deleteDmHonourRoll(id);
    }
    @PostMapping("/deleteDmHonourRollByCondition")
    @ApiOperation(value = "根据条件删除光荣榜，管理")
    public void deleteDmHonourRollByCondition(
            @ApiParam(value = "光荣榜，管理对象")
            @RequestBody DmHonourRoll dmHonourRoll){
        dmHonourRollService.deleteDmHonourRollByCondition(dmHonourRoll);
    }
    @PostMapping("/findOneDmHonourRollByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜，管理,结果必须为单条数据", notes = "返回单个光荣榜，管理,没有时为空")
    public DmHonourRoll findOneDmHonourRollByCondition(
            @ApiParam(value = "光荣榜，管理对象")
            @RequestBody DmHonourRoll dmHonourRoll){
        return dmHonourRollService.findOneDmHonourRollByCondition(dmHonourRoll);
    }
    @PostMapping("/findDmHonourRoll")
    @ApiOperation(value = "根据条件查找光荣榜，管理列表个数", notes = "返回光荣榜，管理总个数")
    public long findDmHonourRoll(
            @ApiParam(value = "光荣榜，管理对象")
            @RequestBody DmHonourRoll dmHonourRoll){
        return dmHonourRollService.findDmHonourRoll(dmHonourRoll);
    }
    @GetMapping("/findDmHonourRollByStudentId/{id}")
    @ApiOperation(value = "通过studentId查找光荣榜，管理", notes = "返回光荣榜，管理对象")
    public DmHonourRoll findDmHonourRollByStudentId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmHonourRollService.findDmHonourRollByStudentId(id);
    }
    @GetMapping("/deleteDmHonourRollByClassId")
    @ApiOperation(value = "升班删除指定的班级内的数据", notes = "升班删除指定的班级内的数据")
    public void deleteDmHonourRollByClassId(
            @ApiParam(value = "升班删除指定的班级内的数据", required = true)
            @RequestBody DmDeleteData dmDeleteData){
        dmHonourRollService.deleteDmHonourRollByClassId(dmDeleteData);
    }

}
