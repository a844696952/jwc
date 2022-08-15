package com.yice.edu.cn.jw.controller.auth;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.jw.service.auth.SchoolPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolPerm")
@Api(value = "/schoolPerm",description = "学校总权限模块")
public class SchoolPermController {
    @Autowired
    private SchoolPermService schoolPermService;

    @GetMapping("/findSchoolPermById/{id}")
    @ApiOperation(value = "通过id查找学校总权限", notes = "返回学校总权限对象")
    public SchoolPerm findSchoolPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolPermService.findSchoolPermById(id);
    }

    @PostMapping("/saveSchoolPerm")
    @ApiOperation(value = "保存学校总权限", notes = "返回学校总权限对象")
    public SchoolPerm saveSchoolPerm(
            @ApiParam(value = "学校总权限对象", required = true)
            @RequestBody SchoolPerm schoolPerm){
        schoolPermService.saveSchoolPerm(schoolPerm);
        return schoolPerm;
    }

    @PostMapping("/findSchoolPermListByCondition")
    @ApiOperation(value = "根据条件查找学校总权限列表", notes = "返回学校总权限列表")
    public List<SchoolPerm> findSchoolPermListByCondition(
            @ApiParam(value = "学校总权限对象")
            @RequestBody SchoolPerm schoolPerm){
        return schoolPermService.findSchoolPermListByCondition(schoolPerm);
    }
    @PostMapping("/findSchoolPermCountByCondition")
    @ApiOperation(value = "根据条件查找学校总权限列表个数", notes = "返回学校总权限总个数")
    public long findSchoolPermCountByCondition(
            @ApiParam(value = "学校总权限对象")
            @RequestBody SchoolPerm schoolPerm){
        return schoolPermService.findSchoolPermCountByCondition(schoolPerm);
    }

    @PostMapping("/updateSchoolPerm")
    @ApiOperation(value = "修改学校总权限", notes = "学校总权限对象必传")
    public void updateSchoolPerm(
            @ApiParam(value = "学校总权限对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolPerm schoolPerm){
        schoolPermService.updateSchoolPerm(schoolPerm);
    }

    @GetMapping("/deleteSchoolPerm/{id}")
    @ApiOperation(value = "通过id删除学校总权限")
    public void deleteSchoolPerm(
            @ApiParam(value = "学校总权限对象", required = true)
            @PathVariable String id){
        schoolPermService.deleteSchoolPerm(id);
    }
    @PostMapping("/deleteSchoolPermByCondition")
    @ApiOperation(value = "根据条件删除学校总权限")
    public void deleteSchoolPermByCondition(
            @ApiParam(value = "学校总权限对象")
            @RequestBody SchoolPerm schoolPerm){
        schoolPermService.deleteSchoolPermByCondition(schoolPerm);
    }

    @GetMapping("/findAllTreeMenu")
    @ApiOperation(value = "查找所有的学校总权限,形成树")
    public List<SysPerm> findAllTreeMenu(){
        return schoolPermService.findAllTreeMenu();
    }

    @GetMapping("/deleteSchoolPermRecursive/{id}")
    public void deleteSchoolPermRecursive(@PathVariable("id") String id){
        schoolPermService.deleteSchoolPermRecursive(id);
    }


    @PostMapping("/updatePerms/{schoolId}")
    public void updatePerms(@PathVariable("schoolId") String schoolId,@RequestBody List<String> checkedIds){
        schoolPermService.updatePerms(schoolId, checkedIds);
    }

    @GetMapping("/syncUpdate")
    public void syncUpdate(){
        schoolPermService.syncUpdate();
    }

    @PostMapping("/batchUpdateSortNum")
    public void batchUpdateSortNum(@RequestBody List<SchoolPerm> perms){
        schoolPermService.batchUpdateSortNum(perms);
    }

    @GetMapping("/findSchoolAndAppPermRelation/{id}")
    public ResponseJson findSchoolAndAppPermRelation(@PathVariable("id") String id){
       return schoolPermService.findSchoolAndAppPermRelation(id);
    }
}
