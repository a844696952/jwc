package com.yice.edu.cn.xw.controller.psychologConsult;

import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import com.yice.edu.cn.xw.service.psychologConsult.XwPshcholgConsultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/xwPshcholgConsult")
@Api(value = "/xwPshcholgConsult", description = "模块")
public class XwPshcholgConsultController {
    @Autowired
    private XwPshcholgConsultService xwPshcholgConsultService;

    @GetMapping("/findXwPshcholgConsultById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwPshcholgConsult findXwPshcholgConsultById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return xwPshcholgConsultService.findXwPshcholgConsultById(id);
    }

    @PostMapping("/saveXwPshcholgConsult")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwPshcholgConsult saveXwPshcholgConsult(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultService.saveXwPshcholgConsult(xwPshcholgConsult);
        return xwPshcholgConsult;
    }

    @PostMapping("/findXwPshcholgConsultListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwPshcholgConsult> findXwPshcholgConsultListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultService.findXwPshcholgConsultListByCondition(xwPshcholgConsult);
    }

    @PostMapping("/findXwPshcholgConsultCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwPshcholgConsultCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultService.findXwPshcholgConsultCountByCondition(xwPshcholgConsult);
    }

    @PostMapping("/updateXwPshcholgConsult")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwPshcholgConsult(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultService.updateXwPshcholgConsult(xwPshcholgConsult);
    }

    @GetMapping("/deleteXwPshcholgConsult/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwPshcholgConsult(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        xwPshcholgConsultService.deleteXwPshcholgConsult(id);
    }

    @PostMapping("/deleteXwPshcholgConsultByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwPshcholgConsultByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        xwPshcholgConsultService.deleteXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    @PostMapping("/findOneXwPshcholgConsultByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwPshcholgConsult findOneXwPshcholgConsultByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultService.findOneXwPshcholgConsultByCondition(xwPshcholgConsult);
    }

    @PostMapping("/findXwPshcholgConsultByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwPshcholgConsult> findXwPshcholgConsultByCondition2(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultService.findXwPshcholgConsultByCondition2(xwPshcholgConsult);
    }

    @PostMapping("/findXwPshcholgConsultCountByCondition2")
    @ApiOperation(value = "根据条件查找个数", notes = "返回个数")
    public Long findXwPshcholgConsultCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody XwPshcholgConsult xwPshcholgConsult) {
        return xwPshcholgConsultService.findXwPshcholgConsultCountByCondition2(xwPshcholgConsult);
    }
}
