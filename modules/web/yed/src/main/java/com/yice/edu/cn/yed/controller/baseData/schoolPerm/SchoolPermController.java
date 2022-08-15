package com.yice.edu.cn.yed.controller.baseData.schoolPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.SchoolPerm;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.yed.service.baseData.schoolPerm.SchoolPermService;
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
    @ApiOperation(value = "通过id查找学校总权限", notes = "返回响应对象")
    public ResponseJson findSchoolPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        SchoolPerm schoolPerm=schoolPermService.findSchoolPermById(id);
        return new ResponseJson(schoolPerm);
    }

    @PostMapping("/saveSchoolPerm")
    @ApiOperation(value = "保存学校总权限对象", notes = "返回响应对象")
    public ResponseJson saveSchoolPerm(
            @ApiParam(value = "学校总权限对象", required = true)
            @RequestBody SchoolPerm schoolPerm){
        if(schoolPerm.getParentId()==null){
            schoolPerm.setParentId("-1");
        }
        SchoolPerm s=schoolPermService.saveSchoolPerm(schoolPerm);
        return new ResponseJson(s);
    }
    @PostMapping("/updateSchoolPerm")
    @ApiOperation(value = "修改学校总权限对象", notes = "返回响应对象")
    public ResponseJson updateSchoolPerm(
            @ApiParam(value = "被修改的学校总权限对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolPerm schoolPerm){
        schoolPermService.updateSchoolPerm(schoolPerm);
        return new ResponseJson();
    }

    @PostMapping("/findSchoolPermsByCondition")
    @ApiOperation(value = "根据条件查找学校总权限", notes = "返回响应对象")
    public ResponseJson findSchoolPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolPerm schoolPerm){
        List<SchoolPerm> data=schoolPermService.findSchoolPermListByCondition(schoolPerm);
        long count=schoolPermService.findSchoolPermCountByCondition(schoolPerm);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteSchoolPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolPermService.deleteSchoolPerm(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteSchoolPermByCondition")
    @ApiOperation(value = "根据条件删除学校总权限", notes = "返回响应对象")
    public ResponseJson deleteSchoolPermByCondition(
            @ApiParam(value = "被删除的学校总权限对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody SchoolPerm schoolPerm){
        schoolPermService.deleteSchoolPermByCondition(schoolPerm);
        return new ResponseJson();
    }

    @GetMapping("/findAllTreeMenu")
    public ResponseJson findAllTreeMenu(){
        List<SchoolPerm> perms=schoolPermService.findAllSchoolTreeMenu();
        return new ResponseJson(perms);
    }
    @GetMapping("/deleteSchoolPermRecursive/{id}")
    public ResponseJson deleteSchoolPermRecursive(@PathVariable("id") String id){
        ResponseJson responseJson = schoolPermService.findSchoolAndAppPermRelation(id);
        if(!responseJson.getResult().isSuccess()){
            return responseJson;
        }
        schoolPermService.deleteSchoolPermRecursive(id);
        return new ResponseJson();
    }

    /**
     * 同步更新学校总权限到学校权限表去，包含两个动作，school_Perm表的数据更新到jw_perm表，
     * jw_perm表里在schoolPerm里没有的要删除,schoolPerm表里新增的不用管，运营决定是否给哪个学校
     * @return
     */
    @GetMapping("/syncUpdate")
    public ResponseJson syncUpdate(){
        schoolPermService.syncUpdate();
        return new ResponseJson();
    }

    @PostMapping("/ignore/batchUpdateSortNum")
    public ResponseJson batchUpdateSortNum(@RequestBody List<SchoolPerm> perms){
        schoolPermService.batchUpdateSortNum(perms);
        return new ResponseJson();
    }

}
