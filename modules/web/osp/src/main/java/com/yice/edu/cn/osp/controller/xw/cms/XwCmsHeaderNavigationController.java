package com.yice.edu.cn.osp.controller.xw.cms;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsHeaderNavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * @author xiezhi
 * */
@RestController
@RequestMapping("/xwCmsHeaderNavigation")
@Api(value = "/xwCmsHeaderNavigation",description = "校务CMS头部导航表模块")
public class XwCmsHeaderNavigationController {
    @Autowired
    private XwCmsHeaderNavigationService xwCmsHeaderNavigationService;

    @PostMapping("/saveXwCmsHeaderNavigation")
    @ApiOperation(value = "保存校务CMS头部导航表对象", notes = "返回保存好的校务CMS头部导航表对象", response=XwCmsHeaderNavigation.class)
    public ResponseJson saveXwCmsHeaderNavigation(
            @ApiParam(value = "校务CMS头部导航表对象", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigation.setSchoolId(mySchoolId());
        XwCmsHeaderNavigation s=xwCmsHeaderNavigationService.saveXwCmsHeaderNavigation(xwCmsHeaderNavigation);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwCmsHeaderNavigationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找校务CMS头部导航表", notes = "返回响应对象", response=XwCmsHeaderNavigation.class)
    public ResponseJson findXwCmsHeaderNavigationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsHeaderNavigation xwCmsHeaderNavigation=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationById(id);
        return new ResponseJson(xwCmsHeaderNavigation);
    }

    @PostMapping("/update/updateXwCmsHeaderNavigation")
    @ApiOperation(value = "修改校务CMS头部导航表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateXwCmsHeaderNavigation(
            @ApiParam(value = "被修改的校务CMS头部导航表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.updateXwCmsHeaderNavigation(xwCmsHeaderNavigation);
        return new ResponseJson();
    }

    @PostMapping("/update/updateXwCmsHeaderNavigationForAll")
    @ApiOperation(value = "修改校务CMS头部导航表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateXwCmsHeaderNavigationForAll(
            @ApiParam(value = "被修改的校务CMS头部导航表对象", required = true)
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigationService.updateXwCmsHeaderNavigationForAll(xwCmsHeaderNavigation);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwCmsHeaderNavigationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找校务CMS头部导航表", notes = "返回响应对象", response=XwCmsHeaderNavigation.class)
    public ResponseJson lookXwCmsHeaderNavigationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsHeaderNavigation xwCmsHeaderNavigation=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationById(id);
        return new ResponseJson(xwCmsHeaderNavigation);
    }

    @PostMapping("/findXwCmsHeaderNavigationsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表", notes = "返回响应对象", response=XwCmsHeaderNavigation.class)
    public ResponseJson findXwCmsHeaderNavigationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigation.setSchoolId(mySchoolId());
        List<XwCmsHeaderNavigation> data=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
        long count=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationCountByCondition(xwCmsHeaderNavigation);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneXwCmsHeaderNavigationByCondition")
    @ApiOperation(value = "根据条件查找单个校务CMS头部导航,结果必须为单条数据", notes = "没有时返回空", response=XwCmsHeaderNavigation.class)
    public ResponseJson findOneXwCmsHeaderNavigationByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        XwCmsHeaderNavigation one=xwCmsHeaderNavigationService.findOneXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwCmsHeaderNavigation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsHeaderNavigation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwCmsHeaderNavigationService.deleteXwCmsHeaderNavigation(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwCmsHeaderNavigationListByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表列表", notes = "返回响应对象,不包含总条数", response=XwCmsHeaderNavigation.class)
    public ResponseJson findXwCmsHeaderNavigationListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigation.setSchoolId(mySchoolId());
        List<XwCmsHeaderNavigation> data=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
        return new ResponseJson(data);
    }

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/


    @PostMapping("/saveXwCmsHeaderNavigationList")
    @ApiOperation(value = "保存校务CMS头部导航表对象列表", notes = "返回保存好的校务CMS头部导航表列表", response=XwCmsHeaderNavigation.class)
    public ResponseJson saveXwCmsHeaderNavigationList(
            @ApiParam(value = "校务CMS头部导航表对象列表", required = true)
            @RequestBody List<XwCmsHeaderNavigation> xwCmsHeaderNavigation){
        List<XwCmsHeaderNavigation> s = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(xwCmsHeaderNavigation)) {
            xwCmsHeaderNavigation.forEach(x -> x.setSchoolId(mySchoolId()));
            s=xwCmsHeaderNavigationService.saveXwCmsHeaderNavigationList(xwCmsHeaderNavigation);
        }
        return new ResponseJson(s);
    }
}
