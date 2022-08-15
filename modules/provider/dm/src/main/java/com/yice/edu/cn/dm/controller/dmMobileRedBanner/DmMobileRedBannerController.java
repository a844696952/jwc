package com.yice.edu.cn.dm.controller.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.dm.service.dmMobileRedBanner.DmMobileRedBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmMobileRedBanner")
@Api(value = "/dmMobileRedBanner",description = "流动红旗表模块")
public class DmMobileRedBannerController {
    @Autowired
    private DmMobileRedBannerService dmMobileRedBannerService;

    @GetMapping("/findDmMobileRedBannerById/{id}")
    @ApiOperation(value = "通过id查找流动红旗表", notes = "返回流动红旗表对象")
    public DmMobileRedBanner findDmMobileRedBannerById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmMobileRedBannerService.findDmMobileRedBannerById(id);
    }

    @PostMapping("/saveDmMobileRedBanner")
    @ApiOperation(value = "保存流动红旗表", notes = "返回流动红旗表对象")
    public DmMobileRedBanner saveDmMobileRedBanner(
            @ApiParam(value = "流动红旗表对象", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.saveDmMobileRedBanner(dmMobileRedBanner);
        return dmMobileRedBanner;
    }

    @PostMapping("/findDmMobileRedBannerListByCondition")
    @ApiOperation(value = "根据条件查找流动红旗表列表", notes = "返回流动红旗表列表")
    public List<DmMobileRedBanner> findDmMobileRedBannerListByCondition(
            @ApiParam(value = "流动红旗表对象")
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        return dmMobileRedBannerService.findDmMobileRedBannerListByCondition(dmMobileRedBanner);
    }
    @PostMapping("/findDmMobileRedBannerCountByCondition")
    @ApiOperation(value = "根据条件查找流动红旗表列表个数", notes = "返回流动红旗表总个数")
    public long findDmMobileRedBannerCountByCondition(
            @ApiParam(value = "流动红旗表对象")
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        return dmMobileRedBannerService.findDmMobileRedBannerCountByCondition(dmMobileRedBanner);
    }

    @PostMapping("/updateDmMobileRedBanner")
    @ApiOperation(value = "修改流动红旗表所有非@Transient注释的字段", notes = "流动红旗表对象必传")
    public void updateDmMobileRedBanner(
            @ApiParam(value = "流动红旗表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.updateDmMobileRedBanner(dmMobileRedBanner);
    }
    @PostMapping("/updateDmMobileRedBannerForNotNull")
    @ApiOperation(value = "修改流动红旗表有值的字段", notes = "流动红旗表对象必传")
    public void updateDmMobileRedBannerForNotNull(
            @ApiParam(value = "流动红旗表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.updateDmMobileRedBannerForNotNull(dmMobileRedBanner);
    }

    @GetMapping("/deleteDmMobileRedBanner/{id}")
    @ApiOperation(value = "通过id删除流动红旗表")
    public void deleteDmMobileRedBanner(
            @ApiParam(value = "流动红旗表对象", required = true)
            @PathVariable String id){
        dmMobileRedBannerService.deleteDmMobileRedBanner(id);
    }
    @PostMapping("/deleteDmMobileRedBannerByCondition")
    @ApiOperation(value = "根据条件删除流动红旗表")
    public void deleteDmMobileRedBannerByCondition(
            @ApiParam(value = "流动红旗表对象")
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        dmMobileRedBannerService.deleteDmMobileRedBannerByCondition(dmMobileRedBanner);
    }
    @PostMapping("/findOneDmMobileRedBannerByCondition")
    @ApiOperation(value = "根据条件查找单个流动红旗表,结果必须为单条数据", notes = "返回单个流动红旗表,没有时为空")
    public DmMobileRedBanner findOneDmMobileRedBannerByCondition(
            @ApiParam(value = "流动红旗表对象")
            @RequestBody DmMobileRedBanner dmMobileRedBanner){
        return dmMobileRedBannerService.findOneDmMobileRedBannerByCondition(dmMobileRedBanner);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "更改流动红旗状态", notes = "更改流动红旗状态")
    public void updateStatus(){
        dmMobileRedBannerService.updateStatus();
    }

    @GetMapping("/findDmMobileRedBannerByClassId/{classId}")
    @ApiOperation(value = "通过classId查找流动红旗表", notes = "返回流动红旗表对象")
    public DmMobileRedBanner findDmMobileRedBannerByClassId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String classId){
        return dmMobileRedBannerService.findDmMobileRedBannerByClassId(classId);
    }

    @PostMapping("/findTodayAwardRedBanner")
    @ApiOperation(value = "返回当天待颁发的流动红旗", notes = "返回当天待颁发的流动红旗")
    public DmMobileRedBanner findTodayAwardRedBanner(){
        return dmMobileRedBannerService.findTodayAwardRedBanner();
    }

    @GetMapping("/updateStatusShowById/{id}")
    @ApiOperation(value = "通过id将流动红旗更新为展示状态", notes = "通过id将流动红旗更新为展示状态")
    public void updateStatusShowById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
         dmMobileRedBannerService.updateStatusShowById(id);
    }

    @GetMapping("/findToBeIssuedDmMobileRedBannerByAwardTime/{awardTime}")
    @ApiOperation(value = "通过awardTime查找待颁发流动红旗表", notes = "返回流动红旗表对象")
    public DmMobileRedBanner findToBeIssuedDmMobileRedBannerByAwardTime(
            @ApiParam(value = "需要用到的awardTime", required = true)
            @PathVariable String awardTime){
        return dmMobileRedBannerService.findToBeIssuedDmMobileRedBannerByAwardTime(awardTime);
    }
}
