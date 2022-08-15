package com.yice.edu.cn.osp.service.dm.teacher;

import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import com.yice.edu.cn.osp.feignClient.dm.teacher.DmFamousTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmFamousTeacherService {
    @Autowired
    private DmFamousTeacherFeign dmFamousTeacherFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public DmFamousTeacher findDmFamousTeacherById(String id) {
        return dmFamousTeacherFeign.findDmFamousTeacherById(id);
    }

    public DmFamousTeacher saveDmFamousTeacher(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherFeign.saveDmFamousTeacher(dmFamousTeacher);
    }

    public void batchSaveDmFamousTeacher(List<DmFamousTeacher> dmFamousTeachers){
        dmFamousTeacherFeign.batchSaveDmFamousTeacher(dmFamousTeachers);
    }

    public List<DmFamousTeacher> findDmFamousTeacherListByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherFeign.findDmFamousTeacherListByCondition(dmFamousTeacher);
    }

    public DmFamousTeacher findOneDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherFeign.findOneDmFamousTeacherByCondition(dmFamousTeacher);
    }

    public long findDmFamousTeacherCountByCondition(DmFamousTeacher dmFamousTeacher) {
        return dmFamousTeacherFeign.findDmFamousTeacherCountByCondition(dmFamousTeacher);
    }

    public void updateDmFamousTeacher(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherFeign.updateDmFamousTeacher(dmFamousTeacher);
    }

    public void updateDmFamousTeacherForAll(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherFeign.updateDmFamousTeacherForAll(dmFamousTeacher);
    }

    public void deleteDmFamousTeacher(String id) {
        dmFamousTeacherFeign.deleteDmFamousTeacher(id);
    }

    public void deleteDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher) {
        dmFamousTeacherFeign.deleteDmFamousTeacherByCondition(dmFamousTeacher);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
