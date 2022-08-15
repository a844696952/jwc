package com.yice.edu.cn.osp.controller.zc.repairNew;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/repairStaff")
@Api(value = "/repairStaff",description = "维修人员信息表模块")
public class RepairStaffController {
    @Autowired
    private RepairStaffService repairStaffService;

    @PostMapping("/saveRepairStaff")
    @ApiOperation(value = "保存维修人员信息表对象", notes = "返回响应对象")
    public ResponseJson saveRepairStaff(
            @ApiParam(value = "维修人员信息表对象", required = true)
            @RequestBody RepairStaff repairStaff){
       repairStaff.setSchoolId(mySchoolId());
        RepairStaff s=repairStaffService.saveRepairStaff(repairStaff);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRepairStaffById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson findRepairStaffById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairStaff repairStaff=repairStaffService.findRepairStaffById(id);
        return new ResponseJson(repairStaff);
    }

    @PostMapping("/update/updateRepairStaff")
    @ApiOperation(value = "修改维修人员信息表对象", notes = "返回响应对象")
    public ResponseJson updateRepairStaff(
            @ApiParam(value = "被修改的维修人员信息表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairStaff repairStaff){
        repairStaffService.updateRepairStaff(repairStaff);
        return new ResponseJson();
    }

    @GetMapping("/look/lookRepairStaffById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson lookRepairStaffById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairStaff repairStaff=repairStaffService.findRepairStaffById(id);
        return new ResponseJson(repairStaff);
    }

    @PostMapping("/findRepairStaffsByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson findRepairStaffsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairStaff repairStaff){
       repairStaff.setSchoolId(mySchoolId());
        List<RepairStaff> data=repairStaffService.findRepairStaffListByCondition(repairStaff);
        long count=repairStaffService.findRepairStaffCountByCondition(repairStaff);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneRepairStaffByCondition")
    @ApiOperation(value = "根据条件查找单个维修人员信息表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneRepairStaffByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RepairStaff repairStaff){
        RepairStaff one=repairStaffService.findOneRepairStaffByCondition(repairStaff);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteRepairStaff/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRepairStaff(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        repairStaffService.deleteRepairStaff(id);
        return new ResponseJson();
    }


    @PostMapping("/findRepairStaffListByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findRepairStaffListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairStaff repairStaff){
       repairStaff.setSchoolId(mySchoolId());
        List<RepairStaff> data=repairStaffService.findRepairStaffListByCondition(repairStaff);
        return new ResponseJson(data);
    }


    @PostMapping("/findRepairStaffDormsByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson findRepairStaffDormsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairStaff repairStaff){
        repairStaff.setSchoolId(mySchoolId());
        List<RepairStaff> data=repairStaffService.findRepairStaffDormsByCondition(repairStaff);
        long count=repairStaffService.findRepairStaffCountByCondition1(repairStaff);
        return new ResponseJson(data,count);
    }

    @PostMapping("/saveRepairStaffDorms")
    @ApiOperation(value = "保存维修人员信息表对象", notes = "返回响应对象")
    public ResponseJson saveRepairStaffDorms(
            @ApiParam(value = "维修人员信息表对象", required = true)
            @RequestBody RepairStaff repairStaff){
        repairStaff.setSchoolId(mySchoolId());
        repairStaff.setStaffType("2");
        RepairStaff s=repairStaffService.saveRepairStaff(repairStaff);
        return new ResponseJson(s);
    }
}
