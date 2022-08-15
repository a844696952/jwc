package com.yice.edu.cn.tap.feignClient.dy.classManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.rules.MesAppletsRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",contextId = "mesAppletsRuleFeign",path = "/mesAppletsRule")
public interface MesAppletsRuleFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findMesAppletsRuleById/{id}")
    MesAppletsRule findMesAppletsRuleById(@PathVariable("id") String id);
    @PostMapping("/saveMesAppletsRule")
    ResponseJson saveMesAppletsRule(MesAppletsRule mesAppletsRule);
    @PostMapping("/batchSaveMesAppletsRule")
    void batchSaveMesAppletsRule(List<MesAppletsRule> mesAppletsRules);
    @PostMapping("/findMesAppletsRuleListByCondition")
    List<MesAppletsRule> findMesAppletsRuleListByCondition(MesAppletsRule mesAppletsRule);
    @PostMapping("/findOneMesAppletsRuleByCondition")
    MesAppletsRule findOneMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule);
    @PostMapping("/findMesAppletsRuleCountByCondition")
    long findMesAppletsRuleCountByCondition(MesAppletsRule mesAppletsRule);
    @PostMapping("/updateMesAppletsRule")
    ResponseJson updateMesAppletsRule(MesAppletsRule mesAppletsRule);
    @PostMapping("/updateMesAppletsRuleForAll")
    void updateMesAppletsRuleForAll(MesAppletsRule mesAppletsRule);
    @GetMapping("/deleteMesAppletsRule/{id}")
    void deleteMesAppletsRule(@PathVariable("id") String id);
    @PostMapping("/deleteMesAppletsRuleByCondition")
    void deleteMesAppletsRuleByCondition(MesAppletsRule mesAppletsRule);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @PostMapping("/updateMove")
    ResponseJson updateMove(MesAppletsRule mesAppletsRule);
    @PostMapping("/findMesAppletsRulesAndStatisticDataByCondition")
    List<MesAppletsRule> findMesAppletsRulesAndStatisticDataByCondition(MesAppletsRule mesAppletsRule);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
