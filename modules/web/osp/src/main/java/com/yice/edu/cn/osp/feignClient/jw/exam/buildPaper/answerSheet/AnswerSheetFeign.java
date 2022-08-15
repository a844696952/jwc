package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "answerSheetFeign",path = "/answerSheet")
public interface AnswerSheetFeign {
    @GetMapping("/findAnswerSheetById/{id}")
    AnswerSheet findAnswerSheetById(@PathVariable("id") String id);
    @PostMapping("/saveAnswerSheet")
    AnswerSheet saveAnswerSheet(AnswerSheet answerSheet);
    @PostMapping("/findAnswerSheetListByCondition")
    List<AnswerSheet> findAnswerSheetListByCondition(AnswerSheet answerSheet);
    @PostMapping("/findOneAnswerSheetByCondition")
    AnswerSheet findOneAnswerSheetByCondition(AnswerSheet answerSheet);
    @PostMapping("/findAnswerSheetCountByCondition")
    long findAnswerSheetCountByCondition(AnswerSheet answerSheet);
    @PostMapping("/updateAnswerSheet")
    void updateAnswerSheet(AnswerSheet answerSheet);
    @GetMapping("/deleteAnswerSheet/{id}")
    void deleteAnswerSheet(@PathVariable("id") String id);
    @PostMapping("/deleteAnswerSheetByCondition")
    void deleteAnswerSheetByCondition(AnswerSheet answerSheet);
}
