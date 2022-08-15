package com.yice.edu.cn.jy.service.analysisClassScore;

import cn.hutool.core.date.DateUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalyseExamStuTopicTypeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public AnalyseExamStuTopicType findAnalyseExamStuTopicTypeById(String id) {
        return mot.findById(id,AnalyseExamStuTopicType.class);
    }
    public void saveAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType) {
        analyseExamStuTopicType.setCreateTime(DateUtil.now());
        analyseExamStuTopicType.setId(sequenceId.nextId());
        mot.insert(analyseExamStuTopicType);
    }
    public List<AnalyseExamStuTopicType> findAnalyseExamStuTopicTypeListByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamStuTopicType.class)).find(MongoKit.buildMatchDocument(analyseExamStuTopicType));
        Pager pager = analyseExamStuTopicType.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseExamStuTopicType> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseExamStuTopicType.class, document)));
        return list;
    }
    public long findAnalyseExamStuTopicTypeCountByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        return mot.getCollection(mot.getCollectionName(AnalyseExamStuTopicType.class)).countDocuments(MongoKit.buildMatchDocument(analyseExamStuTopicType));
    }
    public AnalyseExamStuTopicType findOneAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamStuTopicType.class)).find(MongoKit.buildMatchDocument(analyseExamStuTopicType));
       MongoKit.appendProjection(query,analyseExamStuTopicType.getPager());
       return mot.getConverter().read(AnalyseExamStuTopicType.class,query.first());
    }

    public void updateAnalyseExamStuTopicType(AnalyseExamStuTopicType analyseExamStuTopicType) {
        mot.updateFirst(query(where("id").is(analyseExamStuTopicType.getId())), MongoKit.update(analyseExamStuTopicType),AnalyseExamStuTopicType.class);
    }
    public void deleteAnalyseExamStuTopicType(String id) {
        mot.remove(query(where("id").is(id)),AnalyseExamStuTopicType.class);
    }
    public void deleteAnalyseExamStuTopicTypeByCondition(AnalyseExamStuTopicType analyseExamStuTopicType) {
        mot.getCollection(mot.getCollectionName(AnalyseExamStuTopicType.class)).deleteMany(MongoKit.buildMatchDocument(analyseExamStuTopicType));
    }
    public void batchSaveAnalyseExamStuTopicType(List<AnalyseExamStuTopicType> analyseExamStuTopicTypes){
        analyseExamStuTopicTypes.forEach(analyseExamStuTopicType -> analyseExamStuTopicType.setId(sequenceId.nextId()));
        mot.insertAll(analyseExamStuTopicTypes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void deleteAnalyseExamStuTopicType4Exam(String examId) {
        mot.remove(query(where("examObj._id").is(examId)),AnalyseExamStuTopicType.class);
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
     *             student: 1,
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
     *                 typeName: "$typeName",
     * 								student: "$student"
     *             },
     *             topicArr: {
     *                 $addToSet: "$num"
     *             },
     *             topicFullMarks: {
     *                 $sum: "$score"
     *             },
     *             getTopicScore: {
     *                 $sum: "$yourScore"
     *             }
     *         }
     *     },
     *     {
     *         $project: {
     *             _id: 0,
     *             clazzObj: "$_id.clazz",
     *             courseObj: "$_id.course",
     *             student: "$_id.student",
     *             typeId: "$_id.typeId",
     *             topicTypeName: "$_id.typeName",
     *             topicArr: 1,
     *             topicFullMarks: 1,
     *             getTopicScore: 1,
     * 						topicRate: {
     *                 $divide: ["$getTopicScore", "$topicFullMarks"]
     *             }
     *         }
     *     }
     * ])
     */
    public void analyseExamStuTopicType4Exam(SchoolExam schoolExam, List<AnalyseExamTopicType> analyseExamTopicTypeList){
        this.deleteAnalyseExamStuTopicType4Exam(schoolExam.getId());
        List<AggregationOperation> aggList = new ArrayList<>();
        aggList.add(Aggregation.match(Criteria.where("schoolExamId").is(schoolExam.getId()).and("missing").is(false)));
        aggList.add(Aggregation.project("clazz", "course","student").and("answerSheetDatas.answerSheetItems").as("answerSheetItems"));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.unwind("$answerSheetItems"));
        aggList.add(Aggregation.replaceRoot(aoc -> aoc.getMappedObject(new Document("$mergeObjects", Arrays.asList("$answerSheetItems", "$$ROOT")))));
        aggList.add(Aggregation.group("clazz", "course", "typeId","typeName","student").addToSet("num").as("topicArr").sum("score").as("topicFullMarks").sum("yourScore").as("getTopicScore"));
        aggList.add(Aggregation.project("topicArr","topicFullMarks","getTopicScore").and("_id.clazz").as("clazzObj").and("_id.typeId").as("typeId").and("_id.course").as("courseObj").and("_id.typeName").as("topicTypeName").and(aoe -> aoe.getMappedObject(new Document("$divide", Arrays.asList("$getTopicScore", "$topicFullMarks")))).as("topicRate"));
        List<AnalyseExamStuTopicType> analyseExamStuTopicTypeList = mot.aggregate(Aggregation.newAggregation(StuScore.class, aggList), AnalyseExamStuTopicType.class).getMappedResults();
        analyseExamTopicTypeList.forEach(aet -> {
            // 从学生题型分析获得题型总分
            analyseExamStuTopicTypeList.stream().filter(aes -> aes.getClazzObj().getId().equals(aet.getClazzObj().getId()) && aes.getCourseObj().getId().equals(aet.getCourseObj().getId()) && aes.getTypeId().equals(aet.getTypeId())).findFirst().ifPresent(one -> aet.setTopicFullMarks(one.getTopicFullMarks()));
        });
        // 计算年级平均分年级得分率
        countAvgByTopicType(analyseExamTopicTypeList);
        DecimalFormat df = new DecimalFormat("#.0");
        analyseExamStuTopicTypeList.stream().forEach(aes -> {
            aes.setId(sequenceId.nextId());
            aes.setExamObj(schoolExam);
            if (aes.getTopicArr() != null && aes.getTopicArr().length > 1){
                aes.setTopicArr(Arrays.stream(aes.getTopicArr()).sorted().toArray(Integer[]::new));
            }
            String getTopicRate = df.format(aes.getTopicRate() * 100);
            aes.setTopicRate(new BigDecimal(getTopicRate).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
            // 设置年级和班级均分
            AnalyseExamTopicType analyseExamTopicType = analyseExamTopicTypeList.stream().filter(aet -> aet.getTypeId().equals(aes.getTypeId())).findAny().get();
            aes.setClassAvgMarks(Double.valueOf(df.format(analyseExamTopicType.getClassAvgMarks())));
            String classScoreRate = df.format(analyseExamTopicType.getClassScoreRate() * 100);
            aes.setClassScoreRate(new BigDecimal(classScoreRate).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
            aes.setGradeAvgMarks(analyseExamTopicType.getGradeAvgMarks());
            aes.setGradeScoreRate(analyseExamTopicType.getGradeScoreRate());
        });
        mot.insertAll(analyseExamStuTopicTypeList);

    }

    private void countAvgByTopicType(List<AnalyseExamTopicType> analyseExamTopicTypeList) {
        // 科目分组
        Map<String, List<AnalyseExamTopicType>> courseMap = analyseExamTopicTypeList.stream().collect(Collectors.groupingBy(ss -> {
            return ss.getCourseObj().getId();
        }));
        DecimalFormat df = new DecimalFormat("#.0");
        // 题型分组
        courseMap.forEach((courseId, topicTypeList) -> {
            Map<String, List<AnalyseExamTopicType>> typeListMap = topicTypeList.stream().collect(Collectors.groupingBy(ss -> {
                return ss.getTypeId();
            }));
            // 取到分组后的数据计算年段平均分与得分率
            typeListMap.forEach((typeId, typeList) -> {
                // 年级平均得分(班级平均得分总和的平均)
                Double gradeAvg = typeList.stream().mapToDouble(AnalyseExamTopicType::getClassAvgMarks).average().getAsDouble();
                // 最终年级平均得分
                Double gradeAvgMarks = new BigDecimal(gradeAvg + "").setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                // 年级得分率(年级平均得分/题型总分)
                Double gradeScoreRateFirst = new BigDecimal(gradeAvgMarks / typeList.get(0).getTopicFullMarks() + "").setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                // 最终年级得分率
                Double gradeScoreRate = Double.valueOf(df.format(gradeScoreRateFirst * 100));
                // 设置年级均分与得分率
                analyseExamTopicTypeList.forEach(aet -> {
                    if (courseId.equals(aet.getCourseObj().getId()) && typeId.equals(aet.getTypeId())) {
                        aet.setGradeAvgMarks(gradeAvgMarks);
                        aet.setGradeScoreRate(gradeScoreRate);
                    }
                });
            });
        });
    }

}
