package com.yice.edu.cn.osp.feignClient.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutStartTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "stuInAndOutStartTimeFeign",path = "/stuInAndOutStartTime")
public interface StuInAndOutStartTimeFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuInAndOutStartTimeById/{id}")
    StuInAndOutStartTime findStuInAndOutStartTimeById(@PathVariable("id") String id);
    @PostMapping("/saveStuInAndOutStartTime")
    StuInAndOutStartTime saveStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime);
    @PostMapping("/batchSaveStuInAndOutStartTime")
    void batchSaveStuInAndOutStartTime(List<StuInAndOutStartTime> stuInAndOutStartTimes);
    @PostMapping("/findStuInAndOutStartTimeListByCondition")
    List<StuInAndOutStartTime> findStuInAndOutStartTimeListByCondition(StuInAndOutStartTime stuInAndOutStartTime);
    @PostMapping("/findOneStuInAndOutStartTimeByCondition")
    StuInAndOutStartTime findOneStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime);
    @PostMapping("/findStuInAndOutStartTimeCountByCondition")
    long findStuInAndOutStartTimeCountByCondition(StuInAndOutStartTime stuInAndOutStartTime);
    @PostMapping("/updateStuInAndOutStartTime")
    void updateStuInAndOutStartTime(StuInAndOutStartTime stuInAndOutStartTime);
    @PostMapping("/updateStuInAndOutStartTimeForAll")
    void updateStuInAndOutStartTimeForAll(StuInAndOutStartTime stuInAndOutStartTime);
    @GetMapping("/deleteStuInAndOutStartTime/{id}")
    void deleteStuInAndOutStartTime(@PathVariable("id") String id);
    @PostMapping("/deleteStuInAndOutStartTimeByCondition")
    void deleteStuInAndOutStartTimeByCondition(StuInAndOutStartTime stuInAndOutStartTime);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
