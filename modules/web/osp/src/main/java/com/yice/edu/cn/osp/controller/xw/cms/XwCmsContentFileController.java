package com.yice.edu.cn.osp.controller.xw.cms;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsContentFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xwCmsContentFile")
@Api(value = "/xwCmsContentFile", description = "内容附件表模块")
public class XwCmsContentFileController {
    @Autowired
    private XwCmsContentFileService xwCmsContentFileService;
    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveXwCmsContentFile")
    @ApiOperation(value = "保存内容附件表对象", notes = "返回保存好的内容附件表对象", response = XwCmsContentFile.class)
    public ResponseJson saveXwCmsContentFile(
            @ApiParam(value = "内容附件表对象", required = true)
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        XwCmsContentFile s = xwCmsContentFileService.saveXwCmsContentFile(xwCmsContentFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findXwCmsContentFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找内容附件表", notes = "返回响应对象", response = XwCmsContentFile.class)
    public ResponseJson findXwCmsContentFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwCmsContentFile xwCmsContentFile = xwCmsContentFileService.findXwCmsContentFileById(id);
        return new ResponseJson(xwCmsContentFile);
    }

    @PostMapping("/update/updateXwCmsContentFile")
    @ApiOperation(value = "修改内容附件表对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsContentFile(
            @ApiParam(value = "被修改的内容附件表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileService.updateXwCmsContentFile(xwCmsContentFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookXwCmsContentFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找内容附件表", notes = "返回响应对象", response = XwCmsContentFile.class)
    public ResponseJson lookXwCmsContentFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwCmsContentFile xwCmsContentFile = xwCmsContentFileService.findXwCmsContentFileById(id);
        return new ResponseJson(xwCmsContentFile);
    }

    @PostMapping("/findXwCmsContentFilesByCondition")
    @ApiOperation(value = "根据条件查找内容附件表", notes = "返回响应对象", response = XwCmsContentFile.class)
    public ResponseJson findXwCmsContentFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        List<XwCmsContentFile> data = xwCmsContentFileService.findXwCmsContentFileListByCondition(xwCmsContentFile);
        long count = xwCmsContentFileService.findXwCmsContentFileCountByCondition(xwCmsContentFile);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwCmsContentFileByCondition")
    @ApiOperation(value = "根据条件查找单个内容附件表,结果必须为单条数据", notes = "没有时返回空", response = XwCmsContentFile.class)
    public ResponseJson findOneXwCmsContentFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        XwCmsContentFile one = xwCmsContentFileService.findOneXwCmsContentFileByCondition(xwCmsContentFile);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwCmsContentFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsContentFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwCmsContentFileService.deleteXwCmsContentFile(id);
        return new ResponseJson();
    }

    @PostMapping("/findXwCmsContentFileListByCondition")
    @ApiOperation(value = "根据条件查找内容附件表列表", notes = "返回响应对象,不包含总条数", response = XwCmsContentFile.class)
    public ResponseJson findXwCmsContentFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        List<XwCmsContentFile> data = xwCmsContentFileService.findXwCmsContentFileListByCondition(xwCmsContentFile);
        return new ResponseJson(data);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "返回状态和资源的url")
    public ResponseJson upload(@ApiParam(value = "上传的文件", required = true) MultipartFile file) {
        Map<String, Object> map = new HashMap<>(3);
        //文件名后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        map.put("fileName", StrUtil.subBefore(originalFilename, ".", true));
        map.put("fileType", suffixInt);
        map.put("filePath", QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_CMS));
        return new ResponseJson(map);
    }

}
