package com.yice.edu.cn.jy.controller.handout;

import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.jy.service.handout.HandoutFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/handoutFile")
@Api(value = "/handoutFile",description = "模块")
public class HandoutFileController {
    @Autowired
    private HandoutFileService handoutFileService;

    @GetMapping("/findHandoutFileById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public HandoutFile findHandoutFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return handoutFileService.findHandoutFileById(id);
    }

    @PostMapping("/saveHandoutFile")
    @ApiOperation(value = "保存", notes = "返回对象")
    public HandoutFile saveHandoutFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody HandoutFile handoutFile){
        handoutFileService.saveHandoutFile(handoutFile);
        return handoutFile;
    }

    @PostMapping("/findHandoutFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<HandoutFile> findHandoutFileListByCondition(
            @ApiParam(value = "对象")
            @RequestBody HandoutFile handoutFile){
        return handoutFileService.findHandoutFileListByCondition(handoutFile);
    }
    @PostMapping("/findHandoutFileCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHandoutFileCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody HandoutFile handoutFile){
        return handoutFileService.findHandoutFileCountByCondition(handoutFile);
    }

    @PostMapping("/updateHandoutFile")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHandoutFile(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody HandoutFile handoutFile){
        handoutFileService.updateHandoutFile(handoutFile);
    }

    @GetMapping("/deleteHandoutFile/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHandoutFile(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        handoutFileService.deleteHandoutFile(id);
    }
    @PostMapping("/deleteHandoutFileByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHandoutFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody HandoutFile handoutFile){
        handoutFileService.deleteHandoutFileByCondition(handoutFile);
    }
    @PostMapping("/findOneHandoutFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public HandoutFile findOneHandoutFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody HandoutFile handoutFile){
        return handoutFileService.findOneHandoutFileByCondition(handoutFile);
    }
}
