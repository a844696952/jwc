package com.yice.edu.cn.dm.controller.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.dm.service.active.DmActiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmActive")
@Api(value = "/dmActive",description = "云班牌学校活动模块，添加活动，删除活动，分页查询列表，修改活动，批量删除活动")
public class DmActiveController {
    @Autowired
    private DmActiveService dmActiveService;

    @GetMapping("/findDmActiveById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmActive findDmActiveById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmActiveService.findDmActiveById(id);
    }

    @PostMapping("/saveDmActive")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmActive saveDmActive(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmActive dmActive){
        dmActiveService.saveDmActive(dmActive);
        return dmActive;
    }

    @PostMapping("/findDmActiveListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmActive> findDmActiveListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findDmActiveListByCondition(dmActive);
    }
    @PostMapping("/findDmActiveCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmActiveCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findDmActiveCountByCondition(dmActive);
    }

    @PostMapping("/updateDmActive")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDmActive(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmActive dmActive){
        dmActiveService.updateDmActive(dmActive);
    }

    @GetMapping("/deleteDmActive/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmActive(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmActiveService.deleteDmActive(id);
    }

    @PostMapping("/deleteManyDmActive")
    @ApiOperation(value = "通过id删除")
    public void deleteManyDmActive(
            @ApiParam(value = "对象", required = true) @RequestBody DmActive dmActive){
        dmActiveService.deleteManyDmActive(dmActive.getRowData());
    }
    @PostMapping("/deleteDmActiveByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmActiveByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        dmActiveService.deleteDmActiveByCondition(dmActive);
    }
    @PostMapping("/findOneDmActiveByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmActive findOneDmActiveByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findOneDmActiveByCondition(dmActive);
    }

    @PostMapping("/findDmActiveListByConditionLike")
    @ApiOperation(value = "根据模糊搜索的条件查找列表", notes = "返回列表")
    public List<DmActive> findDmActiveListByConditionLike(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findDmActiveListByConditionLike(dmActive);
    }
    @PostMapping("/findDmActiveCountByConditionLike")
    @ApiOperation(value = "根据模糊搜索的条件查找列表个数", notes = "返回总个数")
    public long findDmActiveCountByConditionLike(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findDmActiveCountByConditionLike(dmActive);
    }

    @PostMapping("/findDmActiveListByConditionVue")
    @ApiOperation(value = "根据前端的条件查找列表", notes = "返回列表")
    public List<DmActive> findDmActiveListByConditionVue(
            @ApiParam(value = "对象")
            @RequestBody DmActive dmActive){
        return dmActiveService.findDmActiveListByConditionVue(dmActive);
    }
}
