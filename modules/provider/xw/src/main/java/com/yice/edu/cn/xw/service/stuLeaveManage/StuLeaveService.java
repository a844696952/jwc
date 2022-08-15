package com.yice.edu.cn.xw.service.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StuLeaveService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuLeave findStuLeaveById(String id) {
        return mot.findById(id, StuLeave.class);
    }

    public void saveStuLeave(StuLeave stuLeave) {
        stuLeave.setCreateTime(DateUtil.now());
        stuLeave.setId(sequenceId.nextId());
        mot.insert(stuLeave);
    }

    public List<StuLeave> findStuLeaveListByCondition(StuLeave stuLeave) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuLeave.class)).find(MongoKit.buildMatchDocument(stuLeave));
        Pager pager = stuLeave.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<StuLeave> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuLeave.class, document)));
        return list;
    }

    public long findStuLeaveCountByCondition(StuLeave stuLeave) {
        return mot.getCollection(mot.getCollectionName(StuLeave.class)).countDocuments(MongoKit.buildMatchDocument(stuLeave));
    }

    public StuLeave findOneStuLeaveByCondition(StuLeave stuLeave) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuLeave.class)).find(MongoKit.buildMatchDocument(stuLeave));
        MongoKit.appendProjection(query, stuLeave.getPager());
        return mot.getConverter().read(StuLeave.class, query.first());
    }

    public void updateStuLeave(StuLeave stuLeave) {
        stuLeave.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(stuLeave.getId())), MongoKit.update(stuLeave, false), StuLeave.class);
    }

    public void updateStuLeaveForAll(StuLeave stuLeave) {
        stuLeave.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(stuLeave.getId())), MongoKit.update(stuLeave, true), StuLeave.class);
    }

    public void deleteStuLeave(String id) {
        mot.remove(query(where("id").is(id)), StuLeave.class);
    }

    public void deleteStuLeaveByCondition(StuLeave stuLeave) {
        mot.getCollection(mot.getCollectionName(StuLeave.class)).deleteMany(MongoKit.buildMatchDocument(stuLeave));
    }

    public void batchSaveStuLeave(List<StuLeave> stuLeaves) {
        stuLeaves.forEach(stuLeave -> stuLeave.setId(sequenceId.nextId()));
        mot.insertAll(stuLeaves);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<StuLeave> findStuLeaveListByConditionForWH(StuLeave stuLeave) {
        Document doc = new Document();
        stuLeave.setRangeTime(null);
        if (stuLeave.getBeginTime() != null && stuLeave.getEndTime() != null) {
            doc.append("$or", Arrays.asList(
                    new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$gte", stuLeave.getBeginTime())), new Document("endTime", new Document("$lte", stuLeave.getEndTime())))
                    )
                    ,
                    new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$lte", stuLeave.getBeginTime())), new Document("endTime", new Document("$gte", stuLeave.getBeginTime())))
                    )
                    , new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$lte", stuLeave.getEndTime())), new Document("endTime", new Document("$gte", stuLeave.getEndTime())))
                    )
            ));
            stuLeave.setBeginTime(null);
            stuLeave.setEndTime(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(stuLeave));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuLeave.class)).find(doc);
        Pager pager = stuLeave.getPager();
        pager.setPaging(false);
        pager.setSortField("endTime");
        pager.setSortOrder(Pager.ASC);
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<StuLeave> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuLeave.class, document)));
        return list;
    }

    public long findStuLeaveCountByConditionForWH(StuLeave stuLeave) {
        Document doc = new Document();
        stuLeave.setRangeTime(null);
        if (stuLeave.getBeginTime() != null && stuLeave.getEndTime() != null) {
            doc.append("$or", Arrays.asList(
                    new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$gte", stuLeave.getBeginTime())), new Document("endTime", new Document("$lte", stuLeave.getEndTime())))
                    )
                    ,
                    new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$lte", stuLeave.getBeginTime())), new Document("endTime", new Document("$gte", stuLeave.getBeginTime())))
                    )
                    , new Document(
                            "$and", Arrays.asList(new Document("beginTime", new Document("$lte", stuLeave.getEndTime())), new Document("endTime", new Document("$gte", stuLeave.getEndTime())))
                    )
            ));
            stuLeave.setBeginTime(null);
            stuLeave.setEndTime(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(stuLeave));
        return mot.getCollection(mot.getCollectionName(StuLeave.class)).countDocuments(doc);
    }
}
