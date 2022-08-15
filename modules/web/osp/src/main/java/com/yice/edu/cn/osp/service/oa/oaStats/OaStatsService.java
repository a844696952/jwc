package com.yice.edu.cn.osp.service.oa.oaStats;

import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.osp.feignClient.oa.oaStats.OaStatsFeign;
import com.yice.edu.cn.osp.feignClient.oa.schoolProcess.SchoolProcessFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OaStatsService {
    @Autowired
    private OaStatsFeign oaStatsFeign;
    @Autowired
    private SchoolProcessFeign schoolProcessFeign;

    public ResponseJson findTotalStats(OaStats oaStats, String schoolId) {
        return oaStatsFeign.findTotalStats(schoolId,oaStats);
    }

    public ResponseJson findStatsDetail(ProcessBusinessData processBusinessData) {
        //获取学校流程id
        if(StrUtil.isEmpty(processBusinessData.getSchoolProcessId())){
            SchoolProcess sp = new SchoolProcess();
            sp.setSchoolId(processBusinessData.getSchoolId());
            sp.setProcessLibId("20181027163655001");//默认请假流程
            sp = schoolProcessFeign.findOneSchoolProcessByCondition(sp);
            if (Objects.isNull(sp)) {
                return new ResponseJson(false,"学校中不存在此流程");
            }
            processBusinessData.setSchoolProcessId(sp.getId());
        }
        return oaStatsFeign.findStatsDetail(processBusinessData,true);
    }

    public ResponseJson findProcessesByRangeTime(String id, Pager pager) {
        return oaStatsFeign.findProcessesByRangeTime(id,pager);
    }

    public ResponseJson findTotalMoney(ProcessBusinessData processBusinessData) {
        return oaStatsFeign.findTotalMoney(processBusinessData);
    }
}
