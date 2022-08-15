package com.yice.edu.cn.dy.service.schoolManage.inspectRecord;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecordCondition;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.dy.dao.schoolManage.attachFile.IMesAttachFileDao;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.MesSchoolEvaluationDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesInstitutionAuditDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesOperateRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesInstitutionDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesTimeStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MesInspectRecordService {
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private MesSchoolEvaluationDao mesSchoolEvaluationDao;
    @Autowired
    private IMesInstitutionDao mesInstitutionDao;
    @Autowired
    private IMesAttachFileDao mesAttachFileDao;
    @Autowired
    private IMesTimeStatusDao mesTimeStatusDao;
    @Autowired
    private IMesInstitutionAuditDao mesInstitutionAuditDao;
    @Autowired
    private IMesOperateRecordDao mesOperateRecordDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Map findMesInspectRecordByRecordId(String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordDao.findMesInspectRecordById(id);
        String auditId = mesInspectRecordDao.findAuditIdByInspectRecordId(id);
        MesAttachFile mesAttachFile = new MesAttachFile();
        mesAttachFile.setReferenceId(mesInspectRecord.getId());
        mesInspectRecord.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
        List<MesOperateRecord> mesOperateRecordForAppeal = mesOperateRecordDao.findMesOperateRecordsByAuditId(auditId);
        Map<String, Object> map = new HashMap<>(3);
        map.put("mesInspectRecord", mesInspectRecord);
        MesOperateRecord mesOperateRecord = new MesOperateRecord();
        mesOperateRecord.setOperatorName(mesInspectRecord.getRecordUserName());
        mesOperateRecord.setRecordUserType(mesInspectRecord.getRecordUserType());
        mesOperateRecord.setOperateDate(mesInspectRecord.getCreateTime());
        mesOperateRecord.setOperateType(3);
        mesOperateRecordForAppeal.add(0,mesOperateRecord);
        map.put("mesOperateRecordsForHistory", mesOperateRecordForAppeal);
        return map;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesInspectRecord findMesInspectRecordById(String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordDao.findMesInspectRecordById(id);
        if (mesInspectRecord != null) {
            //查询相关照片
            MesAttachFile mesAttachFile = new MesAttachFile();
            mesAttachFile.setReferenceId(mesInspectRecord.getId());
            mesInspectRecord.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
            //查询二级制度的点击次数
            MesInstitution institution = mesInstitutionDao.findMesInstitutionById(mesInspectRecord.getInstitutionId());
            if (institution != null && institution.getScore() != 0) {
                mesInspectRecord.setClicks(mesInspectRecord.getInstitutionScore() / institution.getScore());
            }else {
                mesInspectRecord.setClicks(0);//制度分值为零情况下直接将点击次数设为0;
            }
        }
        return mesInspectRecord;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Map<String,Object> findMesInspectRecordByClassId(MesInspectRecord mesInspectRecord) {
        Map<String, Object> map = new HashMap<>();
        if (mesInspectRecord.getSearchType() != null) {
            Calendar cal = Calendar.getInstance();
            if (mesInspectRecord.getSearchType() == 0) {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    cal.set(Calendar.DAY_OF_WEEK, 2);
                    cal.add(Calendar.WEEK_OF_YEAR, -1);
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                } else {
                    cal.set(Calendar.DAY_OF_WEEK, 2);
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                }
            }
            if (mesInspectRecord.getSearchType() == 1) {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
            }
            if (mesInspectRecord.getSearchType() == 2) {
                SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(mesInspectRecord.getSchoolId());
                if (DateUtil.parse(schoolYear.getLastTermBegin()).getTime() <= cal.getTime().getTime() && cal.getTime().getTime() <= DateUtil.parse(schoolYear.getNextTermBegin() + " 23:59:59").getTime()) {
                    mesInspectRecord.setBeginTime(schoolYear.getLastTermBegin() + " 00:00:00");
                }else {
                    mesInspectRecord.setBeginTime(schoolYear.getNextTermBegin() + " 00:00:00");
                }
            }
            mesInspectRecord.setEndTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else {
            mesInspectRecord.setBeginTime(DateUtil.format(DateUtil.parse(mesInspectRecord.getBeginTime(),"yyyy/MM/dd"),"yyyy-MM-dd") + " 00:00:00");
            mesInspectRecord.setEndTime(DateUtil.format(DateUtil.parse(mesInspectRecord.getEndTime(),"yyyy/MM/dd"),"yyyy-MM-dd") + " 23:59:59");
        }
        List<MesInspectRecord> mesInspectRecordByClassId = mesInspectRecordDao.findMesInspectRecordByClassId(mesInspectRecord);
        map.put("list",mesInspectRecordByClassId);
        map.put("beginTime",mesInspectRecord.getBeginTime());
        if (mesInspectRecord.getSearchType()!=null){
            map.put("endTime",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else {
            map.put("endTime",mesInspectRecord.getEndTime());
        }
        return map;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findMesInspectRecordCountByClassId(MesInspectRecord mesInspectRecord) {
        if (mesInspectRecord.getSearchType() != null) {
            Calendar cal = Calendar.getInstance();
            if (mesInspectRecord.getSearchType() == 0) {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    cal.set(Calendar.DAY_OF_WEEK, 2);
                    cal.add(Calendar.WEEK_OF_YEAR, -1);
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                } else {
                    cal.set(Calendar.DAY_OF_WEEK, 2);
                    mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
                }
            }
            if (mesInspectRecord.getSearchType() == 1) {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                mesInspectRecord.setBeginTime(DateUtil.format(cal.getTime(), "yyyy-MM-dd") + " 00:00:00");
            }
            if (mesInspectRecord.getSearchType() == 2) {
                SchoolYear schoolYear = mesSchoolEvaluationDao.findSchoolYearBySchoolId(mesInspectRecord.getSchoolId());
                if (DateUtil.parse(schoolYear.getLastTermBegin()).getTime() <= cal.getTime().getTime() && cal.getTime().getTime() <= DateUtil.parse(schoolYear.getNextTermBegin() + " 23:59:59").getTime()) {
                    mesInspectRecord.setBeginTime(schoolYear.getLastTermBegin() + " 00:00:00");
                }else {
                    mesInspectRecord.setBeginTime(schoolYear.getNextTermBegin() + " 00:00:00");
                }
            }
            mesInspectRecord.setEndTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }else {
            mesInspectRecord.setBeginTime(DateUtil.format(DateUtil.parse(mesInspectRecord.getBeginTime(),"yyyy/MM/dd"),"yyyy-MM-dd") + " 00:00:00");
            mesInspectRecord.setEndTime(DateUtil.format(DateUtil.parse(mesInspectRecord.getEndTime(),"yyyy/MM/dd"),"yyyy-MM-dd") + " 23:59:59");
        }
        return mesInspectRecordDao.findMesInspectRecordCountByClassId(mesInspectRecord);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Boolean ifHaveRecord(MesInspectRecord mesInspectRecord) {
        //先判断一小时之内是否有人登记该学生
        List<MesInspectRecord> checkMesInspectRecords = getRecord(mesInspectRecord);
        if (CollUtil.isNotEmpty(checkMesInspectRecords)) {
            return false;
        }
        return true;
    }

    private List<MesInspectRecord> getRecord(MesInspectRecord mesInspectRecord) {
        List<String> s = mesInspectRecord.getMesInspectRecordConditions().stream()
                .map(MesInspectRecordCondition::getInstitutionId).collect(Collectors.toList());
        mesInspectRecord.setInstitutionIds(s);
        return mesInspectRecordDao.checkMesInspectRecord(mesInspectRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMesInspectRecord(MesInspectRecord mesInspectRecord) {
        //先判断一小时之内是否有人登记该对象
        List<MesInspectRecord> checkMesInspectRecords = getRecord(mesInspectRecord);
        if (CollUtil.isNotEmpty(checkMesInspectRecords)) {
            return false;
        }
        curSchoolYearService.setSchoolYearTerm(mesInspectRecord,mesInspectRecord.getSchoolId());
        //设置创建时间
        mesInspectRecord.setCreateTime(DateUtil.now());
        if (CollUtil.isNotEmpty(mesInspectRecord.getMesInspectRecordConditions())) {

            mesInspectRecord.getMesInspectRecordConditions().forEach(c -> {
                String mesInspectRecordId = sequenceId.nextId();
                //设置主键
                mesInspectRecord.setId(mesInspectRecordId);
                //设置一级制度id
                mesInspectRecord.setInstitutionId(c.getInstitutionId());
                //设置记录日期
                mesInspectRecord.setCreateTime(DateUtil.now());
                //设置事件描述
                String institutionDesc;
                if (c.getInstitutionDesc() != null) {
                    institutionDesc = c.getInstitutionDesc() + (mesInspectRecord.getInfo() == null ? "" : mesInspectRecord.getInfo());
                } else {
                    institutionDesc = "" + (mesInspectRecord.getInfo() == null ? "" : mesInspectRecord.getInfo());
                }
                mesInspectRecord.setInstitutionDesc(institutionDesc);
                //设置事件分值
                mesInspectRecord.setInstitutionScore(c.getInstitutionScore());
                //设置二级制度名称
                mesInspectRecord.setInstitutionName(c.getInstitutionName());
                //设置发生日期
                mesInspectRecord.setHappenDate(c.getHappenDate());
                mesInspectRecordDao.saveMesInspectRecord(mesInspectRecord);
                if (CollUtil.isNotEmpty(mesInspectRecord.getMesAttachFiles())) {
                    mesInspectRecord.getMesAttachFiles().forEach(m -> {
                        m.setId(sequenceId.nextId());
                        m.setReferenceId(mesInspectRecordId);
                        m.setCreateTime(DateUtil.now());
                        m.setSchoolId(mesInspectRecord.getSchoolId());
                        m.setFileType(1);
                        mesAttachFileDao.saveMesAttachFile(m);
                    });
                }
                //查询该对象属于哪个班级对应的班主任id
                List<String> teacherIdList = null;
                if (mesInspectRecord.getObjectType() == 0) {
                    teacherIdList = mesInspectRecordDao.findTeacherPostBySid(mesInspectRecord.getObjectId());
                } else if (mesInspectRecord.getObjectType() == 1) {
                    teacherIdList = mesInspectRecordDao.findTeacherPostByCid(mesInspectRecord.getObjectId());
                }
                //推送消息
                if (CollUtil.isNotEmpty(teacherIdList)) {
                    String[] teacherIds = teacherIdList.toArray(new String[teacherIdList.size()]);
                    final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherIds, "德育-检查通知", "您有一条新的检查通知，请及时查看！", Extras.MES_INSPECT_RECORD, mesInspectRecordId);
                    stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
                }
            });

        }

        return true;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordDao.findMesInspectRecordListByCondition(mesInspectRecord);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Map<String, List<MesInspectRecord>> findMesInspectRecordListByClassId(String classId) {
        Set<String> set = new HashSet<>();
        List<MesInspectRecord> list=new ArrayList<>();
        String today = DateUtil.format(new Date(), "yyyy-MM-dd");
        MesInspectRecord mesInspectRecord=new MesInspectRecord(){{
            this.setClassId(classId);
            this.setCreateTime(today);
        }};
        List<MesInspectRecord> mesInspectRecordListByScoreTypeIsOne = mesInspectRecordDao.findMesInspectRecordListByScoreTypeIsOne(mesInspectRecord);
        List<MesInspectRecord> mesInspectRecordListByScoreTypeIsZero = mesInspectRecordDao.findMesInspectRecordListByScoreTypeIsZero(mesInspectRecord);
        if (CollUtil.isNotEmpty(mesInspectRecordListByScoreTypeIsZero)){
            for (MesInspectRecord inspectRecord : mesInspectRecordListByScoreTypeIsZero) {
                set.add(inspectRecord.getInstitutionId());
            }
            for (String s : set) {
                int count=0;
                MesInspectRecord record = new MesInspectRecord();
                for (MesInspectRecord inspectRecord : mesInspectRecordListByScoreTypeIsZero) {
                    if (s.equals(inspectRecord.getInstitutionId())){
                        count++;
                        record=inspectRecord;
                    }
                }
                record.setCount(count);
                record.setInstitutionScore(record.getInstitutionScore()*count);
                list.add(record);
            }
        }
        Map<String, List<MesInspectRecord>> map = new HashMap<>();
        map.put("addPointsList",mesInspectRecordListByScoreTypeIsOne);
        map.put("minusPointsList",list);
        return map;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordDao.findOneMesInspectRecordByCondition(mesInspectRecord);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordDao.findMesInspectRecordCountByCondition(mesInspectRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMesInspectRecord(MesInspectRecord mesInspectRecord) {
        mesInspectRecordDao.updateMesInspectRecord(mesInspectRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesInspectRecord(String id) {
        mesInspectRecordDao.deleteMesInspectRecord(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        mesInspectRecordDao.deleteMesInspectRecordByCondition(mesInspectRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveMesInspectRecord(List<MesInspectRecord> mesInspectRecords) {
        mesInspectRecords.forEach(mesInspectRecord -> mesInspectRecord.setId(sequenceId.nextId()));
        mesInspectRecordDao.batchSaveMesInspectRecord(mesInspectRecords);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInstitution> findMesInstitutionOlList(String userId) {
        return mesInspectRecordDao.findMesInstitutionOlList(userId);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInstitution> findMesInstitutionTlListByParentId(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesInspectRecordDao.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
    }


    /**
     * 根据老师的带班（班主任） 班级Id去查相关的检查记录
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInspectRecord> findMirAndClassId(MirQuery query) {

        return mesInspectRecordDao.findMirAndClassId(query);
    }

    public long findMirAndClassIdCount(MirQuery query) {
        return mesInspectRecordDao.findMirAndClassIdCount(query);
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesInspectRecord findReference(String id) {
        MesInspectRecord record = mesInspectRecordDao.findMesInspectRecordById(id);
        if (Objects.isNull(record)) {
            return null;
        }
        //查询相关照片
        MesAttachFile mesAttachFile = new MesAttachFile();
        mesAttachFile.setReferenceId(record.getId());
        record.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));

        // 看是否有相关申述的内容
        MesInstitutionAudit mesInstitutionAudit = new MesInstitutionAudit();
        mesInstitutionAudit.setRecordId(record.getId());
        MesInstitutionAudit audit = mesInstitutionAuditDao.findOneMesInstitutionAuditByCondition(mesInstitutionAudit);
        if (!ObjectUtil.isNull(audit)) {
            //表示该条记录有审核
            //不管是第一次申述还是第二次申述，申述结果根据时间升序来，就是第一次 第二次，这里先查是第几次申述，因为第二次不管怎么样都没有按钮了
            MesOperateRecord find = new MesOperateRecord();
            find.setOperateStatus(0);
            find.setAuditId(audit.getId());
            long count = mesOperateRecordDao.findMesOperateRecordCountByCondition(find);
            //申述的内容
            find.setOperateStatus(null);
            find.setPager(new Pager().setPaging(false).setSortOrder("ASC").setSortField("create_time"));
            List<MesOperateRecord> mesOperateRecords = mesOperateRecordDao.findMesOperateRecordListByCondition(find);
            mesOperateRecords.forEach(m ->{
                mesAttachFile.setReferenceId(m.getId());
                m.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
            });
            record.setMesOperateRecords(mesOperateRecords);
            if (count == 2) {
                //说明是再次申述，则第二次申述之后 不管成功与否，都不会有按钮
                record.setState(4);
            } else {
                if (audit.getStatus() == 2) {
                    //表示第一次申述失败，第一次申述失败之后 才会有再次申述按钮，并且再次申述按钮判断还需要走两个公共逻辑见下，逻辑一和二
                    judge(record, 3);
                } else {
                    record.setState(4);
                }
            }
        } else {
            judge(record, 2);
        }
        return record;
    }


    private void judge(MesInspectRecord record, Integer setState) {
        record.setState(setState);
        //通过代码逻辑判断看是否能申述 逻辑一 ：当前时间大于了提交的第二天24点(第三天零点)之后 ，第二 当条记录的制度作废
        DateTime dateTime = DateUtil.offsetDay(DateUtil.parseDate(record.getCreateTime()), 2);
        if (System.currentTimeMillis() > dateTime.getTime()) {
            //超过时间无法申述
            record.setState(1);
            return;
        }
        //逻辑二判断是不是过期
        MesInstitution institution = mesInstitutionDao.findMesInstitutionById(record.getInstitutionId());
        if (Objects.nonNull(institution)) {
            MesTimeStatus status = mesTimeStatusDao.findMesTimeStatusById(institution.getTimeStatusId());
            if (Objects.nonNull(status)) {
                //制度过期无法申述
                if (Objects.equals("0", status.getStatus())) {
                    record.setState(1);
                }
            } else {
                record.setState(0);
            }
        } else {
            record.setState(0);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInspectRecord> findHistoryMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordDao.findHistoryMesInspectRecordListByCondition(mesInspectRecord);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInspectRecord> findTlInstitutionStatistics(Integer type, String schoolId) {
        return mesInspectRecordDao.findTlInstitutionStatistics(getStartDate(type, schoolId), schoolId);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesInspectRecord> findRadarStatistics(Integer type, String schoolId) {
        List<MesInspectRecord> radarStatistics = mesInspectRecordDao.findRadarStatistics(getStartDate(type, schoolId), schoolId);
        List<MesInspectRecord> allOlInstitution = mesInspectRecordDao.findAllOlInstitution(schoolId);
        if (CollUtil.isNotEmpty(radarStatistics) && CollUtil.isNotEmpty(allOlInstitution)) {
            Stream<List<MesInspectRecord>> stream = Stream.of(radarStatistics, allOlInstitution);//去重保留左边集合数据为基准
            // 将两个合为一个
            Stream<MesInspectRecord> newStream = stream.flatMap(
                    (Function<List<MesInspectRecord>, Stream<MesInspectRecord>>) Collection::stream);
            // 为新的集合
            return newStream.distinct().collect(Collectors.toList());
        }
        return radarStatistics;
    }

    private String getStartDate(Integer type, String schoolId) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        switch (type) {
            case 1:
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == 1) {
                    dayOfWeek += 7;
                }
                cal.add(Calendar.DATE, 2 - dayOfWeek);
                break;
            case 2:
                int month = cal.get(Calendar.MONTH);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                String startDate = getStartDate(schoolId, cal);
                DateTime dateTime = DateUtil.parse(startDate, "yyyy-MM-dd");
                cal.setTime(dateTime);
                if (cal.get(Calendar.MONTH) == month) {
                    return startDate;
                }
                cal.setTime(new Date());
                cal.add(Calendar.DATE, 1 - dayOfMonth);
                break;
            case 3:
                return getStartDate(schoolId, cal);
            default:
                break;
        }
        return DateUtil.formatDate(cal.getTime());
    }

    private String getStartDate(String schoolId, Calendar cal) {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setSchoolId(schoolId);
        SchoolYear schoolYearByNowDate = mesInspectRecordDao.findSchoolYearByNowDate(schoolYear);
        if (schoolYearByNowDate != null) {
            return schoolYearByNowDate.getLastTermBegin();
        } else {
            schoolYear.setToYear(cal.get(Calendar.YEAR));
            SchoolYear schoolYearByCondition = mesInspectRecordDao.findSchoolYearByCondition(schoolYear);
            return schoolYearByCondition.getNextTermBegin();
        }
    }

    public MesInspectRecord findMesInspectRecordOneById(String id) {
        MesInspectRecord mesInspectRecord = mesInspectRecordDao.findMesInspectRecordOneById(id);
        JwClasses classes = mesInspectRecordDao.selectClassName(mesInspectRecord.getClassId());
        mesInspectRecord.setGradeName(classes.getGradeName());
        mesInspectRecord.setNumber(classes.getNumber());
        if(mesInspectRecord!=null){
            MesAttachFile mesAttachFile = new MesAttachFile();
            mesAttachFile.setReferenceId(mesInspectRecord.getId());
            List<MesAttachFile> files = mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile);
            mesInspectRecord.setMesAttachFiles(files);
            MesInstitution institution = mesInstitutionDao.findMesInstitutionById(mesInspectRecord.getInstitutionId());
            if (institution != null) {
                mesInspectRecord.setClicks(mesInspectRecord.getInstitutionScore() / institution.getScore());
            }
        }
        return mesInspectRecord;
    }

    public long judgeTeacher(Teacher teacher) {
        return mesInspectRecordDao.judgeTeacher(teacher);
    }
}
