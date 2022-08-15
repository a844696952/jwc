package com.yice.edu.cn.xw.controller.zc.assetsUnit;

import com.yice.edu.cn.common.pojo.xw.zc.assetsUnit.AssetsUnit;
import com.yice.edu.cn.xw.service.zc.assetsUnit.AssetsUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsUnit")
@Api(value = "/assetsUnit",description = "资产单位模块")
public class AssetsUnitController {
    @Autowired
    private AssetsUnitService assetsUnitService;

    @GetMapping("/findAssetsUnitById/{id}")
    @ApiOperation(value = "通过id查找资产单位", notes = "返回资产单位对象")
    public AssetsUnit findAssetsUnitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsUnitService.findAssetsUnitById(id);
    }

    @PostMapping("/saveAssetsUnit")
    @ApiOperation(value = "保存资产单位", notes = "返回资产单位对象")
    public AssetsUnit saveAssetsUnit(
            @ApiParam(value = "资产单位对象", required = true)
            @RequestBody AssetsUnit assetsUnit){
        assetsUnitService.saveAssetsUnit(assetsUnit);
        return assetsUnit;
    }

    @PostMapping("/findAssetsUnitListByCondition")
    @ApiOperation(value = "根据条件查找资产单位列表", notes = "返回资产单位列表")
    public List<AssetsUnit> findAssetsUnitListByCondition(
            @ApiParam(value = "资产单位对象")
            @RequestBody AssetsUnit assetsUnit){
        return assetsUnitService.findAssetsUnitListByCondition(assetsUnit);
    }
    @PostMapping("/findAssetsUnitCountByCondition")
    @ApiOperation(value = "根据条件查找资产单位列表个数", notes = "返回资产单位总个数")
    public long findAssetsUnitCountByCondition(
            @ApiParam(value = "资产单位对象")
            @RequestBody AssetsUnit assetsUnit){
        return assetsUnitService.findAssetsUnitCountByCondition(assetsUnit);
    }

    @PostMapping("/updateAssetsUnit")
    @ApiOperation(value = "修改资产单位", notes = "资产单位对象必传")
    public void updateAssetsUnit(
            @ApiParam(value = "资产单位对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsUnit assetsUnit){
        assetsUnitService.updateAssetsUnit(assetsUnit);
    }

    @GetMapping("/deleteAssetsUnit/{id}")
    @ApiOperation(value = "通过id删除资产单位")
    public void deleteAssetsUnit(
            @ApiParam(value = "资产单位对象", required = true)
            @PathVariable String id){
        assetsUnitService.deleteAssetsUnit(id);
    }
    @PostMapping("/deleteAssetsUnitByCondition")
    @ApiOperation(value = "根据条件删除资产单位")
    public void deleteAssetsUnitByCondition(
            @ApiParam(value = "资产单位对象")
            @RequestBody AssetsUnit assetsUnit){
        assetsUnitService.deleteAssetsUnitByCondition(assetsUnit);
    }
    @PostMapping("/findOneAssetsUnitByCondition")
    @ApiOperation(value = "根据条件查找单个资产单位,结果必须为单条数据", notes = "返回单个资产单位,没有时为空")
    public AssetsUnit findOneAssetsUnitByCondition(
            @ApiParam(value = "资产单位对象")
            @RequestBody AssetsUnit assetsUnit){
        return assetsUnitService.findOneAssetsUnitByCondition(assetsUnit);
    }


    @PostMapping("/findAssetsUnitCountByName")
    @ApiOperation(value = "根据条件查找资产单位列表个数", notes = "返回资产单位总个数")
    public long findAssetsUnitCountByName(
            @ApiParam(value = "资产单位对象")
            @RequestBody AssetsUnit assetsUnit){
        return assetsUnitService.findAssetsUnitCountByName(assetsUnit);
    }
}
