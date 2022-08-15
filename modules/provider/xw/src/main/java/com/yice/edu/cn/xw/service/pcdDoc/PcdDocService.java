package com.yice.edu.cn.xw.service.pcdDoc;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PcdDocService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private PcdSendService pcdSendService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdDoc findPcdDocById(String id) {
        return mot.findById(id,PcdDoc.class);
    }
    public void savePcdDoc(PcdDoc pcdDoc) {
        pcdDoc.setCreateTime(DateUtil.now());
        pcdDoc.setId(sequenceId.nextId());
        mot.insert(pcdDoc);
    }
    public List<PcdDoc> findPcdDocListByCondition(PcdDoc pcdDoc) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDoc.class)).find(MongoKit.buildMatchDocument(pcdDoc));
        Pager pager = pcdDoc.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdDoc> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdDoc.class, document)));
        return list;
    }
    public long findPcdDocCountByCondition(PcdDoc pcdDoc) {
        return mot.getCollection(mot.getCollectionName(PcdDoc.class)).countDocuments(MongoKit.buildMatchDocument(pcdDoc));
    }
    public PcdDoc findOnePcdDocByCondition(PcdDoc pcdDoc) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDoc.class)).find(MongoKit.buildMatchDocument(pcdDoc));
       MongoKit.appendProjection(query,pcdDoc.getPager());
       return mot.getConverter().read(PcdDoc.class,query.first());
    }

    public void updatePcdDoc(PcdDoc pcdDoc) {
        pcdDoc.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDoc.class)).updateOne(new Document("_id",pcdDoc.getId()), MongoKit.buildUpdateDocument(pcdDoc,false));
    }
    public void updatePcdDocForAll(PcdDoc pcdDoc) {
        pcdDoc.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDoc.class)).updateOne(new Document("_id",pcdDoc.getId()), MongoKit.buildUpdateDocument(pcdDoc,true));
    }
    public void deletePcdDoc(String id) {
        mot.getCollection(mot.getCollectionName(PcdDoc.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdDocByCondition(PcdDoc pcdDoc) {
        mot.getCollection(mot.getCollectionName(PcdDoc.class)).deleteMany(MongoKit.buildMatchDocument(pcdDoc));
    }
    public void batchSavePcdDoc(List<PcdDoc> pcdDocs){
        pcdDocs.forEach(pcdDoc -> pcdDoc.setId(sequenceId.nextId()));
        mot.insertAll(pcdDocs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePcdDocKong(PcdDoc pcdDoc){
        String id = sequenceId.nextId();
        pcdDoc.setId(id);
        pcdDoc.setDocId(id);
        pcdDoc.setTransmit(0);
        List<PcdSend> sends = pcdDoc.getPcdSendList();
        PcdDoc pcdDoc1 = new PcdDoc();
        BeanUtil.copyProperties(pcdDoc,pcdDoc1);
        pcdDoc1.setFileList(null);
        pcdDoc1.setPcdSendList(null);
        String createTime = DateUtil.now();
        sends.stream().forEach(f->{
            f.setEehId(f.getId());
            f.setDocId(pcdDoc.getId());
//            f.setRevert(0);
            f.setType(0);
            f.setPcdDoc(pcdDoc1);
            f.setCreateTime(createTime);
        });
        int count = sends.size();
        pcdDoc.setRead(0);
        pcdDoc.setUnRead(count);
        pcdDoc.setCount(count);
        pcdDoc.setCreateTime(createTime);

        mot.insert(pcdDoc,"pcdDoc");
        pcdSendService.batchSavePcdSend(sends);
    }

    public List<PcdDoc> findListPcdDocByPcdDocKong(PcdDoc pcdDoc){
        Document document = new Document();
        document.append("transmitId",pcdDoc.getTransmitId());
        if(pcdDoc.getDocId()!=null){
            document.append("docId",new Document("$regex",pcdDoc.getDocId()).append("$options","i"));
        }
        if(pcdDoc.getDocNumber()!=null){
            document.append("docNumber",new Document("$regex",pcdDoc.getDocNumber()).append("$options","i"));
        }
        if(pcdDoc.getTitle()!=null){
            document.append("title",new Document("$regex",pcdDoc.getTitle()).append("$options","i"));
        }
        if(pcdDoc.getSearchTimeZone()!=null&&pcdDoc.getSearchTimeZone().length>0){
            document.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdDoc.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdDoc.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document match = new Document("$match",document);
        Document lookUp = new Document("$lookup",new Document("from","pcdDocRevert").append("localField","_id").append("foreignField","docId").append("as","revert"));
        Document size = new Document("$addFields",new Document("size",new Document("$size","$revert")));
        Document sort = new Document("$sort",new Document(pcdDoc.getPager().getSortField(),pcdDoc.getPager().getSortOrder().equals("desc")?-1:1));
        Document skip  = new Document("$skip",(pcdDoc.getPager().getPage() - 1) * pcdDoc.getPager().getPageSize());
        Document limit = new Document("$limit",pcdDoc.getPager().getPageSize());
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdDoc").aggregate(Arrays.asList(match,lookUp,size,sort,skip,limit));
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdDoc> pcdDocs = new ArrayList<>();
        while (iterator.hasNext()){
            pcdDocs.add(mot.getConverter().read(PcdDoc.class,iterator.next()));
        }
        return pcdDocs;
    }

    public long findCountPcdDocByPcdDocKong(PcdDoc pcdDoc){
        Document document = new Document();
        document.append("transmitId",pcdDoc.getTransmitId());
        if(pcdDoc.getDocId()!=null){
            document.append("docId",new Document("$regex",pcdDoc.getDocId()).append("$options","i"));
        }
        if(pcdDoc.getDocNumber()!=null){
            document.append("docNumber",new Document("$regex",pcdDoc.getDocNumber()).append("$options","i"));
        }
        if(pcdDoc.getTitle()!=null){
            document.append("title",new Document("$regex",pcdDoc.getTitle()).append("$options","i"));
        }
        if(pcdDoc.getSearchTimeZone()!=null&&pcdDoc.getSearchTimeZone().length>0){
            document.append("$and",Arrays.asList(new Document("createTime",new Document("$gte",pcdDoc.getSearchTimeZone()[0])),new Document("createTime",new Document("$lte",pcdDoc.getSearchTimeZone()[1]+" 23:59:59"))));
        }
        Document match = new Document("$match",document);
        AggregateIterable<Document> aggregateIterable = mot.getCollection("pcdDoc").aggregate(Arrays.asList(match));
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        List<PcdDoc> pcdDocs = new ArrayList<>();
        while (iterator.hasNext()){
            pcdDocs.add(mot.getConverter().read(PcdDoc.class,iterator.next()));
        }
        return pcdDocs.size();
    }

    public void saveForwardDoc(PcdDoc pcdDoc){
        PcdDoc pcdDocById = this.findPcdDocById(pcdDoc.getId());
        pcdDocById.setId(sequenceId.nextId());
//        pcdDocById.setDocId(pcdDoc.getDocId());
        pcdDocById.setTransmit(1);
        pcdDocById.setInform(pcdDoc.getInform());
        pcdDocById.setInformName(pcdDoc.getInformName());
        pcdDocById.setEehId(pcdDoc.getEehId());
        pcdDocById.setPcdSendList(null);
        pcdDocById.setTransmitId(pcdDoc.getTransmitId());
        pcdDocById.setTransmitName(pcdDoc.getTransmitName());
        List<PcdSend> sends = pcdDoc.getPcdSendList();
        String createTime = DateUtil.now();
        sends.stream().forEach(f->{
            f.setEehId(f.getId());
            f.setDocId(pcdDocById.getId());
//            f.setRevert(0);
            f.setType(0);
            f.setPcdDoc(pcdDocById);
            f.setCreateTime(createTime);
        });
        int count = sends.size();
        pcdDocById.setRead(0);
        pcdDocById.setUnRead(count);
        pcdDocById.setCount(count);
        pcdDocById.setCreateTime(createTime);

        mot.insert(pcdDocById);
        pcdSendService.batchSavePcdSend(sends);
    }

}
