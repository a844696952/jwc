package com.yice.edu.cn.jw.controller.admin;

import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.jw.service.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(value = "/admin",description = "模块")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/findAdminById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Admin findAdminById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return adminService.findAdminById(id);
    }

    @PostMapping("/saveAdmin")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Admin saveAdmin(
            @ApiParam(value = "对象", required = true)
            @RequestBody Admin admin){
        adminService.saveAdmin(admin);
        return admin;
    }

    @PostMapping("/findAdminListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Admin> findAdminListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Admin admin){
        return adminService.findAdminListByCondition(admin);
    }
    @PostMapping("/findAdminCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findAdminCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Admin admin){
        return adminService.findAdminCountByCondition(admin);
    }

    @PostMapping("/updateAdmin")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateAdmin(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Admin admin){
        adminService.updateAdmin(admin);
    }

    @GetMapping("/deleteAdmin/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteAdmin(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        adminService.deleteAdmin(id);
    }

    @PostMapping("/adminLogin")
    public Admin adminLogin(@RequestBody Admin admin){
        return adminService.adminLogin(admin);
    }
    @GetMapping("/findCheckedRoloIdsByAdminId/{adminId}")
    public List<String> findCheckedRoloIdsByAdminId(@PathVariable("adminId") String adminId){
        return adminService.findCheckedRoloIdsByAdminId(adminId);
    }

    @GetMapping("/findSysFuncPermsByAdminId/{adminId}")
    @ApiOperation(value = "通过id查找所属的按钮权限列表")
    public List<SysPerm> findSysFuncPermsByAdminId(@PathVariable("adminId") String adminId){
        return adminService.findSysFuncPermsByAdminId(adminId);
    }

}
