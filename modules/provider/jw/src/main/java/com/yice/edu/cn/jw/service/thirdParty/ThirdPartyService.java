package com.yice.edu.cn.jw.service.thirdParty;

import cn.hutool.core.date.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.api.thirdParty.ThirdParty;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.regex;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ThirdPartyService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ThirdParty findThirdPartyById(String id) {
        return mot.findById(id, ThirdParty.class);
    }

    public void saveThirdParty(ThirdParty thirdParty) {
        thirdParty.setAppKey(sequenceId.nextId());
        thirdParty.setCreateTime(DateUtil.now());
        thirdParty.setId(sequenceId.nextId());
        mot.insert(thirdParty);
    }

    public List<ThirdParty> findThirdPartyListByCondition(ThirdParty thirdParty) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ThirdParty.class)).find(MongoKit.buildMatchDocument(thirdParty));
        Pager pager = thirdParty.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<ThirdParty> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ThirdParty.class, document)));
        return list;
    }

    public long findThirdPartyCountByCondition(ThirdParty thirdParty) {
        return mot.getCollection(mot.getCollectionName(ThirdParty.class)).countDocuments(MongoKit.buildMatchDocument(thirdParty));
    }

    public ThirdParty findOneThirdPartyByCondition(ThirdParty thirdParty) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ThirdParty.class)).find(MongoKit.buildMatchDocument(thirdParty));
        MongoKit.appendProjection(query, thirdParty.getPager());
        return mot.getConverter().read(ThirdParty.class, query.first());
    }

    public void updateThirdParty(ThirdParty thirdParty) {
        mot.updateFirst(query(where("id").is(thirdParty.getId())), MongoKit.update(thirdParty, true), ThirdParty.class);
    }

    public void updateThirdPartyForNotNull(ThirdParty thirdParty) {
        mot.updateFirst(query(where("id").is(thirdParty.getId())), MongoKit.update(thirdParty, false), ThirdParty.class);
    }

    public void deleteThirdParty(String id) {
        mot.remove(query(where("id").is(id)), ThirdParty.class);
    }

    public void deleteThirdPartyByCondition(ThirdParty thirdParty) {
        mot.getCollection(mot.getCollectionName(ThirdParty.class)).deleteMany(MongoKit.buildMatchDocument(thirdParty));
    }

    public void batchSaveThirdParty(List<ThirdParty> thirdPartys) {
        thirdPartys.forEach(thirdParty -> thirdParty.setId(sequenceId.nextId()));
        mot.insertAll(thirdPartys);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
