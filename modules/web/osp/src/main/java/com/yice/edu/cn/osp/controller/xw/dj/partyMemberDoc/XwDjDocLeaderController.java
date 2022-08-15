package com.yice.edu.cn.osp.controller.xw.dj.partyMemberDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.SendObject;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberDoc.XwDjDocLeaderService;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberDoc.XwDjDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjDocLeader")
@Api(value = "/xwDjDocLeader", description = "（收文管理）我批阅的")
public class XwDjDocLeaderController {
    @Autowired
    private XwDjDocLeaderService docLeaderService;
    @Autowired
    private XwDjDocService docService;

    @PostMapping("/look/findDocAndDocLeaderList")
    @ApiOperation(value = "根据条件查找列表,返回我批阅的公文列表（条件：公文类型(3为党建公文)  审批状态(documentType字段 1为待我审核，2为已完成 3为待他人审核 4为全部)  时间段（SearchTimeZone[]字段）)", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDocAndDocLeaderList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Doc doc
    ) {
        doc.setDocNumberType(3);
        doc.setUserId(LoginInterceptor.myId());
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        List<Doc> docs = docLeaderService.findDocAndDocLeaderList(doc);
        long count = docLeaderService.findDocCountByCondition(doc);
        return new ResponseJson(docs, count);
    }

    @GetMapping("/look/lookDocLeaderById/{id}")
    @ApiOperation(value = "通过收文Id查询收文的详细记录", notes = "返回响应对象")
    public ResponseJson lookDocLeaderById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Doc doc = docService.findDocById(id);
        return new ResponseJson(doc);
    }

    @PostMapping("/update/saveUpdateDocCompletion")
    @ApiOperation(value = "审核完成时调用的接口", notes = "返回响应对象,不包含总条数")
    public ResponseJson saveUpdateDocCompletion(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Doc doc
    ) {
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        doc.setDocumentType(2);
        docLeaderService.saveUpdateDocCompletion(doc);
        return new ResponseJson();
    }

    @PostMapping("/update/saveDocManagement/{docId}")
    @ApiOperation(value = "添加收文管理发送对象", notes = "无返回")
    public ResponseJson saveDocManagement(
            @ApiParam(value = "需要传递发送对象（List）,和公文Id")
            @RequestBody List<SendObject> sendObjects,
            @PathVariable String docId
    ) {
        Doc doc = docLeaderService.saveDocManagement(sendObjects, docId);
        return new ResponseJson();
    }

    @PostMapping("/update/saveUpdateDocLeaterCompletion")
    @ApiOperation(value = "转他人审核时调用的接口", notes = "不返回数据")
    public ResponseJson saveUpdateDocLeaterCompletion(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Doc doc
    ) {
        docLeaderService.saveUpdateDocLeaterCompletion(doc);
        return new ResponseJson();
    }
}
