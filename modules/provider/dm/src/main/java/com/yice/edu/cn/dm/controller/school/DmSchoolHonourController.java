package com.yice.edu.cn.dm.controller.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.dm.service.school.DmSchoolHonourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmSchoolHonour")
@Api(value = "/dmSchoolHonour", description = "荣誉表模块")
public class DmSchoolHonourController {
    @Autowired
    private DmSchoolHonourService dmSchoolHonourService;

    @GetMapping("/findDmSchoolHonourById/{id}")
    @ApiOperation(value = "通过id查找荣誉表", notes = "返回荣誉表对象")
    public DmSchoolHonour findDmSchoolHonourById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return dmSchoolHonourService.findDmSchoolHonourById(id);
    }

    @PostMapping("/saveDmSchoolHonour")
    @ApiOperation(value = "保存荣誉表", notes = "返回荣誉表对象")
    public DmSchoolHonour saveDmSchoolHonour(
            @ApiParam(value = "荣誉表对象", required = true)
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourService.saveDmSchoolHonour(dmSchoolHonour);
        return dmSchoolHonour;
    }

    @PostMapping("/findDmSchoolHonourListByCondition")
    @ApiOperation(value = "根据条件查找荣誉表列表", notes = "返回荣誉表列表")
    public List<DmSchoolHonour> findDmSchoolHonourListByCondition(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourService.findDmSchoolHonourListByCondition(dmSchoolHonour);
    }

    @PostMapping("/findDmSchoolHonourCountByCondition")
    @ApiOperation(value = "根据条件查找荣誉表列表个数", notes = "返回荣誉表总个数")
    public long findDmSchoolHonourCountByCondition(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourService.findDmSchoolHonourCountByCondition(dmSchoolHonour);
    }

    @PostMapping("/updateDmSchoolHonour")
    @ApiOperation(value = "修改荣誉表", notes = "荣誉表对象必传")
    public void updateDmSchoolHonour(
            @ApiParam(value = "荣誉表对象,对象属性不为空则修改", required = true)
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourService.updateDmSchoolHonour(dmSchoolHonour);
    }

    @GetMapping("/deleteDmSchoolHonour/{id}")
    @ApiOperation(value = "通过id删除荣誉表")
    public void deleteDmSchoolHonour(
            @ApiParam(value = "荣誉表对象", required = true)
            @PathVariable String id) {
        dmSchoolHonourService.deleteDmSchoolHonour(id);
    }

    @PostMapping("/deleteDmSchoolHonourByCondition")
    @ApiOperation(value = "根据条件删除荣誉表")
    public void deleteDmSchoolHonourByCondition(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourService.deleteDmSchoolHonourByCondition(dmSchoolHonour);
    }

    @PostMapping("/findOneDmSchoolHonourByCondition")
    @ApiOperation(value = "根据条件查找单个荣誉表,结果必须为单条数据", notes = "返回单个荣誉表,没有时为空")
    public DmSchoolHonour findOneDmSchoolHonourByCondition(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourService.findOneDmSchoolHonourByCondition(dmSchoolHonour);
    }

    @PostMapping("/findDmSchoolHonourByactiveNameLike")
    @ApiOperation(value = "根据名称模糊查询所有结果", notes = "返回响应数据")
    public List<DmSchoolHonour> findDmSchoolHonourByactiveNameLike(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour
    ) {
        List<DmSchoolHonour> list = dmSchoolHonourService.findDmSchoolHonourByactiveNameLike(dmSchoolHonour);
        return list;
    }

    @PostMapping("/findDmSchoolHonourByactiveNameLikeCount")
    @ApiOperation(value = "根据名称模糊查询所有结果", notes = "返回响应数据")
    public Long findDmSchoolHonourByactiveNameLikeCount(
            @ApiParam(value = "荣誉表对象")
            @RequestBody DmSchoolHonour dmSchoolHonour
    ) {
        return dmSchoolHonourService.findDmSchoolHonourByactiveNameLikeCount(dmSchoolHonour);
    }
}
