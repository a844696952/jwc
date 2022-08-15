package com.yice.edu.cn.xw.service.pcdDoc;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PcdDocRevertService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PcdSendService pcdSendService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdDocRevert findPcdDocRevertById(String id) {
        return mot.findById(id,PcdDocRevert.class);
    }
    public void savePcdDocRevert(PcdDocRevert pcdDocRevert) {
        pcdDocRevert.setCreateTime(DateUtil.now());
        pcdDocRevert.setId(sequenceId.nextId());
        mot.insert(pcdDocRevert);
    }
    public List<PcdDocRevert> findPcdDocRevertListByCondition(PcdDocRevert pcdDocRevert) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).find(MongoKit.buildMatchDocument(pcdDocRevert));
        Pager pager = pcdDocRevert.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdDocRevert> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdDocRevert.class, document)));
        return list;
    }
    public long findPcdDocRevertCountByCondition(PcdDocRevert pcdDocRevert) {
        return mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).countDocuments(MongoKit.buildMatchDocument(pcdDocRevert));
    }
    public PcdDocRevert findOnePcdDocRevertByCondition(PcdDocRevert pcdDocRevert) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).find(MongoKit.buildMatchDocument(pcdDocRevert));
       MongoKit.appendProjection(query,pcdDocRevert.getPager());
       return mot.getConverter().read(PcdDocRevert.class,query.first());
    }

    public void updatePcdDocRevert(PcdDocRevert pcdDocRevert) {
        pcdDocRevert.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).updateOne(new Document("_id",pcdDocRevert.getId()), MongoKit.buildUpdateDocument(pcdDocRevert,false));
    }
    public void updatePcdDocRevertForAll(PcdDocRevert pcdDocRevert) {
        pcdDocRevert.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).updateOne(new Document("_id",pcdDocRevert.getId()), MongoKit.buildUpdateDocument(pcdDocRevert,true));
    }
    public void deletePcdDocRevert(String id) {
        mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdDocRevertByCondition(PcdDocRevert pcdDocRevert) {
        mot.getCollection(mot.getCollectionName(PcdDocRevert.class)).deleteMany(MongoKit.buildMatchDocument(pcdDocRevert));
    }
    public void batchSavePcdDocRevert(List<PcdDocRevert> pcdDocReverts){
        pcdDocReverts.forEach(pcdDocRevert -> pcdDocRevert.setId(sequenceId.nextId()));
        mot.insertAll(pcdDocReverts);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePcdDocRevertKong(PcdDocRevert revert){
        revert.setId(sequenceId.nextId());
        revert.setType(0);
        revert.setCreateTime(DateUtil.now());
        mot.insert(revert,"pcdDocRevert");
    }

    public List<PcdDocRevert> findAndUpdatePcdDocRevertById(PcdDocRevert pcdDocRevert){
        Criteria criteria = Criteria.where("docId").is(pcdDocRevert.getDocId()).andOperator(Criteria.where("eehName").is(pcdDocRevert.getEehName()));
        Query query1 = new Query(criteria);
        Update update = new Update();
        update.set("type",1);
        mot.updateMulti(query1,update,PcdDocRevert.class);
        List<PcdDocRevert> pcdDocReverts = findPcdDocRevertListByCondition(pcdDocRevert);
        return pcdDocReverts;
    }

    public List<PcdDocRevert> findPcdDocRevertByPcdDocRevertListKong(PcdDocRevert pcdDocRevert){
        Document document = new Document();
        document.append("docId",pcdDocRevert.getDocId());
        Document document2 = new Document();
        if(pcdDocRevert.getEehName()!=null){
            document.append("eehName",new Document("$regex",pcdDocRevert.getEehName()).append("$options","i"));
        }
        Document match1 = new Document("$match",document);

        if(pcdDocRevert.getSearchTimeZone()!=null&&pcdDocRevert.getSearchTimeZone().length>0){
            document2.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdDocRevert.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdDocRevert.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document group = new Document("$group",new Document("_id","$eehName").append("eehName",new Document("$first","$eehName")).append("createTime",new Document("$last","$createTime")).append("size",new Document("$sum",1)).append("type",new Document("$sum","$type")));
        Document match2 = new Document("$match",document2);

        List<Document> list = pcdSendService.returnDocuments(pcdDocRevert.getPager());
        List<Document> documentList = new ArrayList<>();
        documentList.add(match1);
        documentList.add(group);
        documentList.add(match2);
        documentList.addAll(list);
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdDocRevert").aggregate(documentList);
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdDocRevert> pcdDocReverts = new ArrayList<>();
        while (iterator.hasNext()){
            pcdDocReverts.add(mot.getConverter().read(PcdDocRevert.class,iterator.next()));
        }
        return pcdDocReverts;
    }

    public long findPcdDocRevertByPcdDocRevertLongKong(PcdDocRevert pcdDocRevert){
        Document document = new Document();
        document.append("docId",pcdDocRevert.getDocId());
        Document document2 = new Document();
        if(pcdDocRevert.getEehName()!=null){
            document.append("eehName",new Document("$regex",pcdDocRevert.getEehName()).append("$options","i"));
        }
        Document match1 = new Document("$match",document);

        if(pcdDocRevert.getSearchTimeZone()!=null&&pcdDocRevert.getSearchTimeZone().length>0){
            document2.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdDocRevert.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdDocRevert.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document group = new Document("$group",new Document("_id","$eehName").append("eehName",new Document("$first","$eehName")).append("createTime",new Document("$last","$createTime")).append("size",new Document("$sum",1)).append("type",new Document("$sum","$type")));
        Document match2 = new Document("$match",document2);

        List<Document> documentList = new ArrayList<>();
        documentList.add(match1);
        documentList.add(group);
        documentList.add(match2);
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdDocRevert").aggregate(documentList);
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdDocRevert> pcdDocReverts = new ArrayList<>();
        while (iterator.hasNext()){
            pcdDocReverts.add(mot.getConverter().read(PcdDocRevert.class,iterator.next()));
        }
        return pcdDocReverts.size();
    }
}
