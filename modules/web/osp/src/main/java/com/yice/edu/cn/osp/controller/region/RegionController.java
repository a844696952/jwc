package com.yice.edu.cn.osp.controller.region;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.region.Region;
import com.yice.edu.cn.osp.service.region.RegionService;
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
    @ApiOperation(value = "通过id查找", notes = "返回响应对象")
    public ResponseJson findRegionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Region region=regionService.findRegionById(id);
        return new ResponseJson(region);
    }

    @PostMapping("/saveRegion")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveRegion(
            @ApiParam(value = "对象", required = true)
            @RequestBody Region region){
        Region s=regionService.saveRegion(region);
        return new ResponseJson(s);
    }
    @PostMapping("/updateRegion")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateRegion(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Region region){
        regionService.updateRegion(region);
        return new ResponseJson();
    }

    @GetMapping("/findRegionsById/{pId}")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findRegionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable String pId){
        Region region = new Region();
        if(pId==null||"undefined".equals(pId))
            region.setParentId("100000");
        else
            region.setParentId(pId);
        List<Region> data=regionService.findRegionListByCondition(region);
        return new ResponseJson(data);
    }
    @GetMapping("/deleteRegion/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRegion(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        regionService.deleteRegion(id);
        return new ResponseJson();
    }


    @GetMapping("/findRegionForCascade/{ids}")
    @ApiOperation(value = "给级联下拉城市所用,根据省id和市id查询")
    public ResponseJson findRegionForCascade(@PathVariable String ids){
        List<Region> regions = regionService.findRegionForCascade(ids);
        return new ResponseJson(regions);
    }

}
