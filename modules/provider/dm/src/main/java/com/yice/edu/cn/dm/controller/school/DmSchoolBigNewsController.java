package com.yice.edu.cn.dm.controller.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolBigNews;
import com.yice.edu.cn.dm.service.school.DmSchoolBigNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmSchoolBigNews")
@Api(value = "/dmSchoolBigNews",description = "大事件模块")
public class DmSchoolBigNewsController {
    @Autowired
    private DmSchoolBigNewsService dmSchoolBigNewsService;

    @GetMapping("/findDmSchoolBigNewsById/{id}")
    @ApiOperation(value = "通过id查找大事件", notes = "返回大事件对象")
    public DmSchoolBigNews findDmSchoolBigNewsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmSchoolBigNewsService.findDmSchoolBigNewsById(id);
    }

    @PostMapping("/saveDmSchoolBigNews")
    @ApiOperation(value = "保存大事件", notes = "返回大事件对象")
    public DmSchoolBigNews saveDmSchoolBigNews(
            @ApiParam(value = "大事件对象", required = true)
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        dmSchoolBigNewsService.saveDmSchoolBigNews(dmSchoolBigNews);
        return dmSchoolBigNews;
    }

    @PostMapping("/findDmSchoolBigNewsListByCondition")
    @ApiOperation(value = "根据条件查找大事件列表", notes = "返回大事件列表")
    public List<DmSchoolBigNews> findDmSchoolBigNewsListByCondition(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        return dmSchoolBigNewsService.findDmSchoolBigNewsListByCondition(dmSchoolBigNews);
    }
    @PostMapping("/findDmSchoolBigNewsCountByCondition")
    @ApiOperation(value = "根据条件查找大事件列表个数", notes = "返回大事件总个数")
    public long findDmSchoolBigNewsCountByCondition(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        return dmSchoolBigNewsService.findDmSchoolBigNewsCountByCondition(dmSchoolBigNews);
    }

    @PostMapping("/updateDmSchoolBigNews")
    @ApiOperation(value = "修改大事件", notes = "大事件对象必传")
    public void updateDmSchoolBigNews(
            @ApiParam(value = "大事件对象,对象属性不为空则修改", required = true)
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        dmSchoolBigNewsService.updateDmSchoolBigNews(dmSchoolBigNews);
    }

    @GetMapping("/deleteDmSchoolBigNews/{id}")
    @ApiOperation(value = "通过id删除大事件")
    public void deleteDmSchoolBigNews(
            @ApiParam(value = "大事件对象", required = true)
            @PathVariable String id){
        dmSchoolBigNewsService.deleteDmSchoolBigNews(id);
    }
    @PostMapping("/deleteDmSchoolBigNewsByCondition")
    @ApiOperation(value = "根据条件删除大事件")
    public void deleteDmSchoolBigNewsByCondition(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        dmSchoolBigNewsService.deleteDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }
    @PostMapping("/findOneDmSchoolBigNewsByCondition")
    @ApiOperation(value = "根据条件查找单个大事件,结果必须为单条数据", notes = "返回单个大事件,没有时为空")
    public DmSchoolBigNews findOneDmSchoolBigNewsByCondition(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        return dmSchoolBigNewsService.findOneDmSchoolBigNewsByCondition(dmSchoolBigNews);
    }

    @PostMapping("/findDmSchoolBigNewsListByactiveNameLike")
    @ApiOperation(value = "根据名称模糊查询大事件", notes = "返回符合模糊查询条件的所有数据")
    public List<DmSchoolBigNews> findDmSchoolBigNewsListByactiveNameLike(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        return dmSchoolBigNewsService.findDmSchoolBigNewsListByactiveNameLike(dmSchoolBigNews);
    }

    @PostMapping("/findDmSchoolBigNewsListByactiveNameLikeCount")
    @ApiOperation(value = "根据名称模糊查询大事件", notes = "返回符合模糊查询条件的所有数据条数")
    public Long findDmSchoolBigNewsListByactiveNameLikeCount(
            @ApiParam(value = "大事件对象")
            @RequestBody DmSchoolBigNews dmSchoolBigNews){
        return dmSchoolBigNewsService.findDmSchoolBigNewsListByactiveNameLikeCount(dmSchoolBigNews);
    }
}
