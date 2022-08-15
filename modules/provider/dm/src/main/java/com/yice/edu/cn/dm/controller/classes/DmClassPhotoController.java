package com.yice.edu.cn.dm.controller.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.dm.service.classes.DmClassPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassPhoto")
@Api(value = "/dmClassPhoto",description = "班级相册模块")
public class DmClassPhotoController {
    @Autowired
    private DmClassPhotoService dmClassPhotoService;

    @GetMapping("/findDmClassPhotoById/{id}")
    @ApiOperation(value = "通过id查找班级相册表", notes = "返回班级相册表对象")
    public DmClassPhoto findDmClassPhotoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmClassPhotoService.findDmClassPhotoById(id);
    }

    @PostMapping("/saveDmClassPhoto")
    @ApiOperation(value = "保存班级相册表", notes = "返回班级相册表对象")
    public DmClassPhoto saveDmClassPhoto(
            @ApiParam(value = "班级相册表对象", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhotoService.saveDmClassPhoto(dmClassPhoto);
        return dmClassPhoto;
    }

    @PostMapping("/findDmClassPhotoListByCondition")
    @ApiOperation(value = "根据条件查找班级相册表列表", notes = "返回班级相册表列表")
    public List<DmClassPhoto> findDmClassPhotoListByCondition(
            @ApiParam(value = "班级相册表对象")
            @RequestBody DmClassPhoto dmClassPhoto){
        return dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
    }
    @PostMapping("/findDmClassPhotoCountByCondition")
    @ApiOperation(value = "根据条件查找班级相册表列表个数", notes = "返回班级相册表总个数")
    public long findDmClassPhotoCountByCondition(
            @ApiParam(value = "班级相册表对象")
            @RequestBody DmClassPhoto dmClassPhoto){
        return dmClassPhotoService.findDmClassPhotoCountByCondition(dmClassPhoto);
    }

    @PostMapping("/updateDmClassPhoto")
    @ApiOperation(value = "修改班级相册表", notes = "班级相册表对象必传")
    public void updateDmClassPhoto(
            @ApiParam(value = "班级相册表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhotoService.updateDmClassPhoto(dmClassPhoto);
    }

    @GetMapping("/deleteDmClassPhoto/{id}")
    @ApiOperation(value = "通过id删除班级相册表")
    public void deleteDmClassPhoto(
            @ApiParam(value = "班级相册表对象", required = true)
            @PathVariable String id){
        dmClassPhotoService.deleteDmClassPhoto(id);
    }
    @PostMapping("/deleteDmClassPhotoByCondition")
    @ApiOperation(value = "根据条件删除班级相册表")
    public void deleteDmClassPhotoByCondition(
            @ApiParam(value = "班级相册表对象")
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhotoService.deleteDmClassPhotoByCondition(dmClassPhoto);
    }

    @PostMapping("/batchDeleteDmClassPhoto")
    @ApiOperation(value = "根据条件删除批量班级相册表")
    public void batchDeleteDmClassPhoto(
            @ApiParam(value = "班级相册表对象")
            @RequestBody List<String> idlist){
        dmClassPhotoService.batchDeleteDmClassPhoto(idlist);
    }
    @PostMapping("/findOneDmClassPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个班级相册表,结果必须为单条数据", notes = "返回单个班级相册表,没有时为空")
    public DmClassPhoto findOneDmClassPhotoByCondition(
            @ApiParam(value = "班级相册表对象")
            @RequestBody DmClassPhoto dmClassPhoto){
        return dmClassPhotoService.findOneDmClassPhotoByCondition(dmClassPhoto);
    }
}
