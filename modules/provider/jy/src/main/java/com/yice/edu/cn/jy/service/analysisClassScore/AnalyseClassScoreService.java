package com.yice.edu.cn.jy.service.analysisClassScore;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamRate;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudent;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.exam.examManageHistory.ExamStudentHistory;
import com.yice.edu.cn.common.pojo.jw.exam.examManageHistory.SchoolExamHistory;
import com.yice.edu.cn.common.pojo.jw.exam.examManageHistory.StuScoreHistory;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseAllSubjectGradeScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.ExamRateVo;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalyseExamTopicStuHistory;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalyseStuKnowledgeHistory;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreAllHistory;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.jy.feignClient.examManage.StuScoreFeign;
import com.yice.edu.cn.jy.service.analysisStudentScore.AnalysisStudentScoreService;

import cn.hutool.core.date.DateUtil;

@Service
public class AnalyseClassScoreService {
	@Autowired
	private MongoTemplate mot;
	@Autowired
	private SequenceId sequenceId;
	@Autowired
	private AnalyseClassScoreAllService analyseClassScoreAllService;
	@Autowired
	private StuScoreFeign stuScoreFeign;
	@Autowired
	private AnalyseSubjectGradeScoreService analyseSubjectGradeScoreService;
	
	public AnalyseClassScore findAnalyseClassScoreById(String id) {
		return mot.findById(id, AnalyseClassScore.class);
	}

	public void saveAnalyseClassScore(AnalyseClassScore analyseClassScore) {
		analyseClassScore.setCreateTime(DateUtil.now());
		analyseClassScore.setId(sequenceId.nextId());
		mot.insert(analyseClassScore);
	}

	public List<AnalyseClassScore> findAnalyseClassScoreListByCondition(AnalyseClassScore analyseClassScore) {
		List<AggregationOperation> operations = new ArrayList<>();
		Criteria criteria = MongoKit.buildCriteria(analyseClassScore, analyseClassScore.getPager());
		operations.add(Aggregation.match(criteria));
		MongoKit.sortPageInclude(analyseClassScore.getPager(), operations);
		return mot.aggregate(Aggregation.newAggregation(AnalyseClassScore.class, operations), AnalyseClassScore.class)
				.getMappedResults();

	}

	public long findAnalyseClassScoreCountByCondition(AnalyseClassScore analyseClassScore) {
		Criteria criteria = MongoKit.buildCriteria(analyseClassScore, analyseClassScore.getPager());
		return mot.count(query(criteria), AnalyseClassScore.class);
	}

