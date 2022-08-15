package com.yice.edu.cn.ewb.service.scoreAnalysis;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.ewb.feignClient.scoreAnalysis.PaperQuestionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperQuestionService {
    @Autowired
    private PaperQuestionFeign paperQuestionFeign;

    public  List<PaperQuestion> findListQuestionListKong(Paper paper){
       return paperQuestionFeign.findListQuestionListKong(paper);
    }

    public  long findCountQuestionCountKong(Paper paper){
        return paperQuestionFeign.findCountQuestionCountKong(paper);
    }

    public PaperTopics findOnePaperTopicsOneKong(String paperId,String topicsId){
        return paperQuestionFeign.findOnePaperTopicsOneKong(paperId,topicsId);
    }
}
