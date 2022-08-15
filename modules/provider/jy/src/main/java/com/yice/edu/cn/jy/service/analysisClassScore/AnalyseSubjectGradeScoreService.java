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
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.ExamRateVo;

import cn.hutool.core.date.DateUtil;

@Service
public class AnalyseSubjectGradeScoreService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseSubjectGradeScore findAnalyseSubjectGradeScoreById(String id) {
        return mot.findById(id,AnalyseSubjectGradeScore.class);
    }
    public void saveAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        analyseSubjectGradeScore.setCreateTime(DateUtil.now());
        analyseSubjectGradeScore.setId(sequenceId.nextId());
        mot.insert(analyseSubjectGradeScore);
    }
    public List<AnalyseSubjectGradeScore> findAnalyseSubjectGradeScoreListByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(analyseSubjectGradeScore, analyseSubjectGradeScore.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analyseSubjectGradeScore.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(AnalyseSubjectGradeScore.class,operations),AnalyseSubjectGradeScore.class).getMappedResults();

    }
    public long findAnalyseSubjectGradeScoreCountByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        Criteria criteria = MongoKit.buildCriteria(analyseSubjectGradeScore, analyseSubjectGradeScore.getPager());
        return mot.count(query(criteria), AnalyseSubjectGradeScore.class);
    }
    public AnalyseSubjectGradeScore findOneAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        Example<AnalyseSubjectGradeScore> example = Example.of(analyseSubjectGradeScore, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = analyseSubjectGradeScore.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnalyseSubjectGradeScore.class,operations),AnalyseSubjectGradeScore.class).getUniqueMappedResult();
    }

    public void updateAnalyseSubjectGradeScore(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        mot.updateFirst(query(where("id").is(analyseSubjectGradeScore.getId())), MongoKit.update(analyseSubjectGradeScore),AnalyseSubjectGradeScore.class);
    }
    public void deleteAnalyseSubjectGradeScore(String id) {
        mot.remove(query(where("id").is(id)),AnalyseSubjectGradeScore.class);
    }
    public void deleteAnalyseSubjectGradeScoreByCondition(AnalyseSubjectGradeScore analyseSubjectGradeScore) {
        Example<AnalyseSubjectGradeScore> example = Example.of(analyseSubjectGradeScore, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnalyseSubjectGradeScore.class);
    }
    public void batchSaveAnalyseSubjectGradeScore(List<AnalyseSubjectGradeScore> analyseSubjectGradeScores){
        analyseSubjectGradeScores.forEach(analyseSubjectGradeScore -> analyseSubjectGradeScore.setId(sequenceId.nextId()));
        mot.insertAll(analyseSubjectGradeScores);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    
    public void analyseOneSubjectScore(List<AnalyseClassScore> analyseClassScore,SchoolExam schoolExam) {
      mot.remove(query(where("examObj.id").is(schoolExam.getId())),AnalyseSubjectGradeScore.class);
      
      //对科目分组
 	   Map<String,List<AnalyseClassScore>> analyseClassScoreMapByCourseId = analyseClassScore.stream().collect(Collectors.groupingBy(ss->{
 		   return ss.getCourseObj().getId();
 	   }));
 	 
 	   //分科计算全年级的单科四率
 	  List<AnalyseSubjectGradeScore> analyseSubjectGradeScoreList = new ArrayList<AnalyseSubjectGradeScore>();
    	 analyseClassScoreMapByCourseId.entrySet().forEach(analyseClassScoreMap->{
    		 
 		 List<AnalyseClassScore> analyseClassScoreList =  analyseClassScoreMap.getValue();
 		 AnalyseClassScore analyseClassScoreModel = analyseClassScoreList.get(0);
     		 
     	 AnalyseSubjectGradeScore analyseSubjectGradeScore = new AnalyseSubjectGradeScore();
     	 analyseSubjectGradeScore.setId(sequenceId.nextId());
     	 analyseSubjectGradeScore.setCourseObj(analyseClassScoreModel.getCourseObj());
     	 analyseSubjectGradeScore.setExamObj(analyseClassScoreModel.getExamObj());
 		 
     	 //各率的人数
     	 int excellentSum = 0;
     	 int goodSum = 0;
     	 int poorSum = 0;
     	 int fairSum = 0;
     	 for(AnalyseClassScore analyseClassScoreCount:analyseClassScoreList) {
      		excellentSum = excellentSum+analyseClassScoreCount.getExcellentObj().getNum();
      		goodSum = goodSum+analyseClassScoreCount.getGoodObj().getNum();
      		poorSum = poorSum+analyseClassScoreCount.getPoorObj().getNum();
      		fairSum = fairSum+analyseClassScoreCount.getFairObj().getNum();
     	 }
     	 //成绩的参考人数
     	 int allStu = excellentSum+goodSum+poorSum+fairSum;
     	 analyseSubjectGradeScore.setActualNum(Long.valueOf(allStu));
     	 
     	 //缺考人数
     	 analyseSubjectGradeScore.setAbsenceNum(Long.valueOf(schoolExam.getTotalNum()-allStu));
     	 
 		 //计算优秀率对象
         ExamRateVo excellentVo = new ExamRateVo();
         excellentVo.setNum(excellentSum);
         excellentVo.setScoreRate(allStu==0?0:new BigDecimal(excellentSum*100.0/allStu+"").setScale(1, RoundingMode.HALF_UP).doubleValue());
         excellentVo.setMinScoreRange(analyseClassScoreModel.getExcellentObj().getMinScoreRange());
         excellentVo.setMaxScoreRange(analyseClassScoreModel.getExcellentObj().getMaxScoreRange());
         analyseSubjectGradeScore.setExcellentObj(excellentVo);
         
         //计算良好率对象
         ExamRateVo goodVo = new ExamRateVo();
         goodVo.setNum(goodSum);
         goodVo.setScoreRate(allStu==0?0:new BigDecimal(goodSum*100.0/allStu+"").setScale(1, RoundingMode.HALF_UP).doubleValue());
         goodVo.setMinScoreRange(analyseClassScoreModel.getGoodObj().getMinScoreRange());
         goodVo.setMaxScoreRange(analyseClassScoreModel.getGoodObj().getMaxScoreRange());
         analyseSubjectGradeScore.setGoodObj(goodVo);
         
         //计算及格率对象
         ExamRateVo poorVo = new ExamRateVo();
         poorVo.setNum(poorSum);
         poorVo.setScoreRate(allStu==0?0:new BigDecimal(poorSum*100.0/allStu+"").setScale(1, RoundingMode.HALF_UP).doubleValue());
         poorVo.setMinScoreRange(analyseClassScoreModel.getPoorObj().getMinScoreRange());
         poorVo.setMaxScoreRange(analyseClassScoreModel.getPoorObj().getMaxScoreRange());
         analyseSubjectGradeScore.setPoorObj(poorVo);
         
         //计算低分率对象
         ExamRateVo fairVo = new ExamRateVo();
         fairVo.setNum(fairSum);
         fairVo.setScoreRate(new BigDecimal(100.0-(poorVo.getScoreRate()+goodVo.getScoreRate()+excellentVo.getScoreRate())).setScale(1, RoundingMode.HALF_UP).doubleValue());
         fairVo.setMinScoreRange(analyseClassScoreModel.getFairObj().getMinScoreRange());
         fairVo.setMaxScoreRange(analyseClassScoreModel.getFairObj().getMaxScoreRange());
         analyseSubjectGradeScore.setFairObj(fairVo);
         
 		 analyseSubjectGradeScoreList.add(analyseSubjectGradeScore);
 	  });
 	   
 	  mot.insertAll(analyseSubjectGradeScoreList);
 	   
    }
}
