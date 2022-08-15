package com.yice.edu.cn.osp.service.xw.dutyArrment;

import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.osp.feignClient.xw.checkedDetail.CheckedDetailFeign;
import com.yice.edu.cn.osp.feignClient.xw.dutyArrment.DutyArrmentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyArrmentService {
    @Autowired
    private DutyArrmentFeign dutyArrmentFeign;
    @Autowired
    private CheckedDetailFeign checkedDetailFeign;

    public DutyArrment findDutyArrmentById(String id) {
        return dutyArrmentFeign.findDutyArrmentById(id);
    }

    public DutyArrment saveDutyArrment(DutyArrment dutyArrment) {
        return dutyArrmentFeign.saveDutyArrment(dutyArrment);
    }

    public List<DutyArrment> findDutyArrmentListByCondition(DutyArrment dutyArrment) {
        return dutyArrmentFeign.findDutyArrmentListByCondition(dutyArrment);
    }

    public DutyArrment findOneDutyArrmentByCondition(DutyArrment dutyArrment) {
        return dutyArrmentFeign.findOneDutyArrmentByCondition(dutyArrment);
    }

    public long findDutyArrmentCountByCondition(DutyArrment dutyArrment) {
        return dutyArrmentFeign.findDutyArrmentCountByCondition(dutyArrment);
    }

    public void updateDutyArrment(DutyArrment dutyArrment) {
        dutyArrmentFeign.updateDutyArrment(dutyArrment);
    }

    public void deleteDutyArrment(String id) {
        dutyArrmentFeign.deleteDutyArrment(id);
    }

    public void deleteDutyArrmentByCondition(DutyArrment dutyArrment) {
        dutyArrmentFeign.deleteDutyArrmentByCondition(dutyArrment);
    }

    public List<DutyArrment> findDutyArrmentListByConditionForLike(DutyArrment dutyArrment) {
        return dutyArrmentFeign.findDutyArrmentListByConditionForLike(dutyArrment);
    }

    public long findDutyArrmentCountByConditionForLike(DutyArrment dutyArrment) {
        return dutyArrmentFeign.findDutyArrmentCountByConditionForLike(dutyArrment);
    }
}
