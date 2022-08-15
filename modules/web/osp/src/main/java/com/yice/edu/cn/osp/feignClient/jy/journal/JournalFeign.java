package com.yice.edu.cn.osp.feignClient.jy.journal;

import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.journal.NewestJournal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "journalFeign",path = "/journal")
public interface JournalFeign {
    @GetMapping("/findJournalById/{id}")
    Journal findJournalById(@PathVariable("id") String id);
    @PostMapping("/saveJournal")
    Journal saveJournal(Journal journal);
    @PostMapping("/findJournalListByCondition")
    List<Journal> findJournalListByCondition(Journal journal);
    @PostMapping("/findOneJournalByCondition")
    Journal findOneJournalByCondition(Journal journal);
    @PostMapping("/findJournalCountByCondition")
    long findJournalCountByCondition(Journal journal);
    @PostMapping("/updateJournal")
    void updateJournal(Journal journal);
    @GetMapping("/deleteJournal/{id}")
    void deleteJournal(@PathVariable("id") String id);
    @PostMapping("/deleteJournalByCondition")
    void deleteJournalByCondition(Journal journal);
    @PostMapping("/findJournalsForMyIndex")
    List<Journal> findJournalsForMyIndex(Journal journal);
    @GetMapping("/clickThumb/{sqId}/{teacherId}")
    void clickThumb(@PathVariable("sqId") String sqId,@PathVariable("teacherId") String teacherId);
    @PostMapping("/findNewestJournalsForWorkbench")
    NewestJournal findNewestJournalsForWorkbench(Journal journal);
}
