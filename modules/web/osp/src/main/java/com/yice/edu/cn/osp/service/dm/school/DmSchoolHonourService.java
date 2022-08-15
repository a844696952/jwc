package com.yice.edu.cn.osp.service.dm.school;

import com.yice.edu.cn.common.pojo.dm.school.DmSchoolHonour;
import com.yice.edu.cn.osp.feignClient.dm.school.DmSchoolHonourFeign;
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

    public DmSchoolHonour saveDmSchoolHonour(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.saveDmSchoolHonour(dmSchoolHonour);
    }

    public List<DmSchoolHonour> findDmSchoolHonourListByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.findDmSchoolHonourListByCondition(dmSchoolHonour);
    }

    public DmSchoolHonour findOneDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.findOneDmSchoolHonourByCondition(dmSchoolHonour);
    }

    public long findDmSchoolHonourCountByCondition(DmSchoolHonour dmSchoolHonour) {
        return dmSchoolHonourFeign.findDmSchoolHonourCountByCondition(dmSchoolHonour);
    }

    public void updateDmSchoolHonour(DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourFeign.updateDmSchoolHonour(dmSchoolHonour);
    }

    public void deleteDmSchoolHonour(String id) {
        dmSchoolHonourFeign.deleteDmSchoolHonour(id);
    }

    public void deleteDmSchoolHonourByCondition(DmSchoolHonour dmSchoolHonour) {
        dmSchoolHonourFeign.deleteDmSchoolHonourByCondition(dmSchoolHonour);
    }

    public List<DmSchoolHonour> findDmSchoolHonourByactiveNameLike(DmSchoolHonour dmSchoolHonour){
        return dmSchoolHonourFeign.findDmSchoolHonourByactiveNameLike(dmSchoolHonour);
    }

    public Long findDmSchoolHonourByactiveNameLikeCount(DmSchoolHonour dmSchoolHonour){
        return dmSchoolHonourFeign.findDmSchoolHonourByactiveNameLikeCount(dmSchoolHonour);
    }
}
