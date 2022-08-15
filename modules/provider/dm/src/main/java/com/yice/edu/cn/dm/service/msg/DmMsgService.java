package com.yice.edu.cn.dm.service.msg;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DmMsgService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmMsg findDmMsgById(String id) {
        return mot.findById(id, DmMsg.class);
    }

    public void saveDmMsg(DmMsg dmMsg) {
        dmMsg.setId(sequenceId.nextId());
        dmMsg.setSendTime(DateUtil.now());
        mot.insert(dmMsg);
    }

    public List<DmMsg> findDmMsgListByCondition(DmMsg dmMsg) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(DmMsg.class)).find(MongoKit.buildMatchDocument(dmMsg));
        Pager pager = dmMsg.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<DmMsg> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(DmMsg.class, document)));
        return list;
    }

    public long findDmMsgCountByCondition(DmMsg dmMsg) {
        return mot.getCollection(mot.getCollectionName(DmMsg.class)).count(MongoKit.buildMatchDocument(dmMsg));
    }

    public DmMsg findOneDmMsgByCondition(DmMsg dmMsg) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(DmMsg.class)).find(MongoKit.buildMatchDocument(dmMsg));
        MongoKit.appendProjection(query, dmMsg.getPager());
        return mot.getConverter().read(DmMsg.class, query.first());
    }

    public void updateDmMsg(DmMsg dmMsg) {
        mot.updateFirst(query(where("id").is(dmMsg.getId())), MongoKit.update(dmMsg), DmMsg.class);
    }

    public void deleteDmMsg(String id) {
        mot.remove(query(where("id").is(id)), DmMsg.class);
    }

    public void deleteDmMsgByCondition(DmMsg dmMsg) {
        mot.getCollection(mot.getCollectionName(DmMsg.class)).deleteMany(MongoKit.buildMatchDocument(dmMsg));
    }


    public void batchSaveDmMsg(List<DmMsg> dmMsgs) {
        final String now = DateUtil.now();
        dmMsgs.forEach(dmMsg -> {
                    dmMsg.setId(sequenceId.nextId());
                    dmMsg.setSendTime(now);
                }
        );
        mot.insertAll(dmMsgs);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public DmMsg sendMsg(DmMsg dmMsg) {
        this.saveDmMsg(dmMsg);
        return dmMsg;
    }


}
