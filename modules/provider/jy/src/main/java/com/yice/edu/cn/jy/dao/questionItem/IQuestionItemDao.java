package com.yice.edu.cn.jy.dao.questionItem;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IQuestionItemDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<QuestionItem> findQuestionItemListByCondition(QuestionItem questionItem);

    long findQuestionItemCountByCondition(QuestionItem questionItem);

    QuestionItem findOneQuestionItemByCondition(QuestionItem questionItem);

    QuestionItem findQuestionItemById(@Param("id") String id);

    void saveQuestionItem(QuestionItem questionItem);

    void updateQuestionItem(QuestionItem questionItem);

    void updateQuestionItemForAll(QuestionItem questionItem);

    void deleteQuestionItem(@Param("id") String id);

    void deleteQuestionItemByCondition(QuestionItem questionItem);

    void batchSaveQuestionItem(List<QuestionItem> questionItems);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
