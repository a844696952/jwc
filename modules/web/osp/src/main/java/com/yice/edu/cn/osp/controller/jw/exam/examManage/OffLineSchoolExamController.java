package com.yice.edu.cn.osp.controller.jw.exam.examManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamRate;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentScoreNum;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.SchoolExamService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.StuScoreService;
import com.yice.edu.cn.osp.service.jw.jwCourse.JwCourseService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/offLineSchoolExam")
@Api(value = "/offLineSchoolExam",description = "学校考试模块")
public class OffLineSchoolExamController {
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private JwCourseService jwCourseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StuScoreService stuScoreService;
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @PostMapping("/saveSchoolExam")
    @ApiOperation(value = "保存学校考试对象", notes = "返回保存好的学校考试对象", response= SchoolExam.class)
    public ResponseJson saveSchoolExam(
            @ApiParam(value = "学校考试对象", required = true)
            @RequestBody SchoolExam schoolExam){
        SchoolExam schoolExam1=new SchoolExam();
//        schoolExam1.setExamTypeId(schoolExam.getExamTypeId());
        curSchoolYearService.setSchoolYearTerm(schoolExam,mySchoolId());
        schoolExam1.setCourses(schoolExam.getCourses());
        schoolExam1.setClasses(schoolExam.getClasses());
        schoolExam1.setExamTime(schoolExam.getExamTime());
        long count =schoolExamService.checkSchoolExamNum(schoolExam1);

        //验证同一时间，同样班级，同一课程的是否存在线下考试，只能添加一个
        if(count>0){
            return new ResponseJson(false," 同一时间，同样班级，同一课程的线下考试不能重复添加");
        }else {
            schoolExam.setCreateTime(DateUtil.now());
            schoolExam.setSchoolId(mySchoolId());
            schoolExam.setType(0);
            schoolExam.setFinished(false);
            schoolExam.setExamRate(new ExamRate());
            //获取参考总人数
            ArrayList<String> arrayList = new ArrayList<>();
            schoolExam.getClasses().forEach(
                    jwClasses -> {
                        arrayList.add(jwClasses.getId());
                    }
            );

            Map map=new HashMap();
            map.put("schoolId",mySchoolId());
            map.put("classesIds",arrayList);
            List<ClassesStudentScoreNum> studentScoreNumList= studentService.findStudentCountClassesByCondition(map);
            int num=0;
            for (int i = 0; i <studentScoreNumList.size() ; i++) {
                num+=studentScoreNumList.get(i).getStudentNum();
                for (int j = 0; j < schoolExam.getClasses().size() ; j++) {
                    if(studentScoreNumList.get(i).getClassesId().equals(schoolExam.getClasses().get(j).getId())){
                        schoolExam.getClasses().get(j).setStudentCount(studentScoreNumList.get(i).getStudentNum().toString());
                    }
                }
            }
            schoolExam.setTotalNum(num);
            SchoolExam s=schoolExamService.saveSchoolExam(schoolExam);
            return new ResponseJson(s);
        }
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
        schoolExamService.updateSchoolExam(schoolExam);
        return new ResponseJson();
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
        curSchoolYearService.setSchoolYearId(schoolExam,mySchoolId());
        List<SchoolExam> data=schoolExamService.findSchoolExamListByCondition1(schoolExam);
        long count=schoolExamService.findSchoolExamCountByCondition1(schoolExam);
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
        schoolExamService.deleteSchoolExam(id);
        stuScoreService.deleteStuScoreByschoolExamId(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolExamListByCondition")
    @ApiOperation(value = "根据条件查找学校考试列表", notes = "返回响应对象,不包含总条数", response=SchoolExam.class)
    public ResponseJson findSchoolExamListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolExam schoolExam){
        schoolExam.setSchoolId(mySchoolId());
        List<SchoolExam> data=schoolExamService.findSchoolExamListByCondition1(schoolExam);
        return new ResponseJson(data);
    }
    @PostMapping("ignore/findJwClassessByCondition")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        jwClasses.getPager().setPaging(false);
        List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);
        return new ResponseJson(data);
    }

    @PostMapping("ignore/findJwCoursesByCondition")
    @ApiOperation(value = "根据条件查找课程信息", notes = "返回响应对象")
    public ResponseJson findJwCoursesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwCourse jwCourse) {
        jwCourse.setSchoolId(LoginInterceptor.mySchoolId());
        jwCourse.getPager().setPaging(false);
        List<JwCourse> data = jwCourseService.findJwCourseListByCondition(jwCourse);
        data.forEach(d->{
            d.setTotalScore(1);
            d.setPaperImgs(new String[]{});
        });
        return new ResponseJson(data);
    }


    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamsByCondition2")
    @ApiOperation(value = "学情分析特殊筛选条件使用", notes = "返回响应对象", response=SchoolExam.class)
    public ResponseJson findSchoolExamsByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolExam schoolExam){
        schoolExam.setSchoolId(mySchoolId());
        List<SchoolExam> data=schoolExamService.findSchoolExamListByCondition2(schoolExam);
        long count=schoolExamService.findSchoolExamCountByCondition2(schoolExam);
        return new ResponseJson(data,count);
    }

    /*====================学生考试成绩分析===start===============*/
    @GetMapping("/look/analysisStudentScore/{examinationId}")
    @ApiOperation(value = "按考试id生成学期分析数据")
    public ResponseJson analysisStudentScore(@PathVariable String examinationId) {
    	//没有导入学生成绩提示请导入学生成绩
    	StuScore stuScore = new StuScore();
    	stuScore.setSchoolExamId(examinationId);
    	long count = stuScoreService.findStuScoreCountByCondition(stuScore);
    	if(count==0) {
    		return new ResponseJson(false,"请先导入学生成绩!"); 
    	}
        analysisStudentScoreService.analysisStudentScore(examinationId);
        SchoolExam schoolExam=new SchoolExam();
        schoolExam.setId(examinationId);
        schoolExam.setFinished(true);
        schoolExamService.updateSchoolExam(schoolExam);
        return new ResponseJson();
    }


    @PostMapping("/findJwClassessByConditionForExam")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassessByConditionForExam(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data = jwClassesService.findJwClassessByConditionForExam(jwClasses);
        data= data.stream().filter(a-> Integer.valueOf(a.getStudentCount())>0).collect(Collectors.toList());
        return new ResponseJson(data);
    }
}
