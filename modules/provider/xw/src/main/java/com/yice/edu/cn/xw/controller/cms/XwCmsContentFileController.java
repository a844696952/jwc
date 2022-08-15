package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import com.yice.edu.cn.xw.service.cms.XwCmsContentFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsContentFile")
@Api(value = "/xwCmsContentFile", description = "内容附件表模块")
public class XwCmsContentFileController {
    @Autowired
    private XwCmsContentFileService xwCmsContentFileService;

    @GetMapping("/findXwCmsContentFileById/{id}")
    @ApiOperation(value = "通过id查找内容附件表", notes = "返回内容附件表对象")
    public XwCmsContentFile findXwCmsContentFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return xwCmsContentFileService.findXwCmsContentFileById(id);
    }

    @PostMapping("/saveXwCmsContentFile")
    @ApiOperation(value = "保存内容附件表", notes = "返回内容附件表对象")
    public XwCmsContentFile saveXwCmsContentFile(
            @ApiParam(value = "内容附件表对象", required = true)
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileService.saveXwCmsContentFile(xwCmsContentFile);
        return xwCmsContentFile;
    }

    @PostMapping("/findXwCmsContentFileListByCondition")
    @ApiOperation(value = "根据条件查找内容附件表列表", notes = "返回内容附件表列表")
    public List<XwCmsContentFile> findXwCmsContentFileListByCondition(
            @ApiParam(value = "内容附件表对象")
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileService.findXwCmsContentFileListByCondition(xwCmsContentFile);
    }

    @PostMapping("/findXwCmsContentFileCountByCondition")
    @ApiOperation(value = "根据条件查找内容附件表列表个数", notes = "返回内容附件表总个数")
    public long findXwCmsContentFileCountByCondition(
            @ApiParam(value = "内容附件表对象")
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileService.findXwCmsContentFileCountByCondition(xwCmsContentFile);
    }

    @PostMapping("/updateXwCmsContentFile")
    @ApiOperation(value = "修改内容附件表", notes = "内容附件表对象必传")
    public void updateXwCmsContentFile(
            @ApiParam(value = "内容附件表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileService.updateXwCmsContentFile(xwCmsContentFile);
    }

    @GetMapping("/deleteXwCmsContentFile/{id}")
    @ApiOperation(value = "通过id删除内容附件表")
    public void deleteXwCmsContentFile(
            @ApiParam(value = "内容附件表对象", required = true)
            @PathVariable String id) {
        xwCmsContentFileService.deleteXwCmsContentFile(id);
    }

    @PostMapping("/deleteXwCmsContentFileByCondition")
    @ApiOperation(value = "根据条件删除内容附件表")
    public void deleteXwCmsContentFileByCondition(
            @ApiParam(value = "内容附件表对象")
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        xwCmsContentFileService.deleteXwCmsContentFileByCondition(xwCmsContentFile);
    }

    @PostMapping("/findOneXwCmsContentFileByCondition")
    @ApiOperation(value = "根据条件查找单个内容附件表,结果必须为单条数据", notes = "返回单个内容附件表,没有时为空")
    public XwCmsContentFile findOneXwCmsContentFileByCondition(
            @ApiParam(value = "内容附件表对象")
            @RequestBody XwCmsContentFile xwCmsContentFile) {
        return xwCmsContentFileService.findOneXwCmsContentFileByCondition(xwCmsContentFile);
    }
}
