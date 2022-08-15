package com.yice.edu.cn.jw.service.classSchedule;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classSchedule.PastClassSchedule;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PastClassScheduleService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PastClassSchedule findPastClassScheduleById(String id) {
        return mot.findById(id,PastClassSchedule.class);
    }
    public void savePastClassSchedule(PastClassSchedule pastClassSchedule) {
        pastClassSchedule.setCreateTime(DateUtil.now());
        pastClassSchedule.setId(sequenceId.nextId());
        mot.insert(pastClassSchedule);
    }
    public List<PastClassSchedule> findPastClassScheduleListByCondition(PastClassSchedule pastClassSchedule) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).find(MongoKit.buildMatchDocument(pastClassSchedule));
        Pager pager = pastClassSchedule.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PastClassSchedule> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PastClassSchedule.class, document)));
        return list;
    }
    public long findPastClassScheduleCountByCondition(PastClassSchedule pastClassSchedule) {
        return mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).countDocuments(MongoKit.buildMatchDocument(pastClassSchedule));
    }
    public PastClassSchedule findOnePastClassScheduleByCondition(PastClassSchedule pastClassSchedule) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).find(MongoKit.buildMatchDocument(pastClassSchedule));
       MongoKit.appendProjection(query,pastClassSchedule.getPager());
       return mot.getConverter().read(PastClassSchedule.class,query.first());
    }

    public void updatePastClassSchedule(PastClassSchedule pastClassSchedule) {
        pastClassSchedule.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).updateOne(new Document("_id",pastClassSchedule.getId()), MongoKit.buildUpdateDocument(pastClassSchedule,false));
    }
    public void updatePastClassScheduleForAll(PastClassSchedule pastClassSchedule) {
        pastClassSchedule.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).updateOne(new Document("_id",pastClassSchedule.getId()), MongoKit.buildUpdateDocument(pastClassSchedule,true));
    }
    public void deletePastClassSchedule(String id) {
        mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).deleteOne(new Document("_id",id));
    }
    public void deletePastClassScheduleByCondition(PastClassSchedule pastClassSchedule) {
        mot.getCollection(mot.getCollectionName(PastClassSchedule.class)).deleteMany(MongoKit.buildMatchDocument(pastClassSchedule));
    }
    public void batchSavePastClassSchedule(List<PastClassSchedule> pastClassSchedules){
        pastClassSchedules.forEach(pastClassSchedule -> pastClassSchedule.setId(sequenceId.nextId()));
        mot.insertAll(pastClassSchedules);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<PastClassSchedule> findListPastClassScheduleByCountKong(PastClassSchedule pastClassSchedule){
        Document match = new Document("$match",new Document("scheduleId",pastClassSchedule.getScheduleId()));
        Document classScheduleList = new Document("$push",new Document("gradeName","$gradeName").append("className","$className").append("weekId","$weekId").append("numberId","$numberId").append("schoolId","$schoolId").append("courseName","$courseName").append("teacherName","$teacherName"));
        Document document = new Document("$group",new Document("_id",new Document("gradeName","$gradeName").append("className","$className")).append("gradeName",new Document("$first","$gradeName")).append("className",new Document("$first","$className")).append("classScheduleList",classScheduleList));
        Document sort = new Document("$sort",new Document("gradeName",1).append("className",1));
        AggregateIterable<Document> aggregate = mot.getCollection("pastClassSchedule").aggregate(Arrays.asList(match,document,sort));
        MongoCursor<Document> iterator = aggregate.iterator();
        List<PastClassSchedule> pastClassSchedules = new ArrayList<>();
        while (iterator.hasNext()){
            pastClassSchedules.add(mot.getConverter().read(PastClassSchedule.class,iterator.next()));
        }
        return pastClassSchedules;
    }

}
