package com.yice.edu.cn.ecc.controller.schoolPush;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.ecc.service.schoolPush.SchoolPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolPush")
@Api(value = "/schoolPush",description = "通过appId（JpushApp枚举类中的id）和schoolId以及pager,获取学校广播列表")
public class SchoolPushController {
    @Autowired
    private SchoolPushService schoolPushService;
    @PostMapping("/findPageSchoolPushesByAppIdAndSchoolId")
    @ApiOperation("通过appId（JpushApp枚举类中的id）和schoolId以及pager,获取学校广播列表")
    public ResponseJson findPageSchoolPushesByAppIdAndSchoolId(@RequestBody SchoolPush schoolPush){
        //appid代表的是当前应用是云班牌
        schoolPush.setAppId(1);
        return new ResponseJson(schoolPushService.findPageSchoolPushesByAppIdAndSchoolId(schoolPush));
    }
}
