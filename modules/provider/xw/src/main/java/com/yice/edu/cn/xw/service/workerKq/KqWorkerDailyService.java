package com.yice.edu.cn.xw.service.workerKq;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.TeacherAttendance;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.workerKq.*;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.WeekUtils.DateZoneUtil;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;
import com.yice.edu.cn.xw.feignClient.TeacherFeign;
import com.yice.edu.cn.xw.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.xw.feignClient.resetDynamicTask.ResetDynamicTaskFeign;
import com.yice.edu.cn.xw.service.kq.KqOriginalDataService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqWorkerDailyService {
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private SpecialDataService specialDataService;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private CommenSettingsService commenSettingsService;
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;
    @Autowired
    private SchoolFeign schoolFeign;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private ResetDynamicTaskFeign resetDynamicTaskFeign;
    @Autowired
    private KqOriginalDataService kqOriginalDataService;

    private final static Logger logger = LoggerFactory.getLogger(KqWorkerDailyService.class);
    @CreateCache(name = Constant.Redis.YC_VERIFACE_BACK_ONLINE_SCHOOL, expire = Constant.Redis.YC_VERIFACE_BACK_ONLINE_SCHOOL_TIMEOUT,timeUnit = TimeUnit.DAYS)
    private Cache<String, Set<String>> ycVerifaceBackOnlineSchoolList;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KqWorkerDaily findKqWorkerDailyById(String id) {
        return mot.findById(id, KqWorkerDaily.class);
    }

    public void saveKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDaily.setCreateTime(DateUtil.now());
        kqWorkerDaily.setId(sequenceId.nextId());
        mot.insert(kqWorkerDaily);
    }

    public List<KqWorkerDaily> findKqWorkerDailyListByCondition(KqWorkerDaily kqWorkerDaily) {
        Document doc = new Document();
        if (kqWorkerDaily.getPersonsIdArr() != null && kqWorkerDaily.getPersonsIdArr().size() > 0) {
            doc.append("userId", new Document("$in", kqWorkerDaily.getPersonsIdArr()));
            kqWorkerDaily.setGroupIdList(null);
        }
        if (kqWorkerDaily.getGroupIdList() != null && kqWorkerDaily.getGroupIdList().size() > 0) {
            kqWorkerDaily.setGroupId(null);
            doc.append("groupId", new Document("$in", kqWorkerDaily.getGroupIdList()));
        }
        doc.putAll(MongoKit.buildMatchDocument(kqWorkerDaily));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqWorkerDaily.class)).find(doc);
        Pager pager = kqWorkerDaily.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqWorkerDaily> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqWorkerDaily.class, document)));
        List<KqWorkerDaily> list1 = new ArrayList<>();
        for (KqWorkerDaily k : list) {
            WorkerKqRules theRules = k.getTodayStandardRules();
            String kqDate = k.getKqDate();
            List<String> passedPoint = getPassedPoint(theRules, kqDate, k);
            k.setNeedShowCard(passedPoint);
            list1.add(k);
        }
        return list1;
    }

    public long findKqWorkerDailyCountByCondition(KqWorkerDaily kqWorkerDaily) {
        return mot.getCollection(mot.getCollectionName(KqWorkerDaily.class)).countDocuments(MongoKit.buildMatchDocument(kqWorkerDaily));
    }

    public KqWorkerDaily findOneKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqWorkerDaily.class)).find(MongoKit.buildMatchDocument(kqWorkerDaily));
        MongoKit.appendProjection(query, kqWorkerDaily.getPager());
        return mot.getConverter().read(KqWorkerDaily.class, query.first());
    }

    public void updateKqWorkerDaily(KqWorkerDaily kqWorkerDaily) {
        kqWorkerDaily.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqWorkerDaily.getId())), MongoKit.update(kqWorkerDaily), KqWorkerDaily.class);
    }

    public void deleteKqWorkerDaily(String id) {
        mot.remove(query(where("id").is(id)), KqWorkerDaily.class);
    }

    public void deleteKqWorkerDailyByCondition(KqWorkerDaily kqWorkerDaily) {
        mot.getCollection(mot.getCollectionName(KqWorkerDaily.class)).deleteMany(MongoKit.buildMatchDocument(kqWorkerDaily));
    }

    public void batchSaveKqWorkerDaily(List<KqWorkerDaily> kqWorkerDailys) {
        kqWorkerDailys.forEach(kqWorkerDaily -> kqWorkerDaily.setCreateTime(DateUtil.now()));
        kqWorkerDailys.forEach(kqWorkerDaily -> kqWorkerDaily.setId(sequenceId.nextId()));
        mot.insertAll(kqWorkerDailys);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    //职工考勤日统计
    public KqWorkerDaily dailyRecords(KqWorkerDaily kqWorkerDaily) {
        //当天打卡规则
        WorkerKqRules workerKqRules = kqWorkerDaily.getTodayStandardRules();
        String captureTime = kqWorkerDaily.getCaptureTime();
        Integer workerTime = DateUtil.timeToSecond(captureTime + ":00");
        //一天一组
        if (workerKqRules.getPunchNumber().equals("1")) {
            //A正常
            if (workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00") + 60) {
                if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("2")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningInStatus("2");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //A有请假  记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getMorningInStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("7")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            //A迟到
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00") + 60 && workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00")) {
                String currentMorningInTime = kqWorkerDaily.getPunchRules().getMorningIn();
                if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3") && kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") &&
                        kqWorkerDaily.getPunchRules().getMorningIn().equals("--")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningInStatus("0");
                    Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00")) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setMorningInTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //A有请假 记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getMorningInStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("7")) {
                    if (kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") && kqWorkerDaily.getPunchRules().getMorningIn().equals("--")) {
                        kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                        Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00")) / 60;
                        if (time == 0) {
                            time = time + 1;
                        }
                        kqWorkerDaily.getPunchRules().setMorningInTimeLag(time.toString());
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                        //A请假 D正常 请假 或无需打卡  记录同时给D
                        if (kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("0")) {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("0")) {
                                if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")
                                        || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                    kqWorkerDaily.getPunchRules().setDuskOutStatus("1");
                                    Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                    if (time1 == 0) {
                                        time1 = time1 + 1;
                                    }
                                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                    if (kqWorkerDaily.getTheKqLocation() != null) {
                                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                    } else {
                                        kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                    }
                                }
                            } else {
                                kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        } else {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                                kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        }

                    }
                    //B有请假
                    else if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("5") ||
                            kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("7")
                            || (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3") && kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("1"))) {
                        if (!kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") ||
                                (kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") && !kqWorkerDaily.getPunchRules().getMorningIn().equals("--"))) {
                            kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                            Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                            if (time == 0) {
                                time = time + 1;
                            }
                            kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time.toString());
                            if (kqWorkerDaily.getTheKqLocation() != null) {
                                kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                            }
                        }
                    }
                }
                //B早退
                if (!currentMorningInTime.equals("--") && kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1")) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutStatus("1");
                    Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00")) {
                //B正常
                if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")
                        || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutStatus("2");
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag("0");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //B有请假 记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("7")) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag("0");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            kqWorkerDaily.setIsAbsence("0");
            if (workerKqRules.getNoNeedCard().equals("1")) {
                if (!kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setDuskOutStatus("3");
                    }
                }
            }

            if (kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                if (!kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getMorningInStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setMorningInStatus("3");
                    }
                }

                if (!kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setDuskOutStatus("3");
                    }
                }
            }
            if (kqWorkerDaily.getIsBatch()!=null&&kqWorkerDaily.getIsBatch()) {
                return kqWorkerDaily;
            }
            kqWorkerDailyService.updateKqWorkerDaily(kqWorkerDaily);
            return kqWorkerDaily;
            //一天两组
        } else if (workerKqRules.getPunchNumber().equals("2")) {
            //A正常
            if (workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00") + 60) {
                if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("2")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningInStatus("2");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
                //A有请假 记录打卡时间
                else if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getMorningInStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("7")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00") + 60 && workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00")) {
                String currentMorningInTime = kqWorkerDaily.getPunchRules().getMorningIn();
                //A迟到
                if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3") && kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") &&
                        kqWorkerDaily.getPunchRules().getMorningIn().equals("--")) {
                    kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningInStatus("0");
                    Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00")) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setMorningInTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //A请假 记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getMorningInStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("7")) {
                    if (kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") && kqWorkerDaily.getPunchRules().getMorningIn().equals("--")) {
                        kqWorkerDaily.getPunchRules().setMorningIn(captureTime);
                        Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningIn() + ":00")) / 60;
                        if (time == 0) {
                            time = time + 1;
                        }
                        kqWorkerDaily.getPunchRules().setMorningInTimeLag(time.toString());
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setMorningInKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                        //A请假 B正常 请假 或无需打卡  记录同时给B
                        if (kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("0")) {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("0")) {
                                kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                                kqWorkerDaily.getPunchRules().setMorningOutStatus("1");
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setMorningOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            } else {
                                kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setMorningOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        } else {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                                kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setMorningOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        }
                    }
                    //B请假 记录打卡时间
                    else if (kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("5") ||
                            kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("7")
                            || (kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3") && kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("1"))) {
                        if (!kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") ||
                                (kqWorkerDaily.getPunchRules().getMorningInTimeLag().equals("0") && !kqWorkerDaily.getPunchRules().getMorningIn().equals("--"))) {
                            kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                            Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") - workerTime) / 60;
                            if (time == 0) {
                                time = time + 1;
                            }
                            kqWorkerDaily.getPunchRules().setMorningOutTimeLag(time.toString());
                            if (kqWorkerDaily.getTheKqLocation() != null) {
                                kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                            }
                        }
                    }
                }
                //B早退
                if (!currentMorningInTime.equals("--") && kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("1")) {
                    kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningOutStatus("1");
                    Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") - workerTime) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setMorningOutTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getMorningOut() + ":00") && workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getNoonIn() + ":00") + 60) {
                //用于下班无需打卡及节假日(两次打卡的情况)
                if (kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("1")) {
                    if (!kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                        //不是节假日
                        if ((kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("4") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("5") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("6") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("7")) &&
                                kqWorkerDaily.getPunchRules().getMorningOut().equals("--") &&
                                kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0")) {
                            kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                            if (kqWorkerDaily.getTheKqLocation() != null) {
                                kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                            }
                            //下一状态为请假
                            if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("4") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("5") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("6") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("7")) {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                            } else {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                                kqWorkerDaily.getPunchRules().setNoonInStatus("2");
                            }
                        } else if ((kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("4") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("5") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("6") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("7")) &&
                                !kqWorkerDaily.getPunchRules().getMorningOut().equals("--") &&
                                !kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0")) {
                            kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                            kqWorkerDaily.getPunchRules().setMorningOutTimeLag("0");
                            //下一状态为请假
                            if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("4") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("5") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("6") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("7")) {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                            } else {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                                kqWorkerDaily.getPunchRules().setNoonInStatus("2");
                            }
                        }
                    } else {
                        //节假日  打卡显示时间
                        if ((kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("4") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("5") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("6") ||
                                kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("7")) &&
                                kqWorkerDaily.getPunchRules().getMorningOut().equals("--") &&
                                kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0")) {
                            kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                            if (kqWorkerDaily.getTheKqLocation() != null) {
                                kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                            }
                            //下一状态为请假
                            if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("4") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("5") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("6") ||
                                    kqWorkerDaily.getPunchRules().getNoonInStatus().equals("7")) {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                            } else {
                                kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                                kqWorkerDaily.getPunchRules().setNoonInStatus("3");
                            }
                        }
                    }
                }
                //B正常
                if ((kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("1") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3"))
                        && (kqWorkerDaily.getPunchRules().getMorningOut().equals("--") || !kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0"))) {
                    kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                    kqWorkerDaily.getPunchRules().setMorningOutStatus("2");
                    kqWorkerDaily.getPunchRules().setMorningOutTimeLag("0");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //B请假 记录打卡时间
                } else if ((kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("7")) &&
                        kqWorkerDaily.getPunchRules().getMorningOut().equals("--")) {
                    if (!kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0") ||
                            (kqWorkerDaily.getPunchRules().getMorningOutTimeLag().equals("0") && kqWorkerDaily.getPunchRules().getMorningOut().equals("--"))) {
                        kqWorkerDaily.getPunchRules().setMorningOut(captureTime);
                        kqWorkerDaily.getPunchRules().setMorningOutTimeLag("0");
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setMorningOutKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                        //同时记录给B
                        if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("3") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("2")) {
                            kqWorkerDaily.getPunchRules().setNoonInStatus("2");
                        }
                        kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                    }//C请假 记录打卡时间
                    else if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("4") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("5") ||
                            kqWorkerDaily.getPunchRules().getNoonInStatus().equals("6") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("7")) {
                        kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setNoonInKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                    }
                }
                //C正常
                else {
                    if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("3") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                        kqWorkerDaily.getPunchRules().setNoonInStatus("2");
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setNoonInKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                    } else {
                        //B C同时请假 第一次给B C 之后给C
                        kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                    }
                }
            }
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getNoonIn() + ":00") + 60 && workerTime < DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00")) {
                String currentNoonInTime = kqWorkerDaily.getPunchRules().getNoonIn();
                //C迟到
                if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("3") && kqWorkerDaily.getPunchRules().getNoonInTimeLag().equals("0") &&
                        kqWorkerDaily.getPunchRules().getNoonIn().equals("--")) {
                    kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                    kqWorkerDaily.getPunchRules().setNoonInStatus("0");
                    Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getNoonIn() + ":00")) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setNoonInTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setNoonInKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //C请假 记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("4") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getNoonInStatus().equals("6") || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("7")) {
                    if (kqWorkerDaily.getPunchRules().getNoonInTimeLag().equals("0") && (kqWorkerDaily.getPunchRules().getNoonIn().equals("--")
                            && DateUtil.timeToSecond(captureTime + ":00") >=
                            DateUtil.timeToSecond(kqWorkerDaily.getTodayStandardRules().getPunchRules().getNoonIn() + ":00") + 60)) {
                        kqWorkerDaily.getPunchRules().setNoonIn(captureTime);
                        Integer time = (workerTime - DateUtil.timeToSecond(workerKqRules.getPunchRules().getNoonIn() + ":00")) / 60;
                        if (time == 0) {
                            time = time + 1;
                        }
                        kqWorkerDaily.getPunchRules().setNoonInTimeLag(time.toString());
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setNoonInKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                        //C请假 D正常 请假 或无需打卡  记录同时给D
                        if (kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("0")) {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("0")) {
                                if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("4") ||
                                        kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("5") ||
                                        kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("6") ||
                                        kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("7")) {
                                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                } else {
                                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                    kqWorkerDaily.getPunchRules().setDuskOutStatus("1");
                                    Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                    if (time1 == 0) {
                                        time1 = time1 + 1;
                                    }
                                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                    if (kqWorkerDaily.getTheKqLocation() != null) {
                                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                    }
                                }
                            } else {
                                kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        } else {
                            if (kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                                kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                                Integer time1 = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                                if (time1 == 0) {
                                    time1 = time1 + 1;
                                }
                                kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time1.toString());
                                if (kqWorkerDaily.getTheKqLocation() != null) {
                                    kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                                }
                            }
                        }
                        //D请假  记录打卡时间
                    } else if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("5") ||
                            kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("7")
                            || (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3") && kqWorkerDaily.getTodayStandardRules().getNoNeedCard().equals("1"))) {
                        kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                        Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                        if (time == 0) {
                            time = time + 1;
                        }
                        kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time.toString());
                        if (kqWorkerDaily.getTheKqLocation() != null) {
                            kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                        }
                    }
                }
                //D早退  当前c点时间不等于--
                if (!currentNoonInTime.equals("--") && (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1"))) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutStatus("1");
                    Integer time = (DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00") - workerTime) / 60;
                    if (time == 0) {
                        time = time + 1;
                    }
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag(time.toString());
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            //D正常
            if (workerTime >= DateUtil.timeToSecond(workerKqRules.getPunchRules().getDuskOut() + ":00")) {
                if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")
                        || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutStatus("2");
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag("0");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                    //D有请假 记录打卡时间
                } else if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("4") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("5") ||
                        kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("6") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("7")) {
                    kqWorkerDaily.getPunchRules().setDuskOut(captureTime);
                    kqWorkerDaily.getPunchRules().setDuskOutTimeLag("0");
                    if (kqWorkerDaily.getTheKqLocation() != null) {
                        kqWorkerDaily.getPunchRules().setDuskOutKqLocation(kqWorkerDaily.getTheKqLocation());
                    }
                }
            }
            kqWorkerDaily.setIsAbsence("0");
            if (workerKqRules.getNoNeedCard().equals("1")) {
                if (!kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("0") || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")
                            ) {
                        kqWorkerDaily.getPunchRules().setDuskOutStatus("3");
                    }
                }

                if (!kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("1") ||
                            kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setMorningOutStatus("3");
                    }
                }
            }

            if (kqWorkerDaily.getTodayIsHoliday().equals("1")) {
                if (!kqWorkerDaily.getPunchRules().getMorningInStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getMorningInStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getMorningInStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getMorningInStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setMorningInStatus("3");
                    }
                }

                if (!kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getMorningOutStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setMorningOutStatus("3");
                    }
                }

                if (!kqWorkerDaily.getPunchRules().getNoonInStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getNoonInStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getNoonInStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getNoonInStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setNoonInStatus("3");
                    }
                }

                if (!kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("3")) {
                    if (kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("0") ||
                            kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("1")
                            || kqWorkerDaily.getPunchRules().getDuskOutStatus().equals("2")) {
                        kqWorkerDaily.getPunchRules().setDuskOutStatus("3");
                    }
                }
            }
            if (kqWorkerDaily.getIsBatch()!=null&&kqWorkerDaily.getIsBatch()) {
                return kqWorkerDaily;
            }
            kqWorkerDailyService.updateKqWorkerDaily(kqWorkerDaily);
            return kqWorkerDaily;
        }
        return null;
    }

    public PunchRules getNewPunchRules(WorkerKqRules workerKqRules1) {
        String punchNumber = workerKqRules1.getPunchNumber();
        PunchRules punchRules = new PunchRules();
        if (punchNumber.equals("1")) {
            punchRules.setMorningIn("--");
            punchRules.setDuskOut("--");
            punchRules.setMorningInTimeLag("0");
            punchRules.setDuskOutTimeLag("0");
            punchRules.setMorningInStatus("3");
            punchRules.setDuskOutStatus("3");
        } else {
            punchRules.setMorningIn("--");
            punchRules.setMorningOut("--");
            punchRules.setNoonIn("--");
            punchRules.setDuskOut("--");
            punchRules.setMorningInTimeLag("0");
            punchRules.setMorningOutTimeLag("0");
            punchRules.setNoonInTimeLag("0");
            punchRules.setDuskOutTimeLag("0");
            punchRules.setMorningInStatus("3");
            punchRules.setMorningOutStatus("3");
            punchRules.setNoonInStatus("3");
            punchRules.setDuskOutStatus("3");
        }
        return punchRules;
    }

    /**
     * 获得需要展示的打卡点
     */
    public List<String> getPassedPoint(WorkerKqRules theRules, String kqDate, KqWorkerDaily kqWorkerDaily) {
        PunchRules punchRules = kqWorkerDaily.getPunchRules();
        String nowTime = DateUtil.now().substring(11, 16);
        String noNeedCard = theRules.getNoNeedCard();
        String punchNumber = theRules.getPunchNumber();
        PunchRules thePunchRules = theRules.getPunchRules();
        if (punchNumber.equals("1")) {
            List<String> needShowCard = new ArrayList<>();
            if (DateUtil.parse(kqDate).isBefore(DateUtil.parse(DateUtil.today()))) {
                needShowCard.add("1");
                needShowCard.add("2");
                return needShowCard;
            }
            String morningIn = thePunchRules.getMorningIn();
            String duskOut = thePunchRules.getDuskOut();
            String theMorningIn = punchRules.getMorningIn();
            String theDuskOut = punchRules.getDuskOut();
            String nextClock = morningIn;
            if (!theMorningIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningIn + ":00"))) {
                needShowCard.add("1");
            }
            if (!theDuskOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(duskOut + ":00"))) {
                needShowCard.add("2");
            }
            return needShowCard;
        } else if (punchNumber.equals("2")) {
            List<String> needShowCard = new ArrayList<>();
            if (DateUtil.parse(kqDate).isBefore(DateUtil.parse(DateUtil.today()))) {
                needShowCard.add("1");
                needShowCard.add("2");
                needShowCard.add("3");
                needShowCard.add("4");
                return needShowCard;
            }
            String morningIn = thePunchRules.getMorningIn();
            String morningOut = thePunchRules.getMorningOut();
            String noonIn = thePunchRules.getNoonIn();
            String duskOut = thePunchRules.getDuskOut();
            String theMorningIn = punchRules.getMorningIn();
            String theMorningOut = punchRules.getMorningOut();
            String theNoonIn = punchRules.getNoonIn();
            String theDuskOut = punchRules.getDuskOut();
            String nextClock = morningIn;

            if (!theMorningIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningIn + ":00"))) {
                nextClock = morningOut;
                needShowCard.add("1");
            }
            if (!theMorningOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningOut + ":00"))) {
                nextClock = noonIn;
                needShowCard.add("2");
            }
            if (!theNoonIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(noonIn + ":00"))) {
                nextClock = duskOut;
                needShowCard.add("3");
            }

            if (!theDuskOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(duskOut + ":00"))) {
                nextClock = morningIn;
                needShowCard.add("4");
            }
            return needShowCard;
        }
        return null;
    }

    /**
     * tap进入打卡页面
     */
    public KqWorkerDaily getInClock(KqWorkerDaily kqWorkerDaily) {
        String schoolId = kqWorkerDaily.getSchoolId();
        String teacherId = kqWorkerDaily.getUserId();
        //查找考勤打卡半径
        CommenSettings comm = new CommenSettings();
        comm.setSchoolId(schoolId);
        List<CommenSettings> comms = commenSettingsService.findCommenSettingsListByCondition(comm);
        if (comms != null && comms.size() == 1) {
            CommenSettings commenSettings = comms.get(0);
            String appPunchCard = commenSettings.getAppPunchCard();
            //String punchCardRange = commenSettings.getPunchCardRange();
            School schoolById = schoolFeign.findSchoolById(schoolId);
            kqWorkerDaily.setRadius(schoolById.getClockInRange() == null ? 1000 : schoolById.getClockInRange());
            kqWorkerDaily.setAppPunchCard(appPunchCard);
        } else {
            kqWorkerDaily.setAppPunchCard("0");
        }
        String nowTime = DateUtil.now().substring(11, 16);
        KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
        kqWorkerDaily1.setSchoolId(schoolId);
        kqWorkerDaily1.setUserId(teacherId);
        kqWorkerDaily1.setKqDate(DateUtil.today());
        List<KqWorkerDaily> kqWorkerDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily1);
        /// System.out.println(kqWorkerDailyList.size() + "这个人在当日考勤中");
        if (kqWorkerDailyList != null && kqWorkerDailyList.size() == 1) {
            //今天要打卡
            KqWorkerDaily isTodayKq = kqWorkerDailyList.get(0);
            String groupId = isTodayKq.getGroupId();
            //查出当天规则
            // WorkerKqRules theRules = workerKqRulesService.findWorkerKqRulesById(groupId);
            WorkerKqRules theRules = isTodayKq.getTodayStandardRules();
            String noNeedCard = theRules.getNoNeedCard();
            String punchNumber = theRules.getPunchNumber();
            PunchRules thePunchRules = theRules.getPunchRules();
            PunchRules punchRules = isTodayKq.getPunchRules();

            if (punchNumber.equals("1")) {
                String morningIn = thePunchRules.getMorningIn();
                String duskOut = thePunchRules.getDuskOut();
                String theMorningIn = punchRules.getMorningIn();
                String theDuskOut = punchRules.getDuskOut();
                String nextClock = morningIn;
                List<String> needShowCard = new ArrayList<>();
                if (!theMorningIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningIn + ":00"))) {
                    needShowCard.add("1");
                }
                if (!theDuskOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(duskOut + ":00"))) {
                    needShowCard.add("2");
                }
                isTodayKq.setNeedShowCard(needShowCard);
            } else if (punchNumber.equals("2")) {
                String morningIn = thePunchRules.getMorningIn();
                String morningOut = thePunchRules.getMorningOut();
                String noonIn = thePunchRules.getNoonIn();
                String duskOut = thePunchRules.getDuskOut();
                String theMorningIn = punchRules.getMorningIn();
                String theMorningOut = punchRules.getMorningOut();
                String theNoonIn = punchRules.getNoonIn();
                String theDuskOut = punchRules.getDuskOut();
                String nextClock = morningIn;
                List<String> needShowCard = new ArrayList<>();
                if (!theMorningIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningIn + ":00"))) {
                    nextClock = morningOut;
                    needShowCard.add("1");
                }
                if (!theMorningOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(morningOut + ":00"))) {
                    nextClock = noonIn;
                    needShowCard.add("2");
                }
                if (!theNoonIn.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(noonIn + ":00"))) {
                    nextClock = duskOut;
                    needShowCard.add("3");
                }

                if (!theDuskOut.equals("--") || DateUtil.parse(nowTime + ":00").isAfter(DateUtil.parse(duskOut + ":00"))) {
                    nextClock = morningIn;
                    needShowCard.add("4");
                }
                isTodayKq.setNeedShowCard(needShowCard);
            }
            isTodayKq.setAppPunchCard(kqWorkerDaily.getAppPunchCard());
            isTodayKq.setRadius(kqWorkerDaily.getRadius());
            //获得学校的位置
            School schoolById = schoolFeign.findSchoolById(kqWorkerDaily.getSchoolId());
            KqLocation kqLocation = new KqLocation();
            kqLocation.setLat(schoolById.getLat());
            kqLocation.setLon(schoolById.getLon());
            isTodayKq.setSchoolLocation(kqLocation);
            //检查是否今日打卡完毕
            String duskOut = isTodayKq.getPunchRules().getDuskOut();
            String duskOut1 = thePunchRules.getDuskOut();
            if (!duskOut.equals("--") && DateUtil.parse(duskOut + ":00").isAfterOrEquals(DateUtil.parse(duskOut1 + ":00"))) {
                isTodayKq.setClockInFinished("1");
            } else {
                isTodayKq.setClockInFinished("0");
            }
            isTodayKq.setTodayNeedClockIn("1");
            //今日考勤记录
            return isTodayKq;
        }
        //今日不打卡
        KqWorkerDaily notNeedClock = new KqWorkerDaily();
        notNeedClock.setAppPunchCard(kqWorkerDaily.getAppPunchCard());
        notNeedClock.setTodayNeedClockIn("0");
        return notNeedClock;
    }


    /**
     * 根据请假条和每日规则创建每日打卡数据
     */
    public void createWorkerKqDailyRecord() {
        //获得所有考勤组
        WorkerKqRules workerKqRules = new WorkerKqRules();
        List<WorkerKqRules> allKqGroup = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
        List<KqWorkerDaily> kqWorkerDailyList = new ArrayList<>();
        for (WorkerKqRules workerKqRules1 : allKqGroup) {
            CurSchoolYear curSchoolYear = new CurSchoolYear();
            curSchoolYearService.setSchoolYearTerm(curSchoolYear, workerKqRules1.getSchoolId());
            WorkerKqRules todayRules = new WorkerKqRules();
            todayRules.setPunchNumber(workerKqRules1.getPunchNumber());
            todayRules.setPunchRules(workerKqRules1.getPunchRules());
            todayRules.setNoNeedCard(workerKqRules1.getNoNeedCard());
            /**/
            Integer specialNeed = workerKqRulesService.ifSpecialNeedCard(workerKqRules1.getId()); //特殊是否打卡  特殊需要打卡 1  特殊无需打卡 0  无特殊考勤规则记录 -1
            Integer basicNeed = workerKqRulesService.ifBasicNeedCard(workerKqRules1.getId());//普通是否打卡  需要打卡 1  无需打卡 0 无普通考勤规则记录 -1
            if ((specialNeed == 1 && basicNeed == 1) || (specialNeed == -1 && basicNeed == 1)) {//  基本上班，特殊也上班 = 都上班
                /// System.out.println("该考勤组今日普通考勤打卡");
                List<Teacher> basicWorker = workerKqRules1.getBasicWorker();
                for (Teacher teacher1 : basicWorker) {
                    Teacher teacher = new Teacher();
                    teacher.setId(teacher1.getId());
                    List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                    if (teachers.size() > 0) {
                        Teacher theTeacher = teachers.get(0);
                        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                        kqWorkerDaily.setUserId(theTeacher.getId());
                        kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                        kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                        kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                        kqWorkerDaily.setGroupId(workerKqRules1.getId());
                        kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                        kqWorkerDaily.setKqDate(DateUtil.today());
                        kqWorkerDaily.setUserName(theTeacher.getName());
                        kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                        kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                        kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                        kqWorkerDaily.setIsAbsence("1");
                        kqWorkerDaily.setTodayIsHoliday("0");//不放假
                        PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                        kqWorkerDaily.setPunchRules(newPunchRules);
                        kqWorkerDaily.setTodayStandardRules(todayRules);
                        kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                        kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                        kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                        kqWorkerDailyList.add(kqWorkerDaily);
                    }
                }
            } else if ((specialNeed == -1 && basicNeed == 0) || (specialNeed == 0 && basicNeed == 0)) {//基本不上班，特殊也不上班 = 都不上班
                ///System.out.println("该考勤组今天是节假日不需要打卡，但也要生成打卡记录");
                List<Teacher> basicWorker = workerKqRules1.getBasicWorker();
                for (Teacher teacher1 : basicWorker) {
                    Teacher teacher = new Teacher();
                    teacher.setId(teacher1.getId());
                    List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                    if (teachers.size() > 0) {
                        Teacher theTeacher = teachers.get(0);
                        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                        kqWorkerDaily.setUserId(theTeacher.getId());
                        kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                        kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                        kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                        kqWorkerDaily.setGroupId(workerKqRules1.getId());
                        kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                        kqWorkerDaily.setKqDate(DateUtil.today());
                        kqWorkerDaily.setUserName(theTeacher.getName());
                        kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                        kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                        kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                        kqWorkerDaily.setIsAbsence("0");
                        kqWorkerDaily.setTodayIsHoliday("1");
                        PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                        kqWorkerDaily.setPunchRules(newPunchRules);
                        kqWorkerDaily.setTodayStandardRules(todayRules);
                        kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                        kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                        kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                        kqWorkerDailyList.add(kqWorkerDaily);
                    }
                }
            } else if (specialNeed == 0 && basicNeed == 1) {//基本上班，特殊不上班
                ///System.out.println("该考勤组今日普通考勤打卡，但有一部分人特殊无需考勤打卡");
                List<Teacher> basicWorker = workerKqRules1.getBasicWorker();//基础考勤人员
                SpecialData specialData = new SpecialData();
                specialData.setKqGroupId(workerKqRules1.getId());
                List<SpecialData> specialDatas = specialDataService.findSpecialDataListByCondition(specialData);
                List<SpecialData> todaySpecial = specialDatas.stream().filter(specialData1 -> specialData1.getEndTime() != null && specialData1.getStartTime() != null
                        && DateUtil.parse(DateUtil.today()).isAfterOrEquals(DateUtil.parse(specialData1.getStartTime()))
                        && DateUtil.parse(DateUtil.today()).isBeforeOrEquals(DateUtil.parse(specialData1.getEndTime()))
                ).collect(Collectors.toList());
                if (todaySpecial.size() > 0) {
                    List<Teacher> specialWorker = todaySpecial.get(0).getSpecialWorker();//特殊考勤人员
                    //基础中剔除特殊考勤人员
                    for (Teacher t : specialWorker) {
                        basicWorker = basicWorker.stream().filter(teacher -> !teacher.getId().equals(t.getId())).collect(Collectors.toList());
                    }
                    //生成无需打卡人员日记录
                    for (Teacher t : specialWorker) {
                        Teacher teacher = new Teacher();
                        teacher.setId(t.getId());
                        List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                        if (teachers.size() > 0) {
                            Teacher theTeacher = teachers.get(0);
                            KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                            kqWorkerDaily.setUserId(theTeacher.getId());
                            kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                            kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                            kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                            kqWorkerDaily.setGroupId(workerKqRules1.getId());
                            kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                            kqWorkerDaily.setKqDate(DateUtil.today());
                            kqWorkerDaily.setUserName(theTeacher.getName());
                            kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                            kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                            kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                            kqWorkerDaily.setIsAbsence("0");
                            kqWorkerDaily.setTodayIsHoliday("1");
                            PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                            kqWorkerDaily.setPunchRules(newPunchRules);
                            kqWorkerDaily.setTodayStandardRules(todayRules);
                            kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                            kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                            kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                            kqWorkerDailyList.add(kqWorkerDaily);
                        }
                    }
                }

                for (Teacher teacher1 : basicWorker) {//生成需要打卡人员记录
                    Teacher teacher = new Teacher();
                    teacher.setId(teacher1.getId());
                    List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                    if (teachers.size() > 0) {
                        Teacher theTeacher = teachers.get(0);
                        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                        kqWorkerDaily.setUserId(theTeacher.getId());
                        kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                        kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                        kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                        kqWorkerDaily.setGroupId(workerKqRules1.getId());
                        kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                        kqWorkerDaily.setKqDate(DateUtil.today());
                        kqWorkerDaily.setUserName(theTeacher.getName());
                        kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                        kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                        kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                        kqWorkerDaily.setIsAbsence("1");
                        kqWorkerDaily.setTodayIsHoliday("0");
                        PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                        kqWorkerDaily.setPunchRules(newPunchRules);
                        kqWorkerDaily.setTodayStandardRules(todayRules);
                        kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                        kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                        kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                        kqWorkerDailyList.add(kqWorkerDaily);
                    }
                }


            } else if (specialNeed == 1 && basicNeed == 0) {//基本不打卡，特殊要打卡
                /// System.out.println("今日是节假日，但又需要特殊考勤打卡");
                SpecialData specialData = new SpecialData();
                specialData.setKqGroupId(workerKqRules1.getId());
                List<Teacher> basicWorker = workerKqRules1.getBasicWorker();//基础考勤人员
                List<SpecialData> specialDatas = specialDataService.findSpecialDataListByCondition(specialData);
                List<SpecialData> todaySpecial = specialDatas.stream().filter(specialData1 -> specialData1.getEndTime() != null && specialData1.getStartTime() != null
                        && DateUtil.parse(DateUtil.today()).isAfterOrEquals(DateUtil.parse(specialData1.getStartTime()))
                        && DateUtil.parse(DateUtil.today()).isBeforeOrEquals(DateUtil.parse(specialData1.getEndTime()))
                ).collect(Collectors.toList());
                if (todaySpecial != null && todaySpecial.size() > 0) {
                    List<Teacher> specialWorker = todaySpecial.get(0).getSpecialWorker();
                    for (Teacher t : specialWorker) {
                        basicWorker = basicWorker.stream().filter(teacher -> !teacher.getId().equals(t.getId())).collect(Collectors.toList());
                    }
                    for (Teacher teacher1 : specialWorker) {//生成特殊要打卡人员数据
                        Teacher teacher = new Teacher();
                        teacher.setId(teacher1.getId());
                        List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                        if (teachers.size() > 0) {
                            Teacher theTeacher = teachers.get(0);
                            KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                            kqWorkerDaily.setUserId(theTeacher.getId());
                            kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                            kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                            kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                            kqWorkerDaily.setGroupId(workerKqRules1.getId());
                            kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                            kqWorkerDaily.setKqDate(DateUtil.today());
                            kqWorkerDaily.setUserName(theTeacher.getName());
                            kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                            kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                            kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                            kqWorkerDaily.setIsAbsence("1");
                            kqWorkerDaily.setTodayIsHoliday("0");
                            PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                            kqWorkerDaily.setPunchRules(newPunchRules);
                            kqWorkerDaily.setTodayStandardRules(todayRules);
                            kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                            kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                            kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                            kqWorkerDailyList.add(kqWorkerDaily);
                        }
                    }
                }
                //生成无需打卡人员日记录
                for (Teacher t : basicWorker) {
                    Teacher teacher = new Teacher();
                    teacher.setId(t.getId());
                    List<Teacher> teachers = teacherFeign.findTeacherImgListByConditionWithoutType(teacher);
                    if (teachers.size() > 0) {
                        Teacher theTeacher = teachers.get(0);
                        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                        kqWorkerDaily.setUserId(theTeacher.getId());
                        kqWorkerDaily.setSchoolId(workerKqRules1.getSchoolId());
                        kqWorkerDaily.setDepartmentId(theTeacher.getDepartmentId());
                        kqWorkerDaily.setDepartmentName(theTeacher.getDepartmentName());
                        kqWorkerDaily.setGroupId(workerKqRules1.getId());
                        kqWorkerDaily.setGroupName(workerKqRules1.getKqGroupName());
                        kqWorkerDaily.setKqDate(DateUtil.today());
                        kqWorkerDaily.setUserName(theTeacher.getName());
                        kqWorkerDaily.setUserType(theTeacher.getPersonType() + "");
                        kqWorkerDaily.setWorknumber(theTeacher.getWorkNumber());
                        kqWorkerDaily.setPunchNumber(workerKqRules1.getPunchNumber());
                        kqWorkerDaily.setIsAbsence("0");
                        kqWorkerDaily.setTodayIsHoliday("1");
                        PunchRules newPunchRules = getNewPunchRules(workerKqRules1);
                        kqWorkerDaily.setPunchRules(newPunchRules);
                        kqWorkerDaily.setTodayStandardRules(todayRules);
                        kqWorkerDaily.setSchoolYearId(curSchoolYear.getSchoolYearId());
                        kqWorkerDaily.setTerm(curSchoolYear.getTerm());
                        kqWorkerDaily.setFromTo(curSchoolYear.getFromTo());
                        kqWorkerDailyList.add(kqWorkerDaily);
                    }
                }
            } else if ((specialNeed == -1 && basicNeed == -1) || (specialNeed == 1 && basicNeed == -1) || (specialNeed == 0 && basicNeed == -1)) {
                /// System.out.println("该考勤组没有考勤规则");
            }
            workerKqRulesService.createSendTask(workerKqRules1);
        }
        batchSaveKqWorkerDaily(kqWorkerDailyList);
        //查询请假条，修改状态
        KqWorkerDaily workerDaily1 = new KqWorkerDaily();
        workerDaily1.setKqDate(DateUtil.today());
        List<KqWorkerDaily> kqWorkerDailys = findKqWorkerDailyListByCondition(workerDaily1);
        for (KqWorkerDaily workerDaily : kqWorkerDailys) {
            KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
            kqDailyStatusDetail.setApplicantId(workerDaily.getUserId());
            String kqDate = workerDaily.getKqDate();
            String dateBegin = kqDate + " 00:00:00";
            String dateEnd = kqDate + " 23:59:59";
            DateTime today = DateUtil.parse(kqDate);
            DateTime todayBegin = DateUtil.parse(dateBegin);
            DateTime todayEnd = DateUtil.parse(dateEnd);
            List<KqDailyStatusDetail> s = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
            //1包含了今天
            List<KqDailyStatusDetail> list1 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                    && DateUtil.parse(detail.getBeginTime()).isBeforeOrEquals(todayBegin) && DateUtil.parse(detail.getEndTime()).isAfterOrEquals(todayEnd)
            ).collect(Collectors.toList());
            //2被今天包含
            List<KqDailyStatusDetail> list2 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                    && DateUtil.parse(detail.getBeginTime()).isAfterOrEquals(todayBegin) && DateUtil.parse(detail.getEndTime()).isBeforeOrEquals(todayEnd)
            ).collect(Collectors.toList());
            //3包含今天的前半部分
            List<KqDailyStatusDetail> list3 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                    && DateUtil.parse(detail.getBeginTime()).isBefore(todayBegin) && DateUtil.parse(detail.getEndTime()).isAfter(todayBegin) && DateUtil.parse(detail.getEndTime()).isBefore(todayEnd)
            ).collect(Collectors.toList());
            //4包含今天的后半部分
            List<KqDailyStatusDetail> list4 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                    && DateUtil.parse(detail.getBeginTime()).isAfter(todayBegin) && DateUtil.parse(detail.getBeginTime()).isBefore(todayEnd) && DateUtil.parse(detail.getEndTime()).isAfter(todayEnd)
            ).collect(Collectors.toList());
            List<KqDailyStatusDetail> checkList = new ArrayList<>();
            checkList.addAll(list1);
            checkList.addAll(list2);
            checkList.addAll(list3);
            checkList.addAll(list4);
            checkList = checkList.stream().sorted(Comparator.comparing(KqDailyStatusDetail::getCreateTime)).collect(Collectors.toList());
            if (checkList.size() > 0) {
                for (KqDailyStatusDetail detail : checkList) {
                    kqDailyStatusDetailService.createKqWorkerDailyCheckDetail(detail, workerDaily);
                }
            }
        }

        try {
            logger.info("开始重置动态任务");
            resetDynamicTaskFeign.resetDynamicTask();
            logger.info("重置动态任务完成");
        } catch (Exception e) {
            logger.error("重置动态任务出错了", e.getMessage());
        }

    }
    /*-----------------------------------------------------------*/


    /**
     * osp查找指定时间区间考勤统计记录列表 ---=
     */
    public List<KqWorkerMonth> findWorkerMonthRecordList(KqWorkerMonth kqWorkerMonth) {
        List<KqWorkerMonth> kqWorkerMonths = new ArrayList<>();
        /// System.out.println("月考勤统计开始");
        //查找通用设置
        CommenSettings commenSettings = new CommenSettings();
        commenSettings.setSchoolId(kqWorkerMonth.getSchoolId());
        System.out.println(kqWorkerMonth.getSchoolId());
        List<CommenSettings> theCommenList = commenSettingsService.findCommenSettingsListByCondition(commenSettings);
        //统计阶梯类型 0迟到 1早退
        if (theCommenList != null && theCommenList.size() != 1) {
            /// System.out.println("学校通用配置错误");
            return null;
        }
        CommenSettings theCommen = theCommenList.get(0);
        List<CensusType> censusTypes = theCommen.getCensusTypes();
        /// System.out.println(censusTypes.size() + "共有这么多条超时配置");
        //将统计阶梯分组

        Map<String, List<CensusType>> census = censusTypes.stream().collect(Collectors.groupingBy(censusType -> censusType.getCensusType()));
        //得到迟到规则
        List<CensusType> censusTypes0 = new ArrayList<>();
        List<CensusType> censusTypes1 = new ArrayList<>();
        if (census != null && census.size() > 0) {
            censusTypes0 = census.get("0");
            censusTypes1 = census.get("1");
        }

        if (censusTypes0 != null && censusTypes0.size() != 0) {
            censusTypes0 = censusTypes0.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes0 = new ArrayList<>();
        }
        //得到早退规则

        if (censusTypes1 != null && censusTypes1.size() != 0) {
            censusTypes1 = censusTypes1.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes1 = new ArrayList<>();
        }
        //按条件查出需要统计的所有记录
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setGroupId(kqWorkerMonth.getGroupId());
        kqWorkerDaily.setPager(kqWorkerMonth.getPager());
        kqWorkerDaily.getPager().setPaging(false);
        kqWorkerDaily.setUserName(kqWorkerMonth.getUserName());
        kqWorkerDaily.setKqDate(kqWorkerMonth.getKqDate());
        kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
        List<KqWorkerDaily> workerKqDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily);
        ///System.out.println(workerKqDailyList.size() + "参与月考勤的记录数");
        //按人员id分组
        Map<String, List<KqWorkerDaily>> userDailyMap = new HashMap<>();
        if (workerKqDailyList != null && workerKqDailyList.size() > 0) {
            for (KqWorkerDaily inClock : workerKqDailyList) {
                WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
                String punchNumber = todayStandardRules.getPunchNumber();
                String noNeedCard = todayStandardRules.getNoNeedCard();
                String todayIsHoliday = inClock.getTodayIsHoliday();
                if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                    if (punchNumber.equals("1")) {
                        inClock.getPunchRules().setDuskOut("--");
                    }
                    if (punchNumber.equals("2")) {
                        inClock.getPunchRules().setMorningOut("--");
                        inClock.getPunchRules().setDuskOut("--");
                    }
                }
            }
            userDailyMap = workerKqDailyList.stream().collect(Collectors.groupingBy((kq -> kq.getUserId())));
        }
        if (userDailyMap.size() == 0) {
            return null;
        }
        //循环统计每个人的月考勤
        for (String userId : userDailyMap.keySet()) {
            List<KqWorkerDaily> userDailyList = new ArrayList<>();
            if (userDailyMap.get(userId) != null) {
                userDailyList = userDailyMap.get(userId);
            }

            int lateNum = 0;
            int earlyNum = 0;
            int missNum = 0;
            int absenceNum = 0;
            int fillMissNum = 0;
            //日期内容map
            HashMap<String, String> dateAndMsgMap = new HashMap<>();
            //迟到次数map
            HashMap<String, Integer> lateMap = new HashMap<>();
            for (int i = 0; i < censusTypes0.size(); i++) {
                if (i == 0) {
                    String key = "0-" + censusTypes0.get(0).getCensusTime();
                    lateMap.put(key, 0);
                }
                if (i == censusTypes0.size() - 1) {
                    String key = censusTypes0.get(censusTypes0.size() - 1).getCensusTime() + "-10000000";
                    lateMap.put(key, 0);
                } else {
                    String key = censusTypes0.get(i).getCensusTime() + "-" + censusTypes0.get(i + 1).getCensusTime();
                    lateMap.put(key, 0);
                }
            }
            //早退次数map
            HashMap<String, Integer> earlyMap = new HashMap<>();
            for (int i = 0; i < censusTypes1.size(); i++) {
                if (i == 0) {
                    String key = "0-" + censusTypes1.get(0).getCensusTime();
                    earlyMap.put(key, 0);
                }
                if (i == censusTypes1.size() - 1) {
                    String key = censusTypes1.get(censusTypes1.size() - 1).getCensusTime() + "-10000000";
                    earlyMap.put(key, 0);
                } else {
                    String key = censusTypes1.get(i).getCensusTime() + "-" + censusTypes1.get(i + 1).getCensusTime();
                    earlyMap.put(key, 0);
                }
            }
            //循环每天的记录
            for (KqWorkerDaily k : userDailyList) {
                WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                String outNoNeedCard = "0";
                if (todayStandardRules != null && todayStandardRules.getNoNeedCard() != null) {
                    outNoNeedCard = todayStandardRules.getNoNeedCard();
                }
                if (k.getTodayIsHoliday().equals("1")) {
                    continue;
                }
                String lateAndEarlyMsg = "";
                int todayMissNum = 0;
                if (k.getIsAbsence().equals("1")) {//旷工
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、旷工");
                    absenceNum++;
                }
                //打卡记录 0迟到 1早退 2正常 3缺卡 4 请假 5补卡 6出差 7公出 8私出
                PunchRules punchRules = k.getPunchRules();
                String morningInStatus = punchRules.getMorningInStatus();
                String duskOutStatus = punchRules.getDuskOutStatus();
                String todayIsHoliday = k.getTodayIsHoliday();

                //早上入校
                if (morningInStatus.equals("0") && todayIsHoliday.equals("0")) {
                    String morningInTimeLag = punchRules.getMorningInTimeLag();
                    int theMorningInTimeLag = Integer.parseInt(morningInTimeLag);
                    for (String timeZone : lateMap.keySet()) {
                        Integer num = lateMap.get(timeZone);
                        String[] zone = timeZone.split("-");
                        int start = Integer.parseInt(zone[0]);
                        int end = Integer.parseInt(zone[1]);
                        if (theMorningInTimeLag > start && theMorningInTimeLag <= end) {
                            num++;
                            lateMap.put(timeZone, num);
                        }
                    }
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(morningInTimeLag).concat("分钟");
                    lateNum++;
                } else if (morningInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (morningInStatus.equals("5")) {
                    fillMissNum++;
                }
                //中午入校
                if (punchRules.getNoonInStatus() != null) {
                    String noonInStatus = punchRules.getNoonInStatus();
                    if (noonInStatus.equals("0") && todayIsHoliday.equals("0")) {
                        String noonInTimeLag = punchRules.getNoonInTimeLag();
                        int theLag = Integer.parseInt(noonInTimeLag);
                        for (String timeZone : lateMap.keySet()) {
                            Integer num = lateMap.get(timeZone);
                            String[] zone = timeZone.split("-");
                            int start = Integer.parseInt(zone[0]);
                            int end = Integer.parseInt(zone[1]);
                            if (theLag > start && theLag <= end) {
                                num++;
                                lateMap.put(timeZone, num);
                            }
                        }
                        lateNum++;
                        lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(noonInTimeLag).concat("分钟");
                    } else if (noonInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                        missNum++;
                        todayMissNum++;
                    } else if (noonInStatus.equals("5")) {
                        fillMissNum++;
                    }
                }
                //早上离校
                if (punchRules.getMorningOutStatus() != null) {
                    String morningOutStatus = punchRules.getMorningOutStatus();
                    if (morningOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                        String morningOutTimeLag = punchRules.getMorningOutTimeLag();
                        int theLag = Integer.parseInt(morningOutTimeLag);
                        for (String timeZone : earlyMap.keySet()) {
                            Integer num = earlyMap.get(timeZone);
                            String[] zone = timeZone.split("-");
                            int start = Integer.parseInt(zone[0]);
                            int end = Integer.parseInt(zone[1]);
                            if (theLag > start && theLag <= end) {
                                num++;
                                earlyMap.put(timeZone, num);
                            }
                        }
                        lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(morningOutTimeLag).concat("分钟");
                        earlyNum++;
                    } else if (morningOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                        missNum++;
                        todayMissNum++;
                    } else if (morningOutStatus.equals("5")) {
                        fillMissNum++;
                    }
                }
                //傍晚离校
                if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                    String duskOutTimeLag = punchRules.getDuskOutTimeLag();
                    int theLag = Integer.parseInt(duskOutTimeLag);
                    for (String timeZone : earlyMap.keySet()) {
                        Integer num = earlyMap.get(timeZone);
                        String[] zone = timeZone.split("-");
                        int start = Integer.parseInt(zone[0]);
                        int end = Integer.parseInt(zone[1]);
                        if (theLag > start && theLag <= end) {
                            num++;
                            earlyMap.put(timeZone, num);
                        }
                    }
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(duskOutTimeLag).concat("分钟");
                    earlyNum++;
                } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (duskOutStatus.equals("5")) {
                    fillMissNum++;
                }
                //生成时间区间内每天的考勤详情
                String key = k.getKqDate();
                if (todayMissNum > 0 && k.getIsAbsence().equals("0")) {
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、缺卡").concat(String.valueOf(todayMissNum)).concat("次");
                }
                if (lateAndEarlyMsg.length() > 0) {
                    lateAndEarlyMsg = lateAndEarlyMsg.substring(1, lateAndEarlyMsg.length());
                } else {
                    lateAndEarlyMsg = "正常";
                }

                dateAndMsgMap.put(key, lateAndEarlyMsg);
            }
            //请假汇总
            //根据这个人Id和时间区间查找出所有涉及到的请假条
            KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
            if (kqWorkerMonth.getPager() != null && kqWorkerMonth.getPager().getRangeArray() != null) {
                Object[] rangeArray = kqWorkerMonth.getPager().getRangeArray();
                String beginTime = (String) rangeArray[0] + " 00:00:00";//"2019-05-21 07:00:00";
                String endTime = (String) rangeArray[1] + " 23:59:59";//"2019-05-21 18:00:00";
                kqDailyStatusDetail.setBeginTime(beginTime);
                kqDailyStatusDetail.setEndTime(endTime);
            }
            kqDailyStatusDetail.setApplicantId(userId);
            kqDailyStatusDetail.setStatus("4");
            List<KqDailyStatusDetail> kqDailyStatusDetailListByTimeZone = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(kqDailyStatusDetail);
            if (kqDailyStatusDetailListByTimeZone != null && kqDailyStatusDetailListByTimeZone.size() > 0) {
                kqDailyStatusDetailListByTimeZone = kqDailyStatusDetailListByTimeZone.stream().sorted(Comparator.comparing(KqDailyStatusDetail::getBeginTime)).collect(Collectors.toList());
            }
            String leaveMsg = "";
            for (KqDailyStatusDetail ksd : kqDailyStatusDetailListByTimeZone) {
                String statusType = ksd.getStatusType();
                /// System.out.println(statusType);
                String type = getStatusType(statusType);
                String beginDate = ksd.getBeginTime().substring(5, 16);
                String endDate = ksd.getEndTime().substring(5, 16);
                leaveMsg = leaveMsg.concat("、").concat(type).concat(beginDate).concat(" ~ ").concat(endDate);
            }
            if (leaveMsg.length() > 0) {
                leaveMsg = leaveMsg.substring(1);
            } else {
                leaveMsg = "0";
            }

            //填充内容
            KqWorkerDaily kqWorkerDaily1 = userDailyList.get(0);
            KqWorkerMonth theManMonth = new KqWorkerMonth();
            theManMonth.setLateMap(lateMap);
            theManMonth.setEarlyMap(earlyMap);
            theManMonth.setMissCardNum(missNum);
            theManMonth.setAbsenceNum(absenceNum);
            theManMonth.setFillMissNum(fillMissNum);
            theManMonth.setUserId(userId);
            theManMonth.setUserName(kqWorkerDaily1.getUserName());
            theManMonth.setWorknumber(kqWorkerDaily1.getWorknumber());
            theManMonth.setDepartmentId(kqWorkerDaily1.getDepartmentId());
            theManMonth.setDepartmentName(kqWorkerDaily1.getDepartmentName());
            theManMonth.setGroupId(kqWorkerDaily1.getGroupId());
            theManMonth.setGroupName(kqWorkerDaily1.getGroupName());
            theManMonth.setDateAndMsgMap(dateAndMsgMap);//日期map
            theManMonth.setLeaveMsg(leaveMsg);//请假汇总
            kqWorkerMonths.add(theManMonth);
        }
        /// System.out.println("月考勤统计结束");
        return kqWorkerMonths;
    }

    private String getStatusType(String statusType) {
        //4 请假(0 事假 2 病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他
        switch (statusType) {
            case "0":
                statusType = "事假";
                break;
            case "1":
                statusType = "年假";
                break;
            case "2":
                statusType = "病假";
                break;
            case "3":
                statusType = "婚假";
                break;
            case "4":
                statusType = "生育假";
                break;
            case "5":
                statusType = "陪产假";
                break;
            case "6":
                statusType = "丧假";
                break;
            case "7":
                statusType = "会议请假";
                break;
            case "8":
                statusType = "其他";
                break;
            case "9":
                statusType = "调休";
                break;
        }
        return statusType;
    }

    /**
     * tap查找本人某月或某日考勤统计记录结果 ---=
     */
    public KqWorkerMonth findWorkerMonthStatistic(KqWorkerMonth kqWorkerMonth) {
        KqWorkerMonth kqWorkerMonths = new KqWorkerMonth();
        if (kqWorkerMonth.getKqDate() == null) {
            kqWorkerMonth.setKqDate(DateUtil.today());
        }
        String[] kqDate = kqWorkerMonth.getKqDate().split("-");
        String firstDayOfMonth = kqWorkerMonth.getKqDate();
        String lastDayOfMonth = kqWorkerMonth.getKqDate();
        boolean todayIsMonthFirstDay = false;
        if (kqDate.length == 2) {
            firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String nowMonth = DateUtil.today().substring(5, 7);
            String searchMonth = lastDayOfMonth.substring(5, 7);
            if (nowMonth.equals(searchMonth)) {
                String s = DateUtil.yesterday().toString();
                lastDayOfMonth = s.substring(0, 10);
                String yesTodayMonth = lastDayOfMonth.substring(5, 7);
                if (!yesTodayMonth.equals(nowMonth)) {
                    lastDayOfMonth = firstDayOfMonth;
                    todayIsMonthFirstDay = true;
                }
            }
        }
        String[] dateZone = {firstDayOfMonth, lastDayOfMonth};
        Pager pager = new Pager();
        pager.setRangeField("kqDate");
        pager.setRangeArray(dateZone);
        pager.setPaging(false);
        kqWorkerMonth.setPager(pager);
        /// System.out.println("月考勤统计开始");
        //查找通用设置
        CommenSettings commenSettings = new CommenSettings();
        commenSettings.setSchoolId(kqWorkerMonth.getSchoolId());
        List<CommenSettings> theCommenList = commenSettingsService.findCommenSettingsListByCondition(commenSettings);
        //统计阶梯类型 0迟到 1早退
        if (theCommenList != null && theCommenList.size() != 1) {
            /// System.out.println("学校通用配置错误");
            return null;
        }
        CommenSettings theCommen = theCommenList.get(0);
        List<CensusType> censusTypes = theCommen.getCensusTypes();
        //将统计阶梯分组
        Map<String, List<CensusType>> census = censusTypes.stream().collect(Collectors.groupingBy(censusType -> censusType.getCensusType()));

        //得到迟到规则
        List<CensusType> censusTypes0 = new ArrayList<>();
        List<CensusType> censusTypes1 = new ArrayList<>();
        if (census != null && census.size() > 0) {
            censusTypes0 = census.get("0");
            censusTypes1 = census.get("1");
        }

        if (censusTypes0 != null && censusTypes0.size() != 0) {
            censusTypes0 = censusTypes0.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes0 = new ArrayList<>();
        }
        //得到早退规则

        if (censusTypes1 != null && censusTypes1.size() != 0) {
            censusTypes1 = censusTypes1.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes1 = new ArrayList<>();
        }
       /* //得到迟到规则
        List<CensusType> censusTypes0 = census.get("0");
        if (censusTypes0 != null && censusTypes0.size() != 0) {
            censusTypes0 = censusTypes0.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes0 = new ArrayList<>();
        }
        //得到早退规则
        List<CensusType> censusTypes1 = census.get("1");
        if (censusTypes1 != null && censusTypes1.size() != 0) {
            censusTypes1 = censusTypes1.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes1 = new ArrayList<>();
        }*/
        //按条件查出需要统计的所有记录
        /// System.out.println(kqWorkerMonth);
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setGroupId(kqWorkerMonth.getGroupId());
        kqWorkerDaily.setUserId(kqWorkerMonth.getUserId());
        kqWorkerDaily.setPager(kqWorkerMonth.getPager());
        kqWorkerDaily.getPager().setPaging(false);
        kqWorkerDaily.setUserName(kqWorkerMonth.getUserName());
        kqWorkerDaily.setKqDate(kqWorkerMonth.getKqDate());
        kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
        List<KqWorkerDaily> workerKqDailyList;
        if (todayIsMonthFirstDay) {
            workerKqDailyList = new ArrayList<>();
        } else {
            workerKqDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily);
        }
        //按人员id分组
        Map<String, List<KqWorkerDaily>> userDailyMap = new HashMap<>();
        if (workerKqDailyList != null && workerKqDailyList.size() > 0) {

            for (KqWorkerDaily inClock : workerKqDailyList) {
                WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
                String punchNumber = todayStandardRules.getPunchNumber();
                String noNeedCard = todayStandardRules.getNoNeedCard();
                String todayIsHoliday = inClock.getTodayIsHoliday();
                if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                    if (punchNumber.equals("1")) {
                        inClock.getPunchRules().setDuskOut("--");
                    }
                    if (punchNumber.equals("2")) {
                        inClock.getPunchRules().setMorningOut("--");
                        inClock.getPunchRules().setDuskOut("--");
                    }

                }
            }
            userDailyMap = workerKqDailyList.stream().collect(Collectors.groupingBy((kq -> kq.getUserId())));
        }
        //循环统计每个人的月考勤
        String userId = kqWorkerMonth.getUserId();
        //List<KqWorkerDaily> userDailyList = userDailyMap.get(userId);
        List<KqWorkerDaily> userDailyList = new ArrayList<>();
        if (userDailyMap.size() > 0 && userDailyMap.get(userId) != null) {
            userDailyList = userDailyMap.get(userId);
        }
        int lateNum = 0;
        int earlyNum = 0;
        int missNum = 0;
        int absenceNum = 0;
        int fillMissNum = 0;
        //各种状态详情列表
        List<HashMap<String, Object>> theManLateDetailList = new ArrayList<>();
        List<HashMap<String, Object>> theManEarlyDetailList = new ArrayList<>();
        List<HashMap<String, Object>> theManMissDetailList = new ArrayList<>();
        List<HashMap<String, Object>> theManFillMissDetailList = new ArrayList<>();
        List<HashMap<String, Object>> theManAbsenceDetailList = new ArrayList<>();
        List<KqDailyStatusDetail> theManLeaveDetailList = new ArrayList<>();
        List<KqDailyStatusDetail> theManOutDetailList = new ArrayList<>();
        List<KqDailyStatusDetail> theManOfficialOutDetailList = new ArrayList<>();
        //日期内容map
        HashMap<String, String> dateAndMsgMap = new HashMap<>();
        //迟到次数map
        HashMap<String, Integer> lateMap = new HashMap<>();
        for (int i = 0; i < censusTypes0.size(); i++) {
            if (i == 0) {
                String key = "0-" + censusTypes0.get(0).getCensusTime();
                lateMap.put(key, 0);
            }
            if (i == censusTypes0.size() - 1) {
                String key = censusTypes0.get(censusTypes0.size() - 1).getCensusTime() + "-10000000";
                lateMap.put(key, 0);
            } else {
                String key = censusTypes0.get(i).getCensusTime() + "-" + censusTypes0.get(i + 1).getCensusTime();
                lateMap.put(key, 0);
            }
        }
        //早退次数map
        HashMap<String, Integer> earlyMap = new HashMap<>();
        for (int i = 0; i < censusTypes1.size(); i++) {
            if (i == 0) {
                String key = "0-" + censusTypes1.get(0).getCensusTime();
                earlyMap.put(key, 0);
            }
            if (i == censusTypes1.size() - 1) {
                String key = censusTypes1.get(censusTypes1.size() - 1).getCensusTime() + "-10000000";
                earlyMap.put(key, 0);
            } else {
                String key = censusTypes1.get(i).getCensusTime() + "-" + censusTypes1.get(i + 1).getCensusTime();
                earlyMap.put(key, 0);
            }
        }
        //循环每天的记录0-迟到 1-早退 3-缺卡 4-请假 5补卡 6出差 7公出  (IsAbsence 1-旷工))
        for (KqWorkerDaily k : userDailyList) {

            //
            int todayLateNum = 0;
            int todayEarlyNum = 0;
            int todayMissNum = 0;
            int todayFillMissNum = 0;

            String lateAndEarlyMsg = "";
            if (k.getIsAbsence().equals("1")) {//旷工
                lateAndEarlyMsg = lateAndEarlyMsg.concat("、旷工");
                absenceNum++;
            }
            WorkerKqRules todayStandardRules = k.getTodayStandardRules();
            String outNoNeedCard = "0";
            if (todayStandardRules != null && todayStandardRules.getNoNeedCard() != null) {
                outNoNeedCard = todayStandardRules.getNoNeedCard();
            }
            //填入已经过了的时间点

            List<String> passedPoint = getPassedPoint(todayStandardRules, k.getKqDate(), k);
            k.setNeedShowCard(passedPoint);
            PunchRules punchRules = k.getPunchRules();
            String morningInStatus = punchRules.getMorningInStatus();
            String duskOutStatus = punchRules.getDuskOutStatus();
            String todayIsHoliday = k.getTodayIsHoliday();
            //早上入校
            if (morningInStatus.equals("0") && todayIsHoliday.equals("0")) {
                String morningInTimeLag = punchRules.getMorningInTimeLag();
                int theMorningInTimeLag = Integer.parseInt(morningInTimeLag);
                for (String timeZone : lateMap.keySet()) {
                    Integer num = lateMap.get(timeZone);
                    String[] zone = timeZone.split("-");
                    int start = Integer.parseInt(zone[0]);
                    int end = Integer.parseInt(zone[1]);
                    if (theMorningInTimeLag > start && theMorningInTimeLag <= end) {
                        num++;
                        lateMap.put(timeZone, num);
                    }
                }
                lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(morningInTimeLag).concat("分钟");
                lateNum++;
                todayLateNum++;
            } else if (morningInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                missNum++;
                todayMissNum++;
            } else if (morningInStatus.equals("5")) {
                fillMissNum++;
                todayFillMissNum++;
            }
            //中午入校
            if (punchRules.getNoonInStatus() != null) {
                String noonInStatus = punchRules.getNoonInStatus();
                if (noonInStatus.equals("0") && todayIsHoliday.equals("0")) {
                    String noonInTimeLag = punchRules.getNoonInTimeLag();
                    int theLag = Integer.parseInt(noonInTimeLag);
                    for (String timeZone : lateMap.keySet()) {
                        Integer num = lateMap.get(timeZone);
                        String[] zone = timeZone.split("-");
                        int start = Integer.parseInt(zone[0]);
                        int end = Integer.parseInt(zone[1]);
                        if (theLag > start && theLag <= end) {
                            num++;
                            lateMap.put(timeZone, num);
                        }
                    }
                    lateNum++;
                    todayLateNum++;
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(noonInTimeLag).concat("分钟");
                } else if (noonInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (noonInStatus.equals("5")) {
                    fillMissNum++;
                    todayFillMissNum++;
                }
            }
            //早上离校
            if (punchRules.getMorningOutStatus() != null) {
                String morningOutStatus = punchRules.getMorningOutStatus();
                if (morningOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                    String morningOutTimeLag = punchRules.getMorningOutTimeLag();
                    int theLag = Integer.parseInt(morningOutTimeLag);
                    for (String timeZone : earlyMap.keySet()) {
                        Integer num = earlyMap.get(timeZone);
                        String[] zone = timeZone.split("-");
                        int start = Integer.parseInt(zone[0]);
                        int end = Integer.parseInt(zone[1]);
                        if (theLag > start && theLag <= end) {
                            num++;
                            earlyMap.put(timeZone, num);
                        }
                    }
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(morningOutTimeLag).concat("分钟");
                    earlyNum++;
                    todayEarlyNum++;
                } else if (morningOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (morningOutStatus.equals("5")) {
                    fillMissNum++;
                    todayFillMissNum++;
                }
            }
            //傍晚离校
            if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                String duskOutTimeLag = punchRules.getDuskOutTimeLag();
                int theLag = Integer.parseInt(duskOutTimeLag);
                for (String timeZone : earlyMap.keySet()) {
                    Integer num = earlyMap.get(timeZone);
                    String[] zone = timeZone.split("-");
                    int start = Integer.parseInt(zone[0]);
                    int end = Integer.parseInt(zone[1]);
                    if (theLag > start && theLag <= end) {
                        num++;
                        earlyMap.put(timeZone, num);
                    }
                }
                lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(duskOutTimeLag).concat("分钟");
                earlyNum++;
                todayEarlyNum++;
            } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                missNum++;
                todayMissNum++;
            } else if (duskOutStatus.equals("5")) {
                fillMissNum++;
                todayFillMissNum++;
            }

            //生成时间区间内每天的考勤详情
            String key = k.getKqDate();
            if (todayMissNum > 0 && k.getIsAbsence().equals("0")) {
                lateAndEarlyMsg = lateAndEarlyMsg.concat("、缺卡").concat(String.valueOf(todayMissNum)).concat("次");
            }
            if (lateAndEarlyMsg.length() > 0) {
                lateAndEarlyMsg = lateAndEarlyMsg.substring(1, lateAndEarlyMsg.length());
            } else {
                lateAndEarlyMsg = "正常";
            }

            dateAndMsgMap.put(key, lateAndEarlyMsg);


            //今日各种状态详情----
            //迟到
            if (todayLateNum > 0) {
                HashMap<String, Object> lateDetailMap = new HashMap<>();
                lateDetailMap.put("kqDate", k.getKqDate());
                lateDetailMap.put("todayLateNum", todayLateNum);
                lateDetailMap.put("detail", k);
                theManLateDetailList.add(lateDetailMap);
            }
            //早退
            if (todayEarlyNum > 0) {
                HashMap<String, Object> earlyDetailMap = new HashMap<>();
                earlyDetailMap.put("kqDate", k.getKqDate());
                earlyDetailMap.put("todayEarlyNum", todayEarlyNum);
                earlyDetailMap.put("detail", k);
                theManEarlyDetailList.add(earlyDetailMap);
            }
            //缺卡
            if (todayMissNum > 0 && k.getIsAbsence().equals("0")) {
                HashMap<String, Object> missDetailMap = new HashMap<>();
                missDetailMap.put("kqDate", k.getKqDate());
                missDetailMap.put("todayMissNum", todayMissNum);
                missDetailMap.put("detail", k);
                theManMissDetailList.add(missDetailMap);
            }
            //补卡
            if (todayFillMissNum > 0) {
                HashMap<String, Object> fillMissDetailMap = new HashMap<>();
                fillMissDetailMap.put("kqDate", k.getKqDate());
                fillMissDetailMap.put("todayFillMissNum", todayFillMissNum);
                fillMissDetailMap.put("detail", k);
                theManFillMissDetailList.add(fillMissDetailMap);
            }
            //旷工
            if (k.getIsAbsence().equals("1")) {
                HashMap<String, Object> absenceDetailMap = new HashMap<>();
                absenceDetailMap.put("kqDate", k.getKqDate());
                absenceDetailMap.put("todayAbsenceNum", 1);
                absenceDetailMap.put("detail", k);
                theManAbsenceDetailList.add(absenceDetailMap);
            }
            //记录循环结束
        }
        //请假汇总
        //根据这个人Id和时间区间查找出所有涉及到的请假条
        KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
        if (kqWorkerMonth.getPager().getRangeArray() != null) {
            Object[] rangeArray = kqWorkerMonth.getPager().getRangeArray();
            String beginTime = (String) rangeArray[0] + " 00:00:00";//"2019-05-21 07:00:00";
            String endTime = (String) rangeArray[1] + " 23:59:59";//"2019-05-21 18:00:00";
            kqDailyStatusDetail.setBeginTime(beginTime);
            kqDailyStatusDetail.setEndTime(endTime);
        }
        kqDailyStatusDetail.setApplicantId(userId);
        /// 4-请假 5补卡 6出差 7公出
        List<KqDailyStatusDetail> detailList = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(kqDailyStatusDetail);
        Map<String, List<KqDailyStatusDetail>> detailMap = detailList.stream().collect(Collectors.groupingBy(kqDailyStatusDetail1 -> kqDailyStatusDetail1.getStatus()));
        /// System.out.println(detailMap);
        List<KqDailyStatusDetail> leaveList = null;
        String leaveMsg = "";
        if (detailMap != null && detailMap.get("4") != null) {
            leaveList = detailMap.get("4");
            leaveList = leaveList.stream().sorted(Comparator.comparing(KqDailyStatusDetail::getBeginTime)).collect(Collectors.toList());
            /// System.out.println("排序后的请假列表" + leaveList);
            for (KqDailyStatusDetail ksd : leaveList) {
                String statusType = ksd.getStatusType();
                /// System.out.println(statusType);
                String type = getStatusType(statusType);
                String beginDate = ksd.getBeginTime().substring(5, 16);
                String endDate = ksd.getEndTime().substring(5, 16);
                leaveMsg = leaveMsg.concat("、").concat(type).concat(beginDate).concat(" ~ ").concat(endDate);
            }
            theManLeaveDetailList = detailMap.get("4");
        }
        int leaveNum = 0;
        if (leaveList != null && leaveList.size() > 0) {
            leaveNum = leaveList.size();
        }

        if (leaveMsg.length() > 0) {
            leaveMsg = leaveMsg.substring(1);
        } else {
            leaveMsg = "0";
        }

        //填充基础内容
        KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
        if (userDailyList.size() > 0) {
            kqWorkerDaily1 = userDailyList.get(0);
        }
        KqWorkerMonth theManMonth = new KqWorkerMonth();
        theManMonth.setLateMap(lateMap);
        theManMonth.setEarlyMap(earlyMap);
        //theManMonth.setMissCardNum(missNum);
        theManMonth.setAbsenceNum(absenceNum);
        theManMonth.setFillMissNum(fillMissNum);
        theManMonth.setUserId(userId);
        theManMonth.setUserName(kqWorkerDaily1.getUserName());
        theManMonth.setWorknumber(kqWorkerDaily1.getWorknumber());
        theManMonth.setDepartmentId(kqWorkerDaily1.getDepartmentId());
        theManMonth.setDepartmentName(kqWorkerDaily1.getDepartmentName());
        theManMonth.setGroupId(kqWorkerDaily1.getGroupId());
        theManMonth.setGroupName(kqWorkerDaily1.getGroupName());
        theManMonth.setDateAndMsgMap(dateAndMsgMap);//日期map
        theManMonth.setLeaveMsg(leaveMsg);//请假汇总
        //月统计状态次数
        theManMonth.setLateNum(lateNum);
        theManMonth.setEarlyNum(earlyNum);
        theManMonth.setMissNum(missNum);
        theManMonth.setAbsenceNum(absenceNum);
        theManMonth.setFillMissNum(fillMissNum);
        theManMonth.setLeaveNum(leaveNum);
        int outNum = 0;
        if (detailMap != null && detailMap.get("6") != null) {
            outNum = detailMap.get("6").size();
            theManOutDetailList = detailMap.get("6");
        }
        theManMonth.setOutNum(outNum);
        int officialOutNum = 0;
        if (detailMap != null && detailMap.get("7") != null) {
            officialOutNum = detailMap.get("7").size();
            theManOfficialOutDetailList = detailMap.get("7");
        }
        theManMonth.setOfficialOutNum(officialOutNum);
        /// System.out.println("---------------------------------------------这个人统计完了");
        //状态详情列表
        theManMonth.setTheManLateDetailList(theManLateDetailList);
        theManMonth.setTheManEarlyDetailList(theManEarlyDetailList);
        theManMonth.setTheManMissDetailList(theManMissDetailList);
        theManMonth.setTheManFillMissDetailList(theManFillMissDetailList);
        theManMonth.setTheManAbsenceDetailList(theManAbsenceDetailList);
        theManMonth.setTheManLeaveDetailList(theManLeaveDetailList);
        theManMonth.setTheManOutDetailList(theManOutDetailList);
        theManMonth.setTheManOfficialOutDetailList(theManOfficialOutDetailList);
        kqWorkerMonths = theManMonth;

        /// System.out.println("月考勤统计结束");
        return kqWorkerMonths;
    }

    /**
     * tap考勤组管理员查找某些人某月考勤统计记录结果  ---=
     */
    public KqWorkerManageMonth findWorkerManageMonthStatistic(KqWorkerMonth kqWorkerMonth) {
        List<KqWorkerMonth> kqWorkerMonths = new ArrayList<>();
        if (kqWorkerMonth.getKqDate() == null) {
            kqWorkerMonth.setKqDate(DateUtil.today());
        }
        String[] kqDate = kqWorkerMonth.getKqDate().split("-");
        String firstDayOfMonth = kqWorkerMonth.getKqDate();
        String lastDayOfMonth = kqWorkerMonth.getKqDate();
        boolean todayIsMonthFirstDay = false;
        if (kqDate.length == 2) {
            firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
            String nowMonth = DateUtil.today().substring(5, 7);
            String searchMonth = lastDayOfMonth.substring(5, 7);
            if (nowMonth.equals(searchMonth)) {
                String s = DateUtil.yesterday().toString();
                lastDayOfMonth = s.substring(0, 10);
                String yesTodayMonth = lastDayOfMonth.substring(5, 7);
                if (!yesTodayMonth.equals(nowMonth)) {
                    lastDayOfMonth = firstDayOfMonth;
                    todayIsMonthFirstDay = true;
                }
            }
        }
        String[] dateZone = {firstDayOfMonth, lastDayOfMonth};
        Pager pager = new Pager();
        pager.setRangeField("kqDate");
        pager.setRangeArray(dateZone);
        pager.setPaging(false);
        kqWorkerMonth.setPager(pager);
        /// System.out.println("月考勤统计开始");
        int lateManNum = 0;
        int earlyManNum = 0;
        int missManNum = 0;
        int absenceManNum = 0;
        int fillMissManNum = 0;
        int leaveManNum = 0;
        int officialOutManNum = 0;
        int outManNum = 0;
        List<KqWorkerMonth> theMansLateDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansEarlyDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansMissDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansFillMissDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansAbsenceDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansLeaveDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansOfficialOutDetailList = new ArrayList<>();
        List<KqWorkerMonth> theMansOutDetailList = new ArrayList<>();
        //查找通用设置
        CommenSettings commenSettings = new CommenSettings();
        commenSettings.setSchoolId(kqWorkerMonth.getSchoolId());
        List<CommenSettings> theCommenList = commenSettingsService.findCommenSettingsListByCondition(commenSettings);
        //统计阶梯类型 0迟到 1早退
        if (theCommenList != null && theCommenList.size() != 1) {
            /// System.out.println("学校通用配置错误");
            return null;
        }
        CommenSettings theCommen = theCommenList.get(0);
        List<CensusType> censusTypes = theCommen.getCensusTypes();
        //将统计阶梯分组
        Map<String, List<CensusType>> census = censusTypes.stream().collect(Collectors.groupingBy(censusType -> censusType.getCensusType()));
        //得到迟到规则
        List<CensusType> censusTypes0 = new ArrayList<>();
        List<CensusType> censusTypes1 = new ArrayList<>();
        if (census != null && census.size() > 0) {
            censusTypes0 = census.get("0");
            censusTypes1 = census.get("1");
        }

        if (censusTypes0 != null && censusTypes0.size() != 0) {
            censusTypes0 = censusTypes0.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes0 = new ArrayList<>();
        }
        //得到早退规则

        if (censusTypes1 != null && censusTypes1.size() != 0) {
            censusTypes1 = censusTypes1.stream().sorted(Comparator.comparing(CensusType::getCensusTime)).collect(Collectors.toList());
        } else {
            censusTypes1 = new ArrayList<>();
        }
        //按条件查出需要统计的所有记录
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setGroupId(kqWorkerMonth.getGroupId());
        kqWorkerDaily.setUserId(kqWorkerMonth.getUserId());
        kqWorkerDaily.setPager(kqWorkerMonth.getPager());
        kqWorkerDaily.getPager().setPaging(false);
        kqWorkerDaily.setUserName(kqWorkerMonth.getUserName());
        kqWorkerDaily.setKqDate(kqWorkerMonth.getKqDate());
        kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
        kqWorkerDaily.setGroupIdList(kqWorkerMonth.getGroupIdList());
        if (kqWorkerMonth.getSchoolNotifySendObjectList() != null && kqWorkerMonth.getSchoolNotifySendObjectList().size() > 0) {
            ArrayList<SchoolNotifySendObject> teacherList = kqWorkerMonth.getSchoolNotifySendObjectList();
            List<String> tIds = teacherList.stream().map(SchoolNotifySendObject::getId).collect(Collectors.toList());
            kqWorkerDaily.setPersonsIdArr(tIds);
        }
        List<KqWorkerDaily> workerKqDailyList;
        if (todayIsMonthFirstDay) {
            workerKqDailyList = new ArrayList<>();
        } else {
            workerKqDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily);
        }
        //按人员id分组
        Map<String, List<KqWorkerDaily>> userDailyMap = new HashMap<>();
        if (workerKqDailyList != null && workerKqDailyList.size() > 0) {
            for (KqWorkerDaily inClock : workerKqDailyList) {
                WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
                String punchNumber = todayStandardRules.getPunchNumber();
                String noNeedCard = todayStandardRules.getNoNeedCard();
                String todayIsHoliday = inClock.getTodayIsHoliday();
                if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                    if (punchNumber.equals("1")) {
                        inClock.getPunchRules().setDuskOut("--");
                    }
                    if (punchNumber.equals("2")) {
                        inClock.getPunchRules().setMorningOut("--");
                        inClock.getPunchRules().setDuskOut("--");
                    }
                }
            }
            userDailyMap = workerKqDailyList.stream().collect(Collectors.groupingBy((kq -> kq.getUserId())));
        }
        /// System.out.println("共这么多人参与考勤统计" + userDailyMap.size());
        //循环统计每个人的月考勤
        Set<String> userIdSet = new HashSet<>();
        if (userDailyMap.size() > 0) {
            userIdSet = userDailyMap.keySet();
        }
        for (String userId : userIdSet) {
            List<KqWorkerDaily> userDailyList = userDailyMap.get(userId);
            int lateNum = 0;
            int earlyNum = 0;
            int missNum = 0;
            int absenceNum = 0;
            int fillMissNum = 0;
            //各种状态详情列表
            List<HashMap<String, Object>> theManLateDetailList = new ArrayList<>();
            List<HashMap<String, Object>> theManEarlyDetailList = new ArrayList<>();
            List<HashMap<String, Object>> theManMissDetailList = new ArrayList<>();
            List<HashMap<String, Object>> theManFillMissDetailList = new ArrayList<>();
            List<HashMap<String, Object>> theManAbsenceDetailList = new ArrayList<>();
            //循环每天的记录0-迟到 1-早退 3-缺卡 4-请假 5补卡 6出差 7公出  (IsAbsence 1-旷工))
            for (KqWorkerDaily k : userDailyList) {
                int todayLateNum = 0;
                int todayEarlyNum = 0;
                int todayMissNum = 0;
                int todayFillMissNum = 0;
                String todayIsHoliday = k.getTodayIsHoliday();
                String lateAndEarlyMsg = "";
                if (k.getIsAbsence().equals("1")) {//旷工
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、旷工");
                    absenceNum++;
                }
                WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                String outNoNeedCard = "0";
                if (todayStandardRules != null && todayStandardRules.getNoNeedCard() != null) {
                    outNoNeedCard = todayStandardRules.getNoNeedCard();
                }
                List<String> passedPoint = getPassedPoint(todayStandardRules, k.getKqDate(), k);
                k.setNeedShowCard(passedPoint);
                PunchRules punchRules = k.getPunchRules();
                String morningInStatus = punchRules.getMorningInStatus();
                String duskOutStatus = punchRules.getDuskOutStatus();
                //早上入校
                if (morningInStatus.equals("0") && todayIsHoliday.equals("0")) {
                    String morningInTimeLag = punchRules.getMorningInTimeLag();
                    int theMorningInTimeLag = Integer.parseInt(morningInTimeLag);
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(morningInTimeLag).concat("分钟");
                    lateNum++;
                    todayLateNum++;
                } else if (morningInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (morningInStatus.equals("5")) {
                    fillMissNum++;
                    todayFillMissNum++;
                }
                //中午入校
                if (punchRules.getNoonInStatus() != null) {
                    String noonInStatus = punchRules.getNoonInStatus();
                    if (noonInStatus.equals("0") && todayIsHoliday.equals("0")) {
                        String noonInTimeLag = punchRules.getNoonInTimeLag();
                        int theLag = Integer.parseInt(noonInTimeLag);
                        lateNum++;
                        todayLateNum++;
                        lateAndEarlyMsg = lateAndEarlyMsg.concat("、迟到").concat(noonInTimeLag).concat("分钟");
                    } else if (noonInStatus.equals("3") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                        missNum++;
                        todayMissNum++;
                    } else if (noonInStatus.equals("5")) {
                        fillMissNum++;
                        todayFillMissNum++;
                    }
                }
                //早上离校
                if (punchRules.getMorningOutStatus() != null) {
                    String morningOutStatus = punchRules.getMorningOutStatus();
                    if (morningOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                        String morningOutTimeLag = punchRules.getMorningOutTimeLag();
                        int theLag = Integer.parseInt(morningOutTimeLag);
                        lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(morningOutTimeLag).concat("分钟");
                        earlyNum++;
                        todayEarlyNum++;
                    } else if (morningOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                        missNum++;
                        todayMissNum++;
                    } else if (morningOutStatus.equals("5")) {
                        fillMissNum++;
                        todayFillMissNum++;
                    }
                }
                //傍晚离校
                if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                    String duskOutTimeLag = punchRules.getDuskOutTimeLag();
                    int theLag = Integer.parseInt(duskOutTimeLag);
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、早退").concat(duskOutTimeLag).concat("分钟");
                    earlyNum++;
                    todayEarlyNum++;
                } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0") && k.getIsAbsence().equals("0")) {
                    missNum++;
                    todayMissNum++;
                } else if (duskOutStatus.equals("5")) {
                    fillMissNum++;
                    todayFillMissNum++;
                }

                //生成时间区间内每天的考勤详情
                String key = k.getKqDate();
                if (todayMissNum > 0 && todayMissNum != (Integer.parseInt(k.getPunchNumber()) * 2)) {
                    lateAndEarlyMsg = lateAndEarlyMsg.concat("、缺卡").concat(String.valueOf(todayMissNum)).concat("次");
                }
                if (lateAndEarlyMsg.length() > 0) {
                    lateAndEarlyMsg = lateAndEarlyMsg.substring(1, lateAndEarlyMsg.length());
                } else {
                    lateAndEarlyMsg = "正常";
                }

                //今日各种状态详情----
                //迟到
                if (todayLateNum > 0) {
                    HashMap<String, Object> lateDetailMap = new HashMap<>();
                    KqWorkerDaily tk = new KqWorkerDaily();
                    tk.setUserName(k.getUserName());
                    tk.setKqDate(k.getKqDate());
                    tk.setPunchNumber(k.getPunchNumber());
                    tk.setPunchRules(k.getPunchRules());
                    tk.setTodayIsHoliday(k.getTodayIsHoliday());
                    tk.setNeedShowCard(k.getNeedShowCard());
                    tk.setTodayStandardRules(k.getTodayStandardRules());
                    lateDetailMap.put("kqDate", k.getKqDate());
                    lateDetailMap.put("todayLateNum", todayLateNum);
                    lateDetailMap.put("detail", tk);
                    theManLateDetailList.add(lateDetailMap);
                }
                //早退
                if (todayEarlyNum > 0) {
                    HashMap<String, Object> earlyDetailMap = new HashMap<>();
                    KqWorkerDaily tk = new KqWorkerDaily();
                    tk.setUserName(k.getUserName());
                    tk.setKqDate(k.getKqDate());
                    tk.setPunchNumber(k.getPunchNumber());
                    tk.setPunchRules(k.getPunchRules());
                    tk.setTodayIsHoliday(k.getTodayIsHoliday());
                    tk.setNeedShowCard(k.getNeedShowCard());
                    tk.setTodayStandardRules(k.getTodayStandardRules());
                    earlyDetailMap.put("kqDate", k.getKqDate());
                    earlyDetailMap.put("todayEarlyNum", todayEarlyNum);
                    earlyDetailMap.put("detail", tk);
                    theManEarlyDetailList.add(earlyDetailMap);
                }
                //缺卡
                if (todayMissNum > 0 && k.getIsAbsence().equals("0")) {
                    HashMap<String, Object> missDetailMap = new HashMap<>();
                    KqWorkerDaily tk = new KqWorkerDaily();
                    tk.setUserName(k.getUserName());
                    tk.setKqDate(k.getKqDate());
                    tk.setPunchNumber(k.getPunchNumber());
                    tk.setPunchRules(k.getPunchRules());
                    tk.setTodayIsHoliday(k.getTodayIsHoliday());
                    tk.setNeedShowCard(k.getNeedShowCard());
                    tk.setTodayStandardRules(k.getTodayStandardRules());
                    missDetailMap.put("kqDate", k.getKqDate());
                    missDetailMap.put("todayMissNum", todayMissNum);
                    missDetailMap.put("detail", tk);
                    theManMissDetailList.add(missDetailMap);
                }
                //补卡
                if (todayFillMissNum > 0) {
                    HashMap<String, Object> fillMissDetailMap = new HashMap<>();
                    KqWorkerDaily tk = new KqWorkerDaily();
                    tk.setUserName(k.getUserName());
                    tk.setKqDate(k.getKqDate());
                    tk.setPunchNumber(k.getPunchNumber());
                    tk.setPunchRules(k.getPunchRules());
                    tk.setTodayIsHoliday(k.getTodayIsHoliday());
                    tk.setNeedShowCard(k.getNeedShowCard());
                    tk.setTodayStandardRules(k.getTodayStandardRules());
                    fillMissDetailMap.put("kqDate", k.getKqDate());
                    fillMissDetailMap.put("todayFillMissNum", todayFillMissNum);
                    fillMissDetailMap.put("detail", tk);
                    theManFillMissDetailList.add(fillMissDetailMap);
                }
                //旷工
                if (k.getIsAbsence().equals("1")) {
                    HashMap<String, Object> absenceDetailMap = new HashMap<>();
                    KqWorkerDaily tk = new KqWorkerDaily();
                    tk.setUserName(k.getUserName());
                    tk.setKqDate(k.getKqDate());
                    tk.setPunchNumber(k.getPunchNumber());
                    tk.setPunchRules(k.getPunchRules());
                    tk.setTodayIsHoliday(k.getTodayIsHoliday());
                    tk.setNeedShowCard(k.getNeedShowCard());
                    tk.setTodayStandardRules(k.getTodayStandardRules());
                    absenceDetailMap.put("kqDate", k.getKqDate());
                    absenceDetailMap.put("todayAbsenceNum", 1);
                    absenceDetailMap.put("detail", tk);
                    theManAbsenceDetailList.add(absenceDetailMap);
                }
                //记录循环结束
            }
            //请假汇总
            //根据这个人Id和时间区间查找出所有涉及到的请假条
            KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
            if (kqWorkerMonth.getPager().getRangeArray() != null) {
                Object[] rangeArray = kqWorkerMonth.getPager().getRangeArray();
                String beginTime = (String) rangeArray[0] + " 00:00:00";//"2019-05-21 07:00:00";
                String endTime = (String) rangeArray[1] + " 23:59:59";//"2019-05-21 18:00:00";
                kqDailyStatusDetail.setBeginTime(beginTime);
                kqDailyStatusDetail.setEndTime(endTime);
            }
            kqDailyStatusDetail.setApplicantId(userId);
            //// 4-请假 5补卡 6出差 7公出
            List<KqDailyStatusDetail> detailList = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(kqDailyStatusDetail);
            Map<String, List<KqDailyStatusDetail>> detailMap = detailList.stream().collect(Collectors.groupingBy(kqDailyStatusDetail1 -> kqDailyStatusDetail1.getStatus()));
            List<KqDailyStatusDetail> leaveList = null;
            String leaveMsg = "";
            if (detailMap != null && detailMap.get("4") != null) {
                //指定时间段这个人的所有请假调
                leaveList = detailMap.get("4");
                leaveList = leaveList.stream().sorted(Comparator.comparing(KqDailyStatusDetail::getBeginTime)).collect(Collectors.toList());
                for (KqDailyStatusDetail ksd : leaveList) {
                    String statusType = ksd.getStatusType();
                    /// System.out.println(statusType);
                    String type = getStatusType(statusType);
                    String beginDate = ksd.getBeginTime().substring(5, 16);
                    String endDate = ksd.getEndTime().substring(5, 16);
                    leaveMsg = leaveMsg.concat("、").concat(type).concat(beginDate).concat(" ~ ").concat(endDate);
                }
            }
            int leaveNum = 0;
            if (leaveList != null && leaveList.size() > 0) {
                leaveNum = leaveList.size();//指定时间段这个人的请假次数
            }


            if (leaveMsg.length() > 0) {
                leaveMsg = leaveMsg.substring(1);
            } else {
                leaveMsg = "0";
            }

            //填充内容
            KqWorkerDaily kqWorkerDaily1 = new KqWorkerDaily();
            if (userDailyList.size() > 0) {
                kqWorkerDaily1 = userDailyList.get(0);
            }
            KqWorkerMonth theManMonth = new KqWorkerMonth();
            /*theManMonth.setLateMap(lateMap);
            theManMonth.setEarlyMap(earlyMap);*/
            theManMonth.setMissCardNum(missNum);
            theManMonth.setAbsenceNum(absenceNum);
            theManMonth.setFillMissNum(fillMissNum);
            theManMonth.setUserId(userId);
            theManMonth.setUserName(kqWorkerDaily1.getUserName());
            theManMonth.setWorknumber(kqWorkerDaily1.getWorknumber());
            theManMonth.setDepartmentId(kqWorkerDaily1.getDepartmentId());
            theManMonth.setDepartmentName(kqWorkerDaily1.getDepartmentName());
            theManMonth.setGroupId(kqWorkerDaily1.getGroupId());
            theManMonth.setGroupName(kqWorkerDaily1.getGroupName());
            /* theManMonth.setDateAndMsgMap(dateAndMsgMap);//日期map*/
            theManMonth.setLeaveMsg(leaveMsg);//请假汇总
            //本月状态次数
            theManMonth.setLateNum(lateNum);
            theManMonth.setEarlyNum(earlyNum);
            theManMonth.setMissNum(missNum);
            theManMonth.setAbsenceNum(absenceNum);
            theManMonth.setFillMissNum(fillMissNum);
            theManMonth.setLeaveNum(leaveNum);
            int outNum = 0;
            if (detailMap != null && detailMap.get("6") != null) {
                outNum = detailMap.get("6").size();//这个人这段时间的出差次数
            }
            theManMonth.setOutNum(outNum);
            int officialOutNum = 0;
            if (detailMap != null && detailMap.get("7") != null) {
                officialOutNum = detailMap.get("7").size();//这个人这段时间的公出次数
            }
            theManMonth.setOfficialOutNum(officialOutNum);

            //状态详情列表
            theManMonth.setTheManLateDetailList(theManLateDetailList);
            theManMonth.setTheManEarlyDetailList(theManEarlyDetailList);
            theManMonth.setTheManMissDetailList(theManMissDetailList);
            theManMonth.setTheManFillMissDetailList(theManFillMissDetailList);
            theManMonth.setTheManAbsenceDetailList(theManAbsenceDetailList);
            theManMonth.setTheManLeaveDetailList(detailMap.get("4"));
            theManMonth.setTheManOutDetailList(detailMap.get("6"));
            theManMonth.setTheManOfficialOutDetailList(detailMap.get("7"));
            kqWorkerMonths.add(theManMonth);
            //考勤迟到人次统计
            if (lateNum > 0) {
                lateManNum++;
                //将这个人加入迟到人员列表
                KqWorkerMonth late = new KqWorkerMonth();
                late.setUserName(theManMonth.getUserName());
                late.setUserId(theManMonth.getUserId());
                late.setTheManStatusNum(lateNum);
                late.setTheManLateDetailList(theManMonth.getTheManLateDetailList());
                theMansLateDetailList.add(late);
            }
            if (earlyNum > 0) {
                earlyManNum++;
                KqWorkerMonth early = new KqWorkerMonth();
                early.setUserName(theManMonth.getUserName());
                early.setUserId(theManMonth.getUserId());
                early.setTheManStatusNum(earlyNum);
                early.setTheManEarlyDetailList(theManMonth.getTheManEarlyDetailList());
                theMansEarlyDetailList.add(early);
            }
            if (missNum > 0) {
                missManNum++;
                KqWorkerMonth miss = new KqWorkerMonth();
                miss.setUserName(theManMonth.getUserName());
                miss.setUserId(theManMonth.getUserId());
                miss.setTheManStatusNum(missNum);
                miss.setTheManMissDetailList(theManMonth.getTheManMissDetailList());
                theMansMissDetailList.add(miss);
            }
            if (absenceNum > 0) {
                absenceManNum++;
                KqWorkerMonth absence = new KqWorkerMonth();
                absence.setUserName(theManMonth.getUserName());
                absence.setUserId(theManMonth.getUserId());
                absence.setTheManStatusNum(absenceNum);
                absence.setTheManAbsenceDetailList(theManMonth.getTheManAbsenceDetailList());
                theMansAbsenceDetailList.add(absence);
            }
            if (fillMissNum > 0) {
                fillMissManNum++;
                KqWorkerMonth fillMiss = new KqWorkerMonth();
                fillMiss.setUserName(theManMonth.getUserName());
                fillMiss.setUserId(theManMonth.getUserId());
                fillMiss.setTheManStatusNum(fillMissNum);
                fillMiss.setTheManFillMissDetailList(theManMonth.getTheManFillMissDetailList());
                theMansFillMissDetailList.add(fillMiss);
            }
            if (leaveNum > 0) {
                leaveManNum++;
                KqWorkerMonth leave = new KqWorkerMonth();
                leave.setUserName(theManMonth.getUserName());
                leave.setUserId(theManMonth.getUserId());
                leave.setTheManStatusNum(leaveNum);
                leave.setTheManLeaveDetailList(theManMonth.getTheManLeaveDetailList());
                theMansLeaveDetailList.add(leave);
            }
            if (officialOutNum > 0) {
                officialOutManNum++;
                KqWorkerMonth officialOut = new KqWorkerMonth();
                officialOut.setUserName(theManMonth.getUserName());
                officialOut.setUserId(theManMonth.getUserId());
                officialOut.setTheManStatusNum(officialOutNum);
                officialOut.setTheManOfficialOutDetailList(theManMonth.getTheManOfficialOutDetailList());
                theMansOfficialOutDetailList.add(officialOut);
            }
            if (outNum > 0) {
                outManNum++;
                KqWorkerMonth out = new KqWorkerMonth();
                out.setUserName(theManMonth.getUserName());
                out.setUserId(theManMonth.getUserId());
                out.setTheManStatusNum(outNum);
                out.setTheManOutDetailList(theManMonth.getTheManOutDetailList());
                theMansOutDetailList.add(out);
            }
        }
        KqWorkerManageMonth kqWorkerManageMonth = new KqWorkerManageMonth();
        kqWorkerManageMonth.setLateManNum(lateManNum);
        kqWorkerManageMonth.setEarlyManNum(earlyManNum);
        kqWorkerManageMonth.setMissManNum(missManNum);
        kqWorkerManageMonth.setAbsenceManNum(absenceManNum);
        kqWorkerManageMonth.setFillMissManNum(fillMissManNum);
        kqWorkerManageMonth.setLeaveManNum(leaveManNum);
        kqWorkerManageMonth.setOfficialOutManNum(officialOutManNum);
        kqWorkerManageMonth.setOutManNum(outManNum);
        kqWorkerManageMonth.setAllLateManDetailList(theMansLateDetailList);
        kqWorkerManageMonth.setAllEarlyManDetailList(theMansEarlyDetailList);
        kqWorkerManageMonth.setAllMissManDetailList(theMansMissDetailList);
        kqWorkerManageMonth.setAllFillMissManDetailList(theMansFillMissDetailList);
        kqWorkerManageMonth.setAllAbsenceManDetailList(theMansAbsenceDetailList);
        kqWorkerManageMonth.setAllLeaveManDetailList(theMansLeaveDetailList);
        kqWorkerManageMonth.setAllOfficialOutManDetailList(theMansOfficialOutDetailList);
        /// System.out.println(theMansOfficialOutDetailList + "公出");
        kqWorkerManageMonth.setAllOutManDetailList(theMansOutDetailList);
        /// System.out.println("月考勤统计结束");
        return kqWorkerManageMonth;
    }

    /**
     * tap考勤组管理员查找某些人今日考勤统计记录结果  ---=
     */
    public KqWorkerManageNow findWorkerManageNowStatistic(KqWorkerMonth kqWorkerMonth) {
        if (kqWorkerMonth.getPager() != null) {
            kqWorkerMonth.getPager().setPaging(false);
        }
        //查今天
        kqWorkerMonth.setKqDate(DateUtil.today());
        int morningInLateNum = 0;
        int morningInMissNum = 0;
        int noonInLateNum = 0;
        int noonInMissNum = 0;
        int morningOutEarlyNum = 0;
        int morningOutMissNum = 0;
        int daskOutEarlyNum = 0;
        int daskOutMissNum = 0;
        List<KqWorkerDaily> morningInLatePeople = new ArrayList<>();
        List<KqWorkerDaily> morningInMissPeople = new ArrayList<>();
        List<KqWorkerDaily> noonInLatePeople = new ArrayList<>();
        List<KqWorkerDaily> noonInMissPeople = new ArrayList<>();
        List<KqWorkerDaily> morningOutEarlyPeople = new ArrayList<>();
        List<KqWorkerDaily> morningOutMissPeople = new ArrayList<>();
        List<KqWorkerDaily> daskOutEarlyPeople = new ArrayList<>();
        List<KqWorkerDaily> daskOutMissPeople = new ArrayList<>();

        List<KqWorkerMonth> kqWorkerMonths = new ArrayList<>();
        /// System.out.println("月考勤统计开始");
        boolean punchNumIsTwo = false;

        //按条件查出需要统计的所有记录
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setGroupId(kqWorkerMonth.getGroupId());
        kqWorkerDaily.setUserId(kqWorkerMonth.getUserId());
        kqWorkerDaily.setPager(kqWorkerMonth.getPager());
        kqWorkerDaily.setUserName(kqWorkerMonth.getUserName());
        kqWorkerDaily.setKqDate(kqWorkerMonth.getKqDate());
        kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
        kqWorkerDaily.setGroupIdList(kqWorkerMonth.getGroupIdList());
        if (kqWorkerMonth.getSchoolNotifySendObjectList() != null && kqWorkerMonth.getSchoolNotifySendObjectList().size() > 0) {
            ArrayList<SchoolNotifySendObject> teacherList = kqWorkerMonth.getSchoolNotifySendObjectList();
            List<String> tIds = teacherList.stream().map(SchoolNotifySendObject::getId).collect(Collectors.toList());
            kqWorkerDaily.setPersonsIdArr(tIds);
        }
        List<KqWorkerDaily> workerKqDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily);
        Map<String, List<KqWorkerDaily>> userDailyMap = new HashMap<>();
        Set<String> userIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(workerKqDailyList)) {
            for (KqWorkerDaily inClock : workerKqDailyList) {
                WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
                String punchNumber = todayStandardRules.getPunchNumber();
                String noNeedCard = todayStandardRules.getNoNeedCard();
                String todayIsHoliday = inClock.getTodayIsHoliday();
                if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                    if (punchNumber.equals("1")) {
                        inClock.getPunchRules().setDuskOut("--");
                    }
                    if (punchNumber.equals("2")) {
                        inClock.getPunchRules().setMorningOut("--");
                        inClock.getPunchRules().setDuskOut("--");
                    }
                }
            }
            List<KqWorkerDaily> haspn2 = workerKqDailyList.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getPunchNumber().equals("2")).collect(Collectors.toList());
            if (haspn2 != null && haspn2.size() > 0) {
                punchNumIsTwo = true;
            }
            //按人员id分组
            userDailyMap = workerKqDailyList.stream().collect(Collectors.groupingBy((kq -> kq.getUserId())));
            userIdSet = userDailyMap.keySet();
        }

        /// System.out.println("共这么多人参与考勤统计" + userDailyMap.size());
        //循环统计每个人的月考勤
        for (String userId : userIdSet) {
            List<KqWorkerDaily> userDailyList = userDailyMap.get(userId);
            //循环每天的记录0-迟到 1-早退 3-缺卡 4-请假 5补卡 6出差 7公出  (IsAbsence 1-旷工))
            for (KqWorkerDaily k : userDailyList) {
                WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                String outNoNeedCard = "0";
                if (todayStandardRules != null && todayStandardRules.getNoNeedCard() != null) {
                    outNoNeedCard = todayStandardRules.getNoNeedCard();
                }

                List<String> passedPoint = getPassedPoint(todayStandardRules, k.getKqDate(), k);
                k.setNeedShowCard(passedPoint);
                PunchRules punchRules = k.getPunchRules();
                String morningInStatus = punchRules.getMorningInStatus();
                String duskOutStatus = punchRules.getDuskOutStatus();
                String todayIsHoliday = k.getTodayIsHoliday();
                //早上入校
                if (morningInStatus.equals("0") && todayIsHoliday.equals("0")) {
                    morningInLatePeople.add(k);
                } else if (morningInStatus.equals("3") && todayIsHoliday.equals("0")) {
                    morningInMissPeople.add(k);
                }
                //中午入校
                if (punchRules.getNoonInStatus() != null) {
                    String noonInStatus = punchRules.getNoonInStatus();
                    if (noonInStatus.equals("0") && todayIsHoliday.equals("0")) {
                        noonInLatePeople.add(k);
                    } else if (noonInStatus.equals("3") && todayIsHoliday.equals("0")) {
                        noonInMissPeople.add(k);
                    }
                }
                //早上离校
                if (punchRules.getMorningOutStatus() != null) {
                    String morningOutStatus = punchRules.getMorningOutStatus();
                    if (morningOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                        morningOutEarlyPeople.add(k);
                    } else if (morningOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                        morningOutMissPeople.add(k);
                    }
                }
                if (k.getPunchNumber().equals("2")) {
                    //傍晚离校
                    if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                        daskOutEarlyPeople.add(k);
                    } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                        daskOutMissPeople.add(k);
                    }
                } else if (k.getPunchNumber().equals("1")) {
                    if (punchNumIsTwo) {
                        //傍晚离校
                        if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                            morningOutEarlyPeople.add(k);
                        } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                            morningOutMissPeople.add(k);
                        }
                    } else {
                        //傍晚离校
                        if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                            daskOutEarlyPeople.add(k);
                        } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                            daskOutMissPeople.add(k);
                        }
                    }

                }


                //记录循环结束
            }
        }
        KqWorkerManageNow kqWorkerManageNow = new KqWorkerManageNow();
        morningInLateNum = morningInLatePeople.size();
        morningInMissNum = morningInMissPeople.size();
        daskOutEarlyNum = daskOutEarlyPeople.size();
        daskOutMissNum = daskOutMissPeople.size();
        kqWorkerManageNow.setMorningInLateNum(morningInLateNum);
        kqWorkerManageNow.setMorningInLatePeople(morningInLatePeople);
        kqWorkerManageNow.setMorningInMissNum(morningInMissNum);
        kqWorkerManageNow.setMorningInMissPeople(morningInMissPeople);
        kqWorkerManageNow.setDaskOutEarlyNum(daskOutEarlyNum);
        kqWorkerManageNow.setDaskOutEarlyPeople(daskOutEarlyPeople);
        kqWorkerManageNow.setDaskOutMissNum(daskOutMissNum);
        kqWorkerManageNow.setDaskOutMissPeople(daskOutMissPeople);
        kqWorkerManageNow.setPunchNumber("1");
        if (punchNumIsTwo) {
            noonInLateNum = noonInLatePeople.size();
            noonInMissNum = noonInMissPeople.size();
            morningOutEarlyNum = morningOutEarlyPeople.size();
            morningOutMissNum = morningOutMissPeople.size();
            kqWorkerManageNow.setNoonInLateNum(noonInLateNum);
            kqWorkerManageNow.setNoonInLatePeople(noonInLatePeople);
            kqWorkerManageNow.setNoonInMissNum(noonInMissNum);
            kqWorkerManageNow.setNoonInMissPeople(noonInMissPeople);
            kqWorkerManageNow.setMorningOutEarlyNum(morningOutEarlyNum);
            kqWorkerManageNow.setMorningOutEarlyPeople(morningOutEarlyPeople);
            kqWorkerManageNow.setMorningOutMissNum(morningOutMissNum);
            kqWorkerManageNow.setMorningOutMissPeople(morningOutMissPeople);
            kqWorkerManageNow.setPunchNumber("2");
        }
        /// System.out.println("月考勤统计结束");
        return kqWorkerManageNow;
    }

    /**
     * tap考勤组管理员查找到了入校时间未入校的人员  xxx
     */
    public List<KqWorkerDaily> findWorkerManageNotInManStatistic(KqWorkerMonth kqWorkerMonth) {
        //查到他所管辖的考勤组
        List<String> groupIds = new ArrayList<>();
        WorkerKqRules workerKqRules = new WorkerKqRules();
        workerKqRules.setSchoolId(kqWorkerMonth.getSchoolId());
        List<WorkerKqRules> list = workerKqRulesService.findWorkerKqRulesListByCondition(workerKqRules);
        if (list.size() > 0) {
            for (WorkerKqRules k : list) {
                if (k.getKqGroupManager().size() > 0 && k.getKqGroupManager() != null) {
                    List<Teacher> kqGroupManager = k.getKqGroupManager();
                    for (Teacher kk : kqGroupManager) {
                        if (kk.getId().equals(kqWorkerMonth.getUserId())) {
                            groupIds.add(k.getId());
                        }
                    }
                }
            }
        }
        //本次的考勤推送时间
        String manageSendTime = kqWorkerMonth.getManageSendTime();

        List<KqWorkerDaily> kqWorkerDailyList = new ArrayList<>();

        for (String gId : groupIds) {
            //按考勤组id找出今日的考勤记录
            KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
            kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
            String kqDate = manageSendTime.substring(0, 10);
            /// System.out.println(kqDate);
            kqWorkerDaily.setKqDate(kqDate);
            kqWorkerDaily.setGroupId(gId);
            List<KqWorkerDaily> theGroupkqWorkerDailys = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
            if (theGroupkqWorkerDailys != null && theGroupkqWorkerDailys.size() > 0) {
                WorkerKqRules todayStandardRules = theGroupkqWorkerDailys.get(0).getTodayStandardRules();
                String punchNumber = theGroupkqWorkerDailys.get(0).getTodayStandardRules().getPunchNumber();
                String sendTime = manageSendTime.substring(11, 15);
                sendTime = sendTime + "0";
                if (punchNumber.equals("1")) {
                    //发送时间接近的考勤点2019-04-23 11:40:32
                    /// System.out.println("早上的通知");
                    String todayMorningIn = todayStandardRules.getPunchRules().getMorningIn();
                    if (sendTime.equals(todayMorningIn)) {
                        //过滤出所有该考勤点缺卡的人
                        List<KqWorkerDaily> missMan = theGroupkqWorkerDailys.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday().equals("0")
                                && kqWorkerDaily1.getPunchRules().getMorningInStatus().equals("3")).collect(Collectors.toList());
                        kqWorkerDailyList.addAll(missMan);
                    }
                } else if (punchNumber.equals("2")) {
                    /// System.out.println("早上的通知");
                    String todayMorningIn = todayStandardRules.getPunchRules().getMorningIn();
                    String todayNoonIn = todayStandardRules.getPunchRules().getNoonIn();
                    if (sendTime.equals(todayMorningIn)) {
                        /// System.out.println("早上的通知");
                        //过滤出所有该考勤点缺卡的人
                        List<KqWorkerDaily> missMan = theGroupkqWorkerDailys.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday().equals("0")
                                && kqWorkerDaily1.getPunchRules().getMorningInStatus().equals("3")).collect(Collectors.toList());
                        kqWorkerDailyList.addAll(missMan);
                    } else if (sendTime.equals(todayNoonIn)) {
                        /// System.out.println("中午的通知");
                        //过滤出所有该考勤点缺卡的人
                        List<KqWorkerDaily> missMan = theGroupkqWorkerDailys.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday().equals("0")
                                && kqWorkerDaily1.getPunchRules().getNoonInStatus().equals("3")).collect(Collectors.toList());
                        kqWorkerDailyList.addAll(missMan);
                    }


                }

            }

        }
       /*if (kqWorkerDailyList.size()>0){
            for (int k=0 ;k<kqWorkerDailyList.size();k++){
                String userId = kqWorkerDailyList.get(k).getUserId();
                Teacher teacherById = teacherFeign.findTeacherById(userId);
                String imgUrl = teacherById.getImgUrl();
                kqWorkerDailyList.get(k).setUserImgUrl(imgUrl);
            }
        }*/
        List<KqWorkerDaily> kqWorkerDailyList1 = new ArrayList<>();
        if (kqWorkerDailyList.size() > 0) {
            for (int k = 0; k < kqWorkerDailyList.size(); k++) {
                String userId = kqWorkerDailyList.get(k).getUserId();
                Teacher teacherById = teacherFeign.findTeacherById(userId);
                String imgUrl = teacherById.getImgUrl();
                KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                kqWorkerDaily.setUserImgUrl(imgUrl);
                kqWorkerDaily.setUserName(kqWorkerDailyList.get(k).getUserName());
                kqWorkerDailyList1.add(kqWorkerDaily);
            }
        }
        return kqWorkerDailyList1;
    }

    /**
     * osp校领导查找全校某月或某周考勤统计记录结果 ---=
     */
    public KqWorkerManageMonth findSchoolLeaderMonthStatistic(KqWorkerMonth kqWorkerMonth) {
        Pager pager = new Pager();
        pager.setRangeField("kqDate");
        pager.setRangeArray(kqWorkerMonth.getPager().getRangeArray());
        kqWorkerMonth.setPager(pager);


        /// System.out.println("月考勤统计开始");
        int lateManNum = 0;
        int earlyManNum = 0;
        int missManNum = 0;
        int leaveManNum = 0;

        int allLateNum = 0;
        int allEarlyNum = 0;
        int allMissNum = 0;
        int allLeaveNum = 0;

        String rifenessLeaveType = "";
        List<String> leaveTypeList = new ArrayList<>();
        //按条件查出需要统计的所有记录
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setPager(kqWorkerMonth.getPager());
        kqWorkerDaily.getPager().setPaging(false);
        kqWorkerDaily.setSchoolId(kqWorkerMonth.getSchoolId());
        kqWorkerDaily.setGroupIdList(kqWorkerMonth.getGroupIdList());
        if (kqWorkerMonth.getSchoolNotifySendObjectList() != null && kqWorkerMonth.getSchoolNotifySendObjectList().size() > 0) {
            ArrayList<SchoolNotifySendObject> teacherList = kqWorkerMonth.getSchoolNotifySendObjectList();
            List<String> tIds = teacherList.stream().map(SchoolNotifySendObject::getId).collect(Collectors.toList());
            kqWorkerDaily.setPersonsIdArr(tIds);
        }
        List<KqWorkerDaily> workerKqDailyList = findKqWorkerDailyListByCondition(kqWorkerDaily);
        Map<String, List<KqWorkerDaily>> userDailyMap = new HashMap<>();
        Set<String> userIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(workerKqDailyList)) {
            for (KqWorkerDaily inClock : workerKqDailyList) {
                WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
                String punchNumber = todayStandardRules.getPunchNumber();
                String noNeedCard = todayStandardRules.getNoNeedCard();
                String todayIsHoliday = inClock.getTodayIsHoliday();
                if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                    if (punchNumber.equals("1")) {
                        inClock.getPunchRules().setDuskOut("--");
                    }
                    if (punchNumber.equals("2")) {
                        inClock.getPunchRules().setMorningOut("--");
                        inClock.getPunchRules().setDuskOut("--");
                    }
                }
            }
            userDailyMap = workerKqDailyList.stream().collect(Collectors.groupingBy((kq -> kq.getUserId())));
            userIdSet = userDailyMap.keySet();
        }

        /// System.out.println(workerKqDailyList.size() + "参与月考勤的记录数");
        //按人员id分组

        int allKqMan = userDailyMap.size();
        int allWrongMan = 0;
        //循环统计每个人的月考勤
        for (String userId : userIdSet) {
            List<KqWorkerDaily> userDailyList = userDailyMap.get(userId);
            int lateNum = 0;
            int earlyNum = 0;
            int missNum = 0;
            //循环每天的记录0-迟到 1-早退 3-缺卡 4-请假 5补卡 6出差 7公出  (IsAbsence 1-旷工))
            for (KqWorkerDaily k : userDailyList) {
                int todayLateNum = 0;
                int todayEarlyNum = 0;
                int todayMissNum = 0;
                int todayFillMissNum = 0;
                String todayIsHoliday = k.getTodayIsHoliday();
                String lateAndEarlyMsg = "";
                WorkerKqRules todayStandardRules = k.getTodayStandardRules();
                String outNoNeedCard = "0";
                if (todayStandardRules != null && todayStandardRules.getNoNeedCard() != null) {
                    outNoNeedCard = todayStandardRules.getNoNeedCard();
                }
                List<String> passedPoint = getPassedPoint(todayStandardRules, k.getKqDate(), k);
                k.setNeedShowCard(passedPoint);
                PunchRules punchRules = k.getPunchRules();
                String morningInStatus = punchRules.getMorningInStatus();
                String duskOutStatus = punchRules.getDuskOutStatus();
                //早上入校
                if (morningInStatus.equals("0") && todayIsHoliday.equals("0")) {
                    lateNum++;
                    todayLateNum++;
                    allLateNum++;
                } else if (morningInStatus.equals("3") && todayIsHoliday.equals("0")) {
                    missNum++;
                    todayMissNum++;
                    allMissNum++;
                } else if (morningInStatus.equals("5")) {
                    todayFillMissNum++;
                }
                //中午入校
                if (punchRules.getNoonInStatus() != null) {
                    String noonInStatus = punchRules.getNoonInStatus();
                    if (noonInStatus.equals("0") && todayIsHoliday.equals("0")) {
                        lateNum++;
                        todayLateNum++;
                        allLateNum++;
                    } else if (noonInStatus.equals("3") && todayIsHoliday.equals("0")) {
                        missNum++;
                        todayMissNum++;
                        allMissNum++;
                    } else if (noonInStatus.equals("5")) {
                        todayFillMissNum++;
                    }
                }
                //早上离校
                if (punchRules.getMorningOutStatus() != null) {
                    String morningOutStatus = punchRules.getMorningOutStatus();
                    if (morningOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                        earlyNum++;
                        todayEarlyNum++;
                        allEarlyNum++;
                    } else if (morningOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                        missNum++;
                        todayMissNum++;
                        allMissNum++;
                    } else if (morningOutStatus.equals("5")) {
                        todayFillMissNum++;
                    }
                }
                //傍晚离校
                if (duskOutStatus.equals("1") && todayIsHoliday.equals("0") && outNoNeedCard.equals("0")) {
                    earlyNum++;
                    todayEarlyNum++;
                    allEarlyNum++;
                } else if (duskOutStatus.equals("3") && outNoNeedCard.equals("0") && todayIsHoliday.equals("0")) {
                    missNum++;
                    todayMissNum++;
                    allMissNum++;
                } else if (duskOutStatus.equals("5")) {
                    todayFillMissNum++;
                }
                //记录循环结束
            }
            //请假汇总
            //根据这个人Id和时间区间查找出所有涉及到的请假条
            KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
            if (kqWorkerMonth.getPager().getRangeArray() != null) {
                Object[] rangeArray = kqWorkerMonth.getPager().getRangeArray();
                String beginTime = (String) rangeArray[0] + " 00:00:00";
                String endTime = (String) rangeArray[1] + " 23:59:59";
                kqDailyStatusDetail.setBeginTime(beginTime);
                kqDailyStatusDetail.setEndTime(endTime);
            }
            kqDailyStatusDetail.setApplicantId(userId);
            //// 4-请假 5补卡 6出差 7公出
            List<KqDailyStatusDetail> detailList = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(kqDailyStatusDetail);
            if (CollectionUtil.isEmpty(detailList)) {
                detailList = new ArrayList<KqDailyStatusDetail>();
            }
            Map<String, List<KqDailyStatusDetail>> detailMap = detailList.stream().collect(Collectors.groupingBy(kqDailyStatusDetail1 -> kqDailyStatusDetail1.getStatus()));
            List<KqDailyStatusDetail> leaveList = new ArrayList<>();
            if (detailMap != null && detailMap.get("4") != null) {
                leaveList = detailMap.get("4");
                allLeaveNum = allLeaveNum + leaveList.size();
                List<String> theManLeaveType = leaveList.stream().map(KqDailyStatusDetail::getStatusType).collect(Collectors.toList());
                leaveTypeList.addAll(theManLeaveType);
            }
            int leaveNum = 0;
            if (leaveList != null && leaveList.size() > 0) {
                leaveNum = leaveList.size();
            }
            //考勤迟到人次统计
            if (lateNum > 0) {
                lateManNum++;
            }
            if (earlyNum > 0) {
                earlyManNum++;
            }
            if (missNum > 0) {
                missManNum++;
            }
            if (leaveNum > 0) {
                leaveManNum++;
            }
            if (lateNum > 0 || earlyNum > 0 || missNum > 0) {
                allWrongMan++;
            }
            //一个人统计结束
        }

        Map<String, List<String>> leaveTypeMap = leaveTypeList.stream().collect(Collectors.groupingBy(String::toString));
        int max = 0;
        if (CollectionUtil.isEmpty(leaveTypeMap)) {
            leaveTypeMap = new HashMap<String, List<String>>();
        }
        for (String key : leaveTypeMap.keySet()) {
            int size = leaveTypeMap.get(key).size();
            if (size > max) {
                max = size;
                rifenessLeaveType = key;
            }
        }

        KqWorkerManageMonth kqWorkerManageMonth = new KqWorkerManageMonth();
        /*kqWorkerManageMonth.setLateManNum(lateManNum);
        kqWorkerManageMonth.setEarlyManNum(earlyManNum);
        kqWorkerManageMonth.setMissManNum(missManNum);*/
        kqWorkerManageMonth.setLeaveManNum(leaveManNum);///
        kqWorkerManageMonth.setAllLateNum(allLateNum);
        kqWorkerManageMonth.setAllEarlyNum(allEarlyNum);
        kqWorkerManageMonth.setAllMissNum(allMissNum);
        kqWorkerManageMonth.setAllLeaveNum(allLeaveNum);
        kqWorkerManageMonth.setRifenessLeaveType(rifenessLeaveType);
        kqWorkerManageMonth.setAllKqMan(allKqMan);
        kqWorkerManageMonth.setAllWrongMan(allWrongMan);
        double ps = 0;
        if (allKqMan != 0) {
            ps = allWrongMan / (double) allKqMan;
            /// System.out.println(ps);
        }
        kqWorkerManageMonth.setWrongPercentage(ps);
        /// System.out.println("月考勤统计结束");
        return kqWorkerManageMonth;
    }

    /**
     * osp数据中心教师考勤率展示 ---=
     */
    public TeacherAttendance dateCenterFindWorkerArrendance(TeacherAttendance attendance) {
        double teacherAttendance = 1;//月考勤率 【满勤天数/实际天数】* 100％
        int weekLeaveNumber = 0;//周请假次数 本周时间，列表请假条数
        double weekComparedToTheSame = 1;//周同比 【（上周请假天数 - 本周请假天数）/本周请假天数】×100％
        double monthComparedToTheSame = 1;//月同比【（上月出勤天数 - 本月出勤天数）/本月出勤天数】×100％
        List<HashMap<String, Double>> teacherWeekAttList = new ArrayList<>();//分布图
        //本月日期，包括今天
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        kqWorkerDaily.setSchoolId(attendance.getSchooleId());
        String[] kqDate = DateUtil.today().split("-");
        /*String firstDayOfMonth = DateUtil.today();
        String lastDayOfMonth = DateUtil.today();
        firstDayOfMonth = WeekDayUtil.getFirstDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
        lastDayOfMonth = WeekDayUtil.getLastDayOfMonth(Integer.parseInt(kqDate[0]), Integer.parseInt(kqDate[1]));
        String nowMonth = DateUtil.today().substring(5, 7);
        String searchMonth = lastDayOfMonth.substring(5, 7);
        if (nowMonth.equals(searchMonth)) {
            String s = DateUtil.yesterday().toString();
            lastDayOfMonth = DateUtil.today().toString().substring(0, 10);
            String yesTodayMonth = s.substring(5, 7);
            if (!yesTodayMonth.equals(nowMonth)) {
                lastDayOfMonth = firstDayOfMonth;
            }
        }*/
        String firstDayOfMonth = DateUtil.date().offset(DateField.MONTH, -1).toString().substring(0, 10);
        String lastDayOfMonth = DateUtil.today();
        ///System.out.println(firstDayOfMonth + "," + lastDayOfMonth);
        String[] dateZone = {firstDayOfMonth, lastDayOfMonth};
        Pager pager = new Pager();
        pager.setRangeField("kqDate");
        pager.setRangeArray(dateZone);
        pager.setPaging(false);
        kqWorkerDaily.setPager(pager);
        kqWorkerDaily.setTodayIsHoliday("0");
        List<KqWorkerDaily> kqWorkerDailyListByCondition = findKqWorkerDailyListByCondition(kqWorkerDaily);
        if (CollectionUtil.isEmpty(kqWorkerDailyListByCondition)) {
            kqWorkerDailyListByCondition = new ArrayList<KqWorkerDaily>();
        }
        for (KqWorkerDaily inClock : kqWorkerDailyListByCondition) {
            WorkerKqRules todayStandardRules = inClock.getTodayStandardRules();
            String punchNumber = todayStandardRules.getPunchNumber();
            String noNeedCard = todayStandardRules.getNoNeedCard();
            String todayIsHoliday = inClock.getTodayIsHoliday();
            if (noNeedCard.equals("1") && todayIsHoliday.equals("0")) {
                if (punchNumber.equals("1")) {
                    inClock.getPunchRules().setDuskOut("--");
                }
                if (punchNumber.equals("2")) {
                    inClock.getPunchRules().setMorningOut("--");
                    inClock.getPunchRules().setDuskOut("--");
                }
            }
        }

        //得到需要考勤的天数
        //List<KqWorkerDaily> theMonthKqData = kqWorkerDailyListByCondition.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday() != null && kqWorkerDaily1.getTodayIsHoliday().equals("0")).collect(Collectors.toList());
        Map<String, List<KqWorkerDaily>> kqDateMap = kqWorkerDailyListByCondition.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        Set<String> allKqDate = kqDateMap.keySet();
        int allKqDateSize = allKqDate.size();
        //得到满勤的天数

        List<KqWorkerDaily> absentData = kqWorkerDailyListByCondition.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday() != null && kqWorkerDaily1.getTodayIsHoliday().equals("0") && kqWorkerDaily1.getIsAbsence() != null && kqWorkerDaily1.getIsAbsence().equals("1")).collect(Collectors.toList());
        Map<String, List<KqWorkerDaily>> kqAbsentDateMap = absentData.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        Set<String> allAbsentKqDate = kqAbsentDateMap.keySet();
        int thisMonthAllNoAbsentKqDateSize = 0;
        if (allKqDateSize > 0) {
            thisMonthAllNoAbsentKqDateSize = allKqDateSize - allAbsentKqDate.size();//满勤天数 = 全部天数 - 缺勤天数
            System.out.println(thisMonthAllNoAbsentKqDateSize + "," + allKqDateSize + "," + allAbsentKqDate.size());
            teacherAttendance = thisMonthAllNoAbsentKqDateSize / (double) allKqDateSize;//【满勤天数/实际天数】* 100％
        }
        attendance.setTeacherAttendance(teacherAttendance);
        //本周请假次数
        //获得本周日期
        KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
       /* Calendar nowTime = Calendar.getInstance();
        Calendar nowTime1 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beginOfWeek = DateUtil.beginOfWeek(nowTime);
        Calendar endOfWeek = DateUtil.endOfWeek(nowTime1);
        String thisWeekBeginTime = df.format(beginOfWeek.getTime());
        String thisWeekEndTime = df.format(endOfWeek.getTime());*/
        String thisWeekBeginTime = DateUtil.tomorrow().offset(DateField.WEEK_OF_YEAR, -1).toString().substring(0, 10) + " 00:00:00";
        String thisWeekEndTime = DateUtil.today() + " 23:59:59";
        ///System.out.println("查询请假条");
        ///System.out.println(thisWeekBeginTime);
        /// System.out.println(thisWeekEndTime);
        kqDailyStatusDetail.setBeginTime(thisWeekBeginTime);
        kqDailyStatusDetail.setEndTime(thisWeekEndTime);
        kqDailyStatusDetail.setSchoolId(attendance.getSchooleId());
        kqDailyStatusDetail.setStatus("4");
        List<KqDailyStatusDetail> thisWeekDetailList = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(kqDailyStatusDetail);
        if (CollectionUtil.isEmpty(thisWeekDetailList)) {
            thisWeekDetailList = new ArrayList<KqDailyStatusDetail>();
        }
        //请假次数
        if (thisWeekDetailList != null && thisWeekDetailList.size() > 0) {
            weekLeaveNumber = thisWeekDetailList.size();
        }
        attendance.setWeekLeaveNumber(weekLeaveNumber);
        KqDailyStatusDetail lastWeekLeave = new KqDailyStatusDetail();
       /* DateTime todayLastWeek = DateUtil.lastWeek();
        DateTime lsBeginOfWeek = DateUtil.beginOfWeek(todayLastWeek);
        DateTime lsEndOfWeek = DateUtil.endOfWeek(todayLastWeek);
        String lastWeekBegin = lsBeginOfWeek.toString();
        String lastWeekEnd = lsEndOfWeek.toString();*/
        String lastWeekBegin = DateUtil.tomorrow().offset(DateField.WEEK_OF_YEAR, -2).toString().substring(0, 10) + " 00:00:00";
        String lastWeekEnd = DateUtil.date().offset(DateField.WEEK_OF_YEAR, -1).toString().substring(0, 10) + " 23:59:59";
        //System.out.println(lastWeekBegin + "," + lastWeekEnd);
        lastWeekLeave.setBeginTime(lastWeekBegin);
        lastWeekLeave.setEndTime(lastWeekEnd);
        lastWeekLeave.setSchoolId(attendance.getSchooleId());
        lastWeekLeave.setStatus("4");
        List<KqDailyStatusDetail> lastWeekLeaveDetailList = kqDailyStatusDetailService.findKqDailyStatusDetailListByTimeZone(lastWeekLeave);
        if (CollectionUtil.isEmpty(lastWeekLeaveDetailList)) {
            lastWeekLeaveDetailList = new ArrayList<KqDailyStatusDetail>();
        }
        DateZoneUtil dateCal = new DateZoneUtil();
        dateCal.countDays(DateUtil.parse(lastWeekBegin), DateUtil.parse(lastWeekEnd));
        List<Date> lastWeeklist = dateCal.getDayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //上周请假天数
        int lastWeekLeaveDayNum = 0;
        for (Date date : lastWeeklist) {
            String format = dateFormat.format(date);
            List<KqDailyStatusDetail> collect = lastWeekLeaveDetailList.stream().filter(kqDailyStatusDetail1 -> kqDailyStatusDetail1.getBeginTime().substring(0, 10).equals(format) || kqDailyStatusDetail1.getBeginTime().substring(0, 10).equals(format)).collect(Collectors.toList());
            if (collect.size() > 0) {
                lastWeekLeaveDayNum++;
            }
        }
        //本周请假天数
        DateZoneUtil dateCal2 = new DateZoneUtil();
        dateCal2.countDays(DateUtil.parseDate(thisWeekBeginTime), DateUtil.parseDate(thisWeekEndTime));
        List<Date> thisWeeklist = dateCal2.getDayList();
        int thisWeekLeaveDayNum = 0;
        for (Date date : thisWeeklist) {
            String format = dateFormat.format(date);
            List<KqDailyStatusDetail> collect = thisWeekDetailList.stream().filter(kqDailyStatusDetail1 -> kqDailyStatusDetail1.getBeginTime().substring(0, 10).equals(format) || kqDailyStatusDetail1.getBeginTime().substring(0, 10).equals(format)).collect(Collectors.toList());
            if (collect.size() > 0) {
                thisWeekLeaveDayNum++;
            }
        }
        //周同比 【（本周请假天数 -上周请假天数 ）/上周请假天数】×100％
        attendance.setWeekComparedIsRise(true);
        if (lastWeekLeaveDayNum > 0) {
            int weekLeaveDiff = thisWeekLeaveDayNum - lastWeekLeaveDayNum;
            if (weekLeaveDiff < 0) {
                attendance.setWeekComparedIsRise(false);
                weekLeaveDiff = weekLeaveDiff * -1;
            }
            weekComparedToTheSame = weekLeaveDiff / (double) lastWeekLeaveDayNum;
        }
        attendance.setWeekComparedToTheSame(weekComparedToTheSame);


        //月同比【（本月满勤天数 - 上月满勤天数）/上月满勤天数】×100％
        //上月，当前日期往前2个月


        KqWorkerDaily lastMonthkqWorkerDaily = new KqWorkerDaily();
       /* lastMonthkqWorkerDaily.setSchoolId(attendance.getSchooleId());
        String firstDayOfLastMonth = DateUtil.beginOfMonth(DateUtil.lastMonth()).toString();
        String lastDayOfLastMonth = DateUtil.endOfMonth(DateUtil.lastMonth()).toString();
        System.out.println(firstDayOfLastMonth + "," + lastDayOfLastMonth);*/
        String firstDayOfLastMonth = DateUtil.yesterday().offset(DateField.MONTH, -2).toString().substring(0, 10);
        String lastDayOfLastMonth = DateUtil.yesterday().offset(DateField.MONTH, -1).toString().substring(0, 10);
        ///System.out.println(firstDayOfLastMonth + "," + lastDayOfLastMonth);
        String[] lastMonthDateZone = {firstDayOfLastMonth, lastDayOfLastMonth};
        Pager pager2 = new Pager();
        pager2.setRangeField("kqDate");
        pager2.setRangeArray(lastMonthDateZone);
        pager2.setPaging(false);
        lastMonthkqWorkerDaily.setPager(pager2);
        lastMonthkqWorkerDaily.setTodayIsHoliday("0");
        List<KqWorkerDaily> lastMonthKqWorkerDailyListByCondition = findKqWorkerDailyListByCondition(lastMonthkqWorkerDaily);
        if (CollectionUtil.isEmpty(lastMonthKqWorkerDailyListByCondition)) {
            lastMonthKqWorkerDailyListByCondition = new ArrayList<KqWorkerDaily>();
        }
        //得到需要考勤的天数
        //List<KqWorkerDaily> lastMonthKqData = lastWeekKqWorkerDailyListByCondition.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday() != null && kqWorkerDaily1.getTodayIsHoliday().equals("0")).collect(Collectors.toList());
        Map<String, List<KqWorkerDaily>> lastKqDateMap = lastMonthKqWorkerDailyListByCondition.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        Set<String> lastMonthAllKqDate = lastKqDateMap.keySet();
        int lastMonthAllKqDateSize = lastMonthAllKqDate.size();
        //得到满勤的天数
        List<KqWorkerDaily> lastMonthAbsentData = lastMonthKqWorkerDailyListByCondition.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getTodayIsHoliday() != null && kqWorkerDaily1.getTodayIsHoliday().equals("0") && kqWorkerDaily1.getIsAbsence() != null && kqWorkerDaily1.getIsAbsence().equals("1")).collect(Collectors.toList());
        Map<String, List<KqWorkerDaily>> lastMonthKqAbsentDateMap = lastMonthAbsentData.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        Set<String> lastMonthAllAbsentKqDate = lastMonthKqAbsentDateMap.keySet();
        int lastMonthAllNoAbsentKqDateSize = 0;
        if (lastMonthAllKqDateSize > 0) {
            lastMonthAllNoAbsentKqDateSize = lastMonthAllKqDateSize - lastMonthAllAbsentKqDate.size();//满勤天数 = 全部天数 - 缺勤天数
        }
        attendance.setMonthComparedIsRise(true);
        ///System.out.println(lastMonthAllNoAbsentKqDateSize + "月同比," + thisMonthAllNoAbsentKqDateSize);
        if (lastMonthAllNoAbsentKqDateSize > 0) {
            //月同比【（本月满勤天数 - 上月满勤天数）/上月满勤天数】×100％
            int monthNoAbsentDiff = thisMonthAllNoAbsentKqDateSize - lastMonthAllNoAbsentKqDateSize;
            if (monthNoAbsentDiff < 0) {
                attendance.setMonthComparedIsRise(false);
                monthNoAbsentDiff = monthNoAbsentDiff * -1;
            }
            monthComparedToTheSame = monthNoAbsentDiff / (double) lastMonthAllNoAbsentKqDateSize;
        }
        attendance.setMonthComparedToTheSame(monthComparedToTheSame);
        //分布图 横轴本周日期，纵轴每日【出勤非旷工教师人数/教师总考勤人数】×100％
        KqWorkerDaily thisWeekkqWorkerDaily = new KqWorkerDaily();
        thisWeekkqWorkerDaily.setSchoolId(attendance.getSchooleId());
        //获取最近七天时间区间
        String todayLastWeekDay = DateUtil.lastWeek().toString().substring(0, 10);
        String[] thisWeekDateZone = {todayLastWeekDay, DateUtil.today()};
        Pager pager3 = new Pager();
        pager3.setRangeField("kqDate");
        pager3.setRangeArray(thisWeekDateZone);
        pager3.setPaging(false);
        thisWeekkqWorkerDaily.setPager(pager3);
        thisWeekkqWorkerDaily.setTodayIsHoliday("0");
        List<KqWorkerDaily> thisWeekKqWorkerDailyListByCondition = findKqWorkerDailyListByCondition(thisWeekkqWorkerDaily);
        if (CollectionUtil.isEmpty(thisWeekKqWorkerDailyListByCondition)) {
            thisWeekKqWorkerDailyListByCondition = new ArrayList<KqWorkerDaily>();
        }
        Map<String, List<KqWorkerDaily>> thisWeekKqMapByDate = thisWeekKqWorkerDailyListByCondition.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        if (thisWeekKqMapByDate != null && thisWeekKqMapByDate.size() > 0) {
            for (Date date : thisWeeklist) {
                String thiskqDate = dateFormat.format(date);
                List<KqWorkerDaily> kqWorkerDailyList = thisWeekKqMapByDate.get(thiskqDate);
                double todayNoAbsentRate = 1;
                if (kqWorkerDailyList != null) {
                    int todayAllManNum = kqWorkerDailyList.size();
                    List<KqWorkerDaily> todayNoAbsentManList = kqWorkerDailyList.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getIsAbsence() != null && kqWorkerDaily1.getIsAbsence().equals("0")).collect(Collectors.toList());
                    int todayNoAbsentManNum = todayNoAbsentManList.size();
                    if (todayAllManNum > 0) {
                        todayNoAbsentRate = todayNoAbsentManNum / (double) todayAllManNum;
                    }
                }
                HashMap<String, Double> todayData = new HashMap<>();
                todayData.put(thiskqDate, todayNoAbsentRate);
                teacherWeekAttList.add(todayData);
            }
        }
        attendance.setTeacherWeekAttList(teacherWeekAttList);
        return attendance;
    }

    /**
     * osp数据中心教师考勤今日已经进行打卡操作的比率 xxx
     */
    public Double findTodayCheckRate(String schoolId) {

        KqWorkerDaily todayKqWorkerDaily = new KqWorkerDaily();
        todayKqWorkerDaily.setSchoolId(schoolId);
        todayKqWorkerDaily.setTodayIsHoliday("0");
        todayKqWorkerDaily.setKqDate(DateUtil.today());
        List<KqWorkerDaily> todayKqWorkerDailyListByCondition = findKqWorkerDailyListByCondition(todayKqWorkerDaily);
        if (CollectionUtil.isEmpty(todayKqWorkerDailyListByCondition)) {
            todayKqWorkerDailyListByCondition = new ArrayList<KqWorkerDaily>();
        }
        int todayAllManNum = todayKqWorkerDailyListByCondition.size();
        List<KqWorkerDaily> todayNoAbsentList = todayKqWorkerDailyListByCondition.stream().filter(kqWorkerDaily -> kqWorkerDaily.getIsAbsence() != null && kqWorkerDaily.getIsAbsence().equals("0")).collect(Collectors.toList());
        int todayNoAbsentManNum = todayNoAbsentList.size();
        double rate = 1;
        if (todayAllManNum > 0) {
            //【已打卡人数/教师总数】* 100％
            rate = todayNoAbsentManNum / (double) todayAllManNum;
        }
        //System.out.println("今日的打卡率是："+rate);
        return rate;
    }

    /**
     * osp数据中心教师考勤分布图 横轴本周日期，纵轴每日【出勤非旷工教师人数/教师总考勤人数】×100％ xxx
     */
    public TeacherAttendance findNearSeverDaysWorkerKqHistogram(TeacherAttendance attendance) {
        List<HashMap<String, Double>> teacherWeekAttList = new ArrayList<>();//分布图
        String thisWeekBeginTime = DateUtil.tomorrow().offset(DateField.WEEK_OF_YEAR, -1).toString().substring(0, 10) + " 00:00:00";
        String thisWeekEndTime = DateUtil.today() + " 23:59:59";
        DateZoneUtil dateCal2 = new DateZoneUtil();
        dateCal2.countDays(DateUtil.parseDate(thisWeekBeginTime), DateUtil.parseDate(thisWeekEndTime));
        List<Date> thisWeeklist = dateCal2.getDayList();
        //分布图 横轴本周日期，纵轴每日【出勤非旷工教师人数/教师总考勤人数】×100％
        KqWorkerDaily thisWeekkqWorkerDaily = new KqWorkerDaily();
        thisWeekkqWorkerDaily.setSchoolId(attendance.getSchooleId());
        //获取最近七天时间区间
        String todayLastWeekDay = DateUtil.lastWeek().toString().substring(0, 10);
        String[] thisWeekDateZone = {todayLastWeekDay, DateUtil.today()};
        Pager pager3 = new Pager();
        pager3.setRangeField("kqDate");
        pager3.setRangeArray(thisWeekDateZone);
        pager3.setPaging(false);
        thisWeekkqWorkerDaily.setPager(pager3);
        thisWeekkqWorkerDaily.setTodayIsHoliday("0");
        List<KqWorkerDaily> thisWeekKqWorkerDailyListByCondition = findKqWorkerDailyListByCondition(thisWeekkqWorkerDaily);
        if (CollectionUtil.isEmpty(thisWeekKqWorkerDailyListByCondition)) {
            thisWeekKqWorkerDailyListByCondition = new ArrayList<KqWorkerDaily>();
        }
        Map<String, List<KqWorkerDaily>> thisWeekKqMapByDate = thisWeekKqWorkerDailyListByCondition.stream().collect(Collectors.groupingBy(kqWorkerDaily1 -> kqWorkerDaily1.getKqDate()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (thisWeekKqMapByDate != null && thisWeekKqMapByDate.size() > 0) {
            for (Date date : thisWeeklist) {
                String thiskqDate = dateFormat.format(date);
                List<KqWorkerDaily> kqWorkerDailyList = thisWeekKqMapByDate.get(thiskqDate);
                double todayNoAbsentRate = 1;
                if (kqWorkerDailyList != null) {
                    int todayAllManNum = kqWorkerDailyList.size();
                    List<KqWorkerDaily> todayNoAbsentManList = kqWorkerDailyList.stream().filter(kqWorkerDaily1 -> kqWorkerDaily1.getIsAbsence() != null && kqWorkerDaily1.getIsAbsence().equals("0")).collect(Collectors.toList());
                    int todayNoAbsentManNum = todayNoAbsentManList.size();
                    if (todayAllManNum > 0) {
                        todayNoAbsentRate = todayNoAbsentManNum / (double) todayAllManNum;
                    }
                }
                HashMap<String, Double> todayData = new HashMap<>();
                todayData.put(thiskqDate, todayNoAbsentRate);
                teacherWeekAttList.add(todayData);
            }
        }
        attendance.setTeacherWeekAttList(teacherWeekAttList);
        return attendance;
    }


    public void reCalculateKqWorkerDaily(){
        School school = new School();
        List<School> schoolList = schoolFeign.findSchoolListByCondition(school);
        Set<String> schoolIdSet = schoolList.stream().map(School::getId).collect(Collectors.toSet());
        Map<String, Set<String>> all = ycVerifaceBackOnlineSchoolList.getAll(schoolIdSet);
        List<KqWorkerDaily> needToUpdateDatas = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(all)){
            Set<String> schoolIds = all.keySet();
            for (String id:schoolIds){
                Set<String> date = all.get(id);
                for (String day:date){
                    KqOriginalData kqOriginalData = new KqOriginalData();
                    kqOriginalData.setCapturedTime(day);
                    kqOriginalData.setSchoolId(id);
                    Pager pager = new Pager();
                    pager.setLike("capturedTime");
                    pager.setPaging(false);
                    kqOriginalData.setPager(pager);
                    List<KqOriginalData> reData = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
                    Map<String, List<KqOriginalData>> userIdDataMap = reData.stream().filter(k ->
                            StrUtil.isNotEmpty(k.getUserId())
                            && (k.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_STAFF)
                            || k.getUserType().equals(Constant.KQ_ORIGINAL_DATA.USER_TYPE_TEACHER))).collect(Collectors.groupingBy(KqOriginalData::getUserId));
                    Set<String> uids = userIdDataMap.keySet();
                    for (String uid :uids){
                        KqWorkerDaily k = new KqWorkerDaily();
                        k.setKqDate(day);
                        k.setUserId(uid);
                        List<KqWorkerDaily> kqWorkerDailies = findKqWorkerDailyListByCondition(k);
                        KqWorkerDaily  oneKqWorkerDaily = CollectionUtil.isNotEmpty(kqWorkerDailies)?kqWorkerDailies.get(0):null;
                        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                        if (oneKqWorkerDaily!=null){
                            kqWorkerDaily.setId(oneKqWorkerDaily.getId());
                            kqWorkerDaily.setUserId(oneKqWorkerDaily.getUserId());
                            kqWorkerDaily.setSchoolId(oneKqWorkerDaily.getSchoolId());
                            kqWorkerDaily.setDepartmentId(oneKqWorkerDaily.getDepartmentId());
                            kqWorkerDaily.setDepartmentName(oneKqWorkerDaily.getDepartmentName());
                            kqWorkerDaily.setGroupId(oneKqWorkerDaily.getGroupId());
                            kqWorkerDaily.setGroupName(oneKqWorkerDaily.getGroupName());
                            kqWorkerDaily.setKqDate(oneKqWorkerDaily.getKqDate());
                            kqWorkerDaily.setUserName(oneKqWorkerDaily.getUserName());
                            kqWorkerDaily.setUserType(oneKqWorkerDaily.getUserType() + "");
                            kqWorkerDaily.setWorknumber(oneKqWorkerDaily.getWorknumber());
                            kqWorkerDaily.setPunchNumber(oneKqWorkerDaily.getPunchNumber());
                            kqWorkerDaily.setIsAbsence(oneKqWorkerDaily.getIsAbsence());
                            kqWorkerDaily.setTodayIsHoliday(oneKqWorkerDaily.getTodayIsHoliday());
                            PunchRules newPunchRules = getNewPunchRules(oneKqWorkerDaily.getTodayStandardRules());
                            kqWorkerDaily.setPunchRules(newPunchRules);
                            kqWorkerDaily.setTodayStandardRules(oneKqWorkerDaily.getTodayStandardRules());
                            kqWorkerDaily.setSchoolYearId(oneKqWorkerDaily.getSchoolYearId());
                            kqWorkerDaily.setTerm(oneKqWorkerDaily.getTerm());
                            kqWorkerDaily.setFromTo(oneKqWorkerDaily.getFromTo());
                        }

                        if (oneKqWorkerDaily!=null){
                            System.out.println("the man "+day+" need clock in ==>"+uid);
                            //change
                            List<KqOriginalData> clockIndatas = userIdDataMap.get(uid);
                            KqWorkerDaily kqWorkerDaily1 = kqWorkerDaily;
                            for (KqOriginalData c : clockIndatas){
                                kqWorkerDaily.setCaptureTime(c.getCapturedTime().substring(11,16));
                                kqWorkerDaily.setIsBatch(true);
                                kqWorkerDaily1  = dailyRecords(kqWorkerDaily);
                            }
                            //find details
                            KqWorkerDaily detailsAndUpdate = findDetailsAndChange(kqWorkerDaily1);
                            needToUpdateDatas.add(detailsAndUpdate);
                        }
                    }
                }
            }
        }
        if (needToUpdateDatas.size()>0){
            needToUpdateDatas.forEach(data->{
                updateKqWorkerDaily(data);
            });
        }
        //删除缓存
        if (CollectionUtil.isNotEmpty(all)){
            ycVerifaceBackOnlineSchoolList.removeAll(all.keySet());

        }
    }
    /**
     * find details and change
     */
    public KqWorkerDaily findDetailsAndChange(KqWorkerDaily workerDaily){
        KqDailyStatusDetail kqDailyStatusDetail = new KqDailyStatusDetail();
        kqDailyStatusDetail.setApplicantId(workerDaily.getUserId());
        String kqDate = workerDaily.getKqDate();
        String dateBegin = kqDate + " 00:00:00";
        String dateEnd = kqDate + " 23:59:59";
        DateTime today = DateUtil.parse(kqDate);
        DateTime todayBegin = DateUtil.parse(dateBegin);
        DateTime todayEnd = DateUtil.parse(dateEnd);
        List<KqDailyStatusDetail> s = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kqDailyStatusDetail);
        //1包含了今天
        List<KqDailyStatusDetail> list1 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                && DateUtil.parse(detail.getBeginTime()).isBeforeOrEquals(todayBegin) && DateUtil.parse(detail.getEndTime()).isAfterOrEquals(todayEnd)
        ).collect(Collectors.toList());
        //2被今天包含
        List<KqDailyStatusDetail> list2 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                && DateUtil.parse(detail.getBeginTime()).isAfterOrEquals(todayBegin) && DateUtil.parse(detail.getEndTime()).isBeforeOrEquals(todayEnd)
        ).collect(Collectors.toList());
        //3包含今天的前半部分
        List<KqDailyStatusDetail> list3 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                && DateUtil.parse(detail.getBeginTime()).isBefore(todayBegin) && DateUtil.parse(detail.getEndTime()).isAfter(todayBegin) && DateUtil.parse(detail.getEndTime()).isBefore(todayEnd)
        ).collect(Collectors.toList());
        //4包含今天的后半部分
        List<KqDailyStatusDetail> list4 = s.stream().filter(detail -> detail.getBeginTime() != null && detail.getEndTime() != null
                && DateUtil.parse(detail.getBeginTime()).isAfter(todayBegin) && DateUtil.parse(detail.getBeginTime()).isBefore(todayEnd) && DateUtil.parse(detail.getEndTime()).isAfter(todayEnd)
        ).collect(Collectors.toList());
        List<KqDailyStatusDetail> checkList = new ArrayList<>();
        checkList.addAll(list1);
        checkList.addAll(list2);
        checkList.addAll(list3);
        checkList.addAll(list4);
        checkList = checkList.stream().sorted(Comparator.comparing(KqDailyStatusDetail::getCreateTime)).collect(Collectors.toList());
        if (checkList.size() > 0) {
            for (KqDailyStatusDetail detail : checkList) {
                workerDaily =  kqDailyStatusDetailService.createKqWorkerDailyCheckDetail(detail, workerDaily);
            }
        }
        return workerDaily;
    }
}
