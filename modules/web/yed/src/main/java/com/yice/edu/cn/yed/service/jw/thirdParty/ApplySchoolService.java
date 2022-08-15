package com.yice.edu.cn.yed.service.jw.thirdParty;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import com.yice.edu.cn.yed.feignClient.jw.thirdParty.ApplySchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplySchoolService {
    @Autowired
    private ApplySchoolFeign applySchoolFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ApplySchool findApplySchoolById(String id) {
        return applySchoolFeign.findApplySchoolById(id);
    }

    public List<ApplySchool> saveApplySchool(List<ApplySchool> applySchoolList) {
        return applySchoolFeign.saveApplySchool(applySchoolList);
    }

    public void batchSaveApplySchool(List<ApplySchool> applySchools){
        applySchoolFeign.batchSaveApplySchool(applySchools);
    }

    public List<ApplySchool> findApplySchoolListByCondition(ApplySchool applySchool) {
        return applySchoolFeign.findApplySchoolListByCondition(applySchool);
    }

    public ApplySchool findOneApplySchoolByCondition(ApplySchool applySchool) {
        return applySchoolFeign.findOneApplySchoolByCondition(applySchool);
    }

    public long findApplySchoolCountByCondition(ApplySchool applySchool) {
        return applySchoolFeign.findApplySchoolCountByCondition(applySchool);
    }

    public void updateApplySchool(ApplySchool applySchool) {
        applySchoolFeign.updateApplySchool(applySchool);
    }

    public void updateApplySchoolForAll(ApplySchool applySchool) {
        applySchoolFeign.updateApplySchoolForAll(applySchool);
    }

    public void deleteApplySchool(String id) {
        applySchoolFeign.deleteApplySchool(id);
    }

    public void deleteApplySchoolByCondition(ApplySchool applySchool) {
        applySchoolFeign.deleteApplySchoolByCondition(applySchool);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
