package com.yice.edu.cn.dm.controller.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.dm.service.dmScreenSaver.DmScreenSaverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmScreenSaver")
@Api(value = "/dmScreenSaver",description = "模块")
public class DmScreenSaverController {
    @Autowired
    private DmScreenSaverService dmScreenSaverService;

    @GetMapping("/findDmScreenSaverById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmScreenSaver findDmScreenSaverById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmScreenSaverService.findDmScreenSaverById(id);
    }

    @PostMapping("/saveDmScreenSaver")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmScreenSaver saveDmScreenSaver(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.saveDmScreenSaver(dmScreenSaver);
        return dmScreenSaver;
    }

    @PostMapping("/findDmScreenSaverListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmScreenSaver> findDmScreenSaverListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        return dmScreenSaverService.findDmScreenSaverListByCondition(dmScreenSaver);
    }
    @PostMapping("/findDmScreenSaverCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmScreenSaverCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        return dmScreenSaverService.findDmScreenSaverCountByCondition(dmScreenSaver);
    }

    @PostMapping("/updateDmScreenSaver")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDmScreenSaver(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.updateDmScreenSaver(dmScreenSaver);
    }

    @GetMapping("/deleteDmScreenSaver/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmScreenSaver(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmScreenSaverService.deleteDmScreenSaver(id);
    }
    @PostMapping("/deleteDmScreenSaverByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmScreenSaverByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.deleteDmScreenSaverByCondition(dmScreenSaver);
    }
    @PostMapping("/findOneDmScreenSaverByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmScreenSaver findOneDmScreenSaverByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        return dmScreenSaverService.findOneDmScreenSaverByCondition(dmScreenSaver);
    }
    @PostMapping("/batchUpdateDmScreenSaver")
    @ApiOperation(value = "批量修改屏保密码")
    public void batchUpdateDmScreenSaver(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.batchUpdateDmScreenSaver(dmScreenSaver);
    }
    @PostMapping("/batchDeleteDmScreenSaver")
    @ApiOperation(value = "批量删除屏保")
    public void batchDeleteDmScreenSaver(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.batchDeleteDmScreenSaver(dmScreenSaver);
    }
    @PostMapping("/getAddAreaByDmClass")
    @ApiOperation(value = "获取班级名称和班牌的用户名")
    public List<AreaByDmClassVo> getAddAreaByDmClass(
            @ApiParam(value = "获取屏保发送的区域", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        //如果id为空，则调用新增
        if(null==dmScreenSaver.getId() || "".equals(dmScreenSaver.getId())){
            return dmScreenSaverService.getAddAreaByDmClass(dmScreenSaver);
        }else{
            return dmScreenSaverService.getUpdateAreaByDmClass(dmScreenSaver);
        }

    }

    @PostMapping("/getUserNameByClassId")
    @ApiOperation(value = "根据班级id获取到云班牌用户名")
    public String getUserNameByClassId(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        //如果id为空，则调用新增
        return dmScreenSaverService.getUserNameByClassId(dmScreenSaver);

    }
    @PostMapping("/batchUpdateDmScreenSaverStatus")
    @ApiOperation(value = "根据时间修改当前学校所有的屏保状态")
    public void batchUpdateDmScreenSaverStatus(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.batchUpdateDmScreenSaverStatus(dmScreenSaver);
    }

    @PostMapping("/getRunNingDmScreenSaver")
    @ApiOperation(value = "获取当前还在进行中的屏保")
    public DmScreenSaver getRunNingDmScreenSaver(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        return dmScreenSaverService.getRunNingDmScreenSaver(dmScreenSaver);
    }
}
