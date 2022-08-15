package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.JwTeacherCms;
import com.yice.edu.cn.jw.service.teacher.JwTeacherCmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jwTeacherCms")
@Api(value = "/jwTeacherCms", description = "cms关联账户表模块")
public class JwTeacherCmsController {
    @Autowired
    private JwTeacherCmsService jwTeacherCmsService;

    @GetMapping("/findJwTeacherCmsById/{id}")
    @ApiOperation(value = "通过id查找cms关联账户表", notes = "返回cms关联账户表对象")
    public JwTeacherCms findJwTeacherCmsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return jwTeacherCmsService.findJwTeacherCmsById(id);
    }

    @PostMapping("/saveJwTeacherCms")
    @ApiOperation(value = "保存cms关联账户表", notes = "返回cms关联账户表对象")
    public JwTeacherCms saveJwTeacherCms(
            @ApiParam(value = "cms关联账户表对象", required = true)
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCmsService.saveJwTeacherCms(jwTeacherCms);
        return jwTeacherCms;
    }

    @PostMapping("/findJwTeacherCmsListByCondition")
    @ApiOperation(value = "根据条件查找cms关联账户表列表", notes = "返回cms关联账户表列表")
    public List<JwTeacherCms> findJwTeacherCmsListByCondition(
            @ApiParam(value = "cms关联账户表对象")
            @RequestBody JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsService.findJwTeacherCmsListByCondition(jwTeacherCms);
    }

    @PostMapping("/findJwTeacherCmsCountByCondition")
    @ApiOperation(value = "根据条件查找cms关联账户表列表个数", notes = "返回cms关联账户表总个数")
    public long findJwTeacherCmsCountByCondition(
            @ApiParam(value = "cms关联账户表对象")
            @RequestBody JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsService.findJwTeacherCmsCountByCondition(jwTeacherCms);
    }

    @PostMapping("/updateJwTeacherCms")
    @ApiOperation(value = "修改cms关联账户表", notes = "cms关联账户表对象必传")
    public void updateJwTeacherCms(
            @ApiParam(value = "cms关联账户表对象,对象属性不为空则修改", required = true)
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCmsService.updateJwTeacherCms(jwTeacherCms);
    }

    @GetMapping("/deleteJwTeacherCms/{id}")
    @ApiOperation(value = "通过id删除cms关联账户表")
    public void deleteJwTeacherCms(
            @ApiParam(value = "cms关联账户表对象", required = true)
            @PathVariable String id) {
        jwTeacherCmsService.deleteJwTeacherCms(id);
    }

    @PostMapping("/deleteJwTeacherCmsByCondition")
    @ApiOperation(value = "根据条件删除cms关联账户表")
    public void deleteJwTeacherCmsByCondition(
            @ApiParam(value = "cms关联账户表对象")
            @RequestBody JwTeacherCms jwTeacherCms) {
        jwTeacherCmsService.deleteJwTeacherCmsByCondition(jwTeacherCms);
    }

    @PostMapping("/findOneJwTeacherCmsByCondition")
    @ApiOperation(value = "根据条件查找单个cms关联账户表,结果必须为单条数据", notes = "返回单个cms关联账户表,没有时为空")
    public JwTeacherCms findOneJwTeacherCmsByCondition(
            @ApiParam(value = "cms关联账户表对象")
            @RequestBody JwTeacherCms jwTeacherCms) {
        return jwTeacherCmsService.findOneJwTeacherCmsByCondition(jwTeacherCms);
    }
}
