package com.yice.edu.cn.oa.service.processCopy;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProcessCopyService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public ProcessCopy findProcessCopyById(String id) {
        return mot.findById(id,ProcessCopy.class);
    }
    public void saveProcessCopy(ProcessCopy processCopy) {
        processCopy.setCreateTime(DateUtil.now());
        processCopy.setId(sequenceId.nextId());
        mot.insert(processCopy);
    }
    public List<ProcessCopyVo> findProcessCopyListByCondition(ProcessCopy processCopy) {
        Example<ProcessCopy> example = Example.of(processCopy, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        final Criteria criteria = new Criteria().alike(example);
        final String[] rangeTime = processCopy.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(processCopy.getPager(),operations);
        operations.add(Aggregation.lookup(
                mot.getCollectionName(ProcessBusinessData.class),
                "processBusinessDataId",
                "_id",
                "processBusinessData"
        ));
       return mot.aggregate(Aggregation.newAggregation(ProcessCopy.class,operations), ProcessCopyVo.class).getMappedResults();
    }
    public ProcessCopy findOneProcessCopyByCondition(ProcessCopy processCopy) {
        Example<ProcessCopy> example = Example.of(processCopy, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = processCopy.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(ProcessCopy.class,operations),ProcessCopy.class).getUniqueMappedResult();
    }
    public long findProcessCopyCountByCondition(ProcessCopy processCopy) {
        Example<ProcessCopy> example = Example.of(processCopy, UntypedExampleMatcher.matchingAll());
        Criteria criteria = new Criteria().alike(example);
        final String[] rangeTime = processCopy.getRangeTime();
        if(rangeTime !=null&&rangeTime.length==2){
            criteria.andOperator(new Criteria("createTime").gte(rangeTime[0]),new Criteria("createTime").lte(rangeTime[1]));
        }
        Query query = query(criteria);
        return mot.count(query, ProcessCopy.class);
    }
    public void updateProcessCopy(ProcessCopy processCopy) {
        mot.updateFirst(query(where("id").is(processCopy.getId())), MongoKit.update(processCopy),ProcessCopy.class);
    }
    public void deleteProcessCopy(String id) {
        mot.remove(query(where("id").is(id)),ProcessCopy.class);
    }
    public void deleteProcessCopyByCondition(ProcessCopy processCopy) {
        Example<ProcessCopy> example = Example.of(processCopy, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, ProcessCopy.class);
    }
    public void batchSaveProcessCopy(List<ProcessCopy> processCopys){
        processCopys.forEach(processCopy -> processCopy.setId(sequenceId.nextId()));
        mot.insertAll(processCopys);
    }
    public void batchLookProcessCopyByIds(String[] ids){
       mot.updateMulti(query(where("id").in(Arrays.asList(ids)).and("looked").is(false)),new Update().set("looked",true),ProcessCopy.class);
    }
}
