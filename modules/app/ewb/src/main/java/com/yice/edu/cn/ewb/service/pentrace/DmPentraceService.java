package com.yice.edu.cn.ewb.service.pentrace;

import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import com.yice.edu.cn.ewb.feignClient.pentrace.DmPentraceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmPentraceService {
    @Autowired
    private DmPentraceFeign dmPentraceFeign;

    public DmPentrace findDmPentraceById(String id) {
        return dmPentraceFeign.findDmPentraceById(id);
    }

    public DmPentrace saveDmPentrace(DmPentrace dmPentrace) {
        return dmPentraceFeign.saveDmPentrace(dmPentrace);
    }

    public List<DmPentrace> findDmPentraceListByCondition(DmPentrace dmPentrace) {
        return dmPentraceFeign.findDmPentraceListByCondition(dmPentrace);
    }

    public DmPentrace findOneDmPentraceByCondition(DmPentrace dmPentrace) {
        return dmPentraceFeign.findOneDmPentraceByCondition(dmPentrace);
    }

    public long findDmPentraceCountByCondition(DmPentrace dmPentrace) {
        return dmPentraceFeign.findDmPentraceCountByCondition(dmPentrace);
    }

    public void updateDmPentrace(DmPentrace dmPentrace) {
        dmPentraceFeign.updateDmPentrace(dmPentrace);
    }

    public void deleteDmPentrace(String id) {
        dmPentraceFeign.deleteDmPentrace(id);
    }

    public void deleteDmPentraceByCondition(DmPentrace dmPentrace) {
        dmPentraceFeign.deleteDmPentraceByCondition(dmPentrace);
    }
}
