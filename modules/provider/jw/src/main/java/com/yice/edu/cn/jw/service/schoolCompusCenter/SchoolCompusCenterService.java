package com.yice.edu.cn.jw.service.schoolCompusCenter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolCompusCenterService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolCompusCenter findSchoolCompusCenterById(String id) {
        return mot.findById(id,SchoolCompusCenter.class);
    }
    public void saveSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter) {
        schoolCompusCenter.setId(sequenceId.nextId());
        mot.insert(schoolCompusCenter);
    }
    public List<SchoolCompusCenter> findSchoolCompusCenterListByCondition(SchoolCompusCenter schoolCompusCenter) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolCompusCenter.class)).find(MongoKit.buildMatchDocument(schoolCompusCenter));
        Pager pager = schoolCompusCenter.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolCompusCenter> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolCompusCenter.class, document)));
        return list;
    }
    public long findSchoolCompusCenterCountByCondition(SchoolCompusCenter schoolCompusCenter) {
        return mot.getCollection(mot.getCollectionName(SchoolCompusCenter.class)).countDocuments(MongoKit.buildMatchDocument(schoolCompusCenter));
    }
    public SchoolCompusCenter findOneSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolCompusCenter.class)).find(MongoKit.buildMatchDocument(schoolCompusCenter));
       MongoKit.appendProjection(query,schoolCompusCenter.getPager());
       return mot.getConverter().read(SchoolCompusCenter.class,query.first());
    }

    public void updateSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter) {
        mot.updateFirst(query(where("id").is(schoolCompusCenter.getId())), MongoKit.update(schoolCompusCenter),SchoolCompusCenter.class);
    }
    public void deleteSchoolCompusCenter(String id) {
        mot.remove(query(where("id").is(id)),SchoolCompusCenter.class);
    }
    public void deleteSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter) {
        mot.getCollection(mot.getCollectionName(SchoolCompusCenter.class)).deleteMany(MongoKit.buildMatchDocument(schoolCompusCenter));
    }
    public void batchSaveSchoolCompusCenter(List<SchoolCompusCenter> schoolCompusCenters){
        schoolCompusCenters.forEach(schoolCompusCenter -> schoolCompusCenter.setId(sequenceId.nextId()));
        mot.insertAll(schoolCompusCenters);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public ResponseJson findSchoolCompusCenter(String schoolId){
        Document document = new Document("schoolId",schoolId);
       Document findIterable =  mot.getCollection("schoolCompusCenter").find(document).first();
       if(findIterable!=null){
           String data =  findIterable.getString("data");
           System.out.println(data);
           return  new ResponseJson(JSONUtil.parseObj(data));
       }
       return new ResponseJson();
    }

    public void  saveSchoolCompusCenterData(SchoolCompusCenter schoolCompusCenter){
        SchoolCompusCenter schoolCompusCenter1 = new SchoolCompusCenter();
        schoolCompusCenter1.setSchoolId(schoolCompusCenter.getSchoolId());
        SchoolCompusCenter schoolCompusCenter2 = findOneSchoolCompusCenterByCondition(schoolCompusCenter1);
        if(schoolCompusCenter2!=null){
            schoolCompusCenter.setId(schoolCompusCenter2.getId());
            updateSchoolCompusCenter(schoolCompusCenter);
        }else{
            saveSchoolCompusCenter(schoolCompusCenter);
        }
    }
}
