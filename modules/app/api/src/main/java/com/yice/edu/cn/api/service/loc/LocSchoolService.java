package com.yice.edu.cn.api.service.loc;

import com.yice.edu.cn.api.feign.loc.LocSchoolFeign;
import com.yice.edu.cn.common.pojo.loc.LocSchool;
import com.yice.edu.cn.common.pojo.loc.LocSchoolExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocSchoolService {
    @Autowired
    private LocSchoolFeign locSchoolFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public LocSchool findLocSchoolById(String id) {
        return locSchoolFeign.findLocSchoolById(id);
    }

    public LocSchool saveLocSchool(LocSchool locSchool) {
        return locSchoolFeign.saveLocSchool(locSchool);
    }

    public void batchSaveLocSchool(List<LocSchool> locSchools){
        locSchoolFeign.batchSaveLocSchool(locSchools);
    }

    public List<LocSchool> findLocSchoolListByCondition(LocSchool locSchool) {
        return locSchoolFeign.findLocSchoolListByCondition(locSchool);
    }

    public LocSchool findOneLocSchoolByCondition(LocSchool locSchool) {
        return locSchoolFeign.findOneLocSchoolByCondition(locSchool);
    }

    public long findLocSchoolCountByCondition(LocSchool locSchool) {
        return locSchoolFeign.findLocSchoolCountByCondition(locSchool);
    }

    public void updateLocSchool(LocSchool locSchool) {
        locSchoolFeign.updateLocSchool(locSchool);
    }

    public void updateLocSchoolForAll(LocSchool locSchool) {
        locSchoolFeign.updateLocSchoolForAll(locSchool);
    }

    public void deleteLocSchool(String id) {
        locSchoolFeign.deleteLocSchool(id);
    }

    public void deleteLocSchoolByCondition(LocSchool locSchool) {
        locSchoolFeign.deleteLocSchoolByCondition(locSchool);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public void saveLocSchoolAndSaveLocSchoolYear(LocSchoolExt locSchoolExt){
        locSchoolFeign.saveLocSchoolAndSaveLocSchoolYear(locSchoolExt);
    }

    public void deleteLocSchoolAndLocSchoolYear(String id){
        locSchoolFeign.deleteLocSchoolAndLocSchoolYear(id);
    }
}
