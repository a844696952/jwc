package com.yice.edu.cn.osp.controller.jw.webindex;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlan;
import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.WebIndexStudyAnalyseVo;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisVo;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerManageMonth;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.reviewTask.ReviewTaskService;
import com.yice.edu.cn.osp.service.jw.exam.examManage.SchoolExamService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.jw.webIndex.WebIndexService;
import com.yice.edu.cn.osp.service.jy.collectivePlan.CollectivePlanService;
import com.yice.edu.cn.osp.service.jy.handout.HandoutService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkService;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreAllService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreAllService;
import com.yice.edu.cn.osp.service.xw.doc.DocLeaderService;
import com.yice.edu.cn.osp.service.xw.doc.DocManagementService;
import com.yice.edu.cn.osp.service.xw.kq.KqStuEnterService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * osp 首页
 */
@RestController
@RequestMapping("/index")
public class WebIndexController {
    @Autowired
    private WebIndexService webIndexService;
    @Autowired
    private SchoolExamService schoolExamService;
    @Autowired
    private AnalyseClassScoreAllService analyseClassScoreAllService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private KqStuEnterService kqStuAddService;
    @Autowired
    private HandoutService handoutService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private DocLeaderService docLeaderService;
    @Autowired
    private DocManagementService docManagementService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private RepairNewService repairNewService;
    @Autowired
    private ReviewTaskService reviewTaskService;
    @Autowired
    private CollectivePlanService collectivePlanService;
    /**
     * 首页获取基础信息
     */
    @GetMapping("/ignore/getBaseMsg/{post}")
    public ResponseJson getBaseMsg(@PathVariable("post") String postId){
        return new ResponseJson(webIndexService.getBaseMsg(postId));
    }

    /**
     *我的申请
     * @return
     */
    @ApiOperation(value = "获取我的申请总数", notes = "返回对象数据")
    @GetMapping("/ignore/oa/myApply")
    public ResponseJson myApply(){
        long count =  webIndexService.myApply(myId());
        return new ResponseJson(count);
    }

    /**
     *我的审批
     * @return
     */
    @ApiOperation(value = "获取我的审批总数", notes = "返回对象数据")
    @GetMapping("/ignore/oa/myApproval")
    public ResponseJson myApproval(){
        long count =  webIndexService.myApproval(myId());
        return new ResponseJson(count);
    }

