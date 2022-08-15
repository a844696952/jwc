package com.yice.edu.cn.bmp.controller.scoreAnalysis;

import com.yice.edu.cn.bmp.feignClient.scoreAnalysis.AnalyseExamTopicStuFeign;
import com.yice.edu.cn.bmp.service.scoreAnalysis.*;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamStuTopicType;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/analysis")
@Api(value = "/analysis", description = "学生知识点分析及查看试卷小题模块")
public class AnalyseStuKnowledgeController {

    @Autowired
    private AnalyseStuKnowledgeService analyseStuKnowledgeService;

    @Autowired
    private AnalyseExamTopicStuService analyseExamTopicStuService;

    @Autowired
    private PaperQuestionService paperQuestionService;

    @Autowired
    private AnalyseExamStuTopicTypeService analyseExamStuTopicTypeService;


    @PostMapping("/findWeakPointListByCondition")
    @ApiOperation(value = "根据条件查找学生薄弱知识点", notes = "返回学生薄弱知识点列表", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findweakPointListByCondition(
            @ApiParam(value = "考试id:examinationId,课程id:subjectId,学生id:studentId")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        //设置试卷id
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseStuKnowledge.setExamination(schoolExam);
        //设置课程id
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseStuKnowledge.setCourse(course);

        //设置学生id
        if (StringUtils.isNotEmpty(analysisVo.getStudentId())) {
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analyseStuKnowledge.setStudent(student);
        }
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseStuKnowledge.setKnowledge(knowledgePoint);
        }

        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "getPersent");
        pager.setRangeField("getPersent");
        //薄弱知识点 家长端为啥是60%
        Double[] persentArr = {0D,60D};
        pager.setRangeArray(persentArr);
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.ASC);
        analyseStuKnowledge.setPager(pager);
        List<AnalyseStuKnowledge> weakPointList = analyseStuKnowledgeService.findWeakPoint(analyseStuKnowledge);
        return new ResponseJson(weakPointList);
    }


    @PostMapping("/findAnalyseStuKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找学生知识点分析", notes = "返回学生知识点分析列表", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findAnalyseStuKnowledgeByCondition(
            @ApiParam(value = "考试id,课程id,学生id必填")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        //设置试卷id
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseStuKnowledge.setExamination(schoolExam);
        //设置课程id
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseStuKnowledge.setCourse(course);

        //设置学生id
        if (StringUtils.isNotEmpty(analysisVo.getStudentId())) {
            Student student = new Student();
            student.setId(analysisVo.getStudentId());
            analyseStuKnowledge.setStudent(student);
        }
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseStuKnowledge.setKnowledge(knowledgePoint);
        }
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "topicArr", "topicCount", "getPersent", "classAvgScore", "gradeAvgScore", "getScore");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("topicCount").setSortOrder(Pager.DESC);
        analyseStuKnowledge.setPager(pager);
        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeService.findAnalyseStuKnowledge(analyseStuKnowledge);
        return new ResponseJson(analyseStuKnowledgeList);


    }


    //学生个人小题分析
    @PostMapping("/findAnalyseExamTopicStuListByCondition")
    @ApiOperation(value = "根据条件查找学生个人的小题分析列表", notes = "返回响应对象", responseContainer = "List", response=AnalyseExamTopicStu.class)
    public ResponseJson findAnalyseExamTopicStuListByCondition(
            @ApiParam(value = "参数考试id,课程id,学生id必填,班级id选填属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalysisVo analysisVo){

        AnalyseExamTopicStu analyseExamTopicStu = new AnalyseExamTopicStu();

        //设置考试id
        analyseExamTopicStu.setExamId(analysisVo.getExaminationId());
        //设置课程id
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseExamTopicStu.setCourseObj(course);
        //设置个人id
        Student student = new Student();
        student.setId(analysisVo.getStudentId());
        analyseExamTopicStu.setStudent(student);
        //设置班级id
        if (StringUtils.isNotEmpty(analysisVo.getClassId())) {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setId(analysisVo.getClassId());
            analyseExamTopicStu.setClazzObj(jwClasses);
        }
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("topicNum", "topicTypeName", "fullMarks", "score", "topicRate","classAvgMarks");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("topicNum").setSortOrder(Pager.ASC);
        analyseExamTopicStu.setPager(pager);

        List<AnalyseExamTopicStu> data=analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
        return new ResponseJson(data);
    }


    //查看试卷小题及试卷小题详情
    @GetMapping("/findListQuestionListKong/{paperId}")
    @ApiOperation(value = "传递试卷id，获取解开后的所有小题数据", responseContainer = "List", response = PaperQuestion.class)
    public ResponseJson findListQuestionListKong(
            @ApiParam(value = "传递试卷id")
            @PathVariable("paperId") String paperId
    ) {

        Paper paper = new Paper();
        paper.setId(paperId);
        List<PaperQuestion> paperQuestions = paperQuestionService.findListQuestionListKong(paper);
        return new ResponseJson(paperQuestions);
    }

    @GetMapping("/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    @ApiOperation(value = "传递试卷Id和小题Id，获取单个小题数据", response = PaperTopics.class)
    public ResponseJson findOnePaperTopicsOneKong(
            @PathVariable("paperId") String paperId,
            @PathVariable("topicsId") String topicsId
    ) {
        PaperTopics paperTopics = paperQuestionService.findOnePaperTopicsOneKong(paperId, topicsId);
        return new ResponseJson(paperTopics);
    }

    // 学生个人考试题型分析
    @PostMapping("/findAnalyseExamStuTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找学生考试题型分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseExamStuTopicType.class)
    public ResponseJson findAnalyseExamStuTopicTypeListByCondition(
            @ApiParam(value = "参数考试id,课程id,学生id必填,班级id选填属性不为空则作为条件查询")
            @RequestBody AnalysisVo analysisVo){
        AnalyseExamStuTopicType analyseExamStuTopicType = new AnalyseExamStuTopicType();
        //设置考试id
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseExamStuTopicType.setExamObj(schoolExam);
        //设置课程id
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseExamStuTopicType.setCourseObj(course);
        //设置个人id
        Student student = new Student();
        student.setId(analysisVo.getStudentId());
        analyseExamStuTopicType.setStudent(student);
        //设置班级id
        if (StringUtils.isNotEmpty(analysisVo.getClassId())) {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setId(analysisVo.getClassId());
            analyseExamStuTopicType.setClazzObj(jwClasses);
        }
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("topicRate","topicTypeName", "topicArr", "getTopicScore", "classAvgMarks", "gradeAvgMarks","typeId");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("typeId").setSortOrder(Pager.ASC);
        analyseExamStuTopicType.setPager(pager);
        List<AnalyseExamStuTopicType> data=analyseExamStuTopicTypeService.findAnalyseExamStuTopicTypeListByCondition(analyseExamStuTopicType);
        return new ResponseJson(data);
    }

}
