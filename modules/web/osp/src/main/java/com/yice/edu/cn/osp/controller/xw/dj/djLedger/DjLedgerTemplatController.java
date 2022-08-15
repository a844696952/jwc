package com.yice.edu.cn.osp.controller.xw.dj.djLedger;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import com.yice.edu.cn.osp.service.xw.dj.djLedger.DjLedgerTemplatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/djLedgerTemplat")
@Api(value = "/djLedgerTemplat",description = "模块")
public class DjLedgerTemplatController {
    @Autowired
    private DjLedgerTemplatService djLedgerTemplatService;

    @PostMapping("/saveDjLedgerTemplat")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=DjLedgerTemplat.class)
    public ResponseJson saveDjLedgerTemplat(
            @ApiParam(value = "对象", required = true)
            @RequestBody DjLedgerTemplat djLedgerTemplat){
       //djLedgerTemplat.setSchoolId(mySchoolId());
        DjLedgerTemplat s=djLedgerTemplatService.saveDjLedgerTemplat(djLedgerTemplat);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDjLedgerTemplatById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DjLedgerTemplat.class)
    public ResponseJson findDjLedgerTemplatById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DjLedgerTemplat djLedgerTemplat=djLedgerTemplatService.findDjLedgerTemplatById(id);
        return new ResponseJson(djLedgerTemplat);
    }

    @PostMapping("/update/updateDjLedgerTemplat")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDjLedgerTemplat(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        djLedgerTemplatService.updateDjLedgerTemplat(djLedgerTemplat);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDjLedgerTemplatById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DjLedgerTemplat.class)
    public ResponseJson lookDjLedgerTemplatById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DjLedgerTemplat djLedgerTemplat=djLedgerTemplatService.findDjLedgerTemplatById(id);
        return new ResponseJson(djLedgerTemplat);
    }

    @PostMapping("/findDjLedgerTemplatsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DjLedgerTemplat.class)
    public ResponseJson findDjLedgerTemplatsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DjLedgerTemplat djLedgerTemplat){
      // djLedgerTemplat.setSchoolId(mySchoolId());
        List<DjLedgerTemplat> data=djLedgerTemplatService.findDjLedgerTemplatListByCondition(djLedgerTemplat);
        long count=djLedgerTemplatService.findDjLedgerTemplatCountByCondition(djLedgerTemplat);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDjLedgerTemplatByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DjLedgerTemplat.class)
    public ResponseJson findOneDjLedgerTemplatByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjLedgerTemplat djLedgerTemplat){
        DjLedgerTemplat one=djLedgerTemplatService.findOneDjLedgerTemplatByCondition(djLedgerTemplat);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDjLedgerTemplat/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDjLedgerTemplat(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        djLedgerTemplatService.deleteDjLedgerTemplat(id);
        return new ResponseJson();
    }


    @PostMapping("/findDjLedgerTemplatListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DjLedgerTemplat.class)
    public ResponseJson findDjLedgerTemplatListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DjLedgerTemplat djLedgerTemplat){
       djLedgerTemplat.setSchoolId(mySchoolId());
        List<DjLedgerTemplat> data=djLedgerTemplatService.findDjLedgerTemplatListByCondition(djLedgerTemplat);
        return new ResponseJson(data);
    }



}
