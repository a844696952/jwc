package com.yice.edu.cn.osp.controller.xw.doc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.doc.WritingManagementService;
import com.yice.edu.cn.osp.service.xw.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/writingManagement")
@Api(value = "/writingManagement",description = "（发文管理）我接收的")
public class WritingManagementController {
    @Autowired
    private WritingManagementService writingManagementService;

    @Autowired
    private WritingService writingService;

    @PostMapping("/saveWritingManagement")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveWritingManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody WritingManagement writingManagement){
        WritingManagement s=writingManagementService.saveWritingManagement(writingManagement);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findWritingManagementById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findWritingManagementById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        WritingManagement writingManagement=writingManagementService.findWritingManagementById(id);
        return new ResponseJson(writingManagement);
    }

    @PostMapping("/update/updateWritingManagement")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWritingManagement(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody WritingManagement writingManagement){
        writingManagementService.updateWritingManagement(writingManagement);
        return new ResponseJson();
    }

    @GetMapping("/look/lookWritingManagementById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookWritingManagementById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        WritingManagement writingManagement=writingManagementService.findWritingManagementById(id);
        return new ResponseJson(writingManagement);
    }

    @PostMapping("/findWritingManagementsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findWritingManagementsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WritingManagement writingManagement){
        List<WritingManagement> data=writingManagementService.findWritingManagementListByCondition(writingManagement);
        long count=writingManagementService.findWritingManagementCountByCondition(writingManagement);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneWritingManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneWritingManagementByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingManagement writingManagement){
        WritingManagement one=writingManagementService.findOneWritingManagementByCondition(writingManagement);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteWritingManagement/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWritingManagement(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        writingManagementService.deleteWritingManagement(id);
        return new ResponseJson();
    }

    //根据发文Id查找到对应的已读未读名单
    @PostMapping("/ignore/findWritingManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表,传递发文Id（writingId）和type查找已读未读名单", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWritingManagementListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingManagement writingManagement){
        writingManagement.setDepartmentType(1);//排除掉组织架构
        List<WritingManagement> data=writingManagementService.findWritingManagementListByCondition(writingManagement);
        return new ResponseJson(data);
    }


    //查询我已接收的(已不使用)
    @PostMapping("/look/findWritingAndManagementListByCondtion")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWritingAndManagementListByCondtion(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingManagement writingManagement){
        writingManagement.setWritingObjectId(LoginInterceptor.myId());
        List<Writing> data=writingManagementService.findWritingAndManagementListByCondtion(writingManagement);
        return new ResponseJson(data);
    }

    //查看详情修改查看状态
    @GetMapping("/update/lookAndupdateWritingAndWritingManagement/{id}")
    @ApiOperation(value = "查看详情，修改查看状态",notes = "返回响应对象，修改查看状态")
    public  ResponseJson lookAndupdateWritingAndWritingManagement(
            @ApiParam(value = "要查看的id", required = true)
            @PathVariable String id){
        Writing writings = new Writing();
        writings.setId(id);
        writings.setSendObjectId(LoginInterceptor.myId());
        Writing writing =  writingManagementService.lookAndupdateWritingAndWritingManagement(writings);
        return  new ResponseJson(writing);
    }

    //查询我已接收的2.0
    @PostMapping("/look/findWritingAndWritingManagement")
    @ApiOperation(value = "根据条件查找,返回我接收的发文列表 （条件:当前状态（type字段 0为全部 1为待查看 2已收文  时间段（SearchTimeZone[]字段） 公文类型（writingNumberType字段 1为公文  2为教育督导）））", notes = "返回响应对象")
    public ResponseJson findWritingAndWritingManagement(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing
    ){
        if(writing.getWritingNumberType()!=null&&writing.getWritingNumberType()==3){
            writing.setWritingNumberType(null);
        }

        if(writing.getType()!=null&&writing.getType()==0){
            writing.setType(null);
        }
        writing.setSendObjectId(LoginInterceptor.myId());
        String[] zero = writing.getSearchTimeZone();
        if(zero!=null&&zero.length>0){
            writing.setStartTime(zero[0]);
            writing.setEndTime(zero[1]);
        }
        List<Writing> list = writingManagementService.findWritingAndWritingManagement(writing);
        long count = writingManagementService.findWritingAndWritingManagementLong(writing);
        return new ResponseJson(list,count);
    }

}
