package com.yice.edu.cn.jw.service.qusSurvey;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class QusSurveyOptionService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public QusSurveyOption findQusSurveyOptionById(String id) {
        return mot.findById(id,QusSurveyOption.class);
    }
    public void saveQusSurveyOption(QusSurveyOption qusSurveyOption) {
        qusSurveyOption.setId(sequenceId.nextId());
        qusSurveyOption.setCreateTime(DateUtil.now());
        mot.insert(qusSurveyOption);
    }
    public List<QusSurveyOption> findQusSurveyOptionListByCondition(QusSurveyOption qusSurveyOption) {
        Example<QusSurveyOption> example = Example.of(qusSurveyOption, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(qusSurveyOption.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveyOption.class,operations),QusSurveyOption.class).getMappedResults();
    }

    public QusSurveyOption findOneQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption) {
        Example<QusSurveyOption> example = Example.of(qusSurveyOption, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurveyOption.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurveyOption.class,operations),QusSurveyOption.class).getUniqueMappedResult();
    }
    public long findQusSurveyOptionCountByCondition(QusSurveyOption qusSurveyOption) {
        Example<QusSurveyOption> example = Example.of(qusSurveyOption, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, QusSurveyOption.class);
    }
    public void updateQusSurveyOption(QusSurveyOption qusSurveyOption) {
        mot.updateFirst(query(where("id").is(qusSurveyOption.getId())), MongoKit.update(qusSurveyOption),QusSurveyOption.class);
    }
    public void deleteQusSurveyOption(String id) {
        mot.remove(query(where("id").is(id)),QusSurveyOption.class);
    }
    public void deleteQusSurveyOptionByCondition(QusSurveyOption qusSurveyOption) {
        Example<QusSurveyOption> example = Example.of(qusSurveyOption, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, QusSurveyOption.class);
    }
    public void batchSaveQusSurveyOption(List<QusSurveyOption> qusSurveyOptions){
        qusSurveyOptions.forEach(qusSurveyOption -> qusSurveyOption.setId(sequenceId.nextId()));
        mot.insertAll(qusSurveyOptions);
    }

    /**
     * 获取选项次数统计的结果集
     * @param qusSurveyOption
     * @return
     */
    public List<QusSurveyOption> getOptionCountList(QusSurveyOption qusSurveyOption) {
        Example<QusSurveyOption> example = Example.of(qusSurveyOption, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        operations.add(Aggregation.group("optionId").count().as("optionCount"));
        MongoKit.sortPageInclude(qusSurveyOption.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveyOption.class,operations),QusSurveyOption.class).getMappedResults();
    }

}
