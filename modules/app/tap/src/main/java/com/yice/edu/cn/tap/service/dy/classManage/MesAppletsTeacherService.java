package com.yice.edu.cn.tap.service.dy.classManage;

import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.tap.feignClient.dy.classManage.MesAppletsTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAppletsTeacherService {
    @Autowired
    private MesAppletsTeacherFeign mesAppletsTeacherFeign;

    public MesAppletsTeacher findMesAppletsTeacherById(String id) {
        return mesAppletsTeacherFeign.findMesAppletsTeacherById(id);
    }

    public MesAppletsTeacher saveMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherFeign.saveMesAppletsTeacher(mesAppletsTeacher);
    }

    public List<MesAppletsTeacher> findMesAppletsTeacherListByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherFeign.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
    }

    public MesAppletsTeacher findOneMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherFeign.findOneMesAppletsTeacherByCondition(mesAppletsTeacher);
    }

    public long findMesAppletsTeacherCountByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherFeign.findMesAppletsTeacherCountByCondition(mesAppletsTeacher);
    }

    public void updateMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher) {
        mesAppletsTeacherFeign.updateMesAppletsTeacher(mesAppletsTeacher);
    }

    public void deleteMesAppletsTeacher(String id) {
        mesAppletsTeacherFeign.deleteMesAppletsTeacher(id);
    }

    public void deleteMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher) {
        mesAppletsTeacherFeign.deleteMesAppletsTeacherByCondition(mesAppletsTeacher);
    }
}
