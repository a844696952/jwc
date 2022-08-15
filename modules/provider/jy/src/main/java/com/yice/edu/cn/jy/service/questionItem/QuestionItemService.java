package com.yice.edu.cn.jy.service.questionItem;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.jy.dao.questionItem.IQuestionItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionItemService {
    @Autowired
    private IQuestionItemDao questionItemDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public QuestionItem findQuestionItemById(String id) {
        return questionItemDao.findQuestionItemById(id);
    }
    @Transactional
    public void saveQuestionItem(QuestionItem questionItem) {
        questionItem.setId(sequenceId.nextId());
        questionItemDao.saveQuestionItem(questionItem);
    }
    @Transactional(readOnly = true)
    public List<QuestionItem> findQuestionItemListByCondition(QuestionItem questionItem) {
        return questionItemDao.findQuestionItemListByCondition(questionItem);
    }
    @Transactional(readOnly = true)
    public QuestionItem findOneQuestionItemByCondition(QuestionItem questionItem) {
        return questionItemDao.findOneQuestionItemByCondition(questionItem);
    }
    @Transactional(readOnly = true)
    public long findQuestionItemCountByCondition(QuestionItem questionItem) {
        return questionItemDao.findQuestionItemCountByCondition(questionItem);
    }
    @Transactional
    public void updateQuestionItem(QuestionItem questionItem) {
        questionItemDao.updateQuestionItem(questionItem);
    }
    @Transactional
    public void updateQuestionItemForAll(QuestionItem questionItem) {
        questionItemDao.updateQuestionItemForAll(questionItem);
    }
    @Transactional
    public void deleteQuestionItem(String id) {
        questionItemDao.deleteQuestionItem(id);
    }
    @Transactional
    public void deleteQuestionItemByCondition(QuestionItem questionItem) {
        questionItemDao.deleteQuestionItemByCondition(questionItem);
    }
    @Transactional
    public void batchSaveQuestionItem(List<QuestionItem> questionItems){
        questionItems.forEach(questionItem -> questionItem.setId(sequenceId.nextId()));
        questionItemDao.batchSaveQuestionItem(questionItems);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
