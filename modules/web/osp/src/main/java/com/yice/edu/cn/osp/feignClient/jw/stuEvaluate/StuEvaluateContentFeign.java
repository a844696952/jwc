package com.yice.edu.cn.osp.feignClient.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "stuEvaluateContentFeign",path = "/stuEvaluateContent")
public interface StuEvaluateContentFeign {
    @GetMapping("/findStuEvaluateContentById/{id}")
    StuEvaluateContent findStuEvaluateContentById(@PathVariable("id") String id);
    @PostMapping("/saveStuEvaluateContent")
    StuEvaluateContent saveStuEvaluateContent(StuEvaluateContent stuEvaluateContent);
    @PostMapping("/findStuEvaluateContentListByCondition1")
    List<StuEvaluateContent> findStuEvaluateContentListByCondition(StuEvaluateContent stuEvaluateContent);
    @PostMapping("/findOneStuEvaluateContentByCondition")
    StuEvaluateContent findOneStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent);
    @PostMapping("/findStuEvaluateContentCountByCondition")
    long findStuEvaluateContentCountByCondition(StuEvaluateContent stuEvaluateContent);
    @PostMapping("/updateStuEvaluateContent")
    void updateStuEvaluateContent(StuEvaluateContent stuEvaluateContent);
    @GetMapping("/deleteStuEvaluateContent/{id}")
    void deleteStuEvaluateContent(@PathVariable("id") String id);
    @PostMapping("/deleteStuEvaluateContentByCondition")
    void deleteStuEvaluateContentByCondition(StuEvaluateContent stuEvaluateContent);
}
