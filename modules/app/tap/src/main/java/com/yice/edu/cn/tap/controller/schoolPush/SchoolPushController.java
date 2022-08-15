package com.yice.edu.cn.tap.controller.schoolPush;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.tap.service.schoolPush.SchoolPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/schoolPush")
@Api(value = "/schoolPush",description = "学校推送模块")
public class SchoolPushController {
    @Autowired
    private SchoolPushService schoolPushService;


    @GetMapping("/look/lookSchoolPushById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校推送", notes = "返回响应对象")
    public ResponseJson lookSchoolPushById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolPush schoolPush=schoolPushService.findSchoolPushById(id);
        return new ResponseJson(schoolPush);
    }

   @PostMapping("/findSchoolPushsByCondition")
    @ApiOperation(value = "根据页码查找学校通知", notes = "返回响应对象")
    public ResponseJson findPageSchoolPushesByAppIdAndSchoolId(
           @ApiParam(value = "pager必传", required = true)
           @Validated
           @RequestBody SchoolPush schoolPush){
        schoolPush.setSchoolId(mySchoolId());
        schoolPush.setAppId(JpushApp.TAP.getId());
        List<SchoolPush> data = schoolPushService.findSchoolPushsByCondition(schoolPush);
        return  new ResponseJson(data);

   }







}
