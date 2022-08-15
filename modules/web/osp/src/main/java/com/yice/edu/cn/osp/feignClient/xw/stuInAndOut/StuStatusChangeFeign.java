package com.yice.edu.cn.osp.feignClient.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuStatusChange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "StuStatusChangeFeign",path = "/stuStatusChange")
public interface StuStatusChangeFeign {
    @GetMapping("/findStuStatusChangeById/{id}")
    StuStatusChange findStuStatusChangeById(@PathVariable("id") String id);
    @PostMapping("/saveStuStatusChange")
    StuStatusChange saveStuStatusChange(StuStatusChange stuStatusChange);
    @PostMapping("/findStuStatusChangeListByCondition")
    List<StuStatusChange> findStuStatusChangeListByCondition(StuStatusChange stuStatusChange);
    @PostMapping("/findOneStuStatusChangeByCondition")
    StuStatusChange findOneStuStatusChangeByCondition(StuStatusChange stuStatusChange);
    @PostMapping("/findStuStatusChangeCountByCondition")
    long findStuStatusChangeCountByCondition(StuStatusChange stuStatusChange);
    @PostMapping("/updateStuStatusChange")
    void updateStuStatusChange(StuStatusChange stuStatusChange);
    @GetMapping("/deleteStuStatusChange/{id}")
    void deleteStuStatusChange(@PathVariable("id") String id);
    @PostMapping("/deleteStuStatusChangeByCondition")
    void deleteStuStatusChangeByCondition(StuStatusChange stuStatusChange);
}
