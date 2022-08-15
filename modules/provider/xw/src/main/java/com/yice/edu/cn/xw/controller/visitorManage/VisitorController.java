package com.yice.edu.cn.xw.controller.visitorManage;

import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.xw.service.visitorManage.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitor")
@Api(value = "/visitor",description = "模块")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @GetMapping("/findVisitorById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Visitor findVisitorById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return visitorService.findVisitorById(id);
    }

    @PostMapping("/creatQRCode")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Visitor creatQRCode(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor){
        visitorService.creatQRCode(visitor);
        return visitor;
    }

    @PostMapping("/saveVisitor")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Visitor saveVisitor(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor){
        visitorService.saveVisitor(visitor);
        return visitor;
    }

    @PostMapping("/findVisitorListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Visitor> findVisitorListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Visitor visitor){
        return visitorService.findVisitorListByCondition(visitor);
    }
    @PostMapping("/findVisitorCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findVisitorCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Visitor visitor){
        return visitorService.findVisitorCountByCondition(visitor);
    }

    @PostMapping("/updateVisitor")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateVisitor(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Visitor visitor){
        visitorService.updateVisitor(visitor);
    }

    @GetMapping("/deleteVisitor/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteVisitor(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        visitorService.deleteVisitor(id);
    }
    @PostMapping("/deleteVisitorByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteVisitorByCondition(
            @ApiParam(value = "对象")
            @RequestBody Visitor visitor){
        visitorService.deleteVisitorByCondition(visitor);
    }
    @PostMapping("/findOneVisitorByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Visitor findOneVisitorByCondition(
            @ApiParam(value = "对象")
            @RequestBody Visitor visitor){
        return visitorService.findOneVisitorByCondition(visitor);
    }

}
