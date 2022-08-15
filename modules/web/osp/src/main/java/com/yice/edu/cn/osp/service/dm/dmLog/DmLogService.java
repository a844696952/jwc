package com.yice.edu.cn.osp.service.dm.dmLog;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.osp.feignClient.dm.dmLog.DmLogFeign;
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

    public DmLog saveDmLog(DmLog dmLog) {
        return dmLogFeign.saveDmLog(dmLog);
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

    public void updateDmLog(DmLog dmLog) {
        dmLogFeign.updateDmLog(dmLog);
    }

    public void deleteDmLog(String id) {
        dmLogFeign.deleteDmLog(id);
    }

    public void deleteDmLogByCondition(DmLog dmLog) {
        dmLogFeign.deleteDmLogByCondition(dmLog);
    }

    public void batchSaveDmLog(List<DmLog> dmLogs){
        dmLogFeign.batchSaveDmLog(dmLogs);
    }
}
