package com.yice.edu.cn.jw.service.holiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class HolidayService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public Holiday findHolidayById(String id) {
        return mot.findById(id, Holiday.class);
    }

    public void saveHoliday(Holiday holiday) {
        holiday.setCreateTime(DateUtil.now());
        holiday.setId(sequenceId.nextId());
        mot.insert(holiday);
    }

    public List<Holiday> findHolidayListByCondition(Holiday holiday) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Holiday.class)).find(MongoKit.buildMatchDocument(holiday));
        Pager pager = holiday.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<Holiday> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(Holiday.class, document)));
        return list;
    }

    public long findHolidayCountByCondition(Holiday holiday) {
        return mot.getCollection(mot.getCollectionName(Holiday.class)).countDocuments(MongoKit.buildMatchDocument(holiday));
    }

    public Holiday findOneHolidayByCondition(Holiday holiday) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Holiday.class)).find(MongoKit.buildMatchDocument(holiday));
        MongoKit.appendProjection(query, holiday.getPager());
        return mot.getConverter().read(Holiday.class, query.first());
    }

    public void updateHoliday(Holiday holiday) {
        holiday.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(holiday.getId())), MongoKit.update(holiday), Holiday.class);
    }

    public void deleteHoliday(String id) {
        mot.remove(query(where("id").is(id)), Holiday.class);
    }

    public void deleteHolidayByCondition(Holiday holiday) {
        mot.getCollection(mot.getCollectionName(Holiday.class)).deleteMany(MongoKit.buildMatchDocument(holiday));
    }

    public void batchSaveHoliday(List<Holiday> holidays) {
        holidays.forEach(holiday -> holiday.setId(sequenceId.nextId()));
        mot.insertAll(holidays);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
