package com.yice.edu.cn.jw.service.qusSurvey;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.Option;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class QusSurveyQuestionService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public QusSurveyQuestion findQusSurveyQuestionById(String id) {
        return mot.findById(id,QusSurveyQuestion.class);
    }
    public void saveQusSurveyQuestion(ArrayList<QusSurveyQuestion> qusSurveyQuestionList) {

//        qusSurveyQuestionList.forEach(qusSurveyQuestion -> {
//            qusSurveyQuestion.setCreateTime(DateUtil.now());
//            qusSurveyQuestion.setId(sequenceId.nextId());
//            ArrayList<Option> options=qusSurveyQuestion.getOptions();
//            options.forEach(option ->{
//                option.setOptionId(sequenceId.nextId());
//            });
//            qusSurveyQuestion.setOptions(options);
//        });


//        mot.insert(qusSurveyQuestion);
    }
    public List<QusSurveyQuestion> findQusSurveyQuestionListByCondition(QusSurveyQuestion qusSurveyQuestion) {
        Example<QusSurveyQuestion> example = Example.of(qusSurveyQuestion, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(qusSurveyQuestion.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveyQuestion.class,operations),QusSurveyQuestion.class).getMappedResults();
    }
    public QusSurveyQuestion findOneQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion) {
        Example<QusSurveyQuestion> example = Example.of(qusSurveyQuestion, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurveyQuestion.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurveyQuestion.class,operations),QusSurveyQuestion.class).getUniqueMappedResult();
    }
    public long findQusSurveyQuestionCountByCondition(QusSurveyQuestion qusSurveyQuestion) {
        Example<QusSurveyQuestion> example = Example.of(qusSurveyQuestion, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, QusSurveyQuestion.class);
    }
    public void updateQusSurveyQuestion(QusSurveyQuestion qusSurveyQuestion) {
        mot.updateFirst(query(where("id").is(qusSurveyQuestion.getId())), MongoKit.update(qusSurveyQuestion),QusSurveyQuestion.class);
    }
    public void deleteQusSurveyQuestion(String id) {
        mot.remove(query(where("id").is(id)),QusSurveyQuestion.class);
    }
    public void deleteQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion) {
        Example<QusSurveyQuestion> example = Example.of(qusSurveyQuestion, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, QusSurveyQuestion.class);
    }
    public void batchSaveQusSurveyQuestion(List<QusSurveyQuestion> qusSurveyQuestions){
        qusSurveyQuestions.forEach(qusSurveyQuestion -> qusSurveyQuestion.setId(sequenceId.nextId()));
        mot.insertAll(qusSurveyQuestions);
    }
}
