package com.yice.edu.cn.osp.controller.dm.dmHonourRollType;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.osp.service.dm.dmHonourRollType.DmHonourRollTypeService;
import com.yice.edu.cn.osp.service.dm.honourRoll.DmHonourRollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmHonourRollType")
@Api(value = "/dmHonourRollType",description = "光荣榜类型模块")
public class DmHonourRollTypeController {
    @Autowired
    private DmHonourRollTypeService dmHonourRollTypeService;
    @Autowired
    private DmHonourRollService dmHonourRollService;

    @PostMapping("/saveDmHonourRollType")
    @ApiOperation(value = "保存光荣榜类型对象", notes = "返回保存好的光荣榜类型对象", response=DmHonourRollType.class)
    public ResponseJson saveDmHonourRollType(
            @ApiParam(value = "光荣榜类型对象", required = true)
            @RequestBody DmHonourRollType dmHonourRollType){
       dmHonourRollType.setSchoolId(mySchoolId());
        DmHonourRollType s=dmHonourRollTypeService.saveDmHonourRollType(dmHonourRollType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmHonourRollTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找光荣榜类型", notes = "返回响应对象", response=DmHonourRollType.class)
    public ResponseJson findDmHonourRollTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRollType dmHonourRollType=dmHonourRollTypeService.findDmHonourRollTypeById(id);
        return new ResponseJson(dmHonourRollType);
    }

    @PostMapping("/update/updateDmHonourRollType")
    @ApiOperation(value = "修改光荣榜类型对象", notes = "返回响应对象")
    public ResponseJson updateDmHonourRollType(
            @ApiParam(value = "被修改的光荣榜类型对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRollType dmHonourRollType){
        dmHonourRollTypeService.updateDmHonourRollType(dmHonourRollType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmHonourRollTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找光荣榜类型", notes = "返回响应对象", response=DmHonourRollType.class)
    public ResponseJson lookDmHonourRollTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmHonourRollType dmHonourRollType=dmHonourRollTypeService.findDmHonourRollTypeById(id);
        return new ResponseJson(dmHonourRollType);
    }

    @PostMapping("/findDmHonourRollTypesByCondition")
    @ApiOperation(value = "根据条件查找光荣榜类型", notes = "返回响应对象", response=DmHonourRollType.class)
    public ResponseJson findDmHonourRollTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollType dmHonourRollType){
       dmHonourRollType.setSchoolId(mySchoolId());
        List<DmHonourRollType> data=dmHonourRollTypeService.findDmHonourRollTypeListByCondition(dmHonourRollType);
        long count=dmHonourRollTypeService.findDmHonourRollTypeCountByCondition(dmHonourRollType);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmHonourRollTypeByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜类型,结果必须为单条数据", notes = "没有时返回空", response=DmHonourRollType.class)
    public ResponseJson findOneDmHonourRollTypeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmHonourRollType dmHonourRollType){
        DmHonourRollType one=dmHonourRollTypeService.findOneDmHonourRollTypeByCondition(dmHonourRollType);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmHonourRollType/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmHonourRollType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        DmHonourRoll dmHonourRoll = new DmHonourRoll();
        dmHonourRoll.setType(id);
        dmHonourRoll.setSchoolId(mySchoolId());
        long cnt = dmHonourRollService.findDmHonourRollCountByCondition(dmHonourRoll);
        if(cnt>0){
            return new ResponseJson(false,"该类型下面存在光荣榜，请先删除光荣榜");
        }else{
            dmHonourRollTypeService.deleteDmHonourRollType(id);
            return new ResponseJson();
        }
    }
    @PostMapping("/findDmHonourRollTypeListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜类型列表", notes = "返回响应对象,不包含总条数", response=DmHonourRollType.class)
    public ResponseJson findDmHonourRollTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmHonourRollType dmHonourRollType){
       dmHonourRollType.setSchoolId(mySchoolId());
        List<DmHonourRollType> data=dmHonourRollTypeService.findDmHonourRollTypeListByCondition(dmHonourRollType);
        return new ResponseJson(data);
    }
    @GetMapping("/findDmHonourRollTypeList")
    @ApiOperation(value = "根据条件查找光荣榜类型列表", notes = "返回响应对象,不包含总条数", response=DmHonourRollType.class)
    public ResponseJson findDmHonourRollTypeList(){
        DmHonourRollType dmHonourRollType = new DmHonourRollType();
        dmHonourRollType.setSchoolId(mySchoolId());
        dmHonourRollType.setPager(new Pager().setPaging(false));
        List<DmHonourRollType> data=dmHonourRollTypeService.findDmHonourRollTypeListByCondition(dmHonourRollType);
        return new ResponseJson(data);
    }
}
