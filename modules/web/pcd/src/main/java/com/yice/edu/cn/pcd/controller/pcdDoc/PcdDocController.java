package com.yice.edu.cn.pcd.controller.pcdDoc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.FileObj;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.pcdDoc.PcdDocService;
import com.yice.edu.cn.pcd.service.pcdDoc.PcdSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pcdDoc")
@Api(value = "/pcdDoc",description = "区级公文模块")
public class PcdDocController {
    @Autowired
    private PcdDocService pcdDocService;
    @Autowired
    private PcdSendService pcdSendService;
    @PostMapping("/savePcdDoc")
    @ApiOperation(value = "保存区级公文对象", notes = "返回保存好的区级公文对象", response=PcdDoc.class)
    public ResponseJson savePcdDoc(
            @ApiParam(value = "区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDoc.setEehId(LoginInterceptor.currentEehId());
        pcdDoc.setEehName(LoginInterceptor.currentAccount().getEehName());
        pcdDoc.setTransmitId(LoginInterceptor.currentAccount().getId());
        pcdDocService.savePcdDocKong(pcdDoc);
        return new ResponseJson(pcdDoc);
    }

    @GetMapping("/findPcdDocById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找区级公文", notes = "返回响应对象", response=PcdDoc.class)
    public ResponseJson findPcdDocById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdDoc pcdDoc=pcdDocService.findPcdDocById(id);
        return new ResponseJson(pcdDoc);
    }

    @PostMapping("/updatePcdDoc")
    @ApiOperation(value = "修改区级公文对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdDoc(
            @ApiParam(value = "被修改的区级公文对象,对象属性不为空则修改", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.updatePcdDoc(pcdDoc);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdDocForAll")
    @ApiOperation(value = "修改区级公文对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdDocForAll(
            @ApiParam(value = "被修改的区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.updatePcdDocForAll(pcdDoc);
        return new ResponseJson();
    }


    @PostMapping("/findPcdDocsByCondition")
    @ApiOperation(value = "根据条件查找区级公文", notes = "返回响应对象", response=PcdDoc.class)
    public ResponseJson findPcdDocsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdDoc pcdDoc){
        pcdDoc.setEehId(LoginInterceptor.currentEehId());
        pcdDoc.setTransmitId(LoginInterceptor.currentAccount().getId());
        List<PcdDoc> data=pcdDocService.findListPcdDocByPcdDocKong(pcdDoc);
        long count=pcdDocService.findCountPcdDocByPcdDocKong(pcdDoc);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdDocByCondition")
    @ApiOperation(value = "根据条件查找单个区级公文,结果必须为单条数据", notes = "没有时返回空", response=PcdDoc.class)
    public ResponseJson findOnePcdDocByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdDoc pcdDoc){
        PcdDoc one=pcdDocService.findOnePcdDocByCondition(pcdDoc);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdDoc/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdDoc(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdDocService.deletePcdDoc(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdDocListByCondition")
    @ApiOperation(value = "根据条件查找区级公文列表", notes = "返回响应对象,不包含总条数", response=PcdDoc.class)
    public ResponseJson findPcdDocListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdDoc pcdDoc){
        List<PcdDoc> data=pcdDocService.findPcdDocListByCondition(pcdDoc);
        return new ResponseJson(data);
    }

    @PostMapping("/upload")
    public FileObj upload(MultipartFile file){
        FileObj fileObj = new FileObj();
        try {
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadPcdDocFile(file,Constant.Upload.UPLOAD_PCD_DOC,50*1024*1024));
        }catch (Exception e){
            fileObj.setSuccess(false);
            fileObj.setPath("文件格式错误");
        }
        return fileObj;
    }


    @PostMapping("/saveForwardDoc")
    @ApiOperation(value = "保存区级公文", notes = "返回区级公文对象")
    public ResponseJson saveForwardDoc(
            @ApiParam(value = "区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDoc.setInformName(LoginInterceptor.currentAccount().getName());
        pcdDoc.setTransmitId(LoginInterceptor.currentAccount().getId());
        pcdDoc.setEehId(LoginInterceptor.currentEehId());
        pcdDoc.setTransmitName(LoginInterceptor.currentAccount().getEehName());
        pcdDocService.saveForwardDoc(pcdDoc);
        return new ResponseJson();
    }

    @PostMapping("/findDocSendByListAndCount")
    @ApiOperation(value = "查询公文发送对象",notes = "返回发送对象列表与发送数量")
    public ResponseJson findDocSendByListAndCount(
            @RequestBody PcdSend pcdSend
            ){
        Pager pager = pcdSend.getPager();
        pager.setLike("title");
        List<PcdSend> p = pcdSendService.findPcdSendListByCondition(pcdSend);
        long l = pcdSendService.findPcdSendCountByCondition(pcdSend);
        return new ResponseJson(p,l);
    }
}
