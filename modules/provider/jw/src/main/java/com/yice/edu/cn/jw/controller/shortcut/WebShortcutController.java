package com.yice.edu.cn.jw.controller.shortcut;

import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.jw.service.shortcut.WebShortcutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webShortcut")
@Api(value = "/webShortcut",description = "校园平台首页快捷应用模块")
public class WebShortcutController {
    @Autowired
    private WebShortcutService webShortcutService;

    @GetMapping("/findWebShortcutById/{id}")
    @ApiOperation(value = "通过id查找校园平台首页快捷应用", notes = "返回校园平台首页快捷应用对象")
    public WebShortcut findWebShortcutById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return webShortcutService.findWebShortcutById(id);
    }

    @PostMapping("/saveWebShortcut")
    @ApiOperation(value = "保存校园平台首页快捷应用", notes = "返回校园平台首页快捷应用对象")
    public WebShortcut saveWebShortcut(
            @ApiParam(value = "校园平台首页快捷应用对象", required = true)
            @RequestBody WebShortcut webShortcut){
        webShortcutService.saveWebShortcut(webShortcut);
        return webShortcut;
    }

    @PostMapping("/findWebShortcutListByCondition")
    @ApiOperation(value = "根据条件查找校园平台首页快捷应用列表", notes = "返回校园平台首页快捷应用列表")
    public List<WebShortcut> findWebShortcutListByCondition(
            @ApiParam(value = "校园平台首页快捷应用对象")
            @RequestBody WebShortcut webShortcut){
        return webShortcutService.findWebShortcutListByCondition(webShortcut);
    }
    @PostMapping("/findWebShortcutCountByCondition")
    @ApiOperation(value = "根据条件查找校园平台首页快捷应用列表个数", notes = "返回校园平台首页快捷应用总个数")
    public long findWebShortcutCountByCondition(
            @ApiParam(value = "校园平台首页快捷应用对象")
            @RequestBody WebShortcut webShortcut){
        return webShortcutService.findWebShortcutCountByCondition(webShortcut);
    }

    @PostMapping("/updateWebShortcut")
    @ApiOperation(value = "修改校园平台首页快捷应用所有非@Transient注释的字段", notes = "校园平台首页快捷应用对象必传")
    public void updateWebShortcut(
            @ApiParam(value = "校园平台首页快捷应用对象,对象属性不为空则修改", required = true)
            @RequestBody WebShortcut webShortcut){
        webShortcutService.updateWebShortcut(webShortcut);
    }
    @PostMapping("/updateWebShortcutForNotNull")
    @ApiOperation(value = "修改校园平台首页快捷应用有值的字段", notes = "校园平台首页快捷应用对象必传")
    public void updateWebShortcutForNotNull(
            @ApiParam(value = "校园平台首页快捷应用对象,对象属性不为空则修改", required = true)
            @RequestBody WebShortcut webShortcut){
        webShortcutService.updateWebShortcutForNotNull(webShortcut);
    }

    @GetMapping("/deleteWebShortcut/{id}")
    @ApiOperation(value = "通过id删除校园平台首页快捷应用")
    public void deleteWebShortcut(
            @ApiParam(value = "校园平台首页快捷应用对象", required = true)
            @PathVariable String id){
        webShortcutService.deleteWebShortcut(id);
    }
    @PostMapping("/deleteWebShortcutByCondition")
    @ApiOperation(value = "根据条件删除校园平台首页快捷应用")
    public void deleteWebShortcutByCondition(
            @ApiParam(value = "校园平台首页快捷应用对象")
            @RequestBody WebShortcut webShortcut){
        webShortcutService.deleteWebShortcutByCondition(webShortcut);
    }
    @PostMapping("/findOneWebShortcutByCondition")
    @ApiOperation(value = "根据条件查找单个校园平台首页快捷应用,结果必须为单条数据", notes = "返回单个校园平台首页快捷应用,没有时为空")
    public WebShortcut findOneWebShortcutByCondition(
            @ApiParam(value = "校园平台首页快捷应用对象")
            @RequestBody WebShortcut webShortcut){
        return webShortcutService.findOneWebShortcutByCondition(webShortcut);
    }
}
