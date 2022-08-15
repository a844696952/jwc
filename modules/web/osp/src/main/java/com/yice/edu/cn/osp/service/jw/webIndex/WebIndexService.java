package com.yice.edu.cn.osp.service.jw.webIndex;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerManageMonth;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import com.yice.edu.cn.common.util.LocalDateTimeUtils;
import com.yice.edu.cn.common.util.WeekUtils.WeekUtil;
import com.yice.edu.cn.common.util.math.MathKit;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.ClassScheduleFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.JwStudentGraduationFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.oa.myApprove.MyApproveFeign;
import com.yice.edu.cn.osp.feignClient.oa.oaStats.OaStatsFeign;
import com.yice.edu.cn.osp.feignClient.oa.processBusinessData.ProcessBusinessDataFeign;
import com.yice.edu.cn.osp.feignClient.oa.processCopy.ProcessCopyFeign;
import com.yice.edu.cn.osp.feignClient.xw.checkedDetail.CheckedDetailFeign;
import com.yice.edu.cn.osp.service.jw.classSchedule.ScheduleListService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.osp.service.jw.schoolPush.SchoolPushService;
import com.yice.edu.cn.osp.service.jw.shortcut.TeacherWebShortcutService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import com.yice.edu.cn.osp.service.xw.workerKq.KqWorkerDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class WebIndexService {
    @Autowired
    private TeacherWebShortcutService teacherWebShortcutService;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwStudentGraduationFeign jwStudentGraduationFeign;
    @Autowired
    private MyApproveFeign myApproveFeign;
    @Autowired
    private ProcessBusinessDataFeign processBusinessDataFeign;
    @Autowired
    private ProcessCopyFeign processCopyFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private ClassScheduleFeign classScheduleFeign;
    @Autowired
    private SchoolNotifySendObjectService  schoolNotifySendObjectService;
    @Autowired
    private SchoolPushService schoolPushService;
    @Autowired
    private CheckedDetailFeign checkedDetailFeign;

    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private OaStatsFeign oaStatsFeign;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private ScheduleListService scheduleListService;
    /**
     * 校园平台首页 默认数据
     * @param postId
     * @return
     */
    public Map<String,Object> getBaseMsg(String postId) {
        Map<String,Object> result = new HashMap<>();
        //1、快捷功能
        result.put("teacherWebShortcut",teacherWebShortcutService.findTeacherWebShortcut4Index(myId()));
        //2、通知公告
        //校园通知
        result.put("schoolNotify",schoolNotifySendObjectService.getSchoolNotifyToWebIndex());
        //校园公告
        result.put("schoolPush",schoolPushService.getSchoolPushListToWebIndex());
        //3、通讯录
        Teacher teacher = new Teacher();//教师通讯录
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setSchoolId(mySchoolId());
        teacher.setId(myId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//只查询教师
        result.put("teachersList4AllSchool",teacherFeign.findCorrespondencesByTeacher(teacher));

        if(Constant.TEACHER_POST.PRINCIPAL.equals(postId)||Constant.TEACHER_POST.DEAN.equals(postId)||Constant.TEACHER_POST.GENERAL_MANAGER.equals(postId)){
            //校长 总务 教务主任
            //4、师生情况
            result.put("teacherSummary",teacherFeign.findTeacherSummaryBySchool4Index(mySchoolId()));
            result.put("courserTeacherSummary",teacherFeign.findCourseTeacherSummaryBySchool4Index(mySchoolId()));
            Map<String, Long> studentSummaryBySchool4Index = studentFeign.findStudentSummaryBySchool4Index(mySchoolId());
            Map<String,Object> studentSummary = new HashMap<>();
            JwStudentGraduation jwStudentGraduation = new JwStudentGraduation();
            jwStudentGraduation.setDel(1);
            jwStudentGraduation.setGraduationTime(LocalDateTimeUtils.getNextYear().toString());
            jwStudentGraduation.setSchoolId(mySchoolId());
            studentSummary.put("total",studentSummaryBySchool4Index.get("total"));
            studentSummary.put("rateOfMale", MathKit.getPercent(studentSummaryBySchool4Index.get("male"),studentSummaryBySchool4Index.get("total")));
            studentSummary.put("rateOfFemale", MathKit.getPercent(studentSummaryBySchool4Index.get("female"),studentSummaryBySchool4Index.get("total")));
            studentSummary.put("rateOfRise", MathKit.getPercent(studentSummaryBySchool4Index.get("total")-studentSummaryBySchool4Index.get("lastTotal"),studentSummaryBySchool4Index.get("lastTotal")));
            studentSummary.put("graduationTotal",jwStudentGraduationFeign.findJwStudentGraduationCountByCondition(jwStudentGraduation));
            result.put("studentSummary",studentSummary);
            result.put("studentGradeSummary",studentFeign.findGradeStudentSummaryBySchool4Index(mySchoolId()));
        }else if(Constant.TEACHER_POST.GRADE_LEADER.equals(postId)||Constant.TEACHER_POST.CLASS_TEACHER.equals(postId)||Constant.TEACHER_POST.ORDINARY_TEACHER.equals(postId)){
            //年段长  班主任  任课老师
            //4、我的课表，我的值班
            ClassSchedule classSchedule = new ClassSchedule();
            classSchedule.setTeacherId(myId());
            classSchedule.setSchoolId(mySchoolId());
            ScheduleList scheduleList = new ScheduleList();
            scheduleList.setType(1);
            scheduleList.setSchoolId(mySchoolId());
            ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
            if(scheduleList1!=null){
                classSchedule.setScheduleId(scheduleList1.getId());
            }
            List<List<ClassSchedule>> list = classScheduleFeign.findList(classSchedule);
            result.put("classSchedule",list);
            //值班(当前日期本周的教师值班情况)
            List<CheckedDetail> teacherCheckedDetail4OneWeek = teacherCheckedDetail4OneWeek();
            result.put("teacherCheckedDetail4OneWeek",teacherCheckedDetail4OneWeek);
            List<Student> studentsList4Teacher = new ArrayList<Student>();
            List<Student> studentsByclassTeacher = new ArrayList<Student>();
            if(Constant.TEACHER_POST.CLASS_TEACHER.equals(postId)){//班主任学生通讯录数据
                //班主任所教班級的学生通讯录
                //班主任所教班級
                TeacherClasses teacherClasses = teacherClassesService.findTeacherClassByTeacherIdAndPost(myId(), Constant.TEACHER_POST.CLASS_TEACHER);
                Student student = new Student();
                student.setClassesId(teacherClasses.getClassesId());
                student.setDel("1");
                String title = teacherClasses.getGradeName()+"("+teacherClasses.getClassesName()+")班";
                studentsByclassTeacher = studentService.findStudentListByCondition(student);
                studentsByclassTeacher.stream().forEach(s->s.setTitle(title));
                result.put("studentsByclassTeacher",studentsByclassTeacher);
            }else if(Constant.TEACHER_POST.ORDINARY_TEACHER.equals(postId)){//任課老師
                studentsList4Teacher = studentsList4Teacher();//学生通讯录
                result.put("studentsList4Teacher",studentsList4Teacher);
            }else{
                result.put("studentsList4Teacher",studentsList4Teacher);
                result.put("studentsByclassTeacher",studentsByclassTeacher);
            }

        }
        return result;
    }

    //学生通讯录(老师所教班级的学生信息)
    private List<Student> studentsList4Teacher(){
        Student student = new Student();//学生通讯录
        student.setTeacherId(myId());//教师id
        student.setSchoolId(mySchoolId());
        List<Student> teacherClassList = studentService.findTeacherClassList(student);//教师所教班级
        List<Student> studentsList4Teacher = new ArrayList<Student>();
        if(teacherClassList !=null && teacherClassList.size()>0){//这个教师有教对应的班级
            for(int i=0;i<teacherClassList.size();i++){
                Student student1 = teacherClassList.get(i);
                List<Student> classStudents4One = studentFeign.findClassStudentByclassTitle(student1);
                classStudents4One.stream().forEach(s->s.setTitle(student1.getTitle()));
                studentsList4Teacher.addAll(classStudents4One);
            }
        }
        return studentsList4Teacher;
    }

    //教师本周值班（今日）安排
    private List<CheckedDetail> teacherCheckedDetail4OneWeek(){
        CheckedDetail checkedDetail = new CheckedDetail();
        checkedDetail.setTeacherId(myId());
        Date date1 = WeekUtil.getBeginDayOfWeek();
        Date date2 = WeekUtil.getEndDayOfWeek();
        Pager pager1 = new Pager();
        pager1.setPaging(false);
        pager1.setRangeField("recordDate");
        String[] arr = {DateUtil.format(date1, "yyyy-MM-dd"),
                DateUtil.format(date2, "yyyy-MM-dd")};
        pager1.setRangeArray(arr);
        String[] includes = {"recordDate","dutyArrmentSpace","dutyTimeStart","dutyTimeEnd"};//只输出日期和地点
        pager1.setIncludes(includes);
        pager1.setSortField("recordDate");
        pager1.setSortOrder("desc");
        checkedDetail.setPager(pager1);
        List<CheckedDetail> teacherCheckedDetail4OneWeek = checkedDetailFeign.findCheckedDetailListByCondition(checkedDetail);
        return  teacherCheckedDetail4OneWeek;
    }

    //校领导获得教职工考勤信息`gzw
    public KqWorkerManageMonth schoolLeadergetTeacherKq(KqWorkerMonth kqWorkerMonth) {
        kqWorkerMonth.setSchoolId(mySchoolId());
        List<String> groupIdList = kqWorkerDailyService.isKqGroupManage();
        kqWorkerMonth.setGroupIdList(groupIdList);
        return kqWorkerDailyService.findSchoolLeaderMonthStatistic(kqWorkerMonth);
    }

    //校领导获得学生到校情况信息`gzw
    public HashMap<String, Double> schoolLeadergetStudentsNowStatus(Student student) {
        //校长查看学生到校情况   每种状态人数 每种状态比例
        Student student1 = student;
        student1.setSchoolId(mySchoolId());
        long allStudent = studentFeign.findStudentCountByCondition(student1);
        student1.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL);
        long inSchoolStu = studentFeign.findStudentCountByCondition(student1);
        student1.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);
        long noInSchoolStu = studentFeign.findStudentCountByCondition(student1);
        student1.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_IN);
        long leaveStu1 = studentFeign.findStudentCountByCondition(student1);
        student1.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);
        long leaveStu2 = studentFeign.findStudentCountByCondition(student1);
        long leaveStu =leaveStu1 + leaveStu2;
        double inPercent = 0;
        double noInPercent = 0;
        double leavePercent = 0;
        if (allStudent>0){
            inPercent = inSchoolStu/(double)allStudent;
            noInPercent = noInSchoolStu/(double)allStudent;
            leavePercent = leaveStu/(double)allStudent;
        }
        HashMap<String,Double> result = new HashMap();
        result.put("allStudent",(double)allStudent);
        result.put("inSchoolStu",(double)inSchoolStu);
        result.put("noInSchoolStu",(double)noInSchoolStu);
        result.put("leaveStu",(double)leaveStu);
        result.put("inPercent",inPercent);
        result.put("noInPercent",noInPercent);
        result.put("leavePercent",leavePercent);
        return result;
    }

    //段长获得年段信息`gzw
    public List<HashMap<String, String>> gradeLeaderGetGradeMsg(Student Student) {
        List<HashMap<String, String>> classesMsg = new ArrayList<>();
        //获得所有班级
        JwClasses c = new JwClasses();
        c.setGradeId(Student.getGradeId());
        c.setSchoolId(mySchoolId());
        List<JwClasses> jwClassesListByCondition = jwClassesService.findJwClassesListWithStuNum(c);
        for (JwClasses jwClasses :jwClassesListByCondition){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("classesId",jwClasses.getId());
            hashMap.put("classesNumber",jwClasses.getNumber());
            hashMap.put("gradeId",jwClasses.getGradeId());
            hashMap.put("gradeName",jwClasses.getGradeName());
            hashMap.put("studentCount",jwClasses.getStudentCount());
            classesMsg.add(hashMap);
        }
        return classesMsg;
    }

    public long myApply(String myId) {
        //我的申请
        ProcessBusinessData data = new ProcessBusinessData();
        data.setStatus(Constant.OA.WAIT_TO_APPROVE);
        data.setInitiatorId(myId);
        return processBusinessDataFeign.findProcessBusinessDataCountByCondition(data);
    }
    public long myApproval(String myId) {
        //我的审批
        return myApproveFeign.findWaitApproveCount(myId,new ProcessBusinessData());
    }
    public long myCopy(String myId) {
        //我的抄送
        ProcessCopy copy = new ProcessCopy();
        copy.setLooked(false);
        copy.setTeacherId(myId);
        return processCopyFeign.findProcessCopyCountByCondition(copy);
    }

    public ResponseJson findTotalStats(OaStats oaStats) {
        return oaStatsFeign.findTotalStats(mySchoolId(),oaStats);
    }
}
