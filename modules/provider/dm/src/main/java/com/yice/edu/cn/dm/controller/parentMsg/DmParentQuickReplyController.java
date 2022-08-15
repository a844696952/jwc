package com.yice.edu.cn.dm.controller.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import com.yice.edu.cn.dm.service.parentMsg.DmParentQuickReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmParentQuickReply")
@Api(value = "/dmParentQuickReply",description = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复模块")
public class DmParentQuickReplyController {
    @Autowired
    private DmParentQuickReplyService dmParentQuickReplyService;

    @GetMapping("/findDmParentQuickReplyById/{id}")
    @ApiOperation(value = "通过id查找设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复", notes = "返回设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
    public DmParentQuickReply findDmParentQuickReplyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmParentQuickReplyService.findDmParentQuickReplyById(id);
    }

    @PostMapping("/saveDmParentQuickReply")
    @ApiOperation(value = "保存设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复", notes = "返回设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
    public DmParentQuickReply saveDmParentQuickReply(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", required = true)
            @RequestBody DmParentQuickReply dmParentQuickReply){
        dmParentQuickReplyService.saveDmParentQuickReply(dmParentQuickReply);
        return dmParentQuickReply;
    }

    @PostMapping("/findDmParentQuickReplyListByCondition")
    @ApiOperation(value = "根据条件查找设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复列表", notes = "返回设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复列表")
    public List<DmParentQuickReply> findDmParentQuickReplyListByCondition(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
            @RequestBody DmParentQuickReply dmParentQuickReply){
        return dmParentQuickReplyService.findDmParentQuickReplyListByCondition(dmParentQuickReply);
    }
    @PostMapping("/findDmParentQuickReplyCountByCondition")
    @ApiOperation(value = "根据条件查找设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复列表个数", notes = "返回设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复总个数")
    public long findDmParentQuickReplyCountByCondition(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
            @RequestBody DmParentQuickReply dmParentQuickReply){
        return dmParentQuickReplyService.findDmParentQuickReplyCountByCondition(dmParentQuickReply);
    }

    @PostMapping("/updateDmParentQuickReply")
    @ApiOperation(value = "修改设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复", notes = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象必传")
    public void updateDmParentQuickReply(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象,对象属性不为空则修改", required = true)
            @RequestBody DmParentQuickReply dmParentQuickReply){
        dmParentQuickReplyService.updateDmParentQuickReply(dmParentQuickReply);
    }

    @GetMapping("/deleteDmParentQuickReply/{id}")
    @ApiOperation(value = "通过id删除设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复")
    public void deleteDmParentQuickReply(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象", required = true)
            @PathVariable String id){
        dmParentQuickReplyService.deleteDmParentQuickReply(id);
    }
    @PostMapping("/deleteDmParentQuickReplyByCondition")
    @ApiOperation(value = "根据条件删除设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复")
    public void deleteDmParentQuickReplyByCondition(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
            @RequestBody DmParentQuickReply dmParentQuickReply){
        dmParentQuickReplyService.deleteDmParentQuickReplyByCondition(dmParentQuickReply);
    }
    @PostMapping("/findOneDmParentQuickReplyByCondition")
    @ApiOperation(value = "根据条件查找单个设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复,结果必须为单条数据", notes = "返回单个设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复,没有时为空")
    public DmParentQuickReply findOneDmParentQuickReplyByCondition(
            @ApiParam(value = "设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复对象")
            @RequestBody DmParentQuickReply dmParentQuickReply){
        return dmParentQuickReplyService.findOneDmParentQuickReplyByCondition(dmParentQuickReply);
    }
}
