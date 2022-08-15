package com.yice.edu.cn.osp.controller.dm.active;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.osp.service.dm.active.DmActiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmActive")
@Api(value = "/dmActive",description = "模块")
public class DmActiveController {
    @Autowired
    private DmActiveService dmActiveService;

    @PostMapping("/saveDmActive")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveDmActive(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmActive dmActive){
       dmActive.setSchoolId(mySchoolId());
        DmActive s=dmActiveService.saveDmActive(dmActive);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmActiveById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDmActiveById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActive dmActive=dmActiveService.findDmActiveById(id);
        return new ResponseJson(dmActive);
    }

    @PostMapping("/update/updateDmActive")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmActive(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmActive dmActive){
        dmActiveService.updateDmActive(dmActive);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmActiveById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDmActiveById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActive dmActive=dmActiveService.findDmActiveById(id);
        return new ResponseJson(dmActive);
    }

    @PostMapping("/findDmActivesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmActivesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActive dmActive){
       dmActive.setSchoolId(mySchoolId());
        List<DmActive> data=dmActiveService.findDmActiveListByCondition(dmActive);
        long count=dmActiveService.findDmActiveCountByCondition(dmActive);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmActiveByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmActiveByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmActive dmActive){
        DmActive one=dmActiveService.findOneDmActiveByCondition(dmActive);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmActive/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActive(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActiveService.deleteDmActive(id);
        return new ResponseJson();
    }

    @PostMapping("/deleteManyDmActive")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteManyDmActive(@ApiParam(value = "被删除记录的id", required = true) @RequestBody DmActive dmActive){
        if(dmActive.getRowData().length<=0){
            return  new ResponseJson(false,"删除失败，请勾选数据");
        }else{
            dmActiveService.deleteManyDmActive(dmActive);
            return new ResponseJson();
        }
    }


    @PostMapping("/findDmActiveListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmActiveListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActive dmActive){
       dmActive.setSchoolId(mySchoolId());
        List<DmActive> data=dmActiveService.findDmActiveListByCondition(dmActive);
        return new ResponseJson(data);
    }

    @PostMapping("/findDmActivesByConditionLike")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmActivesByConditionLike(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActive dmActive){
        dmActive.setSchoolId(mySchoolId());
        List<DmActive> data=dmActiveService.findDmActiveListByConditionLike(dmActive);
        long count=dmActiveService.findDmActiveCountByConditionLike(dmActive);
        return new ResponseJson(data,count);
    }


}
