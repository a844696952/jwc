package com.yice.edu.cn.osp.controller.xq.analysis;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.vo.ExamTopicQueryVo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.vo.ExportExamTopicViewVo;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseClassKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseGradeKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisKnowledge.AnalyseStuKnowledge;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperQuestionService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.SchoolExamService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.StuScoreService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreAllService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamTopicService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamTopicStuService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseSubjectGradeScoreService;
import com.yice.edu.cn.osp.service.xq.analysisKnowledge.AnalyseClassKnowledgeService;
import com.yice.edu.cn.osp.service.xq.analysisKnowledge.AnalyseGradeKnowledgeService;
import com.yice.edu.cn.osp.service.xq.analysisKnowledge.AnalyseStuKnowledgeService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreAllService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analysis")
@Api(value = "/analysis", description = "学情分析相关文档")
public class AnalysisController {
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private AnalyseClassScoreAllService analyseClassScoreAllService;
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private StuScoreService stuScoreService;
    @Autowired
    private AnalyseGradeKnowledgeService analyseGradeKnowledgeService;
    @Autowired
    private AnalyseClassKnowledgeService analyseClassKnowledgeService;
    @Autowired
    private AnalyseStuKnowledgeService analyseStuKnowledgeService;
    @Autowired
    private AnalyseSubjectGradeScoreService analyseSubjectGradeScoreService;
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;
    @Autowired
    private PaperQuestionService paperQuestionService;
    @Autowired
    private AnalyseExamTopicStuService analyseExamTopicStuService;
    @Autowired
    private SchoolYearService schoolYearService;
    

    /*====================学生考试成绩分析===start===============*/
    @GetMapping("/look/analysisStudentScore/{examinationId}")
    @ApiOperation(value = "按考试id生成学期分析数据")
    public ResponseJson analysisStudentScore(@PathVariable String examinationId) {
        analysisStudentScoreService.analysisStudentScore(examinationId);
        return new ResponseJson();
    }

