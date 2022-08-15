package com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.institution.MesClassHonor;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation.MesSchoolEvaluationVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value = "dy", contextId = "mesSchoolEvaluationFeign", path = "/mesSchoolEvaluation")
public interface MesSchoolEvaluationFeign {


    @PostMapping("/findSchoolWeekOrMonthByCondition")
    Map findSchoolWeekOrMonthByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo);

    @PostMapping("/findMesSchoolEvaluationListByCondition")
    Map findMesSchoolEvaluationListByCondition(MesSchoolEvaluationVo mesSchoolEvaluationVo);

    @GetMapping("/selectTableWhetherToShow/{schoolId}")
    List<MesInstitution> selectTableWhetherToShow(@PathVariable("schoolId") String schoolId);

    @PostMapping("/findEvaluationListByDay")
    Map<String, Object> findEvaluationListByDay(MesSchoolEvaluationVo mesSchoolEvaluationVo);

    @GetMapping("/saveMesSchoolEvaluationByAdmin/{time}")
    void saveMesSchoolEvaluationByAdmin(@PathVariable("time") String time);

    @GetMapping("/saveMesSchoolEvaluationHonor")
    void saveMesSchoolEvaluationHonor();

    @PostMapping("/findMesSchoolEvaluationHonor")
    List<List<MesClassHonor>> findMesSchoolEvaluationHonor(MesClassHonor mesClassHonor);

    @PostMapping("/findMesSchoolEvaluationHonorNewest")
    List<MesClassHonor> findMesSchoolEvaluationHonorNewest(MesClassHonor mesClassHonor);

    @GetMapping("/saveMesSchoolEvaluationHonorByAdmin/{time}")
    void saveMesSchoolEvaluationHonorByAdmin(@PathVariable("time") String time);
}
