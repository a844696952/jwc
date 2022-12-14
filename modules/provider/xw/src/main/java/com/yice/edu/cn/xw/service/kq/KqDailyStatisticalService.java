package com.yice.edu.cn.xw.service.kq;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqMonthStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqPunchRules;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqDailyStatisticalService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public KqDailyStatistical findKqDailyStatisticalById(String id) {
        return mot.findById(id, KqDailyStatistical.class);
    }

    public void saveKqDailyStatistical(KqDailyStatistical kqDailyStatistical) {
        kqDailyStatistical.setCreateTime(DateUtil.now());
        kqDailyStatistical.setId(sequenceId.nextId());
        mot.insert(kqDailyStatistical);
    }

    public List<KqDailyStatistical> findKqDailyStatisticalListByCondition(KqDailyStatistical kqDailyStatistical) {
        if (!StrUtil.isEmpty(kqDailyStatistical.getKqDate())) {
            //??????????????????????????????????????????
            String[] kqDate = kqDailyStatistical.getKqDate().split("-");
            String firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            kqDailyStatistical.getPager().setRangeField("kqDate");
            String[] kqDate1 = new String[2];
            kqDate1[0] = firstDayOfMonth;
            kqDate1[1] = lastDayOfMonth;
            kqDailyStatistical.getPager().setRangeArray(kqDate1);
        }
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDailyStatistical.class)).find(MongoKit.buildMatchDocument(kqDailyStatistical));
        Pager pager = kqDailyStatistical.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqDailyStatistical> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDailyStatistical.class, document)));
        return list;
    }

    public long findKqDailyStatisticalCountByCondition(KqDailyStatistical kqDailyStatistical) {
        if (!StrUtil.isEmpty(kqDailyStatistical.getKqDate())) {
            //??????????????????????????????????????????
            String[] kqDate = kqDailyStatistical.getKqDate().split("-");
            String firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            kqDailyStatistical.getPager().setRangeField("kqDate");
            String[] kqDate1 = new String[2];
            kqDate1[0] = firstDayOfMonth;
            kqDate1[1] = lastDayOfMonth;
            kqDailyStatistical.getPager().setRangeArray(kqDate1);
        }
        return mot.getCollection(mot.getCollectionName(KqDailyStatistical.class)).count(MongoKit.buildMatchDocument(kqDailyStatistical));
    }

    public KqDailyStatistical findOneKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDailyStatistical.class)).find(MongoKit.buildMatchDocument(kqDailyStatistical));
        MongoKit.appendProjection(query, kqDailyStatistical.getPager());
        return mot.getConverter().read(KqDailyStatistical.class, query.first());
    }

    public void updateKqDailyStatistical(KqDailyStatistical kqDailyStatistical) {

        KqDailyStatistical k = findKqDailyStatisticalById(kqDailyStatistical.getId());
        KqPunchRules kr = k.getRuleStatus();
        KqPunchRules newkr = kqDailyStatistical.getRuleStatus();

        //??????????????????????????????
        int missNum = 0;
        int lateNum = 0;
        int leaveNum = 0;
        if (newkr.getDuskOutStatus().equals("1")) {
            missNum++;
        }

        if (newkr.getMorningInStatus().equals("1")) {
            missNum++;
        }
        if (newkr.getDuskOutStatus().equals("2")) {
            lateNum++;
        }

        if (newkr.getMorningInStatus().equals("2")) {
            lateNum++;
        }
        if (newkr.getDuskOutStatus().equals("3")) {
            leaveNum++;
        }

        if (newkr.getMorningInStatus().equals("3")) {
            leaveNum++;
        }
        if (kqDailyStatistical.getPunchNumber().equals("2")) {

            if (newkr.getMorningOutStatus().equals("1")) {
                missNum++;
            }

            if (newkr.getNoonInStatus().equals("1")) {
                missNum++;
            }

            if (newkr.getMorningOutStatus().equals("2")) {
                lateNum++;
            }

            if (newkr.getNoonInStatus().equals("2")) {
                lateNum++;
            }

            if (newkr.getMorningOutStatus().equals("3")) {
                leaveNum++;
            }

            if (newkr.getNoonInStatus().equals("3")) {
                leaveNum++;
            }
        }

        //??????
        if (lateNum <= 0) {
            lateNum = 0;
        }
        if (leaveNum <= 0) {
            leaveNum = 0;
        }
        if (missNum <= 0) {
            missNum = 0;
        }
        if (lateNum >= 1) {
            lateNum = 1;
        }
        if (leaveNum >= 1) {
            leaveNum = 1;
        }
        if (missNum >= 1) {
            missNum = 1;
        }
        kqDailyStatistical.setLateStatus(String.valueOf(lateNum));
        kqDailyStatistical.setLeaveEarlyStatus(String.valueOf(leaveNum));
        kqDailyStatistical.setMissStatus(String.valueOf(missNum));

        kqDailyStatistical.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqDailyStatistical.getId())), MongoKit.update(kqDailyStatistical), KqDailyStatistical.class);
    }

    public void deleteKqDailyStatistical(String id) {
        mot.remove(query(where("id").is(id)), KqDailyStatistical.class);
    }

    public void deleteKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical) {
        mot.getCollection(mot.getCollectionName(KqDailyStatistical.class)).deleteMany(MongoKit.buildMatchDocument(kqDailyStatistical));
    }

    public void batchSaveKqDailyStatistical(List<KqDailyStatistical> kqDailyStatisticals) {

        if (CollectionUtil.isNotEmpty(kqDailyStatisticals)){
            for (KqDailyStatistical s :kqDailyStatisticals){
                curSchoolYearService.setSchoolYearTerm(s,s.getSchoolId());
            }
        }
        kqDailyStatisticals.forEach(kqDailyStatistical -> kqDailyStatistical.setId(sequenceId.nextId()));
        mot.insertAll(kqDailyStatisticals);
    }

    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    /*
    db.kqDailyStatistical.aggregate([

            {$match:{schoolId:"1741058039675183104",classId:"1984262693114961920"}}
            ,
            {$group:{
                            _id:{stuId:"$studentId",name:"$name",classesNumber:"$classesNumber",gradeName:"$gradeName"},
                            duskOutStatus:{$push:"$ruleStatus.duskOutStatus"},
                            morningInStatus:{$push:"$ruleStatus.morningInStatus"},
                            morningOutStatus:{$push:"$ruleStatus.morningOutStatus"},
                            noonInStatus:{$push:"$ruleStatus.noonInStatus"}
                            }}
            ])
    */
    //???????????????
    public List<KqMonthStatistical> findKqMonthStatisticalListByCondition(KqDailyStatistical kqDailyStatistical) {
        List<Student> students = kqDailyStatistical.getStudentList();
        //???????????????????????????????????????id
        List<String> stuIdList = new ArrayList<String>();
        for (Student s : students) {
            stuIdList.add(s.getId());
        }
        ///
        if (!StrUtil.isEmpty(kqDailyStatistical.getKqDate())) {
            //??????????????????????????????????????????
            String[] kqDate = kqDailyStatistical.getKqDate().split("-");
            String firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));

            List<Criteria> criterias = new ArrayList<>();
            criterias.add(new Criteria().where("schoolId").is(kqDailyStatistical.getSchoolId()));//??????id
            if (!StrUtil.isEmpty(kqDailyStatistical.getClassId())){
                criterias.add(new Criteria().where("classId").is(kqDailyStatistical.getClassId()));//??????id
            }
            criterias.add(new Criteria().where("kqDate").gte(firstDayOfMonth).andOperator(new Criteria().where("kqDate").lte(lastDayOfMonth)));
            //criterias.add(new Criteria().where("kqDate").gte(firstDayOfMonth).lte(lastDayOfMonth));
            criterias.add(new Criteria().where("studentId").in(stuIdList));//???????????????id*/


           /* if (kqDailyStatistical.getLateStatus()!=null){
                criterias.add(new Criteria().where("lateStatus").is(kqDailyStatistical.getLateStatus().equals("1")?"1":"0"));
            }
            if (kqDailyStatistical.getLeaveEarlyStatus()!=null){
                criterias.add(new Criteria().where("leaveEarlyStatus").is(kqDailyStatistical.getLeaveEarlyStatus().equals("1")?"1":"0"));
            }
            if (kqDailyStatistical.getMissStatus()!=null){
                criterias.add(new Criteria().where("missStatus").is(kqDailyStatistical.getMissStatus().equals("1")?"1":"0"));
            }*/
            if (!StrUtil.isEmpty(kqDailyStatistical.getName())) {
                criterias.add(new Criteria().where("name").regex(StringUtils.specialCharacterConvert(kqDailyStatistical.getName()), "i"));
            }
            Pager pager = kqDailyStatistical.getPager();
            System.out.println("??????????????????"+pager);
            String sortField = kqDailyStatistical.getPager().getSortField();
            if (StrUtil.isEmpty(sortField) || sortField.equals("lateNum") || sortField.equals("leaveEarlyNum") || sortField.equals("missNum")) {
                sortField = "studentId";
            }
            Sort.Direction sort = Sort.Direction.ASC;
            if (kqDailyStatistical.getPager().getSortOrder() == null || kqDailyStatistical.getPager().getSortOrder().equals("desc")) {
                sort = Sort.Direction.DESC;
            }
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                    Aggregation.group("studentId", "name", "classesNumber", "gradeName", "schoolId", "seatNumber")
                            .push("ruleStatus.morningInStatus").as("morningInStatus")
                            .push("ruleStatus.morningOutStatus").as("morningOutStatus")
                            .push("ruleStatus.noonInStatus").as("noonInStatus")
                            .push("ruleStatus.duskOutStatus").as("duskOutStatus"),
                    Aggregation.project().and("_id.studentId").as("studentId").and("_id.seatNumber").as("seatNumber")
                            .and("_id.name").as("name")
                            .and("_id.classesNumber").as("classesNumber").and("_id.schoolId").as("schoolId").and("_id.gradeName").as("gradeName")
                            .and("morningInStatus").as("morningInStatus").and("morningOutStatus").as("morningOutStatus")
                            .and("noonInStatus").as("noonInStatus").and("duskOutStatus").as("duskOutStatus"),
                    Aggregation.sort(sort, sortField)//????????????
                    //???????????????pageNumber??????????????????pageSize?????????????????????
                    /*pager.getPageNumber()>1?(pager.getPageNumber()-1)*pager.getPageSize():0;*/
                  /*  Aggregation.skip(pager.getBeginRow()),
                    Aggregation.limit(pager.getPageSize())*/

            );
            AggregationResults<KqMonthStatistical> outputTypeCount1 =
                    mot.aggregate(aggregation, KqDailyStatistical.class, KqMonthStatistical.class);
            List<KqMonthStatistical> kqMonthStatisticals = outputTypeCount1.getMappedResults();
            //??????????????????????????????
            for (KqMonthStatistical km : kqMonthStatisticals) {
                //??????1??????
                int lateNum = 0;
                //??????2??????
                int leaveEarlyNum = 0;
                //??????3??????
                int missNum = 0;
                for (String s : km.getDuskOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                km.setDuskOutStatus(null);
                for (String s : km.getMorningInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                km.setMorningInStatus(null);
                for (String s : km.getNoonInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                km.setNoonInStatus(null);
                for (String s : km.getMorningOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                km.setMorningOutStatus(null);
                km.setKqMonth(kqDailyStatistical.getKqDate());
                km.setLateNum(String.valueOf(lateNum));
                km.setLeaveEarlyNum(String.valueOf(leaveEarlyNum));
                km.setMissNum(String.valueOf(missNum));

            }//????????????

           if (kqDailyStatistical.getLateStatus() != null || kqDailyStatistical.getLeaveEarlyStatus() != null || kqDailyStatistical.getMissStatus() != null) {

                List<KqMonthStatistical> kqMonthList = new ArrayList<KqMonthStatistical>();
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getLeaveEarlyStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getLeaveEarlyNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getLeaveEarlyStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getLeaveEarlyNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLeaveEarlyStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLeaveEarlyNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLateStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLeaveEarlyStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLeaveEarlyNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }
                        return kqMonthList;
                    }
                }
                return kqMonthStatisticals;
            }
            List<KqMonthStatistical> noKqMonths = new ArrayList<KqMonthStatistical>();
            for (Student student : students) {

                if (kqMonthStatisticals != null && kqMonthStatisticals.stream().anyMatch(c -> c.getStudentId().equals(student.getId()))) {
                   /* KqMonthStatistical k = new KqMonthStatistical();
                    k.setStudentId(student.getId());
                    k.setLateNum("---");
                    k.setLeaveEarlyNum("---");
                    k.setMissNum("---");
                    k.setName(student.getName());
                    k.setSeatNumber(String.valueOf(student.getSeatNumber()));
                    kqMonthStatisticals.add(k);*/
                } else {
                    KqMonthStatistical k = new KqMonthStatistical();
                    k.setStudentId(student.getId());
                    k.setSchoolId(student.getSchoolId());
                    k.setLateNum("---");
                    k.setLeaveEarlyNum("---");
                    k.setMissNum("---");
                    k.setName(student.getName());
                    k.setGradeName(student.getGradeName());
                    k.setClassesNumber(student.getClassesNumber());
                    k.setSeatNumber(String.valueOf(student.getSeatNumber()));
                    k.setKqMonth(kqDailyStatistical.getKqDate());
                    noKqMonths.add(k);
                }
            }
            if (kqMonthStatisticals != null && kqMonthStatisticals.size() > 0) {
                noKqMonths.addAll(kqMonthStatisticals);
            }

            List<KqMonthStatistical> collect = noKqMonths.stream().sorted(Comparator.comparing(KqMonthStatistical::getSeatNumber)).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    //????????????
    public List<KqMonthStatistical> findKqMonthStatisticalListAll(KqDailyStatistical kqDailyStatistical) {
        if (!StrUtil.isEmpty(kqDailyStatistical.getKqDate())) {
            //??????????????????????????????????????????
            String[] kqDate = kqDailyStatistical.getKqDate().split("-");
            String firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));

            List<Criteria> criterias = new ArrayList<>();
            criterias.add(new Criteria().where("schoolId").is(kqDailyStatistical.getSchoolId()));//??????id
            if (!StrUtil.isEmpty(kqDailyStatistical.getClassId())){
                criterias.add(new Criteria().where("classId").is(kqDailyStatistical.getClassId()));//??????id
            }
            criterias.add(new Criteria().where("kqDate").gte(firstDayOfMonth).andOperator(new Criteria().where("kqDate").lte(lastDayOfMonth)));
        /*    if (kqDailyStatistical.getLateStatus()!=null){
                criterias.add(new Criteria().where("lateStatus").is(kqDailyStatistical.getLateStatus()));
            }
            if (kqDailyStatistical.getLeaveEarlyStatus()!=null){
                criterias.add(new Criteria().where("leaveEarlyStatus").is(kqDailyStatistical.getLeaveEarlyStatus()));
            }
            if (kqDailyStatistical.getMissStatus()!=null){
            criterias.add(new Criteria().where("missStatus").is(kqDailyStatistical.getMissStatus()));
            }*/
            if (!StrUtil.isEmpty(kqDailyStatistical.getName())) {
                criterias.add(new Criteria().where("name").regex(StringUtils.specialCharacterConvert(kqDailyStatistical.getName()), "i"));
            }
            Pager pager = kqDailyStatistical.getPager();
            String sortField = kqDailyStatistical.getPager().getSortField();
            if (StrUtil.isEmpty(sortField)) {
                sortField = "studentId";
            }
            Sort.Direction sort = Sort.Direction.ASC;
            if (kqDailyStatistical.getPager().getSortOrder() == null || kqDailyStatistical.getPager().getSortOrder().equals("desc")) {
                sort = Sort.Direction.DESC;
            }
            Aggregation aggregation = Aggregation.newAggregation(


                    Aggregation.match(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))),
                    Aggregation.group("studentId", "name", "classesNumber", "gradeName", "schoolId", "seatNumber")
                            .push("ruleStatus.morningInStatus").as("morningInStatus")
                            .push("ruleStatus.morningOutStatus").as("morningOutStatus")
                            .push("ruleStatus.noonInStatus").as("noonInStatus")
                            .push("ruleStatus.duskOutStatus").as("duskOutStatus"),

                    Aggregation.project().and("_id.studentId").as("studentId").and("_id.name").as("name")
                            .and("_id.seatNumber").as("seatNumber")
                            .and("_id.classesNumber").as("classesNumber").and("_id.schoolId").as("schoolId").and("_id.gradeName").as("gradeName")
                            .and("morningInStatus").as("morningInStatus").and("morningOutStatus").as("morningOutStatus")
                            .and("noonInStatus").as("noonInStatus").and("duskOutStatus").as("duskOutStatus"),


                    Aggregation.sort(sort, sortField)//????????????
                    //???????????????pageNumber??????????????????pageSize?????????????????????
                   /* Aggregation.skip(pager.getPageNumber()>1?(pager.getPageNumber()-1)*pager.getPageSize():0),
                    Aggregation.limit(pager.getPageSize())*/

            );


            AggregationResults<KqMonthStatistical> outputTypeCount1 =
                    mot.aggregate(aggregation, KqDailyStatistical.class, KqMonthStatistical.class);
            List<KqMonthStatistical> kqMonthStatisticals = outputTypeCount1.getMappedResults();

            //??????????????????????????????
            for (KqMonthStatistical km : kqMonthStatisticals) {
                //??????1??????
                int lateNum = 0;
                //??????2??????
                int leaveEarlyNum = 0;
                //??????3??????
                int missNum = 0;
                for (String s : km.getDuskOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                // km.setDuskOutStatus(null);
                for (String s : km.getMorningInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                // km.setMorningInStatus(null);
                for (String s : km.getNoonInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                // km.setNoonInStatus(null);
                for (String s : km.getMorningOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                    }
                }
                ;
                // km.setMorningOutStatus(null);
                km.setKqMonth(kqDailyStatistical.getKqDate());
                km.setLateNum(String.valueOf(lateNum));
                km.setLeaveEarlyNum(String.valueOf(leaveEarlyNum));
                km.setMissNum(String.valueOf(missNum));

            }//????????????


            List<KqMonthStatistical> kqMonthList = new ArrayList<KqMonthStatistical>();
            if (kqDailyStatistical.getLateStatus() != null || kqDailyStatistical.getLeaveEarlyStatus() != null || kqDailyStatistical.getMissStatus() != null) {


                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getLeaveEarlyStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getLeaveEarlyNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getLeaveEarlyStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getLeaveEarlyNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLateStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getLeaveEarlyStatus().equals("1") && kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLeaveEarlyNum()) > 0 && Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() != null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLateStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLateNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() == null && kqDailyStatistical.getMissStatus() != null) {
                    if (kqDailyStatistical.getMissStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getMissNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }
                if (kqDailyStatistical.getLateStatus() == null && kqDailyStatistical.getLeaveEarlyStatus() != null && kqDailyStatistical.getMissStatus() == null) {
                    if (kqDailyStatistical.getLeaveEarlyStatus().equals("1")) {
                        for (KqMonthStatistical k : kqMonthStatisticals) {
                            if (Integer.parseInt(k.getLeaveEarlyNum()) > 0) {
                                kqMonthList.add(k);
                            }
                        }

                    }
                }

            }


            if (kqMonthList.size() > 0) {
                kqMonthStatisticals = kqMonthList;
            }


            //??????????????????????????????
            //??????1??????
            int lateNum = 0;
            //??????2??????
            int leaveEarlyNum = 0;
            //??????3??????
            int missNum = 0;
            //??????3????????????
            int lateManNum = 0;
            //??????2????????????
            int leaveEarlyManNum = 0;
            //??????1????????????
            int missManNum = 0;

            KqMonthStatistical kqMonthStatistical = new KqMonthStatistical();
            for (KqMonthStatistical km : kqMonthStatisticals) {
                int lateNum1 = 0;
                //??????2??????
                int leaveEarlyNum1 = 0;
                //??????3??????
                int missNum1 = 0;
                for (String s : km.getDuskOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                        lateNum1++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                        leaveEarlyNum1++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                        missNum1++;
                    }
                }
                ;
                km.setDuskOutStatus(null);
                for (String s : km.getMorningInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                        lateNum1++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                        leaveEarlyNum1++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                        missNum1++;
                    }
                }
                ;
                km.setMorningInStatus(null);
                for (String s : km.getNoonInStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                        lateNum1++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                        leaveEarlyNum1++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                        missNum1++;
                    }
                }
                ;
                km.setNoonInStatus(null);
                for (String s : km.getMorningOutStatus()) {
                    if (s.equals("2")) {
                        lateNum++;
                        lateNum1++;
                    }
                    if (s.equals("3")) {
                        leaveEarlyNum++;
                        leaveEarlyNum1++;
                    }
                    if (s.equals("1")) {
                        missNum++;
                        missNum1++;
                    }
                }
                ;
                km.setMorningOutStatus(null);
                if (lateNum1 > 0) {
                    lateManNum++;
                }
                if (leaveEarlyNum1 > 0) {
                    leaveEarlyManNum++;
                }
                if (missNum1 > 0) {
                    missManNum++;
                }
            }


            kqMonthStatistical.setLateManNum(lateManNum);//???????????????
            kqMonthStatistical.setLeaveEarlyManNum(leaveEarlyManNum);//???????????????
            kqMonthStatistical.setMissManNum(missManNum);//???????????????
            kqMonthStatistical.setLateNum(String.valueOf(lateNum));//???????????????
            kqMonthStatistical.setLeaveEarlyNum(String.valueOf(leaveEarlyNum));//???????????????
            kqMonthStatistical.setMissNum(String.valueOf(missNum));//???????????????
            List<KqMonthStatistical> kqMonth = new ArrayList<KqMonthStatistical>();

            kqMonth.add(kqMonthStatistical);
            return kqMonth;
        }
        return null;
    }
}
