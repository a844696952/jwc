package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceAccountService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceAccount findYcVerifaceAccountById(String id) {
        return mot.findById(id,YcVerifaceAccount.class);
    }
    public void saveYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount) {
        ycVerifaceAccount.setId(sequenceId.nextId());
        ycVerifaceAccount.setCreateTime(DateUtil.now());
            mot.insert(ycVerifaceAccount);
    }
    public List<YcVerifaceAccount> findYcVerifaceAccountListByCondition(YcVerifaceAccount ycVerifaceAccount) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceAccount.class)).find(MongoKit.buildMatchDocument(ycVerifaceAccount));
        Pager pager = ycVerifaceAccount.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceAccount> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceAccount.class, document)));
        return list;
    }
    public long findYcVerifaceAccountCountByCondition(YcVerifaceAccount ycVerifaceAccount) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceAccount.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceAccount));
    }
    public YcVerifaceAccount findOneYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceAccount.class)).find(MongoKit.buildMatchDocument(ycVerifaceAccount));
       MongoKit.appendProjection(query,ycVerifaceAccount.getPager());
       return mot.getConverter().read(YcVerifaceAccount.class,query.first());
    }

    public void updateYcVerifaceAccount(YcVerifaceAccount ycVerifaceAccount) {
        ycVerifaceAccount.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceAccount.getId())), MongoKit.update(ycVerifaceAccount),YcVerifaceAccount.class);
    }
    public void deleteYcVerifaceAccount(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceAccount.class);
    }
    public void deleteYcVerifaceAccountByCondition(YcVerifaceAccount ycVerifaceAccount) {
        mot.getCollection(mot.getCollectionName(YcVerifaceAccount.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceAccount));
    }
    public void batchSaveYcVerifaceAccount(List<YcVerifaceAccount> ycVerifaceAccounts){
        ycVerifaceAccounts.forEach(ycVerifaceAccount -> ycVerifaceAccount.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceAccounts);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
