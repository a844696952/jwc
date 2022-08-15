package com.yice.edu.cn.jw.service.stuEvaluate;

import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.*;
import org.bson.Document;
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
public class StuEvaluateSendObjectService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public StuEvaluateSendObject findStuEvaluateSendObjectById(String id) {
        return mot.findById(id,StuEvaluateSendObject.class);
    }
    public void saveStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject) {
        stuEvaluateSendObject.setId(sequenceId.nextId());
        mot.insert(stuEvaluateSendObject);
    }
    public List<StuEvaluateSendObject> findStuEvaluateSendObjectListByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        Document doc=new Document();
        if(StrUtil.isNotEmpty(stuEvaluateSendObject.getState())&&stuEvaluateSendObject.getState().equals("1")){
            doc.append("stuEvaluate.endTime",new Document("$gte",DateUtil.formatDateTime(DateUtil.date())));
        }else if(StrUtil.isNotEmpty(stuEvaluateSendObject.getState())&&stuEvaluateSendObject.getState().equals("2")){
            doc.append("stuEvaluate.endTime",new Document("$lte",DateUtil.formatDateTime(DateUtil.date())));
        }
        doc.putAll(MongoKit.buildMatchDocument(stuEvaluateSendObject));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuEvaluateSendObject.class)).find(doc);
        Pager pager = stuEvaluateSendObject.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StuEvaluateSendObject> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuEvaluateSendObject.class, document)));
        return list;
    }
    public long findStuEvaluateSendObjectCountByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        Document doc=new Document();
        if(StrUtil.isNotEmpty(stuEvaluateSendObject.getState())&&stuEvaluateSendObject.getState().equals("1")){
            doc.append("stuEvaluate.endTime",new Document("$gte",DateUtil.formatDateTime(DateUtil.date())));
        }else if(StrUtil.isNotEmpty(stuEvaluateSendObject.getState())&&stuEvaluateSendObject.getState().equals("2")){
            doc.append("stuEvaluate.endTime",new Document("$lte",DateUtil.formatDateTime(DateUtil.date())));
        }
        doc.putAll(MongoKit.buildMatchDocument(stuEvaluateSendObject));
        return mot.getCollection(mot.getCollectionName(StuEvaluateSendObject.class)).countDocuments(doc);
    }
    public StuEvaluateSendObject findOneStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        Example<StuEvaluateSendObject> example = Example.of(stuEvaluateSendObject, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = stuEvaluateSendObject.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateSendObject.class,operations),StuEvaluateSendObject.class).getUniqueMappedResult();
    }

    public void updateStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject) {
        mot.updateFirst(query(where("id").is(stuEvaluateSendObject.getId())), MongoKit.update(stuEvaluateSendObject),StuEvaluateSendObject.class);
    }
    public void deleteStuEvaluateSendObject(String id) {
        mot.remove(query(where("id").is(id)),StuEvaluateSendObject.class);
    }
    public void deleteStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject) {
        Example<StuEvaluateSendObject> example = Example.of(stuEvaluateSendObject, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, StuEvaluateSendObject.class);
    }
    public void batchSaveStuEvaluateSendObject(List<StuEvaluateSendObject> stuEvaluateSendObjects){
        stuEvaluateSendObjects.forEach(stuEvaluateSendObject -> stuEvaluateSendObject.setId(sequenceId.nextId()));
        mot.insertAll(stuEvaluateSendObjects);
    }



    public List<StuEvaluateSendObjectHistory> findStuEvaluateSendObjectListByClassId(List<String>classIdList) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        cl.add(new Criteria("classId").in(classIdList));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateSendObject.class,operations),StuEvaluateSendObjectHistory.class).getMappedResults();

    }

    public void batchSaveStuEvaluateSendObjectHistory(List<StuEvaluateSendObjectHistory> stuEvaluateSendObjectHistories){
        mot.insertAll(stuEvaluateSendObjectHistories);
    }

}
