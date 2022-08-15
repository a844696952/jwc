package com.yice.edu.cn.osp.controller.xw.dj.partyMemberDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.dj.partyMemberDoc.XwDjDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/xwDjDoc")
@Api(value = "/xwDjDoc",description = "党建公文模块(收文管理)我发送的模块")
public class XwDjDocController {


    @Autowired
    private XwDjDocService docService;


    @PostMapping("/saveDoc")
    @ApiOperation(value = "保存对象,添加收文记录", notes = "返回响应对象")
    public ResponseJson saveDoc(
            @ApiParam(value = "对象", required = true)
            @RequestBody Doc doc){
        //判断过滤附件是否为空
        if(!Objects.isNull(doc.getFileUrl())){
            String[] strings = doc.getFileUrl().split("\\|");
            for (String string : strings) {
                if(Objects.equals("",string.trim())){
                    return new ResponseJson(false,"附件命名不能由空格组成，请重新上传！");
                }
            }
        }
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        doc.setUserId(LoginInterceptor.myId());
        Doc s=docService.saveDoc(doc);
        return new ResponseJson(s);
    }


    @GetMapping("/look/lookDocById/{id}")
    @ApiOperation(value = "去查看页面,,通过id查找对应的收文管理——我发送的收文的详细记录", notes = "返回响应对象")
    public ResponseJson lookDocById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Doc doc=docService.findDocById(id);
        return new ResponseJson(doc);
    }


    @PostMapping("/findDocsByCondition")
    @ApiOperation(value = "根据条件查找,收文管理——我发送的列表（条件：公文类型(docNumberType 只有党建公文只差3  审批状态(documentType字段 1为未审核，2已审核)  时间段（SearchTimeZone[]字段）)）", notes = "返回响应对象")
    public ResponseJson findDocsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Doc doc){
        if(doc.getDocumentType() != null && doc.getDocumentType() == 3){
            doc.setDocumentType(null);
        }
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        doc.setUserId(LoginInterceptor.myId());
        List<Doc> data=docService.findDocListByCondition(doc);
        long count=docService.findDocCountByCondition(doc);
        return new ResponseJson(data,count);
    }





}
