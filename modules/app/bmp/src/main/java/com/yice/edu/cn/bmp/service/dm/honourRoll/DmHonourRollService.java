package com.yice.edu.cn.bmp.service.dm.honourRoll;

import com.yice.edu.cn.bmp.feignClient.dm.honourRoll.DmHonourRollFeign;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmHonourRollService {
    @Autowired
    private DmHonourRollFeign dmHonourRollFeign;

    public DmHonourRoll findDmHonourRollById(String id) {
        return dmHonourRollFeign.findDmHonourRollById(id);
    }

    public List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findDmHonourRollListByCondition(dmHonourRoll);
    }
    public DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findOneDmHonourRollByCondition(dmHonourRoll);
    }
    public long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll) {
        return dmHonourRollFeign.findDmHonourRollCountByCondition(dmHonourRoll);
    }


}
