package com.yice.edu.cn.osp.controller.xw.dutyLocation;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import com.yice.edu.cn.osp.service.xw.dutyLocation.DutyLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dutyLocation")
@Api(value = "/dutyLocation",description = "值班地点管理模块")
public class DutyLocationController {
    @Autowired
    private DutyLocationService dutyLocationService;

    @PostMapping("/saveDutyLocation")
    @ApiOperation(value = "保存值班地点管理对象", notes = "返回保存好的值班地点管理对象", response=DutyLocation.class)
    public ResponseJson saveDutyLocation(
            @ApiParam(value = "值班地点管理对象", required = true)
            @RequestBody DutyLocation dutyLocation){
        dutyLocation.setSchoolId(mySchoolId());
        dutyLocation.setTeacherId(myId());
        DutyLocation s=dutyLocationService.saveDutyLocation(dutyLocation);
        return new ResponseJson(s);
    }

    @GetMapping("/deleteDutyLocation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDutyLocation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        DutyLocation dutyLocation = new DutyLocation();
        dutyLocation.setId(id);
        dutyLocation.setSchoolId(mySchoolId());
        DutyLocation result = dutyLocationService.deleteDutyLocationByCondition(dutyLocation);
        return new ResponseJson(result);
    }

    @PostMapping("/findDutyLocationsByCondition")
    @ApiOperation(value = "根据条件查找值班地点管理", notes = "返回响应对象", response=DutyLocation.class)
    public ResponseJson findDutyLocationsByCondition(
            @ApiParam(value = "createTime以创建时间倒序排序")
            @RequestBody DutyLocation dutyLocation){
        dutyLocation.setSchoolId(mySchoolId());
        List<DutyLocation> data=dutyLocationService.findDutyLocationListByCondition(dutyLocation);
        long count=dutyLocationService.findDutyLocationCountByCondition(dutyLocation);
        return new ResponseJson(data,count);
    }
}
