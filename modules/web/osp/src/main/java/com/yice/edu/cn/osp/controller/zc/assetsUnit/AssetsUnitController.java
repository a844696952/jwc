package com.yice.edu.cn.osp.controller.zc.assetsUnit;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import com.yice.edu.cn.osp.service.zc.assetsUnit.AssetsUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsUnit")
@Api(value = "/assetsUnit",description = "资产单位模块")
public class AssetsUnitController {
    @Autowired
    private AssetsUnitService assetsUnitService;

    @PostMapping("/saveAssetsUnit")
    @ApiOperation(value = "保存资产单位对象", notes = "返回保存好的资产单位对象", response= AssetsUnit.class)
    public ResponseJson saveAssetsUnit(
            @ApiParam(value = "资产单位对象", required = true)
            @RequestBody AssetsUnit assetsUnit){
        assetsUnit.setSchoolId(mySchoolId());
        assetsUnit.setName(assetsUnit.getName());
        // 拿name 去表中查询 看是否有重复
       long a = assetsUnitService.findAssetsUnitCountByName(assetsUnit);
        /*System.out.println("++++++++++++++++++++++++"+a);*/
        if (a == 0 ){
            assetsUnit.setSchoolId(mySchoolId());
            assetsUnit.setDel(1);
            AssetsUnit s=assetsUnitService.saveAssetsUnit(assetsUnit);
            return new ResponseJson(s);
        }
        return new ResponseJson(false, "该资产单位已存在！");
    }

    @GetMapping("/update/findAssetsUnitById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产单位", notes = "返回响应对象", response=AssetsUnit.class)
    public ResponseJson findAssetsUnitById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsUnit assetsUnit=assetsUnitService.findAssetsUnitById(id);
        return new ResponseJson(assetsUnit);
    }

    @PostMapping("/update/updateAssetsUnit")
    @ApiOperation(value = "修改资产单位对象", notes = "返回响应对象")
    public ResponseJson updateAssetsUnit(
            @ApiParam(value = "被修改的资产单位对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsUnit assetsUnit){
        assetsUnit.setDel(2);
        assetsUnitService.updateAssetsUnit(assetsUnit);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsUnitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产单位", notes = "返回响应对象", response=AssetsUnit.class)
    public ResponseJson lookAssetsUnitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsUnit assetsUnit=assetsUnitService.findAssetsUnitById(id);
        return new ResponseJson(assetsUnit);
    }

    @PostMapping("/findAssetsUnitsByCondition")
    @ApiOperation(value = "根据条件查找资产单位", notes = "返回响应对象", response=AssetsUnit.class)
    public ResponseJson findAssetsUnitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsUnit assetsUnit){
       assetsUnit.setSchoolId(mySchoolId());
        assetsUnit.setDel(1);
        assetsUnit.getPager().setLike("name");
        assetsUnit.getPager().setSortField("createTime");
        assetsUnit.getPager().setSortOrder(Pager.DESC);
        List<AssetsUnit> data=assetsUnitService.findAssetsUnitListByCondition(assetsUnit);
        long count=assetsUnitService.findAssetsUnitCountByCondition(assetsUnit);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAssetsUnitByCondition")
    @ApiOperation(value = "根据条件查找单个资产单位,结果必须为单条数据", notes = "没有时返回空", response=AssetsUnit.class)
    public ResponseJson findOneAssetsUnitByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsUnit assetsUnit){
        AssetsUnit one=assetsUnitService.findOneAssetsUnitByCondition(assetsUnit);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAssetsUnit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsUnit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        assetsUnitService.deleteAssetsUnit(id);
        return new ResponseJson();
    }


    @PostMapping("/findAssetsUnitListByCondition")
    @ApiOperation(value = "根据条件查找资产单位列表", notes = "返回响应对象,不包含总条数", response=AssetsUnit.class)
    public ResponseJson findAssetsUnitListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsUnit assetsUnit){
       assetsUnit.setSchoolId(mySchoolId());
        List<AssetsUnit> data=assetsUnitService.findAssetsUnitListByCondition(assetsUnit);
        return new ResponseJson(data);
    }

    @PostMapping("update/updateAssetsUnitById")
    @ApiOperation(value = "根据条件查找资产单位列表", notes = "返回响应对象,不包含总条数", response=AssetsUnit.class)
    public ResponseJson updateAssetsUnitById(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody  AssetsUnit assetsUnit){
        assetsUnit.setDel(2);
        assetsUnitService.updateAssetsUnit(assetsUnit);
        return new ResponseJson();
    }



}
