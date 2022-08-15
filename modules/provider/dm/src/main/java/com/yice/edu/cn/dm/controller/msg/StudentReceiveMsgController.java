package com.yice.edu.cn.dm.controller.msg;

import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import com.yice.edu.cn.dm.service.msg.StudentReceiveMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentReceiveMsg")
@Api(value = "/receiveMsg",description = "消息接收表模块")
public class StudentReceiveMsgController {
    @Autowired
    private StudentReceiveMsgService receiveMsgService;

    @GetMapping("/findReceiveMsgById/{id}")
    @ApiOperation(value = "通过id查找消息接收表", notes = "返回消息接收表对象")
    public StudentReceiveMsg findReceiveMsgById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return  receiveMsgService.findReceiveMsgById(id);
    }

    @PostMapping("/saveReceiveMsg")
    @ApiOperation(value = "保存消息接收表", notes = "返回消息接收表对象")
    public StudentReceiveMsg saveReceiveMsg(
            @ApiParam(value = "消息接收表对象", required = true)
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsgService.saveReceiveMsg(receiveMsg);
        return receiveMsg;
    }

    @PostMapping("/findReceiveMsgListByCondition")
    @ApiOperation(value = "根据条件查找消息接收表列表", notes = "返回消息接收表列表")
    public List<StudentReceiveMsg> findReceiveMsgListByCondition(
            @ApiParam(value = "消息接收表对象")
            @RequestBody StudentReceiveMsg receiveMsg){
        return receiveMsgService.findReceiveMsgListByCondition(receiveMsg);
    }
    @PostMapping("/findReceiveMsgCountByCondition")
    @ApiOperation(value = "根据条件查找消息接收表列表个数", notes = "返回消息接收表总个数")
    public long findReceiveMsgCountByCondition(
            @ApiParam(value = "消息接收表对象")
            @RequestBody StudentReceiveMsg receiveMsg){
        return receiveMsgService.findReceiveMsgCountByCondition(receiveMsg);
    }

    @PostMapping("/updateReceiveMsg")
    @ApiOperation(value = "修改消息接收表", notes = "消息接收表对象必传")
    public void updateReceiveMsg(
            @ApiParam(value = "消息接收表对象,对象属性不为空则修改", required = true)
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsgService.updateReceiveMsg(receiveMsg);
    }

    @GetMapping("/deleteReceiveMsg/{id}")
    @ApiOperation(value = "通过id删除消息接收表")
    public void deleteReceiveMsg(
            @ApiParam(value = "消息接收表对象", required = true)
            @PathVariable String id){
        receiveMsgService.deleteReceiveMsg(id);
    }
    @PostMapping("/deleteReceiveMsgByCondition")
    @ApiOperation(value = "根据条件删除消息接收表")
    public void deleteReceiveMsgByCondition(
            @ApiParam(value = "消息接收表对象")
            @RequestBody StudentReceiveMsg receiveMsg){
        receiveMsgService.deleteReceiveMsgByCondition(receiveMsg);
    }
    @PostMapping("/findOneReceiveMsgByCondition")
    @ApiOperation(value = "根据条件查找单个消息接收表,结果必须为单条数据", notes = "返回单个消息接收表,没有时为空")
    public StudentReceiveMsg findOneReceiveMsgByCondition(
            @ApiParam(value = "消息接收表对象")
            @RequestBody StudentReceiveMsg receiveMsg){
        return receiveMsgService.findOneReceiveMsgByCondition(receiveMsg);
    }

    @PostMapping("/batchSave")
    @ApiOperation(value = "根据条件查找单个消息接收表,结果必须为单条数据", notes = "返回单个消息接收表,没有时为空")
    public void batchSave(
            @ApiParam(value = "消息接收表对象")
            @RequestBody List<StudentReceiveMsg> list){
        receiveMsgService.batchSaveReceiveMsg(list);
    }

    @PostMapping("/readMsgs")
    @ApiOperation(value = "阅读文字消息s")
    public void readMsgs(@RequestBody List<String> ids){
        receiveMsgService.readMsgs(ids);
    }

    @PostMapping("/readMsg/{id}")
    @ApiOperation(value = "阅读消息")
    public void readMsg(@PathVariable("id") String id){
        receiveMsgService.readMsg(id);
    }

}
