package com.yice.edu.cn.jw.service.thirdParty;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchoolTeacher;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplySchoolTeacherService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ApplySchoolTeacher findApplySchoolTeacherById(String id) {
        return mot.findById(id,ApplySchoolTeacher.class);
    }
    public void saveApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacher.setCreateTime(DateUtil.now());
        applySchoolTeacher.setId(sequenceId.nextId());
        mot.insert(applySchoolTeacher);
    }
    public List<ApplySchoolTeacher> findApplySchoolTeacherListByCondition(ApplySchoolTeacher applySchoolTeacher) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).find(MongoKit.buildMatchDocument(applySchoolTeacher));
        Pager pager = applySchoolTeacher.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ApplySchoolTeacher> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ApplySchoolTeacher.class, document)));
        return list;
    }
    public long findApplySchoolTeacherCountByCondition(ApplySchoolTeacher applySchoolTeacher) {
        return mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).countDocuments(MongoKit.buildMatchDocument(applySchoolTeacher));
    }
    public ApplySchoolTeacher findOneApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).find(MongoKit.buildMatchDocument(applySchoolTeacher));
       MongoKit.appendProjection(query,applySchoolTeacher.getPager());
       return mot.getConverter().read(ApplySchoolTeacher.class,query.first());
    }

    public void updateApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher) {
        mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).updateOne(new Document("_id",applySchoolTeacher.getId()), MongoKit.buildUpdateDocument(applySchoolTeacher,false));
    }
    public void updateApplySchoolTeacherForAll(ApplySchoolTeacher applySchoolTeacher) {
        mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).updateOne(new Document("_id",applySchoolTeacher.getId()), MongoKit.buildUpdateDocument(applySchoolTeacher,true));
    }
    public void deleteApplySchoolTeacher(String id) {
        mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).deleteOne(new Document("_id",id));
    }
    public void deleteApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher) {
        mot.getCollection(mot.getCollectionName(ApplySchoolTeacher.class)).deleteMany(MongoKit.buildMatchDocument(applySchoolTeacher));
    }
    public void batchSaveApplySchoolTeacher(List<ApplySchoolTeacher> applySchoolTeachers){
        applySchoolTeachers.forEach(applySchoolTeacher -> applySchoolTeacher.setId(sequenceId.nextId()));
        mot.insertAll(applySchoolTeachers);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
