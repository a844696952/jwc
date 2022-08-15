package com.yice.edu.cn.osp.feignClient.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/deleteStuScoreByschoolExamId/{id}")
    void deleteStuScoreByschoolExamId(@PathVariable("id") String id);
    @PostMapping("/deleteStuScoreByCondition")
    void deleteStuScoreByCondition(StuScore stuScore);
    @PostMapping("/batchSaveStuScore")
    Map<String,Object> batchSaveStuScore(List<StuScore> stuScoreList);

    @GetMapping("/findStuScoreByschoolExamId/{id}")
    List<Map<String,Object>> findStuScoreByschoolExamId(@PathVariable("id") String id);
    @PostMapping("/findStuScoreListByCondition1")
    List<StuScore> findStuScoreListByCondition1(StuScore stuScore);
    @PostMapping("/findStuScoreCountByCondition1")
    long findStuScoreCountByCondition1(StuScore stuScore);
    @PostMapping("/findStudentByScoreSection")
    List<StuScore> findStudentByScoreSection(StuScore stuScore);
    @PostMapping("/findStuScoresForDownload/{schoolExamId}")
    List<StuScore> findStuScoresForDownload(@PathVariable("schoolExamId") String schoolExamId, List<String> courseIds);
}
