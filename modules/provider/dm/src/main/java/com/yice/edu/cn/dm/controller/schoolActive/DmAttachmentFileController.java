package com.yice.edu.cn.dm.controller.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import com.yice.edu.cn.dm.service.schoolActive.DmAttachmentFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmAttachmentFile")
@Api(value = "/dmAttachmentFile",description = "电子班牌附件表模块")
public class DmAttachmentFileController {
    @Autowired
    private DmAttachmentFileService dmAttachmentFileService;

    @GetMapping("/findDmAttachmentFileById/{id}")
    @ApiOperation(value = "通过id查找电子班牌附件表", notes = "返回电子班牌附件表对象")
    public DmAttachmentFile findDmAttachmentFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmAttachmentFileService.findDmAttachmentFileById(id);
    }

    @PostMapping("/saveDmAttachmentFile")
    @ApiOperation(value = "保存电子班牌附件表", notes = "返回电子班牌附件表对象")
    public DmAttachmentFile saveDmAttachmentFile(
            @ApiParam(value = "电子班牌附件表对象", required = true)
            @RequestBody DmAttachmentFile dmAttachmentFile){
        dmAttachmentFileService.saveDmAttachmentFile(dmAttachmentFile);
        return dmAttachmentFile;
    }

    @PostMapping("/findDmAttachmentFileListByCondition")
    @ApiOperation(value = "根据条件查找电子班牌附件表列表", notes = "返回电子班牌附件表列表")
    public List<DmAttachmentFile> findDmAttachmentFileListByCondition(
            @ApiParam(value = "电子班牌附件表对象")
            @RequestBody DmAttachmentFile dmAttachmentFile){
        return dmAttachmentFileService.findDmAttachmentFileListByCondition(dmAttachmentFile);
    }
    @PostMapping("/findDmAttachmentFileCountByCondition")
    @ApiOperation(value = "根据条件查找电子班牌附件表列表个数", notes = "返回电子班牌附件表总个数")
    public long findDmAttachmentFileCountByCondition(
            @ApiParam(value = "电子班牌附件表对象")
            @RequestBody DmAttachmentFile dmAttachmentFile){
        return dmAttachmentFileService.findDmAttachmentFileCountByCondition(dmAttachmentFile);
    }

    @PostMapping("/updateDmAttachmentFile")
    @ApiOperation(value = "修改电子班牌附件表", notes = "电子班牌附件表对象必传")
    public void updateDmAttachmentFile(
            @ApiParam(value = "电子班牌附件表对象,对象属性不为空则修改", required = true)
            @RequestBody DmAttachmentFile dmAttachmentFile){
        dmAttachmentFileService.updateDmAttachmentFile(dmAttachmentFile);
    }

    @GetMapping("/deleteDmAttachmentFile/{id}")
    @ApiOperation(value = "通过id删除电子班牌附件表")
    public void deleteDmAttachmentFile(
            @ApiParam(value = "电子班牌附件表对象", required = true)
            @PathVariable String id){
        dmAttachmentFileService.deleteDmAttachmentFile(id);
    }
    @PostMapping("/deleteDmAttachmentFileByCondition")
    @ApiOperation(value = "根据条件删除电子班牌附件表")
    public void deleteDmAttachmentFileByCondition(
            @ApiParam(value = "电子班牌附件表对象")
            @RequestBody DmAttachmentFile dmAttachmentFile){
        dmAttachmentFileService.deleteDmAttachmentFileByCondition(dmAttachmentFile);
    }
    @PostMapping("/findOneDmAttachmentFileByCondition")
    @ApiOperation(value = "根据条件查找单个电子班牌附件表,结果必须为单条数据", notes = "返回单个电子班牌附件表,没有时为空")
    public DmAttachmentFile findOneDmAttachmentFileByCondition(
            @ApiParam(value = "电子班牌附件表对象")
            @RequestBody DmAttachmentFile dmAttachmentFile){
        return dmAttachmentFileService.findOneDmAttachmentFileByCondition(dmAttachmentFile);
    }
}
