package com.yice.edu.cn.osp.controller.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.osp.service.dm.dmLockScreenPhoto.DmLockScreenPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmLockScreenPhoto")
@Api(value = "/dmLockScreenPhoto",description = "模块")
public class DmLockScreenPhotoController {
    @Autowired
    private DmLockScreenPhotoService dmLockScreenPhotoService;

    @PostMapping("/saveDmLockScreenPhoto")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=DmLockScreenPhoto.class)
    public ResponseJson saveDmLockScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
       dmLockScreenPhoto.setSchoolId(mySchoolId());
        DmLockScreenPhoto s=dmLockScreenPhotoService.saveDmLockScreenPhoto(dmLockScreenPhoto);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmLockScreenPhotoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DmLockScreenPhoto.class)
    public ResponseJson findDmLockScreenPhotoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmLockScreenPhoto dmLockScreenPhoto=dmLockScreenPhotoService.findDmLockScreenPhotoById(id);
        return new ResponseJson(dmLockScreenPhoto);
    }

    @PostMapping("/update/updateDmLockScreenPhoto")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmLockScreenPhoto(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.updateDmLockScreenPhoto(dmLockScreenPhoto);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmLockScreenPhotoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DmLockScreenPhoto.class)
    public ResponseJson lookDmLockScreenPhotoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmLockScreenPhoto dmLockScreenPhoto=dmLockScreenPhotoService.findDmLockScreenPhotoById(id);
        return new ResponseJson(dmLockScreenPhoto);
    }

    @PostMapping("/findDmLockScreenPhotosByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DmLockScreenPhoto.class)
    public ResponseJson findDmLockScreenPhotosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
       dmLockScreenPhoto.setSchoolId(mySchoolId());
        List<DmLockScreenPhoto> data=dmLockScreenPhotoService.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
        long count=dmLockScreenPhotoService.findDmLockScreenPhotoCountByCondition(dmLockScreenPhoto);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmLockScreenPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DmLockScreenPhoto.class)
    public ResponseJson findOneDmLockScreenPhotoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        DmLockScreenPhoto one=dmLockScreenPhotoService.findOneDmLockScreenPhotoByCondition(dmLockScreenPhoto);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmLockScreenPhoto/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmLockScreenPhoto(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.deleteDmLockScreenPhoto(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmLockScreenPhotoListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DmLockScreenPhoto.class)
    public ResponseJson findDmLockScreenPhotoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
       dmLockScreenPhoto.setSchoolId(mySchoolId());
        List<DmLockScreenPhoto> data=dmLockScreenPhotoService.findDmLockScreenPhotoListByCondition(dmLockScreenPhoto);
        return new ResponseJson(data);
    }

    @GetMapping("/updateCurrentScreenPhoto/{id}")
    @ApiOperation(value = "通过id更改当前屏保", notes = "返回响应对象")
    public ResponseJson updateCurrentScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.updateCurrentScreenPhoto(id);
        return new ResponseJson();
    }

    @GetMapping("/cancelCurrentScreenPhoto/{id}")
    @ApiOperation(value = "通过id取消当前屏保", notes = "返回响应对象")
    public ResponseJson cancelCurrentScreenPhoto(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmLockScreenPhotoService.cancelCurrentScreenPhoto(id);
        return new ResponseJson();
    }

    @PostMapping("/batchdelete")
    @ApiOperation(value = "通过ids批量删除当前屏保")
    public ResponseJson batchdelete(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.batchdelete(dmLockScreenPhoto);
        return new ResponseJson();
    }

}
