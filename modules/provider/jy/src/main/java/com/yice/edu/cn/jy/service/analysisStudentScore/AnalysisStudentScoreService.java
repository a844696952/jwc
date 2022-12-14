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
        //????????????????????????????????????????????????
        Criteria criteria = new Criteria();
        //????????????????????????????????????????????????
        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analysisStudentScore.getPager(), operations);
        return mot.aggregate(Aggregation.newAggregation(AnalysisStudentScore.class, operations), AnalysisStudentScore.class).getMappedResults();

    }

    public long findAnalysisStudentScoreCountByCondition(AnalysisStudentScore analysisStudentScore) {
        Criteria criteria = new Criteria();
        //????????????????????????????????????????????????

        criteria.andOperator(MongoKit.buildCriteria(analysisStudentScore, analysisStudentScore.getPager()));

        return mot.count(query(criteria), AnalysisStudentScore.class);
    }

    public AnalysisStudentScore findOneAnalysisStudentScoreByCondition(AnalysisStudentScore analysisStudentScore) {
        Example<AnalysisStudentScore> example = Example.of(analysisStudentScore, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        //????????????????????????????????????????????????
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
     * ???????????????
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }

    /**
     * ??????????????????
     * ????????????????????????
     * ??????????????????
     */
    public void analysisByExaminationId(String examinationId) {
        Log.info("????????????????????????????????????");
        DecimalFormat df = new DecimalFormat("#.0");
        //???????????? ?????????????????????
        this.deleteAnalysisStudentScoreByExaminationId(examinationId);
        //?????????????????????
        SchoolExam se = schoolExamFeign.findSchoolExamById(examinationId);
        if (se == null || se.getCourses() == null)//??????????????????????????????????????????
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
        schoolExam.setCourses(se.getCourses());//????????????
        schoolExam.setClasses(se.getClasses());//????????????
        //??????????????????
        List<JwCourse> courses = se.getCourses();
        if (courses != null && courses.size() > 0) {
            Log.info("??????????????????");
            //??????????????????
            final List<AnalysisStudentScore> assList = new ArrayList<>();
            //?????????????????????????????????????????????
            final CountDownLatch countDownLatch = new CountDownLatch(courses.size());
            courses.forEach(course ->
                    getExecutor().execute(() -> {
                        //??????????????????????????????????????????????????? ??????????????????????????????
                        StuScore stuScore = new StuScore();
                        stuScore.setSchoolExamId(examinationId);
                        stuScore.setCourse(course);
                        List<AnalysisStudentScore> stuScores = new ArrayList<>();
                        stuScores.addAll(this.findStuScoreListByCondition(stuScore));
                        //?????????????????????
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
            //?????????????????? ????????? ????????? ?????????
            List<AnalysisStudentScore> analysis = this.getMaxAndMinAndAvg(examinationId);
            try {
                countDownLatch.await();
                Log.info("???????????????" + assList.size());
                //?????????????????????????????????-?????????
                Map<String, List<AnalysisStudentScore>> groupMap = assList.stream().collect(Collectors.groupingBy(ass -> ass.getClassObj().getId()));
                assList.clear();//????????????????????? ????????????
                //???????????????
                groupMap.forEach((classId, v) -> {
                    //????????????????????????????????????
                    Map<String, List<AnalysisStudentScore>> subjectMap = v.stream().collect(Collectors.groupingBy(s -> s.getSubject().getId()));
                    //????????????????????????
                    analysis.stream().filter(as -> as.getClassObj().getId().equals(classId)).forEach(ass -> {
                        //?????????????????????
                        Map<String, Double> temp = new HashMap<>();
                        temp.put("ranking", 0.0);
                        temp.put("preScore", 0.0);
                        //?????????????????????????????? ?????????foreach ??????
                        assList.addAll(subjectMap.get(ass.getSubject().getId()).stream().map(a -> {
                            Double preScore = temp.get("preScore");
                            int ranking = temp.get("ranking").intValue();
                            if (preScore.intValue() != a.getScore().intValue()) {
                                ranking = subjectMap.get(ass.getSubject().getId()).indexOf(a) + 1;
                                temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                temp.put("preScore", a.getScore());
                            }
                            a.setClassRanking(ranking);//????????????
                            a.setMaxScore(ass.getMaxScore());//?????????????????????
                            a.setMinScore(ass.getMinScore());//?????????????????????
                            a.setAvgScore(Double.valueOf(df.format(ass.getAvgScore())));//?????????????????????
                            return a;
                        }).collect(Collectors.toList()));
                    });
                    subjectMap.clear();
                });
                groupMap.clear();//??????????????????
                analysis.clear();//??????????????????????????????
            } catch (Exception e) {
                e.getMessage();
            }
            Log.info("??????????????????" + assList.size());
            //??????????????????
            Log.info("??????????????????" + assList.size());
            mot.bulkOps(BulkOperations.BulkMode.UNORDERED, AnalysisStudentScore.class).insert(assList).execute();
            Log.info("??????????????????");
            assList.clear();
        }
    }

    public void analysisByExaminationIdNew(SchoolExam se) {
        Log.info("????????????????????????????????????");
        //???????????? ?????????????????????
        this.deleteAnalysisStudentScoreByExaminationId(se.getId());

        if (se == null || se.getCourses() == null)//??????????????????????????????????????????
            return;

        //??????????????????
        List<JwCourse> courses = se.getCourses();
        if (courses != null && courses.size() > 0) {
            Log.info("??????????????????");
            //??????????????????
            final List<AnalysisStudentScore> assList = new ArrayList<>();
            //?????????????????????????????????????????????????????????
            StuScore stuScore = new StuScore();
            stuScore.setSchoolExamId(se.getId());
            Map<String, List<AnalysisStudentScore>> courseMap = this.findStuScoreListByCondition(stuScore).stream().collect(Collectors.groupingBy(ass -> ass.getSubject().getId()));
            final CountDownLatch countDownLatch = new CountDownLatch(courseMap.size());
            Log.info("??????????????????" + assList.size());
            courseMap.forEach((subjectId, list) ->
                    getExecutor().execute(() -> {
                        //?????????????????????
                        Map<String, Double> temp = new ConcurrentHashMap<>();
                        temp.put("ranking", 0.0);
                        temp.put("preScore", 0.0);
                        list.stream().map(ss -> {
                            //???????????????????????????
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
                            //??????????????? ?????????????????? ?????????????????? ????????? ?????????
                            temp.put("ranking", 0.0);
                            temp.put("preScore", 0.0);
                            //?????????????????????????????? ?????????foreach ??????
                            assList.addAll(cList.stream().map(a -> {
                                Double preScore = temp.get("preScore");
                                int ranking = temp.get("ranking").intValue();
                                if (preScore.intValue() != a.getScore().intValue()) {
                                    ranking = cList.indexOf(a) + 1;
                                    temp.put("ranking", Double.parseDouble(String.valueOf(ranking)));
                                    temp.put("preScore", a.getScore());
                                }
                                a.setClassRanking(ranking);//????????????
                                return a;
                            }).collect(Collectors.toList()));
                        });
                        countDownLatch.countDown();
                    })
            );
            try {
                countDownLatch.await();
                Log.info("??????????????????" + assList.size());
                //??????????????????
                Log.info("??????????????????" + assList.size());
                mot.insertAll(assList);
                //mot.bulkOps(BulkOperations.BulkMode.UNORDERED, AnalysisStudentScore.class).insert(assList).execute();
                Log.info("??????????????????");
            } catch (Exception e) {
                Log.error(e.getLocalizedMessage());
                Log.info("??????????????????");
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
     * ??????????????????????????????????????????
     * ???????????????
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
     * ??????????????????
     *
     * @param examinationId
     */
    public void analysisStudentScore(String examinationId) {
        //?????????????????????
        SchoolExam se = schoolExamFeign.findSchoolExamById(examinationId);
        //?????????
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
        schoolExam.setCourses(se.getCourses());//????????????
        schoolExam.setClasses(se.getClasses());//????????????
        schoolExam.setType(se.getType());//??????????????????????????????
        schoolExam.setTerm(se.getTerm());//??????
        schoolExam.setSchoolYearId(se.getSchoolYearId());//??????
        schoolExam.setFromTo(se.getFromTo());//????????????
        schoolExam.setCreateTime(se.getCreateTime());
        //????????????
        analyseClassScoreService.analyseClassScoreGroupByCourse(se);
        //????????????
        analyseExamTopicService.analyseExamTopicData(se);
        //????????????
        analyseExamTopicTypeService.analyseExamTopicTypes4Exam(se);

        getExecutor().execute(() ->
                this.analysisByExaminationIdNew(schoolExam)
        );
        getExecutor().execute(() ->
                analysisStudentScoreAllService.analysisByExamination(schoolExam)
        );
        //????????????????????????????????????????????????????????????
        if (schoolExam.getCourses().stream().anyMatch(c -> StringUtils.isNotEmpty(c.getPaperId()))) {
            getExecutor().execute(() ->
                    analyseGradeKnowledgeService.analyGradeKnowledge4Exam(schoolExam)
            );
        }
    }

    public List<AnalysisStudentScore> findStudentExamScoreListByCondition(AnalysisStudentScore analysisStudentScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        //????????????????????????????????????????????????
        Criteria criteria = new Criteria();
        //????????????????????????????????????????????????
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
        //????????????????????????????????????????????????
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
