package com.yice.edu.cn.yed.controller.jw.faq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import com.yice.edu.cn.yed.service.jw.faq.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/faq")
@Api(value = "/faq",description = "faq模块")
public class FaqController {
    @Autowired
    private FaqService faqService;

    @PostMapping("/saveFaq")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveFaq(
            @ApiParam(value = "对象", required = true)
            @RequestBody Faq faq){
        Faq s=faqService.saveFaq(faq);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findFaqById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findFaqById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Faq faq=faqService.findFaqById(id);
        return new ResponseJson(faq);
    }

    @PostMapping("/update/updateFaq")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateFaq(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Faq faq){
        faqService.updateFaq(faq);
        return new ResponseJson();
    }

    @GetMapping("/look/lookFaqById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookFaqById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Faq faq=faqService.findFaqById(id);
        return new ResponseJson(faq);
    }

    @PostMapping("/findFaqsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findFaqsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Faq faq){
        List<Faq> data=faqService.findFaqListByCondition(faq);
        long count=faqService.findFaqCountByCondition(faq);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneFaqByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneFaqByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Faq faq){
        Faq one=faqService.findOneFaqByCondition(faq);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteFaq/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteFaq(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        faqService.deleteFaq(id);
        return new ResponseJson();
    }


    @PostMapping("/findFaqListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findFaqListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Faq faq){
        List<Faq> data=faqService.findFaqListByCondition(faq);
        return new ResponseJson(data);
    }



}
