package com.yice.edu.cn.tap.controller.xw.djDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.xw.dj.doc.XwDjDocLeaderService;
import com.yice.edu.cn.tap.service.xw.dj.doc.XwDjDocManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xwDjDocManagement")
@Api(value = "/xwDjDocManagement",description = "（收文管理）我接收的")
public class XwDjDocManagementController {

    @Autowired
    private XwDjDocManagementService docManagementService;
    @Autowired
    private XwDjDocLeaderService docLeaderService;

    @PostMapping("/findDocManagementsByCondition")
    @ApiOperation(value = "通过条件查询我接收的列表（条件：公文类型(docNumberType字段1为公文 2为教育督导 3为党建公文  当前状态是否查看 docManagement[0].type字段 1为未查看  2为已查看 3为全部 时间段（SearchTimeZone[]字段）)",notes = "无返回")
    public ResponseJson findDocManagementListByCondition(
            @ApiParam(value = "通过条件查询我接收的列表")
            @RequestBody Doc doc
    ){
        if(doc.getType()!=null&&doc.getType()==3){
            doc.setType(null);
        }
        doc.setDocNumberType(3);
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

    @GetMapping("/ignore/getDocRedDotNew")
    @ApiOperation(value = "用来提示黨建收文管理是否有新的红点(最新版)", notes = "返回我批阅的和我接收的红点状态")
    public ResponseJson getDocRedDotNew() {
        Doc doc = new Doc();
        doc.setUserId(LoginInterceptor.myId());
        //黨建收文管理我批阅的是否有我批阅的
        doc.setDocumentType(1);
        doc.setDocNumberType(3);
        long count = docLeaderService.findDocCountByCondition(doc);

        boolean flag = false;
        if (count != 0) {
            flag = true;
        }

        //收文管理-我接收的是否有未读的
        doc.setType(1);
        doc.setDocumentType(null);
        long count1 = docManagementService.findDocManagementCountByCondition(doc);
        boolean b = false;
        if (count1 != 0) {
            b = true;
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("docLeader", flag);
        map.put("docManagement", b);
        return new ResponseJson(map);

    }
}
