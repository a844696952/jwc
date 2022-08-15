package com.yice.edu.cn.dm.service.zyDevice;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqDevicePersonService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KqDevicePerson findKqDevicePersonById(String id) {
        return mot.findById(id,KqDevicePerson.class);
    }
    public void saveKqDevicePerson(KqDevicePerson kqDevicePerson) {
        kqDevicePerson.setCreateTime(DateUtil.now());
        kqDevicePerson.setId(sequenceId.nextId());
        mot.insert(kqDevicePerson);
    }
    public List<KqDevicePerson> findKqDevicePersonListByCondition(KqDevicePerson kqDevicePerson) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDevicePerson.class)).find(MongoKit.buildMatchDocument(kqDevicePerson));
        Pager pager = kqDevicePerson.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<KqDevicePerson> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDevicePerson.class, document)));
        return list;
    }
    public long findKqDevicePersonCountByCondition(KqDevicePerson kqDevicePerson) {
        return mot.getCollection(mot.getCollectionName(KqDevicePerson.class)).countDocuments(MongoKit.buildMatchDocument(kqDevicePerson));
    }
    public KqDevicePerson findOneKqDevicePersonByCondition(KqDevicePerson kqDevicePerson) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDevicePerson.class)).find(MongoKit.buildMatchDocument(kqDevicePerson));
       MongoKit.appendProjection(query,kqDevicePerson.getPager());
       return mot.getConverter().read(KqDevicePerson.class,query.first());
    }

    public void updateKqDevicePerson(KqDevicePerson kqDevicePerson) {
        kqDevicePerson.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqDevicePerson.getId())), MongoKit.update(kqDevicePerson),KqDevicePerson.class);
    }
    public void deleteKqDevicePerson(String id) {
        mot.remove(query(where("id").is(id)),KqDevicePerson.class);
    }
    public void deleteKqDevicePersonByCondition(KqDevicePerson kqDevicePerson) {
        mot.getCollection(mot.getCollectionName(KqDevicePerson.class)).deleteMany(MongoKit.buildMatchDocument(kqDevicePerson));
    }
    public void batchSaveKqDevicePerson(List<KqDevicePerson> kqDevicePersons){
        kqDevicePersons.forEach(kqDevicePerson -> kqDevicePerson.setId(sequenceId.nextId()));
        mot.insertAll(kqDevicePersons);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
