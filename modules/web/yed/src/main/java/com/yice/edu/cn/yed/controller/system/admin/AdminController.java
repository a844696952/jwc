package com.yice.edu.cn.yed.controller.system.admin;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.pojo.yedAdmin.AdminSysRole;
import com.yice.edu.cn.yed.service.baseData.educationBureau.EducationBureauService;
import com.yice.edu.cn.yed.service.system.admin.AdminService;
import com.yice.edu.cn.yed.service.system.adminSysRole.AdminSysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;
import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/admin")
@Api(value = "/admin",description = "管理员模块")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminSysRoleService adminSysRoleService;
    @Autowired
    private EducationBureauService educationBureauService;

    @GetMapping("/findAdminById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回响应对象")
    public ResponseJson findAdminById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Admin admin=adminService.findAdminById(id);
        return new ResponseJson(admin);
    }

    @PostMapping("/saveAdmin")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveAdmin(
            @ApiParam(value = "对象", required = true)
            @RequestBody Admin admin){
        admin.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        Admin s=adminService.saveAdmin(admin);
        return new ResponseJson(s);
    }
    @PostMapping("/updateAdmin")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateAdmin(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Admin admin){
        adminService.updateAdmin(admin);
        return new ResponseJson();
    }

    @PostMapping("/findAdminsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findAdminsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Admin admin){
        List<Admin> data=adminService.findAdminListByCondition(admin);
        long count=adminService.findAdminCountByCondition(admin);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteAdmin/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAdmin(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        adminService.deleteAdmin(id);
        return new ResponseJson();
    }

    @GetMapping("/findSysRolesByAdminId/{id}")
    @ApiOperation(value = "根据adminId获取角色列表", notes = "返回响应对象")
    public ResponseJson findSysRolesByAdminId(@PathVariable("id") String id){
        return adminService.findSysRolesByAdminId(id);
    }

    @PostMapping("/saveAdminSysRoles")
    public ResponseJson saveAdminSysRoles(@RequestBody List<AdminSysRole> adminSysRoles){
        adminSysRoleService.batchSaveAdminSysRole(adminSysRoles);
        return new ResponseJson();
    }

    @PostMapping("/delsertAdminSysRoles")
    public ResponseJson delsertAdminSysRoles(@RequestBody Map<String,String> map){
        adminSysRoleService.delsertAdminSysRoles(map);
        return new ResponseJson();
    }


    @GetMapping("/ignore/findAllEducationBureaus")
    public ResponseJson findAllEducationBureaus(){
        List<EducationBureau> r = educationBureauService.findEducationBureauListByCondition(new EducationBureau());
        return new ResponseJson(r);
    }

    /*gzw*/
    @GetMapping("/ignore/findMySelf")
    public ResponseJson findMySelf(){
        Admin admin = adminService.findAdminById(myId());
        admin.setPassword(null);
        return new ResponseJson(admin);
    }

    @PostMapping("/ignore/updateMySelf")
    public ResponseJson updateMySelf(@RequestBody Admin admin){
        admin.setId(myId());
        final Admin a = new Admin();
        a.setId(myId());
        a.setPortrait(admin.getPortrait());
        a.setRealName(admin.getRealName());
        a.setPhone(admin.getPhone());
        a.setEmail(admin.getEmail());
        Admin result = adminService.updateMySelf(a);
        return new ResponseJson(result);
    }
    @PostMapping("/ignore/updateMyPassword")
    public ResponseJson updateMyPassword(@Validated(value = GroupTwo.class) @RequestBody Admin admin){
        admin.setId(myId());
        Admin a = adminService.findAdminById(myId());
        String newPassword=DigestUtil.sha1Hex(admin.getNewPassword());
        String oldPassword=DigestUtil.sha1Hex(admin.getPassword());
        if(!a.getPassword().equals(oldPassword)){
            return new ResponseJson(false,"原密码错误");
        }
        a.setPassword(newPassword);
        adminService.updateMySelf(a);
        return new ResponseJson();
    }

}
