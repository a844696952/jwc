package com.yice.edu.cn.dm.controller.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.dm.service.schoolActive.DmActivitySiginUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmActivitySiginUp")
@Api(value = "/dmActivitySiginUp",description = "活动报名表模块")
public class DmActivitySiginUpController {
    @Autowired
    private DmActivitySiginUpService dmActivitySiginUpService;

    @GetMapping("/findDmActivitySiginUpById/{id}")
    @ApiOperation(value = "通过id查找活动报名表", notes = "返回活动报名表对象")
    public DmActivitySiginUp findDmActivitySiginUpById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmActivitySiginUpService.findDmActivitySiginUpById(id);
    }

    @PostMapping("/saveDmActivitySiginUp")
    @ApiOperation(value = "保存活动报名表", notes = "返回活动报名表对象")
    public DmActivitySiginUp saveDmActivitySiginUp(
            @ApiParam(value = "活动报名表对象", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        dmActivitySiginUpService.saveDmActivitySiginUp(dmActivitySiginUp);
        return dmActivitySiginUp;
    }


    @PostMapping("/findDmActivitySiginUpListByCondition")
    @ApiOperation(value = "根据条件查找活动报名表列表", notes = "返回活动报名表列表")
    public List<DmActivitySiginUp> findDmActivitySiginUpListByCondition(
            @ApiParam(value = "活动报名表对象")
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        return dmActivitySiginUpService.findDmActivitySiginUpListByCondition(dmActivitySiginUp);
    }
    @PostMapping("/findDmActivitySiginUpCountByCondition")
    @ApiOperation(value = "根据条件查找活动报名表列表个数", notes = "返回活动报名表总个数")
    public long findDmActivitySiginUpCountByCondition(
            @ApiParam(value = "活动报名表对象")
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        return dmActivitySiginUpService.findDmActivitySiginUpCountByCondition(dmActivitySiginUp);
    }

    @PostMapping("/updateDmActivitySiginUp")
    @ApiOperation(value = "修改活动报名表", notes = "活动报名表对象必传")
    public void updateDmActivitySiginUp(
            @ApiParam(value = "活动报名表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        dmActivitySiginUpService.updateDmActivitySiginUp(dmActivitySiginUp);
    }

    @GetMapping("/deleteDmActivitySiginUp/{id}")
    @ApiOperation(value = "通过id删除活动报名表")
    public void deleteDmActivitySiginUp(
            @ApiParam(value = "活动报名表对象", required = true)
            @PathVariable String id){
        dmActivitySiginUpService.deleteDmActivitySiginUp(id);
    }
    @PostMapping("/deleteDmActivitySiginUpByCondition")
    @ApiOperation(value = "根据条件删除活动报名表")
    public void deleteDmActivitySiginUpByCondition(
            @ApiParam(value = "活动报名表对象")
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        dmActivitySiginUpService.deleteDmActivitySiginUpByCondition(dmActivitySiginUp);
    }
    @PostMapping("/findOneDmActivitySiginUpByCondition")
    @ApiOperation(value = "根据条件查找单个活动报名表,结果必须为单条数据", notes = "返回单个活动报名表,没有时为空")
    public DmActivitySiginUp findOneDmActivitySiginUpByCondition(
            @ApiParam(value = "活动报名表对象")
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        return dmActivitySiginUpService.findOneDmActivitySiginUpByCondition(dmActivitySiginUp);
    }


    @PostMapping("/batchSaveDmActivitySiginUp")
    @ApiOperation(value = "保存活动报名表")
    public void batchSaveDmActivitySiginUp(
            @ApiParam(value = "活动报名表对象", required = true)
            @RequestBody List<DmActivitySiginUp> dmActivitySiginUps){
        dmActivitySiginUpService.batchSaveDmActivitySiginUp(dmActivitySiginUps);
    }
}
