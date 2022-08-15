package com.yice.edu.cn.osp.controller.jw.role;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.jw.perm.PermService;
import com.yice.edu.cn.osp.service.jw.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/role")
@Api(value = "/role",description = "学校角色模块")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermService permService;
    @PostMapping("/saveRole")
    @ApiOperation(value = "保存学校角色对象", notes = "返回响应对象")
    public ResponseJson saveRole(
            @ApiParam(value = "学校角色对象", required = true)
            @RequestBody Role role){
        role.setSchoolId(mySchoolId());
        Role s=roleService.saveRole(role);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRoleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校角色", notes = "返回响应对象")
    public ResponseJson findRoleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Role role=roleService.findRoleById(id);
        return new ResponseJson(role);
    }

    @PostMapping("/update/updateRole")
    @ApiOperation(value = "修改学校角色对象", notes = "返回响应对象")
    public ResponseJson updateRole(
            @ApiParam(value = "被修改的学校角色对象,对象属性不为空则修改", required = true)
            @RequestBody Role role){
        roleService.updateRole(role);
        return new ResponseJson();
    }

    @GetMapping("/look/lookRoleById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校角色", notes = "返回响应对象")
    public ResponseJson lookRoleById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Role role=roleService.findRoleById(id);
        return new ResponseJson(role);
    }

    @PostMapping("/findRolesByCondition")
    @ApiOperation(value = "根据条件查找学校角色", notes = "返回响应对象")
    public ResponseJson findRolesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Role role){
        role.setSchoolId(currentTeacher().getSchoolId());
        Pager pager = role.getPager()==null?new Pager().setPaging(false):role.getPager();
        pager.setLike("title");
        role.setPager(pager);
        List<Role> data=roleService.findRoleListByCondition(role);
        long count=roleService.findRoleCountByCondition(role);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteRole/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRole(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        roleService.deleteRole(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteRoleByCondition")
    @ApiOperation(value = "根据条件删除学校角色", notes = "返回响应对象")
    public ResponseJson deleteRoleByCondition(
            @ApiParam(value = "被删除的学校角色对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Role role){
        roleService.deleteRoleByCondition(role);
        return new ResponseJson();
    }

    @GetMapping("/findPermsByRoleId/{id}")
    public ResponseJson findPermsByRoleId(@PathVariable String id){
        Perm perm = new Perm();
        perm.setSchoolId(mySchoolId());
        perm.setPager(new Pager().setPaging(false).setIncludes("id","title","parentId","identify").setSortField("sortNum").setSortOrder(Pager.ASC));
        List<Perm> perms = permService.findPermListByCondition(perm);
        List<String> checked=roleService.findCheckedPermIdsByRoleId(id);
        return new ResponseJson(ObjectKit.buildTree(perms,"-1"), checked);
    }
    @GetMapping("/deleteRoleRelated/{id}")
    @ApiOperation(value = "根据id删除角色相关的数据")
    public ResponseJson deleteRoleRelated(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        roleService.deleteRoleRelated(id);
        return new ResponseJson();
    }

    @PostMapping("/delsertRolePerms")
    public ResponseJson delsertRolePerms(@RequestBody RolePerm rolePerm){
        rolePerm.setSchoolId(mySchoolId());
        roleService.delsertRolePerms(rolePerm);

        return new ResponseJson();
    }

}
