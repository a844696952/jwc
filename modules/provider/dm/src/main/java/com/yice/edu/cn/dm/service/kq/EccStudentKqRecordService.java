package com.yice.edu.cn.dm.service.kq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.dm.kq.*;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.date.CalendayUtil;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.dm.feignClient.jw.classSchedule.ClassScheduleInitFeign;
import com.yice.edu.cn.dm.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.dm.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.dm.feignClient.xw.stuLeave.StuLeaveFeign;
import com.yice.edu.cn.dm.service.dmlog.DmLogService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author dengfengfeng
 */
@Service
@Slf4j
public class EccStudentKqRecordService {

    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private StudentFeign studentFeign;

    @Autowired
    private EccStudentFaceService eccStudentFaceService;


    @Autowired
    private StuLeaveFeign stuLeaveFeign;
    @Autowired
    private JwClassesFeign jwClassesFeign;

    @Autowired
    private DmLogService dmLogService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CurSchoolYearService curSchoolYearService;


    @CreateCache(name = Constant.Redis.DM_HOLIDAY_INFO, expire = Constant.Redis.DM_HOLIDAY_DAY, timeUnit = TimeUnit.DAYS)
    private Cache<String,String> holidayInfos;


    public void saveEccStudentKqRecord(EccStudentKqRecord eccStudentKqRecord) {
        eccStudentKqRecord.setId(sequenceId.nextId());
        eccStudentKqRecord.setCreateTime(DateUtil.now());
        eccStudentKqRecord.setDkTime(DateUtil.now());
        mot.insert(eccStudentKqRecord);
    }

    public void deleteEccKqRecord(String schoolId){
        if(StringUtils.isNotEmpty(schoolId)){
            mot.remove(query(where("schoolId").is(schoolId)),EccStudentKqRecord.class);
            log.info("升学删除电子班牌考勤数据:schoolId="+ schoolId);
        }
    }

