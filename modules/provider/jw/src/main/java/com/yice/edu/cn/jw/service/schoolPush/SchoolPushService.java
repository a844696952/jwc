package com.yice.edu.cn.jw.service.schoolPush;

import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolPushService {

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*--------------------------------------------------------generated code start-----------------------------------------------------------------------------*/
    public SchoolPush findSchoolPushById(String id) {
        return mot.findById(id,SchoolPush.class);
    }
    public void saveSchoolPush(SchoolPush schoolPush) {
        schoolPush.setCreateTime(DateUtil.now());
        schoolPush.setId(sequenceId.nextId());
        mot.insert(schoolPush);
    }
    public List<SchoolPush> findSchoolPushListByCondition(SchoolPush schoolPush) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolPush.class)).find(MongoKit.buildMatchDocument(schoolPush));
        Pager pager = schoolPush.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolPush> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolPush.class, document)));
        return list;

    }
    public long findSchoolPushCountByCondition(SchoolPush schoolPush) {
        return  mot.getCollection(mot.getCollectionName(SchoolPush.class)).count(MongoKit.buildMatchDocument(schoolPush));

    }
    public SchoolPush findOneSchoolPushByCondition(SchoolPush schoolPush) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolPush.class)).find(MongoKit.buildMatchDocument(schoolPush));
        MongoKit.appendProjection(query,schoolPush.getPager());
        return mot.getConverter().read(SchoolPush.class,query.first());
    }

    public void updateSchoolPush(SchoolPush schoolPush) {
        mot.updateFirst(query(where("id").is(schoolPush.getId())), MongoKit.update(schoolPush),SchoolPush.class);
    }
    public void deleteSchoolPush(String id) {
        mot.remove(query(where("id").is(id)),SchoolPush.class);
    }
    public void deleteSchoolPushByCondition(SchoolPush schoolPush) {
        mot.getCollection(mot.getCollectionName(SchoolPush.class)).deleteMany(MongoKit.buildMatchDocument(schoolPush));
    }
    public void batchSaveSchoolPush(List<SchoolPush> schoolPushs){
        schoolPushs.forEach(schoolPush -> schoolPush.setId(sequenceId.nextId()));
        mot.insertAll(schoolPushs);
    }
    /*----------------------------------------------------------------generated code end---------------------------------------------------------------------*/

    public List<SchoolPush> findPageSchoolPushesByAppIdAndSchoolId(SchoolPush schoolPush) {
        final TypedAggregation aggregation = TypedAggregation.newAggregation(
                SchoolPush.class,
                Aggregation.match(where("schoolId").is(schoolPush.getSchoolId()).and("appIds").in(schoolPush.getAppId())),
                Aggregation.sort(schoolPush.getPager().getSort()),
                Aggregation.skip(schoolPush.getPager().getBeginRow()),
                Aggregation.limit(schoolPush.getPager().getPageSize()),
                Aggregation.project("id","urgent","title","content","createTime","schoolId")
        );
        return mot.aggregate(aggregation, SchoolPush.class).getMappedResults();
    }
}
