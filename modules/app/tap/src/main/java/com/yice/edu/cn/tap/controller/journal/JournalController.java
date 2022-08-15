package com.yice.edu.cn.tap.controller.journal;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.user.User;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.tap.service.journal.JournalService;
import com.yice.edu.cn.tap.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/journal")
@Api(value = "/journal", description = "日志模块")
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;
    @PostMapping("/saveJournal")
    @ApiOperation(value = "保存日志对象", notes = "返回响应对象")
    public ResponseJson saveJournal(
            @ApiParam(value = "只需要text或者images,必有其一", required = true)
            @Validated(value = GroupOne.class)
            @RequestBody Journal journal) {
        if (journal.getText() == null && (journal.getImages() == null || journal.getImages().size() == 0)) {
            return new ResponseJson(false, "日志文本或者图片必须上传");
        }
        if (journal.getImages() != null && journal.getImages().size() > 9) {
            return new ResponseJson(false, "图片最多9张");
        }
        journal.setUserId(myId());
        journal.setSchoolId(mySchoolId());
        journal.setFromTeacher(true);
        journal.setName(currentTeacher().getName());
        journal.setPortrait(currentTeacher().getImgUrl());
        boolean hasSensitive = journalService.saveJournal2(journal);
        if (hasSensitive) {
            return new ResponseJson(false, "日志内容含有敏感词汇，请核实后重新编辑");
        }
        return new ResponseJson();
    }

    @GetMapping("/deleteJournal/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJournal(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        journalService.deleteJournal(id);
        return new ResponseJson();
    }


    @PostMapping("/findJournalsForMyIndex")
    @ApiOperation(value = "获取教师个人主页日志")
    public ResponseJson findJournalsForMyIndex(
            @Validated(value = {Default.class})
            @ApiParam(value = "需传pager对象")
            @RequestBody Journal journal) {
        List<Journal> journals = journalService.findJournalsForMyIndex(journal);
        return new ResponseJson(journals);
    }

    @PostMapping("/findOtherIndexJournals")
    @ApiOperation(value = "根据用户id,获取用户的个人日志列表(查看别人日志空间时用)")
    public ResponseJson findOtherIndexJournals(
            @ApiParam("需要传userId,即被查看的教师或者学生id,还有pager")
            @Validated({GroupThree.class,Default.class})
            @RequestBody Journal journal) {
        final List<Journal> otherIndexJournals = journalService.findOtherIndexJournals(journal);
        long count=journalService.findOtherIndexJournalCount(journal);
        return new ResponseJson(otherIndexJournals,count);
    }


    @GetMapping("/clickThumb/{sqId}")
    @ApiOperation(value = "给日志点赞")
    public ResponseJson clickThumb(
            @ApiParam(value = "日志列表里的sqId")
            @PathVariable
            String sqId) {
        journalService.clickThumb(sqId, myId());
        return new ResponseJson();
    }

    @GetMapping("/findUserById/{id}")
    @ApiOperation("根据用户id查询用户数据,(老师或者学生)")
    public ResponseJson findUserById(@PathVariable String id){
        User user=userService.findUserById(id);
        return new ResponseJson(user);
    }

}
