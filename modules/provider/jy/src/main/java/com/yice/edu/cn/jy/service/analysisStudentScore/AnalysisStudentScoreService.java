package com.yice.edu.cn.jy.service.analysisStudentScore;

import com.esotericsoftware.minlog.Log;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.jy.feignClient.examManage.SchoolExamFeign;
import com.yice.edu.cn.jy.feignClient.examManage.StuScoreFeign;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseClassScoreService;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicService;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicTypeService;
import com.yice.edu.cn.jy.service.analysisKnowledge.AnalyseGradeKnowledgeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AnalysisStudentScoreService {
    private static ExecutorService executorService;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private SchoolExamFeign schoolExamFeign;
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;
    @Autowired
    private AnalyseGradeKnowledgeService analyseGradeKnowledgeService;
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;
    @Autowired
    private AnalyseExamTopicTypeService analyseExamTopicTypeService;

    public AnalysisStudentScore findAnalysisStudentScoreById(String id) {
        return mot.findById(id, AnalysisStudentScore.class);
    }

    public void saveAnalysisStudentScore(AnalysisStudentScore analysisStudentScore) {
        analysisStudentScore.setId(sequenceId.nextId());
        mot.insert(analysisStudentScore);
    }

    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(AnalysisStudentScore analysisStudentScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        //对对象类型的属性进行重构查询条件
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analysisStudentScore.getPager(), operations);
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScore.class, operations), AnalysisStudentScore.class).getMappedResults();

    }

    public long findAnalysisStudentScoreCountByCondition(AnalysisStudentScore analysisStudentScore) {
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件

        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));

        return mot.count(query(criteria), AnalysisStudentScore.class);
    }

    public AnalysisStudentScore findOneAnalysisStudentScoreByCondition(AnalysisStudentScore analysisStudentScore) {
        Example<AnalysisStudentScore> example = Example.of(analysisStudentScore, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件
        operations.add(Aggregation.match(new Criteria().alike(example).andOperator(criteria)));
        Pager pager = analysisStudentScore.getPager();
        if (pager != null && pager.getIncludes() != null) {
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScore.class, operations), AnalysisStudentScore.class).getUniqueMappedResult();
    }

    public void updateAnalysisStudentScore(AnalysisStudentScore analysisStudentScore) {
        mot.updateFirst(query(where("id").is(analysisStudentScore.getId())), MongoKit.update(analysisStudentScore), AnalysisStudentScore.class);
    }

    public void deleteAnalysisStudentScore(String id) {
        mot.remove(query(where("id").is(id)), AnalysisStudentScore.class);
    }

    public void deleteAnalysisStudentScoreByExaminationId(String examinationId) {
        mot.remove(query(where("examination.id").is(examinationId)), AnalysisStudentScore.class);
    }

    public void deleteAnalysisStudentScoreByCondition(AnalysisStudentScore analysisStudentScore) {
        Example<AnalysisStudentScore> example = Example.of(analysisStudentScore, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnalysisStudentScore.class);
    }

    public void batchSaveAnalysisStudentScore(List<AnalysisStudentScore> analysisStudentScores) {
        analysisStudentScores.forEach(analysisStudentScore -> analysisStudentScore.setId(sequenceId.nextId()));
        mot.insertAll(analysisStudentScores);
    }

    /**
     * 定义线程池
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }

    /**
     * 进行学情分析
     * 通过考试进行分析
     * 分析各个科目
     */
    public void analysisByExaminationId(String examinationId) {
        Log.info("学生各个科目成绩分析开始");
        DecimalFormat df = new DecimalFormat("#.0");
        //首先清空 以前的分析记录
        this.deleteAnalysisStudentScoreByExaminationId(examinationId);
        //先查询学校考试
        SchoolExam se = schoolExamFeign.findSchoolExamById(examinationId);
        if (se == null || se.getCourses() == null)//查询不到考试就没有后续步骤了
            return;
        final SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(se.getId());
        schoolExam.setName(se.getName());
        schoolExam.setExamTypeId(se.getExamTypeId());
        schoolExam.setExamTypeName(se.getExamTypeName());
        schoolExam.setType(se.getType());
        schoolExam.setGradeId(se.getGradeId());
        schoolExam.setGradeName(se.getGradeName());
        schoolExam.setTotalNum(se.getTotalNum());
        schoolExam.setSchoolId(se.getSchoolId());
        schoolExam.setExamTime(se.getExamTime());
//        schoolExam.setSchoolYear(se.getSchoolYear());
        schoolExam.setTerm(se.getTerm());
        schoolExam.setCourses(se.getCourses());//考试课程
        schoolExam.setClasses(se.getClasses());//考试班级
        //获取所有课程
        List<JwCourse> courses = se.getCourses();
        if (courses != null && courses.size() > 0) {
            Log.info("数据整合开始");
            //学生成绩列表
            final List<AnalysisStudentScore> assList = new ArrayList<>();
            //对每个课程获取学生成绩进行分析
            final CountDownLatch countDownLatch = new CountDownLatch(courses.size());
            courses.forEach(course ->
                    getExecutor().execute(() -> {
                        //获取当前考试中各科目对应的学生成绩 按成绩大到小排序输出
                        StuScore stuScore = new StuScore();
                        stuScore.setSchoolExamId(examinationId);
                        stuScore.setCourse(course);
                        List<AnalysisStudentScore> stuScores = new ArrayList<>();
                        stuScores.addAll(this.findStuScoreListByCondition(stuScore));
                        //对成绩进行分析
                        Map<String, Double> temp = new HashMap<>();
                        temp.put("ranking", 0.0);
                        temp.put("preScore", 0.0);
                        assList.addAll(stuScores.stream().map(ss -> {
                            Double preScore = temp.get("preScore");
                            int ranking = temp.get("ranking").intValue();
                            if (preScore.intValue() != ss.getScore().intValue()) {
                                ranking = stuScores.indexOf(ss) + 1;
                                temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                temp.put("preScore", ss.getScore());
                            }
                            ss.setExamination(schoolExam);
                            ss.setId(sequenceId.nextId());
                            ss.setSchoolId(schoolExam.getSchoolId());
                            ss.setTotalRanking(ranking);
                            return ss;
                        }).collect(Collectors.toList()));
                        stuScores.clear();
                        countDownLatch.countDown();
                    })
            );
            //获取班级课程 最高分 最低分 评价分
            List<AnalysisStudentScore> analysis = this.getMaxAndMinAndAvg(examinationId);
            try {
                countDownLatch.await();
                Log.info("数据整合中" + assList.size());
                //对分析后的成绩进行分组-按班级
                Map<String, List<AnalysisStudentScore>> groupMap = assList.stream().collect(Collectors.groupingBy(ass -> ass.getClassObj().getId()));
                assList.clear();//清空原先的列表 进行复用
                //按班级遍历
                groupMap.forEach((classId, v) -> {
                    //对班级小组按各个科目分组
                    Map<String, List<AnalysisStudentScore>> subjectMap = v.stream().collect(Collectors.groupingBy(s -> s.getSubject().getId()));
                    //对各个组进行遍历
                    analysis.stream().filter(as -> as.getClassObj().getId().equals(classId)).forEach(ass -> {
                        //对成绩进行分析
                        Map<String, Double> temp = new HashMap<>();
                        temp.put("ranking", 0.0);
                        temp.put("preScore", 0.0);
                        //如果这个方式排序错乱 只能用foreach 替换
                        assList.addAll(subjectMap.get(ass.getSubject().getId()).stream().map(a -> {
                            Double preScore = temp.get("preScore");
                            int ranking = temp.get("ranking").intValue();
                            if (preScore.intValue() != a.getScore().intValue()) {
                                ranking = subjectMap.get(ass.getSubject().getId()).indexOf(a) + 1;
                                temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                temp.put("preScore", a.getScore());
                            }
                            a.setClassRanking(ranking);//班级排名
                            a.setMaxScore(ass.getMaxScore());//班级科目最高分
                            a.setMinScore(ass.getMinScore());//班级科目最低分
                            a.setAvgScore(Double.valueOf(df.format(ass.getAvgScore())));//班级科目平均分
                            return a;
                        }).collect(Collectors.toList()));
                    });
                    subjectMap.clear();
                });
                groupMap.clear();//清空班级分组
                analysis.clear();//清空最高分最低分平均
            } catch (Exception e) {
                e.getMessage();
            }
            Log.info("数据整合结束" + assList.size());
            //成绩分析导入
            Log.info("导入数据开始" + assList.size());
            mot.bulkOps(BulkOperations.BulkMode.UNORDERED, AnalysisStudentScore.class).insert(assList).execute();
            Log.info("导入数据结束");
            assList.clear();
        }
    }

    public void analysisByExaminationIdNew(SchoolExam se) {
        Log.info("学生各个科目成绩分析开始");
        //首先清空 以前的分析记录
        this.deleteAnalysisStudentScoreByExaminationId(se.getId());

        if (se == null || se.getCourses() == null)//查询不到考试就没有后续步骤了
            return;

        //获取所有课程
        List<JwCourse> courses = se.getCourses();
        if (courses != null && courses.size() > 0) {
            Log.info("数据整合开始");
            //学生成绩列表
            final List<AnalysisStudentScore> assList = new ArrayList<>();
            //查询所有考试成绩然后按科目分组进行分析
            StuScore stuScore = new StuScore();
            stuScore.setSchoolExamId(se.getId());
            Map<String, List<AnalysisStudentScore>> courseMap = this.findStuScoreListByCondition(stuScore).stream().collect(Collectors.groupingBy(ass -> ass.getSubject().getId()));
            final CountDownLatch countDownLatch = new CountDownLatch(courseMap.size());
            Log.info("数据排名开始" + assList.size());
            courseMap.forEach((subjectId, list) ->
                    getExecutor().execute(() -> {
                        //对成绩进行分析
                        Map<String, Double> temp = new ConcurrentHashMap<>();
                        temp.put("ranking", 0.0);
                        temp.put("preScore", 0.0);
                        list.stream().map(ss -> {
                            //计算单科成绩总排名
                            Double preScore = temp.get("preScore");
                            int ranking = temp.get("ranking").intValue();
                            if (preScore.intValue() != ss.getScore().intValue()) {
                                ranking = list.indexOf(ss) + 1;
                                temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                temp.put("preScore", ss.getScore());
                            }
                            ss.setId(sequenceId.nextId());
                            ss.setExamination(se);
                            ss.setSchoolId(se.getSchoolId());
                            ss.setTotalRanking(ranking);
                            return ss;
                        }).collect(Collectors.groupingBy(na -> na.getClassObj().getId())).forEach((classId, cList) -> {
                            //按班级分组 进行班级排名 不统计最高分 最低分 平均分
                            temp.put("ranking", 0.0);
                            temp.put("preScore", 0.0);
                            //如果这个方式排序错乱 只能用foreach 替换
                            assList.addAll(cList.stream().map(a -> {
                                Double preScore = temp.get("preScore");
                                int ranking = temp.get("ranking").intValue();
                                if (preScore.intValue() != a.getScore().intValue()) {
                                    ranking = cList.indexOf(a) + 1;
                                    temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                    temp.put("preScore", a.getScore());
                                }
                                a.setClassRanking(ranking);//班级排名
                                return a;
                            }).collect(Collectors.toList()));
                        });
                        countDownLatch.countDown();
                    })
            );
            try {
                countDownLatch.await();
                Log.info("数据整合结束" + assList.size());
                //成绩分析导入
                Log.info("导入数据开始" + assList.size());
                mot.insertAll(assList);
                //mot.bulkOps(BulkOperations.BulkMode.UNORDERED, AnalysisStudentScore.class).insert(assList).execute();
                Log.info("导入数据结束");
            } catch (Exception e) {
                Log.error(e.getLocalizedMessage());
                Log.info("数据分析失败");
            } finally {
                courseMap.clear();
                assList.clear();
            }
        }
    }

    private List<AnalysisStudentScore> findStuScoreListByCondition(StuScore stuScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria match = new Criteria("schoolExamId").is(stuScore.getSchoolExamId());
        if (stuScore.getCourse() != null) {
            match.and("course.id").is(stuScore.getCourse().getId());
        }
        operations.add(Aggregation.match(match));
        operations.add(Aggregation.sort(DESC, "score"));
        operations.add(Aggregation.project("score", "missing", "student", "schoolExamId").and("clazz").as("classObj").and("course").as("subject").andExclude("_id"));
        List<AnalysisStudentScore> analysisStudentScores = mot.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalysisStudentScore.class).getMappedResults();
        return analysisStudentScores;
    }


    /**
     * 获取总分的最高分最低分平均分
     * 排除全缺考
     *
     * @return
     */
    private List<AnalysisStudentScore> getMaxAndMinAndAvg(String examinationId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria("missing").is(false).and("schoolExamId").is(examinationId)));
        operations.add(Aggregation.group("student", "clazz", "course").sum("score").as("score"));
        operations.add(Aggregation.group("_id.clazz", "_id.course").min("score").as("minScore").max("score").as("maxScore").avg("score").as("avgScore"));
        operations.add(Aggregation.project("minScore", "maxScore", "avgScore").and("clazz").as("classObj").and("course").as("subject"));
        List<AnalysisStudentScore> analysisStudentScores = mot.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalysisStudentScore.class).getMappedResults();
        return analysisStudentScores;
    }

    /**
     * 学生成绩分析
     *
     * @param examinationId
     */
    public void analysisStudentScore(String examinationId) {
        //先查询学校考试
        SchoolExam se = schoolExamFeign.findSchoolExamById(examinationId);
        //处理下
        final SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(se.getId());
        schoolExam.setName(se.getName());
        schoolExam.setExamTypeId(se.getExamTypeId());
        schoolExam.setExamTypeName(se.getExamTypeName());
        schoolExam.setType(se.getType());
        schoolExam.setGradeId(se.getGradeId());
        schoolExam.setGradeName(se.getGradeName());
        schoolExam.setTotalNum(se.getTotalNum());
        schoolExam.setSchoolId(se.getSchoolId());
        schoolExam.setExamTime(se.getExamTime());
        schoolExam.setCourses(se.getCourses());//考试课程
        schoolExam.setClasses(se.getClasses());//考试班级
        schoolExam.setType(se.getType());//网阅考试还是线下考试
        schoolExam.setTerm(se.getTerm());//学期
        schoolExam.setSchoolYearId(se.getSchoolYearId());//学年
        schoolExam.setFromTo(se.getFromTo());//学年名称
        schoolExam.setCreateTime(se.getCreateTime());
        //班级分析
        analyseClassScoreService.analyseClassScoreGroupByCourse(se);
        //小题分析
        analyseExamTopicService.analyseExamTopicData(se);
        //题型分析
        analyseExamTopicTypeService.analyseExamTopicTypes4Exam(se);

        getExecutor().execute(() ->
                this.analysisByExaminationIdNew(schoolExam)
        );
        getExecutor().execute(() ->
                analysisStudentScoreAllService.analysisByExamination(schoolExam)
        );
        //判断是否存在科目用我们的线上题目出的卷子
        if (schoolExam.getCourses().stream().anyMatch(c -> StringUtils.isNotEmpty(c.getPaperId()))) {
            getExecutor().execute(() ->
                    analyseGradeKnowledgeService.analyGradeKnowledge4Exam(schoolExam)
            );
        }
    }

    public List<AnalysisStudentScore> findStudentExamScoreListByCondition(AnalysisStudentScore analysisStudentScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        //对对象类型的属性进行重构查询条件
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件
        if (analysisStudentScore.getClassObj() != null) {
            if(analysisStudentScore.getClassObj().getGradeId()!=null){
                criteria.and("classObj.gradeId").is(analysisStudentScore.getClassObj().getGradeId());
            }
            analysisStudentScore.setClassObj(null);
        }
        if (analysisStudentScore.getSubject() != null) {
            criteria.and("subject.id").is(analysisStudentScore.getSubject().getId());
            analysisStudentScore.setSubject(null);
        }
        if (analysisStudentScore.getStudent() != null) {
            criteria.and("student.id").is(analysisStudentScore.getStudent().getId());
            analysisStudentScore.setStudent(null);
        }
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analysisStudentScore.getPager(), operations);
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScore.class, operations), AnalysisStudentScore.class).getMappedResults();
    }

    public Long findStudentScoresCountByCondition(AnalysisStudentScore analysisStudentScore) {
        Criteria criteria = new Criteria();
        //对对象类型的属性进行重构查询条件
        if (analysisStudentScore.getClassObj() != null) {
            if(analysisStudentScore.getClassObj().getGradeId()!=null){
                criteria.and("classObj.gradeId").is(analysisStudentScore.getClassObj().getGradeId());
            }
            analysisStudentScore.setClassObj(null);
        }
        if (analysisStudentScore.getSubject() != null) {
            criteria.and("subject.id").is(analysisStudentScore.getSubject().getId());
            analysisStudentScore.setSubject(null);
        }
        if (analysisStudentScore.getStudent() != null) {
            criteria.and("student.id").is(analysisStudentScore.getStudent().getId());
            analysisStudentScore.setStudent(null);
        }
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));
        return  mot.count(query(criteria), AnalysisStudentScore.class);
    }
}
