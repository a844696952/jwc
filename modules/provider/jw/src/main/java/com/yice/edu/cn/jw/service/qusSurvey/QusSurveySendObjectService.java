package com.yice.edu.cn.jw.service.qusSurvey;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySendObject;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyTeacherSendClass;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.SendObjectQusSurvey;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class QusSurveySendObjectService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private  QusSurveySubmitService qusSurveySubmitService;
    @Autowired
    private QusSurveyService qusSurveyService;
    public QusSurveySendObject findQusSurveySendObjectById(String id) {
        return mot.findById(id,QusSurveySendObject.class);
    }
    public void saveQusSurveySendObject(QusSurveySendObject qusSurveySendObject) {
        deleteQusSurveySendObjectBySurveyId(qusSurveySendObject.getSurveyId());
        qusSurveySendObject.setId(sequenceId.nextId());
        mot.insert(qusSurveySendObject);
    }
    public List<QusSurveySendObject> findQusSurveySendObjectListByCondition(QusSurveySendObject qusSurveySendObject) {
        Example<QusSurveySendObject> example = Example.of(qusSurveySendObject, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(qusSurveySendObject.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveySendObject.class,operations),QusSurveySendObject.class).getMappedResults();

    }
    public QusSurveySendObject findOneQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject) {
        Example<QusSurveySendObject> example = Example.of(qusSurveySendObject, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurveySendObject.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurveySendObject.class,operations),QusSurveySendObject.class).getUniqueMappedResult();
    }
    public long findQusSurveySendObjectCountByCondition(QusSurveySendObject qusSurveySendObject) {
        Example<QusSurveySendObject> example = Example.of(qusSurveySendObject, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, QusSurveySendObject.class);
    }
    public void updateQusSurveySendObject(QusSurveySendObject qusSurveySendObject) {
        mot.updateFirst(query(where("id").is(qusSurveySendObject.getId())), MongoKit.update(qusSurveySendObject),QusSurveySendObject.class);
    }
    public void deleteQusSurveySendObject(String id) {
        mot.remove(query(where("id").is(id)),QusSurveySendObject.class);
    }

    public void deleteQusSurveySendObjectByCondition(QusSurveySendObject qusSurveySendObject) {
        Example<QusSurveySendObject> example = Example.of(qusSurveySendObject, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, QusSurveySendObject.class);
    }
    public void batchSaveQusSurveySendObject(List<QusSurveySendObject> qusSurveySendObjects){
        qusSurveySendObjects.forEach(qusSurveySendObject -> qusSurveySendObject.setId(sequenceId.nextId()));
        mot.insertAll(qusSurveySendObjects);
    }
    public void deleteQusSurveySendObjectBySurveyId(String surveyId) {
        mot.remove(query(where("surveyId").is(surveyId)),QusSurveySendObject.class);
    }

    public List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(StudentQusSurvey student) {
             Criteria criteria = Criteria.where("teacherList").elemMatch(Criteria.where("alreadyQusSurveyClassList").elemMatch(Criteria.where("classesId").is(student.getClassId())));
            List<AggregationOperation> operations = new ArrayList<>();
            operations.add(Aggregation.match(criteria));
            MongoKit.sortPageInclude(student.getPager(),operations);
            List<SendObjectQusSurvey> list= mot.aggregate(Aggregation.newAggregation(SendObjectQusSurvey.class,operations),SendObjectQusSurvey.class).getMappedResults();


             list.forEach(sendObjectQusSurvey -> {
                 sendObjectQusSurvey.setTeacherList(null);
                 Date endTime1= DateUtil.parse(sendObjectQusSurvey.getEndTime());
                 Date nowDate = DateUtil.date();
                 if(nowDate.getTime()>endTime1.getTime()){
                     sendObjectQusSurvey.setEndState("2");
                 }else {
                     sendObjectQusSurvey.setEndState("1");
                 }
             });
         return list;
    }

    public long findQusSurveySendObjectListCountByClassId(StudentQusSurvey student) {
        Criteria criteria = Criteria.where("teacherList").elemMatch(Criteria.where("alreadyQusSurveyClassList").elemMatch(Criteria.where("classesId").is(student.getClassId())));
        Query query = query(criteria);
        return mot.count(query, SendObjectQusSurvey.class);
    }

    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByTeacherId(SendObjectQusSurvey  sendObject) {
        String nowTime=DateUtil.now();
        String teacherId=sendObject.getTeacherId();
        sendObject.setTeacherId(null);
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria c = MongoKit.buildCriteria(sendObject, sendObject.getPager());
        c.andOperator( Criteria.where("teacherList").elemMatch(Criteria.where("teacherId").is(teacherId)),new Criteria("endTime").lte(nowTime));
        operations.add(Aggregation.match(c));
        MongoKit.sortPageInclude(sendObject.getPager(),operations);
        List<SendObjectQusSurvey> list=mot.aggregate(Aggregation.newAggregation(SendObjectQusSurvey.class,operations),SendObjectQusSurvey.class).getMappedResults();

        //获取老师涉及的班级的总的统计结果
        list.forEach(sendObjectQusSurvey -> {
                 QusSurveyTeacherSendClass qusSurveyTeacherSendClass=new QusSurveyTeacherSendClass();
                 qusSurveyTeacherSendClass.setSurveyId(sendObjectQusSurvey.getSurveyId());
                 qusSurveyTeacherSendClass.setTeacherId(teacherId);
                 QusSurveyTeacherSendClass qusSurveyTeacherSendClass1= qusSurveyService.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
                 sendObjectQusSurvey.setQusSurveyTeacherSendClass(qusSurveyTeacherSendClass1);
             });
        return list;
    }
    public long findSendObjectQusSurveyListCountByTeacherId(SendObjectQusSurvey  sendObject) {
        String nowTime=DateUtil.now();
        String teacherId=sendObject.getTeacherId();
        sendObject.setTeacherId(null);
        Criteria criteria = Criteria.where("teacherList").elemMatch(Criteria.where("teacherId").is(teacherId));
        criteria.andOperator(new Criteria("endTime").lte(nowTime));
        Query query = query(criteria);
        return mot.count(query, SendObjectQusSurvey.class);
    }


}
