package com.yice.edu.cn.ecc.service.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.ecc.feignClient.classCard.DmLogFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmLogService {
    @Autowired
    private DmLogFeign dmLogFeign;

    public DmLog findDmLogById(String id) {
        return dmLogFeign.findDmLogById(id);
    }

    public List<DmLog> findDmLogListByCondition(DmLog dmLog) {
        return dmLogFeign.findDmLogListByCondition(dmLog);
    }

    public DmLog findOneDmLogByCondition(DmLog dmLog) {
        return dmLogFeign.findOneDmLogByCondition(dmLog);
    }

    public long findDmLogCountByCondition(DmLog dmLog) {
        return dmLogFeign.findDmLogCountByCondition(dmLog);
    }

}
