package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.tap.feignClient.oa.OaStatsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OaStatsService {
    @Autowired
    private OaStatsFeign oaStatsFeign;

    public ResponseJson findTotalStats(OaStats os, String schoolId) {
        return oaStatsFeign.findTotalStats(schoolId,os);
    }

    public ResponseJson findStatsDetail(ProcessBusinessData processBusinessData) {
        return oaStatsFeign.findStatsDetail(processBusinessData,false);
    }

    public ResponseJson findTotalMoney(ProcessBusinessData processBusinessData) {
        return oaStatsFeign.findTotalMoney(processBusinessData);
    }
}
