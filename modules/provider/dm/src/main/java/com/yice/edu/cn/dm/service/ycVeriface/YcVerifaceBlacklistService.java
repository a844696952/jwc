package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceBlacklistService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceBlacklist findYcVerifaceBlacklistById(String id) {
        return mot.findById(id,YcVerifaceBlacklist.class);
    }
    public void saveYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklist.setCreateTime(DateUtil.now());
        ycVerifaceBlacklist.setId(sequenceId.nextId());
        mot.insert(ycVerifaceBlacklist);
    }
    public List<YcVerifaceBlacklist> findYcVerifaceBlacklistListByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceBlacklist.class)).find(MongoKit.buildMatchDocument(ycVerifaceBlacklist));
        Pager pager = ycVerifaceBlacklist.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceBlacklist> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceBlacklist.class, document)));
        return list;
    }
    public long findYcVerifaceBlacklistCountByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceBlacklist.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceBlacklist));
    }
    public YcVerifaceBlacklist findOneYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceBlacklist.class)).find(MongoKit.buildMatchDocument(ycVerifaceBlacklist));
       MongoKit.appendProjection(query,ycVerifaceBlacklist.getPager());
       return mot.getConverter().read(YcVerifaceBlacklist.class,query.first());
    }

    public void updateYcVerifaceBlacklist(YcVerifaceBlacklist ycVerifaceBlacklist) {
        ycVerifaceBlacklist.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceBlacklist.getId())), MongoKit.update(ycVerifaceBlacklist),YcVerifaceBlacklist.class);
    }
    public void deleteYcVerifaceBlacklist(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceBlacklist.class);
    }
    public void deleteYcVerifaceBlacklistByCondition(YcVerifaceBlacklist ycVerifaceBlacklist) {
        mot.getCollection(mot.getCollectionName(YcVerifaceBlacklist.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceBlacklist));
    }
    public void batchSaveYcVerifaceBlacklist(List<YcVerifaceBlacklist> ycVerifaceBlacklists){
        ycVerifaceBlacklists.forEach(ycVerifaceBlacklist -> ycVerifaceBlacklist.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceBlacklists);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
