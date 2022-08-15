package com.yice.edu.cn.jy.service.analysisKnowledge;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
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
public class AnalyseStuKnowledgeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseStuKnowledge findAnalyseStuKnowledgeById(String id) {
        return mot.findById(id,AnalyseStuKnowledge.class);
    }
    public void saveAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge) {
        analyseStuKnowledge.setId(sequenceId.nextId());
        mot.insert(analyseStuKnowledge);
    }
    public List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseStuKnowledge.class)).find(MongoKit.buildMatchDocument(analyseStuKnowledge));
        Pager pager = analyseStuKnowledge.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseStuKnowledge> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseStuKnowledge.class, document)));
        return list;
    }
    public long findAnalyseStuKnowledgeCountByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return mot.getCollection(mot.getCollectionName(AnalyseStuKnowledge.class)).count(MongoKit.buildMatchDocument(analyseStuKnowledge));
    }
    public AnalyseStuKnowledge findOneAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseStuKnowledge.class)).find(MongoKit.buildMatchDocument(analyseStuKnowledge));
        MongoKit.appendProjection(query,analyseStuKnowledge.getPager());
        return mot.getConverter().read(AnalyseStuKnowledge.class,query.first());
    }

    public void updateAnalyseStuKnowledge(AnalyseStuKnowledge analyseStuKnowledge) {
        mot.updateFirst(query(where("id").is(analyseStuKnowledge.getId())), MongoKit.update(analyseStuKnowledge),AnalyseStuKnowledge.class);
    }
    public void deleteAnalyseStuKnowledge(String id) {
        mot.remove(query(where("id").is(id)),AnalyseStuKnowledge.class);
    }
    public void deleteAnalyseStuKnowledgeByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        mot.getCollection(mot.getCollectionName(AnalyseStuKnowledge.class)).deleteMany(MongoKit.buildMatchDocument(analyseStuKnowledge));
    }
    public void batchSaveAnalyseStuKnowledge(List<AnalyseStuKnowledge> analyseStuKnowledges){
        analyseStuKnowledges.forEach(analyseStuKnowledge -> analyseStuKnowledge.setId(sequenceId.nextId()));
        mot.insertAll(analyseStuKnowledges);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    public void deleteAnalyseStuKnowledge4Exam(String examId) {
        mot.remove(query(where("examination._id").is(examId)),AnalyseStuKnowledge.class);
    }
    /**
     * 通过考试id对知识点进行班级分析-
     * 缺考的学生不进行知识点分析
     * db.getCollection("stuScore").aggregate([
     * {$match:{schoolExamId:'2234408597820628992'}},
     * {$project:{student:1,course:1,answerSheetDatas:1}},
     * {$unwind:"$answerSheetDatas"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetDatas', '$$ROOT']}}},
     * {$unwind:"$answerSheetItems"},
     * {$replaceRoot:{newRoot:{'$mergeObjects': ['$answerSheetItems', '$$ROOT']}}},
     * {$unwind:"$knowledges"},
     * {$group:{_id:{knowledges:"$knowledges",student:"$student",course:"$course"},totalScore:{$sum:"$score"},getScore:{$sum:"$yourScore"},topic:{$addToSet:"$num"}}},
     * {$project:{_id:0,student:"$_id.student",knowledge:"$_id.knowledges",course:"$_id.course",totalScore:1,getScore:1,topic:1,topicCount:{$size:"$topic"}}}
     * ])
     */
    public void analyStuKnowledge4Exam(SchoolExam schoolExam, List<AnalyseClassKnowledge> analyseClassKnowledgeList) {
        //1、这次考试的分析删除
//        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
//        analyseStuKnowledge.setExamination(schoolExam);
        this.deleteAnalyseStuKnowledge4Exam(schoolExam.getId());
        //2、进行数据分析
        List<AggregationOperation> aggList = new ArrayList<>();
        aggList.add(Aggregation.match(Criteria.where("schoolExamId").is(schoolExam.getId()).and("missing").is(false)));
        aggList.add(Aggregation.project("student", "course", "answerSheetDatas"));
        aggList.add(Aggregation.unwind("answerSheetDatas"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetDatas", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetItems", "$$ROOT")))));
        aggList.add(Aggregation.unwind("$knowledges"));
        aggList.add(Aggregation.group("knowledges", "course", "student").sum("score").as("totalScore").avg("yourScore").as("avgScore").sum("yourScore").as("getScore").addToSet("num").as("topicArr"));
        aggList.add(Aggregation.project("topicArr", "getScore", "totalScore").and("_id.student").as("student").and("_id.knowledges").as("knowledge").and("_id.course").as("course").and(aoe -> aoe.getMappedObject(new Document("$size", "$topicArr"))).as("topicCount").and(aoe -> aoe.getMappedObject(new Document("$divide", Arrays.asList("$getScore", "$totalScore")))).as("getPersent"));
        List<AnalyseStuKnowledge> analyseStuKnowledgeList = mot.aggregate(Aggregation.newAggregation(StuScore.class, aggList), AnalyseStuKnowledge.class).getMappedResults();
        DecimalFormat df = new DecimalFormat("#.0");
        analyseStuKnowledgeList.stream().forEach(ask -> {
            ask.setId(sequenceId.nextId());
            ask.setExamination(schoolExam);
            if (ask.getTopicArr() != null && ask.getTopicArr().length > 1) {
                ask.setTopicArr(Arrays.stream(ask.getTopicArr()).sorted().toArray(Integer[]::new));
            }
            ask.setGetPersent(Double.valueOf(df.format(ask.getGetPersent() * 100)));
            //设置年级和班级均分
            AnalyseClassKnowledge analyseClassKnowledge = analyseClassKnowledgeList.stream().filter(ack -> ack.getKnowledge().getId().equals(ask.getKnowledge().getId())).findAny().get();
            ask.setGradeAvgScore(analyseClassKnowledge.getGradeAvgScore());
            ask.setGradePersent(analyseClassKnowledge.getGradePersent());
            ask.setClassAvgScore(analyseClassKnowledge.getAvgScore());
            ask.setClassPersent(analyseClassKnowledge.getGetPersent());
        });
        mot.insertAll(analyseStuKnowledgeList);
        //给班级分析 添加4率对应的人数
        analyseClassKnowledgeList.forEach(ck->{
            //获取知识点总分
            analyseStuKnowledgeList.stream().filter(sk->ck.getClazz().getId().equals(sk.getStudent().getClassesId())&&ck.getKnowledge().getId().equals(sk.getKnowledge().getId())).findFirst().ifPresent(one->ck.setTotalScore(one.getTotalScore()));
        });
    }
}
