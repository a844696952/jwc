package com.yice.edu.cn.osp.controller.dm.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.osp.service.dm.school.DmSchoolHonourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmSchoolHonour")
@Api(value = "/dmSchoolHonour", description = "荣誉表模块")
public class DmSchoolHonourController {
    @Autowired
    private DmSchoolHonourService dmSchoolHonourService;

    @PostMapping("/saveDmSchoolHonour")
    @ApiOperation(value = "保存荣誉表对象", notes = "返回响应对象")
    public ResponseJson saveDmSchoolHonour(
            @ApiParam(value = "荣誉表对象", required = true)
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonour.setSchooId(mySchoolId());
        DmSchoolHonour s = dmSchoolHonourService.saveDmSchoolHonour(dmSchoolHonour);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmSchoolHonourById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找荣誉表", notes = "返回响应对象")
    public ResponseJson findDmSchoolHonourById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable @Validated String id) {
        DmSchoolHonour dmSchoolHonour = dmSchoolHonourService.findDmSchoolHonourById(id);
        return new ResponseJson(dmSchoolHonour);
    }

    @PostMapping("/update/updateDmSchoolHonour")
    @ApiOperation(value = "修改荣誉表对象", notes = "返回响应对象")
    public ResponseJson updateDmSchoolHonour(
            @ApiParam(value = "被修改的荣誉表对象,对象属性不为空则修改", required = true)
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonour.setSchooId(mySchoolId());
        dmSchoolHonourService.updateDmSchoolHonour(dmSchoolHonour);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmSchoolHonourById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找荣誉表", notes = "返回响应对象")
    public ResponseJson lookDmSchoolHonourById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable @Validated String id) {
        DmSchoolHonour dmSchoolHonour = dmSchoolHonourService.findDmSchoolHonourById(id);
        return new ResponseJson(dmSchoolHonour);
    }

    @PostMapping("/findDmSchoolHonoursByCondition")
    @ApiOperation(value = "根据条件查找荣誉表", notes = "返回响应对象")
    public ResponseJson findDmSchoolHonoursByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonour.setSchooId(mySchoolId());
        List<DmSchoolHonour> data = dmSchoolHonourService.findDmSchoolHonourListByCondition(dmSchoolHonour);
        long count = dmSchoolHonourService.findDmSchoolHonourCountByCondition(dmSchoolHonour);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmSchoolHonourByCondition")
    @ApiOperation(value = "根据条件查找单个荣誉表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmSchoolHonourByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        DmSchoolHonour one = dmSchoolHonourService.findOneDmSchoolHonourByCondition(dmSchoolHonour);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmSchoolHonour/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmSchoolHonour(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable @Validated String id) {
        dmSchoolHonourService.deleteDmSchoolHonour(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmSchoolHonourListByCondition")
    @ApiOperation(value = "根据条件查找荣誉表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmSchoolHonourListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        List<DmSchoolHonour> data = dmSchoolHonourService.findDmSchoolHonourListByCondition(dmSchoolHonour);
        return new ResponseJson(data);
    }


    @PostMapping("/findDmSchoolHonourByactiveNameLike")
    @ApiOperation(value = "根据名称模糊查询", notes = "返回响应对象")
    public ResponseJson findDmSchoolHonourByactiveNameLike(
            @ApiParam(value = "属性不为空作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonour.setSchooId(mySchoolId());
        List<DmSchoolHonour> data = dmSchoolHonourService.findDmSchoolHonourByactiveNameLike(dmSchoolHonour);
        long count = dmSchoolHonourService.findDmSchoolHonourByactiveNameLikeCount(dmSchoolHonour);
        return new ResponseJson(data,count);
    }

}
