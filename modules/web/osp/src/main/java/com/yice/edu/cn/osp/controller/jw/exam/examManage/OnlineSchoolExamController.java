package com.yice.edu.cn.osp.controller.jw.exam.examManage;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.*;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.answerSheet.AnswerSheetService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.ExamStudentService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.SchoolExamService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.StuScoreService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/onlineSchoolExam")
@Api(value = "/onlineSchoolExam",description = "网阅学校考试模块")
public class OnlineSchoolExamController {
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private DdService ddService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private ExamStudentService examStudentService;
    @Autowired
    private StuScoreService stuScoreService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @PostMapping("/saveSchoolExam")
    @ApiOperation(value = "保存学校考试对象", notes = "返回保存好的学校考试对象", response= SchoolExam.class)
    public ResponseJson saveSchoolExam(
            @ApiParam(value = "学校考试对象", required = true)
            @RequestBody SchoolExam schoolExam){
       schoolExam.setSchoolId(mySchoolId());
       schoolExam.setType(1);
       schoolExam.setCreateTime(DateUtil.now());
       schoolExam.setFinished(false);
        curSchoolYearService.setSchoolYearTerm(schoolExam,mySchoolId());
        for (JwCourse course : schoolExam.getCourses()) {
            course.setAllUpload(false);
        }
        return schoolExamService.saveSchoolExamForOnline(schoolExam);
    }

