package com.yice.edu.cn.osp.controller.xw.workerKq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.workerKq.CommenSettings;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.service.xw.workerKq.CommenSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/commenSettings")
@Api(value = "/commenSettings", description = "通用设置模块")
public class CommenSettingsController {
    @Autowired
    private CommenSettingsService commenSettingsService;
    @Autowired
    private SchoolFeign schoolFeign;

    @PostMapping("/saveCommenSettings")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = CommenSettings.class)
    public ResponseJson saveCommenSettings(
            @ApiParam(value = "对象", required = true)
            @RequestBody CommenSettings commenSettings) {
        CommenSettings commenSettings1 = new CommenSettings();
        commenSettings1.setSchoolId(mySchoolId());
        List<CommenSettings> list = commenSettingsService.findCommenSettingsListByCondition(commenSettings1);
        if (list.size() == 0) {
            commenSettings.setSchoolId(mySchoolId());
            CommenSettings s = commenSettingsService.saveCommenSettings(commenSettings);
            return new ResponseJson(s);
        } else if (list.size() == 1) {
            commenSettings.setId(list.get(0).getId());
            commenSettingsService.updateCommenSettings(commenSettings);
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findCommenSettingsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = CommenSettings.class)
    public ResponseJson findCommenSettingsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        CommenSettings commenSettings = commenSettingsService.findCommenSettingsById(id);
        return new ResponseJson(commenSettings);
    }

    @PostMapping("/update/updateCommenSettings")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateCommenSettings(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody CommenSettings commenSettings) {
        commenSettingsService.updateCommenSettings(commenSettings);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCommenSettingsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = CommenSettings.class)
    public ResponseJson lookCommenSettingsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        CommenSettings commenSettings = commenSettingsService.findCommenSettingsById(id);
        return new ResponseJson(commenSettings);
    }

    @PostMapping("/findCommenSettingssByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = CommenSettings.class)
    public ResponseJson findCommenSettingssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CommenSettings commenSettings) {
        commenSettings.setSchoolId(mySchoolId());
        List<CommenSettings> data = commenSettingsService.findCommenSettingsListByCondition(commenSettings);
        long count = commenSettingsService.findCommenSettingsCountByCondition(commenSettings);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneCommenSettingsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = CommenSettings.class)
    public ResponseJson findOneCommenSettingsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CommenSettings commenSettings) {
        CommenSettings one = commenSettingsService.findOneCommenSettingsByCondition(commenSettings);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteCommenSettings/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCommenSettings(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        commenSettingsService.deleteCommenSettings(id);
        return new ResponseJson();
    }


    @PostMapping("/findCommenSettingsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = CommenSettings.class)
    public ResponseJson findCommenSettingsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CommenSettings commenSettings) {
        commenSettings.setSchoolId(mySchoolId());
        List<CommenSettings> data = commenSettingsService.findCommenSettingsListByCondition(commenSettings);
        return new ResponseJson(data);
    }

    @GetMapping("/findSchoolById/{id}")
    @ApiOperation(value = "根据id", notes = "返回响应对象")
    public ResponseJson findSchoolById(
            @ApiParam(value = "id", required = true)
            @PathVariable String id) {
        School school = schoolFeign.findSchoolById(id);
        return new ResponseJson(school);
    }

}
