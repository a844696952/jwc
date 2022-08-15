package com.yice.edu.cn.jw.controller.region;

import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.jw.service.region.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
@Api(value = "/region",description = "模块")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/findRegionById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Region findRegionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return regionService.findRegionById(id);
    }

    @PostMapping("/saveRegion")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Region saveRegion(
            @ApiParam(value = "对象", required = true)
            @RequestBody Region region){
        regionService.saveRegion(region);
        return region;
    }

    @PostMapping("/findRegionListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Region> findRegionListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Region region){
        return regionService.findRegionListByCondition(region);
    }
    @PostMapping("/findRegionCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findRegionCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Region region){
        return regionService.findRegionCountByCondition(region);
    }

    @PostMapping("/updateRegion")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateRegion(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Region region){
        regionService.updateRegion(region);
    }

    @GetMapping("/deleteRegion/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteRegion(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        regionService.deleteRegion(id);
    }

    @GetMapping("/findRegionForCascade/{ids}")
    @ApiOperation(value = "给级联下拉城市所用,根据省id和市id查询")
    public List<Region> findRegionForCascade(@PathVariable String ids){
        return regionService.findRegionForCascade(ids);
    }
}
