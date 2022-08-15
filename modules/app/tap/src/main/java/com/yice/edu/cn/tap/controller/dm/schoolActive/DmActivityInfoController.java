package com.yice.edu.cn.tap.controller.dm.schoolActive;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.tap.service.dm.schoolActive.DmActivityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmActivityInfo")
@Api(value = "/dmActivityInfo",description = "活动信息表模块")
public class DmActivityInfoController {
    @Autowired
    private DmActivityInfoService dmActivityInfoService;

    @PostMapping("/saveDmActivityInfo")
    @ApiOperation(value = "保存活动信息表对象", notes = "返回保存好的活动信息表对象", response= DmActivityInfo.class)
    public ResponseJson saveDmActivityInfo(
            @ApiParam(value = "活动信息表对象", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
       dmActivityInfo.setSchoolId(mySchoolId());
        DmActivityInfo s=dmActivityInfoService.saveDmActivityInfo(dmActivityInfo);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmActivityInfo")
    @ApiOperation(value = "修改活动信息表对象", notes = "返回响应对象")
    public ResponseJson updateDmActivityInfo(
            @ApiParam(value = "被修改的活动信息表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfoService.updateDmActivityInfo(dmActivityInfo);
        return new ResponseJson();
    }

    @GetMapping("/findDmActivityInfoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找活动信息表", notes = "返回响应对象", response=DmActivityInfo.class)
    public ResponseJson findDmActivityInfoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoById(id);
        return new ResponseJson(dmActivityInfo);
    }

    @GetMapping("/deleteDmActivityInfo/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActivityInfo(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActivityInfoService.deleteDmActivityInfo(id);
        return new ResponseJson();
    }



    @GetMapping("/checkStudentIdentity/{studentId}")
    @ApiOperation(value = "检查学生是否为班干部", notes = "返回响应对象")
    public ResponseJson checkStudentIdentity(
            @ApiParam(value = "需要用到的studentId", required = true)
            @PathVariable String studentId){
        long num = dmActivityInfoService.checkStudentIdentity(studentId);
        if(num==0){
            return new ResponseJson(false,"不是班干部");
        }else{
            return new ResponseJson();
        }
    }



    @PostMapping("/selectDmActivityInfosByCondition")
    @ApiOperation(value = "根据条件查找班主任所在年级活动信息表列表", notes = "返回响应对象,不包含总条数", response=DmActivityInfo.class)
    public ResponseJson selectDmActivityInfosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityInfo dmActivityInfo){
        dmActivityInfo.setSchoolId(mySchoolId());
        dmActivityInfo.setTeacherId(currentTeacher().getId());
        List<DmActivityInfo> data=dmActivityInfoService.selectDmActivityInfosByCondition(dmActivityInfo);
        return new ResponseJson(data);
    }



    @GetMapping("/findDmActivityInfoNoItemByActivityId/{activityId}")
    @ApiOperation(value = "去查看页面,通过id查找活动信息表不包含项目", notes = "返回响应对象", response=DmActivityInfo.class)
    public ResponseJson findDmActivityInfoNoItemByActivityId(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String activityId){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoNoItemByActivityId(activityId);
        return new ResponseJson(dmActivityInfo);
    }


    @GetMapping("/findDmActivityInfoByActivityId/{activityId}")
    @ApiOperation(value = "通过activityId查找活动信息表", notes = "返回响应对象", response= DmActivityInfo.class)
    public ResponseJson findDmActivityInfoByActivityId(
            @ApiParam(value = "需要用到的activityId", required = true)
            @PathVariable String activityId){
        DmActivityInfo dmActivityInfo=dmActivityInfoService.findDmActivityInfoByActivityId(activityId);
        return new ResponseJson(dmActivityInfo);
    }





}
