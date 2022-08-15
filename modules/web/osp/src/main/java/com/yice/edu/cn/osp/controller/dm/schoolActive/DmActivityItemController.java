package com.yice.edu.cn.osp.controller.dm.schoolActive;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import com.yice.edu.cn.osp.service.dm.schoolActive.DmActivityItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmActivityItem")
@Api(value = "/dmActivityItem",description = "活动节目表模块")
public class DmActivityItemController {
    @Autowired
    private DmActivityItemService dmActivityItemService;

    @PostMapping("/saveDmActivityItem")
    @ApiOperation(value = "保存活动节目表对象", notes = "返回保存好的活动节目表对象", response= DmActivityItem.class)
    public ResponseJson saveDmActivityItem(
            @ApiParam(value = "活动节目表对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
       dmActivityItem.setSchoolId(mySchoolId());
        DmActivityItem s=dmActivityItemService.saveDmActivityItem(dmActivityItem);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmActivityItemById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找活动节目表", notes = "返回响应对象", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivityItem dmActivityItem=dmActivityItemService.findDmActivityItemById(id);
        return new ResponseJson(dmActivityItem);
    }

    @PostMapping("/update/updateDmActivityItem")
    @ApiOperation(value = "修改活动节目表对象", notes = "返回响应对象")
    public ResponseJson updateDmActivityItem(
            @ApiParam(value = "被修改的活动节目表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.updateDmActivityItem(dmActivityItem);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmActivityItemById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找活动节目表", notes = "返回响应对象", response=DmActivityItem.class)
    public ResponseJson lookDmActivityItemById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivityItem dmActivityItem=dmActivityItemService.findDmActivityItemById(id);
        return new ResponseJson(dmActivityItem);
    }

    @PostMapping("/findDmActivityItemsByCondition")
    @ApiOperation(value = "根据条件查找活动节目表", notes = "返回响应对象", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityItem dmActivityItem){
       dmActivityItem.setSchoolId(mySchoolId());
        List<DmActivityItem> data=dmActivityItemService.findDmActivityItemListByCondition(dmActivityItem);
        long count=dmActivityItemService.findDmActivityItemCountByCondition(dmActivityItem);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmActivityItemByCondition")
    @ApiOperation(value = "根据条件查找单个活动节目表,结果必须为单条数据", notes = "没有时返回空", response=DmActivityItem.class)
    public ResponseJson findOneDmActivityItemByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmActivityItem dmActivityItem){
        DmActivityItem one=dmActivityItemService.findOneDmActivityItemByCondition(dmActivityItem);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmActivityItem/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActivityItem(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActivityItemService.deleteDmActivityItem(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmActivityItemListByCondition")
    @ApiOperation(value = "根据条件查找活动节目表列表", notes = "返回响应对象,不包含总条数", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityItem dmActivityItem){
       dmActivityItem.setSchoolId(mySchoolId());
        List<DmActivityItem> data=dmActivityItemService.findDmActivityItemListByCondition(dmActivityItem);
        return new ResponseJson(data);
    }



}
