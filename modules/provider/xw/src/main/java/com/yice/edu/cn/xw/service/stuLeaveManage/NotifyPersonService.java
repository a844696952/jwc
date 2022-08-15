package com.yice.edu.cn.xw.service.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NotifyPersonService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public NotifyPerson findNotifyPersonById(String id) {
        return mot.findById(id,NotifyPerson.class);
    }
    public void saveNotifyPerson(NotifyPerson notifyPerson) {
        notifyPerson.setCreateTime(DateUtil.now());
        notifyPerson.setId(sequenceId.nextId());
        mot.insert(notifyPerson);
    }
    public List<NotifyPerson> findNotifyPersonListByCondition(NotifyPerson notifyPerson) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(NotifyPerson.class)).find(MongoKit.buildMatchDocument(notifyPerson));
        Pager pager = notifyPerson.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<NotifyPerson> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(NotifyPerson.class, document)));
        return list;
    }
    public long findNotifyPersonCountByCondition(NotifyPerson notifyPerson) {
        return mot.getCollection(mot.getCollectionName(NotifyPerson.class)).countDocuments(MongoKit.buildMatchDocument(notifyPerson));
    }
    public NotifyPerson findOneNotifyPersonByCondition(NotifyPerson notifyPerson) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(NotifyPerson.class)).find(MongoKit.buildMatchDocument(notifyPerson));
       MongoKit.appendProjection(query,notifyPerson.getPager());
       return mot.getConverter().read(NotifyPerson.class,query.first());
    }

    public void updateNotifyPerson(NotifyPerson notifyPerson) {
        mot.updateFirst(query(where("id").is(notifyPerson.getId())), MongoKit.update(notifyPerson,false),NotifyPerson.class);
    }
    public void updateNotifyPersonForAll(NotifyPerson notifyPerson) {
        mot.updateFirst(query(where("id").is(notifyPerson.getId())), MongoKit.update(notifyPerson,true),NotifyPerson.class);
    }
    public void deleteNotifyPerson(String id) {
        mot.remove(query(where("id").is(id)),NotifyPerson.class);
    }
    public void deleteNotifyPersonByCondition(NotifyPerson notifyPerson) {
        mot.getCollection(mot.getCollectionName(NotifyPerson.class)).deleteMany(MongoKit.buildMatchDocument(notifyPerson));
    }
    public void batchSaveNotifyPerson(List<NotifyPerson> notifyPersons){
        notifyPersons.forEach(notifyPerson -> notifyPerson.setId(sequenceId.nextId()));
        mot.insertAll(notifyPersons);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
