package com.yice.edu.cn.ecc.service.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.ecc.feignClient.school.DmSchoolHonourFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmSchoolHonourService {
    @Autowired
    private DmSchoolHonourFeign dmSchoolHonourFeign;

    public DmSchoolHonour findDmSchoolHonourById(String id) {
        return dmSchoolHonourFeign.findDmSchoolHonourById(id);
    }

    public List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.findDmSchoolHonourListByCondition(dmSchoolHonour);
    }

    public long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.findDmSchoolHonourCountByCondition(dmSchoolHonour);
    }
}
