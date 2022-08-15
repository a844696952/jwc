package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.factory.YcVerifaceFactory;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceFactoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceFactory")
@Api(value = "/ycVerifaceFactory",description = "人脸识别设备厂家")
public class YcVerifaceFactoryController {
    @Autowired
    private YcVerifaceFactoryService ycVerifaceFactoryService;

    @GetMapping("/findYcVerifaceFactoryById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceFactory findYcVerifaceFactoryById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceFactoryService.findYcVerifaceFactoryById(id);
    }

    @PostMapping("/saveYcVerifaceFactory")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceFactory saveYcVerifaceFactory(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        ycVerifaceFactoryService.saveYcVerifaceFactory(ycVerifaceFactory);
        return ycVerifaceFactory;
    }

    @PostMapping("/findYcVerifaceFactoryListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceFactory> findYcVerifaceFactoryListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        return ycVerifaceFactoryService.findYcVerifaceFactoryListByCondition(ycVerifaceFactory);
    }
    @PostMapping("/findYcVerifaceFactoryCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceFactoryCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        return ycVerifaceFactoryService.findYcVerifaceFactoryCountByCondition(ycVerifaceFactory);
    }

    @PostMapping("/updateYcVerifaceFactory")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceFactory(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        ycVerifaceFactoryService.updateYcVerifaceFactory(ycVerifaceFactory);
    }

    @GetMapping("/deleteYcVerifaceFactory/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceFactory(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceFactoryService.deleteYcVerifaceFactory(id);
    }
    @PostMapping("/deleteYcVerifaceFactoryByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceFactoryByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        ycVerifaceFactoryService.deleteYcVerifaceFactoryByCondition(ycVerifaceFactory);
    }
    @PostMapping("/findOneYcVerifaceFactoryByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceFactory findOneYcVerifaceFactoryByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceFactory ycVerifaceFactory){
        return ycVerifaceFactoryService.findOneYcVerifaceFactoryByCondition(ycVerifaceFactory);
    }
}
