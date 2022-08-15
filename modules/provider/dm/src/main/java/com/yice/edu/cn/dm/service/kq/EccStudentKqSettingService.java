package com.yice.edu.cn.dm.service.kq;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author dengfengfeng
 */
@Service
public class EccStudentKqSettingService {

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public EccStudentKqSetting findEccStudentKqSettingById(String id) {
        return mot.findById(id, EccStudentKqSetting.class);
    }

    public void saveEccStudentKqSetting(EccStudentKqSetting eccStudentKqSetting) {
        eccStudentKqSetting.setId(sequenceId.nextId());
        eccStudentKqSetting.setCreateTime(DateUtil.now());
        mot.insert(eccStudentKqSetting);
    }

    public List<EccStudentKqSetting> findEccStudentKqSettingListByCondition(EccStudentKqSetting eccStudentKqSetting) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentKqSetting.class)).find(MongoKit.buildMatchDocument(eccStudentKqSetting));
        Pager pager = eccStudentKqSetting.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<EccStudentKqSetting> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(EccStudentKqSetting.class, document)));
        return list;
    }

    public long findEccStudentKqSettingCountByCondition(EccStudentKqSetting eccStudentKqSetting) {
        return mot.getCollection(mot.getCollectionName(EccStudentKqSetting.class)).count(MongoKit.buildMatchDocument(eccStudentKqSetting));
    }

    public EccStudentKqSetting findOneEccStudentKqSettingByCondition(EccStudentKqSetting eccStudentKqSetting) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentKqSetting.class)).find(MongoKit.buildMatchDocument(eccStudentKqSetting));
        MongoKit.appendProjection(query, eccStudentKqSetting.getPager());
        return mot.getConverter().read(EccStudentKqSetting.class, query.first());
    }

    public void updateEccStudentKqSetting(EccStudentKqSetting eccStudentKqSetting) {
        mot.updateFirst(query(where("id").is(eccStudentKqSetting.getId())), MongoKit.update(eccStudentKqSetting), EccStudentKqSetting.class);
    }

    public void deleteEccStudentKqSetting(String id) {
        mot.remove(query(where("id").is(id)), EccStudentKqSetting.class);
    }

    public void deleteEccStudentKqSettingByCondition(EccStudentKqSetting eccStudentKqSetting) {
        mot.getCollection(mot.getCollectionName(EccStudentKqSetting.class)).deleteMany(MongoKit.buildMatchDocument(eccStudentKqSetting));
    }


    public void batchSaveEccStudentKqSetting(List<EccStudentKqSetting> eccStudentKqSettings) {
        final String now = DateUtil.now();
        eccStudentKqSettings.forEach(eccStudentKqSetting -> {
                    eccStudentKqSetting.setId(sequenceId.nextId());
                    eccStudentKqSetting.setCreateTime(now);
                }
        );
        mot.insertAll(eccStudentKqSettings);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
