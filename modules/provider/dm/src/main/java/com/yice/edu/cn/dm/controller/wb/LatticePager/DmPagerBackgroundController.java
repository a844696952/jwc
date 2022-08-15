package com.yice.edu.cn.dm.controller.wb.LatticePager;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.latticePager.DmPagerBackground;
import com.yice.edu.cn.dm.service.wb.LatticePager.DmPagerBackgroundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dmPagerBackground")
@Api(value = "/dmPagerBackground",description = "点阵试卷背景表模块")
public class DmPagerBackgroundController {
    @Autowired
    private DmPagerBackgroundService dmPagerBackgroundService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmPagerBackgroundById/{id}")
    @ApiOperation(value = "通过id查找点阵试卷背景表", notes = "返回点阵试卷背景表对象")
    public DmPagerBackground findDmPagerBackgroundById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmPagerBackgroundService.findDmPagerBackgroundById(id);
    }

    @PostMapping("/saveDmPagerBackground")
    @ApiOperation(value = "保存点阵试卷背景表", notes = "返回点阵试卷背景表对象")
    public DmPagerBackground saveDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.saveDmPagerBackground(dmPagerBackground);
        return dmPagerBackground;
    }

    @PostMapping("/batchSaveDmPagerBackground")
    @ApiOperation(value = "批量保存点阵试卷背景表")
    public void batchSaveDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象集合", required = true)
            @RequestBody List<DmPagerBackground> dmPagerBackgrounds){
        dmPagerBackgroundService.batchSaveDmPagerBackground(dmPagerBackgrounds);
    }

    @PostMapping("/findDmPagerBackgroundListByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷背景表列表", notes = "返回点阵试卷背景表列表")
    public List<DmPagerBackground> findDmPagerBackgroundListByCondition(
            @ApiParam(value = "点阵试卷背景表对象")
            @RequestBody DmPagerBackground dmPagerBackground){
        return dmPagerBackgroundService.findDmPagerBackgroundListByCondition(dmPagerBackground);
    }
    @PostMapping("/findDmPagerBackgroundCountByCondition")
    @ApiOperation(value = "根据条件查找点阵试卷背景表列表个数", notes = "返回点阵试卷背景表总个数")
    public long findDmPagerBackgroundCountByCondition(
            @ApiParam(value = "点阵试卷背景表对象")
            @RequestBody DmPagerBackground dmPagerBackground){
        return dmPagerBackgroundService.findDmPagerBackgroundCountByCondition(dmPagerBackground);
    }

    @PostMapping("/updateDmPagerBackground")
    @ApiOperation(value = "修改点阵试卷背景表有值的字段", notes = "点阵试卷背景表对象必传")
    public void updateDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.updateDmPagerBackground(dmPagerBackground);
    }
    @PostMapping("/updateDmPagerBackgroundForAll")
    @ApiOperation(value = "修改点阵试卷背景表所有字段", notes = "点阵试卷背景表对象必传")
    public void updateDmPagerBackgroundForAll(
            @ApiParam(value = "点阵试卷背景表对象", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.updateDmPagerBackgroundForAll(dmPagerBackground);
    }

    @GetMapping("/deleteDmPagerBackground/{id}")
    @ApiOperation(value = "通过id删除点阵试卷背景表")
    public void deleteDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象", required = true)
            @PathVariable String id){
        dmPagerBackgroundService.deleteDmPagerBackground(id);
    }
    @PostMapping("/deleteDmPagerBackgroundByCondition")
    @ApiOperation(value = "根据条件删除点阵试卷背景表")
    public void deleteDmPagerBackgroundByCondition(
            @ApiParam(value = "点阵试卷背景表对象")
            @RequestBody DmPagerBackground dmPagerBackground){
        dmPagerBackgroundService.deleteDmPagerBackgroundByCondition(dmPagerBackground);
    }
    @PostMapping("/findOneDmPagerBackgroundByCondition")
    @ApiOperation(value = "根据条件查找单个点阵试卷背景表,结果必须为单条数据", notes = "返回单个点阵试卷背景表,没有时为空")
    public DmPagerBackground findOneDmPagerBackgroundByCondition(
            @ApiParam(value = "点阵试卷背景表对象")
            @RequestBody DmPagerBackground dmPagerBackground){
        return dmPagerBackgroundService.findOneDmPagerBackgroundByCondition(dmPagerBackground);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/upload/{latticeId}")
    @ApiOperation(value = "试卷上传背景", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file,
                               @PathVariable("latticeId") String latticeId){
        return dmPagerBackgroundService.upload(file,latticeId);
    }


    @PostMapping("/textUpdateDmPagerBackground")
    @ApiOperation(value = "修改点阵试卷背景表有值的字段", notes = "点阵试卷背景表对象必传")
    public ResponseJson textUpdateDmPagerBackground(
            @ApiParam(value = "点阵试卷背景表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPagerBackground dmPagerBackground){
        return dmPagerBackgroundService.textUpdateDmPagerBackground(dmPagerBackground);
    }



    @PostMapping("/reUpload/{id}")
    @ApiOperation(value = "单条记录重新试卷上传背景", notes = "返回状态和资源的url")
    public ResponseJson reUpload(@ApiParam(value = "上传的文件", required = true) MultipartFile file,
                                 @PathVariable("id") String id){
        return  dmPagerBackgroundService.reUpload(file,id);
    }

}
