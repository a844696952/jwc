package com.yice.edu.cn.jw.service.exam.examManage;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StuScoreService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    SchoolExamService schoolExamService;


    public StuScore findStuScoreById(String id) {
        return mot.findById(id,StuScore.class);
    }
    public void saveStuScore(StuScore stuScore) {
        stuScore.setId(sequenceId.nextId());
        mot.insert(stuScore);
    }
    public List<StuScore> findStuScoreListByCondition(StuScore stuScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(stuScore, stuScore.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(stuScore.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(StuScore.class,operations),StuScore.class).getMappedResults();

    }
    public List<StuScore> findStuScoreListByCondition1(StuScore stuScore) {
        Criteria c = new Criteria("schoolExamId").is(stuScore.getSchoolExamId());
        c.and("course.id").is(stuScore.getCourse().getId());
        if(stuScore.getClazz()!=null&& StringUtils.isNotEmpty(stuScore.getClazz().getId()))
            c.and("clazz.id").is(stuScore.getClazz().getId());
        if(StringUtils.isNotEmpty(stuScore.getNameOrCode()))
            c.orOperator(Criteria.where("student.name").regex(stuScore.getNameOrCode()),Criteria.where("student.studentNo").regex(stuScore.getNameOrCode()));
        Query query = query(c);
        if (stuScore.getPager() != null && stuScore.getPager().getPaging()) {
            query.with(stuScore.getPager());
        }
        return mot.find(query, StuScore.class);

    }
    public long findStuScoreCountByCondition(StuScore stuScore) {
        Criteria criteria = MongoKit.buildCriteria(stuScore, stuScore.getPager());
        return mot.count(query(criteria), StuScore.class);
    }
    public long findStuScoreCountByCondition1(StuScore stuScore) {
        Criteria c = new Criteria("schoolExamId").is(stuScore.getSchoolExamId());
        c.and("course.id").is(stuScore.getCourse().getId());
        if(stuScore.getClazz()!=null&& StringUtils.isNotEmpty(stuScore.getClazz().getId()))
            c.and("clazz.id").is(stuScore.getClazz().getId());
        if(StringUtils.isNotEmpty(stuScore.getNameOrCode()))
            c.orOperator(Criteria.where("student.name").regex(stuScore.getNameOrCode()),Criteria.where("student.studentNo").regex(stuScore.getNameOrCode()));
        Query query = query(c);
        if (stuScore.getPager() != null && stuScore.getPager().getPaging()) {
            query.with(stuScore.getPager());
        }
        return mot.count(query(c), StuScore.class);
    }
    public StuScore findOneStuScoreByCondition(StuScore stuScore) {
        Example<StuScore> example = Example.of(stuScore, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = stuScore.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(StuScore.class,operations),StuScore.class).getUniqueMappedResult();
    }

    public void updateStuScore(StuScore stuScore) {
        mot.updateFirst(query(where("id").is(stuScore.getId())), MongoKit.update(stuScore),StuScore.class);
    }
    public void deleteStuScore(String id) {
        mot.remove(query(where("id").is(id)),StuScore.class);
    }
    public void deleteStuScoreByschoolExamId(String id) {
        mot.remove(query(where("schoolExamId").is(id)),StuScore.class);
    }
    public void deleteStuScoreByCondition(StuScore stuScore) {
        Example<StuScore> example = Example.of(stuScore, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, StuScore.class);
    }
    public void batchSaveStuScore(List<StuScore> stuScores){
        stuScores.forEach(stuScore -> stuScore.setId(sequenceId.nextId()));
        mot.insertAll(stuScores);
    }
    public Map<String,Object> batchSaveStuScore1(List<StuScore> stuScoreList) {
        Map<String,Object>map=new HashMap<>();
        stuScoreList.forEach(stuScore -> stuScore.setId(sequenceId.nextId()));
        mot.insertAll(stuScoreList);
        return map;
    }


    public List<Map<String,Object>> findStuScoreByschoolExamId(String id) {
        List<Bson> operas = new ArrayList<>();
        Document matchObj = new Document("schoolExamId", id);
        Document match = new Document("$match", matchObj);//筛选条件
        Document groupObj=new Document("_id",new Document("clazz","$clazz").append("student","$student"));
        Document projectObj=new Document("number","$_id.clazz.number").append("name","$_id.student.name").append("seatNumber","$_id.student.seatNumber");
        Document sort = new Document("$sort", new Document("seatNumber", 1));
        MongoCollection<Document> collection = mot.getCollection("stuScore");
        AggregateIterable<Document> result = null;
        SchoolExam schoolExam = schoolExamService.findSchoolExamById(id);
        for (int i = 0; i < schoolExam.getCourses().size(); i++) {
            groupObj.append(schoolExam.getCourses().get(i).getId(),new Document("$max",new Document("$cond", Arrays.asList(new Document("$eq",Arrays.asList("$course._id",schoolExam.getCourses().get(i).getId())),"$score",-2))));
            projectObj.append(schoolExam.getCourses().get(i).getId(),"$"+schoolExam.getCourses().get(i).getId());
        }
        Document group = new Document("$group",groupObj);
        Document project = new Document("$project",projectObj.append("_id",false));
        operas.addAll(Arrays.asList(match,group,project,sort));
        result = collection.aggregate(operas);
        List<Map<String, Object>> list = new ArrayList<>();
        MongoCursor<Document> mdi = result.iterator();
        while (mdi.hasNext()) {
            list.add(mdi.next());
        }
       /* for (int i = 0; i < list.size(); i++) {
            Object number = list.get(i).get("number")+"班";
            list.get(i).put("number",number);
        }*/
        return list;
    }

    public List<StuScore> findStudentByScoreSection(StuScore stuScore){
        Criteria c = new Criteria("schoolExamId").is(stuScore.getSchoolExamId());
        c.and("course._id").is(stuScore.getCourse().getId());
        if(stuScore.getClazz()!=null && stuScore.getClazz().getId()!=null) {
        	c.and("clazz._id").is(stuScore.getClazz().getId());        	
        }
        Criteria criteria = Criteria.where("score").gte(stuScore.getMinVal());
        if(stuScore.getMaxVal()!=null) {
        	criteria.lt(stuScore.getMaxVal());
        }
        c.andOperator(criteria);
        Query query = query(c);
        if (stuScore.getPager() != null && stuScore.getPager().getPaging()) {
            query.with(stuScore.getPager());
        }
        return mot.find(query, StuScore.class);
    }

    public List<StuScore> findStuScoresForDownload(String schoolExamId, List<String> courseIds) {

        FindIterable<Document> documents = mot.getCollection(mot.getCollectionName(StuScore.class)).find(new Document("schoolExamId", schoolExamId).append("course._id", new Document("$in", courseIds)))
                .sort(new Document("clazz.number",1))
                .projection(Document.parse("{answerSheetDatas:0}"));
        List<StuScore> result = new ArrayList<>();
        MongoConverter converter = mot.getConverter();
        documents.forEach((Block<? super Document>) document -> result.add(converter.read(StuScore.class, document)));
        return result;
    }
}
