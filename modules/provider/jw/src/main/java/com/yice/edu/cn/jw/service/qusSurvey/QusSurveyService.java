package com.yice.edu.cn.jw.service.qusSurvey;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.student.StudentService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class QusSurveyService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentService  studentService;
    @Autowired
    private QusSurveyOptionService qusSurveyOptionService;
    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public QusSurvey findQusSurveyById(String id) {
        return mot.findById(id,QusSurvey.class);
    }
    public void saveQusSurvey(QusSurvey qusSurvey) {
        qusSurvey.setCreateTime(DateUtil.now());
        qusSurvey.setStatus("1");
        qusSurvey.setQusSurveyQuestions(new ArrayList<>());
        qusSurvey.setId(sequenceId.nextId());
        mot.insert(qusSurvey);
    }
    public List<QusSurvey> findQusSurveyListByCondition(QusSurvey qusSurvey) {
        Criteria c =new Criteria();
                List<Criteria> c1 = new ArrayList<>();
        if(StrUtil.isNotEmpty(qusSurvey.getSearchStartTime())){
            c1.add(new Criteria("createTime").gte(qusSurvey.getSearchStartTime()));
        }
        if(StrUtil.isNotEmpty(qusSurvey.getSearchEndTime())){
            c1.add(new Criteria("createTime").lte(qusSurvey.getSearchEndTime()));
        }
        List<AggregationOperation> operations = new ArrayList<>();
        if(StrUtil.isNotEmpty(qusSurvey.getState())){
            if(qusSurvey.getState().equals("1")){
                c1.add(new Criteria("endTime").gte(DateUtil.formatDateTime(DateUtil.date())));
            }else if(qusSurvey.getState().equals("2")){
                c1.add(new Criteria("endTime").lte(DateUtil.formatDateTime(DateUtil.date())));
            }
        }
        c1.add(MongoKit.buildCriteria(qusSurvey, qusSurvey.getPager()));
        c.andOperator(c1.toArray(new Criteria[c1.size()]));
        operations.add(Aggregation.match(c));
        MongoKit.sortPageInclude(qusSurvey.getPager(),operations);

        List<QusSurvey> list= mot.aggregate(Aggregation.newAggregation(QusSurvey.class,operations),QusSurvey.class).getMappedResults();
        list.forEach(qusSurvey1 -> {
           Date endTime1= DateUtil.parse(qusSurvey1.getEndTime());
            Date nowDate = DateUtil.date();
           if(nowDate.getTime()>endTime1.getTime()){
                qusSurvey1.setShowSumState("2");
           }else {
               qusSurvey1.setShowSumState("1");
           }
        });
        return  list;

    }

    public long findQusSurveyCountByCondition(QusSurvey qusSurvey) {
        List<Criteria> c1 = new ArrayList<>();
        if(StrUtil.isNotEmpty(qusSurvey.getSearchStartTime())){
            c1.add(new Criteria("createTime").gte(qusSurvey.getSearchStartTime()));
        }
        if(StrUtil.isNotEmpty(qusSurvey.getSearchEndTime())){
            c1.add(new Criteria("createTime").lte(qusSurvey.getSearchEndTime()));
        }
        if(StrUtil.isNotEmpty(qusSurvey.getState())){
            if(qusSurvey.getState().equals("1")){
                c1.add(new Criteria("endTime").gte(DateUtil.formatDateTime(DateUtil.date())));
            }else if(qusSurvey.getState().equals("2")){
                c1.add(new Criteria("endTime").lte(DateUtil.formatDateTime(DateUtil.date())));
            }
        }
        Criteria c =new Criteria();
        c1.add(MongoKit.buildCriteria(qusSurvey, qusSurvey.getPager()));
        c.andOperator(c1.toArray(new Criteria[c1.size()]));
        Query query = query(c);
        return mot.count(query, QusSurvey.class);
    }

    public QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey) {
        Example<QusSurvey> example = Example.of(qusSurvey, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurvey.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurvey.class,operations),QusSurvey.class).getUniqueMappedResult();
    }


    public QusSurvey updateQusSurvey(QusSurvey qusSurvey) {
        ArrayList<QusSurveyQuestion> qusSurveyQuestionList=qusSurvey.getQusSurveyQuestions();
        if (qusSurveyQuestionList!=null&&qusSurveyQuestionList.size()>0){
            qusSurveyQuestionList.forEach(qusSurveyQuestion -> {
                qusSurveyQuestion.setCreateTime(DateUtil.now());
                qusSurveyQuestion.setId(sequenceId.nextId());
                qusSurveyQuestion.setSurveyId(qusSurvey.getId());
                ArrayList<Option> options=qusSurveyQuestion.getOptions();
                options.forEach(option -> {
                    option.setOptionId(sequenceId.nextId());
                    option.setOptionNum(0);
                });
                qusSurveyQuestion.setOptions(options);
            });

        }
        mot.updateFirst(query(where("id").is(qusSurvey.getId())), MongoKit.update(qusSurvey),QusSurvey.class);
        return  qusSurvey;
    }
    public void deleteQusSurvey(String id) {
        mot.remove(query(where("id").is(id)),QusSurvey.class);
    }
    public void deleteQusSurveyByCondition(QusSurvey qusSurvey) {
        Example<QusSurvey> example = Example.of(qusSurvey, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, QusSurvey.class);
    }
    public void batchSaveQusSurvey(List<QusSurvey> qusSurveys){
        qusSurveys.forEach(qusSurvey -> qusSurvey.setId(sequenceId.nextId()));
        mot.insertAll(qusSurveys);
    }

    public void ConfirmSend(QusSurveySendObject1 qusSurveySendObject1) {
        if(qusSurveySendObject1.getQusSurvey().getType().equals("4")){
            QusSurvey qusSurvey=qusSurveySendObject1.getQusSurvey();
            QusSurveySendObject surveySendObject=qusSurveySendObject1.getQusSurveySendObject();
            qusSurvey.setStatus("2");//设置状态为已发布

            deleteQusSurveySendObjectBySurveyId(surveySendObject.getSurveyId());
            saveQusSurveySendObject(surveySendObject);

            SendObjectQusSurvey sendObjectQusSurvey=new SendObjectQusSurvey();
            sendObjectQusSurvey.setSurveyId(qusSurvey.getId());
            sendObjectQusSurvey.setSurveyTitle(qusSurvey.getTitle());
            sendObjectQusSurvey.setIntroduction(qusSurvey.getIntroduction());
            sendObjectQusSurvey.setEndTime(qusSurvey.getEndTime());
            sendObjectQusSurvey.setBeEvaluatedTeacherList(surveySendObject.getBeEvaluatedTeacherList());
            sendObjectQusSurvey.setEvaluateTeacherList(surveySendObject.getEvaluateTeacherList());
            sendObjectQusSurvey.setCreateTime(DateUtil.now());
            sendObjectQusSurvey.setSchoolId(qusSurvey.getSchoolId());
            saveSendObjectQusSurvey(sendObjectQusSurvey);
            updateQusSurvey(qusSurvey);

            //教师互评，先生成数据用于当做评价任务的数据
            qusSurveySendObject1.getQusSurveySendObject().getBeEvaluatedTeacherList().forEach(b->{
                qusSurveySendObject1.getQusSurveySendObject().getEvaluateTeacherList().forEach(e->{
                    QusSurveySubmit qusSurveySubmit=new QusSurveySubmit();
                    qusSurveySubmit.setSurveyId(qusSurvey.getId());
                    qusSurveySubmit.setBeEvaluatedTeacherId(b.getId());
                    qusSurveySubmit.setBeEvaluatedTeacherName(b.getName());
                    qusSurveySubmit.setEvaluateTeacherId(e.getId());
                    qusSurveySubmit.setEvaluateTeacherName(e.getName());
                    qusSurveySubmit.setSubmitStatus("1");
                    qusSurveySubmitService.saveQusSurveySubmit1(qusSurveySubmit);
                });
            });



        }else {
            List<String> studentIdList = new ArrayList<>();
            QusSurvey qusSurvey = qusSurveySendObject1.getQusSurvey();
            QusSurveySendObject surveySendObject = qusSurveySendObject1.getQusSurveySendObject();
            qusSurvey.setStatus("2");//设置状态为已发布

            deleteQusSurveySendObjectBySurveyId(surveySendObject.getSurveyId());
            saveQusSurveySendObject(surveySendObject);

            SendObjectQusSurvey sendObjectQusSurvey = new SendObjectQusSurvey();
            sendObjectQusSurvey.setSurveyId(qusSurvey.getId());
            sendObjectQusSurvey.setSurveyTitle(qusSurvey.getTitle());
            sendObjectQusSurvey.setIntroduction(qusSurvey.getIntroduction());
            sendObjectQusSurvey.setEndTime(qusSurvey.getEndTime());
            sendObjectQusSurvey.setTeacherList(surveySendObject.getTeacherList());
            sendObjectQusSurvey.setCreateTime(DateUtil.now());
            sendObjectQusSurvey.setPushState("1");
            sendObjectQusSurvey.setSchoolId(qusSurvey.getSchoolId());
            saveSendObjectQusSurvey(sendObjectQusSurvey);

            surveySendObject.getTeacherList().forEach(qusSurveyTeacher -> {
                qusSurveyTeacher.getAlreadyQusSurveyClassList().forEach(qusSurveyClass -> {
                    QusSurveyTeacherSendClass teacherSendClass = new QusSurveyTeacherSendClass();
                    teacherSendClass.setSurveyId(qusSurvey.getId());
                    teacherSendClass.setImg(qusSurveyTeacher.getImgUrl());
                    teacherSendClass.setTeacherId(qusSurveyTeacher.getTeacherId());
                    teacherSendClass.setTeacherName(qusSurveyTeacher.getName());
                    teacherSendClass.setClassId(qusSurveyClass.getClassesId());
                    teacherSendClass.setClassName(qusSurveyClass.getClassesName());
                    teacherSendClass.setGradeName(qusSurveyClass.getGradeName());
                    teacherSendClass.setSubmitNum(0);
                    teacherSendClass.setAvg(0.0);
                    teacherSendClass.setLowScore(0);
                    teacherSendClass.setHighScore(0);
                    teacherSendClass.setSchoolId(qusSurvey.getSchoolId());
                    Student student = new Student();
                    student.setClassesId(qusSurveyClass.getClassesId());
                    List<Student> studentList = studentService.findStudentListByCondition(student);
                    teacherSendClass.setClassPeopleNum(studentList.size());
                    saveQusSurveyTeacherSendClasses(teacherSendClass);
                    studentList.forEach(stu -> {
                        studentIdList.add(stu.getId());
                    });
                });
            });
            qusSurvey.setQusTotalNum(studentIdList.size());
            updateQusSurvey(qusSurvey);
            //对学生id进行去重
            List<String> distinctList = studentIdList.stream().distinct().collect(Collectors.toList());
            //发起推送
            QusSurveyPush(distinctList, qusSurvey.getTitle());
        }
    }


    public void saveSendObjectQusSurvey(SendObjectQusSurvey sendObjectQusSurvey) {
        sendObjectQusSurvey.setId(sequenceId.nextId());
        sendObjectQusSurvey.setCreateTime(DateUtil.now());
        mot.insert(sendObjectQusSurvey);
    }
    public void updateSaveSendObjectQusSurvey(SendObjectQusSurvey sendObjectQusSurvey) {
        mot.updateFirst(query(where("id").is(sendObjectQusSurvey.getId())), MongoKit.update(sendObjectQusSurvey),SendObjectQusSurvey.class);
    }


