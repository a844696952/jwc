package com.yice.edu.cn.jy.service.analysisStudentScore;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.jy.feignClient.examManage.SchoolExamFeign;
import com.yice.edu.cn.jy.feignClient.schoolYear.SchoolYearFeign;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalysisStudentScoreAllService {
    public static ExecutorService executorService;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SchoolExamFeign schoolExamFeign;
    @Autowired
    private SchoolYearFeign schoolYearFeign;

    public AnalysisStudentScoreAll findAnalysisStudentScoreAllById(String id) {
        return mot.findById(id, AnalysisStudentScoreAll.class);
    }

    public void saveAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        analysisStudentScoreAll.setId(sequenceId.nextId());
        mot.insert(analysisStudentScoreAll);
    }

    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScoreAll, analysisStudentScoreAll.getPager()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analysisStudentScoreAll.getPager(), operations);
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScoreAll.class, operations), AnalysisStudentScoreAll.class).getMappedResults();

    }

    public long findAnalysisStudentScoreAllCountByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        Criteria criteria = new Criteria();
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScoreAll, analysisStudentScoreAll.getPager()));
        return mot.count(query(criteria), AnalysisStudentScoreAll.class);
    }

    public AnalysisStudentScoreAll findOneAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        Example<AnalysisStudentScoreAll> example = Example.of(analysisStudentScoreAll, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        operations.add(Aggregation.match(new Criteria().alike(example).andOperator(criteria)));
        Pager pager = analysisStudentScoreAll.getPager();
        if (pager != null && pager.getIncludes() != null) {
            operations.add(project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScoreAll.class, operations), AnalysisStudentScoreAll.class).getUniqueMappedResult();
    }

    public void updateAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        mot.updateFirst(query(where("id").is(analysisStudentScoreAll.getId())), MongoKit.update(analysisStudentScoreAll), AnalysisStudentScoreAll.class);
    }

    public void deleteAnalysisStudentScoreAll(String id) {
        mot.remove(query(where("id").is(id)), AnalysisStudentScoreAll.class);
    }

    public void deleteAnalysisStudentScoreAllByExaminationId(String examinationId) {
        mot.remove(query(where("examination.id").is(examinationId)), AnalysisStudentScoreAll.class);
    }

    public void deleteAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        Example<AnalysisStudentScoreAll> example = Example.of(analysisStudentScoreAll, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnalysisStudentScoreAll.class);
    }

    public void batchSaveAnalysisStudentScoreAll(List<AnalysisStudentScoreAll> analysisStudentScoreAlls) {
        analysisStudentScoreAlls.forEach(analysisStudentScoreAll -> analysisStudentScoreAll.setId(sequenceId.nextId()));
        mot.insertAll(analysisStudentScoreAlls);
    }

    /**
     * 学期分析
     * 分析学生考试总成绩
     *
     * @param schoolExam
     */
    public void analysisByExamination(SchoolExam schoolExam) {
        DecimalFormat df = new DecimalFormat("#.0");
        //先删除这个考试的分析
        this.deleteAnalysisStudentScoreAllByExaminationId(schoolExam.getId());
        if (schoolExam == null || schoolExam.getCourses() == null)//查询不到考试就没有后续步骤了
            return;

        //获取考试中按学生统计出成绩列表 1获取未缺考的人员
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria("schoolExamId").is(schoolExam.getId())));
        //计算总分，用cond判定，missing为true 记录0 false记录 真实分数
        //缺考统计 为true 记录为 0 false记录为1 missCount 表示参考的数量 missCount为0 表示一科考试都没参加
        operations.add(Aggregation.group("student", "clazz").sum(ConditionalOperators.when(ComparisonOperators.valueOf("missing").equalToValue(true)).then(0).otherwise(1)).as("missCount").sum("course.totalScore").as("total").sum(ConditionalOperators.when(ComparisonOperators.valueOf("missing").equalToValue(false)).thenValueOf("$score").otherwise(0)).as("score"));
        //构建返回值 missCount为0 表示一科考试都没参加
        operations.add(Aggregation.project("total", "score").and(ConditionalOperators.when(Criteria.where("missCount").lt(1)).then(true).otherwise(false)).as("missing").and("_id.schoolId").as("schoolId").and("_id.student").as("student").and("_id.clazz").as("classObj"));
        operations.add(Aggregation.sort(DESC, "score"));
        final List<AnalysisStudentScoreAll> assaList = new ArrayList<>();
        AggregationResults<AnalysisStudentScoreAll> typedAggregation = mot.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalysisStudentScoreAll.class);
        assaList.addAll(typedAggregation.getMappedResults());
        typedAggregation = null;
        //排序
        Map<String, Double> temp = new HashMap<>();
        temp.put("ranking", 0.0);
        temp.put("preScore", 0.0);
        Map<String, List<AnalysisStudentScoreAll>> groupMap = assaList.stream().map(assa -> {
            Double preScore = temp.get("preScore");
            int ranking = temp.get("ranking").intValue();
            if (preScore.intValue() != assa.getScore().intValue()) {
                ranking = assaList.indexOf(assa) + 1;
                temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                temp.put("preScore", assa.getScore());
            }
            assa.setExamination(schoolExam);//保存考试
            assa.setSchoolId(schoolExam.getSchoolId());
            assa.setTotalRanking(ranking);//年段排名
            return assa;
        }).collect(Collectors.groupingBy(s -> s.getClassObj().getId()));
        assaList.clear();
        //获取最高分最低分平均分-按班级排布
        List<AnalysisStudentScoreAll> analysis = this.getMaxAndMinAndAvg(schoolExam.getId());
        analysis.stream().forEach(a -> {
            //对成绩进行分析
            Map<String, Double> tempC = new HashMap<>();
            tempC.put("ranking", 0.0);
            tempC.put("preScore", 0.0);
            //List<AnalysisStudentScoreAll> test = groupMap.get(a.getClassObj().getId());
            assaList.addAll(groupMap.get(a.getClassObj().getId()).stream().map(ca -> {
                ca.setMinScore(a.getMinScore());
                ca.setMaxScore(a.getMaxScore());
                ca.setAvgScore(Double.valueOf(df.format(a.getAvgScore())));
                Double preScore = tempC.get("preScore");
                int ranking = tempC.get("ranking").intValue();
                if (preScore.intValue() != ca.getScore().intValue()) {
                    ranking = groupMap.get(a.getClassObj().getId()).indexOf(ca) + 1;
                    tempC.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                    tempC.put("preScore", ca.getScore());
                }
                ca.setClassRanking(ranking);
                return ca;
            }).collect(Collectors.toList()));
        });
        //进行数据存储
        this.batchSaveAnalysisStudentScoreAll(assaList);
    }

    /**
     * 获取总分的最高分最低分平均分
     * 排除全缺考
     *
     * @return
     */
    private List<AnalysisStudentScoreAll> getMaxAndMinAndAvg(String examinationId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria("missing").is(false).and("schoolExamId").is(examinationId)));
        operations.add(Aggregation.group("student", "clazz").sum("score").as("score"));//算总分
        //算出最高分最低分平均分
        operations.add(Aggregation.group("_id.clazz").min("score").as("minScore").max("score").as("maxScore").avg("score").as("avgScore"));
        operations.add(Aggregation.project("minScore", "maxScore", "avgScore").and("_id").as("classObj").andExclude("_id"));
        List<AnalysisStudentScoreAll> analysisStudentScoreAlls = mot.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalysisStudentScoreAll.class).getMappedResults();
        return analysisStudentScoreAlls;
    }

    /**
     * 查询总成绩列表 同时关联初各个科目成绩
     * db.getCollection('analysisStudentScoreAll').aggregate([
     * { "$match" : { "examination._id" : "2074251878692904960", "classObj._id" : "1"} },
     * {$lookup:{
     * from:'analysisStudentScore',
     * let:{all_exam_id:"$examination._id",all_student_id:"$student._id"},
     * pipeline:[
     * {
     * $match:{
     * $expr:{
     * $and:[
     * {$eq:["$examination._id","$$all_exam_id"]},
     * {$eq:["$student._id","$$all_student_id"]}
     * ]
     * }
     * }
     * },
     * {
     * $project :{subjectName:"$subject.name",subjectId:"$subject._id",score:1,_id:0}
     * }
     * ],
     * as:"stuScore"
     * }
     * },
     * {$unwind:"$stuScore"},
     * {
     * $group:{_id:{"examination":"$examination"},"语文":{$max:{$cond:[{$eq:["$stuScore.subjectId","1"]},"$stuScore.score",0]}},
     * "数学":{$max:{$cond:[{$eq:["$stuScore.subjectId","2"]},"$stuScore.score",0]}},
     * "英语":{$max:{$cond:[{$eq:["$stuScore.subjectId","3"]},"$stuScore.score",0]}}}
     * },
     * {
     * $replaceRoot:{newRoot:{$mergeObjects:["$_id","$$ROOT"]}}
     * }
     * {$project:{
     * "1":{$cond:[{$eq:["$stuScore.subjectId","1"]},"$stuScore.score",0]},
     * "2":{$cond:[{$eq:["$stuScore.subjectId","2"]},"$stuScore.score",0]},
     * "3":{$cond:[{$eq:["$stuScore.subjectId","3"]},"$stuScore.score",0]}
     * }
     * },
     * ])
     *
     * @param
     * @return
     */
    public List<Map<String, Object>> findAnalysisStuScoreAllListByCondition(AnalysisVo analysisVo) {
        List<Bson> operas = new ArrayList<>();
        Document matchObj = new Document("examination._id", analysisVo.getExaminationId());
        if (analysisVo.getClassId() != null) {
            matchObj.append("classObj._id", analysisVo.getClassId());
        }
        if (analysisVo.getStudentId() != null) {
            matchObj.append("student._id", analysisVo.getStudentId());
        }
        Document match = new Document("$match", matchObj);//筛选条件
        Document lookup = new Document("$lookup", new Document("from", "analysisStudentScore")
                .append("let", new Document("all_exam_id", "$examination._id").append("all_student_id", "$student._id"))
                .append("pipeline", Arrays.asList(
                        new Document("$match", new Document("$expr", new Document("$and", Arrays.asList(new Document("$eq", Arrays.asList("$examination._id", "$$all_exam_id")), new Document("$eq", Arrays.asList("$student._id", "$$all_student_id"))))))
                        , new Document("$project", new Document("subjectName", "$subject.name").append("subjectId", "$subject._id").append("score", 1).append("_id", 0))
                ))
                .append("as", "stuScore"));
        //展示姓名、座号、班号、总分,是否缺考,班级排名,年级排名
        Document sort = new Document("$sort", new Document("score", -1));

        MongoCollection<Document> collection = mot.getCollection("analysisStudentScoreAll");
        AggregateIterable<Document> result = null;
        if (analysisVo.isShowAll()) {
            Document unwind = new Document("$unwind", "$stuScore");
            Document groupObj = new Document("_id", new Document("examination", "$examination").append("student", "$student").append("classObj", "$classObj").append("total", "$total").append("score", "$score").append("classRanking", "$classRanking").append("totalRanking", "$totalRanking").append("missing", "$missing"));
            //Document replaceRoot = new Document("$replaceRoot",new Document("newRoot",new Document("$mergeObjects",Arrays.asList("$_id","$$ROOT"))));
            Document projectObj = new Document("seatNumber", "$_id.student.seatNumber").append("studentName", "$_id.student.name")
                    .append("classNum", "$_id.classObj.number").append("score", "$_id.score")
                    .append("missing", "$_id.missing").append("classRanking", "$_id.classRanking").append("totalRanking", "$_id.totalRanking");
            SchoolExam schoolExam = schoolExamFeign.findSchoolExamById(analysisVo.getExaminationId());
            for (JwCourse c : schoolExam.getCourses()) {
                projectObj.append(c.getId(), "$" + c.getId());
                groupObj.append(c.getId(), new Document("$max", new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList("$stuScore.subjectId", c.getId())), "$stuScore.score", 0))));
            }
            Document group = new Document("$group", groupObj);
            Document project = new Document("$project", projectObj.append("_id", false));
            operas.addAll(Arrays.asList(match, lookup, unwind, group, project, sort));
        } else {
            Document projectObj = new Document("seatNumber", "$student.seatNumber").append("studentName", "$student.name")
                    .append("classNum", "$classObj.number").append("score", "$score")
                    .append("missing", 1).append("classRanking", 1).append("totalRanking", 1);
            Document project = new Document("$project", projectObj.append("_id", false));
            operas.addAll(Arrays.asList(match, lookup, project, sort));
        }
        //分页处理
        if (analysisVo.getPager() != null && analysisVo.getPager().getPaging()) {
            operas.add(new Document("$skip", analysisVo.getPager().getBeginRow()));
            operas.add(new Document("$limit", analysisVo.getPager().getPageSize()));

        }
        result = collection.aggregate(operas);
        List<Map<String, Object>> rlist = new ArrayList<>();
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        return rlist;
    }

    public long findAnalysisStuScoreAllCountByCondition(AnalysisVo analysisVo) {
        Criteria criteria = new Criteria();
        criteria.and("examination.id").is(analysisVo.getExaminationId());
        if (analysisVo.getClassId() != null) {
            criteria.and("classObj.id").is(analysisVo.getClassId());
        }
        if (analysisVo.getStudentId() != null) {
            criteria.and("student.id").is(analysisVo.getStudentId());
        }
        return mot.count(query(criteria), AnalysisStudentScoreAll.class);
    }

    public List<SchoolExam> findSchoolExam4Student(String studentId, SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        List<Criteria> cl = new ArrayList<>();
        //先将考试提取出来
        operations.add(Aggregation.match(Criteria.where("student.id").is(studentId)));
        operations.add(Aggregation.replaceRoot("examination"));
        Criteria criteria = new Criteria();
        //周期处理
        if (schoolExam.getTimeMark() != null) {
            Map<String,String> map = getTime(schoolExam.getTimeMark(), schoolExam.getSchoolId());
            //默认限制本学年
            cl.add(Criteria.where("schoolYearId").is(map.get("schoolYearId")));
            if(schoolExam.getTimeMark().equals("3")){//本学期
                cl.add(Criteria.where("term").is(map.get("term")));
            }else{
                if(StringUtils.isNotEmpty(map.get("searchStartTime")))
                    cl.add(Criteria.where("examTime").gte(map.get("searchStartTime")));
                if(StringUtils.isNotEmpty(map.get("searchEndTime")))
                    cl.add(Criteria.where("examTime").lte(map.get("searchEndTime")));
                schoolExam.setSearchStartTime(null);
                schoolExam.setSearchEndTime(null);
            }
            schoolExam.setTimeMark(null);
        }

        if (schoolExam.getCourses() != null && schoolExam.getCourses().size() > 0) {
            criteria.and("courses._id").in(schoolExam.getCourses().stream().map(JwCourse::getId).toArray());
            schoolExam.setCourses(null);
        }
        if (schoolExam.getClasses() != null && schoolExam.getClasses().size() > 0) {
            criteria.orOperator(schoolExam.getClasses().stream().map(se ->
                    Criteria.where("classes").elemMatch(Criteria.where("id").is(se.getId()).and("enrollYear").is(se.getEnrollYear()))
            ).toArray(Criteria[]::new));
            schoolExam.setClasses(null);
        }
        if (StrUtil.isNotEmpty(schoolExam.getExamTypeId())) {
            criteria.and("examTypeId").is(schoolExam.getExamTypeId());
        }
        if (StrUtil.isNotEmpty(schoolExam.getName())) {
            criteria.and("name").regex(schoolExam.getName());
        }
        criteria.and("schoolId").is(schoolExam.getSchoolId());
        if(StrUtil.isNotEmpty(schoolExam.getId()))
            criteria.and("id").is(schoolExam.getId());
        //cl.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        if (cl.size() > 0)
            criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolExam.getPager(), operations);
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScoreAll.class, operations), SchoolExam.class).getMappedResults();
    }

    /**
     * 获取周期时间段
     *
     * @return
     */
    private Map getTime(String timeMark, String schoolId) {
        Map timeMap = new HashMap();
        CurSchoolYear curSchoolYear;
        Date date;
        curSchoolYear = schoolYearFeign.findCurSchoolYear(schoolId);
        timeMap.put("schoolYearId", curSchoolYear.getSchoolYearId());
        switch (timeMark) {
            case "1"://近一周
                date = DateUtil.lastWeek();
                timeMap.put("searchStartTime", date.toString());
                timeMap.put("searchEndTime", DateUtil.now());
                break;
            case "2"://近一月
                date = DateUtil.lastMonth();
                timeMap.put("searchStartTime", date.toString());
                timeMap.put("searchEndTime", DateUtil.now());
                break;
            case "3"://本学期
                timeMap.put("term", curSchoolYear.getTerm());
                break;
        }

        return timeMap;
    }


}
