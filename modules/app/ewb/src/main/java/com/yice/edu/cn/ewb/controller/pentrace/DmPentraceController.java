package com.yice.edu.cn.ewb.controller.pentrace;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import com.yice.edu.cn.ewb.service.pentrace.DmPentraceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/dmPentrace")
@Api(value = "/dmPentrace",description = "智能笔轨迹表模块")
public class DmPentraceController {
    @Autowired
    private DmPentraceService dmPentraceService;

    @PostMapping("/saveDmPentrace")
    @ApiOperation(value = "保存智能笔轨迹表对象", notes = "返回保存好的智能笔轨迹表对象", response= DmPentrace.class)
    public ResponseJson saveDmPentrace(
            @ApiParam(value = "智能笔轨迹表对象", required = true)
            @RequestBody DmPentrace dmPentrace){
        DmPentrace s=dmPentraceService.saveDmPentrace(dmPentrace);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmPentraceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找智能笔轨迹表", notes = "返回响应对象", response=DmPentrace.class)
    public ResponseJson findDmPentraceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmPentrace dmPentrace=dmPentraceService.findDmPentraceById(id);
        return new ResponseJson(dmPentrace);
    }

    @PostMapping("/update/updateDmPentrace")
    @ApiOperation(value = "修改智能笔轨迹表对象", notes = "返回响应对象")
    public ResponseJson updateDmPentrace(
            @ApiParam(value = "被修改的智能笔轨迹表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPentrace dmPentrace){
        dmPentraceService.updateDmPentrace(dmPentrace);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmPentraceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找智能笔轨迹表", notes = "返回响应对象", response=DmPentrace.class)
    public ResponseJson lookDmPentraceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmPentrace dmPentrace=dmPentraceService.findDmPentraceById(id);
        return new ResponseJson(dmPentrace);
    }

    @PostMapping("/findDmPentracesByCondition")
    @ApiOperation(value = "根据条件查找智能笔轨迹表", notes = "返回响应对象", response=DmPentrace.class)
    public ResponseJson findDmPentracesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPentrace dmPentrace){
        List<DmPentrace> data=dmPentraceService.findDmPentraceListByCondition(dmPentrace);
        long count=dmPentraceService.findDmPentraceCountByCondition(dmPentrace);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmPentraceByCondition")
    @ApiOperation(value = "根据条件查找单个智能笔轨迹表,结果必须为单条数据", notes = "没有时返回空", response=DmPentrace.class)
    public ResponseJson findOneDmPentraceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmPentrace dmPentrace){
        DmPentrace one=dmPentraceService.findOneDmPentraceByCondition(dmPentrace);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmPentrace/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmPentrace(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmPentraceService.deleteDmPentrace(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmPentraceListByCondition")
    @ApiOperation(value = "根据条件查找智能笔轨迹表列表", notes = "返回响应对象,不包含总条数", response=DmPentrace.class)
    public ResponseJson findDmPentraceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmPentrace dmPentrace){
        List<DmPentrace> data=dmPentraceService.findDmPentraceListByCondition(dmPentrace);
        return new ResponseJson(data);
    }



}
