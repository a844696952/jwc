package com.yice.edu.cn.tap.service.scoreAnalysis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.tap.service.exam.SchoolExamService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.tap.feignClient.scoreAnalysis.AnalyseClassScoreFeign;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;

@Service
public class AnalyseClassScoreService {
    @Autowired
    private AnalyseClassScoreFeign analyseClassScoreFeign;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolExamService schoolExamService;

    public AnalyseClassScore findAnalyseClassScoreById(String id) {
        return analyseClassScoreFeign.findAnalyseClassScoreById(id);
    }

    public AnalyseClassScore saveAnalyseClassScore(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.saveAnalyseClassScore(analyseClassScore);
    }

    public List<AnalyseClassScore> findAnalyseClassScoreListByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findAnalyseClassScoreListByCondition(analyseClassScore);
    }

    public AnalyseClassScore findOneAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findOneAnalyseClassScoreByCondition(analyseClassScore);
    }

    public long findAnalyseClassScoreCountByCondition(AnalyseClassScore analyseClassScore) {
        return analyseClassScoreFeign.findAnalyseClassScoreCountByCondition(analyseClassScore);
    }

    public void updateAnalyseClassScore(AnalyseClassScore analyseClassScore) {
        analyseClassScoreFeign.updateAnalyseClassScore(analyseClassScore);
    }

    public void deleteAnalyseClassScore(String id) {
        analyseClassScoreFeign.deleteAnalyseClassScore(id);
    }

    public void deleteAnalyseClassScoreByCondition(AnalyseClassScore analyseClassScore) {
        analyseClassScoreFeign.deleteAnalyseClassScoreByCondition(analyseClassScore);
    }

    /**
     * ??????????????????????????????
     * ?????? ???????????? ?????????????????????????????????????????????
     * @param examId
     * @return
     */
    public Map<String,List<Map<String, String>>> findTeacherClassCourseByExamId(String examId){
        final List<Map<String, String>> lm = teacherService.findTeacherClassCourseByTeacherId(myId());
        if(lm==null || lm.isEmpty()){
            return new HashMap<>();
        }
        //??????????????????
        final SchoolExam schoolExam = schoolExamService.findSchoolExamById(examId);
        //???????????????????????????????????????
        AnalyseClassScore analyseClassScore = new AnalyseClassScore();
        SchoolExam temp = new SchoolExam();
        temp.setId(examId);
        analyseClassScore.setExamObj(temp);
        final List<AnalyseClassScore> aList = this.findAnalyseClassScoreListByCondition(analyseClassScore);
        //???????????????
        Map<String,List<Map<String, String>>> courseMap = lm.stream()//???????????????????????????
                .filter(s -> schoolExam.getClasses().stream().anyMatch(se -> se.getId().equals(s.get("classId")))&&schoolExam.getCourses().stream().anyMatch(se -> se.getId().equals(s.get("courseId"))))
                .filter(s -> aList.stream().anyMatch(se -> se.getClassObj().getId().equals(s.get("classId"))&&se.getCourseObj().getId().equals(s.get("courseId"))))
                .collect(Collectors.groupingBy(s -> s.get("courseName")));
        courseMap.forEach((k,v)->
            v.forEach(m->
                aList.stream().filter(a->a.getCourseObj().getId().equals(m.get("courseId"))&&a.getClassObj().getId().equals(m.get("classId"))).findFirst().ifPresent(s->{
                    m.put("gradeSort",String.valueOf(s.getGradeSort()));
                    m.put("avgMark",String.valueOf(s.getAvgMarks()));
                    m.put("gradeAvgMarks",String.valueOf(s.getGradeAvgMarks()));
                    m.put("totalScore",String.valueOf(s.getCourseObj().getTotalScore()));
                })
            )
        );
        return courseMap;
    }
}
