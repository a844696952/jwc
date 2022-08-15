package com.yice.edu.cn.osp.controller.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.osp.service.dy.schoolManage.institution.MesCustomTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesCustomTitle")
@Api(value = "/mesCustomTitle", description = "自定义称号表模块")
public class MesCustomTitleController {
    @Autowired
    private MesCustomTitleService mesCustomTitleService;

    @PostMapping("/saveMesCustomTitle")
    @ApiOperation(value = "保存自定义称号表对象", notes = "返回保存好的自定义称号表对象", response = MesCustomTitle.class)
    public ResponseJson saveMesCustomTitle(
            @ApiParam(value = "自定义称号表对象", required = true)
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitle.setSchoolId(mySchoolId());
        MesCustomTitle s = mesCustomTitleService.saveMesCustomTitle(mesCustomTitle);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesCustomTitleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找自定义称号表", notes = "返回响应对象", response = MesCustomTitle.class)
    public ResponseJson findMesCustomTitleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesCustomTitle mesCustomTitle = mesCustomTitleService.findMesCustomTitleById(id);
        return new ResponseJson(mesCustomTitle);
    }

    @PostMapping("/update/updateMesCustomTitle")
    @ApiOperation(value = "修改自定义称号表对象", notes = "返回响应对象")
    public ResponseJson updateMesCustomTitle(
            @ApiParam(value = "被修改的自定义称号表对象,对象属性不为空则修改", required = true)
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitleService.updateMesCustomTitle(mesCustomTitle);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesCustomTitleById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找自定义称号表", notes = "返回响应对象", response = MesCustomTitle.class)
    public ResponseJson lookMesCustomTitleById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesCustomTitle mesCustomTitle = mesCustomTitleService.findMesCustomTitleById(id);
        return new ResponseJson(mesCustomTitle);
    }

    @PostMapping("/findMesCustomTitlesByCondition")
    @ApiOperation(value = "根据条件查找自定义称号表", notes = "返回响应对象", response = MesCustomTitle.class)
    public ResponseJson findMesCustomTitlesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitle.setSchoolId(mySchoolId());
        List<MesCustomTitle> data = mesCustomTitleService.findMesCustomTitleListByCondition(mesCustomTitle);
        long count = mesCustomTitleService.findMesCustomTitleCountByCondition(mesCustomTitle);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesCustomTitleByCondition")
    @ApiOperation(value = "根据条件查找单个自定义称号表,结果必须为单条数据", notes = "没有时返回空", response = MesCustomTitle.class)
    public ResponseJson findOneMesCustomTitleByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesCustomTitle mesCustomTitle) {
        MesCustomTitle one = mesCustomTitleService.findOneMesCustomTitleByCondition(mesCustomTitle);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesCustomTitle/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesCustomTitle(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesCustomTitleService.deleteMesCustomTitle(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesCustomTitleListByCondition")
    @ApiOperation(value = "根据条件查找自定义称号表列表", notes = "返回响应对象,不包含总条数", response = MesCustomTitle.class)
    public ResponseJson findMesCustomTitleListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitle.setSchoolId(mySchoolId());
        List<MesCustomTitle> data = mesCustomTitleService.findMesCustomTitleListByCondition(mesCustomTitle);
        return new ResponseJson(data);
    }


}
