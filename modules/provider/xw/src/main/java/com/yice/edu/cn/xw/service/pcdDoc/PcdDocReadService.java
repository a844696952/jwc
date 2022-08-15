package com.yice.edu.cn.xw.service.pcdDoc;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRead;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PcdDocReadService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdDocRead findPcdDocReadById(String id) {
        return mot.findById(id,PcdDocRead.class);
    }
    public void savePcdDocRead(PcdDocRead pcdDocRead) {
        pcdDocRead.setCreateTime(DateUtil.now());
        pcdDocRead.setId(sequenceId.nextId());
        mot.insert(pcdDocRead);
    }
    public List<PcdDocRead> findPcdDocReadListByCondition(PcdDocRead pcdDocRead) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDocRead.class)).find(MongoKit.buildMatchDocument(pcdDocRead));
        Pager pager = pcdDocRead.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdDocRead> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdDocRead.class, document)));
        return list;
    }
    public long findPcdDocReadCountByCondition(PcdDocRead pcdDocRead) {
        return mot.getCollection(mot.getCollectionName(PcdDocRead.class)).countDocuments(MongoKit.buildMatchDocument(pcdDocRead));
    }
    public PcdDocRead findOnePcdDocReadByCondition(PcdDocRead pcdDocRead) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdDocRead.class)).find(MongoKit.buildMatchDocument(pcdDocRead));
       MongoKit.appendProjection(query,pcdDocRead.getPager());
       return mot.getConverter().read(PcdDocRead.class,query.first());
    }

    public void updatePcdDocRead(PcdDocRead pcdDocRead) {
        pcdDocRead.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDocRead.class)).updateOne(new Document("_id",pcdDocRead.getId()), MongoKit.buildUpdateDocument(pcdDocRead,false));
    }
    public void updatePcdDocReadForAll(PcdDocRead pcdDocRead) {
        pcdDocRead.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdDocRead.class)).updateOne(new Document("_id",pcdDocRead.getId()), MongoKit.buildUpdateDocument(pcdDocRead,true));
    }
    public void deletePcdDocRead(String id) {
        mot.getCollection(mot.getCollectionName(PcdDocRead.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdDocReadByCondition(PcdDocRead pcdDocRead) {
        mot.getCollection(mot.getCollectionName(PcdDocRead.class)).deleteMany(MongoKit.buildMatchDocument(pcdDocRead));
    }
    public void batchSavePcdDocRead(List<PcdDocRead> pcdDocReads){
        pcdDocReads.forEach(pcdDocRead -> pcdDocRead.setId(sequenceId.nextId()));
        mot.insertAll(pcdDocReads);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
