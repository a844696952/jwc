package com.yice.edu.cn.jy.controller.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.jy.service.resourceCenter.ResourceCenterConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourceCenterCondition")
@Api(value = "/resourceCenterCondition",description = "资源中心:条件表模块")
public class ResourceCenterConditionController {
    @Autowired
    private ResourceCenterConditionService resourceCenterConditionService;

    @PostMapping("/deleteResourceCenterCondition4Like")
    @ApiOperation(value = "通过id删除资源中心:条件表")
    public ResourceCenterType deleteResourceCenterCondition4Like(
            @ApiParam(value = "资源中心:条件表对象", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        return resourceCenterConditionService.deleteResourceCenterCondition4Like(resourceCenterType);
    }

    @GetMapping("/findResourceCenterConditionById/{id}")
    @ApiOperation(value = "通过id查找资源中心:条件表", notes = "返回资源中心:条件表对象")
    public ResourceCenterCondition findResourceCenterConditionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return resourceCenterConditionService.findResourceCenterConditionById(id);
    }

    @PostMapping("/saveResourceCenterCondition")
    @ApiOperation(value = "保存资源中心:条件表", notes = "返回资源中心:条件表对象")
    public ResourceCenterCondition saveResourceCenterCondition(
            @ApiParam(value = "资源中心:条件表对象", required = true)
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        resourceCenterConditionService.saveResourceCenterCondition(resourceCenterCondition);
        return resourceCenterCondition;
    }

    @PostMapping("/findResourceCenterConditionListByCondition")
    @ApiOperation(value = "根据条件查找资源中心:条件表列表", notes = "返回资源中心:条件表列表")
    public List<ResourceCenterCondition> findResourceCenterConditionListByCondition(
            @ApiParam(value = "资源中心:条件表对象")
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        return resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
    }
    @PostMapping("/findResourceCenterConditionCountByCondition")
    @ApiOperation(value = "根据条件查找资源中心:条件表列表个数", notes = "返回资源中心:条件表总个数")
    public long findResourceCenterConditionCountByCondition(
            @ApiParam(value = "资源中心:条件表对象")
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        return resourceCenterConditionService.findResourceCenterConditionCountByCondition(resourceCenterCondition);
    }

    @PostMapping("/updateResourceCenterCondition")
    @ApiOperation(value = "修改资源中心:条件表", notes = "资源中心:条件表对象必传")
    public void updateResourceCenterCondition(
            @ApiParam(value = "资源中心:条件表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        resourceCenterConditionService.updateResourceCenterCondition(resourceCenterCondition);
    }

    @GetMapping("/deleteResourceCenterCondition/{id}")
    @ApiOperation(value = "通过id删除资源中心:条件表")
    public void deleteResourceCenterCondition(
            @ApiParam(value = "资源中心:条件表对象", required = true)
            @PathVariable String id){
        resourceCenterConditionService.deleteResourceCenterCondition(id);
    }
    @PostMapping("/deleteResourceCenterConditionByCondition")
    @ApiOperation(value = "根据条件删除资源中心:条件表")
    public void deleteResourceCenterConditionByCondition(
            @ApiParam(value = "资源中心:条件表对象")
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        resourceCenterConditionService.deleteResourceCenterConditionByCondition(resourceCenterCondition);
    }
    @PostMapping("/findOneResourceCenterConditionByCondition")
    @ApiOperation(value = "根据条件查找单个资源中心:条件表,结果必须为单条数据", notes = "返回单个资源中心:条件表,没有时为空")
    public ResourceCenterCondition findOneResourceCenterConditionByCondition(
            @ApiParam(value = "资源中心:条件表对象")
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        return resourceCenterConditionService.findOneResourceCenterConditionByCondition(resourceCenterCondition);
    }
}
