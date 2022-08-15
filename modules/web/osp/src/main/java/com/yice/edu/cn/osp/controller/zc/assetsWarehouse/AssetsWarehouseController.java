package com.yice.edu.cn.osp.controller.zc.assetsWarehouse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.common.pojo.xw.zc.assetsWarehouse.AssetsWarehouse;
import com.yice.edu.cn.osp.service.zc.AssetsInNew.AssetsInNewService;
import com.yice.edu.cn.osp.service.zc.assetsWarehouse.AssetsWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/assetsWarehouse")
@Api(value = "/assetsWarehouse",description = "资产仓库模块")
public class AssetsWarehouseController {
    @Autowired
    private AssetsWarehouseService assetsWarehouseService;
    @Autowired
    private AssetsInNewService assetsInNewService;

    @PostMapping("/saveAssetsWarehouse")
    @ApiOperation(value = "保存资产仓库对象", notes = "返回保存好的资产仓库对象", response= AssetsWarehouse.class)
    public ResponseJson saveAssetsWarehouse(
            @ApiParam(value = "资产仓库对象", required = true)
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouse.setName(assetsWarehouse.getName());
        assetsWarehouse.setSchoolId(mySchoolId());
        long a = assetsWarehouseService.findAssetsWarehouseCountByName(assetsWarehouse);
        System.out.println("+++++++++++"+a);
        if (a == 0){
            assetsWarehouse.setSchoolId(mySchoolId());
            AssetsWarehouse s=assetsWarehouseService.saveAssetsWarehouse(assetsWarehouse);
            return new ResponseJson(s);
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findAssetsWarehouseById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产仓库", notes = "返回响应对象", response=AssetsWarehouse.class)
    public ResponseJson findAssetsWarehouseById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsWarehouse assetsWarehouse=assetsWarehouseService.findAssetsWarehouseById(id);
        return new ResponseJson(assetsWarehouse);
    }

    @PostMapping("/update/updateAssetsWarehouse")
    @ApiOperation(value = "修改资产仓库对象", notes = "返回响应对象")
    public ResponseJson updateAssetsWarehouse(
            @ApiParam(value = "被修改的资产仓库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouseService.updateAssetsWarehouse(assetsWarehouse);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsWarehouseById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产仓库", notes = "返回响应对象", response=AssetsWarehouse.class)
    public ResponseJson lookAssetsWarehouseById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsWarehouse assetsWarehouse=assetsWarehouseService.findAssetsWarehouseById(id);
        return new ResponseJson(assetsWarehouse);
    }

    @PostMapping("/findAssetsWarehousesByCondition")
    @ApiOperation(value = "根据条件查找资产仓库", notes = "返回响应对象", response=AssetsWarehouse.class)
    public ResponseJson findAssetsWarehousesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsWarehouse assetsWarehouse){
        assetsWarehouse.setSchoolId(mySchoolId());
        assetsWarehouse.getPager().setLike("name");
        assetsWarehouse.getPager().setSortField("createTime");
        assetsWarehouse.getPager().setSortOrder(Pager.DESC);
        List<AssetsWarehouse> data=assetsWarehouseService.findAssetsWarehouseListByCondition(assetsWarehouse);
        long count=assetsWarehouseService.findAssetsWarehouseCountByCondition(assetsWarehouse);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAssetsWarehouseByCondition")
    @ApiOperation(value = "根据条件查找单个资产仓库,结果必须为单条数据", notes = "没有时返回空", response=AssetsWarehouse.class)
    public ResponseJson findOneAssetsWarehouseByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsWarehouse assetsWarehouse){
        AssetsWarehouse one=assetsWarehouseService.findOneAssetsWarehouseByCondition(assetsWarehouse);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAssetsWarehouse/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsWarehouse(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        AssetsInNew assetsInNew = new AssetsInNew();
        assetsInNew.setPutInWarehouse(id);
        assetsInNew.setSchoolId(mySchoolId());
        List<AssetsInNew> data = assetsInNewService.findAssetsNoListByCondition(assetsInNew);
        if(data.size()!=0){
            return new ResponseJson(false);
        }else{
            assetsWarehouseService.deleteAssetsWarehouse(id);
            return new ResponseJson(true);
        }
    }


    @PostMapping("/findAssetsWarehouseListByCondition")
    @ApiOperation(value = "根据条件查找资产仓库列表", notes = "返回响应对象,不包含总条数", response=AssetsWarehouse.class)
    public ResponseJson findAssetsWarehouseListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsWarehouse assetsWarehouse){
       assetsWarehouse.setSchoolId(mySchoolId());
        List<AssetsWarehouse> data=assetsWarehouseService.findAssetsWarehouseListByCondition(assetsWarehouse);
        return new ResponseJson(data);
    }



}
