package com.yice.edu.cn.dm.service.setting;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.setting.EccGlobalSetting;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EccGlobalSettingService {


    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public EccGlobalSetting findEccGlobalSettingById(String id) {
        return mot.findById(id, EccGlobalSetting.class);
    }

    private void saveEccGlobalSetting(EccGlobalSetting eccGlobalSetting) {
        eccGlobalSetting.setId(sequenceId.nextId());
        mot.insert(eccGlobalSetting);
    }

    public List<EccGlobalSetting> findEccGlobalSettingListByCondition(EccGlobalSetting eccGlobalSetting) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccGlobalSetting.class)).find(MongoKit.buildMatchDocument(eccGlobalSetting));
        Pager pager = eccGlobalSetting.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<EccGlobalSetting> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(EccGlobalSetting.class, document)));
        return list;
    }

    public long findEccGlobalSettingCountByCondition(EccGlobalSetting eccGlobalSetting) {
        return mot.getCollection(mot.getCollectionName(EccGlobalSetting.class)).count(MongoKit.buildMatchDocument(eccGlobalSetting));
    }

    public EccGlobalSetting findOneEccGlobalSettingByCondition(EccGlobalSetting eccGlobalSetting) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccGlobalSetting.class)).find(MongoKit.buildMatchDocument(eccGlobalSetting));
        MongoKit.appendProjection(query, eccGlobalSetting.getPager());
        return mot.getConverter().read(EccGlobalSetting.class, query.first());
    }

    private void updateEccGlobalSetting(EccGlobalSetting eccGlobalSetting) {
        mot.updateFirst(query(where("id").is(eccGlobalSetting.getId())), MongoKit.update(eccGlobalSetting), EccGlobalSetting.class);
    }

    public void deleteEccGlobalSetting(String id) {
        mot.remove(query(where("id").is(id)), EccGlobalSetting.class);
    }

    public void deleteEccGlobalSettingByCondition(EccGlobalSetting eccGlobalSetting) {
        mot.getCollection(mot.getCollectionName(EccGlobalSetting.class)).deleteMany(MongoKit.buildMatchDocument(eccGlobalSetting));
    }


    public void batchSaveEccGlobalSetting(List<EccGlobalSetting> eccGlobalSettings) {
        eccGlobalSettings.forEach(eccGlobalSetting -> {
            eccGlobalSetting.setId(sequenceId.nextId());
                }
        );
        mot.insertAll(eccGlobalSettings);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void putSetting(EccGlobalSetting setting){
        EccGlobalSetting eccGlobalSetting = getSetting(setting.getSchoolId(), setting.getKey());
        if(eccGlobalSetting==null){
            this.saveEccGlobalSetting(setting);
        }else{
            setting.setId(eccGlobalSetting.getId());
            this.updateEccGlobalSetting(setting);
        }

    }

    public EccGlobalSetting getSetting(String schoolId,String key){
        EccGlobalSetting setting = new EccGlobalSetting();
        setting.setKey(key);
        setting.setSchoolId(schoolId);
        return this.findOneEccGlobalSettingByCondition(setting);

    }

    public EccGlobalSetting getSetting(EccGlobalSetting globalSetting) {
        EccGlobalSetting setting = new EccGlobalSetting();
        setting.setKey(globalSetting.getKey());
        setting.setSchoolId(globalSetting.getSchoolId());
        return this.findOneEccGlobalSettingByCondition(setting);
    }
}
