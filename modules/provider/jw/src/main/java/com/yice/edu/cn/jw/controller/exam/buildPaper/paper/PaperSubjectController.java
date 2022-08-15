package com.yice.edu.cn.jw.controller.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperSubject;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paperSubject")
@Api(value = "/paperSubject",description = "保存上一次选中的科目记录")
public class PaperSubjectController {
    @Autowired
    private PaperSubjectService paperSubjectService;

    @PostMapping("/savaPaperSubject")
    @ApiOperation(value = "添加科目记录",notes = "无返回结果")
    public void savaPaperSubject(
            @ApiParam(value = "添加科目记录")
            @RequestBody PaperSubject paperSubject
    ){
        paperSubjectService.savePaperSubject(paperSubject);
    }

    @GetMapping("/findOnePaperSubjectKong/{createUserId}")
    @ApiOperation(value = "查看上次选择",notes = "无返回结果")
    public PaperSubject findOnePaperSubjectKong(
            @ApiParam(value = "用户id",required = true)
            @PathVariable String createUserId
    ){
       return paperSubjectService.findOnePaperSubjectKong(createUserId);
    }


}
