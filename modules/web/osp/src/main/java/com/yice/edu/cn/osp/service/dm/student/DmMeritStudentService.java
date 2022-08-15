package com.yice.edu.cn.osp.service.dm.student;

import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.osp.feignClient.dm.student.DmMeritStudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmMeritStudentService {
    @Autowired
    private DmMeritStudentFeign dmMeritStudentFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmMeritStudent findDmMeritStudentById(String id) {
        return dmMeritStudentFeign.findDmMeritStudentById(id);
    }

    public void saveDmMeritStudentOrUpdate(List<DmMeritStudent> dmMeritStudent) {
         dmMeritStudentFeign.saveDmMeritStudentOrUpdate(dmMeritStudent);
    }

    public void batchSaveDmMeritStudent(List<DmMeritStudent> dmMeritStudents){
        dmMeritStudentFeign.batchSaveDmMeritStudent(dmMeritStudents);
    }

    public List<DmMeritStudent> findDmMeritStudentListByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentFeign.findDmMeritStudentListByCondition(dmMeritStudent);
    }

    public DmMeritStudent findOneDmMeritStudentByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentFeign.findOneDmMeritStudentByCondition(dmMeritStudent);
    }

    public long findDmMeritStudentCountByCondition(DmMeritStudent dmMeritStudent) {
        return dmMeritStudentFeign.findDmMeritStudentCountByCondition(dmMeritStudent);
    }

    public void updateDmMeritStudent(DmMeritStudent dmMeritStudent) {
        dmMeritStudentFeign.updateDmMeritStudent(dmMeritStudent);
    }

    public void updateDmMeritStudentForAll(DmMeritStudent dmMeritStudent) {
        dmMeritStudentFeign.updateDmMeritStudentForAll(dmMeritStudent);
    }

    public void deleteDmMeritStudent(String id) {
        dmMeritStudentFeign.deleteDmMeritStudent(id);
    }

    public void deleteDmMeritStudentByCondition(DmMeritStudent dmMeritStudent) {
        dmMeritStudentFeign.deleteDmMeritStudentByCondition(dmMeritStudent);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<AllStudent> findAllJwClassesAndStudents(String schoolId) {
        return dmMeritStudentFeign.findAllJwClassesAndStudents(schoolId);
    }

    public List<DmMeritStudent> findAll(String schoolId) {
        return dmMeritStudentFeign.findAll(schoolId);
    }
}
