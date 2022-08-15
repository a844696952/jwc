package com.yice.edu.cn.dm.controller.msg;

import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.dm.service.msg.DmMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmMsg")
@Api(value = "/dmMsg",description = "云班牌-消息表模块")
public class DmMsgController {
    @Autowired
    private DmMsgService dmMsgService;

    @GetMapping("/findDmMsgById/{id}")
    @ApiOperation(value = "通过id查找云班牌-消息表", notes = "返回云班牌-消息表对象")
    public DmMsg findDmMsgById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmMsgService.findDmMsgById(id);
    }

    @PostMapping("/saveDmMsg")
    @ApiOperation(value = "保存云班牌-消息表", notes = "返回云班牌-消息表对象")
    public DmMsg saveDmMsg(
            @ApiParam(value = "云班牌-消息表对象", required = true)
            @RequestBody DmMsg dmMsg){
        dmMsgService.saveDmMsg(dmMsg);
        return dmMsg;
    }

    @PostMapping("/findDmMsgListByCondition")
    @ApiOperation(value = "根据条件查找云班牌-消息表列表", notes = "返回云班牌-消息表列表")
    public List<DmMsg> findDmMsgListByCondition(
            @ApiParam(value = "云班牌-消息表对象")
            @RequestBody DmMsg dmMsg){
        return dmMsgService.findDmMsgListByCondition(dmMsg);
    }
    @PostMapping("/findDmMsgCountByCondition")
    @ApiOperation(value = "根据条件查找云班牌-消息表列表个数", notes = "返回云班牌-消息表总个数")
    public long findDmMsgCountByCondition(
            @ApiParam(value = "云班牌-消息表对象")
            @RequestBody DmMsg dmMsg){
        return dmMsgService.findDmMsgCountByCondition(dmMsg);
    }

    @PostMapping("/updateDmMsg")
    @ApiOperation(value = "修改云班牌-消息表", notes = "云班牌-消息表对象必传")
    public void updateDmMsg(
            @ApiParam(value = "云班牌-消息表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMsg dmMsg){
        dmMsgService.updateDmMsg(dmMsg);
    }

    @GetMapping("/deleteDmMsg/{id}")
    @ApiOperation(value = "通过id删除云班牌-消息表")
    public void deleteDmMsg(
            @ApiParam(value = "云班牌-消息表对象", required = true)
            @PathVariable String id){
        dmMsgService.deleteDmMsg(id);
    }
    @PostMapping("/deleteDmMsgByCondition")
    @ApiOperation(value = "根据条件删除云班牌-消息表")
    public void deleteDmMsgByCondition(
            @ApiParam(value = "云班牌-消息表对象")
            @RequestBody DmMsg dmMsg){
        dmMsgService.deleteDmMsgByCondition(dmMsg);
    }
    @PostMapping("/findOneDmMsgByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌-消息表,结果必须为单条数据", notes = "返回单个云班牌-消息表,没有时为空")
    public DmMsg findOneDmMsgByCondition(
            @ApiParam(value = "云班牌-消息表对象")
            @RequestBody DmMsg dmMsg){
        return dmMsgService.findOneDmMsgByCondition(dmMsg);
    }

    @PostMapping("/sendMsg")
    @ApiOperation(value = "发送消息", notes = "返回单个云班牌-消息表,没有时为空")
    public DmMsg sendMsg(
            @ApiParam(value = "云班牌-消息表对象")
            @RequestBody DmMsg dmMsg){
        return dmMsgService.sendMsg(dmMsg);
    }




}
