package com.yice.edu.cn.xw.service.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuStatusChange;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StuStatusChangeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuStatusChange findStuStatusChangeById(String id) {
        return mot.findById(id,StuStatusChange.class);
    }
    public void saveStuStatusChange(StuStatusChange stuStatusChange) {
        stuStatusChange.setCreateTime(DateUtil.now());
        stuStatusChange.setId(sequenceId.nextId());
        mot.insert(stuStatusChange);
    }
    public List<StuStatusChange> findStuStatusChangeListByCondition(StuStatusChange stuStatusChange) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuStatusChange.class)).find(MongoKit.buildMatchDocument(stuStatusChange));
        Pager pager = stuStatusChange.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StuStatusChange> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuStatusChange.class, document)));
        return list;
    }
    public long findStuStatusChangeCountByCondition(StuStatusChange stuStatusChange) {
        return mot.getCollection(mot.getCollectionName(StuStatusChange.class)).countDocuments(MongoKit.buildMatchDocument(stuStatusChange));
    }
    public StuStatusChange findOneStuStatusChangeByCondition(StuStatusChange stuStatusChange) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuStatusChange.class)).find(MongoKit.buildMatchDocument(stuStatusChange));
       MongoKit.appendProjection(query,stuStatusChange.getPager());
       return mot.getConverter().read(StuStatusChange.class,query.first());
    }

    public void updateStuStatusChange(StuStatusChange stuStatusChange) {
        mot.updateFirst(query(where("id").is(stuStatusChange.getId())), MongoKit.update(stuStatusChange),StuStatusChange.class);
    }
    public void deleteStuStatusChange(String id) {
        mot.remove(query(where("id").is(id)),StuStatusChange.class);
    }
    public void deleteStuStatusChangeByCondition(StuStatusChange stuStatusChange) {
        mot.getCollection(mot.getCollectionName(StuStatusChange.class)).deleteMany(MongoKit.buildMatchDocument(stuStatusChange));
    }
    public void batchSaveStuStatusChange(List<StuStatusChange> stuStatusChanges){
        stuStatusChanges.forEach(stuStatusChange -> stuStatusChange.setId(sequenceId.nextId()));
        mot.insertAll(stuStatusChanges);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
