package com.yice.edu.cn.bmp.feignClient.scoreAnalysis;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/paperQuestion")
public interface PaperQuestionFeign {
    @GetMapping("/findPaperQuestionById/{id}")
    PaperQuestion findPaperQuestionById(@PathVariable("id") String id);
    @PostMapping("/savePaperQuestion")
    PaperQuestion savePaperQuestion(PaperQuestion paperQuestion);
    @PostMapping("/findPaperQuestionListByCondition")
    List<PaperQuestion> findPaperQuestionListByCondition(PaperQuestion paperQuestion);
    @PostMapping("/findOnePaperQuestionByCondition")
    PaperQuestion findOnePaperQuestionByCondition(PaperQuestion paperQuestion);
    @PostMapping("/findPaperQuestionCountByCondition")
    long findPaperQuestionCountByCondition(PaperQuestion paperQuestion);
    @PostMapping("/updatePaperQuestion")
    void updatePaperQuestion(PaperQuestion paperQuestion);
    @GetMapping("/deletePaperQuestion/{id}")
    void deletePaperQuestion(@PathVariable("id") String id);
    @PostMapping("/deletePaperQuestionByCondition")
    void deletePaperQuestionByCondition(PaperQuestion paperQuestion);


    @PostMapping("/dragSortBigPaperQuestionKong")
    void dragSortBigPaperQuestionKong(List<PaperQuestion> paperQuestionList);

    @PostMapping("/dragSortMinPaperQuestionKong")
    void dragSortMinPaperQuestionKong(PaperQuestion paperQuestion);

    @GetMapping("/findStandardPaperQuestionKong/{testPaperId}")
    ResponseJson findStandardPaperQuestionKong(@PathVariable("testPaperId") String createUserId);

    @PostMapping("/updatePaperQuestionKong")
    void updatePaperQuestionKong(PaperTopics paperTopics);


    @PostMapping("/findListQuestionListKong")
    List<PaperQuestion> findListQuestionListKong(Paper paper);

    @PostMapping("/findCountQuestionCountKong")
    long findCountQuestionCountKong(Paper paper);

    @GetMapping("/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    PaperTopics findOnePaperTopicsOneKong(@PathVariable("paperId") String paperId, @PathVariable("topicsId") String topicsId);
}