//    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
//        Example<SendObjectQusSurvey> example = Example.of(sendObjectQusSurvey, UntypedExampleMatcher.matchingAll());
//        List<AggregationOperation> operations = new ArrayList<>();
//        operations.add(Aggregation.match(new Criteria().alike(example)));
//        MongoKit.sortPageInclude(sendObjectQusSurvey.getPager(),operations);
//        return mot.aggregate(Aggregation.newAggregation(SendObjectQusSurvey.class,operations),SendObjectQusSurvey.class).getMappedResults();
//
//    }

    /**
     * 查询评价任务
     * @param sendObjectQusSurvey
     * @return
     */
    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
        String teacherId=sendObjectQusSurvey.getTeacherId();
        sendObjectQusSurvey.setTeacherId(null);
        Document doc=new Document();
        doc.append("evaluateTeacherList._id",teacherId);
        if(StrUtil.isNotEmpty(sendObjectQusSurvey.getEndState())){
            if (sendObjectQusSurvey.getEndState().equals("1"))
                doc.append("endTime",new Document("$gte",DateUtil.formatDateTime(DateUtil.date())));
            if (sendObjectQusSurvey.getEndState().equals("2"))
                doc.append("endTime",new Document("$lte",DateUtil.formatDateTime(DateUtil.date())));
            sendObjectQusSurvey.setEndState(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(sendObjectQusSurvey));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SendObjectQusSurvey.class)).find(doc);
        Pager pager = sendObjectQusSurvey.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SendObjectQusSurvey> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SendObjectQusSurvey.class, document)));
        list.forEach(s-> {
            Date endTime1= DateUtil.parse(s.getEndTime());
            Date nowDate = DateUtil.date();
            if(endTime1.getTime()> nowDate.getTime()){
                s.setEndState("1");
            }else {
                s.setEndState("2");
            }


        });
        return list;
    }

    /**
     * 给推送使用，根据时间段返回结果集
     * @param sendObjectQusSurvey
     * @return
     */
    public List<SendObjectQusSurvey> findSendObjectQusSurveyListToPushByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        Date date = DateUtil.parse(today, "yyyy-MM-dd");
        Date beginOfDay = DateUtil.beginOfDay(date);
        DateTime yestedayDate= DateUtil.offsetHour(beginOfDay, -6);//获取昨天18：00

        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(sendObjectQusSurvey, sendObjectQusSurvey.getPager());
        criteria.andOperator(new Criteria("endTime").gte(yestedayDate.toString()),new Criteria("endTime").lte(DateUtil.now()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(sendObjectQusSurvey.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(SendObjectQusSurvey.class,operations),SendObjectQusSurvey.class).getMappedResults();

    }


    public void saveQusSurveySendObject(QusSurveySendObject qusSurveySendObject) {
        qusSurveySendObject.setId(sequenceId.nextId());
        mot.insert(qusSurveySendObject);
    }

    public void deleteQusSurveySendObjectBySurveyId(String surveyId) {
        mot.remove(query(where("surveyId").is(surveyId)),QusSurveySendObject.class);
    }

    public void saveQusSurveyTeacherSendClasses(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        qusSurveyTeacherSendClasses.setId(sequenceId.nextId());
        qusSurveyTeacherSendClasses.setCreateTime(DateUtil.now());
        mot.insert(qusSurveyTeacherSendClasses);
    }

    public List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(qusSurveyTeacherSendClasses, qusSurveyTeacherSendClasses.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(qusSurveyTeacherSendClasses.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveyTeacherSendClass.class,operations),QusSurveyTeacherSendClass.class).getMappedResults();

    }
    public long findQusSurveyTeacherSendClassesCountByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        Criteria criteria = MongoKit.buildCriteria(qusSurveyTeacherSendClasses, qusSurveyTeacherSendClasses.getPager());
        return mot.count(query(criteria), QusSurveyTeacherSendClass.class);
    }

    public QusSurveyTeacherSendClass findOneQusSurveyTeacherSendClassesByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClass) {
        Example<QusSurveyTeacherSendClass> example = Example.of(qusSurveyTeacherSendClass, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurveyTeacherSendClass.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurveyTeacherSendClass.class,operations),QusSurveyTeacherSendClass.class).getUniqueMappedResult();
    }

    public void updateQusSurveyTeacherSendClasses(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        mot.updateFirst(query(where("id").is(qusSurveyTeacherSendClasses.getId())), MongoKit.update(qusSurveyTeacherSendClasses),QusSurveyTeacherSendClass.class);
    }


    public List<QusSurveyQuestion> getQuestionTypeCountList(QusSurveySubmit qusSurveySubmit) {
        QusSurvey q=new QusSurvey();
        q.setId(qusSurveySubmit.getSurveyId());
        List<QusSurveyQuestion> questionList= findOneQusSurveyByCondition(q).getQusSurveyQuestions();
        QusSurveyOption qusSurveyOption=new QusSurveyOption();
        qusSurveyOption.setTeacherId(qusSurveySubmit.getTeacherId());
        qusSurveyOption.setSurveyId(qusSurveySubmit.getSurveyId());
        List<QusSurveyOption> qusSurveyOptionList=qusSurveyOptionService.getOptionCountList(qusSurveyOption);
        int count= (int) qusSurveySubmitService.findQusSurveySubmitCountByCondition(qusSurveySubmit);

        questionList.forEach(
                qusSurveyQuestion -> {
                    qusSurveyQuestion.setSubmitPeoNum(count);
                    qusSurveyQuestion.getOptions().forEach(
                            option -> {
                                   qusSurveyOptionList.forEach(
                                           qusSurveyOption1 -> {
                                               if(qusSurveyOption1.getId().equals(option.getOptionId())){
                                                   option.setOptionNum(qusSurveyOption1.getOptionCount());

                                               }
                                           }
                                   );
                            }
                    );

                }
        );
         return questionList;
    }

    /**
     * 根据教师id,取得老师问卷各个班级一起的平均分、最高分、最低分、总提交人数
     * @param  "问卷id,老师id"
     * @return
     */
    public QusSurveyTeacherSendClass getTeacherSendClassTopSum(QusSurveyTeacherSendClass qusSurveyTeacherSendClass){
        Example<QusSurveyTeacherSendClass> example = Example.of(qusSurveyTeacherSendClass, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria c= new Criteria().alike(example);
        c.andOperator(Criteria.where("avg").ne(0));
        operations.add(Aggregation.match(c));
        operations.add(Aggregation.group("teacherId").avg("avg").as("topAvg").min("lowScore").as("topLowScore")
                .max("highScore").as("topHighScore").sum("submitNum").as("topTotalSubmit"));
        return mot.aggregate(Aggregation.newAggregation(QusSurveyTeacherSendClass.class,operations),QusSurveyTeacherSendClass.class).getUniqueMappedResult();
    }


    public void QusSurveyPush(List<String> stuIdList,String title){
        if(stuIdList.size()>0){
            //处理推送
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(stuIdList.toArray(new String[stuIdList.size()]),"学生评教",title+"已出，点击查看详情。",Extras.BMP_TEACHER_SURVEY);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }

    }

    public long findSendObjectQusSurveyCountByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
        String teacherId=sendObjectQusSurvey.getTeacherId();
        sendObjectQusSurvey.setTeacherId(null);
        Document doc=new Document();
        doc.append("evaluateTeacherList._id",teacherId);
        if(StrUtil.isNotEmpty(sendObjectQusSurvey.getEndState())){
            if (sendObjectQusSurvey.getEndState().equals("1"))
                doc.append("endTime",new Document("$gte",DateUtil.formatDateTime(DateUtil.date())));
            if (sendObjectQusSurvey.getEndState().equals("2"))
                doc.append("endTime",new Document("$lte",DateUtil.formatDateTime(DateUtil.date())));
            sendObjectQusSurvey.setEndState(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(sendObjectQusSurvey));
        return mot.getCollection(mot.getCollectionName(SendObjectQusSurvey.class)).countDocuments(doc);
    }

    /**
     * 教师互评统计接口
     */
    public List<QusSurveyQuestion> getTeacherQuestionTypeCountList(QusSurveySubmit qusSurveySubmit) {
        QusSurvey q=new QusSurvey();
        q.setId(qusSurveySubmit.getSurveyId());
        List<QusSurveyQuestion> questionList= findOneQusSurveyByCondition(q).getQusSurveyQuestions();
        QusSurveyOption qusSurveyOption=new QusSurveyOption();
        qusSurveyOption.setBeEvaluatedTeacherId(qusSurveySubmit.getBeEvaluatedTeacherId());
        qusSurveyOption.setSurveyId(qusSurveySubmit.getSurveyId());
        List<QusSurveyOption> qusSurveyOptionList=qusSurveyOptionService.getOptionCountList(qusSurveyOption);
        int count= (int) qusSurveySubmitService.findQusSurveySubmitCountByCondition(qusSurveySubmit);

        questionList.forEach(
                qusSurveyQuestion -> {
                    qusSurveyQuestion.setSubmitPeoNum(count);
                    qusSurveyQuestion.getOptions().forEach(
                            option -> {
                                qusSurveyOptionList.forEach(
                                        qusSurveyOption1 -> {
                                            if(qusSurveyOption1.getId().equals(option.getOptionId())){
                                                option.setOptionNum(qusSurveyOption1.getOptionCount());

                                            }
                                        }
                                );
                            }
                    );

                }
        );
        return questionList;
    }


    public QusSurveySubmit getTeacherEvaluateTopSum(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria c= new Criteria().alike(example);
        operations.add(Aggregation.match(c));
        operations.add(Aggregation.group("surveyId").avg("score").as("topAvg").min("score").as("topLowScore")
                .max("score").as("topHighScore"));
        return mot.aggregate(Aggregation.newAggregation(QusSurveySubmit.class,operations),QusSurveySubmit.class).getUniqueMappedResult();
    }

    public SendObjectQusSurvey findOneSendObjectQusSurveyByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
        Example<SendObjectQusSurvey> example = Example.of(sendObjectQusSurvey, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = sendObjectQusSurvey.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(SendObjectQusSurvey.class,operations),SendObjectQusSurvey.class).getUniqueMappedResult();
    }

    public QusSurvey lookAlreadyQusSurvey(QusSurveySubmit qusSurveySubmit) {

        QusSurvey q=mot.findById(qusSurveySubmit.getSurveyId(),QusSurvey.class);

        QusSurveyOption option=new QusSurveyOption();
        option.setSurveyId(qusSurveySubmit.getSurveyId());
        option.setBeEvaluatedTeacherId(qusSurveySubmit.getBeEvaluatedTeacherId());
        option.setEvaluateTeacherId(qusSurveySubmit.getEvaluateTeacherId());

        List<QusSurveyOption> list=qusSurveyOptionService.findQusSurveyOptionListByCondition(option);


        QusSurveySubmit qs=  qusSurveySubmitService.findOneQusSurveySubmitByCondition(qusSurveySubmit);
        q.setQuestionAnswer(qs.getQuestionAnswer());
        if(list.size()>0){
            Map<String,List<QusSurveyOption>> m=list.stream().collect(Collectors.groupingBy(QusSurveyOption::getQuestionId));
            q.getQusSurveyQuestions().forEach(q1->{
                List<QusSurveyOption> options=m.get(q1.getId());
                if(options!=null){
                    List<String> idList=options.stream().map(QusSurveyOption::getOptionId).collect(Collectors.toList());
                    q1.setAlreadyOptions(idList);
                }
            });
        }

        return q;
    }


}
