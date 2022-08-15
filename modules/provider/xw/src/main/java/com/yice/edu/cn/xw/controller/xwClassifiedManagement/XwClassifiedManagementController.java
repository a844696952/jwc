package com.yice.edu.cn.xw.controller.xwClassifiedManagement;

import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.xw.service.xwClassifiedManagement.XwClassifiedManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwClassifiedManagement")
@Api(value = "/xwClassifiedManagement",description = "模块")
public class XwClassifiedManagementController {
    @Autowired
    private XwClassifiedManagementService xwClassifiedManagementService;

    @GetMapping("/findXwClassifiedManagementById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwClassifiedManagement findXwClassifiedManagementById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwClassifiedManagementService.findXwClassifiedManagementById(id);
    }

    @PostMapping("/saveXwClassifiedManagement")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwClassifiedManagement saveXwClassifiedManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        xwClassifiedManagementService.saveXwClassifiedManagement(xwClassifiedManagement);
        return xwClassifiedManagement;
    }

    @PostMapping("/findXwClassifiedManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        return xwClassifiedManagementService.findXwClassifiedManagementListByCondition(xwClassifiedManagement);
    }
    @PostMapping("/findXwClassifiedManagementCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwClassifiedManagementCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        return xwClassifiedManagementService.findXwClassifiedManagementCountByCondition(xwClassifiedManagement);
    }

    @PostMapping("/updateXwClassifiedManagement")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwClassifiedManagement(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        xwClassifiedManagementService.updateXwClassifiedManagement(xwClassifiedManagement);
    }

    @GetMapping("/deleteXwClassifiedManagement/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwClassifiedManagement(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        xwClassifiedManagementService.deleteXwClassifiedManagement(id);
    }
    @PostMapping("/deleteXwClassifiedManagementByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwClassifiedManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        xwClassifiedManagementService.deleteXwClassifiedManagementByCondition(xwClassifiedManagement);
    }
    @PostMapping("/findOneXwClassifiedManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwClassifiedManagement findOneXwClassifiedManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        return xwClassifiedManagementService.findOneXwClassifiedManagementByCondition(xwClassifiedManagement);
    }



    @PostMapping("/findXwClassifiedManagementListByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition2(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        return xwClassifiedManagementService.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
    }
    @PostMapping("/findXwClassifiedManagementCountByCondition2")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwClassifiedManagementCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody XwClassifiedManagement xwClassifiedManagement){
        return xwClassifiedManagementService.findXwClassifiedManagementCountByCondition2(xwClassifiedManagement);
    }

    @GetMapping("/findCountByIdForDelete/{id}")
    public long findCountByIdForDelete(
            @ApiParam(value = "对象")
            @RequestBody XwRegulatoryFramework xwRegulatoryFramework){
        return xwClassifiedManagementService.findCountByIdForDelete(xwRegulatoryFramework);
    }
}
