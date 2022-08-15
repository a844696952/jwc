package com.yice.edu.cn.osp.controller.dm.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.osp.service.dm.school.DmSchoolBigNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmSchoolBigNews")
@Api(value = "/dmSchoolBigNews", description = "大事件模块")
public class DmSchoolBigNewsController {
    @Autowired
    private DmSchoolBigNewsService dmSchoolBigNewsService;

    @PostMapping("/saveDmSchoolBigNews")
    @ApiOperation(value = "保存大事件对象", notes = "返回响应对象")
    public ResponseJson saveDmSchoolBigNews(
            @ApiParam(value = "大事件对象", required = true)
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNews.setSchooId(mySchoolId());
        DmSchoolBigNews s = dmSchoolBigNewsService.saveDmSchoolBigNews(dmSchoolBigNews);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmSchoolBigNewsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找大事件", notes = "返回响应对象")
    public ResponseJson findDmSchoolBigNewsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable @Validated String id) {
        DmSchoolBigNews dmSchoolBigNews = dmSchoolBigNewsService.findDmSchoolBigNewsById(id);
        return new ResponseJson(dmSchoolBigNews);
    }

    @PostMapping("/update/updateDmSchoolBigNews")
    @ApiOperation(value = "修改大事件对象", notes = "返回响应对象")
    public ResponseJson updateDmSchoolBigNews(
            @ApiParam(value = "被修改的大事件对象,对象属性不为空则修改", required = true)
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNews.setSchooId(mySchoolId());
        dmSchoolBigNewsService.updateDmSchoolBigNews(dmSchoolBigNews);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmSchoolBigNewsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找大事件", notes = "返回响应对象")
    public ResponseJson lookDmSchoolBigNewsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable @Validated String id) {
        DmSchoolBigNews dmSchoolBigNews = dmSchoolBigNewsService.findDmSchoolBigNewsById(id);
        return new ResponseJson(dmSchoolBigNews);
    }

    @PostMapping("/findDmSchoolBigNewssByCondition")
    @ApiOperation(value = "根据条件查找大事件", notes = "返回响应对象")
    public ResponseJson findDmSchoolBigNewssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNews.setSchooId(mySchoolId());
        List<DmSchoolBigNews> data = dmSchoolBigNewsService.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
        long count = dmSchoolBigNewsService.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmSchoolBigNewsByCondition")
    @ApiOperation(value = "根据条件查找单个大事件,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmSchoolBigNewsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        DmSchoolBigNews one = dmSchoolBigNewsService.findOneDmSchoolBigNewsByCondition(dmSchoolBigNews);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmSchoolBigNews/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmSchoolBigNews(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable @Validated String id) {
        dmSchoolBigNewsService.deleteDmSchoolBigNews(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmSchoolBigNewsListByCondition")
    @ApiOperation(value = "根据条件查找大事件列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmSchoolBigNewsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        List<DmSchoolBigNews> data = dmSchoolBigNewsService.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
        return new ResponseJson(data);
    }

    @PostMapping("/findDmSchoolBigNewsListByactiveNameLike")
    @ApiOperation(value = "根据名称模糊查询", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmSchoolBigNewsListByactiveNameLike(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmSchoolBigNews dmSchoolBigNews) {
        dmSchoolBigNews.setSchooId(mySchoolId());
        List<DmSchoolBigNews> data = dmSchoolBigNewsService.findDmSchoolBigNewsListByactiveNameLike(dmSchoolBigNews);
        long count = dmSchoolBigNewsService.findDmSchoolBigNewsListByactiveNameLikeCount(dmSchoolBigNews);
        return new ResponseJson(data,count);
    }


}
