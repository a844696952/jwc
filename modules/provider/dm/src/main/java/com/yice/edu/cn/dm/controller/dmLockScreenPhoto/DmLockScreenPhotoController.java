package com.yice.edu.cn.dm.controller.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.dm.service.dmLockScreenPhoto.DmLockScreenPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmLockScreenPhoto")
@Api(value = "/dmLockScreenPhoto",description = "模块")
public class DmLockScreenPhotoController {
    @Autowired
    private DmLockScreenPhotoService dmLockScreenPhotoService;

    @GetMapping("/findDmLockScreenPhotoById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmLockScreenPhoto findDmLockScreenPhotoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmLockScreenPhotoService.findDmLockScreenPhotoById(id);
    }

    @PostMapping("/saveDmLockScreenPhoto")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmLockScreenPhoto saveDmLockScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.saveDmLockScreenPhoto(dmLockScreenPhoto);
        return dmLockScreenPhoto;
    }

    @PostMapping("/findDmLockScreenPhotoListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmLockScreenPhoto> findDmLockScreenPhotoListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        return dmLockScreenPhotoService.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
    }
    @PostMapping("/findDmLockScreenPhotoCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmLockScreenPhotoCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        return dmLockScreenPhotoService.findDmLockScreenPhotoCountByCondition(dmLockScreenPhoto);
    }

    @PostMapping("/updateDmLockScreenPhoto")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDmLockScreenPhoto(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.updateDmLockScreenPhoto(dmLockScreenPhoto);
    }

    @GetMapping("/deleteDmLockScreenPhoto/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmLockScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.deleteDmLockScreenPhoto(id);
    }
    @PostMapping("/deleteDmLockScreenPhotoByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmLockScreenPhotoByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.deleteDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }
    @PostMapping("/findOneDmLockScreenPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmLockScreenPhoto findOneDmLockScreenPhotoByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        return dmLockScreenPhotoService.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
    }

    @GetMapping("/updateCurrentScreenPhoto/{id}")
    @ApiOperation(value = "通过id更改当前屏保")
    public void updateCurrentScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.updateCurrentScreenPhoto(id);
    }

    @GetMapping("/cancelCurrentScreenPhoto/{id}")
    @ApiOperation(value = "通过id取消当前屏保")
    public void cancelCurrentScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.cancelCurrentScreenPhoto(id);
    }

    @PostMapping("/batchdelete")
    @ApiOperation(value = "通过ids批量删除当前屏保")
    public void batchdelete(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.batchdelete(dmLockScreenPhoto);
    }
}
