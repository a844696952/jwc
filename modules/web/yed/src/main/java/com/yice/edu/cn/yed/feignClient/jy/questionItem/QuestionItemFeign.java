package com.yice.edu.cn.yed.feignClient.jy.questionItem;

import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "questionItemFeign",path = "/questionItem")
public interface QuestionItemFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findQuestionItemById/{id}")
    QuestionItem findQuestionItemById(@PathVariable("id") String id);
    @PostMapping("/saveQuestionItem")
    QuestionItem saveQuestionItem(QuestionItem questionItem);
    @PostMapping("/batchSaveQuestionItem")
    void batchSaveQuestionItem(List<QuestionItem> questionItems);
    @PostMapping("/findQuestionItemListByCondition")
    List<QuestionItem> findQuestionItemListByCondition(QuestionItem questionItem);
    @PostMapping("/findOneQuestionItemByCondition")
    QuestionItem findOneQuestionItemByCondition(QuestionItem questionItem);
    @PostMapping("/findQuestionItemCountByCondition")
    long findQuestionItemCountByCondition(QuestionItem questionItem);
    @PostMapping("/updateQuestionItem")
    void updateQuestionItem(QuestionItem questionItem);
    @PostMapping("/updateQuestionItemForNotNull")
    void updateQuestionItemForAll(QuestionItem questionItem);
    @GetMapping("/deleteQuestionItem/{id}")
    void deleteQuestionItem(@PathVariable("id") String id);
    @PostMapping("/deleteQuestionItemByCondition")
    void deleteQuestionItemByCondition(QuestionItem questionItem);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
