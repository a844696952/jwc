package com.yice.edu.cn.ecc.controller.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.ecc.service.school.DmSchoolBigNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dmSchoolBigNews")
@Api(value = "/dmSchoolBigNews",description = "大事件模块")
public class DmSchoolBigNewsController {
    @Autowired
    private DmSchoolBigNewsService dmSchoolBigNewsService;

    @GetMapping("/look/lookDmSchoolBigNewsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找大事件", notes = "返回响应对象")
    public ResponseJson lookDmSchoolBigNewsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable @Validated String id){
        DmSchoolBigNews dmSchoolBigNews=dmSchoolBigNewsService.findDmSchoolBigNewsById(id);
        return new ResponseJson(dmSchoolBigNews);
    }

    @PostMapping("/findDmSchoolBigNewssByCondition")
    @ApiOperation(value = "根据条件查找大事件", notes = "返回响应对象")
    public ResponseJson findDmSchoolBigNewssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        List<DmSchoolBigNews> data=dmSchoolBigNewsService.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
        long count=dmSchoolBigNewsService.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findDmSchoolBigNewsListByCondition")
    @ApiOperation(value = "根据条件查找大事件列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmSchoolBigNewsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        List<DmSchoolBigNews> data=dmSchoolBigNewsService.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
        return new ResponseJson(data);
    }



}
