package com.yice.edu.cn.jy.service.analysisClassScore;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.ClassScoreSortVo;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.ExamRateVo;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.StuNoAttendExamVo;
import com.yice.edu.cn.jy.feignClient.examManage.SchoolExamFeign;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;

@Service
public class AnalyseClassScoreAllService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private AnalyseAllSubjectGradeScoreService analyseAllSubjectGradeScoreService;

    public AnalyseClassScoreAll findAnalyseClassScoreAllById(String id) {
        return mot.findById(id,AnalyseClassScoreAll.class);
    }
    public void saveAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll) {
        analyseClassScoreAll.setCreateTime(DateUtil.now());
        analyseClassScoreAll.setId(sequenceId.nextId());
        mot.insert(analyseClassScoreAll);
    }
    public List<AnalyseClassScoreAll> findAnalyseClassScoreAllListByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(analyseClassScoreAll, analyseClassScoreAll.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analyseClassScoreAll.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(AnalyseClassScoreAll.class,operations),AnalyseClassScoreAll.class).getMappedResults();

    }
    public long findAnalyseClassScoreAllCountByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        Criteria criteria = MongoKit.buildCriteria(analyseClassScoreAll, analyseClassScoreAll.getPager());
        return mot.count(query(criteria), AnalyseClassScoreAll.class);
    }
    public AnalyseClassScoreAll findOneAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        Example<AnalyseClassScoreAll> example = Example.of(analyseClassScoreAll, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = analyseClassScoreAll.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnalyseClassScoreAll.class,operations),AnalyseClassScoreAll.class).getUniqueMappedResult();
    }

    public void updateAnalyseClassScoreAll(AnalyseClassScoreAll analyseClassScoreAll) {
        mot.updateFirst(query(where("id").is(analyseClassScoreAll.getId())), MongoKit.update(analyseClassScoreAll),AnalyseClassScoreAll.class);
    }
    public void deleteAnalyseClassScoreAll(String id) {
        mot.remove(query(where("id").is(id)),AnalyseClassScoreAll.class);
    }
    public void deleteAnalyseClassScoreAllByCondition(AnalyseClassScoreAll analyseClassScoreAll) {
        Example<AnalyseClassScoreAll> example = Example.of(analyseClassScoreAll, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnalyseClassScoreAll.class);
    }
    public void batchSaveAnalyseClassScoreAll(List<AnalyseClassScoreAll> analyseClassScoreAlls){
        analyseClassScoreAlls.forEach(analyseClassScoreAll -> analyseClassScoreAll.setId(sequenceId.nextId()));
        mot.insertAll(analyseClassScoreAlls);
    }
    
    /**
     * 班级考试总分成绩分析
     * @param examId
     * 
	 db.getCollection('stuScore').aggregate([{ "$match" : { "schoolExamId" : "2074251878692904960","missing":false } },
	{"$group":{"_id":{"clazz":"$clazz","schoolExamId":"$schoolExamId","student":"$student"},
	          "allCourseScore":{"$sum":"$score"}
	}},
	{"$group" : { "_id" : { "clazz" : "$_id.clazz", "schoolExamId" : "$_id.schoolExamId"}, 
	"minMarks" : { "$min" : "$allCourseScore"}, 
	"maxMarks" : { "$max" : "$allCourseScore"}, 
	"avgMarks" : { "$avg" : "$allCourseScore"}
	} }, 
	  { "$project" : 
	      {"_id":0,"minMarks" : 1, "maxMarks" : 1, "avgMarks" : 1, "classObj" : "$_id.clazz",  "examObj._id" : "$_id.schoolExamId" } },
	      { "$sort" : { "avgMarks" : -1 } }
	     ]
	)
     * @param examId
     * @return
     */
    public void analyseClassScoreWidthTotalScore(SchoolExam schoolExam) {
    	
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria("schoolExamId").is(schoolExam.getId()).and("missing").is(false);
        operations.add(Aggregation.match(criteria));
        
        operations.add(Aggregation.group("clazz","schoolExamId","student")
      		  .sum("score").as("allCourseScore")
      		);
        operations.add(Aggregation.group("_id.clazz","_id.schoolExamId")
        		  .min("allCourseScore").as("minMarks")
        		  .max("allCourseScore").as("maxMarks")
        		  .avg("allCourseScore").as("avgMarks")
        		);
        
        operations.add(Aggregation.project("minMarks","maxMarks","avgMarks").and("clazz").as("classObj").and("schoolExamId").as("examObj._id"));
        operations.add(Aggregation.sort(Sort.Direction.DESC, "avgMarks"));
        //查找总课程成绩的平均分 最高分 最低分
        List<AnalyseClassScoreAll> analyseClassAllsWithScore = mot.aggregate(Aggregation.newAggregation(StuScore.class,operations),AnalyseClassScoreAll.class).getMappedResults();
        
        //计算年段排名  key 课程id
        scortSort(analyseClassAllsWithScore);
        //计算年段平均分
        countGradeAvg(analyseClassAllsWithScore,schoolExam);
        
        //计算缺考人数
        countAttendExamStuNum(analyseClassAllsWithScore,schoolExam);
        
        mot.insertAll(analyseClassAllsWithScore);
        
        //年段总分分析
        analyseAllSubjectGradeScoreService.analyseAllSubjectScore(analyseClassAllsWithScore,schoolExam);
    }
    
    //计算年段平均分
    private void countGradeAvg(List<AnalyseClassScoreAll> analyseClassScoreAlls,SchoolExam schoolExam) {
    	Double gradeAvg = analyseClassScoreAlls.stream().mapToDouble(AnalyseClassScoreAll::getAvgMarks).average().getAsDouble();
    	analyseClassScoreAlls = analyseClassScoreAlls.stream().map(analyseClassScoreAll->{
    		analyseClassScoreAll.setExamObj(schoolExam);
    		analyseClassScoreAll.setId(sequenceId.nextId());
    		analyseClassScoreAll.setGradeAvgMarks(new BigDecimal(gradeAvg).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
    		return analyseClassScoreAll;
    	}).collect(Collectors.toList());
    }
    
    /**
              * 计算年段排名 
     * @param analyseClassVoList
     */
    private void scortSort(List<AnalyseClassScoreAll> analyseClassScoreAlls) {
    	// key 课程id
    	ClassScoreSortVo classScoreSortVo = null;
        for(AnalyseClassScoreAll model :analyseClassScoreAlls) {
        	model.setAvgMarks(new BigDecimal(model.getAvgMarks()).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        	if(classScoreSortVo==null) {
        		model.setGradeSort(1);
        		classScoreSortVo = new ClassScoreSortVo();
        		classScoreSortVo.setScore(model.getAvgMarks());
        		classScoreSortVo.setSort(model.getGradeSort());
        		continue;
        	} 
        	if(model.getAvgMarks().compareTo(classScoreSortVo.getScore())==0) {
        		model.setGradeSort(classScoreSortVo.getSort());
        		continue;
        	}
        	if(model.getAvgMarks().compareTo(classScoreSortVo.getScore())<0) {
        		model.setGradeSort(classScoreSortVo.getSort()+1);
        		classScoreSortVo.setScore(model.getAvgMarks());
        		classScoreSortVo.setSort(model.getGradeSort());
        		continue;
        	}
        }
    }
    
    /** 计算全部课程都缺考的人数
	db.getCollection('stuScore').aggregate([{ "$match" : { "schoolExamId" : "2091790429185728512","missing":true } },
		{"$group":{"_id":{"clazz":"$clazz","schoolExamId":"$schoolExamId","student":"$student"},
		          "noAttendCount" : { "$sum" : 1 }, 
		}},
		{$match:{"noAttendCount":{$gt:0}}},
		  { "$project" : 
		      {"_id":0,"classObj" : "$_id.clazz","noAttendCount":1} }
		     ]
		)
     */
    private void countAttendExamStuNum(List<AnalyseClassScoreAll> analyseClassScoreAlls,SchoolExam schoolExam) {
    	
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria("schoolExamId").is(schoolExam.getId()).and("missing").is(true);
        operations.add(Aggregation.match(criteria));
        
        operations.add(Aggregation.group("clazz","schoolExamId","student")
        		.count().as("noAttendCount")
      		);
        operations.add(Aggregation.match(new Criteria("noAttendCount").gt(0)));
        operations.add(Aggregation.project("noAttendCount").and("_id.clazz._id").as("classId").and("_id.student._id").as("studentId"));
        
        List<StuNoAttendExamVo> stuNoAttendExamVoList = mot.aggregate(Aggregation.newAggregation(StuScore.class,operations),StuNoAttendExamVo.class).getMappedResults();
        int courseNum = schoolExam.getCourses().size(); 
        analyseClassScoreAlls.forEach(analyseClassScoreAll->{
        	 stuNoAttendExamVoList.forEach(stuNoAttendExamVo->{
            	 if(analyseClassScoreAll.getClassObj().getId().equals(stuNoAttendExamVo.getClassId())
            			 && stuNoAttendExamVo.getNoAttendCount().intValue()==courseNum) {
            		 analyseClassScoreAll.setAbsenceNum(analyseClassScoreAll.getAbsenceNum()==null?1:analyseClassScoreAll.getAbsenceNum()+1);
            	 }
        	 });
        });
        //考试总成绩
        Double totalScore = schoolExam.getCourses().stream().mapToDouble(JwCourse::getTotalScore).sum();
        analyseClassScoreAlls = analyseClassScoreAlls.stream().map(analyseClassScoreAll -> {
        	//计算参加考试人数
        	long num =analyseClassScoreAll.getClassObj().getStudentCount()==null?0:Long.valueOf(analyseClassScoreAll.getClassObj().getStudentCount()).longValue()
        			  -(analyseClassScoreAll.getAbsenceNum()==null?0:analyseClassScoreAll.getAbsenceNum().longValue());
        	analyseClassScoreAll.setActualNum(num);
        	//添加考试总成绩
        	analyseClassScoreAll.setFullMarks(totalScore);
        	
        	return analyseClassScoreAll;
        	
        }).collect(Collectors.toList());
        
    }
}
