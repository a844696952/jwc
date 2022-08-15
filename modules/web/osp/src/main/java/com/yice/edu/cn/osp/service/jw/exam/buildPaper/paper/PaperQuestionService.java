package com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.PaperQuestionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class PaperQuestionService {
    @Autowired
    private PaperQuestionFeign paperQuestionFeign;

    public PaperQuestion findPaperQuestionById(String id) {
        return paperQuestionFeign.findPaperQuestionById(id);
    }

    public PaperQuestion savePaperQuestion(PaperQuestion paperQuestion) {
        return paperQuestionFeign.savePaperQuestion(paperQuestion);
    }

    public List<PaperQuestion> findPaperQuestionListByCondition(PaperQuestion paperQuestion) {
        return paperQuestionFeign.findPaperQuestionListByCondition(paperQuestion);
    }

    public PaperQuestion findOnePaperQuestionByCondition(PaperQuestion paperQuestion) {
        return paperQuestionFeign.findOnePaperQuestionByCondition(paperQuestion);
    }

    public long findPaperQuestionCountByCondition(PaperQuestion paperQuestion) {
        return paperQuestionFeign.findPaperQuestionCountByCondition(paperQuestion);
    }

    public void updatePaperQuestion(PaperQuestion paperQuestion) {
        paperQuestionFeign.updatePaperQuestion(paperQuestion);
    }

    public void deletePaperQuestion(String id) {
        paperQuestionFeign.deletePaperQuestion(id);
    }

    public void deletePaperQuestionByCondition(PaperQuestion paperQuestion) {
        paperQuestionFeign.deletePaperQuestionByCondition(paperQuestion);
    }


    public void dragSortBigPaperQuestionKong(List<PaperQuestion> paperQuestionList){
        paperQuestionFeign.dragSortBigPaperQuestionKong(paperQuestionList);
    }

    public void dragSortMinPaperQuestionKong(PaperQuestion paperQuestion){
        paperQuestionFeign.dragSortMinPaperQuestionKong(paperQuestion);
    }

    public ResponseJson findStandardPaperQuestionKong(String testPaperId){
        return paperQuestionFeign.findStandardPaperQuestionKong(testPaperId);
    }

    public  void  updatePaperQuestionKong(PaperTopics paperTopics){
        paperQuestionFeign.updatePaperQuestionKong(paperTopics);
    }

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
