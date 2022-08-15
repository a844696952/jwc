package com.yice.edu.cn.dy.service.schoolManage.audit;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.dy.dao.schoolManage.attachFile.IMesAttachFileDao;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesInstitutionAuditDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesOperateRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCustomTitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MesInstitutionAuditService {
    @Autowired
    private IMesInstitutionAuditDao mesInstitutionAuditDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private IMesAttachFileDao mesAttachFileDao;
    @Autowired
    private IMesOperateRecordDao mesOperateRecordDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private IMesCustomTitleDao mesCustomTitleDao;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesInstitutionAudit findMesInstitutionAuditById(String id) {
        return mesInstitutionAuditDao.findMesInstitutionAuditById(id);
    }

    @Transactional
    public void saveMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit) {
        mesInstitutionAudit.setId(sequenceId.nextId());
        mesInstitutionAuditDao.saveMesInstitutionAudit(mesInstitutionAudit);
    }


    @Transactional(readOnly = true)
    public List<MesInstitutionAudit> findMesInstitutionAuditListByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditDao.selectMesInstitutionAuditListByCondition(mesInstitutionAudit);
    }
    @Transactional(readOnly = true)
    public MesInstitutionAudit findOneMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditDao.findOneMesInstitutionAuditByCondition(mesInstitutionAudit);
    }
    @Transactional(readOnly = true)
    public long findMesInstitutionAuditCountByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditDao.findMesInstitutionAuditCountByCondition(mesInstitutionAudit);
    }
    @Transactional
    public void updateMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit) {
        mesInstitutionAuditDao.updateMesInstitutionAudit(mesInstitutionAudit);
    }
    @Transactional
    public void deleteMesInstitutionAudit(String id) {
        mesInstitutionAuditDao.deleteMesInstitutionAudit(id);
    }
    @Transactional
    public void deleteMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit) {
        mesInstitutionAuditDao.deleteMesInstitutionAuditByCondition(mesInstitutionAudit);
    }
    @Transactional
    public void batchSaveMesInstitutionAudit(List<MesInstitutionAudit> mesInstitutionAudits){
        mesInstitutionAudits.forEach(mesInstitutionAudit -> mesInstitutionAudit.setId(sequenceId.nextId()));
        mesInstitutionAuditDao.batchSaveMesInstitutionAudit(mesInstitutionAudits);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public Map lookMesInstitutionAuditById(String auditId) {
        MesInspectRecord mesInspectRecord = mesInspectRecordDao.selectMesInspectRecordById(auditId);
        MesAttachFile mesAttachFile = new MesAttachFile();
        mesAttachFile.setReferenceId(mesInspectRecord.getId());
        mesInspectRecord.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
        MesOperateRecord mesOperateRecordForAppeal = mesOperateRecordDao.selectMesOperateRecordForAppeal(auditId);
        mesAttachFile.setReferenceId(mesOperateRecordForAppeal.getId());
        mesOperateRecordForAppeal.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
        List<MesOperateRecord> mesOperateRecordsForHistory = mesOperateRecordDao.selectMesOperateRecordExceptLatestAppeal(auditId,mesOperateRecordForAppeal.getId());
        mesOperateRecordsForHistory.forEach(m->{
            mesAttachFile.setReferenceId(m.getId());
            m.setMesAttachFiles(mesAttachFileDao.findMesAttachFileListByCondition(mesAttachFile));
        });
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("mesInspectRecord",mesInspectRecord);
        map.put("mesOperateRecordForAppeal",mesOperateRecordForAppeal);
        map.put("mesOperateRecordsForHistory",mesOperateRecordsForHistory);
        return map;
    }

    @Transactional(readOnly = true)
    public List<MesInstitutionAudit> selectNoAudit() {
        String dateTime = DateUtils.getOriginalDateTime(DateUtil.format(DateUtil.offsetDay(DateTime.now(), -1),
                Constant.DATE_FORMATTER_DAY),1);
        return mesInstitutionAuditDao.selectAuditStatusZero(dateTime);
    }



    @Transactional(rollbackFor = Exception.class)
    public void saveState(MesInstitutionAudit mesInstitutionAudit) {
        //首先判断是否是再次申述
        MesInstitutionAudit find = new MesInstitutionAudit();
        find.setRecordId(mesInstitutionAudit.getRecordId());
        MesInstitutionAudit audit = mesInstitutionAuditDao.findOneMesInstitutionAuditByCondition(find);
        String mit = sequenceId.nextId();
        if(Objects.isNull(audit)){
            //第一次申述
            mesInstitutionAudit.setId(mit);
            mesInstitutionAuditDao.saveMesInstitutionAudit(mesInstitutionAudit);
        }else {
            //第二次申述
            mit = audit.getId();
            audit.setClaimantName(mesInstitutionAudit.getClaimantName());
            audit.setStatus(mesInstitutionAudit.getStatus());
            mesInstitutionAuditDao.updateMesInstitutionAudit(audit);
        }

        //再次插入操作记录表
        MesOperateRecord mesOperateRecord = new MesOperateRecord();
        String mor = sequenceId.nextId();
        mesOperateRecord.setId(mor);
        mesOperateRecord.setAuditId(mit);
        mesOperateRecord.setOperatorId(mesInstitutionAudit.getClaimantId());
        mesOperateRecord.setOperateContent(mesInstitutionAudit.getOperateContent());
        mesOperateRecord.setOperateType(0);
        mesOperateRecord.setOperateStatus(0);
        mesOperateRecord.setSchoolId(mesInstitutionAudit.getSchoolId());
        mesOperateRecord.setOperatorName(mesInstitutionAudit.getClaimantName());
        mesOperateRecordDao.saveMesOperateRecord(mesOperateRecord);
        //接着插入相关的附件
        if(CollectionUtil.isNotEmpty(mesInstitutionAudit.getMesAttachFiles())){
            mesInstitutionAudit.getMesAttachFiles().forEach(x ->{
                x.setId(sequenceId.nextId());
                x.setReferenceId(mor);
                x.setSchoolId(mesInstitutionAudit.getSchoolId());
                mesAttachFileDao.saveMesAttachFile(x);
            });
        }
    }


    public List<String> selectTeacherIdsByAuditId(String auditId) {
        return mesOperateRecordDao.selectTeacherIdsByAuditId(auditId);
    }

    public List<Map> selectTeacherIdsByRecordId(List<MesInstitutionAudit> mesInstitutionAudits) {
        return select(mesInstitutionAudits);
    }

    @Transactional
    public void saveMesOperateRecordBySchoolId(String schoolId) {
        List<MesInstitutionAudit> mesInstitutionAudits = mesInstitutionAuditDao.selectMesInstitutionAuditListBySchoolId(schoolId);
        if(!CollectionUtils.isEmpty(mesInstitutionAudits)){
            List<Map> maps = select(mesInstitutionAudits);
            maps.forEach(map -> {
                final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId((String[]) map.get("teacherIds"),"德育-审核通知","您有一条申诉审核通过，请及时查看！", Extras.MES_INSPECT_RECORD,(String) map.get("recordId"));
                stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
            });
            ArrayList<MesOperateRecord> mesOperateRecords = new ArrayList<>(mesInstitutionAudits.size());
            mesInstitutionAudits.forEach(m->{
                MesOperateRecord operateRecord = new MesOperateRecord();
                operateRecord.setOperatorName("系统管理员");
                operateRecord.setAuditId(m.getId());
                operateRecord.setOperateContent("评比规则已更新，不纳入统计");
                operateRecord.setOperateDate(DateUtils.Nowss());
                operateRecord.setCreateTime(DateUtils.Nowss());
                operateRecord.setOperateStatus(1);
                operateRecord.setOperateType(1);
                operateRecord.setSchoolId(m.getSchoolId());
                operateRecord.setId(sequenceId.nextId());
                mesOperateRecords.add(operateRecord);
            });
            mesInstitutionAuditDao.updateStatusBySchoolId(schoolId);
            mesOperateRecords.forEach(m->{
                curSchoolYearService.setSchoolYearTerm(m,schoolId);
            });
            mesOperateRecordDao.batchSaveMesOperateRecord(mesOperateRecords);
            List<String> recordIds = mesInstitutionAudits.stream().map(MesInstitutionAudit::getRecordId).collect(Collectors.toList());
            mesInspectRecordDao.updateMesInspectRecordScoreToZeroByIds(recordIds);
        }

    }


    public List<Map> select(List<MesInstitutionAudit> mesInstitutionAudits){
        ArrayList<Map> list = new ArrayList<>(mesInstitutionAudits.size());
        mesInstitutionAudits.forEach(m->{
            HashMap<String, Object> map = new HashMap<>(2);
            List<String> teacherIdList = mesOperateRecordDao.selectTeacherIdsByAuditId(m.getId());
            String[] teacherIds = teacherIdList.toArray(new String[teacherIdList.size()]);
            map.put("teacherIds",teacherIds);
            map.put("recordId",m.getRecordId());
            list.add(map);

        });
        return list;
    }

    public long findMesInstitutionAuditListCountByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditDao.findMesInstitutionAuditListCountByCondition(mesInstitutionAudit);
    }

    public Map findCurrentSchoolYear(String schoolId) {
        SchoolYear currentSchoolYear = mesCustomTitleDao.findCurrentSchoolYear(schoolId);
        LocalDate localDate = LocalDate.parse(currentSchoolYear.getLastTermBegin()).plusYears(1).minusDays(1);
        String endDate = localDate.toString();
        HashMap<String, String> map = new HashMap<>(2);
        map.put("beginDate",currentSchoolYear.getLastTermBegin());
        map.put("endDate",endDate);
        return map;
    }
}
