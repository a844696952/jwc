package com.yice.edu.cn.jw.controller.exam.buildPaper.myPaper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.jw.service.exam.buildPaper.myPaper.MyPaperService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myPaper")
public class MyPaperController {
    @Autowired
    private MyPaperService myPaperService;

    @PostMapping("/findOneTestPaperByCondition")
    @ApiOperation(value = "根据条件查询")
    public Paper findOneTestPaperByCondition(
            @ApiParam(value = "对象")
            @RequestBody Paper paper
    ){
       return myPaperService.findOneTestPaperByCondition(paper);
    }

    @PostMapping("/updatePaperQuestion")
    @ApiOperation(value = "根据用户Id查询试题篮是否存在试卷",notes = "返回对象")
    public Paper updatePaperQuestion(
            @ApiParam(value = "用户对象")
            @RequestBody Paper paper
    ){
       return myPaperService.updatePaperQuestion(paper);
    }

    @GetMapping("/deletePaper/{id}")
    @ApiOperation(value = "根据试卷Id删除试卷",notes = "返回（0，1）判断试卷是否被使用")
    public long deletePaper(
            @ApiParam(value = "试卷Id",required = true)
            @PathVariable String id
    ){
        return myPaperService.deletePaper(id);
    }

    @PostMapping("/findOnePaper")
    @ApiOperation(value = "根据试卷Id查询试卷",notes = "返回试卷对象")
    public Paper findOnePaper(
            @ApiParam(value = "根据试卷Id查询试卷")
            @RequestBody Paper paper
    ){
        return myPaperService.findOnePaper(paper);
    }


    @PostMapping("/paperClone")
    @ApiModelProperty(value = "克隆一份试卷",notes = "返回新的试卷记录")
    public Paper paperClone(
            @ApiParam(value = "传递要克隆的试卷对象")
            @RequestBody Paper paper
    ){
        return myPaperService.paperClone(paper);
    }

    @PostMapping("/coveringPaper")
    @ApiModelProperty(value = "覆盖当前试题篮数据",notes = "返回新的试卷记录")
    public Paper coveringPaper(
            @ApiParam(value = "传递要克隆试卷Id")
            @RequestBody Paper paper
    ){
        return myPaperService.coveringPaper(paper);
    }

}
