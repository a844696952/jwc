package com.yice.edu.cn.dy.controller.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.dy.service.schoolManage.institution.MesCustomTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesCustomTitle")
@Api(value = "/mesCustomTitle", description = "自定义称号表模块")
public class MesCustomTitleController {
    @Autowired
    private MesCustomTitleService mesCustomTitleService;

    @GetMapping("/findMesCustomTitleById/{id}")
    @ApiOperation(value = "通过id查找自定义称号表", notes = "返回自定义称号表对象")
    public MesCustomTitle findMesCustomTitleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return mesCustomTitleService.findMesCustomTitleById(id);
    }

    @PostMapping("/saveMesCustomTitle")
    @ApiOperation(value = "保存自定义称号表", notes = "返回自定义称号表对象")
    public MesCustomTitle saveMesCustomTitle(
            @ApiParam(value = "自定义称号表对象", required = true)
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitleService.saveMesCustomTitle(mesCustomTitle);
        return mesCustomTitle;
    }

    @PostMapping("/findMesCustomTitleListByCondition")
    @ApiOperation(value = "根据条件查找自定义称号表列表", notes = "返回自定义称号表列表")
    public List<MesCustomTitle> findMesCustomTitleListByCondition(
            @ApiParam(value = "自定义称号表对象")
            @RequestBody MesCustomTitle mesCustomTitle) {
        return mesCustomTitleService.findMesCustomTitleListByCondition(mesCustomTitle);
    }

    @PostMapping("/findMesCustomTitleCountByCondition")
    @ApiOperation(value = "根据条件查找自定义称号表列表个数", notes = "返回自定义称号表总个数")
    public long findMesCustomTitleCountByCondition(
            @ApiParam(value = "自定义称号表对象")
            @RequestBody MesCustomTitle mesCustomTitle) {
        return mesCustomTitleService.findMesCustomTitleCountByCondition(mesCustomTitle);
    }

    @PostMapping("/updateMesCustomTitle")
    @ApiOperation(value = "修改自定义称号表", notes = "自定义称号表对象必传")
    public void updateMesCustomTitle(
            @ApiParam(value = "自定义称号表对象,对象属性不为空则修改", required = true)
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitleService.updateMesCustomTitle(mesCustomTitle);
    }

    @GetMapping("/deleteMesCustomTitle/{id}")
    @ApiOperation(value = "通过id删除自定义称号表")
    public void deleteMesCustomTitle(
            @ApiParam(value = "自定义称号表对象", required = true)
            @PathVariable String id) {
        mesCustomTitleService.deleteMesCustomTitle(id);
    }

    @PostMapping("/deleteMesCustomTitleByCondition")
    @ApiOperation(value = "根据条件删除自定义称号表")
    public void deleteMesCustomTitleByCondition(
            @ApiParam(value = "自定义称号表对象")
            @RequestBody MesCustomTitle mesCustomTitle) {
        mesCustomTitleService.deleteMesCustomTitleByCondition(mesCustomTitle);
    }

    @PostMapping("/findOneMesCustomTitleByCondition")
    @ApiOperation(value = "根据条件查找单个自定义称号表,结果必须为单条数据", notes = "返回单个自定义称号表,没有时为空")
    public MesCustomTitle findOneMesCustomTitleByCondition(
            @ApiParam(value = "自定义称号表对象")
            @RequestBody MesCustomTitle mesCustomTitle) {
        return mesCustomTitleService.findOneMesCustomTitleByCondition(mesCustomTitle);
    }
}
