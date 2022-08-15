package com.yice.edu.cn.osp.controller.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import com.yice.edu.cn.osp.service.jw.teacher.JwTeacherCmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jwTeacherCms/ignore")
@Api(value = "/jwTeacherCms", description = "cms关联账户表模块")
public class JwTeacherCmsController {
    @Autowired
    private JwTeacherCmsService jwTeacherCmsService;

    @PostMapping("/saveJwTeacherCms")
    @ApiOperation(value = "保存cms关联账户表对象", notes = "返回保存好的cms关联账户表对象", response = JwTeacherCms.class)
    public ResponseJson saveJwTeacherCms(
            @ApiParam(value = "cms关联账户表对象", required = true)
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCms.setSchoolId(mySchoolId());
        jwTeacherCms.setTeacherId(myId());
        JwTeacherCms s = jwTeacherCmsService.saveJwTeacherCms(jwTeacherCms);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findJwTeacherCmsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找cms关联账户表", notes = "返回响应对象", response = JwTeacherCms.class)
    public ResponseJson findJwTeacherCmsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        JwTeacherCms jwTeacherCms = jwTeacherCmsService.findJwTeacherCmsById(id);
        return new ResponseJson(jwTeacherCms);
    }

    @PostMapping("/update/updateJwTeacherCms")
    @ApiOperation(value = "修改cms关联账户表对象", notes = "返回响应对象")
    public ResponseJson updateJwTeacherCms(
            @ApiParam(value = "被修改的cms关联账户表对象,对象属性不为空则修改", required = true)
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCmsService.updateJwTeacherCms(jwTeacherCms);
        return new ResponseJson();
    }

    @GetMapping("/look/lookJwTeacherCmsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找cms关联账户表", notes = "返回响应对象", response = JwTeacherCms.class)
    public ResponseJson lookJwTeacherCmsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        JwTeacherCms jwTeacherCms = jwTeacherCmsService.findJwTeacherCmsById(id);
        return new ResponseJson(jwTeacherCms);
    }

    @PostMapping("/findJwTeacherCmssByCondition")
    @ApiOperation(value = "根据条件查找cms关联账户表", notes = "返回响应对象", response = JwTeacherCms.class)
    public ResponseJson findJwTeacherCmssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCms.setSchoolId(mySchoolId());
        List<JwTeacherCms> data = jwTeacherCmsService.findJwTeacherCmsListByCondition(jwTeacherCms);
        long count = jwTeacherCmsService.findJwTeacherCmsCountByCondition(jwTeacherCms);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneJwTeacherCmsByCondition")
    @ApiOperation(value = "根据条件查找单个cms关联账户表,结果必须为单条数据", notes = "没有时返回空", response = JwTeacherCms.class)
    public ResponseJson findOneJwTeacherCmsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCms.setTeacherId(myId());
        JwTeacherCms one = jwTeacherCmsService.findOneJwTeacherCmsByCondition(jwTeacherCms);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteJwTeacherCms/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJwTeacherCms(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        jwTeacherCmsService.deleteJwTeacherCms(id);
        return new ResponseJson();
    }


    @PostMapping("/findJwTeacherCmsListByCondition")
    @ApiOperation(value = "根据条件查找cms关联账户表列表", notes = "返回响应对象,不包含总条数", response = JwTeacherCms.class)
    public ResponseJson findJwTeacherCmsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCms.setSchoolId(mySchoolId());
        List<JwTeacherCms> data = jwTeacherCmsService.findJwTeacherCmsListByCondition(jwTeacherCms);
        return new ResponseJson(data);
    }


}
