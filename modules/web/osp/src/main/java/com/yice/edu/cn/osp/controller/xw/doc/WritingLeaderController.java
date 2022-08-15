package com.yice.edu.cn.osp.controller.xw.doc;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.doc.WritingLeaderService;
import com.yice.edu.cn.osp.service.xw.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/writingLeader")
@Api(value = "/writingLeader",description = "（发文管理）我批阅的")
public class WritingLeaderController {
    @Autowired
    private WritingLeaderService writingLeaderService;

    @Autowired
    private WritingService writingService;

    @PostMapping("/saveWritingLeader")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveWritingLeader(
            @ApiParam(value = "对象", required = true)
            @RequestBody WritingLeader writingLeader){
        WritingLeader s=writingLeaderService.saveWritingLeader(writingLeader);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findWritingLeaderById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findWritingLeaderById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        WritingLeader writingLeader=writingLeaderService.findWritingLeaderById(id);
        return new ResponseJson(writingLeader);
    }

    @PostMapping("/update/updateWritingLeader")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateWritingLeader(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody WritingLeader writingLeader){
        writingLeaderService.updateWritingLeader(writingLeader);
        return new ResponseJson();
    }

    @GetMapping("/look/lookWritingLeaderById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookWritingLeaderById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        WritingLeader writingLeader=writingLeaderService.findWritingLeaderById(id);
        return new ResponseJson(writingLeader);
    }

    @PostMapping("/findWritingLeadersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findWritingLeadersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WritingLeader writingLeader){
        List<WritingLeader> data=writingLeaderService.findWritingLeaderListByCondition(writingLeader);
        long count=writingLeaderService.findWritingLeaderCountByCondition(writingLeader);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneWritingLeaderByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneWritingLeaderByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingLeader writingLeader){
        WritingLeader one=writingLeaderService.findOneWritingLeaderByCondition(writingLeader);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteWritingLeader/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteWritingLeader(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        writingLeaderService.deleteWritingLeader(id);
        return new ResponseJson();
    }


    @PostMapping("/findWritingLeaderListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWritingLeaderListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WritingLeader writingLeader){
        List<WritingLeader> data=writingLeaderService.findWritingLeaderListByCondition(writingLeader);
        return new ResponseJson(data);
    }


    //我批阅的
    @PostMapping("/look/findWritingLeadersByConditionGai")
    @ApiOperation(value = "根据条件查找,返回我批阅的发文列表 （条件:当前状态（writingType字段 0为全部 1为待我审批 2为已完成 3为已驳回  时间段（SearchTimeZone[]字段） 公文类型（writingNumberType字段 1为公文  2为教育督导）））", notes = "返回响应对象")
    public ResponseJson findWritingLeadersByConditionGai(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing){
        if(writing.getWritingNumberType()!=null&&writing.getWritingNumberType()==3){
            writing.setWritingNumberType(null);
        }
        if(writing.getWritingType()!=null&&writing.getWritingType()==0){
            writing.setWritingType(null);
        }
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setLeaderId(LoginInterceptor.myId());
        String[]  zero = writing.getSearchTimeZone();
        if(zero!=null&&zero.length>0){
            writing.setStartTime(zero[0]);
            writing.setEndTime(zero[1]);
        }
        //清除前台的排序
        if("id".equals(writing.getPager().getSortField())||writing.getPager().getSortField()==null){
            writing.getPager().setSortField("");
            writing.setOrder("true");
        }

        List<Writing> data = writingService.findWritingListByCondition(writing);
        long count = writingService.findWritingCountByCondition(writing);
        return new ResponseJson(data,count);
    }


    @GetMapping("/look/findWritingById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findWritingLeaderByIdGai(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Writing writing = writingService.findWritingById(id);
        return new ResponseJson(writing);
    }
    //驳回
    @PostMapping("/update/updateWriting")
    @ApiOperation(value = "驳回发文,将writingType改为3后，传递Writing对象", notes = "返回响应对象")
    public ResponseJson updateWriting(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing){
        writingLeaderService.updateWriting(writing);
        return new ResponseJson();
    }

    //审核通过
    @PostMapping("/update/updateWritingAndLeader")
    @ApiOperation(value = "审核通过,将writingType变为2后，传递Writing对象",notes = "返回响应对象")
    public ResponseJson updateWritingAndLeader(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing
    ){
        writingLeaderService.updateWritingAndLeader(writing);
        return  new ResponseJson();
    }

}
