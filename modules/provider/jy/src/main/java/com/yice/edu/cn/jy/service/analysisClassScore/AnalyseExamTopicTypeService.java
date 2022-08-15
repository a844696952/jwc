package com.yice.edu.cn.jy.service.analysisClassScore;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalyseExamTopicTypeService {
    private static final Logger logger = LoggerFactory.getLogger(AnalyseExamTopicType.class);
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnalyseExamStuTopicTypeService analyseExamStuTopicTypeService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public AnalyseExamTopicType findAnalyseExamTopicTypeById(String id) {
        return mot.findById(id, AnalyseExamTopicType.class);
    }

    public void saveAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType) {
        analyseExamTopicType.setCreateTime(DateUtil.now());
        analyseExamTopicType.setId(sequenceId.nextId());
        mot.insert(analyseExamTopicType);
    }

    public List<AnalyseExamTopicType> findAnalyseExamTopicTypeListByCondition(AnalyseExamTopicType analyseExamTopicType) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopicType.class)).find(MongoKit.buildMatchDocument(analyseExamTopicType));
        Pager pager = analyseExamTopicType.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<AnalyseExamTopicType> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseExamTopicType.class, document)));
        return list;
    }

    public long findAnalyseExamTopicTypeCountByCondition(AnalyseExamTopicType analyseExamTopicType) {
        return mot.getCollection(mot.getCollectionName(AnalyseExamTopicType.class)).countDocuments(MongoKit.buildMatchDocument(analyseExamTopicType));
    }

    public AnalyseExamTopicType findOneAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopicType.class)).find(MongoKit.buildMatchDocument(analyseExamTopicType));
        MongoKit.appendProjection(query, analyseExamTopicType.getPager());
        return mot.getConverter().read(AnalyseExamTopicType.class, query.first());
    }

    public void updateAnalyseExamTopicType(AnalyseExamTopicType analyseExamTopicType) {
        mot.updateFirst(query(where("id").is(analyseExamTopicType.getId())), MongoKit.update(analyseExamTopicType), AnalyseExamTopicType.class);
    }

    public void deleteAnalyseExamTopicType(String id) {
        mot.remove(query(where("id").is(id)), AnalyseExamTopicType.class);
    }

    public void deleteAnalyseExamTopicTypeByCondition(AnalyseExamTopicType analyseExamTopicType) {
        mot.getCollection(mot.getCollectionName(AnalyseExamTopicType.class)).deleteMany(MongoKit.buildMatchDocument(analyseExamTopicType));
    }

    public void batchSaveAnalyseExamTopicType(List<AnalyseExamTopicType> analyseExamTopicTypes) {
        analyseExamTopicTypes.forEach(analyseExamTopicType -> analyseExamTopicType.setId(sequenceId.nextId()));
        mot.insertAll(analyseExamTopicTypes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void deleteAnalyseExamTopicType4Exam(String examId) {
        mot.remove(query(where("examObj._id").is(examId)),AnalyseExamTopicType.class);
    }
    /**
     * db.getCollection('stuScore').aggregate([
     *     {
     *         "$match": {
     *             schoolExamId: "2435463644808306688",
     *             missing: false
     *         }
     *     },
     *     {
     *         $project: {
     *             clazz: 1,
     *             course: 1,
     *             answerSheetItems: "$answerSheetDatas.answerSheetItems"
     *         }
     *     },
     *     {
     *         $unwind: "$answerSheetItems"
     *     },
     *     {
     *         $unwind: "$answerSheetItems"
     *     },
     *                {
     *         $replaceRoot: {
     *             newRoot: {
     *                 '$mergeObjects': ['$answerSheetItems', '$$ROOT']
     *             }
     *         }
     *     },
     *     {
     *         $group: {
     *             _id: {
     *                 clazz: "$clazz",
     *                 course: "$course",
     *                 typeId: "$typeId",
     *                 typeName: "$typeName"
     *             },
     *             topicArr: {
     *                 $addToSet: "$num"
     *             },
     *             topicFullMarks: {
     *                 $sum: "$score"
     *             },
     *             getTopicScore: {
     *                 $sum: "$yourScore"
     *             },
     *             classAvgMarks: {
     *                 $avg: "$yourScore"
     *             }
     *         }
     *     },
     *        {
     *         $project: {
     *             _id: 0,
     *             clazzObj: "$_id.clazz",
     *             courseObj: "$_id.course",
     *             typeId: "$_id.typeId",
     *             topicTypeName: "$_id.typeName",
     *             topicArr: 1,
     *             topicFullMarks: 1,
     * 						classAvgMarks: {
     *                 $multiply: ["$classAvgMarks", {
     *                     $size: "$topicArr"
     *                 }]
     *             },
     *             classScoreRate: {
     *                 $divide: ["$getTopicScore", "$topicFullMarks"]
     *             }
     *         }
     *     }
     * ])
     */
    public List<AnalyseExamTopicType> analyseExamTopicTypes4Exam(SchoolExam schoolExam) {
        this.deleteAnalyseExamTopicType4Exam(schoolExam.getId());
        List<AggregationOperation> aggList = new ArrayList<>();
        aggList.add(Aggregation.match(Criteria.where("schoolExamId").is(schoolExam.getId()).and("missing").is(false)));
        aggList.add(Aggregation.project("clazz", "course").and("answerSheetDatas.answerSheetItems").as("answerSheetItems"));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetItems", "$$ROOT")))));
        aggList.add(Aggregation.group("clazz", "course", "typeId","typeName").addToSet("num").as("topicArr").sum("score").as("topicFullMarks").sum("yourScore").as("getTopicScore").avg("yourScore").as("classAvgMarks"));
        aggList.add(Aggregation.project("topicArr","topicFullMarks").and("_id.clazz").as("clazzObj").and("_id.typeId").as("typeId").and("_id.course").as("courseObj").and("_id.typeName").as("topicTypeName").and(aoe -> aoe.getMappedObject(new Document("$multiply", Arrays.asList("$classAvgMarks", new Document("$size", "$topicArr"))))).as("classAvgMarks").and(aoe -> aoe.getMappedObject(new Document("$divide", Arrays.asList("$getTopicScore", "$topicFullMarks")))).as("classScoreRate"));
        List<AnalyseExamTopicType> analyseExamTopicTypeList = mot.aggregate(Aggregation.newAggregation(StuScore.class, aggList), AnalyseExamTopicType.class).getMappedResults();
        // 进行学生题型分析
        analyseExamStuTopicTypeService.analyseExamStuTopicType4Exam(schoolExam,analyseExamTopicTypeList);
        DecimalFormat df = new DecimalFormat("#.0");
        analyseExamTopicTypeList.stream().forEach(aet -> {
            aet.setId(sequenceId.nextId());
            aet.setExamObj(schoolExam);
            if (aet.getTopicArr()!=null&&aet.getTopicArr().length>1){
                aet.setTopicArr(Arrays.stream(aet.getTopicArr()).sorted().toArray(Integer[]::new));
            }
            aet.setClassAvgMarks(Double.valueOf(df.format(aet.getClassAvgMarks())));
            String classScoreRate = df.format(aet.getClassScoreRate() * 100);
            Double finalClassScoreRate = new BigDecimal(classScoreRate).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
            aet.setClassScoreRate(finalClassScoreRate);
        });
        mot.insertAll(analyseExamTopicTypeList);
        return analyseExamTopicTypeList;
    }

}
