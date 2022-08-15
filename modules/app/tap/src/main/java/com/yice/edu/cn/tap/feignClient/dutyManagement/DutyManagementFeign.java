package com.yice.edu.cn.tap.feignClient.dutyManagement;

import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",path = "/checkedDetail")
public interface DutyManagementFeign {

    @GetMapping("/findCheckedDetailById/{id}")
    CheckedDetail findCheckedDetailById(@PathVariable("id") String id);
    @PostMapping("/saveCheckedDetail")
    CheckedDetail saveCheckedDetail(CheckedDetail checkedDetail);
    @PostMapping("/findCheckedDetailListByCondition")
    List<CheckedDetail> findCheckedDetailListByCondition(CheckedDetail checkedDetail);
    @PostMapping("/findOneCheckedDetailByCondition")
    CheckedDetail findOneCheckedDetailByCondition(CheckedDetail checkedDetail);
    @PostMapping("/findCheckedDetailCountByCondition")
    long findCheckedDetailCountByCondition(CheckedDetail checkedDetail);
    @PostMapping("/updateCheckedDetail")
    void updateCheckedDetail(CheckedDetail checkedDetail);
    @GetMapping("/deleteCheckedDetail/{id}")
    void deleteCheckedDetail(@PathVariable("id") String id);
    @PostMapping("/deleteCheckedDetailByCondition")
    void deleteCheckedDetailByCondition(CheckedDetail checkedDetail);
    @PostMapping("/findCheckedDetailListForAppTapByCondition") //教师查询
    List<CheckedDetail> findCheckedDetailListForAppTapByCondition(CheckedDetail checkedDetail);

    @PostMapping("/findCheckedDetailsForAppTapByConditionNewPrincipal") //校长查询
    List<CheckedDetail> findCheckedDetailsForAppTapByConditionNewPrincipal(CheckedDetail checkedDetail);

    @PostMapping("/findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal")//校长查询值班统计
    List<CheckedDetail> findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(CheckedDetail checkedDetail);

    @PostMapping("/updateCheckedDetailForTapNewTeacher")  //签到
    void updateCheckedDetailForTapNewTeacher(CheckedDetail checkedDetail);

}
