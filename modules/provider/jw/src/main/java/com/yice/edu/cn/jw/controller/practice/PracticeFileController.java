package com.yice.edu.cn.jw.controller.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import com.yice.edu.cn.jw.service.practice.PracticeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/practiceFile")
@Api(value = "/practiceFile",description = "模块")
public class PracticeFileController {
    @Autowired
    private PracticeFileService practiceFileService;

    @GetMapping("/findPracticeFileById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PracticeFile findPracticeFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return practiceFileService.findPracticeFileById(id);
    }


    @PostMapping("/savePracticeFile")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PracticeFile savePracticeFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody PracticeFile practiceFile){
        practiceFileService.savePracticeFile(practiceFile);
        return practiceFile;
    }

    @PostMapping("/findPracticeFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PracticeFile> findPracticeFileListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeFile practiceFile){
        return practiceFileService.findPracticeFileListByCondition(practiceFile);
    }
    @PostMapping("/findPracticeFileCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPracticeFileCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeFile practiceFile){
        return practiceFileService.findPracticeFileCountByCondition(practiceFile);
    }

    @PostMapping("/updatePracticeFile")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePracticeFile(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PracticeFile practiceFile){
        practiceFileService.updatePracticeFile(practiceFile);
    }

    @GetMapping("/deletePracticeFile/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePracticeFile(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        practiceFileService.deletePracticeFile(id);
    }
    @PostMapping("/deletePracticeFileByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePracticeFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeFile practiceFile){
        practiceFileService.deletePracticeFileByCondition(practiceFile);
    }
    @PostMapping("/findOnePracticeFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PracticeFile findOnePracticeFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody PracticeFile practiceFile){
        return practiceFileService.findOnePracticeFileByCondition(practiceFile);
    }
    @GetMapping("/findPracticeFileListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public List<PracticeFile> findPracticeFileListById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id){
        return practiceFileService.findPracticeFileListById(id);
    }

}
