package com.yice.edu.cn.xw.service.workerKq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.mongo.MongoKit;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqDailyStatusDetail;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.xw.feignClient.ProcessBusinessDataFeign;
import com.yice.edu.cn.xw.feignClient.jw.school.SchoolFeign;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.ObjRecord;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class KqDailyStatusDetailService {
    private static ExecutorService executorService;
    @Autowired
    private MongoTemplate mot;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private KqWorkerDailyService kqWorkerDailyService;
    @Autowired
    private ProcessBusinessDataFeign processBusinessDataFeign;
    @Autowired
    private KqDailyStatusDetailService kqDailyStatusDetailService;
    @Autowired
    private WorkerKqRulesService workerKqRulesService;
    @Autowired
    private SchoolFeign schoolFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KqDailyStatusDetail findKqDailyStatusDetailById(String id) {
        return mot.findById(id, KqDailyStatusDetail.class);
    }


    public void updatKqWorkerDailyRecorrds(KqDailyStatusDetail kqDailyStatusDetail) {
        //更改当前人员打卡记录
        KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        Pager pager = new Pager();
        kqWorkerDaily.setUserId(kqDailyStatusDetail.getApplicantId());
        String[] rangeArray = new String[2];
        if (kqDailyStatusDetail.getStatus().equals("5")) {
            rangeArray[0] = kqDailyStatusDetail.getFillUpTime();
            rangeArray[1] = kqDailyStatusDetail.getFillUpTime();

        } else {
            rangeArray[0] = kqDailyStatusDetail.getBeginTime().substring(0, 10);
            rangeArray[1] = kqDailyStatusDetail.getEndTime().substring(0, 10);
        }
        pager.setRangeField("kqDate");
        pager.setRangeArray(rangeArray);
        kqWorkerDaily.setPager(pager);
        List<KqWorkerDaily> list = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        //当前人员日打卡记录
        if (list.size() > 0) {
            for (KqWorkerDaily kq : list) {
                //当天考勤规则
                WorkerKqRules workKq = new WorkerKqRules();
                workKq.setPunchNumber(kq.getTodayStandardRules().getPunchNumber());
                workKq.setPunchRules(kq.getTodayStandardRules().getPunchRules());
                //一天一组
                if (kq.getPunchNumber().equals("1")) {
                    if (kqDailyStatusDetail.getStatus().equals("5")) {
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("1")) {
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("5")
                                    || kq.getPunchRules().getMorningInStatus().equals("6") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("2")) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("5")
                                    || kq.getPunchRules().getDuskOutStatus().equals("6") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }

                    } else {
                        String beginTime = kqDailyStatusDetail.getBeginTime();
                        String endTime = kqDailyStatusDetail.getEndTime();
                        String morningInTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningIn() + ":00";
                        String duskOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getDuskOut() + ":00";
                        //A
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningInTime)) && (DateUtil.parse(morningInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            // 手动修改不记录上一次状态或详细  source  1  status  4  OA同步 记录上一次状态及详细
                            if (kq.getPunchRules().getMorningInStatus().equals("0") || kq.getPunchRules().getMorningInStatus().equals("1")
                                    || kq.getPunchRules().getMorningInStatus().equals("2") || kq.getPunchRules().getMorningInStatus().equals("3")) {
                                //当前记录为打卡  OA同步记录详细
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatus(kq.getPunchRules().getMorningInStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                //当前记录为手动修改  OA同步记录详细
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }

                        }
                        //B
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(duskOutTime)) && (DateUtil.parse(duskOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("0") || kq.getPunchRules().getDuskOutStatus().equals("1")
                                    || kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatus(kq.getPunchRules().getDuskOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setDuskOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    }
                    kq.setIsAbsence("0");
                    kqWorkerDailyService.updateKqWorkerDaily(kq);
                    //一天两组
                } else if (kq.getPunchNumber().equals("2")) {
                    if (kqDailyStatusDetail.getStatus().equals("5")) {
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("1")) {
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("2")) {
                            if (kq.getPunchRules().getMorningOutStatus().equals("4") || kq.getPunchRules().getMorningOutStatus().equals("6")
                                    || kq.getPunchRules().getMorningOutStatus().equals("5") || kq.getPunchRules().getMorningOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatusDetail(kq.getPunchRules().getMorningOutStatusDetail());
                                    kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                                }
                            }
                            kq.getPunchRules().setMorningOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningOut("--");
                                kq.getPunchRules().setMorningOutTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("3")) {
                            if (kq.getPunchRules().getNoonInStatus().equals("4") || kq.getPunchRules().getNoonInStatus().equals("6")
                                    || kq.getPunchRules().getNoonInStatus().equals("5") || kq.getPunchRules().getNoonInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatusDetail(kq.getPunchRules().getNoonInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setNoonInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setNoonInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setNoonIn("--");
                                kq.getPunchRules().setNoonInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("4")) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("6")
                                    || kq.getPunchRules().getDuskOutStatus().equals("5") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    } else {
                        String beginTime = kqDailyStatusDetail.getBeginTime();
                        String endTime = kqDailyStatusDetail.getEndTime();
                        String morningInTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningIn() + ":00";
                        String morningOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningOut() + ":00";
                        String noonInTime = kq.getKqDate() + " " + workKq.getPunchRules().getNoonIn() + ":00";
                        String duskOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getDuskOut() + ":00";
                        //A
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningInTime)) && (DateUtil.parse(morningInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getMorningInStatus().equals("0") || kq.getPunchRules().getMorningInStatus().equals("1")
                                    || kq.getPunchRules().getMorningInStatus().equals("2") || kq.getPunchRules().getMorningInStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatus(kq.getPunchRules().getMorningInStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("5")
                                    || kq.getPunchRules().getMorningInStatus().equals("6") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }

                        }
                        //B
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningOutTime)) && (DateUtil.parse(morningOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getMorningOutStatus().equals("0") || kq.getPunchRules().getMorningOutStatus().equals("1")
                                    || kq.getPunchRules().getMorningOutStatus().equals("2") || kq.getPunchRules().getMorningOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatus(kq.getPunchRules().getMorningOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningOutStatus().equals("4") || kq.getPunchRules().getMorningOutStatus().equals("5")
                                    || kq.getPunchRules().getMorningOutStatus().equals("6") || kq.getPunchRules().getMorningOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatusDetail(kq.getPunchRules().getMorningOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningOut("--");
                                kq.getPunchRules().setMorningOutTimeLag("0");
                            }
                        }
                        //C
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(noonInTime)) && (DateUtil.parse(noonInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getNoonInStatus().equals("0") || kq.getPunchRules().getNoonInStatus().equals("1")
                                    || kq.getPunchRules().getNoonInStatus().equals("2") || kq.getPunchRules().getNoonInStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatus(kq.getPunchRules().getNoonInStatus());
                                }
                            }
                            if (kq.getPunchRules().getNoonInStatus().equals("4") || kq.getPunchRules().getNoonInStatus().equals("5")
                                    || kq.getPunchRules().getNoonInStatus().equals("6") || kq.getPunchRules().getNoonInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatusDetail(kq.getPunchRules().getNoonInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setNoonInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setNoonInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setNoonInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setNoonIn("--");
                                kq.getPunchRules().setNoonInTimeLag("0");
                            }
                        }
                        //D
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(duskOutTime)) && (DateUtil.parse(duskOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("0") || kq.getPunchRules().getDuskOutStatus().equals("1")
                                    || kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatus(kq.getPunchRules().getDuskOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("5")
                                    || kq.getPunchRules().getDuskOutStatus().equals("6") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setDuskOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    }
                    kq.setIsAbsence("0");
                    kqWorkerDailyService.updateKqWorkerDaily(kq);
                }
            }

        }
    }

    public void saveKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetail.setCreateTime(DateUtil.now());
        kqDailyStatusDetail.setId(sequenceId.nextId());
        mot.insert(kqDailyStatusDetail);
    }


    public List<KqDailyStatusDetail> findKqDailyStatusDetailListByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDailyStatusDetail.class)).find(MongoKit.buildMatchDocument(kqDailyStatusDetail));
        Pager pager = kqDailyStatusDetail.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqDailyStatusDetail> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDailyStatusDetail.class, document)));
        return list;
    }

    public long findKqDailyStatusDetailCountByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        return mot.getCollection(mot.getCollectionName(KqDailyStatusDetail.class)).countDocuments(MongoKit.buildMatchDocument(kqDailyStatusDetail));
    }

    public KqDailyStatusDetail findOneKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDailyStatusDetail.class)).find(MongoKit.buildMatchDocument(kqDailyStatusDetail));
        MongoKit.appendProjection(query, kqDailyStatusDetail.getPager());
        return mot.getConverter().read(KqDailyStatusDetail.class, query.first());
    }

    public void updateKqDailyStatusDetail(KqDailyStatusDetail kqDailyStatusDetail) {
        kqDailyStatusDetail.setUpdateTime(DateUtil.now());
        mot.updateFirst(query(where("id").is(kqDailyStatusDetail.getId())), MongoKit.update(kqDailyStatusDetail), KqDailyStatusDetail.class);
    }

    public void deleteKqDailyStatusDetail(String id) {
        mot.remove(query(where("id").is(id)), KqDailyStatusDetail.class);
    }

    public void deleteKqDailyStatusDetailByCondition(KqDailyStatusDetail kqDailyStatusDetail) {
        mot.getCollection(mot.getCollectionName(KqDailyStatusDetail.class)).deleteMany(MongoKit.buildMatchDocument(kqDailyStatusDetail));
    }

    public void batchSaveKqDailyStatusDetail(List<KqDailyStatusDetail> kqDailyStatusDetails) {
        kqDailyStatusDetails.forEach(kqDailyStatusDetail -> kqDailyStatusDetail.setId(sequenceId.nextId()));
        mot.insertAll(kqDailyStatusDetails);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<KqDailyStatusDetail> findKqDailyStatusDetailListByTimeZone(KqDailyStatusDetail kqDailyStatusDetail) {
        Document doc = new Document();
        doc.append("$or", Arrays.asList(
                new Document(
                        "$and", Arrays.asList(new Document("beginTime", new Document("$gte", kqDailyStatusDetail.getBeginTime())), new Document("endTime", new Document("$lte", kqDailyStatusDetail.getEndTime())))
                )
                ,
                new Document(
                        "$and", Arrays.asList(new Document("beginTime", new Document("$lte", kqDailyStatusDetail.getBeginTime())), new Document("endTime", new Document("$gte", kqDailyStatusDetail.getBeginTime())))
                )
                , new Document(
                        "$and", Arrays.asList(new Document("beginTime", new Document("$lte", kqDailyStatusDetail.getEndTime())), new Document("endTime", new Document("$gte", kqDailyStatusDetail.getEndTime())))
                )
        ));
        kqDailyStatusDetail.setBeginTime(null);
        kqDailyStatusDetail.setEndTime(null);
        kqDailyStatusDetail.setPager(null);
        doc.putAll(MongoKit.buildMatchDocument(kqDailyStatusDetail));
        FindIterable<Document> query = mot.getCollection(mot.getCollectionName(KqDailyStatusDetail.class)).find(doc);
        Pager pager = kqDailyStatusDetail.getPager();
        MongoKit.appendSort(query, pager);
        MongoKit.appendPage(query, pager);
        MongoKit.appendProjection(query, pager);
        List<KqDailyStatusDetail> list = new ArrayList<>();
        query.forEach((Block<Document>) document -> list.add(mot.getConverter().read(KqDailyStatusDetail.class, document)));
        return list;
    }

    /**
     * 创建日考勤记录时更改状态
     */
    public KqWorkerDaily createKqWorkerDailyCheckDetail(KqDailyStatusDetail kqDailyStatusDetail, KqWorkerDaily workerDaily) {
        //更改当前人员打卡记录
       /* KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
        Pager pager = new Pager();
        kqWorkerDaily.setUserId(kqDailyStatusDetail.getApplicantId());*/
        String[] rangeArray = new String[2];
        if (kqDailyStatusDetail.getStatus().equals("5")) {
            rangeArray[0] = kqDailyStatusDetail.getFillUpTime();
            rangeArray[1] = kqDailyStatusDetail.getFillUpTime();

        } else {
            rangeArray[0] = kqDailyStatusDetail.getBeginTime().substring(0, 10);
            rangeArray[1] = kqDailyStatusDetail.getEndTime().substring(0, 10);
        }
       /* pager.setRangeField("kqDate");
        pager.setRangeArray(rangeArray);
        kqWorkerDaily.setPager(pager);*/
        List<KqWorkerDaily> list = new ArrayList<>();
        list.add(workerDaily);
        //kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
        //当前人员日打卡记录
        if (list.size() > 0) {
            for (KqWorkerDaily kq : list) {
                //当天考勤规则
                WorkerKqRules workKq = new WorkerKqRules();
                workKq.setPunchNumber(kq.getTodayStandardRules().getPunchNumber());
                workKq.setPunchRules(kq.getTodayStandardRules().getPunchRules());
                //一天一组
                if (kq.getPunchNumber().equals("1")) {
                    if (kqDailyStatusDetail.getStatus().equals("5")) {
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("1")) {
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("5")
                                    || kq.getPunchRules().getMorningInStatus().equals("6") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("2")) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("5")
                                    || kq.getPunchRules().getDuskOutStatus().equals("6") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }

                    } else {
                        String beginTime = kqDailyStatusDetail.getBeginTime();
                        String endTime = kqDailyStatusDetail.getEndTime();
                        String morningInTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningIn() + ":00";
                        String duskOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getDuskOut() + ":00";
                        //A
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningInTime)) && (DateUtil.parse(morningInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            // 手动修改不记录上一次状态或详细  source  1  status  4  OA同步 记录上一次状态及详细
                            if (kq.getPunchRules().getMorningInStatus().equals("0") || kq.getPunchRules().getMorningInStatus().equals("1")
                                    || kq.getPunchRules().getMorningInStatus().equals("2") || kq.getPunchRules().getMorningInStatus().equals("3")) {
                                //当前记录为打卡  OA同步记录详细
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatus(kq.getPunchRules().getMorningInStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                //当前记录为手动修改  OA同步记录详细
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }

                        }
                        //B
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(duskOutTime)) && (DateUtil.parse(duskOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("0") || kq.getPunchRules().getDuskOutStatus().equals("1")
                                    || kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatus(kq.getPunchRules().getDuskOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setDuskOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    }
                    kq.setIsAbsence("0");
                    if (workerDaily.getIsBatch()!=null&&workerDaily.getIsBatch()){
                       return kq;
                    }
                    kqWorkerDailyService.updateKqWorkerDaily(kq);
                    //一天两组
                } else if (kq.getPunchNumber().equals("2")) {
                    if (kqDailyStatusDetail.getStatus().equals("5")) {
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("1")) {
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("2")) {
                            if (kq.getPunchRules().getMorningOutStatus().equals("4") || kq.getPunchRules().getMorningOutStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getMorningOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatusDetail(kq.getPunchRules().getMorningOutStatusDetail());
                                    kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                                }
                            }
                            kq.getPunchRules().setMorningOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningOut("--");
                                kq.getPunchRules().setMorningOutTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("3")) {
                            if (kq.getPunchRules().getNoonInStatus().equals("4") || kq.getPunchRules().getNoonInStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getNoonInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatusDetail(kq.getPunchRules().getNoonInStatusDetail());
                                }
                            }
                            kq.getPunchRules().setNoonInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setNoonInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setNoonIn("--");
                                kq.getPunchRules().setNoonInTimeLag("0");
                            }
                        }
                        if (kqDailyStatusDetail.getFillMissCardPoint().equals("4")) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("6")
                                    || kq.getPunchRules().getMorningInStatus().equals("5") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    } else {
                        String beginTime = kqDailyStatusDetail.getBeginTime();
                        String endTime = kqDailyStatusDetail.getEndTime();
                        String morningInTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningIn() + ":00";
                        String morningOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getMorningOut() + ":00";
                        String noonInTime = kq.getKqDate() + " " + workKq.getPunchRules().getNoonIn() + ":00";
                        String duskOutTime = kq.getKqDate() + " " + workKq.getPunchRules().getDuskOut() + ":00";
                        //A
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningInTime)) && (DateUtil.parse(morningInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getMorningInStatus().equals("0") || kq.getPunchRules().getMorningInStatus().equals("1")
                                    || kq.getPunchRules().getMorningInStatus().equals("2") || kq.getPunchRules().getMorningInStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatus(kq.getPunchRules().getMorningInStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningInStatus().equals("4") || kq.getPunchRules().getMorningInStatus().equals("5")
                                    || kq.getPunchRules().getMorningInStatus().equals("6") || kq.getPunchRules().getMorningInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningInStatusDetail(kq.getPunchRules().getMorningInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningIn("--");
                                kq.getPunchRules().setMorningInTimeLag("0");
                            }

                        }
                        //B
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(morningOutTime)) && (DateUtil.parse(morningOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getMorningOutStatus().equals("0") || kq.getPunchRules().getMorningOutStatus().equals("1")
                                    || kq.getPunchRules().getMorningOutStatus().equals("2") || kq.getPunchRules().getMorningOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatus(kq.getPunchRules().getMorningOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getMorningOutStatus().equals("4") || kq.getPunchRules().getMorningOutStatus().equals("5")
                                    || kq.getPunchRules().getMorningOutStatus().equals("6") || kq.getPunchRules().getMorningOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastMorningOutStatusDetail(kq.getPunchRules().getMorningOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setMorningOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setMorningOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setMorningOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setMorningOut("--");
                                kq.getPunchRules().setMorningOutTimeLag("0");
                            }
                        }
                        //C
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(noonInTime)) && (DateUtil.parse(noonInTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getNoonInStatus().equals("0") || kq.getPunchRules().getNoonInStatus().equals("1")
                                    || kq.getPunchRules().getNoonInStatus().equals("2") || kq.getPunchRules().getNoonInStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatus(kq.getPunchRules().getNoonInStatus());
                                }
                            }
                            if (kq.getPunchRules().getNoonInStatus().equals("4") || kq.getPunchRules().getNoonInStatus().equals("5")
                                    || kq.getPunchRules().getNoonInStatus().equals("6") || kq.getPunchRules().getNoonInStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastNoonInStatusDetail(kq.getPunchRules().getNoonInStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setNoonInStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setNoonInStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setNoonInStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setNoonIn("--");
                                kq.getPunchRules().setNoonInTimeLag("0");
                            }
                        }
                        //D
                        if (DateUtil.parse(beginTime).isBeforeOrEquals(DateUtil.parse(duskOutTime)) && (DateUtil.parse(duskOutTime).isBeforeOrEquals(DateUtil.parse(endTime)))) {
                            if (kq.getPunchRules().getDuskOutStatus().equals("0") || kq.getPunchRules().getDuskOutStatus().equals("1")
                                    || kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("3")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatus(kq.getPunchRules().getDuskOutStatus());
                                }
                            }
                            if (kq.getPunchRules().getDuskOutStatus().equals("4") || kq.getPunchRules().getDuskOutStatus().equals("5")
                                    || kq.getPunchRules().getDuskOutStatus().equals("6") || kq.getPunchRules().getDuskOutStatus().equals("7")) {
                                if (kqDailyStatusDetail.getSource().equals("1") && kqDailyStatusDetail.getStatus().equals("4")) {
                                    kq.getPunchRules().setLastDuskOutStatusDetail(kq.getPunchRules().getDuskOutStatusDetail());
                                }
                            }
                            if (kqDailyStatusDetail.getStatus().equals("4")) {
                                kq.getPunchRules().setDuskOutStatusType(kqDailyStatusDetail.getStatusType());
                            }
                            kq.getPunchRules().setDuskOutStatus(kqDailyStatusDetail.getStatus());
                            kq.getPunchRules().setDuskOutStatusDetail(kqDailyStatusDetail);
                            if (kqDailyStatusDetail.getSource().equals("0")) {
                                kq.getPunchRules().setDuskOut("--");
                                kq.getPunchRules().setDuskOutTimeLag("0");
                            }
                        }
                    }
                    kq.setIsAbsence("0");
                    if (workerDaily.getIsBatch()!=null&&workerDaily.getIsBatch()){
                        return kq;
                    }
                    kqWorkerDailyService.updateKqWorkerDaily(kq);
                }
            }

        }
        return null;
    }


    //保存OA同步数据
    public void saveOaKqDailyStatusDetail() {
        ProcessBusinessData processBusinessData = new ProcessBusinessData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -5);
        Object[] arr = new Object[2];
        arr[0] = sdf.format(nowTime.getTime());
        arr[1] = null;
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setRangeField("endTime");
        pager.setRangeArray(arr);
        processBusinessData.setPager(pager);
        processBusinessData.setStatus(Constant.OA.SUCCESS_COMPLETE);
        List<ProcessBusinessData> list = processBusinessDataFeign.findProcessBusinessDataListByConditionForKq(processBusinessData);
        //类型
        if (list != null && list.size() > 0) {
            List<ProcessBusinessData> data = list.stream().filter(s -> s != null && s.getProcessLibId() != null && (s.getProcessLibId().equals("20181027163655002") ||
                    s.getProcessLibId().equals("20181027163655010") || s.getProcessLibId().equals("1121702486948855808") || s.getProcessLibId().equals("20181027163655001"))).collect(Collectors.toList());
            if (data.size() > 0 && data != null) {
                List<KqDailyStatusDetail> list2 = new ArrayList<KqDailyStatusDetail>();
                for (ProcessBusinessData p : data) {
                    KqDailyStatusDetail k = new KqDailyStatusDetail();
                    KqDailyStatusDetail kk = new KqDailyStatusDetail();
                    if (p.getProcessLibId().equals("20181027163655001") && (p.getFormData().get("leaveType").equals("0")
                            || p.getFormData().get("leaveType").equals("1")
                            || p.getFormData().get("leaveType").equals("2")
                            || p.getFormData().get("leaveType").equals("3")
                            || p.getFormData().get("leaveType").equals("4")
                            || p.getFormData().get("leaveType").equals("5")
                            || p.getFormData().get("leaveType").equals("6")
                            || p.getFormData().get("leaveType").equals("7")
                            || p.getFormData().get("leaveType").equals("8")
                            || p.getFormData().get("leaveType").equals("9"))) {
                        if (StrUtil.isNotEmpty(p.getCancelReason())) {
                            // 请假撤销通过 审批通过 请假无效
                            kk.setStatus("4");
                            kk.setStatusType(p.getFormData().get("leaveType").toString());
                            kk.setBeginTime(p.getFormData().get("beginTime").toString() + ":00");
                            kk.setEndTime(p.getFormData().get("endTime").toString() + ":00");
                            kk.setApplicant(p.getInitiator());
                            kk.setApplicantId(p.getInitiatorId());
                            kk.setApplyTime(p.getCreateTime());
                            kk.setSchoolId(p.getSchoolId());
                            List<KqDailyStatusDetail> l = kqDailyStatusDetailService.findKqDailyStatusDetailListByCondition(kk);
                            KqWorkerDaily kqWorkerDaily = new KqWorkerDaily();
                            kqWorkerDaily.setUserId(p.getInitiatorId());
                            Pager pager1 = new Pager();
                            String[] rangeArray = new String[2];
                            rangeArray[0] = p.getFormData().get("beginTime").toString().substring(0, 10);
                            if (DateUtil.parse(p.getFormData().get("endTime").toString()).isAfterOrEquals(DateUtil.parse(DateUtil.now()))) {
                                rangeArray[1] = DateUtil.now().substring(0, 10);
                            }
                            rangeArray[1] = p.getFormData().get("endTime").toString().substring(0, 10);
                            pager1.setRangeField("kqDate");
                            pager1.setRangeArray(rangeArray);
                            kqWorkerDaily.setPager(pager1);
                            List<KqWorkerDaily> lll = kqWorkerDailyService.findKqWorkerDailyListByCondition(kqWorkerDaily);
                            if (lll.size() > 0) {
                                for (KqWorkerDaily kq : lll) {
                                    if (kq.getPunchNumber().equals("1")) {
                                        if (kq.getPunchRules().getMorningInStatusDetail() != null) {
                                            if (kq.getPunchRules().getMorningInStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {

                                                if (kq.getPunchRules().getLastMorningInStatusDetail() != null && kq.getPunchRules().getLastMorningInStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningInStatusType(kq.getPunchRules().getLastMorningInStatusDetail().getStatusType());
                                                        kq.getPunchRules().setMorningInStatusDetail(kq.getPunchRules().getLastMorningInStatusDetail());
                                                    } else if (kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningInStatusDetail(kq.getPunchRules().getLastMorningInStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatus());
                                                }

                                                if (!kq.getPunchRules().getMorningInTimeLag().equals("0")) {
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setMorningInStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setMorningInStatus("0");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getMorningIn().equals("--")) {
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setMorningInStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setMorningInStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastMorningInStatus() != null) {
                                                            kq.getPunchRules().setMorningInStatus("3");
                                                        } else if (kq.getPunchRules().getLastMorningInStatusDetail() != null) {
                                                            kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastMorningInStatus() != null) {
                                                    kq.getPunchRules().setLastMorningInStatus(null);
                                                    kq.getPunchRules().setMorningInStatusType(null);
                                                    kq.getPunchRules().setMorningInStatusDetail(null);
                                                    kq.getPunchRules().setLastMorningInStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastMorningInStatus() == null) {
                                                    kq.getPunchRules().setLastMorningInStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getPunchRules().getDuskOutStatusDetail() != null) {
                                            if (kq.getPunchRules().getDuskOutStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {
                                                if (kq.getPunchRules().getLastDuskOutStatusDetail() != null && kq.getPunchRules().getLastDuskOutStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setDuskOutStatusType(kq.getPunchRules().getLastDuskOutStatusDetail().getStatusType());
                                                        kq.getPunchRules().setDuskOutStatusDetail(kq.getPunchRules().getLastDuskOutStatusDetail());
                                                    } else if (kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setDuskOutStatusDetail(kq.getPunchRules().getLastDuskOutStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatus());
                                                }

                                                if (!kq.getPunchRules().getDuskOutTimeLag().equals("0")) {
                                                    if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                        kq.getPunchRules().setDuskOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setDuskOutStatus("1");
                                                    }
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setDuskOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setDuskOutStatus("1");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getDuskOut().equals("--")) {
                                                        if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setDuskOutStatus("2");
                                                        }
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setDuskOutStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastDuskOutStatus() != null) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else if (kq.getPunchRules().getLastDuskOutStatusDetail() != null) {
                                                            kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastDuskOutStatus() != null) {
                                                    kq.getPunchRules().setLastDuskOutStatus(null);
                                                    kq.getPunchRules().setDuskOutStatusType(null);
                                                    kq.getPunchRules().setDuskOutStatusDetail(null);
                                                    kq.getPunchRules().setLastDuskOutStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastDuskOutStatus() == null) {
                                                    kq.getPunchRules().setLastDuskOutStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getTodayStandardRules().getNoNeedCard().equals("1") || kq.getTodayIsHoliday().equals("1")) {
                                            if (kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("1")) {
                                                kq.getPunchRules().setDuskOutStatus("3");
                                            }
                                        }
                                        kqWorkerDailyService.updateKqWorkerDaily(kq);
                                    }
                                    if (kq.getPunchNumber().equals("2")) {
                                        if (kq.getPunchRules().getMorningInStatusDetail() != null) {
                                            if (kq.getPunchRules().getMorningInStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getMorningInStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {

                                                if (kq.getPunchRules().getLastMorningInStatusDetail() != null && kq.getPunchRules().getLastMorningInStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningInStatusType(kq.getPunchRules().getLastMorningInStatusDetail().getStatusType());
                                                        kq.getPunchRules().setMorningInStatusDetail(kq.getPunchRules().getLastMorningInStatusDetail());
                                                    } else if (kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastMorningInStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningInStatusDetail(kq.getPunchRules().getLastMorningInStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatus());
                                                }

                                                if (!kq.getPunchRules().getMorningInTimeLag().equals("0")) {
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setMorningInStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setMorningInStatus("0");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getMorningIn().equals("--")) {
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setMorningInStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setMorningInStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastMorningInStatus() != null) {
                                                            kq.getPunchRules().setMorningInStatus("3");
                                                        } else if (kq.getPunchRules().getLastMorningInStatusDetail() != null) {
                                                            kq.getPunchRules().setMorningInStatus(kq.getPunchRules().getLastMorningInStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastMorningInStatus() != null) {
                                                    kq.getPunchRules().setLastMorningInStatus(null);
                                                    kq.getPunchRules().setMorningInStatusType(null);
                                                    kq.getPunchRules().setMorningInStatusDetail(null);
                                                    kq.getPunchRules().setLastMorningInStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastMorningInStatus() == null) {
                                                    kq.getPunchRules().setLastMorningInStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getPunchRules().getMorningOutStatusDetail() != null) {
                                            if (kq.getPunchRules().getMorningOutStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getMorningOutStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getMorningOutStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getMorningOutStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {

                                                if (kq.getPunchRules().getLastMorningOutStatusDetail() != null && kq.getPunchRules().getLastMorningOutStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastMorningOutStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setMorningOutStatus(kq.getPunchRules().getLastMorningOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningOutStatusType(kq.getPunchRules().getLastMorningOutStatusDetail().getStatusType());
                                                        kq.getPunchRules().setMorningOutStatusDetail(kq.getPunchRules().getLastMorningOutStatusDetail());
                                                    } else if (kq.getPunchRules().getLastMorningOutStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastMorningOutStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastMorningOutStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setMorningOutStatus(kq.getPunchRules().getLastMorningOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setMorningOutStatusDetail(kq.getPunchRules().getLastMorningOutStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setMorningOutStatus(kq.getPunchRules().getLastMorningOutStatus());
                                                }

                                                if (!kq.getPunchRules().getMorningOutTimeLag().equals("0")) {
                                                    if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                        kq.getPunchRules().setMorningOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setMorningOutStatus("1");
                                                    }
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setMorningOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setMorningOutStatus("1");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getMorningOut().equals("--")) {
                                                        if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                            kq.getPunchRules().setMorningOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setMorningOutStatus("2");
                                                        }
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setMorningOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setMorningOutStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastMorningOutStatus() != null) {
                                                            kq.getPunchRules().setMorningOutStatus("3");
                                                        } else if (kq.getPunchRules().getLastMorningOutStatusDetail() != null) {
                                                            kq.getPunchRules().setMorningOutStatus(kq.getPunchRules().getLastMorningOutStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastMorningOutStatus() != null) {
                                                    kq.getPunchRules().setLastMorningOutStatus(null);
                                                    kq.getPunchRules().setMorningOutStatusType(null);
                                                    kq.getPunchRules().setMorningOutStatusDetail(null);
                                                    kq.getPunchRules().setLastMorningOutStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastMorningOutStatus() == null) {
                                                    kq.getPunchRules().setLastMorningOutStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getPunchRules().getNoonInStatusDetail() != null) {
                                            if (kq.getPunchRules().getNoonInStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getNoonInStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getNoonInStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getNoonInStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {

                                                if (kq.getPunchRules().getLastNoonInStatusDetail() != null && kq.getPunchRules().getLastNoonInStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastNoonInStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setNoonInStatus(kq.getPunchRules().getLastNoonInStatusDetail().getStatus());
                                                        kq.getPunchRules().setNoonInStatusType(kq.getPunchRules().getLastNoonInStatusDetail().getStatusType());
                                                        kq.getPunchRules().setNoonInStatusDetail(kq.getPunchRules().getLastNoonInStatusDetail());
                                                    } else if (kq.getPunchRules().getLastNoonInStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastNoonInStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastNoonInStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setNoonInStatus(kq.getPunchRules().getLastNoonInStatusDetail().getStatus());
                                                        kq.getPunchRules().setNoonInStatusDetail(kq.getPunchRules().getLastNoonInStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setNoonInStatus(kq.getPunchRules().getLastNoonInStatus());
                                                }

                                                if (!kq.getPunchRules().getNoonInTimeLag().equals("0")) {
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setNoonInStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setNoonInStatus("0");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getNoonIn().equals("--")) {
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setNoonInStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setNoonInStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastNoonInStatus() != null) {
                                                            kq.getPunchRules().setNoonInStatus("3");
                                                        } else if (kq.getPunchRules().getLastNoonInStatusDetail() != null) {
                                                            kq.getPunchRules().setNoonInStatus(kq.getPunchRules().getLastNoonInStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastNoonInStatus() != null) {
                                                    kq.getPunchRules().setLastNoonInStatus(null);
                                                    kq.getPunchRules().setNoonInStatusType(null);
                                                    kq.getPunchRules().setNoonInStatusDetail(null);
                                                    kq.getPunchRules().setLastNoonInStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastNoonInStatus() == null) {
                                                    kq.getPunchRules().setLastNoonInStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getPunchRules().getDuskOutStatusDetail() != null) {
                                            if (kq.getPunchRules().getDuskOutStatusDetail().getStatus().equals("4") &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getStatusType().equals(p.getFormData().get("leaveType").toString()) &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getBeginTime().equals(p.getFormData().get("beginTime").toString() + ":00") &&
                                                    kq.getPunchRules().getDuskOutStatusDetail().getEndTime().equals(p.getFormData().get("endTime").toString() + ":00")) {

                                                if (kq.getPunchRules().getLastDuskOutStatusDetail() != null && kq.getPunchRules().getLastDuskOutStatusDetail().getStatus() != null) {
                                                    if (kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("4")) {
                                                        kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setDuskOutStatusType(kq.getPunchRules().getLastDuskOutStatusDetail().getStatusType());
                                                        kq.getPunchRules().setDuskOutStatusDetail(kq.getPunchRules().getLastDuskOutStatusDetail());
                                                    } else if (kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("5") ||
                                                            kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("6") ||
                                                            kq.getPunchRules().getLastDuskOutStatusDetail().getStatus().equals("7")) {
                                                        kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        kq.getPunchRules().setDuskOutStatusDetail(kq.getPunchRules().getLastDuskOutStatusDetail());
                                                    }
                                                } else {
                                                    kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatus());
                                                }

                                                if (!kq.getPunchRules().getDuskOutTimeLag().equals("0")) {
                                                    if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                        kq.getPunchRules().setDuskOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setDuskOutStatus("1");
                                                    }
                                                    if (kq.getTodayIsHoliday().equals("1")) {
                                                        kq.getPunchRules().setDuskOutStatus("3");
                                                    } else {
                                                        kq.getPunchRules().setDuskOutStatus("1");
                                                    }
                                                } else {
                                                    if (!kq.getPunchRules().getDuskOut().equals("--")) {
                                                        if (kq.getTodayStandardRules().getNoNeedCard().equals("1")) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setDuskOutStatus("2");
                                                        }
                                                        if (kq.getTodayIsHoliday().equals("1")) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else {
                                                            kq.getPunchRules().setDuskOutStatus("2");
                                                        }
                                                    } else {
                                                        if (kq.getPunchRules().getLastDuskOutStatus() != null) {
                                                            kq.getPunchRules().setDuskOutStatus("3");
                                                        } else if (kq.getPunchRules().getLastDuskOutStatusDetail() != null) {
                                                            kq.getPunchRules().setDuskOutStatus(kq.getPunchRules().getLastDuskOutStatusDetail().getStatus());
                                                        }
                                                    }
                                                }
                                                if (kq.getPunchRules().getLastDuskOutStatus() != null) {
                                                    kq.getPunchRules().setLastDuskOutStatus(null);
                                                    kq.getPunchRules().setDuskOutStatusType(null);
                                                    kq.getPunchRules().setDuskOutStatusDetail(null);
                                                    kq.getPunchRules().setLastDuskOutStatusDetail(null);
                                                }
                                                if (kq.getPunchRules().getLastDuskOutStatus() == null) {
                                                    kq.getPunchRules().setLastDuskOutStatusDetail(null);
                                                }
                                            }
                                        }
                                        if (kq.getTodayStandardRules().getNoNeedCard().equals("1") || kq.getTodayIsHoliday().equals("1")) {
                                            if (kq.getPunchRules().getMorningOutStatus().equals("2") || kq.getPunchRules().getMorningOutStatus().equals("1")) {
                                                kq.getPunchRules().setMorningOutStatus("3");
                                            }
                                            if (kq.getPunchRules().getDuskOutStatus().equals("2") || kq.getPunchRules().getDuskOutStatus().equals("1")) {
                                                kq.getPunchRules().setDuskOutStatus("3");
                                            }
                                        }
                                        kqWorkerDailyService.updateKqWorkerDaily(kq);
                                    }
                                }
                            }
                            if (l.size() > 0) {
                                kqDailyStatusDetailService.deleteKqDailyStatusDetail(l.get(0).getId());
                            }
                        } else {
                            // 请假审批通过 请假有效
                            k.setStatus("4");
                            k.setStatusType(p.getFormData().get("leaveType").toString());
                            k.setBeginTime(p.getFormData().get("beginTime").toString() + ":00");
                            k.setEndTime(p.getFormData().get("endTime").toString() + ":00");
                            k.setApplicant(p.getInitiator());
                            k.setApplicantId(p.getInitiatorId());
                            k.setApplyTime(p.getCreateTime());
                            k.setApprover(p.getApprover());
                            k.setLastApprTime(p.getEndTime());
                            k.setSchoolId(p.getSchoolId());
                            k.setCreateTime(p.getCreateTime());
                            k.setSource("1");
                        }
                    } else if (p.getProcessLibId().equals("1121702486948855808")) {
                        k.setStatus("5");
                        k.setFillUpTime(p.getFormData().get("signInTime").toString());
                        switch (p.getFormData().get("signInPoint").toString()) {
                            case "0":
                                k.setFillMissCardPoint("1");
                                break;
                            case "1":
                                k.setFillMissCardPoint("2");
                                break;
                            case "2":
                                k.setFillMissCardPoint("3");
                                break;
                            case "3":
                                k.setFillMissCardPoint("4");
                                break;
                        }
                        k.setApplicant(p.getInitiator());
                        k.setApplicantId(p.getInitiatorId());
                        k.setApplyTime(p.getCreateTime());
                        k.setApprover(p.getApprover());
                        k.setLastApprTime(p.getEndTime());
                        k.setSchoolId(p.getSchoolId());
                        k.setCreateTime(p.getCreateTime());
                        k.setSource("1");
                    } else if (p.getProcessLibId().equals("20181027163655002")) {
                        k.setStatus("6");
                        k.setBeginTime(p.getFormData().get("beginTime").toString() + " 00:00:00");
                        k.setEndTime(p.getFormData().get("endTime").toString() + " 00:00:00");
                        k.setApplicant(p.getInitiator());
                        k.setApplicantId(p.getInitiatorId());
                        k.setApplyTime(p.getCreateTime());
                        k.setApprover(p.getApprover());
                        k.setLastApprTime(p.getEndTime());
                        k.setSchoolId(p.getSchoolId());
                        k.setCreateTime(p.getCreateTime());
                        k.setSource("1");
                    } else if (p.getProcessLibId().equals("20181027163655010")) {
                        k.setStatus("7");
                        k.setBeginTime(p.getFormData().get("beginTime").toString() + ":00");
                        k.setEndTime(p.getFormData().get("endTime").toString() + ":00");
                        k.setApplicant(p.getInitiator());
                        k.setApplicantId(p.getInitiatorId());
                        k.setApplyTime(p.getCreateTime());
                        k.setApprover(p.getApprover());
                        k.setLastApprTime(p.getEndTime());
                        k.setSchoolId(p.getSchoolId());
                        k.setCreateTime(p.getCreateTime());
                        k.setSource("1");
                    }
                    if (StringUtils.isNotEmpty(k.getStatus())) {
                        list2.add(k);
                        kqDailyStatusDetailService.updatKqWorkerDailyRecorrds(k);
                    }
                }
                kqDailyStatusDetailService.batchSaveKqDailyStatusDetail(list2);
            }
        }
    }


    /**
     * 定义线程池
     */
    public static ExecutorService getExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(30);
        }
        return executorService;
    }

    /**
     * 开启线程同步OA数据
     */
    public void syncStatusDetailAndOaMsg() {
        getExecutor().execute(() -> {
                    kqDailyStatusDetailService.saveOaKqDailyStatusDetail();
                }
        );
    }

}
