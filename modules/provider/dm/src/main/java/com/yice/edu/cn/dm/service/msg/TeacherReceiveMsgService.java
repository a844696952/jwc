package com.yice.edu.cn.dm.service.msg;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.msg.TeacherReceiveMsg;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class TeacherReceiveMsgService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public TeacherReceiveMsg findReceiveMsgById(String id) {
        return mot.findById(id,TeacherReceiveMsg.class);
    }
    public void saveReceiveMsg(TeacherReceiveMsg receiveMsg) {
        receiveMsg.setId(sequenceId.nextId());
        mot.insert(receiveMsg);
    }
    public List<TeacherReceiveMsg> findReceiveMsgListByCondition(TeacherReceiveMsg receiveMsg) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TeacherReceiveMsg.class)).find(MongoKit.buildMatchDocument(receiveMsg));
        Pager pager = receiveMsg.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<TeacherReceiveMsg> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(TeacherReceiveMsg.class, document)));
        return list;
    }
    public long findReceiveMsgCountByCondition(TeacherReceiveMsg receiveMsg) {
        return mot.getCollection(mot.getCollectionName(TeacherReceiveMsg.class)).count(MongoKit.buildMatchDocument(receiveMsg));
    }
    public TeacherReceiveMsg findOneReceiveMsgByCondition(TeacherReceiveMsg receiveMsg) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(TeacherReceiveMsg.class)).find(MongoKit.buildMatchDocument(receiveMsg));
       MongoKit.appendProjection(query,receiveMsg.getPager());
       return mot.getConverter().read(TeacherReceiveMsg.class,query.first());
    }

    public void updateReceiveMsg(TeacherReceiveMsg receiveMsg) {
        mot.updateFirst(query(where("id").is(receiveMsg.getId())), MongoKit.update(receiveMsg),TeacherReceiveMsg.class);
    }
    public void deleteReceiveMsg(String id) {
        mot.remove(query(where("id").is(id)),TeacherReceiveMsg.class);
    }
    public void deleteReceiveMsgByCondition(TeacherReceiveMsg receiveMsg) {
        mot.getCollection(mot.getCollectionName(TeacherReceiveMsg.class)).deleteMany(MongoKit.buildMatchDocument(receiveMsg));
    }
    public void batchSaveReceiveMsg(List<TeacherReceiveMsg> receiveMsgs){
        receiveMsgs.forEach(receiveMsg -> receiveMsg.setId(sequenceId.nextId()));
        mot.insertAll(receiveMsgs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void readMsgs(List<String> ids) {
        mot.updateMulti(
                query(where("id").in(ids)),
                new Update().set("reade", true).set("readTime", DateUtil.now()),
                TeacherReceiveMsg.class);
    }

    public void readMsg(String id) {
        mot.updateFirst(
                query(where("id").is(id)),
                new Update().set("reade", true).set("readTime", DateUtil.now()),
                TeacherReceiveMsg.class);
    }

}
