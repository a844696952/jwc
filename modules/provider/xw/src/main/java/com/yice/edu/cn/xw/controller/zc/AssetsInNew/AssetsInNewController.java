package com.yice.edu.cn.xw.controller.zc.AssetsInNew;

import com.yice.edu.cn.common.pojo.xw.zc.assetsInNew.AssetsInNew;
import com.yice.edu.cn.xw.service.zc.assetsInNew.AssetsInNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsInNew")
@Api(value = "/assetsInNew",description = "资产入库模块")
public class AssetsInNewController {
    @Autowired
    private AssetsInNewService assetsInNewService;

    @GetMapping("/findAssetsInNewById/{id}")
    @ApiOperation(value = "通过id查找资产入库", notes = "返回资产入库对象")
    public AssetsInNew findAssetsInNewById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsInNewService.findAssetsInNewById(id);
    }

    @PostMapping("/saveAssetsInNew")
    @ApiOperation(value = "保存资产入库", notes = "返回资产入库对象")
    public AssetsInNew saveAssetsInNew(
            @ApiParam(value = "资产入库对象", required = true)
            @RequestBody AssetsInNew assetsInNew){
        assetsInNewService.saveAssetsInNew(assetsInNew);
        return assetsInNew;
    }

    @PostMapping("/findAssetsInNewListByCondition")
    @ApiOperation(value = "每个入库单明细(单号，入库时间)，有几个资产", notes = "返回资产入库列表")
    public List<AssetsInNew> findAssetsInNewListByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsInNewListByCondition(assetsInNew);
    }
    @PostMapping("/findAssetsInNewCountByCondition")
    @ApiOperation(value = "根据条件查找资产入库列表个数", notes = "返回资产入库总个数")
    public long findAssetsInNewCountByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsInNewCountByCondition(assetsInNew);
    }

    @PostMapping("/updateAssetsInNew")
    @ApiOperation(value = "修改资产入库", notes = "资产入库对象必传")
    public void updateAssetsInNew(
            @ApiParam(value = "资产入库对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsInNew assetsInNew){
        assetsInNewService.updateAssetsInNew(assetsInNew);
    }

    @GetMapping("/deleteAssetsInNew/{id}")
    @ApiOperation(value = "通过id删除资产入库")
    public void deleteAssetsInNew(
            @ApiParam(value = "资产入库对象", required = true)
            @PathVariable String id){
        assetsInNewService.deleteAssetsInNew(id);
    }
    @PostMapping("/deleteAssetsInNewByCondition")
    @ApiOperation(value = "根据条件删除资产入库")
    public void deleteAssetsInNewByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        assetsInNewService.deleteAssetsInNewByCondition(assetsInNew);
    }
    @PostMapping("/findOneAssetsInNewByCondition")
    @ApiOperation(value = "根据条件查找单个资产入库,结果必须为单条数据", notes = "返回单个资产入库,没有时为空")
    public AssetsInNew findOneAssetsInNewByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findOneAssetsInNewByCondition(assetsInNew);
    }

    @PostMapping("/findAssetsNoListByCondition")
    @ApiOperation(value = "入库单初始化页面查询", notes = "返回资产单号列表")
    public List<AssetsInNew> findAssetsNoListByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsNoListByCondition(assetsInNew);
    }

    @PostMapping("/findAssetsNoInfoByNo")
    @ApiOperation(value = "根据条件查找入库单", notes = "返回资产单号列表")
    public AssetsInNew findAssetsNoInfoByNo(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsNoInfoByNo(assetsInNew);
    }

    @PostMapping("/findAssetsInNewDetailByNo")
    @ApiOperation(value = "查询单号，查询入库单，详细信息，包括，几个资产。", notes = "返回资产单号列表")
    public List<AssetsInNew> findAssetsInNewDetailByNo(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsInNewDetailByNo(assetsInNew);
    }


    @PostMapping("/findAssetsInNewDetailCountByNo")
    @ApiOperation(value = "查询单号，查询入库单，详细信息，包括，几个资产。", notes = "返回资产单号列表")
    public long findAssetsInNewDetailCountByNo(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsInNewDetailCountByNo(assetsInNew);
    }


    @PostMapping("/findAssetsNoCountByCondition")
    @ApiOperation(value = "根据条件查找资产入库列表个数", notes = "返回资产入库总个数")
    public long findAssetsNoCountByCondition(
            @ApiParam(value = "资产入库对象")
            @RequestBody AssetsInNew assetsInNew){
        return assetsInNewService.findAssetsNoCountByCondition(assetsInNew);
    }
}
