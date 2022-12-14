package com.yice.edu.cn.jy.service.homework;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsCount;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jy.feignClient.schoolYear.SchoolYearFeign;
import com.yice.edu.cn.jy.feignClient.student.StudentFeign;
import com.yice.edu.cn.jy.source21.util.Log;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class HomeworkService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private SchoolYearFeign schoolYearFeign;
    @Autowired
    private HomeworkStudentService homeworkStudentService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public Homework findHomeworkById(String id) {
        return mot.findById(id,Homework.class);
    }
    public void saveHomework(Homework homework) {
        homework.setCreateTime(DateUtil.now());
        String id = sequenceId.nextId();
        homework.setId(id);
        mot.insert(homework);
    }
    public List<Homework> findHomeworkListByCondition(Homework homework) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Homework.class)).find(MongoKit.buildMatchDocument(homework));
        Pager pager = homework.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<Homework> homeworkList = new ArrayList<>();
        query.forEach((Block<Document>) document -> homeworkList.add(mot.getConverter().read(Homework.class, document)));

        if(homeworkList!=null&&homeworkList.size()>0) {
            //?????????????????????????????????className????????? ??????????????????
            homeworkList.forEach(h ->
                 h.setClassesName(Optional.ofNullable(h.getClassesName()).map(t->t.replace("???", "")).orElse(null))
            );
        }
        return homeworkList;
    }
    public long findHomeworkCountByCondition(Homework homework) {
        return mot.getCollection(mot.getCollectionName(Homework.class)).countDocuments(MongoKit.buildMatchDocument(homework));
    }
    public Homework findOneHomeworkByCondition(Homework homework) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Homework.class)).find(MongoKit.buildMatchDocument(homework));
        MongoKit.appendProjection(query,homework.getPager());
        return mot.getConverter().read(Homework.class,query.first());
    }

    public void updateHomework(Homework homework) {
        //??????????????????????????? ????????????
        Homework old = this.findHomeworkById(homework.getId());
        if(old.getPublishStatus()==Constant.HOMEWORK.PUBLISH_NOT&&homework.getPublishStatus()==Constant.HOMEWORK.PUBLISH_ON){
            homework.setPublishTime(DateUtil.now());
        }
        mot.updateFirst(query(where("id").is(homework.getId())), MongoKit.update(homework),Homework.class);
    }
    public void deleteHomework(String id) {
        mot.remove(query(where("id").is(id)),Homework.class);
    }
    public void deleteHomeworkByCondition(Homework homework) {
        mot.getCollection(mot.getCollectionName(Homework.class)).deleteMany(MongoKit.buildMatchDocument(homework));
    }
    public void batchSaveHomework(List<Homework> homeworks){
        homeworks.forEach(homework -> homework.setId(sequenceId.nextId()));
        mot.insertAll(homeworks);
    }
    public void batchSaveHomework1(List<Homework> homeworks){
        mot.insertAll(homeworks);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<Homework> findHomeworkListByConditionNew(Homework homework) {
        //????????????????????????????????????
        List<Criteria> c = new ArrayList<>();
        c.add(new Criteria().where("teacherId").is(homework.getTeacherId()));
        if(StringUtils.isNotEmpty(homework.getPublishTime())){
            c.add(new Criteria().where("publishTime").regex(homework.getPublishTime()));
        }
        if(StringUtils.isNotEmpty(homework.getEndTime())){
            c.add(new Criteria().where("endTime").regex(homework.getEndTime()));
        }
        if(StringUtils.isNotEmpty(homework.getClassesId())){
            c.add(new Criteria().where("classesId").is(homework.getClassesId()));
        }
        if(homework.getPublishStatus()!=null){
            c.add(new Criteria().where("publishStatus").is(homework.getPublishStatus()));
        }
        if(homework.getType()!=null){
            c.add(new Criteria().where("type").is(homework.getType()));
        }
        if(homework.getGradeName()!=null){
            c.add(new Criteria().where("gradeName").is(homework.getGradeName()));
        }
        if(homework.getClassesName()!=null){
            c.add(new Criteria().where("classesName").is(homework.getClassesName()));
        }
        if(homework.getSchoolYearId()!=null){
            c.add(new Criteria().where("schoolYearId").is(homework.getSchoolYearId()));
        }

        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        if(homework.getPager()!=null&&homework.getPager().getPaging()){
            query.with(homework.getPager());
        }
        List<Homework> homeworkList = mot.find(query, Homework.class);
        /*if(homeworkList!=null&&homeworkList.size()>0) {
            //?????????????????????????????????className????????? ??????????????????
            homeworkList.forEach(h ->
                    h.setClassesName(h.getClassesName().replace("???", ""))
            );
        }*/
        return homeworkList;
    }
    public long findHomeworkCountByConditionNew(Homework homework) {
        //????????????????????????????????????
        List<Criteria> c = new ArrayList<>();
        c.add(new Criteria().where("teacherId").is(homework.getTeacherId()));
        if(StringUtils.isNotEmpty(homework.getPublishTime())){
            c.add(new Criteria().where("publishTime").regex(homework.getPublishTime()));
        }
        if(StringUtils.isNotEmpty(homework.getEndTime())){
            c.add(new Criteria().where("endTime").regex(homework.getEndTime()));
        }
        if(StringUtils.isNotEmpty(homework.getClassesId())){
            c.add(new Criteria().where("classesId").is(homework.getClassesId()));
        }
        if(homework.getPublishStatus()!=null){
            c.add(new Criteria().where("publishStatus").is(homework.getPublishStatus()));
        }
        if(homework.getType()!=null){
            c.add(new Criteria().where("type").is(homework.getType()));
        }
        if(homework.getGradeName()!=null){
            c.add(new Criteria().where("gradeName").is(homework.getGradeName()));
        }
        if(homework.getClassesName()!=null){
            c.add(new Criteria().where("classesName").is(homework.getClassesName()));
        }
        if(homework.getSchoolYearId()!=null){
            c.add(new Criteria().where("schoolYearId").is(homework.getSchoolYearId()));
        }

        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        if(homework.getPager()!=null&&homework.getPager().getPaging()){
            query.with(homework.getPager());
        }
        return mot.count(query, Homework.class);
    }

    /**
     * ????????????????????????????????????
     * @param id
     * @param key 'punctualNum' or 'overdueNum'
     */
    public void inc4HomeworkSubmit(String id,String key){
        mot.updateFirst(query(where("_id").is(id)),new Update().inc(key,1),Homework.class);
    }
    public void inc4HomeworkTopicsCount(String id,String[] ids,String key){
        Document query = new Document("_id",id);
        Document set = new Document("$inc",new Document("topicsCounts.$[tc]."+key,1));
        mot.getCollection("homework").updateMany(query,set,new UpdateOptions().upsert(true).arrayFilters(Arrays.asList(new Document("tc._id",new Document("$in",Arrays.asList(ids))))));
    }

    /**
     * ??????????????????
     * @param homework
     */
    public void publishHomework(Homework homework) {
        final String nt = DateUtil.now();
        //???????????? ??????????????????
        this.HomeworkAddTopicsCount(homework);
        //????????????????????????
        if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_ON)
            homework.setPublishTime(nt);
        //???????????????????????????????????? ??????????????????????????????
        List<Homework> homeworkList = homework.getClassesList().stream().map(c->{
            Homework hw = new Homework();
            BeanUtils.copyProperties(homework,hw);
            String id = sequenceId.nextId();
            hw.setId(id);
            hw.setClassesId(c.getId());
            hw.setClassesName(c.getNumber());
            hw.setEnrollYear(c.getEnrollYear());
            //???????????????
            hw.setCreateTime(nt);
            hw.setDel(1);
            hw.setOverdueNum(0);
            hw.setPunctualNum(0);
            Student student = new Student();
            student.setClassesId(c.getId());
            hw.setShouldNum(studentFeign.findStudentCountByCondition(student));
            return hw;
        }).collect(Collectors.toList());
        if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_ON){
            //????????????????????? ??????????????????
            this.publishStudentHomework(homeworkList);
        }
        this.batchSaveHomework1(homeworkList);
    }
