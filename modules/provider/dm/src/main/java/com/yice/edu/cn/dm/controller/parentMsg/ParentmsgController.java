package com.yice.edu.cn.dm.controller.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import com.yice.edu.cn.dm.service.parentMsg.ParentmsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parentmsg")
@Api(value = "/parentmsg",description = "班牌家长推送信息表模块")
public class ParentmsgController {
    @Autowired
    private ParentmsgService parentmsgService;

    @GetMapping("/findParentmsgById/{id}")
    @ApiOperation(value = "通过id查找班牌家长推送信息表", notes = "返回班牌家长推送信息表对象")
    public Parentmsg findParentmsgById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return parentmsgService.findParentmsgById(id);
    }

    @PostMapping("/saveParentmsg")
    @ApiOperation(value = "保存班牌家长推送信息表", notes = "返回班牌家长推送信息表对象")
    public Parentmsg saveParentmsg(
            @ApiParam(value = "班牌家长推送信息表对象", required = true)
            @RequestBody Parentmsg parentmsg){
        parentmsgService.saveParentmsg(parentmsg);
        return parentmsg;
    }

    @PostMapping("/findParentmsgListByCondition")
    @ApiOperation(value = "根据条件查找班牌家长推送信息表列表", notes = "返回班牌家长推送信息表列表")
    public List<Parentmsg> findParentmsgListByCondition(
            @ApiParam(value = "班牌家长推送信息表对象")
            @RequestBody Parentmsg parentmsg){
        return parentmsgService.findParentmsgListByCondition(parentmsg);
    }
    @PostMapping("/findParentmsgCountByCondition")
    @ApiOperation(value = "根据条件查找班牌家长推送信息表列表个数", notes = "返回班牌家长推送信息表总个数")
    public long findParentmsgCountByCondition(
            @ApiParam(value = "班牌家长推送信息表对象")
            @RequestBody Parentmsg parentmsg){
        return parentmsgService.findParentmsgCountByCondition(parentmsg);
    }

    @PostMapping("/updateParentmsg")
    @ApiOperation(value = "修改班牌家长推送信息表", notes = "班牌家长推送信息表对象必传")
    public void updateParentmsg(
            @ApiParam(value = "班牌家长推送信息表对象,对象属性不为空则修改", required = true)
            @RequestBody Parentmsg parentmsg){
        parentmsgService.updateParentmsg(parentmsg);
    }

    @GetMapping("/deleteParentmsg/{id}")
    @ApiOperation(value = "通过id删除班牌家长推送信息表")
    public void deleteParentmsg(
            @ApiParam(value = "班牌家长推送信息表对象", required = true)
            @PathVariable String id){
        parentmsgService.deleteParentmsg(id);
    }
    @PostMapping("/deleteParentmsgByCondition")
    @ApiOperation(value = "根据条件删除班牌家长推送信息表")
    public void deleteParentmsgByCondition(
            @ApiParam(value = "班牌家长推送信息表对象")
            @RequestBody Parentmsg parentmsg){
        parentmsgService.deleteParentmsgByCondition(parentmsg);
    }

    @PostMapping("/findOneParentmsgByCondition")
    @ApiOperation(value = "根据条件查找单个班牌家长推送信息表,结果必须为单条数据", notes = "返回单个班牌家长推送信息表,没有时为空")
    public Parentmsg findOneParentmsgByCondition(
            @ApiParam(value = "班牌家长推送信息表对象")
            @RequestBody Parentmsg parentmsg){
        return parentmsgService.findOneParentmsgByCondition(parentmsg);
    }

    @PostMapping("/updateParentmsgByStuCardNo")
    @ApiOperation(value = "修改班牌家长推送信息表", notes = "班牌家长推送信息表对象必传")
    public void updateParentmsgByStuCardNo(@RequestBody Parentmsg parentmsg) {
        if(parentmsg.getSchoolId() == null){
            parentmsg.setSchoolId("");
        }
        parentmsgService.updateParentmsgByStuCardNo(parentmsg);
    }

    @PostMapping("/deleteParentMsgTwoDayBefore")
    @ApiOperation(value = "删除两天前的家长消息", notes = "删除两天前的家长消息")
    public void deleteParentMsgTwoDayBefore(){
        parentmsgService.deleteParentMsgTwoDayBefore();
    }

    @GetMapping("/deleteParentMsgBySchoolId/{schoolId}")
    @ApiOperation(value = "删除该学校的家校互动消息", notes = "删除两天前的家长消息")
    public void deleteParentMsgBySchoolId(@PathVariable("schoolId")String schoolId){
        parentmsgService.deleteParentMsgBySchoolId(schoolId);
    }
}
