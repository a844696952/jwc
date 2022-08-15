package com.yice.edu.cn.xw.service.workerKq;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.workerKq.CommenSettings;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CommenSettingsService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public CommenSettings findCommenSettingsById(String id) {
        return mot.findById(id,CommenSettings.class);
    }
    public void saveCommenSettings(CommenSettings commenSettings) {
        commenSettings.setCreateTime(DateUtil.now());
        commenSettings.setId(sequenceId.nextId());
        mot.insert(commenSettings);
    }
    public List<CommenSettings> findCommenSettingsListByCondition(CommenSettings commenSettings) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CommenSettings.class)).find(MongoKit.buildMatchDocument(commenSettings));
        Pager pager = commenSettings.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<CommenSettings> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(CommenSettings.class, document)));
        return list;
    }
    public long findCommenSettingsCountByCondition(CommenSettings commenSettings) {
        return mot.getCollection(mot.getCollectionName(CommenSettings.class)).countDocuments(MongoKit.buildMatchDocument(commenSettings));
    }
    public CommenSettings findOneCommenSettingsByCondition(CommenSettings commenSettings) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(CommenSettings.class)).find(MongoKit.buildMatchDocument(commenSettings));
       MongoKit.appendProjection(query,commenSettings.getPager());
       return mot.getConverter().read(CommenSettings.class,query.first());
    }

    public void updateCommenSettings(CommenSettings commenSettings) {
        commenSettings.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(commenSettings.getId())), MongoKit.update(commenSettings),CommenSettings.class);
    }
    public void deleteCommenSettings(String id) {
        mot.remove(query(where("id").is(id)),CommenSettings.class);
    }
    public void deleteCommenSettingsByCondition(CommenSettings commenSettings) {
        mot.getCollection(mot.getCollectionName(CommenSettings.class)).deleteMany(MongoKit.buildMatchDocument(commenSettings));
    }
    public void batchSaveCommenSettings(List<CommenSettings> commenSettingss){
        commenSettingss.forEach(commenSettings -> commenSettings.setId(sequenceId.nextId()));
        mot.insertAll(commenSettingss);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
