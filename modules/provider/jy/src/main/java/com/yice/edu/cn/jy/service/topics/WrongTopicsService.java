package com.yice.edu.cn.jy.service.topics;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class WrongTopicsService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public WrongTopics findWrongTopicsById(String id) {
        return mot.findOne(query(where("id").is(id)), WrongTopics.class);
    }
    public void saveWrongTopics(WrongTopics wrongTopics) {
        wrongTopics.setAnswerTime(DateUtil.now());
        wrongTopics.setId(sequenceId.nextId());
        mot.insert(wrongTopics);
    }
    public List<WrongTopics> findWrongTopicsListByCondition(WrongTopics wrongTopics) {
        Example<WrongTopics> example = Example.of(wrongTopics, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        if(wrongTopics.getPager()!=null&&wrongTopics.getPager().getPaging()){
            query.with(wrongTopics.getPager());
        }
        return mot.find(query, WrongTopics.class);
    }
    public WrongTopics findOneWrongTopicsByCondition(WrongTopics wrongTopics) {
        Example<WrongTopics> example = Example.of(wrongTopics, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        return mot.findOne(query, WrongTopics.class);
    }
    public long findWrongTopicsCountByCondition(WrongTopics wrongTopics) {
        Example<WrongTopics> example = Example.of(wrongTopics, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        return mot.count(query, WrongTopics.class);
    }
    public void updateWrongTopics(WrongTopics wrongTopics) {
        mot.updateFirst(query(where("id").is(wrongTopics.getId())), MongoKit.update(wrongTopics),WrongTopics.class);
    }
    public void deleteWrongTopics(String id) {
        mot.remove(query(where("id").is(id)),WrongTopics.class);
    }
    public void deleteWrongTopicsByCondition(WrongTopics wrongTopics) {
        Example<WrongTopics> example = Example.of(wrongTopics, UntypedExampleMatcher.matchingAll().withStringMatcher(UntypedExampleMatcher.StringMatcher.EXACT));
        Query query = query(new Criteria().alike(example));
        mot.remove(query, WrongTopics.class);
    }
    public void batchSaveWrongTopics(List<WrongTopics> wrongTopicss){
        mot.insertAll(wrongTopicss);
    }
}
