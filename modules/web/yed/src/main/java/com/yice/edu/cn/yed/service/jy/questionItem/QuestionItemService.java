package com.yice.edu.cn.yed.service.jy.questionItem;

import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.yed.feignClient.jy.questionItem.QuestionItemFeign;
import com.yice.edu.cn.yed.feignClient.jy.source21.Source21Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionItemService {
    @Autowired
    private QuestionItemFeign questionItemFeign;
    @Autowired
    private Source21Feign source21Feign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public QuestionItem findQuestionItemById(String id) {
        return questionItemFeign.findQuestionItemById(id);
    }

    public QuestionItem saveQuestionItem(QuestionItem questionItem) {
        return questionItemFeign.saveQuestionItem(questionItem);
    }

    public void batchSaveQuestionItem(List<QuestionItem> questionItems){
        questionItemFeign.batchSaveQuestionItem(questionItems);
    }

    public List<QuestionItem> findQuestionItemListByCondition(QuestionItem questionItem) {
        return questionItemFeign.findQuestionItemListByCondition(questionItem);
    }

    public QuestionItem findOneQuestionItemByCondition(QuestionItem questionItem) {
        return questionItemFeign.findOneQuestionItemByCondition(questionItem);
    }

    public long findQuestionItemCountByCondition(QuestionItem questionItem) {
        return questionItemFeign.findQuestionItemCountByCondition(questionItem);
    }

    public void updateQuestionItem(QuestionItem questionItem) {
        questionItemFeign.updateQuestionItem(questionItem);
    }

    public void updateQuestionItemForAll(QuestionItem questionItem) {
        questionItemFeign.updateQuestionItemForAll(questionItem);
    }

    public void deleteQuestionItem(String id) {
        questionItemFeign.deleteQuestionItem(id);
    }

    public void deleteQuestionItemByCondition(QuestionItem questionItem) {
        questionItemFeign.deleteQuestionItemByCondition(questionItem);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void synchronizeType(){
        source21Feign.synchronizeType();
    }
}
