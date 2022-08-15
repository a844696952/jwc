package com.yice.edu.cn.ecc.controller.dmScreenSaver;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.ecc.service.dmScreenSaver.DmScreenSaverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmScreenSaver")
@Api(value = "/dmScreenSaver",description = "模块")
public class DmScreenSaverController {
    @Autowired
    private DmScreenSaverService dmScreenSaverService;


    @GetMapping("/update/findDmScreenSaverById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DmScreenSaver.class)
    public ResponseJson findDmScreenSaverById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmScreenSaver dmScreenSaver=dmScreenSaverService.findDmScreenSaverById(id);
        return new ResponseJson(dmScreenSaver);
    }


    @GetMapping("/look/lookDmScreenSaverById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DmScreenSaver.class)
    public ResponseJson lookDmScreenSaverById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmScreenSaver dmScreenSaver=dmScreenSaverService.findDmScreenSaverById(id);
        return new ResponseJson(dmScreenSaver);
    }

    @PostMapping("/findDmScreenSaversByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DmScreenSaver.class)
    public ResponseJson findDmScreenSaversByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaver dmScreenSaver){
        List<DmScreenSaver> data=dmScreenSaverService.findDmScreenSaverListByCondition(dmScreenSaver);
        long count=dmScreenSaverService.findDmScreenSaverCountByCondition(dmScreenSaver);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmScreenSaverByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DmScreenSaver.class)
    public ResponseJson findOneDmScreenSaverByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmScreenSaver dmScreenSaver){
        DmScreenSaver one=dmScreenSaverService.findOneDmScreenSaverByCondition(dmScreenSaver);
        return new ResponseJson(one);
    }


    @PostMapping("/findDmScreenSaverListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DmScreenSaver.class)
    public ResponseJson findDmScreenSaverListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaver dmScreenSaver){
        List<DmScreenSaver> data=dmScreenSaverService.findDmScreenSaverListByCondition(dmScreenSaver);
        return new ResponseJson(data);
    }

    @PostMapping("/getRunNingDmScreenSaver")
    @ApiOperation(value = "获取当前正在运行中的所有屏保", notes = "返回响应对象,不包含总条数", response=DmScreenSaver.class)
    public ResponseJson getRunNingDmScreenSaver(
            @ApiParam(value = "属性不为空则作为条件查询，schoolId必填",required = true)
            @Validated
            @RequestParam("schoolId") String schoolId){
        DmScreenSaver dmScreenSaver = new DmScreenSaver();
        dmScreenSaver.setSchoolId(schoolId);
        if(null == dmScreenSaver.getSchoolId() || "".equals(dmScreenSaver.getSchoolId())){
            return new ResponseJson(false,"学校编号不能为空");
        }else{
            DmScreenSaver data=dmScreenSaverService.getRunNingDmScreenSaver(dmScreenSaver);
            return new ResponseJson(data);
        }
    }
    @GetMapping("/batchUpdateDmScreenSaverStatus")
    @ApiOperation(value = "修改屏保的状态，已停止为3", notes = "返回响应对象", response=DmScreenSaver.class)
    public ResponseJson batchUpdateDmScreenSaverStatus(
            @ApiParam(value = "属性不为空则作为条件查询，schoolId必填",required = true)
               @Validated
               @RequestParam("schoolId") String schoolId){
        DmScreenSaver dmScreenSaver = new DmScreenSaver();
        dmScreenSaver.setStatus(3);
        dmScreenSaver.setSchoolId(schoolId);
        dmScreenSaverService.batchUpdateDmScreenSaverStatus(dmScreenSaver);
        return new ResponseJson();
    }

}
