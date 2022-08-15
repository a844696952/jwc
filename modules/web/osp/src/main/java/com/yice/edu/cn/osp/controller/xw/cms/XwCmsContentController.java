package com.yice.edu.cn.osp.controller.xw.cms;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwCmsContent")
@Api(value = "/xwCmsContent",description = "内容信息表模块")
public class XwCmsContentController {
    @Autowired
    private XwCmsContentService xwCmsContentService;

    @PostMapping("/saveXwCmsContent")
    @ApiOperation(value = "保存内容信息表对象", notes = "返回保存好的内容信息表对象", response= XwCmsContent.class)
    public ResponseJson saveXwCmsContent(
            @ApiParam(value = "内容信息表对象", required = true)
            @RequestBody XwCmsContent xwCmsContent) {
        if (xwCmsContent != null && StrUtil.isNotBlank(xwCmsContent.getUrl()) && xwCmsContent.getUrl().length() > 1000) {
            return new ResponseJson(false, "链接超出长度限制，请重新输入");
        }
        if (xwCmsContent != null && StrUtil.isNotBlank(xwCmsContent.getTitle()) && xwCmsContent.getTitle().length() > 200) {
            return new ResponseJson(false, "标题最多可输入200个汉字");
        }
        xwCmsContent.setSchoolId(mySchoolId());
        xwCmsContentService.saveXwCmsContent(xwCmsContent);
        return new ResponseJson();
    }

    @GetMapping("/update/findXwCmsContentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找内容信息表", notes = "返回响应对象", response=XwCmsContent.class)
    public ResponseJson findXwCmsContentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsContent xwCmsContent = xwCmsContentService.findXwCmsContentById(id);
        return new ResponseJson(xwCmsContent);
    }

    @PostMapping("/update/updateXwCmsContent")
    @ApiOperation(value = "修改内容信息表对象", notes = "返回响应对象")
    public ResponseJson updateXwCmsContent(
            @ApiParam(value = "被修改的内容信息表对象,对象属性不为空则修改", required = true)
            @RequestBody XwCmsContent xwCmsContent) {
        if (xwCmsContent != null && StrUtil.isNotBlank(xwCmsContent.getTitle()) && xwCmsContent.getTitle().length() > 200) {
            return new ResponseJson(false, "标题最多可输入200个汉字");
        }
        if (xwCmsContent != null && StrUtil.isNotBlank(xwCmsContent.getUrl()) && xwCmsContent.getUrl().length() > 1000) {
            return new ResponseJson(false, "链接超出长度限制，请重新输入");
        }
        xwCmsContent.setSchoolId(mySchoolId());
        xwCmsContentService.updateXwCmsContent(xwCmsContent);
        return new ResponseJson();
    }

    @PostMapping("/look/lookXwCmsContentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找内容信息表", notes = "返回响应对象", response=XwCmsContent.class)
    public ResponseJson lookXwCmsContentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwCmsContent xwCmsContent = xwCmsContentService.findXwCmsContentById(id);
        return new ResponseJson(xwCmsContent);
    }

    @PostMapping("/findXwCmsContentsByCondition")
    @ApiOperation(value = "根据条件查找内容信息表", notes = "返回响应对象", response=XwCmsContent.class)
    public ResponseJson findXwCmsContentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContent xwCmsContent){
        List<XwCmsContent> data = xwCmsContentService.findXwCmsContentListByCondition(xwCmsContent);
        long count = xwCmsContentService.findXwCmsContentCountByCondition(xwCmsContent);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneXwCmsContentByCondition")
    @ApiOperation(value = "根据条件查找单个内容信息表,结果必须为单条数据", notes = "没有时返回空", response=XwCmsContent.class)
    public ResponseJson findOneXwCmsContentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwCmsContent xwCmsContent){
        XwCmsContent one = xwCmsContentService.findOneXwCmsContentByCondition(xwCmsContent);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwCmsContent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwCmsContent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        xwCmsContentService.deleteXwCmsContent(id);
        return new ResponseJson();
    }

    @PostMapping("/findXwCmsContentListByCondition")
    @ApiOperation(value = "根据条件查找内容信息表列表", notes = "返回响应对象,不包含总条数", response=XwCmsContent.class)
    public ResponseJson findXwCmsContentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContent xwCmsContent){
        List<XwCmsContent> data = xwCmsContentService.findXwCmsContentListByCondition(xwCmsContent);
        return new ResponseJson(data);
    }

    @PostMapping("/saveXwCmsContentForLayout")
    @ApiOperation(value = "保存内容通栏", notes = "返回内容=对象")
    public ResponseJson saveXwCmsContentForLayout(
            @ApiParam(value = "内容信息表对象", required = true)
            @RequestBody XwCmsContent xwCmsContent){
        xwCmsContent.setSchoolId(mySchoolId());
        if(xwCmsContentService.saveXwCmsContentForLayout(xwCmsContent)){
            return new ResponseJson(true,"操作成功");
        }else {
            return new ResponseJson(false, "操作失败");
        }

    }
}
