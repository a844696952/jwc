package com.yice.edu.cn.bmp.controller.parentMsg;

import com.yice.edu.cn.bmp.service.parentMsg.DmParentQuickReplyService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/dmParentQuickReply")
@Api(value = "/dmParentQuickReply",description = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复模块")
public class DmParentQuickReplyController {
    @Autowired
    private DmParentQuickReplyService dmParentQuickReplyService;

    @PostMapping("/saveDmParentQuickReply")
    @ApiOperation(value = "保存设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", notes = "返回保存好的设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", response=DmParentQuickReply.class)
    public ResponseJson saveDmParentQuickReply(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", required = true)
            @RequestBody DmParentQuickReply dmParentQuickReply){
        DmParentQuickReply s=dmParentQuickReplyService.saveDmParentQuickReply(dmParentQuickReply);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmParentQuickReply")
    @ApiOperation(value = "修改设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", notes = "返回响应对象")
    public ResponseJson updateDmParentQuickReply(
            @ApiParam(value = "被修改的设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象,对象属性不为空则修改", required = true)
            @RequestBody DmParentQuickReply dmParentQuickReply){
        dmParentQuickReplyService.updateDmParentQuickReply(dmParentQuickReply);
        return new ResponseJson();
    }

    @GetMapping("/findDmParentQuickReplyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复", notes = "返回响应对象", response=DmParentQuickReply.class)
    public ResponseJson findDmParentQuickReplyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmParentQuickReply dmParentQuickReply=dmParentQuickReplyService.findDmParentQuickReplyById(id);
        return new ResponseJson(dmParentQuickReply);
    }

    @GetMapping("/deleteDmParentQuickReply/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmParentQuickReply(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmParentQuickReplyService.deleteDmParentQuickReply(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmParentQuickReplyListByCondition")
    @ApiOperation(value = "根据条件查找设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复列表", notes = "返回响应对象,不包含总条数", response=DmParentQuickReply.class)
    public ResponseJson findDmParentQuickReplyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmParentQuickReply dmParentQuickReply){
        List<DmParentQuickReply> data=dmParentQuickReplyService.findDmParentQuickReplyListByCondition(dmParentQuickReply);
        return new ResponseJson(data);
    }



}
