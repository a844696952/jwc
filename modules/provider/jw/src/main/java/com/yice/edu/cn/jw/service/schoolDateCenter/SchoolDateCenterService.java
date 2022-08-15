package com.yice.edu.cn.jw.service.schoolDateCenter;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.DatePaper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.building.SpaceOfType;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourceCensus;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.common.util.LocalDateTimeUtils;
import com.yice.edu.cn.common.util.math.MathKit;
import com.yice.edu.cn.jw.feign.KqOriginalData.KqWorkerDailyFeign;
import com.yice.edu.cn.jw.feign.analysisStudentScore.AssetsStockDetailFeign;
import com.yice.edu.cn.jw.feign.repairNew.RepairNewFeign;
import com.yice.edu.cn.jw.service.building.BuildingService;
import com.yice.edu.cn.jw.service.classes.JwClassesService;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.jw.service.jySchoolResoucesByDay.JySchoolResoucesService;
import com.yice.edu.cn.jw.service.qusBankResource.SchoolQusBankService;
import com.yice.edu.cn.jw.service.school.SchoolService;
import com.yice.edu.cn.jw.service.student.JwStudentGraduationService;
import com.yice.edu.cn.jw.service.student.StudentService;
import com.yice.edu.cn.jw.service.teacher.TeacherService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SchoolDateCenterService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private AssetsStockDetailFeign assetsStockDetailFeign;
    @Autowired
    private KqWorkerDailyFeign kqWorkerDailyFeign;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;
    @Autowired
    private JwStudentGraduationService jwStudentGraduationService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private RepairNewFeign repairNewFeign;
    @Autowired
    private PaperService paperService;
    @Autowired
    private SchoolQusBankService schoolQusBankService;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolDateCenter findSchoolDateCenterById(String id) {
        return mot.findById(id,SchoolDateCenter.class);
    }
    public void saveSchoolDateCenter(SchoolDateCenter schoolDateCenter) {
        schoolDateCenter.setId(sequenceId.nextId());
        mot.insert(schoolDateCenter);
    }
    public List<SchoolDateCenter> findSchoolDateCenterListByCondition(SchoolDateCenter schoolDateCenter) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolDateCenter.class)).find(MongoKit.buildMatchDocument(schoolDateCenter));
        Pager pager = schoolDateCenter.getPager();
        MongoKit.appendSort(query,pager);
        MongoKit.appendPage(query,pager);
        MongoKit.appendProjection(query,pager);
        List<SchoolDateCenter> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(SchoolDateCenter.class, document)));
        return list;
    }
    public long findSchoolDateCenterCountByCondition(SchoolDateCenter schoolDateCenter) {
        return mot.getCollection(mot.getCollectionName(SchoolDateCenter.class)).countDocuments(MongoKit.buildMatchDocument(schoolDateCenter));
    }
    public SchoolDateCenter findOneSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter) {
       FindIterable<Document> query = mot.getCollection(mot.getCollectionName(SchoolDateCenter.class)).find(MongoKit.buildMatchDocument(schoolDateCenter));
       MongoKit.appendProjection(query,schoolDateCenter.getPager());
       return mot.getConverter().read(SchoolDateCenter.class,query.first());
    }

    public void updateSchoolDateCenter(SchoolDateCenter schoolDateCenter) {
        mot.updateFirst(query(where("id").is(schoolDateCenter.getId())), MongoKit.update(schoolDateCenter,false),SchoolDateCenter.class);
    }
    public void updateSchoolDateCenterForAll(SchoolDateCenter schoolDateCenter) {
        mot.updateFirst(query(where("id").is(schoolDateCenter.getId())), MongoKit.update(schoolDateCenter,true),SchoolDateCenter.class);
    }
    public void deleteSchoolDateCenter(String id) {
        mot.remove(query(where("id").is(id)),SchoolDateCenter.class);
    }
    public void deleteSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter) {
        mot.getCollection(mot.getCollectionName(SchoolDateCenter.class)).deleteMany(MongoKit.buildMatchDocument(schoolDateCenter));
    }
    public void batchSaveSchoolDateCenter(List<SchoolDateCenter> schoolDateCenters){
        schoolDateCenters.forEach(schoolDateCenter -> schoolDateCenter.setId(sequenceId.nextId()));
        mot.insertAll(schoolDateCenters);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    //1为学生数据，2为教师数据***，3资源数据***，4为场地数据，5为考勤数据，6为资产固定数据***,7为资产易耗品数据***,8为学生考勤
    public void  updateSchoolDateCenterByType(SchoolDateCenter schoolDateCenter,Integer type){
        Criteria criteria = Criteria.where("schoolId").is(schoolDateCenter.getSchoolId());
        Query query1 = new Query(criteria);
        SchoolDateCenter schoolDateCenter1 = mot.findOne(query1,SchoolDateCenter.class);
        Update update = new Update();
        switch (type){
            case 1:
                StudentSum studentSum = schoolDateCenter.getStudentSum();
                if(studentSum!=null&&studentSum.getGirlNumber()!=null){
                    studentSum.setStudentNumber(studentSum.getBoyNumber()+studentSum.getGirlNumber());
                }
                update.set("studentSum",studentSum);
                break;
            case 2:
                TeacherSum teacherSum = schoolDateCenter.getTeacherSum();
                if(teacherSum!=null&&teacherSum.getInitNumber()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.HALFMONTH;i++){
                        longs.add(new Double(Math.random()*899+100).longValue());
                    }
                    teacherSum.setHalfMonth(longs);
                }
                update.set("teacherSum",teacherSum);
                break;
            case 3:
                CampusResources campusResources = schoolDateCenter.getCampusResources();
                if(campusResources!=null&&campusResources.getInitNumber()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.HALFMONTH;i++){
                        longs.add(new Double(Math.random()*campusResources.getInitNumber()*0.4+campusResources.getInitNumber()*0.8).longValue());
                    }
                    campusResources.setHlafMonth(longs);
                }
                update.set("campusResources",campusResources);
                break;
            case 4:
                List<SpaceOfType> jwSpaceList = schoolDateCenter.getJwSpaceList();
                update.set("jwSpaceList",jwSpaceList);
                break;
            case 5:
                TeacherAttendance teacherAttendance = schoolDateCenter.getTeacherAttendance();
                //教师考勤分布图
                if(teacherAttendance!=null&&teacherAttendance.getInitNumber()!=null){
                    List<Double> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.WEEK;i++){
                        longs.add(new Double(Math.random()));
                    }
                    teacherAttendance.setWeekTotal(longs);
                }
                //教师考勤今日打卡率
                if(teacherAttendance!=null&&teacherAttendance.getTeacherThisKq()!=null&&teacherAttendance.getFloatValue()!=null){
                    if(teacherAttendance.getFloatValue()==0){
                        teacherAttendance.setTeacherTheClockRate(teacherAttendance.getTeacherThisKq()/100.0);
                    }else {
                        teacherAttendance.setTeacherTheClockRate((Math.random()*2*teacherAttendance.getFloatValue()+teacherAttendance.getTeacherThisKq()-teacherAttendance.getFloatValue())/100);
                    }
                }else{
                    teacherAttendance.setTeacherTheClockRate(Math.random());
                }
                update.set("teacherAttendance",teacherAttendance);
                break;
            case 6:
                Fixation fixation = schoolDateCenter.getFixation();
                Fixation assetsStockDetail = schoolDateCenter.getAssetsStockDetail();
                //近一个月
                if(fixation!=null&&fixation.getMonthOrdinate()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.MONTH;i++){
                        longs.add(new Double(Math.random()*fixation.getMonthOrdinate()*0.4+fixation.getMonthOrdinate()*0.8).longValue());
                    }
                    fixation.setMonthTotal(longs);
                }
                //近一年
                if(fixation!=null&&fixation.getYearOrdinate()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.YEAR;i++){
                        longs.add(new Double(Math.random()*fixation.getYearOrdinate()*0.4+fixation.getYearOrdinate()*0.8).longValue());
                    }
                    fixation.setYearTotal(longs);
                }
                update.set("fixation",fixation);
                update.set("assetsStockDetail",assetsStockDetail);
                break;
            case 7:
                Fixation consumable = schoolDateCenter.getConsumable();
                Fixation assetsStockDetail1 = schoolDateCenter.getAssetsStockDetail();
                //近一个月
                if(consumable!=null&&consumable.getMonthOrdinate()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.MONTH;i++){
                        longs.add(new Double(Math.random()*consumable.getMonthOrdinate()*0.4+consumable.getMonthOrdinate()*0.8).longValue());
                    }
                    consumable.setMonthTotal(longs);
                }
                //近一年
                if(consumable!=null&&consumable.getYearOrdinate()!=null){
                    List<Long> longs = new ArrayList<>();
                    for(int i=0;i<Constant.SCHOOL_DATE_CENTER.YEAR;i++){
                        longs.add(new Double(Math.random()*consumable.getYearOrdinate()*0.4+consumable.getYearOrdinate()*0.8).longValue());
                    }
                    consumable.setYearTotal(longs);
                }
                update.set("consumable",consumable);
                update.set("assetsStockDetail",assetsStockDetail1);
                break;
            case 8:
                StudentThisKq studentThisKq = schoolDateCenter.getStudentThisKq();
                DecimalFormat df1 = new DecimalFormat("0.00%");
                if(studentThisKq!=null&&studentThisKq.getStudentKq()!=null&&studentThisKq.getFloatValue()!=null){
                    Double kq = 0.0;
                    if(studentThisKq.getFloatValue()==0){
                        kq = studentThisKq.getStudentKq().doubleValue();
                    }else{
                        kq = (Math.random()*2*studentThisKq.getFloatValue()+studentThisKq.getStudentKq()-studentThisKq.getFloatValue())/100;
                    }
                    studentThisKq.setStudentKqRatio(df1.format(kq));
                }
                update.set("studentThisKq",studentThisKq);
                break;
            case 9:
                TeacherAttendance teacherAttendance1 = schoolDateCenter.getTeacherAttendance();
                StudentThisKq studentThisKq1 = schoolDateCenter.getStudentThisKq();
                DecimalFormat df2 = new DecimalFormat("0.00%");
                //学生考勤
                if(studentThisKq1!=null&&studentThisKq1.getStudentKq()!=null&&studentThisKq1.getFloatValue()!=null){
                    Double kq = 0.0;
                    if(studentThisKq1.getFloatValue()==0){
                        kq = studentThisKq1.getStudentKq().doubleValue()/100;
                    }else{
                        kq = (Math.random()*2*studentThisKq1.getFloatValue()+studentThisKq1.getStudentKq()-studentThisKq1.getFloatValue())/100;
                    }
                    studentThisKq1.setStudentKqRatio(df2.format(kq));
                }else if(studentThisKq1==null){
                    studentThisKq1 = new StudentThisKq();
                    studentThisKq1.setStudentKqRatio(df2.format(Math.random()));
                }else{
                    studentThisKq1.setStudentKqRatio(df2.format(Math.random()));
                }
                update.set("studentThisKq",studentThisKq1);

                //教师考勤今日打卡率
                Double k =0.0;
                if(teacherAttendance1!=null&&teacherAttendance1.getTeacherThisKq()!=null&&teacherAttendance1.getFloatValue()!=null){
                    if(teacherAttendance1.getFloatValue()==0){
                        k = (teacherAttendance1.getTeacherThisKq()/100.0);
                    }else {
                        k = ((Math.random()*2*teacherAttendance1.getFloatValue()+teacherAttendance1.getTeacherThisKq()*0.8)/100);
                    }
                }else{
                        k = Math.random();
                }
                update.set("teacherAttendance.teacherTheClockRate",k);
                break;
            default:
                break;
        }

        if(schoolDateCenter1==null&&schoolDateCenter.getSchoolId()!=null){
            saveSchoolDateCenter(schoolDateCenter);
        }else {
            mot.updateMulti(query1,update,SchoolDateCenter.class);
        }

    }



    public SchoolDateCenter findSchoolCompusCenterBySchoolId(String schoolId){
//        Map<String,Long> map = teacherFeign.findTeacherSummaryBySchool4Index(mySchoolId());
        SchoolDateCenter schoolDateCenter  = new SchoolDateCenter();
        //查询学校数据
        School school = schoolService.findSchoolById(schoolId);
        schoolDateCenter.setSchool(school);
        schoolDateCenter.setSchoolId(schoolId);
        SchoolDateCenter schoolDateCenter1 = new SchoolDateCenter();
        schoolDateCenter1.setSchoolId(schoolId);
        //查询假数据
        SchoolDateCenter schoolDateCenter2 = findOneSchoolDateCenterByCondition(schoolDateCenter1);
        if(schoolDateCenter2==null){
            getTruesSchoolDateCenter(schoolId);
        }else{
            //当前时间
            String endTime = LocalDateTimeUtils.nowString();
            //往前推15天时间
            String createTime = LocalDateTimeUtils.getNextDays(Constant.SCHOOL_DATE_CENTER.HALFMONTH);

            //一年
            List<String> yearTime = LocalDateTimeUtils.getYearMonthDay(Constant.SCHOOL_DATE_CENTER.YEAR);
            //一个月
            List<String> monthTime = LocalDateTimeUtils.getMonthDays(Constant.SCHOOL_DATE_CENTER.MONTH);
            //半个月
            List<String> halfMonthTime = LocalDateTimeUtils.getMonthDays(Constant.SCHOOL_DATE_CENTER.HALFMONTH);
            //学生总数
            if(schoolDateCenter2.getStudentSum()!=null&&schoolDateCenter2.getStudentSum().getGirlNumber()!=null){
                StudentSum studentSum = schoolDateCenter2.getStudentSum();
                Boolean[] flag = new Boolean[2];
                //女生占比
                studentSum.setRateOfFemale(MathKit.getPercent(studentSum.getGirlNumber(),studentSum.getStudentNumber()));
                //男生占比
                studentSum.setRateOfMale(MathKit.getPercent(studentSum.getBoyNumber(),studentSum.getStudentNumber()));
                //相对于去年百分比
                if(studentSum.getLastYearStudentNumber()==null||studentSum.getLastYearStudentNumber()==0){
                    studentSum.setPercentageIncrease("100%");
                }else{
                    studentSum.setPercentageIncrease(MathKit.getPercent(Math.abs(studentSum.getLastYearStudentNumber()-studentSum.getStudentNumber()),studentSum.getLastYearStudentNumber()));
                }
                //告诉前端是上升还是下降
                flag[0] = studentSum.getStudentNumber()-studentSum.getLastYearStudentNumber()>=0?true:false;
                //相对于前年百分比
                if(studentSum.getYearBeforeLast()==0){
                    studentSum.setPercentageDecline("100%");
                }else{
                    studentSum.setPercentageDecline(MathKit.getPercent(Math.abs(studentSum.getYearBeforeLast()-studentSum.getStudentNumber()),studentSum.getYearBeforeLast()));
                }
                flag[1] = studentSum.getStudentNumber()-studentSum.getYearBeforeLast()>=0?true:false;
                studentSum.setFlags(flag);

                schoolDateCenter.setStudentSum(studentSum);
            }else{
                getStudentNum(schoolDateCenter);
            }


            //教师总数
            if(schoolDateCenter2.getTeacherSum()!=null&&schoolDateCenter2.getTeacherSum().getGirlTeacherNumber()!=null){
                TeacherSum teacherSum = schoolDateCenter2.getTeacherSum();
                teacherSum.setSchoolId(schoolDateCenter2.getSchoolId());
                //获取访问量和访问量分布图
                getTeacherVisit(teacherSum);
                if(teacherSum.getBoyTeacherNumber()!=null&&teacherSum.getGirlTeacherNumber()!=null){
                    teacherSum.setRateOfBoy(MathKit.getPercent(teacherSum.getBoyTeacherNumber(),(teacherSum.getBoyTeacherNumber()+teacherSum.getGirlTeacherNumber())));
                    teacherSum.setRateOfGirl(MathKit.getPercent(teacherSum.getGirlTeacherNumber(),(teacherSum.getBoyTeacherNumber()+teacherSum.getGirlTeacherNumber())));
                    teacherSum.setTeacherNumber(teacherSum.getBoyTeacherNumber()+teacherSum.getGirlTeacherNumber());
                }else{
                    getTeacherRatio(teacherSum);
                }
                schoolDateCenter.setTeacherSum(teacherSum);
            }else{
                getTeacherNum(schoolDateCenter);
            }


            //场地
            if(schoolDateCenter2.getJwSpaceList()!=null&&schoolDateCenter2.getJwSpaceList().size()>0){
                schoolDateCenter.setJwSpaceList(schoolDateCenter2.getJwSpaceList());
            }else{
                getJwSpance(schoolDateCenter);
            }

            //教师考勤数据
            if(schoolDateCenter2.getTeacherAttendance()!=null&&schoolDateCenter2.getTeacherAttendance().getKqRealityTotal()!=null&&schoolDateCenter2.getTeacherAttendance().getKqTotal()!=null){
                TeacherAttendance teacherAttendance  = schoolDateCenter2.getTeacherAttendance();
                //教师考勤率
                teacherAttendance.setTeacherAttendance((teacherAttendance.getKqRealityTotal()/teacherAttendance.getKqTotal())*1.0);
                //月同比
                if(teacherAttendance.getKqUpMonthTotal()==0){
                    teacherAttendance.setMonthComparedToTheSame(1.0);
                }else{
                    teacherAttendance.setMonthComparedToTheSame(Math.abs((teacherAttendance.getKqUpMonthTotal()-teacherAttendance.getKqThisMonthTotal()))*1.0/teacherAttendance.getKqUpMonthTotal());
                }
                //月同比是上升还是下降
                teacherAttendance.setMonthComparedIsRise(teacherAttendance.getKqThisMonthTotal()-teacherAttendance.getKqUpMonthTotal()>=0?true:false);
                //周同比
                if(teacherAttendance.getUpWeekTotal()==0){
                    teacherAttendance.setWeekComparedToTheSame(1.0);
                }else{
                    teacherAttendance.setWeekComparedToTheSame(Math.abs(teacherAttendance.getUpWeekTotal()-teacherAttendance.getThisWeekTotal())*1.0/teacherAttendance.getUpWeekTotal());
                }
                //周同比是上升还是下降
                teacherAttendance.setWeekComparedIsRise(teacherAttendance.getThisWeekTotal()-teacherAttendance.getUpWeekTotal()>=0?true:false);
                //判断教师考勤分布图是否存在
                if(teacherAttendance.getInitNumber()!=null&&teacherAttendance.getWeekTotal()!=null){
                    List<Double> longs = teacherAttendance.getWeekTotal();
                    List<HashMap<String,Double>> teacheKq = new ArrayList<>();
                    for(int i=0;i<longs.size();i++){
                        HashMap<String,Double> map = new HashMap<>();
                        map.put(LocalDateTimeUtils.getNextDays(i),longs.get(i));
                        teacheKq.add(map);
                    }
                    teacherAttendance.setTeacherWeekAttList(teacheKq);
                }else {
                    TeacherAttendance teacherAttendance1 = new TeacherAttendance();
                    teacherAttendance1.setSchooleId(schoolId);
                    teacherAttendance1 = kqWorkerDailyFeign.findNearSeverDaysWorkerKqHistogram(teacherAttendance1);
                    teacherAttendance.setTeacherWeekAttList(teacherAttendance1.getTeacherWeekAttList());
                }

                schoolDateCenter.setTeacherAttendance(teacherAttendance);
            }else{
                getTeacherAttendance(schoolDateCenter);
            }

            //资源总数
            if(schoolDateCenter2.getCampusResources()!=null){
                CampusResources campusResources = schoolDateCenter2.getCampusResources();
                campusResources.setSchoolId(schoolId);

                JySchoolResouces jySchoolResouces = new JySchoolResouces();
                jySchoolResouces.setStartTime(createTime);
                jySchoolResouces.setEndTime(endTime);
                jySchoolResouces.setSchoolId(schoolId);
                //周上传数量
                if(schoolDateCenter2.getCampusResources().getSchoolWeekResourcesNumber()==null){
                    long weekCount = jySchoolResoucesService.censusSumSchoolResouces(jySchoolResouces);
                    campusResources.setSchoolWeekResourcesNumber(weekCount);
                }

                List<JySchoolResourcesByDay> jySchoolResourcesByDays = new ArrayList<>();
                //资源日期分布图
                if(campusResources.getInitNumber()!=null&&campusResources.getHlafMonth()!=null){
                    List<Long> longs = campusResources.getHlafMonth();
                    for(int i=0;i<longs.size();i++){
                        JySchoolResourcesByDay jySchoolResourcesByDay = new JySchoolResourcesByDay();
                        jySchoolResourcesByDay.setTime(halfMonthTime.get(i));
                        jySchoolResourcesByDay.setCnt(longs.get(i).intValue());
                        jySchoolResourcesByDays.add(jySchoolResourcesByDay);
                    }
                }else{
                    jySchoolResourcesByDays = getZiyuanTu(jySchoolResouces,createTime,endTime);
                }
                campusResources.setJyResouces(jySchoolResourcesByDays);
                campusResources.setSchoolResourcesNumber(getZiYuanSum(schoolId));
                //校内资源
                if(campusResources.getScholasticResources()==null){
                    getScholasticResources(campusResources);
                }

                schoolDateCenter.setCampusResources(campusResources);
            }else{
                getCampusResources(schoolDateCenter);
            }

            //教师今日打卡考勤率
            if(schoolDateCenter2.getTeacherAttendance()!=null&&schoolDateCenter2.getTeacherAttendance().getTeacherTheClockRate()!=null){
                schoolDateCenter.setTeacherTheClockRate(schoolDateCenter2.getTeacherAttendance().getTeacherTheClockRate());
            }else {
                TeacherAttendance teacherAttendance = new TeacherAttendance();
                teacherAttendance.setSchooleId(schoolId);
                schoolDateCenter.setTeacherTheClockRate(kqWorkerDailyFeign.findTodayCheckRate(teacherAttendance));
            }

            List<List<AssetsStockDetail>> a = new ArrayList<>();
            AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
            assetsStockDetail.setAssetsProperty(1);
            assetsStockDetail.setSchoolId(schoolId);

            //固定资产年判断
            if(schoolDateCenter2.getFixation()!=null&&schoolDateCenter2.getFixation().getYearOrdinate()!=null&&schoolDateCenter2.getFixation().getYearTotal()!=null){
                List<Long> longs = schoolDateCenter2.getFixation().getYearTotal();
                List<AssetsStockDetail> assetsStockDetails = new ArrayList<>();
                for (int i =0;i<longs.size();i++){
                    AssetsStockDetail assetsStockDetails1 = new AssetsStockDetail();
                    assetsStockDetails1.setYCount(longs.get(i).intValue());
                    assetsStockDetails1.setXTime(yearTime.get(i));
                    assetsStockDetails.add(assetsStockDetails1);
                }
                a.add(assetsStockDetails);
            }else{
                List<AssetsStockDetail> assetsStockDetails = assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
                a.add(assetsStockDetails);
            }

            //固定资产月判断
            if(schoolDateCenter2.getFixation()!=null&&schoolDateCenter2.getFixation().getMonthOrdinate()!=null&&schoolDateCenter2.getFixation().getMonthTotal()!=null){
                List<Long> longs = schoolDateCenter2.getFixation().getMonthTotal();
                List<AssetsStockDetail> assetsStockDetails = new ArrayList<>();
                for (int i =0;i<longs.size();i++){
                    AssetsStockDetail assetsStockDetails1 = new AssetsStockDetail();
                    assetsStockDetails1.setYCount(longs.get(i).intValue());
                    assetsStockDetails1.setXTime(monthTime.get(i));
                    assetsStockDetails.add(assetsStockDetails1);
                }
                a.add(assetsStockDetails);
            }else{
                List<AssetsStockDetail> assetsStockDetails = assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);
                a.add(assetsStockDetails);
            }

            assetsStockDetail.setAssetsProperty(2);
            //易耗品年判断
            if(schoolDateCenter2.getConsumable()!=null&&schoolDateCenter2.getConsumable().getYearTotal()!=null){
                List<Long> longs = schoolDateCenter2.getConsumable().getYearTotal();
                List<AssetsStockDetail> assetsStockDetails = new ArrayList<>();
                for (int i =0;i<longs.size();i++){
                    AssetsStockDetail assetsStockDetails1 = new AssetsStockDetail();
                    assetsStockDetails1.setYCount(longs.get(i).intValue());
                    assetsStockDetails1.setXTime(yearTime.get(i));
                    assetsStockDetails.add(assetsStockDetails1);
                }
                a.add(assetsStockDetails);
            }else{
                List<AssetsStockDetail> assetsStockDetails = assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
                a.add(assetsStockDetails);
            }


            //易耗月判断
            if(schoolDateCenter2.getConsumable()!=null&&schoolDateCenter2.getConsumable().getMonthOrdinate()!=null&&schoolDateCenter2.getConsumable().getMonthTotal()!=null){
                List<Long> longs = schoolDateCenter2.getConsumable().getMonthTotal();
                List<AssetsStockDetail> assetsStockDetails = new ArrayList<>();
                for (int i =0;i<longs.size();i++){
                    AssetsStockDetail assetsStockDetails1 = new AssetsStockDetail();
                    assetsStockDetails1.setYCount(longs.get(i).intValue());
                    assetsStockDetails1.setXTime(monthTime.get(i));
                    assetsStockDetails.add(assetsStockDetails1);
                }
                a.add(assetsStockDetails);
            }else{
                List<AssetsStockDetail> assetsStockDetails = assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);
                a.add(assetsStockDetails);
            }

            schoolDateCenter.setAssetsStockDetailList(a);
            //占用资产不等于空
            if(schoolDateCenter2.getAssetsStockDetail()!=null&&schoolDateCenter2.getAssetsStockDetail().getOccupyTotal()!=null){
                Fixation fixation1 = schoolDateCenter2.getAssetsStockDetail();
                //资产使用率
                fixation1.setZcsyl(MathKit.getPercent(fixation1.getOccupyTotal(),fixation1.getAssetsTotal()));
                //周同比
                if(fixation1.getUpWeekUpTotal()==null||fixation1.getUpWeekUpTotal()==0){
                    fixation1.setWeekComparedToTheSame("100%");
                }else{
                    fixation1.setWeekComparedToTheSame(MathKit.getPercent(Math.abs(fixation1.getUpWeekUpTotal()-fixation1.getThisWeekUpTotal()),fixation1.getUpWeekUpTotal()));
                }
                //告诉前端是上升还是下降
                fixation1.setFlag(fixation1.getUpWeekUpTotal()==null?true:(fixation1.getThisWeekUpTotal()-fixation1.getUpWeekUpTotal()>=0?true:false));
                fixation1.setThisWeekUpTotal(fixation1.getThisWeekUpTotal());
                schoolDateCenter.setAssetsStockDetail(fixation1);
            }else{
                getAssetsStockDetail(schoolDateCenter);
            }
            //库存价格
            List<List<AssetsFile>> assetsFiles = new ArrayList<>();
            //固定资产
            if(schoolDateCenter2.getFixation()!=null&&schoolDateCenter2.getFixation().getAssetsFiles()!=null&&schoolDateCenter2.getFixation().getAssetsFiles().size()>0){
                assetsFiles.add(schoolDateCenter2.getFixation().getAssetsFiles());
            }else{
                getAssetsFile(assetsFiles,1,schoolId);
            }
            //易耗品
            if(schoolDateCenter2.getConsumable()!=null&&schoolDateCenter2.getConsumable().getAssetsFiles()!=null&&schoolDateCenter2.getConsumable().getAssetsFiles().size()>0){
                assetsFiles.add(schoolDateCenter2.getConsumable().getAssetsFiles());
            }else{
                getAssetsFile(assetsFiles,2,schoolId);
            }
            schoolDateCenter.setAssetsFiles(assetsFiles);

        }
        //学生考勤
        if(schoolDateCenter2!=null&&schoolDateCenter2.getStudentThisKq()!=null&&schoolDateCenter2.getStudentThisKq().getStudentKqRatio()!=null){
            schoolDateCenter.setStudentThisKq(schoolDateCenter2.getStudentThisKq());
        }else{
            getStudentKq(schoolDateCenter);
        }

        //班级
        getClasses(schoolDateCenter);
        return schoolDateCenter;
    }

    //获取学生总数
    private  void getStudentNum(SchoolDateCenter schoolDateCenter){
        StudentSum studentSum = new StudentSum();
        Boolean[] flag = new Boolean[2];
        Map<String, Long> studentSummaryBySchool4Index = studentService.findStudentSummaryBySchool4Index(schoolDateCenter.getSchoolId());
        JwStudentGraduation jwStudentGraduation = new JwStudentGraduation();
        jwStudentGraduation.setDel(1);
        jwStudentGraduation.setGraduationTime(LocalDateTimeUtils.getNextYear().toString());
        jwStudentGraduation.setSchoolId(schoolDateCenter.getSchoolId());
        //学生年级
        studentSum.setStudentList(studentService.findGradeStudentSummaryBySchool4Index(schoolDateCenter.getSchoolId()));
        //学生总数
        studentSum.setStudentNumber(studentSummaryBySchool4Index.get("total")==0?0L:studentSummaryBySchool4Index.get("total"));

        //女生占比
        //男生占比
        if(studentSummaryBySchool4Index.get("total")!=0){
            studentSum.setRateOfFemale( MathKit.getPercent(studentSummaryBySchool4Index.get("female"),studentSummaryBySchool4Index.get("total")));
            studentSum.setRateOfMale(MathKit.getPercent(studentSummaryBySchool4Index.get("male"),studentSummaryBySchool4Index.get("total")));
        }else{
            studentSum.setRateOfFemale("0%");
            studentSum.setRateOfMale("0%");
        }
        //相对于去年百分比
        if(studentSummaryBySchool4Index.get("lastTotal")==0){
            studentSum.setPercentageIncrease("100%");
        }else {
            studentSum.setPercentageIncrease(MathKit.getPercent(Math.abs(studentSummaryBySchool4Index.get("total")-studentSummaryBySchool4Index.get("lastTotal")),studentSummaryBySchool4Index.get("lastTotal")));
        }
        //告诉前端是上升还是下降
        flag[0] = studentSummaryBySchool4Index.get("total")-studentSummaryBySchool4Index.get("lastTotal")>=0?true:false;
        //相对于前年百分比
        if(studentSummaryBySchool4Index.get("beforeLastTotal")==0){
            studentSum.setPercentageDecline("100%");
        }else {
            studentSum.setPercentageDecline(MathKit.getPercent(Math.abs(studentSummaryBySchool4Index.get("total")-studentSummaryBySchool4Index.get("beforeLastTotal")),studentSummaryBySchool4Index.get("beforeLastTotal")));
        }
        flag[1] = studentSummaryBySchool4Index.get("total")-studentSummaryBySchool4Index.get("beforeLastTotal")>=0?true:false;
        //去年毕业人数
        studentSum.setLastYearNumber(jwStudentGraduationService.findJwStudentGraduationCountByCondition(jwStudentGraduation));
        studentSum.setFlags(flag);
        schoolDateCenter.setStudentSum(studentSum);
    }
    //获取教师总数
    private void getTeacherNum(SchoolDateCenter schoolDateCenter){
        TeacherSum teacherSum = new TeacherSum();
        teacherSum.setSchoolId(schoolDateCenter.getSchoolId());
        getTeacherRatio(teacherSum);
        //设置每日访问量和访问分布图
        getTeacherVisit(teacherSum);
        schoolDateCenter.setTeacherSum(teacherSum);
    }
    //获取教师数量比例
    private void getTeacherRatio(TeacherSum teacherSum ){
        Map<String,Long> mapList = teacherService.findTeacherSummaryBySchool4Index(teacherSum.getSchoolId());
        //教师总数量
        teacherSum.setTeacherNumber(mapList.get("total"));
        //男教师数量
        teacherSum.setBoyTeacherNumber(mapList.get("nan"));
        //女教师数量
        teacherSum.setGirlTeacherNumber(mapList.get("nv"));
        //男老师所占百分比
        //女老师所占百分比
        if(mapList.get("total")!=0){
            teacherSum.setRateOfBoy(MathKit.getPercent(mapList.get("nan"),mapList.get("total")));
            teacherSum.setRateOfGirl(MathKit.getPercent(mapList.get("nv"),mapList.get("total")));
        }else{
            teacherSum.setRateOfGirl("0%");
            teacherSum.setRateOfBoy("0%");
        }
        //教师所教科目和对应人数
        teacherSum.setTeacherList(teacherService.findCourseTeacherSummaryBySchool4Index(teacherSum.getSchoolId()));
    }

    //校内资源
    private void getCampusResources(SchoolDateCenter schoolDateCenter){
        CampusResources campusResources = new CampusResources();
        campusResources.setSchoolId(schoolDateCenter.getSchoolId());
        //获取资源总数
        Long count = getZiYuanSum(schoolDateCenter.getSchoolId());
        //当前时间
        String endTime = LocalDateTimeUtils.nowString();
        //往前推15天时间
        String createTime = LocalDateTimeUtils.getNextDays(Constant.SCHOOL_DATE_CENTER.HALFMONTH);
        JySchoolResouces jyResouces = new JySchoolResouces();
        jyResouces.setSchoolId(schoolDateCenter.getSchoolId());
        jyResouces.setStartTime(createTime);
        jyResouces.setEndTime(endTime);

        List<JySchoolResourcesByDay> jySchoolResourcesByDays = getZiyuanTu(jyResouces,createTime,endTime);

        long weekCount = jySchoolResoucesService.censusSumSchoolResouces(jyResouces);
        //校本资源
        getScholasticResources(campusResources);

        campusResources.setJyResouces(jySchoolResourcesByDays);
        campusResources.setSchoolResourcesNumber(count);
        campusResources.setSchoolWeekResourcesNumber(weekCount);
        schoolDateCenter.setCampusResources(campusResources);

    }

    //教师考勤
    private void getTeacherAttendance(SchoolDateCenter schoolDateCenter){
        TeacherAttendance attendance = new TeacherAttendance();
        attendance.setSchooleId(schoolDateCenter.getSchoolId());
        schoolDateCenter.setTeacherAttendance(kqWorkerDailyFeign.dateCenterFindWorkerArrendance(attendance));
    }


    //资产使用率
    private void getAssetsStockDetail(SchoolDateCenter schoolDateCenter){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(schoolDateCenter.getSchoolId());
        //返回资产使用率
        String zcsyl =  assetsStockDetailFeign.findAssetsUsePercentage(assetsStockDetail);
        if(zcsyl==null){
            zcsyl="0%";
        }
        RepairNew repairNew = new RepairNew();
        repairNew.setSchoolId(schoolDateCenter.getSchoolId());
        //本周报修数
        long week =repairNewFeign.findRepairNewsCountByWeek(repairNew);
        //上周报修数
        long weekLose1 =repairNewFeign.findRepairNewsCountByWeekLose1(repairNew);
        Fixation fixation = new Fixation();
        fixation.setZcsyl(zcsyl);
        //周同比率
        if(weekLose1==0){
            fixation.setWeekComparedToTheSame("100%");
        }else {
            fixation.setWeekComparedToTheSame(MathKit.getPercent(Math.abs(week-weekLose1),weekLose1));
        }
        fixation.setFlag(week-weekLose1>=0?true:false);

        fixation.setThisWeekUpTotal(week);
        schoolDateCenter.setAssetsStockDetail(fixation);

    }

    //班级
    private void getClasses(SchoolDateCenter schoolDateCenter){
        //班级比例
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(schoolDateCenter.getSchoolId());
        jwClasses.setLevelType(Integer.parseInt(schoolDateCenter.getSchool().getTypeId()));
        List<JwClasses> jwClassesList = jwClassesService.getClassesNumber(jwClasses);
        schoolDateCenter.setClassesList(jwClassesList);
    }

    //场地
    private void getJwSpance(SchoolDateCenter schoolDateCenter){
        schoolDateCenter.setJwSpaceList(buildingService.findSpaceListCountOfType(schoolDateCenter.getSchoolId()));
    }


    //校本资源
    private void getScholasticResources(CampusResources schoolDateCenter){
        JySchoolResouces jyResouces = new JySchoolResouces();
        //当前时间
        String endTime = LocalDateTimeUtils.nowString();
        //往前推15天时间
        String createTime = LocalDateTimeUtils.getNextDays(Constant.SCHOOL_DATE_CENTER.HALFMONTH);
        jyResouces.setCreateTime(createTime);
        jyResouces.setEndTime(endTime);
        jyResouces.setSchoolId(schoolDateCenter.getSchoolId());
        //文档：1，图片：2，音频：3，视频：4
        List<JySchoolResourceCensus> jySchoolResouces = jySchoolResoucesService.censusSchoolResouces(jyResouces);
        count:for(int i=1;i<5;i++){
            for(int j=0;j<jySchoolResouces.size();j++){
                if(jySchoolResouces.get(j).getType()==i){
                    continue count;
                }
            }
            JySchoolResourceCensus jySchoolResourceCensus = new JySchoolResourceCensus();
            jySchoolResourceCensus.setNum(0);
            jySchoolResourceCensus.setType(i);
            jySchoolResouces.add(jySchoolResourceCensus);
        }
        Paper paper = new Paper();
        paper.setSchoolId(schoolDateCenter.getSchoolId());
        //试卷总数量
        long total1 = paperService.findTestPaperCountByCondition(paper);
        //校本题库总数量
        SchoolQusBank schoolQusBank = new SchoolQusBank();
        schoolQusBank.setSchoolId(schoolDateCenter.getSchoolId());
        long total2 = schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);
        JySchoolResourceCensus jySchoolResourceCensus = new JySchoolResourceCensus();
        //5为试卷总数
        jySchoolResourceCensus.setNum((int)total1);
        jySchoolResourceCensus.setType(5);
        jySchoolResouces.add(jySchoolResourceCensus);
        //6为校本题库总数量
        JySchoolResourceCensus jySchoolResourceCensus1 = new JySchoolResourceCensus();
        jySchoolResourceCensus1.setType(6);
        jySchoolResourceCensus1.setNum((int)total2);
        jySchoolResouces.add(jySchoolResourceCensus1);


        schoolDateCenter.setScholasticResources(jySchoolResouces);
    }



    //校内资源试卷比例
    private List<PaperDayMonth> getPaperDayMonth(String createTime, String endTime, String schoolId){
        //试卷
        Paper paper1 = new Paper();
        String[] s = new String[]{createTime,endTime};
        paper1.setSchoolId(schoolId);
        paper1.setSearchTimeZone(s);
        List<PaperDayMonth> paperDayMonths = paperService.findEveryDayPaper(paper1);
        List<DatePaper> datePapers = LocalDateTimeUtils.getMonthDay(Constant.SCHOOL_DATE_CENTER.HALFMONTH);
        //最后的统计数据
        List<PaperDayMonth> paperDayMonths1 = new ArrayList<>();
        c:for(int i=0;i<datePapers.size();i++){
            for(int j =0;j<paperDayMonths.size();j++){
                if(paperDayMonths.get(j).getId().getDay()==datePapers.get(i).getDay()){
                    continue c;
                }
            }
            PaperDayMonth paperDayMonth = new PaperDayMonth();
            paperDayMonth.setCount(0L);
            paperDayMonth.setId(datePapers.get(i));
            paperDayMonths1.add(paperDayMonth);
        }

        for(PaperDayMonth p:paperDayMonths){
            paperDayMonths1.add(p);
        }

        return paperDayMonths1;
    }

    //库存价格
    private void getAssetsFile(List<List<AssetsFile>> assetsFile,Integer type,String schoolId){
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setSchoolId(schoolId);
        assetsStockDetail.setAssetsProperty(type);
        assetsFile.add(assetsStockDetailFeign.findFileTotalPrice(assetsStockDetail));
    }

    //获取资源总数
    private Long getZiYuanSum(String schoolId){
        //校内资源总数(文档，图片，音频，视频)
        JySchoolResouces jyResouces = new JySchoolResouces();
        jyResouces.setSchoolId(schoolId);
        long total = jySchoolResoucesService.findJySchoolResoucesCountByCondition(jyResouces);
        Paper paper = new Paper();
        paper.setSchoolId(schoolId);
        //试卷总数量
        long total1 = paperService.findTestPaperCountByCondition(paper);
        //校本题库总数量
        SchoolQusBank schoolQusBank = new SchoolQusBank();
        schoolQusBank.setSchoolId(schoolId);
        long total2 = schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);
        return (total+total1+total2);
    }


    //学生考勤
    private void getStudentKq(SchoolDateCenter schoolDateCenter){
//        StudentThisKq studentThisKq = schoolDateCenter.getStudentThisKq();
        DecimalFormat df1 = new DecimalFormat("0.00%");
        StudentThisKq studentThisKq1 = new StudentThisKq();
        Double kq = Math.random();
        studentThisKq1.setStudentKqRatio(df1.format(kq));
        SchoolDateCenter schoolDateCenterx = new SchoolDateCenter();
        schoolDateCenterx.setSchoolId(schoolDateCenter.getSchoolId());
        schoolDateCenterx.setStudentThisKq(studentThisKq1);
        //将随机生成的学生考勤记录下来
        updateSchoolDateCenterByType(schoolDateCenterx,8);
        schoolDateCenter.setStudentThisKq(studentThisKq1);

    }

    //教师日访问量
    private void getTeacherVisit(TeacherSum teacherSum){
        if(teacherSum.getDailyVisits()==null){
            //随机生成访问量初始值
            Long fwl = new Double(Math.random()*300+100).longValue();
            teacherSum.setDailyVisits(fwl);
        }
        if(teacherSum.getHalfMonth()==null){
            List<JySchoolResourcesByDay> datePapers = new ArrayList<>();
            //教师访问量分布图
            for(int j =0;j<Constant.SCHOOL_DATE_CENTER.HALFMONTH;j++){
                JySchoolResourcesByDay datePaper = new JySchoolResourcesByDay();
                datePaper.setTime(LocalDateTimeUtils.getNextYearMonthDay(j));
                datePaper.setCnt(new Double(Math.random()*899+100).intValue());
                datePapers.add(datePaper);
            }
            teacherSum.setTeacherVisit(datePapers);
        }else{
            List<Long> longs = teacherSum.getHalfMonth();
            List<JySchoolResourcesByDay> datePapers = new ArrayList<>();
            //教师访问量分布图
            for(int j =0;j<Constant.SCHOOL_DATE_CENTER.HALFMONTH;j++){
                JySchoolResourcesByDay datePaper = new JySchoolResourcesByDay();
                datePaper.setTime(LocalDateTimeUtils.getNextYearMonthDay(j));
                datePaper.setCnt(longs.get(j).intValue());
                datePapers.add(datePaper);
            }
            teacherSum.setTeacherVisit(datePapers);
        }

    }


    //获取校本题库每天的数量
    private List<JySchoolResourcesByDay> getSchoolQusNumByCreateTimeZone(String createTime,String endTime,String schoolId){
        SchoolQusBank schoolQusBank = new SchoolQusBank();
        schoolQusBank.setSchoolId(schoolId);
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setRangeArray(new String[]{createTime,endTime});
        schoolQusBank.setPager(pager);
        return schoolQusBankService.findSchoolQusNumByCreateTimeZone(schoolQusBank);
    }

    //真资源分布图
    private List<JySchoolResourcesByDay> getZiyuanTu(JySchoolResouces jyResouces,String createTime,String endTime){

        List<JySchoolResourcesByDay> jySchoolResourcesByDays = jySchoolResoucesService.censusSumResoucesByDay(jyResouces);
        //试卷分布图
        List<PaperDayMonth> paperDayMonths  = getPaperDayMonth(createTime,endTime,jyResouces.getSchoolId());
        //校本题库分布图
        List<JySchoolResourcesByDay> schoolResourcesByDays  = getSchoolQusNumByCreateTimeZone(createTime,endTime,jyResouces.getSchoolId());
        for(JySchoolResourcesByDay j: jySchoolResourcesByDays){
            for(PaperDayMonth p:paperDayMonths){
                if(j.getTime().equals(p.getId().getYearMonthDay())){
                    j.setCnt((int)(j.getCnt()+p.getCount()));
                    break;
                }
            }

            for(JySchoolResourcesByDay p:schoolResourcesByDays){
                if(j.getTime().equals(p.getTime())){
                    j.setCnt((j.getCnt()+p.getCnt()));
                    break;
                }
            }
        }

        return jySchoolResourcesByDays;
    }


    public SchoolDateCenter getTruesSchoolDateCenter(String schoolId){
        SchoolDateCenter schoolDateCenter = new SchoolDateCenter();
        School school = schoolService.findSchoolById(schoolId);
        schoolDateCenter.setSchool(school);
        schoolDateCenter.setSchoolId(schoolId);
        //获取学生总数在校学生
        getStudentNum(schoolDateCenter);
        //获取教师总数
        getTeacherNum(schoolDateCenter);
        //校内资源
        getCampusResources(schoolDateCenter);
        //班级
        getClasses(schoolDateCenter);
        //场地
        getJwSpance(schoolDateCenter);
        //教师考勤
        getTeacherAttendance(schoolDateCenter);

        //今日教师打卡率
        TeacherAttendance teacherAttendance = new TeacherAttendance();
        teacherAttendance.setSchooleId(schoolId);
        schoolDateCenter.setTeacherTheClockRate(kqWorkerDailyFeign.findTodayCheckRate(teacherAttendance));
        //固定资产，传递学校id与资产类型,默认获取一年的固定资产数据
        AssetsStockDetail assetsStockDetail = new AssetsStockDetail();
        assetsStockDetail.setAssetsProperty(1);
        assetsStockDetail.setSchoolId(schoolId);
        List<List<AssetsStockDetail>> a = new ArrayList<>();
        List<AssetsStockDetail> assetsStockDetails = assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
        //固定资产一个月的
        List<AssetsStockDetail> assetsStockDetails1 = assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);

        //易耗品一年的
        assetsStockDetail.setAssetsProperty(2);
        List<AssetsStockDetail> assetsStockDetails2 = assetsStockDetailFeign.findRecentOneYearAssertsCount(assetsStockDetail);
        //易耗品一个月的
        List<AssetsStockDetail> assetsStockDetails3 = assetsStockDetailFeign.findRecentOneMonthAssertsCount(assetsStockDetail);
        a.add(assetsStockDetails);
        a.add(assetsStockDetails1);
        a.add(assetsStockDetails2);
        a.add(assetsStockDetails3);
        schoolDateCenter.setAssetsStockDetailList(a);

        //库存价格
        List<List<AssetsFile>> assetsFiles = new ArrayList<>();
        //固定资产
        getAssetsFile(assetsFiles,1,schoolId);
        //易耗品
        getAssetsFile(assetsFiles,2,schoolId);
        schoolDateCenter.setAssetsFiles(assetsFiles);

        //资产使用率
        getAssetsStockDetail(schoolDateCenter);
        return schoolDateCenter;
    }

}

