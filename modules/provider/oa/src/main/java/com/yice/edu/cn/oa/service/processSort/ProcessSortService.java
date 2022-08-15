package com.yice.edu.cn.oa.service.processSort;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProcessSortService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ProcessSort findProcessSortById(String id) {
        return mot.findById(id,ProcessSort.class);
    }
    public void saveProcessSort(ProcessSort processSort) {
        processSort.setId(sequenceId.nextId());
        processSort.setCreateTime(DateUtil.now());
        mot.insert(processSort);
    }
    public List<ProcessSort> findProcessSortListByCondition(ProcessSort processSort) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ProcessSort.class)).find(MongoKit.buildMatchDocument(processSort));
        Pager pager = processSort.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ProcessSort> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ProcessSort.class, document)));
        return list;
    }
    public long findProcessSortCountByCondition(ProcessSort processSort) {
        return mot.getCollection(mot.getCollectionName(ProcessSort.class)).countDocuments(MongoKit.buildMatchDocument(processSort));
    }
    public ProcessSort findOneProcessSortByCondition(ProcessSort processSort) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ProcessSort.class)).find(MongoKit.buildMatchDocument(processSort));
       MongoKit.appendProjection(query,processSort.getPager());
       return mot.getConverter().read(ProcessSort.class,query.first());
    }

    public void updateProcessSort(ProcessSort processSort) {
        processSort.setCreateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(processSort.getId())), MongoKit.update(processSort,true),ProcessSort.class);
    }
    public void deleteProcessSort(String id) {
        mot.remove(query(where("id").is(id)),ProcessSort.class);
    }
    public void deleteProcessSortByCondition(ProcessSort processSort) {
        mot.getCollection(mot.getCollectionName(ProcessSort.class)).deleteMany(MongoKit.buildMatchDocument(processSort));
    }
    public void batchSaveProcessSort(List<ProcessSort> processSorts){
        processSorts.forEach(processSort -> processSort.setId(sequenceId.nextId()));
        mot.insertAll(processSorts);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<ProcessSort> getProcessSortList(String schoolId) {
/*        List<ProcessSort> list=  mot.aggregate(Aggregation.newAggregation(
                Aggregation.match(where("schoolId").is(schoolId).andOperator(new Criteria(""))),
                Aggregation.sort(Sort.Direction.ASC,"appNum"),
                Aggregation.sort(Sort.Direction.DESC,"createTime")
                ),
                mot.getCollectionName(ProcessSort.class),
                ProcessSort.class).getMappedResults();*/
        List<ProcessSort> list = new ArrayList<>();
        mot.getCollection(mot.getCollectionName(ProcessSort.class)).find(new Document("schoolId",schoolId))
                .sort(new Document("appNum",1).append("createTime",-1)).
                forEach((Block<Document>) document -> list.add(mot.getConverter().read(ProcessSort.class, document)));
        return list;

    }
}