    /**
     * 我的抄送
     * @return
     */
    @ApiOperation(value = "获取我的抄送总数", notes = "返回对象数据")
    @GetMapping("/ignore/oa/myCopy")
    public ResponseJson myCopy(){
        long count =  webIndexService.myCopy(myId());
        return new ResponseJson(count);
    }
    /**
     * 年段长获取年段最近考试的学情
     */
    @PostMapping("/ignore/getRecentExamAnalyseGrade")
    @ApiOperation(value = "段长获取最近考试的学情", notes = "返回对象数据", response=AnalyseClassScoreAll.class)
    public ResponseJson getRecentExamAnalyseGrade(
    		 @ApiParam(value = "{examTypeId:考试类型(选填)}")
    		@RequestBody SchoolExam schoolExam){

        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.GRADE_LEADER);
        if(teacherClasses==null) {
        	return new ResponseJson(new ArrayList<AnalyseClassScoreAll>());
        }
        schoolExam.setGradeId(teacherClasses.getGradeId());
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        schoolExam.setPager(new Pager().setSortField("examTime").setSortOrder("DESC").setPage(1).setPageSize(1));
        List<SchoolExam> schoolExamRecentList = schoolExamService.findSchoolExamListByCondition(schoolExam);
        if (schoolExamRecentList.isEmpty()) {
            return new ResponseJson(new ArrayList<AnalyseClassScoreAll>());
        }

        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam schoolExamObj = new SchoolExam();
        schoolExamObj.setId(schoolExamRecentList.get(0).getId());
        analyseClassScoreAll.setExamObj(schoolExamObj);
        analyseClassScoreAll.setPager(new Pager().setPaging(false));
        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        return new ResponseJson(data);
    }
    /**
     * 获取全校最近考试的学情
     */
    @PostMapping("/ignore/getRecentExamAnalyseSchool")
    @ApiOperation(value = "校领导获取最近考试的学情", notes = "返回对象数据",response=AnalyseClassScoreAll.class)
    public ResponseJson getRecentExamAnalyseSchool(
    		 @ApiParam(value = "{examTypeId:考试类型(选填),gradeId:年级id 必填}")
    		@RequestBody SchoolExam schoolExam){
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        schoolExam.setPager(new Pager().setSortField("examTime").setSortOrder("DESC").setPage(1).setPageSize(1));
        List<SchoolExam> schoolExamRecentList = schoolExamService.findSchoolExamListByCondition(schoolExam);
        if (schoolExamRecentList.isEmpty()) {
            return new ResponseJson(new ArrayList<AnalyseClassScoreAll>());
        }

        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam schoolExamObj = new SchoolExam();
        schoolExamObj.setId(schoolExamRecentList.get(0).getId());
        analyseClassScoreAll.setExamObj(schoolExamObj);
        analyseClassScoreAll.setPager(new Pager().setPaging(false));
        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        return new ResponseJson(data);
    }
    /**
     * 班主任获取班级最近考试的学情
     */
    @PostMapping("/ignore/getRecentExamAnalyseMaster")
    @ApiOperation(value = "班主任获取最近考试的学情", notes = "返回对象数据",response=WebIndexStudyAnalyseVo.class)
    public ResponseJson getRecentExamAnalyseMaster(
    		 @ApiParam(value = "{examTypeId:考试类型(选填)}")
    		@RequestBody SchoolExam schoolExam){
        TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
        if(teacherClasses==null) {
        	return new ResponseJson(new WebIndexStudyAnalyseVo());
        }
        schoolExam.setGradeId(teacherClasses.getGradeId());
        JwClasses jwClasses = new JwClasses();
        jwClasses.setId(teacherClasses.getClassesId());
        jwClasses.setEnrollYear(String.valueOf(teacherClasses.getEnrollYear()));
        schoolExam.setClasses(Arrays.asList(jwClasses));
        schoolExam.setFinished(true);
        schoolExam.setSchoolId(mySchoolId());
        schoolExam.setPager(new Pager().setSortField("examTime").setSortOrder("DESC").setPage(1).setPageSize(1));
        List<SchoolExam> schoolExamRecentList = schoolExamService.findSchoolExamListByCondition2(schoolExam);
        if (schoolExamRecentList.isEmpty()) {
            return new ResponseJson(new WebIndexStudyAnalyseVo());
        }

        WebIndexStudyAnalyseVo returnVo = new WebIndexStudyAnalyseVo();

        AnalyseClassScoreAll analyseClassScoreAll = new AnalyseClassScoreAll();
        SchoolExam schoolExamObj = new SchoolExam();
        schoolExamObj.setId(schoolExamRecentList.get(0).getId());
        analyseClassScoreAll.setExamObj(schoolExamObj);
        analyseClassScoreAll.setPager(new Pager().setPaging(false));
        List<AnalyseClassScoreAll> data = analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        returnVo.setAnalyseClassScoreAll(data);

        AnalysisVo analysisVo = new AnalysisVo();
        analysisVo.setClassId(teacherClasses.getClassesId());
        analysisVo.setExaminationId(schoolExamObj.getId());
        analysisVo.setShowAll(true);
        analysisVo.setPager(new Pager().setPaging(false));
        List<Map<String, Object>> studentAnalyMap = analysisStudentScoreAllService.findAnalysisStuScoreAllListByCondition(analysisVo);
        returnVo.setStudentAnalyseList(studentAnalyMap);

        return new ResponseJson(returnVo);
    }


    @PostMapping("/ignore/oa/findTotalStats")
    @ApiOperation(value = "获取OA流程统计情况", notes = "返回列表")
    public ResponseJson findTotalStats(
            @ApiParam("必须传时间段,且不能超过一年")
            @RequestBody OaStats oaStats){
        String[] rangeTime = oaStats.getRangeTime();
        // 验证rangeTime非空并且时间范围正在一年之内
        if(null == rangeTime && rangeTime.length != 2 ){
            return new ResponseJson(false, "时间范围必须是两个字符串元素");
        }
        return webIndexService.findTotalStats(oaStats);
    }


    /**
     * gzw
     * 校长首页获取学生到校信息
     */
    @GetMapping("/ignore/schoolLeaderGetStudentsNowStatus")
    @ApiOperation(value = "校领导查看学生到校情况", notes = "返回单个,没有时为空")
    public ResponseJson schoolLeadergetStudentsNowStatus(){
        Student student = new Student();
        HashMap<String, Double> stringDoubleHashMap = webIndexService.schoolLeadergetStudentsNowStatus(student);
        return new ResponseJson(stringDoubleHashMap);
    }

    /**
     * gzw
     * 段长首页获取年段班级人数信息
     */
    @GetMapping("/ignore/gradeLeaderGetGradeMsg/{gradeId}")
    @ApiOperation(value = "段长首页获取年段,班级,人数信息", notes = "返回单个,没有时为空")
    public ResponseJson gradeLeaderGetGradeMsg(
            @ApiParam(value = "传gradeId")
            @PathVariable("gradeId") String gradeId){
        Student student = new Student();
        student.setGradeId(gradeId);
        List<HashMap<String,String>> result= webIndexService.gradeLeaderGetGradeMsg(student);
        return new ResponseJson(result);
    }

    /**
     * gzw
     * 段长首页获取学生到校信息
     */
    @GetMapping("/ignore/gradeOrClassLeaderGetStudentsNowStatus/{classesId}")
    @ApiOperation(value = "段长/班主任首页按班级id获取学生到校信息", notes = "返回单个,没有时为空")
    public ResponseJson gradeLeaderGetStudentsNowStatus(
            @ApiParam(value = "传classesId")
            @PathVariable("classesId") String classesId){
        Student student = new Student();
        student.setClassesId(classesId);
        List<Student> data = studentService.findStudentListByCondition(student);
        long count = studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }

    /**
     * gzw
     * 段长首页获取学生到校离校请假人数信息
     */
    @GetMapping("/ignore/gradeLeaderGetStuNowStatusNumMsg/{gradeId}")
    @ApiOperation(value = "段长首页获取学生到校/离校/请假人数信息", notes = "返回单个,没有时为空")
    public ResponseJson gradeLeaderGetStuNowStatusNumMsg(
            @ApiParam(value = "传gradeId")
            @PathVariable("gradeId") String gradeId){
        Student student = new Student();
        student.setGradeId(gradeId);
        HashMap<String, Double> stringDoubleHashMap = webIndexService.schoolLeadergetStudentsNowStatus(student);
        return new ResponseJson(stringDoubleHashMap);
    }

    /**
     * gzw
     * 班主任首页更新学生在校状态信息
     */
    @PostMapping("/ignore/updateStudentNowStatus")
    @ApiOperation(value = "班主任首页更新学生在校状态信息", notes = "返回响应对象")
    public ResponseJson updateStudentNowStatus(
            @ApiParam(value = "传学生id,在校状态nowStatus")
            @RequestBody Student student) {
        //是否是该班班主任
        Student student1 = new Student();
        student1.setId(student.getId());
        student1.setNowStatus(student.getNowStatus());
        boolean isHeadmaster = kqStuAddService.checkIsHeadmaster(student);
        if (!isHeadmaster) {
            return new ResponseJson(false, "您不是本班班主任，不能修改学生状态");
        }
        studentService.updateStudent(student1);
        //发推送给家长
        kqStuAddService.sendStuNowStatusToParent(student);
        return new ResponseJson();
    }

    /**
     * gzw
     * 校长首页获取职工考勤信息
     */
    @PostMapping("/ignore/schoolLeadergetTeacherKq")
    @ApiOperation(value = "校领导查看教职工考勤", notes = "返回单个,没有时为空",response = KqWorkerManageMonth.class)
    public ResponseJson schoolLeadergetTeacherKq(
            @ApiParam(value = "pager:{rangeArray:[2019-06-01,2019-06-09]}")
            @RequestBody KqWorkerMonth kqWorkerMonth){
        KqWorkerManageMonth kqWorkerManageMonth = webIndexService.schoolLeadergetTeacherKq(kqWorkerMonth);
        return new ResponseJson(kqWorkerManageMonth);
    }

    /**
     * 作业
     * @return
     */
    @GetMapping("/ignore/findHomeworksByCondition")
    @ApiOperation(value = "作业", notes = "返回响应对象",response = Homework.class,responseContainer = "List")
    public ResponseJson findHomeworksByCondition() {
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        homework.setPager(new Pager().setSortField("createTime").setSortOrder(Pager.DESC).setPageSize(3));
        List<Homework> data = homeworkService.findHomeworkListByCondition(homework);
        return new ResponseJson(data);
    }

    /**
     * 个人备课
     * @return
     */
    @GetMapping("/ignore/findHandoutsByCondition")
    @ApiOperation(value = "个人备课", notes = "返回列表",response = Handout.class)
    public ResponseJson findHandoutsByCondition(){
        Handout handout = new Handout();
        handout.setTeacherId(myId());
        handout.setPager(new Pager().setPageSize(5).setSortField("createTime").setSortOrder(Pager.DESC));
        List<Handout> data=handoutService.findHandoutsByCondition2(handout);
        return new ResponseJson(data);
    }

    /**
     * 公文->收文管理->待我审批总数量
     */
    @GetMapping("/ignore/findDocCountByCondition")
    @ApiOperation(value = "公文->待我审批总数量",notes = "返回总数量")
    public ResponseJson findDocCountByCondition(){
        Doc doc = new Doc();
        doc.setUserId(myId());
        doc.setSchoolId(mySchoolId());
        doc.setDocumentType(1);
        long count = docLeaderService.findDocCountByCondition(doc);
        return new ResponseJson(count);
    }

    /**
     * 公文->收文->待我接收未读总数量
     */
    @GetMapping("/ignore/findDocManagementsByCondition")
    @ApiOperation(value = "公文->收文->待我接收未读总数量",notes = "返回总数量")
    public ResponseJson findDocManagementListByCondition(){
        Doc doc = new Doc();
        doc.setType(1);
        doc.setUserId(LoginInterceptor.myId());
        long count = docManagementService.findDocManagementCountByCondition(doc);
        return new ResponseJson(count);
    }

    /**
     *  阅卷任务->阅卷任务总数
     */
    @GetMapping("/ignore/findSchoolExamLongByCondionKong")
    @ApiOperation(value = "阅卷任务->待我批阅的总数量",notes = "返回总数量")
    public ResponseJson findSchoolExamLongByCondionKong(){
        long count = reviewTaskService.findSchoolExamLongByCondionKong(new SchoolExam(),LoginInterceptor.myId(),LoginInterceptor.mySchoolId());
        return new ResponseJson(count);
    }


    /**
     * 资产->报表->库存明细
     */
    @GetMapping("/ignore/findAssertsInfoByCondition")
    @ApiOperation(value = "资产->数据报表->库存明细",notes = "返回固定资产数量、易耗品、维修总额")
    public ResponseJson findAssertsInfoByCondition(){


        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(mySchoolId());

        assetsStockDetail.setDel(1);
        assetsStockDetail.setAssetsProperty(1);//固定资产
        assetsStockDetail.setStatus(6);// 6 表示状态不为报废的情况
        long fixedAssertsCount = assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);

        assetsStockDetail.setAssetsProperty(2);//易耗品
        long consumablesAssertsCount = assetsStockDetailService.findAssetsStockDetailCountByFuzzyCondition(assetsStockDetail);

        RepairNew repairNew = new RepairNew();
        repairNew.setSchoolId(mySchoolId());//维修总费用
        double repairTotalCost = repairNewService.findRepairNewUpkeepCostsBySchool(repairNew);

        Map map = new HashMap();
        map.put("fixedAssertsCount",fixedAssertsCount);
        map.put("consumablesAssertsCount",consumablesAssertsCount);
        map.put("repairTotalCost",repairTotalCost);
        return new ResponseJson(map);
    }
    @GetMapping("/ignore/findCollectivePlanCountByCondition")
    @ApiOperation(value = "查找单个教师集体备课统计条数", notes = "count")
    public ResponseJson findCollectivePlanCountByCondition(){
        CollectivePlan collectivePlan = new CollectivePlan();
        collectivePlan.setTeacherId(myId());
        collectivePlan.setSchoolId(mySchoolId());
        long count=collectivePlanService.findCollectivePlanCountByCondition(collectivePlan);
        return new ResponseJson(count);
    }
}
