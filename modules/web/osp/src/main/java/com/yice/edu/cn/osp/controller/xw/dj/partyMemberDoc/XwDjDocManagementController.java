package com.yice.edu.cn.osp.controller.xw.dj.partyMemberDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberDoc.XwDjDocManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwDjDocManagement")
@Api(value = "/xwDjDocManagement",description = "（收文管理）我接收的")
public class XwDjDocManagementController {

    @Autowired
    private XwDjDocManagementService docManagementService;

    @PostMapping("/findDocManagementsByCondition")
    @ApiOperation(value = "通过条件查询我接收的列表（条件：公文类型(docNumberType字段1为公文 2为教育督导 3为党建公文  当前状态是否查看 docManagement[0].type字段 1为未查看  2为已查看 3为全部 时间段（SearchTimeZone[]字段）)",notes = "无返回")
    public ResponseJson findDocManagementListByCondition(
            @ApiParam(value = "通过条件查询我接收的列表")
            @RequestBody Doc doc
    ){
        if(doc.getType()!=null&&doc.getType()==3){
            doc.setType(null);
        }
        doc.setUserId(LoginInterceptor.myId());
        List<Doc> docs =  docManagementService.findDocListByCondition(doc);
        long count = docManagementService.findDocManagementCountByCondition(doc);
        return new ResponseJson(docs,count);
    }

    @GetMapping("/look/fingOneDocUpdateManagement/{docId}")
    @ApiOperation(value = "通过收文id返回查找对象，修改已读未读数量,修改docManagement类的type字段值")
    public ResponseJson fingOneDocUpdateManagement(
            @ApiParam(value = "返回收文对象",required = true)
            @PathVariable String docId
    ){
        Doc doc = docManagementService.fingOneDocUpdateManagement(docId,LoginInterceptor.myId());
        return  new ResponseJson(doc);
    }

    //通过type类型和收文Id(docId)查询收文管理发送人阅读名单
    @PostMapping("/ignore/findDocManagementReadList")
    @ApiOperation(value = "通过type类型和收文Id(docId)查询收文管理发送人阅读名单",notes = "返回对应名单")
    public ResponseJson findDocManagementReadList(
            @ApiParam(value = "按type类型与收文Id查询阅读名单")
            @RequestBody DocManagement docManagement
    ){
        List<DocManagement> docManagementList = docManagementService.findDocManagementReadList(docManagement);
        return new ResponseJson(docManagementList);
    }

}
