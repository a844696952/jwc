package com.yice.edu.cn.jy.service.homework;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.TopicType;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.jy.service.knowledge.JyKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ClassTestService {


    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JyKnowledgeService jyKnowledgeService;


    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public ClassTest findClassTestById(String id){
        return mongoTemplate.findById (id,ClassTest.class);
    }


    /**
     * 新增课堂检测
     * @param classTest
     * @return
     */
    @Transactional(rollbackFor =Exception.class)
    public boolean addCourseTestInfo(ClassTest classTest){
        if(Objects.nonNull (classTest)){
            classTest.setId (sequenceId.nextId ());
            //设置创建时间
            classTest.setCreateTime(DateUtil.now());
            List<TopicDetail> topicDetails = classTest.getTopicDetails ();
            ArrayList<String> errorIndex=new ArrayList<>();
            if(CollUtil.isNotEmpty (topicDetails)){
                topicDetails.forEach (x->{
                    x.setClassTestId (classTest.getId ());
                    x.setId (sequenceId.nextId ());
                    //设置创建时间
                    x.setCreateTime(DateUtil.now());
                    //设置学校id
                    x.setSchoolId(classTest.getSchoolId());
                    //设置易错题
                    if(classTest.getStudentCount() != 0 && (double)x.getErrorCount()+(double)x.getUnAnswered()/(double)(classTest.getStudentCount()) > 0.5){
                        x.setFallibility("1");
                    }else if(classTest.getStudentCount() == 0){
                        x.setFallibility("1");
                    }else {
                        x.setFallibility("0");
                    }
                    if(x.getFallibility().equals("1")){
                        errorIndex.add(x.getIndex().toString());
                    }
                    //设置提交时间
                    x.setCommitTime(DateUtil.now());
                    if(Constant.TOPIC_TYPE.PERSON_TOPIC == x.getTopicType()){
                        PersonalTopics personalTopics = findPersonTopicsById(x.getTopicId());
                        if(Objects.nonNull(personalTopics)){
                            String answer = "";
                            if(personalTopics.getTypeId() != TopicType.READING.getId()&&personalTopics.getTypeId() != TopicType.CLOZE.getId()){
                                //除了复合题和完型填空 其他的答案 可以常规处理
                                //复合题和完型题 要答案的话 根据需要自己去取
                                //复合题的答案 在每个小题里面;完型的答案是多个即整个数组
                                if(Optional.ofNullable(personalTopics.getAnswer()).isPresent()){
                                    answer = Arrays.stream(personalTopics.getAnswer()).collect(Collectors.joining());
                                }
                            }
                            setTopicInfo(x, Objects.nonNull(personalTopics), personalTopics.getContent(), personalTopics.getContentText(), personalTopics.getAnalysis(), answer,personalTopics.getTypeId());
                        }
                    }
                    if(Constant.TOPIC_TYPE.SCHOOL_TOPIC == x.getTopicType()){
                        SchoolQusBank schoolTopics = findSchoolTopicById(x.getTopicId());
                        if(Objects.nonNull(schoolTopics)){
                            //现在的题目答案是个数组 具体需求要怎么使用这个数组可以自己调整
                            String answer = "";
                            if(schoolTopics.getTypeId() != TopicType.READING.getId()&&schoolTopics.getTypeId() != TopicType.CLOZE.getId()){
                                //除了复合题和完型填空 其他的答案 可以常规处理
                                //复合题和完型题 要答案的话 根据需要自己去取
                                //复合题的答案 在每个小题里面;完型的答案是多个即整个数组
                                if(Optional.ofNullable(schoolTopics.getAnswer()).isPresent()){
                                    answer = Arrays.stream(schoolTopics.getAnswer()).collect(Collectors.joining());
                                }
                            }
                            setTopicInfo(x, Objects.nonNull(schoolTopics), schoolTopics.getContent(), schoolTopics.getContentText(), schoolTopics.getAnalysis(),answer,schoolTopics.getTypeId());
                        }
                    }
                });
                if(Objects.nonNull(errorIndex) && errorIndex.size()>0){
                    classTest.setFallibilityTopics(org.apache.commons.lang3.StringUtils.join(errorIndex,","));
                }
                mongoTemplate.insert (classTest,"classTest");
                mongoTemplate.insertAll(topicDetails);
            }
        }
        return true;
    }

    /**
     * 设置题目信息
     * @param x
     * @param b
     * @param content
     * @param contentText
     * @param analysis
     * @param answer
     */
    private void setTopicInfo(TopicDetail x, boolean b, String content, String contentText, String analysis, String answer,Integer typeId) {
        if (b) {
            x.setTopicContent(content);
            x.setTopicContentText(contentText);
            x.setAnalysis(analysis);
            x.setRightAnswer(answer);
            x.setTypeId(typeId);
        }
    }

    /**
     * 查询个人题库详情
     * @param topicId
     * @return
     */
    public PersonalTopics findPersonTopicsById(String topicId){
        if(StringUtils.isNotEmpty(topicId)){
            return mongoTemplate.findOne(query(where("id").is(topicId)), PersonalTopics.class);
        }
        return null;
    }

    /**
     * 获取校本题库
     * @param topicId
     * @return
     */
    public SchoolQusBank findSchoolTopicById(String topicId){
        if(StringUtils.isNotEmpty(topicId)){
            return mongoTemplate.findOne(query(where("id").is(topicId)),SchoolQusBank.class);
        }
        return null;
    }

    /**
     * 查询课堂检测列表
     * @param classTest
     * @return
     */
    public List<ClassTest> findClassTestByCondition(ClassTest classTest){
        Query query = getQuery(classTest);
        if(classTest.getPager()!=null&&classTest.getPager().getPaging()){
            query.with(classTest.getPager());
        }
        List<ClassTest> classTestList = mongoTemplate.find(query, ClassTest.class);
        if(CollUtil.isNotEmpty(classTestList)){
            List<String> collect = classTestList.stream().map(x -> x.getClassId()).collect(Collectors.toList());
            List<JwClasses> jwClassesById = jyKnowledgeService.findJwClassesById(collect);
            if(CollUtil.isNotEmpty(jwClassesById)){
                Map<String, JwClasses> classesMap = jwClassesById.stream().collect(Collectors.toMap(JwClasses::getId, y -> y));
                classTestList.forEach(x->{
                    if(Objects.nonNull(classesMap.get(x.getClassId()))){
                        x.setGradeId(classesMap.get(x.getClassId()).getGradeId());
                        x.setGradeName(classesMap.get(x.getClassId()).getGradeName());
                    }
                });
            }
        }
        return  classTestList;
    }

    /**
     * 获取Query对象
     * @param classTest
     * @return
     */
    private Query getQuery(ClassTest classTest) {
        List<Criteria> criteriaList = new ArrayList<>();
        if(StringUtils.isNotEmpty(classTest.getSchoolId())){
            criteriaList.add(Criteria.where("schoolId").is(classTest.getSchoolId()));
        }
        if(StringUtils.isNotEmpty(classTest.getTeacherId())){
            criteriaList.add(Criteria.where("teacherId").is(classTest.getTeacherId()));
        }
        if(StringUtils.isNotEmpty(classTest.getClassId())){
            criteriaList.add(Criteria.where("classId").is(classTest.getClassId()));
        }
        if(StringUtils.isNotEmpty(classTest.getGradeId())){
            criteriaList.add(Criteria.where("gradeId").is(classTest.getGradeId()));
        }
        if (StringUtils.isNotEmpty(classTest.getSearchKey())) {
            criteriaList.add(new Criteria().orOperator(Criteria.where("courseName").regex(classTest.getSearchKey()),Criteria.where("classWorkName").regex(classTest.getSearchKey())));
        }
        if (StringUtils.isNotEmpty(classTest.getBeginTime()) && StringUtils.isNotEmpty(classTest.getEndTime())) {
            criteriaList.add(Criteria.where("attendClassTime").gte(classTest.getBeginTime()).lte(classTest.getEndTime()));
        }
        return query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
    }

    /**
     * 查询总数
     * @param classTest
     * @return
     */
    public long findClassTestCount(ClassTest classTest){
        Query query = getQuery(classTest);
        return mongoTemplate.count(query,ClassTest.class);
    }


    /**
     * 通过Aggregation方式查询
     * @param classTest
     * @param searchKey
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<ClassTest> findListByCondition(ClassTest classTest,String searchKey,String beginTime,String endTime){
        List<AggregationOperation> operations = getAggregationOperations (classTest,searchKey,beginTime,endTime);
        MongoKit.sortPageInclude(classTest.getPager(),operations);
        List<ClassTest> classTestList = mongoTemplate.aggregate (Aggregation.newAggregation (ClassTest.class, operations), ClassTest.class).getMappedResults ();
        return classTestList;
    }

    /**
     * 查询习题详情列表
     * @param topicDetail
     * @return
     */
    public List<TopicDetail> findTopDetailListByCondition(TopicDetail topicDetail){
        Query query = getTopDetailQuery(topicDetail);
        if(Objects.nonNull(topicDetail.getPager()) && topicDetail.getPager().getPaging()){
            query.with(topicDetail.getPager());
        }
        List<TopicDetail> topicDetails = mongoTemplate.find(query, TopicDetail.class);
        return topicDetails;
    }

    /**
     * 查询习题详情列表总数
     * @param topicDetail
     * @return
     */
    public Long findTopDetailCountByCondition(TopicDetail topicDetail){
        Query query=getTopDetailQuery(topicDetail);
        long count = mongoTemplate.count(query, topicDetail.getClass());
        return count;
    }


    /**
     * 组装TopDetail查询条件
     * @param topicDetail
     * @return
     */
    private Query getTopDetailQuery(TopicDetail topicDetail) {
        List<Criteria> list=new ArrayList<>();
        if(StringUtils.isNotEmpty(topicDetail.getClassTestId())){
            list.add(Criteria.where("classTestId").is(topicDetail.getClassTestId()));
      }
        Query query=new Query();
        if(CollUtil.isNotEmpty(list)){
            query=new Query(new Criteria().andOperator(list.toArray(new Criteria[list.size()])));
        }
        return query;
    }


    /***
     *组合查询课堂检测的aggregation条件
     * @param classTest
     * @param searchKey
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<AggregationOperation> getAggregationOperations(ClassTest classTest,String searchKey,String beginTime,String endTime) {
        classTest.setEndTime(null);
        classTest.setBeginTime(null);
        classTest.setSearchKey(null);
        List<AggregationOperation> operations = new ArrayList<> ();
        Criteria criteria = MongoKit.buildCriteria (classTest, classTest.getPager ());
        if (StringUtils.isNotEmpty (beginTime) && StringUtils.isNotEmpty (endTime)) {
            Criteria attendClassTime = Criteria.where ("attendClassTime").gte (beginTime).lte (endTime);
            operations.add (Aggregation.match (attendClassTime));
        }
        if (StringUtils.isNotEmpty (searchKey)) {
            Example example = Example.of(classTest, UntypedExampleMatcher.matchingAll());
            Criteria searchQuery = new Criteria().alike(example).orOperator(new Criteria("courseName").regex(searchKey),new Criteria("classWorkName").regex(searchKey));
            operations.add (Aggregation.match (searchQuery));
        }
        operations.add (Aggregation.match (criteria));
        return operations;
    }

    /**
     * 查询课程信息
     * @param classTest
     * @return
     */
    public List<ClassTest> findClassTestByJoinTable(ClassTest classTest){
        List<AggregationOperation> list=new ArrayList<>();
        LookupOperation lookup = Aggregation.lookup("topicDetail", "_id", "classTestId", "topicDetails");
        if(StringUtils.isNotEmpty(classTest.getId())){
            list.add(  Aggregation.match(Criteria.where("id").is(classTest.getId())));
        }
        list.add(lookup);
        List<ClassTest> mappedResults = mongoTemplate.aggregate(Aggregation.newAggregation(ClassTest.class, list), ClassTest.class).getMappedResults();
        return mappedResults;
    }


    public void deleteClassTest(List<String> classIds) {
        ClassTest classTest = new ClassTest();
        Query query;
        Query query1;
        List<ClassTest> classTestList;
        List<TopicDetail> topicDetails;
        for (String classId : classIds) {
            classTest.setClassId(classId);
            query = getQuery(classTest);
            classTestList = mongoTemplate.find(query,ClassTest.class);
            if(CollUtil.isEmpty(classTestList)){
                continue;
            }
            for (ClassTest test : classTestList) {
                topicDetails = test.getTopicDetails();
                if (CollUtil.isEmpty(topicDetails)){
                    continue;
                }
                for (TopicDetail topicDetail : topicDetails) {
                    query1 = new Query(where("id").is(topicDetail.getId()));
                    mongoTemplate.remove(query1,TopicDetail.class);
                }
            }
            mongoTemplate.remove(query,ClassTest.class);
        }
    }
}
