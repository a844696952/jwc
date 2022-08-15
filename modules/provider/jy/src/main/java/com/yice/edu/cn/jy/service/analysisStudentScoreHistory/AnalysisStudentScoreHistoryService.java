package com.yice.edu.cn.jy.service.analysisStudentScoreHistory;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalysisStudentScoreHistoryService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public AnalysisStudentScoreHistory findAnalysisStudentScoreHistoryById(String id) {
        return mot.findById(id,AnalysisStudentScoreHistory.class);
    }
    public AnalysisStudentScoreHistory saveAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        analysisStudentScoreHistory.setId(sequenceId.nextId());
        mot.insert(analysisStudentScoreHistory);
        return analysisStudentScoreHistory;
    }
    public List<AnalysisStudentScoreHistory> findAnalysisStudentScoreHistoryListByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalysisStudentScoreHistory.class)).find(MongoKit.buildMatchDocument(analysisStudentScoreHistory));
        Pager pager = analysisStudentScoreHistory.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalysisStudentScoreHistory> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalysisStudentScoreHistory.class, document)));
        return list;
    }
    public long findAnalysisStudentScoreHistoryCountByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        return mot.getCollection(mot.getCollectionName(AnalysisStudentScoreHistory.class)).countDocuments(MongoKit.buildMatchDocument(analysisStudentScoreHistory));
    }
    public AnalysisStudentScoreHistory findOneAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalysisStudentScoreHistory.class)).find(MongoKit.buildMatchDocument(analysisStudentScoreHistory));
       MongoKit.appendProjection(query,analysisStudentScoreHistory.getPager());
       return mot.getConverter().read(AnalysisStudentScoreHistory.class,query.first());
    }

    public void updateAnalysisStudentScoreHistory(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        mot.updateFirst(query(where("id").is(analysisStudentScoreHistory.getId())), MongoKit.update(analysisStudentScoreHistory,false),AnalysisStudentScoreHistory.class);
    }
    public void updateAnalysisStudentScoreHistoryForAll(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        mot.updateFirst(query(where("id").is(analysisStudentScoreHistory.getId())), MongoKit.update(analysisStudentScoreHistory,true),AnalysisStudentScoreHistory.class);
    }
    public void deleteAnalysisStudentScoreHistory(String id) {
        mot.remove(query(where("id").is(id)),AnalysisStudentScoreHistory.class);
    }
    public void deleteAnalysisStudentScoreHistoryByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        mot.getCollection(mot.getCollectionName(AnalysisStudentScoreHistory.class)).deleteMany(MongoKit.buildMatchDocument(analysisStudentScoreHistory));
    }
    public void batchSaveAnalysisStudentScoreHistory(List<AnalysisStudentScoreHistory> analysisStudentScoreHistorys){
        analysisStudentScoreHistorys.forEach(analysisStudentScoreHistory -> analysisStudentScoreHistory.setId(sequenceId.nextId()));
        mot.insertAll(analysisStudentScoreHistorys);
    }

    public Long findStudentScoresCountByCondition(AnalysisStudentScoreHistory analysisStudentScoreHistory) {
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件
        if (analysisStudentScoreHistory.getClassObj() != null) {
            if(analysisStudentScoreHistory.getClassObj().getGradeId()!=null){
                criteria.and("classObj.gradeId").is(analysisStudentScoreHistory.getClassObj().getGradeId());
            }
            analysisStudentScoreHistory.setClassObj(null);
        }
        if (analysisStudentScoreHistory.getSubject() != null) {
            criteria.and("subject.id").is(analysisStudentScoreHistory.getSubject().getId());
            analysisStudentScoreHistory.setSubject(null);
        }
        if (analysisStudentScoreHistory.getStudent() != null) {
            criteria.and("student.id").is(analysisStudentScoreHistory.getStudent().getId());
            analysisStudentScoreHistory.setStudent(null);
        }
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScoreHistory, analysisStudentScoreHistory.getPager()));
        return  mot.count(query(criteria), AnalysisStudentScoreHistory.class);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