//    public void updateHomeworkStatus(Homework homework){
//        if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_NOT){
//            this.updateHomework(homework);
//        }
//        if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_ON){
//            final String nt = DateUtil.now();
//            this.deleteHomework(homework.getId());
//            homework.setPublishTime(nt);
//            //???????????????????????????????????? ??????????????????????????????
//            List<Homework> homeworkList = homework.getClassesList().stream().map(c->{
//                Homework hw = new Homework();
//                BeanUtils.copyProperties(homework,hw);
//                String id = sequenceId.nextId();
//                hw.setId(id);
//                hw.setSqId(id);
//                hw.setClassesId(c.getId());
//                hw.setClassesName(c.getNumber());
//                hw.setEnrollYear(c.getEnrollYear());
//                //???????????????
//                hw.setCreateTime(nt);
//                hw.setDel(1);
//                hw.setPunctualNum(0);
//                hw.setOverdueNum(0);
//                Student student = new Student();
//                student.setClassesId(c.getId());
//                hw.setShouldNum(studentFeign.findStudentCountByCondition(student));
//                return hw;
//            }).collect(Collectors.toList());
//            if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_ON){
//                //????????????????????? ??????????????????
//                this.publishStudentHomework(homeworkList);
//            }
//            this.batchSaveHomework(homeworkList);
//        }
//    }


    private void HomeworkAddTopicsCount(Homework homework){
        //??????????????? ????????????????????????
        if(homework.getType()==Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE){
            //?????? ????????????
            homework.setTopicsCounts(Arrays.stream(homework.getTopicArr()).map(t->{
                TopicsCount tc = new TopicsCount();
                tc.setId(t.getId());
                tc.setCorrectNum(0);
                tc.setWrongNum(0);
                return tc;
            }).collect(Collectors.toList()));
        }
    }
    private void publishStudentHomework(List<Homework> homeworkList){
        final String nt = DateUtil.now();
        List<Student> alls = new ArrayList<>();
        List<HomeworkStudent> homeworkStudents = new ArrayList<>();

        //????????????????????????????????????????????????
        homeworkList.stream().forEach(homework -> {
            //??????????????????
            Student student = new Student();
            student.setClassesId(homework.getClassesId());
            student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
            List<Student> students = studentFeign.findStudentListByCondition(student);
            //?????????????????????????????????
            if(students!=null && students.size()>0){
                alls.addAll(students);
                homeworkStudents.addAll(students.stream().map(s->{
                    //???????????????????????????
                    HomeworkStudent homeworkStudent = new HomeworkStudent();
                    String id = sequenceId.nextId();
                    homeworkStudent.setId(id);
                    homeworkStudent.setHomeworkObj(homework);
                    homeworkStudent.setStudentId(s.getId());
                    homeworkStudent.setStudentName(s.getName());
                    homeworkStudent.setStudent(s);
                    homeworkStudent.setRemarkStatus(Constant.HOMEWORK.REMARK_NOT);
                    homeworkStudent.setStatus(Constant.HOMEWORK.SUBMIT_NOT);
                    homeworkStudent.setCompleteTime(nt);
                    //????????????????????????
                    homeworkStudent.setGradeId(homework.getGradeId());
                    homeworkStudent.setGradeName(homework.getGradeName());
                    homeworkStudent.setSubjectId(homework.getSubjectId());
                    homeworkStudent.setSubjectName(homework.getSubjectName());
                    homeworkStudent.setClassesId(homework.getClassesId());
                    homeworkStudent.setEnrollYear(homework.getEnrollYear());
                    homeworkStudent.setClassesName(homework.getClassesName());
                    homeworkStudent.setCreateTime(nt);
                    homeworkStudent.setFromTo(homework.getFromTo());
                    homeworkStudent.setTerm(homework.getTerm());
                    homeworkStudent.setSchoolYearId(homework.getSchoolYearId());
                    return homeworkStudent;
                }).collect(Collectors.toList()));
            }
        });
        homeworkStudentService.batchSaveHomeworkStudent(homeworkStudents);
        //?????????????????????
        if(alls.size()>0){
            //????????????
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePush(alls.stream().map(Student::getId).toArray(String[]::new),"????????????","???????????????",Extras.SYSTEM_BROADCAST_HOMEWORK);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
    }

    public void updateHomeworkNew(Homework homework) {
        this.updateHomework(homework);
        homework = this.findHomeworkById(homework.getId());
        if(homework.getPublishStatus() == Constant.HOMEWORK.PUBLISH_ON){
            //????????????????????? ??????????????????
            List<Homework> homeworkList = new ArrayList<>();
            homeworkList.add(homework);
            this.publishStudentHomework(homeworkList);
            homework.setPublishTime(DateUtil.now());
        }
        //???????????? ??????????????????
        this.HomeworkAddTopicsCount(homework);
    }

    public void deleteHomeworkNew(String id) {
        //????????????
        Homework homework = this.findHomeworkById(id);
        //???????????????????????????
        homeworkStudentService.deleteHomeworkStudentByHomeworkId(homework.getId());
        //??????????????????

        this.deleteHomework(id);

    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param homework
     * @return
     */
    public List<Homework> findHomeworks4Index(Homework homework) {
        //????????????????????????
        Date taday = DateUtil.date();
        Date newDate = DateUtil.offsetDay(taday, -1);
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(where("teacherId").is(homework.getTeacherId()));
        criterias.add(where("publishStatus").is(Constant.HOMEWORK.PUBLISH_ON));
        criterias.add(where("endTime").gte(DateUtil.formatDate(newDate)+" 00:00:00"));
        criterias.add(where("endTime").lte(DateUtil.formatDate(taday)+" 23:59:59"));
        Query query = query(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
        query.with(new Sort(Sort.Direction.DESC,"endTime"));
        return mot.find(query,Homework.class);
    }

    /**
     * ????????????-??????????????????
     * interval ????????????
     */
    public void scheduleEndTime(int interval){
        //???????????????????????????????????????
        DateTime nowTime = DateUtil.date();
        String nowTimeStr = DateUtil.formatDateTime(nowTime);
        String lastTimeStr = DateUtil.formatDateTime(DateUtil.offsetMinute(nowTime,-interval));
        Homework homework = new Homework();
        homework.setEndTime(lastTimeStr);
        List<Homework> homeworkList = mot.find(query(new Criteria().andOperator(new Criteria("endTime").gte(lastTimeStr),new Criteria("endTime").lt(nowTimeStr))),Homework.class);
        if(homeworkList != null && homeworkList.size() > 0){
            //????????????
            homeworkList.stream().forEach(h->{
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(new String[]{h.getTeacherId()},"????????????",h.getClassesName()+"????????????"+Optional.ofNullable(h.getHomeworkContent()).orElse("")+"??????????????????????????????????????????????????????",Extras.SYSTEM_BROADCAST_HOMEWORK);
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            });
        }
    }

    /**
     * db.topicsRecord.aggregate([
     *            {$match:{"classesId" : "1807003276775612416","subjectId":"1818889684666638336"}},
     *            {$project:{knowledge:"$topicsObj.knowledges",correct:1,studentId:1,subjectId:1,student:1}},
     *            {$unwind:"$knowledge"},
     *            {$group:{
     *                      _id:{knowledge:"$knowledge", studentId:"$studentId",subjectId:"$subjectId",
     *                      seatNumber:"$student.seatNumber"},
     *                     trueCount:{$sum:{$cond:[{$eq:["$correct",1]},1,0]}},
     *                      wrongCount:{$sum:{$cond:[{$ne:["$correct",1]},1,0]}},
     *
     *             }},
     *            {$project:{_id:0,knowledgeId:"$_id.knowledge._id",knowledgeName:"$_id.knowledge.name",trueCount:1,
     *                       wrongCount:1,studentId:"$_id.studentId",
     *                      subjectId:"$_id.subjectId",seatNumber:"$_id.seatNumber"
     *                      ,totalCount:{$add:["$trueCount","$wrongCount"]}
     *                      ,divideCount:{$divide:["$wrongCount",{$add:["$trueCount","$wrongCount"]}]}
     *            }},
     *            {$sort : { divideCount : -1} },
     *            {$group:{
     *                      _id:{studentId:"$studentId",subjectId:"$subjectId",
     *                      seatNumber:"$seatNumber"},
     *                      wknowledgeC:{$sum:{$cond:[{$gte:["$divideCount",0.3]},1,0]}},
     *           }},
     *
     *            {$lookup:{
     *                from: "wrongTopics",
     *                      let: { studentId: "$_id.studentId", subjectId: "$_id.subjectId" },
     *                     pipeline: [
     *                           { $match:
     *                             { $expr:
     *                                { $and:
     *                                 [
     *                                    { $eq: [ "$studentId",  "$$studentId" ] },{ $eq: [ "$subjectId",  "$$subjectId" ] }
     *                                   ]
     *                                }
     *                            }
     *                           },
     *                           {$group:{_id:"$studentId",count:{$sum:1}}},
     *                           { $project: { _id: 0,count:1} },
     *
     *            ],
     *      as: "stockdata"}
     *      },
     *      {$unwind:"$stockdata"},
     *                  {$lookup:{
     *                 from: "topicsRecord",
     *                       let: { studentId: "$_id.studentId", subjectId: "$_id.subjectId" },
     *                      pipeline: [
     *                           { $match:
     *                             { $expr:
     *                                { $and:
     *                                  [
     *                                     { $eq: [ "$studentId",  "$$studentId" ] },{ $eq: [ "$subjectId",  "$$subjectId" ] },
     *                                   ]
     *                                }
     *                             }
     *                           },
     *                         { $sort: { answerTime: 1} },
     *                           {$group:{_id:"$studentId",count:{$sum:1},studentName: { $first: "$studentName" }}},
     *                           { $project: { _id: 0,count:1,studentName:1} },
     *
     *                        ],
     *               as: "stockdata1"
     *                     }
     *       },
     *            {$unwind:"$stockdata1"},
     *      {$project:{studentId:"$_id.studentId",subjectId:"$_id.subjectId",seatNumber:"$_id.seatNumber",
     * 			wknowledgeC:1,wrongTopicCount:"$stockdata.count",_id:0,studentName:"$stockdata1.studentName"}}
     *     ])
     * @param homework
     * @return
     */
    public List<Map<String,Object>> findOneError(Homework homework) {
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        matchOpts.append("classesId",homework.getClassesId());
        matchOpts.append("subjectId",homework.getSubjectId());
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }
        if(homework.getStudentName()!=null && !"".equals(homework.getStudentName())){//????????????????????????
            matchOpts.append("studentName",homework.getStudentName());
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$project",new Document("knowledge","$topicsObj.knowledges").append("correct",1)
         .append("studentId",1).append("subjectId",1).append("student",1));
        Document match3 = new Document("$unwind","$knowledge");
        Document match4 = new Document("$group",new Document("_id",new Document("knowledge","$knowledge").append("studentId","$studentId").append("subjectId","$subjectId")
                .append("seatNumber","$student.seatNumber")).append("trueCount",new Document("$sum",
                new Document("$cond",Arrays.asList(new Document("$eq",Arrays.asList("$correct",1)),1,0)))).
                append("wrongCount",new Document("$sum",new Document("$cond",Arrays.asList(new Document("$ne",Arrays.asList("$correct",1)),1,0)))));
        Document match5 = new Document("$project",new Document("_id",0).append("knowledgeId","$_id._id").append("knowledgeName","$_id.name")
                .append("trueCount",1).append("wrongCount",1).append("studentId","$_id.studentId").append("subjectId","$_id.subjectId")
        .append("seatNumber","$_id.seatNumber").append("totalCount",new Document("$add",Arrays.asList("$trueCount","$wrongCount")))
        .append("divideCount",new Document("$divide",Arrays.asList("$wrongCount",new Document("$add",Arrays.asList("$trueCount","$wrongCount"))))));
        Document match6 = new Document("$sort",new Document("divideCount",-1));
        Document match61 = new Document("$group",new Document("_id",new Document("studentId","$studentId").append("subjectId","$subjectId")
        .append("seatNumber","$seatNumber")).append("wknowledgeC",
                new Document("$sum",new Document("$cond",Arrays.asList(new Document("$gte",Arrays.asList("$divideCount",0.3)),1,0)))));

        Document match7 = new Document();
        if(studyYear!=null){
            match7 = new Document("$lookup",new Document("from","wrongTopics").append("let",new Document("studentId","$_id.studentId").
                    append("subjectId","$_id.subjectId")).append("pipeline",Arrays.asList(
                    new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(
                            new Document("$eq",Arrays.asList("$studentId",  "$$studentId" )),
                            new Document("$eq",Arrays.asList("$subjectId",  "$$subjectId")),
                            new Document("$eq",Arrays.asList("$fromTo",studyYear)))))),
                    new Document("$group",new Document("_id","$studentId").append("count",new Document("$sum",1))),
                    new Document("$project",new Document("_id",0).append("count",1))
            )).append("as","stockdata"));

        }else{
            match7 = new Document("$lookup",new Document("from","wrongTopics").
                    append("let",new Document("studentId","$studentId").append("subjectId","$subjectId")).
                    append("pipeline",Arrays.asList(
                            new Document(new Document("$match",new Document("$expr",
                                    new Document("$and",Arrays.asList(
                                            new Document("$eq",Arrays.asList("$studentId",  "$$studentId" )),
                                            new Document("$eq",Arrays.asList("$subjectId",  "$$subjectId"))))))),
                            new Document("$group",new Document("_id","$studentId").append("count",new Document("$sum",1))),
                            new Document("$project",new Document("_id",0).append("count",1)))).append("as","stockdata"));
        }

        Document match8 = new Document("$unwind","$stockdata");

        Document match9 = new Document();
        match9 = new Document("$lookup",new Document("from","topicsRecord").append("let",new Document("studentId","$_id.studentId").
                    append("subjectId","$_id.subjectId")).append("pipeline",Arrays.asList(
                    new Document("$match",new Document("$expr",new Document("$and",Arrays.asList(
                            new Document("$eq",Arrays.asList("$studentId",  "$$studentId" )),
                            new Document("$eq",Arrays.asList("$subjectId",  "$$subjectId")))))),
                    new Document("$sort",new Document("answerTime",1)),
                    new Document("$group",new Document("_id","$studentId").append("count",new Document("$sum",1))
                    .append("studentName", new Document("$first","$studentName"))),
                    new Document("$project",new Document("_id",0).append("count",1).append("studentName", 1))
            )).append("as","stockdata1"));


        Document match10 = new Document("$unwind","$stockdata1");

        Document match11 = new Document("$project",new Document("studentId","$_id.studentId").
                append("subjectId","$_id.subjectId").
                append("seatNumber","$_id.seatNumber").append("wknowledgeC",1)
                .append("wrongTopicCount","$stockdata.count").append("_id",0)
                .append("studentName", "$stockdata1.studentName"));

        operas.addAll(Arrays.asList(match1,match2,match3,match4,match5,match6,match61,match7,match8,match9,match10,match11));

        //????????????
        if(homework.getPager()!=null && homework.getPager().getPaging()){
            operas.add(new Document("$skip",homework.getPager().getBeginRow()));
            operas.add(new Document("$limit",homework.getPager().getPageSize()));
        }

        Log.info(new Gson().toJson(operas));
        MongoCollection<Document> collection = mot.getCollection("topicsRecord");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        return rlist;
    }

    /**
     * db.topicsRecord.aggregate([
     *     {
     *         $match: {
     *             "classesId": "1807003276775612416",
     *             "subjectId": "1818889684666638336",
     *             "answerTime": {
     *                 "$gte": "2018-09-01 00:00:00","$lte":"2019-07-01 23:59:59"
     *             },
     * //            "answerTime": {
     * //                "$lte": "2019-07-01 23:59:59"
     * //            }
     *         }
     *     },
     *     {
     *         $project: {
     *             knowledge: "$topicsObj.knowledges",
     *             correct: 1
     *         }
     *     },
     *     {
     *         $unwind: "$knowledge"
     *     },
     *     {
     *         $group: {
     *             _id: "$knowledge",
     *             trueCount: {
     *                 $sum: {
     *                     $cond: [{
     *                         $eq: ["$correct", 1]
     *                     }, 1, 0]
     *                 }
     *             },
     *             wrongCount: {
     *                 $sum: {
     *                     $cond: [{
     *                         $ne: ["$correct", 1]
     *                     }, 1, 0]
     *                 }
     *             }
     *         }
     *     },
     *     {
     *         $project: {
     *             _id: 0,
     *             knowledgeId: "$_id._id",
     *             knowledgeName: "$_id.name",
     *             trueCount: 1,
     *             wrongCount: 1,
     *             totalCount: {
     *                 $add: ["$trueCount", "$wrongCount"]
     *             }
     *             ,
     *             divideCount: {
     *                 $divide: ["$wrongCount", {
     *                     $add: ["$trueCount", "$wrongCount"]
     *                 }]
     *             }
     *         }
     *     },
     *     {
     *         $sort: {
     *             divideCount: - 1
     *         }
     *     }
     * ])
     * @param homework
     * @return
     */
    public List<Map<String, Object>> findHomeworkListByConditionXq(Homework homework) {
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//??????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }

        Document match1 = new Document("$match",matchOpts);
        Document match2 = new Document("$project",new Document("knowledge","$topicsObj.knowledges").append("correct",1));
        Document match3 = new Document("$unwind","$knowledge");
        Document match4 = new Document("$group",new Document("_id","$knowledge").append("trueCount",
                new Document("$sum",new Document("$cond",Arrays.asList(new Document("$eq",
                        Arrays.asList("$correct",1)),1,0)))).append("wrongCount",
                new Document("$sum",new Document("$cond",Arrays.asList(new Document("$ne",Arrays.asList("$correct",1)),1,0)))));

        Document match5 = new Document("$project",new Document("_id",0).append("knowledgeId","$_id._id").append("knowledgeName",
                "$_id.name").append("trueCount",1).append("wrongCount",1).append("totalCount",
                new Document("$add",Arrays.asList("$trueCount","$wrongCount"))).append("divideCount",
                new Document("$divide",Arrays.asList("$wrongCount",new Document("$add",Arrays.asList("$trueCount","$wrongCount"))))));
        Document match6 = new Document("$sort",new Document("divideCount",-1));

        operas.addAll(Arrays.asList(match1, match2,match3,match4,match5,match6));

        //????????????
        if(homework.getPager()!=null && homework.getPager().getPaging()){
            operas.add(new Document("$skip",homework.getPager().getBeginRow()));
            operas.add(new Document("$limit",homework.getPager().getPageSize()));
        }
        MongoCollection<Document> collection = mot.getCollection("topicsRecord");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        return rlist;
    }

    public Map<String, Object> findHomeworksByConditionDetail(Homework homework) {
         //1.?????????????????????????????????id
        List<Map<String, Object>> list =
                    findHomeworkListByConditionXq(homework);
        if(list.size()==0){
            return new HashMap<>();
        }
          //2.????????????id?????????id?????????id?????????????????????????????????????????????
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias.add(new Criteria().where("del").is(1));//??????
        criterias.add(new Criteria().where("type").is(1));//??????
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));

        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????
            criterias.add(new Criteria().where("fromTo").is(studyYear));
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("topicArr"),
                Aggregation.unwind("topicArr.knowledges"),
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.group("topicArr.knowledges.id").count().as("knowledgenum"),Aggregation.project().
                        and("_id").as("knowledgeId").and("knowledgenum").as("knowledgenum"));

        AggregationResults<Homework> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, Homework.class);//(????????????id??????????????????)
        List<Homework> homeworkList = outputTypeCount.getMappedResults();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            String kId = (String) map.get("knowledgeId");
            if(homeworkList.size()==0){//??????????????????0
                map.put("relativeTc",0);
            }
            if(homeworkList.size()>0) {
                for (int j = 0; j < homeworkList.size(); j++) {
                    if (kId.equals(homeworkList.get(j).getKnowledgeId())) {
                        map.put("relativeTc", homeworkList.get(j).getKnowledgenum());//?????????????????????
                    }
                }
            }
        }
        //3.??????????????????????????????????????????
        Query query = query(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
        Long subjectPushNum = mot.count(query, Homework.class);
        Map map = new HashMap();
        map.put("list",list);
        map.put("subjectPushNum",subjectPushNum);
        map.put("totalCount",list.size());
        return map;
    }

    /**
     * db.homework.aggregate([{ "$unwind" : "$topicArr" },
     * { "$match" : { "$and" : [{ "schoolId" : "1741058039675183104" }, { "del" : 1 }, { "type" : 1 },{ "endTime" : { "$lte" : "2018-12-10 00:00:00" }
     * { "classesId" : "1807003276775612416" },
     * { "subjectId" : "1818889684666638336" },
     * { "publishTime" : { "$gte" : "2018-09-01 00:00:00" } },
     * { "publishTime" : { "$lte" : "2019-07-01 23:59:59" } }] } },
     * ])
     * @param homework
     * @return
     */
    public List<HomeworkNew> findKnowlegAllMoreDetailByCondition(Homework homework) {//??????????????????????????????
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias.add(new Criteria().where("del").is(1));//??????
        criterias.add(new Criteria().where("type").is(1));//??????
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            criterias.add(new Criteria().where("fromTo").is(studyYear));
        }
        Aggregation aggregation = Aggregation.newAggregation(
                        Aggregation.unwind("topicArr"),
                        Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                       Aggregation.match(Criteria.where("topicArr.knowledges._id").is(homework.getKnowledgeId())));
        Pager pager = homework.getPager();

        if(homework.getPager()!=null){//???page???????????????
            aggregation = Aggregation.newAggregation(//??????criteria????????????
                    Aggregation.unwind("topicArr"),
                    Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                    Aggregation.match(Criteria.where("topicArr.knowledges._id").is(homework.getKnowledgeId())),
                    Aggregation.skip(pager.getBeginRow()),
                    Aggregation.limit(pager.getPageSize())
                    );
        }
        AggregationResults<HomeworkNew> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, HomeworkNew.class);
        List<HomeworkNew> homeworkList = outputTypeCount.getMappedResults();
        return homeworkList;
    }

    //????????????
    private String getStudyYear(Homework homework){
        /**
         * ?????????????????? ??????????????????????????????????????????
         * ??????????????????????????? ?????????????????????????????????
         */
        CurSchoolYear curSchoolYear = schoolYearFeign.findCurSchoolYear(homework.getSchoolId());
        if(curSchoolYear!=null){
            return curSchoolYear.getFromTo();
        }
        return null;
    }

    /**
     * db.homework.aggregate([{ "$unwind" : "$topicArr" },
     * { "$match" : { "$and" : [{ "schoolId" : "1741058039675183104" }, { "del" : 1 }, { "type" : 1 },
     *  {"endTime" : { "$lte" : "2018-12-10 00:00:00" },{ "topicArr.sqId" : "1859862401361068032" },
     * {"sqId" : "1937341137479745536"},
     * { "classesId" : "1807003276775612416" },
     * { "subjectId" : "1818889684666638336" },
     * { "publishTime" : { "$gte" : "2018-09-01 00:00:00" } },
     * { "publishTime" : { "$lte" : "2019-07-01 23:59:59" } }] } },
     * ])
     * @param homework
     * @return
     */
    public HomeworkNew findCurrentTopicDetail(Homework homework) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias.add(new Criteria().where("del").is(1));//??????
        criterias.add(new Criteria().where("type").is(1));//??????
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));//??????id

        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????
            criterias.add(where("fromTo").is(studyYear));
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("topicArr"),
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.match(Criteria.where("topicArr.knowledges._id").is(homework.getKnowledgeId())),
                Aggregation.match(Criteria.where("topicArr._id").is(homework.getTopicSqId())),
                Aggregation.match(Criteria.where("_id").is(homework.getId()))
        );

        AggregationResults<HomeworkNew> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, HomeworkNew.class);
        List<HomeworkNew> homeworkList = outputTypeCount.getMappedResults();
        if(homeworkList.size()==0){
            return new HomeworkNew();
        }
        HomeworkNew homework_new = homeworkList.get(0);//????????????????????????
        if(homework_new.getTopicsCounts().size()==0){//?????????

        }else{//??????
            for(int i=0;i<homework_new.getTopicsCounts().size();i++){
                TopicsCount topicsCount = homework_new.getTopicsCounts().get(i);
                if(homework.getTopicSqId().equals(topicsCount.getId())){//???????????????
                    double wrondNum = (int)topicsCount.getWrongNum();
                    double correctNum = (int)topicsCount.getCorrectNum();
                    if((correctNum+wrondNum)>0){
                        double d = (correctNum)/(correctNum+wrondNum);
                        homework_new.setClassesRightRate(d);//???????????????
                    }
                }
            }
        }
        //??????????????????
        List<Criteria> criterias1 = new ArrayList<>();
        //criterias1.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias1.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias1.add(new Criteria().where("subjectId").is(homework.getSubjectId()));//??????id
        Aggregation aggregation1 = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criterias1.toArray(new Criteria[criterias1.size()]))),
                Aggregation.match(Criteria.where("topicsObj._id").is(homework.getTopicSqId())),//?????????id
                Aggregation.match(Criteria.where("topicsObj.knowledges._id").is(homework.getKnowledgeId())),//??????
                Aggregation.match(Criteria.where("channelId").is(homework.getId())),
                Aggregation.project("studentName").andExclude("_id")//???????????????id
        );

        AggregationResults<WrongTopics> outputTypeCount1 =
                mot.aggregate(aggregation1, WrongTopics.class, WrongTopics.class);
        List<String> studentsName = outputTypeCount1.getMappedResults().stream().
                map(WrongTopics::getStudentName).collect(Collectors.toList());
        homework_new.setStudentsName(studentsName);
        return homework_new;
    }

    public List<Map<String,Object>> findOneStudentDetail(Homework homework) {
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//???????????????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);

        if(studyYear!=null){//??????????????????
            matchOpts.append("fromTo",studyYear);
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$project",new Document("knowledge","$topicsObj.knowledges").append("correct",1));//?????????????????????????????????
        Document match3 = new Document("$unwind","$knowledge");
        Document match4 = new Document("$group",new Document("_id","$knowledge").append("trueCount",
                new Document("$sum",new Document("$cond",Arrays.asList(new Document("$eq",
                        Arrays.asList("$correct",1)),1,0)))).append("wrongCount",
                new Document("$sum",new Document("$cond",Arrays.asList(new Document("$ne",Arrays.asList("correct",1)),1,0)))));

        Document match5 = new Document("$project",new Document("_id",0).append("knowledgeId","$_id._id").append("knowledgeName",
                "$_id.name").append("trueCount",1).append("wrongCount",1).append("totalCount",
                new Document("$add",Arrays.asList("$trueCount","$wrongCount"))).append("divideCount",
                new Document("$divide",Arrays.asList("$wrongCount",new Document("$add",Arrays.asList("$trueCount","$wrongCount"))))));
        Document match6 = new Document("$sort",new Document("divideCount",-1));

        operas.addAll(Arrays.asList(match1, match2,match3,match4,match5,match6));

        //????????????
        if(homework.getPager()!=null && homework.getPager().getPaging()){
            operas.add(new Document("$skip",homework.getPager().getBeginRow()));
            operas.add(new Document("$limit",homework.getPager().getPageSize()));
        }

        MongoCollection<Document> collection = mot.getCollection("topicsRecord");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }

        return rlist;
    }

    public List<Map<String, Object>> findOneStudentKnoledgeContext(Homework homework) {
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//???????????????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$lookup",new Document("from","homework").
                append("let",new Document("channelId","$channelId").append("topicId","$topicsObj._id")).
                append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",
                                new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$$channelId","$_id")))))),
                        new Document("$unwind","$topicsCounts"),
                        new Document("$match",new Document("$expr",
                                new Document("$eq",Arrays.asList("$topicsCounts._id","$$topicId")))))).append("as","stockdata"));

        Document match3 = new Document("$project",new Document("_id",0).append("topicsObj",1).append("topicsCounts","$stockdata.topicsCounts"));
        //??????
        Document match4 = new Document("$unwind","$topicsObj.knowledges");
        Document match5 = new Document("$match",new Document("topicsObj.knowledges._id",homework.getKnowledgeId()));
        operas.addAll(Arrays.asList(match1, match2,match3,match4,match5));

        //????????????
        if(homework.getPager()!=null && homework.getPager().getPaging()){
            operas.add(new Document("$skip",homework.getPager().getBeginRow()));
            operas.add(new Document("$limit",homework.getPager().getPageSize()));
        }

        MongoCollection<Document> collection = mot.getCollection("wrongTopics");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        if(rlist.size()==0){
            return null;
        }

        for(int i=0;i<rlist.size();i++){
            Map<String,Object>  map = rlist.get(i);
            if(map.get("topicsCounts")==null){
                map.put("classRightRate",0);
                continue;
            }
            List<Document> documents =  (List<Document>) map.get("topicsCounts");//???????????????????????????
            if(documents.isEmpty()){
                map.put("classRightRate",0);
                continue;
            }
            Document d = documents.get(0);
            TopicsCount t = mot.getConverter().read(TopicsCount.class,d);

            double w = t.getWrongNum();
            double r = t.getCorrectNum();
            if((w+r)==0){
                map.put("classRightRate",0);
            }else{
                map.put("classRightRate",(r)/(r+w));
            }
        }
        return rlist;
    }

    /**
     * db.wrongTopics.aggregate([{$match:{ "classesId" : "1807003276775612421" , "subjectId" : "2070339988119969792","topicsObj.typeId":{$in:[2,3]},
     *      "studentId" : "2112950908419399826", "answerTime" : { "$gte" : "2018-09-01 00:00:00" },
     *      "answerTime" : { "$lte" : "2019-07-01 23:59:59" }}},
     *    {$lookup:{
     *              from: "homework",
     *                    let: { channelId: "$channelId",topicId:"$topicsObj.sqId"},
     *                  pipeline: [
     *                       { $match:
     *                         { $expr:
     *                               { $and:
     *                                  [
     *                                   { $eq: [ "$$channelId",  "$sqId" ] }
     *                                ]
     *                             }
     *                            }
     *                          },
     *                         {$unwind: "$topicsCounts"},
     *                         { $match:
     *                          { $expr:
     *                             { $eq: [ "$topicsCounts.sqId",  "$$topicId" ] }
     *                          }
     *                         },
     *                     ],
     *               as: "stockdata"
     *                    },
     *
     *     },
     *     {$sort:{answerTime:-1}},
     * {$project:{_id:0,topicsObj:1,topicsCounts:"$stockdata.topicsCounts"}}
     * ])
     * @param homework
     * @return
     */
    public List<WrongTopics> mistakesCollection(Homework homework) {//?????????
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();

        if(StringUtils.isNotEmpty(homework.getId())){
            matchOpts.append("_id",homework.getId());
        }
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//???????????????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }
        if(homework.getTypeId()!=null && "23".equals(homework.getTypeId())){//typeId:2?????????3??????
            matchOpts.append("topicsObj.typeId",new Document("$in",Arrays.asList(2,3)));
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$lookup",new Document("from","homework").
                append("let",new Document("channelId","$channelId").append("topicId","$topicsObj._id")).
                        append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",
                                        new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$$channelId","$_id")))))),
                                new Document("$unwind","$topicsCounts"),
                                new Document("$match",new Document("$expr",
                                        new Document("$eq",Arrays.asList("$topicsCounts._id","$$topicId")))))).append("as","stockdata"));
        Document match22 = new Document("$sort",new Document("answerTime",-1));//??????????????????????????????
        Document match3 = new Document("$project",new Document("_id",1).append("answer",1).append("topicsObj",1).append("topicsCounts","$stockdata.topicsCounts"));

        operas.addAll(Arrays.asList(match1, match2,match22,match3));

        //????????????
        if(homework.getPager()!=null && homework.getPager().getPaging()){
            operas.add(new Document("$skip",homework.getPager().getBeginRow()));
            operas.add(new Document("$limit",homework.getPager().getPageSize()));
        }

        MongoCollection<Document> collection = mot.getCollection("wrongTopics");//??????????????????????????????????????????

        List<Map<String, Object>> rlist = new ArrayList<>();

        List<WrongTopics> list = new ArrayList<>();

        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            list.add(mot.getConverter().read(WrongTopics.class,mdi.next()));
        }
        if(list==null && list.size()==0){
            return null;
        }
        list.stream().map(wrongTopics -> {
            if(wrongTopics.getTopicsCounts()==null){
                wrongTopics.setClassRightRate(0.0);
            }else{
                List<TopicsCount> topicsCountList = wrongTopics.getTopicsCounts();
                if(topicsCountList.size()>0){
                    TopicsCount t = topicsCountList.get(0);
                    double w = t.getWrongNum();
                    double r = t.getCorrectNum();
                    if((w+r)==0){
                        wrongTopics.setClassRightRate(0.0);
                    }else{
                        wrongTopics.setClassRightRate(r/(r+w));
                    }
                }
            }
            return wrongTopics;
        }).collect(Collectors.toList());
        return list;
    }

    public HomeworkNew mistakesCollectionDetail(Homework homework) {//???????????????????????????????????????
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias.add(new Criteria().where("del").is(1));//??????
        criterias.add(new Criteria().where("type").is(1));//??????
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));//??????id

        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            criterias.add(new Criteria().where("fromTo").is(studyYear));//??????id
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("topicArr"),
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.match(Criteria.where("topicArr._id").is(homework.getTopicSqId())),
                Aggregation.match(Criteria.where("_id").is(homework.getId()))
        );

        AggregationResults<HomeworkNew> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, HomeworkNew.class);
        List<HomeworkNew> homeworkList = outputTypeCount.getMappedResults();
        if(homeworkList.size()==0 || homeworkList==null){
            return null;
        }
        HomeworkNew homework_new = homeworkList.get(0);//????????????????????????
        if(homework_new.getTopicsCounts().size()==0){//?????????

        }else{//??????
            for(int i=0;i<homework_new.getTopicsCounts().size();i++){
                TopicsCount topicsCount = homework_new.getTopicsCounts().get(i);
                if(homework.getTopicSqId().equals(topicsCount.getId())){//???????????????
                    double wrondNum = (int)topicsCount.getWrongNum();
                    double correctNum = (int)topicsCount.getCorrectNum();
                    if((correctNum+wrondNum)>0){
                        double d = (correctNum)/(correctNum+wrondNum);
                        homework_new.setClassesRightRate(d);//???????????????
                    }
                }
            }
        }
        return homework_new;
    }

    public Map<String, Object> findWrongtopicInfo(Homework homework) {
        //1.???????????????????????????????????????
        List<Map<String, Object>> list =
                findHomeworkListByConditionXq(homework);
        list = list.stream().filter(s->//?????????????????????????????????
                (((String)s.get("knowledgeId")).equals(homework.getKnowledgeId()))
        ).collect(Collectors.toList());

        //2.????????????????????????????????????????????????????????????????????????
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        criterias.add(new Criteria().where("studentId").is(homework.getStudentId()));
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            criterias.add(new Criteria().where("fromTo").is(studyYear));
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("topicsObj.knowledges"),
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.group("topicsObj.knowledges.id").count().as("knowledgenum"),Aggregation.project().
                        and("_id").as("knowledgeName").and("knowledgenum").as("knowledgenum"));

        AggregationResults<WrongTopics> outputTypeCount =
                mot.aggregate(aggregation, WrongTopics.class, WrongTopics.class);//???????????????????????????
        List<WrongTopics> homeworkList = outputTypeCount.getMappedResults();
        Double  relativeCount = 0.0;
        if(homeworkList.size()==0){
            relativeCount = 0.0;
        }
        for(int i=0;i<homeworkList.size();i++){
            String knowlegeId = homework.getKnowledgeId();
            for(int j=0;j<homeworkList.size();j++){
                WrongTopics wrongTopics = homeworkList.get(j);
                if(knowlegeId.equals(wrongTopics.getKnowledgeName())){
                    relativeCount = wrongTopics.getKnowledgenum();
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("relativeCount",relativeCount);
        map.put("list",list.get(0));
        return map;
    }

    public Integer findOneStudentKnoledgeContextCount(Homework homework) {//?????????
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//???????????????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$lookup",new Document("from","homework").
                append("let",new Document("channelId","$channelId").append("topicId","$topicsObj._id")).
                append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",
                                new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$$channelId","$_id")))))),
                        new Document("$unwind","$topicsCounts"),
                        new Document("$match",new Document("$expr",
                                new Document("$eq",Arrays.asList("$topicsCounts._id","$$topicId")))))).append("as","stockdata"));

        Document match3 = new Document("$project",new Document("_id",0).append("topicsObj",1).append("topicsCounts","$stockdata.topicsCounts"));
        //??????
        Document match4 = new Document("$unwind","$topicsObj.knowledges");
        Document match5 = new Document("$match",new Document("topicsObj.knowledges._id",homework.getKnowledgeId()));
        operas.addAll(Arrays.asList(match1, match2,match3,match4,match5));

        MongoCollection<Document> collection = mot.getCollection("wrongTopics");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        if(rlist.size()==0){
            return 0;
        }

        return rlist.size();
    }

    public Integer mistakesCollectionCount(Homework homework) {//??????
        List<Bson> operas = new ArrayList<>();
        Document matchOpts = new Document();
        if(StringUtils.isNotEmpty(homework.getClassesId())){//??????
            matchOpts.append("classesId",homework.getClassesId());
        }
        if(StringUtils.isNotEmpty(homework.getSubjectId())){//??????????????????
            matchOpts.append("subjectId",homework.getSubjectId());
        }
        if(StringUtils.isNotEmpty(homework.getStudentId())){//???????????????id
            matchOpts.append("studentId",homework.getStudentId());
        }
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            matchOpts.append("fromTo",studyYear);//????????????????????????????????????
        }
        if(homework.getTypeId()!=null){//typeId:2?????????3??????
            matchOpts.append("topicsObj.typeId",new Document("$in",Arrays.asList(2,3)));
        }
        Document match1 = new Document("$match",matchOpts);//????????????(????????????new??????document)
        Document match2 = new Document("$lookup",new Document("from","homework").
                append("let",new Document("channelId","$channelId").append("topicId","$topicsObj._id")).
                append("pipeline",Arrays.asList(new Document("$match",new Document("$expr",
                                new Document("$and",Arrays.asList(new Document("$eq",Arrays.asList("$$channelId","$_id")))))),
                        new Document("$unwind","$topicsCounts"),
                        new Document("$match",new Document("$expr",
                                new Document("$eq",Arrays.asList("$topicsCounts._id","$$topicId")))))).append("as","stockdata"));

        Document match3 = new Document("$project",new Document("_id",0).append("topicsObj",1).append("topicsCounts","$stockdata.topicsCounts"));

        operas.addAll(Arrays.asList(match1, match2,match3));

        MongoCollection<Document> collection = mot.getCollection("wrongTopics");//??????????????????????????????????????????
        List<Map<String, Object>> rlist = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(operas);
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            rlist.add(mdi.next());
        }
        if(rlist==null){
            return 0;
        }
        return rlist.size();
    }

    public Integer findKnowlegAllMoreDetailByConditionCount(Homework homework) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(new Criteria().where("schoolId").is(homework.getSchoolId()));//??????id
        criterias.add(new Criteria().where("del").is(1));//??????
        criterias.add(new Criteria().where("type").is(1));//??????
        criterias.add(new Criteria().where("classesId").is(homework.getClassesId()));//??????id
        criterias.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        String studyYear = getStudyYear(homework);
        if(studyYear!=null){//?????????????????????????????????
            criterias.add(new Criteria().where("fromTo").is(studyYear));
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("topicArr"),
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.match(Criteria.where("topicArr.knowledges._id").is(homework.getKnowledgeId())));
        AggregationResults<HomeworkNew> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, HomeworkNew.class);
        List<HomeworkNew> homeworkList = outputTypeCount.getMappedResults();
        if(homeworkList.size()==0){
            return 0;
        }
        return homeworkList.size();
    }

    public List<Homework> findHomeworkListByConditionEwb(Homework homework) {
        //????????????????????????????????????
        List<Criteria> c = new ArrayList<>();
        c.add(new Criteria().where("teacherId").is(homework.getTeacherId()));
        if(StringUtils.isNotEmpty(homework.getEwbStartTime())&&StringUtils.isNotEmpty(homework.getEwbEndTime())){
            c.add(new Criteria().where("publishTime").gte(homework.getEwbStartTime()));
            c.add(new Criteria().where("publishTime").lte(homework.getEwbEndTime()));
        }
        if(homework.getGradeId()!=null){
            c.add(new Criteria().where("gradeId").is(homework.getGradeId()));
        }
        if(homework.getClassesId()!=null){
            c.add(new Criteria().where("classesId").is(homework.getClassesId()));
        }
        if(homework.getSubjectId()!=null){
            c.add(new Criteria().where("subjectId").is(homework.getSubjectId()));
        }
        if(homework.getPublishStatus()!=null){
            c.add(new Criteria().where("publishStatus").is(homework.getPublishStatus()));
        }
        Query query = query(new Criteria().andOperator(c.toArray(new Criteria[c.size()])));
        if(homework.getPager()!=null&&homework.getPager().getPaging()){
            query.with(homework.getPager());
        }
        List<Homework> homeworkList = mot.find(query, Homework.class);
        if(homeworkList!=null&&homeworkList.size()>0) {
            //?????????????????????????????????className????????? ??????????????????
            homeworkList.forEach(h ->
                    h.setClassesName(h.getClassesName().replace("???", ""))
            );
        }
        return homeworkList;
    }

    public void deleteHomeworkByClasses(List<String> classIdList){
    	classIdList.forEach(ss->{
            mot.remove(query(where("classesId").is(ss)),Homework.class);
            mot.remove(query(where("classesId").is(ss)),HomeworkStudent.class);
            mot.remove(query(where("classesId").is(ss)), TopicsRecord.class);
            mot.remove(query(where("classesId").is(ss)),WrongTopics.class);
    	});
    }
}
