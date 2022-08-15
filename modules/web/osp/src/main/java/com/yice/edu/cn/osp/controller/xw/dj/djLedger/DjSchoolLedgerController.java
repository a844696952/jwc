package com.yice.edu.cn.osp.controller.xw.dj.djLedger;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjLedgerTemplat;
import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import com.yice.edu.cn.osp.service.xw.dj.djLedger.DjLedgerTemplatService;
import com.yice.edu.cn.osp.service.xw.dj.djLedger.DjSchoolLedgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/djSchoolLedger")
@Api(value = "/djSchoolLedger",description = "模块")
public class DjSchoolLedgerController {
    @Autowired
    private DjSchoolLedgerService djSchoolLedgerService;
    @Autowired
    private DjLedgerTemplatService djLedgerTemplatService;
    @PostMapping("/saveDjSchoolLedger")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=DjSchoolLedger.class)
    public ResponseJson saveDjSchoolLedger(
            @ApiParam(value = "对象", required = true)
            @RequestBody DjSchoolLedger djSchoolLedger){
       djSchoolLedger.setSchoolId(mySchoolId());
        DjSchoolLedger s=djSchoolLedgerService.saveDjSchoolLedger(djSchoolLedger);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDjSchoolLedgerById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DjSchoolLedger.class)
    public ResponseJson findDjSchoolLedgerById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DjSchoolLedger djSchoolLedger=djSchoolLedgerService.findDjSchoolLedgerById(id);
        return new ResponseJson(djSchoolLedger);
    }

    @PostMapping("/update/updateDjSchoolLedger")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDjSchoolLedger(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DjSchoolLedger djSchoolLedger){
        djSchoolLedgerService.updateDjSchoolLedger(djSchoolLedger);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDjSchoolLedgerById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DjSchoolLedger.class)
    public ResponseJson lookDjSchoolLedgerById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DjSchoolLedger djSchoolLedger=djSchoolLedgerService.findDjSchoolLedgerById(id);
        return new ResponseJson(djSchoolLedger);
    }

    @PostMapping("/findDjSchoolLedgersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DjSchoolLedger.class)
    public ResponseJson findDjSchoolLedgersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DjSchoolLedger djSchoolLedger){
       djSchoolLedger.setSchoolId(mySchoolId());
        List<DjSchoolLedger> data=djSchoolLedgerService.findDjSchoolLedgerListByCondition(djSchoolLedger);
        long count=djSchoolLedgerService.findDjSchoolLedgerCountByCondition(djSchoolLedger);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDjSchoolLedgerByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DjSchoolLedger.class)
    public ResponseJson findOneDjSchoolLedgerByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DjSchoolLedger djSchoolLedger){
        DjSchoolLedger one=djSchoolLedgerService.findOneDjSchoolLedgerByCondition(djSchoolLedger);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDjSchoolLedger/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDjSchoolLedger(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        djSchoolLedgerService.deleteDjSchoolLedger(id);
        return new ResponseJson();
    }


    @PostMapping("/findDjSchoolLedgerListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DjSchoolLedger.class)
    public ResponseJson findDjSchoolLedgerListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DjSchoolLedger djSchoolLedger){
       djSchoolLedger.setSchoolId(mySchoolId());
        List<DjSchoolLedger> data=djSchoolLedgerService.findDjSchoolLedgerListByCondition(djSchoolLedger);
        return new ResponseJson(data);
    }


    @GetMapping("/findTemplateTree")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson findTemplateTree(){
        List<DjLedgerTemplat> templateTree = djLedgerTemplatService.findTemplateTree();
        return new ResponseJson(templateTree);
    }


    @GetMapping("/exportToWordFile/{id}")
    public void exportToWordFile(@ApiParam(value = "被删除记录的id", required = true)
                                      @PathVariable String id, HttpServletResponse response , HttpServletRequest request){


        djSchoolLedgerService.doExport(id,response,request);



    }
}