    /***
     *根据条件查询当前考勤数据 已过期
     * @param eccStudentKqRecord
     * @return
     */
    @Deprecated
    public List<EccStudentKqRecord> findEccStudentKqRecordListByCondition(EccStudentKqRecord eccStudentKqRecord) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentKqRecord.class)).find(MongoKit.buildMatchDocument(eccStudentKqRecord));
        Pager pager = eccStudentKqRecord.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<EccStudentKqRecord> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(EccStudentKqRecord.class, document)));
        return list;
    }

    /**
     * 获取指定班级的打卡记录
     *
     * @param eccStudentKqRecord
     * @return
     */
    public List<EccStudentKqRecord> findDmStudentKqRecordListByCondition(EccStudentKqRecord eccStudentKqRecord) {
        curSchoolYearService.setSchoolYearTerm(eccStudentKqRecord,eccStudentKqRecord.getSchoolId());
        if (Objects.nonNull(eccStudentKqRecord)) {
            List<Criteria> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(eccStudentKqRecord.getDkBeginTime()) && StringUtils.isNotEmpty(eccStudentKqRecord.getDkEndTime())) {
                list.add(Criteria.where("dkTime").gte(eccStudentKqRecord.getDkBeginTime()).lte(eccStudentKqRecord.getDkEndTime()));
                if (StringUtils.isNotEmpty(eccStudentKqRecord.getSchoolId())) {
                    list.add(Criteria.where("schoolId").is(eccStudentKqRecord.getSchoolId()));
                }
                if (StringUtils.isNotEmpty(eccStudentKqRecord.getClassesId())) {
                    list.add(Criteria.where("classesId").is(eccStudentKqRecord.getClassesId()));
                }
                if (StringUtils.isNotEmpty(eccStudentKqRecord.getStudentId())) {
                    list.add(Criteria.where("studentId").is(eccStudentKqRecord.getStudentId()));
                }
                if (StringUtils.isNotEmpty(eccStudentKqRecord.getGradeId())) {
                    list.add(Criteria.where("gradeId").is(eccStudentKqRecord.getGradeId()));
                }
                if(StringUtils.isNotEmpty(eccStudentKqRecord.getSchoolYearId())){
                    list.add(Criteria.where("schoolYearId").is(eccStudentKqRecord.getSchoolYearId()));
                }
                if(StringUtils.isNotEmpty(eccStudentKqRecord.getFromTo())){
                    list.add(Criteria.where("fromTo").is(eccStudentKqRecord.getFromTo()));
                }
                if(Objects.nonNull(eccStudentKqRecord.getTerm())){
                    list.add(Criteria.where("term").is(eccStudentKqRecord.getTerm()));
                }
                Query query = Query.query(new Criteria().andOperator(list.toArray(new Criteria[list.size()]))).with(new Sort(Sort.Direction.DESC, "dkTime"));
                return mot.find(query, EccStudentKqRecord.class);
            }
        }
        return null;
    }

    public EccStudentKqRecord findOneEccStudentKqRecordByCondition(EccStudentKqRecord eccStudentKqRecord) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(EccStudentKqRecord.class)).find(MongoKit.buildMatchDocument(eccStudentKqRecord));
        MongoKit.appendProjection(query, eccStudentKqRecord.getPager());
        return mot.getConverter().read(EccStudentKqRecord.class, query.first());
    }


    /**
     * 考勤打卡
     *
     * @param record
     * @return
     */
    public EccStudentKqRecord dk(EccStudentKqRecord record) {
        record.setId(sequenceId.nextId());
        curSchoolYearService.setSchoolYearTerm(record,record.getSchoolId());
        this.saveEccStudentKqRecord(record);
        Student student = new Student();
        student.setId(record.getStudentId());
        student.setSchoolId(record.getSchoolId());
        Student studentInfo = studentFeign.findStudentById(record.getStudentId());
        if (Objects.nonNull(studentInfo)) {
            //向教师端推送
            List<String> teacherIds = dmLogService.findTeacherPostBySid(record.getStudentId());
            if (CollUtil.isNotEmpty(teacherIds)) {
                String[] sendObjects = teacherIds.toArray(new String[0]);
                //推送给活动发送对象
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(sendObjects, "电子班牌", String.format("%1$s(%2$s)班%3$s,已在电子班牌签到。点击查看全班考勤", studentInfo.getGradeName(),
                        studentInfo.getClassesNumber(), studentInfo.getName()), Extras.DM_KQ_DK_TAP, record.getClassesId());
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            }
            //向家长端推送
            final Push push = Push.newBuilder(JpushApp.BMP).getSimplePusByRefrenceId(new String[]{record.getStudentId()}, "电子班牌考勤", "您的孩子已安全到教室", Extras.DM_KQ_DK_BMP, record.getStudentId());
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }
        return record;
    }

    /**
     * 学生打卡
     *
     * @param student 学生信息
     * @return 返回错误信息
     */
    public String dkRecord(Student student) {
        String nowTime = DateUtil.now();
        //是否过考勤结束时间
        ProtectedStudent protectedStudentModel = new ProtectedStudent();
        protectedStudentModel.setSchoolId(student.getSchoolId());
        String endTime = getKqEndTime(protectedStudentModel, DateUtil.today());
        if (DateUtil.parse(nowTime, DatePattern.NORM_DATETIME_PATTERN).isAfter(DateUtil.parse(endTime, DatePattern.NORM_DATETIME_PATTERN))) {
            return "考勤时间已过";
        }
        //是否第一次打卡
        EccStudentKqRecord kqRecordModel = new EccStudentKqRecord();
        kqRecordModel.setStudentId(student.getId());
        kqRecordModel.setSchoolId(student.getSchoolId());
        kqRecordModel.setClassesId(student.getClassesId());
        String today = DateUtil.today();
        kqRecordModel.setDkBeginTime(today + " 00:00:00");
        kqRecordModel.setDkEndTime(nowTime);
        List<EccStudentKqRecord> kdList = findDmStudentKqRecordListByCondition(kqRecordModel);
        if (CollectionUtil.isNotEmpty(kdList)) {
            //是否请假
            protectedStudentModel.setClassId(student.getClassesId());
            protectedStudentModel.setStudentId(student.getId());
            protectedStudentModel.setKqBeginDate(today + " 00:00:00");
            protectedStudentModel.setKqEndDate(nowTime);
            List<StuLeave> leaveList = getStuLeaveList(protectedStudentModel);
            if (CollectionUtil.isEmpty(leaveList)) {
                return "今天已打卡";
            }else{
                //判断是否是请假时间范围内是否打卡
                StuLeave currStuLeave;
                if(leaveList.size()>1){
                    currStuLeave=leaveList.get(leaveList.size()-1);
                }else{
                    currStuLeave=leaveList.get(0);
                }
                List<EccStudentKqRecord> collect = kdList.stream().filter(x -> DateUtil.parse(x.getDkTime(), Constant.DATE_FORMATTER).isAfterOrEquals(DateUtil.parse(currStuLeave.getBeginTime()))).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(collect)){
                    return "今日已打卡";
                }
            }
        }
        createDkRecord(student);
        return null;
    }

    /**
     * 打卡
     *
     * @param student 数据
     */
    private void createDkRecord(Student student) {
        ProtectedStudent protectedStudent = new ProtectedStudent(student);
        EccStudentKqRecord record = new EccStudentKqRecord(protectedStudent);
        record.setSchoolId(student.getSchoolId());
        record.setClassesId(student.getClassesId());
        dk(record);
    }


    /**
     * 根据条件查询当天的数据
     *
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> getCurrentKqListByCondition(ProtectedStudent protectedStudent) {
        List<EccStudentKqRecord> kqStudentList = getStudentKqList(protectedStudent);
        List<Student> students = getStudentsList(protectedStudent);
        //获取人脸信息
        List<EccStudentFace> studentFaces = getEccStudentFacesList(protectedStudent);
        //获取请假数据
        List<StuLeave> stuLeaveList = getStuLeaveList(protectedStudent);
        Map<String, List<EccStudentKqRecord>> kqRecordMap = getKqRecordMap(kqStudentList);
        Map<String, List<StuLeave>> stuLeaveMap = getStuLeaveMap(stuLeaveList);
        List<ProtectedStudent> kqStatus = getKqStatus(protectedStudent, kqRecordMap, studentFaces, students, stuLeaveMap);
        return kqStatus;
    }

    private List<EccStudentFace> getEccStudentFacesList(ProtectedStudent protectedStudent) {
        EccStudentFace studentFace = getEccStudentFace(protectedStudent);
        return eccStudentFaceService.findEccStudentFaceListByCondition(studentFace);
    }

    private List<Student> getStudentsList(ProtectedStudent protectedStudent) {
        Student student = getStudent(protectedStudent);
        return studentFeign.findStudentListByConditionWithBmp(student);
    }


    /**
     * 查询单个学生时间段范围内的数据
     *
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findKqListByStudentId(ProtectedStudent protectedStudent) {
        List<ProtectedStudent> list = new ArrayList<>();
        String originalBeginDate = protectedStudent.getKqBeginDate();
        List<Student> students = getStudentsList(protectedStudent);
        List<EccStudentFace> studentFaces = getEccStudentFacesList(protectedStudent);
        //获取考勤记录
        List<EccStudentKqRecord> currentKq = getStudentKqList(protectedStudent);
        //获取请假记录
        List<StuLeave> stuLeaveList = getStuLeaveList(protectedStudent);
        Map<String, List<EccStudentKqRecord>> kqRecordMap = getKqRecordMap(currentKq);
        Map<String, List<StuLeave>> stuLeaveMap = getStuLeaveMap(stuLeaveList);
        if (CollUtil.isNotEmpty(studentFaces) && CollUtil.isNotEmpty(students)) {
            boolean b = studentFaces.stream().anyMatch(x -> Objects.equals(x.getStudentId(), students.get(0).getId()));
            if (b) {
                if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate()) && StringUtils.isNotEmpty(protectedStudent.getKqEndDate())){
                    long between = DateUtil.between(DateUtil.parse(protectedStudent.getKqBeginDate(), Constant.DATE_FORMATTER_DAY), DateUtil.parse(protectedStudent.getKqEndDate()), DateUnit.DAY);
                    if (between > 0) {
                        for (int i = 0; i < between + 1; i++) {
                            String currentDate = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(originalBeginDate, Constant.DATE_FORMATTER_DAY), i), Constant.DATE_FORMATTER);
                            if (DateUtil.parse(currentDate, Constant.DATE_FORMATTER_DAY).isAfterOrEquals(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY))) {
                                //说明当天数据 需实时查询
                                //获取最后一条考勤记录
                                setCurrentKqTime(protectedStudent, currentDate);
                                Map<String, List<StuLeave>> todayStuLeave = getTodayStuLeave(stuLeaveList, protectedStudent);
                                Map<String,List<EccStudentKqRecord>> todayKqRecord=getTodayKqRecord(currentKq,protectedStudent);
                                getTodayKq(protectedStudent, list, students, studentFaces, todayKqRecord, todayStuLeave);
                            }else{
                                setKqTime(protectedStudent, currentDate);
                                List<EccStudentKqRecord> eccStudentKqRecords = getEccStudentKqRecords(protectedStudent, kqRecordMap,studentFaces.get(0));
                                List<StuLeave> eccStuLeaveList = getStuLeaves(protectedStudent, stuLeaveMap, studentFaces.get(0));
                                statisticsKqByRange(protectedStudent, list, students.get(0),eccStudentKqRecords,eccStuLeaveList);
                            }
                        }
                    } else {
                        //统计当天
                        setCurrentKqTime(protectedStudent, protectedStudent.getKqBeginDate());
                        Map<String, List<StuLeave>> todayStuLeave = getTodayStuLeave(stuLeaveList, protectedStudent);
                        Map<String,List<EccStudentKqRecord>> todayKqRecord=getTodayKqRecord(currentKq,protectedStudent);
                        getTodayKq(protectedStudent, list, students, studentFaces, todayKqRecord, todayStuLeave);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 对历史考勤进行统计
     * @param protectedStudent
     * @param list
     * @param currentStudent
     * @param currentKq
     * @param stuLeaveList
     */
    private void statisticsKqByRange(ProtectedStudent protectedStudent, List<ProtectedStudent> list, Student currentStudent,List<EccStudentKqRecord> currentKq,List<StuLeave> stuLeaveList) {
            ProtectedStudent protectedStudentInfo = new ProtectedStudent(currentStudent);
            //判断当前是否周末和法定节假日
            if (setFestival(protectedStudent, protectedStudentInfo,list)) {
                return;
            }
            if (CollUtil.isNotEmpty(currentKq)) {
                List<EccStudentKqRecord> currentKqList=currentKq;
                if (currentKq.size() > 1) {
                    //取最后一条记录
                    currentKq = CollUtil.toList(currentKq.get(currentKq.size() - 1));
                }
                if (StringUtils.isNotEmpty(getKqBeginTime(protectedStudent, protectedStudent.getKqBeginDate())) &&
                        DateUtil.parse(currentKq.get(0).getDkTime(), Constant.DATE_FORMATTER)
                                .isBefore(DateUtil.parse(getKqBeginTime(protectedStudent, protectedStudent.getKqBeginDate()), Constant.DATE_FORMATTER))) {
                    //打卡时间小于上课开始时间
                    //设置状态为已到
                    setKqStatus(protectedStudentInfo, 3, 1, currentKqList, stuLeaveList);
                } else {
                    if (CollUtil.isNotEmpty(stuLeaveList)) {
                        List<StuLeave>  currentStuLeaveList=stuLeaveList;
                        //获取最后一条请假记录
                        if (stuLeaveList.size() > 1) {
                            stuLeaveList = CollUtil.toList(stuLeaveList.get(stuLeaveList.size() - 1));
                        }
                        //判断请假结束时间 是否大于考勤结束时间
                        if (DateUtil.parse(stuLeaveList.get(0).getEndTime()).isAfter(DateUtil.parse(getKqEndTime(protectedStudent, protectedStudent.getKqEndDate()), Constant.DATE_FORMATTER))) {
                            //请假中状态
                            setKqStatus(protectedStudentInfo, 4, 0, currentKqList, currentStuLeaveList);
                        } else {
                            //根据请假结束时间 计算请假结束后的第一节课
                            String[] currentCourse = currentCourse(protectedStudent, stuLeaveList.get(0));
                            if (Objects.nonNull(currentCourse)) {
                                //判断打卡时间是否大于请假结束时间
                                if (DateUtil.parse(currentKq.get(0).getDkTime()).isAfter(DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(), currentCourse[0]),Constant.DATE_FORMATTER))) {
                                    //超过课程开始时间 状态为迟到
                                    setKqStatus(protectedStudentInfo, 2, 0, currentKqList, currentStuLeaveList);
                                } else {
                                    //已到状态
                                    setKqStatus(protectedStudentInfo, 3, 1, currentKqList, currentStuLeaveList);
                                }
                            }else{
                                 log.warn(String.format("未查询到当前学校id为%1$s当前请假信息为beginTime=%2$s,endTime=%3$s结束后的课程信息",protectedStudent.getSchoolId(),stuLeaveList.get(0).getBeginTime(),stuLeaveList.get(0).getEndTime()));
                                //处理异常情况
                                setKqStatus(protectedStudentInfo, 1, 1, currentKqList, currentStuLeaveList);
                            }
                        }
                    } else {
                        //迟到
                        setKqStatus(protectedStudentInfo, 2, 0, currentKqList, stuLeaveList);
                    }
                }
            } else {
                if (CollUtil.isNotEmpty(stuLeaveList)) {
                    //判断请假时间 是否超过当前考勤结束时间
                    List<StuLeave>  currentStuLeaveList=stuLeaveList;
                    //获取最后一次考勤
                    StuLeave currentStuLeave = getStuLeave(stuLeaveList);
                    if (DateUtil.parse(currentStuLeave.getEndTime()).isAfterOrEquals(DateUtil.parse(getKqEndTime(protectedStudent, protectedStudent.getKqEndDate()), Constant.DATE_FORMATTER))) {
                        //设置请假状态
                        setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentStuLeaveList);
                    } else {
                        //设置未到状态
                        setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentStuLeaveList);
                    }
                } else {
                    //未打卡 未请假 设置未到状态
                    setKqStatus(protectedStudentInfo, 1, 0, currentKq, stuLeaveList);
                }
            }
            protectedStudentInfo.setRecordDate(DateUtil.format(DateUtil.parse(protectedStudent.getKqBeginDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
            list.add(protectedStudentInfo);
    }

    /**
     * 获取考勤数据
     * @param month
     * @return
     */
    private String getHodayInfos(String month){
        String holiday = holidayInfos.get(month);
        if(StringUtils.isNotEmpty(holiday)){
            return holiday;
        }else{
            String holidayInfo = CalendayUtil.getInstance().getHolidayInfo(month);
            if(StringUtils.isNotEmpty(holidayInfo)){
                holidayInfos.put(month,holidayInfo);
            }
            return  holidayInfo;
        }
    }


    /**
     * 是否是法定节假日
     *
     * @param protectedStudent
     * @param protectedStudentInfo
     * @throws IOException
     * @throws ParseException
     */
    private boolean setFestival(ProtectedStudent protectedStudent, ProtectedStudent protectedStudentInfo,List<ProtectedStudent> list) {
        String hodayInfos = getHodayInfos(DateUtil.format(DateUtil.parse(protectedStudent.getKqBeginDate(), "yyyy-MM"), "yyyy-MM"));
        try {
            int festival = CalendayUtil.getInstance().isFestival(DateUtil.parse(protectedStudent.getKqBeginDate(), "yyyy-MM-d"),hodayInfos);
            if (festival == 1) {
                //法定节假日
                setKqStatus(protectedStudentInfo, 5, -1, null, null);
                setFestivalRecord(protectedStudent, protectedStudentInfo, list);
                return true;
            }
            if (festival == 2) {
                setKqStatus(protectedStudentInfo, 6, -1, null, null);
                setFestivalRecord(protectedStudent, protectedStudentInfo, list);
                return true;
            }
        } catch (Exception e) {
           log.error("setFestival报错",e.getMessage()+"---"+e.getStackTrace());
        }
        return false;
    }

    private void setFestivalRecord(ProtectedStudent protectedStudent, ProtectedStudent protectedStudentInfo, List<ProtectedStudent> list) {
        protectedStudentInfo.setRecordDate(DateUtil.format(DateUtil.parse(protectedStudent.getKqBeginDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
        list.add(protectedStudentInfo);
    }

    /**
     * 根据条件查询当前学生考勤数据
     *
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findStudentKqByCondition(ProtectedStudent protectedStudent) {
        List<ProtectedStudent> list = new ArrayList<>();
        List<Student> students = getStudentsList(protectedStudent);
        String originalBeginDate = protectedStudent.getKqBeginDate();
        String originalEndDate = protectedStudent.getKqEndDate();
        //获取考勤记录
        List<EccStudentKqRecord> currentKq = getStudentKqList(protectedStudent);
        //获取请假记录
        List<StuLeave> stuLeaveList = getStuLeaveList(protectedStudent);
        List<EccStudentFace> studentFaces = getEccStudentFacesList(protectedStudent);
        Map<String, List<EccStudentKqRecord>> kqMap = getKqRecordMap(currentKq);
        Map<String, List<StuLeave>> stuMap = getStuLeaveMap(stuLeaveList);
        if (CollUtil.isNotEmpty(studentFaces) && CollUtil.isNotEmpty(students) && StringUtils.isNotEmpty(protectedStudent.getKqBeginDate())) {
            long between = DateUtil.between(DateUtil.parse(originalBeginDate, Constant.DATE_FORMATTER_DAY), DateUtil.parse(originalEndDate), DateUnit.DAY);
            if (between > 0) {
                for (int i = 0; i < between + 1; i++) {
                    String currentDate = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(originalBeginDate, Constant.DATE_FORMATTER_DAY), i), Constant.DATE_FORMATTER);
                    if (DateUtil.parse(currentDate, Constant.DATE_FORMATTER_DAY).isAfterOrEquals(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY))) {
                        //说明当天数据 需实时查询
                        //获取最后一条考勤记录
                        setCurrentKqTime(protectedStudent, currentDate);
                        Map<String,List<EccStudentKqRecord>> todayKqRecord=getTodayKqRecord(currentKq,protectedStudent);
                        Map<String, List<StuLeave>> todayStuLeave = getTodayStuLeave(stuLeaveList, protectedStudent);
                        getTodayKq(protectedStudent, list, students, studentFaces,todayKqRecord,todayStuLeave);
                    }else{
                        statisticHistory(protectedStudent, list, students, studentFaces, kqMap, stuMap, currentDate);
                    }
                }
            } else {
                if(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY).isBefore(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY))){
                    statisticHistory(protectedStudent,list,students,studentFaces,kqMap,stuMap,protectedStudent.getKqBeginDate());
                }else{
                    setCurrentKqTime(protectedStudent, protectedStudent.getKqBeginDate());
                    Map<String,List<EccStudentKqRecord>> todayKqRecord=getTodayKqRecord(currentKq,protectedStudent);
                    Map<String, List<StuLeave>> todayStuLeave = getTodayStuLeave(stuLeaveList, protectedStudent);
                    //统计当天
                    getTodayKq(protectedStudent, list, students, studentFaces,todayKqRecord,todayStuLeave);
                }
            }
        }
        return list;
    }

    private void statisticHistory(ProtectedStudent protectedStudent, List<ProtectedStudent> list, List<Student> students, List<EccStudentFace> studentFaces, Map<String, List<EccStudentKqRecord>> kqMap, Map<String, List<StuLeave>> stuMap, String currentDate) {
        studentFaces.stream().filter(x -> students.stream().anyMatch(y -> Objects.equals(y.getId(), x.getStudentId()))).forEach(z ->{
        protectedStudent.setStudentId(z.getStudentId());
        protectedStudent.setClassId(z.getClassesId());
        //历史数据进行统计
        setKqTime(protectedStudent, currentDate);
        List<EccStudentKqRecord> eccStudentKqRecords = getEccStudentKqRecords(protectedStudent, kqMap, z);
        List<StuLeave> eccStuLeaveList = getStuLeaves(protectedStudent, stuMap, z);
        statisticsKqByRange(protectedStudent,list, z.getStudent(), eccStudentKqRecords, eccStuLeaveList);
         }
        );
    }

    /**
     * 获取今日打卡数据
     * @param currentKq
     * @param protectedStudent
     * @return
     */
    public Map<String,List<EccStudentKqRecord>> getTodayKqRecord(List<EccStudentKqRecord> currentKq,ProtectedStudent protectedStudent){
        if(CollUtil.isNotEmpty(currentKq)){
            List<EccStudentKqRecord> collect = compareKq(currentKq, protectedStudent);
            if(CollUtil.isNotEmpty(collect)){
               return collect.stream().collect(Collectors.groupingBy(EccStudentKqRecord::getStudentId));
            }
        }
        return new HashMap<>(1);
    }

    /**
     * 获取今日请假
     * @param stuLeaves
     * @param protectedStudent
     * @return
     */
    public Map<String,List<StuLeave>> getTodayStuLeave(List<StuLeave> stuLeaves,ProtectedStudent protectedStudent){
        Map<String,List<StuLeave>> stuMap=new HashMap<>(100);
        if(CollUtil.isNotEmpty(stuLeaves)){
            List<StuLeave> stuLeaveList = compareStuLeave(stuLeaves, protectedStudent);
            getStuLeaveMapByList(stuMap, stuLeaveList);
        }
        return stuMap;
    }

    private void getStuLeaveMapByList(Map<String, List<StuLeave>> stuMap, List<StuLeave> stuLeaveList) {
        if (CollUtil.isNotEmpty(stuLeaveList)) {
            stuLeaveList.forEach(x ->
                    stuMap.put(x.getStudent().getId(), stuLeaveList.stream().filter(z -> Objects.equals(z.getStudent().getId(), x.getStudent().getId())).collect(Collectors.toList()))
            );
        }
    }

    /**
     * 查询当前时间段范围内的请假集合
     * @param stuLeaves
     * @param protectedStudent
     * @return
     */
    private List<StuLeave> compareStuLeave(List<StuLeave> stuLeaves, ProtectedStudent protectedStudent){
        return  stuLeaves.stream().filter(x->(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(x.getBeginTime()))
                && DateUtil.parse(x.getEndTime()).isBeforeOrEquals(DateUtil.parse(protectedStudent.getKqEndDate(),Constant.DATE_FORMATTER)))
                        || (DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(x.getBeginTime())) &&
                        DateUtil.parse(protectedStudent.getKqEndDate(),Constant.DATE_FORMATTER).isAfterOrEquals(DateUtil.parse(x.getBeginTime())))
                        || (DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(x.getEndTime())) &&
                        DateUtil.parse(protectedStudent.getKqEndDate(),Constant.DATE_FORMATTER).isAfterOrEquals(DateUtil.parse(x.getEndTime())))
                  ).collect(Collectors.toList()).stream()
                .sorted(Comparator.comparing(StuLeave::getEndTime)).collect(Collectors.toList());
    }

    /**
     * 查询当前时间段范围内的考勤集合
     * @param currentKq
     * @param protectedStudent
     * @return
     */
    private List<EccStudentKqRecord> compareKq(List<EccStudentKqRecord> currentKq, ProtectedStudent protectedStudent) {
        List<EccStudentKqRecord> collect = currentKq.stream().filter(x -> DateUtil.parse(protectedStudent.getKqBeginDate(), Constant.DATE_FORMATTER).
                isBeforeOrEquals(DateUtil.parse(x.getDkTime(), Constant.DATE_FORMATTER))
                && DateUtil.parse(x.getDkTime(), Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(protectedStudent.getKqEndDate(),
                Constant.DATE_FORMATTER))).collect(Collectors.toList());
                collect=collect.stream().sorted(Comparator.comparing(EccStudentKqRecord::getDkTime)).collect(Collectors.toList());
        return collect;
    }

    private Map<String, List<StuLeave>> getStuLeaveMap(List<StuLeave> stuLeaveList) {
        Map<String,List<StuLeave>> stuMap=new HashMap<>(100);
        getStuLeaveMapByList(stuMap, stuLeaveList);
        return stuMap;
    }

    private Map<String, List<EccStudentKqRecord>> getKqRecordMap(List<EccStudentKqRecord> currentKq) {
        return CollUtil.isNotEmpty(currentKq)?currentKq.stream().collect(Collectors.groupingBy(EccStudentKqRecord::getStudentId)):new HashMap<>(100);
    }

    /**
     * 从当前时间段内取出请假数据
     * @param protectedStudent
     * @param stuMap
     * @param z
     * @return
     */
    private List<StuLeave> getStuLeaves(ProtectedStudent protectedStudent, Map<String, List<StuLeave>> stuMap, EccStudentFace z) {
        List<StuLeave> eccStuLeaveList = stuMap.get(z.getStudentId());
        if(CollUtil.isNotEmpty(eccStuLeaveList)){
            eccStuLeaveList= compareStuLeave(eccStuLeaveList, protectedStudent);
        }
        return eccStuLeaveList;
    }

    /**
     * 从内存中取出当前时间段内的考勤记录
     * @param protectedStudent
     * @param kqMap
     * @param z
     * @return
     */
    private List<EccStudentKqRecord> getEccStudentKqRecords(ProtectedStudent protectedStudent, Map<String, List<EccStudentKqRecord>> kqMap, EccStudentFace z) {
        //获取该学生考勤信息
        List<EccStudentKqRecord> eccStudentKqRecords = kqMap.get(z.getStudentId());
        if(CollUtil.isNotEmpty(eccStudentKqRecords)){
            eccStudentKqRecords = compareKq(eccStudentKqRecords, protectedStudent);
        }
        return eccStudentKqRecords;
    }

    /**
     * 设置当前打卡时间
     *
     * @param protectedStudent
     * @param currentDate
     */
    private void setKqTime(ProtectedStudent protectedStudent, String currentDate) {
        String beginTime = DateUtils.getOriginalDateTime(currentDate, 1);
        String endTime = DateUtils.getOriginalDateTime(currentDate, 2);
        protectedStudent.setKqBeginDate(beginTime);
        protectedStudent.setKqEndDate(endTime);
    }

    private void setCurrentKqTime(ProtectedStudent protectedStudent, String currentDate) {
        String beginTime = DateUtils.getOriginalDateTime(currentDate, 1);
        protectedStudent.setKqBeginDate(beginTime);
        protectedStudent.setKqEndDate(DateUtil.format(DateTime.now(), Constant.DATE_FORMATTER));
    }

    /**
     * 查询今日考勤统计
     *
     * @param protectedStudent
     * @param list
     * @param students
     * @param studentFaces
     */
    private void getTodayKq(ProtectedStudent protectedStudent, List<ProtectedStudent> list, List<Student> students, List<EccStudentFace> studentFaces,Map<String, List<EccStudentKqRecord>> currKqMap,Map<String, List<StuLeave>> currStuLeaveMap) {
        List<ProtectedStudent> kqStatus = getKqStatus(protectedStudent, currKqMap, studentFaces, students, currStuLeaveMap);
        list.addAll(kqStatus);
    }


    private List<StuLeave> getStuLeaveList(ProtectedStudent protectedStudent) {
        StuLeave stuLeave = getStuLeave(protectedStudent);
        curSchoolYearService.setSchoolYearTerm(stuLeave,stuLeave.getSchoolId());
        List<StuLeave> stuLeaveList = stuLeaveFeign.findStuLeaveListByConditionForWH(stuLeave);
        return stuLeaveList;
    }

    /**
     * 查询当前考勤记录
     *
     * @param protectedStudent
     * @return
     */
    private List<EccStudentKqRecord> getStudentKqList(ProtectedStudent protectedStudent) {
        EccStudentKqRecord record = getStudentKqRecord(protectedStudent);
        List<EccStudentKqRecord> kqStudentList = findDmStudentKqRecordListByCondition(record);
        if(CollUtil.isNotEmpty(kqStudentList)){
          return   kqStudentList.stream().sorted(Comparator.comparing(EccStudentKqRecord::getDkTime)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 设置考勤状态信息
     *
     * @param protectedStudent
     * @param kqState
     * @param aberrant
     * @param eccKqRecordInfos
     * @param stuLeaves
     */
    private void setKqStatus(ProtectedStudent protectedStudent, int kqState, int aberrant, List<EccStudentKqRecord> eccKqRecordInfos, List<StuLeave> stuLeaves) {
        protectedStudent.setKqState(kqState);
        protectedStudent.setAberrant(aberrant);
        if (CollUtil.isNotEmpty(eccKqRecordInfos)) {
            List<EccKqRecordInfo> eccKqRecords = eccKqRecordInfos.stream().map(x -> {
                EccKqRecordInfo eccKqRecordInfo = new EccKqRecordInfo();
                eccKqRecordInfo.setDkTime(x.getDkTime());
                return eccKqRecordInfo;
            }).collect(Collectors.toList());
            protectedStudent.setKqRecordInfos(eccKqRecords);
        }
        if (CollUtil.isNotEmpty(stuLeaves)) {
            List<EccKqStuLeaveInfo> stuLeaveInfos = stuLeaves.stream().map(x -> {
                EccKqStuLeaveInfo eccKqStuLeaveInfo = new EccKqStuLeaveInfo();
                eccKqStuLeaveInfo.setBeginTime(x.getBeginTime());
                eccKqStuLeaveInfo.setEndTime(x.getEndTime());
                return eccKqStuLeaveInfo;
            }).collect(Collectors.toList());
            protectedStudent.setStuLeaveInfos(stuLeaveInfos);
        }
    }


    /**
     * 对考勤数据进行统计业务
     *
     * @param protectedStudent
     * @param kqStudentList
     * @param studentFaces
     * @param students
     * @param stuLeaveList
     * @return
     */
    private List<ProtectedStudent> getKqStatus(ProtectedStudent protectedStudent, Map<String, List<EccStudentKqRecord>> kqStudentList, List<EccStudentFace> studentFaces, List<Student> students, Map<String, List<StuLeave>> stuLeaveList) {
        if (CollUtil.isNotEmpty(studentFaces) && CollUtil.isNotEmpty(students)) {
            return studentFaces.stream().filter(x -> students.stream().anyMatch(y -> Objects.equals(y.getId(), x.getStudentId())))
                    .map(z -> {
                        ProtectedStudent protectedStudentInfo = new ProtectedStudent(z.getStudent());
                        protectedStudentInfo.setHead(z.getFaceImg());
                        //判断当前是否是法定节假日和周末
                        if (!setFestival(protectedStudent, protectedStudentInfo,null)) {
                            //是否打卡
                            List<EccStudentKqRecord> currentKq =kqStudentList.get(z.getStudentId());
                            //判断是否请假
                            List<StuLeave> currentSL = stuLeaveList.get(z.getStudentId());
                            if (CollUtil.isNotEmpty(currentKq)) {
                                //取最后一次打卡记录
                                EccStudentKqRecord record = currentKq.size() > 1 ? currentKq.get(currentKq.size() - 1) : currentKq.get(0);
                                //打卡时间小于考勤开始时间
                                if (DateUtils.isBefore(record.getDkTime(), getKqBeginTime(protectedStudent, record.getDkTime()), Constant.DATE_FORMATTER, Constant.DATE_FORMATTER)) {
                                    //修改业务漏洞 考勤开始时间之前打卡且请假
                                    if(CollUtil.isNotEmpty(currentSL)){
                                        StuLeave stuLeave=getStuLeave(currentSL);
                                        if(DateUtil.parse(stuLeave.getEndTime()).isAfter(DateUtil.parse(record.getDkTime(),Constant.DATE_FORMATTER)) &&
                                                DateUtil.parse(stuLeave.getEndTime()).isAfter(DateUtil.parse(getKqBeginTime(protectedStudent, record.getDkTime()),Constant.DATE_FORMATTER))){
                                            setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentSL);
                                        }
                                    }else{
                                        //设置状态为已到
                                        setKqStatus(protectedStudentInfo, 3, 1, currentKq, stuLeaveList.get(z.getStudentId()));
                                    }
                                } else {
                                    if (CollUtil.isNotEmpty(currentSL)) {
                                        //请假判断
                                        stuLeaveDk(protectedStudent, protectedStudentInfo, currentKq, currentSL);
                                    } else {
                                        //设置迟到状态
                                        setKqStatus(protectedStudentInfo, 2, 0, currentKq,  stuLeaveList.get(z.getStudentId()));
                                    }
                                }
                            } else {
                                if (CollUtil.isNotEmpty(currentSL)) {
                                    if (DateUtil.parse(getKqBeginTime(protectedStudent, protectedStudent.getKqBeginDate()), Constant.DATE_FORMATTER).isBefore(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER))) {
                                        //未打卡请假判断
                                        stuLeaveNotDk(protectedStudent, protectedStudentInfo, currentSL,currentKq);
                                    }else{
                                        setKqStatus(protectedStudentInfo, 1, 0, currentKq, stuLeaveList.get(z.getStudentId()));
                                    }
                                } else {
                                    //未到
                                    setKqStatus(protectedStudentInfo, 1, 0, currentKq, stuLeaveList.get(z.getStudentId()));
                                }
                            }
                        }
                        protectedStudentInfo.setRecordDate(DateUtil.format(DateUtil.parse(protectedStudent.getKqBeginDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
                        return protectedStudentInfo;
                    }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 当前未打卡请假判断过程
     * @param protectedStudent
     * @param protectedStudentInfo
     * @param currentSL
     */
    private void stuLeaveNotDk(ProtectedStudent protectedStudent, ProtectedStudent protectedStudentInfo, List<StuLeave> currentSL,List<EccStudentKqRecord> currentKq) {
        //判断是否请假
        StuLeave currentStuLeave = getStuLeave(currentSL);
        if (StringUtils.isNotEmpty(getKqEndTime(protectedStudent, protectedStudent.getKqBeginDate())) && DateUtil.parse(currentStuLeave.getEndTime()).isAfter(DateUtil.parse(getKqEndTime(protectedStudent, protectedStudent.getKqBeginDate()), Constant.DATE_FORMATTER))) {
            //请假时间 大于考勤结束时候
            //设置请假中状态
            setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentSL);
        } else {
            //区分历史还是今日
            if (DateUtil.parse(protectedStudent.getKqEndDate(), Constant.DATE_FORMATTER_DAY).isAfterOrEquals(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER_DAY))) {
                if(DateUtil.parse(currentStuLeave.getBeginTime()).isAfter(DateUtil.parse(DateUtil.now(),Constant.DATE_FORMATTER))){
                    //还未到请假时间
                    setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
                }else if (DateUtil.parse(currentStuLeave.getEndTime()).isBefore(DateUtil.parse(DateUtil.now(), Constant.DATE_FORMATTER))) {
                    //判断是否过了 请假后的第一节课
                    String[] currentCourse = currentCourse(protectedStudent, currentStuLeave);
                    if (Objects.nonNull(currentCourse)) {
                        //上课时间大于当前时间
                        if (DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),currentCourse[0]),Constant.DATE_FORMATTER).isBefore(DateUtil.parse(DateUtil.now(),Constant.DATE_FORMATTER))) {
                            //未到
                            setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
                        } else {
                            //请假状态
                            setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentSL);
                        }
                    }else{
                        log.info(String.format("未查询到当前请假信息为beginTime=%1$s,endTime=%2$s结束后的课程信息",currentStuLeave.getBeginTime(),currentStuLeave.getEndTime()));
                        //异常状态
                        setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
                    }
                } else {
                    //请假
                    setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentSL);
                }
            } else {
                //统计历史 未到状态
                setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
            }
        }
    }


    /**
     * 打卡请假判断
     *
     * @param protectedStudent
     * @param protectedStudentInfo
     * @param currentKq
     * @param currentSL
     */
    private void stuLeaveDk(ProtectedStudent protectedStudent, ProtectedStudent protectedStudentInfo, List<EccStudentKqRecord> currentKq, List<StuLeave> currentSL) {
        if(currentSL.size()<=currentKq.size()){
            StuLeave currentStuLeave = getStuLeave(currentSL);
            EccStudentKqRecord record = currentKq.size() > 1 ? currentKq.get(currentKq.size() - 1) : currentKq.get(0);
            if (DateUtil.parse(currentStuLeave.getEndTime()).isBefore(DateTime.now())) {
                //从请假结束时间开始计算业务
                //根据请假结束时间判断属于哪一节课
                String[] currentCourse = currentCourse(protectedStudent, currentStuLeave);
                if (Objects.nonNull(currentCourse)) {
                    //获取最后一次打卡时间
                    //判断打卡时间 是否在当前课程范围内
                    if (DateUtil.parse(record.getDkTime(),Constant.DATE_FORMATTER).isAfterOrEquals(DateUtil.parse(currentStuLeave.getEndTime())) && DateUtil.parse(record.getDkTime(), Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),currentCourse[0]), Constant.DATE_FORMATTER))) {
                        setKqStatus(protectedStudentInfo, 3, 1, currentKq, currentSL);
                    } else if(DateUtil.parse(record.getDkTime(),Constant.DATE_FORMATTER).isAfterOrEquals(DateUtil.parse(currentStuLeave.getEndTime())) &&
                            DateUtil.parse(record.getDkTime(), Constant.DATE_FORMATTER).isAfter(DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),currentCourse[0]), Constant.DATE_FORMATTER))){
                        //迟到
                        setKqStatus(protectedStudentInfo, 2, 0, currentKq, currentSL);
                    }else{
                        if(DateUtil.parse(record.getDkTime(),Constant.DATE_FORMATTER).isAfter(DateUtil.parse(currentStuLeave.getBeginTime())) && DateUtil.parse(record.getDkTime()).isBeforeOrEquals(DateUtil.parse(currentStuLeave.getEndTime()))) {
                            //在请假中打卡 设置为已到
                            setKqStatus(protectedStudentInfo, 3, 1, currentKq, currentSL);
                        }else{
                            setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
                        }
                    }
                }else{
                    //异常状态
                    setKqStatus(protectedStudentInfo, 1, 0, currentKq, currentSL);
                }
            }else if(DateUtil.parse(currentStuLeave.getEndTime()).isAfter(DateTime.now()) && DateUtil.parse(record.getDkTime(),Constant.DATE_FORMATTER).isAfter(DateUtil.parse(currentStuLeave.getBeginTime())) && DateUtil.parse(record.getDkTime()).isBeforeOrEquals(DateUtil.parse(currentStuLeave.getEndTime()))) {
                //在请假中打卡 设置为已到
                setKqStatus(protectedStudentInfo, 3, 1, currentKq, currentSL);
            }else{
                setKqStatus(protectedStudentInfo, 4, 0, currentKq, currentSL);
            }
        }else{
            stuLeaveNotDk(protectedStudent,protectedStudentInfo,currentSL,currentKq);
        }
    }

    /**
     * 获取当前最新的一条请假数据
     *
     * @param currentSL
     * @return
     */
    private StuLeave getStuLeave(List<StuLeave> currentSL) {
        StuLeave currentStuLeave = null;
        if (currentSL.size() > 1) {
            currentStuLeave = currentSL.get(currentSL.size() - 1);
        } else {
            currentStuLeave = currentSL.get(0);
        }
        return currentStuLeave;
    }

    /**
     * 设置人脸信息
     *
     * @param protectedStudent
     * @return
     */
    private EccStudentFace getEccStudentFace(ProtectedStudent protectedStudent) {
        EccStudentFace studentFace = new EccStudentFace();
        if (StringUtils.isNotEmpty(protectedStudent.getClassId())) {
            studentFace.setClassesId(protectedStudent.getClassId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getStudentId())) {
            studentFace.setStudentId(protectedStudent.getStudentId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getSchoolId())) {
            studentFace.setSchoolId(protectedStudent.getSchoolId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getGradeId())) {
            studentFace.setGradeId(protectedStudent.getGradeId());
        }
        return studentFace;
    }

    /**
     * 设置当前学生信息
     *
     * @param protectedStudent
     * @return
     */
    private Student getStudent(ProtectedStudent protectedStudent) {
        Student student = new Student();
        if (StringUtils.isNotEmpty(protectedStudent.getClassId())) {
            student.setClassesId(protectedStudent.getClassId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getSchoolId())) {
            student.setSchoolId(protectedStudent.getSchoolId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getGradeId())) {
            student.setGradeId(protectedStudent.getGradeId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getStudentId())) {
            student.setId(protectedStudent.getStudentId());
        }
        return student;
    }

    /**
     * 设置当前考勤实体对象
     *
     * @param protectedStudent
     */
    private EccStudentKqRecord getStudentKqRecord(ProtectedStudent protectedStudent) {
        EccStudentKqRecord record = new EccStudentKqRecord();
        if (StringUtils.isNotEmpty(protectedStudent.getKqEndDate())) {
            record.setDkEndTime(protectedStudent.getKqEndDate());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getKqBeginDate())) {
            record.setDkBeginTime(protectedStudent.getKqBeginDate());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getClassId())) {
            record.setClassesId(protectedStudent.getClassId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getSchoolId())) {
            record.setSchoolId(protectedStudent.getSchoolId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getGradeId())) {
            record.setGradeId(protectedStudent.getGradeId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getStudentId())) {
            record.setStudentId(protectedStudent.getStudentId());
        }
        return record;
    }

    /**
     * 获请假结束时间属于哪一节课
     *
     * @return
     */
    private String[] currentCourse(ProtectedStudent protectedStudent, StuLeave stuLeave) {
        List<ClassScheduleInit> listClassSchedules = eccStudentFaceService.getClassScheduleInitsForCache(protectedStudent.getSchoolId());
        if (CollUtil.isNotEmpty(listClassSchedules)) {
            if(DateUtil.parse(stuLeave.getEndTime()).isBefore(DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),listClassSchedules.get(0).getStartTime())))){
                return new String[]{listClassSchedules.get(0).getStartTime(),listClassSchedules.get(0).getEndTime()};
            }
            for (int i = 0; i < listClassSchedules.size(); i++) {
                if (DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),listClassSchedules.get(i).getStartTime()), Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(stuLeave.getEndTime()))
                        && DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqEndDate(),listClassSchedules.get(i).getEndTime()), Constant.DATE_FORMATTER).isAfter(DateUtil.parse(stuLeave.getEndTime()))) {
                    return new String[]{listClassSchedules.get(i).getStartTime(), listClassSchedules.get(i).getEndTime()};
                } else if (i < listClassSchedules.size() - 1) {
                    if ((DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqBeginDate(),listClassSchedules.get(i).getEndTime()), Constant.DATE_FORMATTER).isBeforeOrEquals(DateUtil.parse(stuLeave.getEndTime()))
                            && DateUtil.parse(DateUtils.getCurrentClassTime(protectedStudent.getKqEndDate(), listClassSchedules.get(i + 1).getStartTime()), Constant.DATE_FORMATTER).isAfter(DateUtil.parse(stuLeave.getEndTime())))) {
                        return new String[]{listClassSchedules.get(i + 1).getStartTime(), listClassSchedules.get(i + 1).getEndTime()};
                    }
                }
            }
        }
        log.error(String.format("未查询到学校ID为%1%s的课表信息",protectedStudent.getSchoolId()));
        return null;
    }

    /**
     * 获取当前考勤开始时间
     * @param protectedStudent
     * @return
     */
    public String getKqBeginTime(ProtectedStudent protectedStudent){
        List<ClassScheduleInit> listClassSchedules = eccStudentFaceService.getClassScheduleInitsForCache(protectedStudent.getSchoolId());
        if(CollUtil.isNotEmpty(listClassSchedules)){
             return listClassSchedules.get(0).getStartTime();
        }
        return "";
    }

    /***
     * 获取考勤时间 课程表第一节课程开始时间
     * @return
     */
    private String getKqBeginTime(ProtectedStudent protectedStudent, String date) {
        List<ClassScheduleInit> listClassSchedules = eccStudentFaceService.getClassScheduleInitsForCache(protectedStudent.getSchoolId());
        if (CollUtil.isNotEmpty(listClassSchedules)) {
            String startTime = listClassSchedules.get(0).getStartTime();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(startTime)) {
                if (Objects.nonNull(date)) {
                    return DateUtils.getCurrentClassTime(date, startTime);
                } else {
                    return DateUtils.getClassTime(startTime);
                }
            }
        }
        return "";
    }

    /**
     * 获取考勤结束时间 最后一节课时间
     *
     * @param protectedStudent
     * @return
     */
    private String getKqEndTime(ProtectedStudent protectedStudent, String date) {
        List<ClassScheduleInit> listClassSchedules = eccStudentFaceService.getClassScheduleInitsForCache(protectedStudent.getSchoolId());
        if (CollUtil.isNotEmpty(listClassSchedules)) {
            String endTime = listClassSchedules.get(listClassSchedules.size() - 1).getEndTime();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(endTime)) {
                if (Objects.nonNull(date)) {
                    return DateUtils.getCurrentClassTime(date, endTime);
                }
                return DateUtils.getClassTime(endTime);
            }
        }
        return "";
    }



    /**
     * 设置请假实体对象
     *
     * @param protectedStudent
     * @return
     */
    private StuLeave getStuLeave(ProtectedStudent protectedStudent) {
        StuLeave stuLeave = new StuLeave();
        Student stuInfo = new Student();
        if (StringUtils.isNotEmpty(protectedStudent.getSchoolId())) {
            stuLeave.setSchoolId(protectedStudent.getSchoolId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getClassId())) {
            stuInfo.setClassesId(protectedStudent.getClassId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getGradeId())) {
            stuInfo.setGradeId(protectedStudent.getGradeId());
        }
        if (StringUtils.isNotEmpty(protectedStudent.getStudentId())) {
            stuInfo.setId(protectedStudent.getStudentId());
        }

        stuLeave.setApproveStatus("0");
        stuLeave.setBeginTime(DateUtil.format(DateUtil.parse(protectedStudent.getKqBeginDate(),"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm"));
        stuLeave.setEndTime(DateUtil.format(DateUtil.parse(protectedStudent.getKqEndDate(),"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm"));
        Pager pager = new Pager();
        stuLeave.setStudent(stuInfo);
        stuLeave.setPager(pager);
        return stuLeave;
    }




    /**
     * 获取异常考勤导出数据
     */
    public List<ExportKqRecord> getExportKqData(ProtectedStudent protectedStudent) {
        if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate()) && StringUtils.isNotEmpty(protectedStudent.getKqEndDate())){
            List<ProtectedStudent> list = findStudentKqByCondition(protectedStudent);
            if (CollectionUtil.isNotEmpty(list)) {
                //排除法定假日
                List<Integer> status = Arrays.asList(5, 6);
                list.removeIf(item -> (item.getAberrant() != null && item.getAberrant() == 1) || status.contains(item.getKqState()));
                //拼接姓名及请假时间
                List<ExportKqRecord> exportList = new ArrayList<>();
                setNameAndTime(list, exportList);
                //组装导出数据
                setClassInfo(exportList);
                // 根据三个字段去重
                return removeDuplicates(exportList);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 去重
     */
    private List<ExportKqRecord> removeDuplicates(List<ExportKqRecord> exportList) {
        return exportList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(o -> o.getRecordDate() + "#" + o.getKqState() + "#" + o.getClassId()))),
                ArrayList::new));
    }

    /**
     * 设置班级信息
     */
    private void setClassInfo(List<ExportKqRecord> exportList) {
        for (ExportKqRecord i : exportList) {
            for (ExportKqRecord j : exportList) {
                if (i.getRecordDate().equals(j.getRecordDate()) && i.getKqState().equals(j.getKqState())
                        && i.getClassId().equals(j.getClassId()) && !i.getStudentId().equals(j.getStudentId())) {
                    StringBuilder sb = new StringBuilder();
                    i.setName(sb.append(i.getName()).append(",").append(j.getName()).toString());
                }
            }
        }

        List<String> classList = exportList.stream().map(ExportKqRecord::getClassId).distinct().collect(Collectors.toList());
        //获取班级信息
        Map<String, JwClasses> map = new HashMap<>();
        for (String id : classList) {
            map.put(id, findJwClassesById(id));
        }
        exportList.forEach(data -> {
            data.setKqStatus(getStatusString(data.getKqState()));
            int num = StrUtil.count(data.getName(), ",") + 1;
            StringBuilder builder = new StringBuilder();
            builder.append(map.get(data.getClassId()).getGradeName()).append("（").append(map.get(data.getClassId()).getNumber())
                    .append("）班 ").append(num).append("人");
            data.setClassAndStudents(builder.toString());
        });
    }

    /**
     * 设置异常考勤状态
     */
    private String getStatusString(int status) {
        switch (status) {
            case 1:
                return "未到";
            case 2:
                return "迟到";
            case 4:
                return "请假";
            default:
                return "";
        }
    }

    /**
     * 拼接姓名及请假时间
     */
    private void setNameAndTime(List<ProtectedStudent> list, List<ExportKqRecord> exportList) {
        list.forEach(item -> {
            if (item.getKqState() != null && 4 == item.getKqState() && CollectionUtil.isNotEmpty(item.getStuLeaveInfos())) {
                StringBuilder dateString = new StringBuilder();
                boolean flag = false;
                for (EccKqStuLeaveInfo entry : item.getStuLeaveInfos()) {
                    if (flag) {
                        dateString.append(" ");
                    }
                    dateString.append(StrUtil.subAfter(entry.getBeginTime(), " ", false))
                            .append("-").append(StrUtil.subAfter(entry.getEndTime(), " ", false));
                    flag = true;
                }

                item.setName(item.getName() + dateString.toString());
            }
        });
        //日期正序
        List<ProtectedStudent> middleList = list.stream().sorted(Comparator.comparing(ProtectedStudent::getRecordDate)).collect(Collectors.toList());
        for (ProtectedStudent data : middleList) {
            ExportKqRecord model = new ExportKqRecord();
            BeanUtils.copyProperties(data, model);
            exportList.add(model);
        }
    }

    /**
     * 通过classId获取班级信息
     *
     * @param id 班级id
     * @return 班级信息
     */
    private JwClasses findJwClassesById(String id) {
        return jwClassesFeign.findJwClassesById(id);
    }

}
