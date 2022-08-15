package com.yice.edu.cn.yed.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.yed.feignClient.jy.titleQuota.HistoryTitleQuotaFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryTitleQuotaService {
    @Autowired
    private HistoryTitleQuotaFeign historyTitleQuotaFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public HistoryTitleQuota findHistoryTitleQuotaById(String id) {
        return historyTitleQuotaFeign.findHistoryTitleQuotaById(id);
    }

    public HistoryTitleQuota saveHistoryTitleQuota(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.saveHistoryTitleQuota(historyTitleQuota);
    }

    public void batchSaveHistoryTitleQuota(List<HistoryTitleQuota> historyTitleQuotas){
        historyTitleQuotaFeign.batchSaveHistoryTitleQuota(historyTitleQuotas);
    }

    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findHistoryTitleQuotaListByCondition(historyTitleQuota);
    }

    public HistoryTitleQuota findOneHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findOneHistoryTitleQuotaByCondition(historyTitleQuota);
    }

    public long findHistoryTitleQuotaCountByCondition(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findHistoryTitleQuotaCountByCondition(historyTitleQuota);
    }

    public void updateHistoryTitleQuota(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaFeign.updateHistoryTitleQuota(historyTitleQuota);
    }

    public void updateHistoryTitleQuotaForAll(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaFeign.updateHistoryTitleQuotaForAll(historyTitleQuota);
    }

    public void deleteHistoryTitleQuota(String id) {
        historyTitleQuotaFeign.deleteHistoryTitleQuota(id);
    }

    public void deleteHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota) {
        historyTitleQuotaFeign.deleteHistoryTitleQuotaByCondition(historyTitleQuota);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
    }

    public List<HistoryTitleQuota> findHistoryTitleQuotasByConditionNoSchoolId(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findHistoryTitleQuotasByConditionNoSchoolId(historyTitleQuota);
    }
}
