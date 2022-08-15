package com.yice.edu.cn.ewb.controller.wisdomclassroom;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.ewb.service.wisdomclassroom.GroupManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/groupManage")
@Api(value = "/groupManage",description = "分组管理模块")
public class GroupManageController {
    @Autowired
    private GroupManageService groupManageService;

    @PostMapping("/saveGroupManage")
    @ApiOperation(value = "保存分组管理对象", notes = "返回保存好的分组管理对象", response= GroupManage.class)
    public ResponseJson saveGroupManage(
            @ApiParam(value = "分组管理对象", required = true)
            @RequestBody GroupManage groupManage){
        groupManage.setSchoolId(mySchoolId());
        groupManageService.saveGroupManage(groupManage);
        return new ResponseJson();
    }

    @GetMapping("/update/findGroupManageById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找分组管理", notes = "返回响应对象", response=GroupManage.class)
    public ResponseJson findGroupManageById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        GroupManage groupManage=groupManageService.findGroupManageById(id);
        return new ResponseJson(groupManage);
    }

    @PostMapping("/update/updateGroupManage")
    @ApiOperation(value = "修改分组管理对象", notes = "返回响应对象")
    public ResponseJson updateGroupManage(
            @ApiParam(value = "被修改的分组管理对象,对象属性不为空则修改", required = true)
            @RequestBody GroupManage groupManage){
        groupManageService.updateGroupManage(groupManage);
        return new ResponseJson();
    }

    @GetMapping("/look/lookGroupManageById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找分组管理", notes = "返回响应对象", response=GroupManage.class)
    public ResponseJson lookGroupManageById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        GroupManage groupManage=groupManageService.findGroupManageById(id);
        return new ResponseJson(groupManage);
    }

    @PostMapping("/findGroupManagesByCondition")
    @ApiOperation(value = "根据条件查找分组管理", notes = "返回响应对象", response=GroupManage.class)
    public ResponseJson findGroupManagesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody GroupManage groupManage){
        groupManage.setSchoolId(mySchoolId());
        groupManage.setStartTime(DateUtils.getOriginalDateTime(groupManage.getStartTime(),1));
        groupManage.setEndTime(DateUtils.getOriginalDateTime(groupManage.getEndTime(),2));
        List<GroupManage> data=groupManageService.findGroupManageListByCondition(groupManage);
        long count=groupManageService.findGroupManageCountByCondition(groupManage);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneGroupManageByCondition")
    @ApiOperation(value = "根据条件查找单个分组管理,结果必须为单条数据", notes = "没有时返回空", response=GroupManage.class)
    public ResponseJson findOneGroupManageByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody GroupManage groupManage){
        GroupManage one=groupManageService.findOneGroupManageByCondition(groupManage);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteGroupManage/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteGroupManage(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        groupManageService.deleteGroupManage(id);
        return new ResponseJson();
    }


    @PostMapping("/findGroupManageListByCondition")
    @ApiOperation(value = "根据条件查找分组管理列表", notes = "返回响应对象,不包含总条数", response=GroupManage.class)
    public ResponseJson findGroupManageListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody GroupManage groupManage){
        groupManage.setSchoolId(mySchoolId());
        List<GroupManage> data=groupManageService.findGroupManageListByCondition(groupManage);
        return new ResponseJson(data);
    }


    @PostMapping("/update/moveGroupManage")
    @ApiOperation(value = "移动分组", notes = "返回响应对象")
    public ResponseJson moveGroupManage(
            @ApiParam(value = "移动后的对象集合", required = true)
            @RequestBody List<GroupManage> groupManages){
        groupManageService.moveGroupManage(groupManages);
        return new ResponseJson();
    }





}
