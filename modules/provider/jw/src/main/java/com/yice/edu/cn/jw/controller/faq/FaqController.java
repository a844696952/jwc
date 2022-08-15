package com.yice.edu.cn.jw.controller.faq;

import com.yice.edu.cn.common.pojo.jw.faq.Faq;
import com.yice.edu.cn.jw.service.faq.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
@Api(value = "/faq",description = "模块")
public class FaqController {
    @Autowired
    private FaqService faqService;

    @GetMapping("/findFaqById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Faq findFaqById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return faqService.findFaqById(id);
    }

    @PostMapping("/saveFaq")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Faq saveFaq(
            @ApiParam(value = "对象", required = true)
            @RequestBody Faq faq){
        faqService.saveFaq(faq);
        return faq;
    }

    @PostMapping("/findFaqListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Faq> findFaqListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Faq faq){
        return faqService.findFaqListByCondition(faq);
    }
    @PostMapping("/findFaqCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findFaqCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Faq faq){
        return faqService.findFaqCountByCondition(faq);
    }

    @PostMapping("/updateFaq")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateFaq(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Faq faq){
        faqService.updateFaq(faq);
    }

    @GetMapping("/deleteFaq/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteFaq(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        faqService.deleteFaq(id);
    }
    @PostMapping("/deleteFaqByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteFaqByCondition(
            @ApiParam(value = "对象")
            @RequestBody Faq faq){
        faqService.deleteFaqByCondition(faq);
    }
    @PostMapping("/findOneFaqByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Faq findOneFaqByCondition(
            @ApiParam(value = "对象")
            @RequestBody Faq faq){
        return faqService.findOneFaqByCondition(faq);
    }
}
