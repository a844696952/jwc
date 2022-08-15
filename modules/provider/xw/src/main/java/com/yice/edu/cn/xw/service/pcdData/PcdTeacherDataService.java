package com.yice.edu.cn.xw.service.pcdData;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PcdTeacherDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdTeacherData findPcdTeacherDataById(String id) {
        return mot.findById(id,PcdTeacherData.class);
    }
    public void savePcdTeacherData(PcdTeacherData pcdTeacherData) {
        pcdTeacherData.setCreateTime(DateUtil.now());
        pcdTeacherData.setId(sequenceId.nextId());
        mot.insert(pcdTeacherData);
    }
    public List<PcdTeacherData> findPcdTeacherDataListByCondition(PcdTeacherData pcdTeacherData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).find(MongoKit.buildMatchDocument(pcdTeacherData));
        Pager pager = pcdTeacherData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdTeacherData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdTeacherData.class, document)));
        return list;
    }
    public long findPcdTeacherDataCountByCondition(PcdTeacherData pcdTeacherData) {
        return mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).countDocuments(MongoKit.buildMatchDocument(pcdTeacherData));
    }
    public PcdTeacherData findOnePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).find(MongoKit.buildMatchDocument(pcdTeacherData));
       MongoKit.appendProjection(query,pcdTeacherData.getPager());
       return mot.getConverter().read(PcdTeacherData.class,query.first());
    }

    public void updatePcdTeacherData(PcdTeacherData pcdTeacherData) {
        pcdTeacherData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).updateOne(new Document("_id",pcdTeacherData.getId()), MongoKit.buildUpdateDocument(pcdTeacherData,false));
    }
    public void updatePcdTeacherDataForAll(PcdTeacherData pcdTeacherData) {
        pcdTeacherData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).updateOne(new Document("_id",pcdTeacherData.getId()), MongoKit.buildUpdateDocument(pcdTeacherData,true));
    }
    public void deletePcdTeacherData(String id) {
        mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdTeacherDataByCondition(PcdTeacherData pcdTeacherData) {
        mot.getCollection(mot.getCollectionName(PcdTeacherData.class)).deleteMany(MongoKit.buildMatchDocument(pcdTeacherData));
    }
    public void batchSavePcdTeacherData(List<PcdTeacherData> pcdTeacherDatas){
        pcdTeacherDatas.forEach(pcdTeacherData -> pcdTeacherData.setId(sequenceId.nextId()));
        mot.insertAll(pcdTeacherDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void savePcdTeacherDataKong(PcdTeacherData pcdTeacherData){
        pcdTeacherData.setUpdateTime(DateUtil.now());
        Map<String,Object> map = pcdTeacherData.getData();
        Map<String,Long> count = new HashMap<>();
        //添加小初高教师总数量
        Object primary = map.get(Constant.PCD_DATA.PRIMARY);
        Object middle = map.get(Constant.PCD_DATA.MIDDLE);
        Object high = map.get(Constant.PCD_DATA.HIGH);
        JSONObject jsonPrimary = JSONUtil.parseObj(primary);
        JSONObject jsonMiddle = JSONUtil.parseObj(middle);
        JSONObject jsonHigh = JSONUtil.parseObj(high);
        count.put(Constant.PCD_DATA.PRIMARY,getTeacherCount(jsonPrimary));
        count.put(Constant.PCD_DATA.MIDDLE,getTeacherCount(jsonMiddle));
        count.put(Constant.PCD_DATA.HIGH,getTeacherCount(jsonHigh));
        pcdTeacherData.setCount(count);
        mot.save(pcdTeacherData);
    }

    public Long getTeacherCount(JSONObject jsonObject){
        Object teacherNum = jsonObject.get(Constant.PCD_DATA.TEACHERNUM);
        JSONObject jsonObject1 = JSONUtil.parseObj(teacherNum);
        Long man = Long.parseLong(jsonObject1.get("man").toString());
        Long feman = Long.parseLong(jsonObject1.get("feman").toString());
        Long count = man+feman;
        return count;
    }
}
