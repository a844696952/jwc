package com.yice.edu.cn.xw.service.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StuNotComeToSchoolService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuNotComeToSchool findStuNotComeToSchoolById(String id) {
        return mot.findById(id,StuNotComeToSchool.class);
    }
    public void saveStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool) {
        stuNotComeToSchool.setCreateTime(DateUtil.now());
        stuNotComeToSchool.setId(sequenceId.nextId());
        mot.insert(stuNotComeToSchool);
    }
    public List<StuNotComeToSchool> findStuNotComeToSchoolListByCondition(StuNotComeToSchool stuNotComeToSchool) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuNotComeToSchool.class)).find(MongoKit.buildMatchDocument(stuNotComeToSchool));
        Pager pager = stuNotComeToSchool.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StuNotComeToSchool> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuNotComeToSchool.class, document)));
        return list;
    }
    public long findStuNotComeToSchoolCountByCondition(StuNotComeToSchool stuNotComeToSchool) {
        return mot.getCollection(mot.getCollectionName(StuNotComeToSchool.class)).countDocuments(MongoKit.buildMatchDocument(stuNotComeToSchool));
    }
    public StuNotComeToSchool findOneStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuNotComeToSchool.class)).find(MongoKit.buildMatchDocument(stuNotComeToSchool));
       MongoKit.appendProjection(query,stuNotComeToSchool.getPager());
       return mot.getConverter().read(StuNotComeToSchool.class,query.first());
    }

    public void updateStuNotComeToSchool(StuNotComeToSchool stuNotComeToSchool) {
        mot.updateFirst(query(where("id").is(stuNotComeToSchool.getId())), MongoKit.update(stuNotComeToSchool),StuNotComeToSchool.class);
    }
    public void deleteStuNotComeToSchool(String id) {
        mot.remove(query(where("id").is(id)),StuNotComeToSchool.class);
    }
    public void deleteStuNotComeToSchoolByCondition(StuNotComeToSchool stuNotComeToSchool) {
        mot.getCollection(mot.getCollectionName(StuNotComeToSchool.class)).deleteMany(MongoKit.buildMatchDocument(stuNotComeToSchool));
    }
    public void batchSaveStuNotComeToSchool(List<StuNotComeToSchool> stuNotComeToSchools){
        //System.out.println("批量插入学生未到校记录");
        stuNotComeToSchools.forEach(stuNotComeToSchool -> stuNotComeToSchool.setId(sequenceId.nextId()));
        mot.insertAll(stuNotComeToSchools);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
