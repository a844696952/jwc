package com.yice.edu.cn.mes.controller;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.mes.service.AppVersionControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appVersionControl")
@Api(value = "/appVersionControl", description = "模块")
public class AppVersionControlController {
    @Autowired
    private AppVersionControlService appVersionControlService;

    @PostMapping("/ignore/findVersionControlUptodate")
    @ApiOperation(value = "根据App类型 type（IOS1或安卓2）,应用端 item（教师端1或者家长端2一日常规检查终端3）,versionNumber版本号查询是否需要更新", notes = "返回单个或者null")
    public ResponseJson findVersionControlUptodate(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl
    ) {
        AppVersionControl data = appVersionControlService.findVersionControlUptodate(appVersionControl);
        return new ResponseJson(data);
    }

}
