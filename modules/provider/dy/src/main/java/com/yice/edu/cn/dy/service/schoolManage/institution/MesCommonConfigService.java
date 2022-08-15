package com.yice.edu.cn.dy.service.schoolManage.institution;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCommonConfig;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCommonConfigDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCustomTitleDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesInstitutionDao;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesTimeStatusDao;
import com.yice.edu.cn.dy.service.schoolManage.audit.MesInstitutionAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MesCommonConfigService {
    @Autowired
    private IMesCommonConfigDao mesCommonConfigDao;
    @Autowired
    private IMesTimeStatusDao mesTimeStatusDao;
    @Autowired
    private IMesInstitutionDao mesInstitutionDao;
    @Autowired
    private IMesCustomTitleDao mesCustomTitleDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private MesInstitutionAuditService mesInstitutionAuditService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesCommonConfig findMesCommonConfigById(String id) {
        return mesCommonConfigDao.findMesCommonConfigById(id);
    }

    @Transactional
    public void saveMesCommonConfig(MesCommonConfig mesCommonConfig) {
        MesTimeStatus mesTimeStatus = new MesTimeStatus();
        mesTimeStatus.setSchoolId(mesCommonConfig.getSchoolId());
        mesTimeStatus.setStatus(1);
        mesTimeStatus = mesTimeStatusDao.findOneMesTimeStatusByCondition(mesTimeStatus);
        if (mesTimeStatus!=null){
            mesTimeStatus.setStatus(0);
            mesTimeStatus.setEndTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            mesTimeStatusDao.updateMesTimeStatus(mesTimeStatus);
        }
        MesTimeStatus mesTimeStatus1 = new MesTimeStatus();
        String id = sequenceId.nextId();
        mesTimeStatus1.setId(id);
        mesTimeStatus1.setSchoolId(mesCommonConfig.getSchoolId());
        mesTimeStatus1.setStatus(1);
        mesTimeStatusDao.saveMesTimeStatus(mesTimeStatus1);
        mesCommonConfig.setId(sequenceId.nextId());
        mesCommonConfig.setTimeStatusId(id);
        MesInstitution mesInstitution = new MesInstitution();
        mesInstitution.setSchoolId(mesCommonConfig.getSchoolId());
        mesInstitution.setTimeStatusId(id);
        mesInstitutionDao.updateMesInstitutionTimeStatusId(mesInstitution);
        mesCommonConfigDao.saveMesCommonConfig(mesCommonConfig);
        Map<String, List<MesCustomTitle>> map = mesCommonConfig.getMesCustomTitleMap();
        if (CollUtil.isEmpty(map)) {
            return;
        }
        List<MesCustomTitle> mesCustomTitleList = new ArrayList<>();
        if (mesCommonConfig.getIsByRank() == 1) {
            if (mesCommonConfig.getIsFirstInstitution() == 1) {
                mesCustomTitleList.addAll(map.get("mesCustomTitleFirstRank"));
            }
            if (mesCommonConfig.getIsByTotal() == 1) {
                mesCustomTitleList.addAll(map.get("mesCustomTitleTotalRank"));
            }
        } else if (mesCommonConfig.getIsByReach() == 1) {
            if (mesCommonConfig.getIsByTotal() == 1) {
                mesCustomTitleList.addAll(map.get("mesCustomTitleTotalReach"));

            }
            if (mesCommonConfig.getIsFirstInstitution() == 1) {
                mesCustomTitleList.addAll(map.get("mesCustomTitleFirstReach"));
            }
        }
        if (CollUtil.isEmpty(mesCustomTitleList)) {
            return;
        }
        for (MesCustomTitle mesCustomTitle : mesCustomTitleList) {
            mesCustomTitle.setId(sequenceId.nextId());
            mesCustomTitle.setSchoolId(mesCommonConfig.getSchoolId());
            mesCustomTitle.setTimeStatusId(id);
        }
        mesCustomTitleDao.batchSaveMesCustomTitle(mesCustomTitleList);
        mesInstitutionAuditService.saveMesOperateRecordBySchoolId(mesCommonConfig.getSchoolId());
    }

    @Transactional(readOnly = true)
    public List<MesCommonConfig> findMesCommonConfigListByCondition(MesCommonConfig mesCommonConfig) {
        List<MesCommonConfig> list = mesCommonConfigDao.findMesCommonConfigListByCondition(mesCommonConfig);
        if (CollUtil.isEmpty(list)){
            return list;
        }
        MesCommonConfig commonConfig = list.get(0);
        Map<String, List<MesCustomTitle>> map = new HashMap<>();
        if (commonConfig.getIsByRank() == 1) {
            if (commonConfig.getIsFirstInstitution() == 1) {
                List<MesCustomTitle> mesCustomTitleList = mesCustomTitleDao.findMesCustomTitleListByFirstRank(mesCommonConfig.getSchoolId());
                map.put("mesCustomTitleFirstRank",mesCustomTitleList);
            }
            if (commonConfig.getIsByTotal() == 1) {
                List<MesCustomTitle> mesCustomTitleList = mesCustomTitleDao.findMesCustomTitleListByTotalRank(mesCommonConfig.getSchoolId());
                map.put("mesCustomTitleTotalRank",mesCustomTitleList);
            }
        } else if (commonConfig.getIsByReach() == 1) {
            if (commonConfig.getIsByTotal() == 1) {
                List<MesCustomTitle> mesCustomTitleList = mesCustomTitleDao.findMesCustomTitleListByTotalReach(mesCommonConfig.getSchoolId());
                map.put("mesCustomTitleTotalReach",mesCustomTitleList);
            }
            if (commonConfig.getIsFirstInstitution() == 1) {
                List<MesCustomTitle> mesCustomTitleList = mesCustomTitleDao.findMesCustomTitleListByFirstReach(mesCommonConfig.getSchoolId());
                map.put("mesCustomTitleFirstReach",mesCustomTitleList);
            }
        }
        for (MesCommonConfig config : list) {
            config.setMesCustomTitleMap(map);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public MesCommonConfig findOneMesCommonConfigByCondition(MesCommonConfig mesCommonConfig) {
        MesCommonConfig oneMesCommonConfigByCondition = mesCommonConfigDao.findOneMesCommonConfigByCondition(mesCommonConfig);
        if (Objects.isNull(oneMesCommonConfigByCondition)){
            return null;
        }
        if (oneMesCommonConfigByCondition.getIsFirstInstitution()==1){
            MesInstitution mesInstitution = new MesInstitution();
            mesInstitution.setSchoolId(mesCommonConfig.getSchoolId());
            mesInstitution.setParentId("-1");
            List<MesInstitution> mesInstitutionsByCondition = mesInstitutionDao.findMesInstitutionsByCondition(mesInstitution);
            oneMesCommonConfigByCondition.setMesInstitutions(mesInstitutionsByCondition);
        }
        return oneMesCommonConfigByCondition;
    }

    @Transactional(readOnly = true)
    public long findMesCommonConfigCountByCondition(MesCommonConfig mesCommonConfig) {
        return mesCommonConfigDao.findMesCommonConfigCountByCondition(mesCommonConfig);
    }

    @Transactional
    public void updateMesCommonConfig(MesCommonConfig mesCommonConfig) {
        mesCommonConfigDao.updateMesCommonConfig(mesCommonConfig);
    }

    @Transactional
    public void deleteMesCommonConfig(String id) {
        mesCommonConfigDao.deleteMesCommonConfig(id);
    }

    @Transactional
    public void deleteMesCommonConfigByCondition(MesCommonConfig mesCommonConfig) {
        mesCommonConfigDao.deleteMesCommonConfigByCondition(mesCommonConfig);
    }

    @Transactional
    public void batchSaveMesCommonConfig(List<MesCommonConfig> mesCommonConfigs) {
        mesCommonConfigs.forEach(mesCommonConfig -> mesCommonConfig.setId(sequenceId.nextId()));
        mesCommonConfigDao.batchSaveMesCommonConfig(mesCommonConfigs);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
