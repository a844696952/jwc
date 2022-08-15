package com.yice.edu.cn.osp.controller.dm.classes;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dm.classes.DmClassDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/dmClassDesc")
@Api(value = "/dmClassDesc",description = "班级简介模块")
public class DmClassDescController {
    @Autowired
    private DmClassDescService dmClassDescService;

    @PostMapping("/saveDmClassDesc")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveDmClassDesc(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassDesc dmClassDesc){
        dmClassDesc.setSchoolId(LoginInterceptor.mySchoolId());
        long cnt = dmClassDescService.findDmClassDescCountByCondition(dmClassDesc);
        if(cnt>0){
            dmClassDescService.updateDmClassDesc(dmClassDesc);
        }else{
            dmClassDescService.saveDmClassDesc(dmClassDesc);
        }
        return new ResponseJson(true);
    }

    @GetMapping("/update/findDmClassDescById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDmClassDescById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassDesc dmClassDesc=dmClassDescService.findDmClassDescById(id);
        return new ResponseJson(dmClassDesc);
    }

    @PostMapping("/update/updateDmClassDesc")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmClassDesc(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassDesc dmClassDesc){
        dmClassDescService.updateDmClassDesc(dmClassDesc);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassDescById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDmClassDescById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassDesc dmClassDesc=dmClassDescService.findDmClassDescById(id);
        return new ResponseJson(dmClassDesc);
    }

    @PostMapping("/findDmClassDescsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmClassDescsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassDesc dmClassDesc){
        List<DmClassDesc> data=dmClassDescService.findDmClassDescListByCondition(dmClassDesc);
        long count=dmClassDescService.findDmClassDescCountByCondition(dmClassDesc);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassDescByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmClassDescByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassDesc dmClassDesc){
        try{
            DmClassDesc one=dmClassDescService.findOneDmClassDescByCondition(dmClassDesc);
            if(one!=null){
                if(one.getClassAlias()==null){
                    one.setClassAlias("");
                }
            }
            return new ResponseJson(one);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseJson();
        }
    }
    @GetMapping("/deleteDmClassDesc/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmClassDesc(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassDescService.deleteDmClassDesc(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmClassDescListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmClassDescListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassDesc dmClassDesc){
        List<DmClassDesc> data=dmClassDescService.findDmClassDescListByCondition(dmClassDesc);
        return new ResponseJson(data);
    }

    @GetMapping("/clearClassDescAndPhoto/{classId}")
    @ApiOperation(value = "清除班级简介以及班级相册", notes = "返回响应对象")
    public ResponseJson clearClassDescAndPhoto(@ApiParam(value = "班级Id") @PathVariable String classId) {
        dmClassDescService.clearClassDescAndPhoto(classId);
        return new ResponseJson();
    }

    @PostMapping("/batchClearClassDescAndPhoto")
    @ApiOperation(value = "批量清除班级简介以及班级相册", notes = "返回响应对象")
    public ResponseJson batchClearClassDescAndPhoto(@ApiParam(value = "班级Id字符串集合")
                                                        @RequestBody DmClassDesc dmClassDesc) {
        dmClassDescService.batchClearClassDescAndPhoto(dmClassDesc.getClassId());
        return new ResponseJson();
    }
}
