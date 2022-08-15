package com.yice.edu.cn.yed.controller.dy.rules;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.yed.service.dy.rules.MesAppletsRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/mesAppletsRule")
@Api(value = "/mesAppletsRule",description = "德育小程序制度表全网适用，每一个学校都是一样的制度模块")
public class MesAppletsRuleController {


    private final FileTypeUtil fileTypeUtil;

    private final MesAppletsRuleService mesAppletsRuleService;

    @Autowired
    public MesAppletsRuleController(FileTypeUtil fileTypeUtil,MesAppletsRuleService mesAppletsRuleService) {
        this.fileTypeUtil = fileTypeUtil;
        this.mesAppletsRuleService = mesAppletsRuleService;
    }


    @PostMapping("/saveMesAppletsRule")
    @ApiOperation(value = "保存德育小程序制度表全网适用，每一个学校都是一样的制度对象", notes = "返回保存好的德育小程序制度表全网适用，每一个学校都是一样的制度对象", response=MesAppletsRule.class)
    public ResponseJson saveMesAppletsRule(
            @ApiParam(value = "德育小程序制度表全网适用，每一个学校都是一样的制度对象", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.saveMesAppletsRule(mesAppletsRule);
    }

    @GetMapping("/findMesAppletsRuleById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找德育小程序制度表全网适用，每一个学校都是一样的制度", notes = "返回响应对象", response= MesAppletsRule.class)
    public ResponseJson findMesAppletsRuleById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsRule mesAppletsRule=mesAppletsRuleService.findMesAppletsRuleById(id);
        return new ResponseJson(mesAppletsRule);
    }

    @PostMapping("/updateMesAppletsRule")
    @ApiOperation(value = "修改德育小程序制度表全网适用，每一个学校都是一样的制度对象非空字段", notes = "返回响应对象")
    public ResponseJson updateMesAppletsRule(
            @ApiParam(value = "被修改的德育小程序制度表全网适用，每一个学校都是一样的制度对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.updateMesAppletsRule(mesAppletsRule);
    }

    @PostMapping("/updateMesAppletsRuleForAll")
    @ApiOperation(value = "修改德育小程序制度表全网适用，每一个学校都是一样的制度对象所有字段", notes = "返回响应对象")
    public ResponseJson updateMesAppletsRuleForAll(
            @ApiParam(value = "被修改的德育小程序制度表全网适用，每一个学校都是一样的制度对象", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        mesAppletsRuleService.updateMesAppletsRuleForAll(mesAppletsRule);
        return new ResponseJson();
    }


    @PostMapping("/findMesAppletsRulesByCondition")
    @ApiOperation(value = "根据条件查找德育小程序制度表全网适用，每一个学校都是一样的制度", notes = "返回响应对象", response=MesAppletsRule.class)
    public ResponseJson findMesAppletsRulesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRule mesAppletsRule){
        List<MesAppletsRule> data=mesAppletsRuleService.findMesAppletsRuleListByCondition(mesAppletsRule);
        long count=mesAppletsRuleService.findMesAppletsRuleCountByCondition(mesAppletsRule);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneMesAppletsRuleByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序制度表全网适用，每一个学校都是一样的制度,结果必须为单条数据", notes = "没有时返回空", response=MesAppletsRule.class)
    public ResponseJson findOneMesAppletsRuleByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAppletsRule mesAppletsRule){
        MesAppletsRule one=mesAppletsRuleService.findOneMesAppletsRuleByCondition(mesAppletsRule);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesAppletsRule/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsRule(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAppletsRuleService.deleteMesAppletsRule(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesAppletsRuleListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序制度表全网适用，每一个学校都是一样的制度列表", notes = "返回响应对象,不包含总条数", response=MesAppletsRule.class)
    public ResponseJson findMesAppletsRuleListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsRule mesAppletsRule){
        List<MesAppletsRule> data=mesAppletsRuleService.findMesAppletsRuleListByCondition(mesAppletsRule);
        return new ResponseJson(data);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "附件上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的附件", required = true) MultipartFile file){
        Map<String, String> map = new HashMap<>(16);
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt != 2){
            return new ResponseJson(false,"不支持的文件格式");
        }
        map.put("fileName", file.getOriginalFilename());
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_DY_FILE + suffix));
        return new ResponseJson(map);
    }


    @PostMapping("/updateMove")
    @ApiOperation(value = "修改德育小程序制度表上移动还是下移动", notes = "返回响应对象")
    public ResponseJson updateMove(
            @ApiParam(value = "修改德育小程序制度表上移动还是下移动", required = true)
            @RequestBody MesAppletsRule mesAppletsRule){
        return mesAppletsRuleService.updateMove(mesAppletsRule);
    }



    @PostMapping("/findMesAppletsRulesAndStatisticDataByCondition")
    @ApiOperation(value = "根据条件查找德育制度表并计算统计数据", notes = "返回响应对象", response=MesAppletsRule.class)
    public ResponseJson findMesAppletsRulesAndStatisticDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAppletsRule mesAppletsRule){
        List<MesAppletsRule> data=mesAppletsRuleService.findMesAppletsRulesAndStatisticDataByCondition(mesAppletsRule);
        return new ResponseJson(data);
    }




}
