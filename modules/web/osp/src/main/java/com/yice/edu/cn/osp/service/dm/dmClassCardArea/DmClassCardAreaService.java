package com.yice.edu.cn.osp.service.dm.dmClassCardArea;

import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import com.yice.edu.cn.osp.feignClient.dm.dmClassCardArea.DmClassCardAreaFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassCardAreaService {
    @Autowired
    private DmClassCardAreaFeign dmClassCardAreaFeign;

    public DmClassCardArea findDmClassCardAreaById(String id) {
        return dmClassCardAreaFeign.findDmClassCardAreaById(id);
    }

    public DmClassCardArea saveDmClassCardArea(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaFeign.saveDmClassCardArea(dmClassCardArea);
    }

    public List<DmClassCardArea> findDmClassCardAreaListByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaFeign.findDmClassCardAreaListByCondition(dmClassCardArea);
    }

    public DmClassCardArea findOneDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaFeign.findOneDmClassCardAreaByCondition(dmClassCardArea);
    }

    public long findDmClassCardAreaCountByCondition(DmClassCardArea dmClassCardArea) {
        return dmClassCardAreaFeign.findDmClassCardAreaCountByCondition(dmClassCardArea);
    }

    public void updateDmClassCardArea(DmClassCardArea dmClassCardArea) {
        dmClassCardAreaFeign.updateDmClassCardArea(dmClassCardArea);
    }

    public void deleteDmClassCardArea(String id) {
        dmClassCardAreaFeign.deleteDmClassCardArea(id);
    }

    public void deleteDmClassCardAreaByCondition(DmClassCardArea dmClassCardArea) {
        dmClassCardAreaFeign.deleteDmClassCardAreaByCondition(dmClassCardArea);
    }
}
