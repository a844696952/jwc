package com.yice.edu.cn.osp.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.osp.feignClient.jy.titleQuota.HistoryTitleQuotaFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryTitleQuotaService {
    @Autowired
    private HistoryTitleQuotaFeign historyTitleQuotaFeign;

    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(HistoryTitleQuota historyTitleQuota) {
        return historyTitleQuotaFeign.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
    }
}
