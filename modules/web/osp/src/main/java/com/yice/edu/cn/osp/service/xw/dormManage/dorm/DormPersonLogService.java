package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormPersonLogFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonLogService {
    @Autowired
    private DormPersonLogFeign dormPersonLogFeign;

    public DormPersonLog findDormPersonLogById(String id) {
        return dormPersonLogFeign.findDormPersonLogById(id);
    }

    public DormPersonLog saveDormPersonLog(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.saveDormPersonLog(dormPersonLog);
    }

    public List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.findDormPersonLogListByCondition(dormPersonLog);
    }

    public DormPersonLog findOneDormPersonLogByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.findOneDormPersonLogByCondition(dormPersonLog);
    }

    public long findDormPersonLogCountByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.findDormPersonLogCountByCondition(dormPersonLog);
    }

    public void updateDormPersonLog(DormPersonLog dormPersonLog) {
        dormPersonLogFeign.updateDormPersonLog(dormPersonLog);
    }

    public void deleteDormPersonLog(String id) {
        dormPersonLogFeign.deleteDormPersonLog(id);
    }

    public void deleteDormPersonLogByCondition(DormPersonLog dormPersonLog) {
        dormPersonLogFeign.deleteDormPersonLogByCondition(dormPersonLog);
    }
}
