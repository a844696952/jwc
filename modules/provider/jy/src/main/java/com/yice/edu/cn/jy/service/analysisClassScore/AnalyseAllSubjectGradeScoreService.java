package com.yice.edu.cn.jy.service.analysisClassScore;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

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
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseAllSubjectGradeScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;

import cn.hutool.core.date.DateUtil;

@Service
public class AnalyseAllSubjectGradeScoreService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public AnalyseAllSubjectGradeScore findAnalyseAllSubjectGradeScoreById(String id) {
        return mot.findById(id,AnalyseAllSubjectGradeScore.class);
    }
    public void saveAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        analyseAllSubjectGradeScore.setCreateTime(DateUtil.now());
        analyseAllSubjectGradeScore.setId(sequenceId.nextId());
        mot.insert(analyseAllSubjectGradeScore);
    }
    public List<AnalyseAllSubjectGradeScore> findAnalyseAllSubjectGradeScoreListByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(analyseAllSubjectGradeScore, analyseAllSubjectGradeScore.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(analyseAllSubjectGradeScore.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(AnalyseAllSubjectGradeScore.class,operations),AnalyseAllSubjectGradeScore.class).getMappedResults();

    }
    public long findAnalyseAllSubjectGradeScoreCountByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        Criteria criteria = MongoKit.buildCriteria(analyseAllSubjectGradeScore, analyseAllSubjectGradeScore.getPager());
        return mot.count(query(criteria), AnalyseAllSubjectGradeScore.class);
    }
    public AnalyseAllSubjectGradeScore findOneAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        Example<AnalyseAllSubjectGradeScore> example = Example.of(analyseAllSubjectGradeScore, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = analyseAllSubjectGradeScore.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(AnalyseAllSubjectGradeScore.class,operations),AnalyseAllSubjectGradeScore.class).getUniqueMappedResult();
    }

    public void updateAnalyseAllSubjectGradeScore(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        mot.updateFirst(query(where("id").is(analyseAllSubjectGradeScore.getId())), MongoKit.update(analyseAllSubjectGradeScore),AnalyseAllSubjectGradeScore.class);
    }
    public void deleteAnalyseAllSubjectGradeScore(String id) {
        mot.remove(query(where("id").is(id)),AnalyseAllSubjectGradeScore.class);
    }
    public void deleteAnalyseAllSubjectGradeScoreByCondition(AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore) {
        Example<AnalyseAllSubjectGradeScore> example = Example.of(analyseAllSubjectGradeScore, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, AnalyseAllSubjectGradeScore.class);
    }
    public void batchSaveAnalyseAllSubjectGradeScore(List<AnalyseAllSubjectGradeScore> analyseAllSubjectGradeScores){
        analyseAllSubjectGradeScores.forEach(analyseAllSubjectGradeScore -> analyseAllSubjectGradeScore.setId(sequenceId.nextId()));
        mot.insertAll(analyseAllSubjectGradeScores);
    }
    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    
    /**
              *年级分析总成绩
     * @param analyseClassScore
     */
   public void analyseAllSubjectScore(List<AnalyseClassScoreAll> analyseClassScore,SchoolExam schoolExam) {
	   
   	   mot.remove(query(where("examObj.id").is(schoolExam.getId())),AnalyseAllSubjectGradeScore.class);
	   
	   AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore = new AnalyseAllSubjectGradeScore();
	   
	   Double minScore = analyseClassScore.stream().min((o1, o2) -> o1.getMinMarks().compareTo(o2.getMinMarks())).get().getMinMarks();
	   analyseAllSubjectGradeScore.setMinMarks(minScore);
	   Double maxScore = analyseClassScore.stream().max((o1, o2) -> o1.getMaxMarks().compareTo(o2.getMaxMarks())).get().getMaxMarks();
	   analyseAllSubjectGradeScore.setMaxMarks(maxScore);
	   
	   analyseAllSubjectGradeScore.setAvgMarks(analyseClassScore.get(0).getAvgMarks());
	   analyseAllSubjectGradeScore.setExamObj(analyseClassScore.get(0).getExamObj());
	   analyseAllSubjectGradeScore.setFullMarks(analyseClassScore.get(0).getFullMarks());
	   
	   saveAnalyseAllSubjectGradeScore(analyseAllSubjectGradeScore);
   }
   
	   
}
