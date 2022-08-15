package com.yice.edu.cn.xw.controller.dj.partyMemberFile;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.xw.service.dj.partyMemberFile.XwDjAttachmentFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjAttachmentFile")
@Api(value = "/xwDjAttachmentFile",description = "党建附件表模块")
public class XwDjAttachmentFileController {
    @Autowired
    private XwDjAttachmentFileService xwDjAttachmentFileService;

    @GetMapping("/findXwDjAttachmentFileById/{id}")
    @ApiOperation(value = "通过id查找党建附件表", notes = "返回党建附件表对象")
    public XwDjAttachmentFile findXwDjAttachmentFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwDjAttachmentFileService.findXwDjAttachmentFileById(id);
    }

    @PostMapping("/saveXwDjAttachmentFile")
    @ApiOperation(value = "保存党建附件表", notes = "返回党建附件表对象")
    public XwDjAttachmentFile saveXwDjAttachmentFile(
            @ApiParam(value = "党建附件表对象", required = true)
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        xwDjAttachmentFileService.saveXwDjAttachmentFile(xwDjAttachmentFile);
        return xwDjAttachmentFile;
    }

    @PostMapping("/findXwDjAttachmentFileListByCondition")
    @ApiOperation(value = "根据条件查找党建附件表列表", notes = "返回党建附件表列表")
    public List<XwDjAttachmentFile> findXwDjAttachmentFileListByCondition(
            @ApiParam(value = "党建附件表对象")
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        return xwDjAttachmentFileService.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
    }
    @PostMapping("/findXwDjAttachmentFileCountByCondition")
    @ApiOperation(value = "根据条件查找党建附件表列表个数", notes = "返回党建附件表总个数")
    public long findXwDjAttachmentFileCountByCondition(
            @ApiParam(value = "党建附件表对象")
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        return xwDjAttachmentFileService.findXwDjAttachmentFileCountByCondition(xwDjAttachmentFile);
    }

    @PostMapping("/updateXwDjAttachmentFile")
    @ApiOperation(value = "修改党建附件表", notes = "党建附件表对象必传")
    public void updateXwDjAttachmentFile(
            @ApiParam(value = "党建附件表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        xwDjAttachmentFileService.updateXwDjAttachmentFile(xwDjAttachmentFile);
    }

    @GetMapping("/deleteXwDjAttachmentFile/{id}")
    @ApiOperation(value = "通过id删除党建附件表")
    public void deleteXwDjAttachmentFile(
            @ApiParam(value = "党建附件表对象", required = true)
            @PathVariable String id){
        xwDjAttachmentFileService.deleteXwDjAttachmentFile(id);
    }
    @PostMapping("/deleteXwDjAttachmentFileByCondition")
    @ApiOperation(value = "根据条件删除党建附件表")
    public void deleteXwDjAttachmentFileByCondition(
            @ApiParam(value = "党建附件表对象")
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        xwDjAttachmentFileService.deleteXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
    @PostMapping("/findOneXwDjAttachmentFileByCondition")
    @ApiOperation(value = "根据条件查找单个党建附件表,结果必须为单条数据", notes = "返回单个党建附件表,没有时为空")
    public XwDjAttachmentFile findOneXwDjAttachmentFileByCondition(
            @ApiParam(value = "党建附件表对象")
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        return xwDjAttachmentFileService.findOneXwDjAttachmentFileByCondition(xwDjAttachmentFile);
    }
}
