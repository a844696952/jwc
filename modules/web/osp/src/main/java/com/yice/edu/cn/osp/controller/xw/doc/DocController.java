package com.yice.edu.cn.osp.controller.xw.doc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.doc.DocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doc")
@Api(value = "/doc",description = "(收文管理)我发送的模块")
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

    @GetMapping("/update/findDocById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDocById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Doc doc=docService.findDocById(id);
        return new ResponseJson(doc);
    }

    @PostMapping("/update/updateDoc")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDoc(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Doc doc){
        docService.updateDoc(doc);
        return new ResponseJson();
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
    @ApiOperation(value = "根据条件查找,收文管理——我发送的列表（条件：公文类型(docNumberType字段1为公文 2为教育督导 3为全部  审批状态(documentType字段 1为未审核，2已审核)  时间段（SearchTimeZone[]字段）)）", notes = "返回响应对象")
    public ResponseJson findDocsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Doc doc){
        if(doc.getDocNumberType()!=null&&doc.getDocNumberType()==3){
            doc.setDocNumberType(null);
        }
        if(doc.getDocumentType()!=null&&doc.getDocumentType()==3){
            doc.setDocumentType(null);
        }
        doc.setSchoolId(LoginInterceptor.mySchoolId());
        doc.setUserId(LoginInterceptor.myId());

        List<Doc> data=docService.findDocListByCondition(doc);
        long count=docService.findDocCountByCondition(doc);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDocByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDocByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Doc doc){
        Doc one=docService.findOneDocByCondition(doc);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDoc/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDoc(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        docService.deleteDoc(id);
        return new ResponseJson();
    }


    @PostMapping("/findDocListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDocListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Doc doc){
       doc.setSchoolId(LoginInterceptor.mySchoolId());
        List<Doc> data=docService.findDocListByCondition(doc);
        return new ResponseJson(data);
    }



    @PostMapping("/ignore/uploadFile")
    public FileObj uploadHandout(MultipartFile file){
        FileObj fileObj = new FileObj();
        try{
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadDocFile(file, Constant.Upload.UPLOAD_DOC));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return fileObj;
    }


}
