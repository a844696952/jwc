package com.yice.edu.cn.xw.controller.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import com.yice.edu.cn.xw.service.zc.repairNew.RepairStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repairStaff")
@Api(value = "/repairStaff",description = "维修人员信息表模块")
public class RepairStaffController {
    @Autowired
    private RepairStaffService repairStaffService;

    @GetMapping("/findRepairStaffById/{id}")
    @ApiOperation(value = "通过id查找维修人员信息表", notes = "返回维修人员信息表对象")
    public RepairStaff findRepairStaffById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return repairStaffService.findRepairStaffById(id);
    }

    @PostMapping("/saveRepairStaff")
    @ApiOperation(value = "保存维修人员信息表", notes = "返回维修人员信息表对象")
    public RepairStaff saveRepairStaff(
            @ApiParam(value = "维修人员信息表对象", required = true)
            @RequestBody RepairStaff repairStaff){
        repairStaffService.saveRepairStaff(repairStaff);
        return repairStaff;
    }

    @PostMapping("/findRepairStaffListByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表列表", notes = "返回维修人员信息表列表")
    public List<RepairStaff> findRepairStaffListByCondition(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        return repairStaffService.findRepairStaffListByCondition(repairStaff);
    }
    @PostMapping("/findRepairStaffCountByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表列表个数", notes = "返回维修人员信息表总个数")
    public long findRepairStaffCountByCondition(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        return repairStaffService.findRepairStaffCountByCondition(repairStaff);
    }

    @PostMapping("/updateRepairStaff")
    @ApiOperation(value = "修改维修人员信息表", notes = "维修人员信息表对象必传")
    public void updateRepairStaff(
            @ApiParam(value = "维修人员信息表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairStaff repairStaff){
        repairStaffService.updateRepairStaff(repairStaff);
    }

    @GetMapping("/deleteRepairStaff/{id}")
    @ApiOperation(value = "通过id删除维修人员信息表")
    public void deleteRepairStaff(
            @ApiParam(value = "维修人员信息表对象", required = true)
            @PathVariable String id){
        repairStaffService.deleteRepairStaff(id);
    }
    @PostMapping("/deleteRepairStaffByCondition")
    @ApiOperation(value = "根据条件删除维修人员信息表")
    public void deleteRepairStaffByCondition(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        repairStaffService.deleteRepairStaffByCondition(repairStaff);
    }
    @PostMapping("/findOneRepairStaffByCondition")
    @ApiOperation(value = "根据条件查找单个维修人员信息表,结果必须为单条数据", notes = "返回单个维修人员信息表,没有时为空")
    public RepairStaff findOneRepairStaffByCondition(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        return repairStaffService.findOneRepairStaffByCondition(repairStaff);
    }



    @PostMapping("/findRepairStaffDormsByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表列表", notes = "返回维修人员信息表列表")
    public List<RepairStaff> findRepairStaffDormsByCondition(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        return repairStaffService.findRepairStaffDormsByCondition(repairStaff);
    }
    @PostMapping("/findRepairStaffCountByCondition1")
    @ApiOperation(value = "根据条件查找维修人员信息表列表个数", notes = "返回维修人员信息表总个数")
    public long findRepairStaffCountByCondition1(
            @ApiParam(value = "维修人员信息表对象")
            @RequestBody RepairStaff repairStaff){
        return repairStaffService.findRepairStaffCountByCondition1(repairStaff);
    }

}
