package com.yice.edu.cn.osp.service.dm.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.osp.feignClient.dm.active.DmActiveFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmActiveService {
    @Autowired
    private DmActiveFeign dmActiveFeign;

    public DmActive findDmActiveById(String id) {
        return dmActiveFeign.findDmActiveById(id);
    }

    public DmActive saveDmActive(DmActive dmActive) {
        return dmActiveFeign.saveDmActive(dmActive);
    }

    public List<DmActive> findDmActiveListByCondition(DmActive dmActive) {
        return dmActiveFeign.findDmActiveListByCondition(dmActive);
    }

    public DmActive findOneDmActiveByCondition(DmActive dmActive) {
        return dmActiveFeign.findOneDmActiveByCondition(dmActive);
    }

    public long findDmActiveCountByCondition(DmActive dmActive) {
        return dmActiveFeign.findDmActiveCountByCondition(dmActive);
    }

    public void updateDmActive(DmActive dmActive) {
        dmActiveFeign.updateDmActive(dmActive);
    }

    public void deleteDmActive(String id) {
        dmActiveFeign.deleteDmActive(id);
    }

    public void deleteDmActiveByCondition(DmActive dmActive) {
        dmActiveFeign.deleteDmActiveByCondition(dmActive);
    }

    public void deleteManyDmActive(DmActive dmActive) {
        dmActiveFeign.deleteManyDmActive(dmActive);
    }

    public List<DmActive> findDmActiveListByConditionLike(DmActive dmActive) {
        return dmActiveFeign.findDmActiveListByConditionLike(dmActive);
    }

    public long findDmActiveCountByConditionLike(DmActive dmActive) {
        return dmActiveFeign.findDmActiveCountByConditionLike(dmActive);
    }
}
