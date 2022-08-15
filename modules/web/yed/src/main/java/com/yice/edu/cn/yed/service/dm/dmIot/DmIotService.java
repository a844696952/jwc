package com.yice.edu.cn.yed.service.dm.dmIot;

import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import com.yice.edu.cn.yed.feignClient.dm.dmIot.DmIotFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmIotService {
    @Autowired
    private DmIotFeign dmIotFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmIot findDmIotById(String id) {
        return dmIotFeign.findDmIotById(id);
    }

    public DmIot saveDmIot(DmIot dmIot) {
        return dmIotFeign.saveDmIot(dmIot);
    }

    public void batchSaveDmIot(List<DmIot> dmIots){
        dmIotFeign.batchSaveDmIot(dmIots);
    }

    public List<DmIot> findDmIotListByCondition(DmIot dmIot) {
        return dmIotFeign.findDmIotListByCondition(dmIot);
    }

    public DmIot findOneDmIotByCondition(DmIot dmIot) {
        return dmIotFeign.findOneDmIotByCondition(dmIot);
    }

    public long findDmIotCountByCondition(DmIot dmIot) {
        return dmIotFeign.findDmIotCountByCondition(dmIot);
    }

    public void updateDmIot(DmIot dmIot) {
        dmIotFeign.updateDmIot(dmIot);
    }

    public void updateDmIotForAll(DmIot dmIot) {
        dmIotFeign.updateDmIotForAll(dmIot);
    }

    public void deleteDmIot(String id) {
        dmIotFeign.deleteDmIot(id);
    }

    public void deleteDmIotByCondition(DmIot dmIot) {
        dmIotFeign.deleteDmIotByCondition(dmIot);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
