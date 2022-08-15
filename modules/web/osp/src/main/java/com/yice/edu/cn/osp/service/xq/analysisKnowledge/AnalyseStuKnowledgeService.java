package com.yice.edu.cn.osp.service.xq.analysisKnowledge;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.osp.feignClient.xq.analysisKnowledge.AnalyseStuKnowledgeFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyseStuKnowledgeService {
    @Autowired
    private AnalyseStuKnowledgeFeign analyseStuKnowledgeFeign;

    public List<AnalyseStuKnowledge> findAnalyseStuKnowledgeListByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
    }

    public ResponseJson findAnalyseStuKnowledgeListByCondition(AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            SchoolExam schoolExam = new SchoolExam();
            schoolExam.setId(analysisVo.getExaminationId());
            analyseStuKnowledge.setExamination(schoolExam);
        }else {
            return new ResponseJson(false,"考试id不能为空");
        }
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            JwCourse course = new JwCourse();
            course.setId(analysisVo.getSubjectId());
            analyseStuKnowledge.setCourse(course);
        }else {
            return new ResponseJson(false,"课程不能为空");
        }
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseStuKnowledge.setKnowledge(knowledgePoint);
        }
        if(StringUtils.isNotEmpty(analysisVo.getStudentId())){
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analyseStuKnowledge.setStudent(student);
        }else{
            return new ResponseJson(false,"学生id不能为空");
        }
        Pager pager = Optional.ofNullable(analysisVo.getPager()).orElse(new Pager().setPaging(false));
        pager.setIncludes("knowledge", "topicArr", "topicCount","getScore", "classAvgScore", "gradeAvgScore", "getPersent");
        pager.setRangeField("getPersent").setRangeArray(new Double[]{0d,30d});
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.DESC);
        analyseStuKnowledge.setPager(pager);
        return new ResponseJson(analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge));
    }

    public long findAnalyseStuKnowledgeCountByCondition(AnalyseStuKnowledge analyseStuKnowledge) {
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeCountByCondition(analyseStuKnowledge);
    }
    public List<AnalyseStuKnowledge> findClassStudentByKnowlegePoint(AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            SchoolExam schoolExam = new SchoolExam();
            schoolExam.setId(analysisVo.getExaminationId());
            analyseStuKnowledge.setExamination(schoolExam);
        }
        if(StringUtils.isNotEmpty(analysisVo.getExaminationId())){
            JwCourse course = new JwCourse();
            course.setId(analysisVo.getSubjectId());
            analyseStuKnowledge.setCourse(course);
        }
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseStuKnowledge.setKnowledge(knowledgePoint);
        }
        if(StringUtils.isNotEmpty(analysisVo.getClassId())){
            Student student = new Student();
            student.setClassesId(analysisVo.getClassId());
            analyseStuKnowledge.setStudent(student);
        }
        Pager pager = Optional.ofNullable(analysisVo.getPager()).orElse(new Pager()).setPaging(false);
        pager.setIncludes("student", "getPersent", "getScore");
        analyseStuKnowledge.setPager(pager);
        return analyseStuKnowledgeFeign.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
    }

}
