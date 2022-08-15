package com.yice.edu.cn.tap.controller.locConfig;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.tap.service.school.SchoolService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/locConfig")
@Api(value = "/locConfig", description = "学校打开配置")
public class LocConfigController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/findSchoolById")
    @ApiOperation(value = "获取学校的地址和打卡范围", notes = "返回响应对象",response = School.class)
    public ResponseJson findSchoolById() {
    	School school = schoolService.findSchoolById(mySchoolId());
    	school.setId(null);
        return new ResponseJson(school);
    }

}
