package com.yice.edu.cn.dm.controller.wb.pentrace;

import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import com.yice.edu.cn.dm.service.wb.pentrace.DmPentraceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmPentrace")
@Api(value = "/dmPentrace",description = "智能笔轨迹表模块")
public class DmPentraceController {
    @Autowired
    private DmPentraceService dmPentraceService;

    @GetMapping("/findDmPentraceById/{id}")
    @ApiOperation(value = "通过id查找智能笔轨迹表", notes = "返回智能笔轨迹表对象")
    public DmPentrace findDmPentraceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmPentraceService.findDmPentraceById(id);
    }

    @PostMapping("/saveDmPentrace")
    @ApiOperation(value = "保存智能笔轨迹表", notes = "返回智能笔轨迹表对象")
    public DmPentrace saveDmPentrace(
            @ApiParam(value = "智能笔轨迹表对象", required = true)
            @RequestBody DmPentrace dmPentrace){
        dmPentraceService.saveDmPentrace(dmPentrace);
        return dmPentrace;
    }

    @PostMapping("/findDmPentraceListByCondition")
    @ApiOperation(value = "根据条件查找智能笔轨迹表列表", notes = "返回智能笔轨迹表列表")
    public List<DmPentrace> findDmPentraceListByCondition(
            @ApiParam(value = "智能笔轨迹表对象")
            @RequestBody DmPentrace dmPentrace){
        return dmPentraceService.findDmPentraceListByCondition(dmPentrace);
    }
    @PostMapping("/findDmPentraceCountByCondition")
    @ApiOperation(value = "根据条件查找智能笔轨迹表列表个数", notes = "返回智能笔轨迹表总个数")
    public long findDmPentraceCountByCondition(
            @ApiParam(value = "智能笔轨迹表对象")
            @RequestBody DmPentrace dmPentrace){
        return dmPentraceService.findDmPentraceCountByCondition(dmPentrace);
    }

    @PostMapping("/updateDmPentrace")
    @ApiOperation(value = "修改智能笔轨迹表", notes = "智能笔轨迹表对象必传")
    public void updateDmPentrace(
            @ApiParam(value = "智能笔轨迹表对象,对象属性不为空则修改", required = true)
            @RequestBody DmPentrace dmPentrace){
        dmPentraceService.updateDmPentrace(dmPentrace);
    }

    @GetMapping("/deleteDmPentrace/{id}")
    @ApiOperation(value = "通过id删除智能笔轨迹表")
    public void deleteDmPentrace(
            @ApiParam(value = "智能笔轨迹表对象", required = true)
            @PathVariable String id){
        dmPentraceService.deleteDmPentrace(id);
    }
    @PostMapping("/deleteDmPentraceByCondition")
    @ApiOperation(value = "根据条件删除智能笔轨迹表")
    public void deleteDmPentraceByCondition(
            @ApiParam(value = "智能笔轨迹表对象")
            @RequestBody DmPentrace dmPentrace){
        dmPentraceService.deleteDmPentraceByCondition(dmPentrace);
    }
    @PostMapping("/findOneDmPentraceByCondition")
    @ApiOperation(value = "根据条件查找单个智能笔轨迹表,结果必须为单条数据", notes = "返回单个智能笔轨迹表,没有时为空")
    public DmPentrace findOneDmPentraceByCondition(
            @ApiParam(value = "智能笔轨迹表对象")
            @RequestBody DmPentrace dmPentrace){
        return dmPentraceService.findOneDmPentraceByCondition(dmPentrace);
    }
}
