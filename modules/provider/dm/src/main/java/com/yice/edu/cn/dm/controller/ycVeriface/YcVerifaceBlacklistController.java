package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceBlacklist")
@Api(value = "/ycVerifaceBlacklist",description = "人脸识别黑名单")
public class YcVerifaceBlacklistController {
    @Autowired
    private YcVerifaceBlacklistService ycVerifaceBlacklistService;

    @GetMapping("/findYcVerifaceBlacklistById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceBlacklist findYcVerifaceBlacklistById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceBlacklistService.findYcVerifaceBlacklistById(id);
    }

    @PostMapping("/saveYcVerifaceBlacklist")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceBlacklist saveYcVerifaceBlacklist(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        ycVerifaceBlacklistService.saveYcVerifaceBlacklist(ycVerifaceBlacklist);
        return ycVerifaceBlacklist;
    }

    @PostMapping("/findYcVerifaceBlacklistListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceBlacklist> findYcVerifaceBlacklistListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        return ycVerifaceBlacklistService.findYcVerifaceBlacklistListByCondition(ycVerifaceBlacklist);
    }
    @PostMapping("/findYcVerifaceBlacklistCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceBlacklistCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        return ycVerifaceBlacklistService.findYcVerifaceBlacklistCountByCondition(ycVerifaceBlacklist);
    }

    @PostMapping("/updateYcVerifaceBlacklist")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceBlacklist(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        ycVerifaceBlacklistService.updateYcVerifaceBlacklist(ycVerifaceBlacklist);
    }

    @GetMapping("/deleteYcVerifaceBlacklist/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceBlacklist(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceBlacklistService.deleteYcVerifaceBlacklist(id);
    }
    @PostMapping("/deleteYcVerifaceBlacklistByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceBlacklistByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        ycVerifaceBlacklistService.deleteYcVerifaceBlacklistByCondition(ycVerifaceBlacklist);
    }
    @PostMapping("/findOneYcVerifaceBlacklistByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceBlacklist findOneYcVerifaceBlacklistByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceBlacklist ycVerifaceBlacklist){
        return ycVerifaceBlacklistService.findOneYcVerifaceBlacklistByCondition(ycVerifaceBlacklist);
    }
}
