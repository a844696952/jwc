package com.yice.edu.cn.yed.service.baseData.eehManagement;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.yed.feignClient.baseData.eehManagement.EehSchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EehSchoolService {
    @Autowired
    private EehSchoolFeign eehSchoolFeign;

    public EehSchool findEehSchoolById(String id) {
        return eehSchoolFeign.findEehSchoolById(id);
    }

    public EehSchool saveEehSchool(EehSchool eehSchool) {
        return eehSchoolFeign.saveEehSchool(eehSchool);
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

    public void deleteEehSchool(String id) {
        eehSchoolFeign.deleteEehSchool(id);
    }

    public void deleteEehSchoolByCondition(EehSchool eehSchool) {
        eehSchoolFeign.deleteEehSchoolByCondition(eehSchool);
    }


    public void saveEehSchoolNew(EehSchool eehSchool) {
         eehSchoolFeign.saveEehSchoolNew(eehSchool);
    }

    public List<EehSchool> findCheckEehSchoolListById(String id){
       return eehSchoolFeign.findCheckEehSchoolListById(id);
    }
}
