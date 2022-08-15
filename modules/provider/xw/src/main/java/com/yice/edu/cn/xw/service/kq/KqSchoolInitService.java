package com.yice.edu.cn.xw.service.kq;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqSchoolInitService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public KqSchoolInit findKqSchoolInitById(String id) {
        return mot.findById(id,KqSchoolInit.class);
    }
    public void saveKqSchoolInit(KqSchoolInit kqSchoolInit) {
        kqSchoolInit.setCreateTime(DateUtil.now());
        kqSchoolInit.setId(sequenceId.nextId());
        mot.insert(kqSchoolInit);
    }
    public List<KqSchoolInit> findKqSchoolInitListByCondition(KqSchoolInit kqSchoolInit) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqSchoolInit.class)).find(MongoKit.buildMatchDocument(kqSchoolInit));
        Pager pager = kqSchoolInit.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<KqSchoolInit> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqSchoolInit.class, document)));
        return list;
    }
    public long findKqSchoolInitCountByCondition(KqSchoolInit kqSchoolInit) {
        return mot.getCollection(mot.getCollectionName(KqSchoolInit.class)).count(MongoKit.buildMatchDocument(kqSchoolInit));
    }
    public KqSchoolInit findOneKqSchoolInitByCondition(KqSchoolInit kqSchoolInit) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqSchoolInit.class)).find(MongoKit.buildMatchDocument(kqSchoolInit));
       MongoKit.appendProjection(query,kqSchoolInit.getPager());
       return mot.getConverter().read(KqSchoolInit.class,query.first());
    }

    public void updateKqSchoolInit(KqSchoolInit kqSchoolInit) {
        kqSchoolInit.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqSchoolInit.getId())), MongoKit.update(kqSchoolInit),KqSchoolInit.class);
    }
    public void deleteKqSchoolInit(String id) {
        mot.remove(query(where("id").is(id)),KqSchoolInit.class);
    }
    public void deleteKqSchoolInitByCondition(KqSchoolInit kqSchoolInit) {
        mot.getCollection(mot.getCollectionName(KqSchoolInit.class)).deleteMany(MongoKit.buildMatchDocument(kqSchoolInit));
    }
    public void batchSaveKqSchoolInit(List<KqSchoolInit> kqSchoolInits){
        kqSchoolInits.forEach(kqSchoolInit -> kqSchoolInit.setId(sequenceId.nextId()));
        mot.insertAll(kqSchoolInits);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
}
