package com.yice.edu.cn.xw.service.kq;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqSpecialDateService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public KqSpecialDate findKqSpecialDateById(String id) {
        return mot.findById(id, KqSpecialDate.class);
    }

    public void saveKqSpecialDate(KqSpecialDate kqSpecialDate) {
        kqSpecialDate.setId(sequenceId.nextId());
        kqSpecialDate.setCreatTime(DateUtil.now());
        mot.insert(kqSpecialDate);
    }

    public List<KqSpecialDate> findKqSpecialDateListByCondition(KqSpecialDate kqSpecialDate) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqSpecialDate.class)).find(MongoKit.buildMatchDocument(kqSpecialDate));
        Pager pager = kqSpecialDate.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqSpecialDate> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqSpecialDate.class, document)));
        return list;
    }

    public long findKqSpecialDateCountByCondition(KqSpecialDate kqSpecialDate) {
        return mot.getCollection(mot.getCollectionName(KqSpecialDate.class)).count(MongoKit.buildMatchDocument(kqSpecialDate));
    }

    public KqSpecialDate findOneKqSpecialDateByCondition(KqSpecialDate kqSpecialDate) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqSpecialDate.class)).find(MongoKit.buildMatchDocument(kqSpecialDate));
        MongoKit.appendProjection(query, kqSpecialDate.getPager());
        return mot.getConverter().read(KqSpecialDate.class, query.first());
    }

    public void updateKqSpecialDate(KqSpecialDate kqSpecialDate) {
        kqSpecialDate.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqSpecialDate.getId())), MongoKit.update(kqSpecialDate), KqSpecialDate.class);
    }

    public void deleteKqSpecialDate(String id) {
        mot.remove(query(where("id").is(id)), KqSpecialDate.class);
    }

    public void deleteKqSpecialDateByCondition(KqSpecialDate kqSpecialDate) {
        mot.getCollection(mot.getCollectionName(KqSpecialDate.class)).deleteMany(MongoKit.buildMatchDocument(kqSpecialDate));
    }

    public void batchSaveKqSpecialDate(List<KqSpecialDate> kqSpecialDates) {
        kqSpecialDates.forEach(kqSpecialDate -> kqSpecialDate.setId(sequenceId.nextId()));
        mot.insertAll(kqSpecialDates);
    }

    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
}
