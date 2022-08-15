package com.yice.edu.cn.ecc.service.active;

import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.ecc.feignClient.active.DmActiveFeign;
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

    public List<DmActive> findDmActiveListByConditionVue(DmActive dmActive) {
        return dmActiveFeign.findDmActiveListByConditionVue(dmActive);
    }
}
