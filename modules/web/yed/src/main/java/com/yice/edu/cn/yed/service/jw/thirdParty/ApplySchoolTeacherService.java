package com.yice.edu.cn.yed.service.jw.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchoolTeacher;
import com.yice.edu.cn.yed.feignClient.jw.thirdParty.ApplySchoolTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplySchoolTeacherService {
    @Autowired
    private ApplySchoolTeacherFeign applySchoolTeacherFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public ApplySchoolTeacher findApplySchoolTeacherById(String id) {
        return applySchoolTeacherFeign.findApplySchoolTeacherById(id);
    }

    public ApplySchoolTeacher saveApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher) {
        return applySchoolTeacherFeign.saveApplySchoolTeacher(applySchoolTeacher);
    }

    public void batchSaveApplySchoolTeacher(List<ApplySchoolTeacher> applySchoolTeachers){
        applySchoolTeacherFeign.batchSaveApplySchoolTeacher(applySchoolTeachers);
    }

    public List<ApplySchoolTeacher> findApplySchoolTeacherListByCondition(ApplySchoolTeacher applySchoolTeacher) {
        return applySchoolTeacherFeign.findApplySchoolTeacherListByCondition(applySchoolTeacher);
    }

    public ApplySchoolTeacher findOneApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher) {
        return applySchoolTeacherFeign.findOneApplySchoolTeacherByCondition(applySchoolTeacher);
    }

    public long findApplySchoolTeacherCountByCondition(ApplySchoolTeacher applySchoolTeacher) {
        return applySchoolTeacherFeign.findApplySchoolTeacherCountByCondition(applySchoolTeacher);
    }

    public void updateApplySchoolTeacher(ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacherFeign.updateApplySchoolTeacher(applySchoolTeacher);
    }

    public void updateApplySchoolTeacherForAll(ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacherFeign.updateApplySchoolTeacherForAll(applySchoolTeacher);
    }

    public void deleteApplySchoolTeacher(String id) {
        applySchoolTeacherFeign.deleteApplySchoolTeacher(id);
    }

    public void deleteApplySchoolTeacherByCondition(ApplySchoolTeacher applySchoolTeacher) {
        applySchoolTeacherFeign.deleteApplySchoolTeacherByCondition(applySchoolTeacher);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
