package com.yice.edu.cn.ecc.service.studentAspect;

import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import com.yice.edu.cn.ecc.feignClient.studentAspect.DmStudentAspectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DmStudentAspectService {
    @Autowired
    private DmStudentAspectFeign dmStudentAspectFeign;

    public DmStudentAspect findDmStudentAspectById(String id) {
        return dmStudentAspectFeign.findDmStudentAspectById(id);
    }

    public DmStudentAspect saveDmStudentAspect(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectFeign.saveDmStudentAspect(dmStudentAspect);
    }

    public void batchSaveDmStudentAspect(List<DmStudentAspect> dmStudentAspects){
        dmStudentAspectFeign.batchSaveDmStudentAspect(dmStudentAspects);
    }

    public List<DmStudentAspect> findDmStudentAspectListByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectFeign.findDmStudentAspectListByCondition(dmStudentAspect);
    }

    public DmStudentAspect findOneDmStudentAspectByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectFeign.findOneDmStudentAspectByCondition(dmStudentAspect);
    }

    public long findDmStudentAspectCountByCondition(DmStudentAspect dmStudentAspect) {
        return dmStudentAspectFeign.findDmStudentAspectCountByCondition(dmStudentAspect);
    }

    public void updateDmStudentAspect(DmStudentAspect dmStudentAspect) {
        dmStudentAspectFeign.updateDmStudentAspect(dmStudentAspect);
    }

    public void deleteDmStudentAspect(String id) {
        dmStudentAspectFeign.deleteDmStudentAspect(id);
    }

    public void deleteDmStudentAspectByCondition(DmStudentAspect dmStudentAspect) {
        dmStudentAspectFeign.deleteDmStudentAspectByCondition(dmStudentAspect);
    }

    /**---------------------------*/

    public List<DmStudentAspect> findDmStudentAspectList(List<DmStudentAspect> dmStudentAspect) {
        List<DmStudentAspect> list  = new ArrayList<>();
        dmStudentAspect.forEach(x->{
            DmStudentAspect condition = dmStudentAspectFeign.findOneDmStudentAspectByCondition(x);
            list.add(condition);
        });
        return list;
    }
}
