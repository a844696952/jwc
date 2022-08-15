package com.yice.edu.cn.tap.controller.scoreAnalysis;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

import java.util.*;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.tap.service.schoolYear.SchoolYearService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
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
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.tap.service.classes.JwClassesService;
import com.yice.edu.cn.tap.service.course.JwCourseService;
import com.yice.edu.cn.tap.service.dd.DdService;
import com.yice.edu.cn.tap.service.exam.SchoolExamService;
import com.yice.edu.cn.tap.service.scoreAnalysis.AnalyseClassScoreAllService;
import com.yice.edu.cn.tap.service.scoreAnalysis.AnalyseClassScoreService;
import com.yice.edu.cn.tap.service.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreAllService;
import com.yice.edu.cn.tap.service.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analysis")
@Api(value = "/analyseClassScore", description = "班级全部科目考试分析模块")
public class AnalysisController {
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;
    @Autowired
    private AnalyseClassScoreAllService analyseClassScoreAllService;
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private DdService ddService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private SchoolYearService schoolYearService;

    /**
     * -----------------班级学情分析分析---------------
     **/
    @PostMapping("/findAnalyseClassScoreAllListByCondition")
    @ApiOperation(value = "查找班级考试分析列表--全部科目", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllListByCondition(
            @ApiParam(value = "{examinationId:考试id}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseClassScoreAll.setExamObj(examObj);

        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);

        return new ResponseJson(data);
    }

    @PostMapping("/findAnalyseClassScoreAvg")
    @ApiOperation(value = "班级平均分对比--全部科目", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAvg(
            @ApiParam(value = "{examinationId:考试id}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseClassScoreAll.setExamObj(examObj);

        analyseClassScoreAll.setPager(new Pager().setPaging(false).setIncludes("classObj", "fullMarks", "avgMarks", "gradeSort", "gradeAvgMarks"));

        //按照班号排序
        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        data.sort((AnalyseClassScoreAll s1, AnalyseClassScoreAll s2) -> (Integer.parseInt(s1.getClassObj().getNumber()) >= Integer.parseInt(s2.getClassObj().getNumber()) ? 1 : -1));

        return new ResponseJson(data);
    }

    @PostMapping("/findAnalyseClassScoreGradeSort")
    @ApiOperation(value = "班级年段排名对比--全部科目", notes = "返回响应对象,不包含总条数", response = AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreGradeSort(
            @ApiParam(value = "{examinationId:考试id}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseClassScoreAll.setExamObj(examObj);
        //按照年段排名排序
        analyseClassScoreAll.setPager(new Pager().setPaging(false).setIncludes("examObj", "classObj", "avgMarks", "gradeSort", "excellentObj", "maxMarks", "minMarks").setSortField("gradeSort").setSortOrder("ASC"));

        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);

        return new ResponseJson(data);
    }

    @PostMapping("/findAnalyseClassScoreListByCondition")
    @ApiOperation(value = "查找某个班级某个课程考试分析列表--单个科目", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByCondition(
            @ApiParam(value = "{examinationId:考试id，subjectId:课程id，classId: 班级id}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScore analyseClassScore = new AnalyseClassScore();
        SchoolExam examObj = new SchoolExam();
        examObj.setId(analysisVo.getExaminationId());
        analyseClassScore.setExamObj(examObj);

        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseClassScore.setCourseObj(course);

        JwClasses jwClass = new JwClasses();
        jwClass.setId(analysisVo.getClassId());
        analyseClassScore.setClassObj(jwClass);

        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);

        if (data.isEmpty()) {
            return new ResponseJson(new AnalyseClassScore());
        }

        return new ResponseJson(data.get(0));
    }

    @PostMapping("/findAnalyseClassScoreListByConditionRecentFive")
    @ApiOperation(value = "查找某个班级某个课程最近5次的排名", notes = "返回响应对象,不包含总条数", response = AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByConditionRecentFive(
            @ApiParam(value = "{subjectId:课程id，classId: 班级id}")
            @RequestBody AnalysisVo analysisVo) {
        AnalyseClassScore analyseClassScore = new AnalyseClassScore();

        JwClasses jwClass = new JwClasses();
        jwClass.setId(analysisVo.getClassId());
        analyseClassScore.setClassObj(jwClass);

        JwCourse course = new JwCourse();
        course.setId(analysisVo.getSubjectId());
        analyseClassScore.setCourseObj(course);

        CurSchoolYear curSchoolYear = schoolYearService.findCurSchoolYear(mySchoolId());
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setSchoolYearId(curSchoolYear.getSchoolYearId());
        analyseClassScore.setExamObj(schoolExam);

        analyseClassScore.setPager(new Pager().setPageSize(5).setPage(1).setSortOrder("DESC").setSortField("examObj.examTime").setIncludes("gradeSort", "examObj"));

        List<AnalyseClassScore> data = analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        return new ResponseJson(data);
    }

    @PostMapping("/findAnalyseStuScoreListByConditionRecentFive")
    @ApiOperation(value = "查找某个学生某个课程最近5次的排名", notes = "返回响应对象,不包含总条数", response = AnalysisStudentScore.class)
    public ResponseJson findAnalyseStuScoreListByConditionRecentFive(
            @ApiParam(value = "{subjectId:课程id,studentId:学生id }")
            @RequestBody AnalysisVo analysisVo) {
        Pager page = new Pager();
        page.setPageSize(5);
        page.setPage(1);
        page.setSortOrder("DESC");
        page.setSortField("examination.examTime");
        analysisVo.setPager(page);
        List<AnalysisStudentScore> studentScoreList = analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo);
        return new ResponseJson(studentScoreList);
    }

    /**
     * -----------------end---班级学情分析分析---------------
     **/
    /*======================成绩分析查询相关========start================*/
    @PostMapping("/findSubjectScoreList4Student")
    @ApiOperation(value = "获取单科学生成绩列表", notes = "考试id，科目id必填；其他条件根据需求自行考虑要不要", response = AnalysisStudentScore.class, responseContainer = "List")
    public ResponseJson findSubjectScoreList4Student(
            @ApiParam(value = "考试id，科目id必填；其他条件根据需求自行考虑要不要", required = true)
            @Validated
            @RequestBody AnalysisVo analysisVo) {
        if (analysisVo.getPager() == null) {
            analysisVo.setPager(new Pager().setPaging(false).setIncludes("student", "score", "classRanking", "totalRanking", "missing"));
        } else {
            analysisVo.setPager(analysisVo.getPager().setIncludes("student", "score", "classRanking", "totalRanking", "missing"));
        }
        return new ResponseJson(analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo));
    }

    @PostMapping("/findTotalScoreList4Student")
    @ApiOperation(value = "获取全科目学生总成绩列表", notes = "考试id必填；其他条件根据需求自行考虑要不要,比如班主任要传班级id，科任老师要传班级id和课程id，不一一举例，自行搭配", response = AnalysisStudentScoreAll.class, responseContainer = "List")
    public ResponseJson findTotalScoreList4Student(
            @ApiParam(value = "考试id必填；其他条件根据需求自行考虑要不要", required = true)
            @Validated
            @RequestBody AnalysisVo analysisVo) {
        if (analysisVo.getPager() == null) {
            analysisVo.setPager(new Pager().setPaging(false).setIncludes("student", "score", "classRanking", "totalRanking", "missing"));
        } else {
            analysisVo.setPager(analysisVo.getPager().setIncludes("student", "score", "classRanking", "totalRanking", "missing"));
        }
        return new ResponseJson(analysisStudentScoreAllService.findAnalysisStudentScoreAllListByCondition(analysisVo));
    }

    /*======================成绩分析查询相关========end================*/
    /*======================考试查询相关========start================*/
    @PostMapping("/findSchoolExam4Teacher")
    @ApiOperation(value = "任课老师获取考试列表", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Teacher(@ApiParam(value = "timeMark时间标志：1、近一周 2、近一月 3、本学期 4、本学年 默认本学年")
                                               @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        if (schoolExam.getTimeMark() == null || "".equals(schoolExam.getTimeMark().trim()))
            schoolExam.setTimeMark("4");
        if (StringUtils.isEmpty(String.valueOf(schoolExam.getType())))
            schoolExam.setType(null);
        //根据教师任课课程和班级筛选记录
        schoolExam.setTeacherClassesList(teacherService.findCourse4TeacherClasses(myId()));
        schoolExam.setPager(Optional.ofNullable(schoolExam.getPager()).orElse(new Pager().setPaging(false)).setLike("name").setSortField("examTime").setSortOrder("desc"));
        return new ResponseJson(schoolExamService.findSchoolExamListByConditionForTeacher(schoolExam));
    }

    @PostMapping("/findSchoolExam4Headmaster")
    @ApiOperation(value = "班主任查询", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Headmaster(
            @ApiParam(value = "时间限制同任课老师，多选课程则把选择的课程按对象放入数组作为参数进行请求")
            @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        //获取班主任所教班级
        TeacherClasses teacherClasses = teacherService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
        if (teacherClasses == null || teacherClasses.getGradeId() == null)
            return new ResponseJson(new ArrayList<>());
        schoolExam.setGradeId(teacherClasses.getGradeId());
        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(teacherClasses.getClassesId());
        jwClasses.setEnrollYear(String.valueOf(teacherClasses.getEnrollYear()));
        schoolExam.setClasses(Arrays.asList(jwClasses));
        if (schoolExam.getCourses() != null && schoolExam.getCourses().size() <= 0) {
            schoolExam.setCourses(null);
        }
        schoolExam.setPager(Optional.ofNullable(schoolExam.getPager()).orElse(new Pager().setPaging(false)).setLike("name").setSortField("examTime").setSortOrder("desc"));
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam));
    }

    @PostMapping("/findSchoolExam4Grader")
    @ApiOperation(value = "年段长查询", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4Grader(
            @ApiParam(value = "时间和课程同班主任说明，多选班级类似课程处理")
            @RequestBody SchoolExam schoolExam) {
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        if ((schoolExam.getClasses() == null || schoolExam.getClasses().size() <= 0) && (schoolExam.getCourses() == null || schoolExam.getCourses().size() <= 0)) {
            //获取年段长所在年级
            TeacherClasses teacherClasses = teacherService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
            if (teacherClasses != null && teacherClasses.getGradeId() != null)
                schoolExam.setGradeId(teacherClasses.getGradeId());
            else
                return new ResponseJson(new ArrayList<>());
        }
        schoolExam.setPager(Optional.ofNullable(schoolExam.getPager()).orElse(new Pager().setPaging(false)).setLike("name").setSortField("examTime").setSortOrder(Pager.DESC));
        return new ResponseJson(schoolExamService.findSchoolExamListByCondition2(schoolExam));
    }

    @PostMapping("/findSchoolExam4School")
    @ApiOperation(value = "按学校查询查询-校长啊教务主任啊之类的权限大的人用", response = SchoolExam.class, responseContainer = "List")
    public ResponseJson findSchoolExam4School(
            @ApiParam(value = "时间和课程、班级同年段长说明")
            @RequestBody SchoolExam schoolExam) {
        Pager old = schoolExam.getPager();
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setLike("name");

        //pager.setPage(page);
        //pager.setPageSize(pageSize);
        schoolExam.setPager(pager);
        List<SchoolExam> list = schoolExamService.findSchoolExamListByCondition2(schoolExam);
        if (old != null && list != null) {
            List<SchoolExam> schoolExamList = list.stream().sorted((t1, t2) -> {
                if (t1.getExamTime().equals(t2.getExamTime())) {
                    return  -t1.getId().compareTo(t2.getId());
                }
                return -t1.getExamTime().compareTo(t2.getExamTime());
            }).skip((old.getPage() - 1) * old.getPageSize()).limit(old.getPageSize()).collect(Collectors.toList());
            //两次排序
            //schoolExam中page
            return new ResponseJson(schoolExamList);
        } else {
            //不为null
            //以examTime排序
            if (list != null) {
                List<SchoolExam> collect = list.stream().sorted(Comparator.comparing(SchoolExam::getExamTime).reversed()).collect(Collectors.toList());
                return new ResponseJson(collect);
            } else {
                return new ResponseJson(new ArrayList<>());
            }
        }
    }

    /*======================考试查询相关========end================*/
    /*======================考试条件相关========start================*/
    @GetMapping("/findSchoolExamType")
    @ApiOperation(value = "查询学校考试类型", response = Dd.class, responseContainer = "List")
    public ResponseJson findSchoolExamType() {
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.EXAMINATIONTYPE);
        List<Dd> data = ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }

    @GetMapping("/findTeacherClassByHead")
    @ApiOperation(value = "查询当前班主任所教班级", notes = "查看返回数据中class对应的信息,其他数学看情况忽略", response = TeacherClasses.class)
    public ResponseJson findTeacherClassByHead() {
        return new ResponseJson(teacherService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER));
    }

    @GetMapping("/findTeacherClassByGradeLeader")
    @ApiOperation(value = "查询当前年段长所任年级", notes = "查看返回数据中grade对应的信息,其他数学看情况忽略", response = TeacherClasses.class)
    public ResponseJson findTeacherClassByGradeLeader() {
        return new ResponseJson(teacherService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER));
    }

    @GetMapping("/findSubject4Headmaster")
    @ApiOperation(value = "查询班主任所在班级的课程信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSubject4Headmaster() {
        //获取班主任所教班级
        JwCourse jwCourse = new JwCourse();
        jwCourse.setExam(1);
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        jwCourse.setSchoolId(mySchoolId());
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }

    @GetMapping("/findClass4Grader")
    @ApiOperation(value = "查询年段长所在年级的班级信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findClass4Grader() {
        //获取年段长所在年级
        TeacherClasses teacherClasses = teacherService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
        List<JwClasses> jwClassesList = new ArrayList<>();
        if (teacherClasses != null && teacherClasses.getGradeId() != null) {
            JwClasses jwClasses = new JwClasses();
            jwClasses.setGradeId(teacherClasses.getGradeId());
            jwClasses.setSchoolId(mySchoolId());
            jwClasses.setPager(new Pager().setPaging(false).setIncludes("id", "number", "enrollYear"));
            jwClassesList = jwClassesService.findJwClassesListByCondition(jwClasses);
        }
        return new ResponseJson(jwClassesList);
    }

    @GetMapping("/findSubject4Grader")
    @ApiOperation(value = "查询年段长所在年级的课程信息", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSubject4Grader() {
        //获取年段长所在年级
        JwCourse jwCourse = new JwCourse();
        jwCourse.setExam(1);
        jwCourse.setSchoolId(mySchoolId());
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }

    @GetMapping("/findSchoolGrade")
    @ApiOperation(value = "查询当前学校所有年级", response = Dd.class, responseContainer = "List")
    public ResponseJson findSchoolGrade() {
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        return new ResponseJson(ddService.findDdListByCondition(dd));
    }

    @GetMapping("/findSchoolSubjectByGrade/{gradeId}")
    @ApiOperation(value = "查询年级对应的课程", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findSchoolSubjectByGrade(@ApiParam(value = "年级id") @PathVariable("gradeId") String gradeId) {
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        jwCourse.setGradeId(gradeId);
        jwCourse.setExam(1);
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id", "alias"));
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }

    @GetMapping("/findSchoolClassByGrade/{gradeId}")
    @ApiOperation(value = "查询年级对应的班级", response = JwClasses.class, responseContainer = "List")
    public ResponseJson findSchoolClassByGrade(@ApiParam(value = "年级id") @PathVariable("gradeId") String gradeId) {
        JwClasses jwClasses = new JwClasses();
        jwClasses.setGradeId(gradeId);
        jwClasses.setPager(new Pager().setPaging(false).setIncludes("id", "number", "enrollYear"));
        jwClasses.setSchoolId(mySchoolId());
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(jwClasses));
    }

    @GetMapping("/findTeacherClassCourseByExamId/{examId}")
    @ApiOperation(value = "任课老师该考试中所教授的班级和课程信息", notes = "返回 年级班级列表树结构，第一层")
    public ResponseJson findTeacherClassCourseByExamId(
            @ApiParam(value = "传递考试id")
            @PathVariable("examId") String examId) {
        return new ResponseJson(analyseClassScoreService.findTeacherClassCourseByExamId(examId));
    }

    @GetMapping("/findTeacherClassByTeacher")
    @ApiOperation(value = "通过教师id查询教师所教的班级信息（含年级）", response = JwClasses.class, responseContainer = "List")
    public ResponseJson findTeacherClassByTeacher() {
        return new ResponseJson(teacherService.findTeacherClassByTeacherId(myId()));
    }

    @GetMapping("/findTeacherCourseByClassId/{classId}")
    @ApiOperation(value = "通过教师所教班级的id获取所教课程", notes = "查看返回数据中grade对应的信息,其他数学看情况忽略", response = JwCourse.class, responseContainer = "List")
    public ResponseJson findTeacherCourseByTeacherClassId(@PathVariable String classId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setTeacherId(myId());
        teacherClasses.setClassesId(classId);
        List<JwCourse> res = teacherService.findCourse4TeacherClasses(teacherClasses).stream()
                .map(tcc -> {
                    JwCourse jc = new JwCourse();
                    jc.setName(tcc.getCourseName());
                    jc.setId(tcc.getCourseId());
                    return jc;
                }).collect(Collectors.toList());
        return new ResponseJson(res);
    }
    /*======================考试条件相关========end================*/
}
