package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "paperFeign",path = "/testPaper")
public interface PaperFeign {
    @GetMapping("/findTestPaperById/{id}")
    Paper findTestPaperById(@PathVariable("id") String id);
    @PostMapping("/saveTestPaper")
    String[] saveTestPaper(Paper paper);
    @PostMapping("/findTestPaperListByCondition")
    List<Paper> findTestPaperListByCondition(Paper paper);
    @PostMapping("/findOneTestPaperByCondition")
    Paper findOneTestPaperByCondition(Paper paper);
    @PostMapping("/findTestPaperCountByCondition")
    long findTestPaperCountByCondition(Paper paper);
    @PostMapping("/updateTestPaper")
    void updateTestPaper(Paper paper);
    @GetMapping("/deleteTestPaper/{id}")
    void deleteTestPaper(@PathVariable("id") String id);
    @PostMapping("/deleteTestPaperByCondition")
    void deleteTestPaperByCondition(Paper paper);

    @PostMapping("/saveTestPaperOne")
    void saveTestPaperOne(Paper paper);


    @PostMapping("/savePaperAndPaperQuestion")
    Paper savePaperAndPaperQuestion(PaperTopics paperTopics);

    @PostMapping("/removePaperQuestionKong")
    void removePaperQuestionKong(PaperTopics paperTopics);

    @PostMapping("/findTestPaperListByConditionKong")
    List<Paper> findTestPaperListByConditionKong(Paper paper);

    @PostMapping("/findTestPaperCountByConditionKong")
    long findTestPaperCountByConditionKong(Paper paper);

    @PostMapping("/setPaperFlags/{flag}")
    void setPaperFlags(@RequestBody SchoolExam schoolExam,@PathVariable("flag") Boolean flag);

    @PostMapping("/findEveryDayPaper")
    List<PaperDayMonth> findEveryDayPaper(Paper paper);

    @GetMapping("/findBySchoolExamIdAndCouserId/{schoolExamId}/{courseId}")
    List<PaperQuestion> findBySchoolExamIdAndCouserId(@PathVariable("schoolExamId")String schoolExamId,@PathVariable("courseId")String courseId);
}
