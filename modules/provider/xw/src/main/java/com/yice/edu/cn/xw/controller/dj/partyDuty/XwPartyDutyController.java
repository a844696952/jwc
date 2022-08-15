package com.yice.edu.cn.xw.controller.dj.partyDuty;

import com.yice.edu.cn.common.pojo.xw.dj.partyDuty.XwPartyDuty;
import com.yice.edu.cn.xw.service.dj.partyDuty.XwPartyDutyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwPartyDuty")
@Api(value = "/xwPartyDuty",description = "党员职务管理模块")
public class XwPartyDutyController {
    @Autowired
    private XwPartyDutyService xwPartyDutyService;

    @GetMapping("/findXwPartyDutyById/{id}")
    @ApiOperation(value = "通过id查找党员职务管理", notes = "返回党员职务管理对象")
    public XwPartyDuty findXwPartyDutyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwPartyDutyService.findXwPartyDutyById(id);
    }

    @PostMapping("/saveXwPartyDuty")
    @ApiOperation(value = "保存党员职务管理", notes = "返回党员职务管理对象")
    public XwPartyDuty saveXwPartyDuty(
            @ApiParam(value = "党员职务管理对象", required = true)
            @RequestBody XwPartyDuty xwPartyDuty){
        xwPartyDutyService.saveXwPartyDuty(xwPartyDuty);
        return xwPartyDuty;
    }

    @PostMapping("/findXwPartyDutyListByCondition")
    @ApiOperation(value = "根据条件查找党员职务管理列表", notes = "返回党员职务管理列表")
    public List<XwPartyDuty> findXwPartyDutyListByCondition(
            @ApiParam(value = "党员职务管理对象")
            @RequestBody XwPartyDuty xwPartyDuty){
        return xwPartyDutyService.findXwPartyDutyListByCondition(xwPartyDuty);
    }
    @PostMapping("/findXwPartyDutyCountByCondition")
    @ApiOperation(value = "根据条件查找党员职务管理列表个数", notes = "返回党员职务管理总个数")
    public long findXwPartyDutyCountByCondition(
            @ApiParam(value = "党员职务管理对象")
            @RequestBody XwPartyDuty xwPartyDuty){
        return xwPartyDutyService.findXwPartyDutyCountByCondition(xwPartyDuty);
    }

    @PostMapping("/updateXwPartyDuty")
    @ApiOperation(value = "修改党员职务管理", notes = "党员职务管理对象必传")
    public void updateXwPartyDuty(
            @ApiParam(value = "党员职务管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyDuty xwPartyDuty){
        xwPartyDutyService.updateXwPartyDuty(xwPartyDuty);
    }

    @GetMapping("/deleteXwPartyDuty/{id}")
    @ApiOperation(value = "通过id删除党员职务管理")
    public void deleteXwPartyDuty(
            @ApiParam(value = "党员职务管理对象", required = true)
            @PathVariable String id){
        xwPartyDutyService.deleteXwPartyDuty(id);
    }
    @PostMapping("/deleteXwPartyDutyByCondition")
    @ApiOperation(value = "根据条件删除党员职务管理")
    public void deleteXwPartyDutyByCondition(
            @ApiParam(value = "党员职务管理对象")
            @RequestBody XwPartyDuty xwPartyDuty){
        xwPartyDutyService.deleteXwPartyDutyByCondition(xwPartyDuty);
    }
    @PostMapping("/findOneXwPartyDutyByCondition")
    @ApiOperation(value = "根据条件查找单个党员职务管理,结果必须为单条数据", notes = "返回单个党员职务管理,没有时为空")
    public XwPartyDuty findOneXwPartyDutyByCondition(
            @ApiParam(value = "党员职务管理对象")
            @RequestBody XwPartyDuty xwPartyDuty){
        return xwPartyDutyService.findOneXwPartyDutyByCondition(xwPartyDuty);
    }

    @GetMapping("/findXwPartyDutyByName/{name}")
    @ApiOperation(value = "通过name查找党员职务管理", notes = "返回党员职务管理对象")
    public XwPartyDuty findXwPartyDutyByName(
            @ApiParam(value = "需要用到的name", required = true)
            @PathVariable String name){
        return xwPartyDutyService.findXwPartyDutyByName(name);
    }
}