	public AnalyseClassScore findOneAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
		Example<AnalyseClassScore> example = Example.of(analyseClassScore, UntypedExampleMatcher.matchingAll());
		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(Aggregation.match(new Criteria().alike(example)));
		Pager pager = analyseClassScore.getPager();
		if (pager != null && pager.getIncludes() != null) {
			operations.add(Aggregation.project(pager.getIncludes()));
		}
		return mot.aggregate(Aggregation.newAggregation(AnalyseClassScore.class, operations), AnalyseClassScore.class)
				.getUniqueMappedResult();
	}

	public void updateAnalyseClassScore(AnalyseClassScore analyseClassScore) {
		mot.updateFirst(query(where("id").is(analyseClassScore.getId())), MongoKit.update(analyseClassScore),
				AnalyseClassScore.class);
	}

	public void deleteAnalyseClassScore(String id) {
		mot.remove(query(where("id").is(id)), AnalyseClassScore.class);
	}

	public void deleteAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
		Example<AnalyseClassScore> example = Example.of(analyseClassScore, UntypedExampleMatcher.matchingAll());
		Query query = query(new Criteria().alike(example));
		mot.remove(query, AnalyseClassScore.class);
	}

	public void batchSaveAnalyseClassScore(List<AnalyseClassScore> analyseClassScores) {
		analyseClassScores.forEach(analyseClassScore -> analyseClassScore.setId(sequenceId.nextId()));
		mot.insertAll(analyseClassScores);
	}

	/**
	 * 班级单课程成绩分析
	 * 
	 * @param schoolExam
	 */
	public void analyseClassScoreGroupByCourse(SchoolExam schoolExam) {
		String examId = schoolExam.getId();
		// 删除原先的考试分析
		mot.remove(query(where("examObj._id").is(examId)), AnalyseClassScore.class);
		mot.remove(query(where("examObj._id").is(examId)), AnalyseClassScoreAll.class);

		// 判断是否有学生成绩
		StuScore stuScore = new StuScore();
		stuScore.setSchoolExamId(examId);
		long count = stuScoreFeign.findStuScoreCountByCondition(stuScore);
		if (count == 0) {
			return;
		}
		// 过滤掉没用的考试数据
		List<JwCourse> courseList = schoolExam.getCourses().stream().map(course -> {
			course.setAnswerSheet(null);
			return course;
		}).collect(Collectors.toList());
		schoolExam.setCourses(courseList);

		ExamRate rate = schoolExam.getExamRate();

		List<AnalyseClassScore> analyseClassAvgMinMaxList = countClassAvgMinMax(examId, rate);
		List<AnalyseClassScore> analyseClassRateAndExamList = countClassRateAndExamNum(examId, rate);

		List<AnalyseClassScore> addList = new ArrayList<AnalyseClassScore>();
		for (AnalyseClassScore model : analyseClassRateAndExamList) {

			AnalyseClassScore analyModel = analyseClassAvgMinMaxList.stream().filter(
					filterClassScore -> model.getClassObj().getId().equals(filterClassScore.getClassObj().getId())
							&& model.getCourseObj().getId().equals(filterClassScore.getCourseObj().getId()))
					.findFirst().orElse(new AnalyseClassScore());
			// 全班某一个课程缺考
			if (analyModel.getClassObj() == null) {
				analyModel.setClassObj(model.getClassObj());
				analyModel.setCourseObj(model.getCourseObj());
				analyModel.setAvgMarks(0.0);
				analyModel.setMaxMarks(0.0);
				analyModel.setMinMarks(0.0);
			}
			analyModel.setAvgMarks(
					new BigDecimal(analyModel.getAvgMarks()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
			
			analyModel.setExamObj(schoolExam);
			analyModel.setId(sequenceId.nextId());

			// 计算参加考试和缺考的人数
			analyModel.setAbsenceNum(model.getAbsenceNum());
			analyModel.setActualNum(model.getActualNum());

			long totalScore = analyModel.getCourseObj().getTotalScore();
			long actualNum = analyModel.getActualNum();

			ExamRateVo excellentObj = new ExamRateVo();
			excellentObj.setNum(model.getExcellentObj().getNum());
			excellentObj.setMinScoreRange(new BigDecimal(totalScore * rate.getExcellent()[0] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			excellentObj.setMaxScoreRange(new BigDecimal(totalScore * rate.getExcellent()[1] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			if (actualNum != 0) {
				excellentObj.setScoreRate(new BigDecimal(model.getExcellentObj().getNum() * 100.0 / actualNum)
						.setScale(1, RoundingMode.HALF_UP).doubleValue());
			}

			analyModel.setExcellentObj(excellentObj);

			ExamRateVo goodObj = new ExamRateVo();
			goodObj.setNum(model.getGoodObj().getNum());
			goodObj.setMinScoreRange(new BigDecimal(totalScore * rate.getGood()[0] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			goodObj.setMaxScoreRange(new BigDecimal(totalScore * rate.getGood()[1] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			if (actualNum != 0) {
				goodObj.setScoreRate(new BigDecimal(model.getGoodObj().getNum() * 100.0 / actualNum)
						.setScale(1, RoundingMode.HALF_UP).doubleValue());
			}
			analyModel.setGoodObj(goodObj);

			ExamRateVo fairObj = new ExamRateVo();
			fairObj.setNum(model.getFairObj().getNum());
			fairObj.setMinScoreRange(new BigDecimal(totalScore * rate.getFair()[0] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			fairObj.setMaxScoreRange(new BigDecimal(totalScore * rate.getFair()[1] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			if (actualNum != 0) {
				fairObj.setScoreRate(new BigDecimal(model.getFairObj().getNum() * 100.0 / actualNum)
						.setScale(1, RoundingMode.HALF_UP).doubleValue());
			}
			analyModel.setFairObj(fairObj);

			ExamRateVo poorObj = new ExamRateVo();
			poorObj.setNum(model.getPoorObj().getNum());
			poorObj.setMinScoreRange(new BigDecimal(totalScore * rate.getPoor()[0] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			poorObj.setMaxScoreRange(new BigDecimal(totalScore * rate.getPoor()[1] / 100.0).setScale(1, RoundingMode.HALF_UP).doubleValue());
			double poorScoreRate = new BigDecimal(
					100.0 - (fairObj.getScoreRate() + goodObj.getScoreRate() + excellentObj.getScoreRate()))
					.setScale(1, RoundingMode.HALF_UP).doubleValue();
			if (actualNum != 0) {
				poorObj.setScoreRate(poorScoreRate<0?0:poorScoreRate);
			}
			analyModel.setPoorObj(poorObj);

			addList.add(analyModel);
		}

		// 计算年段排名
		addList = sortSort(addList);

		// 计算某个课程年段平均分
		Map<String, Double> mapResult = countAvgByCourse(addList, rate);
		addList.forEach(addModel -> {
			addModel.setGradeAvgMarks(mapResult.get(addModel.getCourseObj().getId()));
		});
		mot.insertAll(addList);
		// 班级总分分析
		analyseClassScoreAllService.analyseClassScoreWidthTotalScore(schoolExam);

		// 单科年段分析
		analyseSubjectGradeScoreService.analyseOneSubjectScore(addList, schoolExam);

	}

	/**
	 * 获取该班级单课程的平均分 最高分 最低分
	 */
	private List<AnalyseClassScore> countClassAvgMinMax(String examId, ExamRate rate) {

		List<AggregationOperation> operations = new ArrayList<>();
		Criteria criteria = new Criteria("schoolExamId").is(examId).and("missing").is(false);
		operations.add(Aggregation.match(criteria));
		operations.add(Aggregation.group("clazz", "course", "schoolExamId").min("score").as("minMarks").max("score")
				.as("maxMarks").avg("score").as("avgMarks"));
		operations.add(Aggregation.project("minMarks", "maxMarks", "avgMarks").and("clazz").as("classObj").and("course")
				.as("courseObj").and("schoolExamId").as("examObj._id"));
		operations.add(Aggregation.sort(Sort.Direction.DESC, "avgMarks"));
		List<AnalyseClassScore> analyseClassVoList = mot
				.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalyseClassScore.class)
				.getMappedResults();

		return analyseClassVoList;
	}

	/**
	 * 获取该班级单课程的四率人数 和参加考试 未参加考试人数
	 */
	private List<AnalyseClassScore> countClassRateAndExamNum(String examId, ExamRate rate) {

		List<AggregationOperation> operations = new ArrayList<>();
		Criteria criteria = new Criteria("schoolExamId").is(examId);
		operations.add(Aggregation.match(criteria));
		operations.add(Aggregation.project("course", "clazz", "schoolExamId", "score", "missing")
				.and("course.totalScore").multiply(rate.getExcellent()[0] / 100.0).as("excellentScoreMin")
				.and("course.totalScore").multiply(rate.getGood()[0] / 100.0).as("goodScoreMin")
				.and("course.totalScore").multiply(rate.getFair()[0] / 100.0).as("fairMin").and("course.totalScore")
				.multiply(rate.getPoor()[0] / 100.0).as("poorMin"));
		operations.add(Aggregation.group("clazz", "course", "schoolExamId")
				.sum(ConditionalOperators
						.when(ComparisonOperators.valueOf("missing").equalToValue(false)).then(1).otherwise(0))
				.as("actualNum")
				.sum(ConditionalOperators.when(ComparisonOperators.valueOf("missing").equalToValue(true)).then(1)
						.otherwise(0))
				.as("absenceNum")
				.sum(ConditionalOperators.when(Criteria.where("score").gte("$excellentScoreMin")).then(1).otherwise(0))
				.as("excellentNum")
				.sum(ConditionalOperators.when(new Criteria().andOperator(Criteria.where("score").gte("$goodScoreMin"),
						Criteria.where("score").lt("$excellentScoreMin"))).then(1).otherwise(0))
				.as("goodNum")
				.sum(ConditionalOperators.when(new Criteria().andOperator(Criteria.where("score").gte("$fairMin"),
						Criteria.where("score").lt("$goodScoreMin"))).then(1).otherwise(0))
				.as("fairNum")
				.sum(ConditionalOperators.when(new Criteria().andOperator(Criteria.where("score").gte("$poorMin"),
						Criteria.where("score").lt("$fairMin"))).then(1).otherwise(0))
				.as("poorNum"));
		operations.add(Aggregation.project("actualNum", "absenceNum").and("poorNum").as("poorObj.num").and("fairNum")
				.as("fairObj.num").and("goodNum").as("goodObj.num").and("excellentNum").as("excellentObj.num")
				.and("clazz").as("classObj").and("course").as("courseObj").and("schoolExamId").as("examObj._id"));
		List<AnalyseClassScore> analyseClassVoList = mot
				.aggregate(Aggregation.newAggregation(StuScore.class, operations), AnalyseClassScore.class)
				.getMappedResults();

		return analyseClassVoList;

	}

	/**
	 * 计算某个课程年段平均分
	 * 
	 * @param analyseClassVoList
	 * @param rate
	 * @param schoolExam
	 * @return key 课程id value 平均分值
	 */
	private Map<String, Double> countAvgByCourse(List<AnalyseClassScore> analyseClassVoList, ExamRate rate) {
		Map<String, List<AnalyseClassScore>> groupByCourse = analyseClassVoList.stream()
				.collect(Collectors.groupingBy(ss -> {
					return ss.getCourseObj().getId();
				}));
		Map<String, Double> courseAvgScore = new HashMap<String, Double>();
		groupByCourse.forEach((courseId, analyseClassScoreList) -> {
			double avg = analyseClassScoreList.stream().mapToDouble(AnalyseClassScore::getAvgMarks).average()
					.getAsDouble();
			courseAvgScore.put(courseId, new BigDecimal(avg).setScale(1, RoundingMode.HALF_UP).doubleValue());
		});

		return courseAvgScore;
	}

	/**
	 * 计算年段排名
	 * 
	 * @param analyseClassVoList
	 */
	private List<AnalyseClassScore> sortSort(List<AnalyseClassScore> analyseClassVoList) {
		List<AnalyseClassScore> retrunAnalyseClassVoList = new ArrayList<AnalyseClassScore>();
		// key 课程
		Map<JwCourse, List<AnalyseClassScore>> courseMap = analyseClassVoList.stream()
				.collect(Collectors.groupingBy(AnalyseClassScore::getCourseObj));
		// 排序
		courseMap.forEach((k, v) -> {
			List<AnalyseClassScore> analyseClassVoListCourseList = v.stream().sorted(Comparator.comparingDouble(AnalyseClassScore::getAvgMarks).reversed())
					.collect(Collectors.toList());
			Double preScore = null;
			int sort = 1;
			for (AnalyseClassScore model : analyseClassVoListCourseList) {
				if (preScore == null) {
					model.setGradeSort(sort);
					preScore = model.getAvgMarks();
				}
				if (model.getAvgMarks().compareTo(preScore) == 0) {
					model.setGradeSort(sort);
				}
				if (model.getAvgMarks().compareTo(preScore) < 0) {
					sort++;
					preScore = model.getAvgMarks();
					model.setGradeSort(sort);
				}
			}
			retrunAnalyseClassVoList.addAll(analyseClassVoListCourseList);
			
		});
		return retrunAnalyseClassVoList;
	}
	
	public void saveTopClazzScoreAnalyse(List<String> clazzIdList){
	 AnalysisStudentScoreService.getExecutor().execute(() -> {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        List<Criteria> cl = new ArrayList<>();
        cl.add(new Criteria("classes._id").in(clazzIdList));
        criteria.andOperator(cl.toArray(new Criteria[cl.size()]));
        operations.add(Aggregation.match(criteria));
        List<SchoolExamHistory> listExamList = mot.aggregate(Aggregation.newAggregation(SchoolExam.class,operations),SchoolExamHistory.class).getMappedResults();
        listExamList.forEach(exam->{
        	String examId = exam.getId();
        	mot.save(exam, getClassNameWithFirstLowerCase(SchoolExamHistory.class.getSimpleName()));
        	mot.remove(query(where("_id").is(examId)), SchoolExam.class);
        	//删除没用的分析
        	removeAnalyScoreData(examId);
        	//存档学生的学情分析
        	moveAnalyScoreData(examId);
        });		
		});
	}
	
	/**
	 * 存档学生学情分析数据
	 * @param examId
	 */
	private void moveAnalyScoreData(String examId) {
    	//stuScore analyseExamTopicStu  analyseStuKnowledge analysisStudentScore analysisStudentScoreAll examStudent
        //存档学生分数
		Query stuScoreQuery = new Query().addCriteria(Criteria.where("schoolExamId").is(examId));
		List<StuScore> stuScoreList = mot.find(stuScoreQuery, StuScore.class);
		String stuScoreHistoryCollection = getClassNameWithFirstLowerCase(StuScoreHistory.class.getSimpleName());
		stuScoreList.forEach(stuScore->{
			mot.save(stuScore, stuScoreHistoryCollection);
		});
		mot.remove(stuScoreQuery, StuScore.class);
		
        //存档学生的小题分析
		Query analyseExamTopicStuQuery = new Query().addCriteria(Criteria.where("examId").is(examId));
		List<AnalyseExamTopicStu> analyseExamTopicStuList = mot.find(analyseExamTopicStuQuery, AnalyseExamTopicStu.class);
		String analyseExamTopicStuHistoryCollection = getClassNameWithFirstLowerCase(AnalyseExamTopicStuHistory.class.getSimpleName());
		analyseExamTopicStuList.forEach(analyseExamTopic->{
			mot.save(analyseExamTopic, analyseExamTopicStuHistoryCollection);
		});
		mot.remove(analyseExamTopicStuQuery, AnalyseExamTopicStu.class);
		
		//存档学生的知识点分析
		Query analyseStuKnowledgeQuery = new Query().addCriteria(Criteria.where("examination._id").is(examId));
		List<AnalyseStuKnowledge> analyseStuKnowledgeList = mot.find(analyseStuKnowledgeQuery, AnalyseStuKnowledge.class);
        String AnalyseStuKnowledgeHistoryCollection = getClassNameWithFirstLowerCase(AnalyseStuKnowledgeHistory.class.getSimpleName());
        analyseStuKnowledgeList.forEach(analyseStuKnowledge->{
			mot.save(analyseStuKnowledge, AnalyseStuKnowledgeHistoryCollection);
		});
        mot.remove(analyseStuKnowledgeQuery, AnalyseStuKnowledge.class);
        
        //存档学生的单科成绩分析
        Query analysisStudentScoreQuery = new Query().addCriteria(Criteria.where("examination._id").is(examId));
		List<AnalysisStudentScore> analysisStudentScoreList = mot.find(analysisStudentScoreQuery, AnalysisStudentScore.class);
        String analysisStudentScoreHistoryCollection = getClassNameWithFirstLowerCase(AnalysisStudentScoreHistory.class.getSimpleName());
        analysisStudentScoreList.forEach(analysisStudentScore->{
			mot.save(analysisStudentScore, analysisStudentScoreHistoryCollection);
		});
        mot.remove(analysisStudentScoreQuery, AnalysisStudentScore.class);
		
        //存档学生的总成绩分析
        Query analysisStudentScoreAllQuery = new Query().addCriteria(Criteria.where("examination._id").is(examId));
		List<AnalysisStudentScoreAll> analysisStudentScoreAllList = mot.find(analysisStudentScoreAllQuery, AnalysisStudentScoreAll.class);
		String analysisStudentScoreAllHistoryCollection = getClassNameWithFirstLowerCase(AnalysisStudentScoreAllHistory.class.getSimpleName());
		analysisStudentScoreAllList.forEach(analysisStudentScoreAll->{
			mot.save(analysisStudentScoreAll, analysisStudentScoreAllHistoryCollection);
		});
		mot.remove(analysisStudentScoreAllQuery, AnalysisStudentScoreAll.class);
		
		//存档考试学生 
		Query examStudentQuery = new Query().addCriteria(Criteria.where("schoolExamId").is(examId));
		List<ExamStudent> examStudentList = mot.find(examStudentQuery,ExamStudent.class);
		String examStudentHistoryCollection = getClassNameWithFirstLowerCase(ExamStudentHistory.class.getSimpleName());
		examStudentList.forEach(examStudent->{
			mot.save(examStudent, examStudentHistoryCollection);
		});
		mot.remove(examStudentQuery, ExamStudent.class);
	}
	
	private String getClassNameWithFirstLowerCase(String classSimpleName) {
		return classSimpleName.substring(0,1).toLowerCase().concat(classSimpleName.substring(1));
	}
	
	/**
	 * 根据考试id删除该考试分析的相关数据
	 * @param examId
	 */
	private void removeAnalyScoreData(String examId) {
    	
		mot.remove(query(where("examObj._id").is(examId)), AnalyseAllSubjectGradeScore.class);
    	
    	mot.remove(query(where("examination._id").is(examId)), AnalyseClassKnowledge.class);
    	
    	mot.remove(query(where("examObj._id").is(examId)), AnalyseClassScore.class);
    	
    	mot.remove(query(where("examObj._id").is(examId)), AnalyseClassScoreAll.class);
    	
    	mot.remove(query(where("examId").is(examId)), AnalyseExamTopic.class);
    	
    	mot.remove(query(where("examination._id").is(examId)), AnalyseGradeKnowledge.class);
    	
    	mot.remove(query(where("examObj._id").is(examId)), AnalyseSubjectGradeScore.class);
		
	}
}
