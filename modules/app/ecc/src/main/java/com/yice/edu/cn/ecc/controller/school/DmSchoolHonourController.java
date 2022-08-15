package com.yice.edu.cn.ecc.controller.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.ecc.service.school.DmSchoolHonourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dmSchoolHonour")
@Api(value = "/dmSchoolHonour",description = "荣誉表模块")
public class DmSchoolHonourController {
    @Autowired
    private DmSchoolHonourService dmSchoolHonourService;

    @GetMapping("/look/lookDmSchoolHonourById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找荣誉表", notes = "返回响应对象")
    public ResponseJson lookDmSchoolHonourById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable @Validated String id){
        DmSchoolHonour dmSchoolHonour=dmSchoolHonourService.findDmSchoolHonourById(id);
        return new ResponseJson(dmSchoolHonour);
    }

    @PostMapping("/findDmSchoolHonoursByCondition")
    @ApiOperation(value = "根据条件查找荣誉表", notes = "返回响应对象")
    public ResponseJson findDmSchoolHonoursByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour){
        List<DmSchoolHonour> data=dmSchoolHonourService.findDmSchoolHonourListByCondition(dmSchoolHonour);
        long count=dmSchoolHonourService.findDmSchoolHonourCountByCondition(dmSchoolHonour);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findDmSchoolHonourListByCondition")
    @ApiOperation(value = "根据条件查找荣誉表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmSchoolHonourListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolHonour dmSchoolHonour){
        List<DmSchoolHonour> data=dmSchoolHonourService.findDmSchoolHonourListByCondition(dmSchoolHonour);
        return new ResponseJson(data);
    }



}