    @GetMapping("/update/findSchoolExamById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校考试", notes = "返回响应对象", response=SchoolExam.class)
    public ResponseJson findSchoolExamById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolExam schoolExam=schoolExamService.findSchoolExamById(id);
        return new ResponseJson(schoolExam);
    }

    @PostMapping("/update/updateSchoolExam")
    @ApiOperation(value = "修改学校考试对象", notes = "返回响应对象")
    public ResponseJson updateSchoolExam(
            @ApiParam(value = "被修改的学校考试对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolExam schoolExam){
        return schoolExamService.updateSchoolExamForOnline(schoolExam);
    }

    @GetMapping("/look/lookSchoolExamById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校考试", notes = "返回响应对象", response=SchoolExam.class)
    public ResponseJson lookSchoolExamById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolExam schoolExam=schoolExamService.findSchoolExamById(id);
        return new ResponseJson(schoolExam);
    }

    @PostMapping("/findSchoolExamsByCondition")
    @ApiOperation(value = "根据条件查找学校考试", notes = "返回响应对象", response=SchoolExam.class)
    public ResponseJson findSchoolExamsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolExam schoolExam){
       schoolExam.setSchoolId(mySchoolId());
       schoolExam.setType(1);
       curSchoolYearService.setSchoolYearId(schoolExam,mySchoolId());
        List<SchoolExam> data=schoolExamService.findSchoolExamListByCondition(schoolExam);
        long count=schoolExamService.findSchoolExamCountByCondition(schoolExam);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolExamByCondition")
    @ApiOperation(value = "根据条件查找单个学校考试,结果必须为单条数据", notes = "没有时返回空", response=SchoolExam.class)
    public ResponseJson findOneSchoolExamByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolExam schoolExam){
        SchoolExam one=schoolExamService.findOneSchoolExamByCondition(schoolExam);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolExam/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolExam(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        SchoolExam schoolExam =  schoolExamService.deleteSchoolExamById(id);
        paperService.setPaperFlags(schoolExam,false);
        //同时删除考生表数据
        ExamStudent examStudent = new ExamStudent();
        examStudent.setSchoolExamId(id);
        examStudentService.deleteExamStudentByCondition(examStudent);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolExamListByCondition")
    @ApiOperation(value = "根据条件查找学校考试列表", notes = "返回响应对象,不包含总条数", response=SchoolExam.class)
    public ResponseJson findSchoolExamListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolExam schoolExam){
       schoolExam.setSchoolId(mySchoolId());
       schoolExam.setType(1);
        List<SchoolExam> data=schoolExamService.findSchoolExamListByCondition(schoolExam);
        return new ResponseJson(data);
    }
    @GetMapping("/ignore/findExamTypeGradesSchoolYear")
    public ResponseJson findExamTypeGradesSchoolYear(){
        Dd dd = new Dd();
        dd.setTypeId("23");
        List<Dd> examTypes=ddService.findDdListByCondition(dd);
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        List<Dd> grades=ddService.findDdListByCondition(dd);
        return new ResponseJson(examTypes,grades);
    }
    @GetMapping("/ignore/findClassCourseByGradeId/{gradeId}")
    public ResponseJson findClassCourseByGradeId(@PathVariable String gradeId){
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.setGradeId(gradeId);
        List<JwClasses> jwClassesList = jwClassesService.findJwClassessByConditionForExam(jwClasses);
        return new ResponseJson(jwClassesList);
    }
    @GetMapping("/findCoursesForExam")
    public ResponseJson findCoursesForExam(){
        JwCourse course = new JwCourse();
        course.setExam(1);
        course.setSchoolId(mySchoolId());
        List<JwCourse> courses = jwCourseService.findJwCourseListByCondition(course);
        return new ResponseJson(courses);
    }
    @GetMapping("/ignore/findTeachersByNameId/{nameId}")
    public ResponseJson findTeachersByNameId(@PathVariable String nameId){
        List<Teacher> teachers=jwCourseService.findTeachersByNameId(nameId,mySchoolId());
        return new ResponseJson(teachers);
    }

    @PostMapping("/ignore/findMyAnswerSheetByName")
    public ResponseJson findMyAnswerSheetByName(@RequestBody AnswerSheet answerSheet){
        answerSheet.setCreateUserId(myId());
        answerSheet.setType(0);
        List<AnswerSheet> answerSheets = answerSheetService.findAnswerSheetListByCondition(answerSheet);
        long count = answerSheetService.findAnswerSheetCountByCondition(answerSheet);
        return new ResponseJson(answerSheets, count);
    }
    @PostMapping("/ignore/findMyPaperByName")
    public ResponseJson findMyPaperByName(@RequestBody Paper paper){
        paper.setCreateUserId(myId());
        paper.setCurrentType(2);
        paper.getPager().setIncludes("mainTitle","updateTime");
        List<Paper> papers = paperService.findTestPaperListByCondition(paper);
        long count = paperService.findTestPaperCountByCondition(paper);
        return new ResponseJson(papers, count);
    }



    @GetMapping("/exportExaminee/{schoolExamId}")
    public void exportExaminee(@PathVariable String schoolExamId, HttpServletResponse response) throws IOException {
        ExamStudent examStudent = new ExamStudent();
        examStudent.setSchoolExamId(schoolExamId);
        ExamStudent examStu = examStudentService.findOneExamStudentByCondition(examStudent);
//        List<ExamStudent> examStudents = examStudentService.findExamStudentListByCondition(examStudent);
        SchoolExam schoolExam = schoolExamService.findSchoolExamById(schoolExamId);
        if(examStu!=null){
            List<ExamStudentInfo> examStudentInfos = examStu.getExamStudentInfos();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String,Object> map;
            List<JwClasses> classes = schoolExam.getClasses();
            for (int i = 0; i < classes.size(); i++) {
                JwClasses clazz = classes.get(i);
                List<ExamStudentInfo> examStudentInfoes=examStudentInfos.stream().filter(examStudentInfo -> examStudentInfo.getClassesId().equals(clazz.getId())).collect(Collectors.toList());
                map = new HashMap<>();
                map.put("title",new ExportParams(null,clazz.getGradeName()+clazz.getNumber()+"班"));
                map.put("entity",ExamStudentInfo.class);
                map.put("data",examStudentInfoes);
                list.add(map);
            }

            Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
            workbook.write(response.getOutputStream());
        }
    }

    @PostMapping("/score/findStuScoresByCond")
    public ResponseJson findStuScoresByCond(@RequestBody ScoreCond scoreCond){
        List<StuScore> stuScores = schoolExamService.findStuScoresByCond(scoreCond);
        long total = schoolExamService.findStuScoreCountByCond(scoreCond);
        List<JwCourse> jwCourseList = schoolExamService.findListCoursePaper(scoreCond.getSchoolExamId());
        return new ResponseJson(stuScores,total,jwCourseList);
    }
    @GetMapping("/score/findStuScoreById/{id}")
    public ResponseJson findStuScoreById(@PathVariable String id){
        StuScore stuScore = stuScoreService.findStuScoreById(id);
        SchoolExam schoolExam = schoolExamService.findSchoolExamById(stuScore.getSchoolExamId());
        for (JwCourse course : schoolExam.getCourses()) {
            if(course.getId().equals(stuScore.getCourse().getId())){
                return new ResponseJson(stuScore,course.getPaperTitle());
            }
        }
        throw new IllegalArgumentException("传入参数错误,查询不到相关考试");
    }
    @PostMapping("/score/updateScore")
    public ResponseJson updateScore(@RequestBody StuScore stuScore){
        stuScoreService.updateStuScore(stuScore);
        return new ResponseJson();
    }

    @GetMapping("/score/downloadScores/{schoolExamId}")
    public void downloadScores(@PathVariable String schoolExamId,HttpServletResponse response) throws IOException {
        SchoolExam schoolExam = schoolExamService.findSchoolExamById(schoolExamId);
        List<JwCourse> courses = schoolExam.getCourses();
        List<String> courseIds = courses.stream().filter(new Predicate<JwCourse>() {
            @Override
            public boolean test(JwCourse jwCourse) {
                Boolean allReview = jwCourse.getAllReview();
                if(allReview==null)return false;
                return allReview;
            }
        }).flatMap(course -> Stream.of(course.getId())).collect(Collectors.toList());
        List<StuScore> stuScores=stuScoreService.findStuScoresForDownload(schoolExamId,courseIds);
        Map<String,Object> map;
        List<Map<String, Object>> list = new ArrayList<>();
        for (JwCourse course : courses) {
            List<StuScoreExcel> stuScoreExcels = new ArrayList<>();
            for (StuScore stuScore : stuScores) {
                if(stuScore.getCourse().getId().equals(course.getId())){
                    StuScoreExcel stuScoreExcel = new StuScoreExcel();
                    stuScoreExcel.setStudentName(stuScore.getStudent().getName());
                    stuScoreExcel.setGradeName(stuScore.getStudent().getGradeName());
                    stuScoreExcel.setClazzName(stuScore.getClazz().getNumber()+"班");
                    stuScoreExcel.setStudentNo(stuScore.getStudent().getStudentNo());
                    stuScoreExcel.setExamNo(stuScore.getStudent().getExamNo());
                    stuScoreExcel.setStatus(stuScore.getMissing()!=null&&stuScore.getMissing()?"缺考":stuScore.getLostPaper()!=null&&stuScore.getLostPaper()?"缺卷":"正常");
                    stuScoreExcel.setScore(stuScore.getMissing()!=null&&stuScore.getMissing()?null:stuScore.getScore());
                    stuScoreExcels.add(stuScoreExcel);
                }
            }
            map = new HashMap<>();
            map.put("title",new ExportParams(course.getAllReview()!=null&&course.getAllReview()?course.getAlias():"阅卷未完成",course.getAlias()));
            map.put("entity",StuScoreExcel.class);
            map.put("data",stuScoreExcels);
            list.add(map);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        workbook.write(response.getOutputStream());
    }

    @GetMapping("/ignore/findAnswerSheetByPaperId/{paperId}")
    public ResponseJson findAnswerSheetByPaperId(@PathVariable String paperId){
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setPaperId(paperId);
        AnswerSheet as = answerSheetService.findOneAnswerSheetByCondition(answerSheet);
        return new ResponseJson(as);
    }

    @GetMapping("/finish/{schoolExamId}")
    public ResponseJson finish(@PathVariable String schoolExamId){
        analysisStudentScoreService.analysisStudentScore(schoolExamId);
        SchoolExam schoolExam = new SchoolExam();
        schoolExam.setId(schoolExamId);
        schoolExam.setFinished(true);
        schoolExamService.updateSchoolExam(schoolExam);
        return new ResponseJson();
    }

    /**
     *
     * @param ids 班级id
     * @return
     */
    @PostMapping("/ignore/getStudentInfos")
    public ResponseJson getStudentInfos(@RequestBody List<String> ids){
        List<ExamStudentInfo> r = schoolExamService.getStudentInfos(ids);
        for (ExamStudentInfo studentInfo : r) {
            studentInfo.setSchoolId(null);
            studentInfo.setImgUrl(null);
            studentInfo.setId(null);
            studentInfo.setEnrollYear(null);
            studentInfo.setGradeId(null);
            studentInfo.setGradeName(null);
            studentInfo.setClassesNumber(null);
        }
        return new ResponseJson(r);
    }
}
