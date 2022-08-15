package com.yice.edu.cn.jy.feignClient.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "stuScoreFeign",path = "/stuScore")
public interface StuScoreFeign {
    @GetMapping("/findStuScoreById/{id}")
    StuScore findStuScoreById(@PathVariable("id") String id);
    @PostMapping("/saveStuScore")
    StuScore saveStuScore(StuScore stuScore);
    @PostMapping("/findStuScoreListByCondition")
    List<StuScore> findStuScoreListByCondition(StuScore stuScore);
    @PostMapping("/findOneStuScoreByCondition")
    StuScore findOneStuScoreByCondition(StuScore stuScore);
    @PostMapping("/findStuScoreCountByCondition")
    long findStuScoreCountByCondition(StuScore stuScore);
    @PostMapping("/updateStuScore")
    void updateStuScore(StuScore stuScore);
    @GetMapping("/deleteStuScore/{id}")
    void deleteStuScore(@PathVariable("id") String id);
    @PostMapping("/deleteStuScoreByCondition")
    void deleteStuScoreByCondition(StuScore stuScore);
}
