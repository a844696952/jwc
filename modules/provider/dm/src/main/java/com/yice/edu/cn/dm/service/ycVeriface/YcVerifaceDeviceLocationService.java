package com.yice.edu.cn.dm.service.ycVeriface;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceLocation.YcVerifaceDeviceLocation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class YcVerifaceDeviceLocationService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public YcVerifaceDeviceLocation findYcVerifaceDeviceLocationById(String id) {
        return mot.findById(id,YcVerifaceDeviceLocation.class);
    }
    public void saveYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        ycVerifaceDeviceLocation.setCreateTime(DateUtil.now());
        ycVerifaceDeviceLocation.setId(sequenceId.nextId());
        mot.insert(ycVerifaceDeviceLocation);
    }
    public List<YcVerifaceDeviceLocation> findYcVerifaceDeviceLocationListByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDeviceLocation.class)).find(MongoKit.buildMatchDocument(ycVerifaceDeviceLocation));
        Pager pager = ycVerifaceDeviceLocation.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<YcVerifaceDeviceLocation> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(YcVerifaceDeviceLocation.class, document)));
        return list;
    }
    public long findYcVerifaceDeviceLocationCountByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        return mot.getCollection(mot.getCollectionName(YcVerifaceDeviceLocation.class)).countDocuments(MongoKit.buildMatchDocument(ycVerifaceDeviceLocation));
    }
    public YcVerifaceDeviceLocation findOneYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(YcVerifaceDeviceLocation.class)).find(MongoKit.buildMatchDocument(ycVerifaceDeviceLocation));
       MongoKit.appendProjection(query,ycVerifaceDeviceLocation.getPager());
       return mot.getConverter().read(YcVerifaceDeviceLocation.class,query.first());
    }

    public void updateYcVerifaceDeviceLocation(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        ycVerifaceDeviceLocation.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(ycVerifaceDeviceLocation.getId())), MongoKit.update(ycVerifaceDeviceLocation),YcVerifaceDeviceLocation.class);
    }
    public void deleteYcVerifaceDeviceLocation(String id) {
        mot.remove(query(where("id").is(id)),YcVerifaceDeviceLocation.class);
    }
    public void deleteYcVerifaceDeviceLocationByCondition(YcVerifaceDeviceLocation ycVerifaceDeviceLocation) {
        mot.getCollection(mot.getCollectionName(YcVerifaceDeviceLocation.class)).deleteMany(MongoKit.buildMatchDocument(ycVerifaceDeviceLocation));
    }
    public void batchSaveYcVerifaceDeviceLocation(List<YcVerifaceDeviceLocation> ycVerifaceDeviceLocations){
        ycVerifaceDeviceLocations.forEach(ycVerifaceDeviceLocation -> ycVerifaceDeviceLocation.setId(sequenceId.nextId()));
        mot.insertAll(ycVerifaceDeviceLocations);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
