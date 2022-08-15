package com.yice.edu.cn.dm.controller.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import com.yice.edu.cn.dm.service.smartPen.DmAnswerSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmAnswerSheet")
@Api(value = "/dmAnswerSheet",description = "答题卡模块")
public class DmAnswerSheetController {
    @Autowired
    private DmAnswerSheetService dmAnswerSheetService;

    @GetMapping("/findDmAnswerSheetById/{id}")
    @ApiOperation(value = "通过id查找答题卡表", notes = "返回答题卡表对象")
    public DmAnswerSheet findDmAnswerSheetById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmAnswerSheetService.findDmAnswerSheetById(id);
    }

    @PostMapping("/saveDmAnswerSheet")
    @ApiOperation(value = "保存答题卡表", notes = "返回答题卡表对象")
    public DmAnswerSheet saveDmAnswerSheet(
            @ApiParam(value = "答题卡表对象", required = true)
            @RequestBody DmAnswerSheet dmAnswerSheet){
        dmAnswerSheetService.saveDmAnswerSheet(dmAnswerSheet);
        return dmAnswerSheet;
    }

    @PostMapping("/findDmAnswerSheetListByCondition")
    @ApiOperation(value = "根据条件查找答题卡表列表", notes = "返回答题卡表列表")
    public List<DmAnswerSheet> findDmAnswerSheetListByCondition(
            @ApiParam(value = "答题卡表对象")
            @RequestBody DmAnswerSheet dmAnswerSheet){
        return dmAnswerSheetService.findDmAnswerSheetListByCondition(dmAnswerSheet);
    }
    @PostMapping("/findDmAnswerSheetCountByCondition")
    @ApiOperation(value = "根据条件查找答题卡表列表个数", notes = "返回答题卡表总个数")
    public long findDmAnswerSheetCountByCondition(
            @ApiParam(value = "答题卡表对象")
            @RequestBody DmAnswerSheet dmAnswerSheet){
        return dmAnswerSheetService.findDmAnswerSheetCountByCondition(dmAnswerSheet);
    }

    @PostMapping("/updateDmAnswerSheet")
    @ApiOperation(value = "修改答题卡表", notes = "答题卡表对象必传")
    public void updateDmAnswerSheet(
            @ApiParam(value = "答题卡表对象,对象属性不为空则修改", required = true)
            @RequestBody DmAnswerSheet dmAnswerSheet){
        dmAnswerSheetService.updateDmAnswerSheet(dmAnswerSheet);
    }

    @GetMapping("/deleteDmAnswerSheet/{id}")
    @ApiOperation(value = "通过id删除答题卡表")
    public void deleteDmAnswerSheet(
            @ApiParam(value = "答题卡表对象", required = true)
            @PathVariable String id){
        dmAnswerSheetService.deleteDmAnswerSheet(id);
    }
    @PostMapping("/deleteDmAnswerSheetByCondition")
    @ApiOperation(value = "根据条件删除答题卡表")
    public void deleteDmAnswerSheetByCondition(
            @ApiParam(value = "答题卡表对象")
            @RequestBody DmAnswerSheet dmAnswerSheet){
        dmAnswerSheetService.deleteDmAnswerSheetByCondition(dmAnswerSheet);
    }
    @PostMapping("/findOneDmAnswerSheetByCondition")
    @ApiOperation(value = "根据条件查找单个答题卡表,结果必须为单条数据", notes = "返回单个答题卡表,没有时为空")
    public DmAnswerSheet findOneDmAnswerSheetByCondition(
            @ApiParam(value = "答题卡表对象")
            @RequestBody DmAnswerSheet dmAnswerSheet){
        return dmAnswerSheetService.findOneDmAnswerSheetByCondition(dmAnswerSheet);
    }
}
