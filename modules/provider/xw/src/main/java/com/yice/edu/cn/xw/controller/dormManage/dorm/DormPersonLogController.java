package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormPersonLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dormPersonLog")
@Api(value = "/dormPersonLog",description = "模块")
public class DormPersonLogController {
    @Autowired
    private DormPersonLogService dormPersonLogService;

    @GetMapping("/findDormPersonLogById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DormPersonLog findDormPersonLogById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dormPersonLogService.findDormPersonLogById(id);
    }

    @PostMapping("/saveDormPersonLog")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DormPersonLog saveDormPersonLog(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPersonLog dormPersonLog){
        dormPersonLogService.saveDormPersonLog(dormPersonLog);
        return dormPersonLog;
    }

    @PostMapping("/findDormPersonLogListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DormPersonLog> findDormPersonLogListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonLog dormPersonLog){
        return dormPersonLogService.findDormPersonLogListByCondition(dormPersonLog);
    }
    @PostMapping("/findDormPersonLogCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDormPersonLogCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonLog dormPersonLog){
        return dormPersonLogService.findDormPersonLogCountByCondition(dormPersonLog);
    }

    @PostMapping("/updateDormPersonLog")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDormPersonLog(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DormPersonLog dormPersonLog){
        dormPersonLogService.updateDormPersonLog(dormPersonLog);
    }

    @GetMapping("/deleteDormPersonLog/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDormPersonLog(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dormPersonLogService.deleteDormPersonLog(id);
    }
    @PostMapping("/deleteDormPersonLogByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDormPersonLogByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonLog dormPersonLog){
        dormPersonLogService.deleteDormPersonLogByCondition(dormPersonLog);
    }
    @PostMapping("/findOneDormPersonLogByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DormPersonLog findOneDormPersonLogByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonLog dormPersonLog){
        return dormPersonLogService.findOneDormPersonLogByCondition(dormPersonLog);
    }
}
