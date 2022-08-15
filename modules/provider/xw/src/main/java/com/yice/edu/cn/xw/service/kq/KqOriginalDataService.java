package com.yice.edu.cn.xw.service.kq;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.esotericsoftware.minlog.Log;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.kq.*;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.*;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.xw.feignClient.jw.classes.JwClassesFeign;
import com.yice.edu.cn.xw.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.xw.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.xw.service.stuInAndOut.ClassTimeService;
import com.yice.edu.cn.common.pojo.xw.kq.KqBasicRules;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.xw.service.stuInAndOut.StuInAndOutClassTimeService;
import com.yice.edu.cn.xw.service.stuInAndOut.StuStatusChangeService;
import com.yice.edu.cn.xw.service.workerKq.KqDailyStatusDetailService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqOriginalDataService {
    private final static Logger logger = LoggerFactory.getLogger(KqOriginalDataService.class);
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private KqSpecialDateService kqSpecialDateService;
    @Autowired
    private KqDailyStatisticalService kqDailyStatisticalService;
    @Autowired
    private KqOriginalDataService kqOriginalDataService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqBasicRulesService kqBasicRulesService;
    @Autowired
    private ClassTimeService classTimeService;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private StuStatusChangeService stuStatusChangeService;
    @Autowired
    private StuInAndOutClassTimeService stuInAndOutClassTimeService;

    @CreateCache(name = Constant.Redis.STUINTIMERECORDS, expire = Constant.Redis.STUINTIMERECORDS_TIMEOUT, timeUnit = TimeUnit.HOURS)
    private Cache<String, Map<String,List<KqDailyStatistical>>> codeCache;

    /*-------------------------------------------------generated code start-----------------------------------------------------------*/
    public KqOriginalData findKqOriginalDataById(String id) {
        return mot.findById(id, KqOriginalData.class);
    }

    public void saveKqOriginalData(KqOriginalData kqOriginalData) {
        kqOriginalData.setCreateTime(DateUtil.now());
        kqOriginalData.setId(sequenceId.nextId());
        mot.insert(kqOriginalData);
    }

    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        Document doc = new Document();
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("2,4")) {
            doc.append("$or", Arrays.asList(new Document("userType", "2"), new Document("userType", "4")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("1,3")) {
            doc.append("$or", Arrays.asList(new Document("userType", "1"), new Document("userType", "3")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("-1,0")) {
            doc.append("$or", Arrays.asList(new Document("userType", "-1"), new Document("userType", "0")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getPassStatus() != null && kqOriginalData.getPassStatus().equals("-1")){
            doc.append("$or", Arrays.asList(new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL), new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE),new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG)));
            kqOriginalData.setPassStatus(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(kqOriginalData));
        /**/
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqOriginalData.class)).find(doc);
        Pager pager = kqOriginalData.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqOriginalData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqOriginalData.class, document)));
        return list;
    }

    public long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        Document doc = new Document();
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("2,4")) {
            doc.append("$or", Arrays.asList(new Document("userType", "2"), new Document("userType", "4")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("1,3")) {
            doc.append("$or", Arrays.asList(new Document("userType", "1"), new Document("userType", "3")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("-1,0")) {
            doc.append("$or", Arrays.asList(new Document("userType", "-1"), new Document("userType", "0")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getPassStatus() != null && kqOriginalData.getPassStatus().equals("-1")){
            doc.append("$or", Arrays.asList(new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL), new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE),new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG)));
            kqOriginalData.setPassStatus(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(kqOriginalData));
        return mot.getCollection(mot.getCollectionName(KqOriginalData.class)).countDocuments(doc);
    }

    public KqOriginalData findOneKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqOriginalData.class)).find(MongoKit.buildMatchDocument(kqOriginalData));
        MongoKit.appendProjection(query, kqOriginalData.getPager());
        return mot.getConverter().read(KqOriginalData.class, query.first());
    }

    public void updateKqOriginalData(KqOriginalData kqOriginalData) {
        mot.updateFirst(query(where("id").is(kqOriginalData.getId())), MongoKit.update(kqOriginalData), KqOriginalData.class);
    }

    public void deleteKqOriginalData(String id) {
        mot.remove(query(where("id").is(id)), KqOriginalData.class);
    }

    public void deleteKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        mot.getCollection(mot.getCollectionName(KqOriginalData.class)).deleteMany(MongoKit.buildMatchDocument(kqOriginalData));
    }

    public void batchSaveKqOriginalData(List<KqOriginalData> kqOriginalDatas) {
        kqOriginalDatas.forEach(kqOriginalData -> kqOriginalData.setId(sequenceId.nextId()));
        mot.insertAll(kqOriginalDatas);
    }

    /*-------------------------------------------------generated code end-----------------------------------------------------------*/
    /*普通规则类*/
    private static KqDailyStatistical kqBasicClass(KqOriginalData t, KqBasicRules bs, KqPunchRules pc) {
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        kqDailyStatistical.setStudentId(t.getStudentId());
        kqDailyStatistical.setSchoolId(t.getSchoolId());
        kqDailyStatistical.setKqDate(t.getCapturedTime().substring(0, 10));
        kqDailyStatistical.setWeekDay((WeekDayUtil.dayForWeek(DateUtil.yesterday()).toString()));
        kqDailyStatistical.setPunchNumber(bs.getPunchNumber());
        kqDailyStatistical.setName(t.getName());
        kqDailyStatistical.setClassId(t.getClassId());
        kqDailyStatistical.setGradeName(t.getGradeName());
        kqDailyStatistical.setClassesNumber(t.getClassesNumber());
        kqDailyStatistical.setGradeId(t.getGradeId());
        kqDailyStatistical.setRuleStatus(pc);
        kqDailyStatistical.setLateStatus("0");
        kqDailyStatistical.setLeaveEarlyStatus("0");
        kqDailyStatistical.setMissStatus("0");
        kqDailyStatistical.setSeatNumber(t.getSeatNumber());
        kqDailyStatistical.setTodayRules(bs.getPunchTime());
        return kqDailyStatistical;
    }

    /*普通规则*/
    public KqDailyStatistical dayBasicRecords(KqOriginalData kqOriginalData) {
        //当天全校数据
        Date date = DateUtil.date();
        Date date1 = DateUtil.offsetDay(date, -1);
        Pager pager = new Pager();
        pager.setIncludes("studentId", "name", "capturedTime", "derectionFlag", "seatNumber", "schoolId", "gradeId", "classId", "userType", "prsnAvtrUrlAddr", "classesNumber", "gradeName", "reqId", "deviceType", "deviceNo", "punchStatus", "createTime");
        pager.setSortOrder(Pager.DESC);
        pager.setSortField("capturedTime");
        pager.setRangeField("capturedTime");
        pager.setPaging(false);
        String[] kqDate = new String[2];
        kqDate[0] = DateUtil.formatDate(date1) + " 00:00:00";
        kqDate[1] = DateUtil.formatDate(date1) + " 23:59:59";
        pager.setRangeArray(kqDate);
        kqOriginalData.setPager(pager);
        kqOriginalData.setUserType("2");
        List<KqOriginalData> list = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        Map<String, List<KqOriginalData>> result = list.stream().collect(Collectors.groupingBy(kq -> kq.getStudentId()));
        //普通规则
        KqBasicRules kqBasicRules = new KqBasicRules();
        kqBasicRules.setSchoolId(kqOriginalData.getSchoolId());
        Query query1 = new Query(Criteria.where("schoolId").is(kqBasicRules.getSchoolId()).and("rule").is("0").and("punchDay").is(WeekDayUtil.dayForWeek(DateUtil.yesterday()).toString()));
        List<KqBasicRules> basicRules = mot.find(query1, KqBasicRules.class);
        if (basicRules.size() == 0 || basicRules == null) {
            return new KqDailyStatistical();
        }
        //当天普通规则
        KqBasicRules bs = basicRules.stream().sorted(Comparator.comparing(KqBasicRules::getCreateTime).reversed()).limit(1).collect(Collectors.toList()).get(0);
        if (bs.getPunchNumber().equals("1")) {//一天一组
            List<KqDailyStatistical> kqDailyStatisticals = new ArrayList<>();
            result.forEach((k, v) -> {
                KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
                KqPunchRules pc = new KqPunchRules();
                List<KqOriginalData> a1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> a2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> a3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                ;
                //A 正常 迟到 缺卡
                if (a1 != null && !(a1.size() == 0)) {
                    KqOriginalData a4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                    pc.setMorningInStatus("5");
                    pc.setMorningIn(a4.getCapturedTime());
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (a1.size() == 0 && !(a2.size() == 0) && a2 != null) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setMorningInStatus("2");
                        pc.setMorningIn(a5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else {
                        pc.setMorningInStatus("1");
                    }
                }
                //B正常 缺卡 早退
                if (!(a2.size() == 0) && a2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        pc.setDuskOutStatus("3");
                        KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOut(a5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (a3 != null && !(a3.size() == 0)) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) > 0 && c.getDerectionFlag().equals("1"))) {
                            pc.setDuskOutStatus("1");
                        }
                    }
                }
                if (!(a2.size() == 0) && a2 != null && !(a3.size() == 0) && a3 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setDuskOutStatus("5");
                            pc.setDuskOut(a6.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        } else {
                            pc.setDuskOutStatus("1");
                        }
                    }
                }
                if (!(a3.size() == 0) && a3 != null && a1 != null && !(a1.size() == 0) && a2.size() == 0) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                        KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setDuskOut(a6.getCapturedTime());
                        pc.setDuskOutStatus("5");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                        pc.setDuskOutStatus("1");
                    }
                }

                if (a3.size() == 0 && !(a2.size() == 0) && a2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        pc.setDuskOutStatus("1");
                    }
                }
                if (a1.size() == 0 && a2.size() == 0) {
                    pc.setDuskOutStatus("1");
                    pc.setMorningInStatus("1");
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (a2.size() == 0 && a3.size() == 0) {
                    pc.setDuskOutStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1")) {
                    kqDailyStatistical.setMissStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2")) {
                    kqDailyStatistical.setLateStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3")) {
                    kqDailyStatistical.setLeaveEarlyStatus("1");
                }
                kqDailyStatisticals.add(kqDailyStatistical);
            });
            kqDailyStatisticalService.batchSaveKqDailyStatistical(kqDailyStatisticals);
        } else if (bs.getPunchNumber().equals("2")) {
            List<KqDailyStatistical> kqDailyStatisticals = new ArrayList<>();
            result.forEach((k, v) -> {
                KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
                KqPunchRules pc = new KqPunchRules();
                List<KqOriginalData> b1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                //A 迟到 缺卡 正常  状态
                if (!(b1.size() == 0) && b1 != null) {
                    KqOriginalData b6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                    pc.setMorningInStatus("5");
                    pc.setMorningIn(b6.getCapturedTime());
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (b1.size() == 0 && !(b2.size() == 0) && b2 != null) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData b = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setMorningInStatus("2");
                        pc.setMorningIn(b.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else {
                        pc.setMorningInStatus("1");
                    }
                }
                if (b1.size() == 0 && b2.size() == 0) {
                    pc.setMorningInStatus("1");
                    pc.setMorningOutStatus("1");
                }
                //B 缺卡 早退 正常  状态
                if (b2.size() == 0 && b3.size() == 0) {
                    pc.setMorningOutStatus("1");
                }
                if (b3.size() == 0) {
                    if (!(b2.size() == 0) && b2 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                            pc.setMorningOutStatus("1");
                        } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                            pc.setMorningOutStatus("3");
                            KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                            pc.setMorningOut(x.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }
                    }
                }

                if (!(b2.size() == 0) && b2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        pc.setMorningOutStatus("3");
                        KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                        pc.setMorningOut(x.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }

                if (!(b3.size() == 0) && b3 != null) {
                    if (!(b2.size() == 0) && b2 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setMorningOutStatus("5");
                                pc.setMorningOut(cc.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            }
                        }
                    } else if (b2.size() == 0) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                            pc.setMorningOutStatus("5");
                            pc.setMorningOut(cc.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        } else {
                            pc.setMorningOutStatus("1");
                        }
                    }
                }
                //C缺卡 正常 迟到 状况
                if (!(b2.size() == 0) && b2 != null) {
                    if (b3.size() == 0) {
                        pc.setNoonInStatus("1");
                    }
                }

                if (b3.size() == 0) {
                    if (b4.size() == 0) {
                        pc.setNoonInStatus("1");
                    } else if (!(b4.size() == 0 && b4 != null)) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0") == false)) {
                            pc.setNoonInStatus("1");
                        }
                    }
                }
                if (!(b3.size() == 0 && b3 != null)) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        KqOriginalData c3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setNoonIn(c3.getCapturedTime());
                        pc.setNoonInStatus("5");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }

                if (!(b3.size() == 0) && b3 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                } else {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                //D 早退 缺卡 正常 状态
                if (b3.size() == 0 && b4 != null && !(b4.size() == 0)) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOutStatus("3");
                        pc.setDuskOut(bb.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (b5 != null && !(b5.size() == 0)) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData vv = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(vv.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                pc.setDuskOutStatus("1");
                            }
                        }
                    }
                }
                if (b4.size() == 0) {
                    if (b3.size() == 0) {
                        pc.setDuskOutStatus("1");
                    } else if (!(b3.size() == 0) && b3 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                            pc.setNoonInStatus("1");
                            pc.setDuskOutStatus("1");
                        }
                    }
                }

                if (b4.size() == 0 && !(b3.size() == 0) && b3 != null) {
                    if (!(b5.size() == 0) && b5 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            ;
                            pc.setDuskOutStatus("5");
                            pc.setDuskOut(bb.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }
                    } else if (b5.size() == 0) {
                        pc.setDuskOutStatus("1");
                    }
                }
                if (!(b4.size() == 0) && b4 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (!(b5.size() == 0) && b5 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == false)) {
                                pc.setDuskOutStatus("1");
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == true)) {
                                KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                ;
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(c7.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            }
                        } else {
                            pc.setDuskOutStatus("1");
                        }
                    } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        KqOriginalData c6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOutStatus("3");
                        pc.setDuskOut(c6.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                if (!(b4.size() == 0) && b4 != null && !(b5.size() == 0) && b5 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                        KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        ;
                        pc.setDuskOutStatus("5");
                        pc.setDuskOut(c7.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }

                if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0 && b4.size() == 0) {
                    pc.setMorningInStatus("1");
                    pc.setMorningOutStatus("1");
                    pc.setNoonInStatus("1");
                    pc.setDuskOutStatus("1");
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(kqBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("1") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("1")) {
                    kqDailyStatistical.setMissStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("2") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("2")) {
                    kqDailyStatistical.setLateStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("3") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("3")) {
                    kqDailyStatistical.setLeaveEarlyStatus("1");
                }
                kqDailyStatisticals.add(kqDailyStatistical);
            });
            kqDailyStatisticalService.batchSaveKqDailyStatistical(kqDailyStatisticals);
        }
        return new KqDailyStatistical();
    }

    /* 特殊日期类*/
    private static KqDailyStatistical kqSpecialClass(KqOriginalData t, KqSpecialDate ks, KqPunchRules pc) {
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        kqDailyStatistical.setStudentId(t.getStudentId());
        kqDailyStatistical.setSchoolId(t.getSchoolId());
        kqDailyStatistical.setKqDate(t.getCapturedTime().substring(0, 10));
        kqDailyStatistical.setWeekDay(WeekDayUtil.dayForWeek(DateUtil.yesterday()).toString());
        kqDailyStatistical.setPunchNumber(ks.getPunchNumber());
        kqDailyStatistical.setName(t.getName());
        kqDailyStatistical.setClassId(t.getClassId());
        kqDailyStatistical.setGradeId(t.getGradeId());
        kqDailyStatistical.setGradeName(t.getGradeName());
        kqDailyStatistical.setClassesNumber(t.getClassesNumber());
        kqDailyStatistical.setRuleStatus(pc);
        kqDailyStatistical.setSeatNumber(t.getSeatNumber());
        kqDailyStatistical.setLateStatus("0");
        kqDailyStatistical.setLeaveEarlyStatus("0");
        kqDailyStatistical.setMissStatus("0");
        kqDailyStatistical.setTodayRules(ks.getPunchTime());
        return kqDailyStatistical;
    }

    /*特殊日期*/
    public KqDailyStatistical judgePunchStatusByrules(KqOriginalData kqOriginalData) {
        //当天全校考勤记录
        Date date = DateUtil.date();
        Date date1 = DateUtil.offsetDay(date, -1);
        Pager pager = new Pager();
        pager.setIncludes("studentId", "name", "capturedTime", "derectionFlag", "seatNumber", "schoolId", "gradeId", "classId", "userType", "prsnAvtrUrlAddr", "classesNumber", "gradeName", "reqId", "deviceType", "deviceNo", "punchStatus", "createTime");
        pager.setSortOrder(Pager.DESC);
        pager.setSortField("capturedTime");
        pager.setRangeField("capturedTime");
        pager.setPaging(false);
        String[] kqDate = new String[2];
        kqDate[0] = DateUtil.formatDate(date1) + " 00:00:00";
        kqDate[1] = DateUtil.formatDate(date1) + " 23:59:59";
        pager.setRangeArray(kqDate);
        kqOriginalData.setPager(pager);
        kqOriginalData.setUserType("2");
        List<KqOriginalData> list = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        Map<String, List<KqOriginalData>> result = list.stream().collect(Collectors.groupingBy(kq -> kq.getStudentId()));
        //特殊日期当天规则
        KqSpecialDate kqSpecialDate = new KqSpecialDate();
        kqSpecialDate.setSchoolId(kqOriginalData.getSchoolId());
        Query query1 = new Query(Criteria.where("startTime").lte(DateUtil.yesterday().toString().substring(0, 10)).andOperator(Criteria.where("endTime").gte(DateUtil.yesterday().toString().substring(0, 10))).and("schoolId").is(kqOriginalData.getSchoolId()));
        List<KqSpecialDate> specialDateList = mot.find(query1, KqSpecialDate.class);
        if (specialDateList.size() > 0) {
            KqSpecialDate ks = specialDateList.stream().sorted(Comparator.comparing(KqSpecialDate::getCreatTime).reversed()).limit(1).collect(Collectors.toList()).get(0);
            if (ks.getType().equals("0")) {//无需打卡
                KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
                return kqDailyStatistical;
            } else if (ks.getType().equals("1")) {//需要打卡
                if (ks.getPunchNumber().equals("1")) {//一天一组
                    List<KqDailyStatistical> kqDailyStatisticals = new ArrayList<>();
                    result.forEach((k, v) -> {
                        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
                        KqPunchRules pc = new KqPunchRules();///
                        List<KqOriginalData> a1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> a2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> a3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                        ;
                        //A 正常 迟到 缺卡
                        if (a1 != null && !(a1.size() == 0)) {
                            KqOriginalData a4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("5");
                            pc.setMorningIn(a4.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (a1.size() == 0 && !(a2.size() == 0) && a2 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setMorningInStatus("2");
                                pc.setMorningIn(a5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else {
                                pc.setMorningInStatus("1");
                            }
                        }
                        //B正常 缺卡 早退
                        if (!(a2.size() == 0) && a2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                pc.setDuskOutStatus("3");
                                KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOut(a5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (a3 != null && !(a3.size() == 0)) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) > 0 && c.getDerectionFlag().equals("1"))) {
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }
                        if (!(a2.size() == 0) && a2 != null && !(a3.size() == 0) && a3 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    pc.setDuskOutStatus("5");
                                    pc.setDuskOut(a6.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                } else {
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }
                        if (!(a3.size() == 0) && a3 != null && a1 != null && !(a1.size() == 0) && a2.size() == 0) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setDuskOut(a6.getCapturedTime());
                                pc.setDuskOutStatus("5");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                pc.setDuskOutStatus("1");
                            }
                        }

                        if (a3.size() == 0 && !(a2.size() == 0) && a2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                pc.setDuskOutStatus("1");
                            }
                        }
                        if (a1.size() == 0 && a2.size() == 0) {
                            pc.setDuskOutStatus("1");
                            pc.setMorningInStatus("1");
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (a2.size() == 0 && a3.size() == 0) {
                            pc.setDuskOutStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1")) {
                            kqDailyStatistical.setMissStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2")) {
                            kqDailyStatistical.setLateStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3")) {
                            kqDailyStatistical.setLeaveEarlyStatus("1");
                        }
                        kqDailyStatisticals.add(kqDailyStatistical);
                    });
                    kqDailyStatisticalService.batchSaveKqDailyStatistical(kqDailyStatisticals);
                } else if (ks.getPunchNumber().equals("2")) {
                    List<KqDailyStatistical> kqDailyStatisticals = new ArrayList<>();
                    result.forEach((k, v) -> {
                        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
                        KqPunchRules pc = new KqPunchRules();
                        List<KqOriginalData> b1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                        //A 迟到 缺卡 正常  状态
                        if (!(b1.size() == 0) && b1 != null) {
                            KqOriginalData b6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("5");
                            pc.setMorningIn(b6.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (b1.size() == 0 && !(b2.size() == 0) && b2 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData b = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setMorningInStatus("2");
                                pc.setMorningIn(b.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else {
                                pc.setMorningInStatus("1");
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0) {
                            pc.setMorningInStatus("1");
                            pc.setMorningOutStatus("1");
                        }
                        //B 缺卡 早退 正常  状态
                        if (b2.size() == 0 && b3.size() == 0) {
                            pc.setMorningOutStatus("1");
                        }
                        if (b3.size() == 0) {
                            if (!(b2.size() == 0) && b2 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                    pc.setMorningOutStatus("1");
                                } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                    pc.setMorningOutStatus("3");
                                    KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                                    pc.setMorningOut(x.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }
                            }
                        }

                        if (!(b2.size() == 0) && b2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                pc.setMorningOutStatus("3");
                                KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                                pc.setMorningOut(x.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }

                        if (!(b3.size() == 0) && b3 != null) {
                            if (!(b2.size() == 0) && b2 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                        KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                        pc.setMorningOutStatus("5");
                                        pc.setMorningOut(cc.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    }
                                }
                            } else if (b2.size() == 0) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                    pc.setMorningOutStatus("5");
                                    pc.setMorningOut(cc.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                } else {
                                    pc.setMorningOutStatus("1");
                                }
                            }
                        }
                        //C缺卡 正常 迟到 状况
                        if (!(b2.size() == 0) && b2 != null) {
                            if (b3.size() == 0) {
                                pc.setNoonInStatus("1");
                            }
                        }

                        if (b3.size() == 0) {
                            if (b4.size() == 0) {
                                pc.setNoonInStatus("1");
                            } else if (!(b4.size() == 0 && b4 != null)) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0") == false)) {
                                    pc.setNoonInStatus("1");
                                }
                            }
                        }
                        if (!(b3.size() == 0 && b3 != null)) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                KqOriginalData c3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setNoonIn(c3.getCapturedTime());
                                pc.setNoonInStatus("5");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }

                        if (!(b3.size() == 0) && b3 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        } else {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        //D 早退 缺卡 正常 状态
                        if (b3.size() == 0 && b4 != null && !(b4.size() == 0)) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOutStatus("3");
                                pc.setDuskOut(bb.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (b5 != null && !(b5.size() == 0)) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                        KqOriginalData vv = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                        pc.setDuskOutStatus("5");
                                        pc.setDuskOut(vv.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                        pc.setDuskOutStatus("1");
                                    }
                                }
                            }
                        }
                        if (b4.size() == 0) {
                            if (b3.size() == 0) {
                                pc.setDuskOutStatus("1");
                            } else if (!(b3.size() == 0) && b3 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                    pc.setNoonInStatus("1");
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }

                        if (b4.size() == 0 && !(b3.size() == 0) && b3 != null) {
                            if (!(b5.size() == 0) && b5 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    ;
                                    pc.setDuskOutStatus("5");
                                    pc.setDuskOut(bb.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }
                            } else if (b5.size() == 0) {
                                pc.setDuskOutStatus("1");
                            }
                        }
                        if (!(b4.size() == 0) && b4 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (!(b5.size() == 0) && b5 != null) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == false)) {
                                        pc.setDuskOutStatus("1");
                                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                        KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                        ;
                                        pc.setDuskOutStatus("5");
                                        pc.setDuskOut(c7.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    }
                                } else {
                                    pc.setDuskOutStatus("1");
                                }
                            } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                KqOriginalData c6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOutStatus("3");
                                pc.setDuskOut(c6.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (!(b4.size() == 0) && b4 != null && !(b5.size() == 0) && b5 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                ;
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(c7.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0 && b4.size() == 0) {
                            pc.setMorningInStatus("1");
                            pc.setMorningOutStatus("1");
                            pc.setNoonInStatus("1");
                            pc.setDuskOutStatus("1");
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(kqSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("1") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("1")) {
                            kqDailyStatistical.setMissStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("2") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("2")) {
                            kqDailyStatistical.setLateStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("3") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("3")) {
                            kqDailyStatistical.setLeaveEarlyStatus("1");
                        }
                        kqDailyStatisticals.add(kqDailyStatistical);
                    });
                    kqDailyStatisticalService.batchSaveKqDailyStatistical(kqDailyStatisticals);
                }
            }
        }
        return new KqDailyStatistical();
    }

    //即时普通规则类
    private static KqDailyStatistical inTimeBasicClass(KqOriginalData t, KqBasicRules bs, KqPunchRules pc) {
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        kqDailyStatistical.setStudentId(t.getStudentId());
        kqDailyStatistical.setSchoolId(t.getSchoolId());
        kqDailyStatistical.setKqDate(t.getCapturedTime().substring(0, 10));
        kqDailyStatistical.setWeekDay((WeekDayUtil.dayForWeek(DateUtil.date()).toString()));
        kqDailyStatistical.setPunchNumber(bs.getPunchNumber());
        kqDailyStatistical.setName(t.getName());
        kqDailyStatistical.setGradeName(t.getGradeName());
        kqDailyStatistical.setClassesNumber(t.getClassesNumber());
        kqDailyStatistical.setClassId(t.getClassId());
        kqDailyStatistical.setGradeId(t.getGradeId());
        kqDailyStatistical.setRuleStatus(pc);
        kqDailyStatistical.setSeatNumber(t.getSeatNumber());
        return kqDailyStatistical;
    }

    //即时普通规则
    public KqDailyStatistical inTimeBasicCount(KqOriginalData kqOriginalData) {
        //该校学生当天考勤数据
        Date date = DateUtil.date();
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        Pager pager = new Pager();
        pager.setIncludes("studentId", "name", "capturedTime", "derectionFlag", "seatNumber", "schoolId", "gradeId", "classId", "userType", "prsnAvtrUrlAddr", "classesNumber", "gradeName", "reqId", "deviceType", "deviceNo", "punchStatus", "createTime");
        pager.setSortOrder(Pager.DESC);
        pager.setSortField("capturedTime");
        pager.setRangeField("capturedTime");
        pager.setPaging(false);
        String[] kqDate = new String[2];
        kqDate[0] = DateUtil.formatDate(date) + " 00:00:00";
        kqDate[1] = DateUtil.formatDate(date) + " 23:59:59";
        pager.setRangeArray(kqDate);
        kqOriginalData.setPager(pager);
        kqOriginalData.setUserType("2");
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> list = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        Map<String, List<KqOriginalData>> result = list.stream().collect(Collectors.groupingBy(kq -> kq.getStudentId()));
        //普通规则
        KqBasicRules kqBasicRules = new KqBasicRules();
        kqBasicRules.setSchoolId(kqOriginalData.getSchoolId());
        Query query1 = new Query(Criteria.where("schoolId").is(kqBasicRules.getSchoolId()).and("rule").is("0").and("punchDay").is(WeekDayUtil.dayForWeek(DateUtil.date()).toString()));
        List<KqBasicRules> basicRules = mot.find(query1, KqBasicRules.class);
        if (basicRules.size() == 0 || basicRules == null) {
            return kqDailyStatistical;
        }
        KqBasicRules bs = basicRules.stream().sorted(Comparator.comparing(KqBasicRules::getCreateTime).reversed()).limit(1).collect(Collectors.toList()).get(0);
        if (bs.getPunchNumber().equals("1")) {//一天一组
            result.forEach((k, v) -> {
                KqPunchRules pc = new KqPunchRules();///
                List<KqOriginalData> a1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> a2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> a3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                ;
                //A 正常 迟到 缺卡
                if (a1 != null && !(a1.size() == 0)) {
                    KqOriginalData a4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                    pc.setMorningInStatus("5");
                    pc.setMorningIn(a4.getCapturedTime());
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (a1.size() == 0 && !(a2.size() == 0) && a2 != null) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setMorningInStatus("2");
                        pc.setMorningIn(a5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else {
                        pc.setMorningInStatus("1");
                    }
                }
                //B正常 缺卡 早退
                if (!(a2.size() == 0) && a2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        pc.setDuskOutStatus("3");
                        KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOut(a5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (a3 != null && !(a3.size() == 0)) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) > 0 && c.getDerectionFlag().equals("1"))) {
                            pc.setDuskOutStatus("1");
                        }
                    }
                }
                if (!(a2.size() == 0) && a2 != null && !(a3.size() == 0) && a3 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setDuskOutStatus("5");
                            pc.setDuskOut(a6.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        } else {
                            pc.setDuskOutStatus("1");
                        }
                    }
                }
                if (!(a3.size() == 0) && a3 != null && a1 != null && !(a1.size() == 0) && a2.size() == 0) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                        KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setDuskOut(a6.getCapturedTime());
                        pc.setDuskOutStatus("5");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                        pc.setDuskOutStatus("1");
                    }
                }

                if (a3.size() == 0 && !(a2.size() == 0) && a2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        pc.setDuskOutStatus("1");
                    }
                }

                if (a1.size() == 0 && a2.size() == 0) {
                    if (a3.size() == 0) {
                        pc.setDuskOutStatus("1");
                        pc.setMorningInStatus("1");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (!(a3.size() == 0) && a3 != null) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData kk = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("1");
                            pc.setDuskOut(kk.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }

                    }
                }

                if (a2.size() == 0 && a3.size() == 0) {
                    pc.setDuskOutStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1")) {
                    kqDailyStatistical.setMissStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2")) {
                    kqDailyStatistical.setLateStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3")) {
                    kqDailyStatistical.setLeaveEarlyStatus("1");
                }
            });
        } else if (bs.getPunchNumber().equals("2")) {
            result.forEach((k, v) -> {
                KqPunchRules pc = new KqPunchRules();///
                List<KqOriginalData> b1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                List<KqOriginalData> b5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                //A 迟到 缺卡 正常  状态
                if (!(b1.size() == 0) && b1 != null) {
                    KqOriginalData b6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                    pc.setMorningInStatus("5");
                    pc.setMorningIn(b6.getCapturedTime());
                    v.stream().forEach(t -> {
                        BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                    });
                }
                if (b1.size() == 0 && !(b2.size() == 0) && b2 != null) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData b = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        pc.setMorningInStatus("2");
                        pc.setMorningIn(b.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else {
                        pc.setMorningInStatus("1");
                    }
                }
                if (b1.size() == 0 && b2.size() == 0) {
                    pc.setMorningInStatus("1");
                    pc.setMorningOutStatus("1");
                }
                //B 缺卡 早退 正常  状态
                if (b2.size() == 0 && b3.size() == 0) {
                    pc.setMorningOutStatus("1");
                }
                if (b3.size() == 0) {
                    if (!(b2.size() == 0) && b2 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                            pc.setMorningOutStatus("1");
                        } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                            pc.setMorningOutStatus("3");
                            KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                            pc.setMorningOut(x.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }
                    }
                }

                if (!(b2.size() == 0) && b2 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        pc.setMorningOutStatus("3");
                        KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                        pc.setMorningOut(x.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }

                if (!(b3.size() == 0) && b3 != null) {
                    if (!(b2.size() == 0) && b2 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setMorningOutStatus("5");
                                pc.setMorningOut(cc.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            }
                        }
                    } else if (b2.size() == 0) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                            pc.setMorningOutStatus("5");
                            pc.setMorningOut(cc.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        } else {
                            pc.setMorningOutStatus("1");
                        }
                    }
                }
                //C缺卡 正常 迟到 状况
                if (!(b2.size() == 0) && b2 != null) {
                    if (b3.size() == 0) {
                        pc.setNoonInStatus("1");
                    }
                }

                if (b3.size() == 0) {
                    if (b4.size() == 0) {
                        pc.setNoonInStatus("1");
                    } else if (!(b4.size() == 0 && b4 != null)) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0") == false)) {
                            pc.setNoonInStatus("1");
                        }
                    }
                }
                if (!(b3.size() == 0 && b3 != null)) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        KqOriginalData c3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setNoonIn(c3.getCapturedTime());
                        pc.setNoonInStatus("5");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }

                if (!(b3.size() == 0) && b3 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                } else {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0) {
                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                        KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                        pc.setNoonInStatus("2");
                        pc.setNoonIn(c5.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                //D 早退 缺卡 正常 状态
                if (b3.size() == 0 && b4 != null && !(b4.size() == 0)) {//////////新增修改
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOutStatus("3");
                        pc.setDuskOut(bb.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (b5 != null && !(b5.size() == 0)) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData vv = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(vv.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                pc.setDuskOutStatus("1");
                            }
                        }
                    }
                }
                if (b4.size() == 0) {
                    if (b3.size() == 0) {
                        pc.setDuskOutStatus("1");
                    } else if (!(b3.size() == 0) && b3 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                            pc.setNoonInStatus("1");
                            pc.setDuskOutStatus("1");
                        }
                    }
                }

                if (b4.size() == 0 && !(b3.size() == 0) && b3 != null) {
                    if (!(b5.size() == 0) && b5 != null) {
                        if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            ;
                            pc.setDuskOutStatus("5");
                            pc.setDuskOut(bb.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }
                    } else if (b5.size() == 0) {
                        pc.setDuskOutStatus("1");
                    }
                }
                if (!(b4.size() == 0) && b4 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                        if (!(b5.size() == 0) && b5 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == false)) {
                                pc.setDuskOutStatus("1");
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == true)) {
                                KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                ;
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(c7.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                                });
                            }
                        } else {
                            pc.setDuskOutStatus("1");
                        }
                    } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                        KqOriginalData c6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                        pc.setDuskOutStatus("3");
                        pc.setDuskOut(c6.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                if (!(b4.size() == 0) && b4 != null && !(b5.size() == 0) && b5 != null) {
                    if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                        KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                        ;
                        pc.setDuskOutStatus("5");
                        pc.setDuskOut(c7.getCapturedTime());
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    }
                }
                if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0 && b4.size() == 0) {
                    if (b5.size() == 0) {
                        pc.setMorningInStatus("1");
                        pc.setMorningOutStatus("1");
                        pc.setNoonInStatus("1");
                        pc.setDuskOutStatus("1");
                        v.stream().forEach(t -> {
                            BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                        });
                    } else if (!(b5.size() == 0) && b5 != null) {
                        if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                            KqOriginalData kk = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(bs.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("1");
                            pc.setMorningOutStatus("1");
                            pc.setNoonInStatus("1");
                            pc.setDuskOut(kk.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeBasicClass(t, bs, pc), kqDailyStatistical);
                            });
                        }
                    }
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("1") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("1")) {
                    kqDailyStatistical.setMissStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("2") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("2")) {
                    kqDailyStatistical.setLateStatus("1");
                }
                if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("3") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("3")) {
                    kqDailyStatistical.setLeaveEarlyStatus("1");
                }
            });
        }
        return kqDailyStatistical;
    }

    //即时特殊日期类
    private static KqDailyStatistical inTimeSpecialClass(KqOriginalData t, KqSpecialDate ks, KqPunchRules pc) {
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        kqDailyStatistical.setStudentId(t.getStudentId());
        kqDailyStatistical.setSchoolId(t.getSchoolId());
        kqDailyStatistical.setKqDate(t.getCapturedTime().substring(0, 10));
        kqDailyStatistical.setWeekDay(WeekDayUtil.dayForWeek(DateUtil.date()).toString());
        kqDailyStatistical.setPunchNumber(ks.getPunchNumber());
        kqDailyStatistical.setName(t.getName());
        kqDailyStatistical.setGradeName(t.getGradeName());
        kqDailyStatistical.setClassesNumber(t.getClassesNumber());
        kqDailyStatistical.setClassId(t.getClassId());
        kqDailyStatistical.setGradeId(t.getGradeId());
        kqDailyStatistical.setRuleStatus(pc);
        kqDailyStatistical.setSeatNumber(t.getSeatNumber());
        return kqDailyStatistical;
    }

    //即时特殊日期
    public KqDailyStatistical inTimeSpecialCount(KqOriginalData kqOriginalData) {
        //查出当天全校考勤记录
        Date date = DateUtil.date();
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        Pager pager = new Pager();
        pager.setIncludes("studentId", "name", "capturedTime", "derectionFlag", "seatNumber", "schoolId", "gradeId", "classId", "userType", "prsnAvtrUrlAddr", "classesNumber", "gradeName", "reqId", "deviceType", "deviceNo", "punchStatus", "createTime");
        pager.setSortOrder(Pager.DESC);
        pager.setSortField("capturedTime");
        pager.setRangeField("capturedTime");
        pager.setPaging(false);
        String[] kqDate = new String[2];
        kqDate[0] = DateUtil.formatDate(date) + " 00:00:00";
        kqDate[1] = DateUtil.formatDate(date) + " 23:59:59";
        pager.setRangeArray(kqDate);
        kqOriginalData.setPager(pager);
        kqOriginalData.setUserType("2");
        kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> list = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        Map<String, List<KqOriginalData>> result = list.stream().collect(Collectors.groupingBy(kq -> kq.getStudentId()));
        //特殊日期当天规则
        KqSpecialDate kqSpecialDate = new KqSpecialDate();
        kqSpecialDate.setSchoolId(kqOriginalData.getSchoolId());
        Query query1 = new Query(Criteria.where("startTime").lte(DateUtil.today()).andOperator(Criteria.where("endTime").gte(DateUtil.today())).and("schoolId").is(kqOriginalData.getSchoolId()));
        List<KqSpecialDate> specialDateList = mot.find(query1, KqSpecialDate.class);//查出当天的规则
        if (specialDateList.size() > 0) {
            KqSpecialDate ks = specialDateList.stream().sorted(Comparator.comparing(KqSpecialDate::getCreatTime).reversed()).limit(1).collect(Collectors.toList()).get(0);
            if (ks.getType().equals("0")) {//无需打卡
                return kqDailyStatistical;
            } else if (ks.getType().equals("1")) {//需要打卡
                if (ks.getPunchNumber().equals("1")) {//一天一组
                    result.forEach((k, v) -> {
                        KqPunchRules pc = new KqPunchRules();///
                        List<KqOriginalData> a1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> a2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> a3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                        ;
                        //A 正常 迟到 缺卡
                        if (a1 != null && !(a1.size() == 0)) {
                            KqOriginalData a4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("5");
                            pc.setMorningIn(a4.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (a1.size() == 0 && !(a2.size() == 0) && a2 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setMorningInStatus("2");
                                pc.setMorningIn(a5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else {
                                pc.setMorningInStatus("1");
                            }
                        }
                        //B正常 缺卡 早退
                        if (!(a2.size() == 0) && a2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                pc.setDuskOutStatus("3");
                                KqOriginalData a5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOut(a5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (a3 != null && !(a3.size() == 0)) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) > 0 && c.getDerectionFlag().equals("1"))) {
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }
                        if (!(a2.size() == 0) && a2 != null && !(a3.size() == 0) && a3 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    pc.setDuskOutStatus("5");
                                    pc.setDuskOut(a6.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                } else {
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }
                        if (!(a3.size() == 0) && a3 != null && a1 != null && !(a1.size() == 0) && a2.size() == 0) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData a6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setDuskOut(a6.getCapturedTime());
                                pc.setDuskOutStatus("5");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                pc.setDuskOutStatus("1");
                            }
                        }

                        if (a3.size() == 0 && !(a2.size() == 0) && a2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                pc.setDuskOutStatus("1");
                            }
                        }

                        if (a1.size() == 0 && a2.size() == 0) {
                            if (a3.size() == 0) {
                                pc.setDuskOutStatus("1");
                                pc.setMorningInStatus("1");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (!(a3.size() == 0) && a3 != null) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData kk = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    pc.setMorningInStatus("1");
                                    pc.setDuskOut(kk.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }

                            }
                        }
                        if (a2.size() == 0 && a3.size() == 0) {
                            pc.setDuskOutStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1")) {
                            kqDailyStatistical.setMissStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2")) {
                            kqDailyStatistical.setLateStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3")) {
                            kqDailyStatistical.setLeaveEarlyStatus("1");
                        }
                    });
                } else if (ks.getPunchNumber().equals("2")) {
                    result.forEach((k, v) -> {
                        KqPunchRules pc = new KqPunchRules();///
                        List<KqOriginalData> b1 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b2 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b4 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).collect(Collectors.toList());
                        List<KqOriginalData> b5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).collect(Collectors.toList());
                        //A 迟到 缺卡 正常  状态
                        if (!(b1.size() == 0) && b1 != null) {
                            KqOriginalData b6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                            pc.setMorningInStatus("5");
                            pc.setMorningIn(b6.getCapturedTime());
                            v.stream().forEach(t -> {
                                BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                            });
                        }
                        if (b1.size() == 0 && !(b2.size() == 0) && b2 != null) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData b = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                pc.setMorningInStatus("2");
                                pc.setMorningIn(b.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else {
                                pc.setMorningInStatus("1");
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0) {
                            pc.setMorningInStatus("1");
                            pc.setMorningOutStatus("1");
                        }
                        //B 缺卡 早退 正常  状态
                        if (b2.size() == 0 && b3.size() == 0) {
                            pc.setMorningOutStatus("1");
                        }
                        if (b3.size() == 0) {
                            if (!(b2.size() == 0) && b2 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                    pc.setMorningOutStatus("1");
                                } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                    pc.setMorningOutStatus("3");
                                    KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                                    pc.setMorningOut(x.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }
                            }
                        }

                        if (!(b2.size() == 0) && b2 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                pc.setMorningOutStatus("3");
                                KqOriginalData x = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0 && c.getDerectionFlag().equals("1")).limit(1).collect(Collectors.toList()).get(0);
                                pc.setMorningOut(x.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }

                        if (!(b3.size() == 0) && b3 != null) {
                            if (!(b2.size() == 0) && b2 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                        KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                        pc.setMorningOutStatus("5");
                                        pc.setMorningOut(cc.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    }
                                }
                            } else if (b2.size() == 0) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData cc = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0 && c.getDerectionFlag().equals("1")).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                    pc.setMorningOutStatus("5");
                                    pc.setMorningOut(cc.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                } else {
                                    pc.setMorningOutStatus("1");
                                }
                            }
                        }
                        //C缺卡 正常 迟到 状况
                        if (!(b2.size() == 0) && b2 != null) {
                            if (b3.size() == 0) {
                                pc.setNoonInStatus("1");
                            }
                        }

                        if (b3.size() == 0) {
                            if (b4.size() == 0) {
                                pc.setNoonInStatus("1");
                            } else if (!(b4.size() == 0 && b4 != null)) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0") == false)) {
                                    pc.setNoonInStatus("1");
                                }
                            }
                        }
                        if (!(b3.size() == 0 && b3 != null)) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                KqOriginalData c3 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setNoonIn(c3.getCapturedTime());
                                pc.setNoonInStatus("5");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }

                        if (!(b3.size() == 0) && b3 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        } else {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0) {
                            if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0 && c.getDerectionFlag().equals("0"))) {
                                KqOriginalData c5 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime).reversed()).findFirst().get();
                                pc.setNoonInStatus("2");
                                pc.setNoonIn(c5.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        //D 早退 缺卡 正常 状态
                        if (b3.size() == 0 && b4 != null && !(b4.size() == 0)) {//////////新增修改
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOutStatus("3");
                                pc.setDuskOut(bb.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (b5 != null && !(b5.size() == 0)) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                        KqOriginalData vv = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                        pc.setDuskOutStatus("5");
                                        pc.setDuskOut(vv.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1")) == false) {
                                        pc.setDuskOutStatus("1");
                                    }
                                }
                            }
                        }
                        if (b4.size() == 0) {
                            if (b3.size() == 0) {
                                pc.setDuskOutStatus("1");
                            } else if (!(b3.size() == 0) && b3 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                    pc.setNoonInStatus("1");
                                    pc.setDuskOutStatus("1");
                                }
                            }
                        }

                        if (b4.size() == 0 && !(b3.size() == 0) && b3 != null) {
                            if (!(b5.size() == 0) && b5 != null) {
                                if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getMorningOut()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData bb = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    ;
                                    pc.setDuskOutStatus("5");
                                    pc.setDuskOut(bb.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }
                            } else if (b5.size() == 0) {
                                pc.setDuskOutStatus("1");
                            }
                        }
                        if (!(b4.size() == 0) && b4 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0")) {
                                if (!(b5.size() == 0) && b5 != null) {
                                    if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == false)) {
                                        pc.setDuskOutStatus("1");
                                    } else if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1") == true)) {
                                        KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                        ;
                                        pc.setDuskOutStatus("5");
                                        pc.setDuskOut(c7.getCapturedTime());
                                        v.stream().forEach(t -> {
                                            BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                        });
                                    }
                                } else {
                                    pc.setDuskOutStatus("1");
                                }
                            } else if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("1")) {
                                KqOriginalData c6 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0);
                                pc.setDuskOutStatus("3");
                                pc.setDuskOut(c6.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (!(b4.size() == 0) && b4 != null && !(b5.size() == 0) && b5 != null) {
                            if (v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getNoonIn()) >= 0 && c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) < 0).limit(1).collect(Collectors.toList()).get(0).getDerectionFlag().equals("0") && v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                KqOriginalData c7 = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                ;
                                pc.setDuskOutStatus("5");
                                pc.setDuskOut(c7.getCapturedTime());
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            }
                        }
                        if (b1.size() == 0 && b2.size() == 0 && b3.size() == 0 && b4.size() == 0) {
                            if (b5.size() == 0) {
                                pc.setMorningInStatus("1");
                                pc.setMorningOutStatus("1");
                                pc.setNoonInStatus("1");
                                pc.setDuskOutStatus("1");
                                v.stream().forEach(t -> {
                                    BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                });
                            } else if (!(b5.size() == 0) && b5 != null) {
                                if (v.stream().anyMatch(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0 && c.getDerectionFlag().equals("1"))) {
                                    KqOriginalData kk = v.stream().filter(c -> c.getCapturedTime().substring(11).compareTo(ks.getPunchTime().getDuskOut()) >= 0).sorted(Comparator.comparing(KqOriginalData::getCapturedTime)).findFirst().get();
                                    pc.setMorningInStatus("1");
                                    pc.setMorningOutStatus("1");
                                    pc.setNoonInStatus("1");
                                    pc.setDuskOut(kk.getCapturedTime());
                                    v.stream().forEach(t -> {
                                        BeanUtils.copyProperties(inTimeSpecialClass(t, ks, pc), kqDailyStatistical);
                                    });
                                }
                            }
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("1") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("1") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("1") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("1")) {
                            kqDailyStatistical.setMissStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("2") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("2") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("2") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("2")) {
                            kqDailyStatistical.setLateStatus("1");
                        }
                        if (pc.getMorningInStatus() != null && pc.getMorningInStatus().equals("3") || pc.getDuskOutStatus() != null && pc.getDuskOutStatus().equals("3") || pc.getMorningOutStatus() != null && pc.getMorningOutStatus().equals("3") || pc.getNoonInStatus() != null && pc.getNoonInStatus().equals("3")) {
                            kqDailyStatistical.setLeaveEarlyStatus("1");
                        }
                    });
                }
            }
        }
        return kqDailyStatistical;
    }

    //判断普通还是特殊日期
    public Integer judgeBasicOrSpecialIsTrue(String schoolId, int isDaliyStatis) {
        // 1特殊 2 普通 3没有规则
        KqSpecialDate kqSpecialDate = new KqSpecialDate();
        kqSpecialDate.setSchoolId(schoolId);
        Query query1 = new Query(Criteria.where("startTime").lte(DateUtil.today()).andOperator(Criteria.where("endTime").gte(DateUtil.today())).and("schoolId").is(kqSpecialDate.getSchoolId()));
        if (isDaliyStatis == 1) {//日统计统计昨天的数据//*
            query1 = new Query(Criteria.where("startTime").lte(DateUtil.yesterday().toString().substring(0, 10)).andOperator(Criteria.where("endTime").gte(DateUtil.yesterday().toString().substring(0, 10))).and("schoolId").is(kqSpecialDate.getSchoolId()));
        }
        List<KqSpecialDate> specialDateList = mot.find(query1, KqSpecialDate.class);//是否特殊规则
        if (specialDateList.size() > 0) {
            return 1;
        } else if (specialDateList.size() == 0) {
            KqBasicRules kqBasicRules = new KqBasicRules();
            kqBasicRules.setSchoolId(schoolId);
            Query query2 = new Query(Criteria.where("schoolId").is(kqBasicRules.getSchoolId()).and("rule").is("0").and("punchDay").is(WeekDayUtil.dayForWeek(DateUtil.date()).toString()));
            List<KqBasicRules> special = mot.find(query2, KqBasicRules.class);
            if (special.size() > 0) {
                return 2;
            }
        }
        return 3;
    }

    public void xwStuKqDailyStatis() {
        KqDailyStatusDetailService.getExecutor().execute(() ->{
            try {
                KqSchoolInit kqSchoolInit = new KqSchoolInit();
                List<KqSchoolInit> kqSchoolInits = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
                for (KqSchoolInit k : kqSchoolInits) {
                    Integer i = judgeBasicOrSpecialIsTrue(k.getCustId(), 1);
                    KqOriginalData kqOriginalData1 = new KqOriginalData();
                    kqOriginalData1.setSchoolId(k.getCustId());
                    if (i == 1) {
                        //进行特殊规则统计
                        judgePunchStatusByrules(kqOriginalData1);
                    } else if (i == 2) {
                        //进行普通规则统计
                        dayBasicRecords(kqOriginalData1);
                    }
                    //更新当日学校基础规则
                    KqBasicRules kqBasicRules = new KqBasicRules();
                    kqBasicRules.setSchoolId(k.getCustId());
                    kqBasicRulesService.dailyStatisUpdateBasicRlues(kqBasicRules);
                }

            } catch (Exception e) {
                logger.info("统计学生考勤异常");
                logger.error(e.getLocalizedMessage());
            }
        });

       /* Thread thread = new Thread() {
            public void run() {
                try {
                    KqSchoolInit kqSchoolInit = new KqSchoolInit();
                    List<KqSchoolInit> kqSchoolInits = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
                    for (KqSchoolInit k : kqSchoolInits) {
                        Integer i = judgeBasicOrSpecialIsTrue(k.getCustId(), 1);
                        KqOriginalData kqOriginalData1 = new KqOriginalData();
                        kqOriginalData1.setSchoolId(k.getCustId());
                        if (i == 1) {
                            //进行特殊规则统计
                            judgePunchStatusByrules(kqOriginalData1);
                        } else if (i == 2) {
                            //进行普通规则统计
                            dayBasicRecords(kqOriginalData1);
                        }
                        //更新当日学校基础规则
                        KqBasicRules kqBasicRules = new KqBasicRules();
                        kqBasicRules.setSchoolId(k.getCustId());
                        kqBasicRulesService.dailyStatisUpdateBasicRlues(kqBasicRules);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    this.interrupt();
                }
            }
        };
        thread.start();*/
    }

    //学生出入校记录
    //状态 true这条记录正常  false这条记录异常
    public Boolean statusOfKqOriginalData(KqOriginalData kqOriginalData) {
        String schoolId = kqOriginalData.getSchoolId();
        String gradeId = kqOriginalData.getGradeId();
        boolean flag = true;
        StuInAndOutClassTime stuInAndOutClassTime = new StuInAndOutClassTime();
        stuInAndOutClassTime.setSchoolId(schoolId);
        List<StuInAndOutClassTime> schoolAllClassTimes = stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
        StuInAndOutClassTime theClassTime = null;
        for (StuInAndOutClassTime classTimeTable:schoolAllClassTimes){
            String[] classes = classTimeTable.getClasses();
            System.out.println(gradeId);
            if (Arrays.stream(classes).anyMatch(s -> s.equals(gradeId))){
                //这个就是他的上课时间表
                theClassTime=classTimeTable;
            }
        }
        if (theClassTime==null){return true;}
        //找出今天的上课时间规则
        List<ClassTime> todayRealUseClassTimeList = new ArrayList<>();
        List<StartTimeAndDayAndEndTime> list = theClassTime.getList();
        for (StartTimeAndDayAndEndTime startTimeAndDayAndEndTime:  list){
            ClassTime classTime2 = new ClassTime();
            classTime2.setStartTime(startTimeAndDayAndEndTime.getStartTime());
                List<DayAndEndTime> dayAndEndTimes= startTimeAndDayAndEndTime.getList();
                Integer integer = WeekDayUtil.dayForWeek(DateUtil.date())-1;
                List<DayAndEndTime> todayEndTime = dayAndEndTimes.stream().filter(dayAndEndTime -> dayAndEndTime != null && dayAndEndTime.getEndTime()!=null&&dayAndEndTime.getWeekDay() != null
                        && dayAndEndTime.getWeekDay().equals(String.valueOf(integer))).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(todayEndTime)){
                    classTime2.setEndTime(todayEndTime.get(0).getEndTime());
                    todayRealUseClassTimeList.add(classTime2);
                }
        }
        if (CollectionUtil.isEmpty(todayRealUseClassTimeList)){
            System.out.println("代表放假");
            return true;
        }
        System.out.println(todayRealUseClassTimeList);

        List<ClassTime> classTimeList =todayRealUseClassTimeList;
        if (classTimeList.size() > 0) {
            for (ClassTime ct : classTimeList) {
                if (DateUtil.timeToSecond(kqOriginalData.getCapturedTime().substring(11)) > (DateUtil.timeToSecond(ct.getStartTime() + ":00")) && DateUtil.timeToSecond(kqOriginalData.getCapturedTime().substring(11)) < (DateUtil.timeToSecond(ct.getEndTime() + ":00"))) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            return flag;
        }
        return true;
    }

    public void changeStuNowStatus() {
        //KqSchoolInit kqSchoolInit = new KqSchoolInit();
        //List<KqSchoolInit> kqSchoolInits = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);


            KqDailyStatusDetailService.getExecutor().execute(() -> {
                School school = new School();
                List<School> schoolListByCondition = schoolFeign.findSchoolListByCondition(school);
                for (School s : schoolListByCondition) {
                try {
                    Student studentData = new Student();
                    studentData.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);//改为离校
                    Student studentCondition = new Student();
                    studentCondition.setSchoolId(s.getId());
                    studentCondition.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.IN_SCHOOL);//在校
                    studentCondition.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT);//走读
                    Map<String, Student> map = new HashMap<>();
                    map.put("student", studentData);
                    map.put("condition", studentCondition);
                    studentFeign.updateStudentByCondition(map);
                    Student student = new Student();
                    student.setSchoolId(s.getId());
                    //student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);
                    List<Student> studentList = studentFeign.findStudentListByCondition(student);
                    //添加学生在校状态变化记录
                    List<StuStatusChange> stuStatusChanges = new ArrayList<>();
                    for (Student s1 :studentList){
                        StuStatusChange sc= new StuStatusChange();
                        sc.setSchoolId(s1.getSchoolId());
                        sc.setStudentId(s1.getId());
                        sc.setStatus(s1.getNowStatus());
                        sc.setCreateDate(DateUtil.today());
                        sc.setCreateTime(DateUtil.now());
                        sc.setChangeTime(DateUtil.now());
                        sc.setClassId(s1.getClassesId());
                        stuStatusChanges.add(sc);
                    }
                    stuStatusChangeService.batchSaveStuStatusChange(stuStatusChanges);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("按学校更新学生状态异常");
                    logger.error(e.getLocalizedMessage());

                }
                }
            });


    }

    public void  deleteThreeMonthsAgoAllData(){
        KqDailyStatusDetailService.getExecutor().execute(()->{
            KqOriginalData kqOriginalData = new KqOriginalData();
            String todayThreeMonthAgo = DateUtil.date().offset(DateField.MONTH,-3).toString().substring(0,10);
             String date =todayThreeMonthAgo+" 00:00:00";
            //今天5月1日
            //三个月前的今天 2月1日
            //删除2月1号前所有数据
            Document doc = new Document();
            doc.append("capturedTime",new Document("$lt", date));
            doc.putAll(MongoKit.buildMatchDocument(kqOriginalData));
            mot.getCollection(mot.getCollectionName(KqOriginalData.class)).deleteMany(doc);
        });
    }

    public List<KqOriginalData> findSomeTimeAgoKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        Document doc = new Document();
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("2,4")) {
            doc.append("$or", Arrays.asList(new Document("userType", "2"), new Document("userType", "4")));
            kqOriginalData.setUserType(null);
        }
        if (kqOriginalData.getUserType() != null && kqOriginalData.getUserType().equals("1,3")) {
            doc.append("$or", Arrays.asList(new Document("userType", "1"), new Document("userType", "3")));
            kqOriginalData.setUserType(null);
        }
        boolean present = Optional.ofNullable(kqOriginalData.getCapturedTime()).isPresent();
        if (present){
            doc.append("$and", Arrays.asList(new Document("capturedTime",new Document("$lte", kqOriginalData.getCapturedTime()) )
                    ,new Document("capturedTime",new Document("$gte",DateUtil.today()+" 00:00:00" ))));
        }
        kqOriginalData.setCapturedTime(null);
        boolean present1 = Optional.ofNullable(kqOriginalData.getId()).isPresent();
        if (present1){
            doc.append("_id",new Document("$lt",kqOriginalData.getId()));
            kqOriginalData.setId(null);
        }
        if (kqOriginalData.getPassStatus() != null && kqOriginalData.getPassStatus().equals("-1")){
            doc.append("$or", Arrays.asList(new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_MORMAL), new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_LEAVE),new Document("passStatus", Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG)));
            kqOriginalData.setPassStatus(null);
        }
        doc.putAll(MongoKit.buildMatchDocument(kqOriginalData));
        /**/
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqOriginalData.class)).find(doc);
        Pager pager = kqOriginalData.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqOriginalData> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqOriginalData.class, document)));
        return list;
    }

    /*------------------------------------*/

