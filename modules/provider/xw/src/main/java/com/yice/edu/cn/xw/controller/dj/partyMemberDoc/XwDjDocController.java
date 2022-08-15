package com.yice.edu.cn.xw.controller.dj.partyMemberDoc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.xw.service.dj.partyMemberDoc.XwDjDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjDoc")
@Api(value = "/xwDjDoc",description = "校务党建公文模块")
public class XwDjDocController {

    @Autowired
    private XwDjDocService docService;

    @PostMapping("/saveDoc")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Doc saveDoc(
            @ApiParam(value = "对象", required = true)
            @RequestBody Doc doc){
        docService.saveDoc(doc);
        return doc;
    }

    @GetMapping("/findDocById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Doc findDocById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return docService.findDocById(id);
    }

    @PostMapping("/findDocListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Doc> findDocListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        return docService.findDocListByCondition(doc);
    }

    @PostMapping("/findDocCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDocCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Doc doc){
        return docService.findDocCountByCondition(doc);
    }




}
