package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormPersonLog")
@Api(value = "/dormPersonLog",description = "人员入住历史模块")
public class DormPersonLogController {
    @Autowired
    private DormPersonLogService dormPersonLogService;

    @PostMapping("/saveDormPersonLog")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=DormPersonLog.class)
    public ResponseJson saveDormPersonLog(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPersonLog dormPersonLog){
       dormPersonLog.setSchoolId(mySchoolId());
        DormPersonLog s=dormPersonLogService.saveDormPersonLog(dormPersonLog);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDormPersonLogById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DormPersonLog.class)
    public ResponseJson findDormPersonLogById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DormPersonLog dormPersonLog=dormPersonLogService.findDormPersonLogById(id);
        return new ResponseJson(dormPersonLog);
    }

    @PostMapping("/update/updateDormPersonLog")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDormPersonLog(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DormPersonLog dormPersonLog){
        dormPersonLogService.updateDormPersonLog(dormPersonLog);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDormPersonLogById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DormPersonLog.class)
    public ResponseJson lookDormPersonLogById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DormPersonLog dormPersonLog=dormPersonLogService.findDormPersonLogById(id);
        return new ResponseJson(dormPersonLog);
    }

    @PostMapping("/findDormPersonLogsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DormPersonLog.class)
    public ResponseJson findDormPersonLogsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonLog dormPersonLog){
       dormPersonLog.setSchoolId(mySchoolId());
        List<DormPersonLog> data=dormPersonLogService.findDormPersonLogListByCondition(dormPersonLog);
        long count=dormPersonLogService.findDormPersonLogCountByCondition(dormPersonLog);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDormPersonLogByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DormPersonLog.class)
    public ResponseJson findOneDormPersonLogByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormPersonLog dormPersonLog){
        DormPersonLog one=dormPersonLogService.findOneDormPersonLogByCondition(dormPersonLog);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDormPersonLog/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDormPersonLog(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dormPersonLogService.deleteDormPersonLog(id);
        return new ResponseJson();
    }


    @PostMapping("/findDormPersonLogListByCondition")
    @ApiOperation(value = "根据条件查找住宿记录详情列表", notes = "返回响应对象,不包含总条数", response=DormPersonLog.class)
    public ResponseJson findDormPersonLogListByCondition(
            @ApiParam(value = "查询人员入住记录,personId必传,属性不为空则作为条件查询")
            @RequestBody DormPersonLog dormPersonLog){
       dormPersonLog.setSchoolId(mySchoolId());
        List<DormPersonLog> data=dormPersonLogService.findDormPersonLogListByCondition(dormPersonLog);
        return new ResponseJson(data);
    }
}
