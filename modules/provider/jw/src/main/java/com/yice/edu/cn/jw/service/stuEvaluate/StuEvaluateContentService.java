package com.yice.edu.cn.jw.service.stuEvaluate;

import cn.hutool.core.date.DateTime;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContentHistory;
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
public class StuEvaluateContentService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public StuEvaluateContent findStuEvaluateContentById(String id) {
        return mot.findById(id,StuEvaluateContent.class);
    }
    public void saveStuEvaluateContent(StuEvaluateContent stuEvaluateContent) {
        stuEvaluateContent.setId(sequenceId.nextId());
        mot.insert(stuEvaluateContent);
    }
    public List<StuEvaluateContent> findStuEvaluateContentListByCondition(StuEvaluateContent stuEvaluateContent) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(stuEvaluateContent, stuEvaluateContent.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(stuEvaluateContent.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateContent.class,operations),StuEvaluateContent.class).getMappedResults();
    }
    public long findStuEvaluateContentCountByCondition(StuEvaluateContent stuEvaluateContent) {
        Criteria criteria = MongoKit.buildCriteria(stuEvaluateContent, stuEvaluateContent.getPager());
        return mot.count(query(criteria), StuEvaluateContent.class);
    }
    public StuEvaluateContent findOneStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent) {
        Example<StuEvaluateContent> example = Example.of(stuEvaluateContent, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = stuEvaluateContent.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateContent.class,operations),StuEvaluateContent.class).getUniqueMappedResult();
    }

    public void updateStuEvaluateContent(StuEvaluateContent stuEvaluateContent) {
        mot.updateFirst(query(where("id").is(stuEvaluateContent.getId())), MongoKit.update(stuEvaluateContent),StuEvaluateContent.class);
    }
    public void deleteStuEvaluateContent(String id) {
        mot.remove(query(where("id").is(id)),StuEvaluateContent.class);
    }
    public void deleteStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent) {
        Example<StuEvaluateContent> example = Example.of(stuEvaluateContent, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, StuEvaluateContent.class);
    }
    public void batchSaveStuEvaluateContent(List<StuEvaluateContent> stuEvaluateContents){
        stuEvaluateContents.forEach(stuEvaluateContent -> stuEvaluateContent.setId(sequenceId.nextId()));
        mot.insertAll(stuEvaluateContents);
    }


    /**
     * 推送时候根据时间段查询出已评价的内容
     * @param stuEvaluateContent
     * @return
     */
    public List<StuEvaluateContent> findStuEvaluateContentListByCondition1(StuEvaluateContent stuEvaluateContent) {
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        Date date = DateUtil.parse(today, "yyyy-MM-dd");
        Date beginOfDay = DateUtil.beginOfDay(date);
        DateTime yestedayDate= DateUtil.offsetHour(beginOfDay, -6);//获取昨天18：00

        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(stuEvaluateContent, stuEvaluateContent.getPager());
        criteria.andOperator(new Criteria("endTime").gte(yestedayDate.toString()),new Criteria("endTime").lte(DateUtil.now()));
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(stuEvaluateContent.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateContent.class,operations),StuEvaluateContent.class).getMappedResults();
    }


    public List<StuEvaluateContentHistory> findStuEvaluateContentListByClassId(List<String>classIdList) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        cl.add(new Criteria("classId").in(classIdList));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        return mot.aggregate(Aggregation.newAggregation(StuEvaluateContent.class,operations),StuEvaluateContentHistory.class).getMappedResults();
    }

    public void batchSaveStuEvaluateContentHistory(List<StuEvaluateContentHistory> stuEvaluateContents){
        mot.insertAll(stuEvaluateContents);
    }

}
