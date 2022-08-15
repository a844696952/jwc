package com.yice.edu.cn.dy.service.schoolManage.audit;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.dy.dao.schoolManage.inspectRecord.IMesInspectRecordDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesInstitutionAuditDao;
import com.yice.edu.cn.dy.dao.schoolManage.audit.IMesOperateRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesOperateRecordService {
    @Autowired
    private IMesOperateRecordDao mesOperateRecordDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IMesInstitutionAuditDao mesInstitutionAuditDao;
    @Autowired
    private IMesInspectRecordDao mesInspectRecordDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CurSchoolYearService curSchoolYearService;

/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesOperateRecord findMesOperateRecordById(String id) {
        return mesOperateRecordDao.findMesOperateRecordById(id);
    }
    @Transactional
    public void saveMesOperateRecord(MesOperateRecord mesOperateRecord) {
        mesOperateRecord.setId(sequenceId.nextId());
        mesOperateRecordDao.saveMesOperateRecord(mesOperateRecord);
    }
    @Transactional(readOnly = true)
    public List<MesOperateRecord> findMesOperateRecordListByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordDao.findMesOperateRecordListByCondition(mesOperateRecord);
    }
    @Transactional(readOnly = true)
    public MesOperateRecord findOneMesOperateRecordByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordDao.findOneMesOperateRecordByCondition(mesOperateRecord);
    }
    @Transactional(readOnly = true)
    public long findMesOperateRecordCountByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordDao.findMesOperateRecordCountByCondition(mesOperateRecord);
    }
    @Transactional
    public void updateMesOperateRecord(MesOperateRecord mesOperateRecord) {
        mesOperateRecordDao.updateMesOperateRecord(mesOperateRecord);
    }
    @Transactional
    public void deleteMesOperateRecord(String id) {
        mesOperateRecordDao.deleteMesOperateRecord(id);
    }
    @Transactional
    public void deleteMesOperateRecordByCondition(MesOperateRecord mesOperateRecord) {
        mesOperateRecordDao.deleteMesOperateRecordByCondition(mesOperateRecord);
    }
    @Transactional
    public void batchSaveMesOperateRecord(List<MesOperateRecord> mesOperateRecords){
        if(!CollectionUtil.isEmpty(mesOperateRecords)){
            mesOperateRecords.forEach(mesOperateRecord -> mesOperateRecord.setId(sequenceId.nextId()));
            mesOperateRecords.forEach(m -> {
                curSchoolYearService.setSchoolYearTerm(m,m.getSchoolId());
            });
            mesOperateRecordDao.batchSaveMesOperateRecord(mesOperateRecords);
            List<String> ids = mesOperateRecords.stream().map(MesOperateRecord::getAuditId).collect(Collectors.toList());
            mesInstitutionAuditDao.updateStatusByIds(ids);
        }
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional
    public int passOrRefuse(MesOperateRecord mesOperateRecord) {
        int row = mesInstitutionAuditDao.updateStatus(mesOperateRecord);
        if(row==0){
            return 0;
        }
        if(mesOperateRecord.getOperateStatus()==1){
            mesInspectRecordDao.updateMesInspectRecordScoreToZero(mesOperateRecord.getAuditId());
        }
        mesOperateRecord.setId(sequenceId.nextId());
        mesOperateRecord.setOperateType(1);
        curSchoolYearService.setSchoolYearTerm(mesOperateRecord,mesOperateRecord.getSchoolId());
        mesOperateRecordDao.saveMesOperateRecord(mesOperateRecord);
        String recordId = mesInstitutionAuditDao.selectRecordIdById(mesOperateRecord.getAuditId());
        List<String> teacherIdList = mesOperateRecordDao.selectTeacherIdsByAuditId(mesOperateRecord.getAuditId());
        String[] teacherIds = teacherIdList.toArray(new String[teacherIdList.size()]);
        final Push push = Push.newBuilder(JpushApp.TAP).getSimplePusByRefrenceId(teacherIds,"德育-审核通知",mesOperateRecord.getOperateStatus()==1?"您提交的申述已通过，请及时查看！":"您提交的申诉未通过，请及时查看！", Extras.MES_INSPECT_RECORD,recordId);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        return 1;
    }


}
