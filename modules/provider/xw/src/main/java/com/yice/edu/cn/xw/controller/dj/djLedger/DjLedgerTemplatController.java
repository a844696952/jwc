package com.yice.edu.cn.xw.controller.dj.djLedger;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import com.yice.edu.cn.xw.service.dj.djLedger.DjLedgerTemplatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/djLedgerTemplat")
@Api(value = "/djLedgerTemplat",description = "模块")
public class DjLedgerTemplatController {
    @Autowired
    private DjLedgerTemplatService djLedgerTemplatService;

    @GetMapping("/findDjLedgerTemplatById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DjLedgerTemplat findDjLedgerTemplatById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return djLedgerTemplatService.findDjLedgerTemplatById(id);
    }

    @PostMapping("/saveDjLedgerTemplat")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DjLedgerTemplat saveDjLedgerTemplat(
            @ApiParam(value = "对象", required = true)
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        djLedgerTemplatService.saveDjLedgerTemplat(djLedgerTemplat);
        return djLedgerTemplat;
    }

    @PostMapping("/findDjLedgerTemplatListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DjLedgerTemplat> findDjLedgerTemplatListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        return djLedgerTemplatService.findDjLedgerTemplatListByCondition(djLedgerTemplat);
    }
    @PostMapping("/findDjLedgerTemplatCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDjLedgerTemplatCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        return djLedgerTemplatService.findDjLedgerTemplatCountByCondition(djLedgerTemplat);
    }

    @PostMapping("/updateDjLedgerTemplat")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDjLedgerTemplat(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        djLedgerTemplatService.updateDjLedgerTemplat(djLedgerTemplat);
    }

    @GetMapping("/deleteDjLedgerTemplat/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDjLedgerTemplat(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        djLedgerTemplatService.deleteDjLedgerTemplat(id);
    }
    @PostMapping("/deleteDjLedgerTemplatByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDjLedgerTemplatByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        djLedgerTemplatService.deleteDjLedgerTemplatByCondition(djLedgerTemplat);
    }
    @PostMapping("/findOneDjLedgerTemplatByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DjLedgerTemplat findOneDjLedgerTemplatByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        return djLedgerTemplatService.findOneDjLedgerTemplatByCondition(djLedgerTemplat);
    }

    @GetMapping("/findTemplateTree/{schoolId}")
    public List<DjLedgerTemplat> findTemplateTree(   @ApiParam(value = "对象", required = true)
                             @PathVariable String schoolId ){
        return djLedgerTemplatService.findTemplateTree(schoolId);
    }

}
