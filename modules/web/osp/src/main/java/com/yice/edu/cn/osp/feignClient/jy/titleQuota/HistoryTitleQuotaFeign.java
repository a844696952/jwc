package com.yice.edu.cn.osp.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "historyTitleQuotaFeign",path = "/historyTitleQuota")
public interface HistoryTitleQuotaFeign {

    @PostMapping("/findHistoryTitleQuotaListByCondition4Like")
    List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota);
}
