package com.yice.edu.cn.ewb.controller.nb;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.ewb.service.nb.NBCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/nbCourse")
@Api(value = "/nbCourse",description = "NB对接模块")
public class NBCourseController {

    @Autowired
    private NBCourseService nbCourseService;

    @GetMapping("/getNbCourseUrl/{type}")
    @ApiOperation(value = "获取NB连接", notes = "返回连接URL")
    public ResponseJson getNbCourseUrl(
            @ApiParam(value = "课程类型", required = true)
            @PathVariable("type")Integer type){

        return nbCourseService.getNbUrlByCourse(type, currentTeacher().getName(), myId());
    }



}
