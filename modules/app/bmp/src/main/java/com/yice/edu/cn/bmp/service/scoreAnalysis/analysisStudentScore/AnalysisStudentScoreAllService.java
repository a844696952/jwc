package com.yice.edu.cn.bmp.service.scoreAnalysis.analysisStudentScore;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseClassScoreAllFeign;
import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseClassScoreFeign;
import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreAllFeign;
import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreFeign;
import com.yice.edu.cn.bmp.service.scoreAnalysis.AnalyseClassScoreAllService;
import com.yice.edu.cn.bmp.service.scoreAnalysis.AnalyseClassScoreService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;


@Service
public class AnalysisStudentScoreAllService {
    @Autowired
    private AnalysisStudentScoreAllFeign analysisStudentScoreAllFeign;
    @Autowired
    private AnalysisStudentScoreFeign analysisStudentScoreFeign;
    @Autowired
    private AnalyseClassScoreFeign analyseClassScoreFeign;
    @Autowired
    private AnalyseClassScoreAllFeign analyseClassScoreAllFeign;

    public AnalysisStudentScoreAll findAnalysisStudentScoreAllById(String id) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllById(id);
    }

    public AnalysisStudentScoreAll saveAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.saveAnalysisStudentScoreAll(analysisStudentScoreAll);
    }

    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
    }

    public List<AnalysisStudentScoreAll> findAnalysisStudentScoreAllListByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllListByCondition(init(analysisVo));
    }

    public AnalysisStudentScoreAll findOneAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findOneAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }

    public long findAnalysisStudentScoreAllCountByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllCountByCondition(analysisStudentScoreAll);
    }

    public long findAnalysisStudentScoreAllCountByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreAllFeign.findAnalysisStudentScoreAllCountByCondition(init(analysisVo));
    }
    private AnalysisStudentScoreAll init(AnalysisVo analysisVo){
        AnalysisStudentScoreAll analysisStudentScoreAll = new AnalysisStudentScoreAll();
        analysisStudentScoreAll.setSchoolId(mySchoolId());
        if(analysisVo.getClassId()!=null){
            JwClasses classObj = new JwClasses();
            classObj.setId(analysisVo.getClassId());
            analysisStudentScoreAll.setClassObj(classObj);
        }
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analysisStudentScoreAll.setExamination(schoolExam);
        if(analysisVo.getPager()!=null)
            analysisStudentScoreAll.setPager(analysisVo.getPager());
        if(analysisVo.getStudentId()!=null){
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analysisStudentScoreAll.setStudent(student);
        }
        return analysisStudentScoreAll;
    }
    public void updateAnalysisStudentScoreAll(AnalysisStudentScoreAll analysisStudentScoreAll) {
        analysisStudentScoreAllFeign.updateAnalysisStudentScoreAll(analysisStudentScoreAll);
    }

    public void deleteAnalysisStudentScoreAll(String id) {
        analysisStudentScoreAllFeign.deleteAnalysisStudentScoreAll(id);
    }

    public void deleteAnalysisStudentScoreAllByCondition(AnalysisStudentScoreAll analysisStudentScoreAll) {
        analysisStudentScoreAllFeign.deleteAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
    }
    public List<Map<String,Object>> findAnalysisStuScoreAllListByCondition(AnalysisVo analysisVo){
        return analysisStudentScoreAllFeign.findAnalysisStuScoreAllListByCondition(analysisVo);
    }
    public long findAnalysisStuScoreAllCountByCondition(AnalysisVo analysisVo) {
        return analysisStudentScoreAllFeign.findAnalysisStuScoreAllCountByCondition(analysisVo);
    }

    public List<SchoolExam> findSchoolExam4Student(String studentId, SchoolExam schoolExam) {
        return analysisStudentScoreAllFeign.findSchoolExam4Student(studentId,schoolExam);
    }

    public Map<String,Object> findStuAllScoreAnalyse(String examinationId){
        //查询学生总成绩
        AnalysisStudentScoreAll all = new AnalysisStudentScoreAll();
        SchoolExam s = new SchoolExam();
        s.setId(examinationId);
        all.setExamination(s);
        Student student = new Student();
        student.setId(myStudentId());
        all.setStudent(student);
        all.setPager(new Pager().setPaging(false).setIncludes("id","student","total","score","examination","missing","classRanking","totalRanking"));
        all = this.findOneAnalysisStudentScoreAllByCondition(all);
        //获取班级考试情况
        AnalyseClassScoreAll classScoreAll = new AnalyseClassScoreAll();
        classScoreAll.setExamObj(s);
        JwClasses classes = new JwClasses();
        classes.setId(all.getStudent().getClassesId());
        classScoreAll.setClassObj(classes);
        classScoreAll = analyseClassScoreAllFeign.findOneAnalyseClassScoreAllByCondition(classScoreAll);
        Map<String,Object> am = new HashMap<>();
        am.put("total",all.getTotal());
        am.put("examination",all.getExamination());
        am.put("score",all.getScore());
        am.put("classAvgMarks",classScoreAll.getAvgMarks());
        am.put("gradeAvgMarks",classScoreAll.getGradeAvgMarks());
        //查询学生各科成绩
        AnalysisStudentScore a = new AnalysisStudentScore();
        a.setStudent(student);
        a.setExamination(s);
        a.setPager(new Pager().setPaging(false).setIncludes("id","subject","classObj","score","missing","classRanking","totalRanking"));
        List<AnalysisStudentScore> list = analysisStudentScoreFeign.findAnalysisStudentScoreListByCondition(a);
        Map<String,Object> t = new HashMap<>();
        t.put("allScoreMsg",am);
        t.put("listScoreMsg",list);
        return t;
    }
}