/*
    //即时统计  普通  班级
    public List<KqDailyStatistical> InTimeBasicByClass(KqOriginalData kqOriginalData) {
        List<KqDailyStatistical> list=new ArrayList<>();
        Map<String, List<KqDailyStatistical>> map = new HashMap<>();
        try{
                codeCache.get("stuIn").isEmpty();
                if(!codeCache.get("stuIn").isEmpty()){
                Map<String, List<KqDailyStatistical>> map2 = codeCache.get("stuIn");
                for(String key:map2.keySet()){
                    if(key!=null&&key.equals(kqOriginalDataService.inTimeBasicCount(kqOriginalData).getClassId())){
                        for(KqDailyStatistical k:map2.get(key)){
                            if(k.getSchoolId()!=null&&k.getSchoolId().equals(
                                    kqOriginalDataService.inTimeBasicCount(kqOriginalData).getSchoolId())){
                                list.remove(k);
                                map.put(key,list);
                            }
                        }
                        codeCache.put("stuIn",map);
                    }
                }
                Map<String,List<KqDailyStatistical>> map1=new HashMap<>();
                Student student=new Student();
                KqDailyStatistical kkkk = kqOriginalDataService.inTimeBasicCount(kqOriginalData);
                student.setClassesId(kkkk.getClassId());
                student.setSchoolId(kkkk.getSchoolId());
                List<Student> studentList = studentFeign.findStudentListByCondition(student);
                for (Student s:studentList){
                    kqOriginalData.setStudentId(s.getId());
                    KqDailyStatistical kk=new KqDailyStatistical();
                    kk = kqOriginalDataService.inTimeBasicCount(kqOriginalData);
                    kk.setStudentId(s.getId());
                    list.add(kk);
                    map.put(s.getClassesId(),list);
                }
                codeCache.put("stuIn",map);
            }
        }catch (Exception e){
            KqDailyStatistical k = kqOriginalDataService.inTimeBasicCount(kqOriginalData);
            list.add(k);
            map.put(k.getClassId(),list);
            codeCache.put("stuIn",map);
        }
        return list;
    }
   //即时统计  特殊  班级
    public List<KqDailyStatistical> InTimeSpecialByClass(KqOriginalData kqOriginalData){
        List<KqDailyStatistical> list=new ArrayList<>();
        Map<String, List<KqDailyStatistical>> map = new HashMap<>();
        try{
            codeCache.get("stuIn").isEmpty();
            if(!codeCache.get("stuIn").isEmpty()){
                Map<String, List<KqDailyStatistical>> map2 = codeCache.get("stuIn");
                for(String key:map2.keySet()){
                    if(key!=null&&key.equals(kqOriginalDataService.inTimeSpecialCount(kqOriginalData).getClassId())){
                        for(KqDailyStatistical k:map2.get(key)){
                            if(k.getSchoolId()!=null&&k.getSchoolId().equals(
                                    kqOriginalDataService.inTimeSpecialCount(kqOriginalData).getSchoolId())){
                                list.remove(k);
                                map.put(key,list);
                            }
                        }
                        codeCache.put("stuIn",map);
                    }
                }
                Map<String,List<KqDailyStatistical>> map1=new HashMap<>();
                Student student=new Student();
                KqDailyStatistical kkkk = kqOriginalDataService.inTimeSpecialCount(kqOriginalData);
                student.setClassesId(kkkk.getClassId());
                student.setSchoolId(kkkk.getSchoolId());
                List<Student> studentList = studentFeign.findStudentListByCondition(student);
                for (Student s:studentList){
                    kqOriginalData.setStudentId(s.getId());
                    KqDailyStatistical kk=new KqDailyStatistical();
                    kk = kqOriginalDataService.inTimeSpecialCount(kqOriginalData);
                    kk.setStudentId(s.getId());
                    list.add(kk);
                    map.put(s.getClassesId(),list);
                }
                codeCache.put("stuIn",map);
            }
        }catch (Exception e){
            KqDailyStatistical k = kqOriginalDataService.inTimeSpecialCount(kqOriginalData);
            list.add(k);
            map.put(k.getClassId(),list);
            codeCache.put("stuIn",map);
        }
        return list;
    }
*/

}
