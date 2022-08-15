package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.netflix.discovery.converters.Auto;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.alarmReceiver.YcVerifaceAlarmReceiver;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceAlarmReceiverService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private YcVerifaceAlarmReceiverService ycVerifaceAlarmReceiverService;
    @Autowired
    private YcVerifaceBlacklistService ycVerifaceBlacklistService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceAlarmReceiver findYcVerifaceAlarmReceiverById(String id) {
        return mot.findById(id, YcVerifaceAlarmReceiver.class);
    }

    public void saveYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiver.setCreateTime(DateUtil.now());
        ycVerifaceAlarmReceiver.setId(sequenceId.nextId());
        mot.insert(ycVerifaceAlarmReceiver);
    }

    public List<YcVerifaceAlarmReceiver> findYcVerifaceAlarmReceiverListByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceAlarmReceiver.class)).find(MongoKit.buildMatchDocument(ycVerifaceAlarmReceiver));
        Pager pager = ycVerifaceAlarmReceiver.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<YcVerifaceAlarmReceiver> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceAlarmReceiver.class, document)));
        return list;
    }

    public long findYcVerifaceAlarmReceiverCountByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceAlarmReceiver.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceAlarmReceiver));
    }

    public YcVerifaceAlarmReceiver findOneYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceAlarmReceiver.class)).find(MongoKit.buildMatchDocument(ycVerifaceAlarmReceiver));
        MongoKit.appendProjection(query, ycVerifaceAlarmReceiver.getPager());
        return mot.getConverter().read(YcVerifaceAlarmReceiver.class, query.first());
    }

    public void updateYcVerifaceAlarmReceiver(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        ycVerifaceAlarmReceiver.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceAlarmReceiver.getId())), MongoKit.update(ycVerifaceAlarmReceiver), YcVerifaceAlarmReceiver.class);
    }

    public void deleteYcVerifaceAlarmReceiver(String id) {
        mot.remove(query(where("id").is(id)), YcVerifaceAlarmReceiver.class);
    }

    public void deleteYcVerifaceAlarmReceiverByCondition(YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver) {
        mot.getCollection(mot.getCollectionName(YcVerifaceAlarmReceiver.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceAlarmReceiver));
    }

    public void batchSaveYcVerifaceAlarmReceiver(List<YcVerifaceAlarmReceiver> ycVerifaceAlarmReceivers) {
        ycVerifaceAlarmReceivers.forEach(ycVerifaceAlarmReceiver -> ycVerifaceAlarmReceiver.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceAlarmReceivers);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void sendPushToAlarmReceiver(String id, String type) {
        YcVerifaceAlarmReceiver ycVerifaceAlarmReceiver = new YcVerifaceAlarmReceiver();
        ycVerifaceAlarmReceiver.setSchoolId(id);
        List<YcVerifaceAlarmReceiver> receiverList = ycVerifaceAlarmReceiverService.findYcVerifaceAlarmReceiverListByCondition(ycVerifaceAlarmReceiver);
        if (receiverList != null && receiverList.size() > 0) {
            List<String> list = receiverList.stream().map(YcVerifaceAlarmReceiver::getPersonId).collect(Collectors.toList());
            String[] notifyArr = new String[list.size()];
            if (type.equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STRANGER)) {
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(list.toArray(notifyArr), "安全预警", "疑似陌生人员入校,请及时处理!", Extras.NOTIFY_STRANGERS);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            } else if (type.equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_BLACKPERSON)) {
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(list.toArray(notifyArr), "安全预警", "疑似黑名单人员入校,请及时处理!", Extras.NOTIFY_BLACKLIST);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
        }
    }
}
