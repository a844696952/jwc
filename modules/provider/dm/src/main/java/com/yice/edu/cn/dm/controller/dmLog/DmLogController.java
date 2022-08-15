package com.yice.edu.cn.dm.controller.dmLog;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.dm.service.dmlog.DmLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmLog")
@Api(value = "/dmLog",description = "云班牌日志表模块")
public class DmLogController {
    @Autowired
    private DmLogService dmLogService;

    @GetMapping("/findDmLogById/{id}")
    @ApiOperation(value = "通过id查找云班牌日志表", notes = "返回云班牌日志表对象")
    public DmLog findDmLogById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmLogService.findDmLogById(id);
    }

    @PostMapping("/saveDmLog")
    @ApiOperation(value = "保存云班牌日志表", notes = "返回云班牌日志表对象")
    public DmLog saveDmLog(
            @ApiParam(value = "云班牌日志表对象", required = true)
            @RequestBody DmLog dmLog){
        dmLogService.saveDmLog(dmLog);
        return dmLog;
    }

    @PostMapping("/findDmLogListByCondition")
    @ApiOperation(value = "根据条件查找云班牌日志表列表", notes = "返回云班牌日志表列表")
    public List<DmLog> findDmLogListByCondition(
            @ApiParam(value = "云班牌日志表对象")
            @RequestBody DmLog dmLog){
        return dmLogService.findDmLogListByCondition(dmLog);
    }
    @PostMapping("/findDmLogCountByCondition")
    @ApiOperation(value = "根据条件查找云班牌日志表列表个数", notes = "返回云班牌日志表总个数")
    public long findDmLogCountByCondition(
            @ApiParam(value = "云班牌日志表对象")
            @RequestBody DmLog dmLog){
        return dmLogService.findDmLogCountByCondition(dmLog);
    }

    @PostMapping("/updateDmLog")
    @ApiOperation(value = "修改云班牌日志表", notes = "云班牌日志表对象必传")
    public void updateDmLog(
            @ApiParam(value = "云班牌日志表对象,对象属性不为空则修改", required = true)
            @RequestBody DmLog dmLog){
        dmLogService.updateDmLog(dmLog);
    }

    @GetMapping("/deleteDmLog/{id}")
    @ApiOperation(value = "通过id删除云班牌日志表")
    public void deleteDmLog(
            @ApiParam(value = "云班牌日志表对象", required = true)
            @PathVariable String id){
        dmLogService.deleteDmLog(id);
    }
    @PostMapping("/deleteDmLogByCondition")
    @ApiOperation(value = "根据条件删除云班牌日志表")
    public void deleteDmLogByCondition(
            @ApiParam(value = "云班牌日志表对象")
            @RequestBody DmLog dmLog){
        dmLogService.deleteDmLogByCondition(dmLog);
    }
    @PostMapping("/findOneDmLogByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌日志表,结果必须为单条数据", notes = "返回单个云班牌日志表,没有时为空")
    public DmLog findOneDmLogByCondition(
            @ApiParam(value = "云班牌日志表对象")
            @RequestBody DmLog dmLog){
        return dmLogService.findOneDmLogByCondition(dmLog);
    }

    @PostMapping("/batchSaveDmLog")
    @ApiOperation(value = "批量保存云班牌日志表", notes = "返回云班牌日志表对象")
    public DmLog batchSaveDmLog(
            @ApiParam(value = "云班牌日志表对象", required = true)
            @RequestBody List<DmLog> dmLog){
        dmLogService.batchSaveDmLog(dmLog);
        return dmLog.get(0);
    }
}
