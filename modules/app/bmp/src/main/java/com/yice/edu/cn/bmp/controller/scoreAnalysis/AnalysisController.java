package com.yice.edu.cn.bmp.controller.scoreAnalysis;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.bmp.service.course.JwCourseService;
import com.yice.edu.cn.bmp.service.dd.DdService;
import com.yice.edu.cn.bmp.service.scoreAnalysis.AnalyseClassScoreService;
import com.yice.edu.cn.bmp.service.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreAllService;
import com.yice.edu.cn.bmp.service.scoreAnalysis.analysisStudentScore.AnalysisStudentScoreService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/analysis")
@RestController
@Api(value = "/analysis",description = "学情分析")
public class AnalysisController {
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;
    @Autowired
    private DdService ddService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;
    /**================考试查询条件==start===============**/
    @GetMapping("/findSchoolExamType")
    @ApiOperation(value = "查询学校考试类型",response = Dd.class,responseContainer = "List")
    public ResponseJson findSchoolExamType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.EXAMINATIONTYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findSubject4Student")
    @ApiOperation(value = "查询学生所在年级的课程信息",response = JwCourse.class,responseContainer = "List")
    public ResponseJson findSubject4Student(){
        //获取当前学生所在年级
        Student student = studentService.findStudentById(myStudentId());
        JwCourse jwCourse = new JwCourse();
        jwCourse.setGradeId(student.getGradeId());
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id","alias"));
        jwCourse.setSchoolId(mySchoolId());
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }
    /**================考试查询条件==end===============**/
    /**================考试查询==start===============**/
    @PostMapping("/findSchoolExam4Student")
    @ApiOperation(value = "学生获取获取考试列表",response = SchoolExam.class,responseContainer = "List")
    public ResponseJson findSchoolExam4Student(@ApiParam(value = "timeMark时间标志：1、近一周 2、近一月 3、本学期 4、本学年 默认本学年")
                                               @RequestBody SchoolExam schoolExam){
        schoolExam.setSchoolId(mySchoolId());
        if(schoolExam.getTimeMark()==null||"".equals(schoolExam.getTimeMark().trim()))
            schoolExam.setTimeMark("4");
        if(StringUtils.isEmpty(String.valueOf(schoolExam.getType())))
            schoolExam.setType(null);
        Pager pager = schoolExam.getPager()==null?new Pager().setPaging(false):schoolExam.getPager();
        pager.setSortField("examTime").setSortOrder(Pager.DESC);
        schoolExam.setPager(pager);
        return new ResponseJson(analysisStudentScoreAllService.findSchoolExam4Student(myStudentId(),schoolExam));
    }
    /**================考试查询==end===============**/
    /**================学生成绩查询==start===============**/
    @PostMapping("/findSubjectScoreList4Student")
    @ApiOperation(value = "获取单科学生成绩列表",notes = "考试id，科目id必填；其他条件根据需求自行考虑要不要",response = AnalysisStudentScore.class,responseContainer = "List")
    public ResponseJson findSubjectScoreList4Student(
            @ApiParam(value = "考试id，科目id必填；其他条件根据需求自行考虑要不要", required = true)
            @Validated
            @RequestBody AnalysisVo analysisVo){
        analysisVo.setStudentId(myStudentId());
        if(analysisVo.getPager()==null){
            analysisVo.setPager(new Pager().setPaging(false).setIncludes("student","score","classRanking","totalRanking","missing"));
        }else{
            analysisVo.setPager(analysisVo.getPager().setIncludes("student","score","classRanking","totalRanking","missing"));
        }
        return new ResponseJson(analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisVo));
    }

    @PostMapping("/findTotalScoreList4Student")
    @ApiOperation(value = "获取全科目学生总成绩列表",notes = "考试id必填；其他条件根据需求自行考虑要不要",response = AnalysisStudentScoreAll.class,responseContainer = "List")
    public ResponseJson findTotalScoreList4Student(
            @ApiParam(value = "考试id必填；其他条件根据需求自行考虑要不要", required = true)
            @Validated
            @RequestBody AnalysisVo analysisVo){
        analysisVo.setStudentId(myStudentId());
        if(analysisVo.getPager()==null){
            analysisVo.setPager(new Pager().setPaging(false).setIncludes("student","score","classRanking","totalRanking","missing"));
        }else{
            analysisVo.setPager(analysisVo.getPager().setIncludes("student","score","classRanking","totalRanking","missing"));
        }
        return new ResponseJson(analysisStudentScoreAllService.findAnalysisStudentScoreAllListByCondition(analysisVo));
    }
    /**================学生成绩查询==end===============**/
    @PostMapping("/findStuAnalyseScore")
    @ApiOperation(value = "查询该学生某场考试某科成绩的成绩报告", notes = "返回响应对象,不包含总条数", response=AnalyseClassScore.class)
    public ResponseJson findStuAnalyseScore(
            @ApiParam(value = "{examinationId:考试id,subjectId:科目id,classId:班级id}")
            @RequestBody AnalysisVo vo){
    	
    	vo.setStudentId(myStudentId());
    	
    	AnalyseClassScore analyseClassScore= new AnalyseClassScore();
    	SchoolExam examObj = new SchoolExam(); 
    	examObj.setId(vo.getExaminationId());
    	analyseClassScore.setExamObj(examObj);
    	
    	JwCourse courseObj = new JwCourse();
    	courseObj.setId(vo.getSubjectId());
    	analyseClassScore.setCourseObj(courseObj);
    	
    	JwClasses classObj= new JwClasses();
    	classObj.setId(vo.getClassId());
    	analyseClassScore.setClassObj(classObj);
    	
    	
        List<AnalyseClassScore> data=analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        if(data.isEmpty()) {
        	return new ResponseJson();
        }
        
        AnalyseClassScore classScore = data.get(0);
        List<AnalysisStudentScore> stuScoreList = analysisStudentScoreService.findAnalysisStudentScoreListByCondition(vo);
        
        if(stuScoreList.isEmpty()) {
        	return new ResponseJson();
        }
        classScore.setStuScore(stuScoreList.get(0).getScore().toString());
        classScore.setClassSort(stuScoreList.get(0).getClassRanking().toString());
        classScore.setMissing(stuScoreList.get(0).getMissing());
        
        return new ResponseJson(classScore);
    }
    @GetMapping("/findStuAllScoreAnalyse/{examinationId}")
    @ApiOperation(value = "查询学生成绩总览")
    public ResponseJson findStuAllScoreAnalyse(@PathVariable("examinationId")String examinationId){
        return new ResponseJson(analysisStudentScoreAllService.findStuAllScoreAnalyse(examinationId));
    }
}
