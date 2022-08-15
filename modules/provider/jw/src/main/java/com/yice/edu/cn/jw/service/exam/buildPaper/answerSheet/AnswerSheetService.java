package com.yice.edu.cn.jw.service.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnswerSheetService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public AnswerSheet findAnswerSheetById(String id) {
        return mot.findById(id,AnswerSheet.class);
    }
    public void saveAnswerSheet(AnswerSheet answerSheet) {
        answerSheet.setCreateTime(DateUtil.now());
        answerSheet.setId(sequenceId.nextId());
        mot.insert(answerSheet);
    }
    public List<AnswerSheet> findAnswerSheetListByCondition(AnswerSheet answerSheet) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(answerSheet, answerSheet.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(answerSheet.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(AnswerSheet.class,operations),AnswerSheet.class).getMappedResults();

    }
    public long findAnswerSheetCountByCondition(AnswerSheet answerSheet) {
        Criteria criteria = MongoKit.buildCriteria(answerSheet, answerSheet.getPager());
        return mot.count(query(criteria), AnswerSheet.class);
    }
    public AnswerSheet findOneAnswerSheetByCondition(AnswerSheet answerSheet) {
        Example<AnswerSheet> example = Example.of(answerSheet, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = answerSheet.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnswerSheet.class,operations),AnswerSheet.class).getUniqueMappedResult();
    }

    public void updateAnswerSheet(AnswerSheet answerSheet) {
        mot.updateFirst(query(where("_id").is(answerSheet.getId())), MongoKit.update(answerSheet),AnswerSheet.class);
    }
    public void deleteAnswerSheet(String id) {
        mot.remove(query(where("id").is(id)),AnswerSheet.class);
    }
    public void deleteAnswerSheetByCondition(AnswerSheet answerSheet) {
        Example<AnswerSheet> example = Example.of(answerSheet, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnswerSheet.class);
    }
    public void batchSaveAnswerSheet(List<AnswerSheet> answerSheets){
        answerSheets.forEach(answerSheet -> answerSheet.setId(sequenceId.nextId()));
        mot.insertAll(answerSheets);
    }
}
