package com.yice.edu.cn.osp.controller.jy.journal;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.jy.journal.JournalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/journal")
@Api(value = "/journal",description = "日志模块")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping("/ignore/saveJournal")
    @ApiOperation(value = "保存日志对象", notes = "返回响应对象")
    public ResponseJson saveJournal(
            @ApiParam(value = "日志对象", required = true)
            @Validated(value = GroupOne.class)
            @RequestBody Journal journal){
        if(journal.getImages()!=null&&journal.getImages().size()>9){
            return new ResponseJson(false, "图片最多9张");
        }
        journal.setUserId(myId());
        journal.setSchoolId(mySchoolId());
        journal.setFromTeacher(true);
        journal.setName(currentTeacher().getName());
        journal.setPortrait(currentTeacher().getImgUrl());
        boolean hasSensitive=journalService.saveJournal2(journal);
        if(hasSensitive){
            return new ResponseJson(false,"日志内容含有敏感词汇，请核实后重新编辑");
        }
        return new ResponseJson();
    }

    @GetMapping("/ignore/deleteJournal/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJournal(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        journalService.deleteJournal(id);
        return new ResponseJson();
    }


    @PostMapping("/ignore/findJournalsForMyIndex")
    @ApiOperation(value = "获取教师空间里我的主页的日志列表,同时也给我的日志tab用")
    public ResponseJson findJournalsForMyIndex(
            @Validated
            @RequestBody Journal journal){
        List<Journal> journals=journalService.findJournalsForMyIndex(journal);
        return new ResponseJson(journals);
    }

    @GetMapping("/ignore/clickThumb/{sqId}")
    @ApiOperation(value = "给日志点赞")
    public ResponseJson clickThumb(@PathVariable String sqId){
        journalService.clickThumb(sqId,myId());
        return new ResponseJson();
    }


}
