package com.yice.edu.cn.yed.controller.jw.shortcut;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.yed.service.baseData.schoolPerm.SchoolPermService;
import com.yice.edu.cn.yed.service.jw.shortcut.WebShortcutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/webShortcut")
@Api(value = "/webShortcut",description = "校园平台首页快捷应用模块")
public class WebShortcutController {
    @Autowired
    private WebShortcutService webShortcutService;
    @Autowired
    private SchoolPermService schoolPermService;
    @PostMapping("/saveWebShortcut")
    @ApiOperation(value = "保存校园平台首页快捷应用对象", notes = "返回保存好的校园平台首页快捷应用对象", response= WebShortcut.class)
    public ResponseJson saveWebShortcut(
            @ApiParam(value = "校园平台首页快捷应用对象", required = true)
            @RequestBody WebShortcut webShortcut){
        WebShortcut s=webShortcutService.saveWebShortcut(webShortcut);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findWebShortcutById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找校园平台首页快捷应用", notes = "返回响应对象", response=WebShortcut.class)
    public ResponseJson findWebShortcutById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        WebShortcut webShortcut=webShortcutService.findWebShortcutById(id);
        return new ResponseJson(webShortcut);
    }

    @PostMapping("/update/updateWebShortcut")
    @ApiOperation(value = "修改校园平台首页快捷应用对象所有非@Transient注释的字段", notes = "返回响应对象")
    public ResponseJson updateWebShortcut(
            @ApiParam(value = "被修改的校园平台首页快捷应用对象", required = true)
            @RequestBody WebShortcut webShortcut){
        webShortcutService.updateWebShortcut(webShortcut);
        return new ResponseJson();
    }

    @PostMapping("/update/updateWebShortcutForNotNull")
    @ApiOperation(value = "修改校园平台首页快捷应用对象非空字段", notes = "返回响应对象")
    public ResponseJson updateWebShortcutForNotNull(
            @ApiParam(value = "被修改的校园平台首页快捷应用对象,对象属性不为空则修改", required = true)
            @RequestBody WebShortcut webShortcut){
        webShortcutService.updateWebShortcutForNotNull(webShortcut);
        return new ResponseJson();
    }

    @GetMapping("/look/lookWebShortcutById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找校园平台首页快捷应用", notes = "返回响应对象", response=WebShortcut.class)
    public ResponseJson lookWebShortcutById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        WebShortcut webShortcut=webShortcutService.findWebShortcutById(id);
        return new ResponseJson(webShortcut);
    }

    @PostMapping("/findWebShortcutsByCondition")
    @ApiOperation(value = "根据条件查找校园平台首页快捷应用", notes = "返回响应对象", response=WebShortcut.class)
    public ResponseJson findWebShortcutsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WebShortcut webShortcut){
        List<WebShortcut> data=webShortcutService.findWebShortcutListByCondition(webShortcut);
        long count=webShortcutService.findWebShortcutCountByCondition(webShortcut);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneWebShortcutByCondition")
    @ApiOperation(value = "根据条件查找单个校园平台首页快捷应用,结果必须为单条数据", notes = "没有时返回空", response=WebShortcut.class)
    public ResponseJson findOneWebShortcutByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WebShortcut webShortcut){
        WebShortcut one=webShortcutService.findOneWebShortcutByCondition(webShortcut);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteWebShortcut/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWebShortcut(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        webShortcutService.deleteWebShortcut(id);
        return new ResponseJson();
    }


    @PostMapping("/findWebShortcutListByCondition")
    @ApiOperation(value = "根据条件查找校园平台首页快捷应用列表", notes = "返回响应对象,不包含总条数", response=WebShortcut.class)
    public ResponseJson findWebShortcutListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WebShortcut webShortcut){
        List<WebShortcut> data=webShortcutService.findWebShortcutListByCondition(webShortcut);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findAllSchoolTreeMenuNoButton")
    @ApiOperation(value = "获取总权限树不包含按钮",response = Perm.class)
    public ResponseJson findAllSchoolTreeMenuNoButton(){
        return new ResponseJson(schoolPermService.findAllSchoolTreeMenuNoButton());
    }

}
