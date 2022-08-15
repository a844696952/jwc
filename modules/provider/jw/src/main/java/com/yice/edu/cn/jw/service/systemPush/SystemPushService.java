package com.yice.edu.cn.jw.service.systemPush;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.systemPush.SystemPush;
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
public class SystemPushService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public SystemPush findSystemPushById(String id) {
        return mot.findOne(query(where("id").is(id)), SystemPush.class);
    }
    public void saveSystemPush(SystemPush systemPush) {
        systemPush.setCreateTime(DateUtil.now());
        mot.insert(systemPush);
    }
    public List<SystemPush> findSystemPushListByCondition(SystemPush systemPush) {
        Example<SystemPush> example = Example.of(systemPush, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(systemPush.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SystemPush.class,operations),SystemPush.class).getMappedResults();

    }
    public SystemPush findOneSystemPushByCondition(SystemPush systemPush) {
        Example<SystemPush> example = Example.of(systemPush, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = systemPush.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SystemPush.class,operations),SystemPush.class).getUniqueMappedResult();
    }
    public long findSystemPushCountByCondition(SystemPush systemPush) {
        Example<SystemPush> example = Example.of(systemPush, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, SystemPush.class);
    }
    public void updateSystemPush(SystemPush systemPush) {
        mot.updateFirst(query(where("id").is(systemPush.getId())), MongoKit.update(systemPush),SystemPush.class);
    }
    public void deleteSystemPush(String id) {
        mot.remove(query(where("id").is(id)),SystemPush.class);
    }
    public void deleteSystemPushByCondition(SystemPush systemPush) {
        Example<SystemPush> example = Example.of(systemPush, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SystemPush.class);
    }
    public void batchSaveSystemPush(List<SystemPush> systemPushs){
        mot.insertAll(systemPushs);
    }
}
