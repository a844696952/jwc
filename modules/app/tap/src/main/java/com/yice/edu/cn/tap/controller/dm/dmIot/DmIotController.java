package com.yice.edu.cn.tap.controller.dm.dmIot;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.dm.dmIot.DmIotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmIot")
@Api(value = "/dmIot",description = "物联网表模块")
public class DmIotController {
    @Autowired
    private DmIotService dmIotService;
    @PostMapping("/saveDmIot")
    @ApiOperation(value = "保存物联网表对象", notes = "返回保存好的物联网表对象", response=DmIot.class)
    public ResponseJson saveDmIot(
            @ApiParam(value = "物联网表对象", required = true)
            @RequestBody DmIot dmIot){
        dmIotService.saveDmIot(dmIot);
        return new ResponseJson();
    }

    @GetMapping("/findDmIotById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找物联网表", notes = "返回响应对象", response=DmIot.class)
    public ResponseJson findDmIotById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmIot dmIot=dmIotService.findDmIotById(id);
        return new ResponseJson(dmIot);
    }

    @PostMapping("/updateDmIot")
    @ApiOperation(value = "修改物联网表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateDmIot(
            @ApiParam(value = "被修改的物联网表对象,对象属性不为空则修改", required = true)
            @RequestBody DmIot dmIot){
        dmIotService.updateDmIot(dmIot);
        return new ResponseJson();
    }

    @PostMapping("/updateDmIotForAll")
    @ApiOperation(value = "修改物联网表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateDmIotForAll(
            @ApiParam(value = "被修改的物联网表对象", required = true)
            @RequestBody DmIot dmIot){
        dmIot.setSchoolId(LoginInterceptor.mySchoolId());
        if(dmIot.getId()==null){
            dmIotService.saveDmIot(dmIot);
        }else{
            dmIotService.updateDmIotForAll(dmIot);
        }

        return new ResponseJson();
    }


    @PostMapping("/findDmIotsByCondition")
    @ApiOperation(value = "根据条件查找物联网表", notes = "返回响应对象", response=DmIot.class)
    public ResponseJson findDmIotsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmIot dmIot){
        List<DmIot> data=dmIotService.findDmIotListByCondition(dmIot);
        long count=dmIotService.findDmIotCountByCondition(dmIot);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmIotByCondition")
    @ApiOperation(value = "根据条件查找单个物联网表,结果必须为单条数据", notes = "没有时返回空", response=DmIot.class)
    public ResponseJson findOneDmIotByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmIot dmIot){
        DmIot one=dmIotService.findOneDmIotByCondition(dmIot);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmIot/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmIot(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmIotService.deleteDmIot(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmIotListByCondition")
    @ApiOperation(value = "根据条件查找物联网表列表", notes = "返回响应对象,不包含总条数", response=DmIot.class)
    public ResponseJson findDmIotListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmIot dmIot){
        List<DmIot> data=dmIotService.findDmIotListByCondition(dmIot);
        return new ResponseJson(data);
    }

    @GetMapping("/findDmIotOneBySchoolId")
    @ApiOperation(value = "无需传参，直接调用",notes = "返回该学校物联网端口、Ip、apitoken")
    public ResponseJson findDmIotOneBySchooolId(){
        DmIot dmIot = new DmIot();
        dmIot.setSchoolId(LoginInterceptor.mySchoolId());
        Pager pager = new Pager();
        pager.setIncludes("ip,port,token");
        dmIot.setPager(pager);
        return new ResponseJson(dmIotService.findOneDmIotByCondition(dmIot));

    }



}
