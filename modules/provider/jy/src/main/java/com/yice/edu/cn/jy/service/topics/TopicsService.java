package com.yice.edu.cn.jy.service.topics;

import cn.hutool.core.bean.BeanUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.SearchParam;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.jy.dao.knowledgePoint.IKnowledgePointDao;
import com.yice.edu.cn.jy.feignClient.qusBankResource.PersonalTopicsFeign;
import com.yice.edu.cn.jy.feignClient.qusBankResource.SchoolQusBankFeign;
import com.yice.edu.cn.jy.service.titleQuota.HistoryTeacherAesService;
import com.yice.edu.cn.jy.source21.service.Source21Service;
import com.yice.edu.cn.jy.source21.util.Log;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class TopicsService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IKnowledgePointDao knowledgePointDao;
    @Autowired
    private SchoolQusBankFeign schoolQusBankFeign;
    @Autowired
    private PersonalTopicsFeign personalTopicsFeign;
    @Autowired
    private HistoryTeacherAesService historyTeacherAesService;

    @Autowired
    private Source21Service source21Service;

    @CreateCache(name = "school_find_topic_")
    private Cache<String,Object> lock;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Cached(name = "yc_topic_",key = "#id")
    public Topics findTopicsById(String id) {
        return mot.findById(id,Topics.class);
    }
    public void saveTopics(Topics topics) {
        topics.setCreateTime(DateUtil.now());
        String index = sequenceId.nextId();
        topics.setId(Optional.ofNullable(topics.getId()).orElse(index));
        mot.insert(topics);
        //?????????????????????
        this.setKnowledgeTopicsCount(topics.getKnowledges().stream().map(KnowledgePoint::getId).toArray(String[]::new),1);
    }
    public List<Topics> findTopicsListByCondition(Topics topics) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Topics.class)).find(MongoKit.buildMatchDocument(topics));
        Pager pager = topics.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<Topics> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(Topics.class, document)));
        return list;
    }
    public long findTopicsCountByCondition(Topics topics) {
        return mot.getCollection(mot.getCollectionName(Topics.class)).countDocuments(MongoKit.buildMatchDocument(topics));
    }
    public Topics findOneTopicsByCondition(Topics topics) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Topics.class)).find(MongoKit.buildMatchDocument(topics));
        MongoKit.appendProjection(query,topics.getPager());
        return mot.getConverter().read(Topics.class,query.first());
    }
    @CacheInvalidate(name = "yc_topic_",key = "#topics.id")
    public void updateTopics(Topics topics) {
        topics.setUpdateTime(DateUtil.now());
        //????????????????????? ???????????????????????????
        Topics old = this.findTopicsById(topics.getId());
        mot.updateFirst(query(where("id").is(topics.getId())), MongoKit.update(topics,false),Topics.class);
        //???????????????????????????
        this.setKnowledgeTopicsCount(topics.getKnowledges().stream().filter(n->!old.getKnowledges().stream().anyMatch(o->o.getId().equals(n.getId()))).map(KnowledgePoint::getId).toArray(String[]::new),1);
        this.setKnowledgeTopicsCount(old.getKnowledges().stream().filter(n->!topics.getKnowledges().stream().anyMatch(o->o.getId().equals(n.getId()))).map(KnowledgePoint::getId).toArray(String[]::new),-1);
    }
    @CacheInvalidate(name = "yc_topic_",key = "#topics.id")
    public void updateTopicsForAll(Topics topics) {
        topics.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(topics.getId())), MongoKit.update(topics,true),Topics.class);
    }
    @CacheInvalidate(name = "yc_topic_",key = "#id")
    public void deleteTopics(String id) {
        //????????????????????? ???????????????????????????
        Topics topics = this.findTopicsById(id);
        if(topics == null)
            return ;//???????????????????????? ??????????????????
        mot.remove(query(where("id").is(id)),Topics.class);
        //???????????????????????????
        this.setKnowledgeTopicsCount(topics.getKnowledges().stream().map(KnowledgePoint::getId).toArray(String[]::new),-1);
    }
    @CacheInvalidate(name = "yc_topic_",key = "#topics.id")
    public void deleteTopicsByCondition(Topics topics) {
        mot.getCollection(mot.getCollectionName(Topics.class)).deleteMany(MongoKit.buildMatchDocument(topics));
    }
    public void batchSaveTopics(List<Topics> topicss){
        topicss.forEach(topics -> topics.setId(sequenceId.nextId()));
        mot.insertAll(topicss);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<Topics> findTopicsListByCondition4Muti(Topics temp) {
        final Document match = new Document();
        Topics topics = new Topics();
        BeanUtil.copyProperties(temp,topics);
        if(topics.getTypeIds()!=null && topics.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(topics.getTypeIds())));
            topics.setTypeIds(null);
        }
        if(topics.getKnowledges()!=null && !topics.getKnowledges().isEmpty()){
            match.append("knowledges._id",new Document("$in",topics.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            topics.setKnowledges(null);
        }
        if(topics.getCategories()!=null && !topics.getCategories().isEmpty()){
            match.append("categories",new Document("$in",topics.getCategories()));
            topics.setCategories(null);
        }
        if(topics.getTopicClass()!=null && topics.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(topics.getTopicClass())));
            topics.setTopicClass(null);
        }
        if(StringUtils.isNotEmpty(topics.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(topics.getDifficult().split(","))));
            topics.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(topics.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(topics.getQuestionItemId().split(","))));
            topics.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(topics));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(Topics.class)).find(match);
        Pager pager = topics.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        pager.setExcludes(Arrays.asList("answer","analysis","child.answer","child.analysis"));
        MongoKit.appendProjection(query,pager);
        List<Topics> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(Topics.class, document)));
        return list;
    }

    public long findTopicsCountByCondition4Muti(Topics temp) {
        Document match = new Document();
        Topics topics = new Topics();
        BeanUtil.copyProperties(temp,topics);
        if(topics.getTypeIds()!=null && topics.getTypeIds().length>0){
            match.append("typeId",new Document("$in", Arrays.asList(topics.getTypeIds())));
            topics.setTypeIds(null);
        }
        if(topics.getKnowledges()!=null && topics.getKnowledges().size()>0){
            match.append("knowledges._id",new Document("$in",topics.getKnowledges().stream().map(KnowledgePoint::getId).collect(Collectors.toList())));
            topics.setKnowledges(null);
        }
        if(topics.getCategories()!=null && topics.getCategories().size()>0){
            match.append("categories",new Document("$in",topics.getCategories()));
            topics.setCategories(null);
        }
        if(topics.getTopicClass()!=null && topics.getTopicClass().length>0){
            match.append("topicClass",new Document("$in",Arrays.asList(topics.getTopicClass())));
            topics.setTopicClass(null);
        }
        if(StringUtils.isNotEmpty(topics.getDifficult())){
            match.append("difficult",new Document("$in",Arrays.asList(topics.getDifficult().split(","))));
            topics.setDifficult(null);
        }
        if(StringUtils.isNotEmpty(topics.getQuestionItemId())){
            match.append("questionItemId",new Document("$in",Arrays.asList(topics.getQuestionItemId().split(","))));
            topics.setQuestionItemId(null);
        }
        match.putAll(MongoKit.buildMatchDocument(topics));
        return mot.getCollection(mot.getCollectionName(Topics.class)).countDocuments(match);
    }
    private void setKnowledgeTopicsCount(String[] ids,int count){
        if(ids.length>0)
            knowledgePointDao.batchSetKnowledgeTopicsCount(ids,count);
    }

    public void updateTopicsKnowledge(KnowledgePoint knowledgePoint) {
        Query q = query(where("knowledges.id").is(knowledgePoint.getId()));
        Update u = Update.update("knowledges.$.name", knowledgePoint.getName());
        mot.updateMulti(q, u, Topics.class);
    }

    /**
     * ??????????????????????????????????????????
     * ???????????????????????????????????????
     * ??????????????????
     * @param topicParam
     * @return
     */
    public Object findTopicDetail(TopicParam topicParam) {
        if(Constant.TOPIC_SOURCE.WDTK.equals(topicParam.getSource())||Constant.TOPIC_SOURCE.XBTK.equals(topicParam.getSource())){
            //????????????????????????????????????????????????
            return getTopicsFromPlatForm(topicParam);
        }else{
            //???????????? ????????????????????????
            HistoryTeacherAes isExist = new HistoryTeacherAes();
            isExist.setTopicId(topicParam.getId());
            isExist.setSchoolId(topicParam.getSchoolId());
            historyTeacherAesService.findIsExist(isExist);
            if(isExist.getCode().equals("200")){//??????????????????
                HistoryTeacherAes isPass = new HistoryTeacherAes();
                isPass.setTeacherId(topicParam.getTeacherId());
                isPass.setTopicId(topicParam.getId());
                isPass.setSchoolId(topicParam.getSchoolId());
                historyTeacherAesService.findIsDownload(isPass);
                //?????????????????????
                if("200".equals(isPass.getCode())){
                    final Object topics = getTopicsFromPlatForm(topicParam);
                    //???????????? ?????????????????????????????????
                    lock.tryLockAndRun(topicParam.getSchoolId()+topicParam.getId(),100, TimeUnit.SECONDS, () -> {
                        //??????????????? ???????????????????????????????????????????????????
                        historyTeacherAesService.findIsExist(isExist);
                        if(isExist.getCode().equals("200")){
                            System.out.println("???????????????????????????");
                            //?????????????????????
                            SchoolQusBank schoolQusBank = new SchoolQusBank();
                            BeanUtil.copyProperties(topics,schoolQusBank);
                            schoolQusBank.setCreateBy(Constant.TOPIC_CREATEBY.YCJD);
                            schoolQusBank.setSchoolId(topicParam.getSchoolId());
                            schoolQusBankFeign.copyTopicToSchoolQusBank(schoolQusBank);
                            //????????????
                            System.out.println("??????????????????");
                            historyTeacherAesService.findByTeacherIdVist(isPass);
                        }
                    });
                    return topics;
                }else
                    return new Topics().setErrorCode("204");//????????????
            }else{//???????????????
                return getTopicsFromPlatForm(topicParam);
            }
        }
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     * @return
     */
    private Object getTopicsFromPlatForm(TopicParam topicParam){
        //???????????????????????????
        if(Constant.TOPIC_SOURCE.WDTK.equals(topicParam.getSource())){
            return personalTopicsFeign.findPersonalTopicsById(topicParam.getId());
        }else if(Constant.TOPIC_SOURCE.XBTK.equals(topicParam.getSource())){
            return schoolQusBankFeign.findSchoolQusBankById(topicParam.getId());
        }else{
            Topics topics = this.findTopicsById(topicParam.getId());
            if(topics==null){
                if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(topicParam.getSource())){
                    //21????????????
                    topics = source21Service.getQuestionDetail(topicParam.getId());
                }
            }
            return topics;
        }
    }
    /**
     * ??????????????????????????????
     * ??????????????????????????????????????????
     */
    public ResponseJson findTopicList(SearchParam searchParam) {
        if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(searchParam.getPlatform())){
            //????????????
            //?????????21???????????????
            searchParam.setSubjectId(Integer.parseInt(this.getSubjectToMap(String.valueOf(searchParam.getSubjectId()))));
            //baseType ????????????
            if(StringUtils.isNotEmpty(searchParam.getBaseType())){
                String[] types = searchParam.getBaseType().split(",");
                searchParam.setBaseType(Arrays.stream(types).map(s->get21BaseType(s)).collect(Collectors.joining(",")));
            }
            APIResult<Question> questionAPIResult = source21Service.getQuestions(searchParam);
            List<Question> questions = questionAPIResult.getData();
            questions.forEach(question ->
                    question.setStem(question.getStem().replaceAll("\\{#blank#\\}\\d+\\{#/blank#\\}","( )"))
            );
            return new ResponseJson(questions, Optional.ofNullable(Optional.ofNullable(questionAPIResult.getPage()).orElse(new APIResult.Page()).getTotalCount()).orElse(0));
        }else{
            //?????? ?????? ????????????
            Topics topics = new Topics();
            topics.setPager(searchParam.getPager().setLike("content"));
            if(StringUtils.isNotEmpty(searchParam.getBaseType())){
                String[] types = searchParam.getBaseType().split(",");
                topics.setTypeIds(Arrays.stream(types).map(s->Integer.parseInt(s)).toArray(Integer[]::new));
            }
            topics.setContent(searchParam.getKeyword());
            if(searchParam.getChapterId()!=null&&searchParam.getChapterId().length()>0)
                topics.setCategories(Arrays.asList(searchParam.getChapterId().split(",")));
            if(searchParam.getKnowledgeId()!=null&&searchParam.getKnowledgeId().length()>0)
                topics.setKnowledges(Arrays.stream(searchParam.getKnowledgeId().split(",")).map(s->{
                    KnowledgePoint knowledgePoint = new KnowledgePoint();
                    knowledgePoint.setId(s);
                    return knowledgePoint;
                }).collect(Collectors.toList()));
            Optional.ofNullable(searchParam.getType()).ifPresent(s->topics.setQuestionItemId(String.valueOf(s)));
            Optional.ofNullable(searchParam.getDifficult()).ifPresent(s->topics.setDifficult(String.valueOf(s)));
            Optional.ofNullable(searchParam.getExamType()).ifPresent(s->topics.setTopicClass(s.split(",")));
            if(Constant.TOPIC_SOURCE.XBTK.equals(searchParam.getPlatform())){
                SchoolQusBank schoolQusBank = new SchoolQusBank();
                BeanUtil.copyProperties(topics,schoolQusBank);
                schoolQusBank.setSchoolId(searchParam.getSchoolId());
                return new ResponseJson(schoolQusBankFeign.findSchoolQusBankListByCondition(schoolQusBank),schoolQusBankFeign.findSchoolQusBankCountByCondition(schoolQusBank));
            }else if(Constant.TOPIC_SOURCE.WDTK.equals(searchParam.getPlatform())){
                PersonalTopics personalTopics = new PersonalTopics();
                BeanUtil.copyProperties(topics,personalTopics);
                personalTopics.setSchoolId(searchParam.getSchoolId());
                personalTopics.setTeacherId(searchParam.getTeacherId());
                return new ResponseJson(personalTopicsFeign.findPersonalTopicsListByCondition(personalTopics),personalTopicsFeign.findPersonalTopicsCountByCondition(personalTopics));
            }else {
                Topics ct = new Topics();
                BeanUtil.copyProperties(topics,ct);
                return new ResponseJson(this.findTopicsListByCondition4Muti(topics),this.findTopicsCountByCondition4Muti(ct));
            }
        }
    }
    /**
     * ???????????????21????????????
     * @return
     */
    public static String getSubjectToMap(String subjectId){
        final Map<String,String> mine221 = new HashMap<>();
        //??????
        mine221.put("130","2");//??????
        mine221.put("131","3");//??????
        mine221.put("132","4");//??????
        mine221.put("134","5");//??????
        mine221.put("133","9");//???????????????
        //??????
        mine221.put("150","2");//??????
        mine221.put("151","3");//??????
        mine221.put("152","4");//??????
        mine221.put("153","8");//??????
        mine221.put("154","10");//??????
        mine221.put("155","11");//??????
        mine221.put("156","6");//??????
        mine221.put("157","7");//??????
        mine221.put("158","9");//????????????
        mine221.put("162","14");//????????????
        mine221.put("166","5");//??????
        mine221.put("167","20");//???????????????
        mine221.put("168","21");//????????????
        //??????
        mine221.put("180","2");//??????
        mine221.put("181","3");//??????
        mine221.put("182","4");//??????
        mine221.put("183","8");//??????
        mine221.put("184","10");//??????
        mine221.put("185","11");//??????
        mine221.put("186","6");//??????
        mine221.put("187","7");//??????
        mine221.put("188","9");//??????
        mine221.put("191","14");//????????????
        mine221.put("18202","20");//???????????????
        mine221.put("18203","21");//????????????
        return mine221.get(subjectId);
    }
    /**
     * ???????????????21??????????????????
     * @return
     */
    private String get21BaseType(String type){
        //[ 1 = '?????????', 2 = '?????????', 3 = '?????????', 4 = '?????????', 5 = '?????????', 6 = '???????????????', 7 = '?????????' ]
        final Map<String,String> baseType = new HashMap<>();
        baseType.put("2","1");
        baseType.put("3","2");
        baseType.put("1","3");
        baseType.put("4","4");
        baseType.put("5","5");
        baseType.put("9","6");
        baseType.put("10","7");
        return baseType.get(type);
    }

    /**
     * ?????? ?????? ????????? ???????????????????????????
     * ?????????1????????? 2?????????
     * ?????????3?????????
     * @param topicParam
     * @return
     */
    public Long findTopicUseCount(TopicParam topicParam) {
        Long count;
        //???????????? 3?????????
        String pt = DateUtil.offsetMonth(DateUtil.date(),-3).toDateStr();
        //?????? ??????????????? ??????????????????
        //???????????? ???????????????????????????
        Document examMatch = new Document("$match",new Document("type",Constant.ExamType.onLine_exam)
        .append("finished",true).append("schoolId",topicParam.getSchoolId())
        .append("examTime",new Document("$gte",pt)));
        //????????????????????????id?????????
        Document courseUnwind = new Document("$unwind","$courses");
        Document paperExists = new Document("$match",new Document("courses.paperId",new Document("$exists",true)));
        Document paperIdProject = new Document("$project",new Document("paperId","$courses.paperId"));
        //????????????????????? ??????????????????????????????
        Document paperLookUp = new Document("$lookup",new Document("from","paper")
                .append("localField","paperId").append("foreignField","_id").append("as","paper"));
        //???????????????????????????????????????????????????
        Document paperUnwind = new Document("$unwind","$paper");
        Document paperMatch = new Document("$match",new Document("paper.createUserId",topicParam.getTeacherId()));
        Document paperProject = new Document("$project",new Document("paperId",1));
        //?????????????????????????????????
        Document topicLookUp = new Document("$lookup",new Document("from","paperQuestion")
                .append("localField","paperId").append("foreignField","testPaperId").append("as","topics"));
        //????????????id ????????????
        Document topicUnwind = new Document("$unwind","$topics");
        Document topicMatch = new Document("$match",new Document("topics.topicsList._id",topicParam.getId()));
        Document topicCount = new Document("$count","usageAmount");
        Object result = mot.getCollection(mot.getCollectionName(SchoolExam.class)).aggregate(Arrays.asList(examMatch,courseUnwind,paperExists,paperIdProject,paperLookUp,paperUnwind,paperMatch,paperProject,topicLookUp,topicUnwind,topicMatch,topicCount)).first();
        count = Long.parseLong(String.valueOf(((Document) Optional.ofNullable(result).orElse(new Document("usageAmount",0))).get("usageAmount")));
        if(count<=0){
            //????????????????????? ???????????????????????????????????????
            topicMatch = new Document("$match",new Document("type",Constant.HOMEWORK.HOMEWORK_TYPE_ONLINE)
            .append("publishStatus",Constant.HOMEWORK.PUBLISH_ON)
            .append("teacherId",topicParam.getTeacherId())
            .append("publishTime",new Document("$gte",pt))
            .append("topicArr._id",topicParam.getId()));
            result = mot.getCollection(mot.getCollectionName(Homework.class)).aggregate(Arrays.asList(topicMatch,topicCount)).first();
            count = Long.parseLong(String.valueOf(((Document) Optional.ofNullable(result).orElse(new Document("usageAmount",0))).get("usageAmount")));
        }
        return count;
    }
}
