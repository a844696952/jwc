package com.yice.edu.cn.osp.feignClient.xw.stuLeaveManage;

import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "stuLeaveFeign",path = "/stuLeave")
public interface StuLeaveFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuLeaveById/{id}")
    StuLeave findStuLeaveById(@PathVariable("id") String id);
    @PostMapping("/saveStuLeave")
    StuLeave saveStuLeave(StuLeave stuLeave);
    @PostMapping("/batchSaveStuLeave")
    void batchSaveStuLeave(List<StuLeave> stuLeaves);
    @PostMapping("/findStuLeaveListByCondition")
    List<StuLeave> findStuLeaveListByCondition(StuLeave stuLeave);
    @PostMapping("/findOneStuLeaveByCondition")
    StuLeave findOneStuLeaveByCondition(StuLeave stuLeave);
    @PostMapping("/findStuLeaveCountByCondition")
    long findStuLeaveCountByCondition(StuLeave stuLeave);
    @PostMapping("/updateStuLeave")
    void updateStuLeave(StuLeave stuLeave);
    @PostMapping("/updateStuLeaveForAll")
    void updateStuLeaveForAll(StuLeave stuLeave);
    @GetMapping("/deleteStuLeave/{id}")
    void deleteStuLeave(@PathVariable("id") String id);
    @PostMapping("/deleteStuLeaveByCondition")
    void deleteStuLeaveByCondition(StuLeave stuLeave);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findStuLeaveListByConditionForWH")
    List<StuLeave> findStuLeaveListByConditionForWH(StuLeave stuLeave);
    @PostMapping("/findStuLeaveCountByConditionForWH")
    long findStuLeaveCountByConditionForWH(StuLeave stuLeave);

}
