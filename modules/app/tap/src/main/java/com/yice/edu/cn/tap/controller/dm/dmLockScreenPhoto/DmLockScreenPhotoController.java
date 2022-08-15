package com.yice.edu.cn.tap.controller.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto.DmLockScreenPhoto;
import com.yice.edu.cn.tap.service.dm.dmLockScreenPhoto.DmLockScreenPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

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


    @PostMapping("/updateDmLockScreenPhoto")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmLockScreenPhoto(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmLockScreenPhoto dmLockScreenPhoto){
        dmLockScreenPhotoService.updateDmLockScreenPhoto(dmLockScreenPhoto);
        return new ResponseJson();
    }

    @GetMapping("/findDmLockScreenPhotoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DmLockScreenPhoto.class)
    public ResponseJson findDmLockScreenPhotoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmLockScreenPhoto dmLockScreenPhoto=dmLockScreenPhotoService.findDmLockScreenPhotoById(id);
        return new ResponseJson(dmLockScreenPhoto);
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



}
