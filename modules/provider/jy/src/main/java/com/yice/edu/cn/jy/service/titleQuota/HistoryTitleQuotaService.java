package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.jy.dao.titleQuota.IHistoryTitleQuotaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryTitleQuotaService {
    @Autowired
    private IHistoryTitleQuotaDao historyTitleQuotaDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public HistoryTitleQuota findHistoryTitleQuotaById(String id) {
        return historyTitleQuotaDao.findHistoryTitleQuotaById(id);
    }
    @Transactional
    public void saveHistoryTitleQuota(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuota.setId(sequenceId.nextId());
        historyTitleQuotaDao.saveHistoryTitleQuota(historyTitleQuota);
    }
    @Transactional(readOnly = true)
    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaDao.findHistoryTitleQuotaListByCondition(historyTitleQuota);
    }
    @Transactional(readOnly = true)
    public HistoryTitleQuota findOneHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaDao.findOneHistoryTitleQuotaByCondition(historyTitleQuota);
    }
    @Transactional(readOnly = true)
    public long findHistoryTitleQuotaCountByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaDao.findHistoryTitleQuotaCountByCondition(historyTitleQuota);
    }
    @Transactional
    public void updateHistoryTitleQuota(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaDao.updateHistoryTitleQuota(historyTitleQuota);
    }
    @Transactional
    public void updateHistoryTitleQuotaForAll(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaDao.updateHistoryTitleQuotaForAll(historyTitleQuota);
    }
    @Transactional
    public void deleteHistoryTitleQuota(String id) {
        historyTitleQuotaDao.deleteHistoryTitleQuota(id);
    }
    @Transactional
    public void deleteHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaDao.deleteHistoryTitleQuotaByCondition(historyTitleQuota);
    }
    @Transactional
    public void batchSaveHistoryTitleQuota(List<HistoryTitleQuota> historyTitleQuotas){
        historyTitleQuotas.forEach(historyTitleQuota -> historyTitleQuota.setId(sequenceId.nextId()));
        historyTitleQuotaDao.batchSaveHistoryTitleQuota(historyTitleQuotas);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaDao.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
    }

    public List<HistoryTitleQuota> findHistoryTitleQuotasByConditionNoSchoolId(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaDao.findHistoryTitleQuotasByConditionNoSchoolId(historyTitleQuota);
    }
}
