package com.yice.edu.cn.osp.service.dm.dmHonourRollType;

import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import com.yice.edu.cn.osp.feignClient.dm.dmHonourRollType.DmHonourRollTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmHonourRollTypeService {
    @Autowired
    private DmHonourRollTypeFeign dmHonourRollTypeFeign;

    public DmHonourRollType findDmHonourRollTypeById(String id) {
        return dmHonourRollTypeFeign.findDmHonourRollTypeById(id);
    }

    public DmHonourRollType saveDmHonourRollType(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeFeign.saveDmHonourRollType(dmHonourRollType);
    }

    public List<DmHonourRollType> findDmHonourRollTypeListByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeFeign.findDmHonourRollTypeListByCondition(dmHonourRollType);
    }

    public DmHonourRollType findOneDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeFeign.findOneDmHonourRollTypeByCondition(dmHonourRollType);
    }

    public long findDmHonourRollTypeCountByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeFeign.findDmHonourRollTypeCountByCondition(dmHonourRollType);
    }

    public void updateDmHonourRollType(DmHonourRollType dmHonourRollType) {
        dmHonourRollTypeFeign.updateDmHonourRollType(dmHonourRollType);
    }

    public void deleteDmHonourRollType(String id) {
        dmHonourRollTypeFeign.deleteDmHonourRollType(id);
    }

    public void deleteDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType) {
        dmHonourRollTypeFeign.deleteDmHonourRollTypeByCondition(dmHonourRollType);
    }
}
