package com.yice.edu.cn.xw.controller.doc;

import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import com.yice.edu.cn.xw.service.doc.WritingLeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writingLeader")
@Api(value = "/writingLeader",description = "模块")
public class WritingLeaderController {
    @Autowired
    private WritingLeaderService writingLeaderService;

    @GetMapping("/findWritingLeaderById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WritingLeader findWritingLeaderById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return writingLeaderService.findWritingLeaderById(id);
    }

    @PostMapping("/saveWritingLeader")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WritingLeader saveWritingLeader(
            @ApiParam(value = "对象", required = true)
            @RequestBody WritingLeader writingLeader){
        writingLeaderService.saveWritingLeader(writingLeader);
        return writingLeader;
    }

    @PostMapping("/findWritingLeaderListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WritingLeader> findWritingLeaderListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingLeader writingLeader){
        return writingLeaderService.findWritingLeaderListByCondition(writingLeader);
    }
    @PostMapping("/findWritingLeaderCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWritingLeaderCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingLeader writingLeader){
        return writingLeaderService.findWritingLeaderCountByCondition(writingLeader);
    }

    @PostMapping("/updateWritingLeader")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWritingLeader(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WritingLeader writingLeader){
        writingLeaderService.updateWritingLeader(writingLeader);
    }

    @GetMapping("/deleteWritingLeader/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWritingLeader(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        writingLeaderService.deleteWritingLeader(id);
    }
    @PostMapping("/deleteWritingLeaderByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWritingLeaderByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingLeader writingLeader){
        writingLeaderService.deleteWritingLeaderByCondition(writingLeader);
    }
    @PostMapping("/findOneWritingLeaderByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WritingLeader findOneWritingLeaderByCondition(
            @ApiParam(value = "对象")
            @RequestBody WritingLeader writingLeader){
        return writingLeaderService.findOneWritingLeaderByCondition(writingLeader);
    }

    //驳回
    @PostMapping("/updateWriting")
    @ApiOperation(value = "修改",notes = "对象必传")
    public  void  updateWriting(
            @ApiParam(value = "对象")
            @RequestBody Writing writing
    ){
        writingLeaderService.updateWriting(writing);
    }

    //审核通过
    @PostMapping("/updateWritingAndLeader")
    @ApiOperation(value = "修改",notes = "对象必传")
    public  void  updateWritingAndLeader(
            @ApiParam(value = "对象")
            @RequestBody Writing writing
    ){
        writingLeaderService.updateWritingAndLeader(writing);
    }
}
