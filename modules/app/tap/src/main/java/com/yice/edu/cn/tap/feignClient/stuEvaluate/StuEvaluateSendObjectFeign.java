package com.yice.edu.cn.tap.feignClient.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/stuEvaluateSendObject")
public interface StuEvaluateSendObjectFeign {
    @GetMapping("/findStuEvaluateSendObjectById/{id}")
    StuEvaluateSendObject findStuEvaluateSendObjectById(@PathVariable("id") String id);
    @PostMapping("/saveStuEvaluateSendObject")
    StuEvaluateSendObject saveStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject);
    @PostMapping("/findStuEvaluateSendObjectListByCondition")
    List<StuEvaluateSendObject> findStuEvaluateSendObjectListByCondition(StuEvaluateSendObject stuEvaluateSendObject);
    @PostMapping("/findOneStuEvaluateSendObjectByCondition")
    StuEvaluateSendObject findOneStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject);
    @PostMapping("/findStuEvaluateSendObjectCountByCondition")
    long findStuEvaluateSendObjectCountByCondition(StuEvaluateSendObject stuEvaluateSendObject);
    @PostMapping("/updateStuEvaluateSendObject")
    void updateStuEvaluateSendObject(StuEvaluateSendObject stuEvaluateSendObject);
    @GetMapping("/deleteStuEvaluateSendObject/{id}")
    void deleteStuEvaluateSendObject(@PathVariable("id") String id);
    @PostMapping("/deleteStuEvaluateSendObjectByCondition")
    void deleteStuEvaluateSendObjectByCondition(StuEvaluateSendObject stuEvaluateSendObject);
}
