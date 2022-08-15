package com.yice.edu.cn.ewb.feignClient.scoreAnalysis;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "paperQuestionFeign",path = "/paperQuestion")
public interface PaperQuestionFeign {
    @PostMapping("/findListQuestionListKong")
    List<PaperQuestion> findListQuestionListKong(Paper paper);

    @PostMapping("/findCountQuestionCountKong")
    long findCountQuestionCountKong(Paper paper);

    @GetMapping("/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    PaperTopics findOnePaperTopicsOneKong(@PathVariable("paperId") String paperId, @PathVariable("topicsId") String topicsId);
}
