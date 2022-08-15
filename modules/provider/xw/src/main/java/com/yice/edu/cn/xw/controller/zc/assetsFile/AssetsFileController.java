package com.yice.edu.cn.xw.controller.zc.assetsFile;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.xw.service.zc.assetsFile.AssetsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsFile")
@Api(value = "/assetsFile",description = "资产档案模块")
public class AssetsFileController {
    @Autowired
    private AssetsFileService assetsFileService;

    @GetMapping("/findAssetsFileById/{id}")
    @ApiOperation(value = "通过id查找资产档案", notes = "返回资产档案对象")
    public AssetsFile findAssetsFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return assetsFileService.findAssetsFileById(id);
    }

    @PostMapping("/saveAssetsFile")
    @ApiOperation(value = "保存资产档案", notes = "返回资产档案对象")
    public AssetsFile saveAssetsFile(
            @ApiParam(value = "资产档案对象", required = true)
            @RequestBody AssetsFile assetsFile){
        assetsFileService.saveAssetsFile(assetsFile);
        return assetsFile;
    }

    @PostMapping("/findAssetsFileListByCondition")
    @ApiOperation(value = "根据条件查找资产档案列表", notes = "返回资产档案列表")
    public List<AssetsFile> findAssetsFileListByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        return assetsFileService.findAssetsFileListByCondition(assetsFile);
    }
    @PostMapping("/findAssetsFileCountByCondition")
    @ApiOperation(value = "根据条件查找资产档案列表个数", notes = "返回资产档案总个数")
    public long findAssetsFileCountByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        return assetsFileService.findAssetsFileCountByCondition(assetsFile);
    }

    @PostMapping("/updateAssetsFile")
    @ApiOperation(value = "修改资产档案", notes = "资产档案对象必传")
    public void updateAssetsFile(
            @ApiParam(value = "资产档案对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsFile assetsFile){
        assetsFileService.updateAssetsFile(assetsFile);
    }

    @GetMapping("/deleteAssetsFile/{id}")
    @ApiOperation(value = "通过id删除资产档案")
    public void deleteAssetsFile(
            @ApiParam(value = "资产档案对象", required = true)
            @PathVariable String id){
        assetsFileService.deleteAssetsFile(id);
    }
    @PostMapping("/deleteAssetsFileByCondition")
    @ApiOperation(value = "根据条件删除资产档案")
    public void deleteAssetsFileByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        assetsFileService.deleteAssetsFileByCondition(assetsFile);
    }
    @PostMapping("/findOneAssetsFileByCondition")
    @ApiOperation(value = "根据条件查找单个资产档案,结果必须为单条数据", notes = "返回单个资产档案,没有时为空")
    public AssetsFile findOneAssetsFileByCondition(
            @ApiParam(value = "资产档案对象")
            @RequestBody AssetsFile assetsFile){
        return assetsFileService.findOneAssetsFileByCondition(assetsFile);
    }
}
