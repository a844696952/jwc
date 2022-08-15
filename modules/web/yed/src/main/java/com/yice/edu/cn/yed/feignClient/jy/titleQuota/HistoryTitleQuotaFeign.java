package com.yice.edu.cn.yed.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "historyTitleQuotaFeign",path = "/historyTitleQuota")
public interface HistoryTitleQuotaFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findHistoryTitleQuotaById/{id}")
    HistoryTitleQuota findHistoryTitleQuotaById(@PathVariable("id") String id);
    @PostMapping("/saveHistoryTitleQuota")
    HistoryTitleQuota saveHistoryTitleQuota(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/batchSaveHistoryTitleQuota")
    void batchSaveHistoryTitleQuota(List<HistoryTitleQuota> historyTitleQuotas);
    @PostMapping("/findHistoryTitleQuotaListByCondition")
    List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/findOneHistoryTitleQuotaByCondition")
    HistoryTitleQuota findOneHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/findHistoryTitleQuotaCountByCondition")
    long findHistoryTitleQuotaCountByCondition(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/updateHistoryTitleQuota")
    void updateHistoryTitleQuota(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/updateHistoryTitleQuotaForNotNull")
    void updateHistoryTitleQuotaForAll(HistoryTitleQuota historyTitleQuota);
    @GetMapping("/deleteHistoryTitleQuota/{id}")
    void deleteHistoryTitleQuota(@PathVariable("id") String id);
    @PostMapping("/deleteHistoryTitleQuotaByCondition")
    void deleteHistoryTitleQuotaByCondition(HistoryTitleQuota historyTitleQuota);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findHistoryTitleQuotaListByCondition4Like")
    List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota);
    @PostMapping("/findHistoryTitleQuotasByConditionNoSchoolId")
    List<HistoryTitleQuota> findHistoryTitleQuotasByConditionNoSchoolId(HistoryTitleQuota historyTitleQuota);
}
