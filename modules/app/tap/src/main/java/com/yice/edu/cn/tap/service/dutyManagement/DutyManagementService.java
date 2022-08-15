package com.yice.edu.cn.tap.service.dutyManagement;

import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.tap.feignClient.dutyManagement.DutyManagementFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DutyManagementService {

    @Autowired
    private DutyManagementFeign dutyManagementFeign;


    public CheckedDetail findCheckedDetailById(String id) {
        return dutyManagementFeign.findCheckedDetailById(id);
    }

    public CheckedDetail saveCheckedDetail(CheckedDetail checkedDetail) {
        return dutyManagementFeign.saveCheckedDetail(checkedDetail);
    }

    public List<CheckedDetail> findCheckedDetailListByCondition(CheckedDetail checkedDetail) {
        return dutyManagementFeign.findCheckedDetailListByCondition(checkedDetail);
    }

    public CheckedDetail findOneCheckedDetailByCondition(CheckedDetail checkedDetail) {
        return dutyManagementFeign.findOneCheckedDetailByCondition(checkedDetail);
    }

    public long findCheckedDetailCountByCondition(CheckedDetail checkedDetail) {
        return dutyManagementFeign.findCheckedDetailCountByCondition(checkedDetail);
    }

    public void updateCheckedDetail(CheckedDetail checkedDetail) {
        dutyManagementFeign.updateCheckedDetail(checkedDetail);
    }

    public void deleteCheckedDetail(String id) {
        dutyManagementFeign.deleteCheckedDetail(id);
    }

    public void deleteCheckedDetailByCondition(CheckedDetail checkedDetail) {
        dutyManagementFeign.deleteCheckedDetailByCondition(checkedDetail);
    }

    public List<CheckedDetail> findCheckedDetailListForAppTapByCondition(CheckedDetail checkedDetail){
        return dutyManagementFeign.findCheckedDetailListForAppTapByCondition(checkedDetail);
    }

    public List<CheckedDetail> findCheckedDetailsForAppTapByConditionNewPrincipal(CheckedDetail checkedDetail){
        return dutyManagementFeign.findCheckedDetailsForAppTapByConditionNewPrincipal(checkedDetail);
    }

    public List<CheckedDetail> findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(CheckedDetail checkedDetail){
        return dutyManagementFeign.findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(checkedDetail);
    }

    public void updateCheckedDetailForTapNewTeacher(CheckedDetail checkedDetail){
        dutyManagementFeign.updateCheckedDetailForTapNewTeacher(checkedDetail);
    }
}
