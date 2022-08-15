package com.yice.edu.cn.jw.service.qusBankResource;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
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

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PersonalTopicsService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PersonalTopics findPersonalTopicsById(String id) {
        return mot.findById(id,PersonalTopics.class);
    }
    public void savePersonalTopics(PersonalTopics personalTopics) {
        personalTopics.setCreateTime(DateUtil.now());
        personalTopics.setId(sequenceId.nextId());
        personalTopics.setCreateBy(Constant.TOPIC_CREATEBY.PERSONAL);
        mot.insert(personalTopics);
    }
    public List<PersonalTopics> findPersonalTopicsListByCondition(PersonalTopics personalTopics) {
        //知识点查询和章节查询做in处理
        Document match = new Document();
        if(personalTopics.getTypeIds()!=null && personalTopics.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(personalTopics.getTypeIds())));
            personalTopics.setTypeIds(null);
        }
        if(personalTopics.getKnowledges()!=null && personalTopics.getKnowledges().size()>0){
            match.append("knowledges._id",new Document("$in",personalTopics.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            personalTopics.setKnowledges(null);
        }
        if(personalTopics.getCategories()!=null && personalTopics.getCategories().size()>0){
            match.append("categories",new Document("$in",personalTopics.getCategories()));
            personalTopics.setCategories(null);
        }
        if(personalTopics.getTopicClass()!=null && personalTopics.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(personalTopics.getTopicClass())));
            personalTopics.setTopicClass(null);
        }
        if(StringUtils.isNotEmpty(personalTopics.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(personalTopics.getDifficult().split(","))));
            personalTopics.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(personalTopics.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(personalTopics.getQuestionItemId().split(","))));
            personalTopics.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(personalTopics));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PersonalTopics.class)).find(match);
        Pager pager = personalTopics.getPager();
        pager.setExcludes(Arrays.asList("answer","analysis","child.answer","child.analysis"));
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<PersonalTopics> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(PersonalTopics.class, document)));
        return list;
    }
    public long findPersonalTopicsCountByCondition(PersonalTopics personalTopics) {
        //知识点查询和章节查询做in处理
        Document match = new Document();
        if(personalTopics.getTypeIds()!=null && personalTopics.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(personalTopics.getTypeIds())));
            personalTopics.setTypeIds(null);
        }
        if(personalTopics.getKnowledges()!=null && personalTopics.getKnowledges().size()>0){
            match.append("knowledges._id",new Document("$in",personalTopics.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            personalTopics.setKnowledges(null);
        }
        if(personalTopics.getCategories()!=null && personalTopics.getCategories().size()>0){
            match.append("categories",new Document("$in",personalTopics.getCategories()));
            personalTopics.setCategories(null);
        }
        if(personalTopics.getTopicClass()!=null && personalTopics.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(personalTopics.getTopicClass())));
            personalTopics.setTopicClass(null);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(personalTopics.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(personalTopics.getDifficult().split(","))));
            personalTopics.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(personalTopics.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(personalTopics.getQuestionItemId().split(","))));
            personalTopics.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(personalTopics));
        return mot.getCollection(mot.getCollectionName(PersonalTopics.class)).countDocuments(match);
    }
    public PersonalTopics findOnePersonalTopicsByCondition(PersonalTopics personalTopics) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(PersonalTopics.class)).find(MongoKit.buildMatchDocument(personalTopics));
        MongoKit.appendProjection(query,personalTopics.getPager());
        return mot.getConverter().read(PersonalTopics.class,query.first());
    }

    public void updatePersonalTopics(PersonalTopics personalTopics) {
        personalTopics.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(personalTopics.getId())), MongoKit.update(personalTopics,false),PersonalTopics.class);
    }
    public void updatePersonalTopicsForAll(PersonalTopics personalTopics) {
        personalTopics.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(personalTopics.getId())), MongoKit.update(personalTopics,true),PersonalTopics.class);
    }
    public void deletePersonalTopics(String id) {
        mot.remove(query(where("id").is(id)),PersonalTopics.class);
    }
    public void deletePersonalTopicsByCondition(PersonalTopics personalTopics) {
        mot.getCollection(mot.getCollectionName(PersonalTopics.class)).deleteMany(MongoKit.buildMatchDocument(personalTopics));
    }
    public void batchSavePersonalTopics(List<PersonalTopics> personalTopicss){
        personalTopicss.forEach(personalTopics -> personalTopics.setId(sequenceId.nextId()));
        mot.insertAll(personalTopicss);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    /**
     * 将校本、平台题库的题目复制到我的题库
     * @param personalTopics
     */
    public void copyTopicToPersonalTopics(PersonalTopics personalTopics) {
        //先判断这个老师是否已经存在该题目了
        PersonalTopics temp = new PersonalTopics();
        if(Constant.TOPIC_CREATEBY.SCHOOLBASE.equals(personalTopics.getCreateBy())){
            temp.setOriginalId(personalTopics.getId());
        }else {
            temp.setOriginalId(personalTopics.getOriginalId());
        }
        temp.setSchoolId(personalTopics.getSchoolId());
        temp.setTeacherId(personalTopics.getTeacherId());
        if(this.findPersonalTopicsCountByCondition(temp)<=0){
            if(Constant.TOPIC_CREATEBY.SCHOOLBASE.equals(personalTopics.getCreateBy()))
                personalTopics.setOriginalId(personalTopics.getId());
            personalTopics.setId(sequenceId.nextId());
            //原先不存在,则进行添加题目
            mot.insert(personalTopics);
        }
    }
}
