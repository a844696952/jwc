package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "dy", contextId = "mesSchoolEvaluationFeign", path = "/mesSchoolEvaluation")
public interface MesSchoolEvaluationFeign {

    @GetMapping("/saveMesSchoolEvaluation")
    void saveMesSchoolEvaluation();

    @GetMapping("/saveMesSchoolEvaluationHonor")
    void saveMesSchoolEvaluationHonor();

}
