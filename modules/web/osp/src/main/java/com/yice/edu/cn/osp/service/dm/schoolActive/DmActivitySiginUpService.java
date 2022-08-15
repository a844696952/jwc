package com.yice.edu.cn.osp.service.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.osp.feignClient.dm.schoolActive.DmActivitySiginUpFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmActivitySiginUpService {
    @Autowired
    private DmActivitySiginUpFeign dmActivitySiginUpFeign;

    public DmActivitySiginUp findDmActivitySiginUpById(String id) {
        return dmActivitySiginUpFeign.findDmActivitySiginUpById(id);
    }

    public DmActivitySiginUp saveDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpFeign.saveDmActivitySiginUp(dmActivitySiginUp);
    }

    public List<DmActivitySiginUp> findDmActivitySiginUpListByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpFeign.findDmActivitySiginUpListByCondition(dmActivitySiginUp);
    }

    public DmActivitySiginUp findOneDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpFeign.findOneDmActivitySiginUpByCondition(dmActivitySiginUp);
    }

    public long findDmActivitySiginUpCountByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpFeign.findDmActivitySiginUpCountByCondition(dmActivitySiginUp);
    }

    public void updateDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp) {
        dmActivitySiginUpFeign.updateDmActivitySiginUp(dmActivitySiginUp);
    }

    public void deleteDmActivitySiginUp(String id) {
        dmActivitySiginUpFeign.deleteDmActivitySiginUp(id);
    }

    public void deleteDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp) {
        dmActivitySiginUpFeign.deleteDmActivitySiginUpByCondition(dmActivitySiginUp);
    }
}
