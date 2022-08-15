package com.yice.edu.cn.jw.service.loc;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.loc.SchoolConfig;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SchoolConfigService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolConfig findSchoolConfigById(String id) {
        return mot.findById(id,SchoolConfig.class);
    }
    public void saveSchoolConfig(SchoolConfig schoolConfig) {
        schoolConfig.setId(sequenceId.nextId());
        schoolConfig.setCreateTime(DateUtil.now());
        mot.insert(schoolConfig);
    }
    public List<SchoolConfig> findSchoolConfigListByCondition(SchoolConfig schoolConfig) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolConfig.class)).find(MongoKit.buildMatchDocument(schoolConfig));
        Pager pager = schoolConfig.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolConfig> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolConfig.class, document)));
        return list;
    }
    public long findSchoolConfigCountByCondition(SchoolConfig schoolConfig) {
        return mot.getCollection(mot.getCollectionName(SchoolConfig.class)).countDocuments(MongoKit.buildMatchDocument(schoolConfig));
    }
    public SchoolConfig findOneSchoolConfigByCondition(SchoolConfig schoolConfig) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolConfig.class)).find(MongoKit.buildMatchDocument(schoolConfig));
       MongoKit.appendProjection(query,schoolConfig.getPager());
       return mot.getConverter().read(SchoolConfig.class,query.first());
    }

    public void updateSchoolConfig(SchoolConfig schoolConfig) {
        schoolConfig.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(SchoolConfig.class)).updateOne(new Document("_id",schoolConfig.getId()), MongoKit.buildUpdateDocument(schoolConfig,false));
    }
    public void updateSchoolConfigForAll(SchoolConfig schoolConfig) {
        schoolConfig.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(SchoolConfig.class)).updateOne(new Document("_id",schoolConfig.getId()), MongoKit.buildUpdateDocument(schoolConfig,true));
    }
    public void deleteSchoolConfig(String id) {
        mot.getCollection(mot.getCollectionName(SchoolConfig.class)).deleteOne(new Document("_id",id));
    }
    public void deleteSchoolConfigByCondition(SchoolConfig schoolConfig) {
        mot.getCollection(mot.getCollectionName(SchoolConfig.class)).deleteMany(MongoKit.buildMatchDocument(schoolConfig));
    }
    public void batchSaveSchoolConfig(List<SchoolConfig> schoolConfigs){
        schoolConfigs.forEach(schoolConfig -> schoolConfig.setId(sequenceId.nextId()));
        mot.insertAll(schoolConfigs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void saveSchoolConfigKong(SchoolConfig schoolConfig) {
        schoolConfig.setCreateTime(DateUtil.now());
        mot.save(schoolConfig);
    }
}
