package com.yice.edu.cn.xw.service.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutClassTime;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StuInAndOutClassTimeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuInAndOutClassTime findStuInAndOutClassTimeById(String id) {
        return mot.findById(id,StuInAndOutClassTime.class);
    }
    public void saveStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setCreateTime(DateUtil.now());
        stuInAndOutClassTime.setId(sequenceId.nextId());
        mot.insert(stuInAndOutClassTime);
    }
    public List<StuInAndOutClassTime> findStuInAndOutClassTimeListByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).find(MongoKit.buildMatchDocument(stuInAndOutClassTime));
        Pager pager = stuInAndOutClassTime.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StuInAndOutClassTime> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuInAndOutClassTime.class, document)));
        return list;
    }
    public long findStuInAndOutClassTimeCountByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        return mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).countDocuments(MongoKit.buildMatchDocument(stuInAndOutClassTime));
    }
    public StuInAndOutClassTime findOneStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).find(MongoKit.buildMatchDocument(stuInAndOutClassTime));
       MongoKit.appendProjection(query,stuInAndOutClassTime.getPager());
       return mot.getConverter().read(StuInAndOutClassTime.class,query.first());
    }

    public void updateStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).updateOne(new Document("_id",stuInAndOutClassTime.getId()), MongoKit.buildUpdateDocument(stuInAndOutClassTime,false));
    }
    public void updateStuInAndOutClassTimeForAll(StuInAndOutClassTime stuInAndOutClassTime) {
        stuInAndOutClassTime.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).updateOne(new Document("_id",stuInAndOutClassTime.getId()), MongoKit.buildUpdateDocument(stuInAndOutClassTime,true));
    }
    public void deleteStuInAndOutClassTime(String id) {
        mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).deleteOne(new Document("_id",id));
    }
    public void deleteStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime) {
        mot.getCollection(mot.getCollectionName(StuInAndOutClassTime.class)).deleteMany(MongoKit.buildMatchDocument(stuInAndOutClassTime));
    }
    public void batchSaveStuInAndOutClassTime(List<StuInAndOutClassTime> stuInAndOutClassTimes){
        stuInAndOutClassTimes.forEach(stuInAndOutClassTime -> stuInAndOutClassTime.setId(sequenceId.nextId()));
        mot.insertAll(stuInAndOutClassTimes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
