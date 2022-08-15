package com.yice.edu.cn.jy.controller.mySpace.journal;

import com.google.gson.JsonObject;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.journal.NewestJournal;
import com.yice.edu.cn.jy.service.mySpace.journal.JournalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
@Api(value = "/journal",description = "日志模块")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @GetMapping("/findJournalById/{id}")
    @ApiOperation(value = "通过id查找日志", notes = "返回日志对象")
    public Journal findJournalById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return journalService.findJournalById(id);
    }

    @PostMapping("/saveJournal")
    @ApiOperation(value = "保存日志", notes = "返回日志对象")
    public Journal saveJournal(
            @ApiParam(value = "日志对象", required = true)
            @RequestBody Journal journal){
        journalService.saveJournal(journal);
        return journal;
    }

    @PostMapping("/findJournalListByCondition")
    @ApiOperation(value = "根据条件查找日志列表", notes = "返回日志列表")
    public List<Journal> findJournalListByCondition(
            @ApiParam(value = "日志对象")
            @RequestBody Journal journal){
        return journalService.findJournalListByCondition(journal);
    }
    @PostMapping("/findJournalCountByCondition")
    @ApiOperation(value = "根据条件查找日志列表个数", notes = "返回日志总个数")
    public long findJournalCountByCondition(
            @ApiParam(value = "日志对象")
            @RequestBody Journal journal){
        return journalService.findJournalCountByCondition(journal);
    }

    @PostMapping("/updateJournal")
    @ApiOperation(value = "修改日志", notes = "日志对象必传")
    public void updateJournal(
            @ApiParam(value = "日志对象,对象属性不为空则修改", required = true)
            @RequestBody Journal journal){
        journalService.updateJournal(journal);
    }

    @GetMapping("/deleteJournal/{id}")
    @ApiOperation(value = "通过id删除日志")
    public void deleteJournal(
            @ApiParam(value = "日志对象", required = true)
            @PathVariable String id){
        journalService.deleteJournal(id);
    }
    @PostMapping("/deleteJournalByCondition")
    @ApiOperation(value = "根据条件删除日志")
    public void deleteJournalByCondition(
            @ApiParam(value = "日志对象")
            @RequestBody Journal journal){
        journalService.deleteJournalByCondition(journal);
    }
    @PostMapping("/findOneJournalByCondition")
    @ApiOperation(value = "根据条件查找单个日志,结果必须为单条数据", notes = "返回单个日志,没有时为空")
    public Journal findOneJournalByCondition(
            @ApiParam(value = "日志对象")
            @RequestBody Journal journal){
        return journalService.findOneJournalByCondition(journal);
    }

    @PostMapping("/findJournalsForMyIndex")
    @ApiOperation(value = "根据分页对象,教师id,教师所教班级对象列表查询教师或者学生的主页日志", notes = "返回日志列表")
    public List<Journal> findJournalsForMyIndex(@RequestBody Journal journal){
        return journalService.findJournalsForMyIndex(journal);
    }

    @PostMapping("/findOtherIndexJournals")
    @ApiOperation(value = "根据用户id和登录用户id，获取用户的个人日志列表(查看别人日志空间时用)")
    public List<Journal> findOtherIndexJournals(@RequestBody Journal journal){
        return journalService.findOtherIndexJournals(journal);
    }

    @PostMapping("/findOtherIndexJournalCount")
    public long findOtherIndexJournalCount(@RequestBody Journal journal){
        return journalService.findOtherIndexJournalCount(journal);
    }

    @GetMapping("/clickThumb/{sqId}/{userId}")
    @ApiOperation(value = "根据日志sqId和教师id,为日志点赞")
    public void clickThumb(@PathVariable("sqId") String sqId,@PathVariable("userId") String userId){
        journalService.clickThumb(sqId, userId);
    }

    @PostMapping("/findNewestJournalsForWorkbench")
    @ApiOperation(value="根据教师id查询教师的最新日志和教师所教的班级学生日志")
    public NewestJournal findNewestJournalsForWorkbench(@RequestBody Journal journal){
        return journalService.findNewestJournalsForWorkbench(journal);
    }



}
