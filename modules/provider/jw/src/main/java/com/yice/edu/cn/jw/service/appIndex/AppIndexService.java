package com.yice.edu.cn.jw.service.appIndex;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.document.Doc;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.util.WeekUtils.WeekUtil;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jw.dao.appIndex.IAppIndexDao;
import com.yice.edu.cn.jw.dao.auth.IPermDao;
import com.yice.edu.cn.jw.feign.KqOriginalData.KqOriginalDataFeign;
import com.yice.edu.cn.jw.feign.analysisStudentScore.AnalysisStudentScoreAllFeign;
import com.yice.edu.cn.jw.feign.checkedDetail.CheckedDetailFeign;
import com.yice.edu.cn.jw.feign.homework.HomeworkFeign;
import com.yice.edu.cn.jw.feign.homework.HomeworkStudentFeign;
import com.yice.edu.cn.jw.feign.honourRoll.DmHonourRollFeign;
import com.yice.edu.cn.jw.service.classSchedule.ClassScheduleService;
import com.yice.edu.cn.jw.service.classSchedule.ScheduleListService;
import com.yice.edu.cn.jw.service.exam.examManage.StuScoreService;
import com.yice.edu.cn.jw.service.schoolNotify.SchoolNotifySendObjectService;
import com.yice.edu.cn.jw.service.schoolPush.SchoolPushService;
import com.yice.edu.cn.jw.service.student.StudentService;
import com.yice.edu.cn.jw.service.teacher.TeacherPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class AppIndexService {
    @Autowired
    private IAppIndexDao appIndexDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IPermDao permDao;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private ClassScheduleService classScheduleService;
    @Autowired
    private DocManagementService docManagementService;
    @Autowired
    private SchoolNotifySendObjectService schoolNotifySendObjectService;
    @Autowired
    private HomeworkFeign homeworkFeign;
    @Autowired
    private CheckedDetailFeign checkedDetailFeign;
    @Autowired
    private HomeworkStudentFeign homeworkStudentFeign;
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;
    @Autowired
    private StuScoreService stuScoreService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolPushService schoolPushService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private AnalysisStudentScoreAllFeign analysisStudentScoreAllFeign;
    @Autowired
    private DmHonourRollFeign dmHonourRollFeign;
    @Autowired
    private ScheduleListService scheduleListService;

    @Transactional(readOnly = true)
    public AppIndex findAppIndexById(String id) {
        return appIndexDao.findAppIndexById(id);
    }
    @Transactional
    public void saveAppIndex(AppIndex appIndex) {
        appIndex.setId(sequenceId.nextId());
        appIndexDao.saveAppIndex(appIndex);
    }
    @Transactional(readOnly = true)
    public List<AppIndex> findAppIndexListByCondition(AppIndex appIndex) {
        return appIndexDao.findAppIndexListByCondition(appIndex);
    }
    @Transactional(readOnly = true)
    public AppIndex findOneAppIndexByCondition(AppIndex appIndex) {
        return appIndexDao.findOneAppIndexByCondition(appIndex);
    }
    @Transactional(readOnly = true)
    public long findAppIndexCountByCondition(AppIndex appIndex) {
        return appIndexDao.findAppIndexCountByCondition(appIndex);
    }
    @Transactional
    public void updateAppIndex(AppIndex appIndex) {
        appIndexDao.updateAppIndex(appIndex);
    }
    @Transactional
    public void deleteAppIndex(String id) {
        appIndexDao.deleteAppIndex(id);
    }
    @Transactional
    public void deleteAppIndexByCondition(AppIndex appIndex) {
        appIndexDao.deleteAppIndexByCondition(appIndex);
    }
    @Transactional
    public void batchSaveAppIndex(List<AppIndex> appIndexs) {
        appIndexs.forEach(appIndex -> appIndex.setId(sequenceId.nextId()));
        appIndexDao.batchSaveAppIndex(appIndexs);
    }


    @Transactional
    public void moveAppIndexItem(int fromIndex, int toIndex, int type) {
        AppIndex appIndex = new AppIndex();
        appIndex.setType(type);
        appIndex.setParentId("-1");
        appIndex.setFixed(false);
        Pager pager = new Pager();
        pager.setPaging(false).setSortField("row").setSortOrder(Pager.ASC);
        appIndex.setPager(pager);
        List<AppIndex> appIndexes = appIndexDao.findAppIndexListByCondition(appIndex);
        AppIndex toObj = appIndexes.get(toIndex);
        appIndexes.get(fromIndex).setRow(toObj.getRow());
        if (toIndex > fromIndex) {
            for (int i = fromIndex + 1; i <= toIndex; i++) {
                AppIndex ai = appIndexes.get(i);
                ai.setRow(ai.getRow() - 1);
            }
        } else {
            for (int i = toIndex; i < fromIndex; i++) {
                AppIndex ai = appIndexes.get(i);
                ai.setRow(ai.getRow() + 1);
            }

        }
        appIndexDao.moveAppIndexes(appIndexes);
    }

    @Transactional(readOnly = true)
    public List<AppIndex> findAppIndexListForSchool(String schoolId) {
        List<AppIndex> appIndices = appIndexDao.findAppIndexListForSchool(schoolId, 0, "-1");
        //???????????????????????????????????????????????????
        Perm perm = new Perm();
        Pager pager = new Pager();
        pager.setPaging(false).setIncludes("identify");
        perm.setPager(pager);
        perm.setSchoolId(schoolId);
        perm.setType(0);
        List<Perm> schoolPerms = permDao.findPermListByCondition(perm);
        List<AppIndex> result = new ArrayList<>();
        appIndices.forEach(appIndex -> {
            if (appIndex.getFixed()) {
                result.add(appIndex);
            } else {
                if (schoolPerms.stream().anyMatch(p -> p.getIdentify().equals(appIndex.getIdentify()))) {
                    result.add(appIndex);
                }
            }
        });
        return result;
    }
    @Transactional(readOnly = true)
    public List<AppIndex> getAppIndexesForTeacher(String schoolId, String id) {


        //1.??????????????????????????????????????????
        List<AppIndex> appIndices = appIndexDao.findAppIndexListForSchool(schoolId, 0, null);


        // ????????????????????????????????????????????????????????????
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setSchoolId(schoolId);
        teacherPost.setTeacherId(id);
        Long countStudent = teacherPostService.findStudentsCountByPower(teacherPost);


        List<Perm> perms = permDao.findTeacherTreeMenuByTId(id);
        List<AppIndex> result = new ArrayList<>();
        appIndices.forEach(appIndex -> {
            if (appIndex.getFixed()) {
                result.add(appIndex);
            } else {
                if (perms.stream().anyMatch(p -> p.getIdentify().equals(appIndex.getIdentify()))) {
//                    result.add(appIndex);
                    if( appIndex.isDisplay()){//????????????
                        if(!"studentNowStatus".equals(appIndex.getIdentify())){
                            result.add(appIndex);
                        }else{
                            if ( countStudent != null){
                                result.add(appIndex);
                            }
                        }
                    }
                }
            }
            appIndex.setFixed(null);
            appIndex.setType(null);
        });
        //2.??????????????????????????????????????????
        result.forEach(appIndex -> {
            switch (appIndex.getIdentify()) {
                case "visitor"://????????????,????????????????????????
                    appIndex.setData(new ArrayList<>());
                    break;
                case "carousel"://???????????????????????????
                    appIndex.setData(new ArrayList<>());
                    break;
                case "schoolPush"://??????,??????????????????
//                    Document first = mot.getCollection(mot.getCollectionName(SchoolPush.class)).find(new Document("appIds", JpushApp.TAP.getId()).append("schoolId", schoolId))
//                            .sort(new Document("createTime", -1)).first();
//                    if (first != null) {
//                        appIndex.setData(first.get("title"));
//                    }

                    SchoolPush sc = new SchoolPush();
                    int[] appIds = {0};
                    sc.setAppIds(appIds);
                    sc.setAppId(JpushApp.TAP.getId());
                    sc.setSchoolId(schoolId);
                    Pager pager4 = new Pager();
                    pager4.setSortField("createTime");
                    pager4.setSortOrder(Pager.DESC);
                    pager4.setPage(1);
                    pager4.setPageSize(1);
                    pager4.setPaging(false);
                    pager4.setRangeField("createTime");
                    sc.setPager(pager4);
                    List<SchoolPush> list = schoolPushService.findPageSchoolPushesByAppIdAndSchoolId(sc);

                    if (list != null && list.size() > 0) {
                        appIndex.setData(list.stream().limit(1));
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;
                case "docManagement"://??????,?????????????????????
                    Doc doc = new Doc();
                    doc.setUserId(id);
                    doc.setType(1);
                    long count1 = docManagementService.findDocManagementCountByCondition(doc);
//                    appIndex.setData(count1);
                    appIndex.setCount(count1);
                    break;
                case "schoolNotifySendObject"://??????,?????????????????????
                    SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
                    schoolNotifySendObject.setSchoolId(schoolId);
                    schoolNotifySendObject.setObjectId(id);
                    schoolNotifySendObject.setReadState("1");
                    schoolNotifySendObject.setDel("1");
                    long notifyCount = schoolNotifySendObjectService.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
//                    appIndex.setData(notifyCount);
                    appIndex.setCount(notifyCount);
                    break;
                case "studentNowStatus": //?????????????????????????????????
//                    TeacherPost teacherPost = new TeacherPost();
//                    teacherPost.setSchoolId(schoolId);
//                    teacherPost.setTeacherId(id);
//                    Long countStudent = teacherPostService.findStudentsCountByPower(teacherPost);
                    appIndex.setCount(countStudent);
                    break;
                case "homework"://??????,?????????????????? ps:???????????????
                    Homework homework = new Homework();
                    homework.setPublishStatus(Constant.HOMEWORK.PUBLISH_ON);
                    homework.setTeacherId(id);
                    homework.setPager(new Pager().setSortField("createTime").setSortOrder(Pager.DESC).setPageSize(3).setIncludes("subjectName","homeworkName", "homeworkContent", "shouldNum", "punctualNum", "overdueNum", "endTime", "publishTime","type","gradeName","classesName","sqId"));
                    List<Homework> homeworkList = homeworkFeign.findHomeworkListByCondition(homework);
                    if (homeworkList != null && homeworkList.size() > 0) {
                        appIndex.setData(homeworkList.stream().limit(2));
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;
                case "myProcessApply"://OA???????????????,???????????????????????????????????????
                    ProcessBusinessData myProcessApply = new ProcessBusinessData();
                    myProcessApply.setInitiatorId(id);
                    myProcessApply.setStatus(0);//?????????
                    long count = mot.count(MongoKit.query(myProcessApply), ProcessBusinessData.class);
//                    appIndex.setData(count);
                    appIndex.setCount(count);
                    break;
                case "myApprove"://OA,????????????,???????????????????????????
                    Query query = MongoKit.query(new ProcessBusinessData());
                    Criteria criteria = new Criteria();
                    criteria.and("approver").elemMatch(where("id").is(id).and("status").is(Constant.OA.WAIT_TO_APPROVE));
//                    appIndex.setData(mot.count(query.addCriteria(criteria), ProcessBusinessData.class));
                    appIndex.setCount(mot.count(query.addCriteria(criteria), ProcessBusinessData.class));
                    break;
                case "processCopy"://OA,????????????,????????????????????????????????????
                    ProcessCopy processCopy = new ProcessCopy();
                    processCopy.setTeacherId(id);
                    processCopy.setLooked(false);//false ?????????
//                    appIndex.setData(mot.count(MongoKit.query(processCopy), ProcessCopy.class));
                    appIndex.setCount(mot.count(MongoKit.query(processCopy), ProcessCopy.class));
                    break;
                case "personalDuty"://??????????????????????????????????????????
                    CheckedDetail checkedDetail = new CheckedDetail();
                    checkedDetail.setTeacherId(id);
                    Date date1 = WeekUtil.getBeginDayOfWeek();
                    Date date2 = WeekUtil.getEndDayOfWeek();
                    Pager pager1 = new Pager();
                    pager1.setPaging(false);
                    pager1.setRangeField("recordDate");
                    String[] arr = {DateUtil.format(date1, "yyyy-MM-dd"),
                            DateUtil.format(date2, "yyyy-MM-dd")};
                    pager1.setRangeArray(arr);
                    String[] includes = {"teacherId", "recordDate"};
                    pager1.setIncludes(includes);
                    pager1.setSortField("recordDate");
                    pager1.setSortOrder("desc");
                    checkedDetail.setPager(pager1);
                    List<CheckedDetail> rs = checkedDetailFeign.findCheckedDetailListByCondition(checkedDetail);
                    if(rs != null){
                        appIndex.setData(rs);
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
//                    String str = "";
//                    if (!rs.isEmpty()) {
//                        rs = rs.stream().collect(
//                                Collectors.collectingAndThen(
//                                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CheckedDetail::getRecordDate))), ArrayList::new)
//                        );
//                        str = rs.stream().map(CheckedDetail::getRecordDate).collect(Collectors.joining(","));
//                        appIndex.setData(str);
//                    } else {
//                        appIndex.setData(null);
//                    }
                    break;
                case "classSchedule"://?????????,??????????????????
                    ClassSchedule classSchedule = new ClassSchedule();
                    Date date = new Date();
                    int s = date.getDay();
                    if (s == 0) {
                        s = 7;
                    }
                    classSchedule.setWeekId(s);
                    classSchedule.setTeacherId(id);
                    classSchedule.setSchoolId(schoolId);
                    Pager pager = new Pager();
                    pager.setIncludes("id,weekId,numberId");
                    classSchedule.setPager(pager);
                    ScheduleList scheduleList = new ScheduleList();
                    scheduleList.setType(1);
                    scheduleList.setSchoolId(schoolId);
                    ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
                    if(scheduleList1!=null){
                        classSchedule.setScheduleId(scheduleList1.getId());
                    }
                    List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
                    ClassScheduleService.romoveClassSchedule(classSchedules, false);
                    appIndex.setData(classSchedules);
                    break;
            }

        });
        //3.???????????????
        return ObjectKit.buildTree(result, "-1");
    }

    @Transactional(readOnly = true)
    public List<AppIndex> getAppIndexesForParents(String schoolId, String studentId) {

        //1.???????????????????????????
//        List<AppIndex> appIndices = appIndexDao.findAppIndexListForSchool(schoolId,2,"-1");

        AppIndex appIndexPara = new AppIndex();
        appIndexPara.setType(2);//??????
        List<AppIndex> appIndices = appIndexDao.findAppIndexListByCondition(appIndexPara);

        //????????????????????????????????????
        Perm perm = new Perm();
        perm.setSchoolId(schoolId);
        List<Perm> perms = permDao.findPermListByCondition(perm);
        List<AppIndex> result = new ArrayList<>();
        appIndices.forEach(appIndex -> {
            if (appIndex.getFixed()) {
                result.add(appIndex);
            } else {
                if (perms.stream().anyMatch(p -> p.getIdentify().equals(appIndex.getIdentify()))) {
                    result.add(appIndex);
                }
            }
            appIndex.setFixed(null);
            appIndex.setType(null);
        });

        // try{
        //2.??????????????????????????????????????????
        appIndices.forEach(appIndex -> {
            switch (appIndex.getIdentify()) {
                case "carousel"://???????????????????????????
                    List carousellist = new ArrayList();
                    appIndex.setData(carousellist);
                    break;
                case "schoolNotifySendObject"://??????,?????????????????????

                    SchoolNotifySendObject schoolNotifySendObject = new SchoolNotifySendObject();
                    schoolNotifySendObject.setSchoolId(schoolId);
                    schoolNotifySendObject.setObjectId(studentId);//????????????????????????id
                    schoolNotifySendObject.setDel("1");
                    Pager pagerNotify = new Pager();
                    pagerNotify.setSortOrder(Pager.DESC);
                    pagerNotify.setSortField("createTime");
                    pagerNotify.setPage(1);
                    pagerNotify.setPageSize(2);
                    schoolNotifySendObject.setPager(pagerNotify);
                    List<SchoolNotifySendObject> notifySendObjectList = schoolNotifySendObjectService.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
                    if (notifySendObjectList != null && notifySendObjectList.size() > 0) {
                        appIndex.setData(notifySendObjectList.stream().limit(2));
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;

                case "schoolPush":// ??????
                    SchoolPush sc = new SchoolPush();
                    int[] appIds = {2};
                    sc.setAppIds(appIds);
                    sc.setAppId(JpushApp.BMP.getId());
                    sc.setSchoolId(schoolId);
                    Pager pager4 = new Pager();
                    pager4.setSortField("createTime");
                    pager4.setSortOrder(Pager.DESC);
//                    pager4.setPage(1);
//                    pager4.setPageSize(10);
                    pager4.setPaging(false);
//                    pager4.setRangeField("createTime");
                    sc.setPager(pager4);
                    List<SchoolPush> list = schoolPushService.findPageSchoolPushesByAppIdAndSchoolId(sc);

                    if (list != null && list.size() > 0) {
                        appIndex.setData(list.stream().limit(2));
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;
                case "homework"://??????
                    HomeworkStudent homeworkStudent = new HomeworkStudent();
                    Homework homework = new Homework();
                    homework.setPublishStatus(Constant.HOMEWORK.PUBLISH_ON);
                    homeworkStudent.setStudentId(studentId);
                    homeworkStudent.setHomeworkObj(homework);
                    Pager pager = new Pager();
                    pager.setPage(1);
                    pager.setPageSize(2);
                    pager.setSortField("homeworkObj.createTime");
                    pager.setSortOrder(Pager.DESC);
                    homeworkStudent.setPager(pager);
                    List<HomeworkStudent> homeworkStudentList = homeworkStudentFeign.findHomeworkStudentListByCondition(homeworkStudent);
                    appIndex.setData(homeworkStudentList);
                    break;

                case  "honourRoll"://?????????
                    DmHonourRoll dmHonourRoll = dmHonourRollFeign.findDmHonourRollByStudentId(studentId);
//                    DmHonourRoll dmHonourRoll = dmHonourRollFeign.findDmHonourRollByStudentId("1130766136057663488");
                    if(dmHonourRoll != null){
                        List honList = new ArrayList();
                        honList.add(dmHonourRoll);
                        appIndex.setData(honList);
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;

                case "stuInAndOut"://????????????????????????
                    KqOriginalData kqOriginalData = new KqOriginalData();
                    kqOriginalData.setStudentId(studentId);
                    Pager pager2 = new Pager();
                    pager2.setPage(1);
                    pager2.setPageSize(1);
                    pager2.setSortField("capturedTime");
                    pager2.setSortOrder("desc");
                    kqOriginalData.setPager(pager2);

                    List<KqOriginalData> data = kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
                    appIndex.setData(data);
                    break;

                case "onlineSchoolExam-score":// ??????-??????
                    SchoolExam schoolExam = new SchoolExam();
                    schoolExam.setSchoolId(schoolId);
                    if(schoolExam.getTimeMark()==null||"".equals(schoolExam.getTimeMark().trim()))
                        schoolExam.setTimeMark("4");
                    if(StringUtils.isEmpty(String.valueOf(schoolExam.getType())))
                        schoolExam.setType(null);
                    Pager pagerExam = schoolExam.getPager()==null?new Pager().setPaging(false):schoolExam.getPager();
                    pagerExam.setSortField("examTime").setSortOrder(Pager.DESC);
                    schoolExam.setPager(pagerExam);
                    List<SchoolExam> schoolExams = analysisStudentScoreAllFeign.findSchoolExam4Student(studentId,schoolExam);
                    if (schoolExams != null && schoolExams.size() > 0) {
                        appIndex.setData(schoolExams.stream().limit(1));
                    }else{
                        appIndex.setData(new ArrayList<>());
                    }
                    break;

                case "classSchedule"://??????
                    Student student = studentService.findStudentById(studentId);
                    ClassSchedule classSchedule = new ClassSchedule();
                    classSchedule.setClassId(student.getClassesId());
                    classSchedule.setSchoolId(schoolId);
                    ScheduleList scheduleList = new ScheduleList();
                    scheduleList.setType(1);
                    scheduleList.setSchoolId(schoolId);
                    ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
                    if(scheduleList1!=null){
                        classSchedule.setScheduleId(scheduleList1.getId());
                    }
                    Date date = new Date();
                    int s = date.getDay();
                    if (s == 0) {
                        s = 7;
                    }
                    classSchedule.setWeekId(s);
                    Pager pager1 = new Pager();
                    pager1.setIncludes("id,weekId,numberId");
                    classSchedule.setPager(pager1);
                    List<ClassSchedule> classSchedules = classScheduleService.todayClassScheduleList(classSchedule);
                    ClassScheduleService.romoveClassSchedule(classSchedules, false);
                    appIndex.setData(classSchedules);
                    break;

            }
        });
        // }catch (Exception e){
        // e.printStackTrace();
        // }


        //3.???????????????
        return ObjectKit.buildTree(appIndices, "-1");
    }


    @Transactional(readOnly = true)
    public Long getCountLeaveSchool(String schoolId, String id) {
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setSchoolId(schoolId);
        teacherPost.setTeacherId(id);
        Long countStudent = teacherPostService.findStudentsCountByPower(teacherPost);
        return countStudent;
    }
}