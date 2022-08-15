package com.yice.edu.cn.tap.controller.doc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.doc.DocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doc")
@Api(value = "/docFeign",description = "(收文管理)我发送的模块")
public class DocController {
    @Autowired
    private DocService docService;

    @PostMapping("/saveDoc")
    @ApiOperation(value = "保存对象,添加收文记录", notes = "返回响应对象")
    public ResponseJson saveDoc(
            @ApiParam(value = "对象", required = true)
            @RequestBody Doc doc){
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
    @ApiOperation(value = "根据条件查找,收文管理——我发送的列表（条件：公文类型(docNumberType字段1为公文 2为教育督导 3为全部  审批状态(documentType字段 1为未审核，2为已审核 3为全部)  时间段（SearchTimeZone[]字段）)）", notes = "返回响应对象")
    public ResponseJson findDocsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Doc doc){
        if(doc.getDocNumberType()!=null&&doc.getDocNumberType()==3){
            doc.setDocNumberType(null);
        }
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        doc.setUserId(LoginInterceptor.myId());
        if(doc.getDocumentType()!=null&&doc.getDocumentType()==3){
            doc.setDocumentType(null);
        }
        List<Doc> data=docService.findDocListByCondition(doc);
        long count=docService.findDocCountByCondition(doc);
        return new ResponseJson(data,count);
    }




    @PostMapping("/ignore/uploadFile")
    @ApiOperation(value = "上传文件")
    public ResponseJson uploadHandout(MultipartFile file){
        String path ;
        try{
            path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_DOC);
        }catch (Exception e){
            return new ResponseJson(false,500,"文件格式错误");
        }
        return new ResponseJson(path);
    }


}
