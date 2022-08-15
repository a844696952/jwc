package com.yice.edu.cn.osp.controller.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;

import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.pcdDoc.PcdSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/pcdSend")
@Api(value = "/pcdSend",description = "区公文发送对象公文表模块")
public class PcdSendController {
    @Autowired
    private PcdSendService pcdSendService;
    @PostMapping("/savePcdSend")
    @ApiOperation(value = "保存区公文发送对象公文表对象", notes = "返回保存好的区公文发送对象公文表对象", response=PcdSend.class)
    public ResponseJson savePcdSend(
            @ApiParam(value = "区公文发送对象公文表对象", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.savePcdSend(pcdSend);
        return new ResponseJson();
    }

    @GetMapping("/findPcdSendById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找区公文发送对象公文表", notes = "返回响应对象", response=PcdSend.class)
    public ResponseJson findPcdSendById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdDocRevert p = new PcdDocRevert();
        p.setDocId(id);
        p.setEehId(mySchoolId());
        p.setCreateUserId(LoginInterceptor.myId());
        PcdDoc onePcdSendByPcdSend = pcdSendService.findOnePcdSendByPcdSend(p);
        return new ResponseJson(onePcdSendByPcdSend);
    }

    @PostMapping("/updatePcdSend")
    @ApiOperation(value = "修改区公文发送对象公文表对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdSend(
            @ApiParam(value = "被修改的区公文发送对象公文表对象,对象属性不为空则修改", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.updatePcdSend(pcdSend);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdSendForAll")
    @ApiOperation(value = "修改区公文发送对象公文表对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdSendForAll(
            @ApiParam(value = "被修改的区公文发送对象公文表对象", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.updatePcdSendForAll(pcdSend);
        return new ResponseJson();
    }


    @PostMapping("/findPcdSendsByCondition")
    @ApiOperation(value = "根据条件查找区公文发送对象公文表", notes = "返回响应对象", response=PcdSend.class)
    public ResponseJson findPcdSendsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdSend pcdSend){
        pcdSend.setEehId(mySchoolId());
        pcdSend.setCreataUserId(LoginInterceptor.myId());
        List<PcdSend> data=pcdSendService.findPcdSendByListKong(pcdSend);
        long count=pcdSendService.findPcdSendByCountKong(pcdSend);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdSendByCondition")
    @ApiOperation(value = "根据条件查找单个区公文发送对象公文表,结果必须为单条数据", notes = "没有时返回空", response=PcdSend.class)
    public ResponseJson findOnePcdSendByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdSend pcdSend){
        PcdSend one=pcdSendService.findOnePcdSendByCondition(pcdSend);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdSend/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdSend(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdSendService.deletePcdSend(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdSendListByCondition")
    @ApiOperation(value = "根据条件查找区公文发送对象公文表列表", notes = "返回响应对象,不包含总条数", response=PcdSend.class)
    public ResponseJson findPcdSendListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdSend pcdSend){
        List<PcdSend> data=pcdSendService.findPcdSendListByCondition(pcdSend);
        return new ResponseJson(data);
    }

    @PostMapping("/findPcdSendListByEehId")
    @ApiOperation(value = "根据条件查找区公文发送对象公文表列表", notes = "返回响应对象,不包含总条数", response=PcdSend.class)
    public ResponseJson findPcdSendListByEehId(){
        PcdSend pcdSend=new PcdSend();
        pcdSend.setEehId(mySchoolId());
        List<PcdSend> data=pcdSendService.findPcdSendListByCondition(pcdSend);
        return new ResponseJson(data);
    }



}
