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
@Api(value = "/analysis",description = "ĺ­Śćĺć")
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
    /**================ččŻćĽčŻ˘ćĄäťś==start===============**/
    @GetMapping("/findSchoolExamType")
    @ApiOperation(value = "ćĽčŻ˘ĺ­Ść ĄččŻçąťĺ",response = Dd.class,responseContainer = "List")
    public ResponseJson findSchoolExamType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.EXAMINATIONTYPE);
        List<Dd> data=ddService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
    @GetMapping("/findSubject4Student")
    @ApiOperation(value = "ćĽčŻ˘ĺ­Śçćĺ¨ĺš´çş§çčŻžç¨äżĄćŻ",response = JwCourse.class,responseContainer = "List")
    public ResponseJson findSubject4Student(){
        //čˇĺĺ˝ĺĺ­Śçćĺ¨ĺš´çş§
        Student student = studentService.findStudentById(myStudentId());
        JwCourse jwCourse = new JwCourse();
        jwCourse.setGradeId(student.getGradeId());
        jwCourse.setPager(new Pager().setPaging(false).setIncludes("id","alias"));
        jwCourse.setSchoolId(mySchoolId());
        return new ResponseJson(jwCourseService.findJwCourseListByCondition(jwCourse));
    }
    /**================ččŻćĽčŻ˘ćĄäťś==end===============**/
    /**================ččŻćĽčŻ˘==start===============**/
    @PostMapping("/findSchoolExam4Student")
    @ApiOperation(value = "ĺ­ŚçčˇĺčˇĺččŻĺčĄ¨",response = SchoolExam.class,responseContainer = "List")
    public ResponseJson findSchoolExam4Student(@ApiParam(value = "timeMarkćśé´ć ĺżďź1ăčżä¸ĺ¨ 2ăčżä¸ć 3ăćŹĺ­Ść 4ăćŹĺ­Śĺš´ éťčŽ¤ćŹĺ­Śĺš´")
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
    /**================ččŻćĽčŻ˘==end===============**/
    /**================ĺ­ŚçćçťŠćĽčŻ˘==start===============**/
    @PostMapping("/findSubjectScoreList4Student")
    @ApiOperation(value = "čˇĺĺç§ĺ­ŚçćçťŠĺčĄ¨",notes = "ččŻidďźç§çŽidĺżĺĄŤďźĺśäťćĄäťść šćŽéćąčŞčĄčččŚä¸čŚ",response = AnalysisStudentScore.class,responseContainer = "List")
    public ResponseJson findSubjectScoreList4Student(
            @ApiParam(value = "ččŻidďźç§çŽidĺżĺĄŤďźĺśäťćĄäťść šćŽéćąčŞčĄčččŚä¸čŚ", required = true)
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
    @ApiOperation(value = "čˇĺĺ¨ç§çŽĺ­ŚçćťćçťŠĺčĄ¨",notes = "ččŻidĺżĺĄŤďźĺśäťćĄäťść šćŽéćąčŞčĄčččŚä¸čŚ",response = AnalysisStudentScoreAll.class,responseContainer = "List")
    public ResponseJson findTotalScoreList4Student(
            @ApiParam(value = "ččŻidĺżĺĄŤďźĺśäťćĄäťść šćŽéćąčŞčĄčččŚä¸čŚ", required = true)
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
    /**================ĺ­ŚçćçťŠćĽčŻ˘==end===============**/
    @PostMapping("/findStuAnalyseScore")
    @ApiOperation(value = "ćĽčŻ˘čŻĽĺ­ŚçćĺşččŻćç§ćçťŠçćçťŠćĽĺ", notes = "čżĺĺĺşĺŻščąĄ,ä¸ĺĺŤćťćĄć°", response=AnalyseClassScore.class)
    public ResponseJson findStuAnalyseScore(
            @ApiParam(value = "{examinationId:ččŻid,subjectId:ç§çŽid,classId:ç­çş§id}")
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
    @ApiOperation(value = "ćĽčŻ˘ĺ­ŚçćçťŠćťč§")
    public ResponseJson findStuAllScoreAnalyse(@PathVariable("examinationId")String examinationId){
        return new ResponseJson(analysisStudentScoreAllService.findStuAllScoreAnalyse(examinationId));
    }
}
