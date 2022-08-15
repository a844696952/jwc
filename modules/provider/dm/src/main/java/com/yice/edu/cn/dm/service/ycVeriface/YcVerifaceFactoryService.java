package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.factory.YcVerifaceFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceFactoryService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceFactory findYcVerifaceFactoryById(String id) {
        return mot.findById(id,YcVerifaceFactory.class);
    }
    public void saveYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory) {
        ycVerifaceFactory.setCreateTime(DateUtil.now());
        ycVerifaceFactory.setId(sequenceId.nextId());
        mot.insert(ycVerifaceFactory);
    }
    public List<YcVerifaceFactory> findYcVerifaceFactoryListByCondition(YcVerifaceFactory ycVerifaceFactory) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceFactory.class)).find(MongoKit.buildMatchDocument(ycVerifaceFactory));
        Pager pager = ycVerifaceFactory.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceFactory> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceFactory.class, document)));
        return list;
    }
    public long findYcVerifaceFactoryCountByCondition(YcVerifaceFactory ycVerifaceFactory) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceFactory.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceFactory));
    }
    public YcVerifaceFactory findOneYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceFactory.class)).find(MongoKit.buildMatchDocument(ycVerifaceFactory));
       MongoKit.appendProjection(query,ycVerifaceFactory.getPager());
       return mot.getConverter().read(YcVerifaceFactory.class,query.first());
    }

    public void updateYcVerifaceFactory(YcVerifaceFactory ycVerifaceFactory) {
        ycVerifaceFactory.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceFactory.getId())), MongoKit.update(ycVerifaceFactory),YcVerifaceFactory.class);
    }
    public void deleteYcVerifaceFactory(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceFactory.class);
    }
    public void deleteYcVerifaceFactoryByCondition(YcVerifaceFactory ycVerifaceFactory) {
        mot.getCollection(mot.getCollectionName(YcVerifaceFactory.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceFactory));
    }
    public void batchSaveYcVerifaceFactory(List<YcVerifaceFactory> ycVerifaceFactorys){
        ycVerifaceFactorys.forEach(ycVerifaceFactory -> ycVerifaceFactory.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceFactorys);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
