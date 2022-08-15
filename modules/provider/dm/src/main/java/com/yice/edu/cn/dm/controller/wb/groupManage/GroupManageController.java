package com.yice.edu.cn.dm.controller.wb.groupManage;

import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import com.yice.edu.cn.dm.service.wb.groupManage.GroupManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupManage")
@Api(value = "/groupManage",description = "分组管理模块")
public class GroupManageController {
    @Autowired
    private GroupManageService groupManageService;

    @GetMapping("/findGroupManageById/{id}")
    @ApiOperation(value = "通过id查找分组管理", notes = "返回分组管理对象")
    public GroupManage findGroupManageById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return groupManageService.findGroupManageById(id);
    }

    @PostMapping("/saveGroupManage")
    @ApiOperation(value = "保存分组管理", notes = "返回分组管理对象")
    public int saveGroupManage(
            @ApiParam(value = "分组管理对象", required = true)
            @RequestBody GroupManage groupManage){
        return groupManageService.saveGroupManage(groupManage);

    }

    @PostMapping("/findGroupManageListByCondition")
    @ApiOperation(value = "根据条件查找分组管理列表", notes = "返回分组管理列表")
    public List<GroupManage> findGroupManageListByCondition(
            @ApiParam(value = "分组管理对象")
            @RequestBody GroupManage groupManage){
        return groupManageService.findGroupManageListByCondition(groupManage);
    }
    @PostMapping("/findGroupManageCountByCondition")
    @ApiOperation(value = "根据条件查找分组管理列表个数", notes = "返回分组管理总个数")
    public long findGroupManageCountByCondition(
            @ApiParam(value = "分组管理对象")
            @RequestBody GroupManage groupManage){
        return groupManageService.findGroupManageCountByCondition(groupManage);
    }

    @PostMapping("/updateGroupManage")
    @ApiOperation(value = "修改分组管理", notes = "分组管理对象必传")
    public int updateGroupManage(
            @ApiParam(value = "分组管理对象,对象属性不为空则修改", required = true)
            @RequestBody GroupManage groupManage){
        return groupManageService.updateGroupManage(groupManage);
    }


    @GetMapping("/deleteGroupManage/{id}")
    @ApiOperation(value = "通过id删除分组管理")
    public void deleteGroupManage(
            @ApiParam(value = "分组管理对象", required = true)
            @PathVariable String id){
        groupManageService.deleteGroupManage(id);
    }
    @PostMapping("/deleteGroupManageByCondition")
    @ApiOperation(value = "根据条件删除分组管理")
    public void deleteGroupManageByCondition(
            @ApiParam(value = "分组管理对象")
            @RequestBody GroupManage groupManage){
        groupManageService.deleteGroupManageByCondition(groupManage);
    }
    @PostMapping("/findOneGroupManageByCondition")
    @ApiOperation(value = "根据条件查找单个分组管理,结果必须为单条数据", notes = "返回单个分组管理,没有时为空")
    public GroupManage findOneGroupManageByCondition(
            @ApiParam(value = "分组管理对象")
            @RequestBody GroupManage groupManage){
        return groupManageService.findOneGroupManageByCondition(groupManage);
    }

    @PostMapping("/moveGroupManage")
    @ApiOperation(value = "移动分组", notes = "分组管理对象必传")
    public void moveGroupManage(
            @ApiParam(value = "分组管理对象,对象属性不为空则修改", required = true)
            @RequestBody List<GroupManage> groupManages){
        groupManageService.moveGroupManage(groupManages);
    }

}
