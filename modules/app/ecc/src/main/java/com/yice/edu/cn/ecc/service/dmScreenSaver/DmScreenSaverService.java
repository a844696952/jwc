package com.yice.edu.cn.ecc.service.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.ecc.feignClient.dmScreenSaver.DmScreenSaverFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmScreenSaverService {
    @Autowired
    private DmScreenSaverFeign dmScreenSaverFeign;

    public DmScreenSaver findDmScreenSaverById(String id) {
        return dmScreenSaverFeign.findDmScreenSaverById(id);
    }

    public DmScreenSaver saveDmScreenSaver(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.saveDmScreenSaver(dmScreenSaver);
    }

    public List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.findDmScreenSaverListByCondition(dmScreenSaver);
    }

    public DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.findOneDmScreenSaverByCondition(dmScreenSaver);
    }

    public long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.findDmScreenSaverCountByCondition(dmScreenSaver);
    }

    public void updateDmScreenSaver(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.updateDmScreenSaver(dmScreenSaver);
    }

    public void deleteDmScreenSaver(String id) {
        dmScreenSaverFeign.deleteDmScreenSaver(id);
    }

    public void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver) {
        dmScreenSaverFeign.deleteDmScreenSaverByCondition(dmScreenSaver);
    }
    public DmScreenSaver getRunNingDmScreenSaver(DmScreenSaver dmScreenSaver) {
        return dmScreenSaverFeign.getRunNingDmScreenSaver(dmScreenSaver);
    }
    public void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver){
        dmScreenSaverFeign.batchUpdateDmScreenSaverStatus(dmScreenSaver);
    }
}
