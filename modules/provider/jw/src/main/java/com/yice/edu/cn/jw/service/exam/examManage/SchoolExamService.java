package com.yice.edu.cn.jw.service.exam.examManage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.*;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolExamService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private MongoConverter mongoConverter;
    @Autowired
    private PaperService paperService;
    @Autowired
    private SchoolYearService schoolYearService;
    public SchoolExam findSchoolExamById(String id) {
        return mot.findById(id,SchoolExam.class);
    }
    public void saveSchoolExam(SchoolExam schoolExam) {
        schoolExam.setId(sequenceId.nextId());
        mot.insert(schoolExam);
    }
    public List<SchoolExam> findSchoolExamListByCondition(SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(schoolExam, schoolExam.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolExam.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExam.class).getMappedResults();

    }
    public long findSchoolExamCountByCondition(SchoolExam schoolExam) {
        Criteria criteria = MongoKit.buildCriteria(schoolExam, schoolExam.getPager());
        return mot.count(query(criteria), SchoolExam.class);
    }

    /**
     * 同一时间，同样班级，同一课程的线下考试不能重复添加
     * @param schoolExam
     * @return
     */
    public long checkSchoolExamNum(SchoolExam schoolExam) {
        List<Criteria> c1 = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<AggregationOperation> operations = new ArrayList<>();
        if(schoolExam.getCourses()!=null&&schoolExam.getCourses().size()>0){
            //  cl.add(Criteria.where("courses").elemMatch(Criteria.where("id").in(schoolExam.getCourses().stream().map(JwCourse::getId).toArray())));
            criteria.and("courses.id").in(schoolExam.getCourses().stream().map(JwCourse::getId).toArray());
            schoolExam.setCourses(null);
        }
        if(schoolExam.getClasses()!=null&&schoolExam.getClasses().size()>0){
            criteria.and("classes.number").in(schoolExam.getClasses().stream().map(JwClasses::getNumber).toArray());
            c1.add(Criteria.where("classes.gradeId").is(schoolExam.getClasses().get(0).getGradeId()));
            schoolExam.setClasses(null);
        }
        c1.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        criteria.andOperator(c1.toArray(new Criteria[c1.size()]));

        return mot.count(query(criteria), SchoolExam.class);

    }

    public SchoolExam findOneSchoolExamByCondition(SchoolExam schoolExam) {
        Example<SchoolExam> example = Example.of(schoolExam, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = schoolExam.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExam.class).getUniqueMappedResult();
    }

    public void updateSchoolExam(SchoolExam schoolExam) {
        mot.updateFirst(query(where("id").is(schoolExam.getId())), MongoKit.update(schoolExam),SchoolExam.class);
    }
    public void deleteSchoolExam(String id) {
        mot.remove(query(where("id").is(id)),SchoolExam.class);
    }
    public void deleteSchoolExamByCondition(SchoolExam schoolExam) {
        Example<SchoolExam> example = Example.of(schoolExam, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, SchoolExam.class);
        paperService.setPaperFlags(schoolExam,false);
    }
    public void batchSaveSchoolExam(List<SchoolExam> schoolExams){
        schoolExams.forEach(schoolExam -> schoolExam.setId(sequenceId.nextId()));
        mot.insertAll(schoolExams);
    }

    /**
     * 带搜索时间
     * @param schoolExam
     * @return
     */
    public List<SchoolExam> findSchoolExamListByCondition1(SchoolExam schoolExam) {
        String startTime=null;
        if(!StrUtil.hasEmpty(schoolExam.getSearchStartTime())){
            startTime=schoolExam.getSearchStartTime();
        }
        String endTime=null;
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            endTime=schoolExam.getSearchEndTime();
        }
        schoolExam.setSearchStartTime(null);
        schoolExam.setSearchEndTime(null);
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(schoolExam, schoolExam.getPager());
        if(startTime!=null&&endTime!=null)
            criteria.andOperator(new Criteria("examTime").gte(startTime),new Criteria("examTime").lte(endTime));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolExam.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExam.class).getMappedResults();

    }
    /**
     * 带搜索时间
     * @param schoolExam
     * @return
     */
    public long findSchoolExamCountByCondition1(SchoolExam schoolExam) {
        String startTime=null;
        if(!StrUtil.hasEmpty(schoolExam.getSearchStartTime())){
            startTime=schoolExam.getSearchStartTime();
        }
        String endTime=null;
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            endTime=schoolExam.getSearchEndTime();
        }
        schoolExam.setSearchStartTime(null);
        schoolExam.setSearchEndTime(null);
        Criteria criteria = MongoKit.buildCriteria(schoolExam, schoolExam.getPager());
        if(startTime!=null&&endTime!=null)
            criteria.andOperator(new Criteria("examTime").gte(startTime),new Criteria("examTime").lte(endTime));
        return mot.count(query(criteria), SchoolExam.class);
    }


    /**
     * 学情分析特殊筛选条件使用,任课老师专用
     * @param schoolExam
     * @return
     */
    public List<SchoolExam> findSchoolExamListByConditionForTeacher(SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        schoolExam.setTimeMark(null);
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.isEmpty(schoolExam.getSearchStartTime())){
            cl.add(Criteria.where("examTime").gte(schoolExam.getSearchStartTime()));
            schoolExam.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            cl.add(Criteria.where("examTime").lte(schoolExam.getSearchEndTime()));
            schoolExam.setSearchEndTime(null);
        }

        if(schoolExam.getTeacherClassesList()!=null&&schoolExam.getTeacherClassesList().size()>0){
            criteria.orOperator(schoolExam.getTeacherClassesList().stream().map(se->
                    Criteria.where("classes.id").is(se.getClassesId()).and("courses.id").is(se.getCourseId())
            ).toArray(Criteria[]::new));
            schoolExam.setTeacherClassesList(null);
        }
        cl.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolExam.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExam.class).getMappedResults();
    }


    /**
     * 学情分析特殊筛选条件使用,任课老师专用
     * @param schoolExam
     * @return
     */
    public long findSchoolExamCountByConditionForTeacher(SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        schoolExam.setTimeMark(null);
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.isEmpty(schoolExam.getSearchStartTime())){
            cl.add(Criteria.where("examTime").gte(schoolExam.getSearchStartTime()));
            schoolExam.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            cl.add(Criteria.where("examTime").lte(schoolExam.getSearchEndTime()));
            schoolExam.setSearchEndTime(null);
        }

        if(schoolExam.getTeacherClassesList()!=null&&schoolExam.getTeacherClassesList().size()>0){
            criteria.orOperator(schoolExam.getTeacherClassesList().stream().map(se->
                    Criteria.where("classes.id").is(se.getClassesId()).and("courses.id").is(se.getCourseId())
            ).toArray(Criteria[]::new));
            schoolExam.setTeacherClassesList(null);
        }
        cl.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        return mot.count(query(criteria), SchoolExam.class);
    }

    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    public List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        schoolExam.setTimeMark(null);
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.isEmpty(schoolExam.getSearchStartTime())){
            cl.add(Criteria.where("examTime").gte(schoolExam.getSearchStartTime()));
            schoolExam.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            cl.add(Criteria.where("examTime").lte(schoolExam.getSearchEndTime()));
            schoolExam.setSearchEndTime(null);
        }
        if(schoolExam.getCourses()!=null&&schoolExam.getCourses().size()>0){
          //  cl.add(Criteria.where("courses").elemMatch(Criteria.where("id").in(schoolExam.getCourses().stream().map(JwCourse::getId).toArray())));
           criteria.and("courses.id").in(schoolExam.getCourses().stream().map(JwCourse::getId).distinct().toArray());
            schoolExam.setCourses(null);
        }
        if(schoolExam.getClasses()!=null&&schoolExam.getClasses().size()>0){
            criteria.orOperator(schoolExam.getClasses().stream().map(se->
                    Criteria.where("classes").elemMatch(Criteria.where("id").is(se.getId()).and("enrollYear").is(se.getEnrollYear()))
            ).toArray(Criteria[]::new));
            schoolExam.setClasses(null);
        }
        cl.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(schoolExam.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExam.class).getMappedResults();
    }
    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    public long findSchoolExamCountByCondition2(SchoolExam schoolExam) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        schoolExam.setTimeMark(null);
        List<Criteria> cl = new ArrayList<>();
        if(!StrUtil.isEmpty(schoolExam.getSearchStartTime())){
            cl.add(Criteria.where("examTime").gte(schoolExam.getSearchStartTime()));
            schoolExam.setSearchStartTime(null);
        }
        if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
            cl.add(Criteria.where("examTime").lte(schoolExam.getSearchEndTime()));
            schoolExam.setSearchEndTime(null);
        }
        if(schoolExam.getCourses()!=null&&schoolExam.getCourses().size()>0){
            criteria.and("courses.id").in(schoolExam.getCourses().stream().map(JwCourse::getId).distinct().toArray());
            schoolExam.setCourses(null);
        }
        if(schoolExam.getClasses()!=null&&schoolExam.getClasses().size()>0){
            criteria.orOperator(schoolExam.getClasses().stream().map(se->
                    Criteria.where("classes").elemMatch(Criteria.where("id").is(se.getId()).and("enrollYear").is(se.getEnrollYear()))
            ).toArray(Criteria[]::new));
            schoolExam.setClasses(null);
        }
        cl.add(MongoKit.buildCriteria(schoolExam, schoolExam.getPager()));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));

        return mot.count(query(criteria), SchoolExam.class);
    }


    public List<SchoolExam> findOnlineSchoolExamNow(String schoolId) {
        CurSchoolYear schoolYear = schoolYearService.findCurSchoolYear(schoolId);
        List<SchoolExam> exams = mot.execute(SchoolExam.class, new CollectionCallback<List<SchoolExam>>() {
            @Override
            public List<SchoolExam> doInCollection(MongoCollection<Document> collection) throws MongoException, DataAccessException {
                Document match = new Document("schoolId", schoolId).append("type", 1).append("examTime", new Document("$lte", DateUtil.format(new Date(), "yyyy-MM-dd")))
                        .append("finished", false).append("courses.allUpload", false).append("fromTo",schoolYear.getFromTo());
                Document project = Document.parse("{name:1,\"courses._id\":1,\"courses.alias\":1,\"courses.allUpload\":1}");
                Document sort = new Document("examTime", 1);
                List<SchoolExam> r = new ArrayList<>();
                collection.find(match).sort(sort).projection(project).forEach((Block<Document>) document -> {
                    SchoolExam se = mongoConverter.read(SchoolExam.class, document);
                    //去掉已经上传答题卡的科目
                    for (int i = se.getCourses().size()-1; i >=0; i--) {
                        JwCourse course = se.getCourses().get(i);
                        if(course.getAllUpload()!=null&&course.getAllUpload()){
                            se.getCourses().remove(i);
                        }
                        course.setAllUpload(null);
                    }
                    r.add(se);
                });
                return r;
            }
        });

        return exams;
    }

    public String commitStuScore(List<StuScore> stuScores) {
        for (StuScore stuScore : stuScores) {
            stuScore.setId(sequenceId.nextId());
        }
        StuScore score = stuScores.get(0);

        try {
            mot.insertAll(stuScores);
            //标记一下本科目上传完毕
            mot.getCollection("schoolExam").updateOne(new Document("_id",score.getSchoolExamId()),
                    Document.parse("{ $set: { \"courses.$[elem].allUpload\" : true } }") ,
                    new UpdateOptions().arrayFilters(Arrays.asList(new Document("elem._id",score.getCourse().getId())))
            );
        } catch (Exception e) {
            e.printStackTrace();
            mot.remove(query(where("schoolExamId").is(score.getSchoolExamId()).and("course.id").is(score.getCourse().getId())),SchoolExam.class);
            mot.getCollection("schoolExam").updateOne(new Document("_id",score.getSchoolExamId()),
                    Document.parse("{ $set: { \"courses.$[elem].allUpload\" : false } }") ,
                    new UpdateOptions().arrayFilters(Arrays.asList(new Document("elem._id",score.getCourse().getId())))
            );
            return e.getMessage();
        }

        return null;
    }

    public List<ExamStudentInfo> findExamStudentsBySchoolExamId(String schoolExamId) {
        ExamStudent schoolExam = mot.findOne(query(where("schoolExamId").is(schoolExamId)), ExamStudent.class);
        return schoolExam!=null?schoolExam.getExamStudentInfos():new ArrayList<>();
    }

    public List<StuScore> findStuScoresByCond(ScoreCond scoreCond) {
        Document match = new Document("schoolExamId", scoreCond.getSchoolExamId()).append("course._id",scoreCond.getCourseId());
        if(StrUtil.isNotEmpty(scoreCond.getClazzId())){
            match.append("clazz._id",scoreCond.getClazzId());
        }
        if(StrUtil.isNotEmpty(scoreCond.getExamNo())){
            match.append("student.examNo",scoreCond.getExamNo());
        }
        FindIterable<Document> result = mot.getCollection(mot.getCollectionName(StuScore.class)).find(match).skip(scoreCond.getPager().getBeginRow().intValue()).limit(scoreCond.getPager().getPageSize()).projection(Document.parse("{answerSheetDatas:0,clazz:0,course:0}"));
        List<StuScore> stuScores=new ArrayList<>();
        result.forEach((Block<Document>) document -> stuScores.add(mot.getConverter().read(StuScore.class, document)));
        return stuScores;
    }

    public long findStuScoreCountByCond(ScoreCond scoreCond) {
        Document match = new Document("schoolExamId", scoreCond.getSchoolExamId()).append("course._id",scoreCond.getCourseId());
        if(StrUtil.isNotEmpty(scoreCond.getClazzId())){
            match.append("clazz._id",scoreCond.getClazzId());
        }
        if(StrUtil.isNotEmpty(scoreCond.getExamNo())){
            match.append("student.examNo",scoreCond.getExamNo());
        }
        return mot.getCollection(mot.getCollectionName(StuScore.class)).count(match);
    }


    public SchoolExam deleteSchoolExamById(String id){
      return mot.findAndRemove(query(where("id").is(id)),SchoolExam.class);
    }

    public List<JwCourse> findListCoursePaper(String schoolExamId){
       SchoolExam schoolExam =  findSchoolExamById(schoolExamId);
       List<JwCourse> jwCourses = new ArrayList<>();

       int count = schoolExam.getCourses().size();
       for(int i=0;i<count;i++){
           JwCourse jwCourse = new JwCourse();
           if(schoolExam.getCourses().get(i).getPaperId()!=null){
               jwCourse.setPaperId(schoolExam.getCourses().get(i).getPaperId());
           }else{
               jwCourse.setPaperImgs(schoolExam.getCourses().get(i).getPaperImgs());
           }
           jwCourse.setId(schoolExam.getCourses().get(i).getId());
           jwCourse.setAlias(schoolExam.getCourses().get(i).getAlias());
           jwCourses.add(jwCourse);
       }
        return jwCourses;
    }
}
