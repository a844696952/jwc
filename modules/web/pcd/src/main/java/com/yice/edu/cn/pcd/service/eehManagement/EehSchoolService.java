package com.yice.edu.cn.pcd.service.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.pcd.feignClient.eehManagement.EehSchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehSchoolService {
    @Autowired
    private EehSchoolFeign eehSchoolFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public EehSchool findEehSchoolById(String id) {
        return eehSchoolFeign.findEehSchoolById(id);
    }

    public EehSchool saveEehSchool(EehSchool eehSchool) {
        return eehSchoolFeign.saveEehSchool(eehSchool);
    }

    public void batchSaveEehSchool(List<EehSchool> eehSchools){
        eehSchoolFeign.batchSaveEehSchool(eehSchools);
    }

    public List<EehSchool> findEehSchoolListByCondition(EehSchool eehSchool) {
        return eehSchoolFeign.findEehSchoolListByCondition(eehSchool);
    }

    public EehSchool findOneEehSchoolByCondition(EehSchool eehSchool) {
        return eehSchoolFeign.findOneEehSchoolByCondition(eehSchool);
    }

    public long findEehSchoolCountByCondition(EehSchool eehSchool) {
        return eehSchoolFeign.findEehSchoolCountByCondition(eehSchool);
    }

    public void updateEehSchool(EehSchool eehSchool) {
        eehSchoolFeign.updateEehSchool(eehSchool);
    }

    public void updateEehSchoolForAll(EehSchool eehSchool) {
        eehSchoolFeign.updateEehSchoolForAll(eehSchool);
    }

    public void deleteEehSchool(String id) {
        eehSchoolFeign.deleteEehSchool(id);
    }

    public void deleteEehSchoolByCondition(EehSchool eehSchool) {
        eehSchoolFeign.deleteEehSchoolByCondition(eehSchool);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
   public List<EehSchool> findEehSchoolListByEehIds(List<String> eehids) {
    return eehSchoolFeign.findEehSchoolListByEehIds(eehids);
   }
}
