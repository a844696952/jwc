package com.yice.edu.cn.osp.feignClient.dm.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "examModeFeign",path = "/examMode")
public interface ExamModeFeign {

    @PostMapping("/saveExamMode")
    ExamMode saveExamMode(ExamMode examMode);

    @GetMapping("/findExamModeById/{id}")
    ExamMode findExamModeById(@PathVariable("id") String id);

    @PostMapping("/findExamModeListByCondition")
    List<ExamMode> findExamModeListByCondition(ExamMode examMode);

    @PostMapping("/findExamModeCountByCondition")
    long findExamModeCountByCondition(ExamMode examMode);

    @GetMapping("/deleteExamMode/{id}")
    void deleteExamMode(@PathVariable("id") String id);


    @PostMapping("/close/{id}")
    void close(@PathVariable("id") String id);




    @PostMapping("/updateExamMode")
    ExamMode updateExamMode(ExamMode examMode);

    @PostMapping("/updateExamInfo")
    ExamMode updateExamInfo(ExamMode examMode);

    @PostMapping("/deleteExamInfo")
    ExamMode deleteExamInfo(ExamMode examMode);
}
