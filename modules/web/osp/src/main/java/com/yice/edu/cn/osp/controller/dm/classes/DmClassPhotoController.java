package com.yice.edu.cn.osp.controller.dm.classes;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.classes.DmClassPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.common.pojo.ts.jpush.Extras.DM_CLASSPHOTO_MSG;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassPhoto")
@Api(value = "/dmClassPhoto",description = "班级相册表模块")
public class DmClassPhotoController {
    @Autowired
    private DmClassPhotoService dmClassPhotoService;

    @PostMapping("/saveDmClassPhoto")
    @ApiOperation(value = "保存班级相册表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_CLASSPHOTO_MSG,content = "新增班级相册")
    public synchronized ResponseJson saveDmClassPhoto(
            @ApiParam(value = "班级相册表对象", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
       dmClassPhoto.setSchoolId(mySchoolId());
        DmClassPhoto s=dmClassPhotoService.saveDmClassPhoto(dmClassPhoto);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmClassPhotoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级相册表", notes = "返回响应对象")
    public ResponseJson findDmClassPhotoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassPhoto dmClassPhoto=dmClassPhotoService.findDmClassPhotoById(id);
        return new ResponseJson(dmClassPhoto);
    }

    @PostMapping("/update/updateDmClassPhoto")
    @ApiOperation(value = "修改班级相册表对象", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_CLASSPHOTO_MSG,content = "修改班级相册")
    public ResponseJson updateDmClassPhoto(
            @ApiParam(value = "被修改的班级相册表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhotoService.updateDmClassPhoto(dmClassPhoto);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassPhotoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班级相册表", notes = "返回响应对象")
    public ResponseJson lookDmClassPhotoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassPhoto dmClassPhoto=dmClassPhotoService.findDmClassPhotoById(id);
        return new ResponseJson(dmClassPhoto);
    }

    @PostMapping("/findDmClassPhotosByCondition")
    @ApiOperation(value = "根据条件查找班级相册表", notes = "返回响应对象")
    public ResponseJson findDmClassPhotosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
       dmClassPhoto.setSchoolId(mySchoolId());
        List<DmClassPhoto> data=dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        long count=dmClassPhotoService.findDmClassPhotoCountByCondition(dmClassPhoto);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个班级相册表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmClassPhotoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassPhoto dmClassPhoto){
        DmClassPhoto one=dmClassPhotoService.findOneDmClassPhotoByCondition(dmClassPhoto);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmClassPhoto/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_CLASSPHOTO_MSG,content = "删除班级相册")
    public ResponseJson deleteDmClassPhoto(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassPhotoService.deleteDmClassPhoto(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmClassPhotoListByCondition")
    @ApiOperation(value = "根据条件查找班级相册表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmClassPhotoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
       dmClassPhoto.setSchoolId(mySchoolId());
        List<DmClassPhoto> data=dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        return new ResponseJson(data);
    }

    @PostMapping("/batchDeleteDmClassPhoto")
    @ApiOperation(value = "根据id批量删除", notes = "返回响应对象")
    @EccJpush(type = Extras.DM_CLASSPHOTO_MSG,content = "删除班级相册")
    public ResponseJson batchDeleteDmClassPhoto(
            @RequestBody List<String> idlist){
        dmClassPhotoService.batchDeleteDmClassPhoto(idlist);
        return new ResponseJson();
    }

}
