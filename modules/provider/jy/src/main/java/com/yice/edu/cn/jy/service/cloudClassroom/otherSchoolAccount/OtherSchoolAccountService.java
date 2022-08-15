package com.yice.edu.cn.jy.service.cloudClassroom.otherSchoolAccount;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class OtherSchoolAccountService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public OtherSchoolAccount findOtherSchoolAccountById(String id) {
        return mot.findById(id,OtherSchoolAccount.class);
    }
    public void saveOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount) {
        otherSchoolAccount.setCreateTime(DateUtil.now());
        otherSchoolAccount.setId(sequenceId.nextId());
        mot.insert(otherSchoolAccount);
    }
    public List<OtherSchoolAccount> findOtherSchoolAccountListByCondition(OtherSchoolAccount otherSchoolAccount) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(OtherSchoolAccount.class)).find(MongoKit.buildMatchDocument(otherSchoolAccount));
        Pager pager = otherSchoolAccount.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<OtherSchoolAccount> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(OtherSchoolAccount.class, document)));
        return list;
    }
    public long findOtherSchoolAccountCountByCondition(OtherSchoolAccount otherSchoolAccount) {
        return mot.getCollection(mot.getCollectionName(OtherSchoolAccount.class)).count(MongoKit.buildMatchDocument(otherSchoolAccount));
    }
    public OtherSchoolAccount findOneOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(OtherSchoolAccount.class)).find(MongoKit.buildMatchDocument(otherSchoolAccount));
       MongoKit.appendProjection(query,otherSchoolAccount.getPager());
       return mot.getConverter().read(OtherSchoolAccount.class,query.first());
    }

    public void updateOtherSchoolAccount(OtherSchoolAccount otherSchoolAccount) {
        mot.updateFirst(query(where("id").is(otherSchoolAccount.getId())), MongoKit.update(otherSchoolAccount),OtherSchoolAccount.class);
    }
    public void deleteOtherSchoolAccount(String id) {
        mot.remove(query(where("id").is(id)),OtherSchoolAccount.class);
    }
    public void deleteOtherSchoolAccountByCondition(OtherSchoolAccount otherSchoolAccount) {
        mot.getCollection(mot.getCollectionName(OtherSchoolAccount.class)).deleteMany(MongoKit.buildMatchDocument(otherSchoolAccount));
    }
    public void batchSaveOtherSchoolAccount(List<OtherSchoolAccount> otherSchoolAccounts){
        otherSchoolAccounts.forEach(otherSchoolAccount -> otherSchoolAccount.setId(sequenceId.nextId()));
        mot.insertAll(otherSchoolAccounts);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