    @PostMapping("/look/findAnalysisStudentScores")
    @ApiOperation(value = "根据条件查找学生成绩列表-单科考试", notes = "返回成绩列表", response = AnalysisStudentScore.class)
    public ResponseJson findAnalysisStudentScores(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalysisVo analysisVo) {
        List<AnalysisStudentScore> data = analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo);
        long count = analysisStudentScoreService.findAnalysisStudentScoreCountByCondition(analysisVo);
        return new ResponseJson(data, count);
    }

    @PostMapping("/look/findAnalysisStudentScoreAllsByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-总成绩", notes = "返回响应对象", response = AnalysisStudentScoreAll.class)
    public ResponseJson findAnalysisStudentScoreAllsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalysisVo analysisVo) {
        List<Map<String, Object>> data = analysisStudentScoreAllService.findAnalysisStuScoreAllListByCondition(analysisVo);
        long count = analysisStudentScoreAllService.findAnalysisStuScoreAllCountByCondition(analysisVo);
        return new ResponseJson(data, count);
    }

    @PostMapping("/look/findMissingStudentBySubject")
    @ApiOperation(value = "根据条件查找缺考学生", notes = "返回响应对象", response = AnalysisStudentScore.class, responseContainer = "List")
    public ResponseJson findMissingStudentBySubject(@RequestBody AnalysisVo analysisVo) {
        return new ResponseJson(analysisStudentScoreService.findMissingStudentBySubject(analysisVo));
    }

    /*====================学生考试成绩分析====end==============*/
    /*======================查询条件相关=====start============*/
    @GetMapping("/look/findTeacherClassByTeacher")
    @ApiOperation(value = "通过教师id查询教师所教的班级信息（含年级）", response = JwClasses.class, responseContainer = "List")
    public ResponseJson findTeacherClassByTeacher() {
        return new ResponseJson(teacherClassesService.findTeacherClassByTeacherId(myId()));
    }

    @GetMapping("/look/findTeacherClassByHead")
    @ApiOperation(value = "查询当前班主任所教班级", notes = "查看返回数据中class对应的信息,其他数学看情况忽略", response = TeacherClasses.class)
    public ResponseJson findTeacherClassByHead() {
        return new ResponseJson(teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER));
    }

    @GetMapping("/look/findTeacherClassByGradeLeader")
    @ApiOperation(value = "查询当前年段长所任年级", notes = "查看返回数据中grade对应的信息,其他数学看情况忽略", response = TeacherClasses.class)
    public ResponseJson findTeacherClassByGradeLeader() {
        return new ResponseJson(teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER));
    }

    @GetMapping("/look/findTeacherCourseByClassId/{classId}")
    @ApiOperation(value = "通过教师所教班级的id获取所教课程", notes = "查看返回数据中grade对应的信息,其他数学看情况忽略", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findTeacherCourseByTeacherClassId(@PathVariable String classId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setClassesId(classId);
        List<JwCourse> res = teacherClassesService.findCourse4TeacherClasses(teacherClasses).stream()
                .map(tcc -> {
                    JwCourse jc = new JwCourse();
                    jc.setId(tcc.getCourseId());
                    jc.setName(tcc.getCourseName());
                    return jc;
                }).collect(Collectors.toList());
        return new ResponseJson(res);
    }

    @GetMapping("/look/findClass4Headmaster")
    @ApiOperation(value = "查询班主任所在班级", response = TeacherClasses.class)
    public ResponseJson findClass4Headmaster() {
        //获取班主任所教班级
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
        return new ResponseJson(teacherClasses);
    }

    @GetMapping("/look/findSubject4Headmaster")
    @ApiOperation(value = "查询班主任所在班级的课程信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSubject4Headmaster() {
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        if (teacherClasses != null && teacherClasses.getGradeId() != null) {
            jwCourse.setGradeId(teacherClasses.getGradeId());
            jwCourse.setSchoolId(mySchoolId());
            return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
        }
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/look/findGrade4Grader")
    @ApiOperation(value = "查询年段长所在年级", response = TeacherClasses.class)
    public ResponseJson findGrade4Grader() {
        //获取班主任所教班级
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
        return new ResponseJson(teacherClasses);
    }

    @GetMapping("/look/findClass4Grader")
    @ApiOperation(value = "查询年段长所在年级的班级信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findClass4Grader() {
        //获取年段长所在年级
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.setPager(new Pager().setPaging(false).setIncludes("id", "number", "enrollYear"));
        if (teacherClasses != null && teacherClasses.getGradeId() != null) {
            jwClasses.setGradeId(teacherClasses.getGradeId());
            new ResponseJson(jwClassesService.findJwClassesListByCondition(jwClasses));
        }
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/look/findSubject4Grader")
    @ApiOperation(value = "查询年段长所在年级的课程信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSubject4Grader() {
        //获取年段长所在年级
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        if (teacherClasses != null && teacherClasses.getGradeId() != null) {
            jwCourse.setGradeId(teacherClasses.getGradeId());
            return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
        }
        return new ResponseJson(new ArrayList<>());
    }

    @GetMapping("/look/findSchoolSubjectByGrade/{gradeId}")
    @ApiOperation(value = "查询年级对应的课程", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSchoolSubjectByGrade(@ApiParam(value = "年级id") @PathVariable("gradeId") String gradeId) {
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        jwCourse.setGradeId(gradeId);
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }

    @GetMapping("/look/findSchoolClassByGrade/{gradeId}")
    @ApiOperation(value = "查询年级对应的班级", response = JwClasses.class, responseContainer = "List")
    public ResponseJson findSchoolClassByGrade(@ApiParam(value = "年级id") @PathVariable("gradeId") String gradeId) {
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.setPager(new Pager().setPaging(false).setIncludes("id", "number", "enrollYear"));
        jwClasses.setGradeId(gradeId);
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(jwClasses));
    }

    /*======================查询条件相关=====end============*/
    /*======================班级分析====start=================*/
    @GetMapping("/look/analyseClassScore/{examinationId}")
    @ApiOperation(value = "按考试id生成班级学期分析数据")
    public ResponseJson analyseClassScore(@PathVariable String examinationId) {
        analyseClassScoreAllService.analyseClassScore(examinationId);
        return new ResponseJson();
    }

    /*======================班级分析====end=================*/
    /*======================查询班级分析====start=================*/
    @PostMapping("/look/findAnalyseClassScoreListByCondition")
    @ApiOperation(value = "根据条件查找班级某个科目考试分析列表--单个课程", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByCondition(
            @ApiParam(value = "classObj.id:班级id(可选),examObj.id:考试id,courseObj.id:课程id,pager:{sortField:gradeSort(排序字段名称),sortOrder:DESC/ASC(降序/升序)}")
            @RequestBody AnalyseClassScore analyseClassScore) {
        if (analyseClassScore.getPager() == null) {
            Pager pager = new Pager();
            pager.setPaging(false).setSortField("gradeSort").setSortOrder("ASC");
            analyseClassScore.setPager(pager);
        } else {
            analyseClassScore.getPager().setPaging(false);
        }

        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);

        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreListByTeacher")
    @ApiOperation(value = "根据任课老师查找班级某个科目考试分析列表--单个课程", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByTeacher(
            @ApiParam(value = "classObj.gradeId:年级id,examObj.id:考试id,courseObj.id:课程id,pager:{sortField:gradeSort(排序字段名称),sortOrder:DESC/ASC(降序/升序)}")
            @RequestBody AnalyseClassScore analyseClassScore) {
        if (analyseClassScore.getPager() == null) {
            Pager pager = new Pager();
            pager.setPaging(false).setSortField("gradeSort").setSortOrder("ASC");
            analyseClassScore.setPager(pager);
        } else {
            analyseClassScore.getPager().setPaging(false);
        }

        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);

        TeacherClassVo vo = new TeacherClassVo();
        vo.setCourseId(analyseClassScore.getCourseObj().getId());
        vo.setTeacherId(myId());
        vo.setGradeId(analyseClassScore.getClassObj().getGradeId());
        List<TeacherClasses> teacherList = teacherClassesService.findClassesByTeacherCourse(vo);
//        Map<String,TeacherClasses> teacherClassMap = teacherList.stream().collect(Collectors.toMap(TeacherClasses::getClassesId, teacherClasses->teacherClasses));
        //过滤当前老师教的哪些班级
        data = data.stream().filter(analyseClassScoreModel -> teacherList.stream().anyMatch(teacherListModel -> teacherListModel.getClassesId().equals(analyseClassScoreModel.getClassObj().getId()))).collect(Collectors.toList());

        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreListByExamId")
    @ApiOperation(value = "班主任查找班级全部考试课程统计", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByExamId(
            @ApiParam(value = "examObj.id:考试id,classObj.id:班级id")
            @RequestBody AnalyseClassScore analyseClassScore) {
        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreAllListByCondition")
    @ApiOperation(value = "段长/校长--全部课程统计", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllListByCondition(
            @ApiParam(value = "examObj.id:考试id,pager:{sortField:gradeSort(排序字段名称),sortOrder:DESC/ASC(降序/升序)}")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll) {
        if (analyseClassScoreAll.getPager() == null) {
            Pager pager = new Pager();
            pager.setPaging(false).setSortField("gradeSort").setSortOrder("ASC");
            analyseClassScoreAll.setPager(pager);
        } else {
            analyseClassScoreAll.getPager().setPaging(false);
        }

        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreAllRecent")
    @ApiOperation(value = "查询最近的考试年段平均分分析", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllRecent(
            @ApiParam(value = "{gradeId:年级id(选填),examTypeId:考试类型(选填)}")
            @RequestBody SchoolExam schoolExam) {
    	//当前学年
    	CurSchoolYear  curSchoolYear  = schoolYearService.findCurSchoolYear(mySchoolId());
    	schoolExam.setSchoolYearId(curSchoolYear.getSchoolYearId());
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        schoolExam.setPager(new Pager().setSortField("examTime").setSortOrder("DESC").setPageSize(1));
        List<SchoolExam> schoolExamRecentList = schoolExamService.findSchoolExamListByCondition(schoolExam);

        if (schoolExamRecentList.isEmpty()) {
            return new ResponseJson();
        }

        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam schoolExamObj = new SchoolExam();
        schoolExamObj.setId(schoolExamRecentList.get(0).getId());
        analyseClassScoreAll.setExamObj(schoolExamObj);

        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreAllGradeSort")
    @ApiOperation(value = "全部课程某一场考试年段平均分分析", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllGradeSort(
            @ApiParam(value = "{id:考试id}")
            @RequestBody SchoolExam schoolExam) {

        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam schoolExamObj = new SchoolExam();
        schoolExamObj.setId(schoolExam.getId());
        analyseClassScoreAll.setExamObj(schoolExamObj);

        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        //按照班级排序
        data.sort((AnalyseClassScoreAll s1, AnalyseClassScoreAll s2) -> (Integer.parseInt(s1.getClassObj().getNumber()) >= Integer.parseInt(s2.getClassObj().getNumber()) ? 1 : -1));

        return new ResponseJson(data);
    }

    @PostMapping("/look/findStuScoreByScoreRange")
    @ApiOperation(value = "查询某一分数段的学生的信息", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findStuScoreByScoreRange(
            @ApiParam(value = "{clazz.id:班级id,schoolExamId:考试id,course.id:课程id(非必填),minVal:最小分数(非必填),maxVal:最大分数(非必填)}")
            @RequestBody StuScore stuScore) {

        List<StuScore> data = stuScoreService.findStudentByScoreSection(stuScore);
        return new ResponseJson(data);
    }

    @PostMapping("/look/findAnalyseClassScoreListByConditionRecent")
    @ApiOperation(value = "查找某个班级某个课程最近某次的排名", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByConditionRecent(
            @ApiParam(value = "{subjectId:课程id,classId: 班级id,pager:{pageSize:最近的条数}}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScore analyseClassScore = new AnalyseClassScore();

        JwClasses jwClass = new JwClasses();
        jwClass.setId(analysisVo.getClassId());
        analyseClassScore.setClassObj(jwClass);

        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseClassScore.setCourseObj(course);
        if (analysisVo.getPager() == null) {
            analyseClassScore.setPager(new Pager());
        } else {
            analyseClassScore.setPager(analysisVo.getPager());
        }

        analyseClassScore.getPager().setSortOrder("DESC");
        analyseClassScore.getPager().setSortField("examObj.examTime");

        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        return new ResponseJson(data);
    }

    @PostMapping("/look/findGradeSubjectAnalyse")
    @ApiOperation(value = "各科统计接口", notes = "返回响应对象,不包含总条数", response = AnalyseSubjectGradeScore.class)
    public ResponseJson findGradeSubjectAnalyse(
            @ApiParam(value = "{examinationId:考试id}")
            @RequestBody AnalysisVo analysisVo) {

        AnalyseSubjectGradeScore analyseSubjectGradeScore = new AnalyseSubjectGradeScore();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseSubjectGradeScore.setExamObj(examObj);
        if (analysisVo.getSubjectId() != null) {
            JwCourse courseObj = new JwCourse();
            courseObj.setId(analysisVo.getSubjectId());
            analyseSubjectGradeScore.setCourseObj(courseObj);
        }
        List<AnalyseSubjectGradeScore> data = analyseSubjectGradeScoreService.findAnalyseSubjectGradeScoreListByCondition(analyseSubjectGradeScore);

        return new ResponseJson(data);
    }

    @PostMapping("/look/findGradeSubjectAnalyseInfo")
    @ApiOperation(value = "年段/校长 查询单个科目全年级分析 参考、缺考人数和卷面总分", notes = "返回响应对象,不包含总条数", response = AnalyseSubjectGradeScore.class)
    public ResponseJson findGradeSubjectAnalyseInfo(
            @ApiParam(value = "{examinationId:考试id,subjectId:科目id}")
            @RequestBody AnalysisVo analysisVo) {

        AnalyseSubjectGradeScore analyseSubjectGradeScore = new AnalyseSubjectGradeScore();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseSubjectGradeScore.setExamObj(examObj);
        JwCourse courseObj = new JwCourse();
        courseObj.setId(analysisVo.getSubjectId());
        analyseSubjectGradeScore.setCourseObj(courseObj);
        List<AnalyseSubjectGradeScore> data = analyseSubjectGradeScoreService.findAnalyseSubjectGradeScoreListByCondition(analyseSubjectGradeScore);

        return new ResponseJson(data);
    }

    @PostMapping("/look/findTopicAnalyse")
    @ApiOperation(value = "年级小题分析查询", notes = "返回响应对象,不包含总条数", response = AnalyseExamTopic.class)
    public ResponseJson findTopicAnalyse(
            @ApiParam(value = "{examinationId:考试id,subjectId:科目id,topicNum:题目序号(非必填)}")
            @RequestBody AnalysisVo analysisVo) {

        AnalyseExamTopic analyseExamTopic = new AnalyseExamTopic();
        analyseExamTopic.setExamId(analysisVo.getExaminationId());
        JwCourse courseObj = new JwCourse();
        courseObj.setId(analysisVo.getSubjectId());
        analyseExamTopic.setCourseObj(courseObj);
        if (analysisVo.getTopicNum() != null) {
            analyseExamTopic.setTopicNum(analysisVo.getTopicNum());
        }
        List<AnalyseExamTopic> data = analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic);

        Map<Integer, List<AnalyseExamTopic>> analyseExamTopicListMap = data.stream().collect(Collectors.groupingBy(x -> x.getTopicNum().intValue()));

        List<AnalyseExamTopic> returnData = new ArrayList<AnalyseExamTopic>();
        analyseExamTopicListMap.forEach((k, v) -> {
            AnalyseExamTopic model = v.get(0);
            model.setClazzObj(null);
            returnData.add(model);
        });

        return new ResponseJson(returnData);
    }

    @PostMapping("/look/findTopicAnalyseByClass")
    @ApiOperation(value = "班级小题分析查询", notes = "返回响应对象,不包含总条数", response = AnalyseExamTopic.class)
    public ResponseJson findTopicAnalyseByClass(
            @ApiParam(value = "{examinationId:考试id,subjectId:科目id,classId:班级id(非必填)},topicNum:题号(非必填)")
            @RequestBody AnalysisVo analysisVo) {

        AnalyseExamTopic analyseExamTopic = new AnalyseExamTopic();
        analyseExamTopic.setExamId(analysisVo.getExaminationId());

        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseExamTopic.setCourseObj(course);

        if (analysisVo.getClassId() != null) {
            JwClasses clazzObj = new JwClasses();
            clazzObj.setId(analysisVo.getClassId());
            analyseExamTopic.setClazzObj(clazzObj);
        }
        if (analysisVo.getTopicNum() != null) {
            analyseExamTopic.setTopicNum(analysisVo.getTopicNum());
        }
        List<AnalyseExamTopic> data = analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic);

        return new ResponseJson(data);
    }

    @PostMapping("/look/findTopicAnalyseByStu")
    @ApiOperation(value = "学生个人小题分析查询", notes = "返回响应对象,不包含总条数", response = AnalyseExamTopic.class)
    public ResponseJson findTopicAnalyseByStu(
            @ApiParam(value = "{examinationId:考试id(必填),subjectId:科目id(必填),studentId:学生id(必填),classId:班级id(必填)}")
            @RequestBody AnalysisVo analysisVo) {

        AnalyseExamTopicStu analyseExamTopicStu = new AnalyseExamTopicStu();
        analyseExamTopicStu.setExamId(analysisVo.getExaminationId());

        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseExamTopicStu.setCourseObj(course);
        
        Student student = new Student();
        student.setId(analysisVo.getStudentId());
        analyseExamTopicStu.setStudent(student);
        
        List<AnalyseExamTopicStu> data = analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);

        AnalyseExamTopic queryAnalyseExamTopic = new AnalyseExamTopic();
        queryAnalyseExamTopic.setExamId(analysisVo.getExaminationId());
        queryAnalyseExamTopic.setCourseObj(course);

        JwClasses clazzObj = new JwClasses();
        clazzObj.setId(analysisVo.getClassId());
        queryAnalyseExamTopic.setClazzObj(clazzObj);
        
        List<AnalyseExamTopic> analyseExamTopicList = analyseExamTopicService.findAnalyseExamTopicListByCondition(queryAnalyseExamTopic);
        
        data.stream().map(ss->{
        	AnalyseExamTopic model = analyseExamTopicList.stream().filter(s-> s.getTopicNum().intValue()==ss.getTopicNum().intValue()).findFirst().orElse(null);
        	if(model.getClassScoreRate()!=null) {
        		ss.setClazzRate(model.getClassScoreRate());
        	}
        	return ss;
        }).collect(Collectors.toList());
        
        
        return new ResponseJson(data);
    }
    
    /*======================查询班级分析====end=================*/
    /*======================考试查询相关========start================*/
    @PostMapping("/look/findSchoolExam4Teacher")
    @ApiOperation(value = "任课老师获取考试列表", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Teacher(@ApiParam(value = "timeMark时间标志：1、近一周 2、近一月 3、本学期 4、本学年 默认本学年")
                                               @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
//        if(schoolExam.getTimeMark()==null||"".equals(schoolExam.getTimeMark().trim()))
//            schoolExam.setTimeMark("4");
        if (StringUtils.isEmpty(String.valueOf(schoolExam.getType())))
            schoolExam.setType(null);
        schoolExam.setSchoolId(mySchoolId());
        //根据教师任课课程和班级筛选记录
        if (schoolExam.getClasses() == null || schoolExam.getClasses().size() <= 0) {
            schoolExam.setClasses(teacherClassesService.findTeacherClassByTeacherId(myId()));
        }
        if (schoolExam.getCourses() == null || schoolExam.getCourses().size() <= 0) {
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setTeacherId(myId());
            List<TeacherClassesCourse> course4TeacherClasses = teacherClassesService.findCourse4TeacherClasses(teacherClasses);
            if (course4TeacherClasses!=null){
                schoolExam.setCourses(course4TeacherClasses.stream().map(t -> {
                    JwCourse jwCourse = new JwCourse();
                    jwCourse.setId(t.getCourseId());
                    return jwCourse;
                }).collect(Collectors.toList()));
            }
        }
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam), schoolExamService.findSchoolExamCountByCondition2(schoolExam));
    }

    @PostMapping("/look/findSchoolExam4Headmaster")
    @ApiOperation(value = "班主任查询", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Headmaster(
            @ApiParam(value = "时间限制同任课老师，多选课程则把选择的课程按对象放入数组作为参数进行请求")
            @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        //获取班主任所教班级
        if (schoolExam.getClasses() == null || schoolExam.getClasses().size() <= 0) {
            TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
            if (teacherClasses == null || teacherClasses.getGradeId() == null)
                return new ResponseJson();
            schoolExam.setGradeId(teacherClasses.getGradeId());
            JwClasses jwClasses = new JwClasses();
            jwClasses.setId(teacherClasses.getClassesId());
            jwClasses.setEnrollYear(String.valueOf(teacherClasses.getEnrollYear()));
            schoolExam.setClasses(Arrays.asList(jwClasses));
        }
        schoolExam.setSchoolId(mySchoolId());
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam), schoolExamService.findSchoolExamCountByCondition2(schoolExam));
    }

    @PostMapping("/look/findSchoolExam4Grader")
    @ApiOperation(value = "年段长查询", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Grader(
            @ApiParam(value = "时间和课程同班主任说明，多选班级类似课程处理")
            @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        //获取年段长所在年级
        if ((schoolExam.getClasses() == null || schoolExam.getClasses().size() <= 0) && (schoolExam.getCourses() == null || schoolExam.getCourses().size() <= 0)) {
            TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
            if (teacherClasses == null || teacherClasses.getGradeId() == null)
                return new ResponseJson();
            schoolExam.setGradeId(teacherClasses.getGradeId());
        }
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam), schoolExamService.findSchoolExamCountByCondition2(schoolExam));
    }

    @PostMapping("/look/findSchoolExam4School")
    @ApiOperation(value = "按学校查询查询-校长啊教务主任啊之类的权限大的人用", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4School(
            @ApiParam(value = "时间和课程、班级同年段长说明")
            @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam), schoolExamService.findSchoolExamCountByCondition2(schoolExam));
    }
    /*======================考试查询相关========end================*/

    /*======================知识点分析=========start==============*/
    @PostMapping("/look/findKnowledgeAnalysis4Grade")
    @ApiOperation(value = "按科目查询知识点分析-年级(校长、段长使用)", responseContainer = "List", response = AnalyseGradeKnowledge.class)
    public ResponseJson findKnowledgeAnalysis4Grade(
            @ApiParam(value = "考试id和科目id必传,分页看情况是否需要")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseGradeKnowledge analyseGradeKnowledge = new AnalyseGradeKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseGradeKnowledge.setExamination(schoolExam);
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseGradeKnowledge.setCourse(course);
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "topicArr", "topicCount", "avgScore", "getPersent");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.DESC);
        analyseGradeKnowledge.setPager(pager);
        List<AnalyseGradeKnowledge> analyseGradeKnowledgeList = analyseGradeKnowledgeService.findAnalyseGradeKnowledgeListByCondition(analyseGradeKnowledge);
        Long count = analyseGradeKnowledgeService.findAnalyseGradeKnowledgeCountByCondition(analyseGradeKnowledge);
        return new ResponseJson(analyseGradeKnowledgeList, count);
    }

    @PostMapping("/look/findKnowledgeAnalysis4Class")
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
        if (StringUtils.isNotEmpty(analysisVo.getClassId())) {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setId(analysisVo.getClassId());
            analyseClassKnowledge.setClazz(jwClasses);
        }
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseClassKnowledge.setKnowledge(knowledgePoint);
        }
        Pager pager = analysisVo.getPager() == null ? new Pager().setPaging(false) : analysisVo.getPager();
        pager.setIncludes("knowledge", "clazz", "topicArr", "topicCount" ,"totalScore", "avgScore", "gradeAvgScore", "getPersent","gradePersent","personMan");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.DESC);
        analyseClassKnowledge.setPager(pager);
        List<AnalyseClassKnowledge> analyseClassKnowledgeList = analyseClassKnowledgeService.findAnalyseClassKnowledgeListByCondition(analyseClassKnowledge);
        Long count = analyseClassKnowledgeService.findAnalyseClassKnowledgeCountByCondition(analyseClassKnowledge);
        return new ResponseJson(analyseClassKnowledgeList, count);
    }

    @PostMapping("/look/findListQuestionListKong")
    @ApiOperation(value = "传递试卷对象，获取解开后的所有小题数据", response = PaperQuestion.class, responseContainer = "List")
    public ResponseJson findListQuestionListKong(
            @ApiParam(value = "小题对象")
            @RequestBody Paper paper) {
        List<PaperQuestion> paperQuestions = paperQuestionService.findListQuestionListKong(paper);
        long count = paperQuestionService.findCountQuestionCountKong(paper);
        return new ResponseJson(paperQuestions, count);
    }

    @PostMapping("/look/findKnowledgeAnalysis4Student")
    @ApiOperation(value = "按科目查询知识点分析-学生", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findKnowledgeAnalysis4Student(
            @ApiParam(value = "考试id和科目id，学生id必传,班级id或者知识点根据需求传递,分页看情况是否需要")
            @Validated(GroupOne.class)
            @RequestBody AnalysisVo analysisVo) {
        AnalyseStuKnowledge analyseStuKnowledge = new AnalyseStuKnowledge();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisVo.getExaminationId());
        analyseStuKnowledge.setExamination(schoolExam);
        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseStuKnowledge.setCourse(course);
        Student student = new Student();
        student.setId(analysisVo.getStudentId());
        analyseStuKnowledge.setStudent(student);
        if (StringUtils.isNotEmpty(analysisVo.getKnowledgeId())) {
            KnowledgePoint knowledgePoint = new KnowledgePoint();
            knowledgePoint.setId(analysisVo.getKnowledgeId());
            analyseStuKnowledge.setKnowledge(knowledgePoint);
        }
        Pager pager = Optional.ofNullable(analysisVo.getPager()).orElse(new Pager().setPaging(false));
        pager.setIncludes("knowledge", "topicArr", "totalScore","getScore", "classAvgScore", "classPersent", "getPersent");
        if (StringUtils.isEmpty(pager.getSortField()))
            pager.setSortField("getPersent").setSortOrder(Pager.DESC);
        analyseStuKnowledge.setPager(pager);
        List<AnalyseStuKnowledge> analyseStuKnowledgeList = analyseStuKnowledgeService.findAnalyseStuKnowledgeListByCondition(analyseStuKnowledge);
        Long count = analyseStuKnowledgeService.findAnalyseStuKnowledgeCountByCondition(analyseStuKnowledge);
        return new ResponseJson(analyseStuKnowledgeList, count);
    }
    @PostMapping("/look/findWeakKnowledge4Student")
    @ApiOperation(value = "查询学生薄弱至少点目前是得分率30%以内", responseContainer = "List", response = AnalyseStuKnowledge.class)
    public ResponseJson findWeakKnowledge4Student(
            @ApiParam(value = "考试id和科目id，学生id必传")
            @Validated(GroupTwo.class)
            @RequestBody AnalysisVo analysisVo){
        return analyseStuKnowledgeService.findAnalyseStuKnowledgeListByCondition(analysisVo);
    }
    @PostMapping("/look/findAnalysisStudentScores4Recent")
    @ApiOperation(value = "查询学生近几次考试成绩和班级排名", notes = "返回成绩列表", response = AnalysisStudentScore.class,responseContainer = "List")
    public ResponseJson findAnalysisStudentScores4Recent(
            @ApiParam(value = "传学生id，课程id，然后再pager中添加分页pageSize=n，n为要查询的近几场场数")
            @Validated(GroupTwo.class)
            @RequestBody AnalysisVo analysisVo) {
        //处理下按考试时间倒序，取第一页,默认5场
        Pager pager = analysisVo.getPager()==null?new Pager().setPageSize(5):analysisVo.getPager();
        pager.setPage(1).setSortOrder(Pager.DESC).setSortField("examination.examTime");
        analysisVo.setPager(pager);
        List<AnalysisStudentScore> data = analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo);
        return new ResponseJson(data);
    }
    @PostMapping("/findClassStudentByKnowlegePoint")
    @ApiOperation(value = "通过知识点查询班级学生得分情况")
    public ResponseJson findClassStudentByKnowlegePoint(
            @ApiParam(value = "考试id和科目id，班级id，知识点根据需求传递")
            @Validated(GroupThree.class)
            @RequestBody AnalysisVo analysisVo){
        return new ResponseJson(analyseStuKnowledgeService.findClassStudentByKnowlegePoint(analysisVo));
    }
    /*======================知识点分析=========end==============*/
    
    @PostMapping("/queryExamTopicsByExamIdAndCourseId")
    @ApiOperation(value = "通过考试Id和课程Id、班级id查询考试的所有题目", notes = "要导出的小题列表", response = ExportExamTopicViewVo.class,responseContainer = "List")
    public ResponseJson queryExamTopicsByExamIdAndCourseId(
            @ApiParam(value = "{schoolExamId:考试id,courseId:科目id,clazzId:班级id}")
            @RequestBody ExamTopicQueryVo examTopicQueryVo) {
    	
    	List<PaperQuestion>  paperQuestionList= paperService.findBySchoolExamIdAndCouserId(examTopicQueryVo.getSchoolExamId(), examTopicQueryVo.getCourseId());
    	if(paperQuestionList.isEmpty()) {
    		return new ResponseJson(false,"该试卷没有题目!");
    	}
    	List<PaperTopics> topicsList = new ArrayList<PaperTopics>();
    	for(PaperQuestion paperQuestion:paperQuestionList) {
    		topicsList.addAll(paperQuestion.getTopicsList());
    	}
    	
    	AnalyseExamTopicStu analyseExamTopicStu = new AnalyseExamTopicStu();
    	analyseExamTopicStu.setExamId(examTopicQueryVo.getSchoolExamId());
    	
    	JwCourse queryCourse = new JwCourse();
    	queryCourse.setId(examTopicQueryVo.getCourseId());
    	analyseExamTopicStu.setCourseObj(queryCourse);
    	
    	JwClasses clazz = new JwClasses();
    	clazz.setId(examTopicQueryVo.getClazzId());
    	analyseExamTopicStu.setClazzObj(clazz);
    	
    	List<AnalyseExamTopicStu>  analyseExamTopicStuList = analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
    	Map<Integer,List<AnalyseExamTopicStu>> examTopicStuGroupByTopicNumMap = analyseExamTopicStuList.stream().collect(Collectors.groupingBy(AnalyseExamTopicStu::getTopicNum));
    	
    	List<ExportExamTopicViewVo> viewVoList = new ArrayList<ExportExamTopicViewVo>();
        for(int i = 0 ;i<topicsList.size();i++) {
        	ExportExamTopicViewVo viewVo = new ExportExamTopicViewVo();
        	BeanUtils.copyProperties(topicsList.get(i), viewVo);
        	getStuScoreRate(viewVo,examTopicStuGroupByTopicNumMap.get(i+1),i+1);
        	viewVoList.add(viewVo);
        }
    	
    	return new ResponseJson(viewVoList);
    }
    
    private void getStuScoreRate(ExportExamTopicViewVo viewVo,List<AnalyseExamTopicStu> analyseExamTopicStuList,int topicNumber) {
    	//[0~30]
    	int firstRate=0;
    	//(30~60%]
    	int secondRate=0;
    	//(60~80%]
    	int thirdRate=0;
    	//(80~100%]
    	int fourthRate=0;
    	
    	for(AnalyseExamTopicStu s:analyseExamTopicStuList){
    		if(s.getTopicRate().compareTo(30.0)<=0) {
    			firstRate++;
    		}else if(s.getTopicRate().compareTo(30.0)>0 && s.getTopicRate().compareTo(60.0)<=0) {
    			secondRate++;
    		}else if(s.getTopicRate().compareTo(60.0)>0 && s.getTopicRate().compareTo(80.0)<=0){
    			thirdRate++;
    		}else if(s.getTopicRate().compareTo(80.0)>0 && s.getTopicRate().compareTo(100.0)<=0){
    			fourthRate++;
    		}
    	};
    	viewVo.setFirstRate(firstRate);
    	viewVo.setSecondRate(secondRate);
    	viewVo.setThirdRate(thirdRate);
    	viewVo.setFourthRate(fourthRate);
    }
    
    @PostMapping("/queryExamTopicsNumber")
    @ApiOperation(value = "通过考试Id和课程Id查询考试的所有题目的", notes = "返回成绩列表", response = PaperTopics.class,responseContainer = "List")
    public ResponseJson queryExamTopicsNumber(
            @ApiParam(value = "{schoolExamId:考试id,courseId:科目id}")
            @RequestBody ExamTopicQueryVo examTopicQueryVo) {
    	List<PaperQuestion>  paperQuestionList= paperService.findBySchoolExamIdAndCouserId(examTopicQueryVo.getSchoolExamId(), examTopicQueryVo.getCourseId());
    	if(paperQuestionList.isEmpty()) {
    		return new ResponseJson(false,"该试卷没有题目!");
    	}
    	List<PaperTopics> topicsList = new ArrayList<PaperTopics>();
    	for(PaperQuestion paperQuestion:paperQuestionList) {
    		topicsList.addAll(paperQuestion.getTopicsList());
    	}
        
    	return new ResponseJson(topicsList);
    }
    
    @PostMapping("/queryStuTopicScoreRate")
    @ApiOperation(value = "查询某个班某个科目某个小题的学生分析", notes = "返回成绩列表", response = ExportExamTopicViewVo.class,responseContainer = "List")
    public ResponseJson queryStuTopicScoreRate(
            @ApiParam(value = "{schoolExamId:考试id,clazzId:考试id,courseId:科目id,number:题目序号}",required=true)
            @RequestBody ExamTopicQueryVo examTopicQueryVo) {
        
    	AnalyseExamTopicStu analyseExamTopicStu= new AnalyseExamTopicStu();
    	
    	JwClasses clazz = new JwClasses();
    	clazz.setId(examTopicQueryVo.getClazzId());
    	analyseExamTopicStu.setClazzObj(clazz);
    	
    	JwCourse course = new JwCourse();
    	course.setId(examTopicQueryVo.getCourseId());
    	analyseExamTopicStu.setCourseObj(course);
    	
    	analyseExamTopicStu.setTopicNum(examTopicQueryVo.getNumber());
    	analyseExamTopicStu.setPager(new Pager().setPaging(false));
    	
    	analyseExamTopicStu.setExamId(examTopicQueryVo.getSchoolExamId());
    	List<AnalyseExamTopicStu> dataList = analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
    	
    	return new ResponseJson(dataList);
    }
}


