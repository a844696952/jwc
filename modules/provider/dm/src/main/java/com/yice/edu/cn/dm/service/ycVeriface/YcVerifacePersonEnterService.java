package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifacePersonEnterService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifacePersonEnter findYcVerifacePersonEnterById(String id) {
        return mot.findById(id,YcVerifacePersonEnter.class);
    }
    public void saveYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter) {
        ycVerifacePersonEnter.setCreateTime(DateUtil.now());
        ycVerifacePersonEnter.setId(sequenceId.nextId());
        mot.insert(ycVerifacePersonEnter);
    }
    public List<YcVerifacePersonEnter> findYcVerifacePersonEnterListByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifacePersonEnter.class)).find(MongoKit.buildMatchDocument(ycVerifacePersonEnter));
        Pager pager = ycVerifacePersonEnter.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifacePersonEnter> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifacePersonEnter.class, document)));
        return list;
    }
    public long findYcVerifacePersonEnterCountByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        return mot.getCollection(mot.getCollectionName(YcVerifacePersonEnter.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifacePersonEnter));
    }
    public YcVerifacePersonEnter findOneYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifacePersonEnter.class)).find(MongoKit.buildMatchDocument(ycVerifacePersonEnter));
       MongoKit.appendProjection(query,ycVerifacePersonEnter.getPager());
       return mot.getConverter().read(YcVerifacePersonEnter.class,query.first());
    }

    public void updateYcVerifacePersonEnter(YcVerifacePersonEnter ycVerifacePersonEnter) {
        ycVerifacePersonEnter.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifacePersonEnter.getId())), MongoKit.update(ycVerifacePersonEnter),YcVerifacePersonEnter.class);
    }
    public void deleteYcVerifacePersonEnter(String id) {
        mot.remove(query(where("id").is(id)),YcVerifacePersonEnter.class);
    }
    public void deleteYcVerifacePersonEnterByCondition(YcVerifacePersonEnter ycVerifacePersonEnter) {
        mot.getCollection(mot.getCollectionName(YcVerifacePersonEnter.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifacePersonEnter));
    }
    public void batchSaveYcVerifacePersonEnter(List<YcVerifacePersonEnter> ycVerifacePersonEnters){
        ycVerifacePersonEnters.forEach(ycVerifacePersonEnter -> ycVerifacePersonEnter.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifacePersonEnters);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<YcVerifacePersonEnter> findYcVerifacePersonEnterByPersonIdList(YcVerifacePersonEnter ycVerifacePersonEnter) {
        Document doc = new Document();
        if (CollectionUtil.isNotEmpty(ycVerifacePersonEnter.getPersonIdList())) {
            doc.append("personId", new Document("$in", ycVerifacePersonEnter.getPersonIdList()));
            ycVerifacePersonEnter.setPersonIdList(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(ycVerifacePersonEnter));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifacePersonEnter.class)).find(doc);
        Pager pager = ycVerifacePersonEnter.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifacePersonEnter> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifacePersonEnter.class, document)));
        return list;
    }


}
