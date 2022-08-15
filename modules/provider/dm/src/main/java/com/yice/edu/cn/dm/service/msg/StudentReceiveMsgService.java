package com.yice.edu.cn.dm.service.msg;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
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
public class StudentReceiveMsgService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StudentReceiveMsg findReceiveMsgById(String id) {
        return mot.findById(id,StudentReceiveMsg.class);
    }
    public void saveReceiveMsg(StudentReceiveMsg receiveMsg) {
        receiveMsg.setId(sequenceId.nextId());
        mot.insert(receiveMsg);
    }
    public List<StudentReceiveMsg> findReceiveMsgListByCondition(StudentReceiveMsg receiveMsg) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StudentReceiveMsg.class)).find(MongoKit.buildMatchDocument(receiveMsg));
        Pager pager = receiveMsg.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StudentReceiveMsg> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StudentReceiveMsg.class, document)));
        return list;
    }
    public long findReceiveMsgCountByCondition(StudentReceiveMsg receiveMsg) {
        return mot.getCollection(mot.getCollectionName(StudentReceiveMsg.class)).count(MongoKit.buildMatchDocument(receiveMsg));
    }
    public StudentReceiveMsg findOneReceiveMsgByCondition(StudentReceiveMsg receiveMsg) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StudentReceiveMsg.class)).find(MongoKit.buildMatchDocument(receiveMsg));
       MongoKit.appendProjection(query,receiveMsg.getPager());
       return mot.getConverter().read(StudentReceiveMsg.class,query.first());
    }

    public void updateReceiveMsg(StudentReceiveMsg receiveMsg) {
        mot.updateFirst(query(where("id").is(receiveMsg.getId())), MongoKit.update(receiveMsg),StudentReceiveMsg.class);
    }
    public void deleteReceiveMsg(String id) {
        mot.remove(query(where("id").is(id)),StudentReceiveMsg.class);
    }
    public void deleteReceiveMsgByCondition(StudentReceiveMsg receiveMsg) {
        mot.getCollection(mot.getCollectionName(StudentReceiveMsg.class)).deleteMany(MongoKit.buildMatchDocument(receiveMsg));
    }
    public void batchSaveReceiveMsg(List<StudentReceiveMsg> receiveMsgs){
        receiveMsgs.forEach(receiveMsg -> receiveMsg.setId(sequenceId.nextId()));
        mot.insertAll(receiveMsgs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void readMsgs(List<String> ids) {
        mot.updateMulti(
                query(where("id").in(ids)),
                new Update().set("reade", true).set("readTime", DateUtil.now()),
                StudentReceiveMsg.class);
    }

    public void readMsg(String id) {
        mot.updateFirst(
                query(where("id").is(id)),
                new Update().set("reade", true).set("readTime", DateUtil.now()),
                StudentReceiveMsg.class);
    }

}
