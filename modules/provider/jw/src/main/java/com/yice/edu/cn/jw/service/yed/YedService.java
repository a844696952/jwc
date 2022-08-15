package com.yice.edu.cn.jw.service.yed;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.yed.*;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.util.math.MathKit;
import com.yice.edu.cn.jw.dao.educationBureau.ISchoolEducationBureauDao;
import com.yice.edu.cn.jw.dao.school.ISchoolDao;
import com.yice.edu.cn.jw.dao.yed.ISchoolNotifiedDao;
import com.yice.edu.cn.jw.dao.yed.YedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class YedService {
    @Autowired
    private YedDao yedDao;
    @Autowired
    private ISchoolEducationBureauDao schoolEducationBureauDao;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private ISchoolNotifiedDao schoolNotifiedDao;
    @Autowired
    private ISchoolDao schoolDao;
    @Transactional(readOnly = true)
    public List<Yed> findNewbornList(String educationBureauId){
        Yed yeds = new Yed();
        yeds.setEducationBureauId(educationBureauId);
        Calendar c = Calendar.getInstance();

        Date date = new Date();
        int ye = c.get(Calendar.YEAR);//获得现在的年份
        int month = date.getMonth();//获得现在的月份
        if(month>=0&&month<8){
            ye = ye-1;
        }
        yeds.setAdmissionDate(ye);

        List<Yed> list = yedDao.findNewbornList(yeds);


        Yed yed = new Yed();
        //查询高一的总人数
        yed.setAdmissionDate(ye);
        yed.setEducationBureauId(educationBureauId);
        long sum = findConutNewbornList(yed);
        //查询高二的总人数
        yed.setAdmissionDate(ye-1);
        long sum1 = findConutNewbornList(yed);

        //计算出高一人数的占比和与去年相比的情况
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setRate((list.get(i).getLast() / (double) sum) * 100);
            if(sum1==0){
                list.get(i).setChanges(null);
            }else{

                list.get(i).setChanges(list.get(i).getLast() / (double) sum - (list.get(i).getYear() / (double) sum1));
            }
        }
        return list;
    }
    @Transactional(readOnly = true)
    public List<JournalCircle> findJournalStatic(String educationBureauId) {
        final SchoolEducationBureau schoolEducationBureau = new SchoolEducationBureau();
        schoolEducationBureau.setEducationBureauId(educationBureauId);
        final List<SchoolEducationBureau> list = schoolEducationBureauDao.findSchoolEducationBureauListByCondition(schoolEducationBureau);
        final List<String> schoolIds = list.stream().map(SchoolEducationBureau::getSchoolId).collect(Collectors.toList());
        final LocalDate now = LocalDate.now();
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final String today = now.format(format);
        final String yesterday = now.minusDays(1).format(format);
        final String tof = now.minusDays(2).format(format);
        //1)计算总发布数
        final long yesterdayCount = mot.count(Query.query(where("schoolId").in(schoolIds).and("createTime").gt(yesterday).lt(today)), Journal.class);
        final long tofCount = mot.count(Query.query(where("schoolId").in(schoolIds).and("createTime").gt(tof).lt(yesterday)), Journal.class);
        final JournalCircle total = new JournalCircle();
        total.setNum(String.valueOf(yesterdayCount));
        total.setText("总发布数");
        total.setRiseFlag(yesterdayCount >= tofCount);
        //2)计算教师活跃数
        final long teacherTotal = yedDao.findTeacherCountByEducationBureauId(educationBureauId);//教师总数
        final TypedAggregation<Journal> teacherYesterdayAggregation = Aggregation.newAggregation(
                Journal.class,
                Aggregation.match(where("schoolId").in(schoolIds).and("fromTeacher").is(true).and("createTime").gt(yesterday).lt(today)),
                Aggregation.group("userId"),
                Aggregation.count().as("count")
        );
        final JournalCircle journalCircle1 = mot.aggregate(teacherYesterdayAggregation, JournalCircle.class).getUniqueMappedResult();
        final Long teacherYesterdayCount = journalCircle1 != null ? journalCircle1.getCount() : 0;


        final TypedAggregation<Journal> teacherTofAggregation = Aggregation.newAggregation(
                Journal.class,
                Aggregation.match(where("schoolId").in(schoolIds).and("fromTeacher").is(true).and("createTime").gt(tof).lt(yesterday)),
                Aggregation.group("userId"),
                Aggregation.count().as("count")
        );
        final JournalCircle journalCircle2 = mot.aggregate(teacherTofAggregation, JournalCircle.class).getUniqueMappedResult();
        final Long teacherTofCount = journalCircle2 != null ? journalCircle2.getCount() : 0;
        final JournalCircle teacherStatic = new JournalCircle();
        teacherStatic.setNum(teacherTotal == 0 ? "00%" : MathKit.getPercent(teacherYesterdayCount,teacherTotal));
        teacherStatic.setText("教师活跃度");
        teacherStatic.setRiseFlag(teacherYesterdayCount >= teacherTofCount);
        //3)计算学生总数
        final long studentTotal = yedDao.findStudentCountByEducationBureauId(educationBureauId);
        final TypedAggregation<Journal> studentYesterdayAggregation = Aggregation.newAggregation(
                Journal.class,
                Aggregation.match(where("schoolId").in(schoolIds).and("fromTeacher").is(false).and("createTime").gt(yesterday).lt(today)),
                Aggregation.group("userId"),
                Aggregation.count().as("count")
        );
        final JournalCircle journalCircle3 = mot.aggregate(studentYesterdayAggregation, JournalCircle.class).getUniqueMappedResult();
        final Long studentYesterdayCount = journalCircle3 != null ? journalCircle3.getCount() : 0;


        final TypedAggregation<Journal> studentTofAggregation = Aggregation.newAggregation(
                Journal.class,
                Aggregation.match(where("schoolId").in(schoolIds).and("fromTeacher").is(false).and("createTime").gt(tof).lt(yesterday)),
                Aggregation.group("userId"),
                Aggregation.count().as("count")
        );
        final JournalCircle journalCircle4 = mot.aggregate(studentTofAggregation, JournalCircle.class).getUniqueMappedResult();
        final Long studentTofCount = journalCircle4 != null ? journalCircle4.getCount() : 0;
        final JournalCircle studentStatic = new JournalCircle();
        studentStatic.setNum(studentTotal==0?"00%":MathKit.getPercent(studentYesterdayCount,studentTotal));
        studentStatic.setText("学生活跃度");
        studentStatic.setRiseFlag(studentYesterdayCount >= studentTofCount);


        //构造list
        List<JournalCircle> r = new ArrayList<>();
        r.add(total);
        r.add(teacherStatic);
        r.add(studentStatic);
        return r;
    }

    public StudentCheckWork findStudentCheckWork(StudentCheckWork studentCheckWork) {

        Date date = DateUtil.date();
        //获得年的部分
        int year=DateUtil.year(date);
        //获得月份，从0开始计数
        int month= DateUtil.month(date)+1;
        if(month>=9){
            String startDate=String.valueOf(year)+"-09";
            String endDate=String.valueOf(year+1)+"-07";
            studentCheckWork.setStartDate(startDate);
            studentCheckWork.setEndDate(endDate);
        }else {
            String startDate=String.valueOf(year-1)+"-09";
            String endDate=String.valueOf(year)+"-07";
            studentCheckWork.setStartDate(startDate);
            studentCheckWork.setEndDate(endDate);
        }
        List<StudentCheckWork> list= yedDao.findStudentCheckWork( studentCheckWork);

        StudentCheckWork studentCheckWork1=new StudentCheckWork();
        int num=0;
        studentCheckWork1.setMonths(new ArrayList<>());
        studentCheckWork1.setEarlys(new ArrayList<>());
        studentCheckWork1.setLates(new ArrayList<>());
        studentCheckWork1.setNormals(new ArrayList<>());
        studentCheckWork1.setMisses(new ArrayList<>());
        studentCheckWork1.getMonths().add("");
        studentCheckWork1.getEarlys().add(0);
        studentCheckWork1.getLates().add(0);
        studentCheckWork1.getNormals().add(0);
        studentCheckWork1.getMisses().add(0);
        for (int i = 0; i <list.size() ; i++) {
            String []temp=list.get(i).getMonth().split("-");
            String month1=temp[1];
            studentCheckWork1.getMonths().set(num,month1);
            if(i!=list.size()-1){

                if(list.get(i).getMonth().equals(list.get(i+1).getMonth())){
                    if(list.get(i).getLate()==0&&list.get(i).getMiss()==0&&list.get(i).getEarly()==0){
                        int value= studentCheckWork1.getNormals().get(num)+1;
                        studentCheckWork1.getNormals().set(num,value);

                    }

                    if(list.get(i).getLate()>0){
                        int value= studentCheckWork1.getLates().get(num)+1;
                        studentCheckWork1.getLates().set(num,value);

                    }

                    if(list.get(i).getMiss()>0){
                        int value= studentCheckWork1.getMisses().get(num)+1;
                        studentCheckWork1.getMisses().set(num,value);

                    }
                    if(list.get(i).getEarly()>0){
                        int value= studentCheckWork1.getEarlys().get(num)+1;
                        studentCheckWork1.getEarlys().set(num,value);

                    }

                }else{

                    if(list.get(i).getLate()==0&&list.get(i).getMiss()==0&&list.get(i).getEarly()==0){
                        int value= studentCheckWork1.getNormals().get(num)+1;
                        studentCheckWork1.getNormals().set(num,value);

                    }

                    if(list.get(i).getLate()>0){
                        int value= studentCheckWork1.getLates().get(num)+1;
                        studentCheckWork1.getLates().set(num,value);

                    }

                    if(list.get(i).getMiss()>0){
                        int value= studentCheckWork1.getMisses().get(num)+1;
                        studentCheckWork1.getMisses().set(num,value);

                    }
                    if(list.get(i).getEarly()>0){
                        int value= studentCheckWork1.getEarlys().get(num)+1;
                        studentCheckWork1.getEarlys().set(num,value);

                    }
                    num++;
                    studentCheckWork1.getMonths().add("");
                    studentCheckWork1.getEarlys().add(0);
                    studentCheckWork1.getLates().add(0);
                    studentCheckWork1.getNormals().add(0);
                    studentCheckWork1.getMisses().add(0);
                }

            }else {
                if(list.get(i).getLate()==0&&list.get(i).getMiss()==0&&list.get(i).getEarly()==0){
                    int value= studentCheckWork1.getNormals().get(num)+1;
                    studentCheckWork1.getNormals().set(num,value);

                }

                if(list.get(i).getLate()>0){
                    int value= studentCheckWork1.getLates().get(num)+1;
                    studentCheckWork1.getLates().set(num,value);

                }

                if(list.get(i).getMiss()>0){
                    int value= studentCheckWork1.getMisses().get(num)+1;
                    studentCheckWork1.getMisses().set(num,value);

                }
                if(list.get(i).getEarly()>0){
                    int value= studentCheckWork1.getEarlys().get(num)+1;
                    studentCheckWork1.getEarlys().set(num,value);

                }



            }

        }
        return  studentCheckWork1;

    }





    @Transactional(readOnly = true)
    public long findConutNewbornList(Yed yed){
        return yedDao.findConutNewbornList(yed);
    }

    /**
     * 查询一段时间内学校布置的作业总数量
     * @param HomeworkCountQueryVo
     * @return
     */
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDate(HomeworkCountQueryVo vo) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(where("publishTime").gte(vo.getBeginDate()));
        criterias.add(where("publishTime").lte(vo.getEndDate()));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.group("schoolId").count().as("homeworkNum"),Aggregation.project().and("_id").as("schoolId").and("homeworkNum").as("homeworkNum"));

        AggregationResults<HomeworkCountViewVo> outputTypeCount =
                mot.aggregate(aggregation, Homework.class, HomeworkCountViewVo.class);


        return outputTypeCount.getMappedResults();
    }

    /**
     * 查询一段时间内学校布置的作业学生完成状态总数量
     * @param HomeworkCountQueryVo
     * @return
     */
    public List<HomeworkCountViewVo> findSchoolHomeworkNumByDateAndStatus(HomeworkCountQueryVo vo) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(where("homeworkObj.publishTime").gte(vo.getBeginDate()));
        criterias.add(where("homeworkObj.publishTime").lte(vo.getEndDate()));
        if(vo.getStatus().intValue()!=4) {
            criterias.add(where("status").is(vo.getStatus()));
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                Aggregation.group("homeworkObj.schoolId").count().as("homeworkNum"),Aggregation.project().and("_id").as("schoolId").and("homeworkNum").as("homeworkNum"));

        AggregationResults<HomeworkCountViewVo> outputTypeCount =
                mot.aggregate(aggregation, HomeworkStudent.class, HomeworkCountViewVo.class);


        return outputTypeCount.getMappedResults();
    }

    @Transactional(readOnly = true)
    public List<Yed> findSpaceByRoleList(String educationBureauId){
        Yed yed = new Yed();
       /* Calendar c = Calendar.getInstance();

        int ye = c.get(Calendar.YEAR);//获得现在的年份
        yed.setAdmissionDate(ye);*/
        yed.setEducationBureauId(educationBureauId);

        return yedDao.findSpaceByRoleList(yed);
    }


    @Transactional(readOnly = true)
    public  List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified){
        return schoolNotifiedDao.findSchoolNotifiedListByCondition(schoolNotified);
    }

    @Transactional
    public List<Object> findEnrolmentList(Enrolment enrolment){
        String year =  DateUtil.format(new Date(), "yyyy");  //当前年份
        Integer last = Integer.parseInt(year)-1;
        Integer before = Integer.parseInt(year)-2;
        enrolment.setEnrolmentYear(year);
        List<Enrolment> enrolmentYear = yedDao.findEnrolmentList(enrolment);
        for (int i=0;i<enrolmentYear.size();i++){     //算出当前年份的升学率
            if(enrolmentYear.get(i).getGraduationCount()==null){
                enrolmentYear.get(i).setGraduationCount(0);
            }
            if(enrolmentYear.get(i).getStudentCount()!=null){
                enrolmentYear.get(i).setRate(((enrolmentYear.get(i).getGraduationCount()) / (double)((enrolmentYear.get(i).getStudentCount()))*100)
                );
                BigDecimal bg = new BigDecimal(enrolmentYear.get(i).getRate()).setScale(2, RoundingMode.UP);
                enrolmentYear.get(i).setRate(bg.doubleValue());

            }
            enrolmentYear.get(i).setEnrolmentYear(year);
        }
        enrolment.setEnrolmentYear(String.valueOf(last));
        List<Enrolment> enrolmentLast = yedDao.findEnrolmentList(enrolment);
        for (int i=0;i<enrolmentLast.size();i++){    //算出去年的升学率
            if(enrolmentLast.get(i).getGraduationCount()==null){
                enrolmentLast.get(i).setGraduationCount(0);
            }
            if(enrolmentLast.get(i).getStudentCount()!=null){
                enrolmentLast.get(i).setRate(((enrolmentLast.get(i).getGraduationCount()) / (double)((enrolmentLast.get(i).getStudentCount()))*100)
                );
                BigDecimal bg = new BigDecimal(enrolmentLast.get(i).getRate()).setScale(2, RoundingMode.UP);
                enrolmentLast.get(i).setRate(bg.doubleValue());
            }
            enrolmentLast.get(i).setEnrolmentYear(String.valueOf(last));
        }
        enrolment.setEnrolmentYear(String.valueOf(before));
        List<Enrolment> enrolmentBefore = yedDao.findEnrolmentList(enrolment);
        for (int i=0;i<enrolmentBefore.size();i++){   //算出前年的升学率
            if(enrolmentBefore.get(i).getGraduationCount()==null){
                enrolmentBefore.get(i).setGraduationCount(0);
            }
            if(enrolmentBefore.get(i).getStudentCount()!=null){
                enrolmentBefore.get(i).setRate(((enrolmentBefore.get(i).getGraduationCount()) / (double)((enrolmentBefore.get(i).getStudentCount()))*100)
                );
                BigDecimal bg = new BigDecimal(enrolmentBefore.get(i).getRate()).setScale(2, RoundingMode.UP);
                enrolmentBefore.get(i).setRate(bg.doubleValue());
            }
            enrolmentLast.get(i).setEnrolmentYear(String.valueOf(before));
        }
        String[] area = new String[enrolmentYear.size()];
        int[] years = new int[]{ before,last,Integer.parseInt(year)};
        double[][] data = new double[3][enrolmentYear.size()];
        for ( int i=0;i<area.length;i++){
            area[i] = enrolmentYear.get(i).getArea();
        }
        for (int j=0;j<enrolmentYear.size();j++){
            if(enrolmentBefore.get(j).getRate()!=null) {
                data[0][j] = enrolmentBefore.get(j).getRate();
            }
            if(enrolmentLast.get(j).getRate()!=null) {
                data[1][j] = enrolmentLast.get(j).getRate();}
            if(enrolmentYear.get(j).getRate()!=null) {
                data[2][j] = enrolmentYear.get(j).getRate();}
        }
        List<Object> enrolmentList = new ArrayList<Object>();
        enrolmentList.add(area);
        enrolmentList.add(years);
        enrolmentList.add(data);
        return  enrolmentList;
    }

 /*   @Transactional(readOnly = true)
    public  List<School>findSchoolByEducation(String educationBureauId){
        return yedDao.findSchoolByEducation(educationBureauId);
    }*/

    @Transactional(readOnly = true)
    public  List<AreaOperationVolume>findTaskSituation(String educationBureauId){
        HomeworkCountQueryVo vo=new HomeworkCountQueryVo();
        HomeworkCountQueryVo vo1=new HomeworkCountQueryVo();
        Date date = DateUtil.date();
        Date datebegin=DateUtil.beginOfMonth(date);
        Date dateend= DateUtil.endOfMonth(date);
        String beginTime = DateUtil.format(datebegin, "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.format(dateend, "yyyy-MM-dd HH:mm:ss");
        vo.setBeginDate(beginTime);
        vo.setEndDate(endTime);
        vo1.setBeginDate(beginTime);
        vo1.setEndDate(endTime);
        vo1.setStatus(1);
        List<HomeworkCountViewVo> homeworkList=findSchoolHomeworkNumByDate(vo);
        List<School>schoolList=yedDao.findSchoolByEducation(educationBureauId);
        List<HomeworkCountViewVo> resultList=new ArrayList<>();
        for (School school:schoolList) {
            for (HomeworkCountViewVo homeworkCountViewVo:homeworkList) {
                if(school.getId().equals(homeworkCountViewVo.getSchoolId())){
                    HomeworkCountViewVo homeworkCountViewVo1=new HomeworkCountViewVo();
                    homeworkCountViewVo1.setSchoolId(homeworkCountViewVo.getSchoolId());
                    homeworkCountViewVo1.setHomeworkNum(homeworkCountViewVo.getHomeworkNum());
                    resultList.add(homeworkCountViewVo1);
                }
            }
        }


        List<HomeworkCountViewVo> homeworkList1=findSchoolHomeworkNumByDateAndStatus(vo1);
        List<HomeworkCountViewVo> resultList1=new ArrayList<>();
        for (School school:schoolList) {
            for (HomeworkCountViewVo homeworkCountViewVo:homeworkList1) {
                if(school.getId().equals(homeworkCountViewVo.getSchoolId())){
                    HomeworkCountViewVo homeworkCountViewVo1=new HomeworkCountViewVo();
                    homeworkCountViewVo1.setSchoolId(homeworkCountViewVo.getSchoolId());
                    homeworkCountViewVo1.setHomeworkNum(homeworkCountViewVo.getHomeworkNum());
                    resultList1.add(homeworkCountViewVo1);
                }
            }
        }


        List<AreaOperationVolume> areaOperationVolumes = combineList(resultList, resultList1);
        return  areaOperationVolumes;
    }
    /**
     *
     * @param homeworkCountViewVos1 homeworkCount
     * @param homeworkCountViewVos2 numbers
     * @return
     */
    private List<AreaOperationVolume> combineList(List<HomeworkCountViewVo> homeworkCountViewVos1, List<HomeworkCountViewVo> homeworkCountViewVos2) {
        Map<String, HomeworkCountViewVo> map1 = listToMap(homeworkCountViewVos1);
        Map<String, HomeworkCountViewVo> map2 = listToMap(homeworkCountViewVos2);
        Set<String> map1Keys = map1.keySet();
        Set<String> map2Keys = map2.keySet();
        Set<String> keys = new HashSet<>();
        keys.addAll(map1Keys);
        keys.addAll(map2Keys);

        List<AreaOperationVolume> areaOperationVolumes = new ArrayList<>();
        AreaOperationVolume areaOperationVolume = null;
        for (String key : keys) {
            HomeworkCountViewVo homeworkCountViewVo1 = map1.get(key);
            HomeworkCountViewVo homeworkCountViewVo2 = map2.get(key);
            areaOperationVolume = new AreaOperationVolume();
            if (homeworkCountViewVo1 != null) {
                areaOperationVolume.setHomeworkNum(homeworkCountViewVo1.getHomeworkNum());
                School school=schoolDao.findSchoolById(homeworkCountViewVo1.getSchoolId());
                areaOperationVolume.setAreaName(school.getDistrictName());
            }else{
                areaOperationVolume.setHomeworkNum(Long.valueOf(0));
                School school=schoolDao.findSchoolById(homeworkCountViewVo1.getSchoolId());
                areaOperationVolume.setAreaName(school.getDistrictName());
            }

            if (homeworkCountViewVo2 != null) {
                areaOperationVolume.setNumber(homeworkCountViewVo2.getHomeworkNum());
                if (areaOperationVolume.getAreaName() != null) {
                    School school=schoolDao.findSchoolById(homeworkCountViewVo1.getSchoolId());
                    areaOperationVolume.setAreaName(school.getDistrictName());
                }
            }else{
                areaOperationVolume.setNumber(Long.valueOf(0));
                if (areaOperationVolume.getAreaName() != null) {
                    School school=schoolDao.findSchoolById(homeworkCountViewVo1.getSchoolId());
                    areaOperationVolume.setAreaName(school.getDistrictName());
                }
            }
            areaOperationVolumes.add(areaOperationVolume);
        }
        return areaOperationVolumes;
    }

    private Map<String, HomeworkCountViewVo> listToMap(List<HomeworkCountViewVo> homeworkCountViewVos) {
        Map<String, HomeworkCountViewVo> map = new HashMap<>();
        for (HomeworkCountViewVo homeworkCountViewVo : homeworkCountViewVos) {
            map.put(homeworkCountViewVo.getSchoolId(), homeworkCountViewVo);
        }
        return map;
    }


}
