package com.yice.edu.cn.xw.service.stuInAndOut;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.*;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.cron.CronUtil;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.xw.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.xw.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.xw.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.DynamicCronFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.ResetDynamicTaskFeign;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class StuInAndOutStartTimeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DynamicCronFeign dynamicCronFeign;
    @Autowired
    private ResetDynamicTaskFeign resetDynamicTaskFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwClassesFeign jwClassesFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private StuInAndOutClassTimeService stuInAndOutClassTimeService;
    @Autowired
    private StuStatusChangeService stuStatusChangeService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private StuNotComeToSchoolService stuNotComeToSchoolService;
    @CreateCache(name = Constant.Redis.STU_IN_AND_OUT_CLASS_NEED_ATTEND_CACHE, expire = Constant.Redis.STU_IN_AND_OUT_CLASS_NEED_ATTEND_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String, Set<String>> stuInAndOutClassNeedAttendCache;//??????????????????????????????
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuInAndOutStartTime findStuInAndOutStartTimeById(String id) {
        return mot.findById(id,StuInAndOutStartTime.class);
    }
    public void saveStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setCreateTime(DateUtil.now());
        stuInAndOutStartTime.setId(sequenceId.nextId());
        mot.insert(stuInAndOutStartTime);
        //??????????????????
        createDynamicTask(stuInAndOutStartTime);
    }
    public List<StuInAndOutStartTime> findStuInAndOutStartTimeListByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).find(MongoKit.buildMatchDocument(stuInAndOutStartTime));
        Pager pager = stuInAndOutStartTime.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<StuInAndOutStartTime> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(StuInAndOutStartTime.class, document)));
        return list;
    }
    public long findStuInAndOutStartTimeCountByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        return mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).countDocuments(MongoKit.buildMatchDocument(stuInAndOutStartTime));
    }
    public StuInAndOutStartTime findOneStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).find(MongoKit.buildMatchDocument(stuInAndOutStartTime));
       MongoKit.appendProjection(query,stuInAndOutStartTime.getPager());
       return mot.getConverter().read(StuInAndOutStartTime.class,query.first());
    }

    public void updateStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).updateOne(new Document("_id",stuInAndOutStartTime.getId()), MongoKit.buildUpdateDocument(stuInAndOutStartTime,false));
        //??????????????????
        createDynamicTask(stuInAndOutStartTime);
    }
    public void updateStuInAndOutStartTimeForAll(StuInAndOutStartTime stuInAndOutStartTime) {
        stuInAndOutStartTime.setUpdateTime(DateUtil.now());
        mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).updateOne(new Document("_id",stuInAndOutStartTime.getId()), MongoKit.buildUpdateDocument(stuInAndOutStartTime,true));
    }
    public void deleteStuInAndOutStartTime(String id) {
        mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).deleteOne(new Document("_id",id));
    }
    public void deleteStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime) {
        mot.getCollection(mot.getCollectionName(StuInAndOutStartTime.class)).deleteMany(MongoKit.buildMatchDocument(stuInAndOutStartTime));
    }
    public void batchSaveStuInAndOutStartTime(List<StuInAndOutStartTime> stuInAndOutStartTimes){
        stuInAndOutStartTimes.forEach(stuInAndOutStartTime -> stuInAndOutStartTime.setId(sequenceId.nextId()));
        mot.insertAll(stuInAndOutStartTimes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * @param classTime
     */
    @Transactional
    public void createDynamicTask(StuInAndOutStartTime classTime) {
//        0 30 8 * * ?
        String startTime =  classTime.getStartTime();
        String teaSendTime =  "";
        //??????????????????
        try {
            Calendar nowTime = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            Date date = simpleDateFormat.parse(startTime);
            nowTime.setTime(date);
            ////System.out.println(nowTime);
            nowTime.add(Calendar.MINUTE, 10);
            startTime = df.format(nowTime.getTime());
            nowTime.add(Calendar.MINUTE, -10);
            teaSendTime = df.format(nowTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //?????????????????????cron?????????"2000-01-01 12:11:00"
        String cron="error";
        String cron1="error";
        try {
            startTime ="2000-01-01 "+startTime+":00";
            teaSendTime ="2000-01-01 "+teaSendTime+":00";
            cron = CronUtil.getCron("day",startTime);
            cron1 = CronUtil.getCron("day",teaSendTime);
            int i = cron.indexOf("?");
            int k = cron1.indexOf("?");
            cron = cron.substring(0,i+1);
            cron1 = cron1.substring(0,k+1);
        } catch (ParseException e) {
            e.printStackTrace();
            ////System.out.println("??????cron???????????????????????????????????????");
            return;
        }
        //?????????????????????????????????????????????????????????????????????mycron???
        DynamicCron dynamicCron =new DynamicCron();
        DynamicCron dynamicCron1 =new DynamicCron();
        dynamicCron.setType(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_IN_OUT_SCHOOL);//???????????????
        dynamicCron1.setType(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_ARRIVE_SCHOOL);//???????????????
        dynamicCron.setCron(cron);
        dynamicCron1.setCron(cron1);
        Pager pager = new Pager();
        pager.setPaging(false);
        dynamicCron.setPager(pager);
        dynamicCron1.setPager(pager);
        List<DynamicCron> dynamicCrons =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron);
        List<DynamicCron> dynamicCrons1 =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron1);
        if (dynamicCrons==null||dynamicCrons.size()==0){
            dynamicCron.setTaskDescription("?????????????????????????????????");
            dynamicCronFeign.saveDynamicCron(dynamicCron);
            ////System.out.println("???????????????????????????????????????????????????");
        }else {
            ////System.out.println("????????????????????????");
        }
        if (dynamicCrons1==null||dynamicCrons1.size()==0){
            dynamicCron1.setTaskDescription("?????????????????????????????????");
            dynamicCronFeign.saveDynamicCron(dynamicCron1);
            ////System.out.println("???????????????????????????????????????????????????");
        }else {
            ////System.out.println("????????????????????????");
        }
        resetDynamicTaskFeign.resetDynamicTask();
        //System.out.println("????????????????????????---?????????");
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     * @param taskId
     */
    public void sendToSchoolTeacherStuNowStatus(String taskId) {
        DynamicCron dynamicCronById = dynamicCronFeign.findDynamicCronById(taskId);
        //??????????????????  ??????
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar nowTime = Calendar.getInstance();
        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_IN_OUT_SCHOOL)){
            nowTime.add(Calendar.MINUTE, -10);
        }
        String startTime = df.format(nowTime.getTime());

        //?????????????????????????????????????????????id
        StuInAndOutStartTime classTime = new StuInAndOutStartTime();
        classTime.setStartTime(startTime);
        List<StuInAndOutStartTime> classTimeList = findStuInAndOutStartTimeListByCondition(classTime);
        if (classTimeList==null||classTimeList.size()==0){
            //?????????????????????????????????,????????????????????????
            dynamicCronFeign.deleteDynamicCron(taskId);
            return;
        }
        Map<String,String> mapStr =  classTimeList.stream().collect(Collectors.toMap(classTime1->classTime1.getSchoolId(), classTime1 -> JSON.toJSONString(classTime1) ));
        Set<String> schoolIdSet =  mapStr.keySet();
        //???????????????????????????????????????
        for (String schoolId:schoolIdSet){
            JwClasses jwClasses = new JwClasses();
            jwClasses.setSchoolId(schoolId);
            List<JwClasses> jwClassesListByCondition = this.jwClassesFeign.findJwClassesListByCondition(jwClasses);
            //??????????????????????????????????????????
            for (JwClasses jwClasses1:jwClassesListByCondition) {
                //?????????????????????????????????
                StuInAndOutClassTime stuInAndOutClassTime = new StuInAndOutClassTime();
                stuInAndOutClassTime.setSchoolId(schoolId);
                List<StuInAndOutClassTime> schoolAllClassTimes = stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
                StuInAndOutClassTime theClassTime = null;
                for (StuInAndOutClassTime classTime1:schoolAllClassTimes){
                    String[] classes = classTime1.getClasses();
                    ////System.out.println(jwClasses1.getGradeId());
                    if (Arrays.stream(classes).anyMatch(s -> s.equals(jwClasses1.getGradeId()))){
                        //?????????????????????????????????
                        theClassTime=classTime1;
                    }
                }
                if (theClassTime==null){continue;}
                //?????????????????????????????????
                List<ClassTime> todayRealUseClassTimeList = new ArrayList<>();
                List<StartTimeAndDayAndEndTime> list = theClassTime.getList();
                for (StartTimeAndDayAndEndTime startTimeAndDayAndEndTime:  list){
                        ClassTime classTime2 = new ClassTime();
                        classTime2.setStartTime(startTimeAndDayAndEndTime.getStartTime());
                        if (startTime.equals(startTimeAndDayAndEndTime.getStartTime())){
                            List<DayAndEndTime> dayAndEndTimes= startTimeAndDayAndEndTime.getList();
                            Integer integer = WeekDayUtil.dayForWeek(DateUtil.date())-1;
                            List<DayAndEndTime> todayEndTime = dayAndEndTimes.stream().filter(dayAndEndTime -> dayAndEndTime != null && dayAndEndTime.getEndTime()!=null&&dayAndEndTime.getWeekDay() != null
                                    && dayAndEndTime.getWeekDay().equals(String.valueOf(integer))).collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(todayEndTime)){
                                classTime2.setEndTime(todayEndTime.get(0).getEndTime());
                                todayRealUseClassTimeList.add(classTime2);
                            }
                        }
                }
                if (CollectionUtil.isEmpty(todayRealUseClassTimeList)){
                    ////System.out.println("?????????????????????????????????????????????????????????????????????????????????????????????");
                    continue;
                }
                ////System.out.println(todayRealUseClassTimeList);
                //
                Student student = new Student();
                student.setClassesId(jwClasses1.getId());
                List<Student> students = studentFeign.findStudentListByCondition(student);
                List<Student> atSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL)).collect(Collectors.toList());
                List<Student> noAtSchoolStu =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                double d =0;
                if (atSchoolStu!=null&&atSchoolStu.size()>0){
                    double atSchool = atSchoolStu.size();
                    double noAtSchool = 0;
                    if (noAtSchoolStu!=null&&noAtSchoolStu.size()>0){
                        noAtSchool = noAtSchoolStu.size();
                    }
                    double all = atSchool + noAtSchool;
                    d = atSchool/all;
                }
                //?????????????????????
                if (d>=0.5) {
                    //??????????????????????????????????????????
                    Set<String> needAttendTime = stuInAndOutClassNeedAttendCache.get(jwClasses1.getId());
                    if (CollectionUtil.isNotEmpty(needAttendTime)){
                            needAttendTime.add(startTime);
                        }else {
                        needAttendTime=new HashSet<>();
                        needAttendTime.add(startTime);
                        }
                    stuInAndOutClassNeedAttendCache.put(jwClasses1.getId(),needAttendTime);
                    //System.out.println(stuInAndOutClassNeedAttendCache.get(jwClasses1.getId()));
                    //?????????????????????????????????????????????
                    List<Student> students2 =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                    if (students2!=null&& students2.size()>0){
                        ////System.out.println(dynamicCronById.getType());
                        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_ARRIVE_SCHOOL)){
                            //????????????????????????????????????????????????
                            TeacherClasses teacherClasses = new TeacherClasses();
                            teacherClasses.setClassesId(jwClasses1.getId());
                            Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
                            String[] teacherList = new String[]{teacher.getId()};
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"??????????????????","???????????????????????????????????????????????????????????????", Extras.XW_STUDENT_ARRIVE_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                            //System.out.println("???????????????u????????????????????????????????????????????????????????????????????????");
                        }else if(dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.NEW_TASK_STUDENT_IN_OUT_SCHOOL)){
                            //??????????????????????????????????????????
                            List<String> stuIdList =new ArrayList<>();
                            String[] stuIdArr = new String[students2.size()];
                            for (Student outsideStu :students2){
                                stuIdList.add(outsideStu.getId());
                            }
                            final Push push1 = Push.newBuilder(JpushApp.BMP).getSimplePush( stuIdList.toArray(stuIdArr),"???????????????","???????????????????????????????????????????????????????????????????????????????????????", Extras.XW_STUDENT_IN_OUT_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push1));
                            //System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                        }
                    }else {
                        ////System.out.println("??????????????????????????????");
                    }

                }else {
                    ////System.out.println("????????????????????????");
                }
            }

        }

    }

    /**
     * ???????????????????????????????????????
     * @param taskId
     */
    public void stuNotArriveSchool(String activeSchoolId) {
        long l = System.currentTimeMillis();
        StuStatusChange stuStatusChange = new StuStatusChange();
        stuStatusChange.setCreateDate(DateUtil.today());
        List<StuStatusChange> stuStatusChangeListByCondition = stuStatusChangeService.findStuStatusChangeListByCondition(stuStatusChange);
        if (CollectionUtil.isEmpty(stuStatusChangeListByCondition)){
            //System.out.println("?????????????????????????????????????????????");
            return;}
        //???????????????????????????????????????????????????
        StuInAndOutClassTime stuInAndOutClassTime = new StuInAndOutClassTime();
        List<StuInAndOutClassTime> allClassTimeList = stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
        List<StuNotComeToSchool> notComeStuList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(allClassTimeList)){
            Map<String, List<StuInAndOutClassTime>> schoolClasstimeMap = allClassTimeList.stream().collect(Collectors.groupingBy(StuInAndOutClassTime::getSchoolId));
            Set<String> schoolIds = schoolClasstimeMap.keySet();
            if (StrUtil.isNotEmpty(activeSchoolId)){
                schoolIds=new HashSet<String>();
                schoolIds.add(activeSchoolId);
            }
            for (String schoolId:schoolIds){
                System.out.println("???????????????????????????-------------------------------------------------------------------------------------------------------"+schoolId);
                CurSchoolYear curSchoolYear = new CurSchoolYear();
                curSchoolYearService.setSchoolYearTerm(curSchoolYear,schoolId);
                JwClasses jwClasses = new JwClasses();
                jwClasses.setSchoolId(schoolId);
                List<JwClasses> jwClassesListByCondition = this.jwClassesFeign.findJwClassesListByCondition(jwClasses);
                //??????????????????????????????????????????
                for (JwClasses jwClasses1:jwClassesListByCondition){

                    Set<String> needAttendTime = stuInAndOutClassNeedAttendCache.get(jwClasses1.getId());
                    if (CollectionUtil.isEmpty(needAttendTime)){
                        //System.out.println("????????????????????????????????????????????????");
                        continue;
                    }
                    Student student = new Student();
                    student.setClassesId(jwClasses1.getId());
                    List<Student> students = studentFeign.findStudentListByCondition(student);
                    if (CollectionUtil.isEmpty(students)){
                        //System.out.println("?????????????????????");
                        continue;
                    }
                    String gradeId = students.get(0).getGradeId();
                    List<StuInAndOutClassTime> schoolAllClassTimes = schoolClasstimeMap.get(schoolId);
                    StuInAndOutClassTime theClassTime = null;
                    for (StuInAndOutClassTime classTime:schoolAllClassTimes){
                        String[] classes = classTime.getClasses();
                        if (Arrays.stream(classes).anyMatch(s -> s.equals(gradeId))){
                            //?????????????????????????????????
                            theClassTime=classTime;
                        }
                    }
                    if (theClassTime==null){continue;}
                    //?????????????????????????????????
                    List<ClassTime> todayRealUseClassTimeList = new ArrayList<>();
                    List<StartTimeAndDayAndEndTime> list = theClassTime.getList();
                    for (StartTimeAndDayAndEndTime startTimeAndDayAndEndTime:  list){
                        if (needAttendTime.contains(startTimeAndDayAndEndTime.getStartTime())){
                            ClassTime classTime = new ClassTime();
                            classTime.setStartTime(startTimeAndDayAndEndTime.getStartTime());
                            List<DayAndEndTime> dayAndEndTimes= startTimeAndDayAndEndTime.getList();
                            Integer integer = WeekDayUtil.dayForWeek(DateUtil.date())-1;
                            List<DayAndEndTime> todayEndTime = dayAndEndTimes.stream().filter(dayAndEndTime -> dayAndEndTime != null && dayAndEndTime.getEndTime()!=null&&dayAndEndTime.getWeekDay() != null
                                    && dayAndEndTime.getWeekDay().equals(String.valueOf(integer))).collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(todayEndTime)){
                                classTime.setEndTime(todayEndTime.get(0).getEndTime());
                                todayRealUseClassTimeList.add(classTime);
                            }
                        }
                    }
                    //--
                    //????????????????????????
                    if (CollectionUtil.isEmpty(todayRealUseClassTimeList)){
                        //System.out.println("????????????????????????????????????????????????????????????????????????????????????");
                        continue;}
                    List<ClassTime> sortedClassTime = todayRealUseClassTimeList.stream().sorted(Comparator.comparing(ClassTime::getStartTime)).collect(Collectors.toList());
                    //??????????????????????????????
                    for (Student student1: students){
                        //System.out.println("++++++++++++++++++++++++++++++++++????????????????????????++++++++++++++++++++++++++++++++++");
                        String stuId = student1.getId();
                        //????????????????????????????????????
                        List<StuStatusChange> changes = stuStatusChangeListByCondition.stream().filter(sc -> sc.getStudentId().equals(stuId)).collect(Collectors.toList());
                        for (ClassTime classTime :sortedClassTime){
                            //?????????????????????????????????????????????
                            String startTime =DateUtil.today()+" "+ classTime.getStartTime() +":00";
                            String endTime =DateUtil.today()+" "+ classTime.getEndTime() +":00";
                            //System.out.println(startTime+"????????????????????????????????????");
                            StuStatusChange stuStatusChange1 = changes.stream().filter(sc -> DateUtil.parse(sc.getChangeTime()).isBefore(DateUtil.parse(startTime)))
                                    .sorted(Comparator.comparing(StuStatusChange::getChangeTime).reversed()).findFirst().orElse(null);
                            if (stuStatusChange1!=null&&stuStatusChange1.getStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)){
                                //System.out.println(classTime.getStartTime()+"??????????????????????????????");
                                //???????????????????????????
                                //???????????????????????????????????????
                                List<StuStatusChange> classTimeChange = changes.stream().filter(sc -> DateUtil.parse(sc.getChangeTime()).isAfterOrEquals(DateUtil.parse(startTime))
                                        && DateUtil.parse(sc.getChangeTime()).isBeforeOrEquals(DateUtil.parse(endTime))).collect(Collectors.toList());
                                if (CollectionUtil.isEmpty(classTimeChange)){
                                    //System.out.println(startTime+"???"+endTime+"???????????????????????????");
                                    //????????????????????????????????????????????????
                                    StuNotComeToSchool stuNotComeToSchool = new StuNotComeToSchool();
                                    stuNotComeToSchool.setCreateTime(DateUtil.now());
                                    stuNotComeToSchool.setClassStartTime(classTime.getStartTime());
                                    stuNotComeToSchool.setClassEndTime(classTime.getEndTime());
                                    stuNotComeToSchool.setClassesNumber(student1.getClassesNumber());
                                    stuNotComeToSchool.setClassesId(student1.getClassesId());
                                    stuNotComeToSchool.setGradeName(student1.getGradeName());
                                    stuNotComeToSchool.setGradeId(student1.getGradeId());
                                    stuNotComeToSchool.setStudentName(student1.getName());
                                    stuNotComeToSchool.setStudentId(student1.getId());
                                    stuNotComeToSchool.setSex(student1.getSex());
                                    stuNotComeToSchool.setSchoolId(student1.getSchoolId());
                                    stuNotComeToSchool.setTerm(curSchoolYear.getTerm());
                                    stuNotComeToSchool.setSchoolYearId(curSchoolYear.getSchoolYearId());
                                    stuNotComeToSchool.setFromTo(curSchoolYear.getFromTo());
                                    notComeStuList.add(stuNotComeToSchool);
                                }else {
                                    //System.out.println(startTime+"???"+endTime+"????????????????????????");
                                }

                            }
                        }
                    }
                    //????????????
                    stuInAndOutClassNeedAttendCache.remove(jwClasses1.getId());
                }

            }
        }
        //System.out.println("?????????????????????????????????????????????"+notComeStuList.size());
        if (CollectionUtil.isNotEmpty(notComeStuList)){
            stuNotComeToSchoolService.batchSaveStuNotComeToSchool(notComeStuList);
        }
        //System.out.println("???????????????"+(System.currentTimeMillis()-l));
        //System.out.println("??????????????????????????????");
       //stuStatusChangeService.deleteStuStatusChangeByCondition(stuStatusChange);
    }
}
