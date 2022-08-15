package com.yice.edu.cn.xw.controller.searchOwner;

import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.xw.service.searchOwner.XwSearchOwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwSearchOwner")
@Api(value = "/xwSearchOwner",description = "寻找失主表模块")
public class XwSearchOwnerController {
    @Autowired
    private XwSearchOwnerService xwSearchOwnerService;

    @GetMapping("/findXwSearchOwnerById/{id}")
    @ApiOperation(value = "通过id查找寻找失主表", notes = "返回寻找失主表对象")
    public XwSearchOwner findXwSearchOwnerById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwSearchOwnerService.findXwSearchOwnerById(id);
    }

    @PostMapping("/saveXwSearchOwner")
    @ApiOperation(value = "保存寻找失主表", notes = "返回寻找失主表对象")
    public XwSearchOwner saveXwSearchOwner(
            @ApiParam(value = "寻找失主表对象", required = true)
            @RequestBody XwSearchOwner xwSearchOwner){
        xwSearchOwnerService.saveXwSearchOwner(xwSearchOwner);
        return xwSearchOwner;
    }

    @PostMapping("/findXwSearchOwnerListByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表列表", notes = "返回寻找失主表列表")
    public List<XwSearchOwner> findXwSearchOwnerListByCondition(
            @ApiParam(value = "寻找失主表对象")
            @RequestBody XwSearchOwner xwSearchOwner){
        return xwSearchOwnerService.findXwSearchOwnerListByCondition(xwSearchOwner);
    }
    @PostMapping("/findXwSearchOwnerCountByCondition")
    @ApiOperation(value = "根据条件查找寻找失主表列表个数", notes = "返回寻找失主表总个数")
    public long findXwSearchOwnerCountByCondition(
            @ApiParam(value = "寻找失主表对象")
            @RequestBody XwSearchOwner xwSearchOwner){
        return xwSearchOwnerService.findXwSearchOwnerCountByCondition(xwSearchOwner);
    }

    @PostMapping("/updateXwSearchOwner")
    @ApiOperation(value = "修改寻找失主表", notes = "寻找失主表对象必传")
    public void updateXwSearchOwner(
            @ApiParam(value = "寻找失主表对象,对象属性不为空则修改", required = true)
            @RequestBody XwSearchOwner xwSearchOwner){
        xwSearchOwnerService.updateXwSearchOwner(xwSearchOwner);
    }

    @GetMapping("/deleteXwSearchOwner/{id}")
    @ApiOperation(value = "通过id删除寻找失主表")
    public void deleteXwSearchOwner(
            @ApiParam(value = "寻找失主表对象", required = true)
            @PathVariable String id){
        xwSearchOwnerService.deleteXwSearchOwner(id);
    }
    @PostMapping("/deleteXwSearchOwnerByCondition")
    @ApiOperation(value = "根据条件删除寻找失主表")
    public void deleteXwSearchOwnerByCondition(
            @ApiParam(value = "寻找失主表对象")
            @RequestBody XwSearchOwner xwSearchOwner){
        xwSearchOwnerService.deleteXwSearchOwnerByCondition(xwSearchOwner);
    }
    @PostMapping("/findOneXwSearchOwnerByCondition")
    @ApiOperation(value = "根据条件查找单个寻找失主表,结果必须为单条数据", notes = "返回单个寻找失主表,没有时为空")
    public XwSearchOwner findOneXwSearchOwnerByCondition(
            @ApiParam(value = "寻找失主表对象")
            @RequestBody XwSearchOwner xwSearchOwner){
        return xwSearchOwnerService.findOneXwSearchOwnerByCondition(xwSearchOwner);
    }
}
