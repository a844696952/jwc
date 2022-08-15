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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicVo;
import com.yice.edu.cn.jy.feignClient.examManage.StuScoreFeign;

import cn.hutool.core.date.DateUtil;

@Service
public class AnalyseExamTopicService {
	
    private static final Logger logger = LoggerFactory.getLogger(AnalyseExamTopicStuService.class);
	
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnalyseExamTopicStuService analyseExamTopicStuService;
    @Autowired
    private StuScoreFeign stuScoreFeign;
    
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseExamTopic findAnalyseExamTopicById(String id) {
        return mot.findById(id,AnalyseExamTopic.class);
    }
    public void saveAnalyseExamTopic(AnalyseExamTopic analyseExamTopic) {
        analyseExamTopic.setCreateTime(DateUtil.now());
        analyseExamTopic.setId(sequenceId.nextId());
        mot.insert(analyseExamTopic);
    }
    public List<AnalyseExamTopic> findAnalyseExamTopicListByCondition(AnalyseExamTopic analyseExamTopic) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopic.class)).find(MongoKit.buildMatchDocument(analyseExamTopic));
        Pager pager = analyseExamTopic.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<AnalyseExamTopic> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(AnalyseExamTopic.class, document)));
        return list;
    }
    public long findAnalyseExamTopicCountByCondition(AnalyseExamTopic analyseExamTopic) {
        return mot.getCollection(mot.getCollectionName(AnalyseExamTopic.class)).count(MongoKit.buildMatchDocument(analyseExamTopic));
    }
    public AnalyseExamTopic findOneAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(AnalyseExamTopic.class)).find(MongoKit.buildMatchDocument(analyseExamTopic));
       MongoKit.appendProjection(query,analyseExamTopic.getPager());
       return mot.getConverter().read(AnalyseExamTopic.class,query.first());
    }

    public void updateAnalyseExamTopic(AnalyseExamTopic analyseExamTopic) {
        mot.updateFirst(query(where("id").is(analyseExamTopic.getId())), MongoKit.update(analyseExamTopic),AnalyseExamTopic.class);
    }
    public void deleteAnalyseExamTopic(String id) {
        mot.remove(query(where("id").is(id)),AnalyseExamTopic.class);
    }
    public void deleteAnalyseExamTopicByCondition(AnalyseExamTopic analyseExamTopic) {
        mot.getCollection(mot.getCollectionName(AnalyseExamTopic.class)).deleteMany(MongoKit.buildMatchDocument(analyseExamTopic));
    }
    public void batchSaveAnalyseExamTopic(List<AnalyseExamTopic> analyseExamTopics){
        analyseExamTopics.forEach(analyseExamTopic -> analyseExamTopic.setId(sequenceId.nextId()));
        mot.insertAll(analyseExamTopics);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    
    /**
db.getCollection('stuScore').aggregate([{"$match":{schoolExamId:"2234408597820628992",missing:false}}
,{$project:{_id:1,schoolExamId:1,clazz:1,course:1,answerSheetItems:"$answerSheetDatas.answerSheetItems"}}
,{$unwind:"$answerSheetItems"}
,{$unwind:"$answerSheetItems"}
,{$group:{_id:{clazz:"$clazz",course:"$course",fullMarks:"$answerSheetItems.score"
         ,topicTypeName:"$answerSheetItems.typeName",topicNum:"$answerSheetItems.num"},
   classAvgMarks:{$avg:"$answerSheetItems.yourScore"}}}
,{$project:{_id:0,clazzObj:"$_id.clazz",courseObj:"$_id.course",fullMarks:"$_id.fullMarks"
    ,topicTypeName:"$_id.topicTypeName",topicNum:"$_id.topicNum",classAvgMarks:1}}
])
     * @param schoolExam
     */
    public void analyseExamTopicData(SchoolExam schoolExam) {
    	String examId = schoolExam.getId();
    	//判断该考试是否有小题
    	if(schoolExam==null || schoolExam.getType().intValue()==Constant.ExamType.offLine_exam) {
    		return;
    	}
    	mot.remove(query(where("examId").is(examId)),AnalyseExamTopic.class);
    	mot.remove(query(where("examId").is(examId)),AnalyseExamTopicStu.class);
    	
    	//判断是否有学生成绩
    	StuScore stuScore = new StuScore();
    	stuScore.setSchoolExamId(examId);
    	long count = stuScoreFeign.findStuScoreCountByCondition(stuScore);
    	if(count==0) {
    		return;
    	}
    	
		try {
		    Document matchParams = new Document("schoolExamId",examId)
					               .append("missing", false);
		//    			               .append("lostPaper", false);
			Document match = new Document("$match", matchParams);
			
			Document projectFirstParams = new Document("_id",1)
					                 .append("schoolExamId", 1)
					                 .append("clazz", 1)
					                 .append("course", 1)
					                 .append("answerSheetItems","$answerSheetDatas.answerSheetItems");
					                 
			Document projectFirst = new Document("$project", projectFirstParams);
			
			Document unwindFirst = new Document("$unwind","$answerSheetItems");
			Document unwindSecond = new Document("$unwind","$answerSheetItems");
			
			Document groupIdParams = new Document("clazz","$clazz")
					                 .append("course","$course")
					                 .append("fullMarks","$answerSheetItems.score")
		 			                 .append("topicTypeName", "$answerSheetItems.typeName")
		 			                 .append("topicId", "$answerSheetItems.typeId")
		 			                 .append("topicNum", "$answerSheetItems.num");
			
			Document groupId = new Document("_id",groupIdParams).append("classAvgMarks", new Document("$avg","$answerSheetItems.yourScore"));
			
			Document group = new Document("$group",groupId);
		
			Document projectSecParams = new Document("_id",0)
					                    .append("clazzObj","$_id.clazz")
					                    .append("courseObj", "$_id.course")
					                    .append("fullMarks", "$_id.fullMarks")
					                    .append("topicTypeName","$_id.topicTypeName")
					                    .append("topicNum", "$_id.topicNum")
					                    .append("topicId", "$_id.topicId")
					                    .append("classAvgMarks",1)
			                            .append("gradeAvgMarkers",new Document("$divide",Arrays.asList("$classAvgMarks","$_id.fullMarks")));
			Document projectSecond = new Document("$project", projectSecParams);
			
			List<Bson> operas = new ArrayList<>();
			operas.addAll(Arrays.asList(match,projectFirst,unwindFirst,unwindSecond,group,projectSecond));
			
			List<AnalyseExamTopic> analyseExamTopicList = new ArrayList<AnalyseExamTopic>();
			
		    MongoCollection<Document> collection = mot.getCollection("stuScore");
		    AggregateIterable<Document> aggResult = collection.aggregate(operas);
		    MongoCursor<Document> cursor =  aggResult.iterator();
		    while(cursor.hasNext()) {
		    	Document document = cursor.next();
		    	AnalyseExamTopic analyseExamTopic = mot.getConverter().read(AnalyseExamTopic.class, document);	
		    	analyseExamTopicList.add(analyseExamTopic);
		    }
		    
		    //先处理字段精度
		    countTopicScoreRateByClass(schoolExam,analyseExamTopicList);
		    
		    //计算某个科目某个小题的年级均分
		    countAvgByTopic(analyseExamTopicList);
		    //开始每个学生的小题分析
		    analyseExamTopicList = analyseExamTopicStuService.analyseStuTopic(schoolExam,analyseExamTopicList);
		   
		    mot.insertAll(analyseExamTopicList);
		    
		    } catch (Exception e) {
			    e.printStackTrace();
			   logger.error(e.getLocalizedMessage());
			 }
    }
    
    /**
     * 计算班级的某个科目的某个小题的得分率
     * @param analyseExamTopicList
     */
    private void countTopicScoreRateByClass(SchoolExam schoolExam,List<AnalyseExamTopic> analyseExamTopicList){
    	analyseExamTopicList.forEach(ss ->{
    		ss.setId(sequenceId.nextId());
    		ss.setExamId(schoolExam.getId());
    		ss.setExamObj(schoolExam);
    		ss.setClassAvgMarks(new BigDecimal(ss.getClassAvgMarks()+"").setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue() );
    		ss.setClassScoreRate(new BigDecimal((100*ss.getClassAvgMarks()/ss.getFullMarks())+"").setScale(1, RoundingMode.HALF_UP).doubleValue());
    	});
    }
    
	/**
	   * 计算某个科目某个小题的年段平均分和年级得分率
	 * @param analyseExamTopicList
	 * 
	*/
	private void countAvgByTopic(List<AnalyseExamTopic> analyseExamTopicList) {
		//科目分组
		Map<String,List<AnalyseExamTopic>> courseMap = analyseExamTopicList.stream().collect(Collectors.groupingBy(ss -> {
			return ss.getCourseObj().getId();
		}));
		
		List<AnalyseExamTopicVo> voList = new ArrayList<AnalyseExamTopicVo>();
		courseMap.forEach((courseId,courseTopicList)->{
			//小题分组计算年段平均分
			Map<Integer,List<AnalyseExamTopic>> topicListMap = courseTopicList.stream().collect(Collectors.groupingBy(ss -> {
				return ss.getTopicNum();
			}));
			topicListMap.forEach((topicNum,topicList) -> {
				AnalyseExamTopicVo vo = new AnalyseExamTopicVo();
				vo.setCourseId(courseId);
				vo.setTopicNum(topicNum);
		    	Double gradeAvg = topicList.stream().mapToDouble(AnalyseExamTopic::getClassAvgMarks).average().getAsDouble();
		    	
		    	vo.setGradeScoreRate(new BigDecimal(gradeAvg*100/topicList.get(0).getFullMarks()+"").setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
		    	vo.setGradeAvgMarkers(new BigDecimal(gradeAvg+"").setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
				voList.add(vo);
			});
		});
		
		analyseExamTopicList.forEach(analyseExamTopicModel ->{
			for(AnalyseExamTopicVo analyseExamTopicVo : voList) {
				if(analyseExamTopicVo.getCourseId().equals(analyseExamTopicModel.getCourseObj().getId()) 
						&& analyseExamTopicModel.getTopicNum().intValue()==analyseExamTopicVo.getTopicNum().intValue()) {
				   analyseExamTopicModel.setGradeScoreRate(analyseExamTopicVo.getGradeScoreRate());
			       analyseExamTopicModel.setGradeAvgMarks(analyseExamTopicVo.getGradeAvgMarkers());
					break;
				}
			}
		});
		}
}
