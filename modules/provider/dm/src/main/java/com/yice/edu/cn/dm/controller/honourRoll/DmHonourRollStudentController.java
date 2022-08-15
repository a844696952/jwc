package com.yice.edu.cn.dm.controller.honourRoll;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import com.yice.edu.cn.dm.service.honourRoll.DmHonourRollStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmHonourRollStudent")
@Api(value = "/dmHonourRollStudent",description = "光荣榜，学生获得者模块")
public class DmHonourRollStudentController {
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;

    @GetMapping("/findDmHonourRollStudentById/{id}")
    @ApiOperation(value = "通过id查找光荣榜，学生获得者", notes = "返回光荣榜，学生获得者对象")
    public DmHonourRollStudent findDmHonourRollStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmHonourRollStudentService.findDmHonourRollStudentById(id);
    }

    @PostMapping("/saveDmHonourRollStudent")
    @ApiOperation(value = "保存光荣榜，学生获得者", notes = "返回光荣榜，学生获得者对象")
    public DmHonourRollStudent saveDmHonourRollStudent(
            @ApiParam(value = "光荣榜，学生获得者对象", required = true)
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        dmHonourRollStudentService.saveDmHonourRollStudent(dmHonourRollStudent);
        return dmHonourRollStudent;
    }

    @PostMapping("/findDmHonourRollStudentListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者列表", notes = "返回光荣榜，学生获得者列表")
    public List<DmHonourRollStudent> findDmHonourRollStudentListByCondition(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.findDmHonourRollStudentListByCondition(dmHonourRollStudent);
    }
    @PostMapping("/findDmHonourRollStudentCountByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者列表个数", notes = "返回光荣榜，学生获得者总个数")
    public long findDmHonourRollStudentCountByCondition(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.findDmHonourRollStudentCountByCondition(dmHonourRollStudent);
    }

    @PostMapping("/updateDmHonourRollStudent")
    @ApiOperation(value = "修改光荣榜，学生获得者", notes = "光荣榜，学生获得者对象必传")
    public void updateDmHonourRollStudent(
            @ApiParam(value = "光荣榜，学生获得者对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        dmHonourRollStudentService.updateDmHonourRollStudent(dmHonourRollStudent);
    }

    @GetMapping("/deleteDmHonourRollStudent/{id}")
    @ApiOperation(value = "通过id删除光荣榜，学生获得者")
    public void deleteDmHonourRollStudent(
            @ApiParam(value = "光荣榜，学生获得者对象", required = true)
            @PathVariable String id){
        dmHonourRollStudentService.deleteDmHonourRollStudent(id);
    }
    @PostMapping("/deleteDmHonourRollStudentByCondition")
    @ApiOperation(value = "根据条件删除光荣榜，学生获得者")
    public void deleteDmHonourRollStudentByCondition(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        dmHonourRollStudentService.deleteDmHonourRollStudentByCondition(dmHonourRollStudent);
    }
    @PostMapping("/findOneDmHonourRollStudentByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜，学生获得者,结果必须为单条数据", notes = "返回单个光荣榜，学生获得者,没有时为空")
    public DmHonourRollStudent findOneDmHonourRollStudentByCondition(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.findOneDmHonourRollStudentByCondition(dmHonourRollStudent);
    }

    @PostMapping("/findDmHonourRollStudentListByConditions")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者列表", notes = "返回光荣榜，学生获得者列表")
    public List<DmHonourRollStudent> findDmHonourRollStudentListByConditions(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.findDmHonourRollStudentListByConditions(dmHonourRollStudent);
    }
    @PostMapping("/findDmHonourRollStudentCountByConditions")
    @ApiOperation(value = "根据条件查找光荣榜，学生获得者列表个数", notes = "返回光荣榜，学生获得者总个数")
    public long findDmHonourRollStudentCountByConditions(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.findDmHonourRollStudentCountByConditions(dmHonourRollStudent);
    }
    @PostMapping("/getHonourRollList")
    @ApiOperation(value = "获取列表", notes = "返回光荣榜，学生获得者列表")
    public List<EccHonourRoll> getHonourRollList(
            @ApiParam(value = "光荣榜，学生获得者对象")
            @RequestBody DmHonourRollStudent dmHonourRollStudent){
        return dmHonourRollStudentService.getHonourRollList(dmHonourRollStudent);
    }

    @GetMapping("/deleteDmHonourRollStudentByClassId")
    @ApiOperation(value = "升班删除指定的班级内的数据", notes = "升班删除指定的班级内的数据")
    public void deleteDmHonourRollStudentByClassId(
            @ApiParam(value = "升班删除指定的班级内的数据", required = true)
            @RequestBody DmDeleteData dmDeleteData){
        dmHonourRollStudentService.deleteDmHonourRollStudentByClassId(dmDeleteData);
    }

}
