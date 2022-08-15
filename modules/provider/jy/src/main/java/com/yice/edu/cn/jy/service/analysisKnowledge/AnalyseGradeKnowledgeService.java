package com.yice.edu.cn.jy.service.analysisKnowledge;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalyseGradeKnowledgeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnalyseClassKnowledgeService analyseClassKnowledgeService;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseGradeKnowledge findAnalyseGradeKnowledgeById(String id) {
        return mot.findById(id,AnalyseGradeKnowledge.class);
    }
    public void saveAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge) {
        analyseGradeKnowledge.setId(sequenceId.nextId());
        mot.insert(analyseGradeKnowledge);
    }
    public List<AnalyseGradeKnowledge> findAnalyseGradeKnowledgeListByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseGradeKnowledge.class)).find(MongoKit.buildMatchDocument(analyseGradeKnowledge));
        Pager pager = analyseGradeKnowledge.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseGradeKnowledge> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseGradeKnowledge.class, document)));
        return list;
    }
    public long findAnalyseGradeKnowledgeCountByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        return mot.getCollection(mot.getCollectionName(AnalyseGradeKnowledge.class)).count(MongoKit.buildMatchDocument(analyseGradeKnowledge));
    }
    public AnalyseGradeKnowledge findOneAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseGradeKnowledge.class)).find(MongoKit.buildMatchDocument(analyseGradeKnowledge));
        MongoKit.appendProjection(query,analyseGradeKnowledge.getPager());
        return mot.getConverter().read(AnalyseGradeKnowledge.class,query.first());
    }

    public void updateAnalyseGradeKnowledge(AnalyseGradeKnowledge analyseGradeKnowledge) {
        mot.updateFirst(query(where("id").is(analyseGradeKnowledge.getId())), MongoKit.update(analyseGradeKnowledge),AnalyseGradeKnowledge.class);
    }
    public void deleteAnalyseGradeKnowledge(String id) {
        mot.remove(query(where("id").is(id)),AnalyseGradeKnowledge.class);
    }
    public void deleteAnalyseGradeKnowledgeByCondition(AnalyseGradeKnowledge analyseGradeKnowledge) {
        mot.getCollection(mot.getCollectionName(AnalyseGradeKnowledge.class)).deleteMany(MongoKit.buildMatchDocument(analyseGradeKnowledge));
    }
    public void batchSaveAnalyseGradeKnowledge(List<AnalyseGradeKnowledge> analyseGradeKnowledges){
        analyseGradeKnowledges.forEach(analyseGradeKnowledge -> analyseGradeKnowledge.setId(sequenceId.nextId()));
        mot.insertAll(analyseGradeKnowledges);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/

    /**
     * 通过考试id进行删除
     * @param examId
     */
    public void deleteAnalyseGradeKnowledge4Exam(String examId) {
        mot.remove(query(where("examination._id").is(examId)),AnalyseGradeKnowledge.class);
    }
    /**
     * 通过考试id对知识点进行年级分析
     * 缺考的学生不进行知识点分析
     * db.getCollection("stuScore").aggregate([
     * {$match:{schoolExamId:'2234408597820628992'}},
     * {$project:{course:1,answerSheetDatas:1}},
     * {$unwind:"$answerSheetDatas"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetDatas', '$$ROOT']}}},
     * {$unwind:"$answerSheetItems"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetItems', '$$ROOT']}}},
     * {$unwind:"$knowledges"},
     * {$group:{_id:{knowledges:"$knowledges",course:"$course"},totalScore:{$sum:"$score"},avgScore:{$avg:"$yourScore"},getScore:{$sum:"$yourScore"},topicArr:{$addToSet:"$num"}}},
     * {$project:{_id:0,knowledge:"$_id.knowledges",course:"$_id.course",topicArr:1,avgScore:{$multiply:["$avgScore",{$size:"$topicArr"}]},topicCount:{$size:"$topicArr"},getPersent:{$divide:["$getScore","$totalScore"]}}}
     * ])
     */
    public void analyGradeKnowledge4Exam(SchoolExam schoolExam) {
        //1、对本次考试分析进行删除
//        AnalyseGradeKnowledge analyseGradeKnowledge = new AnalyseGradeKnowledge();
//        analyseGradeKnowledge.setExamination(schoolExam);
        this.deleteAnalyseGradeKnowledge4Exam(schoolExam.getId());
        //2、进行分析
        List<AggregationOperation> aggList = new ArrayList<>();
        aggList.add(Aggregation.match(Criteria.where("schoolExamId").is(schoolExam.getId()).and("missing").is(false)));
        aggList.add(Aggregation.project("course", "answerSheetDatas"));
        aggList.add(Aggregation.unwind("answerSheetDatas"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetDatas", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetItems", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$knowledges"));
        aggList.add(Aggregation.group("knowledges", "course").sum("score").as("totalScore").avg("yourScore").as("avgScore").sum("yourScore").as("getScore").addToSet("num").as("topicArr"));
        aggList.add(Aggregation.project("topicArr").and("_id.knowledges").as("knowledge").and("_id.course").as("course").and(aoe -> aoe.getMappedObject(new Document("$size", "$topicArr"))).as("topicCount").and(aoe -> aoe.getMappedObject(new Document("$multiply", Arrays.asList("$avgScore", new Document("$size", "$topicArr"))))).as("avgScore").and(aoe -> aoe.getMappedObject(new Document("$divide", Arrays.asList("$getScore", "$totalScore")))).as("getPersent"));
        List<AnalyseGradeKnowledge> analyseGradeKnowledgeList = mot.aggregate(Aggregation.newAggregation(StuScore.class, aggList), AnalyseGradeKnowledge.class).getMappedResults();
        DecimalFormat df = new DecimalFormat("#.0");
        analyseGradeKnowledgeList.forEach(agk -> {
            agk.setId(sequenceId.nextId());
            agk.setExamination(schoolExam);
            if (agk.getTopicArr() != null && agk.getTopicArr().length > 1) {
                agk.setTopicArr(Arrays.stream(agk.getTopicArr()).sorted().toArray(Integer[]::new));
            }
            agk.setAvgScore(Double.valueOf(df.format(agk.getAvgScore())));
            agk.setGetPersent(Double.valueOf(df.format(agk.getGetPersent() * 100)));
        });
        mot.insertAll(analyseGradeKnowledgeList);
        //进行班级分析
        analyseClassKnowledgeService.analyClassKnowledge4Exam(schoolExam, analyseGradeKnowledgeList);
    }
}
