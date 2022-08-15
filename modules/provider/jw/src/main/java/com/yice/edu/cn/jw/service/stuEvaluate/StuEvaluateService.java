package com.yice.edu.cn.jw.service.stuEvaluate;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.*;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForStuEvaluate;
import com.yice.edu.cn.jw.service.teacher.TeacherClassesService;
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
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StuEvaluateService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    StuEvaluateSendObjectService stuEvaluateSendObjectService;
    @Autowired
    StuEvaluateContentService stuEvaluateContentService;

    public StuEvaluate findStuEvaluateById(String id) {
        return mot.findById(id,StuEvaluate.class);
    }
    public void saveStuEvaluate(StuEvaluate stuEvaluate) {
        stuEvaluate.setCreateTime(DateUtil.now());
        stuEvaluate.setId(sequenceId.nextId());
        mot.insert(stuEvaluate);
    }
    public List<StuEvaluate> findStuEvaluateListByCondition(StuEvaluate stuEvaluate) {
        Criteria criteria=new Criteria();
        List<Criteria> c1 = new ArrayList<>();
        if(StrUtil.isNotEmpty(stuEvaluate.getSearchStartTime())){
            c1.add(new Criteria("createTime").gte(stuEvaluate.getSearchStartTime()));
        }
        if(StrUtil.isNotEmpty(stuEvaluate.getSearchEndTime())){
            c1.add(new Criteria("createTime").lte(stuEvaluate.getSearchEndTime()));
        }

        List<AggregationOperation> operations = new ArrayList<>();

        if(StrUtil.isNotEmpty(stuEvaluate.getState())) {
            if (stuEvaluate.getState().equals("1"))
                c1.add(new Criteria("endTime").gte(DateUtil.formatDateTime(DateUtil.date())));
            if (stuEvaluate.getState().equals("2"))
                c1.add(new Criteria("endTime").lte(DateUtil.formatDateTime(DateUtil.date())));
        }

        c1.add(MongoKit.buildCriteria(stuEvaluate, stuEvaluate.getPager()));
        criteria.andOperator(c1.toArray(new Criteria[c1.size()]));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(stuEvaluate.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(StuEvaluate.class,operations),StuEvaluate.class).getMappedResults();

    }
    public long findStuEvaluateCountByCondition(StuEvaluate stuEvaluate) {
        Criteria criteria = new Criteria();
        List<Criteria> c1 = new ArrayList<>();
        if(StrUtil.isNotEmpty(stuEvaluate.getSearchStartTime())){
            c1.add(new Criteria("createTime").gte(stuEvaluate.getSearchStartTime()));
        }
        if(StrUtil.isNotEmpty(stuEvaluate.getSearchEndTime())){
            c1.add(new Criteria("createTime").lte(stuEvaluate.getSearchEndTime()));
        }
        if(StrUtil.isNotEmpty(stuEvaluate.getState())) {
            if (stuEvaluate.getState().equals("1"))
                c1.add(new Criteria("endTime").gte(DateUtil.now()));
            if (stuEvaluate.getState().equals("2"))
                c1.add(new Criteria("endTime").lte(DateUtil.now()));
        }
        c1.add(MongoKit.buildCriteria(stuEvaluate, stuEvaluate.getPager()));
        criteria.andOperator(c1.toArray(new Criteria[c1.size()]));
        return mot.count(query(criteria), StuEvaluate.class);
    }
    public StuEvaluate findOneStuEvaluateByCondition(StuEvaluate stuEvaluate) {
        Example<StuEvaluate> example = Example.of(stuEvaluate, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = stuEvaluate.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(StuEvaluate.class,operations),StuEvaluate.class).getUniqueMappedResult();
    }

    public void updateStuEvaluate(StuEvaluate stuEvaluate) {
        mot.updateFirst(query(where("id").is(stuEvaluate.getId())), MongoKit.update(stuEvaluate),StuEvaluate.class);
    }
    public void deleteStuEvaluate(String id) {
        mot.remove(query(where("id").is(id)),StuEvaluate.class);
    }
    public void deleteStuEvaluateByCondition(StuEvaluate stuEvaluate) {
        Example<StuEvaluate> example = Example.of(stuEvaluate, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, StuEvaluate.class);
    }
    public void batchSaveStuEvaluate(List<StuEvaluate> stuEvaluates){
        stuEvaluates.forEach(stuEvaluate -> stuEvaluate.setId(sequenceId.nextId()));
        mot.insertAll(stuEvaluates);
    }

    public List<TeacherClassesForStuEvaluate> findClassesHeadTeacherBySchoolId(String schoolId){
        return teacherClassesService.findClassesHeadTeacherBySchoolId(schoolId);
    }


    public void moveStuEvaluateToHistory(HistoryPojo historyPojo){
        List<StuEvaluateSendObjectHistory> stuEvaluateSendObjects= stuEvaluateSendObjectService.findStuEvaluateSendObjectListByClassId(historyPojo.getClassIdList());
//        stuEvaluateSendObjectService.batchSaveStuEvaluateSendObjectHistory(stuEvaluateSendObjects);
        stuEvaluateSendObjects.forEach(s ->{
        	mot.save(s);
            stuEvaluateSendObjectService.deleteStuEvaluateSendObject(s.getId());
        });
        List<StuEvaluateContentHistory> stuEvaluateContentList=stuEvaluateContentService.findStuEvaluateContentListByClassId(historyPojo.getClassIdList());
//        stuEvaluateContentService.batchSaveStuEvaluateContentHistory(stuEvaluateContentList);
        stuEvaluateContentList.forEach(c->{
        	mot.save(c);
            stuEvaluateContentService.deleteStuEvaluateContent(c.getId());
        });
    }



}
