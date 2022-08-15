package com.yice.edu.cn.tap.controller.scoreAnalysis;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicType;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.tap.service.scoreAnalysis.AnalyseExamTopicService;
import com.yice.edu.cn.tap.service.scoreAnalysis.AnalyseExamTopicTypeService;
import com.yice.edu.cn.tap.service.scoreAnalysis.PaperQuestionService;
import com.yice.edu.cn.tap.service.scoreAnalysis.analysisKnowledge.AnalyseClassKnowledgeService;
import com.yice.edu.cn.tap.service.scoreAnalysis.analysisKnowledge.AnalyseStuKnowledgeService;
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
@Api(value = "/analyseExam", description = "班级试卷分析模块")
public class AnalyseExamController {
    @Autowired
    private AnalyseClassKnowledgeService analyseClassKnowledgeService;
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;
    @Autowired
    private AnalyseStuKnowledgeService analyseStuKnowledgeService;
    @Autowired
    private PaperQuestionService paperQuestionService;
    @Autowired
    private AnalyseExamTopicTypeService analyseExamTopicTypeService;

    @PostMapping("/findExamTopicAnalysis4Class")
    @ApiOperation(value = "按班级查询考试的小题分析", responseContainer = "List", response = AnalyseExamTopic.class)
    public ResponseJson findExamTopicAnalysis4Class(
            @ApiParam(value = "考试id、班级id、科目id必传,分页看情况是否需要")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseExamTopic analyseExamTopic = new AnalyseExamTopic();
        analyseExamTopic.setExamId(analysisVo.getExaminationId());
        JwCourse jwCourse = new JwCourse();
        jwCourse.setId(analysisVo.getSubjectId());
        analyseExamTopic.setCourseObj(jwCourse);
        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(analysisVo.getClassId());
        analyseExamTopic.setClazzObj(jwClasses);
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("topicTypeName", "topicNum", "fullMarks", "gradeAvgMarks", "classAvgMarks", "classScoreRate");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("topicNum").setSortOrder(Pager.ASC);
        analyseExamTopic.setPager(pager);
        List<AnalyseExamTopic> analyseExamTopicList = analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic).stream().sorted(Comparator.comparing(AnalyseExamTopic::getTopicNum)).collect(Collectors.toList());
        return new ResponseJson(analyseExamTopicList);
    }

    @PostMapping("/findAnalyseExamTopicTypeListByCondition")
    @ApiOperation(value = "根据条件查找考试的题型分析列表", notes = "返回响应对象,不包含总条数",responseContainer = "List", response=AnalyseExamTopicType.class)
    public ResponseJson findAnalyseExamTopicTypeListByCondition(
            @ApiParam(value = "考试id、班级id、科目id必传,分页看情况是否需要")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo){
        AnalyseExamTopicType analyseExamTopicType = new AnalyseExamTopicType();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseExamTopicType.setExamObj(schoolExam);

        JwCourse jwCourse = new JwCourse();
        jwCourse.setId(analysisVo.getSubjectId());
        analyseExamTopicType.setCourseObj(jwCourse);

        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(analysisVo.getClassId());
        analyseExamTopicType.setClazzObj(jwClasses);

        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("topicTypeName","topicArr","classScoreRate","classAvgMarks","gradeAvgMarks","typeId");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("typeId").setSortOrder(Pager.ASC);
        analyseExamTopicType.setPager(pager);
        List<AnalyseExamTopicType> data=analyseExamTopicTypeService.findAnalyseExamTopicTypeListByCondition(analyseExamTopicType);
        return new ResponseJson(data);
    }

    /*======================知识点分析=========start==============*/
    @PostMapping("/findKnowledgeAnalysis4Class")
    @ApiOperation(value = "按科目查询知识点分析-班级", responseContainer = "List", response = AnalyseClassKnowledge.class)
    public ResponseJson findKnowledgeAnalysis4Class(
            @ApiParam(value = "考试id和科目id必传,班级id或者知识点根据需求传递,分页看情况是否需要")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassKnowledge analyseClassKnowledge = new AnalyseClassKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseClassKnowledge.setExamination(schoolExam);
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseClassKnowledge.setCourse(course);
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseClassKnowledge.setKnowledge(knowledgePoint);
        }
        if (StringUtils.isNotEmpty(analysisVo.getClassId())) {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setId(analysisVo.getClassId());
            analyseClassKnowledge.setClazz(jwClasses);
        }
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "clazz", "topicArr", "topicCount", "avgScore", "gradeAvgScore", "getPersent");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("topicArr").setSortOrder(Pager.DESC);
        analyseClassKnowledge.setPager(pager);
        List<AnalyseClassKnowledge> analyseClassKnowledgeList = analyseClassKnowledgeService.findAnalyseClassKnowledgeListByCondition(analyseClassKnowledge);
        return new ResponseJson(analyseClassKnowledgeList);
    }

    @PostMapping("/findWeakKnowledgeAnalysis4Class")
    @ApiOperation(value = "查询班级薄弱知识点", responseContainer = "List", response = AnalyseClassKnowledge.class)
    public ResponseJson findWeakKnowledgeAnalysis4Class(
            @ApiParam(value = "考试id、班级id、科目id必传")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassKnowledge analyseClassKnowledge = new AnalyseClassKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseClassKnowledge.setExamination(schoolExam);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setId(analysisVo.getSubjectId());
        analyseClassKnowledge.setCourse(jwCourse);
        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(analysisVo.getClassId());
        analyseClassKnowledge.setClazz(jwClasses);
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge","getPersent","clazz","course");
        pager.setRangeField("getPersent");
        pager.setRangeArray(new Double[]{0D,30D});
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.ASC);
        analyseClassKnowledge.setPager(pager);
        List<AnalyseClassKnowledge> analyseClassKnowledgeList = analyseClassKnowledgeService.findAnalyseClassKnowledgeListByCondition(analyseClassKnowledge);
        return new ResponseJson(analyseClassKnowledgeList);
    }

    @PostMapping("/findWeakKnowledgeItem4Class")
    @ApiOperation(value = "查询班级某薄弱知识点详情", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findWeakKnowledgeItem4Class(
            @ApiParam(value = "考试id、班级id、科目id、知识点id必传")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseStuKnowledge.setExamination(schoolExam);
        Student student = new Student();
        student.setClassesId(analysisVo.getClassId());
        analyseStuKnowledge.setStudent(student);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setId(analysisVo.getSubjectId());
        analyseStuKnowledge.setCourse(jwCourse);
        KnowledgePoint knowledgePoint = new KnowledgePoint();
        knowledgePoint.setId(analysisVo.getKnowledgeId());
        analyseStuKnowledge.setKnowledge(knowledgePoint);
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "student", "getScore", "getPersent");
        pager.setRangeField("getPersent");
        pager.setRangeArray(new Double[]{0D,30D});
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.ASC);
        analyseStuKnowledge.setPager(pager);
        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeService.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
        return new ResponseJson(analyseStuKnowledgeList);
    }

    @PostMapping("/findWeakKnowledgeItem4One")
    @ApiOperation(value = "查询某个学生薄弱知识点详情", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findWeakKnowledgeItem4One(
            @ApiParam(value = "考试id、学生id、科目id必传")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseStuKnowledge.setExamination(schoolExam);
        Student student = new Student();
        student.setId(analysisVo.getStudentId());
        analyseStuKnowledge.setStudent(student);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setId(analysisVo.getSubjectId());
        analyseStuKnowledge.setCourse(jwCourse);
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "getPersent");
        pager.setRangeField("getPersent");
        pager.setRangeArray(new Double[]{0D,30D});
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.ASC);
        analyseStuKnowledge.setPager(pager);
        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeService.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
        return new ResponseJson(analyseStuKnowledgeList);
    }
    /*======================知识点分析=========end==============*/
    @GetMapping("/findListQuestionListKong/{paperId}")
    @ApiOperation(value = "传递试卷id,获取试卷下所有小题")
    public ResponseJson findListQuestionListKong(
            @PathVariable("paperId") String paperId) {
        Paper paper = new Paper();
        paper.setId(paperId);
        List<PaperQuestion> paperQuestions = paperQuestionService.findListQuestionListKong(paper);
        return new ResponseJson(paperQuestions);
    }

    @GetMapping("/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    @ApiOperation(value = "传递试卷Id和小题Id获取小题详情")
    public ResponseJson findOnePaperTopicsOneKong(
            @PathVariable("paperId") String paperId,
            @PathVariable("topicsId") String topicsId) {
        PaperTopics paperTopics = paperQuestionService.findOnePaperTopicsOneKong(paperId, topicsId);
        return new ResponseJson(paperTopics);
    }
}
