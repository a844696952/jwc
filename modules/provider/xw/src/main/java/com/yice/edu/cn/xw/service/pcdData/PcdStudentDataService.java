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
import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PcdStudentDataService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PcdStudentData findPcdStudentDataById(String id) {
        return mot.findById(id,PcdStudentData.class);
    }
    public void savePcdStudentData(PcdStudentData pcdStudentData) {
        pcdStudentData.setCreateTime(DateUtil.now());
        pcdStudentData.setId(sequenceId.nextId());
        mot.insert(pcdStudentData);
    }
    public List<PcdStudentData> findPcdStudentDataListByCondition(PcdStudentData pcdStudentData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdStudentData.class)).find(MongoKit.buildMatchDocument(pcdStudentData));
        Pager pager = pcdStudentData.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PcdStudentData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PcdStudentData.class, document)));
        return list;
    }
    public long findPcdStudentDataCountByCondition(PcdStudentData pcdStudentData) {
        return mot.getCollection(mot.getCollectionName(PcdStudentData.class)).countDocuments(MongoKit.buildMatchDocument(pcdStudentData));
    }
    public PcdStudentData findOnePcdStudentDataByCondition(PcdStudentData pcdStudentData) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PcdStudentData.class)).find(MongoKit.buildMatchDocument(pcdStudentData));
       MongoKit.appendProjection(query,pcdStudentData.getPager());
       return mot.getConverter().read(PcdStudentData.class,query.first());
    }

    public void updatePcdStudentData(PcdStudentData pcdStudentData) {
        pcdStudentData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdStudentData.class)).updateOne(new Document("_id",pcdStudentData.getId()), MongoKit.buildUpdateDocument(pcdStudentData,false));
    }
    public void updatePcdStudentDataForAll(PcdStudentData pcdStudentData) {
        pcdStudentData.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(PcdStudentData.class)).updateOne(new Document("_id",pcdStudentData.getId()), MongoKit.buildUpdateDocument(pcdStudentData,true));
    }
    public void deletePcdStudentData(String id) {
        mot.getCollection(mot.getCollectionName(PcdStudentData.class)).deleteOne(new Document("_id",id));
    }
    public void deletePcdStudentDataByCondition(PcdStudentData pcdStudentData) {
        mot.getCollection(mot.getCollectionName(PcdStudentData.class)).deleteMany(MongoKit.buildMatchDocument(pcdStudentData));
    }
    public void batchSavePcdStudentData(List<PcdStudentData> pcdStudentDatas){
        pcdStudentDatas.forEach(pcdStudentData -> pcdStudentData.setId(sequenceId.nextId()));
        mot.insertAll(pcdStudentDatas);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public void savePcdStudentDataKong(PcdStudentData pcdStudentData){
        pcdStudentData.setUpdateTime(DateUtil.now());
        Map<String,Object> map = pcdStudentData.getData();
        Map<String,Long> count = new HashMap<>();
        //获取小初高学生数量
        Object primary = map.get(Constant.PCD_DATA.PRIMARY);
        Object middle = map.get(Constant.PCD_DATA.MIDDLE);
        Object high = map.get(Constant.PCD_DATA.HIGH);
        JSONObject jsonPrimary = JSONUtil.parseObj(primary);
        JSONObject jsonMiddle = JSONUtil.parseObj(middle);
        JSONObject jsonHigh = JSONUtil.parseObj(high);
        count.put(Constant.PCD_DATA.PRIMARY,getTeacherCount(jsonPrimary));
        count.put(Constant.PCD_DATA.MIDDLE,getTeacherCount(jsonMiddle));
        count.put(Constant.PCD_DATA.HIGH,getTeacherCount(jsonHigh));
        pcdStudentData.setCount(count);
        mot.save(pcdStudentData);
    }


    public Long getTeacherCount(JSONObject jsonObject){
        Object teacherNum = jsonObject.get(Constant.PCD_DATA.STUDENTNUM);
        JSONObject jsonObject1 = JSONUtil.parseObj(teacherNum);
        Long man = Long.parseLong(jsonObject1.get("man").toString());
        Long feman = Long.parseLong(jsonObject1.get("feman").toString());
        Long count = man+feman;
        return count;
    }
}
