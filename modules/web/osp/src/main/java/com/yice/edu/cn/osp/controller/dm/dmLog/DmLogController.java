package com.yice.edu.cn.osp.controller.dm.dmLog;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.osp.service.dm.dmLog.DmLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmLog")
@Api(value = "/dmLog",description = "云班牌日志表模块")
public class DmLogController {
    @Autowired
    private DmLogService dmLogService;

    @PostMapping("/saveDmLog")
    @ApiOperation(value = "保存云班牌日志表对象", notes = "返回保存好的云班牌日志表对象", response=DmLog.class)
    public ResponseJson saveDmLog(
            @ApiParam(value = "云班牌日志表对象", required = true)
            @RequestBody DmLog dmLog){
       dmLog.setSchoolId(mySchoolId());
        DmLog s=dmLogService.saveDmLog(dmLog);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmLogById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云班牌日志表", notes = "返回响应对象", response=DmLog.class)
    public ResponseJson findDmLogById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmLog dmLog=dmLogService.findDmLogById(id);
        return new ResponseJson(dmLog);
    }

    @PostMapping("/update/updateDmLog")
    @ApiOperation(value = "修改云班牌日志表对象", notes = "返回响应对象")
    public ResponseJson updateDmLog(
            @ApiParam(value = "被修改的云班牌日志表对象,对象属性不为空则修改", required = true)
            @RequestBody DmLog dmLog){
        dmLogService.updateDmLog(dmLog);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmLogById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云班牌日志表", notes = "返回响应对象", response=DmLog.class)
    public ResponseJson lookDmLogById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmLog dmLog=dmLogService.findDmLogById(id);
        return new ResponseJson(dmLog);
    }

    @PostMapping("/findDmLogsByCondition")
    @ApiOperation(value = "根据条件查找云班牌日志表", notes = "返回响应对象", response=DmLog.class)
    public ResponseJson findDmLogsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmLog dmLog){
       dmLog.setSchoolId(mySchoolId());
        List<DmLog> data=dmLogService.findDmLogListByCondition(dmLog);
        long count=dmLogService.findDmLogCountByCondition(dmLog);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmLogByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌日志表,结果必须为单条数据", notes = "没有时返回空", response=DmLog.class)
    public ResponseJson findOneDmLogByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmLog dmLog){
        DmLog one=dmLogService.findOneDmLogByCondition(dmLog);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmLog/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmLog(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmLogService.deleteDmLog(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmLogListByCondition")
    @ApiOperation(value = "根据条件查找云班牌日志表列表", notes = "返回响应对象,不包含总条数", response=DmLog.class)
    public ResponseJson findDmLogListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmLog dmLog){
       dmLog.setSchoolId(mySchoolId());
        List<DmLog> data=dmLogService.findDmLogListByCondition(dmLog);
        return new ResponseJson(data);
    }

    @PostMapping("/batchSaveDmLog")
    @ApiOperation(value = "批量保存云班牌日志表对象", notes = "返回保存好的云班牌日志表对象", response=DmLog.class)
    public ResponseJson batchSaveDmLog(
            @ApiParam(value = "云班牌日志表对象", required = true)
            @RequestBody List<DmLog> dmLog){
           dmLogService.batchSaveDmLog(dmLog);
        return new ResponseJson();
    }



}
