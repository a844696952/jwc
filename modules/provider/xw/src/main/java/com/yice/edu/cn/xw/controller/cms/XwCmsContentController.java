package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.xw.service.cms.XwCmsContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwCmsContent")
@Api(value = "/xwCmsContent",description = "内容信息表模块")
public class XwCmsContentController {
    @Autowired
    private XwCmsContentService xwCmsContentService;

    @PostMapping("/findXwCmsContentById/{id}")
    @ApiOperation(value = "通过id查找内容信息表", notes = "返回内容信息表对象")
    public XwCmsContent findXwCmsContentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwCmsContentService.findXwCmsContentById(id);
    }

    @PostMapping("/saveXwCmsContent")
    @ApiOperation(value = "保存内容信息表", notes = "返回内容信息表对象")
    public void saveXwCmsContent(
            @ApiParam(value = "内容信息表对象", required = true)
            @RequestBody XwCmsContent xwCmsContent){
        xwCmsContentService.saveXwCmsContent(xwCmsContent);
    }

    @PostMapping("/findXwCmsContentListByCondition")
    @ApiOperation(value = "根据条件查找内容信息表列表", notes = "返回内容信息表列表")
    public List<XwCmsContent> findXwCmsContentListByCondition(
            @ApiParam(value = "内容信息表对象")
            @RequestBody XwCmsContent xwCmsContent){
        return xwCmsContentService.findXwCmsContentListByCondition(xwCmsContent);
    }
    @PostMapping("/findXwCmsContentCountByCondition")
    @ApiOperation(value = "根据条件查找内容信息表列表个数", notes = "返回内容信息表总个数")
    public long findXwCmsContentCountByCondition(
            @ApiParam(value = "内容信息表对象")
            @RequestBody XwCmsContent xwCmsContent){
        return xwCmsContentService.findXwCmsContentCountByCondition(xwCmsContent);
    }

    @PostMapping("/updateXwCmsContent")
    @ApiOperation(value = "修改内容信息表", notes = "内容信息表对象必传")
    public void updateXwCmsContent(
            @ApiParam(value = "内容信息表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsContent xwCmsContent){
        xwCmsContentService.updateXwCmsContent(xwCmsContent);
    }

    @GetMapping("/deleteXwCmsContent/{id}")
    @ApiOperation(value = "通过id删除内容信息表")
    public void deleteXwCmsContent(
            @ApiParam(value = "内容信息表对象", required = true)
            @PathVariable String id){
        xwCmsContentService.deleteXwCmsContent(id);
    }
    @PostMapping("/deleteXwCmsContentByCondition")
    @ApiOperation(value = "根据条件删除内容信息表")
    public void deleteXwCmsContentByCondition(
            @ApiParam(value = "内容信息表对象")
            @RequestBody XwCmsContent xwCmsContent){
        xwCmsContentService.deleteXwCmsContentByCondition(xwCmsContent);
    }
    @PostMapping("/findOneXwCmsContentByCondition")
    @ApiOperation(value = "根据条件查找单个内容信息表,结果必须为单条数据", notes = "返回单个内容信息表,没有时为空")
    public XwCmsContent findOneXwCmsContentByCondition(
            @ApiParam(value = "内容信息表对象")
            @RequestBody XwCmsContent xwCmsContent){
        return xwCmsContentService.findOneXwCmsContentByCondition(xwCmsContent);
    }

    @PostMapping("/saveXwCmsContentForLayout")
    @ApiOperation(value = "保存内容通栏", notes = "返回布尔值")
    public Boolean saveXwCmsContentForLayout(
            @ApiParam(value = "内容信息表对象", required = true)
            @RequestBody XwCmsContent xwCmsContent){
        return xwCmsContentService.saveXwCmsContentForLayout(xwCmsContent);
    }
}
