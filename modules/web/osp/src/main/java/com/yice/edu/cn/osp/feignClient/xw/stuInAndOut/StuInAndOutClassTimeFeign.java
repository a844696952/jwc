package com.yice.edu.cn.osp.feignClient.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutClassTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "stuInAndOutClassTimeFeign",path = "/stuInAndOutClassTime")
public interface StuInAndOutClassTimeFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuInAndOutClassTimeById/{id}")
    StuInAndOutClassTime findStuInAndOutClassTimeById(@PathVariable("id") String id);
    @PostMapping("/saveStuInAndOutClassTime")
    StuInAndOutClassTime saveStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime);
    @PostMapping("/batchSaveStuInAndOutClassTime")
    void batchSaveStuInAndOutClassTime(List<StuInAndOutClassTime> stuInAndOutClassTimes);
    @PostMapping("/findStuInAndOutClassTimeListByCondition")
    List<StuInAndOutClassTime> findStuInAndOutClassTimeListByCondition(StuInAndOutClassTime stuInAndOutClassTime);
    @PostMapping("/findOneStuInAndOutClassTimeByCondition")
    StuInAndOutClassTime findOneStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime);
    @PostMapping("/findStuInAndOutClassTimeCountByCondition")
    long findStuInAndOutClassTimeCountByCondition(StuInAndOutClassTime stuInAndOutClassTime);
    @PostMapping("/updateStuInAndOutClassTime")
    void updateStuInAndOutClassTime(StuInAndOutClassTime stuInAndOutClassTime);
    @PostMapping("/updateStuInAndOutClassTimeForAll")
    void updateStuInAndOutClassTimeForAll(StuInAndOutClassTime stuInAndOutClassTime);
    @GetMapping("/deleteStuInAndOutClassTime/{id}")
    void deleteStuInAndOutClassTime(@PathVariable("id") String id);
    @PostMapping("/deleteStuInAndOutClassTimeByCondition")
    void deleteStuInAndOutClassTimeByCondition(StuInAndOutClassTime stuInAndOutClassTime);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
