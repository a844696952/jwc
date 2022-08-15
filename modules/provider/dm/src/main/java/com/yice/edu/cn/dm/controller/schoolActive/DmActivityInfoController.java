package com.yice.edu.cn.dm.controller.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByClass;
import com.yice.edu.cn.common.pojo.dm.schoolActive.ExportActiveByItem;
import com.yice.edu.cn.dm.service.schoolActive.DmActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dmActivityInfo")
@Api(value = "/dmActivityInfo",description = "活动信息表模块")
public class DmActivityInfoController {
    @Autowired
    private DmActivityInfoService dmActivityInfoService;

    @GetMapping("/findDmActivityInfoById/{id}")
    @ApiOperation(value = "通过id查找活动信息表", notes = "返回活动信息表对象")
    public DmActivityInfo findDmActivityInfoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmActivityInfoService.findDmActivityInfoById(id);
    }
    @GetMapping("/findDmActivityInfoByActivityId/{activityId}")
    @ApiOperation(value = "通过activityId查找活动信息表", notes = "返回活动信息表对象")
    public DmActivityInfo findDmActivityInfoByActivityId(
            @ApiParam(value = "需要用到的activityId", required = true)
            @PathVariable String activityId){
        return dmActivityInfoService.findDmActivityInfoByActivityId(activityId);
    }



    @PostMapping("/saveDmActivityInfo")
    @ApiOperation(value = "保存活动信息表", notes = "返回活动信息表对象")
    public void saveDmActivityInfo(
            @ApiParam(value = "活动信息表对象", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfoService.saveDmActivityInfo(dmActivityInfo);

    }

    @PostMapping("/findDmActivityInfoListByCondition")
    @ApiOperation(value = "根据条件查找活动信息表列表", notes = "返回活动信息表列表")
    public List<DmActivityInfo> findDmActivityInfoListByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findDmActivityInfoListByCondition(dmActivityInfo);
    }

    @PostMapping("/findClassItemByActiveId")
    @ApiOperation(value = "根据班级导出模板", notes = "返回活动信息表列表")
    public ExportActiveByClass findClassItemByActiveId(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findClassItemByActiveId(dmActivityInfo);
    }


    @PostMapping("/findItemByActiveId")
    @ApiOperation(value = "根据项目导出模板", notes = "返回活动信息表列表")
    public ExportActiveByItem findItemByActiveId(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findItemByActiveId(dmActivityInfo);
    }



    @PostMapping("/findDmActivityInfoCountByCondition")
    @ApiOperation(value = "根据条件查找活动信息表列表个数", notes = "返回活动信息表总个数")
    public long findDmActivityInfoCountByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findDmActivityInfoCountByCondition(dmActivityInfo);
    }

    @PostMapping("/updateDmActivityInfo")
    @ApiOperation(value = "修改活动信息表", notes = "活动信息表对象必传")
    public void updateDmActivityInfo(
            @ApiParam(value = "活动信息表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfoService.updateDmActivityInfo(dmActivityInfo);
    }

    @GetMapping("/deleteDmActivityInfo/{id}")
    @ApiOperation(value = "通过id删除活动信息表")
    public void deleteDmActivityInfo(
            @ApiParam(value = "活动信息表对象", required = true)
            @PathVariable String id){
        dmActivityInfoService.deleteDmActivityInfo(id);
    }
    @PostMapping("/deleteDmActivityInfoByCondition")
    @ApiOperation(value = "根据条件删除活动信息表")
    public void deleteDmActivityInfoByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfoService.deleteDmActivityInfoByCondition(dmActivityInfo);
    }
    @PostMapping("/findOneDmActivityInfoByCondition")
    @ApiOperation(value = "根据条件查找单个活动信息表,结果必须为单条数据", notes = "返回单个活动信息表,没有时为空")
    public DmActivityInfo findOneDmActivityInfoByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findOneDmActivityInfoByCondition(dmActivityInfo);
    }

    @PostMapping("/checkGradeSingUp")
    @ApiOperation(value = "检查该活动当前年级是否有人报名", notes = "返回响应对象")
    public boolean checkGradeSingUp(
            @RequestBody DmActivityInfo dmActivityInfo
    ){
        return dmActivityInfoService.checkGradeSingUp(dmActivityInfo);
    }

    @GetMapping("/checkItemSingUp/{itemId}")
    @ApiOperation(value = "检查该活动当前项目是否有人报名", notes = "返回响应对象")
    public Map checkItemSingUp(
            @PathVariable String itemId
    ){
        return dmActivityInfoService.checkItemSingUp(itemId);

    }

    @PostMapping("/findDmActivityInfosByCondition")
    @ApiOperation(value = "根据条件查找活动信息表列表", notes = "返回活动信息表列表")
    public List<DmActivityInfo> findDmActivityInfosByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.findDmActivityInfosByCondition(dmActivityInfo);
    }


    @PostMapping("/selectDmActivityInfosByCondition")
    @ApiOperation(value = "根据教师拥有的班牌权限所在年级活动信息表列表", notes = "返回活动信息表列表")
    public List<DmActivityInfo> selectDmActivityInfosByCondition(
            @ApiParam(value = "活动信息表对象")
            @RequestBody DmActivityInfo dmActivityInfo){
        return dmActivityInfoService.selectDmActivityInfosByCondition(dmActivityInfo);
    }

    @GetMapping("/findDmActivityInfoNoItemByActivityId/{activityId}")
    @ApiOperation(value = "通过activityId查找活动信息表", notes = "返回活动信息表对象")
    public DmActivityInfo findDmActivityInfoNoItemByActivityId(
            @ApiParam(value = "需要用到的activityId", required = true)
            @PathVariable String activityId){
        return dmActivityInfoService.findDmActivityInfoNoItemByActivityId(activityId);
    }

    @GetMapping("/findBmpDmActivityInfoByActivityId/{activityId}")
    @ApiOperation(value = "通过activityId查找活动信息表", notes = "返回活动信息表对象")
    public DmActivityInfo findBmpDmActivityInfoByActivityId(
            @ApiParam(value = "需要用到的activityId", required = true)
            @PathVariable String activityId){
        return dmActivityInfoService.findBmpDmActivityInfoByActivityId(activityId);
    }
}
