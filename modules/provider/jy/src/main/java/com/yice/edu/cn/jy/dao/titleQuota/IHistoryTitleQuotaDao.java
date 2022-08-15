package com.yice.edu.cn.jy.dao.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IHistoryTitleQuotaDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition(HistoryTitleQuota historyTitleQuota);

    long findHistoryTitleQuotaCountByCondition(HistoryTitleQuota historyTitleQuota);

    HistoryTitleQuota findOneHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota);

    HistoryTitleQuota findHistoryTitleQuotaById(@Param("id") String id);

    void saveHistoryTitleQuota(HistoryTitleQuota historyTitleQuota);

    void updateHistoryTitleQuota(HistoryTitleQuota historyTitleQuota);

    void updateHistoryTitleQuotaForAll(HistoryTitleQuota historyTitleQuota);

    void deleteHistoryTitleQuota(@Param("id") String id);

    void deleteHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota);

    void batchSaveHistoryTitleQuota(List<HistoryTitleQuota> historyTitleQuotas);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota);

    List<HistoryTitleQuota> findHistoryTitleQuotasByConditionNoSchoolId(HistoryTitleQuota historyTitleQuota);
}
