package com.yice.edu.cn.jy.service.sensitiveWord;

import com.mongodb.client.result.DeleteResult;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.sensitiveWord.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class SensitiveWordService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public SensitiveWord findSensitiveWordById(String id) {
        return mot.findOne(query(where("id").is(id)), SensitiveWord.class);
    }
    public void saveSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWord.setCreateTime(DateUtil.now());
        mot.insert(sensitiveWord);
    }
    public List<SensitiveWord> findSensitiveWordListByCondition(SensitiveWord sensitiveWord) {
        Example<SensitiveWord> example = Example.of(sensitiveWord, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = sensitiveWord.getPager();
        if(pager!=null){
            if(!pager.getSort().equals(Sort.unsorted())){
                operations.add(Aggregation.sort(pager.getSort()));
            }
            if(pager.getIncludes()!=null){
                operations.add(Aggregation.project(pager.getIncludes()));
            }
            if(pager.getPaging()){
                operations.add(Aggregation.skip(pager.getBeginRow()));
                operations.add(Aggregation.limit(pager.getPageSize()));
            }
        }
        return mot.aggregate(Aggregation.newAggregation(SensitiveWord.class,operations),SensitiveWord.class).getMappedResults();
    }
    public SensitiveWord findOneSensitiveWordByCondition(SensitiveWord sensitiveWord) {
        Example<SensitiveWord> example = Example.of(sensitiveWord, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = sensitiveWord.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SensitiveWord.class,operations),SensitiveWord.class).getUniqueMappedResult();
    }
    public long findSensitiveWordCountByCondition(SensitiveWord sensitiveWord) {
        Example<SensitiveWord> example = Example.of(sensitiveWord, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, SensitiveWord.class);
    }
    public void updateSensitiveWord(SensitiveWord sensitiveWord) {
        sensitiveWord.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(sensitiveWord.getId())), MongoKit.update(sensitiveWord),SensitiveWord.class);
    }
    public void deleteSensitiveWord(String id) {
        mot.remove(query(where("id").is(id)),SensitiveWord.class);
    }
    public void deleteSensitiveWordByCondition(SensitiveWord sensitiveWord) {
        Example<SensitiveWord> example = Example.of(sensitiveWord, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SensitiveWord.class);
    }

    public String replaceSensitive(String text) {
        TypedAggregation<SensitiveWord> sensitiveWordType = Aggregation.newAggregation(SensitiveWord.class, Aggregation.project("name"));
        List<SensitiveWord> sensitiveWords = mot.aggregate(sensitiveWordType, SensitiveWord.class).getMappedResults();
        for (SensitiveWord sensitiveWord : sensitiveWords) {
            text=text.replaceAll(sensitiveWord.getName(),"**");
        }
        return text;
    }

    public boolean hasSensitiveWord(String text) {
        TypedAggregation<SensitiveWord> sensitiveWordType = Aggregation.newAggregation(SensitiveWord.class, Aggregation.project("name"));
        List<SensitiveWord> sensitiveWords = mot.aggregate(sensitiveWordType, SensitiveWord.class).getMappedResults();
        for (SensitiveWord sensitiveWord : sensitiveWords) {
            if(text.contains(sensitiveWord.getName())){
                return true;
            }
        }
        return false;
    }


}
