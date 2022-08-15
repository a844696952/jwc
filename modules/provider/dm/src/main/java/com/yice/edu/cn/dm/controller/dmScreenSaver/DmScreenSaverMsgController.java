package com.yice.edu.cn.dm.controller.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import com.yice.edu.cn.dm.service.dmScreenSaver.DmScreenSaverMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmScreenSaverMsg")
@Api(value = "/dmScreenSaverMsg",description = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统模块")
public class DmScreenSaverMsgController {
    @Autowired
    private DmScreenSaverMsgService dmScreenSaverMsgService;

    @GetMapping("/findDmScreenSaverMsgById/{id}")
    @ApiOperation(value = "通过id查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "返回创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
    public DmScreenSaverMsg findDmScreenSaverMsgById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmScreenSaverMsgService.findDmScreenSaverMsgById(id);
    }

    @PostMapping("/saveDmScreenSaverMsg")
    @ApiOperation(value = "保存创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "返回创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
    public DmScreenSaverMsg saveDmScreenSaverMsg(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.saveDmScreenSaverMsg(dmScreenSaverMsg);
        return dmScreenSaverMsg;
    }

    @PostMapping("/findDmScreenSaverMsgListByCondition")
    @ApiOperation(value = "根据条件查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统列表", notes = "返回创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统列表")
    public List<DmScreenSaverMsg> findDmScreenSaverMsgListByCondition(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        return dmScreenSaverMsgService.findDmScreenSaverMsgListByCondition(dmScreenSaverMsg);
    }
    @PostMapping("/findDmScreenSaverMsgCountByCondition")
    @ApiOperation(value = "根据条件查找创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统列表个数", notes = "返回创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统总个数")
    public long findDmScreenSaverMsgCountByCondition(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        return dmScreenSaverMsgService.findDmScreenSaverMsgCountByCondition(dmScreenSaverMsg);
    }

    @PostMapping("/updateDmScreenSaverMsg")
    @ApiOperation(value = "修改创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统", notes = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象必传")
    public void updateDmScreenSaverMsg(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.updateDmScreenSaverMsg(dmScreenSaverMsg);
    }

    @GetMapping("/deleteDmScreenSaverMsg/{id}")
    @ApiOperation(value = "通过id删除创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统")
    public void deleteDmScreenSaverMsg(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象", required = true)
            @PathVariable String id){
        dmScreenSaverMsgService.deleteDmScreenSaverMsg(id);
    }
    @PostMapping("/deleteDmScreenSaverMsgByCondition")
    @ApiOperation(value = "根据条件删除创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统")
    public void deleteDmScreenSaverMsgByCondition(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.deleteDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }
    @PostMapping("/findOneDmScreenSaverMsgByCondition")
    @ApiOperation(value = "根据条件查找单个创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统,结果必须为单条数据", notes = "返回单个创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统,没有时为空")
    public DmScreenSaverMsg findOneDmScreenSaverMsgByCondition(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        return dmScreenSaverMsgService.findOneDmScreenSaverMsgByCondition(dmScreenSaverMsg);
    }

    @PostMapping("/batchUpdateDmScreenSaverMsg")
    @ApiOperation(value = "批量修改消息密码")
    public void batchUpdateDmScreenSaverMsg(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.batchUpdateDmScreenSaverMsg(dmScreenSaverMsg);
    }
    @PostMapping("/batchDeleteDmScreenSaverMsg")
    @ApiOperation(value = "批量删除消息")
    public void batchDeleteDmScreenSaverMsg(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.batchDeleteDmScreenSaverMsg(dmScreenSaverMsg);
    }
   
    @PostMapping("/batchUpdateDmScreenSaverMsgStatus")
    @ApiOperation(value = "根据时间修改当前学校所有的消息状态")
    public void batchUpdateDmScreenSaverMsgStatus(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        dmScreenSaverMsgService.batchUpdateDmScreenSaverMsgStatus(dmScreenSaverMsg);
    }

    @PostMapping("/getRunNingDmScreenSaverMsg")
    @ApiOperation(value = "获取当前还在进行中的消息")
    public DmScreenSaverMsg getRunNingDmScreenSaverMsg(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaverMsg dmScreenSaverMsg){
        return dmScreenSaverMsgService.getRunNingDmScreenSaverMsg(dmScreenSaverMsg);
    }
}
