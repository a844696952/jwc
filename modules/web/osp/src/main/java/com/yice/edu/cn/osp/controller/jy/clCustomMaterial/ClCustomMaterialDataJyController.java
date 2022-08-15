package com.yice.edu.cn.osp.controller.jy.clCustomMaterial;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.clCustomMaterial.ClCustomMaterialDataService;
import com.yice.edu.cn.osp.service.xw.clCustomMaterial.ClWeightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clCustomMaterialDataJy")
@Api(value = "/clCustomMaterialDataJy",description = "模块")
public class ClCustomMaterialDataJyController {
    @Autowired
    private ClCustomMaterialDataService clCustomMaterialDataService;
    @Autowired
    private ClWeightService clWeightService;

    @PostMapping("/saveClCustomMaterialData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClCustomMaterialData.class)
    public ResponseJson saveClCustomMaterialData(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
       clCustomMaterialData.setSchoolId(LoginInterceptor.mySchoolId());
        ClCustomMaterialData s=clCustomMaterialDataService.saveClCustomMaterialData(clCustomMaterialData);
        return new ResponseJson(s);
    }

    @GetMapping("/ignore/update/findClCustomMaterialDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=ClCustomMaterialData.class)
    public ResponseJson findClCustomMaterialDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClCustomMaterialData clCustomMaterialData=clCustomMaterialDataService.findClCustomMaterialDataById(id);
        return new ResponseJson(clCustomMaterialData);
    }

    @PostMapping("/ignore/update/updateClCustomMaterialData")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateClCustomMaterialData(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.updateClCustomMaterialData(clCustomMaterialData);
        return new ResponseJson();
    }

    @PostMapping("/ignore/update/updateClCustomMaterialDataForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateClCustomMaterialDataForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.updateClCustomMaterialDataForAll(clCustomMaterialData);
        return new ResponseJson();
    }

    @GetMapping("/ignore/look/lookClCustomMaterialDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ClCustomMaterialData.class)
    public ResponseJson lookClCustomMaterialDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClCustomMaterialData clCustomMaterialData=clCustomMaterialDataService.findClCustomMaterialDataById(id);
        return new ResponseJson(clCustomMaterialData);
    }

    @PostMapping("/ignore/findClCustomMaterialDatasByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ClCustomMaterialData.class)
    public ResponseJson findClCustomMaterialDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialData.setSchoolId(LoginInterceptor.mySchoolId());

        List<ClCustomMaterialData> data = clCustomMaterialDataService.findClCustomMaterialDataListByConditionKong(clCustomMaterialData);
        long count = clCustomMaterialDataService.findClCustomMaterialDataCountByConditionKong(clCustomMaterialData);
        ClWeight clWeight = new ClWeight();
        clWeight.setStuOrTea(clCustomMaterialData.getStuOrTea());
        clWeight.setParentId(clCustomMaterialData.getParentId());
        clWeight.setType(3);
        clWeight.setSchoolId(LoginInterceptor.mySchoolId());
        ClWeight clWeight1 = clWeightService.findOneClWeightByCondition(clWeight);
        return new ResponseJson(data,count,clWeight1==null?0:clWeight1.getWeight());
    }
    @PostMapping("/ignore/findOneClCustomMaterialDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=ClCustomMaterialData.class)
    public ResponseJson findOneClCustomMaterialDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        ClCustomMaterialData one=clCustomMaterialDataService.findOneClCustomMaterialDataByCondition(clCustomMaterialData);
        return new ResponseJson(one);
    }
    @GetMapping("/ignore/deleteClCustomMaterialData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClCustomMaterialData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        clCustomMaterialDataService.deleteClCustomMaterialData(id);
        return new ResponseJson();
    }


    @PostMapping("/ignore/findClCustomMaterialDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ClCustomMaterialData.class)
    public ResponseJson findClCustomMaterialDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialData.setSchoolId(LoginInterceptor.mySchoolId());
        List<ClCustomMaterialData> data=clCustomMaterialDataService.findClCustomMaterialDataListByCondition(clCustomMaterialData);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/saveClCustomMaterialData")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClCustomMaterialData.class)
    public ResponseJson saveClCustomMaterialDataIgnore(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialData.setSchoolId(LoginInterceptor.mySchoolId());
        clCustomMaterialData.setType(3);
        clCustomMaterialDataService.saveClCustomMaterialDataAndClWeight(clCustomMaterialData);
        return new ResponseJson();
    }



}
