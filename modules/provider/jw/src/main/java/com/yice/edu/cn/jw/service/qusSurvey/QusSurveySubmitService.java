package com.yice.edu.cn.jw.service.qusSurvey;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyTeacherSendClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class QusSurveySubmitService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private QusSurveyService qusSurveyService;
    @Autowired
    private QusSurveyOptionService qusSurveyOptionService;
    public QusSurveySubmit findQusSurveySubmitById(String id) {
        return mot.findById(id,QusSurveySubmit.class);
    }
    public void saveQusSurveySubmit(QusSurveySubmit qusSurveySubmit) {
        ArrayList<QusSurveyOption> qusSurveyOptionList=qusSurveySubmit.getQusSurveyOptionList();
         Integer score=0;
         for (int i = 0; i <qusSurveyOptionList.size() ; i++) {
            QusSurveyOption qusSurveyOption=qusSurveyOptionList.get(i);
            qusSurveyOption.setSurveyId(qusSurveySubmit.getSurveyId());
            qusSurveyOption.setTeacherId(qusSurveySubmit.getTeacherId());
            qusSurveyOption.setStudentId(qusSurveySubmit.getStudentId());
            qusSurveyOption.setSchoolId(qusSurveySubmit.getSchoolId());
            qusSurveyOptionService.saveQusSurveyOption(qusSurveyOption);
            score=score+qusSurveyOptionList.get(i).getOptionScore();
        }
        qusSurveySubmit.setStudentScore(score);
        qusSurveySubmit.setId(sequenceId.nextId());
        qusSurveySubmit.setCreateTime(DateUtil.now());
        mot.insert(qusSurveySubmit);

        //更新缓存表数据
        QusSurveyTeacherSendClass qusSurveyTeacherSendClass=new QusSurveyTeacherSendClass();
        qusSurveyTeacherSendClass.setTeacherId(qusSurveySubmit.getTeacherId());
        qusSurveyTeacherSendClass.setClassId(qusSurveySubmit.getClassId());
        qusSurveyTeacherSendClass.setSurveyId(qusSurveySubmit.getSurveyId());
        QusSurveyTeacherSendClass qusSurveyTeacherSendClass1=qusSurveyService.findOneQusSurveyTeacherSendClassesByCondition(qusSurveyTeacherSendClass);
        Integer num=qusSurveyTeacherSendClass1.getSubmitNum()+1;
        qusSurveyTeacherSendClass1.setSubmitNum(num);


        //对缓存表数据重新统计并重新赋值
        QusSurveySubmit qusSurveySubmit1=new QusSurveySubmit();
        qusSurveySubmit1.setClassId(qusSurveySubmit.getClassId());
        qusSurveySubmit1.setSurveyId(qusSurveySubmit.getSurveyId());
        QusSurveyTeacherSendClass qusSurveyTeacherSendClass2=findQusSurveySubmitSumByCondition(qusSurveySubmit1);

        qusSurveyTeacherSendClass1.setHighScore(qusSurveyTeacherSendClass2.getHighScore());
        qusSurveyTeacherSendClass1.setAvg(qusSurveyTeacherSendClass2.getAvg());
        qusSurveyTeacherSendClass1.setLowScore(qusSurveyTeacherSendClass2.getLowScore());
        qusSurveyService.updateQusSurveyTeacherSendClasses(qusSurveyTeacherSendClass1);

    }
    public List<QusSurveySubmit> findQusSurveySubmitListByCondition(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        MongoKit.sortPageInclude(qusSurveySubmit.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveySubmit.class,operations),QusSurveySubmit.class).getMappedResults();

    }
    public QusSurveySubmit findOneQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = qusSurveySubmit.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(QusSurveySubmit.class,operations),QusSurveySubmit.class).getUniqueMappedResult();
    }
    public long findQusSurveySubmitCountByCondition(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        return mot.count(query, QusSurveySubmit.class);
    }
    public void updateQusSurveySubmit(QusSurveySubmit qusSurveySubmit) {
        mot.updateFirst(query(where("id").is(qusSurveySubmit.getId())), MongoKit.update(qusSurveySubmit),QusSurveySubmit.class);
    }
    public void deleteQusSurveySubmit(String id) {
        mot.remove(query(where("id").is(id)),QusSurveySubmit.class);
    }
    public void deleteQusSurveySubmitByCondition(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, QusSurveySubmit.class);
    }
    public void batchSaveQusSurveySubmit(List<QusSurveySubmit> qusSurveySubmits){
        qusSurveySubmits.forEach(qusSurveySubmit -> qusSurveySubmit.setId(sequenceId.nextId()));
        mot.insertAll(qusSurveySubmits);
    }

    /**
     * 获取该提交学生的班级的分数平均分、最高分、最低分
     * @param qusSurveySubmit
     * @return
     */
    public QusSurveyTeacherSendClass findQusSurveySubmitSumByCondition(QusSurveySubmit qusSurveySubmit) {
        Example<QusSurveySubmit> example = Example.of(qusSurveySubmit, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        operations.add(Aggregation.group("classId").avg("studentScore").as("avg").min("studentScore").as("lowScore").max("studentScore").as("highScore"));
        MongoKit.sortPageInclude(qusSurveySubmit.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(QusSurveySubmit.class,operations),QusSurveyTeacherSendClass.class).getUniqueMappedResult();

    }


    public QusSurveySubmit saveQusSurveySubmit1(QusSurveySubmit qusSurveySubmit) {
        qusSurveySubmit.setId(sequenceId.nextId());
        qusSurveySubmit.setCreateTime(DateUtil.now());
        mot.insert(qusSurveySubmit);
        return qusSurveySubmit;
    }

    /**
     * 更改互动评价
     * @param qusSurveySubmit
     */
    public void updateSubmit(QusSurveySubmit qusSurveySubmit) {
        ArrayList<QusSurveyOption> qusSurveyOptionList=qusSurveySubmit.getQusSurveyOptionList();
        Integer score=0;
        for (int i = 0; i <qusSurveyOptionList.size() ; i++) {
            QusSurveyOption qusSurveyOption=qusSurveyOptionList.get(i);
            qusSurveyOption.setSurveyId(qusSurveySubmit.getSurveyId());
            qusSurveyOption.setBeEvaluatedTeacherId(qusSurveySubmit.getBeEvaluatedTeacherId());
            qusSurveyOption.setEvaluateTeacherId(qusSurveySubmit.getEvaluateTeacherId());
            qusSurveyOption.setSchoolId(qusSurveySubmit.getSchoolId());
            qusSurveyOptionService.saveQusSurveyOption(qusSurveyOption);
            score=score+qusSurveyOptionList.get(i).getOptionScore();
        }
        qusSurveySubmit.setScore(score);
        qusSurveySubmit.setUpdateTime(DateUtil.now());
        qusSurveySubmit.setSubmitStatus("2");
        mot.updateFirst(query(where("id").is(qusSurveySubmit.getId())), MongoKit.update(qusSurveySubmit),QusSurveySubmit.class);
    }

}
