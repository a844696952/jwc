package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceSeries.YcVerifaceDeviceSeries;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceDeviceSeriesService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceDeviceSeries findYcVerifaceDeviceSeriesById(String id) {
        return mot.findById(id,YcVerifaceDeviceSeries.class);
    }
    public void saveYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        ycVerifaceDeviceSeries.setCreateTime(DateUtil.now());
        ycVerifaceDeviceSeries.setId(sequenceId.nextId());
        mot.insert(ycVerifaceDeviceSeries);
    }
    public List<YcVerifaceDeviceSeries> findYcVerifaceDeviceSeriesListByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDeviceSeries.class)).find(MongoKit.buildMatchDocument(ycVerifaceDeviceSeries));
        Pager pager = ycVerifaceDeviceSeries.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceDeviceSeries> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceDeviceSeries.class, document)));
        return list;
    }
    public long findYcVerifaceDeviceSeriesCountByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceDeviceSeries.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceDeviceSeries));
    }
    public YcVerifaceDeviceSeries findOneYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDeviceSeries.class)).find(MongoKit.buildMatchDocument(ycVerifaceDeviceSeries));
       MongoKit.appendProjection(query,ycVerifaceDeviceSeries.getPager());
       return mot.getConverter().read(YcVerifaceDeviceSeries.class,query.first());
    }

    public void updateYcVerifaceDeviceSeries(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        ycVerifaceDeviceSeries.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceDeviceSeries.getId())), MongoKit.update(ycVerifaceDeviceSeries),YcVerifaceDeviceSeries.class);
    }
    public void deleteYcVerifaceDeviceSeries(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceDeviceSeries.class);
    }
    public void deleteYcVerifaceDeviceSeriesByCondition(YcVerifaceDeviceSeries ycVerifaceDeviceSeries) {
        mot.getCollection(mot.getCollectionName(YcVerifaceDeviceSeries.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceDeviceSeries));
    }
    public void batchSaveYcVerifaceDeviceSeries(List<YcVerifaceDeviceSeries> ycVerifaceDeviceSeriess){
        ycVerifaceDeviceSeriess.forEach(ycVerifaceDeviceSeries -> ycVerifaceDeviceSeries.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceDeviceSeriess);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
