package com.yice.edu.cn.jw.service.pen;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperShare;
import com.yice.edu.cn.common.pojo.jw.pen.Pen;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PenService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public Pen findPenById(String id) {
        return mot.findById(id,Pen.class);
    }
    public void savePen(Pen pen) {
        pen.setCreateTime(DateUtil.now());
        pen.setId(sequenceId.nextId());
        mot.insert(pen);
    }
    public List<Pen> findPenListByCondition(Pen pen) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Pen.class)).find(MongoKit.buildMatchDocument(pen));
        Pager pager = pen.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<Pen> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(Pen.class, document)));
        return list;
    }
    public long findPenCountByCondition(Pen pen) {
        return mot.getCollection(mot.getCollectionName(Pen.class)).countDocuments(MongoKit.buildMatchDocument(pen));
    }
    public Pen findOnePenByCondition(Pen pen) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Pen.class)).find(MongoKit.buildMatchDocument(pen));
       MongoKit.appendProjection(query,pen.getPager());
       return mot.getConverter().read(Pen.class,query.first());
    }

    public void updatePen(Pen pen) {
        pen.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(Pen.class)).updateOne(new Document("_id",pen.getId()), MongoKit.buildUpdateDocument(pen,false));
//        mot.updateFirst(query(where("id").is(pen.getId())), MongoKit.update(pen,false),Pen.class);
    }
    public void updatePenForAll(Pen pen) {
        pen.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(Pen.class)).updateOne(new Document("_id",pen.getId()), MongoKit.buildUpdateDocument(pen,true));
//        mot.updateFirst(query(where("id").is(pen.getId())), MongoKit.update(pen,true),Pen.class);
    }
    public void deletePen(String id) {
        mot.getCollection(mot.getCollectionName(Pen.class)).deleteOne(new Document("_id",id));
//        mot.remove(query(where("id").is(id)),Pen.class);
    }
    public void deletePenByCondition(Pen pen) {
        mot.getCollection(mot.getCollectionName(Pen.class)).deleteMany(MongoKit.buildMatchDocument(pen));
    }
    public void batchSavePen(List<Pen> pens){
        pens.forEach(pen -> pen.setId(sequenceId.nextId()));
        mot.insertAll(pens);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
