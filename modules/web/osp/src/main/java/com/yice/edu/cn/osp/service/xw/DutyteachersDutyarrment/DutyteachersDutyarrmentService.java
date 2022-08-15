package com.yice.edu.cn.osp.service.xw.DutyteachersDutyarrment;

import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.osp.feignClient.xw.DutyteachersDutyarrment.DutyteachersDutyarrmentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyteachersDutyarrmentService {
    @Autowired
    private DutyteachersDutyarrmentFeign dutyteachersDutyarrmentFeign;

    public DutyteachersDutyarrment findDutyteachersDutyarrmentById(String id) {
        return dutyteachersDutyarrmentFeign.findDutyteachersDutyarrmentById(id);
    }

    public DutyteachersDutyarrment saveDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentFeign.saveDutyteachersDutyarrment(dutyteachersDutyarrment);
    }

    public List<DutyteachersDutyarrment> findDutyteachersDutyarrmentListByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentFeign.findDutyteachersDutyarrmentListByCondition(dutyteachersDutyarrment);
    }

    public DutyteachersDutyarrment findOneDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentFeign.findOneDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }

    public long findDutyteachersDutyarrmentCountByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        return dutyteachersDutyarrmentFeign.findDutyteachersDutyarrmentCountByCondition(dutyteachersDutyarrment);
    }

    public void updateDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment) {
        dutyteachersDutyarrmentFeign.updateDutyteachersDutyarrment(dutyteachersDutyarrment);
    }

    public void deleteDutyteachersDutyarrment(String id) {
        dutyteachersDutyarrmentFeign.deleteDutyteachersDutyarrment(id);
    }

    public void deleteDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment) {
        dutyteachersDutyarrmentFeign.deleteDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }
}
