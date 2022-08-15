package com.yice.edu.cn.jw.service.qusBankResource;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.common.util.WeekUtils.DateZoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolQusBankService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @CreateCache(name = "copySchoolQusBankLock")
    private Cache<String,Long> lock;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolQusBank findSchoolQusBankById(String id) {
        return mot.findById(id,SchoolQusBank.class);
    }
    public void saveSchoolQusBank(SchoolQusBank schoolQusBank) {
        schoolQusBank.setCreateTime(DateUtil.now());
        schoolQusBank.setId(sequenceId.nextId());
        schoolQusBank.setCreateBy(Constant.TOPIC_CREATEBY.SCHOOLBASE);
        mot.insert(schoolQusBank);
    }
    public List<SchoolQusBank> findSchoolQusBankListByCondition(SchoolQusBank schoolQusBank) {
        //知识点查询和章节查询做in处理
        Document match = new Document();
        if(schoolQusBank.getTypeIds()!=null && schoolQusBank.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(schoolQusBank.getTypeIds())));
            schoolQusBank.setTypeIds(null);
        }
        if(schoolQusBank.getKnowledges()!=null && schoolQusBank.getKnowledges().size()>0){
            match.append("knowledges._id",new Document("$in",schoolQusBank.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            schoolQusBank.setKnowledges(null);
        }
        if(schoolQusBank.getCategories()!=null && schoolQusBank.getCategories().size()>0){
            match.append("categories",new Document("$in",schoolQusBank.getCategories()));
            schoolQusBank.setCategories(null);
        }
        if(schoolQusBank.getTopicClass()!=null && schoolQusBank.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(schoolQusBank.getTopicClass())));
            schoolQusBank.setTopicClass(null);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(schoolQusBank.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(schoolQusBank.getDifficult().split(","))));
            schoolQusBank.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(schoolQusBank.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(schoolQusBank.getQuestionItemId().split(","))));
            schoolQusBank.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(schoolQusBank));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolQusBank.class)).find(match);
        Pager pager = schoolQusBank.getPager();
        pager.setExcludes(Arrays.asList("answer","analysis","child.answer","child.analysis"));
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolQusBank> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolQusBank.class, document)));
        return list;
    }
    public long findSchoolQusBankCountByCondition(SchoolQusBank schoolQusBank) {
        //知识点查询和章节查询做in处理
        Document match = new Document();
        if(schoolQusBank.getTypeIds()!=null && schoolQusBank.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(schoolQusBank.getTypeIds())));
            schoolQusBank.setTypeIds(null);
        }
        if(schoolQusBank.getKnowledges()!=null && schoolQusBank.getKnowledges().size()>0){
            match.append("knowledges._id",new Document("$in",schoolQusBank.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            schoolQusBank.setKnowledges(null);
        }
        if(schoolQusBank.getCategories()!=null && schoolQusBank.getCategories().size()>0){
            match.append("categories",new Document("$in",schoolQusBank.getCategories()));
            schoolQusBank.setCategories(null);
        }
        if(schoolQusBank.getTopicClass()!=null && schoolQusBank.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(schoolQusBank.getTopicClass())));
            schoolQusBank.setTopicClass(null);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(schoolQusBank.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(schoolQusBank.getDifficult().split(","))));
            schoolQusBank.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(schoolQusBank.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(schoolQusBank.getQuestionItemId().split(","))));
            schoolQusBank.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(schoolQusBank));
        return mot.getCollection(mot.getCollectionName(SchoolQusBank.class)).countDocuments(match);
    }
    public SchoolQusBank findOneSchoolQusBankByCondition(SchoolQusBank schoolQusBank) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolQusBank.class)).find(MongoKit.buildMatchDocument(schoolQusBank));
        MongoKit.appendProjection(query,schoolQusBank.getPager());
        return mot.getConverter().read(SchoolQusBank.class,query.first());
    }

    public void updateSchoolQusBank(SchoolQusBank schoolQusBank) {
        schoolQusBank.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(schoolQusBank.getId())), MongoKit.update(schoolQusBank,false),SchoolQusBank.class);
    }
    public void updateSchoolQusBankForAll(SchoolQusBank schoolQusBank) {
        schoolQusBank.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(schoolQusBank.getId())), MongoKit.update(schoolQusBank,true),SchoolQusBank.class);
    }
    public void deleteSchoolQusBank(String id) {
        mot.remove(query(where("id").is(id)),SchoolQusBank.class);
    }
    public void deleteSchoolQusBankByCondition(SchoolQusBank schoolQusBank) {
        mot.getCollection(mot.getCollectionName(SchoolQusBank.class)).deleteMany(MongoKit.buildMatchDocument(schoolQusBank));
    }
    public void batchSaveSchoolQusBank(List<SchoolQusBank> schoolQusBanks){
        schoolQusBanks.forEach(schoolQusBank -> schoolQusBank.setId(sequenceId.nextId()));
        mot.insertAll(schoolQusBanks);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<JySchoolResourcesByDay> findSchoolQusNumByCreateTimeZone(SchoolQusBank schoolQusBank){
        schoolQusBank.getPager().setRangeField("createTime");
        schoolQusBank.getPager().setPaging(false);
        Object[] rangeArray = schoolQusBank.getPager().getRangeArray();
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolQusBank.class)).find(MongoKit.buildMatchDocument(schoolQusBank));
        Pager pager = schoolQusBank.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolQusBank> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolQusBank.class, document)));
        DateZoneUtil dateCal = new DateZoneUtil();
        dateCal.countDays(DateUtil.parseDate((String)rangeArray[0]), DateUtil.parseDate((String)rangeArray[1]));
        List<Date> lastWeeklist = dateCal.getDayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<JySchoolResourcesByDay> jySchoolResourcesByDayList = new ArrayList<>();
        for (Date date : lastWeeklist) {
            String format = dateFormat.format(date);
            JySchoolResourcesByDay data = new JySchoolResourcesByDay();
            data.setTime(format);
            List<SchoolQusBank> todayQus = list.stream().filter(schoolQusBank1 -> schoolQusBank1.getCreateTime().substring(0, 10).equals(format)).collect(Collectors.toList());
            int todayQusNum = 0;
            if (todayQus!=null){
                todayQusNum = todayQus.size();
            }
            data.setCnt(todayQusNum);
            jySchoolResourcesByDayList.add(data);
        }
        return jySchoolResourcesByDayList;
    }

    /**
     * 将平台题库的题目复制到校本题库
     * @param schoolQusBank
     */
    public void copyTopicToSchoolQusBank(SchoolQusBank schoolQusBank) {
        //添加锁以防同校同时有人一起添加同道题目到校本题库 避免重复插入问题
        lock.tryLockAndRun(schoolQusBank.getId()+"-"+schoolQusBank.getSchoolId(), 10, TimeUnit.SECONDS, () -> {
            //先判断这个学校是否已经存在该题目了
            SchoolQusBank temp = new SchoolQusBank();
            temp.setOriginalId(schoolQusBank.getId());
            temp.setSchoolId(schoolQusBank.getSchoolId());
            if(this.findSchoolQusBankCountByCondition(temp)<=0){
                //将原有id设置成平台题目的id
                schoolQusBank.setOriginalId(schoolQusBank.getId());
                schoolQusBank.setId(sequenceId.nextId());
                //原先不存在,则进行添加题目
                mot.insert(schoolQusBank);
            }
        });
    }
}
