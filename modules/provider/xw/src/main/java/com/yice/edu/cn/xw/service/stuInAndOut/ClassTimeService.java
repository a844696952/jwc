package com.yice.edu.cn.xw.service.stuInAndOut;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
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
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.cron.CronUtil;
import com.yice.edu.cn.xw.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.xw.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.xw.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.DynamicCronFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.ResetDynamicTaskFeign;
import com.yice.edu.cn.xw.service.kq.KqOriginalDataService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ClassTimeService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ResetDynamicTaskFeign resetDynamicTaskFeign;
    @Autowired
    private DynamicCronFeign dynamicCronFeign;
    @Autowired
    private JwClassesFeign jwClassesFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private KqOriginalDataService kqOriginalDataService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private StuNotComeToSchoolService stuNotComeToSchoolService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ClassTime findClassTimeById(String id) {
        return mot.findById(id,ClassTime.class);
    }
    public void saveClassTime(ClassTime classTime) {
        classTime.setCreateTime(DateUtil.now());
        classTime.setId(sequenceId.nextId());
        mot.insert(classTime);
    }
    public List<ClassTime> findClassTimeListByCondition(ClassTime classTime) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClassTime.class)).find(MongoKit.buildMatchDocument(classTime));
        Pager pager = classTime.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<ClassTime> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(ClassTime.class, document)));
        return list;
    }
    public long findClassTimeCountByCondition(ClassTime classTime) {
        return mot.getCollection(mot.getCollectionName(ClassTime.class)).countDocuments(MongoKit.buildMatchDocument(classTime));
    }
    public ClassTime findOneClassTimeByCondition(ClassTime classTime) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(ClassTime.class)).find(MongoKit.buildMatchDocument(classTime));
       MongoKit.appendProjection(query,classTime.getPager());
       return mot.getConverter().read(ClassTime.class,query.first());
    }

    public void updateClassTime(ClassTime classTime) {
        classTime.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(classTime.getId())), MongoKit.update(classTime),ClassTime.class);
    }
    public void deleteClassTime(String id) {
        mot.remove(query(where("id").is(id)),ClassTime.class);
    }
    public void deleteClassTimeByCondition(ClassTime classTime) {
        mot.getCollection(mot.getCollectionName(ClassTime.class)).deleteMany(MongoKit.buildMatchDocument(classTime));
    }
    public void batchSaveClassTime(List<ClassTime> classTimes){
        classTimes.forEach(classTime -> classTime.setId(sequenceId.nextId()));
        mot.insertAll(classTimes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * @param classTime
     */
    @Transactional
    public void createDynamicTask(ClassTime classTime) {
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
            //System.out.println(nowTime);
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
            //System.out.println("??????cron???????????????????????????????????????");
            return;
        }
        //?????????????????????????????????????????????????????????????????????mycron???
        DynamicCron dynamicCron =new DynamicCron();
        DynamicCron dynamicCron1 =new DynamicCron();
        dynamicCron.setType(Constant.DYNAMIC_CRON.TASK_STUDENT_IN_OUT_SCHOOL);//???????????????
        dynamicCron1.setType(Constant.DYNAMIC_CRON.TASK_STUDENT_ARRIVE_SCHOOL);//???????????????
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
            if (dynamicCrons1==null||dynamicCrons1.size()==0){
                dynamicCron1.setTaskDescription("?????????????????????????????????");
                dynamicCronFeign.saveDynamicCron(dynamicCron1);
                //System.out.println("???????????????????????????????????????????????????");
            }else {
                //System.out.println("????????????????????????");
            }
            dynamicCronFeign.saveDynamicCron(dynamicCron);
            //System.out.println("???????????????????????????????????????????????????");
            //resetDynamicTaskFeign.resetDynamicTask();
        }else {
            //System.out.println("????????????????????????");
        }
        createClassEndTimeDynamicTask(classTime);
        resetDynamicTaskFeign.resetDynamicTask();
        //System.out.println("????????????????????????---?????????");
    }

    /**
     *  ??????????????????????????????????????????????????????
     * @param classTime
     */
    public void createClassEndTimeDynamicTask (ClassTime classTime){
        //System.out.println("????????????????????????");
        //??????????????????
        String endTime = classTime.getEndTime();
        try {
            Calendar nowTime = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            Date date = simpleDateFormat.parse(endTime);
            nowTime.setTime(date);
            endTime = df.format(nowTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //?????????????????????
        String cron="error";
        try {
            endTime ="2000-01-01 "+endTime+":00";
            cron = CronUtil.getCron("day",endTime);
            int i = cron.indexOf("?");
            cron = cron.substring(0,i+1);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        //?????????????????????????????????????????????????????????????????????mycron???
        DynamicCron dynamicCron =new DynamicCron();
        dynamicCron.setType(Constant.DYNAMIC_CRON.TASK_STUDENT_CLASS_END_TIME_CHECK_STATUS);//????????????????????????????????????
        dynamicCron.setCron(cron);
        Pager pager = new Pager();
        pager.setPaging(false);
        dynamicCron.setPager(pager);
        List<DynamicCron> dynamicCrons =  dynamicCronFeign.findDynamicCronListByCondition(dynamicCron);
        if (dynamicCrons==null||dynamicCrons.size()==0){
            dynamicCron.setTaskDescription("???????????????????????????????????????????????????");
            dynamicCronFeign.saveDynamicCron(dynamicCron);
            //System.out.println("?????????????????????????????????????????????????????????????????????");
        }else {
            //System.out.println("????????????????????????");
        }

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
        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_IN_OUT_SCHOOL)){
           nowTime.add(Calendar.MINUTE, -10);
        }
        String startTime = df.format(nowTime.getTime());

        //?????????????????????????????????????????????id
        ClassTime classTime = new ClassTime();
        classTime.setStartTime(startTime);
        List<ClassTime> classTimeList = findClassTimeListByCondition(classTime);
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
            for (JwClasses jwClasses1:jwClassesListByCondition){
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
                //?????????????????????????????????????????????
                    List<Student> students2 =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                    if (students2!=null&& students2.size()>0){
                        //System.out.println(dynamicCronById.getType());
                        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_ARRIVE_SCHOOL)){
                            //????????????????????????????????????????????????
                            TeacherClasses teacherClasses = new TeacherClasses();
                            teacherClasses.setClassesId(jwClasses1.getId());
                            Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
                            String[] teacherList = new String[]{teacher.getId()};
                            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherList,"??????????????????","???????????????????????????????????????????????????????????????", Extras.XW_STUDENT_ARRIVE_SCHOOL);
                            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                            //System.out.println("???????????????u????????????????????????????????????????????????????????????????????????");
                        }else if(dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_IN_OUT_SCHOOL)){
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
                        ///System.out.println("??????????????????????????????");
                        }

                }else {
                    ///System.out.println("????????????????????????");
                }
            }

        }

    }

    /**
     * ??????????????????????????????????????????
     */
    public void statisStudentNowStatusAfterSchool(String taskId){
        DynamicCron dynamicCronById = dynamicCronFeign.findDynamicCronById(taskId);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar nowTime = Calendar.getInstance();
        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_CLASS_END_TIME_CHECK_STATUS)){

        }
        String endTime = df.format(nowTime.getTime());
        ClassTime classTime = new ClassTime();
        classTime.setEndTime(endTime);
        List<ClassTime> classTimeList = findClassTimeListByCondition(classTime);
        if (classTimeList==null||classTimeList.size()==0){
            //?????????????????????????????????,????????????????????????
            dynamicCronFeign.deleteDynamicCron(taskId);
            return;
        }
        Map<String,ClassTime> mapStr =  classTimeList.stream().collect(Collectors.toMap(classTime1->classTime1.getSchoolId(), Function.identity()));
        Set<String> schoolIdSet =  mapStr.keySet();
        List<StuNotComeToSchool> notComeStuList = new ArrayList<>();
        for (String schoolId:schoolIdSet){
            //System.out.println("????????????????????????"+schoolId);
            CurSchoolYear curSchoolYear = new CurSchoolYear();
            curSchoolYearService.setSchoolYearTerm(curSchoolYear,schoolId);
            JwClasses jwClasses = new JwClasses();
            jwClasses.setSchoolId(schoolId);
            List<JwClasses> jwClassesListByCondition = this.jwClassesFeign.findJwClassesListByCondition(jwClasses);
            //??????????????????????????????????????????
            for (JwClasses jwClasses1:jwClassesListByCondition){
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
                   // System.out.println("??????????????????");
                    //?????????????????????????????????????????????
                    List<Student> students2 =  students.stream().filter(v -> v.getNowStatus()!=null&&v.getNowStatus().equals(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL)).collect(Collectors.toList());
                    if (students2!=null&& students2.size()>0){
                        if (dynamicCronById.getType().equals(Constant.DYNAMIC_CRON.TASK_STUDENT_CLASS_END_TIME_CHECK_STATUS)){
                            //???????????????????????????????????????
                            //????????????????????????????????????
                            KqOriginalData kqOriginalData = new KqOriginalData();
                            Pager pager = new Pager();
                            pager.setRangeField("capturedTime");
                            String[] timeZone = new String[]{DateUtil.today()+" 00:00:00",DateUtil.today()+" 23:59:59"};
                            pager.setRangeArray(timeZone);
                            pager.setPaging(false);
                            pager.setIncludes("studentId");
                            kqOriginalData.setPager(pager);
                            kqOriginalData.setSchoolId(schoolId);
                            kqOriginalData.setPassStatus("-1");//???????????????
                            for (Student s:students2){
                                kqOriginalData.setStudentId(s.getId());
                                List<KqOriginalData> kqOriginalDataListByCondition = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
                                if (CollectionUtil.isEmpty(kqOriginalDataListByCondition)){
                                    StuNotComeToSchool stuNotComeToSchool = new StuNotComeToSchool();
                                    stuNotComeToSchool.setSchoolId(schoolId);
                                    stuNotComeToSchool.setStudentId(s.getId());
                                    stuNotComeToSchool.setGradeId(s.getGradeId());
                                    stuNotComeToSchool.setGradeName(s.getGradeName());
                                    stuNotComeToSchool.setClassesId(s.getClassesId());
                                    stuNotComeToSchool.setClassesNumber(s.getClassesNumber());
                                    stuNotComeToSchool.setCreateTime(DateUtil.now());
                                    stuNotComeToSchool.setSex(s.getSex());
                                    stuNotComeToSchool.setStudentName(s.getName());
                                    ClassTime ct = mapStr.get(schoolId);
                                    stuNotComeToSchool.setClassEndTime(ct.getEndTime());
                                    stuNotComeToSchool.setClassStartTime(ct.getStartTime());
                                    stuNotComeToSchool.setSchoolYearId(curSchoolYear.getSchoolYearId());
                                    stuNotComeToSchool.setTerm(curSchoolYear.getTerm());
                                    stuNotComeToSchool.setFromTo(curSchoolYear.getFromTo());
                                    notComeStuList.add(stuNotComeToSchool);
                                   // System.out.println("?????????????????????++"+notComeStuList.size());
                                }
                            }
                        }
                    }else {
                        //System.out.println("??????????????????????????????");
                    }
                }else {
                    //System.out.println("????????????????????????");
                }
            }

        }
        if (CollectionUtil.isNotEmpty(notComeStuList)){
            stuNotComeToSchoolService.batchSaveStuNotComeToSchool(notComeStuList);
        }
    }
}
