package com.yice.edu.cn.yed.controller.jw.thirdParty;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import com.yice.edu.cn.yed.service.jw.thirdParty.ThirdPartyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/thirdParty")
@Api(value = "/thirdParty",description = "模块")
public class ThirdPartyController {
    @Autowired
    private ThirdPartyService thirdPartyService;
    @PostMapping("/saveThirdParty")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= ThirdParty.class)
    public ResponseJson saveThirdParty(
            @ApiParam(value = "对象", required = true)
            @RequestBody ThirdParty thirdParty){
        ThirdParty s=thirdPartyService.saveThirdParty(thirdParty);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findThirdPartyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=ThirdParty.class)
    public ResponseJson findThirdPartyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ThirdParty thirdParty=thirdPartyService.findThirdPartyById(id);
        return new ResponseJson(thirdParty);
    }

    @PostMapping("/update/updateThirdParty")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateThirdParty(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.updateThirdParty(thirdParty);
        return new ResponseJson();
    }

    @PostMapping("/update/updateThirdPartyForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateThirdPartyForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody ThirdParty thirdParty){
        thirdPartyService.updateThirdPartyForAll(thirdParty);
        return new ResponseJson();
    }

    @GetMapping("/look/lookThirdPartyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ThirdParty.class)
    public ResponseJson lookThirdPartyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ThirdParty thirdParty=thirdPartyService.findThirdPartyById(id);
        return new ResponseJson(thirdParty);
    }

    @PostMapping("/findThirdPartysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ThirdParty.class)
    public ResponseJson findThirdPartysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ThirdParty thirdParty){
        List<ThirdParty> data=thirdPartyService.findThirdPartyListByCondition(thirdParty);
        long count=thirdPartyService.findThirdPartyCountByCondition(thirdParty);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneThirdPartyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=ThirdParty.class)
    public ResponseJson findOneThirdPartyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ThirdParty thirdParty){
        ThirdParty one=thirdPartyService.findOneThirdPartyByCondition(thirdParty);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteThirdParty/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteThirdParty(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        thirdPartyService.deleteThirdParty(id);
        return new ResponseJson();
    }


    @PostMapping("/findThirdPartyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ThirdParty.class)
    public ResponseJson findThirdPartyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ThirdParty thirdParty){
        List<ThirdParty> data=thirdPartyService.findThirdPartyListByCondition(thirdParty);
        return new ResponseJson(data);
    }



}
