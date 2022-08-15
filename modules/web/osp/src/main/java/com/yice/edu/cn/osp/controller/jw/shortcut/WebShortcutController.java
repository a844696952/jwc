package com.yice.edu.cn.osp.controller.jw.shortcut;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.shortcut.WebShortcut;
import com.yice.edu.cn.osp.service.jw.shortcut.WebShortcutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webShortcut")
@Api(value = "/webShortcut",description = "校园平台首页快捷应用模块")
public class WebShortcutController {
    @Autowired
    private WebShortcutService webShortcutService;

    @GetMapping("/ignore/findWebShortcutListByCondition")
    @ApiOperation(value = "根据条件查找校园平台首页所有可选快捷应用列表", notes = "返回响应对象,不包含总条数", response=WebShortcut.class)
    public ResponseJson findWebShortcutListByCondition(){
        WebShortcut webShortcut = new WebShortcut();
        webShortcut.setPager(Optional.ofNullable(webShortcut.getPager()).orElse(new Pager()).setPaging(false));
        List<WebShortcut> data=webShortcutService.findWebShortcutListByCondition(webShortcut);
        return new ResponseJson(data);
    }



}
