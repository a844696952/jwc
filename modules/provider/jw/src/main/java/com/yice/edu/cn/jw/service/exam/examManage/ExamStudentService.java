package com.yice.edu.cn.jw.service.exam.examManage;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ExamStudentService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    public ExamStudent findExamStudentById(String id) {
        return mot.findById(id,ExamStudent.class);
    }
    public void saveExamStudent(ExamStudent examStudent) {
        examStudent.setId(sequenceId.nextId());
        mot.insert(examStudent);
    }
    public List<ExamStudent> findExamStudentListByCondition(ExamStudent examStudent) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = MongoKit.buildCriteria(examStudent, examStudent.getPager());
        operations.add(Aggregation.match(criteria));
        MongoKit.sortPageInclude(examStudent.getPager(),operations);
        return mot.aggregate(Aggregation.newAggregation(ExamStudent.class,operations),ExamStudent.class).getMappedResults();

    }
    public long findExamStudentCountByCondition(ExamStudent examStudent) {
        Criteria criteria = MongoKit.buildCriteria(examStudent, examStudent.getPager());
        return mot.count(query(criteria), ExamStudent.class);
    }
    public ExamStudent findOneExamStudentByCondition(ExamStudent examStudent) {
        Example<ExamStudent> example = Example.of(examStudent, UntypedExampleMatcher.matchingAll());
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(new Criteria().alike(example)));
        Pager pager = examStudent.getPager();
        if(pager!=null&&pager.getIncludes()!=null){
            operations.add(Aggregation.project(pager.getIncludes()));
        }
        return mot.aggregate(Aggregation.newAggregation(ExamStudent.class,operations),ExamStudent.class).getUniqueMappedResult();
    }

    public void updateExamStudent(ExamStudent examStudent) {
        mot.updateFirst(query(where("id").is(examStudent.getId())), MongoKit.update(examStudent),ExamStudent.class);
    }
    public void deleteExamStudent(String id) {
        mot.remove(query(where("id").is(id)),ExamStudent.class);
    }
    public void deleteExamStudentByCondition(ExamStudent examStudent) {
        Example<ExamStudent> example = Example.of(examStudent, UntypedExampleMatcher.matchingAll());
        Query query = query(new Criteria().alike(example));
        mot.remove(query, ExamStudent.class);
    }
    public void batchSaveExamStudent(List<ExamStudent> examStudents){
        examStudents.forEach(examStudent -> examStudent.setId(sequenceId.nextId()));
        mot.insertAll(examStudents);
    }
}
