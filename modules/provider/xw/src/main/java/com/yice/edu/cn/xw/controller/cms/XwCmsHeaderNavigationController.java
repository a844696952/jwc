package com.yice.edu.cn.xw.controller.cms;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.xw.service.cms.XwCmsHeaderNavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsHeaderNavigation")
@Api(value = "/xwCmsHeaderNavigation",description = "校务CMS头部导航表模块")
public class XwCmsHeaderNavigationController {
    @Autowired
    private XwCmsHeaderNavigationService xwCmsHeaderNavigationService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findXwCmsHeaderNavigationById/{id}")
    @ApiOperation(value = "通过id查找校务CMS头部导航表", notes = "返回校务CMS头部导航表对象")
    public XwCmsHeaderNavigation findXwCmsHeaderNavigationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwCmsHeaderNavigationService.findXwCmsHeaderNavigationById(id);
    }

    @PostMapping("/saveXwCmsHeaderNavigation")
    @ApiOperation(value = "保存校务CMS头部导航表", notes = "返回校务CMS头部导航表对象")
    public XwCmsHeaderNavigation saveXwCmsHeaderNavigation(
            @ApiParam(value = "校务CMS头部导航表对象", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.saveXwCmsHeaderNavigation(xwCmsHeaderNavigation);
        return xwCmsHeaderNavigation;
    }

    @PostMapping("/batchSaveXwCmsHeaderNavigation")
    @ApiOperation(value = "批量保存校务CMS头部导航表")
    public void batchSaveXwCmsHeaderNavigation(
            @ApiParam(value = "校务CMS头部导航表对象集合", required = true)
            @RequestBody List<XwCmsHeaderNavigation> xwCmsHeaderNavigations){
        xwCmsHeaderNavigationService.batchSaveXwCmsHeaderNavigation(xwCmsHeaderNavigations);
    }

    @PostMapping("/findXwCmsHeaderNavigationListByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表列表", notes = "返回校务CMS头部导航表列表")
    public List<XwCmsHeaderNavigation> findXwCmsHeaderNavigationListByCondition(
            @ApiParam(value = "校务CMS头部导航表对象")
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        return xwCmsHeaderNavigationService.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
    }
    @PostMapping("/findXwCmsHeaderNavigationCountByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表列表个数", notes = "返回校务CMS头部导航表总个数")
    public long findXwCmsHeaderNavigationCountByCondition(
            @ApiParam(value = "校务CMS头部导航表对象")
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        return xwCmsHeaderNavigationService.findXwCmsHeaderNavigationCountByCondition(xwCmsHeaderNavigation);
    }

    @PostMapping("/updateXwCmsHeaderNavigation")
    @ApiOperation(value = "修改校务CMS头部导航表有值的字段", notes = "校务CMS头部导航表对象必传")
    public void updateXwCmsHeaderNavigation(
            @ApiParam(value = "校务CMS头部导航表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.updateXwCmsHeaderNavigation(xwCmsHeaderNavigation);
    }
    @PostMapping("/updateXwCmsHeaderNavigationForAll")
    @ApiOperation(value = "修改校务CMS头部导航表所有字段", notes = "校务CMS头部导航表对象必传")
    public void updateXwCmsHeaderNavigationForAll(
            @ApiParam(value = "校务CMS头部导航表对象", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.updateXwCmsHeaderNavigationForAll(xwCmsHeaderNavigation);
    }

    @GetMapping("/deleteXwCmsHeaderNavigation/{id}")
    @ApiOperation(value = "通过id删除校务CMS头部导航表")
    public void deleteXwCmsHeaderNavigation(
            @ApiParam(value = "校务CMS头部导航表对象", required = true)
            @PathVariable String id){
        xwCmsHeaderNavigationService.deleteXwCmsHeaderNavigation(id);
    }
    @PostMapping("/deleteXwCmsHeaderNavigationByCondition")
    @ApiOperation(value = "根据条件删除校务CMS头部导航表")
    public void deleteXwCmsHeaderNavigationByCondition(
            @ApiParam(value = "校务CMS头部导航表对象")
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.deleteXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }
    @PostMapping("/findOneXwCmsHeaderNavigationByCondition")
    @ApiOperation(value = "根据条件查找单个校务CMS头部导航表,结果必须为单条数据", notes = "返回单个校务CMS头部导航表,没有时为空")
    public XwCmsHeaderNavigation findOneXwCmsHeaderNavigationByCondition(
            @ApiParam(value = "校务CMS头部导航表对象")
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        return xwCmsHeaderNavigationService.findOneXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @PostMapping("/saveXwCmsHeaderNavigationList")
    @ApiOperation(value = "保存校务CMS头部导航表对象列表", notes = "返回保存好的校务CMS头部导航表列表", response=XwCmsHeaderNavigation.class)
    public List<XwCmsHeaderNavigation> saveXwCmsHeaderNavigationList(
            @ApiParam(value = "校务CMS头部导航表对象列表", required = true)
            @RequestBody List<XwCmsHeaderNavigation> xwCmsHeaderNavigation){

        return xwCmsHeaderNavigationService.saveXwCmsHeaderNavigationList(xwCmsHeaderNavigation);
    }


}
