package com.yice.edu.cn.xw.controller.dj;

import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import com.yice.edu.cn.xw.service.dj.XwDjStudyResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjStudyResource")
@Api(value = "/xwDjStudyResource",description = "模块")
public class XwDjStudyResourceController {
    @Autowired
    private XwDjStudyResourceService xwDjStudyResourceService;

    @GetMapping("/findXwDjStudyResourceById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public XwDjStudyResource findXwDjStudyResourceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjStudyResourceService.findXwDjStudyResourceById(id);
    }

    @PostMapping("/saveXwDjStudyResource")
    @ApiOperation(value = "保存", notes = "返回对象")
    public XwDjStudyResource saveXwDjStudyResource(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource){
        xwDjStudyResourceService.saveXwDjStudyResource(xwDjStudyResource);
        return xwDjStudyResource;
    }

    @PostMapping("/findXwDjStudyResourceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<XwDjStudyResource> findXwDjStudyResourceListByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjStudyResource xwDjStudyResource){
        return xwDjStudyResourceService.findXwDjStudyResourceListByCondition(xwDjStudyResource);
    }
    @PostMapping("/findXwDjStudyResourceCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findXwDjStudyResourceCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjStudyResource xwDjStudyResource){
        return xwDjStudyResourceService.findXwDjStudyResourceCountByCondition(xwDjStudyResource);
    }

    @PostMapping("/updateXwDjStudyResource")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateXwDjStudyResource(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjStudyResource xwDjStudyResource){
        xwDjStudyResourceService.updateXwDjStudyResource(xwDjStudyResource);
    }

    @GetMapping("/deleteXwDjStudyResource/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteXwDjStudyResource(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        xwDjStudyResourceService.deleteXwDjStudyResource(id);
    }
    @PostMapping("/deleteXwDjStudyResourceByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteXwDjStudyResourceByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjStudyResource xwDjStudyResource){
        xwDjStudyResourceService.deleteXwDjStudyResourceByCondition(xwDjStudyResource);
    }
    @PostMapping("/findOneXwDjStudyResourceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public XwDjStudyResource findOneXwDjStudyResourceByCondition(
            @ApiParam(value = "对象")
            @RequestBody XwDjStudyResource xwDjStudyResource){
        return xwDjStudyResourceService.findOneXwDjStudyResourceByCondition(xwDjStudyResource);
    }
}
