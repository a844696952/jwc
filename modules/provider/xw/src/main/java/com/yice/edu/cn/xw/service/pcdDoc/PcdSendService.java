package com.yice.edu.cn.xw.service.pcdDoc;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRead;
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
public class PcdSendService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PcdDocService pcdDocService;
    @Autowired
    private PcdDocRevertService pcdDocRevertService;
    @Autowired
    private PcdDocReadService pcdDocReadService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdSend findPcdSendById(String id) {
        return mot.findById(id,PcdSend.class);
    }
    public void savePcdSend(PcdSend pcdSend) {
        pcdSend.setCreateTime(DateUtil.now());
        pcdSend.setId(sequenceId.nextId());
        mot.insert(pcdSend);
    }
    public List<PcdSend> findPcdSendListByCondition(PcdSend pcdSend) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdSend.class)).find(MongoKit.buildMatchDocument(pcdSend));
        Pager pager = pcdSend.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdSend> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdSend.class, document)));
        return list;
    }
    public long findPcdSendCountByCondition(PcdSend pcdSend) {
        return mot.getCollection(mot.getCollectionName(PcdSend.class)).countDocuments(MongoKit.buildMatchDocument(pcdSend));
    }
    public PcdSend findOnePcdSendByCondition(PcdSend pcdSend) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdSend.class)).find(MongoKit.buildMatchDocument(pcdSend));
       MongoKit.appendProjection(query,pcdSend.getPager());
       return mot.getConverter().read(PcdSend.class,query.first());
    }

    public void updatePcdSend(PcdSend pcdSend) {
        pcdSend.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdSend.class)).updateOne(new Document("_id",pcdSend.getId()), MongoKit.buildUpdateDocument(pcdSend,false));
    }
    public void updatePcdSendForAll(PcdSend pcdSend) {
        pcdSend.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdSend.class)).updateOne(new Document("_id",pcdSend.getId()), MongoKit.buildUpdateDocument(pcdSend,true));
    }
    public void deletePcdSend(String id) {
        mot.getCollection(mot.getCollectionName(PcdSend.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdSendByCondition(PcdSend pcdSend) {
        mot.getCollection(mot.getCollectionName(PcdSend.class)).deleteMany(MongoKit.buildMatchDocument(pcdSend));
    }
    public void batchSavePcdSend(List<PcdSend> pcdSends){
        pcdSends.forEach(pcdSend -> pcdSend.setId(sequenceId.nextId()));
        mot.insertAll(pcdSends);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public PcdDoc findOnePcdSendByPcdSend(PcdDocRevert pcdDocRevert){

        String docId = pcdDocRevert.getDocId();
        //公文信息
        PcdDoc pcd = pcdDocService.findPcdDocById(docId);
        //插入一条读取公文记录
        Criteria criteria = Criteria.where("docId").is(docId).andOperator(Criteria.where("createUserId").is(pcdDocRevert.getCreateUserId()));
        Query query = new Query(criteria);
        PcdDocRead docRead = mot.findOne(query,PcdDocRead.class);
        if(docRead==null){
            docRead = new PcdDocRead();
            docRead.setCreateUserId(pcdDocRevert.getCreateUserId());
            docRead.setDocId(pcdDocRevert.getDocId());
            pcdDocReadService.savePcdDocRead(docRead);
        }

        //回复
        PcdDocRevert pcdDocReverts = pcdDocRevertService.findOnePcdDocRevertByCondition(pcdDocRevert);
        pcd.setPcdDocRevert(pcdDocReverts);
        return pcd;
    }

    public List<PcdSend> findPcdSendByListKong(PcdSend pcdSend){

        Document document = new Document();
        document.append("eehId",pcdSend.getEehId());
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getDocId()!=null){
            document.append("pcdDoc.docId",new Document("$regex",pcdSend.getPcdDoc().getDocId()).append("$options","i"));
        }
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getTitle()!=null){
            document.append("pcdDoc.title",new Document("$regex",pcdSend.getPcdDoc().getTitle()).append("$options","i"));
        }
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getDocNumber()!=null){
            document.append("pcdDoc.docNumber",new Document("$regex",pcdSend.getPcdDoc().getDocNumber()).append("$options","i"));
        }
        if(pcdSend.getSearchTimeZone()!=null&&pcdSend.getSearchTimeZone().length>0){
            document.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdSend.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdSend.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document match = new Document("$match",document);
        Document l1 = new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$docId")),new Document("$eq",Arrays.asList("$createUserId",pcdSend.getCreataUserId()))))));
        Document lookup = new Document("$lookup",new Document("from","pcdDocRead").append("let",new Document("docId","$docId")).append("pipeline",Arrays.asList(l1)).append("as","type"));
        Document size = new Document("$addFields",new Document("type",new Document("$size","$type")));
        List<Document> documentList = returnDocuments(pcdSend.getPager());
        List<Document> l = new ArrayList<>();
        l.add(match);
        l.add(lookup);
        l.add(size);
        documentList.stream().forEach(f->{
            l.add(f);
        });
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdSend").aggregate(l);
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdSend> pcdSends = new ArrayList<>();
        while (iterator.hasNext()){
            pcdSends.add(mot.getConverter().read(PcdSend.class,iterator.next()));
        }
        return pcdSends;
    }

    public long findPcdSendByCountKong(PcdSend pcdSend){
        Document document = new Document();
        document.append("eehId",pcdSend.getEehId());
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getDocId()!=null){
            document.append("pcdDoc.docId",new Document("$regex",pcdSend.getPcdDoc().getDocId()).append("$options","i"));
        }
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getTitle()!=null){
            document.append("pcdDoc.title",new Document("$regex",pcdSend.getPcdDoc().getTitle()).append("$options","i"));
        }
        if(pcdSend.getPcdDoc()!=null&&pcdSend.getPcdDoc().getDocNumber()!=null){
            document.append("pcdDoc.docNumber",new Document("$regex",pcdSend.getPcdDoc().getDocNumber()).append("$options","i"));
        }
        if(pcdSend.getSearchTimeZone()!=null&&pcdSend.getSearchTimeZone().length>0){
            document.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdSend.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdSend.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document match = new Document("$match",document);
        Document l1 = new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$docId","$$docId")),new Document("$eq",Arrays.asList("$createUserId",pcdSend.getCreataUserId()))))));
        Document lookup = new Document("$lookup",new Document("from","pcdDocRead").append("let",new Document("docId","$docId")).append("pipeline",Arrays.asList(l1)).append("as","type"));
        Document size = new Document("$addFields",new Document("type",new Document("$size","$type")));
        List<Document> l = new ArrayList<>();
        l.add(match);
        l.add(lookup);
        l.add(size);
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdSend").aggregate(l);
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdSend> pcdSends = new ArrayList<>();
        while (iterator.hasNext()){
            pcdSends.add(mot.getConverter().read(PcdSend.class,iterator.next()));
        }
        return pcdSends.size();
    }

    public List<Document> returnDocuments(Pager pager){
        Document sort = new Document("$sort",new Document(pager.getSortField(),pager.getSortOrder().equals("desc")?-1:1));
        Document skip  = new Document("$skip",(pager.getPage() - 1) * pager.getPageSize());
        Document limit = new Document("$limit",pager.getPageSize());
        return Arrays.asList(sort,skip,limit);
    }
}
