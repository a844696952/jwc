package com.yice.edu.cn.jy.service.analysisClassScore;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;

@Service
public class AnalyseExamTopicStuService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseExamTopicStu findAnalyseExamTopicStuById(String id) {
        return mot.findById(id,AnalyseExamTopicStu.class);
    }
    public void saveAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu) {
        analyseExamTopicStu.setId(sequenceId.nextId());
        mot.insert(analyseExamTopicStu);
    }
    public List<AnalyseExamTopicStu> findAnalyseExamTopicStuListByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopicStu.class)).find(MongoKit.buildMatchDocument(analyseExamTopicStu));
        Pager pager = analyseExamTopicStu.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseExamTopicStu> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseExamTopicStu.class, document)));
        return list;
    }
    public long findAnalyseExamTopicStuCountByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        return mot.getCollection(mot.getCollectionName(AnalyseExamTopicStu.class)).count(MongoKit.buildMatchDocument(analyseExamTopicStu));
    }
    public AnalyseExamTopicStu findOneAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopicStu.class)).find(MongoKit.buildMatchDocument(analyseExamTopicStu));
       MongoKit.appendProjection(query,analyseExamTopicStu.getPager());
       return mot.getConverter().read(AnalyseExamTopicStu.class,query.first());
    }

    public void updateAnalyseExamTopicStu(AnalyseExamTopicStu analyseExamTopicStu) {
        mot.updateFirst(query(where("id").is(analyseExamTopicStu.getId())), MongoKit.update(analyseExamTopicStu),AnalyseExamTopicStu.class);
    }
    public void deleteAnalyseExamTopicStu(String id) {
        mot.remove(query(where("id").is(id)),AnalyseExamTopicStu.class);
    }
    public void deleteAnalyseExamTopicStuByCondition(AnalyseExamTopicStu analyseExamTopicStu) {
        mot.getCollection(mot.getCollectionName(AnalyseExamTopicStu.class)).deleteMany(MongoKit.buildMatchDocument(analyseExamTopicStu));
    }
    public void batchSaveAnalyseExamTopicStu(List<AnalyseExamTopicStu> analyseExamTopicStus){
        analyseExamTopicStus.forEach(analyseExamTopicStu -> analyseExamTopicStu.setId(sequenceId.nextId()));
        mot.insertAll(analyseExamTopicStus);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    
    /**
     * db.getCollection('stuScore').aggregate([{"$match":{schoolExamId:"2234408597820628992",missing:false}}
,{$project:{_id:1,schoolExamId:1,clazz:1,course:1,student:1,answerSheetItems:"$answerSheetDatas.answerSheetItems"}}
,{$unwind:"$answerSheetItems"}
,{$unwind:"$answerSheetItems"}
,{$group:{_id:{clazz:"$clazz",course:"$course",fullMarks:"$answerSheetItems.score"
         ,topicTypeName:"$answerSheetItems.typeName",topicNum:"$answerSheetItems.num",student:"$student",yourScore:"$answerSheetItems.yourScore"}}}
,{$project:{_id:0,clazzObj:"$_id.clazz",courseObj:"$_id.course",fullMarks:"$_id.fullMarks"
    ,topicTypeName:"$_id.topicTypeName",topicNum:"$_id.topicNum",student:"$_id.student",score:"$_id.yourScore"}}
])
     * @param examId
     */
    public List<AnalyseExamTopic> analyseStuTopic(SchoolExam schoolExam,List<AnalyseExamTopic> analyseExamTopicList) {
    	String examId = schoolExam.getId();
    	Document matchParams = new Document("schoolExamId",examId)
    			               .append("missing", false);
    	Document match = new Document("$match", matchParams);
    	
    	Document projectFirstParams = new Document("_id",1)
    			                 .append("schoolExamId", 1)
    			                 .append("clazz", 1)
    			                 .append("course", 1)
    			                 .append("student", 1)
    			                 .append("answerSheetItems","$answerSheetDatas.answerSheetItems");
    			                 
    	Document projectFirst = new Document("$project", projectFirstParams);
    	
    	Document unwindFirst = new Document("$unwind","$answerSheetItems");
    	Document unwindSecond = new Document("$unwind","$answerSheetItems");
    	
    	Document groupIdParams = new Document("clazz","$clazz")
    			                 .append("course","$course")
    			                 .append("fullMarks","$answerSheetItems.score")
     			                 .append("topicTypeName", "$answerSheetItems.typeName")
     			                 .append("topicNum", "$answerSheetItems.num")
     			                 .append("topicId", "$answerSheetItems.typeId")
     			                 .append("yourScore", "$answerSheetItems.yourScore")
     			                 .append("student", "$student");
    	
    	Document groupId = new Document("_id",groupIdParams);
    	
    	Document group = new Document("$group",groupId);
    
    	Document projectSecParams = new Document("_id",0)
    			                    .append("clazzObj","$_id.clazz")
    			                    .append("courseObj", "$_id.course")
    			                    .append("fullMarks", "$_id.fullMarks")
    			                    .append("topicTypeName","$_id.topicTypeName")
    			                    .append("topicId", "$_id.topicId")
    			                    .append("topicNum", "$_id.topicNum")
    			                    .append("student", "$_id.student")
    			                    .append("score", "$_id.yourScore");
    			                    
    	Document projectSecond = new Document("$project", projectSecParams);
    	
    	List<Bson> operas = new ArrayList<>();
    	operas.addAll(Arrays.asList(match,projectFirst,unwindFirst,unwindSecond,group,projectSecond));
    	
    	List<AnalyseExamTopicStu> analyseExamTopicStuList = new ArrayList<AnalyseExamTopicStu>();
    	
        MongoCollection<Document> collection = mot.getCollection("stuScore");
        AggregateIterable<Document> aggResult = collection.aggregate(operas);
        MongoCursor<Document> cursor =  aggResult.iterator();
        while(cursor.hasNext()) {
        	Document document = cursor.next();
        	AnalyseExamTopicStu analyseExamTopicStu = mot.getConverter().read(AnalyseExamTopicStu.class, document);	
        	analyseExamTopicStuList.add(analyseExamTopicStu);
        }
        //计算某个科目某个小题某个学生的得分率
        countRateByTopic(analyseExamTopicStuList,schoolExam,analyseExamTopicList);
        
//        Map<String,List<AnalyseExamTopicStu>> analyseExamTopicStuGroupByCourseIdMap = analyseExamTopicStuList.stream().collect(Collectors.groupingBy(ss->ss.getCourseObj().getId()));
//        //计算学生各区间得分率情况
//        analyseExamTopicList = analyseExamTopicList.stream().map(ss->{
//        	List<AnalyseExamTopicStu> analyseExamTopicStuCourseList =  analyseExamTopicStuGroupByCourseIdMap.get(ss.getCourseObj().getId());
//        	//[0~30]
//        	int firstRate=0;
//        	//(30~60%]
//        	int secondRate=0;
//        	//(60~80%]
//        	int thirdRate=0;
//        	//(80~100%]
//        	int fourthRate=0;
//        	for(AnalyseExamTopicStu s:analyseExamTopicStuCourseList){
//        		if(s.getTopicRate().compareTo(30.0)<=0) {
//        			firstRate++;
//        		}else if(s.getTopicRate().compareTo(30.0)>0 && s.getTopicRate().compareTo(60.0)<=0) {
//        			secondRate++;
//        		}else if(s.getTopicRate().compareTo(60.0)>0 && s.getTopicRate().compareTo(80.0)<=0){
//        			thirdRate++;
//        		}else if(s.getTopicRate().compareTo(80.0)>0 && s.getTopicRate().compareTo(100.0)<=0){
//        			fourthRate++;
//        		}
//        	};
//        	Integer[] StuScoreRateArr = {Integer.valueOf(firstRate),Integer.valueOf(secondRate),Integer.valueOf(thirdRate),Integer.valueOf(fourthRate)};
//        	ss.setStuScoreRate(StuScoreRateArr);
//        	return ss;
//        }).collect(Collectors.toList());
        
        mot.insertAll(analyseExamTopicStuList);
    	
        return analyseExamTopicList;
    }
    
    private void countRateByTopic(List<AnalyseExamTopicStu> analyseExamTopicStuList,SchoolExam schoolExam,List<AnalyseExamTopic> analyseExamTopicList) {
    	analyseExamTopicStuList.forEach(model ->{
    		AnalyseExamTopic analyseExamTopicModel = analyseExamTopicList.stream().filter(analyseExamTopicStream->
    			analyseExamTopicStream.getClazzObj().getId().equals(model.getClazzObj().getId()) && 
    					analyseExamTopicStream.getCourseObj().getId().equals(model.getCourseObj().getId()) &&
    					analyseExamTopicStream.getTopicNum().intValue()==model.getTopicNum().intValue()
    		).findFirst().get();
    		model.setClassAvgMarks(analyseExamTopicModel.getClassAvgMarks());
    		model.setExamId(schoolExam.getId());
    		model.setExamObj(schoolExam);
    		model.setId(sequenceId.nextId());
    		model.setTopicRate(new BigDecimal((100*model.getScore().doubleValue()/model.getFullMarks().doubleValue())+"")
    				.setScale(1, RoundingMode.HALF_UP).doubleValue());
    	});
    }
    
    
}
