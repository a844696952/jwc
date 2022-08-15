package com.yice.edu.cn.dm.controller.wb.LatticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerNumber;
import com.yice.edu.cn.dm.service.wb.LatticePager.DmPagerNumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmPagerNumber")
@Api(value = "/dmPagerNumber",description = "点阵试卷页码表模块")
public class DmPagerNumberController {
    @Autowired
    private DmPagerNumberService dmPagerNumberService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmPagerNumberById/{id}")
    @ApiOperation(value = "通过id查找点阵试卷页码表", notes = "返回点阵试卷页码表对象")
    public DmPagerNumber findDmPagerNumberById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmPagerNumberService.findDmPagerNumberById(id);
    }

    @PostMapping("/saveDmPagerNumber")
    @ApiOperation(value = "保存点阵试卷页码表", notes = "返回点阵试卷页码表对象")
    public DmPagerNumber saveDmPagerNumber(
            @ApiParam(value = "点阵试卷页码表对象", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.saveDmPagerNumber(dmPagerNumber);
        return dmPagerNumber;
    }

    @PostMapping("/batchSaveDmPagerNumber")
    @ApiOperation(value = "批量保存点阵试卷页码表")
    public void batchSaveDmPagerNumber(
            @ApiParam(value = "点阵试卷页码表对象集合", required = true)
            @RequestBody List<DmPagerNumber> dmPagerNumbers){
        dmPagerNumberService.batchSaveDmPagerNumber(dmPagerNumbers);
    }

    @PostMapping("/findDmPagerNumberListByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷页码表列表", notes = "返回点阵试卷页码表列表")
    public List<DmPagerNumber> findDmPagerNumberListByCondition(
            @ApiParam(value = "点阵试卷页码表对象")
            @RequestBody DmPagerNumber dmPagerNumber){
        return dmPagerNumberService.findDmPagerNumberListByCondition(dmPagerNumber);
    }
    @PostMapping("/findDmPagerNumberCountByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷页码表列表个数", notes = "返回点阵试卷页码表总个数")
    public long findDmPagerNumberCountByCondition(
            @ApiParam(value = "点阵试卷页码表对象")
            @RequestBody DmPagerNumber dmPagerNumber){
        return dmPagerNumberService.findDmPagerNumberCountByCondition(dmPagerNumber);
    }

    @PostMapping("/updateDmPagerNumber")
    @ApiOperation(value = "修改点阵试卷页码表有值的字段", notes = "点阵试卷页码表对象必传")
    public void updateDmPagerNumber(
            @ApiParam(value = "点阵试卷页码表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.updateDmPagerNumber(dmPagerNumber);
    }
    @PostMapping("/updateDmPagerNumberForAll")
    @ApiOperation(value = "修改点阵试卷页码表所有字段", notes = "点阵试卷页码表对象必传")
    public void updateDmPagerNumberForAll(
            @ApiParam(value = "点阵试卷页码表对象", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.updateDmPagerNumberForAll(dmPagerNumber);
    }

    @GetMapping("/deleteDmPagerNumber/{id}")
    @ApiOperation(value = "通过id删除点阵试卷页码表")
    public void deleteDmPagerNumber(
            @ApiParam(value = "点阵试卷页码表对象", required = true)
            @PathVariable String id){
        dmPagerNumberService.deleteDmPagerNumber(id);
    }
    @PostMapping("/deleteDmPagerNumberByCondition")
    @ApiOperation(value = "根据条件删除点阵试卷页码表")
    public void deleteDmPagerNumberByCondition(
            @ApiParam(value = "点阵试卷页码表对象")
            @RequestBody DmPagerNumber dmPagerNumber){
        dmPagerNumberService.deleteDmPagerNumberByCondition(dmPagerNumber);
    }
    @PostMapping("/findOneDmPagerNumberByCondition")
    @ApiOperation(value = "根据条件查找单个点阵试卷页码表,结果必须为单条数据", notes = "返回单个点阵试卷页码表,没有时为空")
    public DmPagerNumber findOneDmPagerNumberByCondition(
            @ApiParam(value = "点阵试卷页码表对象")
            @RequestBody DmPagerNumber dmPagerNumber){
        return dmPagerNumberService.findOneDmPagerNumberByCondition(dmPagerNumber);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findDmPagerBackgroundImg/{id}")
    @ApiOperation(value = "通过id预览试卷只显示绑定了的试卷", notes = "返回响应对象", response= DmPagerBackground.class)
    public List<DmPagerBackground> findDmPagerBackgroundImg(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        return dmPagerNumberService.findDmPagerBackgroundImg(id);
    }


    @PostMapping("/updateRecover")
    @ApiOperation(value = "点击更新可回收的页码", notes = "返回响应对象", response=ResponseJson.class)
    public ResponseJson updateRecover(
            @ApiParam(value = "点击更新可回收的页码，传当前id", required = true)
            @RequestBody DmPagerNumber dmPagerNumber){
        return dmPagerNumberService.updateRecover(dmPagerNumber);
    }



}
