package com.yice.edu.cn.osp.controller.xw.dj.partyMemberFile;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberFile.XwDjAttachmentFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;


import java.util.List;


@RestController
@RequestMapping("/xwDjAttachmentFile")
@Api(value = "/xwDjAttachmentFile",description = "党建附件表模块")
public class XwDjAttachmentFileController {
    @Autowired
    private XwDjAttachmentFileService xwDjAttachmentFileService;

    @PostMapping("/saveXwDjAttachmentFile")
    @ApiOperation(value = "保存党建附件表对象", notes = "返回保存好的党建附件表对象", response=XwDjAttachmentFile.class)
    public ResponseJson saveXwDjAttachmentFile(
            @ApiParam(value = "党建附件表对象", required = true)
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        XwDjAttachmentFile s=xwDjAttachmentFileService.saveXwDjAttachmentFile(xwDjAttachmentFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwDjAttachmentFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党建附件表", notes = "返回响应对象", response=XwDjAttachmentFile.class)
    public ResponseJson findXwDjAttachmentFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjAttachmentFile xwDjAttachmentFile=xwDjAttachmentFileService.findXwDjAttachmentFileById(id);
        return new ResponseJson(xwDjAttachmentFile);
    }

    @PostMapping("/update/updateXwDjAttachmentFile")
    @ApiOperation(value = "修改党建附件表对象", notes = "返回响应对象")
    public ResponseJson updateXwDjAttachmentFile(
            @ApiParam(value = "被修改的党建附件表对象,对象属性不为空则修改", required = true)
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        xwDjAttachmentFileService.updateXwDjAttachmentFile(xwDjAttachmentFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwDjAttachmentFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党建附件表", notes = "返回响应对象", response=XwDjAttachmentFile.class)
    public ResponseJson lookXwDjAttachmentFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwDjAttachmentFile xwDjAttachmentFile=xwDjAttachmentFileService.findXwDjAttachmentFileById(id);
        return new ResponseJson(xwDjAttachmentFile);
    }

    @PostMapping("/findXwDjAttachmentFilesByCondition")
    @ApiOperation(value = "根据条件查找党建附件表", notes = "返回响应对象", response=XwDjAttachmentFile.class)
    public ResponseJson findXwDjAttachmentFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        List<XwDjAttachmentFile> data=xwDjAttachmentFileService.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
        long count=xwDjAttachmentFileService.findXwDjAttachmentFileCountByCondition(xwDjAttachmentFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwDjAttachmentFileByCondition")
    @ApiOperation(value = "根据条件查找单个党建附件表,结果必须为单条数据", notes = "没有时返回空", response=XwDjAttachmentFile.class)
    public ResponseJson findOneXwDjAttachmentFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        XwDjAttachmentFile one=xwDjAttachmentFileService.findOneXwDjAttachmentFileByCondition(xwDjAttachmentFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwDjAttachmentFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwDjAttachmentFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwDjAttachmentFileService.deleteXwDjAttachmentFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwDjAttachmentFileListByCondition")
    @ApiOperation(value = "根据条件查找党建附件表列表", notes = "返回响应对象,不包含总条数", response=XwDjAttachmentFile.class)
    public ResponseJson findXwDjAttachmentFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwDjAttachmentFile xwDjAttachmentFile){
        List<XwDjAttachmentFile> data=xwDjAttachmentFileService.findXwDjAttachmentFileListByCondition(xwDjAttachmentFile);
        return new ResponseJson(data);
    }




}
