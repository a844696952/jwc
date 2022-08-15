package com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.structure.LikeStatic;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.PaperFeign;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {


    @Autowired
    private PaperFeign paperFeign;


    public Paper findTestPaperById(String id) {
        return paperFeign.findTestPaperById(id);
    }

    public String[] saveTestPaper(Paper paper) {
         return  paperFeign.saveTestPaper(paper);
    }

    public List<Paper> findTestPaperListByCondition(Paper paper) {
        return paperFeign.findTestPaperListByCondition(paper);
    }

    public Paper findOneTestPaperByCondition(Paper paper) {
        return paperFeign.findOneTestPaperByCondition(paper);
    }

    public long findTestPaperCountByCondition(Paper paper) {
        return paperFeign.findTestPaperCountByCondition(paper);
    }

    public void updateTestPaper(Paper paper) {
        paperFeign.updateTestPaper(paper);
    }

    public void deleteTestPaper(String id) {
        paperFeign.deleteTestPaper(id);
    }

    public void deleteTestPaperByCondition(Paper paper) {
        paperFeign.deleteTestPaperByCondition(paper);
    }

    public void saveTestPaperOne(Paper paper){
        paperFeign.saveTestPaperOne(paper);
    }


    public  Paper savePaperAndPaperQuestion(PaperTopics paperTopics){
        return paperFeign.savePaperAndPaperQuestion(paperTopics);
    }

    public void removePaperQuestionKong(PaperTopics paperTopics){
          paperFeign.removePaperQuestionKong(paperTopics);
    }


    /**
     * 自定义方法
     */

    public List<Paper> findTestPaperListByConditionKong(Paper paper){
        return paperFeign.findTestPaperListByConditionKong(paper);
    }

    public long findTestPaperCountByConditionKong(Paper paper){
        return paperFeign.findTestPaperCountByConditionKong(paper);
    }

    public void setPaperFlags(SchoolExam schoolExam,Boolean flag){
        paperFeign.setPaperFlags(schoolExam,flag);
    }

    public List<PaperDayMonth> findEveryDayPaper(Paper paper){
        return paperFeign.findEveryDayPaper(paper);
    }

    public List<PaperQuestion> findBySchoolExamIdAndCouserId(String schoolExamId,String courseId){
        return paperFeign.findBySchoolExamIdAndCouserId(schoolExamId, courseId);
    }
}
