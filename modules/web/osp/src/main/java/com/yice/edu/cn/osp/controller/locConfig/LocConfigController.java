package com.yice.edu.cn.osp.controller.locConfig;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.osp.service.jw.locConfig.LocConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/locConfig")
@Api(value = "/locConfig", description = "定位设置")
public class LocConfigController {
    @Autowired
    private LocConfigService locConfigService;

    @GetMapping("/findSchoolById")
    @ApiOperation(value = "获取学校信息", notes = "返回响应对象")
    public ResponseJson findSchoolById() {
        return new ResponseJson(locConfigService.findSchoolById(mySchoolId()));
    }

    @PostMapping("/updateSchool")
    @ApiOperation(value = "更新学校信息", notes = "返回响应对象")
    public ResponseJson updateSchool(@RequestBody School school) {
    	school.setId(mySchoolId());
    	locConfigService.updateSchool(school);
    	return new ResponseJson();
    }
    
    @PostMapping("/queryClockInRange")
    @ApiOperation(value = "查询打卡范围数据字典", notes = "返回响应对象")
    public ResponseJson queryClockInRange(){
   	 return new ResponseJson(locConfigService.queryClockInRange());
   }

}
