package com.yice.edu.cn.tap.controller.doc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.document.DocManagement;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.doc.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.ref.PhantomReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/docManagement")
@Api(value = "/docManagement",description = "（收文管理）我接收的")
public class DocManagementController {
    @Autowired
    private DocManagementService docManagementService;

    @Autowired
    private WritingService writingService;

    @Autowired
    private DocService docService;

    @Autowired
    private DocLeaderService docLeaderService;

    @Autowired
    private WritingLeaderService writingLeaderService;
    @Autowired
    private WritingManagementService writingManagementService;


    @PostMapping("/findDocManagementsByCondition")
    @ApiOperation(value = "通过条件查询我接收的列表（条件：公文类型(docNumberType字段1为公文 2为教育督导 3为全部  当前状态是否查看 docManagement[0].type字段 1为未查看  2为已查看 3为全部 时间段（SearchTimeZone[]字段）)",notes = "无返回")
    public ResponseJson findDocManagementListByCondition(
            @ApiParam(value = "通过条件查询我接收的列表")
            @RequestBody Doc doc
            ){
        if(doc.getDocNumberType()!=null&&doc.getDocNumberType()==3){
            doc.setDocNumberType(null);
        }

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
    @ApiOperation(value = "通过type类型和收文Id(docId)查询收文管理发送人阅读名单(新)",notes = "返回对应名单")
    public ResponseJson findDocManagementReadList(
            @ApiParam(value = "按type类型与收文Id查询阅读名单")
            @RequestBody DocManagement docManagement
    ){
        List<DocManagement> docManagementList = docManagementService.findDocManagementReadList(docManagement);
        return new ResponseJson(docManagementList);
    }



    //通过type类型和收文Id(docId)查询收文管理发送人阅读名单
    @PostMapping("/ignore/getDocManagementReadOrUnRead")
    @ApiOperation(value = "查找名单,通过type类型和收文Id(docId)查询收文管理发送人阅读名单",notes = "返回名单")
    public ResponseJson getDocManagementReadOrUnRead(
            @ApiParam(value = "按type类型与收文Id查询阅读名单")
            @RequestBody DocManagement docManagement
    ){
        List<Department> departmentList = docManagementService.getDocManagementReadOrUnRead(docManagement);
        return new ResponseJson(departmentList);
    }


    //红点，用来提示App端是否新的操作
    @GetMapping("/ignore/getHomePageRedDot")
    @ApiOperation(value = "用来提示公文首页App端是否有新的操作",notes = "有则返回true，无则返回false")
    public ResponseJson getLatestOfficialDoc(
    ){
        long[] longs = docManagementService.getHomePageRedDot();
        Boolean flag = false;
        if(longs[0]!=0||longs[1]!=0||longs[2]!=0||longs[3]!=0||longs[4]!=0){
            flag = true;
        }
        return new ResponseJson(flag);
    }


    //红点，用来提示App端是否新的操作（最新版）
    @GetMapping("/ignore/getNewHomePageRedDot")
    @ApiOperation(value = "用来提示公文首页App端是否有新的未读消息,最新版",notes = "有则返回数量，无则返回0")
    public ResponseJson getFindMyReceive(
    ){
        long count = docManagementService.findMyReceive();
        return new ResponseJson(count);
    }

    /*@GetMapping("/ingore/getDocRedDot")
    @ApiOperation(value = "用来提示收文管理是否有新的红点",notes = "返回我批阅的和我接收的红点状态")
    public ResponseJson  getDocRedDot(){
        Doc doc = new Doc();
        doc.setUserId(LoginInterceptor.myId());
        //收文管理我批阅的是否有我批阅的
        doc.setDocumentType(1);
        long count = docLeaderService.findDocCountByCondition(doc);

        boolean flag = false;
        if(count!=0){
            flag =true;
        }

        //收文管理-我接收的是否有未读的
        doc.setType(1);
        doc.setDocumentType(null);
        long count1 = docManagementService.findDocManagementCountByCondition(doc);
        boolean b = false;
        if(count1!=0){
            b = true;
        }
        Boolean[] flags = new Boolean[]{flag,b};
        return new ResponseJson(flags);

    }*/

    @GetMapping("/ingore/getDocRedDotNew")
    @ApiOperation(value = "用来提示收文管理是否有新的红点(最新版)",notes = "返回我批阅的和我接收的红点状态")
    public ResponseJson  getDocRedDotNew(){
        Doc doc = new Doc();
        doc.setUserId(LoginInterceptor.myId());
        //收文管理我批阅的是否有我批阅的
        doc.setDocumentType(1);
        long count = docLeaderService.findDocCountByCondition(doc);

        boolean flag = false;
        if(count!=0){
            flag =true;
        }

        //收文管理-我接收的是否有未读的
        doc.setType(1);
        doc.setDocumentType(null);
        long count1 = docManagementService.findDocManagementCountByCondition(doc);
        boolean b = false;
        if(count1!=0){
            b = true;
        }
        Map<String,Boolean> map = new HashMap<>();
        map.put("docLeader",flag);
        map.put("docManagement",b);
        return new ResponseJson(map);

    }

   /* @GetMapping("/ingore/getDocOrWritingType")
    @ApiOperation(value = "用来提示收文，发文页面导航红点",notes ="返回布尔数组，包含两个值")
    public ResponseJson getDocOrWritingType(){
       long[] longs =  docManagementService.getHomePageRedDot();
       Boolean flag = false;
       Boolean flag1 = false;

       if(longs[0]!=0||longs[1]!=0){
           flag = true;
       }

       if(longs[2]!=0||longs[3]!=0||longs[4]!=0){
           flag1 = true;
       }
       Boolean[] f = {flag,flag1};
       return new ResponseJson(f);

    }*/

    @GetMapping("/ingore/getDocOrWritingTypeNew")
    @ApiOperation(value = "用来提示收文，发文页面导航红点（最新版）",notes ="返回布尔数组，包含两个值")
    public ResponseJson getDocOrWritingTypeNew(){
        long[] longs =  docManagementService.getHomePageRedDot();
        Boolean flag = false;
        Boolean flag1 = false;

        if(longs[0]!=0||longs[1]!=0){
            flag = true;
        }

        if(longs[2]!=0||longs[3]!=0||longs[4]!=0){
            flag1 = true;
        }
        Map<String,Boolean> map = new HashMap<>();
        map.put("receiver",flag);
        map.put("sendGL",flag1);
        return new ResponseJson(map);

    }
}
