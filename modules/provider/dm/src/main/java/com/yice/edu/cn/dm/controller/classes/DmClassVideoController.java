package com.yice.edu.cn.dm.controller.classes;

import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.dm.service.classes.DmClassVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassVideo")
@Api(value = "/dmClassVideo",description = "班级短视频表模块")
public class DmClassVideoController {
    @Autowired
    private DmClassVideoService dmClassVideoService;

    @GetMapping("/findDmClassVideoById/{id}")
    @ApiOperation(value = "通过id查找班级短视频表", notes = "返回班级短视频表对象")
    public DmClassVideo findDmClassVideoById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmClassVideoService.findDmClassVideoById(id);
    }

    @PostMapping("/saveDmClassVideo")
    @ApiOperation(value = "保存班级短视频表", notes = "返回班级短视频表对象")
    public DmClassVideo saveDmClassVideo(
            @ApiParam(value = "班级短视频表对象", required = true)
            @RequestBody DmClassVideo dmClassVideo){
        dmClassVideoService.saveDmClassVideo(dmClassVideo);
        return dmClassVideo;
    }

    @PostMapping("/findDmClassVideoListByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表列表", notes = "返回班级短视频表列表")
    public List<DmClassVideo> findDmClassVideoListByCondition(
            @ApiParam(value = "班级短视频表对象")
            @RequestBody DmClassVideo dmClassVideo){
        return dmClassVideoService.findDmClassVideoListByCondition(dmClassVideo);
    }
    @PostMapping("/findDmClassVideoCountByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表列表个数", notes = "返回班级短视频表总个数")
    public long findDmClassVideoCountByCondition(
            @ApiParam(value = "班级短视频表对象")
            @RequestBody DmClassVideo dmClassVideo){
        return dmClassVideoService.findDmClassVideoCountByCondition(dmClassVideo);
    }

    @PostMapping("/updateDmClassVideo")
    @ApiOperation(value = "修改班级短视频表", notes = "班级短视频表对象必传")
    public void updateDmClassVideo(
            @ApiParam(value = "班级短视频表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassVideo dmClassVideo){
        dmClassVideoService.updateDmClassVideo(dmClassVideo);
    }

    @GetMapping("/deleteDmClassVideo/{id}")
    @ApiOperation(value = "通过id删除班级短视频表")
    public void deleteDmClassVideo(
            @ApiParam(value = "班级短视频表对象", required = true)
            @PathVariable String id){
        dmClassVideoService.deleteDmClassVideo(id);
    }
    @PostMapping("/deleteDmClassVideoByCondition")
    @ApiOperation(value = "根据条件删除班级短视频表")
    public void deleteDmClassVideoByCondition(
            @ApiParam(value = "班级短视频表对象")
            @RequestBody DmClassVideo dmClassVideo){
        dmClassVideoService.deleteDmClassVideoByCondition(dmClassVideo);
    }
    @PostMapping("/findOneDmClassVideoByCondition")
    @ApiOperation(value = "根据条件查找单个班级短视频表,结果必须为单条数据", notes = "返回单个班级短视频表,没有时为空")
    public DmClassVideo findOneDmClassVideoByCondition(
            @ApiParam(value = "班级短视频表对象")
            @RequestBody DmClassVideo dmClassVideo){
        return dmClassVideoService.findOneDmClassVideoByCondition(dmClassVideo);
    }

    @PostMapping("/batchDeleteDmClassVideo")
    @ApiOperation(value = "根据条件删除批量班级相册表")
    public void batchDeleteDmClassVideo(
            @ApiParam(value = "班级相册表对象")
            @RequestBody List<String> idlist){
        dmClassVideoService.batchDeleteDmClassVideo(idlist);
    }
}
