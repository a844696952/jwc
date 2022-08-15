package com.yice.edu.cn.ecc.service.honourRoll;

import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import com.yice.edu.cn.ecc.feignClient.honourRoll.DmHonourRollStudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmHonourRollStudentService {
    @Autowired
    private DmHonourRollStudentFeign dmHonourRollStudentFeign;

    public List<EccHonourRoll> getHonourRollList(DmHonourRollStudent dmHonourRollStudent) {
        return dmHonourRollStudentFeign.getHonourRollList(dmHonourRollStudent);
    }

}
