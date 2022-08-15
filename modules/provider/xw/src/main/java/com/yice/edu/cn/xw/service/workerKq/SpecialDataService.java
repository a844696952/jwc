package com.yice.edu.cn.xw.service.workerKq;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SpecialDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SpecialData findSpecialDataById(String id) {
        return mot.findById(id,SpecialData.class);
    }
    public void saveSpecialData(SpecialData specialData) {
        specialData.setCreateTime(DateUtil.now());
        specialData.setId(sequenceId.nextId());
        mot.insert(specialData);
    }
    public List<SpecialData> findSpecialDataListByCondition(SpecialData specialData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SpecialData.class)).find(MongoKit.buildMatchDocument(specialData));
        Pager pager = specialData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SpecialData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SpecialData.class, document)));
        return list;
    }
    public long findSpecialDataCountByCondition(SpecialData specialData) {
        return mot.getCollection(mot.getCollectionName(SpecialData.class)).countDocuments(MongoKit.buildMatchDocument(specialData));
    }
    public SpecialData findOneSpecialDataByCondition(SpecialData specialData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SpecialData.class)).find(MongoKit.buildMatchDocument(specialData));
       MongoKit.appendProjection(query,specialData.getPager());
       return mot.getConverter().read(SpecialData.class,query.first());
    }

    public void updateSpecialData(SpecialData specialData) {
        specialData.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(specialData.getId())), MongoKit.update(specialData),SpecialData.class);
    }
    public void deleteSpecialData(String id) {
        mot.remove(query(where("id").is(id)),SpecialData.class);
    }
    public void deleteSpecialDataByCondition(SpecialData specialData) {
        mot.getCollection(mot.getCollectionName(SpecialData.class)).deleteMany(MongoKit.buildMatchDocument(specialData));
    }
    public void batchSaveSpecialData(List<SpecialData> specialDatas){
        specialDatas.forEach(specialData -> specialData.setId(sequenceId.nextId()));
        mot.insertAll(specialDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
