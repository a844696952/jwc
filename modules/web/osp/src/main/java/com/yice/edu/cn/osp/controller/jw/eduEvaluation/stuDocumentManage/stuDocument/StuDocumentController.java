package com.yice.edu.cn.osp.controller.jw.eduEvaluation.stuDocumentManage.stuDocument;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.StudentScoreListVo;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.student.JwStudentGraduationService;
import com.yice.edu.cn.osp.service.jw.student.StudentFamilyService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherPostService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScoreHistory.AnalysisStudentScoreHistoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/stuDocument")
public class  StuDocumentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherPostService teacherPostService;

    @Autowired
    private JwStudentGraduationService jwStudentGraduationService;

    @Autowired
    private StudentFamilyService studentFamilyService;

    @Autowired
    private JwCourseService jwCourseService;

    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;

    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;

    @Autowired
    private AnalysisStudentScoreHistoryService analysisStudentScoreHistoryService;


    @PostMapping("/find/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息(ps:年级 班级 姓名 学籍号 状态 页码信息)", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student){
        student.setSchoolId(mySchoolId());
        String postId = student.getPostId();
        Teacher teacher = currentTeacher();
        TeacherPost teacherPost = getSelectTeacherPost(postId, teacher);
        if(teacherPost==null|| (StringUtils.isNotEmpty(postId) && Integer.valueOf(postId) > Integer.valueOf(Constant.TEACHER_POST.CLASS_TEACHER))){
            return new ResponseJson(Lists.newArrayList(),0L);
        }
        if(StringUtils.equals(postId,Constant.TEACHER_POST.GRADE_LEADER) && !StringUtils.equals(student.getGradeId(),teacherPost.getGradeId())){
            return new ResponseJson(Lists.newArrayList(),0L);
        }
        if(StringUtils.equals(postId,Constant.TEACHER_POST.CLASS_TEACHER) &&(!StringUtils.equals(student.getGradeId(),teacherPost.getGradeId())|| !StringUtils.equals(student.getClassesId(),teacherPost.getClassId()))){
            return new ResponseJson(Lists.newArrayList(),0L);
        }
        List<Student> data=studentService.findStudentListByCondition(student);
        long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }

    private TeacherPost getSelectTeacherPost(String postId, Teacher teacher) {
        TeacherPost teacherPostParam = new TeacherPost();
        teacherPostParam.setTeacherId(teacher.getId());
        List<TeacherPost> postList = teacherPostService.findTeacherPostListByCondition(teacherPostParam);
        if(postList!=null) {
            for (TeacherPost teacherPost : postList) {
                if (StringUtils.equals(teacherPost.getDdId(), postId)) {
                    return teacherPost;
                }
            }
        }
        return null;
    }

    @GetMapping("/find/findStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable  String id){
        Student student=studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @GetMapping("/find/findStudentGraduationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findStudentGraduationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        JwStudentGraduation jwStudentGraduation=jwStudentGraduationService.findJwStudentGraduationById(id);
        return new ResponseJson(jwStudentGraduation);
    }

    @GetMapping("/find/findStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson findStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable  String id){
        List<StudentFamily> studentFamily=studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }


    @PostMapping("/find/findJwStudentGraduationsByCondition")
    @ApiOperation(value = "根据条件查找毕业生信息(ps:毕业年份 姓名 学籍号 页码信息)", notes = "返回响应对象")
    public ResponseJson findJwStudentGraduationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwStudentGraduation jwStudentGraduation){
        jwStudentGraduation.setSchoolId(mySchoolId());
        String postId = jwStudentGraduation.getPostId();
        Teacher teacher = currentTeacher();
        TeacherPost teacherPost = getSelectTeacherPost(postId, teacher);
        if(teacherPost==null || (StringUtils.isNotEmpty(postId) && Integer.valueOf(postId) > Integer.valueOf(Constant.TEACHER_POST.GENERAL_MANAGER))){
            return new ResponseJson(Lists.newArrayList(),0L);
        }
        List<JwStudentGraduation> data=jwStudentGraduationService.findJwStudentGraduationListByCondition(jwStudentGraduation);
        long count=jwStudentGraduationService.findJwStudentGraduationCountByCondition(jwStudentGraduation);
        return new ResponseJson(data,count);
    }

    @PostMapping("/find/findSubjectByGradeIdAndStudentId")
    @ApiOperation(value = "通过年级Id查找科目信息", notes = "返回响应对象")
    public ResponseJson findSubjectByGradeIdAndStudentId(@RequestBody StudentScoreListVo studentScoreListVo){
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        List<JwCourse> jwCourseList = jwCourseService.findJwCourseListByCondition(jwCourse);

        StudentScoreListVo  studentScoreListVoParam = new StudentScoreListVo();
        studentScoreListVoParam.setGradeId(studentScoreListVo.getGradeId());
        studentScoreListVoParam.setStudentId(studentScoreListVo.getStudentId());
        Iterator<JwCourse> iterator = jwCourseList.iterator();
        while(iterator.hasNext()){
            JwCourse course = iterator.next();
            studentScoreListVoParam.setSubjectId(course.getId());
            long count = analysisStudentScoreService.findStudentScoresCountByCondition(studentScoreListVoParam);
            if(count>0L){
                continue;
            }
           iterator.remove();
        }
        return new ResponseJson(jwCourseList);
    }

    @PostMapping("/find/findGraduationSubjectByGradeIdAndStudentId")
    @ApiOperation(value = "通过年级Id查找毕业生所参加科目信息", notes = "返回响应对象")
    public ResponseJson findGraduationSubjectByGradeIdAndStudentId(@RequestBody StudentScoreListVo studentScoreListVo){
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(mySchoolId());
        List<JwCourse> jwCourseList = jwCourseService.findJwCourseListByCondition(jwCourse);

        StudentScoreListVo  studentScoreListVoParam = new StudentScoreListVo();
        studentScoreListVoParam.setGradeId(studentScoreListVo.getGradeId());
        Iterator<JwCourse> iterator = jwCourseList.iterator();
        while(iterator.hasNext()){
            JwCourse course = iterator.next();
            studentScoreListVoParam.setSubjectId(course.getId());
            studentScoreListVoParam.setStudentId(studentScoreListVo.getStudentId());
            if(analysisStudentScoreHistoryService.findStudentScoresCountByCondition(studentScoreListVoParam)>0L){
                continue;
            }
            iterator.remove();
        }
        return new ResponseJson(jwCourseList);
    }

    @PostMapping("/find/findGraduationStudentScoresByCondition")
    @ApiOperation(value = "查看学生考试成绩列表", notes = "返回响应对象")
    public ResponseJson findGraduationStudentScoresByCondition(
            @ApiParam(value = "属性不为空则作为条件查询") @RequestBody StudentScoreListVo studentScoreListVo){
        List<AnalysisStudentScoreHistory> studentExamScoreList = analysisStudentScoreHistoryService.findStudentScoresByCondition(studentScoreListVo);
        List<StudentScoreListVo> studentScoreListVoList = Lists.newArrayList();
        for (AnalysisStudentScoreHistory analysisStudentScoreHistory : studentExamScoreList) {
            AnalyseClassScore analyseClassScore = getAnalyseClassScore(analysisStudentScoreHistory);
            StudentScoreListVo scoreListVo = getStudentScoreListVo(analysisStudentScoreHistory, analyseClassScore);
            studentScoreListVoList.add(scoreListVo);
        }
        long count=analysisStudentScoreHistoryService.findStudentScoresCountByCondition(studentScoreListVo);
        Double totalScore = getTotalScore(studentScoreListVoList);
        Map<String,Object>map = Maps.newHashMap();
        map.put("list",studentScoreListVoList);
        map.put("totalScore",totalScore);
        return new ResponseJson(map,count);
    }


    @PostMapping("/find/findStudentScoresByCondition")
    @ApiOperation(value = "查看学生考试成绩列表", notes = "返回响应对象")
    public ResponseJson findStudentScoresByCondition(
            @ApiParam(value = "属性不为空则作为条件查询") @RequestBody StudentScoreListVo studentScoreListVo){
        List<AnalysisStudentScore> studentExamScoreList = analysisStudentScoreService.findStudentScoresByCondition(studentScoreListVo);
        List<StudentScoreListVo> studentScoreListVoList = Lists.newArrayList();
        for (AnalysisStudentScore analysisStudentScore : studentExamScoreList) {
            AnalyseClassScore analyseClassScore = getAnalyseClassScore(analysisStudentScore);
            StudentScoreListVo scoreListVo = getStudentScoreListVo(analysisStudentScore, analyseClassScore);
            studentScoreListVoList.add(scoreListVo);
        }
        long count=analysisStudentScoreService.findStudentScoresCountByCondition(studentScoreListVo);
        Double totalScore = getTotalScore(studentScoreListVoList);
        Map<String,Object>map = Maps.newHashMap();
        map.put("list",studentScoreListVoList);
        map.put("totalScore",totalScore);
        return new ResponseJson(map,count);
    }

    private Double getTotalScore(List<StudentScoreListVo> studentScoreListVoList) {
        Double totalScore=new Double(0);
        for (StudentScoreListVo scoreListVo : studentScoreListVoList) {
            if(scoreListVo.getTotalScore()>totalScore){
                totalScore = scoreListVo.getTotalScore();
            }
        }
        return totalScore;
    }

    /**
     * 装载学生成绩信息数据结构
     * @param analysisStudentScore
     * @param analyseClassScore
     * @return
     */
    private StudentScoreListVo getStudentScoreListVo(AnalysisStudentScore analysisStudentScore, AnalyseClassScore analyseClassScore) {
        StudentScoreListVo scoreListVo = new StudentScoreListVo();
        scoreListVo.setExamName(analysisStudentScore.getExamination().getName());
        scoreListVo.setExamType(analysisStudentScore.getExamination().getExamTypeName());
        scoreListVo.setExamTime(analysisStudentScore.getExamination().getExamTime());
        scoreListVo.setSchoolYear(analysisStudentScore.getExamination().getFromTo());
        scoreListVo.setTermType(analysisStudentScore.getExamination().getTerm());
        scoreListVo.setScore(analysisStudentScore.getScore());
        scoreListVo.setTotalScore(analysisStudentScore.getSubject().getTotalScore().doubleValue());
        scoreListVo.setAvgScore(analyseClassScore!=null?analyseClassScore.getAvgMarks():null);
        return scoreListVo;
    }

    /**
     * 查询班级成绩分析
     * @param analysisStudentScore
     * @return
     */
    private AnalyseClassScore getAnalyseClassScore(AnalysisStudentScore analysisStudentScore) {
        AnalyseClassScore analyseClassScore = new AnalyseClassScore();
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(analysisStudentScore.getExamination().getId());
        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(analysisStudentScore.getClassObj().getId());
        JwCourse jwCourse = new JwCourse();
        jwCourse.setNameId(analysisStudentScore.getSubject().getNameId());
        analyseClassScore.setClassObj(jwClasses);
        analyseClassScore.setExamObj(schoolExam);
        analyseClassScore.setCourseObj(jwCourse);
        analyseClassScore = analyseClassScoreService.findOneAnalyseClassScoreByCondition(analyseClassScore);
        return analyseClassScore;
    }

}
