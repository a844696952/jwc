package com.yice.edu.cn.ewb.controller.classRegister;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.classRegister.HelpCenter;
import com.yice.edu.cn.ewb.service.classRegister.HelpCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/helpCenter")
@Api(value = "/helpCenter",description = "帮助中心模块")
public class HelpCenterController {
    @Autowired
    private HelpCenterService helpCenterService;

    @PostMapping("/saveHelpCenter")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=HelpCenter.class)
    public ResponseJson saveHelpCenter(
            @ApiParam(value = "对象", required = true)
            @RequestBody HelpCenter helpCenter){
        HelpCenter s=helpCenterService.saveHelpCenter(helpCenter);
        return new ResponseJson(s);
    }


    @PostMapping("/updateHelpCenter")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHelpCenter(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HelpCenter helpCenter){
        helpCenterService.updateHelpCenter(helpCenter);
        return new ResponseJson();
    }

    @GetMapping("/findHelpCenterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HelpCenter.class)
    public ResponseJson findHelpCenterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HelpCenter helpCenter=helpCenterService.findHelpCenterById(id);
        return new ResponseJson(helpCenter);
    }

    @GetMapping("/deleteHelpCenter/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHelpCenter(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        helpCenterService.deleteHelpCenter(id);
        return new ResponseJson();
    }


    @PostMapping("/findHelpCenterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=HelpCenter.class)
    public ResponseJson findHelpCenterListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HelpCenter helpCenter){
        List<HelpCenter> data=helpCenterService.findHelpCenterListByCondition(helpCenter);
        return new ResponseJson(data);
    }



}
