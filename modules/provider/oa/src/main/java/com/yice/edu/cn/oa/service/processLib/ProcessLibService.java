package com.yice.edu.cn.oa.service.processLib;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
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
public class ProcessLibService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public ProcessLib findProcessLibById(String id) {
        return mot.findOne(query(where("id").is(id)), ProcessLib.class);
    }
    public void saveProcessLib(ProcessLib processLib) {
        processLib.setId(sequenceId.nextId());
        mot.insert(processLib);
    }
    public List<ProcessLib> findProcessLibListByCondition(ProcessLib processLib) {
        Example<ProcessLib> example = Example.of(processLib, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(processLib.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(ProcessLib.class,operations),ProcessLib.class).getMappedResults();

    }
    public ProcessLib findOneProcessLibByCondition(ProcessLib processLib) {
        Example<ProcessLib> example = Example.of(processLib, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = processLib.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(ProcessLib.class,operations),ProcessLib.class).getUniqueMappedResult();
    }
    public long findProcessLibCountByCondition(ProcessLib processLib) {
        Example<ProcessLib> example = Example.of(processLib, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, ProcessLib.class);
    }
    public void updateProcessLib(ProcessLib processLib) {
        mot.updateFirst(query(where("id").is(processLib.getId())), MongoKit.update(processLib),ProcessLib.class);
    }
    public void deleteProcessLib(String id) {
        mot.remove(query(where("id").is(id)),ProcessLib.class);
    }
    public void deleteProcessLibByCondition(ProcessLib processLib) {
        Example<ProcessLib> example = Example.of(processLib, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, ProcessLib.class);
    }
    public void batchSaveProcessLib(List<ProcessLib> processLibs){
        processLibs.forEach(processLib -> processLib.setId(sequenceId.nextId()));
        mot.insertAll(processLibs);
    }
}
