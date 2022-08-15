package com.yice.edu.cn.jy.service.analysisKnowledge;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
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
public class AnalyseClassKnowledgeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnalyseStuKnowledgeService analyseStuKnowledgeService;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseClassKnowledge findAnalyseClassKnowledgeById(String id) {
        return mot.findById(id,AnalyseClassKnowledge.class);
    }
    public void saveAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge) {
        analyseClassKnowledge.setId(sequenceId.nextId());
        mot.insert(analyseClassKnowledge);
    }
    public List<AnalyseClassKnowledge> findAnalyseClassKnowledgeListByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseClassKnowledge.class)).find(MongoKit.buildMatchDocument(analyseClassKnowledge));
        Pager pager = analyseClassKnowledge.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseClassKnowledge> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseClassKnowledge.class, document)));
        return list;
    }
    public long findAnalyseClassKnowledgeCountByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        return mot.getCollection(mot.getCollectionName(AnalyseClassKnowledge.class)).count(MongoKit.buildMatchDocument(analyseClassKnowledge));
    }
    public AnalyseClassKnowledge findOneAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseClassKnowledge.class)).find(MongoKit.buildMatchDocument(analyseClassKnowledge));
        MongoKit.appendProjection(query,analyseClassKnowledge.getPager());
        return mot.getConverter().read(AnalyseClassKnowledge.class,query.first());
    }

    public void updateAnalyseClassKnowledge(AnalyseClassKnowledge analyseClassKnowledge) {
        mot.updateFirst(query(where("id").is(analyseClassKnowledge.getId())), MongoKit.update(analyseClassKnowledge),AnalyseClassKnowledge.class);
    }
    public void deleteAnalyseClassKnowledge(String id) {
        mot.remove(query(where("id").is(id)),AnalyseClassKnowledge.class);
    }
    public void deleteAnalyseClassKnowledgeByCondition(AnalyseClassKnowledge analyseClassKnowledge) {
        mot.getCollection(mot.getCollectionName(AnalyseClassKnowledge.class)).deleteMany(MongoKit.buildMatchDocument(analyseClassKnowledge));
    }
    public void batchSaveAnalyseClassKnowledge(List<AnalyseClassKnowledge> analyseClassKnowledges){
        analyseClassKnowledges.forEach(analyseClassKnowledge -> analyseClassKnowledge.setId(sequenceId.nextId()));
        mot.insertAll(analyseClassKnowledges);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    public void deleteAnalyseClassKnowledge4Exam(String examId) {
        mot.remove(query(where("examination._id").is(examId)),AnalyseClassKnowledge.class);
    }
    /**
     * 通过考试id对知识点进行班级分析
     * 缺考的学生不进行知识点分析
     * db.getCollection("stuScore").aggregate([
     * {$match:{schoolExamId:'2234408597820628992'}},
     * {$project:{clazz:1,course:1,answerSheetDatas:1}},
     * {$unwind:"$answerSheetDatas"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetDatas', '$$ROOT']}}},
     * {$unwind:"$answerSheetItems"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetItems', '$$ROOT']}}},
     * {$unwind:"$knowledges"},
     * {$group:{_id:{knowledges:"$knowledges",course:"$course",clazz:"$clazz"},totalScore:{$sum:"$score"},getScore:{$sum:"$yourScore"},avgScore:{$avg:"$yourScore"},topicArr:{$addToSet:"$num"}}},
     * {$project:{_id:0,knowledges:"$_id.knowledges",course:"$_id.course",clazz:"$_id.clazz",totalScore:1,getScore:1,topicArr:1,avgScore:{$multiply:["$avgScore",{$size:"$topicArr"}]},topicCount:{$size:"$topicArr"},getPersent:{$divide:["$getScore","$totalScore"]}}}
     * ])
     */
    public List<AnalyseClassKnowledge> analyClassKnowledge4Exam(SchoolExam schoolExam, List<AnalyseGradeKnowledge> analyseGradeKnowledgeList) {
        //1、对本次考试分析进行删除
//        AnalyseClassKnowledge analyseClassKnowledge = new AnalyseClassKnowledge();
//        analyseClassKnowledge.setExamination(schoolExam);
        this.deleteAnalyseClassKnowledge4Exam(schoolExam.getId());
        //2、进行分析
        List<AggregationOperation> aggList = new ArrayList<>();
        aggList.add(Aggregation.match(Criteria.where("schoolExamId").is(schoolExam.getId()).and("missing").is(false)));
        aggList.add(Aggregation.project("clazz", "course", "answerSheetDatas"));
        aggList.add(Aggregation.unwind("answerSheetDatas"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetDatas", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetItems", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$knowledges"));
        aggList.add(Aggregation.group("knowledges", "course", "clazz").sum("score").as("totalScore").avg("yourScore").as("avgScore").sum("yourScore").as("getScore").addToSet("num").as("topicArr"));
        aggList.add(Aggregation.project("topicArr").and("_id.clazz").as("clazz").and("_id.knowledges").as("knowledge").and("_id.course").as("course").and(aoe -> aoe.getMappedObject(new Document("$size", "$topicArr"))).as("topicCount").and(aoe -> aoe.getMappedObject(new Document("$multiply", Arrays.asList("$avgScore", new Document("$size", "$topicArr"))))).as("avgScore").and(aoe -> aoe.getMappedObject(new Document("$divide", Arrays.asList("$getScore", "$totalScore")))).as("getPersent"));
        List<AnalyseClassKnowledge> analyseClassKnowledgeList = mot.aggregate(Aggregation.newAggregation(StuScore.class, aggList), AnalyseClassKnowledge.class).getMappedResults();
        DecimalFormat df = new DecimalFormat("#.0");
        analyseClassKnowledgeList.stream().forEach(ack -> {
            ack.setId(sequenceId.nextId());
            ack.setExamination(schoolExam);
            if (ack.getTopicArr() != null && ack.getTopicArr().length > 1) {
                ack.setTopicArr(Arrays.stream(ack.getTopicArr()).sorted().toArray(Integer[]::new));
            }
            ack.setAvgScore(Double.valueOf(df.format(ack.getAvgScore())));
            ack.setGetPersent(Double.valueOf(df.format(ack.getGetPersent() * 100)));
            //设置年级均分
            AnalyseGradeKnowledge analyseGradeKnowledge = analyseGradeKnowledgeList.stream().filter(agk -> agk.getKnowledge().getId().equals(ack.getKnowledge().getId())).findAny().get();
            ack.setGradeAvgScore(analyseGradeKnowledge.getAvgScore());
            ack.setGradePersent(analyseGradeKnowledge.getGetPersent());
            ack.setTotalScore(0);//这里不计算知识点总分值 通过学生分析对应的知识点总分值获取
        });
        //进行学生分析 因为 现在的产品要在班级分析里面加 4率对应的人员
        analyseStuKnowledgeService.analyStuKnowledge4Exam(schoolExam, analyseClassKnowledgeList);
        //学生分析之后 再进行入库
        mot.insertAll(analyseClassKnowledgeList);
        return analyseClassKnowledgeList;
    }
}
