package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifacePersonEnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifacePersonEnter")
@Api(value = "/ycVerifacePersonEnter",description = "模块")
public class YcVerifacePersonEnterController {
    @Autowired
    private YcVerifacePersonEnterService ycVerifacePersonEnterService;

    @GetMapping("/findYcVerifacePersonEnterById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifacePersonEnter findYcVerifacePersonEnterById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifacePersonEnterService.findYcVerifacePersonEnterById(id);
    }

    @PostMapping("/saveYcVerifacePersonEnter")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifacePersonEnter saveYcVerifacePersonEnter(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        ycVerifacePersonEnterService.saveYcVerifacePersonEnter(ycVerifacePersonEnter);
        return ycVerifacePersonEnter;
    }

    @PostMapping("/findYcVerifacePersonEnterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifacePersonEnter> findYcVerifacePersonEnterListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        return ycVerifacePersonEnterService.findYcVerifacePersonEnterListByCondition(ycVerifacePersonEnter);
    }
    @PostMapping("/findYcVerifacePersonEnterCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifacePersonEnterCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        return ycVerifacePersonEnterService.findYcVerifacePersonEnterCountByCondition(ycVerifacePersonEnter);
    }

    @PostMapping("/updateYcVerifacePersonEnter")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifacePersonEnter(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        ycVerifacePersonEnterService.updateYcVerifacePersonEnter(ycVerifacePersonEnter);
    }

    @GetMapping("/deleteYcVerifacePersonEnter/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifacePersonEnter(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifacePersonEnterService.deleteYcVerifacePersonEnter(id);
    }
    @PostMapping("/deleteYcVerifacePersonEnterByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifacePersonEnterByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        ycVerifacePersonEnterService.deleteYcVerifacePersonEnterByCondition(ycVerifacePersonEnter);
    }
    @PostMapping("/findOneYcVerifacePersonEnterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifacePersonEnter findOneYcVerifacePersonEnterByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        return ycVerifacePersonEnterService.findOneYcVerifacePersonEnterByCondition(ycVerifacePersonEnter);
    }
    @PostMapping("/batchSaveYcVerifacePersonEnter")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public void batchSaveYcVerifacePersonEnter(
            @ApiParam(value = "对象")
            @RequestBody List<YcVerifacePersonEnter> ycVerifacePersonEnter){
         ycVerifacePersonEnterService.batchSaveYcVerifacePersonEnter(ycVerifacePersonEnter);
    }
    /*--------------------------------------------------------------------------------------------------------------*/
    @PostMapping("/findYcVerifacePersonEnterByPersonIdList")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public  List<YcVerifacePersonEnter> findYcVerifacePersonEnterByPersonIdList( @ApiParam(value = "对象")
                                                                                     @RequestBody YcVerifacePersonEnter ycVerifacePersonEnter){
        return ycVerifacePersonEnterService.findYcVerifacePersonEnterByPersonIdList(ycVerifacePersonEnter);
    }
}
