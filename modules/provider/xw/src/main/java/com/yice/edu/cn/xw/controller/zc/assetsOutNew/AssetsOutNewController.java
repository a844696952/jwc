package com.yice.edu.cn.xw.controller.zc.assetsOutNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsOutNew.AssetsOutNew;
import com.yice.edu.cn.xw.service.zc.assetsOutNew.AssetsOutNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsOutNew")
@Api(value = "/assetsOutNew",description = "新资产出库模块")
public class AssetsOutNewController {
    @Autowired
    private AssetsOutNewService assetsOutNewService;

    @GetMapping("/findAssetsOutNewById/{id}")
    @ApiOperation(value = "通过id查找新资产出库", notes = "返回新资产出库对象")
    public AssetsOutNew findAssetsOutNewById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsOutNewService.findAssetsOutNewById(id);
    }

    @PostMapping("/saveAssetsOutNew")
    @ApiOperation(value = "保存新资产出库", notes = "返回新资产出库对象")
    public AssetsOutNew saveAssetsOutNew(
            @ApiParam(value = "新资产出库对象", required = true)
            @RequestBody AssetsOutNew assetsOutNew){
        assetsOutNewService.saveAssetsOutNew(assetsOutNew);
        return assetsOutNew;
    }

    @PostMapping("/findAssetsOutNewListByCondition")
    @ApiOperation(value = "根据条件查找新资产出库列表", notes = "返回新资产出库列表")
    public List<AssetsOutNew> findAssetsOutNewListByCondition(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        return assetsOutNewService.findAssetsOutNewListByCondition(assetsOutNew);
    }
    @PostMapping("/findAssetsOutNewCountByCondition")
    @ApiOperation(value = "根据条件查找新资产出库列表个数", notes = "返回新资产出库总个数")
    public long findAssetsOutNewCountByCondition(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        return assetsOutNewService.findAssetsOutNewCountByCondition(assetsOutNew);
    }

    @PostMapping("/updateAssetsOutNew")
    @ApiOperation(value = "修改新资产出库", notes = "新资产出库对象必传")
    public void updateAssetsOutNew(
            @ApiParam(value = "新资产出库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsOutNew assetsOutNew){
        assetsOutNewService.updateAssetsOutNew(assetsOutNew);
    }

    @GetMapping("/deleteAssetsOutNew/{id}")
    @ApiOperation(value = "通过id删除新资产出库")
    public void deleteAssetsOutNew(
            @ApiParam(value = "新资产出库对象", required = true)
            @PathVariable String id){
        assetsOutNewService.deleteAssetsOutNew(id);
    }
    @PostMapping("/deleteAssetsOutNewByCondition")
    @ApiOperation(value = "根据条件删除新资产出库")
    public void deleteAssetsOutNewByCondition(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        assetsOutNewService.deleteAssetsOutNewByCondition(assetsOutNew);
    }
    @PostMapping("/findOneAssetsOutNewByCondition")
    @ApiOperation(value = "根据条件查找单个新资产出库,结果必须为单条数据", notes = "返回单个新资产出库,没有时为空")
    public AssetsOutNew findOneAssetsOutNewByCondition(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        return assetsOutNewService.findOneAssetsOutNewByCondition(assetsOutNew);
    }
    @PostMapping("/batchSaveAssetsOutNew")
    @ApiOperation(value = "批量保存")
    public void batchSaveAssetsOutNew(
            @ApiParam(value = "新资产出库对象")
            @RequestBody List<AssetsOutNew> assetsOutNews){
        assetsOutNewService.batchSaveAssetsOutNew(assetsOutNews);
    }
    @PostMapping("/findAssetsOutNewListByCondition4Gather")
    @ApiOperation(value = "出库单列表界面根据条件查找资产出库列表")
    public List<AssetsOutNew> findAssetsOutNewListByCondition4Gather(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        return assetsOutNewService.findAssetsOutNewListByCondition4Gather(assetsOutNew);
    }
    @PostMapping("/findAssetsOutNewCountByCondition4Gather")
    @ApiOperation(value = "出库单列表界面根据条件查找资产出库列表个数")
    public long findAssetsOutNewCountByCondition4Gather(
            @ApiParam(value = "新资产出库对象")
            @RequestBody AssetsOutNew assetsOutNew){
        return assetsOutNewService.findAssetsOutNewCountByCondition4Gather(assetsOutNew);
    }
}
