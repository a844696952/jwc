package com.yice.edu.cn.jw.controller.exam.examManage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ExamStudentInfo;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScoreCond;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.jw.service.exam.examManage.SchoolExamService;
import com.yice.edu.cn.jw.service.schoolYear.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.yice.edu.cn.common.pojo.jw.semester.Semester;

@RestController
@RequestMapping("/schoolExam")
@Api(value = "/schoolExam",description = "学校考试模块")
public class SchoolExamController {
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    @GetMapping("/findSchoolExamById/{id}")
    @ApiOperation(value = "通过id查找学校考试", notes = "返回学校考试对象")
    public SchoolExam findSchoolExamById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolExamService.findSchoolExamById(id);
    }

    @PostMapping("/saveSchoolExam")
    @ApiOperation(value = "保存学校考试", notes = "返回学校考试对象")
    public SchoolExam saveSchoolExam(
            @ApiParam(value = "学校考试对象", required = true)
            @RequestBody SchoolExam schoolExam){
        schoolExamService.saveSchoolExam(schoolExam);
        return schoolExam;
    }

    @PostMapping("/findSchoolExamListByCondition")
    @ApiOperation(value = "根据条件查找学校考试列表", notes = "返回学校考试列表")
    public List<SchoolExam> findSchoolExamListByCondition(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        return schoolExamService.findSchoolExamListByCondition(schoolExam);
    }
    @PostMapping("/findSchoolExamCountByCondition")
    @ApiOperation(value = "根据条件查找学校考试列表个数", notes = "返回学校考试总个数")
    public long findSchoolExamCountByCondition(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        return schoolExamService.findSchoolExamCountByCondition(schoolExam);
    }

    @PostMapping("/updateSchoolExam")
    @ApiOperation(value = "修改学校考试", notes = "学校考试对象必传")
    public void updateSchoolExam(
            @ApiParam(value = "学校考试对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolExam schoolExam){
        schoolExamService.updateSchoolExam(schoolExam);
    }

    @GetMapping("/deleteSchoolExam/{id}")
    @ApiOperation(value = "通过id删除学校考试")
    public void deleteSchoolExam(
            @ApiParam(value = "学校考试对象", required = true)
            @PathVariable String id){
        schoolExamService.deleteSchoolExam(id);
    }
    @PostMapping("/deleteSchoolExamByCondition")
    @ApiOperation(value = "根据条件删除学校考试")
    public void deleteSchoolExamByCondition(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        schoolExamService.deleteSchoolExamByCondition(schoolExam);
    }
    @PostMapping("/findOneSchoolExamByCondition")
    @ApiOperation(value = "根据条件查找单个学校考试,结果必须为单条数据", notes = "返回单个学校考试,没有时为空")
    public SchoolExam findOneSchoolExamByCondition(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        return schoolExamService.findOneSchoolExamByCondition(schoolExam);
    }

    /**
     * 带搜索时间
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamListByCondition1")
    @ApiOperation(value = "根据条件查找学校考试列表", notes = "返回学校考试列表")
    public List<SchoolExam> findSchoolExamListByCondition1(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        return schoolExamService.findSchoolExamListByCondition1(schoolExam);
    }
    /**
     * 带搜索时间
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamCountByCondition1")
    @ApiOperation(value = "根据条件查找学校考试列表个数", notes = "返回学校考试总个数")
    public long findSchoolExamCountByCondition1(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        return schoolExamService.findSchoolExamCountByCondition1(schoolExam);
    }
    /**
     * 任课老师查询考试
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamListByConditionForTeacher")
    @ApiOperation(value = "任课老师查询考试", notes = "返回学校考试列表")
    public List<SchoolExam> findSchoolExamListByConditionForTeacher(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        return schoolExamService.findSchoolExamListByConditionForTeacher(schoolExam);
    }
    /**
     * 任课老师查询考试 数量
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamCountByConditionForTeacher")
    @ApiOperation(value = "任课老师查询考试 数量", notes = "返回学校考试总个数")
    public long findSchoolExamCountByConditionForTeacher(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        return schoolExamService.findSchoolExamCountByConditionForTeacher(schoolExam);
    }


    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamListByCondition2")
    @ApiOperation(value = "根据条件查找学校考试列表", notes = "返回学校考试列表")
    public List<SchoolExam> findSchoolExamListByCondition2(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        if(schoolExam.getTimeMark()!=null){
            Map map= getTime(schoolExam.getTimeMark());
            if(!StrUtil.hasEmpty(schoolExam.getSearchStartTime())){
                schoolExam.setSearchStartTime((String) map.get("searchStartTime"));

            }
            if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
                schoolExam.setSearchEndTime((String) map.get("searchEndTime"));
            }
            if(schoolExam.getTimeMark().equals("3")){
                curSchoolYearService.setSchoolYearTerm(schoolExam,schoolExam.getSchoolId());
            }

        }
        schoolExam.setTimeMark(null);
        return schoolExamService.findSchoolExamListByCondition2(schoolExam);
    }
    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamCountByCondition2")
    @ApiOperation(value = "根据条件查找学校考试列表个数", notes = "返回学校考试总个数")
    public long findSchoolExamCountByCondition2(
            @ApiParam(value = "学校考试对象")
            @RequestBody SchoolExam schoolExam){
        curSchoolYearService.setSchoolYearId(schoolExam,schoolExam.getSchoolId());
        if(schoolExam.getTimeMark()!=null){
            Map map= getTime(schoolExam.getTimeMark());
            if(!StrUtil.hasEmpty(schoolExam.getSearchStartTime())){
                schoolExam.setSearchStartTime((String) map.get("searchStartTime"));

            }
            if(!StrUtil.hasEmpty(schoolExam.getSearchEndTime())){
                schoolExam.setSearchEndTime((String) map.get("searchEndTime"));
            }
            if(schoolExam.getTimeMark().equals("3")){
                curSchoolYearService.setSchoolYearTerm(schoolExam,schoolExam.getSchoolId());
            }

        }
        schoolExam.setTimeMark(null);
        return schoolExamService.findSchoolExamCountByCondition2(schoolExam);
    }


    /**
     * 获取周期时间段
     * @return
     */
    public Map getTime(String timeMark){
        Map timeMap=new HashMap();
        String searchStartTime=null;
        String searchEndTime=null;

        switch (timeMark) {
            case "1":
                Date date = DateUtil.lastWeek();
                searchStartTime=date.toString();
                searchEndTime = DateUtil.now();
                break;
            case "2":
                Date date1 = DateUtil.lastMonth();
                searchStartTime=date1.toString();
                searchEndTime = DateUtil.now();
                break;

        }
        timeMap.put("searchStartTime",searchStartTime);
        timeMap.put("searchEndTime",searchEndTime);
        return timeMap;
    }

    @GetMapping("/findOnlineSchoolExamNow/{schoolId}")
    List <SchoolExam> findOnlineSchoolExamNow(@PathVariable("schoolId") String schoolId){
        return schoolExamService.findOnlineSchoolExamNow(schoolId);
    }
    @PostMapping("/commitStuScore")
    public String commitStuScore(@RequestBody List<StuScore> stuScores){
        return schoolExamService.commitStuScore(stuScores);
    }
    @GetMapping("/findExamStudentsBySchoolExamId/{schoolExamId}")
    public List<ExamStudentInfo> findExamStudentsBySchoolExamId(@PathVariable("schoolExamId") String schoolExamId){
        return schoolExamService.findExamStudentsBySchoolExamId(schoolExamId);
    }
    @PostMapping("/findStuScoresByCond")
    public List<StuScore> findStuScoresByCond(@RequestBody ScoreCond scoreCond){
        return schoolExamService.findStuScoresByCond(scoreCond);
    }
    @PostMapping("/findStuScoreCountByCond")
    public long findStuScoreCountByCond(@RequestBody ScoreCond scoreCond){
        return schoolExamService.findStuScoreCountByCond(scoreCond);
    }

    @PostMapping("/checkSchoolExamNum")
    public long checkSchoolExamNum(@RequestBody SchoolExam schoolExam){
        return schoolExamService.checkSchoolExamNum(schoolExam);
    }


    @GetMapping("/deleteSchoolExamById/{id}")
    public SchoolExam deleteSchoolExamById(@PathVariable String id){
        return schoolExamService.deleteSchoolExamById(id);
    }

    @GetMapping("/findListCoursePaper/{schoolExamId}")
    public List<JwCourse> findListCoursePaper(@PathVariable String schoolExamId){
        return schoolExamService.findListCoursePaper(schoolExamId);
    }
}
