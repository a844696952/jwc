package com.yice.edu.cn.dm.controller.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import com.yice.edu.cn.dm.service.schoolActive.DmActivityItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmActivityItem")
@Api(value = "/dmActivityItem",description = "活动节目表模块")
public class DmActivityItemController {
    @Autowired
    private DmActivityItemService dmActivityItemService;

    @GetMapping("/findDmActivityItemById/{id}")
    @ApiOperation(value = "通过id查找活动节目表", notes = "返回活动节目表对象")
    public DmActivityItem findDmActivityItemById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmActivityItemService.findDmActivityItemById(id);
    }


    @PostMapping("/saveDmActivityItem")
    @ApiOperation(value = "保存活动节目表", notes = "返回活动节目表对象")
    public DmActivityItem saveDmActivityItem(
            @ApiParam(value = "活动节目表对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.saveDmActivityItem(dmActivityItem);
        return dmActivityItem;
    }


    @PostMapping("/findDmActivityItemListByCondition")
    @ApiOperation(value = "根据条件查找活动节目表列表", notes = "返回活动节目表列表")
    public List<DmActivityItem> findDmActivityItemListByCondition(
            @ApiParam(value = "活动节目表对象")
            @RequestBody DmActivityItem dmActivityItem){
        return dmActivityItemService.findDmActivityItemListByCondition(dmActivityItem);
    }
    @PostMapping("/findDmActivityItemCountByCondition")
    @ApiOperation(value = "根据条件查找活动节目表列表个数", notes = "返回活动节目表总个数")
    public long findDmActivityItemCountByCondition(
            @ApiParam(value = "活动节目表对象")
            @RequestBody DmActivityItem dmActivityItem){
        return dmActivityItemService.findDmActivityItemCountByCondition(dmActivityItem);
    }

    @PostMapping("/updateDmActivityItem")
    @ApiOperation(value = "修改活动节目表", notes = "活动节目表对象必传")
    public void updateDmActivityItem(
            @ApiParam(value = "活动节目表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.updateDmActivityItem(dmActivityItem);
    }


    @GetMapping("/deleteDmActivityItem/{id}")
    @ApiOperation(value = "通过id删除活动节目表")
    public void deleteDmActivityItem(
            @ApiParam(value = "活动节目表对象", required = true)
            @PathVariable String id){
        dmActivityItemService.deleteDmActivityItem(id);
    }
    @PostMapping("/deleteDmActivityItemByCondition")
    @ApiOperation(value = "根据条件删除活动节目表")
    public void deleteDmActivityItemByCondition(
            @ApiParam(value = "活动节目表对象")
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.deleteDmActivityItemByCondition(dmActivityItem);
    }
    @PostMapping("/findOneDmActivityItemByCondition")
    @ApiOperation(value = "根据条件查找单个活动节目表,结果必须为单条数据", notes = "返回单个活动节目表,没有时为空")
    public DmActivityItem findOneDmActivityItemByCondition(
            @ApiParam(value = "活动节目表对象")
            @RequestBody DmActivityItem dmActivityItem){
        return dmActivityItemService.findOneDmActivityItemByCondition(dmActivityItem);
    }

    @PostMapping("/findDmActivityItemsByCondition")
    @ApiOperation(value = "通过activityId和班级id查找活动节目表", notes = "返回活动节目表对象")
    public List<DmActivityItem> findDmActivityItemByActivityId(
            @ApiParam(value = "需要用到的id", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        return dmActivityItemService.findDmActivityItemsByCondition(dmActivityItem);
    }

    @PostMapping("/saveDmActivityItemAndPeople")
    @ApiOperation(value = "保存活动节目表和人员表")
    public void saveDmActivityItemAndPeople(
            @ApiParam(value = "活动节目表对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.saveDmActivityItemAndPeople(dmActivityItem);
    }


    @PostMapping("/updateDmActivityItemAndPeople")
    @ApiOperation(value = "修改活动节目表和报名人员", notes = "活动节目表对象必传")
    public void updateDmActivityItemAndPeople(
            @ApiParam(value = "活动节目表对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.updateDmActivityItemAndPeople(dmActivityItem);
    }
}
