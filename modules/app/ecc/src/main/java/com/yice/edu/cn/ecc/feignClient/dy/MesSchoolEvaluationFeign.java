package com.yice.edu.cn.ecc.feignClient.dy;

import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesSchoolEvaluationFeign", path = "/mesSchoolEvaluation")
public interface MesSchoolEvaluationFeign {
    @PostMapping("/findMesSchoolEvaluationHonor")
    List<List<MesClassHonor>> findMesSchoolEvaluationHonor(MesClassHonor mesClassHonor);

    @PostMapping("/findMesSchoolEvaluationHonorNewest")
    List<MesClassHonor> findMesSchoolEvaluationHonorNewest(MesClassHonor mesClassHonor);
}
