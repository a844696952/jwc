package com.yice.edu.cn.xw.service.visitorManage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.AggregationOptions;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class
VisitorService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public Visitor findVisitorById(String id) {
        return mot.findById(id, Visitor.class);
    }

    public void creatQRCode(Visitor visitor) {
        visitor.setId(sequenceId.nextId());
        mot.insert(visitor);
    }

    public void saveVisitor(Visitor visitor) {
        visitor.setCreateTime(DateUtil.now());
        visitor.setId(sequenceId.nextId());
        mot.insert(visitor);
    }

    public List<Visitor> findVisitorListByCondition(Visitor visitor) {
        Document doc = new Document();
        visitor.setSearchTime(null);
        if (visitor.getStartTime() != null && visitor.getEndTime() != null) {
             /*doc.append("startTime",new Document("$gte", visitor.getStartTime()))
                    .append("$and",Arrays.asList(new Document("endTime",new Document("$lt", visitor.getEndTime()))));*/
            Optional.ofNullable(visitor.getStartTime()).ifPresent(s -> doc.append("startTime", new Document("$gte", visitor.getStartTime())));
            Optional.ofNullable(visitor.getEndTime()).ifPresent(s -> doc.append("$and", Arrays.asList(new Document("endTime", new Document("$lt", visitor.getEndTime())))));
            visitor.setStartTime(null);
            visitor.setEndTime(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(visitor));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Visitor.class)).find(doc);
        Pager pager = visitor.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<Visitor> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(Visitor.class, document)));
        return list;
    }

    public long findVisitorCountByCondition(Visitor visitor) {
        Document doc = new Document();
        visitor.setSearchTime(null);
        if (visitor.getStartTime() != null && visitor.getEndTime() != null) {
             /*doc.append("startTime",new Document("$gte", visitor.getStartTime()))
                    .append("$and",Arrays.asList(new Document("endTime",new Document("$lt", visitor.getEndTime()))));*/
            Optional.ofNullable(visitor.getStartTime()).ifPresent(s -> doc.append("startTime", new Document("$gte", visitor.getStartTime())));
            Optional.ofNullable(visitor.getEndTime()).ifPresent(s -> doc.append("$and", Arrays.asList(new Document("endTime", new Document("$lt", visitor.getEndTime())))));
            visitor.setStartTime(null);
            visitor.setEndTime(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(visitor));
        return mot.getCollection(mot.getCollectionName(Visitor.class)).countDocuments(doc);
    }

    public Visitor findOneVisitorByCondition(Visitor visitor) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Visitor.class)).find(MongoKit.buildMatchDocument(visitor));
        MongoKit.appendProjection(query, visitor.getPager());
        return mot.getConverter().read(Visitor.class, query.first());
    }

    public void updateVisitor(Visitor visitor) {
        visitor.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(visitor.getId())), MongoKit.update(visitor), Visitor.class);
    }

    public void deleteVisitor(String id) {
        mot.remove(query(where("id").is(id)), Visitor.class);
    }

    public void deleteVisitorByCondition(Visitor visitor) {
        mot.getCollection(mot.getCollectionName(Visitor.class)).deleteMany(MongoKit.buildMatchDocument(visitor));
    }

    public void batchSaveVisitor(List<Visitor> visitors) {
        visitors.forEach(visitor -> visitor.setId(sequenceId.nextId()));
        mot.insertAll(visitors);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
